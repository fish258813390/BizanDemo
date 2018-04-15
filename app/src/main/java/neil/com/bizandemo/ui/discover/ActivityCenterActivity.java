package neil.com.bizandemo.ui.discover;

import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.adapter.discover.ActivityCenterAdapter;
import neil.com.bizandemo.base.BaseRefreshActivity;
import neil.com.bizandemo.bean.discover.ActivityCenter;
import neil.com.bizandemo.mvp.contract.discover.ActivityCenterContract;
import neil.com.bizandemo.mvp.presenter.discover.ActivityCenterPresenter;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.widget.CustomLoadMoreView;

/**
 * 活动中心
 * Created by neil on 2018/3/17 0017.
 */
public class ActivityCenterActivity extends BaseRefreshActivity<ActivityCenterPresenter, ActivityCenter.ListBean>
        implements ActivityCenterContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    private ActivityCenterAdapter mAdapter;
    private int mPage = 1;
    private static final int PS = 20;
    private int mTotal = 0;
    private boolean mIsError = false;
    private boolean mIsLoadMore = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_center;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("活动中心");
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new ActivityCenterAdapter(mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(this, mRecycler); // 将对应的recyclerview绑定下拉刷新
    }

    @Override
    public void showActivityCenter(List<ActivityCenter.ListBean> listBeanList, int total) {
        if (!mIsLoadMore) {
            mList.addAll(listBeanList);
            mTotal = total;
            finishTask();
        } else {
            mAdapter.addData(listBeanList); // 加载更多
            mAdapter.loadMoreComplete(); // 加载完成
        }
    }

    @Override
    protected void finishTask() {
        mAdapter.setNewData(mList);
    }


    @Override
    protected void loadData() {
        mPresenter.getActivityCenterData(mPage, PS);
    }

    /**
     * 下拉刷新时会调用
     */
    @Override
    protected void clearData() {
        super.clearData();
        mPage = 1;
        mIsError = false;
        mIsLoadMore = false;
        mAdapter.setEnableLoadMore(false); // 刷新时关闭上拉加载
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        AppUtils.runOnUIDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getItemCount() >= mTotal) {
                    mAdapter.loadMoreEnd(); // 结束加载
                } else {
                    if (!mIsError) {
                        mPage++;
                        loadData();
                    } else {
                        mIsError = true;
                        mAdapter.loadMoreFail(); // 加载失败
                    }
                }
            }
        }, 650);
    }

    @Override
    public void showError(String msg) {
        gone(mRefresh);
        super.showError(msg);
        mIsError = true;
    }

    @Override
    public void complete() {
        super.complete();
        mAdapter.setEnableLoadMore(true); // 需要重新开启监听
    }
}
