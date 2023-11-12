package team.TRS.rampantskies.block.custom;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import team.TRS.rampantskies.block.TRSBlockEntities;
import team.TRS.rampantskies.block.entity.GasNozzleBlockEntity;

public class GasNozzleBlock extends CreateDirectionalBlock implements IBE<GasNozzleBlockEntity> {
    public GasNozzleBlock(Properties p_52591_) {
        super(p_52591_);
    }

    @Override
    public Class<GasNozzleBlockEntity> getBlockEntityClass() {
        return GasNozzleBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends GasNozzleBlockEntity> getBlockEntityType() {

        return TRSBlockEntities.GAS_NOZZLE_BLOCK_ENTITY.get();
    }
}
