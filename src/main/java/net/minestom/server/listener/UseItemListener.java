// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.item.PlayerBeginItemUseEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerUseItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.PlayerInventory;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemAnimation;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.Consumable;
// Import d'une classe nécessaire
import net.minestom.server.item.component.Equippable;
// Import d'une classe nécessaire
import net.minestom.server.item.component.InstrumentComponent;
// Import d'une classe nécessaire
import net.minestom.server.item.instrument.Instrument;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientUseItemPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.AcknowledgeBlockChangePacket;

// Déclaration de type (classe/interface/enum/record)
public class UseItemListener {

    // Début d'une méthode/d'un bloc
    public static void useItemListener(ClientUseItemPacket packet, Player player) {
        // Appelle une méthode
        PlayerPositionListener.playerRotation(player, packet.yaw(), packet.pitch());

        // Appelle une méthode
        final PlayerHand hand = packet.hand();
        // Appelle une méthode
        final ItemStack itemStack = player.getItemInHand(hand);
        // Appelle une méthode
        final Material material = itemStack.material();
        // Appelle une méthode
        final Consumable consumable = itemStack.get(DataComponents.CONSUMABLE);

        // The following item animations and use item times come from vanilla.
        // These items do not yet use components, but hopefully they will in the future
        // and this behavior can be removed.
        // Affecte une valeur
        long useItemTime = 0;
        // Affecte une valeur
        ItemAnimation useAnimation = ItemAnimation.NONE;
        // Embranchement : vérifie une condition
        if (material == Material.BOW) {
            // Affecte une valeur
            useItemTime = 72000;
            // Affecte une valeur
            useAnimation = ItemAnimation.BOW;
        // Embranchement : vérifie une condition
        } else if (material == Material.CROSSBOW) {
            // The crossbow has a min charge time dependent on quick charge, but to the
            // client they can hold it forever
            // Affecte une valeur
            useItemTime = 7200;
            // Affecte une valeur
            useAnimation = ItemAnimation.CROSSBOW;
        // Embranchement : vérifie une condition
        } else if (itemStack.has(DataComponents.BLOCKS_ATTACKS)) {
            // Affecte une valeur
            useItemTime = 72000;
            // Affecte une valeur
            useAnimation = ItemAnimation.BLOCK;
        // Embranchement : vérifie une condition
        } else if (material == Material.TRIDENT) {
            // Affecte une valeur
            useItemTime = 72000;
            // Affecte une valeur
            useAnimation = ItemAnimation.SPEAR;
        // Embranchement : vérifie une condition
        } else if (material == Material.SPYGLASS) {
            // Affecte une valeur
            useItemTime = 1200;
            // Affecte une valeur
            useAnimation = ItemAnimation.SPYGLASS;
        // Embranchement : vérifie une condition
        } else if (material == Material.GOAT_HORN) {
            // Appelle une méthode
            useItemTime = getInstrumentTime(itemStack);
            // Affecte une valeur
            useAnimation = ItemAnimation.TOOT_HORN;
        // Embranchement : vérifie une condition
        } else if (material == Material.BRUSH) {
            // Affecte une valeur
            useItemTime = 200;
            // Affecte une valeur
            useAnimation = ItemAnimation.BRUSH;
        // Embranchement : vérifie une condition
        } else if (material.name().contains("bundle")) {
            // Why is a bundle usable???
            // Affecte une valeur
            useItemTime = 200;
            // Affecte une valeur
            useAnimation = ItemAnimation.BUNDLE;
        // Embranchement : vérifie une condition
        } else if (consumable != null) {
            // Appelle une méthode
            useItemTime = consumable.consumeTicks();
            // Appelle une méthode
            useAnimation = consumable.animation();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        boolean usingMainHand = player.getItemUseHand() == PlayerHand.MAIN && hand == PlayerHand.OFF;
        // Affecte une valeur
        PlayerUseItemEvent useItemEvent = new PlayerUseItemEvent(player, hand, itemStack,
                // Instruction de code
                usingMainHand ? 0 : useItemTime);
        // Appelle une méthode
        EventDispatcher.call(useItemEvent);

        // Appelle une méthode
        player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
        // Appelle une méthode
        final PlayerInventory playerInventory = player.getInventory();
        // Embranchement : vérifie une condition
        if (useItemEvent.isCancelled()) {
            // Appelle une méthode
            playerInventory.update();
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        useItemTime = useItemEvent.getItemUseTime();
        // Embranchement : vérifie une condition
        if (useItemTime != 0) {
            // Appelle une méthode
            final PlayerBeginItemUseEvent beginUseEvent = new PlayerBeginItemUseEvent(player, hand, itemStack, useAnimation, useItemTime);
            // Début d'une méthode/d'un bloc
            EventDispatcher.callCancellable(beginUseEvent, () -> {
                // Embranchement : vérifie une condition
                if (beginUseEvent.getItemUseDuration() <= 0) return;

                // Appelle une méthode
                player.refreshItemUse(hand, beginUseEvent.getItemUseDuration());
                // Appelle une méthode
                player.refreshActiveHand(true, hand == PlayerHand.OFF, false);
            // Fin d'un bloc/d'une expression
            });

            // Renvoie une valeur à l'appelant
            return; // Do not also swap after use
        // Fin d'un bloc/d'une expression
        }

        // If the item was not usable, we can try to do an equipment swap with it.
        // Appelle une méthode
        final Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        // Embranchement : vérifie une condition
        if (equippable != null && equippable.swappable() && equippable.slot().armorSlot() > 0) {
            // Appelle une méthode
            final ItemStack currentlyEquipped = player.getEquipment(equippable.slot());
            // Appelle une méthode
            player.setEquipment(equippable.slot(), itemStack);
            // Appelle une méthode
            player.setItemInHand(hand, currentlyEquipped);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int getInstrumentTime(ItemStack itemStack) {
        // Appelle une méthode
        final InstrumentComponent holder = itemStack.get(DataComponents.INSTRUMENT);
        // Embranchement : vérifie une condition
        if (holder == null) return 0;

        // Appelle une méthode
        final Instrument instrument = holder.resolve(MinecraftServer.getInstrumentRegistry());
        // Embranchement : vérifie une condition
        if (instrument == null) return 0;

        // Renvoie une valeur à l'appelant
        return instrument.useDurationTicks();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
