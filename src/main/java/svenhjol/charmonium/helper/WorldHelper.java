package svenhjol.charmonium.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class WorldHelper {
    public static boolean isDay(Player player) {
        long dayTime = player.level.getDayTime() % 24000;
        return dayTime >= 0 && dayTime < 12700;
    }

    public static boolean isNight(Player player) {
        long dayTime = player.level.getDayTime() % 24000;
        return dayTime >= 12700;
    }

    public static boolean isOutside(Player player) {
        if (!DimensionHelper.isOverworld(player.level))
            return false; // TODO: configurable outdoor dimensions

        if (player.isUnderWater()) return false;

        int blocks = 16;
        int start = 1;

        BlockPos playerPos = player.blockPosition();

        for (int i = start; i < start + blocks; i++) {
            BlockPos check = new BlockPos(playerPos.getX(), playerPos.getY() + i, playerPos.getZ());
            BlockState state = player.level.getBlockState(check);
            Block block = state.getBlock();

            if (player.level.canSeeSkyFromBelowWater(check)) return true;
            if (player.level.isEmptyBlock(check)) continue;

            // TODO: configurable clear blocks
            if (state.getMaterial() == Material.GLASS
                || (block instanceof RotatedPillarBlock && state.getMaterial() == Material.WOOD)
                || block instanceof HugeMushroomBlock
                || block instanceof StemBlock
            ) continue;

            if (state.canOcclude()) return false;
        }

        return player.blockPosition().getY() >= 48;
    }

    public static double getDistanceSquared(BlockPos pos1, BlockPos pos2) {
        double d0 = pos1.getX();
        double d1 = pos1.getZ();
        double d2 = d0 - pos2.getX();
        double d3 = d1 - pos2.getZ();
        return d2 * d2 + d3 * d3;
    }
}
