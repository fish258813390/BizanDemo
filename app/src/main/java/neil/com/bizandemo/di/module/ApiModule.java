package neil.com.bizandemo.di.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import neil.com.bizandemo.di.qualifier.AppUrl;
import neil.com.bizandemo.di.scope.ApiUrl;
import neil.com.bizandemo.di.scope.BangumiUrl;
import neil.com.bizandemo.network.api.ApiService;
import neil.com.bizandemo.network.api.AppService;
import neil.com.bizandemo.network.api.BangumiService;
import neil.com.bizandemo.network.helper.OkHttpHelper;
import neil.com.bizandemo.network.helper.RetrofitHelper;
import neil.com.bizandemo.network.support.ApiConstants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述:Api网络模型
 * Created by neil on 2018/3/13 0013.
 */
@Module
public class ApiModule {

    public Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return OkHttpHelper.getInstance().getOkHttpClient();
    }

    @Singleton
    @Provides
    public Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    public RetrofitHelper provideRetrofitHelper(AppService appService, BangumiService bangumiService, ApiService apiService) {
        return new RetrofitHelper(appService, bangumiService, apiService);
    }

    @Singleton
    @Provides
    @AppUrl
    public Retrofit proviceAppRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ApiConstants.APP_BASE_URL);
    }

    @Singleton
    @Provides
    public AppService provideAppService(@AppUrl Retrofit retrofit) {
        return retrofit.create(AppService.class);
    }

    @Singleton
    @Provides
    @BangumiUrl
    public Retrofit provideBangumiRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ApiConstants.BANGUMI_BASE_URL);
    }

    @Singleton
    @Provides
    public BangumiService provideBangumiService(@BangumiUrl Retrofit retrofit) {
        return retrofit.create(BangumiService.class);
    }

    //
    @Singleton
    @Provides
    @ApiUrl
    public Retrofit provideApiRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ApiConstants.API_BASE_URL);
    }

    @Singleton
    @Provides
    public ApiService provideApiService(@ApiUrl Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}
