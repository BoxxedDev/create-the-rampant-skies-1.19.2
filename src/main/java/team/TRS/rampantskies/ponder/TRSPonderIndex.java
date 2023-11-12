package team.TRS.rampantskies.ponder;

import com.simibubi.create.Create;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import com.simibubi.create.infrastructure.ponder.PonderIndex;
import team.TRS.rampantskies.RampantSkiesMod;
import team.TRS.rampantskies.block.TRSBlocks;

public class TRSPonderIndex {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(RampantSkiesMod.MODID);

    public static void register() {
        HELPER.forComponents(TRSBlocks.AIR_INTAKE)
                .addStoryBoard("jet_engine", TRSkineticScenes::engineIntro, TRSPonderTags.ENGINE);
    }
}
