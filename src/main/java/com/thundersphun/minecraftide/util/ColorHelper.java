package com.thundersphun.minecraftide.util;

public class ColorHelper {
	public static int toHex(int gray) {
		return toHex(gray, gray, gray, 255);
	}

	public static int toHex(int red, int green, int blue) {
		return toHex(red, green, blue, 255);
	}

	public static int toHex(int red, int green, int blue, int alpha) {
		return alpha << 24 | red << 16 | green << 8 | blue;
	}
}
