package svenhjol.charmonium.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import svenhjol.charmonium.mixin.accessor.AbstractTickableSoundInstanceAccessor;

public class SingleSound extends AbstractTickableSoundInstance {
    private final Player player;

    public SingleSound(Player player, SoundEvent sound, float volume) {
        this(player, sound, volume, 1.0F, null);
    }

    public SingleSound(Player player, SoundEvent sound, float volume, float pitch, @Nullable BlockPos pos) {
        super(sound, SoundSource.AMBIENT);

        this.player = player;
        this.looping = false;
        this.delay = 0;
        this.pitch = pitch;
        this.volume = volume;

        if (pos != null) {
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
        } else {
            this.relative = true;
        }
    }

    @Override
    public void tick() {
        if (player.level == null || !player.isAlive())
            ((AbstractTickableSoundInstanceAccessor)this).setStopped(true);
    }
}
