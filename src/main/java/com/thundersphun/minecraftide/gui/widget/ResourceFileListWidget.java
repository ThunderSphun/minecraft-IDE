package com.thundersphun.minecraftide.gui.widget;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePack;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceFileListWidget extends FileListWidget<ResourcePack> {
	public ResourceFileListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
		super(client, width, height, top, bottom, itemHeight);
	}

	@Override
	protected Path getRelativePath(ResourcePack pack) {
		return new File(pack.getName()).toPath();
	}

	@Override
	protected Path getAbsolutePath(ResourcePack pack) {
		return new File(MinecraftClient.getInstance().getResourcePackDir(), pack.getName()).toPath();
	}

	@Override
	protected List<ResourcePack> getPacks() {
		return MinecraftClient.getInstance().getResourceManager().streamResourcePacks().collect(Collectors.toList());
	}
}
