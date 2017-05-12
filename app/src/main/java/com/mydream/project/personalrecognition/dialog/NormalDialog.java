package com.mydream.project.personalrecognition.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Switch;

import com.mydream.project.personalrecognition.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by MX on 2017/4/27.
 */

public class NormalDialog {
    private static final String TAG = NormalDialog.class.getSimpleName();
    /**
     * 确定按键
     */
    public static final int FLAG_POS = 1;
    /**
     * 取消按键
     */
    public static final int FLAG_NEG = 2;

    private Context mContext;
    private SweetAlertDialog dialog;
    private String title;
    private String message;
    private String posButton;
    private String negButton;
    private boolean isCancelOutSide;
    private boolean isCancelable;
    private NormalOnClickListener mListener;

    public NormalDialog(Context context) {
        mContext = context;
        Resources resources = context.getResources();
        title = resources.getString(R.string.dialog_title);
        message = resources.getString(R.string.dialog_message_loading);
        posButton = resources.getString(R.string.dialog_sure);
        negButton = resources.getString(R.string.dialog_cancel);
        isCancelOutSide = false;
        isCancelable = true;
    }


    public interface NormalOnClickListener {
        /**
         * 确定按键动作
         * @param sweetAlertDialog
         */
        void onPosClick(SweetAlertDialog sweetAlertDialog);

        /**
         * 取消按键动作
         * @param sweetAlertDialog
         */
        void onNegClick(SweetAlertDialog sweetAlertDialog);
    }

    /**
     * 设置两按键监听
     * @param listener
     */
    public void setOnNormalClickListener(NormalOnClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 外部是否可以取消
     * @param isCancelOutSide
     */
    public void setCancelOutSide(boolean isCancelOutSide) {
        this.isCancelOutSide = isCancelOutSide;
    }

    /**
     * 返回键是否可以取消
     * @param isCancelable
     */
    public void setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置内容
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 设置确定键按键内容
     * @param posButton
     */
    public void setPosButton(String posButton) {
        this.posButton = posButton;
    }

    /**
     * 设置取消按键内容
     * @param negButton
     */
    public void setNegButton(String negButton){
        this.negButton = negButton;
    }

    /**
     * 展现dialog
     */
    public void show() {
        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(message);
        dialog.setConfirmText(posButton);
        dialog.setCancelText(negButton);
        dialog.setCanceledOnTouchOutside(isCancelOutSide);
        dialog.setCancelable(isCancelable);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (null != mListener)
                    mListener.onPosClick(sweetAlertDialog);
                else
                    sweetAlertDialog.dismiss();
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (null != mListener)
                    mListener.onNegClick(sweetAlertDialog);
                else
                    sweetAlertDialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * 附带结果dialog的dilaog
     * @param posResultMessage
     * @param negResultMessage
     */
    public void showWithResultDialog(final String posResultMessage, final String negResultMessage) {
        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(message);
        dialog.setConfirmText(posButton);
        dialog.setCancelText(negButton);
        dialog.setCanceledOnTouchOutside(isCancelOutSide);
        dialog.setCancelable(isCancelable);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                setResultDialog(FLAG_POS, posResultMessage, negResultMessage);
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                setResultDialog(FLAG_NEG, posResultMessage, negResultMessage);
            }
        });
        dialog.show();
    }

    /**
     * 设置点击dialog按键后出现的dialog
     * @param type
     * @param posResultMessage
     * @param negResultMessage
     */
    public void setResultDialog(int type, String posResultMessage, String negResultMessage) {
        switch (type) {
            case FLAG_POS:
                dialog.setTitleText(posResultMessage);
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                dialog.showCancelButton(false);
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (null != mListener)
                            mListener.onPosClick(sweetAlertDialog);
                        else
                            sweetAlertDialog.dismiss();
                    }
                });

                break;
            case FLAG_NEG:
                dialog.setTitleText(negResultMessage);
                dialog.showCancelButton(false);
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (null != mListener)
                            mListener.onNegClick(sweetAlertDialog);
                        else
                            sweetAlertDialog.dismiss();
                    }
                });

                break;
        }

    }

    /**
     * 取消dialog
     */
    public void dismiss() {
        if (null != dialog && dialog.isShowing())
            dialog.dismiss();
    }


}
