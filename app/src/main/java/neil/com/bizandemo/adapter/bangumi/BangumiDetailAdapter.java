package neil.com.bizandemo.adapter.bangumi;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.bangumi.MulBangumiDetail;

/**
 * Created by neil on 2018/3/15 0015.
 */

public class BangumiDetailAdapter extends BaseMultiItemQuickAdapter<MulBangumiDetail,BaseViewHolder> {


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
//        addItemType(MulBangumiDetail.TYPE_COMMENT_HOT_ITEM, R.layout.layout_item_bangumi_detail_comment); // 热门评论
//        addItemType(MulBangumiDetail.TYPE_COMMENT_MORE, R.layout.layout_item_bangumi_detail_more); // 更多推荐
//        addItemType(MulBangumiDetail.TYPE_COMMENT_NOMAL_ITEM, R.layout.layout_item_bangumi_detail_comment); // 评论

    }

    @Override
    protected void convert(BaseViewHolder helper, MulBangumiDetail item) {

    }
}
