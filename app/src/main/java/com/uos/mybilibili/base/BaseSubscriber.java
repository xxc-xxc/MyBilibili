package com.uos.mybilibili.base;

import android.text.TextUtils;

import com.uos.mybilibili.network.exception.ApiException;
import com.uos.mybilibili.utils.AppUtils;
import com.uos.mybilibili.utils.LogUtils;
import com.uos.mybilibili.utils.NetworkUtils;

import java.net.SocketTimeoutException;

import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * @author xxc
 * @date 2020年9月28日15:24:12
 * 描述:统一处理订阅者
 */

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {
    private BaseContract.BaseView mView;
    private String mMsg;

    public BaseSubscriber(BaseContract.BaseView view) {
        this.mView = view;
    }


    public abstract void onSuccess(T t);

    public void onFailure(int code, String message) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected(AppUtils.getAppContext())) {
            // Logger.d("没有网络");
        } else {

        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T response) {
        if (mView == null) return;
        mView.complete();
        onSuccess(response);
    }


    @Override
    public void onError(Throwable e) {
        if (mView == null) return;
        mView.complete();//完成操作
        if (mMsg != null && !TextUtils.isEmpty(mMsg)) {
            mView.showError(mMsg);
        } else if (e instanceof ApiException) {
            mView.showError(e.toString());
        } else if (e instanceof SocketTimeoutException) {
            mView.showError("服务器响应超时ヽ(≧Д≦)ノ");
        } else if (e instanceof HttpException) {
            mView.showError("数据加载失败ヽ(≧Д≦)ノ");
        } else {
            mView.showError("未知错误ヽ(≧Д≦)ノ");
            LogUtils.e("MYERROR:"+e.toString());
        }
    }
}
