// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.bossbar;

// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.Viewable;
// Import d'une classe nécessaire
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.BossBarPacket;

// Import d'une classe nécessaire
import java.util.Collections;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * A holder of a boss bar. This class is not intended for public use, instead you should
 * use {@link BossBarManager} to manage boss bars for players.
 */
// Déclaration de type (classe/interface/enum/record)
final class BossBarHolder implements Viewable {
    // Appelle une méthode
    final UUID uuid = UUID.randomUUID();
    // Affecte une valeur
    final Set<Player> players = new CopyOnWriteArraySet<>();
    // Instruction de code
    final BossBar bar;

    // Début d'une méthode/d'un bloc
    BossBarHolder(BossBar bar) {
        // Accès à l'objet courant/parent
        this.bar = bar;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    BossBarPacket createRemovePacket() {
        // Renvoie une valeur à l'appelant
        return new BossBarPacket(uuid, new BossBarPacket.RemoveAction());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    BossBarPacket createAddPacket() {
        // Renvoie une valeur à l'appelant
        return new BossBarPacket(uuid, new BossBarPacket.AddAction(bar));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    BossBarPacket createPercentUpdate(float newPercent) {
        // Renvoie une valeur à l'appelant
        return new BossBarPacket(uuid, new BossBarPacket.UpdateHealthAction(newPercent));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    BossBarPacket createColorUpdate(BossBar.Color color) {
        // Renvoie une valeur à l'appelant
        return new BossBarPacket(uuid, new BossBarPacket.UpdateStyleAction(color, bar.overlay()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    BossBarPacket createTitleUpdate(Component title) {
        // Renvoie une valeur à l'appelant
        return new BossBarPacket(uuid, new BossBarPacket.UpdateTitleAction(title));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    BossBarPacket createFlagsUpdate() {
        // Renvoie une valeur à l'appelant
        return createFlagsUpdate(bar.flags());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    BossBarPacket createFlagsUpdate(Set<BossBar.Flag> newFlags) {
        // Renvoie une valeur à l'appelant
        return new BossBarPacket(uuid, new BossBarPacket.UpdateFlagsAction(AdventurePacketConvertor.getBossBarFlagValue(newFlags)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    BossBarPacket createOverlayUpdate(BossBar.Overlay overlay) {
        // Renvoie une valeur à l'appelant
        return new BossBarPacket(uuid, new BossBarPacket.UpdateStyleAction(bar.color(), overlay));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean addViewer(Player player) {
        // Renvoie une valeur à l'appelant
        return this.players.add(player);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean removeViewer(Player player) {
        // Renvoie une valeur à l'appelant
        return this.players.remove(player);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Set<Player> getViewers() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(this.players);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
