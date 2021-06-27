package svenhjol.charmonium.module.situational_ambience.sounds;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import svenhjol.charmonium.helper.DimensionHelper;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.helper.WorldHelper;
import svenhjol.charmonium.module.situational_ambience.SituationalSound;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class DeepslateSound extends SituationalSound {
    public static SoundEvent SOUND;

    private DeepslateSound(Player player, Predicate<SituationalSound> validCondition, Function<SituationalSound, SoundEvent> soundCondition) {
        super(player, validCondition, soundCondition);
    }

    public static void init(Player player, List<SituationalSound> sounds) {
        SOUND = RegistryHelper.sound("situational.deepslate");

        Predicate<SituationalSound> validCondition = situation -> {
            ClientLevel level = situation.getLevel();

            if (!DimensionHelper.isOverworld(level))
                return false;

            if (WorldHelper.isOutside(player))
                return false;

            Optional<BlockPos> optBlock = BlockPos.findClosestMatch(player.blockPosition(), 8, 4, pos -> {
                Block block = level.getBlockState(pos).getBlock();
                return block == Blocks.DEEPSLATE;
            });

            return optBlock.isPresent();
        };

        Function<SituationalSound, SoundEvent> soundCondition = situation -> SOUND;
        sounds.add(new DeepslateSound(player, validCondition, soundCondition));
    }

    @Override
    public int getDelay() {
        return level.random.nextInt(350) + 400;
    }

    @Override
    public float getVolume() {
        return 0.35F;
    }
}