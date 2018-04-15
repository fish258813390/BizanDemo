package neil.com.bizandemo.event;

import neil.com.bizandemo.bean.app.video.VideoDetail;
import neil.com.bizandemo.bean.app.video.VideoDetailComment;

/**
 * 事件
 * @author neil
 * @date 2018/3/13
 */

public class Event {

    /**
     * 退出
     */
    public static class ExitEvent {
        public int exit;
    }


    /**
     * 抽屉开启
     */
    public static class StartNavigationEvent {
        public boolean start;
    }


    public static class VideoDetailEvent {
        public VideoDetail.DataBean videoDetail;
    }

    public static class VideoDetailCommentEvent {
        public VideoDetailComment.DataBean videoDetailComment;
    }

}
