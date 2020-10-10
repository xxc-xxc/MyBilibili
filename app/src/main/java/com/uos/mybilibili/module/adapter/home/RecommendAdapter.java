package com.uos.mybilibili.module.adapter.home;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.uos.mybilibili.R;
import com.uos.mybilibili.bean.recommend.MulRecommend;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Create By xxc
 * Date: 2020/10/10 16:44
 * Desc: 推荐Adapter 多类型
 */
public class RecommendAdapter extends BaseMultiItemQuickAdapter<MulRecommend, BaseViewHolder> {

    public RecommendAdapter(@Nullable List<MulRecommend> data) {
        super(data);
        addItemType(MulRecommend.TYPE_HEADER, R.layout.item_recommend_banner);
        addItemType(MulRecommend.TYPE_ITEM, R.layout.item_home_recommend_body);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MulRecommend mulRecommend) {

    }
}
