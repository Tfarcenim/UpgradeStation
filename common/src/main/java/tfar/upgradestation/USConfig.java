package tfar.upgradestation;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class USConfig {
    public static final USConfig CONFIG;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<USConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(USConfig::new);
        SERVER_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public final ConfigHelper.ConfigObject<Map<Item, ScrollData>> scroll_map;

    public USConfig(ForgeConfigSpec.Builder builder)  {
        builder.push("general");
        scroll_map = ConfigHelper.defineObject(builder,"scroll_map", Codec.unboundedMap(BuiltInRegistries.ITEM.byNameCodec(),ScrollData.CODEC),defaults());
        builder.pop();
    }

    static Map<Item, ScrollData> defaults() {
        Map<Item, ScrollData> map =  new HashMap<>();
        map.put(Init.SCROLL_I,new ScrollData(.25,false));
        map.put(Init.SCROLL_II,new ScrollData(.5,false));
        map.put(Init.SCROLL_III,new ScrollData(.75,false));
        map.put(Init.SCROLL_IV,new ScrollData(1,false));

        map.put(Init.SCROLL_OF_PROTECTION_I,new ScrollData(.25,true));
        map.put(Init.SCROLL_OF_PROTECTION_II,new ScrollData(.5,true));
        map.put(Init.SCROLL_OF_PROTECTION_III,new ScrollData(.75,true));
        map.put(Init.SCROLL_OF_PROTECTION_IV,new ScrollData(1,true));
        return map;
    }



}
