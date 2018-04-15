package neil.com.bizandemo.mvp.contract.app.video;

import neil.com.bizandemo.base.BaseContract;
import neil.com.bizandemo.bean.app.video.VideoDetail;
import neil.com.bizandemo.bean.app.video.VideoDetailComment;

/**
 * 视频详情 契约类
 * Created by neil on 2018/4/13 0013.
 */
public interface VideoDetailContract {

    interface View extends BaseContract.BaseView {
        void showVideoDetail(VideoDetail.DataBean videoDetail);

        void showVideoDetailComment(VideoDetailComment.DataBean videoDetailComment);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getVideoDetailData();
    }

}
