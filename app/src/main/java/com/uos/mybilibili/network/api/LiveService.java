package com.uos.mybilibili.network.api;

import com.uos.mybilibili.bean.live.LivePartition;
import com.uos.mybilibili.bean.live.LiveRecommend;
import com.uos.mybilibili.network.response.HttpResponse;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;

/**
 * Create By xxc
 * Date: 2020/10/12 16:02
 * Desc: 直播API接口
 */
public interface LiveService {

    /**
     * 首页推荐直播
     *
     * @return
     */
    @GET("/AppNewIndex/recommend?_device=android&access_key=5b0032c681c2233870c8edcee410b6c6&appkey=1d8b6e7d45233436&build=505000&mobi_app=android&platform=android&scale=xxhdpi&ts=1495639021&sign=9d024a5b09edddd51636d17d860622d2")
    Flowable<HttpResponse<LiveRecommend>> getLiveRecommend();

    /**
     * 直播分区
     *
     * @return
     */
    @GET("/AppNewIndex/common?_device=android&access_key=5b0032c681c2233870c8edcee410b6c6&appkey=1d8b6e7d45233436&build=505000&mobi_app=android&platform=android&scale=xxhdpi&ts=1495639884&sign=74b510ce56ef302742aafad2e20f9899")
    Flowable<HttpResponse<LivePartition>> getLivePartition();

}
