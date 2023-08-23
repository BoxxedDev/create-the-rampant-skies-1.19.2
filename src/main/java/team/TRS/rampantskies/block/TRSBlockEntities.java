package team.TRS.rampantskies.block;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import team.TRS.rampantskies.block.entity.AirIntakeBlockEntity;
import team.TRS.rampantskies.block.entity.CombustionChamberBlockEntity;
import team.TRS.rampantskies.block.entity.instance.AirIntakeInstance;
import team.TRS.rampantskies.block.entity.renderer.AirIntakeBlockRenderer;

import static team.TRS.rampantskies.RampantSkiesMod.REGISTRATE;

public class TRSBlockEntities {
    public static final BlockEntityEntry<AirIntakeBlockEntity> AIR_INTAKE = REGISTRATE
            .blockEntity("air_intake", AirIntakeBlockEntity::new)
            //.instance(() -> AirIntakeInstance::new)
            .validBlocks(TRSBlocks.AIR_INTAKE)
            .renderer(() -> AirIntakeBlockRenderer::new)
            .register();

    public static final BlockEntityEntry<CombustionChamberBlockEntity> COMBUSTION_CHAMBER_BLOCK_ENTITY = REGISTRATE
            .blockEntity("combustion_chamber_block_entity", CombustionChamberBlockEntity::new)
            .validBlocks(TRSBlocks.GAS_COMBUSTION_CHAMBER)
            .register();

    public static void register() {}
}
