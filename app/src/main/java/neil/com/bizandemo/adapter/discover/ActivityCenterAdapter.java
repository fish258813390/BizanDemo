package neil.com.bizandemo.adapter.discover;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.discover.ActivityCenter;
import neil.com.bizandemo.utils.ToastUtils;

/**
 * 活动中心 适配器
 * Created by neil on 2018/3/17 0017.
 */
public class ActivityCenterAdapter extends BaseQuickAdapter<ActivityCenter.ListBean, BaseViewHolder> {

    public ActivityCenterAdapter(@Nullable List<ActivityCenter.ListBean> data) {
        super(R.layout.item_activity_center, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ActivityCenter.ListBean listBean) {
        Glide.with(mContext)
                .load(listBean.cover)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_preview));
        holder.setText(R.id.tv_title, listBean.title).
                setImageResource(R.id.iv_state, listBean.state == 1 ? R.drawable.ic_badge_end : R.drawable.ic_badge_going);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showCenterToast("点击活动~");
            }
        });
    }
}
