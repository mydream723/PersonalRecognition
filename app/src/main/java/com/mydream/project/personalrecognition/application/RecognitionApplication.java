package com.mydream.project.personalrecognition.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.mydream.project.personalrecognition.entity.DaoMaster;
import com.mydream.project.personalrecognition.entity.DaoSession;
import com.mydream.project.personalrecognition.entity.HMROpenHelper;

/**
 * Created by MX on 2017/5/6.
 */

public class RecognitionApplication extends Application {
    private static final String TAG = RecognitionApplication.class.getSimpleName();
    private static final String DB_NAME = "recognition_db";
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static RecognitionApplication instances;

    public static RecognitionApplication getInstances() {
        return instances;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        initailDatabase();
    }

    /**
     * 初始化数据库
     */
    private void initailDatabase() {
        //已封装数据库升级数据迁移
        HMROpenHelper mHelper = new HMROpenHelper(this, DB_NAME, null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }

    /**
     * 获得GreenDao Session
     * @return
     */
    public DaoSession getDaoSession(){
        return mDaoSession;
    }

    public SQLiteDatabase getDb(){
        return db;
    }

}
