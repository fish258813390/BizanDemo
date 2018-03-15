package neil.com.bizandemo.mvp.contract.bangumi;

import java.util.List;

import neil.com.bizandemo.base.BaseContract;
import neil.com.bizandemo.bean.bangumi.MulBangumiDetail;

/**
 * 番剧详情 器乐类
 * Created by neil on 2018/3/15 0015.
 */
public interface BangumiDetailContract {

    interface View extends BaseContract.BaseView {

        void showMulBangumiDetail(List<MulBangumiDetail> mulBangumiDetails, String title);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getBangumiDetailData();

    }

}
