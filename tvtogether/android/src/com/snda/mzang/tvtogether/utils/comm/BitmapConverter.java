package com.snda.mzang.tvtogether.utils.comm;

import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapConverter<T> implements IContentConverter<T> {

	public static IContentConverter<Bitmap> BITMAP = new BitmapConverter<Bitmap>();

	private BitmapConverter() {

	}

	@SuppressWarnings("unchecked")
	public T convert(byte[] content) throws JSONException {

		Bitmap bitmap = BitmapFactory.decodeByteArray(content, 0, content.length);
		return (T) bitmap;
	}

}
