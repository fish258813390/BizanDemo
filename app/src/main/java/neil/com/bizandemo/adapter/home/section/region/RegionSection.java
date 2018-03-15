package neil.com.bizandemo.adapter.home.section.region;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.Random;

import neil.com.bizandemo.AppApplication;
import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.region.Region;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.utils.NumberUtils;
import neil.com.bizandemo.utils.ToastUtils;
import neil.com.bizandemo.widget.section.StatelessSection;
import neil.com.bizandemo.widget.section.ViewHolder;

/**
 * 地区 item
 * 也是recyclerview的一个Viewholder, Section的委托类
 * <p>
 * Created by neil on 2018/3/15 0015.
 */
public class RegionSection extends StatelessSection<Region.BodyBean> {

    private String mTitle;
    private Random mRandom;

    public RegionSection(String title, List<Region.BodyBean> data) {
        super(R.layout.layout_item_home_region_head, R.layout.layout_item_home_region_footer, R.layout.layout_item_home_region_body, data);
        this.mTitle = title;
        this.mRandom = new Random();
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        // 设置图片和标题
        setTypeIcon(holder, mTitle);
        holder.setText(R.id.tv_title, mTitle);
        holder.getView(R.id.tv_look_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showCenterLongToast("更多");
            }
        });
    }

    /**
     * 设置内容item
     */
    @Override
    public void convert(ViewHolder holder, Region.BodyBean bodyBean, int position) {
        Glide.with(mContext)
                .load(bodyBean.cover)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_video_preview));
        holder.setText(R.id.tv_video_title, bodyBean.title); // titile
        holder.setText(R.id.tv_video_play_num, NumberUtils.format(bodyBean.play + "")); // 播放量
        if (TextUtils.equals("番剧区", mTitle)) {
            holder.setVisible(R.id.iv_video_online_region, false)
                    .setVisible(R.id.iv_video_online, true)
                    .setText(R.id.tv_video_favourite, NumberUtils.format(bodyBean.favourite + ""));
        } else {
            holder.setVisible(R.id.iv_video_online_region, true)
                    .setVisible(R.id.iv_video_online, false)
                    .setText(R.id.tv_video_favourite, NumberUtils.format(bodyBean.danmaku + ""));
        }
        if (position % 2 == 0) {
            setMargins(holder.itemView, (int) AppUtils.getDimension(R.dimen.dp10),
                    (int) AppUtils.getDimension(R.dimen.dp5),
                    (int) AppUtils.getDimension(R.dimen.dp5),
                    (int) AppUtils.getDimension(R.dimen.dp5));
        } else {
            setMargins(holder.itemView, (int) AppUtils.getDimension(R.dimen.dp5),
                    (int) AppUtils.getDimension(R.dimen.dp5),
                    (int) AppUtils.getDimension(R.dimen.dp10),
                    (int) AppUtils.getDimension(R.dimen.dp5));
        }
        holder.itemView.setOnClickListener(view ->
                ToastUtils.showCenterSingleLongToast("点击--->" + position)
        );
    }

    /**
     * 底部布局内容
     *
     * @param holder ViewHolder for the Footer of this Section
     */
    @Override
    public void onBindFooterViewHolder(ViewHolder holder) {
        setButtonMore(holder, mTitle);
        if (TextUtils.equals("游戏区", mTitle)) {
            holder.setVisible(R.id.bt_more, false)
                    .setVisible(R.id.bt_more_game, true)
                    .setVisible(R.id.bt_game_center, true);
            //跳转到游戏中心
            holder.getView(R.id.bt_game_center)
                    .setOnClickListener(view ->
                            ToastUtils.showCenterSingleLongToast("点击--->跳转到游戏详情"));
        } else {
            holder.setVisible(R.id.bt_more, true)
                    .setVisible(R.id.bt_more_game, false)
                    .setVisible(R.id.bt_game_center, false);
        }
        holder.setText(R.id.tv_dynamic, String.valueOf(mRandom.nextInt(200) + "条新动态，点击这里刷新"));
        holder.getView(R.id.iv_refresh).setOnClickListener(view ->
                view.animate()
                        .rotation(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000).start());
        holder.getView(R.id.iv_refresh).setOnClickListener(view ->
                view.animate()
                        .rotation(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000).start());
    }

    /**
     * 设置图片和标题
     */
    private void setTypeIcon(ViewHolder holder, String title) {
        switch (title) {
            case "番剧区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t13);
                break;
            case "动画区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t1);
                break;
            case "国创区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t167);
                break;
            case "音乐区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t3);
                break;
            case "舞蹈区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t129);
                break;
            case "游戏区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t4);
                break;
            case "科技区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t36);
                break;
            case "生活区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t160);
                break;
            case "鬼畜区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t13);
                break;
            case "时尚区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t155);
                break;
            case "广告区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t165);
                break;
            case "娱乐区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t5);
                break;
            case "电影区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t23);
                break;
            case "电视剧区":
                holder.setImageResource(R.id.iv_icon, R.mipmap.ic_category_t11);
                break;
        }
    }

    /**
     * 设置底部footer 显示内容
     *
     * @param holder
     * @param title
     */
    private void setButtonMore(ViewHolder holder, String title) {
        switch (title) {
            case "番剧区":
                holder.setText(R.id.bt_more, "更多番剧");
                break;
            case "动画区":
                holder.setText(R.id.bt_more, "更多动画");
                break;
            case "国创区":
                holder.setText(R.id.bt_more, "更多国创");
                break;
            case "音乐区":
                holder.setText(R.id.bt_more, "更多音乐");
                break;
            case "舞蹈区":
                holder.setText(R.id.bt_more, "更多舞蹈");
                break;
            case "游戏区":
                holder.setText(R.id.bt_more_game, "更多游戏");
                break;
            case "科技区":
                holder.setText(R.id.bt_more, "更多科技");
                break;
            case "生活区":
                holder.setText(R.id.bt_more, "更多生活");
                break;
            case "鬼畜区":
                holder.setText(R.id.bt_more, "更多鬼畜");
                break;
            case "时尚区":
                holder.setText(R.id.bt_more, "更多时尚");
                break;
            case "广告区":
                holder.setText(R.id.bt_more, "更多广告");
                break;
            case "娱乐区":
                holder.setText(R.id.bt_more, "更多娱乐");
                break;
            case "电影区":
                holder.setText(R.id.bt_more, "更多电影");
                break;
            case "电视剧区":
                holder.setText(R.id.bt_more, "更多电视剧");
                break;
        }
    }
}
