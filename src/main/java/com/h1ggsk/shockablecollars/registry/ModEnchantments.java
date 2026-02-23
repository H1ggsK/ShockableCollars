package com.h1ggsk.shockablecollars.registry;

import com.h1ggsk.shockablecollars.Shockablecollars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public final class ModEnchantments {
    public static final RegistryKey<Enchantment> SHOCK = RegistryKey.of(RegistryKeys.ENCHANTMENT, Shockablecollars.id("shock"));

    private ModEnchantments() {
    }
}
