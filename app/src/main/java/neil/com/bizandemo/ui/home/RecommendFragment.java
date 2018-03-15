package neil.com.bizandemo.ui.home;

import neil.com.bizandemo.R;
import neil.com.bizandemo.base.BaseRefreshFragment;

/**
 * 推荐
 * @author neil
 * @date 2018/3/14
 */
public class RecommendFragment extends BaseRefreshFragment {

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initWidget() {

    }
}
