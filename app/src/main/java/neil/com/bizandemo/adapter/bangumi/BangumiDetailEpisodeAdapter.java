package neil.com.bizandemo.adapter.bangumi;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import neil.com.bizandemo.R;
import neil.com.bizandemo.bean.bangumi.BangumiDetail;
import neil.com.bizandemo.utils.ToastUtils;

/**
 * 选集 适配器
 * Created by neil on 2018/3/16 0016.
 */

public class BangumiDetailEpisodeAdapter extends BaseQuickAdapter<BangumiDetail.EpisodesBean, BaseViewHolder> {

    private int mOldPos;
    private int mNewPos;

    public BangumiDetailEpisodeAdapter(@Nullable List<BangumiDetail.EpisodesBean> data) {
        super(R.layout.item_bangumi_detail_episodes, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BangumiDetail.EpisodesBean episodesBean) {
        holder.setText(R.id.tv_index, "第" + episodesBean.index + "话");
        holder.setText(R.id.tv_index_title, episodesBean.index_title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewPos = holder.getAdapterPosition(); // 新位置
                mOldPos = mNewPos;
                notifyDataSetChanged();
                // 点击跳转到播放页面
                ToastUtils.showCenterSingleLongToast("跳转到播放页面");
            }
        });
        if (holder.getAdapterPosition() == mNewPos) {
            holder.getView(R.id.tv_index_title).setEnabled(true);
            holder.getView(R.id.tv_index).setEnabled(true);
            holder.getView(R.id.ll_root).setEnabled(true);
        } else {
            holder.getView(R.id.tv_index_title).setEnabled(false);
            holder.getView(R.id.tv_index).setEnabled(false);
            holder.getView(R.id.ll_root).setEnabled(false);
        }
        if (mNewPos != mOldPos) {
            holder.getView(R.id.tv_index_title).setEnabled(false);
            holder.getView(R.id.tv_index).setEnabled(false);
            holder.getView(R.id.ll_root).setEnabled(false);
        }
        if (holder.getAdapterPosition() == getItemCount() - 1) {
            holder.setVisible(R.id.space, true);
        } else {
            holder.setVisible(R.id.space, false);
        }

    }
}
