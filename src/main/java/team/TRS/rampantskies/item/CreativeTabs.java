package team.TRS.rampantskies.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.TRS.rampantskies.RampantSkiesMod;
import team.TRS.rampantskies.block.TRSBlocks;

@Mod.EventBusSubscriber(modid = RampantSkiesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabs {
    public static CreativeModeTab TRS_TAB = new CreativeModeTab("trs_tab") {

        @Override
        public ItemStack makeIcon() {
            return TRSItems.LEVIATHAN_BLUBBER.asStack();
        }
    };
}
