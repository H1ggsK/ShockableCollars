package com.h1ggsk.shockablecollars.registry;

import com.h1ggsk.shockablecollars.Shockablecollars;
import com.h1ggsk.shockablecollars.item.ShockRemoteItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public final class ModItems {
    public static final RegistryKey<Item> SHOCK_REMOTE_KEY = RegistryKey.of(RegistryKeys.ITEM, Shockablecollars.id("shock_remote"));
    public static final Item SHOCK_REMOTE = new ShockRemoteItem(new Item.Settings().maxCount(1).registryKey(SHOCK_REMOTE_KEY));

    private ModItems() {
    }

    public static void register() {
        Registry.register(Registries.ITEM, SHOCK_REMOTE_KEY, SHOCK_REMOTE);
    }
}
