package team.TRS.rampantskies.block.custom;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import team.TRS.rampantskies.block.TRSBlockEntities;
import team.TRS.rampantskies.block.entity.CombustionChamberBlockEntity;

public class CombustionChamberBlock extends CreateDirectionalBlock implements IBE<CombustionChamberBlockEntity> {
    public CombustionChamberBlock(Properties p_52591_) {
        super(p_52591_);
    }

    @Override
    public Class<CombustionChamberBlockEntity> getBlockEntityClass() {
        return CombustionChamberBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CombustionChamberBlockEntity> getBlockEntityType() {
        return TRSBlockEntities.COMBUSTION_CHAMBER_BLOCK_ENTITY.get();
    }
}
