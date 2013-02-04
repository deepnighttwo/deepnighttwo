package com.deepnighttwo.testimagelist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class TestimagelistActivity extends ListActivity {

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		this.setTitle("测试List");
		try {
			setAdaptersForImageList();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setAdaptersForImageList() throws IOException {
		String imageRoot = "/sdcard/tempdir";
		File dir = new File(imageRoot);
		if (dir.exists() == false || dir.isDirectory() == false) {
			dir.mkdir();
		}
		// String[] res = new String[] {
		// "com/deepnighttwo/testimagelist/res/braid.JPG",
		// "com/deepnighttwo/testimagelist/res/chrome.bmp",
		// "com/deepnighttwo/testimagelist/res/ginger_bread.jpg",
		// "com/deepnighttwo/testimagelist/res/github.jpg",
		// "com/deepnighttwo/testimagelist/res/gomboc.bmp" };
		// String[] resName = new String[] { "a.jpg", "b.png", "c.jpg", "d.jpg",
		// "e.bmp" };

		// for (int i = 0; i < res.length; i++) {
		// InputStream input =
		// this.getClassLoader().getResources(resName)(res[i]);
		// File dataFile = new File(dir, resName[i]);
		// if (dataFile.exists() == false || dataFile.isFile() == false) {
		// dataFile.createNewFile();
		// }
		// OutputStream os = new FileOutputStream(dataFile);
		// byte[] buffer = new byte[1024];
		// int len = -1;
		// while ((len = input.read(buffer)) != -1) {
		// os.write(buffer, 0, len);
		// }
		// input.close();
		// os.close();
		// }

		String oneRes = "com/deepnighttwo/testimagelist/res/CCAV字电视台.png";
		URL url = this.getClassLoader().getResource(oneRes);

		String path = url.getPath();

		String jarFilePath = path.substring(path.indexOf('/'), path.indexOf('!'));

		List<String> resName = new ArrayList<String>();

		JarFile file = new JarFile(jarFilePath);

		Enumeration<JarEntry> ets = file.entries();

		String imageDir = "com/deepnighttwo/testimagelist/res/";

		while (ets.hasMoreElements() == true) {
			JarEntry jarEntry = ets.nextElement();
			String jarEntryPath = jarEntry.getName();
			if (jarEntryPath.startsWith(imageDir) == false) {
				continue;
			}
			InputStream input = file.getInputStream(jarEntry);
			String imgFileName = jarEntryPath.substring(jarEntryPath.lastIndexOf('/') + 1, jarEntryPath.lastIndexOf('.'));
			resName.add(imgFileName);
			File dataFile = new File(dir, imgFileName);
			if (dataFile.exists() == false || dataFile.isFile() == false) {
				dataFile.createNewFile();
			}
			OutputStream os = new FileOutputStream(dataFile);
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = input.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			input.close();
			os.close();
		}

		Bitmap[] images = new Bitmap[resName.size()];

		for (int i = 0; i < resName.size(); i++) {
			images[i] = BitmapFactory.decodeFile(imageRoot + "/" + resName.get(i));
		}

		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, resName.toArray(new String[0]), images);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	}
}