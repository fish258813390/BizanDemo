package neil.com.bizandemo.adapter.app;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.app.video.MulSummary;
import neil.com.bizandemo.utils.NumberUtils;
import neil.com.bizandemo.utils.time.TimeUtils;
import neil.com.bizandemo.widget.flowlayout.FlowLayout;
import neil.com.bizandemo.widget.flowlayout.TagAdapter;
import neil.com.bizandemo.widget.flowlayout.TagFlowLayout;

/**
 * 视频简介适配器
 * 描述
 * 关注内容及标注
 * 视频推荐head
 * 视频item
 * Created by neil on 2018/4/13 0013.
 */
public class SummaryAdapter extends BaseMultiItemQuickAdapter<MulSummary, BaseViewHolder> {

    public SummaryAdapter(List<MulSummary> data) {
        super(data);
        addItemType(MulSummary.TYPE_DES, R.layout.layout_item_video_detail_summary_des); // 第一个type ： 描述
        addItemType(MulSummary.TYPE_OWNER, R.layout.layout_item_video_detail_summary_owner); // 第二个type ：关注及视频相关标签
        addItemType(MulSummary.TYPE_RELATE, R.layout.layout_item_video_detail_summary_relate); // 视频相关item
        addItemType(MulSummary.TYPE_RELATE_HEAD, R.layout.layout_item_video_detail_summary_relate_head); // 视频相关头部
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void convert(BaseViewHolder holder, MulSummary mulSummary) {
        switch (mulSummary.getItemType()) {
            case MulSummary.TYPE_DES:
                holder.setText(R.id.tv_title, mulSummary.title)
                        .setText(R.id.tv_video_play_num, NumberUtils.format(mulSummary.state.view + ""))
                        .setText(R.id.tv_video_danmaku, NumberUtils.format(mulSummary.state.danmaku + ""))
                        .setText(R.id.tv_share, NumberUtils.format(mulSummary.state.share + ""))
                        .setText(R.id.tv_coin, NumberUtils.format(mulSummary.state.coin + ""))
                        .setText(R.id.tv_favourite, NumberUtils.format(mulSummary.state.favorite + ""))
                        .setText(R.id.tv_down, "缓存")
                        .setText(R.id.tv_des, mulSummary.desc);
                break;

            case MulSummary.TYPE_OWNER:
                Glide.with(mContext)
                        .load(mulSummary.owner.face)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into((ImageView) holder.getView(R.id.iv_avatar));
//                holder.getView(R.id.iv_avatar).setOnClickListener(view ->
//                        mContext.startActivity(new Intent(mContext, UpDetailActivity.class))
//                );

                String date = TimeUtils.millis2String((long) (mulSummary.ctime * Math.pow(10, 3)));
                String[] split = date.split("-");
                holder.setText(R.id.tv_name, mulSummary.owner.name)
                        .setText(R.id.tv_time, split[0] + "年" + split[1] + "月" + (split[2].split(" "))[0] + "日" + "投递");

                TagFlowLayout tagsLayout = holder.getView(R.id.tags_layout);
                List<String> tag = new ArrayList<>();
                for (int i = 0; i < mulSummary.tags.size(); i++) {
                    tag.add(String.valueOf(mulSummary.tags.get(i)));
                }
                tagsLayout.setAdapter(new TagAdapter<String>(tag) {
                    @Override
                    public View getView(FlowLayout flowLayout, int i, String listBean) {
                        TextView mTags = (TextView) LayoutInflater.from(mContext)
                                .inflate(R.layout.layout_hot_tags_item, flowLayout, false);
                        mTags.setText(listBean);
                        //mTags.setOnClickListener(view -> TotalSearchActivity.startActivity(mContext, listBean.keyword));
                        return mTags;
                    }
                });
                break;
            case MulSummary.TYPE_RELATE_HEAD:

                break;
            case MulSummary.TYPE_RELATE:
                Glide.with(mContext)
                        .load(mulSummary.relates.pic)
                        .centerCrop()
                        .placeholder(R.drawable.bili_default_image_tv)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into((ImageView) holder.getView(R.id.iv_video_preview));
                holder.setText(R.id.tv_video_title, mulSummary.relates.title)
                        .setText(R.id.tv_video_up, mulSummary.relates.owner.name)
                        .setText(R.id.tv_video_play, NumberUtils.format(mulSummary.relates.stat.view + ""))
                        .setText(R.id.tv_video_danmaku, NumberUtils.format(mulSummary.relates.stat.danmaku + ""));
                break;
        }
    }
}
