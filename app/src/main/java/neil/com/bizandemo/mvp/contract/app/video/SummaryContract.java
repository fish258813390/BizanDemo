package neil.com.bizandemo.mvp.contract.app.video;

import java.util.List;

import neil.com.bizandemo.base.BaseContract;
import neil.com.bizandemo.bean.app.video.MulSummary;

/**
 * 视频详情页面
 * 简介contract
 * Created by neil on 2018/4/13 0013.
 */
public interface SummaryContract {

    interface View extends BaseContract.BaseView {

        void showSummary(List<MulSummary> mulSummaryList);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getSummaryData();

    }

}
