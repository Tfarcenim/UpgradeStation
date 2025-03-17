package tfar.upgradestation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ScrollData(double chanceMultiplier, boolean protectsWeapon) {
    public static final Codec<ScrollData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("chance_multiplier").forGetter(ScrollData::chanceMultiplier),
            Codec.BOOL.fieldOf("protects_weapon").forGetter(ScrollData::protectsWeapon)
    ).apply(instance,ScrollData::new));
}
