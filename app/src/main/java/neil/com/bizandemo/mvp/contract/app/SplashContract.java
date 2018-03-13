package neil.com.bizandemo.mvp.contract.app;

import neil.com.bizandemo.base.BaseContract;
import neil.com.bizandemo.bean.app.Splash;

/**
 * 欢迎页面 契约类
 *
 * @author neil
 * @date 2018/3/13
 */
public interface SplashContract {

    interface View extends BaseContract.BaseView {

        void showSplash(Splash splash);

        void showCountDown(int count);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getSplashData();

        void setCountDown();
    }

}
