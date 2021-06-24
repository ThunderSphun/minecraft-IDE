package com.thundersphun.minecraftide.util;

import net.minecraft.client.MinecraftClient;

public class ClientUtil {
	public static boolean mayOpenMenu(MinecraftClient client) {
		return client.player != null && (client.player.isCreative() || client.player.isSpectator());
	}
}
