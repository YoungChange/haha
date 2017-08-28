package com.hailer.news.util.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by moma on 17-8-28.
 */

@Entity
public class ChannelInfo extends MultiItemEntity {

    @Id
    private Long id;
    private int position;
    @Unique
    private String categoryName;
    private String categorySlug;
    private String description;
    private boolean sign;

    @Generated(hash = 8769805)
    public ChannelInfo(Long id, int position, String categoryName, String categorySlug, String description, boolean sign) {
        this.id = id;
        this.position = position;
        this.categoryName = categoryName;
        this.categorySlug = categorySlug;
        this.description = description;
        this.sign = sign;
    }


    @Generated(hash = 1609160491)
    public ChannelInfo() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }


    public boolean getSign() {
        return this.sign;
    }
}


