package com.h1ggsk.shockablecollars.data;

import com.h1ggsk.shockablecollars.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ShockablecollarsRecipeProvider extends FabricRecipeProvider {
    public ShockablecollarsRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {
            /**
             * Generates the base Shock Remote crafting recipe.
             */
            @Override
            public void generate() {
                createShaped(RecipeCategory.MISC, ModItems.SHOCK_REMOTE)
                        .input('R', Items.REDSTONE)
                        .input('C', Items.COPPER_INGOT)
                        .input('I', Items.IRON_INGOT)
                        .pattern(" R ")
                        .pattern("RCR")
                        .pattern(" I ")
                        .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "ShockableCollars Recipes";
    }
}
