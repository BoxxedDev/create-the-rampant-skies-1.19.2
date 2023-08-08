package team.TRS.rampantskies.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.CubeVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import team.TRS.rampantskies.item.TRSItems;

import javax.swing.*;
import java.util.function.Function;

public class RumBarrel extends HorizontalDirectionalBlock {

    public RumBarrel(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        ItemStack itemStack = new ItemStack(player.getItemInHand(hand).getItem());
        ItemStack rumStack = new ItemStack(TRSItems.RUM_BOTTLE.get());
        if (itemStack.getItem() == Items.GLASS_BOTTLE) {
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                if (!player.getInventory().add(rumStack)) {
                    player.drop(rumStack, false);
                }
            } else {
                if (!((Player) player).getAbilities().instabuild) {

                    if (!player.getInventory().add(rumStack)) {
                        player.drop(rumStack, false);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }


        return super.use(blockState, level, blockPos, player, hand, blockHitResult);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
