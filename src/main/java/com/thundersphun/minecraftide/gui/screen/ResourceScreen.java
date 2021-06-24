package com.thundersphun.minecraftide.gui.screen;

import com.thundersphun.minecraftide.gui.widget.EditorWidget;
import com.thundersphun.minecraftide.gui.widget.FileListWidget;
import com.thundersphun.minecraftide.gui.widget.ResourceFileListWidget;
import com.thundersphun.minecraftide.util.Translate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePack;

public class ResourceScreen extends BaseMenuScreen {
	public ResourceScreen() {
		super(Translate.title("resourcescreen"));
	}

	@Override
	protected EditorWidget makeEditorWidget(int x, int y, int width, int height) {
		return new EditorWidget(x, y, width, height);
	}

	@Override
	protected FileListWidget<ResourcePack> makeNavigationWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
		return new ResourceFileListWidget(client, width, height, top, bottom, itemHeight /*, file path*/);
	}
}
