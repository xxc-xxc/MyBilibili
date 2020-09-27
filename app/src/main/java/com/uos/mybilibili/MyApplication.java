package com.uos.mybilibili;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.facebook.stetho.Stetho;
import com.uos.mybilibili.network.broadcast.NetworkStateReceiver;
import com.uos.mybilibili.network.lib.NetworkListener;
import com.uos.mybilibili.utils.AppUtils;

/**
 * Create By xxc
 * Date: 2020/9/25 17:17
 * Desc:
 */
public class MyApplication extends Application {

    private NetworkStateReceiver mNetworkStateReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
//        NetworkListener.getInstance().init(this);
        registerReceiver();
        initStetho();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver();
    }

    /**
     * 初始化调试
     */
    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkStateReceiver = new NetworkStateReceiver();
        registerReceiver(mNetworkStateReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        if (mNetworkStateReceiver != null) {
            unregisterReceiver(mNetworkStateReceiver);
        }
    }

    public void setNetworkChangeListener(NetworkStateReceiver.NetworkChangeListener listener) {
        mNetworkStateReceiver.setNetworkChangeListener(listener);
    }
}
