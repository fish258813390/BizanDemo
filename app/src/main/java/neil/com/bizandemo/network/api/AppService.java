package neil.com.bizandemo.network.api;

import io.reactivex.Flowable;
import neil.com.bizandemo.bean.app.Splash;
import neil.com.bizandemo.network.constants.AppServiceConstant;
import retrofit2.http.GET;

/**
 * @author neil
 * @date 2018/3/13
 */

public interface AppService {


    @GET(AppServiceConstant.APP_SPLASH)
    Flowable<Splash> getSplash();

}
