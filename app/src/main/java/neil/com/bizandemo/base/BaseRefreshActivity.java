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
 * 继承的子类可能含有SwipeRefreshLayout 或没有,在initRefreshLayout初始化时做区分
 *
 * @author neil
 * @date 2018/3/13
 */
public abstract class BaseRefreshActivity<T extends BaseContract.BasePresenter, K> extends BaseActivity<T> implements SwipeRefreshLayout.OnRefreshListener {
    protected RecyclerView mRecycler;
    protected SwipeRefreshLayout mRefresh;
    protected boolean mIsRefreshing = false;
    protected List<K> mList = new ArrayList<>(); // 数据源,用于填充Adapter
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
                if (mRefresh != null) {
                    mRefresh.setRefreshing(false);
                }
            }
        }, 650);
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
        mLoading = ButterKnife.findById(this, R.id.pw_loading); // 加载框
        initRefreshLayout();
        initRecyclerView();
    }

    /**
     * 初始化recyclerview
     */
    protected void initRecyclerView() {

    }

    /**
     * 当已经下拉刷新时，将 mIsRefreshing = true
     */
    protected void clearData() {
        mIsRefreshing = true;
    }

    /**
     * 如果Activity 中没有SwipRereshlayout ,根据有无对话框进行初始化数据
     */
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
                }, 650);
            } else {
                super.initDatas();
            }
        }
    }

}
