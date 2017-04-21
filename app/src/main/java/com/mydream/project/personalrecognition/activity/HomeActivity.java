package com.mydream.project.personalrecognition.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;

import com.mydream.project.personalrecognition.R;

public class HomeActivity extends BaseActivity {
    /**
     * 上工具栏
     */
    private Toolbar mToolbar;
    /**
     * 左侧
     */
    private NavigationView mLeftNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        initView();
        initEvent();
    }

    private void initView(){
        //设置工具栏上导航
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);



    }

    @Override
    protected void initData() {
        super.initData();
        mContext = HomeActivity.this;

    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }
}
