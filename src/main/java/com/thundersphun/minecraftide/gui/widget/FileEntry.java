package com.thundersphun.minecraftide.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.io.File;
import java.nio.file.Path;

public class FileEntry extends EntryListWidget.Entry<FileEntry> {
	private static final float TEXT_SCALE = 0.5f;
	private final File file;
	private final boolean isDirectory;
	private final Path localizedPath;

	public FileEntry(Path absolutePath, Path relativePath) {
		this.file = absolutePath.toFile();
		this.isDirectory = this.file.isDirectory();
		this.localizedPath = relativePath;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		matrices.push();
		matrices.scale(TEXT_SCALE, TEXT_SCALE, TEXT_SCALE);
		MinecraftClient.getInstance().textRenderer.draw(matrices, this.localizedPath.toString(), x, y / TEXT_SCALE, 0xFFFFFFFF);
		matrices.pop();
	}

	public int getWidth() {
		return this.localizedPath.toString().length();
	}
}
