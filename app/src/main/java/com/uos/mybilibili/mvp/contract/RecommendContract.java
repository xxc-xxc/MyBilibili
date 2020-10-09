package com.uos.mybilibili.mvp.contract;

import com.uos.mybilibili.base.BaseContract;
import com.uos.mybilibili.bean.recommend.Recommend;
import com.uos.mybilibili.network.response.HttpResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

/**
 * 推荐契约类，定义MVP之间的关系
 */
public interface RecommendContract {

    interface Model {
        // 推荐资源
        Flowable<HttpResponse<List<Recommend>>> getRecommend();
    }

    interface View extends BaseContract.BaseView {
        void showRecommend(List<Recommend> recommendList);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getRecommend();
    }

}
