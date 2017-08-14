package com.hailer.news.util.annotation;

import com.hailer.news.R;
import com.hailer.news.common.ToolBarType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fuction: Activity、Fragment初始化的用到的注解<p>
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

    /**
     * 判断是否含有侧滑菜单【list 页面有，Detail 页面没有】
     * @return
     */
    boolean hasNavigationView() default false;


    int toolbarType() default ToolBarType.HasBackButton;

}
