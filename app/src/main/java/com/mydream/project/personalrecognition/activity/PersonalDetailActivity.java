package com.mydream.project.personalrecognition.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mydream.project.personalrecognition.R;
import com.mydream.project.personalrecognition.entity.HistroyInfo;
import com.mydream.project.personalrecognition.entity.PersonalInfo;
import com.mydream.project.personalrecognition.utils.Constances;

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


        mHistoryInfo = getIntent().getParcelableExtra(Constances.INTENT_PERSONAL);


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

    }

    private void initValue(HistroyInfo info) {
        nameTextView.setText(info.getPersonalInfo().getName());
        nationTextView.setText(info.getPersonalInfo().getNation());
        genderTextView.setText(info.getPersonalInfo().getGender() == 0 ? getResources().getString(R.string.gender_male) : getResources().getString(R.string.gender_female));
        birthdayTextView.setText(BaseActivity.formatBirthday(mContext, info.getPersonalInfo().getNation()));
        addressTextView.setText(info.getPersonalInfo().getAddress());
        idcardTextView.setText(info.getPersonalInfo().getCardId());
        departmentTextView.setText(info.getPersonalInfo().getSignDepartment());
        validDateTextView.setText(info.getPersonalInfo().getValidStartDate() + "-" + info.getPersonalInfo().getValidEndDate());
    }


}
