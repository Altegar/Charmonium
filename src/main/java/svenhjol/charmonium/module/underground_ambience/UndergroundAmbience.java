package svenhjol.charmonium.module.underground_ambience;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import svenhjol.charmonium.annotation.Config;
import svenhjol.charmonium.annotation.Module;
import svenhjol.charmonium.module.CharmoniumModule;
import svenhjol.charmonium.module.underground_ambience.UndergroundSounds.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Module(description = "Ambient background sound plays when underground.")
public class UndergroundAmbience extends CharmoniumModule {
    public Handler handler;

    @Config(name = "Valid dimensions", description = "Dimensions in which underground ambience will be played.")
    public static List<String> configDimensions = Arrays.asList(
        "minecraft:overworld"
    );

    @Config(name = "Play cave ambience", description = "If true, the cave ambience will be played. Cave ambience is normally a low frequency drone.")
    public static boolean playCaveAmbience = true;

    @Config(name = "Play deepcave ambience", description = "If true, the deepcave ambience will be played. This takes effect when the player is deep underground and at low light level.")
    public static boolean playDeepCaveAmbience = true;

    @Config(name = "Cave depth", description = "When the player is lower than this depth then cave background sound will be always be triggered.")
    public static int caveDepth = 48;

    @Config(name = "Light level", description = "When the light is lower than this level then cave and deepcave background sound will be triggered.")
    public static int lightLevel = 10;

    public static List<ResourceLocation> validDimensions = new ArrayList<>();

    @Override
    public void init() {
        configDimensions.forEach(dim -> validDimensions.add(new ResourceLocation(dim)));

        ClientEntityEvents.ENTITY_LOAD.register(this::handleEntityLoad);
        ClientTickEvents.END_CLIENT_TICK.register(this::handleClientTick);
    }

    private void handleEntityLoad(Entity entity, Level level) {
        if (entity instanceof Player)
            trySetupSoundHandler((Player)entity);
    }

    private void handleClientTick(Minecraft client) {
        if (client.player != null && client.level != null && handler != null)
            handler.tick();
    }

    public void trySetupSoundHandler(Player player) {
        if (player instanceof LocalPlayer && handler == null)
            handler = new Handler(player);
    }

    public static class Handler {
        private final Player player;
        private final List<UndergroundSound> sounds = new ArrayList<>();

        public Handler(@NotNull Player player) {
            this.player = player;

            Cave.init(player, sounds);
            DeepCave.init(player, sounds);
        }

        public void tick() {
            if (!player.isAlive() || player.level == null)
                return;

            sounds.forEach(UndergroundSound::tick);
        }
    }
}
