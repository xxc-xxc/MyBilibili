package com.uos.mybilibili.mvp.contract;

import com.uos.mybilibili.base.BaseContract;
import com.uos.mybilibili.bean.live.LivePartition;
import com.uos.mybilibili.bean.live.LiveRecommend;
import com.uos.mybilibili.network.response.HttpResponse;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Create By xxc
 * Date: 2020/10/12 16:14
 * Desc: 直播契约接口
 */
public interface LiveContract {

    interface Model {
        // 首页推荐直播
        Flowable<HttpResponse<LiveRecommend>> getLiveRecommend();
        // 直播分区
        Flowable<HttpResponse<LivePartition>> getLivePartition();
    }

    interface View extends BaseContract.BaseView {
        void showLiveRecommend(LiveRecommend liveRecommend);
        void showLivePartition(LivePartition livePartition);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getLiveData();
    }

}
