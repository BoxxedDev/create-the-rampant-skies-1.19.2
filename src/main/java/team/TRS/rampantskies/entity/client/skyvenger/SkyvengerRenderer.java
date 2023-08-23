package team.TRS.rampantskies.entity.client.skyvenger;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import team.TRS.rampantskies.RampantSkiesMod;
import team.TRS.rampantskies.entity.custom.SkyvengerEntity;

public class SkyvengerRenderer extends GeoEntityRenderer<SkyvengerEntity> {


    public SkyvengerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SkyvengerModel());
        this.shadowRadius = 1f;
    }

    @Override
    public ResourceLocation getTextureLocation(SkyvengerEntity instance) {
        return new ResourceLocation(RampantSkiesMod.MODID, "textures/entity/skyvenger.png");
    }

    @Override
    public RenderType getRenderType(SkyvengerEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
