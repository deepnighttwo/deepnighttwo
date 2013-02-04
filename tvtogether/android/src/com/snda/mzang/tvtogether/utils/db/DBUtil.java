package com.snda.mzang.tvtogether.utils.db;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snda.mzang.tvtogether.utils.C;

public class DBUtil {

	public static void initDB(Activity activity) {

		SQLiteDatabase db = activity.openOrCreateDatabase(C.DB_NAME, Context.MODE_PRIVATE, null);
		Cursor tableCur = db.query("sqlite_master", new String[] { "name" }, "type='table'", null, null, null, "name");
		Set<String> tableNames = new HashSet<String>();

		while (tableCur.moveToNext()) {
			tableNames.add(tableCur.getString(0));
		}

		if (tableNames.contains(C.TB_USER) == false) {
			db.execSQL("create table " + C.TB_USER + " (username varchar(255) not null, password varchar(32) not null);");
		}

		db.close();
	}
}
