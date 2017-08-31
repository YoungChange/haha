package com.hailer.news.news;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import com.hailer.news.util.bean.ChannelInfo;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fuction: <p>装载Fragment的ViewPager适配器</p>
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
                    .newInstance(channel.getCategoryName(),Long.toString(channel.getId())); // 使用newInstances比重载的构造方法好在哪里？

            fragmentList.add(fragment);
            titleList.add(channel.getCategoryName());
        }
        mFragments = fragmentList;
        mTitles = titleList;
    }

    public void updateFragments(@NonNull List<ChannelInfo> channelList) {
        checkNotNull(channelList);
        int channelSize = channelList.size();
        int fragmentSize = mFragments.size();
        if (fragmentSize < channelSize) {
            for (int i = fragmentSize; i < channelSize; i++) {
                mFragments.add(NewsListFragment.newInstance(channelList.get(i).getCategoryName(),
                        Long.toString(channelList.get(i).getId())));
            }
        } else {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            for (int i = channelSize; i < fragmentSize; i++) {
                ft.remove(mFragments.get(i));
            }
            // 由于List的数据连续，从后面往前删除是一个良好的选择。或者只能删除固定游标，按个数计量
            for (int i = fragmentSize - 1; i >= channelSize; i--) {
                mFragments.remove(i);
            }
            ft.commit();
        }
        mTitles.clear();
        // 更新数据
        for (int i = 0; i < mFragments.size(); i++) {
            ChannelInfo channel = channelList.get(i);
            mTitles.add(channel.getCategoryName());
            mFragments.get(i).setCat(channel.getCategoryName(), Long.toString(channel.getId()));
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
