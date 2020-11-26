package com.uos.mybilibili.module.adapter.home.section;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.mybilibili.R;
import com.uos.mybilibili.bean.live.LiveEnter;
import com.uos.mybilibili.module.adapter.home.LiveEntranceAdapter;
import com.uos.mybilibili.widget.section.StatelessSection;
import com.uos.mybilibili.widget.section.ViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * 描述:首页直播入口Section
 */
public class LiveEntranceSection extends StatelessSection {
    private List<LiveEnter> mList;

    public LiveEntranceSection() {
        super(R.layout.layout_item_home_live_entrance, R.layout.layout_empty);
        init();
    }

    private void init() {
        mList = Arrays.asList(
                new LiveEnter("关注", R.drawable.live_home_follow_anchor),
                new LiveEnter("中心", R.drawable.live_home_live_center),
                new LiveEnter("小视频", R.drawable.live_home_clip_video),
                new LiveEnter("搜索", R.drawable.live_home_search_room),
                new LiveEnter("分类", R.drawable.live_home_all_category));
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        RecyclerView recyclerView = holder.getView(R.id.recycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new LiveEntranceAdapter(mList));

    }


}
