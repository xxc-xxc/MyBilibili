package com.uos.mybilibili.module.adapter.home.section;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.uos.mybilibili.R;
import com.uos.mybilibili.bean.live.LiveRecommend;
import com.uos.mybilibili.utils.AppUtils;
import com.uos.mybilibili.utils.NumberUtils;
import com.uos.mybilibili.utils.SpanUtils;
import com.uos.mybilibili.utils.ToastUtils;
import com.uos.mybilibili.widget.section.StatelessSection;
import com.uos.mybilibili.widget.section.ViewHolder;

import java.util.List;
import java.util.Random;

/**
 * 描述:首页直播推荐主播section
 */
public class LiveRecommendSection extends StatelessSection<LiveRecommend.RecommendDataBean.LivesBean> {
    private boolean mHasHead;
    private boolean mHasFooter;
    private String mUrl;
    private String mTitle;
    private Random mRandom;
    private String mCount;
    private LiveRecommend.RecommendDataBean.BannerDataBean mBannerDataBean;

    public LiveRecommendSection(boolean hasHead, boolean hasFooter, String title, String url, String count,
                                List<LiveRecommend.RecommendDataBean.LivesBean> data) {
        super(R.layout.layout_item_home_live_head, R.layout.layout_item_home_live_footer, R.layout.layout_item_home_live_body, data);
        this.mHasFooter = hasFooter;
        this.mHasHead = hasHead;
        this.mUrl = url;
        this.mTitle = title;
        this.mCount = count;
        this.mRandom = new Random();
    }

    public LiveRecommendSection(boolean hasHead, boolean hasFooter, String title, String url, String count,
                                List<LiveRecommend.RecommendDataBean.LivesBean> data, LiveRecommend.RecommendDataBean.BannerDataBean bannerDataBean) {

        this(hasHead, hasFooter, title, url, count, data);
        this.mBannerDataBean = bannerDataBean;
    }


    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        if (mHasHead) {
            holder.setVisible(R.id.cl_type_root, true);
            Glide.with(mContext)
                    .load(mUrl)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into((ImageView) holder.getView(R.id.iv_icon));
            holder.setText(R.id.tv_title, mTitle);
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder("当前" + mCount + "个直播");
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                    mContext.getResources().getColor(R.color.pink_text_color));
            stringBuilder.setSpan(foregroundColorSpan, 2,
                    stringBuilder.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.setText(R.id.tv_online, stringBuilder);

            if (mBannerDataBean != null) {
                holder.setVisible(R.id.cl_video_root, true);
                Glide.with(mContext)
                        .load(mBannerDataBean.cover.src)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into((ImageView) holder.getView(R.id.iv_video_preview));
                holder.setText(R.id.tv_video_live_up, mBannerDataBean.owner.name)//up
                        .setText(R.id.tv_video_online, mBannerDataBean.online + "");//在线人数;
                holder.setText(R.id.tv_video_title, new SpanUtils()
                        .append("#" + mBannerDataBean.area + "#")
                        .setForegroundColor(AppUtils.getColor(R.color.pink_text_color))
                        .append(mBannerDataBean.title)
                        .create());
            } else {
                holder.setVisible(R.id.card_view, false);
            }
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
            holder.itemView.setLayoutParams(params);
        }
        holder.itemView.setOnClickListener(v -> {
            ToastUtils.showSingleLongToast(holder.getAdapterPosition() + "");
        });

    }

    @Override
    public void convert(ViewHolder holder, LiveRecommend.RecommendDataBean.LivesBean livesBean, int position) {
        Glide.with(mContext)
                .load(livesBean.cover.src)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_video_preview));
        holder.setText(R.id.tv_video_live_up, livesBean.owner.name)//up
                .setText(R.id.tv_video_online, NumberUtils.format(livesBean.online + ""));//在线人数;
        holder.setText(R.id.tv_video_title, new SpanUtils()
                .append("#" + livesBean.area + "#")
                .setForegroundColor(AppUtils.getColor(R.color.pink_text_color))
                .append(livesBean.title).create());
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
        if (mHasFooter) {
            holder.setVisible(R.id.bt_more, true)
                    .setText(R.id.tv_dynamic, String.valueOf(mRandom.nextInt(200) + "条新动态，点击这里刷新"));
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
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
            holder.itemView.setLayoutParams(params);
        }
        holder.itemView.setOnClickListener(v -> {
            ToastUtils.showSingleLongToast(holder.getAdapterPosition() + "");

        });
    }
}
