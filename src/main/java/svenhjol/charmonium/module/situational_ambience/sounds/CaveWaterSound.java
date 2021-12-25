package svenhjol.charmonium.module.situational_ambience.sounds;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import svenhjol.charmonium.handler.SoundHandler;
import svenhjol.charmonium.registry.ClientRegistry;
import svenhjol.charmonium.helper.WorldHelper;
import svenhjol.charmonium.module.situational_ambience.SituationalSound;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class CaveWaterSound extends SituationalSound {
    public static SoundEvent SOUND;

    private CaveWaterSound(Player player, Predicate<SituationalSound> validCondition, Function<SituationalSound, SoundEvent> soundCondition) {
        super(player, validCondition, soundCondition);
    }

    public static void init(SoundHandler<SituationalSound> handler) {
        SOUND = ClientRegistry.sound("situational.cave_water");

        Predicate<SituationalSound> validCondition = situation -> {
            Player player = situation.getPlayer();
            ClientLevel level = situation.getLevel();

            if (WorldHelper.isOutside(player)) return false;
            if (player.isUnderWater()) return false;
            if (!WorldHelper.isBelowSeaLevel(player)) return false;

            Optional<BlockPos> optWater = BlockPos.findClosestMatch(player.blockPosition(), 12, 8, pos -> {
                Block block = level.getBlockState(pos).getBlock();
                return block == Blocks.WATER;
            });

            if (optWater.isPresent()) {
                situation.setPos(optWater.get());
                return true;
            }

            return false;
        };

        Function<SituationalSound, SoundEvent> soundCondition = situation -> SOUND;
        handler.getSounds().add(new CaveWaterSound(handler.getPlayer(), validCondition, soundCondition));
    }

    @Override
    public int getDelay() {
        return level.random.nextInt(150) + 120;
    }

    @Override
    public float getVolume() {
        return 0.3F;
    }

    @Override
    public float getPitch() {
        return 0.77F + (0.3F * level.random.nextFloat());
    }
}