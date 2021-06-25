package com.thundersphun.minecraftide.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.nio.file.Path;
import java.util.List;

public abstract class FileListWidget<T> extends EntryListWidget<FileEntry> {
	private static final int scrollbarWidth = 3;
	private double horizontalScrollAmount;
	private int recordWidth;

	public FileListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
		super(client, width, height, top, bottom, itemHeight);

		// remove special renderRectangle cases
		setRenderBackground(false);
		setRenderHorizontalShadows(false);
		setRenderSelection(false);
		setRenderHeader(false, 0);

		// load active packs
		List<T> packs = this.getPacks();
		for (int i = packs.size() - 1; i >= 0; i--) {
			Path absolute = getAbsolutePath(packs.get(i));
			Path relative = getRelativePath(packs.get(i));
			this.addEntry(new FileEntry(absolute, relative));
		}

		// init this class
		this.horizontalScrollAmount = 0;
		this.recordWidth = 0;
	}

	protected abstract Path getRelativePath(T pack);

	protected abstract Path getAbsolutePath(T pack);

	protected abstract List<T> getPacks();

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		if (Screen.hasShiftDown()) {
			this.horizontalScrollAmount += amount * width / 2;
			return true;
		}
		return super.mouseScrolled(mouseX, mouseY, amount);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

		int topWithScroll = top + 4 - (int) getScrollAmount();

		renderList(matrices, left, topWithScroll, mouseX, mouseY, delta);
		this.renderScrollbar();
		this.renderHorizontalScrollbar();
	}

	private void renderScrollbar() {
		int maxScroll = getMaxScroll();
		if (maxScroll > 0) {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();

			RenderSystem.disableTexture();
			RenderSystem.setShader(GameRenderer::getPositionColorShader);

			int scrollbarLength = bottom - top;
			int scrollbarThumbLength = (int) (scrollbarLength * scrollbarLength / (float) getMaxPosition());
			scrollbarThumbLength = MathHelper.clamp(scrollbarThumbLength, 32, scrollbarLength - 8);
			int scrollbarThumbPos = (int) getScrollAmount() * (scrollbarLength - scrollbarThumbLength) / maxScroll + top;
			scrollbarThumbPos = Math.max(scrollbarThumbPos, top);

			int x0 = getScrollbarPositionX();
			int x1 = x0 + scrollbarWidth;
			int y0 = scrollbarThumbPos + scrollbarThumbLength;
			int y1 = scrollbarThumbPos;

			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

			this.renderRectangle(bufferBuilder, x0, bottom, x1, top - scrollbarWidth, 0, 0, 0);
			this.renderRectangle(bufferBuilder, x0, y0, x1, y1, 128, 128, 128);
			this.renderRectangle(bufferBuilder, x0, y0 - 1, x1 - 1, y1, 192, 192, 192);

			tessellator.draw();

			RenderSystem.enableTexture();
			RenderSystem.disableBlend();
		}
	}

	private void renderHorizontalScrollbar() {
		int maxScroll = this.getMaxHorizontalScroll();
		if (maxScroll > 0) {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();

			RenderSystem.disableTexture();
			RenderSystem.setShader(GameRenderer::getPositionColorShader);

			int scrollbarLength = right - left;
			int scrollbarThumbLength = (int) (scrollbarLength * scrollbarLength / (float) this.getMaxHorizontalPosition());
			scrollbarThumbLength = MathHelper.clamp(scrollbarThumbLength, 32, scrollbarLength - 8);
			int scrollbarThumbPos = (int) this.horizontalScrollAmount * (scrollbarLength - scrollbarThumbLength) / maxScroll + top;
			scrollbarThumbPos = Math.max(scrollbarThumbPos, left);

			int x0 = scrollbarThumbPos + scrollbarThumbLength;
			int x1 = scrollbarThumbPos;
			int y0 = getScrollbarPositionY();
			int y1 = y0 + scrollbarWidth;

			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

			this.renderRectangle(bufferBuilder, x0, bottom, x1, top - scrollbarWidth, 0, 0, 0);
			this.renderRectangle(bufferBuilder, x0, y0, x1, y1, 128, 128, 128);
			this.renderRectangle(bufferBuilder, x0, y0 - 1, x1 - 1, y1, 192, 192, 192);

			tessellator.draw();

			RenderSystem.enableTexture();
			RenderSystem.disableBlend();
		}
	}

	private void renderRectangle(BufferBuilder bufferBuilder, int x0, int y0, int x1, int y1, int r, int g, int b) {
		bufferBuilder.vertex(x0, y0, 0.0D).color(r, g, b, 255).next();
		bufferBuilder.vertex(x1, y0, 0.0D).color(r, g, b, 255).next();
		bufferBuilder.vertex(x1, y1, 0.0D).color(r, g, b, 255).next();
		bufferBuilder.vertex(x0, y1, 0.0D).color(r, g, b, 255).next();
	}

	@Override
	protected int addEntry(FileEntry entry) {
		int entryWidth = entry.getWidth();
		if (entryWidth > this.recordWidth) {
			this.recordWidth = entryWidth;
		}
		return super.addEntry(entry);
	}

	@Override
	protected int getScrollbarPositionX() {
		return this.right - scrollbarWidth;
	}

	private int getScrollbarPositionY() {
		return this.bottom - scrollbarWidth;
	}

	private int getMaxHorizontalPosition() {
		return this.recordWidth;
	}

	private int getMaxHorizontalScroll() {
		return Math.max(0, this.getMaxHorizontalPosition() - width - 4);
	}

	@Override
	public int getRowLeft() {
		return left;
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {

	}
}
