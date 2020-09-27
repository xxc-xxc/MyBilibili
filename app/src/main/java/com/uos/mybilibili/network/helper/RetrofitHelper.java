package com.uos.mybilibili.network.helper;

import com.uos.mybilibili.network.api.AppService;
import com.uos.mybilibili.utils.ApiConstants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create By xxc
 * Date: 2020/9/27 15:51
 * Desc:
 */
public class RetrofitHelper {

    private static volatile RetrofitHelper instance;
    private volatile AppService mAppService;

    private RetrofitHelper() {
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }

        return instance;
    }

    public AppService getAppService() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(OkHttpHelper.getInstance().getOkHttpClient())
                .baseUrl(ApiConstants.APP_BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        if (mAppService == null) {
            synchronized (RetrofitHelper.class) {
                if (mAppService == null) {
                    mAppService = retrofit.create(AppService.class);
                }
            }
        }
        return mAppService;
    }

}
