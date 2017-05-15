package com.mydream.project.personalrecognition.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

/**
 * 扫描人员信息
 * Created by MX on 2017/5/6.
 */
@Entity
public class PersonalInfo implements Parcelable{
    @Id(autoincrement = true)
    private Long id;
    /**
     * 表格id(UUID)
     */
    private String uid;
    /**
     * 头像
     */
    private String header;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    @NotNull
    private String cardId;
    /**
     * 地址
     */
    private String address;
    /**
     * 性别 0男（默认） 1女
     */
    private int gender;
    /**
     * 民族
     */
    private String nation;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 签发单位
     */
    private String signDepartment;
    /**
     * 有效日期 開始日期
     */
    @NotNull
    private String validStartDate;
    /**
     * 有效日期 结束日期
     */
    @NotNull
    private String validEndDate;
    /**
     * 对应的扫描历史列表
     */
    @ToMany(referencedJoinProperty = "pid")
    private List<HistroyInfo> historyList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(uid);
        dest.writeString(header);
        dest.writeString(name);
        dest.writeString(cardId);
        dest.writeString(address);
        dest.writeInt(gender);
        dest.writeString(nation);
        dest.writeString(birthday);
        dest.writeString(signDepartment);
        dest.writeString(validStartDate);
        dest.writeString(validEndDate);
        dest.writeList(historyList);

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNation() {
        return this.nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSignDepartment() {
        return this.signDepartment;
    }

    public void setSignDepartment(String signDepartment) {
        this.signDepartment = signDepartment;
    }

    public String getValidStartDate() {
        return this.validStartDate;
    }

    public void setValidStartDate(String validStartDate) {
        this.validStartDate = validStartDate;
    }

    public String getValidEndDate() {
        return this.validEndDate;
    }

    public void setValidEndDate(String validEndDate) {
        this.validEndDate = validEndDate;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 576679746)
    public List<HistroyInfo> getHistoryList() {
        if (historyList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            HistroyInfoDao targetDao = daoSession.getHistroyInfoDao();
            List<HistroyInfo> historyListNew = targetDao._queryPersonalInfo_HistoryList(id);
            synchronized (this) {
                if (historyList == null) {
                    historyList = historyListNew;
                }
            }
        }
        return historyList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 466944887)
    public synchronized void resetHistoryList() {
        historyList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1131434188)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPersonalInfoDao() : null;
    }

    public static final Parcelable.Creator<PersonalInfo> CREATOR = new Creator<PersonalInfo>() {

        @Override
        public PersonalInfo createFromParcel(Parcel source) {
            return new PersonalInfo(source);
        }

        @Override
        public PersonalInfo[] newArray(int size) {
            return new PersonalInfo[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 289258208)
    private transient PersonalInfoDao myDao;

    public PersonalInfo(Parcel in){
        //如果元素数据是list类型的时候需要： lits = new ArrayList<?> in.readList(list); 否则会出现空指针异常.并且读出和写入的数据类型必须相同.如果不想对部分关键字进行序列化,可以使用transient关键字来修饰以及static修饰.
        id = in.readLong();
        uid = in.readString();
        header = in.readString();
        name = in.readString();
        cardId = in.readString();
        address = in.readString();
        gender = in.readInt();
        nation = in.readString();
        birthday = in.readString();
        signDepartment = in.readString();
        validStartDate = in.readString();
        validEndDate = in.readString();
        in.readList(historyList, HistroyInfo.class.getClassLoader());
    }

    @Generated(hash = 1337656167)
    public PersonalInfo(Long id, String uid, String header, String name, @NotNull String cardId, String address, int gender, String nation,
            String birthday, String signDepartment, @NotNull String validStartDate, @NotNull String validEndDate) {
        this.id = id;
        this.uid = uid;
        this.header = header;
        this.name = name;
        this.cardId = cardId;
        this.address = address;
        this.gender = gender;
        this.nation = nation;
        this.birthday = birthday;
        this.signDepartment = signDepartment;
        this.validStartDate = validStartDate;
        this.validEndDate = validEndDate;
    }

    @Generated(hash = 205152550)
    public PersonalInfo() {
    }
}
