package com.mydream.project.personalrecognition.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


/**
 * 基础Activity
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected SharedPreferences mSharedPreferences;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected void initData(){
        mSharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }

    protected  void initEvent(){

    }

    @Override
    public void onClick(View v) {

    }
}
