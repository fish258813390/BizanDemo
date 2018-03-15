package neil.com.bizandemo.adapter.home.section.region;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.widget.section.StatelessSection;
import neil.com.bizandemo.widget.section.ViewHolder;

/**
 * 分区 item 话题ViewHolder
 * Created by neil on 2018/3/15 0015.
 */
public class RegionTopicSection extends StatelessSection<Region.BodyBean> {

    private Region.BodyBean mBodyBean;

    public RegionTopicSection(Region.BodyBean data) {
        super(R.layout.layout_item_home_region_topic, R.layout.layout_empty);
        this.mBodyBean = data;
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        // 设置标题图片
        holder.setText(R.id.tv_title, "话题")
                .setImageResource(R.id.iv_icon, R.drawable.ic_header_topic);
        Glide.with(mContext)
                .load(mBodyBean.cover)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_video_preview));
    }
}
