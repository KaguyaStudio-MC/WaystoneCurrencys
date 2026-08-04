package org.hiedacamellia.wscurrencys.core.event;

import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.api.event.WaystoneTeleportEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.hiedacamellia.wscurrencys.core.config.WSCCommonConfig;

@EventBusSubscriber
public class WayStoneEventHandler {

    @SubscribeEvent
    public static void onTeleportPre(WaystoneTeleportEvent.Pre event){

        if(WSCCommonConfig.EnableCurrencyConsumption.isFalse())return;
        WaystoneTeleportContext context = event.getContext();

        if (context.getEntity() instanceof Player) {
            Player player = (Player) context.getEntity();
            if (player.isCreative()) return;
            if (!event.getRequirements().canAfford(player)){
                event.setCanceled(true);
            }
        }
    }

}
