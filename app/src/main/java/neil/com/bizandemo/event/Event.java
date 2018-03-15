package neil.com.bizandemo.event;

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


}
