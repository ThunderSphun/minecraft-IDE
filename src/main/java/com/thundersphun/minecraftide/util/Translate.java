package com.thundersphun.minecraftide.util;

import java.util.StringJoiner;
import com.thundersphun.minecraftide.MCIDE;

public class Translate {
	public static String string(String... path) {
		if (path.length == 0) {
			return "";
		}
		if (path.length == 1) {
			return !path[0].equals(MCIDE.MOD_ID) ? MCIDE.MOD_ID + "." + path[0] : "";
		}

		StringJoiner key = new StringJoiner(".");
		for (String s : path) {
			key.add(s);
		}

		String output = key.toString();
		return !output.startsWith(MCIDE.MOD_ID) ? MCIDE.MOD_ID + "." + output : output;
	}

	public static String key(String keyName) {
		return string(MCIDE.MOD_ID, "keys", keyName);
	}

	public static String category(String categoryName) {
		return string(MCIDE.MOD_ID, "keys", "category", categoryName);
	}

	public static String title(String titleName) {
		return string(MCIDE.MOD_ID, "gui", "title", titleName);
	}
}
