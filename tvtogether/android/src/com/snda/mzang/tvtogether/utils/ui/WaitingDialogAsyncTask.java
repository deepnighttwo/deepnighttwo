package com.snda.mzang.tvtogether.utils.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

/**
 * 
 * @author Mark Zang
 * 
 * @param <Params>
 *            传递给worker的参数，worker在非ui线程里工作
 * @param <Result>
 *            worker的返回值
 */
public abstract class WaitingDialogAsyncTask<Params, Result> extends AsyncTask<Params, Integer, Result> {

	private ProgressDialog waitingDialog;

	private final Handler uiHandler;

	private boolean showWaitingDialog;

	private Context context;

	private String waitingMsg;

	public WaitingDialogAsyncTask(final Context context, String waitingMsg) {
		this.context = context;
		this.showWaitingDialog = context != null;
		if (showWaitingDialog == true) {
			this.uiHandler = new Handler();
		} else {
			uiHandler = null;
		}
		this.waitingMsg = waitingMsg;
	}

	protected abstract Result process(Params param);

	@Override
	protected Result doInBackground(final Params... params) {
		Params first = null;
		if (params.length == 0 || params[0] == null) {
			first = null;
		} else {
			first = params[0];
		}
		final Params data = first;

		if (uiHandler != null && context != null && showWaitingDialog == true) {
			showWaitingDialog = true;
			uiHandler.post(new Runnable() {

				public void run() {
					waitingDialog = PopupTipsUtil.showWaitingDialog(context, waitingMsg);

				}

			});
		} else {
			showWaitingDialog = false;
		}

		return process(data);
	}

	protected abstract void postProcess(Result result);

	@Override
	protected void onPostExecute(Result result) {
		if (waitingDialog != null) {
			waitingDialog.dismiss();
		} else if (showWaitingDialog == true) {
			int retry = 0;
			try {
				while (waitingDialog == null && retry < 10) {
					try {
						Thread.sleep(100);
						retry++;
					} catch (InterruptedException e) {
					}
				}
			} finally {
				if (waitingDialog != null) {
					waitingDialog.dismiss();
				}
			}
		}
		postProcess(result);
	}

}
