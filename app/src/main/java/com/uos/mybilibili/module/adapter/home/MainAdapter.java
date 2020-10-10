package com.uos.mybilibili.module.adapter.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.uos.mybilibili.R;
import com.uos.mybilibili.module.fragment.home.RecommendFragment;
import com.uos.mybilibili.utils.AppUtils;

/**
 * @author xxc
 * @date 创建时间：2020年10月9日17:20:24
 * 描述:主页Tag
 */

public class MainAdapter extends FragmentPagerAdapter {
    private String[] mTitle;
    private Fragment[] mFragment;

    public MainAdapter(FragmentManager fm) {
        super(fm);
        init();
    }


    private void init() {
        mTitle = AppUtils.getStringArray(R.array.main_title);
        mFragment = new Fragment[mTitle.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragment[position] == null) {
            switch (position) {
                case 0:
                    //直播
//                    mFragment[position] = LiveFragment.newInstance();
//                    break;
                case 1:
                    //推荐
                    mFragment[position] = RecommendFragment.newInstance();
                    break;
                case 2:
                    //追番
//                    mFragment[position] = ChaseBangumiFragment.newInstance();
                    break;
                case 3:
                    //分区
//                    mFragment[position] = RegionFragment.newInstance();
                    break;
                case 4:
                    //动态
//                    mFragment[position] = DynamicFragment.newInstance();
                    break;
                case 5:
                    //发现
//                    mFragment[position] = DiscoverFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return mFragment[position];
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
