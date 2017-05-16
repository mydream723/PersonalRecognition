package com.mydream.project.personalrecognition.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.mydream.project.personalrecognition.R;


/**
 * 基础Activity
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected SharedPreferences mSharedPreferences;
    protected Context mContext;
    /**
     * 工具條
     */
    protected Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected void initData() {
        mSharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }

    protected void initEvent() {

    }

    /**
     * 初始化二级导航栏
     */
    protected void initSubTitle() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_subtitle);
        setSupportActionBar(mToolbar);
        //返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 显示Toast提示信息
     *
     * @param context 上下文
     * @param message 显示信息
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 格式化生日
     * @param context
     * @param birthday
     * @return
     */
    public static String formatBirthday(Context context, String birthday) {
        String year = birthday.substring(0, 4);
        String month = birthday.substring(4, 6);
        String day = birthday.substring(6, 8);
        return year + context.getResources().getString(R.string.date_year) + month + context.getResources().getString(R.string.date_month) + day + context.getResources().getString(R.string.date_day);
    }

}
