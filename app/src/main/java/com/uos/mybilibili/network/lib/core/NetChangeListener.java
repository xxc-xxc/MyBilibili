package com.uos.mybilibili.network.lib.core;

/**
 * 网络监听接口
 * Created by ywd on 2019/6/10.
 */

public interface NetChangeListener {
    /**
     * 已连接
     * @param netType NetType
     */
    void onConnect(NetType netType);

    /**
     * 连接断开
     */
    void onDisConnect();
}
