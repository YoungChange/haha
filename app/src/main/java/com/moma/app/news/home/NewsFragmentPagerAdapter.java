package com.moma.app.news.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.moma.app.news.base.BaseFragment;
import com.moma.app.news.util.bean.NewsChannelBean;

import java.util.List;

/**
 * Created by moma on 17-7-17.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private  int COUNT = 5;
//    private String[] titles = new String[]{"头条", "要闻", "彩票", "数码", "NBA", "航空", "Tab4", "Tab5", "Tab3", "Tab4", "Tab5"};
//    public final int COUNT = titles.length;
    private Context context;

    private List<NewsChannelBean> channelBeanList;
    private FragmentManager fm;
    private List<BaseFragment> fragments;

    public NewsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    public void setChannelBean(List<NewsChannelBean> newsBeans) {
        this.channelBeanList = newsBeans;

        COUNT = newsBeans.size();
    }

    public void setFragments(List<BaseFragment> fragments) {
        this.fragments = fragments;
    }
    //对fragments进行初始化

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
//        return SimpleFragment.newInstance(position + 1,titles[position]);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return titles[position];
        return channelBeanList.get(position).gettName();
    }
}
