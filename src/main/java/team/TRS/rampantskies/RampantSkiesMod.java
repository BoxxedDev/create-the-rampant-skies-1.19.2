package team.TRS.rampantskies;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;
import team.TRS.rampantskies.block.TRSBlocks;
import team.TRS.rampantskies.block.TRSBlockEntities;
import team.TRS.rampantskies.entity.TRSEntities;
import team.TRS.rampantskies.entity.client.skyvenger.SkyvengerRenderer;
import team.TRS.rampantskies.item.TRSItems;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RampantSkiesMod.MODID)
public class RampantSkiesMod {
    public static final String MODID = "rampantskies";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    public RampantSkiesMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        TRSBlocks.register();
        TRSItems.register();
        TRSEntities.register(modEventBus);
        TRSBlockEntities.register();

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        REGISTRATE.registerEventListeners(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(TRSEntities.SKYVENGER.get(), SkyvengerRenderer::new);

            TRSPartialModels.init();
        }
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
