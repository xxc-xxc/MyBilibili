package com.uos.mybilibili.module.adapter.home.section;

import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.uos.mybilibili.R;
import com.uos.mybilibili.bean.live.LivePartition;
import com.uos.mybilibili.utils.AppUtils;
import com.uos.mybilibili.utils.NumberUtils;
import com.uos.mybilibili.utils.SpanUtils;
import com.uos.mybilibili.widget.section.StatelessSection;
import com.uos.mybilibili.widget.section.ViewHolder;

import java.util.List;
import java.util.Random;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/5/21 11:57
 * 描述:首页直播分区ection
 */
public class LiveRecommendPartitionSection extends StatelessSection<LivePartition.PartitionsBean.LivesBean> {
    private String mUrl;
    private String mTitle;
    private Random mRandom;
    private String mCount;
    private boolean mhasMore = false;

    public LiveRecommendPartitionSection(String title, String url, String count,
                                         List<LivePartition.PartitionsBean.LivesBean> data) {
        super(R.layout.layout_item_home_live_head, R.layout.layout_item_home_live_footer, R.layout.layout_item_home_live_body, data);
        this.mUrl = url;
        this.mTitle = title;
        this.mCount = count;
        this.mRandom = new Random();
    }

    public LiveRecommendPartitionSection(boolean hasMore, String title, String url, String count,
                                         List<LivePartition.PartitionsBean.LivesBean> data) {
        this(title, url, count, data);
        this.mhasMore = hasMore;
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        Glide.with(mContext)
                .load(mUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_icon));
        holder.setText(R.id.tv_title, mTitle);
        holder.setText(R.id.tv_online, new SpanUtils()
                .append("当前")
                .append("" + mCount)
                .setForegroundColor(AppUtils.getColor(R.color.pink_text_color))
                .append("个直播")
                .create());


    }

    @Override
    public void convert(ViewHolder holder, LivePartition.PartitionsBean.LivesBean livesBean, int position) {
        Glide.with(mContext)
                .load(livesBean.cover.src)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_video_preview));
        holder.setText(R.id.tv_video_live_up, livesBean.owner.name)//up
                .setText(R.id.tv_video_online, NumberUtils.format(livesBean.online + ""));//在线人数;
        holder.setText(R.id.tv_video_title, livesBean.title);
        if (position % 2 == 0) {
            setMargins(holder.itemView, (int) AppUtils.getDimension(R.dimen.dp_10),
                    (int) AppUtils.getDimension(R.dimen.dp_5),
                    (int) AppUtils.getDimension(R.dimen.dp_5),
                    (int) AppUtils.getDimension(R.dimen.dp_5));
        } else {
            setMargins(holder.itemView, (int) AppUtils.getDimension(R.dimen.dp_5),
                    (int) AppUtils.getDimension(R.dimen.dp_5),
                    (int) AppUtils.getDimension(R.dimen.dp_10),
                    (int) AppUtils.getDimension(R.dimen.dp_5));
        }
    }

    @Override
    public void onBindFooterViewHolder(ViewHolder holder) {
        if (mhasMore) {
            holder.setVisible(R.id.bt_more_live, true);
        } else {
            holder.setVisible(R.id.bt_more_live, false);
            holder.getView(R.id.bt_more_live).setOnClickListener(view -> {

            });

        }
        holder.setText(R.id.tv_dynamic, String.valueOf(mRandom.nextInt(200) + "条新动态，点击这里刷新"));
        holder.getView(R.id.iv_refresh).setOnClickListener(view ->
                view.animate()
                        .rotation(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000).start());
    }
}
