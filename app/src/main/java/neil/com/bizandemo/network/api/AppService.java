package neil.com.bizandemo.network.api;

import java.util.List;

import io.reactivex.Flowable;
import neil.com.bizandemo.bean.app.Splash;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.network.constants.AppServiceConstant;
import neil.com.bizandemo.network.response.HttpResponse;
import retrofit2.http.GET;

/**
 * @author neil
 * @date 2018/3/13
 */

public interface AppService {


    /**
     * splash界面
     */
    @GET(AppServiceConstant.APP_SPLASH)
    Flowable<Splash> getSplash();

    /**
     * 首页分区
     */
    @GET(AppServiceConstant.HOME_REGION)
    Flowable<HttpResponse<List<Region>>> getRegion();

}
