package neil.com.bizandemo.network.api;

import io.reactivex.Flowable;
import neil.com.bizandemo.bean.bangumi.BangumiDetail;
import neil.com.bizandemo.bean.bangumi.BangumiDetailComment;
import neil.com.bizandemo.bean.bangumi.BangumiDetailRecommend;
import neil.com.bizandemo.network.constants.AppServiceConstant;
import neil.com.bizandemo.network.constants.BangumiServiceConstant;
import neil.com.bizandemo.network.response.HttpResponse;
import retrofit2.http.GET;

/**
 * 番剧服务
 * BANGUMI_BASE_URL = "https://bangumi.bilibili.com/";
 * Created by neil on 2018/3/16 0016.
 */
public interface BangumiService {

    /**
     * 番剧详情
     */
    @GET(BangumiServiceConstant.BANGUMI_DETAIL)
    Flowable<HttpResponse<BangumiDetail>> getBangumiDetail();

    /**
     * 番剧详情 更多推荐
     */
    @GET(BangumiServiceConstant.BANGUMI_DETAIL_RECOMMEND)
    Flowable<HttpResponse<BangumiDetailRecommend>> getBangumiDetailRecommend();



}
