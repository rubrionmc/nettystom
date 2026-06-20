// Package declaration for this file
package net.minestom.server.adventure.bossbar;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages all boss bars known to this Minestom instance. Although this class can be used
 * to show boss bars to players, it is preferable to use the boss bar methods in the
 * {@link Audience} class instead.
 *
 * <p>This implementation is heavily based on
 * <a href="https://github.com/VelocityPowered/Velocity">Velocity</a>'s boss bar
 * management system.</p>
 *
 * @see Audience#showBossBar(BossBar)
 * @see Audience#hideBossBar(BossBar)
 */
// Type declaration (class/interface/enum/record)
public class BossBarManager {
    // Calls a method
    private final BossBarListener listener = new BossBarListener(this);
    // Calls a method
    private final Map<UUID, Set<BossBarHolder>> playerBars = new ConcurrentHashMap<>();
    // Calls a method
    final Map<BossBar, BossBarHolder> bars = new ConcurrentHashMap<>();

    /**
     * Creates a new boss bar manager.
     *
     * @see MinecraftServer#getBossBarManager()
     */
    // Start of a method/block
    public BossBarManager() {
    // End of a block/expression
    }

    /**
     * Adds the specified player to the boss bar's viewers and spawns the boss bar, registering the
     * boss bar if needed.
     *
     * @param player the intended viewer
     * @param bar    the boss bar to show
     */
    // Start of a method/block
    public void addBossBar(Player player, BossBar bar) {
        // Calls a method
        BossBarHolder holder = this.getOrCreateHandler(bar);
        // Branch: checks a condition
        if (holder.addViewer(player)) {
            // Calls a method
            player.sendPacket(holder.createAddPacket());
            // Access to the current/parent object
            this.playerBars.computeIfAbsent(player.getUuid(), uuid -> new HashSet<>()).add(holder);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Removes the specified player from the boss bar's viewers and despawns the boss bar.
     *
     * @param player the intended viewer
     * @param bar    the boss bar to hide
     */
    // Start of a method/block
    public void removeBossBar(Player player, BossBar bar) {
        // Calls a method
        BossBarHolder holder = this.bars.get(bar);
        // Branch: checks a condition
        if (holder != null && holder.removeViewer(player)) {
            // Calls a method
            player.sendPacket(holder.createRemovePacket());
            // Access to the current/parent object
            this.removePlayer(player, holder);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Adds the specified players to the boss bar's viewers and spawns the boss bar, registering the
     * boss bar if needed.
     *
     * @param players the players
     * @param bar     the boss bar
     */
    // Start of a method/block
    public void addBossBar(Collection<? extends Player> players, BossBar bar) {
        // Calls a method
        BossBarHolder holder = this.getOrCreateHandler(bar);
        // Calls a method
        Collection<? extends Player> addedPlayers = players.stream().filter(holder::addViewer).toList();
        // Branch: checks a condition
        if (!addedPlayers.isEmpty()) {
            // Calls a method
            PacketSendingUtils.sendGroupedPacket(addedPlayers, holder.createAddPacket());
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Removes the specified players from the boss bar's viewers and despawns the boss bar.
     *
     * @param players the intended viewers
     * @param bar     the boss bar to hide
     */
    // Start of a method/block
    public void removeBossBar(Collection<? extends Player> players, BossBar bar) {
        // Calls a method
        BossBarHolder holder = this.bars.get(bar);
        // Branch: checks a condition
        if (holder != null) {
            // Calls a method
            Collection<? extends Player> removedPlayers = players.stream().filter(holder::removeViewer).toList();
            // Branch: checks a condition
            if (!removedPlayers.isEmpty()) {
                // Calls a method
                PacketSendingUtils.sendGroupedPacket(removedPlayers, holder.createRemovePacket());
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Completely destroys a boss bar, removing it from all players.
     *
     * @param bossBar the boss bar
     */
    // Start of a method/block
    public void destroyBossBar(BossBar bossBar) {
        // Calls a method
        BossBarHolder holder = this.bars.remove(bossBar);
        // Branch: checks a condition
        if (holder != null) {
            // Calls a method
            PacketSendingUtils.sendGroupedPacket(holder.players, holder.createRemovePacket());
            // Loop: repeats a block
            for (Player player : holder.players) {
                // Access to the current/parent object
                this.removePlayer(player, holder);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Removes a player from all of their boss bars. Note that this method does not
     * send any removal packets to the player. It is meant to be used when a player is
     * disconnecting from the server.
     *
     * @param player the player
     */
    // Start of a method/block
    public void removeAllBossBars(Player player) {
        // Calls a method
        Set<BossBarHolder> holders = this.playerBars.remove(player.getUuid());
        // Branch: checks a condition
        if (holders != null) {
            // Loop: repeats a block
            for (BossBarHolder holder : holders) {
                // Calls a method
                holder.removeViewer(player);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets a collection of all boss bars currently visible to a given player.
     *
     * @param player the player
     * @return the boss bars
     */
    // Start of a method/block
    public Collection<BossBar> getPlayerBossBars(Player player) {
        // Calls a method
        Collection<BossBarHolder> holders = this.playerBars.get(player.getUuid());
        // Returns a value to the caller
        return holders != null ?
                // Calls a method
                holders.stream().map(holder -> holder.bar).toList() : List.of();
    // End of a block/expression
    }

    /**
     * Gets all the players for whom the given boss bar is currently visible.
     *
     * @param bossBar the boss bar
     * @return the players
     */
    // Start of a method/block
    public Collection<? extends Player> getBossBarViewers(BossBar bossBar) {
        // Calls a method
        BossBarHolder holder = this.bars.get(bossBar);
        // Returns a value to the caller
        return holder != null ?
                // Calls a method
                Collections.unmodifiableCollection(holder.players) : List.of();
    // End of a block/expression
    }

    /**
     * Gets or creates a handler for this bar.
     *
     * @param bar the bar
     * @return the handler
     */
    // Start of a method/block
    private BossBarHolder getOrCreateHandler(BossBar bar) {
        // Returns a value to the caller
        return this.bars.computeIfAbsent(bar, bossBar -> {
            // Calls a method
            BossBarHolder holder = new BossBarHolder(bossBar);
            // Calls a method
            bossBar.addListener(this.listener);
            // Returns a value to the caller
            return holder;
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    private void removePlayer(Player player, BossBarHolder holder) {
        // Calls a method
        Set<BossBarHolder> holders = this.playerBars.get(player.getUuid());
        // Branch: checks a condition
        if (holders != null) {
            // Calls a method
            holders.remove(holder);
            // Branch: checks a condition
            if (holders.isEmpty()) {
                // Access to the current/parent object
                this.playerBars.remove(player.getUuid());
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
