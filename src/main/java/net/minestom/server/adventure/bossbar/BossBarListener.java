// Package declaration for this file
package net.minestom.server.adventure.bossbar;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;

// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.function.Consumer;

/**
 * A listener for boss bar updates. This class is not intended for public use, and it is
 * automatically added to boss bars shown to players using the methods in
 * {@link Audience}, instead you should use {@link BossBarManager} to manage boss bars
 * for players.
 */
// Type declaration (class/interface/enum/record)
class BossBarListener implements BossBar.Listener {
    // Code statement
    private final BossBarManager manager;

    /**
     * Creates a new boss bar listener.
     *
     * @param manager the manager instance
     */
    // Start of a method/block
    BossBarListener(BossBarManager manager) {
        // Access to the current/parent object
        this.manager = manager;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void bossBarNameChanged(BossBar bar, Component oldName, Component newName) {
        // Access to the current/parent object
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createTitleUpdate(newName)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void bossBarProgressChanged(BossBar bar, float oldProgress, float newProgress) {
        // Access to the current/parent object
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createPercentUpdate(newProgress)));

    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void bossBarColorChanged(BossBar bar, BossBar.Color oldColor, BossBar.Color newColor) {
        // Access to the current/parent object
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createColorUpdate(newColor)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void bossBarOverlayChanged(BossBar bar, BossBar.Overlay oldOverlay, BossBar.Overlay newOverlay) {
        // Access to the current/parent object
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createOverlayUpdate(newOverlay)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void bossBarFlagsChanged(BossBar bar, Set<BossBar.Flag> flagsAdded, Set<BossBar.Flag> flagsRemoved) {
        // Access to the current/parent object
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createFlagsUpdate()));
    // End of a block/expression
    }

    // Start of a method/block
    private void doIfRegistered(BossBar bar, Consumer<BossBarHolder> consumer) {
        // Calls a method
        BossBarHolder holder = this.manager.bars.get(bar);
        // Branch: checks a condition
        if (holder != null) {
            // Calls a method
            consumer.accept(holder);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
