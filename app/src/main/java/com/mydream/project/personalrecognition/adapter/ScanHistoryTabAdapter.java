package com.mydream.project.personalrecognition.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mydream.project.personalrecognition.fragment.HistoryAlertFragment;
import com.mydream.project.personalrecognition.fragment.HistoryAllFragment;
import com.mydream.project.personalrecognition.fragment.HistoryNormalFragment;

import java.util.List;

/**
 * Created by MX on 2017/5/8.
 */

public class ScanHistoryTabAdapter extends FragmentPagerAdapter {
    public ScanHistoryTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HistoryAllFragment allHistory = new HistoryAllFragment();
                return allHistory;
           case 1:
                HistoryNormalFragment normalHistory = new HistoryNormalFragment();
                return normalHistory;
            case 2:
                HistoryAlertFragment alertHistory = new HistoryAlertFragment();
                return alertHistory;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
