package team.TRS.rampantskies.item;

import com.simibubi.create.AllItems;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import team.TRS.rampantskies.item.custom.ParachuteItem;
import team.TRS.rampantskies.item.custom.RumBottleItem;
import static team.TRS.rampantskies.RampantSkiesMod.REGISTRATE;

public class TRSItems {
    static {
        REGISTRATE.creativeModeTab(() -> CreativeTabs.TRS_TAB);
    }

    public static final ItemEntry<RumBottleItem> RUM_BOTTLE = REGISTRATE.item("rum_bottle", RumBottleItem::new)
            .properties(p -> p.stacksTo(1))
            .register();
    public static final ItemEntry<Item> RAZOR_FANG = REGISTRATE.item("razor_fang", Item::new)
            .register();
    public static final ItemEntry<Item> LEVIATHAN_BLUBBER = REGISTRATE.item("leviathan_blubber", Item::new)
            .register();
    public static final ItemEntry<Item> FLYING_SACK = REGISTRATE.item("flying_sack", Item::new)
            .register();
    public static final ItemEntry<ParachuteItem> PARACHUTE = REGISTRATE.item("parachute", ParachuteItem::new)
            .register();
    public static final ItemEntry<Item> TOUGH_HIDE = REGISTRATE.item("tough_hide", Item::new)
            .register();

    public static void register() {}
}
