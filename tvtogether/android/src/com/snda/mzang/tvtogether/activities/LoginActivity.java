package com.snda.mzang.tvtogether.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.snda.mzang.tvtogether.R;
import com.snda.mzang.tvtogether.base.B;
import com.snda.mzang.tvtogether.base.JSONUtil;
import com.snda.mzang.tvtogether.model.UserInfo;
import com.snda.mzang.tvtogether.utils.C;
import com.snda.mzang.tvtogether.utils.MD5Helper;
import com.snda.mzang.tvtogether.utils.UserSession;
import com.snda.mzang.tvtogether.utils.ui.PopupTipsUtil;
import com.snda.mzang.tvtogether.utils.ui.WaitingDialogAsyncTask;

public class LoginActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		Button loginBtn = (Button) this.findViewById(R.id.loginBtn);

		final EditText userName = (EditText) this.findViewById(R.id.loginUsername);

		final EditText password = (EditText) this.findViewById(R.id.loginPassword);

		final CheckBox regNewUser = (CheckBox) this.findViewById(R.id.loginReg);

		final CheckBox keepLogin = (CheckBox) this.findViewById(R.id.loginKeepLogin);

		final UserInfo userInfo = loadUserInfoFromDB();

		if (userInfo != null) {
			userName.setText(userInfo.getUserName());
			password.setText(C.dummyPassword);
			keepLogin.setChecked(true);
		}

		loginBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String passwordMD5 = userInfo == null ? MD5Helper.getMD5(password.getText().toString()) : userInfo.getPassword();
				JSONObject msg = constuctLoginMessage(userName.getText().toString(), passwordMD5, regNewUser.isChecked(), keepLogin.isChecked());

				handleLogin(msg);
				// Intent intent = new Intent(getApplicationContext(),
				// TextDemoActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putString("demoMsg", msg);
				// intent.putExtras(bundle);
				// startActivity(intent);

				// String phoneNum = "2323232323";
				// Intent intent = new Intent();
				// intent.setAction(Intent.ACTION_DIAL);
				// intent.setData(Uri.parse("tel:" + phoneNum));
				// startActivity(intent);

			}

		});
	}

	private UserInfo loadUserInfoFromDB() {
		UserInfo userInfo = null;
		SQLiteDatabase db = this.openOrCreateDatabase(C.DB_NAME, MODE_PRIVATE, null);
		Cursor users = db.query(C.TB_USER, new String[] { C.col_username, C.col_password }, null, null, null, null, null);
		if (users.moveToNext() == true) {
			userInfo = new UserInfo();
			userInfo.setUserName(users.getString(0));
			userInfo.setPassword(users.getString(1));
		}
		db.close();
		return userInfo;
	}

	private void handleLogin(final JSONObject msg) {
		boolean keepLoginBoolean = JSONUtil.getBoolean(msg, C.keepLogin);
		SQLiteDatabase db = this.openOrCreateDatabase(C.DB_NAME, MODE_PRIVATE, null);

		db.execSQL("delete from " + C.TB_USER);

		if (keepLoginBoolean == true) {
			db.execSQL("insert into " + C.TB_USER + " values (?,?)", new String[] { JSONUtil.getString(msg, C.username), JSONUtil.getString(msg, C.password) });
		}

		db.close();
		LoginTask task = new LoginTask(this, "登录中...");
		task.execute(msg);

	}

	class LoginTask extends WaitingDialogAsyncTask<JSONObject, JSONObject> {

		public LoginTask(Context context, String waitingMsg) {
			super(context, waitingMsg);
		}

		ProgressDialog waitingDialog;

		@Override
		protected JSONObject process(final JSONObject data) {

			JSONObject ret = C.comm.sendMsg(data);
			return ret;
		}

		@Override
		protected void postProcess(JSONObject result) {

			if (B.success.equals(JSONUtil.getString(result, B.result)) == true) {

				UserSession.setUserId(JSONUtil.getString(result, B.userId));

				Intent intent = new Intent(getApplicationContext(), ChannelListActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putString("demoMsg", displayMsg);
				// intent.putExtras(bundle);
				startActivity(intent);
				LoginActivity.this.finish();
			} else {
				String failMsg = JSONUtil.getString(result, B.failMsg);
				PopupTipsUtil.displayToast(LoginActivity.this, "登录失败：" + failMsg);
			}
		}

	}

	public JSONObject constuctLoginMessage(String userName, String password, boolean regNewUser, boolean keepLogin) {
		JSONObject login = new JSONObject();

		UserSession.setUserName(userName);
		UserSession.setPassword(password);
		try {
			login.put(C.processor, B.login);
			login.put(C.keepLogin, keepLogin);
			login.put(C.username, userName);
			login.put(C.password, password);
			login.put(C.regNewUser, regNewUser);
			return login;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
