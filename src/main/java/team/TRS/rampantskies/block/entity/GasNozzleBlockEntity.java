package team.TRS.rampantskies.block.entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class GasNozzleBlockEntity extends BlockEntity implements IHaveGoggleInformation {
    float thrust;
    float intake;

    public GasNozzleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    //thrust related stuff
    public float getThrust() {
        return thrust;
    }
    public void setThrust(float newThrust) {
        thrust = newThrust;
    }

    //gtt related stuff
    public void setIntake(float newIntake){intake = newIntake;}

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        LangBuilder ms = Lang.translate("generic.unit.meters_per_second");
        LangBuilder n = Lang.translate("generic.unit.newton");

        Lang.translate("gui.goggles.jet_engine.info")
                .forGoggles(tooltip);

        Lang.translate("gui.goggles.air_intake.air_flow")
                .add(Lang.number(intake)
                        .space()
                        .add(ms)
                        .style(ChatFormatting.GREEN))
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip, 1);

        Lang.translate("gui.goggles.gas_nozzle.thrust")
                .add(Lang.number(thrust)
                        .space()
                        .add(n)
                        .style(ChatFormatting.LIGHT_PURPLE))
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip, 1);
        return true;
    }
}
