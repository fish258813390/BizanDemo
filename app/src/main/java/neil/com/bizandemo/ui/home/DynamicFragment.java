package neil.com.bizandemo.ui.home;

import neil.com.bizandemo.R;
import neil.com.bizandemo.base.BaseRefreshFragment;

/**
 * 动态
 * @author neil
 * @date 2018/3/14
 */
public class DynamicFragment extends BaseRefreshFragment {


    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_dynamic;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initWidget() {

    }
}
