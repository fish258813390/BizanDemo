package neil.com.bizandemo.network.constants;

/**
 * 番剧url
 * Created by neil on 2018/3/16 0016.
 */
public interface BangumiServiceConstant {

    /**
     * 番剧详情
     */
    String BANGUMI_DETAIL = "api/season_v5?access_key=ccfbb1b10ce8ab8418a2e00b9ca9a3a0&appkey=1d8b6e7d45233436&build=505000&mobi_app=android&platform=android&season_id=6066&ts=1497169313&type=bangumi&sign=c6796f6ea4a6cae28a4d8fc555fde2da";


    /**
     * 番剧详情  更多推荐
     */
    String BANGUMI_DETAIL_RECOMMEND = "api/season/recommend/rnd/6066.json?appkey=1d8b6e7d45233436&build=505000&mobi_app=android&platform=android&ts=1497169314&sign=da4d668fe4aaf97de55541f8d05ac57f";


}
