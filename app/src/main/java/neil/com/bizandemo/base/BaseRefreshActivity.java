package neil.com.bizandemo.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import neil.com.bizandemo.R;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.utils.ToastUtils;
import neil.com.bizandemo.widget.ProgressWheel;

/**
 * 基础刷新Activity
 *
 * @author neil
 * @date 2018/3/13
 */

public abstract class BaseRefreshActivity<T extends BaseContract.BasePresenter, K> extends BaseActivity<T> implements SwipeRefreshLayout.OnRefreshListener {
    protected RecyclerView mRecycler;
    protected SwipeRefreshLayout mRefresh;
    protected boolean mIsRefreshing = false;
    protected List<K> mList = new ArrayList<>();
    private ProgressWheel mLoading;

    protected void initRefreshLayout() {
        if (mRefresh != null) {
            mRefresh.setColorSchemeResources(R.color.colorPrimary);
            mRecycler.post(new Runnable() {
                @Override
                public void run() {
                    mRefresh.setRefreshing(true);
                    loadData();
                }
            });
            mRefresh.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {
        clearData();
        loadData();
    }

    @Override
    public void complete() {
        super.complete();
        AppUtils.runOnUIDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 600);
        if (mIsRefreshing) {
            if (mList != null) {
                mList.clear();
            }
            ToastUtils.showSingleLongToast("刷新成功");
        }
        mIsRefreshing = false;
        if (mLoading != null) {
            gone(mLoading);
        }
    }

    @Override
    public void initWidget() {
        mRefresh = ButterKnife.findById(this, R.id.refresh);
        mRecycler = ButterKnife.findById(this, R.id.recycler);
        // 加载框
        mLoading = ButterKnife.findById(this, R.id.pw_loading);
        initRefreshLayout();
        initRecyclerView();
    }

    /**
     * 初始化recyclerview
     */
    protected void initRecyclerView() {

    }

    protected void clearData() {

    }

    @Override
    protected void initDatas() {
        if (mRefresh == null) {
            if (mLoading != null) {
                visible(mLoading);
                AppUtils.runOnUIDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }, 500);
            }
        }
        super.initDatas();
    }

}