/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Nov 14, 2014, 7:05:32 PM (GMT)]
 */
package vazkii.botania.client.core.handler;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.opengl.GL11;

import vazkii.botania.common.block.tile.string.TileRedString;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibObfuscation;

public final class RedStringRenderer {

	public static final Queue<TileRedString> redStringTiles = new ArrayDeque<>();
	static float sizeAlpha = 0F;

	public static void renderAll() {
		if(!redStringTiles.isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			GL11.glPushAttrib(GL11.GL_LIGHTING);
			GlStateManager.disableLighting();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(1F, 0F, 0F, sizeAlpha);

			// todo 1.8 Tessellator.renderingWorldRenderer = false;
			TileRedString tile;
			while((tile = redStringTiles.poll()) != null)
				renderTile(tile);

			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			GL11.glPopAttrib();
			GlStateManager.popMatrix();

		}
	}

	public static void tick() {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		boolean hasWand = player != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.twigWand;
		if(sizeAlpha > 0F && !hasWand)
			sizeAlpha -= 0.1F;
		else if(sizeAlpha < 1F &&hasWand)
			sizeAlpha += 0.1F;
	}

	private static double getRenderPosX() { // todo 1.8
		return ObfuscationReflectionHelper.getPrivateValue(RenderManager.class, Minecraft.getMinecraft().getRenderManager(), LibObfuscation.RENDERPOSX);
	}

	private static double getRenderPosY() {
		return ObfuscationReflectionHelper.getPrivateValue(RenderManager.class, Minecraft.getMinecraft().getRenderManager(), LibObfuscation.RENDERPOSY);
	}

	private static double getRenderPosZ() {
		return ObfuscationReflectionHelper.getPrivateValue(RenderManager.class, Minecraft.getMinecraft().getRenderManager(), LibObfuscation.RENDERPOSZ);
	}

	private static void renderTile(TileRedString tile) {
		EnumFacing dir = tile.getOrientation();
		BlockPos bind = tile.getBinding();

		if(bind != null) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(tile.getPos().getX() + 0.5 - getRenderPosX(), tile.getPos().getY() + 0.5 - getRenderPosY(), tile.getPos().getZ() + 0.5 - getRenderPosZ());
			Vector3 vecOrig = new Vector3(bind.getX() - tile.getPos().getX(), bind.getY() - tile.getPos().getY(), bind.getZ() - tile.getPos().getZ());
			Vector3 vecNorm = vecOrig.copy().normalize();
			Vector3 vecMag = vecNorm.copy().multiply(0.025);
			Vector3 vecApply = vecMag.copy();

			int stages = (int) (vecOrig.mag() / vecMag.mag());

			Tessellator tessellator = Tessellator.getInstance();
			GL11.glLineWidth(1F);
			tessellator.getWorldRenderer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);

			double len = (double) -ClientTickHandler.ticksInGame / 100F + new Random(dir.ordinal() ^ tile.getPos().hashCode()).nextInt(10000);
			double add = vecMag.mag();
			double rand = Math.random() - 0.5;
			for(int i = 0; i < stages; i++) {
				addVertexAtWithTranslation(tessellator, dir, vecApply.x, vecApply.y, vecApply.z, rand, len);
				rand = Math.random() - 0.5;
				vecApply.add(vecMag);
				len += add;
				addVertexAtWithTranslation(tessellator, dir, vecApply.x, vecApply.y, vecApply.z, rand, len);
			}

			tessellator.draw();

			GlStateManager.popMatrix();
		}
	}

	private static void addVertexAtWithTranslation(Tessellator tess, EnumFacing dir, double xpos, double ypos, double zpos, double rand, double l) {
		double freq = 20;
		double ampl = (0.15 * (Math.sin(l * 2F) * 0.5 + 0.5) + 0.1) * sizeAlpha;
		double randMul = 0.05;
		double x = xpos + Math.sin(l * freq) * ampl * Math.abs(Math.abs(dir.getFrontOffsetX()) - 1) + rand * randMul;
		double y = ypos + Math.cos(l * freq) * ampl * Math.abs(Math.abs(dir.getFrontOffsetY()) - 1) + rand * randMul;
		double z = zpos + (dir.getFrontOffsetY() == 0 ? Math.sin(l * freq) : Math.cos(l * freq)) * ampl * Math.abs(Math.abs(dir.getFrontOffsetZ()) - 1) + rand * randMul;

		tess.getWorldRenderer().pos(x, y, z).endVertex();
	}

}
