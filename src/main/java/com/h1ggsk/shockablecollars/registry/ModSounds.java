package com.h1ggsk.shockablecollars.registry;

import com.h1ggsk.shockablecollars.Shockablecollars;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSounds {
    public static final SoundEvent REMOTE_ON = register("remote_on");
    public static final SoundEvent REMOTE_OFF = register("remote_off");
    public static final SoundEvent ZAP = register("zap");

    private ModSounds() {
    }

    private static SoundEvent register(String path) {
        Identifier id = Shockablecollars.id(path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {
    }
}
