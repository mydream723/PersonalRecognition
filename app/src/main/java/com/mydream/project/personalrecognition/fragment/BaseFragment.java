package com.mydream.project.personalrecognition.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydream.project.personalrecognition.R;

public class BaseFragment extends Fragment implements View.OnClickListener{
    /**
     * 上下文
     */
    protected Context mContext;

    protected SharedPreferences mSharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected  void initData(){
        mContext = getActivity();
        mSharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);

    }

    protected  void initEvent(){

    }


    @Override
    public void onClick(View view) {
        
    }
}
