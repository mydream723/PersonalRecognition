package com.mydream.project.personalrecognition.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;

public class BaseDialog extends AlertDialog {
	public static final Effectstype MODE_FADEIN = Effectstype.Fadein;
	public static final Effectstype MODE_SLIDE_RIGHT = Effectstype.Slideright;
	public static final Effectstype MODE_SLIDE_LEFT = Effectstype.Slideleft;
	public static final Effectstype MODE_SLIDE_TOP = Effectstype.Slidetop;
	public static final Effectstype MODE_SLIDE_BOTTOM = Effectstype.SlideBottom;
	public static final Effectstype MODE_NEWSPAGER = Effectstype.Newspager;
	public static final Effectstype MODE_FALL = Effectstype.Fall;
	public static final Effectstype MODE_SIDEFILL = Effectstype.Sidefill;
	public static final Effectstype MODE_FLIPH = Effectstype.Fliph;
	public static final Effectstype MODE_FLIPV = Effectstype.Flipv;
	public static final Effectstype MODE_ROTATE_BOTTOM = Effectstype.RotateBottom;
	public static final Effectstype MODE_ROTATE_LEFT = Effectstype.RotateLeft;
	public static final Effectstype MODE_SLIT = Effectstype.Slit;
	public static final Effectstype MODE_SHANKE = Effectstype.Shake;
	protected Context mContext;

	protected BaseDialog(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void initData() {

	}

	protected void initEvent() {

	}

	/**
	 * 规定dialog大小
	 */
	protected void initDialogSize(float width, float height) {
		Window dialogWindow = getWindow();
		Display display = this.getWindow().getWindowManager().getDefaultDisplay();
		WindowManager.LayoutParams params = dialogWindow.getAttributes();
		params.height = (int) (display.getHeight() * height);
		params.width = (int) (display.getWidth() * width);
		dialogWindow.setAttributes(params);
	}
	protected void initDialogSize() {
		Window dialogWindow = getWindow();
		Display display = this.getWindow().getWindowManager().getDefaultDisplay();
		WindowManager.LayoutParams params = dialogWindow.getAttributes();
		params.height = (int) (display.getHeight() * 0.35);
		params.width = (int) (display.getWidth() * 0.45);
		dialogWindow.setAttributes(params);
	}



}
