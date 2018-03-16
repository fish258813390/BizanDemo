package neil.com.bizandemo.ui.bangumi;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import neil.com.bizandemo.R;
import neil.com.bizandemo.adapter.bangumi.BangumiDetailAdapter;
import neil.com.bizandemo.base.BaseRefreshActivity;
import neil.com.bizandemo.bean.bangumi.MulBangumiDetail;
import neil.com.bizandemo.mvp.contract.bangumi.BangumiDetailContract;
import neil.com.bizandemo.mvp.presenter.bangumi.BangumiDetailPresenter;
import neil.com.bizandemo.utils.StatusBarUtil;

/**
 * 番剧详情页面
 * Created by neil on 2018/3/15 0015.
 */
public class BangumiDetailActivity extends BaseRefreshActivity<BangumiDetailPresenter, MulBangumiDetail>
        implements BangumiDetailContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private int mDistanceY;
    private BangumiDetailAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bangumi_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.setTitle("");//设置标题
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //滑动的距离
                mDistanceY += dy;
                //toolbar的高度
                int toolbarHeight = mToolbar.getBottom();
                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    mToolbar.setBackgroundColor(Color.argb((int) alpha, 251, 114, 153));
                } else {
                    //上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色
                    //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
                    //将标题栏的颜色设置为完全不透明状态
                    mToolbar.setBackgroundResource(R.color.colorPrimary);
                }

            }
        });
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new BangumiDetailAdapter(mList);
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        initHead();
        mAdapter.notifyDataSetChanged();
    }

    private void initHead() {
        mList.add(new MulBangumiDetail()
                .setItemType(MulBangumiDetail.TYPE_HEAD)
                .setPrepare(true));
    }

    @Override
    protected void loadData() {
        mPresenter.getBangumiDetailData();
    }

    @Override
    public void showMulBangumiDetail(List<MulBangumiDetail> mulBangumiDetails, String title) {
        mList.clear();
        mList.addAll(mulBangumiDetails);
        mTvTitle.setText(title);
        finishTask();
    }

    protected void finishTask() {
        mAdapter.notifyDataSetChanged();
    }

}
