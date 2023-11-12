package team.TRS.rampantskies.ponder;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TRSkineticScenes {
    public static void engineIntro(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("engine", "Setting up an engine");
        scene.configureBasePlate(1, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);

        int speed = 64;

        Selection kineticsSelection1 = util.select.fromTo(2,1,3,0,1,3);
        scene.world.setKineticSpeed(kineticsSelection1, speed);
        Selection kineticsSelection2 = util.select.position(0,0,4);
        scene.world.setKineticSpeed(kineticsSelection2, -speed);
        Selection kineticsSelection3 = util.select.position(2,1,4);
        scene.world.setKineticSpeed(kineticsSelection3, speed * -2);
        BlockPos intakePos = util.grid.at(2,1,2);
        Selection intake = util.select.position(intakePos);
        scene.world.setKineticSpeed(intake, -speed);

        BlockPos tankPos = util.grid.at(1,1,4);
        FluidStack content = new FluidStack(Fluids.WATER.getSource(), 8000);
        scene.world.modifyBlockEntity(tankPos, FluidTankBlockEntity.class, be -> be.getTankInventory().fill(content, IFluidHandler.FluidAction.EXECUTE));

        scene.idle(15);
        scene.world.showSection(util.select.layer(1), Direction.DOWN);
        scene.idle(5);

        scene.overlay.showText(60)
                .placeNearTarget()
                .text("Engines are a good way to propulse your airship.")
                .attachKeyFrame()
                .pointAt(util.vector.of(2.5,1.5,2.5));

        scene.idle(60);

        scene.rotateCameraY(-45);

        scene.idle(10);

        speed = 16;
        scene.world.setKineticSpeed(kineticsSelection1, speed);
        scene.world.setKineticSpeed(kineticsSelection2, -speed);
        scene.world.setKineticSpeed(kineticsSelection3, speed * -2);
        scene.world.setKineticSpeed(intake, -speed);

        scene.overlay.showText(1060)
                .placeNearTarget()
                .text("You can slow down")
                .attachKeyFrame()
                .pointAt(util.vector.of(2.5,1.5,2.5));

        scene.idle(60);

        speed = 256;
        scene.world.setKineticSpeed(kineticsSelection1, speed);
        scene.world.setKineticSpeed(kineticsSelection2, -speed);
        scene.world.setKineticSpeed(kineticsSelection3, speed * -2);
        scene.world.setKineticSpeed(intake, -speed);

        scene.overlay.showText(1000)
                .placeNearTarget()
                .text("Or speed up the air intake speed to slow or speed the propulsion.")
                .pointAt(util.vector.of(2.5,0.5,2.5));
    }
}
