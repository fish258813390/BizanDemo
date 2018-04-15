package neil.com.bizandemo.mvp.presenter.app.video;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import neil.com.bizandemo.base.BaseSubscriber;
import neil.com.bizandemo.base.RxPresenter;
import neil.com.bizandemo.bean.app.video.VideoDetail;
import neil.com.bizandemo.bean.app.video.VideoDetailComment;
import neil.com.bizandemo.mvp.contract.app.video.VideoDetailContract;
import neil.com.bizandemo.network.helper.RetrofitHelper;
import neil.com.bizandemo.rx.RxUtils;

/**
 * 视频详情
 * Created by neil on 2018/4/13 0013.
 */
public class VideoDetailPresenter extends RxPresenter<VideoDetailContract.View>
        implements VideoDetailContract.Presenter<VideoDetailContract.View> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public VideoDetailPresenter(RetrofitHelper retrofitHelper){
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getVideoDetailData() {
        BaseSubscriber<VideoDetailComment> baseSubscriber = mRetrofitHelper.getVideoDetail()
                .flatMap(new Function<VideoDetail, Flowable<VideoDetailComment>>() {
                    @Override
                    public Flowable<VideoDetailComment> apply(VideoDetail videoDetail) throws Exception {
                        mView.showVideoDetail(videoDetail.data);
                        return mRetrofitHelper.getVideoDetailComment();
                    }
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<VideoDetailComment>(mView) {
                    @Override
                    public void onSuccess(VideoDetailComment videoDetailComment) {
                        mView.showVideoDetailComment(videoDetailComment.data);
                    }
                });
        addSubscribe(baseSubscriber);
    }
}
