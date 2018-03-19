package neil.com.bizandemo.mvp.contract.discover;

import java.util.List;

import neil.com.bizandemo.base.BaseContract;
import neil.com.bizandemo.bean.discover.ActivityCenter;

/**
 * 话题、活动中心契约类
 * Created by neil on 2018/3/17 0017.
 */
public interface ActivityCenterContract {

    interface View extends BaseContract.BaseView {

        void showActivityCenter(List<ActivityCenter.ListBean> listBeanList, int total);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getActivityCenterData(int page, int pageSize);

    }

}
