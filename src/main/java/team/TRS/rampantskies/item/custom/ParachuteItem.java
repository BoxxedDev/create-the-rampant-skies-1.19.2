package team.TRS.rampantskies.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParachuteItem extends Item {
    private static boolean ISACTIVATED;

    public ParachuteItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        Vec3 pVec = player.getDeltaMovement();
        if (!player.isOnGround()) {
            player.setDeltaMovement(pVec.x, -0.2, pVec.z);
        }
        if (player.isOnGround()){
            player.setDeltaMovement(pVec.x, pVec.y, pVec.z);
        }



/*
        for (boolean ISACTIVE = false; ISACTIVE = true;) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 2));
            ISACTIVE = player.isOnGround();
        }

         */

        return super.use(level, player, hand);
    }
}
