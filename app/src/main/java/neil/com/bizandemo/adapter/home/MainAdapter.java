package neil.com.bizandemo.adapter.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import neil.com.bizandemo.R;
import neil.com.bizandemo.ui.home.ChaseBangumiFragment;
import neil.com.bizandemo.ui.home.DiscoverFragment;
import neil.com.bizandemo.ui.home.DynamicFragment;
import neil.com.bizandemo.ui.home.LiveFragment;
import neil.com.bizandemo.ui.home.RecommendFragment;
import neil.com.bizandemo.ui.home.RegionFragment;
import neil.com.bizandemo.utils.AppUtils;

/**
 * 主页tab标签 适配器
 *
 * @author neil
 * @date 2018/3/14
 */
public class MainAdapter extends FragmentPagerAdapter {
    private String[] mTitle;
    private Fragment[] mFragment;

    public MainAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    private void init() {
        mTitle = AppUtils.getStringArray(R.array.main_title);
        mFragment = new Fragment[mTitle.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (mFragment[position] == null) {
            switch (position) {
                case 0:
                    // 直播
                    mFragment[position] = LiveFragment.newInstance();
                    break;

                case 1:
                    //推荐
                    mFragment[position] = RecommendFragment.newInstance();
                    break;

                case 2:
                    //追番
                    mFragment[position] = ChaseBangumiFragment.newInstance();
                    break;

                case 3:
                    //分区
                    mFragment[position] = RegionFragment.newInstance();
                    break;

                case 4:
                    //动态
                    mFragment[position] = DynamicFragment.newInstance();
                    break;

                case 5:
                    //发现
                    mFragment[position] = DiscoverFragment.newInstance();
                    break;

                default:
                    break;
            }
        }
        return mFragment[position];
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
