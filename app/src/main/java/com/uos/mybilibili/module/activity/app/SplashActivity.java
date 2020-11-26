package com.uos.mybilibili.module.activity.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.jakewharton.rxbinding4.view.RxView;
import com.uos.mybilibili.MyApplication;
import com.uos.mybilibili.R;
import com.uos.mybilibili.base.BaseActivity;
import com.uos.mybilibili.bean.app.Splash;
import com.uos.mybilibili.di.component.DaggerActivityComponent;
import com.uos.mybilibili.di.module.ActivityModule;
import com.uos.mybilibili.module.activity.home.MainActivity;
import com.uos.mybilibili.mvp.contract.SplashContract;
import com.uos.mybilibili.mvp.presenter.SplashPresenter;
import com.uos.mybilibili.network.lib.core.NetType;
import com.uos.mybilibili.network.lib.core.Network;
import com.uos.mybilibili.utils.StatusBarUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author: XXC
 * Date: 2020/9/24 18:04
 * Desc: 闪屏页(广告页)
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG             #
 * #
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {

    @BindView(R.id.iv_splash_poster)
    ImageView ivSplashPoster;
    @BindView(R.id.tv_countdown_time)
    TextView tvCountdownTime;
    @BindView(R.id.ll_countdown)
    LinearLayout llCountdown;
    @Inject
    SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        StatusBarUtil.setTransparent(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initInject();
        loadData();
    }

    private void loadData() {
        mPresenter.getSplash();
        mPresenter.setCountdown();
    }

    /**
     * 依赖注入
     */
    private void initInject() {
        DaggerActivityComponent.builder()
                .appComponent(MyApplication.getInstance().getAppComponent())
                .activityModule(new ActivityModule(this))
//                .apiModule(new ApiModule())
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        RxView.clicks(llCountdown)
                .throttleFirst(3, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> redirect());
    }

    @Override
    protected void initData() {

    }

    /**
     * 跳转到首页or登录页
     */
    private void redirect() {

        boolean flag = false;

        if (true) {
            startActivity(new Intent(this, MainActivity.class));
        } else {

        }
        finish();
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

    }

    @Override
    public void showCountdown(int count) {
        tvCountdownTime.setText(count + "");
        if (count == 0) {
            redirect();
        }
    }

    @Override
    public void showSplash(Splash splash) {
        if (!splash.data.isEmpty()) {
            int index = new Random().nextInt(splash.data.size());
            Glide.with(this)
                    .load(splash.data.get(index).thumb)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(ivSplashPoster);
        } else {
            ivSplashPoster.setImageResource(R.drawable.ic_default_bg);
        }
    }

    @Override
    public void showError(String msg) {
        ivSplashPoster.setImageResource(R.drawable.ic_default_bg);
    }

    @Override
    public void complete() {

    }
}