package vazkii.botania.common.integration.buildcraft;

import com.google.common.base.Predicates;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.client.core.handler.MiscellaneousIcons;
import vazkii.botania.common.Botania;
import vazkii.botania.common.lib.LibTriggerNames;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.ITriggerInternal;

public class TriggerManaDetector extends StatementBase implements ITriggerInternal {

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getGuiSprite() {
		return MiscellaneousIcons.INSTANCE.manaDetectorIcon;
	}

	@Override
	public String getUniqueTag() {
		return "botania:manaDetector";
	}

	@Override
	public String getDescription() {
		return StatCollector.translateToLocal(LibTriggerNames.TRIGGER_MANA_DETECTOR);
	}

	@Override
	public boolean isTriggerActive(IStatementContainer source, IStatementParameter[] parameters) {
		World world = source.getTile().getWorld();
		int x = source.getTile().getPos().getX(), y = source.getTile().getPos().getY(), z = source.getTile().getPos().getZ();

		boolean output = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1), Predicates.instanceOf(IManaBurst.class)).size() != 0;

		if(output) for(int i = 0; i < 4; i++)
			Botania.proxy.sparkleFX(world, x + Math.random(), y + Math.random(), z + Math.random(), 1F, 0.2F, 0.2F, 0.7F + 0.5F * (float) Math.random(), 5);

		return output;
	}

}
