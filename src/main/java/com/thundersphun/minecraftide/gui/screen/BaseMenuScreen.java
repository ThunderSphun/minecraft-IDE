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
	private FileListWidget navigationWidget;
	private EditorWidget editorWidget;

	public BaseMenuScreen(String title) {
		super(new TranslatableText(title));
	}

	@Override
	protected void init() {
		if (client != null) {
			client.keyboard.setRepeatEvents(true);
		}

		int padding = 5;

		this.navigationWidget = makeNavigationWidget(client, width / 4 - padding,
				height - 16 / 2 - padding - padding, 16 + padding, height - padding, textRenderer.fontHeight);
		this.navigationWidget.setLeftPos(padding);

		this.editorWidget = makeEditorWidget(width / 4, 16 + padding, (width / 4) * 3, height - 16 / 2 - padding - padding);

		System.out.println(this.navigationWidget);
		System.out.println(this.editorWidget);
		System.out.println("width=" + width + ", height=" + height);
		System.out.println();

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
		float y = 16;
		float u = x / 32f;
		float v = y / 32f;

		//init color
		int r = 64;
		int g = 64;
		int b = 64;
		int a = 255;

		// set vertices
		bufferBuilder.vertex(0, y, 0).texture(0, v).color(r, g, b, a).next();
		bufferBuilder.vertex(x, y, 0).texture(u, v).color(r, g, b, a).next();
		bufferBuilder.vertex(x, 0, 0).texture(u, 0).color(r, g, b, a).next();
		bufferBuilder.vertex(0, 0, 0).texture(0, 0).color(r, g, b, a).next();

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
	public void removed() {
		if (client != null) {
			client.keyboard.setRepeatEvents(false);
		}
	}
}
