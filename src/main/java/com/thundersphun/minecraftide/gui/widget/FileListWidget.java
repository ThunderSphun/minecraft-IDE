package com.thundersphun.minecraftide.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

public abstract class FileListWidget<T> extends EntryListWidget<FileEntry> {
	public FileListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight /*, file path*/) {
		super(client, width, height, top, bottom, itemHeight);
		setRenderBackground(false);
		setRenderHorizontalShadows(false);
		setRenderSelection(false);
		setRenderHeader(false, 0);

		List<T> packs = this.getPacks();
		for (int i = packs.size() - 1; i >= 0; i--) {
			Path absolute = getAbsolutePath(packs.get(i));
			Path relative = getRelativePath(packs.get(i));
			addEntry(new FileEntry(absolute, relative));
		}
	}

	protected abstract Path getRelativePath(T pack);

	protected abstract Path getAbsolutePath(T pack);

	protected abstract List<T> getPacks();

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public int getRowLeft() {
		return left;
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {

	}

	@Override
	public String toString() {
		return new StringJoiner(", ", FileListWidget.class.getSimpleName() + "[", "]")
				.add("width=" + width).add("left=" + left).add("right=" + right)
				.add("height=" + height).add("top=" + top).add("bottom=" + bottom)
				.toString();
	}
}
