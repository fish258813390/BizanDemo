package neil.com.bizandemo.mvp.contract.home;

import java.util.List;

import neil.com.bizandemo.base.BaseContract;
import neil.com.bizandemo.bean.recommend.Recommend;

/**
 * 推荐
 * Created by neil on 2018/4/13 0013.
 */
public interface RecommendContract {

    interface View extends BaseContract.BaseView {

        void showRecommend(List<Recommend> recommend);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getRecommendData();
    }

}
