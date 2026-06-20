// Package declaration for this file
package net.minestom.server.adventure.bossbar;

// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.Viewable;
// Import of a required class
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.play.BossBarPacket;

// Import of a required class
import java.util.Collections;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * A holder of a boss bar. This class is not intended for public use, instead you should
 * use {@link BossBarManager} to manage boss bars for players.
 */
// Type declaration (class/interface/enum/record)
final class BossBarHolder implements Viewable {
    // Calls a method
    final UUID uuid = UUID.randomUUID();
    // Calls a method
    final Set<Player> players = new CopyOnWriteArraySet<>();
    // Code statement
    final BossBar bar;

    // Start of a method/block
    BossBarHolder(BossBar bar) {
        // Access to the current/parent object
        this.bar = bar;
    // End of a block/expression
    }

    // Start of a method/block
    BossBarPacket createRemovePacket() {
        // Returns a value to the caller
        return new BossBarPacket(uuid, new BossBarPacket.RemoveAction());
    // End of a block/expression
    }

    // Start of a method/block
    BossBarPacket createAddPacket() {
        // Returns a value to the caller
        return new BossBarPacket(uuid, new BossBarPacket.AddAction(bar));
    // End of a block/expression
    }

    // Start of a method/block
    BossBarPacket createPercentUpdate(float newPercent) {
        // Returns a value to the caller
        return new BossBarPacket(uuid, new BossBarPacket.UpdateHealthAction(newPercent));
    // End of a block/expression
    }

    // Start of a method/block
    BossBarPacket createColorUpdate(BossBar.Color color) {
        // Returns a value to the caller
        return new BossBarPacket(uuid, new BossBarPacket.UpdateStyleAction(color, bar.overlay()));
    // End of a block/expression
    }

    // Start of a method/block
    BossBarPacket createTitleUpdate(Component title) {
        // Returns a value to the caller
        return new BossBarPacket(uuid, new BossBarPacket.UpdateTitleAction(title));
    // End of a block/expression
    }

    // Start of a method/block
    BossBarPacket createFlagsUpdate() {
        // Returns a value to the caller
        return createFlagsUpdate(bar.flags());
    // End of a block/expression
    }

    // Start of a method/block
    BossBarPacket createFlagsUpdate(Set<BossBar.Flag> newFlags) {
        // Returns a value to the caller
        return new BossBarPacket(uuid, new BossBarPacket.UpdateFlagsAction(AdventurePacketConvertor.getBossBarFlagValue(newFlags)));
    // End of a block/expression
    }

    // Start of a method/block
    BossBarPacket createOverlayUpdate(BossBar.Overlay overlay) {
        // Returns a value to the caller
        return new BossBarPacket(uuid, new BossBarPacket.UpdateStyleAction(bar.color(), overlay));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean addViewer(Player player) {
        // Returns a value to the caller
        return this.players.add(player);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean removeViewer(Player player) {
        // Returns a value to the caller
        return this.players.remove(player);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Set<? extends Player> getViewers() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(this.players);
    // End of a block/expression
    }
// End of a block/expression
}
