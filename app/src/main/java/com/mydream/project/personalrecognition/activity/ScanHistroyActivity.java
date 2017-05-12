package com.mydream.project.personalrecognition.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mydream.project.personalrecognition.R;
import com.mydream.project.personalrecognition.adapter.ScanHistoryTabAdapter;

public class ScanHistroyActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{
    private static final String TAG = ScanHistroyActivity.class.getSimpleName();
    /**
     * 工具條
     */
    private Toolbar mToolbar;
    /**
     * tab
     */
    private TabLayout mTabLayout;

    private ViewPager mViewPager;
    private ScanHistoryTabAdapter tabApdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_histroy);

        initData();
        initView();
        initEvent();

        mViewPager.setAdapter(tabApdater);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mContext = ScanHistroyActivity.this;

        tabApdater = new ScanHistoryTabAdapter(getSupportFragmentManager());

    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_scanHistoryActivity_toolbar);
        setSupportActionBar(mToolbar);
        //返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTabLayout = (TabLayout)findViewById(R.id.tab_scanHistoryActivity_type);
        mViewPager = (ViewPager)findViewById(R.id.vp_scanHistoryActivity_viewpager);

        //tab類型
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.scanHistory_tab_all));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.scanHistory_tab_normal));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.scanHistory_tab_alert));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
