package svenhjol.charmonium.module.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import svenhjol.charm.mixin.accessor.AbstractTickableSoundInstanceAccessor;

public class ShortSound extends AbstractTickableSoundInstance {
    private final LocalPlayer player;

    public ShortSound(LocalPlayer player, SoundEvent sound, float volume) {
        this(player, sound, volume, 1.0F);
    }

    public ShortSound(LocalPlayer player, SoundEvent sound, float volume, float pitch) {
        super(sound, SoundSource.AMBIENT);
        this.player = player;
        this.looping = false;
        this.delay = 0;
        this.volume = volume;
        this.pitch = pitch;
        this.relative = true;
    }

    @Override
    public void tick() {
        if (!this.player.isAlive())
            ((AbstractTickableSoundInstanceAccessor)this).setStopped(true);
    }
}
