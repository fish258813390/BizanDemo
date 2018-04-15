package neil.com.bizandemo.ui.app.video;

import neil.com.bizandemo.R;
import neil.com.bizandemo.base.BaseFragment;

/**
 * 视频详情 评论 framgent
 * Created by neil on 2018/4/13 0013.
 */
public class CommentFragment extends BaseFragment {

    public static CommentFragment newInstance(){
        return new CommentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initWidget() {

    }
}
