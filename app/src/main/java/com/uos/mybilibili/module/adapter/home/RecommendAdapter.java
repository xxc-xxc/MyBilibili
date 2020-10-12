package com.uos.mybilibili.module.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.uos.mybilibili.R;
import com.uos.mybilibili.bean.recommend.MulRecommend;
import com.uos.mybilibili.bean.recommend.Recommend;
import com.uos.mybilibili.utils.NumberUtils;
import com.uos.mybilibili.utils.time.FormatUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Create By xxc
 * Date: 2020/10/10 16:44
 * Desc: 推荐Adapter 多类型
 */
public class RecommendAdapter extends BaseMultiItemQuickAdapter<MulRecommend, BaseViewHolder> {

    private Context mContext;

    public RecommendAdapter(Context context, @Nullable List<MulRecommend> data) {
        super(data);
        this.mContext = context;
        addItemType(MulRecommend.TYPE_HEADER, R.layout.item_recommend_banner);
        addItemType(MulRecommend.TYPE_ITEM, R.layout.item_home_recommend_body);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, MulRecommend mulRecommend) {
        switch (holder.getItemViewType()) {
            case MulRecommend.TYPE_HEADER:
                // banner类型
                Banner banner = holder.getView(R.id.recommend_banner);
                List<Recommend.BannerItemBean> bannerItemBeanList = mulRecommend.mBannerItemBean;
//                List<String> urls = StreamSupport.stream(bannerItemBeanList).map(bannerItem ->
//                        bannerItem.image).collect(Collectors.toList());
                banner.setAdapter(new BannerImageAdapter<Recommend.BannerItemBean>(bannerItemBeanList) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Recommend.BannerItemBean data,
                                           int position, int size) {
                        Glide.with(holder.itemView)
                                .load(data.image)
//                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                                .into(holder.imageView);
                    }
                }).addBannerLifecycleObserver((LifecycleOwner) mContext)
                        .setIndicator(new CircleIndicator(mContext));
//                banner.setOnBannerListener(i -> {
//                    Recommend.BannerItemBean bannerBean = banner_item.get(i);
//                    BrowserActivity.startActivity(mContext, bannerBean.uri, bannerBean.title, bannerBean.image);
//                });
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
                        .setText(R.id.tv_video_danmaku_num, NumberUtils.format(mulRecommend.mRecommend.danmaku + ""))
                        .setText(R.id.tv_video_title, mulRecommend.mRecommend.title);
                if (mulRecommend.mRecommend.open != 0) {
                    //直播
                    holder.setText(R.id.tv_video_tag, mulRecommend.mRecommend.area);
                } else {
                    //推荐
                    holder.setText(R.id.tv_video_tag, mulRecommend.mRecommend.tname + " · " + mulRecommend.mRecommend.tag.tag_name);
                }
//                holder.itemView.setOnClickListener(view ->
//                        mContext.startActivity(new Intent(mContext, VideoDetailActivity.class)));
                break;
        }
    }

}
