package tfar.upgradestation.platform;

import io.github.lightman314.lightmanscurrency.api.money.MoneyAPI;
import io.github.lightman314.lightmanscurrency.api.money.bank.IBankAccount;
import io.github.lightman314.lightmanscurrency.api.money.bank.reference.BankReference;
import io.github.lightman314.lightmanscurrency.api.money.value.MoneyStorage;
import io.github.lightman314.lightmanscurrency.api.money.value.MoneyValue;
import io.github.lightman314.lightmanscurrency.api.money.value.MoneyView;
import io.github.lightman314.lightmanscurrency.api.money.value.builtin.CoinValue;
import io.github.lightman314.lightmanscurrency.api.money.value.builtin.CoinValuePair;
import io.github.lightman314.lightmanscurrency.api.money.value.holder.IMoneyHolder;
import io.github.lightman314.lightmanscurrency.common.capability.wallet.IWalletHandler;
import io.github.lightman314.lightmanscurrency.common.capability.wallet.WalletCapability;
import io.github.lightman314.lightmanscurrency.common.core.ModItems;
import io.github.lightman314.lightmanscurrency.common.data.types.BankDataCache;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.units.qual.C;
import tfar.upgradestation.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.ArrayList;
import java.util.List;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public boolean hasAtLeast(Player player, int amount) {

        BankReference bankReference =  BankDataCache.TYPE.get(player.level().isClientSide).getSelectedAccount(player);

        IBankAccount iBankAccount = bankReference.get();
        if (iBankAccount != null) {
            MoneyStorage moneyStorage = iBankAccount.getMoneyStorage();
            List<CoinValuePair> coinValuePairs = new ArrayList<>();
            coinValuePairs.add(new CoinValuePair(ModItems.COIN_COPPER.get(),amount));
            MoneyValue moneyValue = CoinValue.create("main", coinValuePairs);

            return moneyStorage.containsValue(moneyValue);
        }
        return false;
    }

    @Override
    public void takeMoney(Player player, int amount) {
        BankReference bankReference =  BankDataCache.TYPE.get(player.level().isClientSide).getSelectedAccount(player);

        IBankAccount iBankAccount = bankReference.get();
        if (iBankAccount != null) {
            List<CoinValuePair> coinValuePairs = new ArrayList<>();
            coinValuePairs.add(new CoinValuePair(ModItems.COIN_COPPER.get(),amount));
            MoneyValue moneyValue = CoinValue.create("main", coinValuePairs);
            iBankAccount.withdrawMoney(moneyValue);
        }
    }
}