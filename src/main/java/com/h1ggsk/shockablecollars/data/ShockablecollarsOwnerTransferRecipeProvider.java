package com.h1ggsk.shockablecollars.data;

import com.google.gson.JsonObject;
import com.h1ggsk.shockablecollars.Shockablecollars;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ShockablecollarsOwnerTransferRecipeProvider implements DataProvider {
    private final DataOutput.PathResolver recipeResolver;

    public ShockablecollarsOwnerTransferRecipeProvider(FabricDataOutput output) {
        this.recipeResolver = output.getResolver(DataOutput.OutputType.DATA_PACK, "recipe");
    }

    /**
     * Writes the owner-transfer recipe so stamped deeds can link a crafted remote
     * using the same serializer behavior as PlayerCollars ownership crafting.
     */
    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "playercollars:owner_transfer");
        recipe.addProperty("category", "misc");
        recipe.addProperty("base", "shockablecollars:shock_remote");

        Path outputPath = recipeResolver.resolveJson(Shockablecollars.id("shock_remote_owner_transfer"));
        return DataProvider.writeToPath(writer, recipe, outputPath);
    }

    @Override
    public String getName() {
        return "ShockableCollars Owner Transfer Recipes";
    }
}
