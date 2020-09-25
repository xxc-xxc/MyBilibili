package com.uos.mybilibili.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.uos.mybilibili.R;
import com.uos.mybilibili.base.BaseActivity;
import com.uos.mybilibili.network.lib.core.NetType;
import com.uos.mybilibili.network.lib.core.Network;
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
    @BindView(R.id.tv_network)
    TextView tvNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        StatusBarUtil.setTransparent(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

//    @Override
//    public void onNetChange(boolean netWorkState) {
//        if (netWorkState) {
//            tvNetwork.setText("网络已连接");
//        } else {
//            tvNetwork.setText("网络已断开");
//        }
//    }

    @Network(netType = NetType.WIFI)
    public void onNetChangedWifi(NetType netType) {
        tvNetwork.setText("WIFI");
    }

}