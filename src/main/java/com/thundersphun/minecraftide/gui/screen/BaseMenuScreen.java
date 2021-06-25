package com.thundersphun.minecraftide.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thundersphun.minecraftide.gui.widget.EditorWidget;
import com.thundersphun.minecraftide.gui.widget.FileListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public abstract class BaseMenuScreen extends Screen {
	private FileListWidget<?> navigationWidget;
	private EditorWidget editorWidget;
	private static final int topBarHeight = 16;

	public BaseMenuScreen(String title) {
		super(new TranslatableText(title));
	}

	@Override
	protected void init() {
		if (client != null) {
			client.keyboard.setRepeatEvents(true);
		}

		int padding = 5;
		int twoPadding = padding * 2;
		int navigationWidth = width / 4;
		int top = topBarHeight + padding;
		int widgetHeight = height - top - padding;

		this.navigationWidget = makeNavigationWidget(client, navigationWidth - padding,
				widgetHeight, top, height - padding, textRenderer.fontHeight);
		this.navigationWidget.setLeftPos(padding);

		this.editorWidget = makeEditorWidget(navigationWidth + padding, top,
				width - navigationWidth - twoPadding, widgetHeight);

		addDrawable(this.navigationWidget);
		addDrawable(this.editorWidget);
	}

	protected abstract EditorWidget makeEditorWidget(int x, int y, int width, int height);

	protected abstract FileListWidget<?> makeNavigationWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight);

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderTopBarBackground();
		super.render(matrices, mouseX, mouseY, delta);
		drawCenteredText(matrices, textRenderer, title, width / 2, 16 / 2 - textRenderer.fontHeight / 2, 0xffffff);
	}

	private void renderTopBarBackground() {
		// init complex render data
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, OPTIONS_BACKGROUND_TEXTURE);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

		// init positions
		float x = width;
		float y = topBarHeight;
		float u = x / 32f;
		float v = y / 32f;

		//init color
		int red = 64;
		int green = 64;
		int blue = 64;
		int alpha = 255;

		// set vertices
		bufferBuilder.vertex(0, y, 0).texture(0, v).color(red, green, blue, alpha).next();
		bufferBuilder.vertex(x, y, 0).texture(u, v).color(red, green, blue, alpha).next();
		bufferBuilder.vertex(x, 0, 0).texture(u, 0).color(red, green, blue, alpha).next();
		bufferBuilder.vertex(0, 0, 0).texture(0, 0).color(red, green, blue, alpha).next();

		// actually draw textures
		tessellator.draw();
	}

	@Override
	public void tick() {
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		if (this.navigationWidget.isMouseOver(mouseX, mouseY)) {
			return this.navigationWidget.mouseScrolled(mouseX, mouseY, amount);
		}
		if (this.editorWidget.isMouseOver(mouseX, mouseY)) {
			return this.editorWidget.mouseScrolled(mouseX, mouseY, amount);
		}
		return super.mouseScrolled(mouseX, mouseY, amount);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (this.navigationWidget.isMouseOver(mouseX, mouseY)) {
			return this.navigationWidget.mouseClicked(mouseX, mouseY, button);
		}
		if (this.editorWidget.isMouseOver(mouseX, mouseY)) {
			return this.editorWidget.mouseClicked(mouseX, mouseY, button);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (this.navigationWidget.isMouseOver(mouseX, mouseY)) {
			return this.navigationWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
		if (this.editorWidget.isMouseOver(mouseX, mouseY)) {
			return this.editorWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public void removed() {
		if (client != null) {
			client.keyboard.setRepeatEvents(false);
		}
	}
}
