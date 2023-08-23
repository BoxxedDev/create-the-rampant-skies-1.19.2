package team.TRS.rampantskies.block.entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.fluids.tank.FluidTankCTBehaviour;
import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.TRS.rampantskies.block.TRSBlocks;

import java.util.List;
import java.util.Properties;

public class CombustionChamberBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    protected LazyOptional<IFluidHandler> fluidCapability = LazyOptional.empty();
    protected FluidTank tankInventory = createFluidTank();



    protected SmartFluidTankBehaviour tankBehaviour;

    public CombustionChamberBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        tankBehaviour = SmartFluidTankBehaviour.single(this, 4000);
        behaviours.add(tankBehaviour);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking, fluidCapability);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        BlockState state = this.getBlockState();
        Direction direction = state.getValue(BlockStateProperties.FACING);
        if (this.isFluidHandlerCap(cap)) {
            if (direction == Direction.WEST || direction == Direction.EAST) {
                if (side == Direction.UP || side == Direction.DOWN || side == Direction.SOUTH || side == Direction.NORTH) {
                    return fluidCapability.cast();
                }
            } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                if (side == Direction.UP || side == Direction.DOWN || side == Direction.EAST || side == Direction.WEST) {
                    return fluidCapability.cast();
                }
            } else if (direction == Direction.UP || direction == Direction.DOWN) {
                if (side == Direction.NORTH || side == Direction.SOUTH || side == Direction.EAST || side == Direction.WEST) {
                    return fluidCapability.cast();
                }
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        fluidCapability = LazyOptional.of(() -> tankInventory);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fluidCapability.invalidate();
    }

    private FluidTank createFluidTank() {
        return new FluidTank(4000) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }
}
