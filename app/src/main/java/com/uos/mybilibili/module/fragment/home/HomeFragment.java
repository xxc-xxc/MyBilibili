package com.uos.mybilibili.module.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.uos.mybilibili.R;
import com.uos.mybilibili.utils.Event;
import com.uos.mybilibili.utils.RxBus;
import com.uos.mybilibili.widget.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Create By xxc
 * Date: 2020/10/9 11:32
 * Desc:
 */
public class HomeFragment extends BaseHomeFragment {
    @BindView(R.id.home_tab_layout)
    SlidingPaneLayout homeTabLayout;
    @BindView(R.id.home_view_pager)
    NoScrollViewPager homeViewPager;
//    @BindView(R.id.search_view)
//    SearchView searchView;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_navigation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_navigation:
                Event.StartNavigationEvent event = new Event.StartNavigationEvent();
                event.start = true;
                RxBus.INSTANCE.post(event);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_game:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
