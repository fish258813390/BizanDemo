package neil.com.bizandemo;

import android.app.Activity;
import android.app.Application;

import java.util.HashSet;
import java.util.Set;

import neil.com.bizandemo.di.component.AppComponent;
import neil.com.bizandemo.di.component.DaggerAppComponent;
import neil.com.bizandemo.di.module.AppModule;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.utils.CrashHandler;
import neil.com.bizandemo.utils.LogUtils;
import neil.com.bizandemo.utils.NetworkUtils;

/**
 * Created by neil on 2018/3/12 0012.
 * <p>
 * * 描述:APP
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #               佛祖保佑         永无BUG             #
 */
public class AppApplication extends Application {

    private static AppApplication mContext;

    private Set<Activity> allActivities;

    private AppComponent mAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        mContext = this;
        initNetwork(); // 开启网络监听
        initCrashHandler(); // 初始化奔溃日志
        initLog(); // 初始化日志
        initPrefs(); // 初始化sp
        initComponent(); // 初始化网络模块组件
    }

    /**
     * 增加Activity
     *
     * @param act
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除Activity
     *
     * @param act
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    /**
     * 初始化网络模块组件
     */
    private void initComponent() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static AppApplication getInstance() {
        return mContext;
    }

    /**
     * 开启网络监听
     */
    private void initNetwork() {
//        NetworkUtils.startNetService(this);
    }

    /**
     * 初始化崩溃日志
     */
    private void initCrashHandler() {
        CrashHandler.getInstance().init(this);
    }

    /**
     * 初始化log
     */
    private void initLog() {
        LogUtils.init(this);
    }

    /**
     * 初始化sp
     */
    private void initPrefs() {
    }


}
