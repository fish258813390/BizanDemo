package neil.com.bizandemo.network.api;

import java.util.List;

import io.reactivex.Flowable;
import neil.com.bizandemo.bean.app.Splash;
import neil.com.bizandemo.bean.bangumi.BangumiDetail;
import neil.com.bizandemo.bean.bangumi.BangumiDetailComment;
import neil.com.bizandemo.bean.bangumi.BangumiDetailRecommend;
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
     * 首页 fragment分区列表下的分类item
     */
    @GET(AppServiceConstant.HOME_REGION)
    Flowable<HttpResponse<List<Region>>> getRegion();


    /**
     * 番剧详情
     */
    @GET(AppServiceConstant.BANGUMI_DETAIL)
    Flowable<HttpResponse<BangumiDetail>> getBangumiDetail();

    /**
     * 番剧详情 更多推荐
     */
    @GET(AppServiceConstant.BANGUMI_DETAIL_RECOMMEND)
    Flowable<HttpResponse<BangumiDetailRecommend>> getBangumiDetailRecommend();

    /**
     * 番剧详情 评论
     */
    @GET(AppServiceConstant.BANGUMI_DETAIL_COMMENT)
    Flowable<HttpResponse<BangumiDetailComment>> getBangumiDetailComment();

}
