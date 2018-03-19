package neil.com.bizandemo.mvp.presenter.bangumi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
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

    /**
     * 对数据源进行处理
     * flatMap、map操作符
     */
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
                        Collections.reverse(episodes); // 反转
                        mulBangumiDetails.addAll(Arrays.asList(
                                new MulBangumiDetail()
                                        .setItemType(MulBangumiDetail.TYPE_HEAD) // 头部
                                        .setPlayCount(bangumiDetail.play_count)
                                        .setCover(bangumiDetail.cover)
                                        .setFavorites(bangumiDetail.favorites)
                                        .setIsFinish(bangumiDetail.is_finish),
                                new MulBangumiDetail()
                                        .setItemType(MulBangumiDetail.TYPE_SEASON) // 分季节
                                        .setSeasonsTitle(bangumiDetail.season_title)
                                        .setSeasonsBeanList(bangumiDetail.seasons),
                                new MulBangumiDetail()
                                        .setItemType(MulBangumiDetail.TYPE_EPISODE_HEAD)
                                        .setTotalCount(bangumiDetail.total_count)
                                        .setIsFinish(bangumiDetail.is_finish), // 分集头部
                                new MulBangumiDetail()
                                        .setItemType(MulBangumiDetail.TYPE_EPISODE_ITEM) // 分集
                                        .setEpisodesBeans(episodes),
                                new MulBangumiDetail()
                                        .setItemType(MulBangumiDetail.TYPE_CONTRACTED)
                                        .setlistBeanList(bangumiDetail.rank.list)
                                        .setTotalBpCount(bangumiDetail.rank.total_bp_count)
                                        .setWeekBpCount(bangumiDetail.rank.week_bp_count), // 承包
                                new MulBangumiDetail()
                                        .setItemType(MulBangumiDetail.TYPE_DES)
                                        .setEvaluate(bangumiDetail.evaluate)
                                        .setTagsBeanList(bangumiDetail.tags) // 简介
                        ));
                        return mRetrofitHelper.getBangumiDetailRecommend();
                    }
                })
                .compose(RxUtils.handleResult())
                .flatMap(new Function<BangumiDetailRecommend, Flowable<BangumiDetailComment>>() {
                    @Override
                    public Flowable<BangumiDetailComment> apply(BangumiDetailRecommend bangumiDetailRecommend) throws Exception {
                        mulBangumiDetails.addAll(Arrays.asList(new MulBangumiDetail()
                                        .setItemType(MulBangumiDetail.TYPE_RECOMMEND_HEAD), // 推荐头部
                                new MulBangumiDetail()
                                        .setItemType(MulBangumiDetail.TYPE_RECOMMEND_ITEM)
                                        .setBangumiRecommendList(bangumiDetailRecommend.list)// 推荐
                        ));
                        return mRetrofitHelper.getBangumiDetailComment();
                    }
                })
                .map(new Function<BangumiDetailComment, List<MulBangumiDetail>>() {
                    @Override
                    public List<MulBangumiDetail> apply(BangumiDetailComment bangumiDetailComment) throws Exception {
                        mulBangumiDetails.add(new MulBangumiDetail()
                                .setItemType(MulBangumiDetail.TYPE_COMMENT_HEAD)
                                .setNum(bangumiDetailComment.data.page.num)
                                .setAccount(bangumiDetailComment.data.page.acount));
                        // 热门评论
                        List<BangumiDetailComment.DataBean.HotsBean> hotsList = bangumiDetailComment.data.hots;
                        for (BangumiDetailComment.DataBean.HotsBean hotsBean : hotsList) {
                            mulBangumiDetails.add(new MulBangumiDetail()
                                    .setItemType(MulBangumiDetail.TYPE_COMMENT_HOT_ITEM)
                                    .setHotsBean(hotsBean));
                        }
                        mulBangumiDetails.add(new MulBangumiDetail().setItemType(MulBangumiDetail.TYPE_COMMENT_MORE));
                        // 热门评论
                        List<BangumiDetailComment.DataBean.RepliesBean> repliesList = bangumiDetailComment.data.replies;
                        for (BangumiDetailComment.DataBean.RepliesBean repliyBean : repliesList) {
                            mulBangumiDetails.add(new MulBangumiDetail()
                                    .setItemType(MulBangumiDetail.TYPE_COMMENT_NOMAL_ITEM)
                                    .setRepliesBean(repliyBean));
                        }
                        return mulBangumiDetails;
                    }
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<MulBangumiDetail>>(mView) {
                    @Override
                    public void onSuccess(List<MulBangumiDetail> mulBangumiDetails) {
                        mView.showMulBangumiDetail(mulBangumiDetails, title.toString());
                    }
                });
        addSubscribe(subscriber);


//        List<MulBangumiDetail> mulBangumiDetails = new ArrayList<>();
//        StringBuilder title = new StringBuilder();
//        BaseSubscriber<List<MulBangumiDetail>> subscriber = mRetrofitHelper.getBangumiDetail()
//                .compose(RxUtils.handleResult())
//                .flatMap(bangumiDetail -> {
//                    title.append(bangumiDetail.title);
//                    List<BangumiDetail.EpisodesBean> episodes = bangumiDetail.episodes;
//                    Collections.reverse(episodes);//反转
//                    mulBangumiDetails.addAll(Arrays.asList(
//                            new MulBangumiDetail()
//                                    .setItemType(MulBangumiDetail.TYPE_HEAD)//头部
//                                    .setPlayCount(bangumiDetail.play_count)
//                                    .setCover(bangumiDetail.cover)
//                                    .setFavorites(bangumiDetail.favorites)
////                                    .setIsFinish(bangumiDetail.is_finish),
////                            new MulBangumiDetail()
////                                    .setItemType(MulBangumiDetail.TYPE_SEASON)//分季节
////                                    .setSeasonsTitle(bangumiDetail.season_title)
////                                    .setSeasonsBeanList(bangumiDetail.seasons),
////                            new MulBangumiDetail()
////                                    .setItemType(MulBangumiDetail.TYPE_EPISODE_HEAD)
////                                    .setTotalCount(bangumiDetail.total_count)
////                                    .setIsFinish(bangumiDetail.is_finish),//分集头部
////                            new MulBangumiDetail()
////                                    .setItemType(MulBangumiDetail.TYPE_EPISODE_ITEM)//分集
////                                    .setEpisodesBeans(episodes),
////                            new MulBangumiDetail()
////                                    .setItemType(MulBangumiDetail.TYPE_CONTRACTED)
////                                    .setlistBeanList(bangumiDetail.rank.list)
////                                    .setTotalBpCount(bangumiDetail.rank.total_bp_count)
////                                    .setWeekBpCount(bangumiDetail.rank.week_bp_count),//承包
////                            new MulBangumiDetail()
////                                    .setItemType(MulBangumiDetail.TYPE_DES)
////                                    .setEvaluate(bangumiDetail.evaluate)
////                                    .setTagsBeanList(bangumiDetail.tags)//简介
//                    ));
////                    return mRetrofitHelper.getBangumiDetailRecommend();
//                    return null;
//                })
////                .compose(RxUtils.handleResult())
////                .flatMap(bangumiDetailRecommend -> {
////                    mulBangumiDetails.addAll(Arrays.asList(new MulBangumiDetail()
////                                    .setItemType(MulBangumiDetail.TYPE_RECOMMEND_HEAD),//推荐头部
////                            new MulBangumiDetail()
////                                    .setItemType(MulBangumiDetail.TYPE_RECOMMEND_ITEM)
////                                    .setBangumiRecommendList(bangumiDetailRecommend.list)//推荐
////                    ));
////                    return mRetrofitHelper.getBangumiDetailComment();
////                })
//                .map(bangumiDetailComment -> {
////                    mulBangumiDetails.add(new MulBangumiDetail()
////                            .setItemType(MulBangumiDetail.TYPE_COMMENT_HEAD)
////                            .setNum(bangumiDetailComment.data.page.num)
////                            .setAccount(bangumiDetailComment.data.page.acount));
////                    Stream.of(bangumiDetailComment.data.hots).forEach(hotsBean -> mulBangumiDetails.add(new MulBangumiDetail()//热门评论
////                            .setItemType(MulBangumiDetail.TYPE_COMMENT_HOT_ITEM)
////                            .setHotsBean(hotsBean)));
////                    mulBangumiDetails.add(new MulBangumiDetail().setItemType(MulBangumiDetail.TYPE_COMMENT_MORE));
////
////                    Stream.of(bangumiDetailComment.data.replies).forEach(repliesBean -> mulBangumiDetails.add(new MulBangumiDetail()//普通评论
////                            .setItemType(MulBangumiDetail.TYPE_COMMENT_NOMAL_ITEM)
////                            .setRepliesBean(repliesBean)));
//                    return mulBangumiDetails;
//                })
//                .compose(RxUtils.rxSchedulerHelper())
//                .subscribeWith(new BaseSubscriber<List<MulBangumiDetail>>(mView) {
//                    @Override
//                    public void onSuccess(List<MulBangumiDetail> mulBangumiDetails) {
//                        mView.showMulBangumiDetail(mulBangumiDetails, title.toString());
//                    }
//                });
//        addSubscribe(subscriber);

    }
}
