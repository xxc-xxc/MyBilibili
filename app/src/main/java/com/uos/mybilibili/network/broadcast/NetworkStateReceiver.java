package com.uos.mybilibili.network.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

/**
 * Create By xxc
 * Date: 2020/9/25 17:41
 * Desc:
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = "网络已断开";
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // API21之后getNetworkInfo(int networkType)方法被弃用
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 获取WiFi连接信息
            NetworkInfo wifiNetworkInfo =
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            // 获取移动数据连接信息
            NetworkInfo mobileNetworkInfo =
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo != null && mobileNetworkInfo != null) {
                if (wifiNetworkInfo.isConnected() && mobileNetworkInfo.isConnected()) {
                    Toast.makeText(context, "WiFi已连接，移动数据已连接",
                            Toast.LENGTH_SHORT).show();
                    msg = "WiFi已连接，移动数据已连接";
                } else if (wifiNetworkInfo.isConnected() && !mobileNetworkInfo.isConnected()) {
                    Toast.makeText(context, "WiFi已连接，移动数据已断开",
                            Toast.LENGTH_SHORT).show();
                    msg = "WiFi已连接，移动数据已断开";
                } else if (!wifiNetworkInfo.isConnected() && mobileNetworkInfo.isConnected()) {
                    Toast.makeText(context, "WiFi已断开，移动数据已连接",
                            Toast.LENGTH_SHORT).show();
                    msg = "WiFi已断开，移动数据已连接";
                } else {
                    Toast.makeText(context, "WiFi已断开，移动数据已断开",
                            Toast.LENGTH_SHORT).show();
                    msg = "WiFi已断开，移动数据已断开";
                }
            }
        } else {
            StringBuilder sb = new StringBuilder();
            Network[] allNetworks = connectivityManager.getAllNetworks();
            for (Network allNetwork : allNetworks) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(allNetwork);
                if (networkInfo != null) {
                    sb.append(networkInfo.getTypeName()).append(" connect is ").append(networkInfo.isConnected());
                }
            }
            msg = sb.toString();
            if (msg.equals("")) {
                msg = "网络已断开";
            }
//            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        if (mNetworkChangeListener != null) {
            mNetworkChangeListener.onNetworkChange(msg);
        }
    }

    private NetworkChangeListener mNetworkChangeListener;

    public void setNetworkChangeListener(NetworkChangeListener networkChangeListener) {
        this.mNetworkChangeListener = networkChangeListener;
    }

    public interface NetworkChangeListener {
        public void onNetworkChange(String msg);
    }
}
