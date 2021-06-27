package svenhjol.charmonium.module.situational_ambience.sounds;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import svenhjol.charmonium.helper.DimensionHelper;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.helper.WorldHelper;
import svenhjol.charmonium.module.situational_ambience.SituationalSound;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class VillageSound extends SituationalSound {
    public static SoundEvent SOUND;

    private VillageSound(Player player, Predicate<SituationalSound> validCondition, Function<SituationalSound, SoundEvent> soundCondition) {
        super(player, validCondition, soundCondition);
    }

    public static void init(Player player, List<SituationalSound> sounds) {
        SOUND = RegistryHelper.sound("situational.village");

        Predicate<SituationalSound> validCondition = situation -> {
            ClientLevel level = situation.getLevel();

            if (!DimensionHelper.isOverworld(level))
                return false;

            if (WorldHelper.isNight(player))
                return false;

            AABB bb = new AABB(player.blockPosition()).inflate(32);
            List<Villager> villagers = level.getEntitiesOfClass(Villager.class, bb);
            return villagers.size() >= 2;
        };

        Function<SituationalSound, SoundEvent> soundCondition = situation -> SOUND;
        sounds.add(new VillageSound(player, validCondition, soundCondition));
    }

    @Override
    public int getDelay() {
        return level.random.nextInt(500) + 320;
    }

    @Override
    public float getVolume() {
        return 0.5F;
    }
}