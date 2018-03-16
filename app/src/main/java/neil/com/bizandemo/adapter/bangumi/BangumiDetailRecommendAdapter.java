package neil.com.bizandemo.adapter.bangumi;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.bangumi.BangumiDetailRecommend;
import neil.com.bizandemo.utils.NumberUtils;

/**
 * 更多推荐 adpater
 * Created by neil on 2018/3/16 0016.
 */
public class BangumiDetailRecommendAdapter extends BaseQuickAdapter<BangumiDetailRecommend.ListBean, BaseViewHolder> {


    public BangumiDetailRecommendAdapter(@Nullable List<BangumiDetailRecommend.ListBean> data) {
        super(R.layout.item_bangumi_detail_recommend, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BangumiDetailRecommend.ListBean listBean) {
        Glide.with(mContext)
                .load(listBean.cover)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_video_preview));
        holder.setText(R.id.tv_video_follow, NumberUtils.format(listBean.follow + "") + "追番")
                .setText(R.id.tv_video_title, listBean.title);
    }
}
