package team.TRS.rampantskies.entity.client;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import team.TRS.rampantskies.RampantSkiesMod;
import team.TRS.rampantskies.entity.custom.SkyvengerEntity;

public class SkyvengerModel extends AnimatedGeoModel<SkyvengerEntity> {
    @Override
    public ResourceLocation getModelResource(SkyvengerEntity object) {
        return new ResourceLocation(RampantSkiesMod.MODID, "geo/skyvenger.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SkyvengerEntity object) {
        return new ResourceLocation(RampantSkiesMod.MODID, "textures/entity/skyvenger.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SkyvengerEntity animatable) {
        return new ResourceLocation(RampantSkiesMod.MODID, "animations/skyvenger.animation.json");
    }
}
