package com.uos.mybilibili.base;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.uos.mybilibili.R;
import com.uos.mybilibili.utils.AppUtils;
import com.uos.mybilibili.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxc
 * @date 创建时间：2020年10月9日17:43:33
 * 描述:基础刷新的Fragment
 */

public abstract class BaseRefreshFragment<T extends BaseContract.BasePresenter, K>
        extends BaseFragment<T> implements SwipeRefreshLayout.OnRefreshListener {
    protected RecyclerView mRecycler;
    protected SwipeRefreshLayout mRefresh;
    protected boolean mIsRefreshing = false;
    protected List<K> mList = new ArrayList<>();

    /**
     * 将SwipeRefreshLayout的状态改为正在刷新
     */
    @Override
    protected void initRefreshLayout() {
        if (mRefresh != null) {
            mRefresh.setColorSchemeResources(R.color.colorPrimary);
            mRecycler.post(() -> {
                mRefresh.setRefreshing(true);
                lazyLoadData();
            });
            mRefresh.setOnRefreshListener(this);
        }

    }

    /**
     * 刷新时先清空数据再加载新数据
     */
    @Override
    public void onRefresh() {
        clearData();
        lazyLoadData();
    }


    @Override
    protected void clearData() {
        mIsRefreshing = true;
    }

    /**
     * 1.成功创建View
     * @param state
     */
    @Override
    public void finishCreateView(Bundle state) {
        mRefresh = mActivity.findViewById(R.id.refresh);
        mRecycler = mActivity.findViewById(R.id.recycler);
        isPrepared = true;
        // 加载数据
        lazyLoad();
    }

    /**
     * 2.加载数据
     */
    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) return;
        initRefreshLayout();
        initRecyclerView();
        if (mRefresh == null) lazyLoadData();
        isPrepared = false;
    }

    @Override
    public void complete() {
        super.complete();
        AppUtils.runOnUIDelayed(() -> {
            if (mRefresh != null)
                mRefresh.setRefreshing(false);
        }, 650);
        if (mIsRefreshing) {
            if (mList != null) mList.clear();
            clear();
            ToastUtils.showSingleLongToast("刷新成功");
        }
        mIsRefreshing = false;
    }

    protected void clear() {

    }


    @Override
    public void initView() {

    }
}
