package team.TRS.rampantskies.block.entity.instance;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.fan.EncasedFanRenderer;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import team.TRS.rampantskies.TRSPartialModels;
import team.TRS.rampantskies.block.entity.AirIntakeBlockEntity;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class AirIntakeInstance extends SingleRotatingInstance<AirIntakeBlockEntity> {
    protected final RotatingData fan;
    protected final RotatingData cog;
    final Direction direction;
    private final Direction opposite;

    public AirIntakeInstance(MaterialManager materialManager, AirIntakeBlockEntity blockEntity) {
        super(materialManager, blockEntity);

        direction = blockState.getValue(FACING);

        opposite = direction.getOpposite();
        cog = getRotatingMaterial().getModel(AllPartialModels.MECHANICAL_PUMP_COG, blockState, opposite).createInstance();
        fan = materialManager.defaultCutout()
                .material(AllMaterialSpecs.ROTATING)
                .getModel(TRSPartialModels.INTAKE_FAN, blockState, opposite)
                .createInstance();

        setup(fan);
        setup(cog);
    }

    @Override
    public void update() {
        updateRotation(fan);
        updateRotation(cog);
    }

    @Override
    public void updateLight() {
        BlockPos behind = pos.relative(opposite);
        relight(behind);
        BlockPos inFront = pos.relative(direction);
        relight(inFront);
    }

    @Override
    public void remove() {
        fan.delete();
        cog.delete();
    }
}
