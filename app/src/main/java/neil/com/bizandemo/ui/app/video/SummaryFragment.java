package neil.com.bizandemo.ui.app.video;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neil.com.bizandemo.R;
import neil.com.bizandemo.adapter.app.SummaryAdapter;
import neil.com.bizandemo.base.BaseFragment;
import neil.com.bizandemo.bean.app.video.MulSummary;
import neil.com.bizandemo.mvp.contract.app.video.SummaryContract;
import neil.com.bizandemo.mvp.presenter.app.video.SummaryPresenter;

/**
 * 视频简介fragment
 * Created by neil on 2018/4/13 0013.
 */
public class SummaryFragment extends BaseFragment<SummaryPresenter> implements SummaryContract.View {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private List<MulSummary> mList = new ArrayList<>();
    private SummaryAdapter mAdapter;

    public static SummaryFragment newInstance(){
        return new SummaryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_summary;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void loadData() {
        mPresenter.getSummaryData();
    }

    @Override
    protected void initWidget() {
        mAdapter = new SummaryAdapter(mList);
        mRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void showSummary(List<MulSummary> mulSummaryList) {
        mList.addAll(mulSummaryList);
//        mAdapter.notifyDataSetChanged();
    }

}
