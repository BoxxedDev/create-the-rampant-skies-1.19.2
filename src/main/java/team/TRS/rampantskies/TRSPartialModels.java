package team.TRS.rampantskies;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.Create;

public class TRSPartialModels {
    public static final PartialModel
    INTAKE_FAN = block("air_intake/fan")

            ;

    private static PartialModel block(String path) {
        return new PartialModel(RampantSkiesMod.asResource("block/" + path));
    }

    public static void init() {}
}
