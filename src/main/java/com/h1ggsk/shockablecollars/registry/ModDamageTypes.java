package com.h1ggsk.shockablecollars.registry;

import com.h1ggsk.shockablecollars.Shockablecollars;
import com.h1ggsk.shockablecollars.damage.ShockDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

public final class ModDamageTypes {
    public static final RegistryKey<DamageType> SHOCK = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Shockablecollars.id("shock"));

    private ModDamageTypes() {
    }

    public static DamageSource shock(ServerWorld world, Entity attacker) {
        RegistryEntryLookup<DamageType> damageTypes = world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE);
        RegistryEntry<DamageType> damageType = damageTypes.getOrThrow(SHOCK);
        return new ShockDamageSource(damageType, attacker);
    }
}
