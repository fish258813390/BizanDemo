package neil.com.bizandemo.network.helper;

import java.util.List;

import io.reactivex.Flowable;
import neil.com.bizandemo.bean.app.Splash;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.network.api.AppService;
import neil.com.bizandemo.network.response.HttpResponse;

/**
 * 描述:RetrofitHelper 帮助类
 *
 * @author neil
 * @date 2018/3/13
 */
public class RetrofitHelper {

    private final AppService mAppService;

    public RetrofitHelper(AppService appService) {
        this.mAppService = appService;
    }

    /*******************************AppApi****************************************/
    public Flowable<Splash> getSplash() {
        return mAppService.getSplash();
    }

    public Flowable<HttpResponse<List<Region>>> getRegion(){
        return mAppService.getRegion();
    }


}
