package com.uos.mybilibili.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.uos.mybilibili.network.lib.NetworkListener;
import com.uos.mybilibili.network.lib.core.NetType;

/**
 * 网络工具类
 * Created by ywd on 2019/6/10.
 */

public class NetworkUtil2 {

    /**
     * 判断网络是否可用
     *
     * @return true/false
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) NetworkListener.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return false;
        }
        NetworkInfo[] infos = connMgr.getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info : infos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络类型
     *
     * @return NetType
     */
    public static NetType getNetType() {
        ConnectivityManager connMgr = (ConnectivityManager) NetworkListener.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return NetType.NONE;
        }
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if ("cmnet".equals(networkInfo.getExtraInfo().toLowerCase())) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
}
