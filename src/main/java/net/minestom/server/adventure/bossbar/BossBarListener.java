// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.bossbar;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;

// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.function.Consumer;

/**
 * A listener for boss bar updates. This class is not intended for public use and it is
 * automatically added to boss bars shown to players using the methods in
 * {@link Audience}, instead you should use {@link BossBarManager} to manage boss bars
 * for players.
 */
// Déclaration de type (classe/interface/enum/record)
class BossBarListener implements BossBar.Listener {
    // Instruction de code
    private final BossBarManager manager;

    /**
     * Creates a new boss bar listener.
     *
     * @param manager the manager instance
     */
    // Début d'une méthode/d'un bloc
    BossBarListener(BossBarManager manager) {
        // Accès à l'objet courant/parent
        this.manager = manager;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void bossBarNameChanged(BossBar bar, Component oldName, Component newName) {
        // Accès à l'objet courant/parent
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createTitleUpdate(newName)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void bossBarProgressChanged(BossBar bar, float oldProgress, float newProgress) {
        // Accès à l'objet courant/parent
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createPercentUpdate(newProgress)));

    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void bossBarColorChanged(BossBar bar, BossBar.Color oldColor, BossBar.Color newColor) {
        // Accès à l'objet courant/parent
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createColorUpdate(newColor)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void bossBarOverlayChanged(BossBar bar, BossBar.Overlay oldOverlay, BossBar.Overlay newOverlay) {
        // Accès à l'objet courant/parent
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createOverlayUpdate(newOverlay)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void bossBarFlagsChanged(BossBar bar, Set<BossBar.Flag> flagsAdded, Set<BossBar.Flag> flagsRemoved) {
        // Accès à l'objet courant/parent
        this.doIfRegistered(bar, holder -> PacketSendingUtils.sendGroupedPacket(holder.players, holder.createFlagsUpdate()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void doIfRegistered(BossBar bar, Consumer<BossBarHolder> consumer) {
        // Appelle une méthode
        BossBarHolder holder = this.manager.bars.get(bar);
        // Embranchement : vérifie une condition
        if (holder != null) {
            // Appelle une méthode
            consumer.accept(holder);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
