package com.thundersphun.minecraftide.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thundersphun.minecraftide.util.ColorHelper;
import com.thundersphun.minecraftide.util.Orientation;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ScrollWidget extends DrawableHelper implements Element, Drawable {
	private static final int WIDTH = 3;
	private final Orientation orientation;
	private int x;
	private int y;
	private int length;
	private int min;
	private int max;
	private final double progress;
	private final boolean showThumb;

	public ScrollWidget(int x, int y, int length, int min, int max, Orientation orientation) {
		this.x = x;
		this.y = y;
		this.length = length;
		this.min = min;
		this.max = max;
		this.orientation = orientation;
		this.progress = 0;
		this.showThumb = true;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.disableTexture();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		int thumbLength = 0;
		int thumbPos = 0;

		int maxScroll = this.maxScroll();
		if (this.showThumb && maxScroll > 0) {
			thumbLength = (int) (this.length * this.length / (float) this.max);
			thumbLength = MathHelper.clamp(thumbLength, 32, this.length - 8);
			thumbPos = (int) this.progress * (this.length - thumbLength) / maxScroll;
			thumbPos = Math.max(thumbPos, this.min);
		}

		if (this.orientation == Orientation.VERTICAL) {
			DrawableHelper.fill(matrices, this.x, this.y, this.x + WIDTH, this.y + this.length, ColorHelper.toHex(0));
			if (this.showThumb) {
				DrawableHelper.fill(matrices, this.x, thumbPos + thumbLength, this.x + WIDTH, thumbPos, ColorHelper.toHex(128));
				DrawableHelper.fill(matrices, this.x, thumbPos + thumbLength - 1, this.x + WIDTH - 1, thumbPos, ColorHelper.toHex(192));
			}
		} else {
			DrawableHelper.fill(matrices,  this.x, this.y, this.x + this.length, this.y + WIDTH, ColorHelper.toHex(0));
			if (this.showThumb) {
				DrawableHelper.fill(matrices, thumbPos + thumbLength, this.y, thumbPos, this.y + WIDTH, ColorHelper.toHex(128));
				DrawableHelper.fill(matrices, thumbPos + thumbLength - 1, this.y, thumbPos, this.y + WIDTH - 1, ColorHelper.toHex(192));
			}
		}

		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

	private int maxScroll() {
		return Math.max(0, this.max - this.length + 4);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
