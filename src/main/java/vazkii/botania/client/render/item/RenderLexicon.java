/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Mar 25, 2014, 8:49:01 PM (GMT)]
 *//*

package vazkii.botania.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.gui.lexicon.GuiLexicon;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.item.ItemLexicon;
import vazkii.botania.common.item.ModItems;

public class RenderLexicon implements IItemRenderer {

	ModelBook model = new ModelBook();
	ResourceLocation texture = new ResourceLocation(LibResources.MODEL_LEXICA);

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GlStateManager.pushMatrix();
		Minecraft mc = Minecraft.getMinecraft();
		mc.renderEngine.bindTexture(texture);
		float opening = 0F;
		float pageFlip = 0F;

		float ticks = ClientTickHandler.ticksWithLexicaOpen;
		if(ticks > 0 && ticks < 10) {
			if(mc.currentScreen instanceof GuiLexicon)
				ticks += ClientTickHandler.partialTicks;
			else ticks -= ClientTickHandler.partialTicks;
		}

		GlStateManager.translate(0.3F + 0.02F * ticks, 0.475F + 0.01F * ticks, -0.2F - 0.01F * ticks);
		GlStateManager.rotate(87.5F + ticks * 5, 0F, 1F, 0F);
		GlStateManager.rotate(ticks * 2.5F, 0F, 0F, 1F);
		GlStateManager.scale(0.9F, 0.9F, 0.9F);
		opening = ticks / 12F;

		float pageFlipTicks = ClientTickHandler.pageFlipTicks;
		if(pageFlipTicks > 0)
			pageFlipTicks -= ClientTickHandler.partialTicks;

		pageFlip = pageFlipTicks / 5F;

		model.render(null, 0F, 0F, pageFlip, opening, 0F, 1F / 16F);
		if(ticks < 3) {
			FontRenderer font = Minecraft.getMinecraft().fontRenderer;
			GlStateManager.rotate(180F, 0F, 0F, 1F);
			GlStateManager.translate(-0.3F, -0.21F, -0.07F);
			GlStateManager.scale(0.0035F, 0.0035F, -0.0035F);
			boolean bevo = Minecraft.getMinecraft().thePlayer.getName().equalsIgnoreCase("BevoLJ");
			boolean saice = Minecraft.getMinecraft().thePlayer.getName().equalsIgnoreCase("saice");

			String title = ModItems.lexicon.getItemStackDisplayName(null);
			String origTitle = title;

			if(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null)
				title = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getDisplayName();
			if(title.equals(origTitle) && bevo)
				title = StatCollector.translateToLocal("item.botania:lexicon.bevo");
			if(title.equals(origTitle) && saice)
				title = StatCollector.translateToLocal("item.botania:lexicon.saice");

			font.drawString(font.trimStringToWidth(title, 80), 0, 0, 0xD69700);
			GlStateManager.translate(0F, 10F, 0F);
			GlStateManager.scale(0.6F, 0.6F, 0.6F);
			font.drawString(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.BOLD + String.format(StatCollector.translateToLocal("botaniamisc.edition"), ItemLexicon.getEdition()), 0, 0, 0xA07100);

			GlStateManager.translate(0F, 15F, 0F);
			font.drawString(StatCollector.translateToLocal("botaniamisc.lexiconcover0"), 0, 0, 0x79ff92);

			GlStateManager.translate(0F, 10F, 0F);
			font.drawString(StatCollector.translateToLocal("botaniamisc.lexiconcover1"), 0, 0, 0x79ff92);

			GlStateManager.translate(0F, 50F, 0F);
			font.drawString(StatCollector.translateToLocal("botaniamisc.lexiconcover2"), 0, 0, 0x79ff92);

			GlStateManager.translate(0F, 10F, 0F);
			font.drawString(EnumChatFormatting.UNDERLINE + "" + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("botaniamisc.lexiconcover3"), 0, 0, 0x79ff92);

			if(bevo || saice) {
				GlStateManager.translate(0F, 10F, 0F);
				font.drawString(StatCollector.translateToLocal("botaniamisc.lexiconcover" + (bevo ? 4 : 5)), 0, 0, 0x79ff92);
			}
		}

		GlStateManager.popMatrix();
	}

}
*/
