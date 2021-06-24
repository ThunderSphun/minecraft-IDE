package com.thundersphun.minecraftide;

import com.thundersphun.minecraftide.gui.screen.ResourceScreen;
import com.thundersphun.minecraftide.util.ClientUtil;
import com.thundersphun.minecraftide.util.Translate;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(net.fabricmc.api.EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {
	private static KeyBinding openResourceScreenBinding;

	@Override
	public void onInitializeClient() {
		openResourceScreenBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				Translate.key("openresource"),
				GLFW.GLFW_KEY_F10,
				Translate.category("menu")
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (ClientUtil.mayOpenMenu(client)) {
				assert client.player != null;
				while (openResourceScreenBinding.wasPressed()) {
					client.openScreen(new ResourceScreen());
				}
			}
		});
	}
}
