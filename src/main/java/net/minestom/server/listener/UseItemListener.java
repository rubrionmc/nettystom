// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.item.PlayerBeginItemUseEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerUseItemEvent;
// Import of a required class
import net.minestom.server.inventory.PlayerInventory;
// Import of a required class
import net.minestom.server.item.ItemAnimation;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.Consumable;
// Import of a required class
import net.minestom.server.item.component.Equippable;
// Import of a required class
import net.minestom.server.item.instrument.Instrument;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientUseItemPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.AcknowledgeBlockChangePacket;
// Import of a required class
import net.minestom.server.registry.Holder;

// Type declaration (class/interface/enum/record)
public class UseItemListener {

    // Start of a method/block
    public static void useItemListener(ClientUseItemPacket packet, Player player) {
        // Calls a method
        PlayerPositionListener.playerRotation(player, packet.yaw(), packet.pitch());

        // Calls a method
        final PlayerHand hand = packet.hand();
        // Calls a method
        final ItemStack itemStack = player.getItemInHand(hand);
        // Calls a method
        final Material material = itemStack.material();
        // Calls a method
        final Consumable consumable = itemStack.get(DataComponents.CONSUMABLE);

        // The following item animations and use item times come from vanilla.
        // These items do not yet use components, but hopefully they will in the future
        // and this behavior can be removed.
        // Assigns a value
        long useItemTime = 0;
        // Assigns a value
        ItemAnimation useAnimation = ItemAnimation.NONE;
        // Branch: checks a condition
        if (material == Material.BOW) {
            // Assigns a value
            useItemTime = 72000;
            // Assigns a value
            useAnimation = ItemAnimation.BOW;
        // Branch: checks a condition
        } else if (material == Material.CROSSBOW) {
            // The crossbow has a min charge time dependent on quick charge, but to the
            // client they can hold it forever
            // Assigns a value
            useItemTime = 7200;
            // Assigns a value
            useAnimation = ItemAnimation.CROSSBOW;
        // Branch: checks a condition
        } else if (itemStack.has(DataComponents.BLOCKS_ATTACKS)) {
            // Assigns a value
            useItemTime = 72000;
            // Assigns a value
            useAnimation = ItemAnimation.BLOCK;
        // Branch: checks a condition
        } else if (material == Material.TRIDENT) {
            // Assigns a value
            useItemTime = 72000;
            // Assigns a value
            useAnimation = ItemAnimation.TRIDENT;
        // Branch: checks a condition
        } else if (material == Material.SPYGLASS) {
            // Assigns a value
            useItemTime = 1200;
            // Assigns a value
            useAnimation = ItemAnimation.SPYGLASS;
        // Branch: checks a condition
        } else if (material == Material.GOAT_HORN) {
            // Calls a method
            useItemTime = getInstrumentTime(itemStack);
            // Assigns a value
            useAnimation = ItemAnimation.TOOT_HORN;
        // Branch: checks a condition
        } else if (material == Material.BRUSH) {
            // Assigns a value
            useItemTime = 200;
            // Assigns a value
            useAnimation = ItemAnimation.BRUSH;
        // Branch: checks a condition
        } else if (material.name().contains("bundle")) {
            // Why is a bundle usable???
            // Assigns a value
            useItemTime = 200;
            // Assigns a value
            useAnimation = ItemAnimation.BUNDLE;
        // Branch: checks a condition
        } else if (consumable != null) {
            // Calls a method
            useItemTime = consumable.consumeTicks();
            // Calls a method
            useAnimation = consumable.animation();
        // Branch: checks a condition
        } else if (itemStack.has(DataComponents.KINETIC_WEAPON)) {
            // Assigns a value
            useItemTime = 72000;
            // Assigns a value
            useAnimation = ItemAnimation.SPEAR;
        // End of a block/expression
        }

        // Calls a method
        boolean usingMainHand = player.getItemUseHand() == PlayerHand.MAIN && hand == PlayerHand.OFF;
        // Assigns a value
        PlayerUseItemEvent useItemEvent = new PlayerUseItemEvent(player, hand, itemStack,
                // Code statement
                usingMainHand ? 0 : useItemTime);
        // Calls a method
        EventDispatcher.call(useItemEvent);

        // Calls a method
        player.sendPacket(new AcknowledgeBlockChangePacket(packet.sequence()));
        // Calls a method
        final PlayerInventory playerInventory = player.getInventory();
        // Branch: checks a condition
        if (useItemEvent.isCancelled()) {
            // Calls a method
            playerInventory.update();
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        useItemTime = useItemEvent.getItemUseTime();
        // Branch: checks a condition
        if (useItemTime != 0) {
            // Calls a method
            final PlayerBeginItemUseEvent beginUseEvent = new PlayerBeginItemUseEvent(player, hand, itemStack, useAnimation, useItemTime);
            // Start of a method/block
            EventDispatcher.callCancellable(beginUseEvent, () -> {
                // Branch: checks a condition
                if (beginUseEvent.getItemUseDuration() <= 0) return;

                // Calls a method
                player.refreshItemUse(hand, beginUseEvent.getItemUseDuration());
                // Calls a method
                player.refreshActiveHand(true, hand == PlayerHand.OFF, false);
            // End of a block/expression
            });

            // Returns a value to the caller
            return; // Do not also swap after use
        // End of a block/expression
        }

        // If the item was not usable, we can try to do an equipment swap with it.
        // Calls a method
        final Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        // Branch: checks a condition
        if (equippable != null && equippable.swappable() && equippable.slot().armorSlot() > 0) {
            // Calls a method
            final ItemStack currentlyEquipped = player.getEquipment(equippable.slot());
            // Calls a method
            player.setEquipment(equippable.slot(), itemStack);
            // Calls a method
            player.setItemInHand(hand, currentlyEquipped);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static int getInstrumentTime(ItemStack itemStack) {
        // Calls a method
        final Holder<Instrument> holder = itemStack.get(DataComponents.INSTRUMENT);
        // Branch: checks a condition
        if (holder == null) return 0;

        // Calls a method
        final Instrument instrument = holder.resolve(MinecraftServer.getInstrumentRegistry());
        // Branch: checks a condition
        if (instrument == null) return 0;

        // Returns a value to the caller
        return instrument.useDurationTicks();
    // End of a block/expression
    }
// End of a block/expression
}
