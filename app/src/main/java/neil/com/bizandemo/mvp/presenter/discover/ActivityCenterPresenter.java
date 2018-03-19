package neil.com.bizandemo.mvp.presenter.discover;

import javax.inject.Inject;

import neil.com.bizandemo.base.BaseSubscriber;
import neil.com.bizandemo.base.RxPresenter;
import neil.com.bizandemo.bean.discover.ActivityCenter;
import neil.com.bizandemo.mvp.contract.discover.ActivityCenterContract;
import neil.com.bizandemo.network.helper.RetrofitHelper;
import neil.com.bizandemo.rx.RxUtils;

/**
 * 活动中心Presenter
 * Created by neil on 2018/3/17 0017.
 */
public class ActivityCenterPresenter extends RxPresenter<ActivityCenterContract.View>
        implements ActivityCenterContract.Presenter<ActivityCenterContract.View> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ActivityCenterPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getActivityCenterData(int page, int pageSize) {
        BaseSubscriber<ActivityCenter> subscriber = mRetrofitHelper.getActivityCenter(page, pageSize)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<ActivityCenter>(mView) {
                    @Override
                    public void onSuccess(ActivityCenter activityCenter) {
                        if (activityCenter.list != null) {
                            mView.showActivityCenter(activityCenter.list, activityCenter.total);
                        }
                    }
                });
        addSubscribe(subscriber);
    }
}
