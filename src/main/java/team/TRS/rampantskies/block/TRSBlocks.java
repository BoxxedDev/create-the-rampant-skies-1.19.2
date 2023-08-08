package team.TRS.rampantskies.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.bearing.BlankSailBlockItem;
import com.simibubi.create.content.contraptions.bearing.SailBlock;
import com.simibubi.create.content.decoration.encasing.EncasedBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.content.redstone.RoseQuartzLampBlock;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import cpw.mods.modlauncher.TestingLaunchHandlerService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.TRS.rampantskies.RampantSkiesMod;
import team.TRS.rampantskies.block.custom.RumBarrel;
import team.TRS.rampantskies.block.custom.TestKineticBlock;
import team.TRS.rampantskies.item.CreativeTabs;
import team.TRS.rampantskies.item.TRSItems;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static team.TRS.rampantskies.RampantSkiesMod.REGISTRATE;

public class TRSBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> CreativeTabs.TRS_TAB);
    }

    private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
        return (p_50763_) -> {
            return p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
        };
    }

    private static Boolean always(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_, EntityType<?> p_50813_) {
        return (boolean)true;
    }

    public static final RegistryEntry<RumBarrel> RUM_BARREL = REGISTRATE.block("rum_barrel", RumBarrel::new)
            .initialProperties(() -> Blocks.OAK_PLANKS)
            .simpleItem()
            .lang("Barrel O' Rum")
            .register();

    public static final RegistryEntry<RedstoneLampBlock> BLUBBER_LANTERN = REGISTRATE.block("blubber_lantern", RedstoneLampBlock::new)
            .initialProperties(() -> Blocks.REDSTONE_LAMP)
            .properties(p -> p.color(MaterialColor.TERRACOTTA_WHITE)
                .lightLevel(s -> s.getValue(RedstoneLampBlock.LIT) ? 30 : 0))
            .blockstate((c, p) -> BlockStateGen.simpleBlock(c, p, s -> {
                boolean lit = s.getValue(RedstoneLampBlock.LIT);
                String name = c.getName() + (lit ? "_on" : "_off");
                return p.models()
                        .cubeBottomTop(name, p.modLoc("block/" + name), p.modLoc("block/" + c.getName() + "_bottom"), p.modLoc("block/" + c.getName() + "_top"));
            }))
            .simpleItem()
            .register();

    public static final RegistryEntry<Block> LEVITHINE_BLOCK = REGISTRATE.block("levithine_block", Block::new)
            .simpleItem()
            .register();

    public static final BlockEntry<SailBlock> LEVIATHAN_SAIL = REGISTRATE.block("leviathan_sail", p -> SailBlock.withCanvas(p, DyeColor.GRAY))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.color(MaterialColor.DIRT))
            .properties(p -> p.sound(SoundType.SCAFFOLDING)
                    .noOcclusion())
            .transform(axeOnly())
            .blockstate(BlockStateGen.directionalBlockProvider(false))
            .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .simpleItem()
            .register();

    public static void register() {}
}
