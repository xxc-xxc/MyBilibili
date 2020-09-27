package com.uos.mybilibili.network.api;

import com.uos.mybilibili.bean.Splash;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;

/**
 * Create By xxc
 * Date: 2020/9/27 15:18
 * Desc: APP请求API
 */
public interface AppService {

    /**
     * splash界面
     *
     * @return
     */
    @GET("/x/v2/splash?mobi_app=android&build=505000&channel=360&width=1080&height=1920&ver=4344558841496142006")
    Flowable<Splash> getSplash();

}
