package com.keeg4n.biomfinder;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.BiomeDictionary;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Строки в левой колонке F3:
 * <ul>
 * <li>биом и его ID;</li>
 * <li>температура биома и температура в этой точке (выше над морем холоднее - так считает игра для снега и т.п.);</li>
 * <li>влажность, осадки (дождь, снег или сухо);</li>
 * <li>типы биома из словаря Forge (по ним ищут биомы многие моды).</li>
 * </ul>
 */
@SideOnly(Side.CLIENT)
public class DebugLines {

    @SubscribeEvent
    public void onText(RenderGameOverlayEvent.Text e) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.gameSettings.showDebugInfo || mc.theWorld == null || mc.thePlayer == null) return;
        int x = MathHelper.floor_double(mc.thePlayer.posX), y = MathHelper.floor_double(mc.thePlayer.posY),
            z = MathHelper.floor_double(mc.thePlayer.posZ);
        if (!mc.theWorld.blockExists(x, y, z)) return;
        BiomeGenBase b = mc.theWorld.getBiomeGenForCoords(x, z);
        if (b == null) return;
        e.left.add("");
        e.left.add(t("biomfinder.biome", b.biomeName, b.biomeID));
        e.left.add(t("biomfinder.temperature", f(b.temperature), f(b.getFloatTemperature(x, y, z))));
        e.left.add(t("biomfinder.humidity", f(b.rainfall), StatCollector.translateToLocal(
            b.getEnableSnow() ? "biomfinder.snow" : b.canSpawnLightningBolt() ? "biomfinder.rain" : "biomfinder.dry")));
        BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(b);
        if (types != null && types.length > 0) {
            StringBuilder s = new StringBuilder();
            for (BiomeDictionary.Type ty : types) s.append(s.length() == 0 ? "" : ", ").append(ty.name());
            e.left.add(t("biomfinder.types", s.toString()));
        }
    }

    private static String f(float v) {
        return String.format("%.2f", v);
    }

    private static String t(String key, Object... args) {
        return StatCollector.translateToLocalFormatted(key, args);
    }
}
