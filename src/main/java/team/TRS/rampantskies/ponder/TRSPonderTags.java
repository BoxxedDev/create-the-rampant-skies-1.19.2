package team.TRS.rampantskies.ponder;

import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.pump.PumpRenderer;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.tterrag.registrate.util.entry.BlockEntry;
import team.TRS.rampantskies.block.TRSBlocks;

public class TRSPonderTags {
    public static final PonderTag

        ENGINE = create("").item(TRSBlocks.AIR_INTAKE.get())
            .defaultLang("Engine Blocks", "Components used  for propulsion on airships")
            .addToIndex();

    private static PonderTag create(String id) {
        return new PonderTag(Create.asResource(id));
    }

    public static void register() {
        PonderRegistry.TAGS.forTag(ENGINE)
                .add(TRSBlocks.AIR_INTAKE)
                .add(TRSBlocks.GAS_COMBUSTION_CHAMBER)
                .add(TRSBlocks.GAS_NOZZLE);
    }
}
