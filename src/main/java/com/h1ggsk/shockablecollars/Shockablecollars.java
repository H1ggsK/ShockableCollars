package com.h1ggsk.shockablecollars;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import com.h1ggsk.shockablecollars.registry.ModItems;
import com.h1ggsk.shockablecollars.registry.ModSounds;

public class Shockablecollars implements ModInitializer {
    public static final String MOD_ID = "shockablecollars";
    private static final RegistryKey<ItemGroup> PLAYERCOLLARS_GROUP_KEY =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of("playercollars", "group"));

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModSounds.register();
        ModItems.register();
        ItemGroupEvents.modifyEntriesEvent(PLAYERCOLLARS_GROUP_KEY).register(entries -> entries.add(ModItems.SHOCK_REMOTE));
    }
}
