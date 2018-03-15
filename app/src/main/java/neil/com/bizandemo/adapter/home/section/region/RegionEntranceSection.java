package neil.com.bizandemo.adapter.home.section.region;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.adapter.home.RegionEntranceAdapter;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.bean.region.RegionEnter;
import neil.com.bizandemo.bean.region.RegionTagType;
import neil.com.bizandemo.widget.section.StatelessSection;
import neil.com.bizandemo.widget.section.ViewHolder;

/**
 * 地区header
 * 其实是recyclerview的一个header
 *
 * @author neil
 * @date 2018/3/14
 */
public class RegionEntranceSection extends StatelessSection {

    private List<RegionEnter> mList;
    private List<RegionTagType> mRegionTypeList;

    public RegionEntranceSection(List<RegionTagType> regionTypeList) {
        // headerResourceId | itemResourceId
        super(R.layout.layout_item_home_region_entrance, R.layout.layout_empty);
        this.mRegionTypeList = regionTypeList;
        init();
    }

    private void init() {
        mList = Arrays.asList(
                new RegionEnter("直播", R.mipmap.ic_category_live),
                new RegionEnter("番剧", R.mipmap.ic_category_t13),
                new RegionEnter("动画", R.mipmap.ic_category_t1),
                new RegionEnter("国创", R.mipmap.ic_category_t167),
                new RegionEnter("音乐", R.mipmap.ic_category_t3),
                new RegionEnter("舞蹈", R.mipmap.ic_category_t129),
                new RegionEnter("游戏", R.mipmap.ic_category_t4),
                new RegionEnter("科技", R.mipmap.ic_category_t36),
                new RegionEnter("生活", R.mipmap.ic_category_t160),
                new RegionEnter("鬼畜", R.mipmap.ic_category_t11),
                new RegionEnter("时尚", R.mipmap.ic_category_t155),
                new RegionEnter("广告", R.mipmap.ic_category_t165),
                new RegionEnter("娱乐", R.mipmap.ic_category_t5),
                new RegionEnter("电影", R.mipmap.ic_category_t23),
                new RegionEnter("电视剧", R.mipmap.ic_category_t11),
                new RegionEnter("游戏中心", R.mipmap.ic_category_game_center));
    }

    /**
     * 绑定viewholder
     *
     * @param holder ViewHolder for the Header of this Section
     */
    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        RecyclerView recyclerView = holder.getView(R.id.recycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RegionEntranceAdapter(mList, mRegionTypeList));
    }

}
