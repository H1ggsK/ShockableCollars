package com.h1ggsk.shockablecollars.client;

import com.h1ggsk.shockablecollars.data.ShockablecollarsOwnerTransferRecipeProvider;
import com.h1ggsk.shockablecollars.data.ShockablecollarsRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ShockablecollarsDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ShockablecollarsRecipeProvider::new);
        pack.addProvider(ShockablecollarsOwnerTransferRecipeProvider::new);
    }
}
