package neil.com.bizandemo.ui.home;

import android.view.Menu;
import android.view.MenuInflater;

import com.flyco.tablayout.SlidingTabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.OnClick;
import neil.com.bizandemo.R;
import neil.com.bizandemo.adapter.home.MainAdapter;
import neil.com.bizandemo.event.Event;
import neil.com.bizandemo.rx.RxBus;
import neil.com.bizandemo.widget.NoScrollViewPager;

/**
 * @author neil
 * @date 2018/3/14
 */

public class HomeFragment extends BaseHomeFragment {

    @BindView(R.id.stl_tabs)
    SlidingTabLayout mStlTabs;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initViewPager();
    }

    private void initViewPager() {
        MainAdapter adapter = new MainAdapter(getChildFragmentManager());
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(adapter);
        mStlTabs.setViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
    }

    @OnClick(R.id.ll_navigation)
    public void onClick(){
        Event.StartNavigationEvent event = new Event.StartNavigationEvent();
        event.start = true;
        RxBus.getInstance().post(event);
    }

}
