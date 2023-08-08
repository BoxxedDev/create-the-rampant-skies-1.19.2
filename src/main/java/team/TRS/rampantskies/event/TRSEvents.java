package team.TRS.rampantskies.event;

import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.TRS.rampantskies.RampantSkiesMod;
import team.TRS.rampantskies.entity.TRSEntities;
import team.TRS.rampantskies.entity.custom.SkyvengerEntity;

public class TRSEvents {
    @Mod.EventBusSubscriber(modid = RampantSkiesMod.MODID)
    public static class ForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = RampantSkiesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(TRSEntities.SKYVENGER.get(), SkyvengerEntity.setAttributes());
        }
    }
}
