package neil.com.bizandemo.bean.discover;

import java.util.List;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/6/5 22:58
 * 描述:活动中心
 */
public class ActivityCenter {
    public int code;
    public int total;
    public int pages;
    public List<ListBean> list;

    public static class ListBean {

        /**
         * cover : http://i0.hdslb.com/bfs/activity-plat/cover/20170605/r9vj2n3964.jpg
         * link : http://www.bilibili.com/blackboard/topic/activity-H12uuAGzZ.html
         * title : 加油！奋斗的六月
         * state : 0
         */

        public String cover;
        public String link;
        public String title;
        public int state;
    }

}
