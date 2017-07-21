package com.moma.app.news.util.annotation;

import com.moma.app.news.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: ActivityFragmentInject<p>
 * Author: oubowu<p>
 * Fuction: Activity、Fragment初始化的用到的注解<p>
 * CreateDate: 2016/2/15 23:30<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ActivityFragmentInject {

    /**
     * 顶部局的id
     *
     * @return
     */
    int contentViewId() default -1;

    /**
     * 是否处理RefreshLayout，对应父布局为CoordinateLayout加AppbarLayout与RefreshLayout造成的事件冲突
     *
     * @return
     */
    boolean handleRefreshLayout() default false;

    /**
     * Toolbar  id
     */
    int toolbarId() default R.id.my_toolbar;

    /**
     * mToolbarTextViewId
     */
    int toolbarTextViewId() default R.id.toolbar_title;

    /**
     * mToolbarTextView 內容  “新闻”
     */
    int toolbarTextViewTitle() default R.string.news;


    int toolbarBackImageButtonId() default -1;


    int toolbarAllImageButtonId() default  -1;




}
