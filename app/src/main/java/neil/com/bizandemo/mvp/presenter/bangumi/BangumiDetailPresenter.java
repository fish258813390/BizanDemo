package neil.com.bizandemo.mvp.presenter.bangumi;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.subscribers.BlockingSubscriber;
import neil.com.bizandemo.base.BaseSubscriber;
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

        BaseSubscriber<List<MulBangumiDetail>> subscriber = mRetrofitHelper.getBangumiDetail()
                .compose(RxUtils.handleResult())
                .flatMap(new Function<BangumiDetail, Flowable<HttpResponse<BangumiDetailRecommend>>>() {
                    @Override
                    public Flowable<HttpResponse<BangumiDetailRecommend>> apply(BangumiDetail bangumiDetail) throws Exception {
                        title.append(bangumiDetail.title);
                        List<BangumiDetail.EpisodesBean> episodes = bangumiDetail.episodes;
                        Collections.reverse(episodes);
                        mulBangumiDetails.addAll(Arrays.asList(
                                new MulBangumiDetail().
                                        setItemType(MulBangumiDetail.TYPE_HEAD) // 头部
                                        .setPlayCount(bangumiDetail.play_count)
                                        .setCover(bangumiDetail.cover)
                                        .setFavorites(bangumiDetail.favorites)
                                        .setIsFinish(bangumiDetail.is_finish)

                        ));
                        return mRetrofitHelper.getBangumiDetailRecommend();
                    }
                })
                .compose(RxUtils.handleResult())
                .flatMap(new Function<BangumiDetailRecommend, Flowable<HttpResponse<BangumiDetailComment>>>() {
                    @Override
                    public Flowable<HttpResponse<BangumiDetailComment>> apply(BangumiDetailRecommend bangumiDetailRecommend) throws Exception {
                        return mRetrofitHelper.getBangumiDetailComment();
                    }
                })
                .map(new Function<HttpResponse<BangumiDetailComment>, List<MulBangumiDetail>>() {
                    @Override
                    public List<MulBangumiDetail> apply(HttpResponse<BangumiDetailComment> bangumiDetailCommentHttpResponse) throws Exception {
                        return null;
                    }
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<MulBangumiDetail>>(mView) {
                    @Override
                    public void onSuccess(List<MulBangumiDetail> mulBangumiDetails) {

                    }
                });
        addSubscribe(subscriber);

    }
}
