package com.uos.mybilibili.mvp.model;

import com.uos.mybilibili.bean.live.LivePartition;
import com.uos.mybilibili.bean.live.LiveRecommend;
import com.uos.mybilibili.mvp.contract.LiveContract;
import com.uos.mybilibili.network.api.LiveService;
import com.uos.mybilibili.network.response.HttpResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Create By xxc
 * Date: 2020/10/12 17:54
 * Desc: 请求直播数据
 */
public class LiveModel implements LiveContract.Model {

    @Inject
    LiveService mLiveService;

    @Inject
    public LiveModel() {
    }

    @Override
    public Flowable<HttpResponse<LiveRecommend>> getLiveRecommend() {
        return mLiveService.getLiveRecommend();
    }

    @Override
    public Flowable<HttpResponse<LivePartition>> getLivePartition() {
        return mLiveService.getLivePartition();
    }
}
