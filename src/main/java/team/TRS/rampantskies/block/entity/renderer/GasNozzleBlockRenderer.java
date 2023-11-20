package team.TRS.rampantskies.block.entity.renderer;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.core.util.SystemMillisClock;
import team.TRS.rampantskies.TRSPartialModels;
import team.TRS.rampantskies.block.entity.GasNozzleBlockEntity;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.type.LodestoneParticleType;

import java.awt.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class GasNozzleBlockRenderer extends SafeBlockEntityRenderer<GasNozzleBlockEntity> {
    public GasNozzleBlockRenderer(BlockEntityRendererProvider.Context context) {
        super();
    }

    @Override
    protected void renderSafe(GasNozzleBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        Direction direction = be.getBlockState().getValue(FACING).getOpposite();

        BlockPos relativePos = be.getBlockPos().relative(direction);
        Vector3f velocityVec = new Vector3f(((be.getBlockPos().getX() - relativePos.getX()) * (be.getThrust() / 512)),
                ((be.getBlockPos().getY() - relativePos.getY()) * (be.getThrust() / 4096)),
                ((be.getBlockPos().getZ() - relativePos.getZ()) * (be.getThrust() / 4096)));
        if (be.getThrust() > 0) {
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.45f, 0).build())
                    .setColorData(ColorParticleData.create(new Color(102,245,0), new Color(239, 252, 230)).build())
                    .setMotion(velocityVec.x(), velocityVec.y(), velocityVec.z())
                    .setLifetime((int) be.getThrust() / 32)
                    .enableNoClip()
                    .spawn(be.getLevel(), relativePos.getX() + 0.5, relativePos.getY() + 0.5, relativePos.getZ() + 0.5);
        }
    }
}
