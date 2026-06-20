// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.bossbar;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public class BossBarManager {
    // Appelle une méthode
    private final BossBarListener listener = new BossBarListener(this);
    // Affecte une valeur
    private final Map<UUID, Set<BossBarHolder>> playerBars = new ConcurrentHashMap<>();
    // Affecte une valeur
    final Map<BossBar, BossBarHolder> bars = new ConcurrentHashMap<>();

    /**
     * Creates a new boss bar manager.
     *
     * @see MinecraftServer#getBossBarManager()
     */
    // Début d'une méthode/d'un bloc
    public BossBarManager() {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds the specified player to the boss bar's viewers and spawns the boss bar, registering the
     * boss bar if needed.
     *
     * @param player the intended viewer
     * @param bar    the boss bar to show
     */
    // Début d'une méthode/d'un bloc
    public void addBossBar(Player player, BossBar bar) {
        // Appelle une méthode
        BossBarHolder holder = this.getOrCreateHandler(bar);
        // Embranchement : vérifie une condition
        if (holder.addViewer(player)) {
            // Appelle une méthode
            player.sendPacket(holder.createAddPacket());
            // Accès à l'objet courant/parent
            this.playerBars.computeIfAbsent(player.getUuid(), uuid -> new HashSet<>()).add(holder);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes the specified player from the boss bar's viewers and despawns the boss bar.
     *
     * @param player the intended viewer
     * @param bar    the boss bar to hide
     */
    // Début d'une méthode/d'un bloc
    public void removeBossBar(Player player, BossBar bar) {
        // Appelle une méthode
        BossBarHolder holder = this.bars.get(bar);
        // Embranchement : vérifie une condition
        if (holder != null && holder.removeViewer(player)) {
            // Appelle une méthode
            player.sendPacket(holder.createRemovePacket());
            // Accès à l'objet courant/parent
            this.removePlayer(player, holder);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds the specified players to the boss bar's viewers and spawns the boss bar, registering the
     * boss bar if needed.
     *
     * @param players the players
     * @param bar     the boss bar
     */
    // Début d'une méthode/d'un bloc
    public void addBossBar(Collection<Player> players, BossBar bar) {
        // Appelle une méthode
        BossBarHolder holder = this.getOrCreateHandler(bar);
        // Appelle une méthode
        Collection<Player> addedPlayers = players.stream().filter(holder::addViewer).toList();
        // Embranchement : vérifie une condition
        if (!addedPlayers.isEmpty()) {
            // Appelle une méthode
            PacketSendingUtils.sendGroupedPacket(addedPlayers, holder.createAddPacket());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes the specified players from the boss bar's viewers and despawns the boss bar.
     *
     * @param players the intended viewers
     * @param bar     the boss bar to hide
     */
    // Début d'une méthode/d'un bloc
    public void removeBossBar(Collection<Player> players, BossBar bar) {
        // Appelle une méthode
        BossBarHolder holder = this.bars.get(bar);
        // Embranchement : vérifie une condition
        if (holder != null) {
            // Appelle une méthode
            Collection<Player> removedPlayers = players.stream().filter(holder::removeViewer).toList();
            // Embranchement : vérifie une condition
            if (!removedPlayers.isEmpty()) {
                // Appelle une méthode
                PacketSendingUtils.sendGroupedPacket(removedPlayers, holder.createRemovePacket());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Completely destroys a boss bar, removing it from all players.
     *
     * @param bossBar the boss bar
     */
    // Début d'une méthode/d'un bloc
    public void destroyBossBar(BossBar bossBar) {
        // Appelle une méthode
        BossBarHolder holder = this.bars.remove(bossBar);
        // Embranchement : vérifie une condition
        if (holder != null) {
            // Appelle une méthode
            PacketSendingUtils.sendGroupedPacket(holder.players, holder.createRemovePacket());
            // Boucle : répète un bloc
            for (Player player : holder.players) {
                // Accès à l'objet courant/parent
                this.removePlayer(player, holder);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes a player from all of their boss bars. Note that this method does not
     * send any removal packets to the player. It is meant to be used when a player is
     * disconnecting from the server.
     *
     * @param player the player
     */
    // Début d'une méthode/d'un bloc
    public void removeAllBossBars(Player player) {
        // Appelle une méthode
        Set<BossBarHolder> holders = this.playerBars.remove(player.getUuid());
        // Embranchement : vérifie une condition
        if (holders != null) {
            // Boucle : répète un bloc
            for (BossBarHolder holder : holders) {
                // Appelle une méthode
                holder.removeViewer(player);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a collection of all boss bars currently visible to a given player.
     *
     * @param player the player
     * @return the boss bars
     */
    // Début d'une méthode/d'un bloc
    public Collection<BossBar> getPlayerBossBars(Player player) {
        // Appelle une méthode
        Collection<BossBarHolder> holders = this.playerBars.get(player.getUuid());
        // Renvoie une valeur à l'appelant
        return holders != null ?
                // Appelle une méthode
                holders.stream().map(holder -> holder.bar).toList() : List.of();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the players for whom the given boss bar is currently visible.
     *
     * @param bossBar the boss bar
     * @return the players
     */
    // Début d'une méthode/d'un bloc
    public Collection<Player> getBossBarViewers(BossBar bossBar) {
        // Appelle une méthode
        BossBarHolder holder = this.bars.get(bossBar);
        // Renvoie une valeur à l'appelant
        return holder != null ?
                // Appelle une méthode
                Collections.unmodifiableCollection(holder.players) : List.of();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets or creates a handler for this bar.
     *
     * @param bar the bar
     * @return the handler
     */
    // Début d'une méthode/d'un bloc
    private BossBarHolder getOrCreateHandler(BossBar bar) {
        // Renvoie une valeur à l'appelant
        return this.bars.computeIfAbsent(bar, bossBar -> {
            // Appelle une méthode
            BossBarHolder holder = new BossBarHolder(bossBar);
            // Appelle une méthode
            bossBar.addListener(this.listener);
            // Renvoie une valeur à l'appelant
            return holder;
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void removePlayer(Player player, BossBarHolder holder) {
        // Appelle une méthode
        Set<BossBarHolder> holders = this.playerBars.get(player.getUuid());
        // Embranchement : vérifie une condition
        if (holders != null) {
            // Appelle une méthode
            holders.remove(holder);
            // Embranchement : vérifie une condition
            if (holders.isEmpty()) {
                // Accès à l'objet courant/parent
                this.playerBars.remove(player.getUuid());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
