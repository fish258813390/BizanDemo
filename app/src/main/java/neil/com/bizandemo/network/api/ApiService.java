package neil.com.bizandemo.network.api;

import io.reactivex.Flowable;
import neil.com.bizandemo.bean.bangumi.BangumiDetailComment;
import neil.com.bizandemo.network.constants.ApiServiceConstant;
import retrofit2.http.GET;

/**
 * 公共 service
 * API_BASE_URL = "http://api.bilibili.cn/";
 * Created by neil on 2018/3/16 0016.
 */
public interface ApiService {

    /**
     * 番剧详情 评论
     */
    @GET(ApiServiceConstant.BANGUMI_DETAIL_COMMENT)
    Flowable<BangumiDetailComment> getBangumiDetailComment();


}
