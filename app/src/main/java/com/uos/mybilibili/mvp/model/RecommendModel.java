package com.uos.mybilibili.mvp.model;

import com.uos.mybilibili.bean.recommend.Recommend;
import com.uos.mybilibili.mvp.contract.RecommendContract;
import com.uos.mybilibili.network.api.AppService;
import com.uos.mybilibili.network.response.HttpResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Create By xxc
 * Date: 2020/10/10 10:11
 * Desc: 请求推荐位数据
 */
public class RecommendModel implements RecommendContract.Model {

    // 依赖于APPService
    @Inject
    AppService mAppService;

    // 对Presenter提供依赖
    @Inject
    public RecommendModel() {
    }

    @Override
    public Flowable<HttpResponse<List<Recommend>>> getRecommend() {
        return mAppService.getRecommend();
    }
}
