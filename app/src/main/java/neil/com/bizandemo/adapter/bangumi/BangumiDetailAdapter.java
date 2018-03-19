package neil.com.bizandemo.adapter.bangumi;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.bangumi.BangumiDetail;
import neil.com.bizandemo.bean.bangumi.MulBangumiDetail;
import neil.com.bizandemo.utils.AppUtils;
import neil.com.bizandemo.utils.NumberUtils;
import neil.com.bizandemo.utils.SpanUtils;
import neil.com.bizandemo.utils.ToastUtils;
import neil.com.bizandemo.widget.flowlayout.FlowLayout;
import neil.com.bizandemo.widget.flowlayout.TagAdapter;
import neil.com.bizandemo.widget.flowlayout.TagFlowLayout;

/**
 * 番剧详情 适配器
 * 包含多个ViewHolder,可展示多个不同的子视图模块，需要设置不同的ViewType
 * ViewHolder对应的子布局也可以包含RecyclerView,所以存在RecyclerView 嵌套RecyclerView,甚至多个recycler嵌套的情况
 * Created by neil on 2018/3/15 0015.
 * Updated by neil on 2018/3/17 12:48
 */
public class BangumiDetailAdapter extends BaseMultiItemQuickAdapter<MulBangumiDetail, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BangumiDetailAdapter(List<MulBangumiDetail> data) {
        super(data);
        addItemType(MulBangumiDetail.TYPE_HEAD, R.layout.layout_item_bangumi_detail_info); // 头部信息
        addItemType(MulBangumiDetail.TYPE_SEASON, R.layout.layout_item_bangumi_detail_recycler); // 分季item
        addItemType(MulBangumiDetail.TYPE_EPISODE_HEAD, R.layout.layout_item_bangumi_detail_head);// 选集头部
        addItemType(MulBangumiDetail.TYPE_EPISODE_ITEM, R.layout.layout_item_bangumi_detail_recycler); // 选集(横向的recyclerview)
        addItemType(MulBangumiDetail.TYPE_CONTRACTED, R.layout.layout_bangumi_detail_contracted); // 承包
        addItemType(MulBangumiDetail.TYPE_DES, R.layout.layout_item_bangumi_detail_des); // 简介
        addItemType(MulBangumiDetail.TYPE_RECOMMEND_HEAD, R.layout.layout_item_bangumi_detail_head); // 推荐头部
        addItemType(MulBangumiDetail.TYPE_RECOMMEND_ITEM, R.layout.layout_item_bangumi_detail_recommend); // 推荐item
        addItemType(MulBangumiDetail.TYPE_COMMENT_HEAD, R.layout.layout_item_bangumi_detail_head); // 评论头部
        addItemType(MulBangumiDetail.TYPE_COMMENT_HOT_ITEM, R.layout.layout_item_bangumi_detail_comment); // 热门评论
        addItemType(MulBangumiDetail.TYPE_COMMENT_MORE, R.layout.layout_item_bangumi_detail_more); // 更多评论
        addItemType(MulBangumiDetail.TYPE_COMMENT_NOMAL_ITEM, R.layout.layout_item_bangumi_detail_comment); // 评论
    }

    /**
     * 根据不同的ViewHolder所对应的ItemType,展示不同的ViewHolder
     *
     * @param holder
     * @param mulBangumiDetail
     */
    @Override
    protected void convert(BaseViewHolder holder, MulBangumiDetail mulBangumiDetail) {
        switch (mulBangumiDetail.itemType) {
            case MulBangumiDetail.TYPE_HEAD: // 头部信息
                if (!mulBangumiDetail.isPrepare) {
                    holder.setText(R.id.tv_play, "播放:" + NumberUtils.format(mulBangumiDetail.playCount + ""))
                            .setText(R.id.tv_follow, "追番" + NumberUtils.format(mulBangumiDetail.favorites + ""))
                            .setText(R.id.tv_state, TextUtils.equals(mulBangumiDetail.isFinish, "0") ? "连载中" : "已完结");
                    Glide.with(mContext)
                            .load(mulBangumiDetail.cover)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .into((ImageView) holder.getView(R.id.iv_pic));
                    Glide.with(mContext)
                            .load(mulBangumiDetail.cover)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.bili_default_image_tv)
                            .bitmapTransform(new BlurTransformation(mContext, 26))
                            .dontAnimate()
                            .into((ImageView) holder.getView(R.id.iv_pic_big));
                }
                break;

            case MulBangumiDetail.TYPE_SEASON: // 分季
                RecyclerView recyclerSeason = holder.getView(R.id.recycler);
                recyclerSeason.setHasFixedSize(true);
//                recyclerSeason.setNestedScrollingEnabled(false);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerSeason.setLayoutManager(linearLayoutManager);
                recyclerSeason.setAdapter(new BangumiDetailSeasonAdapter(mulBangumiDetail.seasonsBeanList, mulBangumiDetail.seasonsTitle));
                break;

            case MulBangumiDetail.TYPE_EPISODE_HEAD: // 选集头部
                holder.setText(R.id.tv_title, "选集");
                if (TextUtils.equals(mulBangumiDetail.isFinish, "1")) {
                    holder.setText(R.id.tv_online, "一共 " + mulBangumiDetail.totalCount + " 话");
                } else {
                    holder.setText(R.id.tv_online, "更新至第 " + mulBangumiDetail.totalCount + " 话");
                }
                holder.getView(R.id.tv_online).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showCenterLongToast("选集点击更多");
                    }
                });
                break;

            case MulBangumiDetail.TYPE_EPISODE_ITEM: // 选集
                RecyclerView recyclerEpisode = holder.getView(R.id.recycler);
                recyclerEpisode.setHasFixedSize(true);
