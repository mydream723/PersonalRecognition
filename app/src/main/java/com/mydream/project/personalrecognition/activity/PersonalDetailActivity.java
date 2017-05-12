package com.mydream.project.personalrecognition.activity;

import android.os.Bundle;

import com.mydream.project.personalrecognition.R;

/**
 * 人员详细信息
 * Created by MX on 2017/5/12.
 */

public class PersonalDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);
        initData();
        initView();
        initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
        mContext = PersonalDetailActivity.this;

    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    private void initView(){
        initSubTitle();
    }


}
