package com.uos.mybilibili.base;

import android.text.TextUtils;

import com.uos.mybilibili.network.exception.ApiException;
import com.uos.mybilibili.network.response.HttpResponse;
import com.uos.mybilibili.utils.AppUtils;
import com.uos.mybilibili.utils.LogUtils;
import com.uos.mybilibili.utils.NetworkUtils;

import java.net.SocketTimeoutException;

import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/5/12 11:40
 * 描述:统一处理订阅者
 */

public abstract class BaseObjectSubscriber<T> extends ResourceSubscriber<HttpResponse<T>> {
    private BaseContract.BaseView mView;
    private String mMsg;

    public BaseObjectSubscriber(BaseContract.BaseView view) {
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
    public void onNext(HttpResponse<T> response) {
        if (mView == null) return;
        mView.complete();
        if (response.code == 0) {
            if (response.data != null)
                onSuccess(response.data);
            if (response.result != null)
                onSuccess(response.result);
        } else {
            //可以不处理任何东西
            onFailure(response.code, response.message);
        }
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
