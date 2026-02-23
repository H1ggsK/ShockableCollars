package com.h1ggsk.shockablecollars.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

public class ShockDamageSource extends DamageSource {
    private static final String SHOCK_KEY = "death.attack.shockablecollars.shock";

    public ShockDamageSource(RegistryEntry<DamageType> type, Entity attacker) {
        super(type, attacker);
    }

    /**
     * Produces a fixed-format death message for shock damage with attacker and weapon context.
     */
    @Override
    public Text getDeathMessage(LivingEntity killed) {
        Entity attacker = this.getAttacker();
        if (attacker != null) {
            ItemStack weapon = this.getWeaponStack();
            if (weapon != null && !weapon.isEmpty()) {
                return Text.translatable(SHOCK_KEY + ".item", killed.getDisplayName(), attacker.getDisplayName(), weapon.toHoverableText());
            }
            return Text.translatable(SHOCK_KEY + ".player", killed.getDisplayName(), attacker.getDisplayName());
        }
        return Text.translatable(SHOCK_KEY, killed.getDisplayName());
    }
}
