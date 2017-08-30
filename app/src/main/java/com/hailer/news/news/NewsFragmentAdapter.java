package com.hailer.news.news;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.google.common.collect.Interner;
import com.hailer.news.news.NewsListFragment;
import com.hailer.news.util.bean.ChannelInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fuction: 装载Fragment的ViewPager适配器<p>
 */
public class NewsFragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<NewsListFragment> mFragments;
    private List<String> mTitles;

    public NewsFragmentAdapter(FragmentManager fm, @NonNull List<ChannelInfo> channelList) {
        super(fm);
        mFragmentManager = fm;
        List<NewsListFragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (ChannelInfo channel : channelList) {
            NewsListFragment fragment = NewsListFragment
                    .newInstance(channel.getCategoryName(),channel.getCategorySlug()); // 使用newInstances比重载的构造方法好在哪里？

            fragmentList.add(fragment);
            titleList.add(channel.getCategoryName());
        }
        mFragments = fragmentList;
        mTitles = titleList;
    }

    public NewsFragmentAdapter(FragmentManager fm, List<NewsListFragment> fragments, List<String> titles) {
        super(fm);
        mFragmentManager = fm;
        mFragments = fragments;
        mTitles = titles;
    }
    /**
     * 更新频道，前面固定的不更新，后面一律更新
     *
     * @param fragments
     * @param titles
     */
    public void updateFragments(List<NewsListFragment> fragments, List<String> titles) {
        final FragmentTransaction ft = mFragmentManager.beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            final NewsListFragment fragment = mFragments.get(i);
            ft.remove(fragment);
        }
        mFragments.clear();
        mFragments.addAll(fragments);
        for (int i = 0; i < mFragments.size(); i++) {
            ft.add(mFragments.get(i), mTitles.get(i));
        }
        ft.commit();

        this.mTitles = titles;
        notifyDataSetChanged();
    }

    public void updateFragments(@NonNull List<ChannelInfo> channelList) {
        checkNotNull(channelList);
        int channelSize = channelList.size();
        int fragmentSize = mFragments.size();
        int targetGragmentSize = channelSize > fragmentSize ? channelSize : fragmentSize;
        if (fragmentSize < channelSize) {
            for (int i = fragmentSize; i < channelSize; i++) {
                mFragments.add(NewsListFragment.newInstance(channelList.get(i).getCategoryName(),
                        channelList.get(i).getCategorySlug()));
            }
        } else {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            for (int i = channelSize; i < fragmentSize; i++) {
                ft.remove(mFragments.get(i));
            }
            Iterator<NewsListFragment> interner = mFragments.iterator();
            for (int i = fragmentSize - 1; i >= channelSize; i--) {
                mFragments.remove(i);
            }
            ft.commit();
        }
        mTitles.clear();
        for (ChannelInfo info : channelList) {
            mTitles.add(info.getCategoryName());
        }
        notifyDataSetChanged();
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
