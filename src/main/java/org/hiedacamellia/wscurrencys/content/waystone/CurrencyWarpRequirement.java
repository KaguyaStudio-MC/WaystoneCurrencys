package org.hiedacamellia.wscurrencys.content.waystone;

import io.github.lightman314.lightmanscurrency.api.money.MoneyAPI;
import io.github.lightman314.lightmanscurrency.api.money.value.MoneyValue;
import io.github.lightman314.lightmanscurrency.api.money.value.builtin.CoinValue;
import io.github.lightman314.lightmanscurrency.api.money.value.holder.IMoneyHolder;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class CurrencyWarpRequirement implements WarpRequirement {

    public MoneyValue getValue() {
        return value;
    }

    public void setValue(MoneyValue value) {
        this.value = value;
    }

    private MoneyValue value;

    public CurrencyWarpRequirement(int value) {
        this.value = CoinValue.fromNumber("main", value);
    }
    public CurrencyWarpRequirement(MoneyValue value) {
        this.value = value;
    }

    @Override
    public boolean canAfford(Player player) {
        if (player.isCreative()) return true;
        IMoneyHolder handler = MoneyAPI.getApi().GetPlayersMoneyHandler(player);
        return handler.getStoredMoney().containsValue(value) && handler.extractMoney(value, true).isEmpty();
    }

    @Override
    public void consume(Player player) {
        if (player.isCreative()) return;
        IMoneyHolder handler = MoneyAPI.getApi().GetPlayersMoneyHandler(player);
        handler.extractMoney(value, false);
    }

    @Override
    public void rollback(Player player) {
        if (player.isCreative()) return;
        IMoneyHolder handler = MoneyAPI.getApi().GetPlayersMoneyHandler(player);
        handler.insertMoney(value, false);
    }

    @Override
    public void appendHoverText(Player player, List<Component> list) {
        if (value.isFree()||value.isEmpty())return;
        MutableComponent moneyRequirementText = Component.translatable("gui.wscurrencys.currency.need", value.getString());
        moneyRequirementText.withStyle(canAfford(player) ? ChatFormatting.GREEN : ChatFormatting.RED);
        list.add(moneyRequirementText);
    }
}
