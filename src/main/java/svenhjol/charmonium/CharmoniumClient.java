package svenhjol.charmonium;

import net.fabricmc.api.ClientModInitializer;
import svenhjol.charm.base.CharmClientLoader;

public class CharmoniumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        new CharmClientLoader(Charmonium.MOD_ID);
    }
}
