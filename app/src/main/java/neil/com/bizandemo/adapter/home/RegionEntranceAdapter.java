package neil.com.bizandemo.adapter.home;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.region.RegionEnter;
import neil.com.bizandemo.bean.region.RegionTagType;

/**
 * @author neil
 * @date 2018/3/14
 * 分区 recyclerView 适配器
 */
public class RegionEntranceAdapter extends BaseQuickAdapter<RegionEnter, BaseViewHolder> {
    private List<RegionTagType> mRegionTypeList;

    public RegionEntranceAdapter(@Nullable List<RegionEnter> data, List<RegionTagType> regionTypeList) {
        super(R.layout.item_home_region_entrance, data);
        mRegionTypeList = regionTypeList;
    }

    /**
     * "直播",
     * "番剧",
     * "动画",
     * "国创",
     * "音乐",
     * "舞蹈",
     * "游戏",
     * "科技",
     * "生活",
     * "鬼畜",
     * "时尚",
     * "广告",
     * "娱乐",
     * "电影",
     * "电视剧",
     * "游戏中心"
     **/
    @Override
    protected void convert(BaseViewHolder helper, RegionEnter item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setImageResource(R.id.iv_icon, item.img);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
