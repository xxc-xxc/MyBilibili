package com.uos.mybilibili.module.adapter.home.section;

import androidx.lifecycle.LifecycleOwner;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.uos.mybilibili.R;
import com.uos.mybilibili.bean.live.LivePartition;
import com.uos.mybilibili.widget.section.StatelessSection;
import com.uos.mybilibili.widget.section.ViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

/**
 * 描述:首页直播轮播条
 */
public class LiveBannerSection extends StatelessSection {

    private List<LivePartition.BannerBean> mList;

    public LiveBannerSection(List<LivePartition.BannerBean> list) {
        super(R.layout.layout_banner, R.layout.layout_empty);
        mList = list;
    }


    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
//        Banner banner = holder.getView(R.id.banner);
//        List<String> urls = Stream.of(mList).map(bannerBean -> bannerBean.img).collect(Collectors.toList());
//        banner.setIndicatorGravity(BannerConfig.RIGHT)
//                .setImages(urls)
//                .setImageLoader(new GlideImageLoader())
//                .start();

        Banner banner = holder.getView(R.id.banner);
        List<String> urls = Stream.of(mList).map(bannerBean -> bannerBean.img).collect(Collectors.toList());
        banner.setAdapter(new BannerImageAdapter<String>(urls) {
            @Override
            public void onBindView(BannerImageHolder holder, String url,
                                   int position, int size) {
                Glide.with(holder.itemView)
                        .load(url)
//                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                        .into(holder.imageView);
            }
        }).addBannerLifecycleObserver((LifecycleOwner) mContext)
                .setIndicator(new CircleIndicator(mContext));
//        banner.setOnBannerListener(i -> {
//            LivePartition.BannerBean bannerBean = mList.get(i);
//            BrowserActivity.startActivity(mContext,bannerBean.link,bannerBean.title,bannerBean.img);
//        });

    }

//    static class GlideImageLoader extends ImageLoader {
//        @Override
//        public void displayImage(Context context, Object path, ImageView imageView) {
//            Glide.with(context)
//                    .load((String) path)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .dontAnimate()
//                    .into(imageView);
//        }
//    }
}
