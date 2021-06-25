package com.thundersphun.minecraftide.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.LiteralText;

public class EditorWidget extends TextFieldWidget {
	public EditorWidget(int x, int y, int width, int height) {
		super(MinecraftClient.getInstance().textRenderer, x, y, width, height, LiteralText.EMPTY);
	}
}
