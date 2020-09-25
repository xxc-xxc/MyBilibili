package com.uos.mybilibili.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.uos.mybilibili.R;
import com.uos.mybilibili.base.BaseActivity;
import com.uos.mybilibili.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: XXC
 * Date: 2020/9/24 18:04
 * Desc: 闪屏页(广告页)
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_splash_poster)
    ImageView ivSplashPoster;
    @BindView(R.id.tv_countdown_time)
    TextView tvCountdownTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        StatusBarUtil.setTransparent(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}