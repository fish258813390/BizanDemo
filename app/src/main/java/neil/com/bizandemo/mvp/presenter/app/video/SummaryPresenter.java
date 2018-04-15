package neil.com.bizandemo.mvp.presenter.app.video;

import java.lang.reflect.Array;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import neil.com.bizandemo.base.BaseSubscriber;
import neil.com.bizandemo.base.RxPresenter;
import neil.com.bizandemo.bean.app.video.MulSummary;
import neil.com.bizandemo.bean.app.video.VideoDetail;
import neil.com.bizandemo.event.Event;
import neil.com.bizandemo.mvp.contract.app.video.SummaryContract;
import neil.com.bizandemo.rx.RxBus;
import neil.com.bizandemo.rx.RxUtils;
import neil.com.bizandemo.utils.LogUtils;

/**
 * 简介presenter
 * Created by neil on 2018/4/13 0013.
 */
public class SummaryPresenter extends RxPresenter<SummaryContract.View>
        implements SummaryContract.Presenter<SummaryContract.View> {

    @Inject
    public SummaryPresenter() {

    }

    @Override
    public void getSummaryData() {
        LogUtils.e("SummaryPresenter 调用getSummaryData");
        BaseSubscriber<List<MulSummary>> subscriber = RxBus.getInstance().toFlowable(Event.VideoDetailEvent.class)
                .map(new Function<Event.VideoDetailEvent, List<MulSummary>>() {
                    @Override
                    public List<MulSummary> apply(Event.VideoDetailEvent videoDetailEvent) throws Exception {
                        LogUtils.e("SummaryPresenter 发送数据");
                        VideoDetail.DataBean videoDetail = videoDetailEvent.videoDetail;
                        ArrayList<MulSummary> mulSummaries = new ArrayList<>();
                        mulSummaries.addAll(Arrays.asList(new MulSummary()
                                        .setItemType(MulSummary.TYPE_DES)
                                        .setTitle(videoDetail.title)
                                        .setDesc(videoDetail.desc)
                                        .setState(videoDetail.stat),
                                new MulSummary()
                                        .setItemType(MulSummary.TYPE_OWNER)
                                        .setOwner(videoDetail.owner)
                                        .setCtime(videoDetail.ctime)
                                        .setTags(videoDetail.tag),
                                new MulSummary()
                                        .setItemType(MulSummary.TYPE_RELATE_HEAD)));
                        List<VideoDetail.DataBean.RelatesBean> relates = videoDetail.relates;
                        for (VideoDetail.DataBean.RelatesBean relatesBean : relates) {
                            mulSummaries.add(new MulSummary().setItemType(MulSummary.TYPE_RELATE).setRelates(relatesBean));
                        }
                        return mulSummaries;
                    }
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<List<MulSummary>>(mView) {
                    @Override
                    public void onSuccess(List<MulSummary> mulSummaries) {
                        LogUtils.e(" SummaryPresenter 收到数据---" + mulSummaries.size() );
                        mView.showSummary(mulSummaries);
                    }
                });
        addSubscribe(subscriber);
    }
}
