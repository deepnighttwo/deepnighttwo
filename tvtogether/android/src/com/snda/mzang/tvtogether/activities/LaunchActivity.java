package com.snda.mzang.tvtogether.activities;

import java.io.File;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.snda.mzang.tvtogether.R;
import com.snda.mzang.tvtogether.base.JSONUtil;
import com.snda.mzang.tvtogether.utils.C;
import com.snda.mzang.tvtogether.utils.db.DBUtil;
import com.snda.mzang.tvtogether.utils.res.ResUtil;
import com.snda.mzang.tvtogether.utils.ui.WaitingDialogAsyncTask;

public class LaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.launch);
		// MockServer.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LaunchTvTogetherTask initTask = new LaunchTvTogetherTask(null, "");
		initTask.execute(new Object());
	}

	class LaunchTvTogetherTask extends WaitingDialogAsyncTask<Object, Object> {

		public LaunchTvTogetherTask(Context context, String waitingMsg) {
			super(context, waitingMsg);
		}

		@Override
		protected Object process(Object param) {

			initDB();

			initResourceFiles();

			return null;
		}

		@Override
		protected void postProcess(Object result) {
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			// Bundle bundle = new Bundle();
			// bundle.putString("demoMsg", msg);
			// intent.putExtras(bundle);
			startActivity(intent);
			LaunchActivity.this.finish();

		}

	}

	private void initDB() {
		DBUtil.initDB(this);
	}

	private void initResourceFiles() {
		try {
			File appFolder = new File(C.sdroot + C.APP_DIR);
			if (appFolder.exists() == false || appFolder.isDirectory() == false) {
				appFolder.mkdir();
			}

			File channelFolder = new File(C.sdroot + C.CHANNEL_RES_DIR);
			if (channelFolder.exists() == false || channelFolder.isDirectory() == false) {
				channelFolder.mkdir();
			}

			JSONObject reqChannelList = new JSONObject();
			reqChannelList.put(C.processor, C.getChannelList);

			JSONObject ret = C.comm.sendMsg(reqChannelList);
			JSONObject[] channelNames = JSONUtil.getJSONObjArray(ret, C.channels);

			for (JSONObject channel : channelNames) {
				String path = JSONUtil.getString(channel, "image");
				ResUtil.getResAs(C.CHANNEL_RES_DIR + path, null, false);
			}
		} catch (Exception ex) {
			Log.e("TTT", "create file failed", ex);
			return;
		}

	}
}
