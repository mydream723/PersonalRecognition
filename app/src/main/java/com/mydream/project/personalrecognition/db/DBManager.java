package com.mydream.project.personalrecognition.db;

import com.mydream.project.personalrecognition.application.RecognitionApplication;
import com.mydream.project.personalrecognition.entity.DaoSession;
import com.mydream.project.personalrecognition.entity.HistroyInfoDao;
import com.mydream.project.personalrecognition.entity.PersonalInfoDao;

/**
 * 数据库管理类
 * Created by MX on 2017/5/7.
 */

public class DBManager {
    private static DBManager mDBManager;
    /**
     * 身份证信息
     */
    private PersonalInfoDaoImpl mPersonalInfoDaoImpl;
    /**
     * 身份证扫描历史
     */
    private HistoryInfoDaoImpl mHistoryInfoDaoImpl;

    private DaoSession mDaoSession;

    private DBManager(){
        mDaoSession = RecognitionApplication.getInstances().getDaoSession();
    }

    public static DBManager getInstance(){
        if(null == mDBManager)
            mDBManager = new DBManager();
        return mDBManager;
    }

    /**
     * 获得身份证件信息实例
     * @return
     */
    public PersonalInfoDaoImpl getmPersonalInfoDao(){
        if(null == mPersonalInfoDaoImpl)
            mPersonalInfoDaoImpl = new PersonalInfoDaoImpl(mDaoSession.getPersonalInfoDao());
        return mPersonalInfoDaoImpl;
    }

    /**
     * 获得历史信息实例
     * @return
     */
    public HistoryInfoDaoImpl getmHistroyInfoDao(){
        if(null == mHistoryInfoDaoImpl)
            mHistoryInfoDaoImpl = new HistoryInfoDaoImpl(mDaoSession.getHistroyInfoDao());
        return mHistoryInfoDaoImpl;
    }




}
