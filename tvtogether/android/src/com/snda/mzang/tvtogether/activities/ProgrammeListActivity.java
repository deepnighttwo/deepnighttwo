package com.snda.mzang.tvtogether.activities;

import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snda.mzang.tvtogether.R;
import com.snda.mzang.tvtogether.base.B;
import com.snda.mzang.tvtogether.base.JSONUtil;
import com.snda.mzang.tvtogether.utils.C;
import com.snda.mzang.tvtogether.utils.res.ResUtil;
import com.snda.mzang.tvtogether.utils.ui.WaitingDialogAsyncTask;

public class ProgrammeListActivity extends ListActivity {

	Map<String, Bitmap> bitmapCache = new WeakHashMap<String, Bitmap>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// list = new LinearLayout(this);
		// LayoutParams ltp = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT);
		// list.addView(title, ltp);
		// this.setContentView(list);
		// buildChannelList();
		this.setTitle(R.string.programme_programmeList_title);

		String channelName = this.getIntent().getExtras().getString(C.channleName);

		buildProgrammeList(channelName);

	}

	public void buildProgrammeList(String channelName) {

		LoadProgrammeListTask task = new LoadProgrammeListTask(this, channelName, getText(R.string.programme_loading_msg).toString());
		task.execute((String) null);
	}

	class LoadProgrammeListTask extends WaitingDialogAsyncTask<String, Object[][]> {

		String channelName;

		public LoadProgrammeListTask(Context context, String channelName, String waitingMsg) {
			super(context, waitingMsg);
			this.channelName = channelName;
		}

		ProgressDialog waitingDialog;

		@Override
		protected Object[][] process(final String oneRes) {
			try {
				JSONObject reqProgrammeList = new JSONObject();
				reqProgrammeList.put(C.processor, C.getProgrammeList);
				reqProgrammeList.put(C.channleName, channelName);

				JSONObject ret = C.comm.sendMsg(reqProgrammeList);
				JSONObject[] result = JSONUtil.getJSONObjArray(ret, C.programmes);

				Object[][] programmes = new Object[result.length][6];

				for (int i = 0; i < result.length; i++) {
					programmes[i][0] = JSONUtil.getString(result[i], "id");
					programmes[i][1] = JSONUtil.getString(result[i], "name");
					String image = JSONUtil.getString(result[i], "image");
					programmes[i][2] = ResUtil.getResAs(B.PROGRAMME_RES_DIR + image, C.bitmap);
					programmes[i][3] = JSONUtil.getString(result[i], "comments");
					programmes[i][4] = new Date(JSONUtil.getString(result[i], "startTime"));
					programmes[i][5] = new Date(JSONUtil.getString(result[i], "endTime"));

				}

				return programmes;

			} catch (Exception ex) {
				return null;
			}
		}

		@Override
		protected void postProcess(Object[][] result) {
			if (result == null) {
				return;
			}

			ProgrammeListActivity.this.setListAdapter(new ProgrammeAdapter(ProgrammeListActivity.this, result, icons, result));
		}
	}

	public class ProgrammeAdapter extends ArrayAdapter<String> {
		private final Context context;
		private String[] names;
		private Bitmap[] icons;
		@SuppressWarnings("unused")
		private JSONObject[] channels;

		public ProgrammeAdapter(Context context, String[] names, Bitmap[] icons, JSONObject[] channels) {
			super(context, R.layout.channelfragment, names);
			this.context = context;
			this.names = names;
			this.icons = icons;
			this.channels = channels;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.channelfragment, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
			textView.setText(names[position]);

			imageView.setImageBitmap(icons[position]);

			return rowView;
		}
	}

	// @Override
	// public void onBackPressed() {
	//
	// Dialog dialog = new
	// AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher).setTitle(R.string.channel_exit_title).setMessage(R.string.channel_exit_msg)
	// .setPositiveButton(R.string.channel_exit, new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// ProgrammeListActivity.this.finish();
	// }
	// }).setNeutralButton(R.string.channel_not_exit, new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// }
	// }).create();
	// dialog.show();
	// }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// string
		Object item = getListAdapter().getItem(position);
		Toast.makeText(this, item + " selected", Toast.LENGTH_SHORT).show();
	}

}
