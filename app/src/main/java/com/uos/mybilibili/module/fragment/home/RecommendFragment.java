package com.uos.mybilibili.module.fragment.home;

import android.content.Intent;
import android.widget.ImageView;

import com.uos.mybilibili.R;

import butterknife.BindView;

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

    @Override
    protected void lazyLoadData() {
        mPresenter.getRecommendData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mIvRank.setOnClickListener(view-> startActivity(new Intent(getActivity(), AllStationRankActivity.class)));
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new RecommendAdapter(mList);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAdapter.setSpanSizeLookup((gridLayoutManager, i) -> mList.get(i).spanSize);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        //添加分割条
        VerticalDividerItemDecoration build = new VerticalDividerItemDecoration.Builder(getActivity())
                .color(AppUtils.getColor(R.color.transparent))
                // .color(AppUtils.getColor(R.color.colorPrimary))
                .sizeResId(R.dimen.dp10)
                .showLastDivider()
                .build();
        mRecycler.addItemDecoration(build);
    }

    @Override
    public void showRecommend(List<Recommend> recommend) {
        Stream.of(recommend)
                .forEach(recommendBean -> {
                    if (EmptyUtils.isNotEmpty(recommendBean.banner_item)) {
                        mList.add(new MulRecommend(MulRecommend.TYPR_HEADER, MulRecommend.HEADER_SPAN_SIZE, recommendBean.banner_item));
                    } else {
                        mList.add(new MulRecommend(MulRecommend.TYPE_ITEM, MulRecommend.ITEM_SPAN_SIZE, recommendBean));
                    }
                });
        finishTask();
    }

    @Override
    protected void finishTask() {
        mAdapter.notifyDataSetChanged();
    }

}
