package com.uos.mybilibili.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.trello.rxlifecycle4.components.RxFragment;
import com.uos.mybilibili.MyApplication;
import com.uos.mybilibili.di.component.DaggerFragmentComponent;
import com.uos.mybilibili.di.component.FragmentComponent;
import com.uos.mybilibili.di.module.FragmentModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create By xxc
 * Date: 2020/9/30 16:45
 * Desc:
 */
public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends Fragment
        implements BaseContract.BaseView {

    protected View mView;
    private Unbinder mUnbinder;
    protected LayoutInflater inflater;
    protected Activity mActivity;
    protected Context mContext;
    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;
    //标志位 fragment是否可见
    protected boolean isVisible;
    public ConstraintLayout mError;

    @Inject
    protected T mPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup viewGroup = (ViewGroup) mView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mView);
            }
        } else {
            mView = LayoutInflater.from(getActivity()).inflate(getLayoutId(),
                    container, false);
            mActivity = getSupportActivity();
            mContext = mActivity;
            this.inflater = inflater;
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUnbinder = ButterKnife.bind(this, mView);
        initInject();
        initPresenter();
//        mError = ButterKnife.findById(mView, R.id.cl_error);
        initView();
        finishCreateView(savedInstanceState);
        initData();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        loadData();
    }

    /**
     * View视图创建完毕
     * @param state
     */
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) return;
        lazyLoadData();
        isPrepared = false;
    }

    protected void lazyLoadData() {

    }

    /**
     * 加载数据
     */
    protected void loadData() {
    }

    /**
     * 初始化控件等
     */
    public void initView() {

    }

    private void initPresenter() {
        if (mPresenter != null) mPresenter.attachView(this);
    }

    /**
     * Dagger2依赖注入
     */
    protected void initInject() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        mActivity = null;
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (mPresenter != null) mPresenter.detachView();
    }

    /**
     * 获取Activity
     *
     * @return FragmentActivity
     */
    public FragmentActivity getSupportActivity() {
        return (FragmentActivity) super.getActivity();
    }

    /**
     * 获取ApplicationContext 信息
     *
     * @return Context
     */
    public Context getApplicationContext() {
        return this.mContext == null ? (getActivity() == null ? null : getActivity()
                .getApplicationContext()) : this.mContext.getApplicationContext();
    }

    /**
     * 布局
     *
     * @return int
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * 创建FragmentComponent实例
     * @return
     */
    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(MyApplication.getInstance().getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    /**
     * 初始化RV
     */
    protected void initRecyclerView() {

    }

    /**
     * 初始化刷新
     */
    protected void initRefreshLayout() {

    }

    /**
     * 清除数据
     */
    protected void clearData() {

    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {

    }

    /**
     * Fragment数据的懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 显示错误信息
     *
     * @param msg msg
     */
    @Override
    public void showError(String msg) {
        if (mError != null) {
            visible(mError);
        }
    }

    /**
     * 显示View
     *
     * @param views view数组
     */
    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 显示View
     *
     * @param id
     */
    protected void visible(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = $(resId);
                if (view != null)
                    visible(view);
            }
        }
    }

    /**
     * 完成加载
     */
    @Override
    public void complete() {
        if (mError != null) {
            gone(mError);
        }
    }

    protected void finishTask() {

    }

    /**
     * 隐藏View
     *
     * @param views view数组
     */
    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 隐藏View
     *
     * @param id
     */
    protected void gone(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = $(resId);
                if (view != null)
                    gone(view);
            }
        }
    }

    public View $(@IdRes int id) {
        View view;
        if (mView != null) {
            view = mView.findViewById(id);
            return view;
        }
        return null;
    }

}
