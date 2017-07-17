package com.moma.app.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by moma on 17-7-17.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int COUNT = 5;
    private String[] titles = new String[]{"头条", "要闻", "彩票", "数码", "NBA", "航空", "Tab4", "Tab5", "Tab3", "Tab4", "Tab5"};
//    public final int COUNT = titles.length;
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return SimpleFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
