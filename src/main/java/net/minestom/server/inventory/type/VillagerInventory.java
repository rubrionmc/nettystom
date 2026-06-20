// Package declaration for this file
package net.minestom.server.inventory.type;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.InventoryType;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.TradeListPacket;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collections;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class VillagerInventory extends Inventory {
    // Calls a method
    private final CachedPacket tradeCache = new CachedPacket(this::createTradePacket);
    // Calls a method
    private final List<TradeListPacket.Trade> trades = new ArrayList<>();
    // Code statement
    private int villagerLevel;
    // Code statement
    private int experience;
    // Code statement
    private boolean regularVillager;
    // Code statement
    private boolean canRestock;

    // Start of a method/block
    public VillagerInventory(Component title) {
        // Access to the current/parent object
        super(InventoryType.MERCHANT, title);
    // End of a block/expression
    }

    // Start of a method/block
    public VillagerInventory(String title) {
        // Access to the current/parent object
        super(InventoryType.MERCHANT, title);
    // End of a block/expression
    }

    // Start of a method/block
    public List<TradeListPacket.Trade> getTrades() {
        // Returns a value to the caller
        return Collections.unmodifiableList(trades);
    // End of a block/expression
    }

    // Start of a method/block
    public void addTrade(TradeListPacket.Trade trade) {
        // Access to the current/parent object
        this.trades.add(trade);
        // Calls a method
        update();
    // End of a block/expression
    }

    // Start of a method/block
    public void removeTrade(int index) {
        // Access to the current/parent object
        this.trades.remove(index);
        // Calls a method
        update();
    // End of a block/expression
    }

    // Start of a method/block
    public int getVillagerLevel() {
        // Returns a value to the caller
        return villagerLevel;
    // End of a block/expression
    }

    // Start of a method/block
    public void setVillagerLevel(int level) {
        // Access to the current/parent object
        this.villagerLevel = level;
        // Calls a method
        update();
    // End of a block/expression
    }

    // Start of a method/block
    public int getExperience() {
        // Returns a value to the caller
        return experience;
    // End of a block/expression
    }

    // Start of a method/block
    public void setExperience(int experience) {
        // Access to the current/parent object
        this.experience = experience;
        // Calls a method
        update();
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isRegularVillager() {
        // Returns a value to the caller
        return regularVillager;
    // End of a block/expression
    }

    // Start of a method/block
    public void setRegularVillager(boolean regularVillager) {
        // Access to the current/parent object
        this.regularVillager = regularVillager;
        // Calls a method
        update();
    // End of a block/expression
    }

    // Start of a method/block
    public boolean canRestock() {
        // Returns a value to the caller
        return canRestock;
    // End of a block/expression
    }

    // Start of a method/block
    public void setCanRestock(boolean canRestock) {
        // Access to the current/parent object
        this.canRestock = canRestock;
        // Calls a method
        update();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void update() {
        // Access to the current/parent object
        super.update();
        // Access to the current/parent object
        this.tradeCache.invalidate();
        // Calls a method
        sendPacketToViewers(tradeCache);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean addViewer(Player player) {
        // Calls a method
        final boolean result = super.addViewer(player);
        // Branch: checks a condition
        if (result) player.sendPacket(tradeCache);
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Start of a method/block
    private TradeListPacket createTradePacket() {
        // Returns a value to the caller
        return new TradeListPacket(getWindowId(), trades, villagerLevel, experience, regularVillager, canRestock);
    // End of a block/expression
    }
// End of a block/expression
}
