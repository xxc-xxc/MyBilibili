package com.uos.mybilibili.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.inputmethodservice.InputMethodService;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.uos.mybilibili.MyApplication;
import com.uos.mybilibili.network.broadcast.NetBroadcastReceiver;
import com.uos.mybilibili.network.broadcast.NetworkStateReceiver;
import com.uos.mybilibili.network.lib.NetworkListener;
import com.uos.mybilibili.utils.ActivitiesManager;

/**
 * Create By xxc
 * Date: 2020/9/25 11:09
 * Desc:
 */
public abstract class BaseActivity extends AppCompatActivity
        implements NetBroadcastReceiver.NetChangeListener, NetworkStateReceiver.NetworkChangeListener {

    protected final String TAG = this.getClass().getSimpleName();
    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件
    private static Toast sToast;
    private Context mContext;
    //是否显示标题栏
    private boolean isShowTitle;
    // 是否显示ActionBar
    private boolean isShowActionBar;
    //是否允许旋转屏幕
    private boolean isAllowScreenRotate;
//    private NetBroadcastReceiver mNetBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        netEvent = this;
        // 统一管理Activity
        ActivitiesManager.addActivity(this);
        if (!isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (getSupportActionBar() != null && !isShowActionBar) {
            getSupportActionBar().hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        // 设置布局
        setContentView(initLayout());

        // 设置横竖屏
        if (isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // 初始化控件
        initView();
        // 初始化数据
        initData();
        // 动态注册网络变化广播
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        mNetBroadcastReceiver = new NetBroadcastReceiver();
//        registerReceiver(mNetBroadcastReceiver, intentFilter);
//        NetworkListener.getInstance().registerObserver(this);
        ((MyApplication)getApplication()).setNetworkChangeListener(this);
    }

    /**
     * 初始化布局
     * @return
     */
    protected abstract int initLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    public void setShowActionBar(boolean showActionBar) {
        isShowActionBar = showActionBar;
    }

    public void setAllowScreenRotate(boolean allowScreenRotate) {
        isAllowScreenRotate = allowScreenRotate;
    }

    /**
     * 显示吐司
     * @param msg
     */
    @SuppressLint("ShowToast")
    public void showToast(String msg) {
        try {
            if (sToast == null) {
                sToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
            } else {
                sToast.setText(msg);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sToast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            // 解决子线程调用Toast的异常
            Looper.prepare();
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftKeyboard() {
        if (getCurrentFocus() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                InputMethodService ims = new InputMethodService();
                ims.requestShowSelf(0);
            } else {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
                }
            }
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                InputMethodService ims = new InputMethodService();
                ims.requestHideSelf(0);
            } else {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesManager.removeActivity(this);
//        unregisterReceiver(mNetBroadcastReceiver);
//        NetworkListener.getInstance().unRegisterObserver(this);
        ((MyApplication)getApplication()).setNetworkChangeListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivitiesManager.removeActivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检查是否有权限
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 请求权限
     * @param code
     * @param permissions
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 处理请求权限结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequestPermissionResult(requestCode, grantResults);
    }

    /**
     * 处理请求权限结果
     * @param requestCode
     * @param grantResults
     */
    public void doRequestPermissionResult(int requestCode, int[] grantResults) {

    }

    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
    }

    @Override
    public void onNetworkChange(String msg) {
//        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 同一控件一秒内只响应一次点击事件
     */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        private static final int CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onSingleClick(v);
            }
        }
    }

    /**
     * 短时间内可以响应多次点击事件
     */
    public abstract class OnMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }
}
