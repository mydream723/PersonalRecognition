package com.mydream.project.personalrecognition.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class BaseDialog extends AlertDialog {
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
