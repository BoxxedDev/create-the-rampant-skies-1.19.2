package team.TRS.rampantskies.block.entity.renderer;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.bearing.MechanicalBearingBlockEntity;
import com.simibubi.create.content.fluids.pump.PumpRenderer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.TRS.rampantskies.TRSPartialModels;
import team.TRS.rampantskies.block.TRSBlockEntities;
import team.TRS.rampantskies.block.entity.AirIntakeBlockEntity;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class AirIntakeBlockRenderer extends KineticBlockEntityRenderer<AirIntakeBlockEntity> {
    public AirIntakeBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(AirIntakeBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (Backend.canUseInstancing(be.getLevel())) return;

        Direction direction = be.getBlockState().getValue(FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());

        int lightMiddle = LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos());
        int lightInFront = LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos().relative(direction));

        SuperByteBuffer fan = CachedBufferer.partialFacing(TRSPartialModels.INTAKE_FAN, be.getBlockState(), direction.getOpposite());
        SuperByteBuffer cog = CachedBufferer.partialFacing(AllPartialModels.MECHANICAL_PUMP_COG, be.getBlockState(), direction.getOpposite());

        float time = AnimationTickHolder.getRenderTime(be.getLevel());
        float speed = be.getSpeed() * 1.5f;
        if (speed > 0) {
            speed = -speed;
        }
        float angle = (time * speed * 3 / 10f) % 360;
        angle = angle / 180f * (float) Math.PI;

        standardKineticRotationTransform(cog, be, lightMiddle).renderInto(ms, vb);
        kineticRotationTransform(fan, be, direction.getAxis(), angle, lightInFront).renderInto(ms, vb);
    }
}
