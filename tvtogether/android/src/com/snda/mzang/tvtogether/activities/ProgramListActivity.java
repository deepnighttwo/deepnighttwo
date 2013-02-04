package com.snda.mzang.tvtogether.activities;

import java.util.Map;
import java.util.WeakHashMap;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class ProgramListActivity extends ListActivity {

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
		this.setTitle(R.string.channel_channelList_title);

		buildChannelList();

	}

	public void buildChannelList() {

		LoadChannelListTask task = new LoadChannelListTask(this, getText(R.string.channel_loading_msg).toString());
		task.execute((String) null);
	}

	class LoadChannelListTask extends WaitingDialogAsyncTask<String, JSONObject[]> {

		public LoadChannelListTask(Context context, String waitingMsg) {
			super(context, waitingMsg);
		}

		ProgressDialog waitingDialog;

		@Override
		protected JSONObject[] process(final String oneRes) {
			try {
				JSONObject reqChannelList = new JSONObject();
				reqChannelList.put(C.processor, C.getChannelList);

				JSONObject ret = C.comm.sendMsg(reqChannelList);
				return JSONUtil.getJSONObjArray(ret, C.channels);
			} catch (Exception ex) {
				return null;
			}
		}

		@Override
		protected void postProcess(JSONObject[] result) {
			if (result == null) {
				return;
			}

			String[] channelNames = new String[result.length];
			Bitmap[] icons = new Bitmap[result.length];

			for (int i = 0; i < result.length; i++) {
				channelNames[i] = JSONUtil.getString(result[i], "name");
				String resPath = B.CHANNEL_RES_DIR + JSONUtil.getString(result[i], "image");
				try {
					icons[i] = ResUtil.getResAs(resPath, C.bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			ProgramListActivity.this.setListAdapter(new ChannelItemAdapter(ProgramListActivity.this, channelNames, icons, result));
		}
	}

	public class ChannelItemAdapter extends ArrayAdapter<String> {
		private final Context context;
		private String[] names;
		private Bitmap[] icons;
		@SuppressWarnings("unused")
		private JSONObject[] channels;

		public ChannelItemAdapter(Context context, String[] names, Bitmap[] icons, JSONObject[] channels) {
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

	@Override
	public void onBackPressed() {

		Dialog dialog = new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher).setTitle(R.string.channel_exit_title).setMessage(R.string.channel_exit_msg)
				.setPositiveButton(R.string.channel_exit, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						ProgramListActivity.this.finish();
					}
				}).setNeutralButton(R.string.channel_not_exit, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).create();
		dialog.show();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// string
		Object item = getListAdapter().getItem(position);
		Toast.makeText(this, item + " selected", Toast.LENGTH_SHORT).show();
	}

}
