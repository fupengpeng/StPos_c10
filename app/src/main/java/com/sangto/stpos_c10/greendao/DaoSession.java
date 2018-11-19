package com.sangto.stpos_c10.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.sangto.stpos_c10.bean.TopicBean;

import com.sangto.stpos_c10.greendao.TopicBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig topicBeanDaoConfig;

    private final TopicBeanDao topicBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        topicBeanDaoConfig = daoConfigMap.get(TopicBeanDao.class).clone();
        topicBeanDaoConfig.initIdentityScope(type);

        topicBeanDao = new TopicBeanDao(topicBeanDaoConfig, this);

        registerDao(TopicBean.class, topicBeanDao);
    }
    
    public void clear() {
        topicBeanDaoConfig.clearIdentityScope();
    }

    public TopicBeanDao getTopicBeanDao() {
        return topicBeanDao;
    }

}