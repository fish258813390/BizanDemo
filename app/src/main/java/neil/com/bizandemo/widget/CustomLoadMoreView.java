package neil.com.bizandemo.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

import neil.com.bizandemo.R;

/**
 * 自定义加载更多布局
 * 适用于recyclerview中,通过第三方适配器设置
 * Created by neil on 2018/3/17 0017.
 */
public class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.layout_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.ll_loading;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.ll_load_fail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.ll_load_end;
    }
}
