package com.mydream.project.personalrecognition.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mydream.project.personalrecognition.R;
import com.mydream.project.personalrecognition.fragment.RecognitionIDFragment;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,RecognitionIDFragment.OnFragmentInteractionListener {
    /**
     * 导航整体布局
     */
    private DrawerLayout mDrawerLayout;
    /**
     * 上工具栏
     */
    private Toolbar mToolbar;
    /**
     * 左侧导航栏
     */
    private NavigationView mLeftNavigationView;
    /**
     * 主内容布局
     */
    private FrameLayout mFrameLayout;

    private FragmentTransaction mFragmentTransaction;

    private RecognitionIDFragment idFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        initView();
        initEvent();
        idFragment = RecognitionIDFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_homeActivity_content, idFragment, idFragment.getClass().getName()).commit();
    }

    private void initView(){
        //设置工具栏上导航
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //侧导航栏布局
        mDrawerLayout = (DrawerLayout)findViewById(R.id.dl_mainActivity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.drawer_navigation_open, R.string.drawer_navigation_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mLeftNavigationView = (NavigationView)findViewById(R.id.nv_homeActivity_navigation);

    }

    @Override
    protected void initData() {
        super.initData();
        mContext = HomeActivity.this;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mLeftNavigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_idcard:
                //身份证识别
                mDrawerLayout.closeDrawers();
                if(!getSupportFragmentManager().findFragmentByTag(idFragment.getClass().getName()).isVisible()){
                    if(null == idFragment)
                        idFragment = RecognitionIDFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_homeActivity_content,idFragment, idFragment.getClass().getName() );
                }
                break;
            case R.id.nav_history:
                //识别历史
                mDrawerLayout.closeDrawers();
                showToast(mContext,"模块开发中");
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(mDrawerLayout.isDrawerOpen(Gravity.START)){
                    mDrawerLayout.closeDrawer(Gravity.START);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
