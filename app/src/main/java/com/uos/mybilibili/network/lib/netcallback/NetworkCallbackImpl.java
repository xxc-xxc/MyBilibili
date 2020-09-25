package com.uos.mybilibili.network.lib.netcallback;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.uos.mybilibili.network.lib.core.MethodManager;
import com.uos.mybilibili.network.lib.core.NetType;
import com.uos.mybilibili.utils.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Create By xxc
 * Date: 2020/9/25 16:13
 * Desc:
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private static final String TAG = NetworkCallbackImpl.class.getSimpleName();
    private NetType netType;
    private Map<Object, List<MethodManager>> networkMap;

    public NetworkCallbackImpl() {
        //初始化
        netType = NetType.NONE;
        networkMap = new HashMap<>();
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        LogUtils.d(TAG, "onAvailable ==> 网络已连接");
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        LogUtils.d(TAG, "onLost ==> 网络已断开");
    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        // 有网络
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                LogUtils.d(TAG, "onCapabilitiesChanged ==> 当前网络为WiFi");
                post(NetType.WIFI);
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                LogUtils.d(TAG, "onCapabilitiesChanged ==> 当前网络为蜂窝移动数据");
                post(NetType.CMWAP);
            } else {
                LogUtils.d(TAG, "onCapabilitiesChanged ==> 当前网络为其他");
                post(NetType.AUTO);
            }
        } else {

        }
    }

    private void post(NetType netType) {
        Set<Object> set = networkMap.keySet();
        for (Object observer : set) {
            List<MethodManager> methodList = networkMap.get(observer);
            if (methodList != null) {
                for (MethodManager methodManager : methodList) {
                    //两者参数比较
                    if (methodManager.getType().isAssignableFrom(netType.getClass())) {
                        switch (methodManager.getNetType()) {
                            case AUTO:
                                invoke(methodManager, observer, netType);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE) {
                                    invoke(methodManager, observer, netType);
                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType == NetType.NONE) {
                                    invoke(methodManager, observer, netType);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager methodManager, Object observer, NetType netType) {
        try {
            methodManager.getMethod().invoke(observer, netType);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void registerObserver(Object observer) {
        List<MethodManager> methodManagers = networkMap.get(observer);
        if (methodManagers == null) {
            methodManagers = findAnnotationMethods(observer);
            networkMap.put(observer, methodManagers);
        }
    }

    private List<MethodManager> findAnnotationMethods(Object observer) {
        List<MethodManager> methodList = new ArrayList<>();
        Class<?> clazz = observer.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //获取方法注解
            com.uos.mybilibili.network.lib.core.Network network =
                    method.getAnnotation(com.uos.mybilibili.network.lib.core.Network.class);
            if (network == null) {
                continue;
            }
            //方法参数校验
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException(method.getName() + "有且只有一个参数与");
            }
            String name = parameterTypes[0].getName();
            if (name.equals(NetType.class.getName())) {
                MethodManager methodManager = new MethodManager(parameterTypes[0], network.netType(), method);
                methodList.add(methodManager);
            }
        }
        return methodList;
    }

    /**
     * 解除注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void unRegisterObserver(Object observer) {
        if (!networkMap.isEmpty()) {
            networkMap.remove(observer);
        }
        Log.d(TAG, "unRegisterObserver: " + observer.getClass().getName() + "注销成功");
    }

    /**
     * 应用退出时调用
     */
    public void unRegisterAll() {
        if (!networkMap.isEmpty()) {
            networkMap.clear();
        }
    }
}
