package neil.com.bizandemo.ui.home;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.adapter.home.section.region.RegionActivityCenterSection;
import neil.com.bizandemo.adapter.home.section.region.RegionEntranceSection;
import neil.com.bizandemo.adapter.home.section.region.RegionSection;
import neil.com.bizandemo.adapter.home.section.region.RegionTopicSection;
import neil.com.bizandemo.base.BaseRefreshFragment;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.bean.region.RegionTagType;
import neil.com.bizandemo.mvp.contract.home.RegionContract;
import neil.com.bizandemo.mvp.presenter.home.RegionPresenter;
import neil.com.bizandemo.utils.LogUtils;
import neil.com.bizandemo.widget.section.SectionedRVAdapter;

/**
 * 分区fragment
 *
 * @author neil
 * @date 2018/3/14
 */
public class RegionFragment extends BaseRefreshFragment<RegionPresenter, Region> implements RegionContract.View {
    private static final String TAG = RegionFragment.class.getName();

    private SectionedRVAdapter mSectionedAdapter;
    private volatile List<RegionTagType> mRegionTypeList = new ArrayList<>();

    public static RegionFragment newInstance() {
        return new RegionFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_region;
    }

    @Override
    protected void initRecyclerView() {
        mSectionedAdapter = new SectionedRVAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRVAdapter.VIEW_TYPE_HEADER:
                        return 2;//2格
                    case SectionedRVAdapter.VIEW_TYPE_FOOTER:
                        return 2;//2格
                    default:
                        return 1;
                }
            }
        });
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mSectionedAdapter);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initWidget() {

    }

    @Override
    protected void lazyLoadData() {
        mPresenter.getRegionData();
    }

    @Override
    public void showRegion(List<Region> regions) {
        mList.addAll(regions);
        finishTask();
    }

    @Override
    public void showRegionType(List<RegionTagType> regionTypes) {
        mRegionTypeList.addAll(regionTypes);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void finishTask() {
        mSectionedAdapter.addSection(new RegionEntranceSection(mRegionTypeList));
        LogUtils.d(TAG, "mRegionTypeList---->" + new Gson().toJson(mRegionTypeList));
        LogUtils.d(TAG, "--------------------------------------------------------------");
        for (Region region : mList) {
            String type = region.type;
            LogUtils.d(TAG, "region---->" + type);
            if ("topic".equals(type)) { //话题
                mSectionedAdapter.addSection(new RegionTopicSection(region.body.get(0)));
            } else if ("activity".equals(type)) {//活动中心
                mSectionedAdapter.addSection(new RegionActivityCenterSection(region.body));
            } else { //分区和番剧区
                mSectionedAdapter.addSection(new RegionSection(region.title, region.body));
            }
        }
        mSectionedAdapter.notifyDataSetChanged();
    }
}
