package com.snda.mzang.tvtogether.utils.ui;

import org.apache.commons.lang.StringUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class PopupTipsUtil {

	public static ProgressDialog showWaitingDialog(final Context context, String msg) {

		if (StringUtils.isEmpty(msg)) {
			msg = "正在处理中...";
		}

		final ProgressDialog waitingDialog = ProgressDialog.show(context, "请等待...", msg, true);

		return waitingDialog;

	}

	public static void displayToast(Context context, String text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP, 0, 220);
		toast.show();
	}

}
