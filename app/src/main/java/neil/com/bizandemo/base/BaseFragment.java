package neil.com.bizandemo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import neil.com.bizandemo.AppApplication;
import neil.com.bizandemo.di.component.DaggerFragmentComponent;
import neil.com.bizandemo.di.component.FragmentComponent;
import neil.com.bizandemo.di.module.FragmentModule;

/**
 * 基础Fragment
 *
 * @author neil
 * @date 2018/3/13
 */
public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends RxFragment implements BaseContract.BaseView {

    @Inject
    protected T mPresenter;
    protected View mRootView;
    protected Activity mActivity;
    protected LayoutInflater inflater;
    protected Context mContext;
    protected boolean isPrepared; // 标志位 是否已经初始化完成
    protected boolean isVisible; // 标志位 fragment是否可见
    private Unbinder mUnbinder;
    public ConstraintLayout mError;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            mActivity = getSupportActivity();
            mContext = mActivity;
            this.inflater = inflater;
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        initInject();
        initPresenter();
        initVariables();
//        mError = ButterKnife.findById(mRootView, R.id.cl_error);
        initWidget();
        finishCreateView(savedInstanceState);
        initDatas();
    }

    /**
     * 数据懒加载处理
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void complete() {

    }

    /**
     * 布局
     */
    protected abstract int getLayoutId();

    /**
     * 初始化依赖注入
     */
    protected abstract void initInject();

    /**
     * 初始化Presenter
     */
    private void initPresenter() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 初始化变量
     */
    protected void initVariables() {

    }

    /**
     * 初始化控件
     */
    protected abstract void initWidget();

    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    protected void initDatas() {
        loadData();
    }

    /**
     * 加载数据
     */
    protected void loadData() {
    }

    protected void lazyLoad() {
        // 已经预加载 | 是否可见
        if (!isPrepared || !isVisible) {
            return;
        }
        lazyLoadData();
        isPrepared = false;
    }

    /**
     * 懒加载
     */
    protected void lazyLoadData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnbinder.unbind();
    }

    @Override
    public void onDetach() {
        this.mActivity = null;
        super.onDetach();
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(AppApplication.getInstance().getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    /**********************
     * 额外的方法
     *****************************/

    /**
     * 获取Activity
     */
    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    /**
     * 获取ApplicationContext
     */
    public Context getApplicationContext() {
        return this.mContext == null ? (getActivity() == null ? null : getActivity().getApplicationContext())
                : this.mContext.getApplicationContext();
    }

    /**
     * 隐藏View
     *
     * @param views view数组
     */
    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 显示View
     *
     * @param views view数组
     */
    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 隐藏View
     *
     * @param id
     */
    protected void gone(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = $(resId);
                if (view != null)
                    gone(view);
            }
        }

    }

    /**
     * 显示View
     *
     * @param id
     */
    protected void visible(final @IdRes int... id) {
        if (id != null && id.length > 0) {
            for (int resId : id) {
                View view = $(resId);
                if (view != null)
                    visible(view);
            }
        }
    }

    public View $(@IdRes int id) {
        View view;
        if (mRootView != null) {
            view = mRootView.findViewById(id);
            return view;
        }
        return null;
    }

}
