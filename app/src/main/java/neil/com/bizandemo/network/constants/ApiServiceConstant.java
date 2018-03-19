package neil.com.bizandemo.network.constants;

/**
 * Api 常量
 * Created by neil on 2018/3/16 0016.
 */

public interface ApiServiceConstant {

    /**
     * 番剧详情  更多推荐下面的评论
     */
    String BANGUMI_DETAIL_COMMENT = "x/v2/reply?access_key=ccfbb1b10ce8ab8418a2e00b9ca9a3a0&appkey=1d8b6e7d45233436&build=505000&mobi_app=" +
            "android&oid=9716141&plat=2&platform=android&pn=1&ps=20&sort=0&ts=1497169314&type=1&sign=ecca925ba55cecd151b5839f19d57657";

    /**
     * 活动中心
     */
    String ACTIVITY_CENTER = "event/getlist?device=phone&mobi_app=iphone";
}
