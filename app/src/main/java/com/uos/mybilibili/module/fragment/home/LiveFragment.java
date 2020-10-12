package com.uos.mybilibili.module.fragment.home;

import androidx.recyclerview.widget.GridLayoutManager;

import com.uos.mybilibili.R;
import com.uos.mybilibili.base.BaseRefreshFragment;
import com.uos.mybilibili.bean.live.LivePartition;
import com.uos.mybilibili.bean.live.LiveRecommend;
import com.uos.mybilibili.module.adapter.home.SectionedRVAdapter;
import com.uos.mybilibili.mvp.contract.LiveContract;
import com.uos.mybilibili.mvp.presenter.LivePresenter;

/**
 * Create By xxc
 * Date: 2020/10/12 15:53
 * Desc: 直播
 */
public class LiveFragment extends BaseRefreshFragment<LivePresenter,
        LiveRecommend.RecommendDataBean.LivesBean> implements LiveContract.View {

    private SectionedRVAdapter mSectionedAdapter;

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
    public void showLiveRecommend(LiveRecommend liveRecommend) {

    }

    @Override
    public void showLivePartition(LivePartition livePartition) {

    }
}
