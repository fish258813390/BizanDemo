package neil.com.bizandemo.mvp.presenter.bangumi;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import neil.com.bizandemo.base.RxPresenter;
import neil.com.bizandemo.bean.bangumi.BangumiDetail;
import neil.com.bizandemo.bean.bangumi.BangumiDetailComment;
import neil.com.bizandemo.bean.bangumi.BangumiDetailRecommend;
import neil.com.bizandemo.bean.bangumi.MulBangumiDetail;
import neil.com.bizandemo.mvp.contract.bangumi.BangumiDetailContract;
import neil.com.bizandemo.network.helper.RetrofitHelper;
import neil.com.bizandemo.network.response.HttpResponse;
import neil.com.bizandemo.rx.RxUtils;

/**
 * 描述:番剧详情presenter
 * Created by neil on 2018/3/15 0015.
 */
public class BangumiDetailPresenter extends RxPresenter<BangumiDetailContract.View> implements
        BangumiDetailContract.Presenter<BangumiDetailContract.View> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public BangumiDetailPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getBangumiDetailData() {
        List<MulBangumiDetail> mulBangumiDetails = new ArrayList<>();
        StringBuilder title = new StringBuilder();

        mRetrofitHelper.getBangumiDetail()
                .compose(RxUtils.handleResult());


    }
}
