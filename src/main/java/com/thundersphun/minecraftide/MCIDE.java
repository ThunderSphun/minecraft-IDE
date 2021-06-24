package com.thundersphun.minecraftide;

import net.minecraft.util.Identifier;

public class MCIDE {
	public static final String MOD_ID = "mcide";

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
