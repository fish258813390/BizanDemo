package neil.com.bizandemo.bean.recommend;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 推荐tag下的 多布局
 * Created by neil on 2018/4/13 0013.
 */
public class MulRecommend implements MultiItemEntity {

    public static final int TYPR_HEADER = 1;
    public static final int TYPE_ITEM = 2;
    public static final int HEADER_SPAN_SIZE = 2;// 占2
    public static final int ITEM_SPAN_SIZE = 1;// 占1
    public int itemType;
    public int spanSize;
    public Recommend mRecommend;
    public List<Recommend.BannerItemBean> mBannerItemBean;

    public MulRecommend(int itemType, int spanSize, Recommend recommend) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.mRecommend = recommend;
    }

    public MulRecommend(int itemType, int spanSize, List<Recommend.BannerItemBean> bannerItemBeans) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.mBannerItemBean = bannerItemBeans;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
