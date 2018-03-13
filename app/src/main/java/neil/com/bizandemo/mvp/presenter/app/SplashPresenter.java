package neil.com.bizandemo.mvp.presenter.app;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import neil.com.bizandemo.base.BaseSubscriber;
import neil.com.bizandemo.base.RxPresenter;
import neil.com.bizandemo.bean.app.Splash;
import neil.com.bizandemo.mvp.contract.app.SplashContract;
import neil.com.bizandemo.network.helper.RetrofitHelper;
import neil.com.bizandemo.rx.RxUtils;

/**
 * 启动页面presenter
 *
 * @author neil
 * @date 2018/3/13
 */

public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter<SplashContract.View> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SplashPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getSplashData() {
        BaseSubscriber<Splash> subscriber = mRetrofitHelper.getSplash()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<Splash>(mView) {
                    @Override
                    public void onSuccess(Splash splash) {
                        if (splash.code == 0) {
                            mView.showSplash(splash);
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        mView.showError(message);
                    }
                });
        addSubscribe(subscriber);
    }

    /**
     * 5s 倒计时
     */
    @Override
    public void setCountDown() {
        Long count = 5L;
        Disposable subscribe = Flowable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> count - aLong)
                .take(count + 1)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(aLong -> mView.showCountDown(aLong.intValue()));
        addSubscribe(subscribe);
    }
}
