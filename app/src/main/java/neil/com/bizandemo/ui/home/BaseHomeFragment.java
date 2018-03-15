package neil.com.bizandemo.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import neil.com.bizandemo.R;
import neil.com.bizandemo.base.BaseFragment;

/**
 * 首页基础baseFragment
 *
 * @author neil
 * @date 2018/3/14
 */
public abstract class BaseHomeFragment extends BaseFragment {
    public Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // 设置支持menu
    }

    @Override
    protected void initWidget() {
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = ButterKnife.findById(mRootView, R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            mToolbar.inflateMenu(R.menu.menu_main);
        }
    }


}
