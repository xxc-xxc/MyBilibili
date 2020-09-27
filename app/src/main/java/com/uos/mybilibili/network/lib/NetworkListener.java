package com.uos.mybilibili.network.lib;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.uos.mybilibili.network.lib.netcallback.NetworkCallbackImpl;
import com.uos.mybilibili.network.lib.receiver.NetworkStateReceiverWithAnnotation;
import com.uos.mybilibili.network.lib.template.SingletonTemplate;
import com.uos.mybilibili.utils.Constants;

/**
 * 网络监听
 * Created by xxc
 */
public class NetworkListener {
    private Context context;
    private NetworkCallbackImpl networkCallback;
    private NetworkStateReceiverWithAnnotation receiver;

    /**
     * 私有化构造方法
     */
    private NetworkListener() {
    }

    private static final SingletonTemplate<NetworkListener> INSTANCE = new SingletonTemplate<NetworkListener>() {
        @Override
        protected NetworkListener create() {
            return new NetworkListener();
        }
    };

    public static NetworkListener getInstance() {
        return INSTANCE.getInstance();
    }

    public Context getContext() {
        return context;
    }

    /**
     * 初始化
     *
     * @param context context
     */
    public void init(Context context) {
        this.context = context;
        if (aboveLollipop()) {
            networkCallback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager connMgr = (ConnectivityManager) NetworkListener.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr != null) {
                connMgr.registerNetworkCallback(request, networkCallback);
            }
        } else {
            //5.0以下继续使用广播
            receiver = new NetworkStateReceiverWithAnnotation();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        }
    }

    /**
     * 注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void registerObserver(Object observer) {
        if (aboveLollipop()) {
            networkCallback.registerObserver(observer);
        } else {
            receiver.registerObserver(observer);
        }
    }

    /**
     * 解除注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void unRegisterObserver(Object observer) {
        if (aboveLollipop()) {
            networkCallback.unRegisterObserver(observer);
        } else {
            receiver.unRegisterObserver(observer);
        }
    }

    /**
     * 版本是否是5.0及以上
     *
     * @return true/false
     */
    private boolean aboveLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
