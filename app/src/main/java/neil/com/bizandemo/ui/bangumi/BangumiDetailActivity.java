package neil.com.bizandemo.ui.bangumi;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import neil.com.bizandemo.R;
import neil.com.bizandemo.base.BaseRefreshActivity;
import neil.com.bizandemo.bean.bangumi.MulBangumiDetail;
import neil.com.bizandemo.mvp.contract.bangumi.BangumiDetailContract;
import neil.com.bizandemo.mvp.presenter.bangumi.BangumiDetailPresenter;

/**
 * 番剧详情页面
 * Created by neil on 2018/3/15 0015.
 */
public class BangumiDetailActivity extends BaseRefreshActivity<BangumiDetailPresenter, MulBangumiDetail>
        implements BangumiDetailContract.View {

    @BindView(R.id.tv_title)
    TextView mTvtitle;
    private int mDistanceY;


    @Override
    public void showMulBangumiDetail(List<MulBangumiDetail> mulBangumiDetails, String title) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bangumi_detail;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initVariables() {

    }
}
