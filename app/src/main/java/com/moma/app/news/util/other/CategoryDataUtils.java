package com.moma.app.news.util.other;

import com.moma.app.news.util.bean.NewsChannelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moma on 17-7-18.
 */

public class CategoryDataUtils {

    public static List<NewsChannelBean> getChannelCategoryBeans(){
        List<NewsChannelBean>  beans=new ArrayList<>();
//        beans.add(new ProjectChannelBean("头条","T1348647909107"));
//        beans.add(new ProjectChannelBean("要闻","T1467284926140"));
//        beans.add(new ProjectChannelBean("科技","T1348649580692"));
//        beans.add(new ProjectChannelBean("财经","T1348648756099"));
//        beans.add(new ProjectChannelBean("社会","T1348648037603"));
//        beans.add(new ProjectChannelBean("军事","T1348648141035"));
//        beans.add(new ProjectChannelBean("娱乐","T1348648517839"));
//        beans.add(new ProjectChannelBean("体育","T1348649079062"));
//        beans.add(new ProjectChannelBean("数码","T1348649776727"));

        beans.add(new NewsChannelBean("头条"));
        beans.add(new NewsChannelBean("要闻"));
        beans.add(new NewsChannelBean("科技"));
        beans.add(new NewsChannelBean("财经"));
        beans.add(new NewsChannelBean("社会"));
        beans.add(new NewsChannelBean("军事"));
        beans.add(new NewsChannelBean("娱乐"));
        beans.add(new NewsChannelBean("体育"));
        beans.add(new NewsChannelBean("数码"));


        return beans;
    }

}
