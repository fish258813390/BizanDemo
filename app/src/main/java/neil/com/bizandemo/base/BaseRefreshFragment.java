package neil.com.bizandemo.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import neil.com.bizandemo.R;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.utils.ToastUtils;

/**
 * 基础刷新的Fragment
 *
 * @author neil
 * @date 2018/3/13
 */
public abstract class BaseRefreshFragment<T extends BaseContract.BasePresenter, K> extends BaseFragment<T> implements SwipeRefreshLayout.OnRefreshListener {

    protected RecyclerView mRecycler;
    protected SwipeRefreshLayout mRefresh;
    protected boolean mIsRefreshing = false;
    protected List<K> mList = new ArrayList<>();

    protected void initRefreshLayout() {
        if (mRefresh != null) {
            mRefresh.setColorSchemeResources(R.color.colorPrimary);
            mRecycler.post(() -> {
                mRefresh.setRefreshing(true);
                lazyLoadData();
            });
            mRefresh.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {

    }

    protected void clearData() {
        mIsRefreshing = true;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mRefresh = ButterKnife.findById(mRootView, R.id.refresh);
        mRecycler = ButterKnife.findById(mRootView, R.id.recycler);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        if (mRefresh == null) {
            lazyLoadData();
        }
        isPrepared = false;
    }

    @Override
    public void complete() {
        super.complete();
        AppUtils.runOnUIDelayed(new Runnable() {
            @Override
            public void run() {
                if (mRefresh != null) {
                    mRefresh.setRefreshing(false);
                }
            }
        }, 600);
        if (mIsRefreshing) {
            if (mList != null) {
                mList.clear();
            }
            ToastUtils.showSingleLongToast("刷新成功");
        }
        mIsRefreshing = false;
    }

    /**
     * 初始化recyclerview
     */
    protected void initRecyclerView() {

    }

}
