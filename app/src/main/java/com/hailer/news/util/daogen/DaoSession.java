package com.hailer.news.util.daogen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.hailer.news.util.bean.ChannelInfo;

import com.hailer.news.util.daogen.ChannelInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig channelInfoDaoConfig;

    private final ChannelInfoDao channelInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        channelInfoDaoConfig = daoConfigMap.get(ChannelInfoDao.class).clone();
        channelInfoDaoConfig.initIdentityScope(type);

        channelInfoDao = new ChannelInfoDao(channelInfoDaoConfig, this);

        registerDao(ChannelInfo.class, channelInfoDao);
    }
    
    public void clear() {
        channelInfoDaoConfig.clearIdentityScope();
    }

    public ChannelInfoDao getChannelInfoDao() {
        return channelInfoDao;
    }

}