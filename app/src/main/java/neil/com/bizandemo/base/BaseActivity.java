package neil.com.bizandemo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

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
 * updated by neil on 2018/3/19 将通用的错误布局由约束布局改成相对布局
 */
public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {

    @Inject
    protected T mPresenter;
    protected Toolbar mToolbar; // ToolBar
    protected Context mContext; //上下文环境
    protected boolean mBack = true;
    private RelativeLayout mError;
    private Disposable mDisposable;

    /**
     * 布局文件
     */
    protected abstract int getLayoutId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId()); // 先设置布局
        mContext = this;
        ButterKnife.bind(this); // 布局加载完成后，绑定ButterKnife
        mToolbar = ButterKnife.findById(this, R.id.toolbar); // 获取toolbar,如果没有即为null
        mError = ButterKnife.findById(this, R.id.cl_error);  // 获取公共错误布局,没有则为null
        initStatusBar(); // 状态栏初始化
        initInject(); // 依赖注入
        initPresenter(); // 初始化presenter,绑定view
        initVariables(); // 初始化一些基本数据
        AppApplication.getInstance().addActivity(this);  // 将Activity 添加到栈中去管理
        if (mToolbar != null) {
            initToolbar();  // 初始化Toolbar
            setSupportActionBar(mToolbar); // toolbar代替Actionbar
            if (mBack) {
                mToolbar.setNavigationOnClickListener(
                        v -> finish()
                );
            }
        }

        initWidget(); // 初始化一些空间
        initDatas(); // 初始化数据
        initExit();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * 获取ActivityComponent,对子类需要用到依赖注入,进行依赖注入
     */
    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(AppApplication.getInstance().getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    /**
     * 当异常返回时,显示错误页面
     *
     * @param msg
     */
    @Override
    public void showError(String msg) {
        if (mError != null) {
            visible(mError);
        }
    }

    /**
     * 当加载成功时
     */
    @Override
    public void complete() {
        if (mError != null) {
            gone(mError);
        }
    }

    /**
     * Activity销毁时
     */
    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        AppApplication.getInstance().removeActivity(this);
        super.onDestroy();
        if (!mDisposable.isDisposed()) { // 按下物理返回退出的订阅事件
            mDisposable.dispose();
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
     *
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
