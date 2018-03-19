package neil.com.bizandemo.network.helper;

import java.util.List;

import io.reactivex.Flowable;
import neil.com.bizandemo.bean.app.Splash;
import neil.com.bizandemo.bean.bangumi.BangumiDetail;
import neil.com.bizandemo.bean.bangumi.BangumiDetailComment;
import neil.com.bizandemo.bean.bangumi.BangumiDetailRecommend;
import neil.com.bizandemo.bean.discover.ActivityCenter;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.network.api.ApiService;
import neil.com.bizandemo.network.api.AppService;
import neil.com.bizandemo.network.api.BangumiService;
import neil.com.bizandemo.network.response.HttpResponse;

/**
 * 描述:RetrofitHelper 帮助类
 *
 * @author neil
 * @date 2018/3/13
 */
public class RetrofitHelper {

    private final AppService mAppService;
    private final BangumiService mBangumiService;
    private final ApiService mApiService;

    public RetrofitHelper(AppService appService, BangumiService bangumiService, ApiService apiService) {
        this.mAppService = appService;
        this.mBangumiService = bangumiService;
        this.mApiService = apiService;
    }

    /*******************************AppApi****************************************/
    public Flowable<Splash> getSplash() {
        return mAppService.getSplash();
    }

    public Flowable<HttpResponse<List<Region>>> getRegion() {
        return mAppService.getRegion();
    }

    /******************************* BangumiApi ****************************************/
    public Flowable<HttpResponse<BangumiDetail>> getBangumiDetail() {
        return mBangumiService.getBangumiDetail();
    }

    public Flowable<HttpResponse<BangumiDetailRecommend>> getBangumiDetailRecommend() {
        return mBangumiService.getBangumiDetailRecommend();
    }


    /******************************* ApiApi ****************************************/
    public Flowable<BangumiDetailComment> getBangumiDetailComment() {
        return mApiService.getBangumiDetailComment();
    }

    public Flowable<ActivityCenter> getActivityCenter(int page, int pageSize) {
        return mApiService.getActivityCenter(page, pageSize);
    }

}
