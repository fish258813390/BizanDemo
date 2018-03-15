package neil.com.bizandemo.ui.home;

import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import neil.com.bizandemo.R;
import neil.com.bizandemo.base.BaseActivity;
import neil.com.bizandemo.event.Event;
import neil.com.bizandemo.rx.RxBus;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.utils.StatusBarUtil;
import neil.com.bizandemo.utils.ToastUtils;

/**
 * 首页
 *
 * @author neil
 * @date 2018/3/12
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    long exitTime = 0L;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private int mCurrentPos = -1;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, mDrawerLayout, AppUtils.getColor(R.color.colorPrimary));
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initVariables() {
        initFragment();
        //监听事件
        RxBus.getInstance().toFlowable(Event.StartNavigationEvent.class)
                .compose(bindToLifecycle())
                .subscribe(event -> {
                    if (event.start) {
                        toggleDrawer(); // 打开
                    }
                });
    }

    @Override
    public void initWidget() {
        disableNavigationViewScrollbars(mNavView); // 去掉滚动条
        mNavView.setNavigationItemSelectedListener(this);
        switchFragmentIndex(0);//初始化位置
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * 去掉导航视图 滚动条
     *
     * @param navigationView
     */
    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private void initFragment() {
        mFragments = Arrays.asList(HomeFragment.newInstance());
    }

    private void switchFragmentIndex(int pos) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentPos != -1) {
            transaction.hide(mFragments.get(mCurrentPos));
        }
        if (!mFragments.get(pos).isAdded()) {
            transaction.add(R.id.fl_content, mFragments.get(pos));
        }
        transaction.show(mFragments.get(pos)).commit();
        mCurrentPos = pos;
    }


    /**
     * Drawer 开关
     */
    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    /**
     * 监听back返回键处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1))) {
                mDrawerLayout.closeDrawers();
            } else {
                exitApp();
            }
        }
        return true;
    }

    /**
     * 双击退出App
     */
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.showToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            Event.ExitEvent event = new Event.ExitEvent();
            event.exit = -1;
            RxBus.getInstance().post(event);
        }
    }
}
