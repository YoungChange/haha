package com.moma.app.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: ActivityFragmentInject<p>
 * Fuction: Activity、Fragment初始化的用到的注解<p>
 * CreateDate: 2016/2/15 23:30<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ActivityInject {

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
     * toolbar的标题id
     *
     * @return
     */
    int toolbarTitle() default -1;

}
