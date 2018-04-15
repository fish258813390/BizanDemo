package neil.com.bizandemo.ui.app.video;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;
import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.app.video.VideoDetail;
import neil.com.bizandemo.bean.app.video.VideoDetailComment;
import neil.com.bizandemo.event.Event;
import neil.com.bizandemo.mvp.contract.app.video.VideoDetailContract;
import neil.com.bizandemo.mvp.presenter.app.video.VideoDetailPresenter;
import neil.com.bizandemo.rx.RxBus;
import neil.com.bizandemo.ui.region.BaseRegionActivity;
import neil.com.bizandemo.utils.StatusBarUtil;

/**
 * 视频详情
 * Created by neil on 2018/4/13 0013.
 */
public class VideoDetailActivity extends BaseRegionActivity<VideoDetailPresenter, Nullable> implements VideoDetailContract.View {

    @BindView(R.id.iv_video_preview)
    ImageView mIvVideoPreview;
    @BindView(R.id.tv_av)
    TextView mTvAv;
    @BindView(R.id.tv_player)
    TextView mTvPlayer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private VideoDetail.DataBean mVideoDetail;
    private VideoDetailComment.DataBean mVideoDetailComment;
    private CollapsingToolbarLayoutState state; // 折叠状态

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_detail1;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showVideoDetail(VideoDetail.DataBean videoDetail) {
        mVideoDetail = videoDetail;
    }

    @Override
    public void showVideoDetailComment(VideoDetailComment.DataBean videoDetailComment) {
        mVideoDetailComment = videoDetailComment;
        finishTask();
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void finishTask() {
        //设置图片
        Glide.with(mContext)
                .load(mVideoDetail.pic)
                .centerCrop()
                .placeholder(R.drawable.bili_default_image_tv)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(mIvVideoPreview);
        super.finishTask();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initAppBar();
    }

    @Override
    protected void initTitle() {
        mTitles.add("简介");
        mTitles.add("评论(" + mVideoDetailComment.page.count + ")");
    }

    @Override
    protected void initFragment() {
        mFragments.add(SummaryFragment.newInstance());
        mFragments.add(CommentFragment.newInstance());
    }

    @Override
    protected void initDatas() {
        mPresenter.getVideoDetailData();
    }

    @Override
    protected void initEvent() {
        Event.VideoDetailEvent videoDetailEvent = new Event.VideoDetailEvent();
        videoDetailEvent.videoDetail = mVideoDetail;

        Event.VideoDetailCommentEvent videoDetailCommentEvent = new Event.VideoDetailCommentEvent();
        videoDetailCommentEvent.videoDetailComment = mVideoDetailComment;

        RxBus.getInstance().post(videoDetailEvent);
//        RxBus.getInstance().post(videoDetailCommentEvent);

    }

    @Override
    protected void initViewPager() {
        super.initViewPager();
        setCurrentItem(0);
    }



    private void setViewsTranslation(int target){
        mFab.setTranslationY(target);
        if (target == 0) {
            mFab.animate().scaleX(1f).scaleY(1f)
                    .setInterpolator(new OvershootInterpolator())
                    .start();
            mFab.setClickable(true);
        } else if (target < 0) {
            mFab.animate().scaleX(0f).scaleY(0f)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();
            mFab.setClickable(false);
        }
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);
    }

    private void initAppBar(){
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0){

                }
            }
        });
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        gone(R.id.pw_loading);
    }

    @Override
    public void complete() {
        super.complete();
        gone(R.id.pw_loading);
    }
}
