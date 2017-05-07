package com.mydream.project.personalrecognition.db;

import com.mydream.project.personalrecognition.entity.HistroyInfo;
import com.mydream.project.personalrecognition.entity.HistroyInfoDao;
import com.mydream.project.personalrecognition.entity.PersonalInfo;
import com.mydream.project.personalrecognition.entity.PersonalInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 扫描历史数据库操作
 * Created by MX on 2017/5/7.
 */

public class HistoryInfoDaoImpl {
    private static final String TAG = HistoryInfoDaoImpl.class.getSimpleName();
    private HistroyInfoDao mHistroyInfoDao;
    public HistoryInfoDaoImpl(HistroyInfoDao histroyInfoDao){
        mHistroyInfoDao = histroyInfoDao;
    }

    /**
     * 录入扫描历史信息
     * @param info 插入信息
     */
    public long insert(HistroyInfo info){
       return mHistroyInfoDao.insert(info);
    }

    /**
     * 查詢所有歷史信息
     * @return
     */
    public List<HistroyInfo> queryHistoryAll(){
        QueryBuilder qb = mHistroyInfoDao.queryBuilder();
        return qb.list();
    }


}
