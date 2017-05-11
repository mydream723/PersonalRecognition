package com.mydream.project.personalrecognition.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import com.mydream.project.personalrecognition.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by MX on 2017/4/27.
 */

public class LoadingDialog {
    private Context mContext;
    private SweetAlertDialog dialog;
    private String message;
    private boolean isCancelOutSide;
    private boolean isCancelable;
    public LoadingDialog(Context context) {
        mContext = context;
        Resources resources = context.getResources();
        message = resources.getString(R.string.dialog_message_loading);
        isCancelOutSide = false;
        isCancelable = false;
    }

    public interface loadingResultListener{
        void loadingFinish();
    }

    public void setCancelOutSide(boolean isCancelOutSide){
        this.isCancelOutSide = isCancelOutSide;
    }

    public void setCancelable(boolean isCancelable){
        this.isCancelable = isCancelable;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void show(){
        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitle(message);
        dialog.setCanceledOnTouchOutside(isCancelOutSide);
        dialog.setCancelable(isCancelable);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.show();
    }

    public void setResultDialog(String message, String button){
        dialog.setTitleText(message);
        dialog.setConfirmText(button);
        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
    }

    public void dismiss(){
        if(null != dialog && dialog.isShowing())
            dialog.dismiss();
    }


}
