package com.mydream.project.personalrecognition.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

import com.mydream.project.personalrecognition.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by MX on 2017/4/27.
 */

public class SingleDialog {
    private Context mContext;
    private SweetAlertDialog dialog;
    private String title;
    private String message;
    private String posButton;
    private boolean isCancelOutSide;
    private boolean isCancelable;
    private SingleOnClickListener mListener;

    public SingleDialog(Context context) {
        mContext = context;
        Resources resources = context.getResources();
        title = resources.getString(R.string.dialog_title);
        message = resources.getString(R.string.dialog_message_loading);
        posButton = resources.getString(R.string.dialog_sure);
        isCancelOutSide = false;
        isCancelable = true;
    }



    public interface SingleOnClickListener{
        void onPosClick(SweetAlertDialog sweetAlertDialog);
    }

    public void setOnSingleClickListener(SingleOnClickListener listener){
        this.mListener = listener;
    }

    public void setCancelOutSide(boolean isCancelOutSide){
        this.isCancelOutSide = isCancelOutSide;
    }

    public void setCancelable(boolean isCancelable){
        this.isCancelable = isCancelable;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setPosButton(String posButton){
        this.posButton = posButton;
    }

    public void show(){
        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(message);
        dialog.setConfirmText(posButton);
        dialog.setCanceledOnTouchOutside(isCancelOutSide);
        dialog.setCancelable(isCancelable);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if(null != mListener)
                    mListener.onPosClick(sweetAlertDialog);
                else
                    sweetAlertDialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showWithResultDialog(final String resultMessage, final String resultButton){
        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setTitleText(message);
        dialog.setConfirmText(posButton);
        dialog.setCanceledOnTouchOutside(isCancelOutSide);
        dialog.setCancelable(isCancelable);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
               setResultDialog(resultMessage, resultButton);
            }
        });
        dialog.show();
    }

    public void setResultDialog(String message, String button){
        dialog.setTitleText(message);
        dialog.setConfirmText(button);
        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if(null != mListener)
                    mListener.onPosClick(sweetAlertDialog);
                else
                    sweetAlertDialog.dismiss();
            }
        });


    }

    public void dismiss(){
        if(null != dialog && dialog.isShowing())
            dialog.dismiss();
    }


}
