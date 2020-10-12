package com.uos.mybilibili.mvp.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uos.mybilibili.base.BaseListSubscriber;
import com.uos.mybilibili.base.BaseSubscriber;
import com.uos.mybilibili.base.RxPresenter;
import com.uos.mybilibili.bean.recommend.Recommend;
import com.uos.mybilibili.mvp.contract.RecommendContract;
import com.uos.mybilibili.mvp.model.RecommendModel;
import com.uos.mybilibili.network.response.HttpResponse;
import com.uos.mybilibili.utils.JsonUtils;
import com.uos.mybilibili.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Create By xxc
 * Date: 2020/10/10 10:19
 * Desc:
 */
public class RecommendPresenter extends RxPresenter<RecommendContract.View>
        implements RecommendContract.Presenter<RecommendContract.View> {

    private Context mContext;

    @Inject
    RecommendModel mModel;

    @Inject
    public RecommendPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void getRecommend() {
//        BaseListSubscriber<Recommend> subscriber = mModel.getRecommend()
//                .compose(RxUtils.rxSchedulerHelper())
//                .subscribeWith(new BaseListSubscriber<Recommend>(mView) {
//                    @Override
//                    public void onSuccess(List<Recommend> recommends) {
//                        // 成功回调
//                        mView.showRecommend(recommends);
//                    }
//
//                    @Override
//                    public void onFailure(int code, String message) {
//                        // 失败回调
//                        super.onFailure(code, message);
//                    }
//                });
//        addSubscribe(subscriber);

        BaseSubscriber<List<Recommend>> subscriber = Flowable.just(JsonUtils.readJson("recommend.json"))
                .map(string -> {
                    Gson gson = new Gson();
                    JsonObject object = new JsonParser().parse(string).getAsJsonObject();
                    JsonArray array = object.getAsJsonArray("data");
                    List<Recommend> recommends = new ArrayList<>();
                    for (JsonElement jsonElement : array) {
                        recommends.add(gson.fromJson(jsonElement, Recommend.class));
                    }
                    return recommends;
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<Recommend>>(mView) {
                    @Override
                    public void onSuccess(List<Recommend> recommends) {
                        mView.showRecommend(recommends);
                    }
                });
        addSubscribe(subscriber);
    }
}
