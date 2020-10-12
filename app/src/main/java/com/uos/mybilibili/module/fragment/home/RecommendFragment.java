package com.uos.mybilibili.module.fragment.home;

import android.content.Intent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.listener.GridSpanSizeLookup;
import com.uos.mybilibili.R;
import com.uos.mybilibili.base.BaseRefreshFragment;
import com.uos.mybilibili.bean.recommend.MulRecommend;
import com.uos.mybilibili.bean.recommend.Recommend;
import com.uos.mybilibili.module.adapter.home.RecommendAdapter;
import com.uos.mybilibili.mvp.contract.RecommendContract;
import com.uos.mybilibili.mvp.presenter.RecommendPresenter;
import com.uos.mybilibili.utils.AppUtils;
import com.uos.mybilibili.utils.EmptyUtils;
import com.uos.mybilibili.widget.devider.VerticalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import java8.util.stream.StreamSupport;

/**
 * @author xxc
 * @date 创建时间：2020年10月9日17:41:23
 * 描述:推荐
 */
public class RecommendFragment extends BaseRefreshFragment<RecommendPresenter, MulRecommend>
        implements RecommendContract.View {

    @BindView(R.id.iv_rank)
    ImageView mIvRank;

    private RecommendAdapter mAdapter;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    /**
     * 请求推荐位数据
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.getRecommend();
    }

    @Override
    public void initView() {
        super.initView();
        // 全站排行
//        mIvRank.setOnClickListener(view-> startActivity(new Intent(getActivity(), AllStationRankActivity.class)));
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new RecommendAdapter(mContext, mList);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAdapter.setGridSpanSizeLookup((gridLayoutManager, viewType, position) -> mList.get(position).spanSize);
//        mAdapter.setSpanSizeLookup((gridLayoutManager, i) -> mList.get(i).spanSize);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        //添加分割条
        VerticalDividerItemDecoration build = new VerticalDividerItemDecoration.Builder(getActivity())
                .color(AppUtils.getColor(R.color.transparent))
                // .color(AppUtils.getColor(R.color.colorPrimary))
                .sizeResId(R.dimen.dp_10)
                .showLastDivider()
                .build();
        mRecycler.addItemDecoration(build);
    }

    /**
     * 成功请求到数据，刷新页面
     * @param recommendList
     */
    @Override
    public void showRecommend(List<Recommend> recommendList) {
        // 使用StreamSupport支持Java8的语法，否则要将minSdkVersion升到24
        StreamSupport.stream(recommendList)
                .forEach(recommend -> {
                    if (EmptyUtils.isNotEmpty(recommend.banner_item)) {
                        mList.add(new MulRecommend(MulRecommend.TYPE_HEADER, MulRecommend.HEADER_SPAN_SIZE, recommend.banner_item));
                    } else {
                        mList.add(new MulRecommend(MulRecommend.TYPE_ITEM, MulRecommend.ITEM_SPAN_SIZE, recommend));
                    }
                });
//        Stream.of(recommendList)
//                .forEach(recommendBean -> {
//                    if (EmptyUtils.isNotEmpty(recommendBean.banner_item)) {
//                        mList.add(new MulRecommend(MulRecommend.TYPE_HEADER, MulRecommend.HEADER_SPAN_SIZE, recommendBean.banner_item));
//                    } else {
//                        mList.add(new MulRecommend(MulRecommend.TYPE_ITEM, MulRecommend.ITEM_SPAN_SIZE, recommendBean));
//                    }
//                });
        finishTask();
    }

    @Override
    protected void finishTask() {
        mAdapter.notifyDataSetChanged();
    }

}
