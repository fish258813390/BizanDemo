package neil.com.bizandemo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import neil.com.bizandemo.AppApplication;
import neil.com.bizandemo.R;
import neil.com.bizandemo.di.component.ActivityComponent;
import neil.com.bizandemo.di.component.DaggerActivityComponent;
import neil.com.bizandemo.di.module.ActivityModule;
import neil.com.bizandemo.event.Event;
import neil.com.bizandemo.rx.RxBus;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.utils.StatusBarUtil;

/**
 * 基础Activity
 *
 * @author neil
 * @date 2018/3/13
 */
public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {

    @Inject
    protected T mPresenter;
    protected Toolbar mToolbar; // ToolBar
    protected Context mContext; //上下文环境
    protected boolean mBack = true;
    private ConstraintLayout mError;
    private Disposable mDisposable;

    /**
     * 布局文件
     */
    protected abstract int getLayoutId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        ButterKnife.bind(this);
//        mToolbar = ButterKnife.findById(this, R.id.toolbar);
//        mError = ButterKnife.findById(this, R.id.cl_error);
        initStatusBar();
        initInject();
        initPresenter();
        initVariables();
        AppApplication.getInstance().addActivity(this);
        if (mToolbar != null) {
            initToolbar();  // 初始化Toolbar
            setSupportActionBar(mToolbar);
            if (mBack) {
                mToolbar.setNavigationOnClickListener(
                        v -> finish()
                );
            }
        }

        initWidget();
        initDatas();
        initExit();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(AppApplication.getInstance().getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public void showError(String msg) {
        if(mError != null){
            visible(mError);
        }
    }

    @Override
    public void complete() {
        if(mError != null){
            gone(mError);
        }
    }

    /**
     * 初始化StatusBar
     */
    protected void initStatusBar() {
        StatusBarUtil.setColorNoTranslucent((Activity) mContext, AppUtils.getColor(R.color.colorPrimary));
    }

    /**
     * 依赖注入
     */
    protected abstract void initInject();

    /**
     * 初始化Presenter
     */
    protected void initPresenter() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 初始化变量
     */
    protected abstract void initVariables();

    /**
     * 初始化Toolbar
     */
    protected void initToolbar() {
        if (mBack) {
            mToolbar.setNavigationIcon(R.drawable.ic_clip_back_white);
        }
    }

    /**
     * 初始化控件
     */
    public abstract void initWidget();

    /**
     * 初始化数据
     */
    protected void initDatas() {
        loadData();
    }

    /**
     * 加载数据
     */
    protected void loadData() {
    }

    private void initExit() {
        mDisposable = RxBus.getInstance().toDefaultFlowable(
                Event.ExitEvent.class, exitEvent -> {
                    if (exitEvent.exit == -1) {
                        finish();
                    }
                });
//        mDisposable = RxBus.getInstance().toDefaultFlowable(
//                Event.ExitEvent.class, new Consumer<Event.ExitEvent>() {
//                    @Override
//                    public void accept(Event.ExitEvent exitEvent) throws Exception {
//
//                    }
//                });
    }

    /**
     * 隐藏View
     *
     * @param views 视图
     */
    protected void gone(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 显示视图
     * @param views
     */
    protected void visible(View... views) {
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

    private View $(@IdRes int id) {
        View view;
        view = this.findViewById(id);
        return view;
    }

}
