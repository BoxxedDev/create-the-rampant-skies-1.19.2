package team.TRS.rampantskies.block.entity.renderer;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.core.util.SystemMillisClock;
import team.TRS.rampantskies.TRSPartialModels;
import team.TRS.rampantskies.block.entity.GasNozzleBlockEntity;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class GasNozzleBlockRenderer extends SafeBlockEntityRenderer<GasNozzleBlockEntity> {
    public GasNozzleBlockRenderer(BlockEntityRendererProvider.Context context) {
        super();
    }

    @Override
    protected void renderSafe(GasNozzleBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        Direction direction = be.getBlockState().getValue(FACING);

        SuperByteBuffer exhaust = CachedBufferer.partialFacing(TRSPartialModels.EXHAUST, be.getBlockState(), direction.getOpposite());

        VertexConsumer vb = bufferSource.getBuffer(RenderType.cutoutMipped());

        int lightBack = LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos().relative(direction.getOpposite()));



        Vector3f particleVec = new Vector3f((float) (be.getBlockPos().getX() + 0.5), (float) (be.getBlockPos().getY() + 0.5), (float) (be.getBlockPos().getZ() + 0.5));

        float particleAcc = 0.05F;

            createExhaustFlame(exhaust, be, direction.getAxis(), (float) (be.getThrust() / (be.getThrust() * 1.1)), lightBack).renderInto(ms, vb);
            //be.getLevel().addParticle(ParticleTypes.CLOUD, (double) particleVec.x(), (double) particleVec.y(), (double) particleVec.z(), (double) direction.getNormal().getX() * particleAcc, (double) direction.getNormal().getY() * particleAcc, (double) direction.getNormal().getZ() * particleAcc);
            be.getLevel().addParticle(ParticleTypes.SMOKE, (double) particleVec.x(), (double) particleVec.y(), (double) particleVec.z(), (double) direction.getNormal().getX() * particleAcc, (double) direction.getNormal().getY() * particleAcc, (double) direction.getNormal().getZ() * particleAcc);
    }

    private SuperByteBuffer createExhaustFlame(SuperByteBuffer buffer, GasNozzleBlockEntity be, Direction.Axis axis, float size, int lightBack) {
        Direction direction = be.getBlockState().getValue(FACING);
        double translate = 1.5;

        buffer.light(999999999);
        buffer.translate(direction.getNormal().getX() / translate, direction.getNormal().getY() / translate, direction.getNormal().getZ() / translate);
        buffer.scale(size);
        buffer.rotateCentered(direction, AnimationTickHolder.getRenderTime(be.getLevel()) * 10);

        return buffer;
    }
}
