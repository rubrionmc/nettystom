// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.type;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryType;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TradeListPacket;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collections;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class VillagerInventory extends Inventory {
    // Appelle une méthode
    private final CachedPacket tradeCache = new CachedPacket(this::createTradePacket);
    // Affecte une valeur
    private final List<TradeListPacket.Trade> trades = new ArrayList<>();
    // Instruction de code
    private int villagerLevel;
    // Instruction de code
    private int experience;
    // Instruction de code
    private boolean regularVillager;
    // Instruction de code
    private boolean canRestock;

    // Début d'une méthode/d'un bloc
    public VillagerInventory(Component title) {
        // Accès à l'objet courant/parent
        super(InventoryType.MERCHANT, title);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public VillagerInventory(String title) {
        // Accès à l'objet courant/parent
        super(InventoryType.MERCHANT, title);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<TradeListPacket.Trade> getTrades() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableList(trades);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void addTrade(TradeListPacket.Trade trade) {
        // Accès à l'objet courant/parent
        this.trades.add(trade);
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void removeTrade(int index) {
        // Accès à l'objet courant/parent
        this.trades.remove(index);
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getVillagerLevel() {
        // Renvoie une valeur à l'appelant
        return villagerLevel;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setVillagerLevel(int level) {
        // Accès à l'objet courant/parent
        this.villagerLevel = level;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getExperience() {
        // Renvoie une valeur à l'appelant
        return experience;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setExperience(int experience) {
        // Accès à l'objet courant/parent
        this.experience = experience;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isRegularVillager() {
        // Renvoie une valeur à l'appelant
        return regularVillager;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRegularVillager(boolean regularVillager) {
        // Accès à l'objet courant/parent
        this.regularVillager = regularVillager;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean canRestock() {
        // Renvoie une valeur à l'appelant
        return canRestock;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCanRestock(boolean canRestock) {
        // Accès à l'objet courant/parent
        this.canRestock = canRestock;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void update() {
        // Accès à l'objet courant/parent
        super.update();
        // Accès à l'objet courant/parent
        this.tradeCache.invalidate();
        // Appelle une méthode
        sendPacketToViewers(tradeCache);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean addViewer(Player player) {
        // Appelle une méthode
        final boolean result = super.addViewer(player);
        // Embranchement : vérifie une condition
        if (result) player.sendPacket(tradeCache);
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private TradeListPacket createTradePacket() {
        // Renvoie une valeur à l'appelant
        return new TradeListPacket(getWindowId(), trades, villagerLevel, experience, regularVillager, canRestock);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
