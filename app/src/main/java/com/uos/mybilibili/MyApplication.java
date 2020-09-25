package com.uos.mybilibili;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.uos.mybilibili.network.broadcast.NetworkStateReceiver;
import com.uos.mybilibili.network.lib.NetworkListener;

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
//        NetworkListener.getInstance().init(this);
        registerReceiver();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver();
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
