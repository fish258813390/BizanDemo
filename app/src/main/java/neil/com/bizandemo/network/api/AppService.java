package neil.com.bizandemo.network.api;

import java.util.List;

import io.reactivex.Flowable;
import neil.com.bizandemo.bean.app.Splash;
import neil.com.bizandemo.bean.app.video.VideoDetail;
import neil.com.bizandemo.bean.bangumi.BangumiDetail;
import neil.com.bizandemo.bean.bangumi.BangumiDetailComment;
import neil.com.bizandemo.bean.bangumi.BangumiDetailRecommend;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.network.constants.AppServiceConstant;
import neil.com.bizandemo.network.constants.BangumiServiceConstant;
import neil.com.bizandemo.network.response.HttpResponse;
import retrofit2.http.GET;

/**
 * 基础服务类
 * APP_BASE_URL = "http://app.bilibili.com/";
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
     * 首页 fragment分区列表下的分类item
     */
    @GET(AppServiceConstant.HOME_REGION)
    Flowable<HttpResponse<List<Region>>> getRegion();

    /**
     * 视频详情
     */
    @GET(AppServiceConstant.VIDEO_DETAIL)
    Flowable<VideoDetail> getVideoDetail();


}
