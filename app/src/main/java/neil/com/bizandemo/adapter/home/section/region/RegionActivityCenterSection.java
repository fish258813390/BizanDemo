package neil.com.bizandemo.adapter.home.section.region;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.adapter.home.RegionActivityCenterAdapter;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.widget.section.StatelessSection;
import neil.com.bizandemo.widget.section.ViewHolder;

/**
 * fragment 分区item 下的viewHolder
 * item 活动中心
 *
 * @author neil
 * @date 2018/3/14
 */
public class RegionActivityCenterSection extends StatelessSection {

    private List<Region.BodyBean> mList;

    public RegionActivityCenterSection(List<Region.BodyBean> list) {
        /**
         * 参数一：headerResourceId viewholder头部
         * 参数二：footerResourceId  viewholder底部
         * 参数三：itemResourceId viewholder布局
         */
        super(R.layout.layout_item_home_region_head, R.layout.layout_item_home_region_activity_center, R.layout.layout_empty);
        this.mList = list;
    }


    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        holder.setText(R.id.tv_title, "活动中心")
                .setImageResource(R.id.iv_icon, R.drawable.ic_header_activity_center);
    }

    @Override
    public void onBindFooterViewHolder(ViewHolder holder) {
        RecyclerView recyclerView = holder.getView(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RegionActivityCenterAdapter(mList));
    }
}
