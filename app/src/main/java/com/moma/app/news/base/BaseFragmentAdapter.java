package com.moma.app.news.base;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Fuction: 装载Fragment的ViewPager适配器<p>
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    /**
     * 更新频道，前面固定的不更新，后面一律更新
     *
     * @param fragments
     * @param titles
     */
    public void updateFragments(List<BaseFragment> fragments, List<String> titles) {
        for (int i = 0; i < mFragments.size(); i++) {
            final BaseFragment fragment = mFragments.get(i);
            final FragmentTransaction ft = mFragmentManager.beginTransaction();
            if (i > 2) {
                ft.remove(fragment);
                mFragments.remove(i);
                i--;
            }
            ft.commit();
        }
        for (int i = 0; i < fragments.size(); i++) {
            if (i > 2) {
                mFragments.add(fragments.get(i));
            }
        }
        this.mTitles = titles;
        notifyDataSetChanged();
    }

    public BaseFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
        super(fm);
        mFragmentManager = fm;
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

}
