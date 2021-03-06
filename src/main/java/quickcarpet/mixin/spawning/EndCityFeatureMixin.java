package quickcarpet.mixin.spawning;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.EndCityFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import quickcarpet.api.annotation.Feature;

import java.util.List;

@Feature("shulkerSpawningInEndCities")
@Mixin(EndCityFeature.class)
public abstract class EndCityFeatureMixin extends StructureFeature<DefaultFeatureConfig> {
    private static final List<SpawnSettings.SpawnEntry> spawnList = Lists.newArrayList(new SpawnSettings.SpawnEntry(EntityType.SHULKER, 10, 4, 4));

    public EndCityFeatureMixin(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return spawnList;
    }
}
