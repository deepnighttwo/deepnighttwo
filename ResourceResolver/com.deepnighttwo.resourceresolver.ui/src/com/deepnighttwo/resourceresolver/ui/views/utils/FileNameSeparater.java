package com.deepnighttwo.resourceresolver.ui.views.utils;

import java.util.HashSet;
import java.util.Set;

public class FileNameSeparater {

	private static Set<String> END_SIGN = new HashSet<String>();

	static {
		END_SIGN.add("avi");
		END_SIGN.add("mkv");
		END_SIGN.add("rmvb");
		END_SIGN.add("rm");
		END_SIGN.add("dvdraw");
		END_SIGN.add("mp4");
		END_SIGN.add("iso");
		END_SIGN.add("ts");
		END_SIGN.add("wmv");
		END_SIGN.add("hdtv");
		END_SIGN.add("720p");
		END_SIGN.add("480p");
		END_SIGN.add("1080p");
		END_SIGN.add("x264");
		END_SIGN.add("remux");
		END_SIGN.add("blu-ray");
		END_SIGN.add("bluray");
		END_SIGN.add("dts");
		END_SIGN.add("tlf");
		END_SIGN.add("chd");
		END_SIGN.add("mysilu");
		END_SIGN.add("bdrip");
		END_SIGN.add("h264");
		END_SIGN.add("wiki");
		END_SIGN.add("dtshdma");
		END_SIGN.add("bd");
		END_SIGN.add("dvdrip");
		END_SIGN.add("dvd");
		END_SIGN.add("rip");
		END_SIGN.add("hdrip");
		END_SIGN.add("r5");
	}

	/**
	 * movie name, movie year
	 * 
	 * @param rawName
	 * @return
	 */
	public static String[] getMovieNameAndYear(String rawName) {
		if (rawName == null || rawName.length() == 0) {
			return new String[] { "N/A", "0000" };
		}

		// if the name contains two parts or less, then just use the first as
		// movie name. Year will be unknown (representing using 0000).
		String[] parts = rawName.split("\\.");
		if (parts.length < 3) {
			return new String[] { parts[0], "0000" };
		}

		int startPartID = 0;
		int endPartID = -1;
		String unknown = "unknown";
		// the following if-else will remove the start "unknown" or the starting
		// three numbers (at most). It means the next part would be the movie
		// name.
		if (unknown.equalsIgnoreCase(parts[0])) {
			startPartID = 1;
		} else {
			startPartID = 3;
			for (int i = 0; i < 3 && i < parts.length; i++) {
				String part = parts[i];
				try {
					Integer.parseInt(part);
					// this is a validated number, try to find next. reach to
					// the third for most

				} catch (Throwable ex) {
					// not a validated number, search ends.
					startPartID = i;
					break;
				}
			}
		}

		endPartID = parts.length;
		String year = "0000";
		for (int i = startPartID + 1; i < parts.length; i++) {
			String part = parts[i];
			try {
				Integer.parseInt(part);
				endPartID = i;
				year = part;
			} catch (Throwable ex) {
				continue;
			}
		}

		String[] ret = new String[2];
		StringBuffer movieName = new StringBuffer();
		for (int i = startPartID; i < endPartID; i++) {
			if (isNotPartOfMovieName(parts[i]) == false) {
				movieName.append(parts[i] + " ");
			}
		}
		ret[0] = movieName.toString().trim();
		ret[1] = year;
		return ret;
	}

	private static boolean isNotPartOfMovieName(String part) {
		boolean isEndSign = END_SIGN.contains(part.toLowerCase());
		return isEndSign;
	}

}
