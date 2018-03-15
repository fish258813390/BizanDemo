package neil.com.bizandemo.ui.home;

import neil.com.bizandemo.R;
import neil.com.bizandemo.base.BaseRefreshFragment;

/**
 * 直播
 * @author neil
 * @date 2018/3/14
 */
public class LiveFragment extends BaseRefreshFragment {

    public static LiveFragment newInstance() {
        return new LiveFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_live;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initWidget() {

    }
}
