package com.uos.mybilibili.network.api;

import com.uos.mybilibili.bean.app.Splash;
import com.uos.mybilibili.bean.recommend.Recommend;
import com.uos.mybilibili.network.response.HttpResponse;

import java.util.List;

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

    /**
     * 首页推荐
     *
     * @return
     */
    @GET("/x/feed/index?access_key=5c2ea06a566f3dd8850f5750b8d0a650&appkey=1d8b6e7d45233436&build=505000&idx=0&login_event=2&mobi_app=android&network=wifi&open_event=cold&platform=android&pull=true&style=2&ts=1495519813&sign=510278867e908ab3de31a7bd3701a55c")
    Flowable<HttpResponse<List<Recommend>>> getRecommend();

}
