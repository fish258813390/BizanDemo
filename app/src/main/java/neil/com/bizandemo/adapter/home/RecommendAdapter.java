package neil.com.bizandemo.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.recommend.MulRecommend;
import neil.com.bizandemo.bean.recommend.Recommend;
import neil.com.bizandemo.ui.app.video.VideoDetailActivity;
import neil.com.bizandemo.utils.NumberUtils;
import neil.com.bizandemo.utils.ToastUtils;
import neil.com.bizandemo.utils.time.FormatUtils;

/**
 * 推荐tag RecyclerView 适配器
 * Created by neil on 2018/4/13 0013.
 */
public class RecommendAdapter extends BaseMultiItemQuickAdapter<MulRecommend, BaseViewHolder> {

    public RecommendAdapter(List<MulRecommend> data) {
        super(data);
        addItemType(MulRecommend.TYPR_HEADER, R.layout.layout_recommend_banner);
        addItemType(MulRecommend.TYPE_ITEM, R.layout.layout_item_home_recommend_body);
    }

    @Override
    protected void convert(BaseViewHolder holder, MulRecommend mulRecommend) {
        switch (holder.getItemViewType()) {
            case MulRecommend.TYPR_HEADER:
                Banner banner = holder.getView(R.id.banner);
                List<Recommend.BannerItemBean> mBannerItemBean = mulRecommend.mBannerItemBean;
                ArrayList<String> urls = new ArrayList<>();
                for (int i = 0; i < mBannerItemBean.size(); i++) {
                    Recommend.BannerItemBean bannerItemBean = mBannerItemBean.get(i);
                    urls.add(bannerItemBean.image);
                }
                banner.setIndicatorGravity(BannerConfig.RIGHT)
                        .setImages(urls)
                        .setImageLoader(new GlideImageLoader())
                        .start();
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        ToastUtils.showCenterToast("点击--" + (position + 1));
                    }
                });
                break;

            case MulRecommend.TYPE_ITEM:
                Glide.with(mContext)
                        .load(mulRecommend.mRecommend.cover)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.bili_default_image_tv)
                        .dontAnimate()
                        .into((ImageView) holder.getView(R.id.iv_video_preview));
                holder.setText(R.id.tv_video_play_num, NumberUtils.format(mulRecommend.mRecommend.play + ""))
                        .setText(R.id.tv_video_time, FormatUtils.formatDuration(mulRecommend.mRecommend.duration + ""))
                        .setText(R.id.tv_video_danmaku, NumberUtils.format(mulRecommend.mRecommend.danmaku + ""))
                        .setText(R.id.tv_video_title, mulRecommend.mRecommend.title);
                if (mulRecommend.mRecommend.open != 0) {
                    // 直播
                    holder.setText(R.id.tv_video_tag, mulRecommend.mRecommend.area);
                } else {
                    // 推荐
                    holder.setText(R.id.tv_video_tag, mulRecommend.mRecommend.tname + " · " + mulRecommend.mRecommend.tag.tag_name);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, VideoDetailActivity.class));
                    }
                });
                break;
        }
    }

    private static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(imageView);
        }
    }
}
