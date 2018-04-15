package neil.com.bizandemo.ui.region;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import neil.com.bizandemo.R;
import neil.com.bizandemo.base.BaseActivity;
import neil.com.bizandemo.base.BaseContract;

/**
 * 基础分区
 * tab 下 多个fragment
 * tabLayout + Viewpager 配合
 * Created by neil on 2018/4/13
 */
public abstract class BaseRegionActivity<T extends BaseContract.BasePresenter, K> extends BaseActivity<T> {
    protected TextView mTvTitle;
    protected ImageView mIvBack;
    protected List<K> mList = new ArrayList<>();
    protected List<String> mTitles = new ArrayList<>();
    protected List<Fragment> mFragments = new ArrayList<>();
    protected SlidingTabLayout mSlidingTabLayout;
    public ViewPager mViewPager;

    @Override
    protected void initToolbar() {
        mTvTitle = ButterKnife.findById(this, R.id.tv_title);
        mIvBack = ButterKnife.findById(this, R.id.iv_back);
        if (mIvBack != null) {
            mIvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void setTitle(String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
    }

    @Override
    public void initWidget() {
        initSlidingTabLayout();
    }

    protected void initSlidingTabLayout() {
        mSlidingTabLayout = ButterKnife.findById(this, R.id.sliding_tabs);
        mViewPager = ButterKnife.findById(this, R.id.view_pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_region, menu);
        return true;
    }

    protected void initTitle(){

    }

    protected void initFragment(){

    }

    /**
     * 初始化事件
     */
    protected void initEvent() {

    }

    /**
     * 完成请求
     */
    @Override
    protected void finishTask() {
        initTitle();
        initFragment();
        initViewPager();
        initEvent();
    }

    protected void initViewPager() {
        mViewPager.setOffscreenPageLimit(mTitles.size());
        mViewPager.setAdapter(new BaseRegionTypeAdapter(getSupportFragmentManager(), mTitles, mFragments));
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    protected void setCurrentItem(int pos){
        mViewPager.setCurrentItem(pos);
    }


    /**
     * Viewpager适配器
     */
    protected static class BaseRegionTypeAdapter extends FragmentPagerAdapter {

        private List<String> mTitless;
        private List<Fragment> mFragments;

        public BaseRegionTypeAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
            super(fm);
            this.mTitless = titles;
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mTitless.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitless.get(position);
        }
    }

}
