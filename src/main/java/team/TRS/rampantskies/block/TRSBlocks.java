package team.TRS.rampantskies.block;

import com.simibubi.create.*;
import com.simibubi.create.content.contraptions.bearing.SailBlock;
import com.simibubi.create.content.fluids.PipeAttachmentModel;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import com.simibubi.create.content.fluids.pump.PumpRenderer;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.render.AllInstanceFormats;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MaterialColor;
import team.TRS.rampantskies.block.custom.*;
import team.TRS.rampantskies.item.CreativeTabs;

import java.util.function.ToIntFunction;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
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

    public static final BlockEntry<AirIntakeBlock> AIR_INTAKE = REGISTRATE.block("air_intake", AirIntakeBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.noOcclusion().color(MaterialColor.STONE))
            .addLayer(() -> RenderType::cutout)
            .transform(pickaxeOnly())
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.getEntry(), prov.models()
                    .getExistingFile(ctx.getId()), 0))
            .transform(BlockStressDefaults.setImpact(4))
            .simpleItem()
            .register();

    public static final BlockEntry<CombustionChamberBlock> GAS_COMBUSTION_CHAMBER = REGISTRATE.block("gas_combustion_chamber", CombustionChamberBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.color(MaterialColor.STONE).noOcclusion())
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.getEntry(), prov.models()
                    .getExistingFile(ctx.getId()), 0))
            .simpleItem()
            .register();

    public static final BlockEntry<GasNozzleBlock> GAS_NOZZLE = REGISTRATE.block("gas_nozzle", GasNozzleBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.color(MaterialColor.STONE).noOcclusion())
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.getEntry(), prov.models()
                    .getExistingFile(ctx.getId()), 0))
            .simpleItem()
            .register();


    public static void register() {}
}
