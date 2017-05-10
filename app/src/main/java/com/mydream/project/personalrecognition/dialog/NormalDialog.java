package com.mydream.project.personalrecognition.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.esint.government.operationstatistics.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**
 * 单按键dialog
 * Created by MX on 2017/5/10.
 */

public class NormalDialog {
    private static final String TAG = NormalDialog.class.getSimpleName();
    private Context mContext;
    private int mIcon;
    private Effectstype mType;
    private String mTitle;
    private String mMessage;
    private String mPosButtonMsg;
    private String mNegButtonMsg;
    private boolean isCancelOutSide;
    private View mCustomView;
    private NormalButtonOnClickListener mListener;

    public NormalDialog(Context context) {
        Resources resources = context.getResources();
        mType = BaseDialog.MODE_ROTATE_BOTTOM;
        mTitle = resources.getString(R.string.dialog_title);
        mPosButtonMsg = resources.getString(R.string.dialog_sure);
        mNegButtonMsg = resources.getString(R.string.dialog_cancel);
        isCancelOutSide = false;

    }

    public interface NormalButtonOnClickListener {
        void posClick(Dialog dialog);

        void negClick(Dialog dialog);
    }

    /**
     * 单选dialog点击事件
     *
     * @param listener
     */
    public void setSinglePosListener(NormalButtonOnClickListener listener) {
        this.mListener = listener;
    }


    public void setType(Effectstype type) {
        mType = type;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public void setCancelOutSide(boolean isCancelOutSide) {
        this.isCancelOutSide = isCancelOutSide;
    }

    /**
     * 设置确定按键
     *
     * @param posButtonMsg
     */
    public void setPosButtonMessage(String posButtonMsg) {
        mPosButtonMsg = posButtonMsg;
    }

    /**
     * 设置取消按键
     *
     * @param negButtonMsg
     */
    public void setNegButtonMessgae(String negButtonMsg) {
        mNegButtonMsg = negButtonMsg;
    }


    public void show() {
        Resources resources = mContext.getResources();
        final NiftyDialogBuilder builder = NiftyDialogBuilder.getInstance(mContext);
        builder.withTitle(mTitle)                                  //.withTitle(null)  no title
                .withTitleColor(mContext.getResources().getColor(R.color.dialogText))                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage(mMessage)                     //.withMessage(null)  no Msg
                .withMessageColor(mContext.getResources().getColor(R.color.dialogText))                              //def  | withMessageColor(int resid)
                .withDialogColor(resources.getColor(R.color.dialogColor))                               //def  | withDialogColor(int resid)                               //def
                .withIcon(mIcon)
                .isCancelableOnTouchOutside(isCancelOutSide)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(mType)                                         //def Effectstype.Slidetop
                .withButton1Text(mPosButtonMsg)
                .withButton2Text(mNegButtonMsg);                                 //def gone
        if (null != mCustomView)
            builder.setCustomView(mCustomView, mContext);
        builder.setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener)
                    mListener.posClick(builder);
                else
                    builder.dismiss();
            }
        });
        builder.setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener)
                    mListener.negClick(builder);
                else
                    builder.dismiss();
            }
        });
        builder.show();


    }


}
