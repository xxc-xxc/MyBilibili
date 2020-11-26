package com.uos.mybilibili.module.fragment.home;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import com.annimon.stream.Stream;
import com.uos.mybilibili.R;
import com.uos.mybilibili.base.BaseRefreshFragment;
import com.uos.mybilibili.bean.live.LivePartition;
import com.uos.mybilibili.bean.live.LiveRecommend;
import com.uos.mybilibili.module.adapter.home.SectionedRVAdapter;
import com.uos.mybilibili.module.adapter.home.section.LiveBannerSection;
import com.uos.mybilibili.module.adapter.home.section.LiveEntranceSection;
import com.uos.mybilibili.module.adapter.home.section.LiveRecommendBannerSection;
import com.uos.mybilibili.module.adapter.home.section.LiveRecommendPartitionSection;
import com.uos.mybilibili.module.adapter.home.section.LiveRecommendSection;
import com.uos.mybilibili.mvp.contract.LiveContract;
import com.uos.mybilibili.mvp.presenter.LivePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By xxc
 * Date: 2020/10/12 15:53
 * Desc: 直播
 */
public class LiveFragment extends BaseRefreshFragment<LivePresenter,
        LiveRecommend.RecommendDataBean.LivesBean> implements LiveContract.View {

    private SectionedRVAdapter mSectionedAdapter;
    private List<LivePartition.BannerBean> mBannerList = new ArrayList<>();//轮播条
    private List<LiveRecommend.RecommendDataBean.BannerDataBean> mBannerRecommendList = new ArrayList<>();//推荐
    private List<LivePartition.PartitionsBean> mPartitionsBeanList = new ArrayList<>();//推荐直播
    private LiveRecommend.RecommendDataBean.PartitionBean mPartitionBean;
    private volatile LivePartition mLivePartition;

    public static LiveFragment newInstance() {
        return new LiveFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_live;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initRecyclerView() {
        mSectionedAdapter = new SectionedRVAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRVAdapter.VIEW_TYPE_HEADER:
                        return 2;//2格
                    case SectionedRVAdapter.VIEW_TYPE_FOOTER:
                        return 2;//2格
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mSectionedAdapter);
    }

    /**
     * 请求数据
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.getLiveData();
    }

    @Override
    protected void clear() {
        mBannerRecommendList.clear();
        mBannerList.clear();
        mPartitionsBeanList.clear();
        mSectionedAdapter.removeAllSections();
    }

    @Override
    public void showLiveRecommend(LiveRecommend liveRecommend) {
        mBannerList.addAll(mLivePartition.banner);
        mPartitionsBeanList.addAll(mLivePartition.partitions);
        mList.addAll(liveRecommend.recommend_data.lives);
        mBannerRecommendList.addAll(liveRecommend.recommend_data.banner_data);
        mPartitionBean = liveRecommend.recommend_data.partition;
        finishTask();
    }

    @Override
    public void showLivePartition(LivePartition livePartition) {
        mLivePartition = livePartition;
    }

    @Override
    protected void finishTask() {
        mSectionedAdapter.addSection(new LiveBannerSection(mBannerList));
        mSectionedAdapter.addSection(new LiveEntranceSection());
        //推荐主播
        if (mBannerRecommendList.size() != 0) {
            int allot = mList.size() / 2;
            if (mBannerRecommendList.size() == 1) {
                mSectionedAdapter.addSection(new LiveRecommendSection(true, false,
                        mPartitionBean.name,
                        mPartitionBean.sub_icon.src, mPartitionBean.count + "",
                        mList.subList(0, allot)));
                mSectionedAdapter.addSection(new LiveRecommendBannerSection(mBannerRecommendList.get(0)));
                mSectionedAdapter.addSection(new LiveRecommendSection(false, true,
                        mPartitionBean.name,
                        mPartitionBean.sub_icon.src, mPartitionBean.count + "",
                        mList.subList(allot, mList.size())));
            } else {
                mSectionedAdapter.addSection(new LiveRecommendSection(true, false, mPartitionBean.name,
                        mPartitionBean.sub_icon.src, mPartitionBean.count + "",
                        mList.subList(0, allot),
                        mBannerRecommendList.get(0)));
                mSectionedAdapter.addSection(new LiveRecommendBannerSection(mBannerRecommendList.get(1)));
                mSectionedAdapter.addSection(new LiveRecommendSection(false, true, mPartitionBean.name,
                        mPartitionBean.sub_icon.src, mPartitionBean.count + "",
                        mList.subList(allot, mList.size())));
            }
        } else {
            mSectionedAdapter.addSection(new LiveRecommendSection(true, true, mPartitionBean.name,
                    mPartitionBean.sub_icon.src
                    , mPartitionBean.count + "", mList));
        }
        //分区
        Stream.of(mPartitionsBeanList.subList(0, mPartitionsBeanList.size() - 1)).forEach(partitionsBean ->
                mSectionedAdapter.addSection(new LiveRecommendPartitionSection(partitionsBean.partition.name,
                        partitionsBean.partition.sub_icon.src,
                        partitionsBean.partition.count + "", partitionsBean.lives.subList(0, 4))));
        //显示最后---更多直播
        mSectionedAdapter.addSection(
                new LiveRecommendPartitionSection(true, mPartitionsBeanList.get(mPartitionsBeanList.size() - 1).partition.name,
                        mPartitionsBeanList.get(mPartitionsBeanList.size() - 1).partition.sub_icon.src,
                        mPartitionsBeanList.get(mPartitionsBeanList.size() - 1).partition.count + "",
                        mPartitionsBeanList.get(mPartitionsBeanList.size() - 1).lives.subList(0, 4)));
        mSectionedAdapter.notifyDataSetChanged();
    }
}
