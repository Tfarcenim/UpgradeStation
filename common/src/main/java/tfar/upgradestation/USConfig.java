package tfar.upgradestation;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class USConfig {
    public static final USConfig CONFIG;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        final Pair<USConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(USConfig::new);
        SERVER_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public static ScrollData getScrollData(Item item) {
        return CONFIG.scroll_map.get().get(BuiltInRegistries.ITEM.getKey(item));
    }

    public static Set<ResourceLocation> getScrolls() {
        return CONFIG.scroll_map.get().keySet();
    }

    private final ConfigHelper.ConfigObject<Map<ResourceLocation, ScrollData>> scroll_map;

    public USConfig(ModConfigSpec.Builder builder)  {
        builder.push("general");
        scroll_map = ConfigHelper.defineObject(builder,"scroll_map", Codec.unboundedMap(ResourceLocation.CODEC,ScrollData.CODEC),defaults());
        builder.pop();
    }

    static Map<ResourceLocation, ScrollData> defaults() {
        Map<ResourceLocation, ScrollData> map =  new HashMap<>();
        map.put(UpgradeStation.id("scroll_i"),new ScrollData(.25,false));
        map.put(UpgradeStation.id("scroll_ii"),new ScrollData(.5,false));
        map.put(UpgradeStation.id("scroll_iii"),new ScrollData(.75,false));
        map.put(UpgradeStation.id("scroll_iv"),new ScrollData(1,false));

        map.put(UpgradeStation.id("scroll_of_protection_i"),new ScrollData(.25,true));
        map.put(UpgradeStation.id("scroll_of_protection_ii"),new ScrollData(.5,true));
        map.put(UpgradeStation.id("scroll_of_protection_iii"),new ScrollData(.75,true));
        map.put(UpgradeStation.id("scroll_of_protection_iv"),new ScrollData(1,true));
        return map;
    }



}
