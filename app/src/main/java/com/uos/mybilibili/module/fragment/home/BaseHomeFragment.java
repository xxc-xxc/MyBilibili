package com.uos.mybilibili.module.fragment.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uos.mybilibili.R;
import com.uos.mybilibili.base.BaseFragment;

/**
 * Create By xxc
 * Date: 2020/10/9 11:34
 * Desc: 首页的BaseFragment，设置支持menu菜单以及一些菜单项
 */
public abstract class BaseHomeFragment extends BaseFragment {

    public Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void initView() {
        super.initView();
        initToolBar();
    }

    /**
     * 初始化工具栏
     */
    private void initToolBar() {
        mToolbar = mActivity.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            mToolbar.inflateMenu(R.menu.menu_main);
        }
    }
}
