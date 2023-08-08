package team.TRS.rampantskies.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.TRS.rampantskies.RampantSkiesMod;
import team.TRS.rampantskies.entity.custom.SkyvengerEntity;

public class TRSEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RampantSkiesMod.MODID);

    public static final RegistryObject<EntityType<SkyvengerEntity>> SKYVENGER = ENTITY_TYPES.register("skyvenger"
    ,() -> EntityType.Builder.of(SkyvengerEntity::new, MobCategory.MONSTER)
                    .sized(2f, 1.2f)
                    .build(String.valueOf(new ResourceLocation(RampantSkiesMod.MODID, "skyvenger"))));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
