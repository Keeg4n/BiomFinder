package com.keeg4n.biomfinder;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;

/**
 * BiomFinder: в экране F3 - температура и влажность текущего биома (и с поправкой на высоту), его ID и типы, чтобы
 * точно воспроизводить биом без угадайки. Только клиент: на сервер ставить не нужно, сервер без него пускает.
 */
@Mod(modid = BiomFinder.MODID, name = "BiomFinder", version = "1.0.0", acceptableRemoteVersions = "*")
public class BiomFinder {

    public static final String MODID = "biomfinder";

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        if (e.getSide().isClient()) MinecraftForge.EVENT_BUS.register(new DebugLines());
    }
}
