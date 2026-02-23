package com.h1ggsk.shockablecollars.item;

import com.h1ggsk.shockablecollars.registry.ModDamageTypes;
import com.h1ggsk.shockablecollars.registry.ModEnchantments;
import com.h1ggsk.shockablecollars.registry.ModSounds;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jlortiz.playercollars.OwnerComponent;
import org.jlortiz.playercollars.PlayerCollarsMod;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class ShockRemoteItem extends Item {
    public ShockRemoteItem(Settings settings) {
        super(settings);
    }

    /**
     * Triggers a linked remote zap on the linked player when the caller is the recorded owner,
     * the target is online, the target is wearing an owned collar, and that collar has the shock enchantment.
     */
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        OwnerComponent ownerComponent = stack.get(PlayerCollarsMod.OWNER_COMPONENT_TYPE);
        if (ownerComponent != null && ownerComponent.owned().isPresent() && !ownerComponent.uuid().equals(user.getUuid())) {
            return ActionResult.FAIL;
        }

        user.setCurrentHand(hand);

        if (world instanceof ServerWorld serverWorld) {
            playRemoteSound(world, user, ModSounds.REMOTE_ON);
            if (ownerComponent != null) {
                tryZapLinkedTarget(serverWorld, user, stack, ownerComponent);
            }
        }

        return ActionResult.FAIL;
    }

    /**
     * Finds a collar stack worn by the target and owned by the caller.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ItemStack getOwnedCollarStack(PlayerEntity owner, ServerPlayerEntity target) {
        Object capability = getAccessoriesCapability(target);
        if (capability == null) {
            return null;
        }
        Iterable<?> equipped = getEquippedCollars(capability);
        if (equipped == null) {
            return null;
        }
        return PlayerCollarsMod.filterStacksByOwner((Iterable) equipped, owner.getUuid(), target.getUuid());
    }

    /**
     * Reads and clamps the shock enchantment level from a collar stack.
     */
    private static int getShockLevel(ServerWorld world, ItemStack collarStack) {
        RegistryEntryLookup<Enchantment> enchantments = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        Optional<RegistryEntry.Reference<Enchantment>> shock = enchantments.getOptional(ModEnchantments.SHOCK);
        if (shock.isEmpty()) {
            return 0;
        }
        return Math.min(EnchantmentHelper.getLevel(shock.get(), collarStack), 3);
    }

    /**
     * Attempts the linked shock action once on press and applies cooldown when a shock occurs.
     */
    private static void tryZapLinkedTarget(ServerWorld serverWorld, PlayerEntity user, ItemStack remoteStack, OwnerComponent ownerComponent) {
        if (ownerComponent.owned().isEmpty()) {
            return;
        }

        UUID linkedTargetUuid = ownerComponent.owned().get();
        ServerPlayerEntity linkedTarget = serverWorld.getServer().getPlayerManager().getPlayer(linkedTargetUuid);
        if (linkedTarget == null) {
            return;
        }

        ItemStack collarStack = getOwnedCollarStack(user, linkedTarget);
        if (collarStack == null || collarStack.isEmpty()) {
            return;
        }

        ServerWorld targetWorld = linkedTarget.getServerWorld();
        int shockLevel = getShockLevel(targetWorld, collarStack);
        if (shockLevel <= 0) {
            return;
        }

        float damage = Math.min(shockLevel, 3) * 2.0F;
        if (linkedTarget.damage(targetWorld, ModDamageTypes.shock(targetWorld, user), damage)) {
            targetWorld.playSound(null, linkedTarget.getX(), linkedTarget.getY(), linkedTarget.getZ(), ModSounds.ZAP, SoundCategory.PLAYERS, 1.0F, 1.0F);
            user.getItemCooldownManager().set(remoteStack, 20);
        }
    }

    /**
     * Plays the remote toggle sound centered on the user.
     */
    private static void playRemoteSound(World world, PlayerEntity user, net.minecraft.sound.SoundEvent sound) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    /**
     * Resolves the Accessories capability in a soft-dependent way.
     */
    private static Object getAccessoriesCapability(ServerPlayerEntity target) {
        try {
            Class<?> capabilityClass = Class.forName("io.wispforest.accessories.api.AccessoriesCapability");
            return capabilityClass.getMethod("get", net.minecraft.entity.LivingEntity.class).invoke(null, target);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    /**
     * Fetches equipped collar accessory entries without requiring a hard compile-time Accessories dependency.
     */
    private static Iterable<?> getEquippedCollars(Object capability) {
        try {
            Predicate<ItemStack> collarPredicate = stack -> stack.isIn(PlayerCollarsMod.COLLAR_TAG);
            Object result = capability.getClass().getMethod("getEquipped", Predicate.class).invoke(capability, collarPredicate);
            if (result instanceof Iterable<?> iterable) {
                return iterable;
            }
            return null;
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    @Override
    public Text getName(ItemStack stack) {
        OwnerComponent ownerComponent = stack.get(PlayerCollarsMod.OWNER_COMPONENT_TYPE);
        if (ownerComponent == null || ownerComponent.ownedName().isEmpty()) {
            return super.getName(stack);
        }
        return Text.translatable("item.shockablecollars.shock_remote.linked", ownerComponent.ownedName().get());
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient() && user instanceof PlayerEntity player) {
            playRemoteSound(world, player, ModSounds.REMOTE_OFF);
        }
        return false;
    }
}
