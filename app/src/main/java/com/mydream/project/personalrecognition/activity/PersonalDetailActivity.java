package com.mydream.project.personalrecognition.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.mydream.project.personalrecognition.R;
import com.mydream.project.personalrecognition.db.DBManager;
import com.mydream.project.personalrecognition.entity.HistroyInfo;
import com.mydream.project.personalrecognition.entity.PersonalInfo;
import com.mydream.project.personalrecognition.utils.Constances;

import org.greenrobot.greendao.DbUtils;
import org.w3c.dom.Text;

/**
 * 人员详细信息
 * Created by MX on 2017/5/12.
 */

public class PersonalDetailActivity extends BaseActivity {
    private static final String TAG = PersonalDetailActivity.class.getSimpleName();
    /**
     * 历史信息
     */
    private HistroyInfo mHistoryInfo;

    private Intent initIntent;

    private TextView nameTextView, genderTextView, nationTextView, birthdayTextView, addressTextView, idcardTextView, departmentTextView, validDateTextView;
    private TextView recordDateTextView, isMarkedTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);
        initData();
        initView();
        initEvent();

        initValue(mHistoryInfo);
    }

    @Override
    protected void initData() {
        super.initData();
        mContext = PersonalDetailActivity.this;
        initIntent = getIntent();
        mHistoryInfo =  getIntent().getParcelableExtra(Constances.INTENT_PERSONAL);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    private void initView() {
        initSubTitle();
        nameTextView = (TextView) findViewById(R.id.tv_personalDetailActivity_name);
        nationTextView = (TextView) findViewById(R.id.tv_personalDetailActivity_nation);
        genderTextView = (TextView) findViewById(R.id.tv_personalDetailActivity_gender);
        birthdayTextView = (TextView) findViewById(R.id.tv_personalDetailActivity_birthday);
        addressTextView = (TextView) findViewById(R.id.tv_personalDetailActivity_address);
        idcardTextView = (TextView) findViewById(R.id.tv_personalDetailActivity_idcard);
        departmentTextView = (TextView) findViewById(R.id.tv_personalDetailActivity_department);
        validDateTextView = (TextView) findViewById(R.id.tv_personalDetailActivity_validDate);

        recordDateTextView = (TextView)findViewById(R.id.tv_personalDetailActivity_recordDate);
        isMarkedTextView = (TextView)findViewById(R.id.tv_personalDetailActivity_recordMarked);

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

    private void initValue(HistroyInfo info) {
        long pid = info.getPid();
        PersonalInfo personal = DBManager.getInstance().getmPersonalInfoDao().queryPersonalById(pid);
        if(null != personal){
            nameTextView.setText(personal.getName());
            nationTextView.setText(personal.getNation());
            genderTextView.setText(personal.getGender() == 0 ? getResources().getString(R.string.gender_male) : getResources().getString(R.string.gender_female));
            birthdayTextView.setText(BaseActivity.formatBirthday(mContext, personal.getBirthday()));
            addressTextView.setText(personal.getAddress());
            idcardTextView.setText(personal.getCardId());
            departmentTextView.setText(personal.getSignDepartment());
            validDateTextView.setText(personal.getValidStartDate() + " - " + personal.getValidEndDate());

            recordDateTextView.setText(info.getScanDate());
            if(info.getIsMarked() == 0){
                isMarkedTextView.setText(getResources().getString(R.string.marked_enable));
                isMarkedTextView.setTextColor(Color.BLACK);
            }else{
                isMarkedTextView.setText(getResources().getString(R.string.marked_abled));
                isMarkedTextView.setTextColor(Color.RED);
            }
        }else{
            Log.e(TAG, "传递值对应人员信息为空");
        }

    }


}
