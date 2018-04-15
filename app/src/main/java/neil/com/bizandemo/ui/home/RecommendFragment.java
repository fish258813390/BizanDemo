package neil.com.bizandemo.ui.home;

import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.adapter.home.RecommendAdapter;
import neil.com.bizandemo.base.BaseRefreshFragment;
import neil.com.bizandemo.bean.recommend.MulRecommend;
import neil.com.bizandemo.bean.recommend.Recommend;
import neil.com.bizandemo.mvp.contract.home.RecommendContract;
import neil.com.bizandemo.mvp.presenter.recommend.RecommendPresenter;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.widget.divider.VerticalDividerItemDecoration;

/**
 * 推荐页
 *
 * @author neil
 * @date 2018/3/14
 */
public class RecommendFragment extends BaseRefreshFragment<RecommendPresenter, MulRecommend>
        implements RecommendContract.View {

    private RecommendAdapter mAdapter;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initWidget() {

    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new RecommendAdapter(mList);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return mList.get(position).spanSize;
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        // 添加分割条
        VerticalDividerItemDecoration build = new VerticalDividerItemDecoration.Builder(getActivity())
                .color(AppUtils.getColor(R.color.transparent))
                .sizeResId(R.dimen.dp10)
                .showLastDivider()
                .build();
        mRecycler.addItemDecoration(build);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getRecommendData();
    }

    @Override
    public void showRecommend(List<Recommend> mDatas) {
        for (Recommend recommend : mDatas) {
            if (null != recommend.banner_item) {
                mList.add(new MulRecommend(MulRecommend.TYPR_HEADER, MulRecommend.HEADER_SPAN_SIZE, recommend.banner_item));
            } else {
                mList.add(new MulRecommend(MulRecommend.TYPE_ITEM, MulRecommend.ITEM_SPAN_SIZE, recommend));
            }
        }
    }
}
