package team.TRS.rampantskies.block.entity;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.TRS.rampantskies.block.TRSBlockEntities;
import team.TRS.rampantskies.block.TRSBlocks;

import java.util.List;
import java.util.Optional;

public class CombustionChamberBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    protected LazyOptional<IFluidHandler> fluidCapability;
    float intake;
    float intakePerUnitRPM = 5;
    float intakePerUnitSpeed = 0;
    float speed = 0;
    float smoothing;
    float rpm;
    float thrust;

    SmartFluidTankBehaviour tank;

    public CombustionChamberBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        tank = SmartFluidTankBehaviour.single(this, 4000);
        behaviours.add(tank);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        LazyOptional<IFluidHandler> handler = (LazyOptional<IFluidHandler>) tank.getCapability();
        Optional<IFluidHandler> resolve = handler.resolve();
        if (!resolve.isPresent())
            return false;

        IFluidHandler tank = resolve.get();
        if (tank.getTanks() == 0)
            return false;

        LangBuilder mb = Lang.translate("generic.unit.millibuckets");
        LangBuilder ms = Lang.translate("generic.unit.meters_per_second");
        LangBuilder n = Lang.translate("generic.unit.newton");

        Lang.translate("gui.goggles.combustion_chamber.info")
                .forGoggles(tooltip);

        boolean isEmpty = true;
        for (int i = 0; i < tank.getTanks(); i++) {
            FluidStack fluidStack = tank.getFluidInTank(i);
            if (fluidStack.isEmpty())
                continue;

            Lang.fluidName(fluidStack)
                    .style(ChatFormatting.GRAY)
                    .forGoggles(tooltip, 1);

            Lang.builder()
                    .add(Lang.number(fluidStack.getAmount())
                            .add(mb)
                            .style(ChatFormatting.GOLD))
                    .text(ChatFormatting.GRAY, " / ")
                    .add(Lang.number(tank.getTankCapacity(i))
                            .add(mb)
                            .style(ChatFormatting.DARK_GRAY))
                    .forGoggles(tooltip, 1);

            isEmpty = false;
        }

        if (tank.getTanks() > 1) {
            if (isEmpty)
                tooltip.remove(tooltip.size() - 1);
            return true;
        }

        if (!isEmpty)
            return true;

        Lang.translate("gui.goggles.fluid_container.capacity")
                .add(Lang.number(tank.getTankCapacity(0))
                        .add(mb)
                        .style(ChatFormatting.GOLD))
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip, 1);

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

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        BlockState state = this.getBlockState();
        Direction direction = state.getValue(BlockStateProperties.FACING);
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            if (direction == Direction.WEST || direction == Direction.EAST) {
                if (side == Direction.UP || side == Direction.DOWN || side == Direction.SOUTH || side == Direction.NORTH) {
                    return tank.getCapability().cast();
                }
            } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                if (side == Direction.UP || side == Direction.DOWN || side == Direction.EAST || side == Direction.WEST) {
                    return tank.getCapability().cast();
                }
            } else if (direction == Direction.UP || direction == Direction.DOWN) {
                if (side == Direction.NORTH || side == Direction.SOUTH || side == Direction.EAST || side == Direction.WEST) {
                    return tank.getCapability().cast();
                }
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void tick() {
        BlockState state = this.getBlockState();
        Direction direction = state.getValue(BlockStateProperties.FACING);
        BlockPos currentPos = this.getBlockPos();
        BlockState down = this.getLevel().getBlockState(currentPos.below());
        BlockState up = this.getLevel().getBlockState(currentPos.above());
        BlockState east = this.getLevel().getBlockState(currentPos.east());
        BlockState west = this.getLevel().getBlockState(currentPos.west());
        BlockState north = this.getLevel().getBlockState(currentPos.north());
        BlockState south = this.getLevel().getBlockState(currentPos.south());

        AirIntakeBlockEntity intakeEntity = null;
        GasNozzleBlockEntity nozzleEntity = null;

        thrust = 0;

        if (direction == Direction.UP || direction == Direction.DOWN) {
            if (down.is(TRSBlocks.AIR_INTAKE.get()) && down.getValue(BlockStateProperties.FACING) == Direction.DOWN) {
                if (this.getLevel().getBlockEntity(currentPos.below()) instanceof AirIntakeBlockEntity) {
                    intakeEntity = (AirIntakeBlockEntity) this.getLevel().getBlockEntity(currentPos.below());
                }
                if (up.is(TRSBlocks.GAS_NOZZLE.get()) && up.getValue(BlockStateProperties.FACING) == Direction.UP) {
                    if (this.getLevel().getBlockEntity(currentPos.above()) instanceof GasNozzleBlockEntity) {
                        nozzleEntity = (GasNozzleBlockEntity) this.getLevel().getBlockEntity(currentPos.above());
                    }
                }
            } else if (up.is(TRSBlocks.AIR_INTAKE.get()) && up.getValue(BlockStateProperties.FACING) == Direction.UP) {
                if (this.getLevel().getBlockEntity(currentPos.above()) instanceof AirIntakeBlockEntity) {
                    intakeEntity = (AirIntakeBlockEntity) this.getLevel().getBlockEntity(currentPos.above());
                }
                if (down.is(TRSBlocks.GAS_NOZZLE.get()) && down.getValue(BlockStateProperties.FACING) == Direction.DOWN) {
                    if (this.getLevel().getBlockEntity(currentPos.below()) instanceof GasNozzleBlockEntity) {
                        nozzleEntity = (GasNozzleBlockEntity) this.getLevel().getBlockEntity(currentPos.below());
                    }
                }
            }
        } else if (direction == Direction.WEST || direction == Direction.EAST) {
            if (west.is(TRSBlocks.AIR_INTAKE.get()) && west.getValue(BlockStateProperties.FACING) == Direction.WEST) {
                if (this.getLevel().getBlockEntity(currentPos.west()) instanceof AirIntakeBlockEntity) {
                    intakeEntity = (AirIntakeBlockEntity) this.getLevel().getBlockEntity(currentPos.west());
                }
                if (east.is(TRSBlocks.GAS_NOZZLE.get()) && east.getValue(BlockStateProperties.FACING) == Direction.EAST) {
                    if (this.getLevel().getBlockEntity(currentPos.east()) instanceof GasNozzleBlockEntity) {
                        nozzleEntity = (GasNozzleBlockEntity) this.getLevel().getBlockEntity(currentPos.east());
                    }
                }
            } else if (east.is(TRSBlocks.AIR_INTAKE.get()) && east.getValue(BlockStateProperties.FACING) == Direction.EAST) {
                if (this.getLevel().getBlockEntity(currentPos.east()) instanceof AirIntakeBlockEntity) {
                    intakeEntity = (AirIntakeBlockEntity) this.getLevel().getBlockEntity(currentPos.east());
                }
                if (west.is(TRSBlocks.GAS_NOZZLE.get()) && west.getValue(BlockStateProperties.FACING) == Direction.WEST) {
                    if (this.getLevel().getBlockEntity(currentPos.west()) instanceof GasNozzleBlockEntity) {
                        nozzleEntity = (GasNozzleBlockEntity) this.getLevel().getBlockEntity(currentPos.west());
                    }
                }
            }
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            if (north.is(TRSBlocks.AIR_INTAKE.get()) && north.getValue(BlockStateProperties.FACING) == Direction.NORTH) {
                if (this.getLevel().getBlockEntity(currentPos.north()) instanceof AirIntakeBlockEntity) {
                    intakeEntity = (AirIntakeBlockEntity) this.getLevel().getBlockEntity(currentPos.north());
                }
                if (south.is(TRSBlocks.GAS_NOZZLE.get()) && south.getValue(BlockStateProperties.FACING) == Direction.SOUTH) {
                    if (this.getLevel().getBlockEntity(currentPos.south()) instanceof GasNozzleBlockEntity) {
                        nozzleEntity = (GasNozzleBlockEntity) this.getLevel().getBlockEntity(currentPos.south());
                    }
                }
            } else if (south.is(TRSBlocks.AIR_INTAKE.get()) && south.getValue(BlockStateProperties.FACING) == Direction.SOUTH) {
                if (this.getLevel().getBlockEntity(currentPos.south()) instanceof AirIntakeBlockEntity) {
                    intakeEntity = (AirIntakeBlockEntity) this.getLevel().getBlockEntity(currentPos.south());
                }
                if (north.is(TRSBlocks.GAS_NOZZLE.get()) && north.getValue(BlockStateProperties.FACING) == Direction.NORTH) {
                    if (this.getLevel().getBlockEntity(currentPos.north()) instanceof GasNozzleBlockEntity) {
                        nozzleEntity = (GasNozzleBlockEntity) this.getLevel().getBlockEntity(currentPos.north());
                    }
                }
            }
        }
        if (nozzleEntity != null) {
            if (intake > 0) {
                nozzleEntity.setThrust(rpm);
            } else {
                nozzleEntity.setThrust(0);
            }
            nozzleEntity.setIntake(intake);
            thrust = nozzleEntity.getThrust();
        }
        if (intakeEntity != null) {
            intakeEntity.setIntake(intake);
            intakeEntity.setThrust(thrust);
            rpm = intakeEntity.getSpeed();
            if (rpm < 0) {rpm = -rpm;}
        }


        if (rpm != 0) {
            intake = (intakePerUnitRPM * rpm) + (intakePerUnitSpeed * speed * (rpm/(rpm + smoothing)));
        } else {
            intake = 0;
        }


        super.tick();
    }
}
