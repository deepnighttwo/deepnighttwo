package com.snda.mzang.tvtogether.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class ResourceCache {

	private static class CacheItem {
		String key;
		byte[] value;
	}

	private static Map<String, SoftReference<CacheItem>> cache = new HashMap<String, SoftReference<CacheItem>>();

	private static ReferenceQueue<CacheItem> queue = new ReferenceQueue<CacheItem>();

	public static byte[] getResource(String resPath) {
		if (resPath.contains("..")) {
			return new byte[0];
		}

		cleanUpCacheMap();

		SoftReference<CacheItem> cacheItemRef = cache.get(resPath);
		if (cacheItemRef != null) {
			CacheItem cacheItem = cacheItemRef.get();
			if (cacheItem != null) {
				return cacheItem.value;
			}
		}

		String resRealPath = SC.resBase + resPath;

		byte[] fileData = null;
		InputStream input = null;
		try {
			File dataFile = new File(resRealPath);
			input = new FileInputStream(dataFile);
			fileData = new byte[(int) dataFile.length()];
			input.read(fileData);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}

		CacheItem cacheItem = new CacheItem();
		cacheItem.key = resPath;
		cacheItem.value = fileData;
		SoftReference<CacheItem> ref = new SoftReference<CacheItem>(cacheItem, queue);
		cache.put(resPath, ref);

		return fileData;
	}

	private static void cleanUpCacheMap() {
		Reference<? extends CacheItem> softItem = null;
		while ((softItem = queue.poll()) != null) {
			CacheItem item = softItem.get();
			if (item != null) {
				cache.remove(item.key);
			}
		}
	}
}
