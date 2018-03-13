package neil.com.bizandemo.ui.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import neil.com.bizandemo.AppApplication;
import neil.com.bizandemo.MainActivity;
import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.app.Splash;
import neil.com.bizandemo.di.component.DaggerActivityComponent;
import neil.com.bizandemo.di.module.ActivityModule;
import neil.com.bizandemo.mvp.contract.app.SplashContract;
import neil.com.bizandemo.mvp.presenter.app.SplashPresenter;
import neil.com.bizandemo.utils.StatusBarUtil;

/**
 * 欢迎页面
 *
 * @author neil
 * @date 2018/3/13
 */
public class SplashActivity extends RxAppCompatActivity implements SplashContract.View {

    @Inject
    SplashPresenter mPresenter;
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    @BindView(R.id.tv_count_down)
    TextView mTvCountDown;
    @BindView(R.id.ll_count_down)
    LinearLayout mLlCountDown;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        setContentView(R.layout.activity_splash);
        StatusBarUtil.setTransparent(this); // 设置状态栏透明
        ButterKnife.bind(this);
        initInject();
        initWidget();
        loadData();
    }

    private void loadData() {
        mPresenter.getSplashData();
        mPresenter.setCountDown();
    }

    /**
     * 依赖注入
     */
    private void initInject() {
        DaggerActivityComponent.builder()
                .appComponent(AppApplication.getInstance().getAppComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);
        mPresenter.attachView(this); // 保持p和v生命周期一致
    }

    private void initWidget() {
        RxView.clicks(mLlCountDown)
                .throttleFirst(3, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(object -> redirect());
    }

    private void redirect() {
//        boolean flag = PrefsUtils.getInstance().getBoolean(Constants.IS_LOGINED_FLAG, false);
//        flag = false;
//        if (flag) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        } else {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }

        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    @Override
    public void showError(String msg) {
        mIvSplash.setImageResource(R.mipmap.ic_default_bg);  //设置默认图片
    }

    @Override
    public void complete() {

    }

    @Override
    public void showSplash(Splash splash) {
        if (!splash.data.isEmpty()) {
            int pos = new Random().nextInt(splash.data.size());
            Glide.with(this)
                    .load(splash.data.get(pos).thumb)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(mIvSplash);
        } else {
            mIvSplash.setImageResource(R.mipmap.ic_default_bg);
        }
    }

    @Override
    public void showCountDown(int count) {
        mTvCountDown.setText(count + "");
        if (count == 0) {
            redirect();
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }
}
