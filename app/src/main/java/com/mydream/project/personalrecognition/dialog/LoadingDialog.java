package com.mydream.project.personalrecognition.dialog;

import android.content.Context;
import android.os.Bundle;

import com.mydream.project.personalrecognition.R;

/**
 * Created by MX on 2017/4/27.
 */

public class LoadingDialog extends BaseDialog {

    protected LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        initDialogSize();
    }
}
