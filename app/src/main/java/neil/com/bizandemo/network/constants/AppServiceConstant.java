package neil.com.bizandemo.network.constants;

/**
 * @author neil
 * @date 2018/3/13
 */

public interface AppServiceConstant {

    /**
     * splash界面
     */
    String APP_SPLASH = "/x/v2/splash?mobi_app=android&build=505000&channel=360&width=1080&height=1920&ver=4344558841496142006";

    /**
     * 首页分区
     */
    String HOME_REGION = "/x/v2/show/index?access_key=fcbe0b2d947971fd3cc2b9e759d63097&appkey=1d8b6e7d45233436&build=505000&mobi_app=android&platform=android&ts=1495780436&sign=93ebfdf6018d866239977af373d45dba";


    ///////////////////////////////
    /**
     * 番剧详情
     */
    String BANGUMI_DETAIL = "api/season_v5?access_key=ccfbb1b10ce8ab8418a2e00b9ca9a3a0&appkey=1d8b6e7d45233436&build=505000&mobi_app=android&platform=android&season_id=6066&ts=1497169313&type=bangumi&sign=c6796f6ea4a6cae28a4d8fc555fde2da";


    /**
     * 番剧详情推荐
     */
    String BANGUMI_DETAIL_RECOMMEND = "api/season/recommend/rnd/6066.json?appkey=1d8b6e7d45233436&build=505000&mobi_app=android&platform=android&ts=1497169314&sign=da4d668fe4aaf97de55541f8d05ac57f";

}
