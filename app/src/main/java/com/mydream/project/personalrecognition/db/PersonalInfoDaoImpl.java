package com.mydream.project.personalrecognition.db;

import com.mydream.project.personalrecognition.entity.PersonalInfo;
import com.mydream.project.personalrecognition.entity.PersonalInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 身份证数据库操作
 * Created by MX on 2017/5/7.
 */

public class PersonalInfoDaoImpl {
    private static final String TAG = PersonalInfoDaoImpl.class.getSimpleName();
    private PersonalInfoDao mPersonalInfoDao;

    public PersonalInfoDaoImpl(PersonalInfoDao personalInfoDao) {
        mPersonalInfoDao = personalInfoDao;
    }

    /**
     * 出入身份证件信息
     *
     * @param info 证件信息
     * @return 插入id
     */
    public long insert(PersonalInfo info) {
        return mPersonalInfoDao.insert(info);
    }

    /**
     * 查詢是否已有同样的身份证信息
     *
     * @param cardId         身份证号
     * @param validStartDate 有效期开始时间
     * @param validEndDate   有效期结束时间
     * @return
     */
    public PersonalInfo queryExistPersonal(String cardId, String validStartDate, String validEndDate) {
        QueryBuilder<PersonalInfo> qb = mPersonalInfoDao.queryBuilder();
        qb.where(PersonalInfoDao.Properties.CardId.eq(cardId),
                PersonalInfoDao.Properties.ValidStartDate.eq(validStartDate), PersonalInfoDao.Properties.ValidEndDate.eq(validEndDate));
        List<PersonalInfo> list = qb.list();
        if (null != list && list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    public PersonalInfo queryPersonalById(long pid){
        QueryBuilder<PersonalInfo> qb = mPersonalInfoDao.queryBuilder();
        qb.where(PersonalInfoDao.Properties.Id.eq(pid));
        List<PersonalInfo> infos = qb.list();
        if(null != infos && infos.size() > 0){
            return infos.get(0);
        }else{
            return null;
        }
    }

}
