package com.uos.mybilibili.module.activity.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.uos.mybilibili.R;
import com.uos.mybilibili.base.BaseActivity;
import com.uos.mybilibili.module.fragment.home.HomeFragment;
import com.uos.mybilibili.utils.AppUtils;
import com.uos.mybilibili.utils.Event;
import com.uos.mybilibili.utils.RxBus;
import com.uos.mybilibili.utils.StatusBarUtil;
import com.uos.mybilibili.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Author: xxc email: 2331896806@qq.com
 * Date: 2020/9/30 18:16
 * Desc: 主界面
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    @BindView(R.id.fl_main_content)
    FrameLayout flMainContent;
    @BindView(R.id.nav_view)
    NavigationView navView;

    private long exitTime = 0L;
    private int mCurrentPos = -1;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置侧滑面板的沉浸式效果
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this,
                mainDrawerLayout, AppUtils.getColor(R.color.colorPrimary));
        setListener();
    }

    /**
     * 设置事件监听
     */
    private void setListener() {
        RxBus.INSTANCE.toFlowable(Event.StartNavigationEvent.class)
                .compose(bindToLifecycle())
                .subscribe(event -> {
                    if (event.start) {
                        toggleDrawer();
                    }
                });
    }

    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer() {
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mainDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        disableNavigationViewScrollbars(navView);
        // 设置Navigation的item选择事件
        navView.setNavigationItemSelectedListener(this);
    }

    /**
     * 去掉NavigationView的滚动条
     * @param navigationView
     */
    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView =
                    (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    @Override
    protected void initData() {
        initFragment();
        switchFragmentIndex(0);//初始化位置
    }

    private void switchFragmentIndex(int pos) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (mCurrentPos != -1)
            transaction.hide(mFragments.get(mCurrentPos));
        if (!mFragments.get(pos).isAdded()) {
            transaction.add(R.id.fl_main_content, mFragments.get(pos));
        }
        transaction.show(mFragments.get(pos)).commit();
        mCurrentPos = pos;

    }

    private void initFragment() {
        mFragments = Arrays.asList(HomeFragment.newInstance());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        AppUtils.runOnUIDelayed(() -> {
            int itemId = menuItem.getItemId();
            switch (itemId) {
                case R.id.item_home:
                    break;
            }
        }, 250);
        // 关闭DrawerLayout
        mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 返回键处理
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mainDrawerLayout.isDrawerOpen(mainDrawerLayout.getChildAt(1))) {
                mainDrawerLayout.closeDrawers();
            } else {
                // 退出APP
                exitApp();
            }
        }
        return true;
    }

    /**
     * 双击退出App
     */
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.showToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            // BiliSoleilApplication.getInstance().exitApp();
            Event.ExitEvent event = new Event.ExitEvent();
            event.exit = -1;
            RxBus.INSTANCE.post(event);
        }
    }
}