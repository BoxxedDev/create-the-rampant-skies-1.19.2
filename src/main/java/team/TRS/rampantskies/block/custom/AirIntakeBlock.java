package team.TRS.rampantskies.block.custom;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import team.TRS.rampantskies.block.TRSBlockEntities;
import team.TRS.rampantskies.block.entity.AirIntakeBlockEntity;

public class AirIntakeBlock extends DirectionalKineticBlock
    implements ICogWheel, IBE<AirIntakeBlockEntity> {

    public AirIntakeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING)
                .getAxis();
    }

    @Override
    public Class<AirIntakeBlockEntity> getBlockEntityClass() {
        return AirIntakeBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AirIntakeBlockEntity> getBlockEntityType() {
        return TRSBlockEntities.AIR_INTAKE.get();
    }
}
