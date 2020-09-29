package com.uos.mybilibili.di.module;

import com.uos.mybilibili.di.qualifier.AppUrl;
import com.uos.mybilibili.network.api.AppService;
import com.uos.mybilibili.network.helper.OkHttpHelper;
import com.uos.mybilibili.network.helper.RetrofitHelper;
import com.uos.mybilibili.utils.ApiConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create By xxc
 * Date: 2020/9/29 16:07
 * Desc:
 */
@Module
public class ApiModule {

    /**
     * 依赖Retrofit.Builder，OkHttpClient，需要提供
     * @param builder
     * @param client
     * @param url
     * @return
     */
    public Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 注入Retrofit.Builder
     * @return
     */
    @Singleton
    @Provides
    public Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    /**
     * 注入OkHttpClient
     * @return
     */
    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return OkHttpHelper.getInstance().getOkHttpClient();
    }

    @Singleton
    @Provides
    public RetrofitHelper provideRetrofitHelper() {
        return new RetrofitHelper();
    }

    /**
     * 如果Retrofit对象有多个，如何区分？
     * 需要自定义注解，标明需要的是哪一个Retrofit
     * @param retrofit
     * @return
     */
    @Singleton
    @Provides
    public AppService provideAppService(@AppUrl Retrofit retrofit) {
        return retrofit.create(AppService.class);
    }


    @Singleton
    @Provides
    @AppUrl
    public Retrofit provideAppRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ApiConstants.APP_BASE_URL);
    }

}
