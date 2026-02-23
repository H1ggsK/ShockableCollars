package com.h1ggsk.shockablecollars;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import com.h1ggsk.shockablecollars.registry.ModItems;
import com.h1ggsk.shockablecollars.registry.ModSounds;

public class Shockablecollars implements ModInitializer {
    public static final String MOD_ID = "shockablecollars";

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModSounds.register();
        ModItems.register();
    }
}
