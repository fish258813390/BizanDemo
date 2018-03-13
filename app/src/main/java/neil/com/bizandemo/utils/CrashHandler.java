package neil.com.bizandemo.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕获，当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 *
 * @author neil
 * @date 2018/3/13
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler; // 系统默认的UncaughtException处理类
    private static CrashHandler INSTANCE;  // CrashHandler实例
    private Context mContext;
    private Map<String, String> infos = new HashMap<>(); // 用来存储设备信息和异常信息

    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化
     */
    public void init(Context context){
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler(); // 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this);  // 设置该CrashHandler为程序的默认处理器
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
