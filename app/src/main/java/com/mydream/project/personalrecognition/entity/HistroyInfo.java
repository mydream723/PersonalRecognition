package com.mydream.project.personalrecognition.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * 历史记录
 * Created by MX on 2017/5/6.
 */
@Entity
public class HistroyInfo implements Parcelable{
    /**
     * 自增id
     */
    @Id(autoincrement = true)
    private Long id;
    /**
     * 扫描时间
     */
    private String scanDate;
    /**
     * 扫描人员
     */
    private String username;
    /**
     * 是否上传 0未上传(默认) 1已上传
     */
    private int isUploaded;
    /**
     * 上传时间
     */
    private String uploadDate;
    /**
     * 是否被标记为注意 0未标记(默认) 1已标记
     */
    private int isMarked;
    /**
     * 是否已删除 0未删除（默认） 1已删除
     */
    private int isDeleted;
    /**
     * 身份证信息外键
     */
    private Long pid;
    @ToOne(joinProperty = "pid")
    private PersonalInfo personalInfo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(scanDate);
        parcel.writeString(username);
        parcel.writeInt(isUploaded);
        parcel.writeString(uploadDate);
        parcel.writeInt(isMarked);
        parcel.writeInt(isDeleted);
        parcel.writeLong(pid);
        parcel.writeParcelable(personalInfo,i);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScanDate() {
        return this.scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIsUploaded() {
        return this.isUploaded;
    }

    public void setIsUploaded(int isUploaded) {
        this.isUploaded = isUploaded;
    }

    public String getUploadDate() {
        return this.uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getIsMarked() {
        return this.isMarked;
    }

    public void setIsMarked(int isMarked) {
        this.isMarked = isMarked;
    }

    public int getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 129327782)
    public PersonalInfo getPersonalInfo() {
        Long __key = this.pid;
        if (personalInfo__resolvedKey == null || !personalInfo__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PersonalInfoDao targetDao = daoSession.getPersonalInfoDao();
            PersonalInfo personalInfoNew = targetDao.load(__key);
            synchronized (this) {
                personalInfo = personalInfoNew;
                personalInfo__resolvedKey = __key;
            }
        }
        return personalInfo;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 181667847)
    public void setPersonalInfo(PersonalInfo personalInfo) {
        synchronized (this) {
            this.personalInfo = personalInfo;
            pid = personalInfo == null ? null : personalInfo.getId();
            personalInfo__resolvedKey = pid;
        }
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
    @Generated(hash = 250864927)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHistroyInfoDao() : null;
    }

    public static final Parcelable.Creator<HistroyInfo> CREATOR = new Creator<HistroyInfo>(){

        @Override
        public HistroyInfo createFromParcel(Parcel source) {
            return new HistroyInfo(source);
        }

        @Override
        public HistroyInfo[] newArray(int size) {
            return new HistroyInfo[size];
        }


    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 381201273)
    private transient HistroyInfoDao myDao;
    @Generated(hash = 1519672188)
    private transient Long personalInfo__resolvedKey;
    public HistroyInfo(Parcel in){
        //如果元素数据是list类型的时候需要： lits = new ArrayList<?> in.readList(list); 否则会出现空指针异常.并且读出和写入的数据类型必须相同.如果不想对部分关键字进行序列化,可以使用transient关键字来修饰以及static修饰.
        id = in.readLong();
        scanDate = in.readString();
        username = in.readString();
        isUploaded = in.readInt();
        uploadDate = in.readString();
        isMarked = in.readInt();
        isDeleted = in.readInt();
        pid = in.readLong();
        personalInfo = in.readParcelable(PersonalInfo.class.getClassLoader());
    }

    @Generated(hash = 136238827)
    public HistroyInfo(Long id, String scanDate, String username, int isUploaded, String uploadDate, int isMarked, int isDeleted, Long pid) {
        this.id = id;
        this.scanDate = scanDate;
        this.username = username;
        this.isUploaded = isUploaded;
        this.uploadDate = uploadDate;
        this.isMarked = isMarked;
        this.isDeleted = isDeleted;
        this.pid = pid;
    }

    @Generated(hash = 1209619449)
    public HistroyInfo() {
    }


}