//                recyclerEpisode.setNestedScrollingEnabled(false);
                LinearLayoutManager layoutManagerEpisode = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerEpisode.setLayoutManager(layoutManagerEpisode);
                recyclerEpisode.setAdapter(new BangumiDetailEpisodeAdapter(mulBangumiDetail.episodesBeans));
                break;

            case MulBangumiDetail.TYPE_DES: // 简介
                holder.setText(R.id.tv_des, mulBangumiDetail.evaluate)
                        .setText(R.id.tv_title, "简介")
                        .setText(R.id.tv_online, "更多");
                TagFlowLayout tagFlowLayout = holder.getView(R.id.tags_layout);
                tagFlowLayout.setAdapter(new TagAdapter<BangumiDetail.TagsBean>(mulBangumiDetail.tagsBeanList) {
                    @Override
                    public View getView(FlowLayout flowLayout, int position, BangumiDetail.TagsBean listBean) {
                        TextView mTags = (TextView) LayoutInflater.from(mContext)
                                .inflate(R.layout.layout_hot_tags_item, flowLayout, false);
                        mTags.setText(listBean.tag_name);
                        return mTags;
                    }
                });
                holder.getView(R.id.tv_online).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showCenterLongToast("简介 更多~");
                    }
                });
                break;

            case MulBangumiDetail.TYPE_RECOMMEND_HEAD: // 推荐头部
                holder.setText(R.id.tv_title, "更多推荐")
                        .setText(R.id.tv_online, "换一换")
                        .setVisible(R.id.iv_trans, true)
                        .setVisible(R.id.iv_arrow, false);
                holder.getView(R.id.iv_trans).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showCenterLongToast("推荐换一换~");
                    }
                });
                break;

            case MulBangumiDetail.TYPE_RECOMMEND_ITEM: // 更多推荐 内容展示
                RecyclerView recyclerRecommend = holder.getView(R.id.recycler);
                recyclerRecommend.setHasFixedSize(true);
                recyclerRecommend.setNestedScrollingEnabled(false);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
                recyclerRecommend.setLayoutManager(gridLayoutManager);
                recyclerRecommend.setAdapter(new BangumiDetailRecommendAdapter(mulBangumiDetail.bangumiRecommendList.subList(0, 6)));
                break;

            case MulBangumiDetail.TYPE_COMMENT_HEAD: // 评论头部
                holder.setText(R.id.tv_title, new SpanUtils()
                        .append("评论  ")
                        .append("第")
                        .append(mulBangumiDetail.num + "")
                        .append("话")
                        .append("(" + mulBangumiDetail.account + ")").setForegroundColor(AppUtils.getColor(R.color.black_alpha_30))
                        .create())
                        .setText(R.id.tv_online, "选集");
                break;

        }
    }
}
