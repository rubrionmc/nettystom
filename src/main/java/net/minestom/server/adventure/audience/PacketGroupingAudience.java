// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.audience;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.audience.ForwardingAudience;
// Import d'une classe nécessaire
import net.kyori.adventure.audience.MessageType;
// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identity;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.SoundStop;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.title.TitlePart;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.advancements.Notification;
// Import d'une classe nécessaire
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatPosition;
// Import d'une classe nécessaire
import net.minestom.server.message.Messenger;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ActionBarPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ClearTitlesPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.PlayerListHeaderAndFooterPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;

// Import d'une classe nécessaire
import java.util.Collection;

/**
 * An audience implementation that sends grouped packets if possible.
 */
// Déclaration de type (classe/interface/enum/record)
public interface PacketGroupingAudience extends ForwardingAudience {
    /**
     * Creates a packet grouping audience that copies an iterable of players. The
     * underlying collection is not copied, so changes to the collection will be
     * reflected in the audience.
     *
     * @param players the players
     * @return the audience
     */
    // Début d'une méthode/d'un bloc
    static PacketGroupingAudience of(Collection<Player> players) {
        // Renvoie une valeur à l'appelant
        return () -> players;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets an iterable of the players this audience contains.
     *
     * @return the connections
     */
    // Appelle une méthode
    Collection<Player> getPlayers();

    /**
     * Broadcast a ServerPacket to all players of this audience
     *
     * @param packet the packet to broadcast
     */
    // Début d'une méthode/d'un bloc
    default void sendGroupedPacket(ServerPacket packet) {
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(getPlayers(), packet);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void sendMessage(Identity source, Component message, MessageType type) {
        // Appelle une méthode
        Messenger.sendMessage(this.getPlayers(), message, ChatPosition.fromMessageType(type), source.uuid());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void sendActionBar(Component message) {
        // Appelle une méthode
        sendGroupedPacket(new ActionBarPacket(message));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void sendPlayerListHeaderAndFooter(Component header, Component footer) {
        // Appelle une méthode
        sendGroupedPacket(new PlayerListHeaderAndFooterPacket(header, footer));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <T> void sendTitlePart(TitlePart<T> part, T value) {
        // Appelle une méthode
        sendGroupedPacket(AdventurePacketConvertor.createTitlePartPacket(part, value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void clearTitle() {
        // Appelle une méthode
        sendGroupedPacket(new ClearTitlesPacket(false));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void resetTitle() {
        // Appelle une méthode
        sendGroupedPacket(new ClearTitlesPacket(true));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void showBossBar(BossBar bar) {
        // Appelle une méthode
        MinecraftServer.getBossBarManager().addBossBar(this.getPlayers(), bar);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void hideBossBar(BossBar bar) {
        // Appelle une méthode
        MinecraftServer.getBossBarManager().removeBossBar(this.getPlayers(), bar);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Plays a {@link Sound} at a given point
     * @param sound The sound to play
     * @param point The point in this instance at which to play the sound
     */
    // Début d'une méthode/d'un bloc
    default void playSound(Sound sound, Point point) {
        // Appelle une méthode
        playSound(sound, point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void playSound(Sound sound, double x, double y, double z) {
        // Appelle une méthode
        sendGroupedPacket(AdventurePacketConvertor.createSoundPacket(sound, x, y, z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void playSound(Sound sound, Sound.Emitter emitter) {
        // Embranchement : vérifie une condition
        if (emitter != Sound.Emitter.self()) {
            // Appelle une méthode
            sendGroupedPacket(AdventurePacketConvertor.createSoundPacket(sound, emitter));
        // Branche alternative de la condition
        } else {
            // if we're playing on self, we need to delegate to each audience member
            // Boucle : répète un bloc
            for (Audience audience : this.audiences()) {
                // Appelle une méthode
                audience.playSound(sound, emitter);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void stopSound(SoundStop stop) {
        // Appelle une méthode
        sendGroupedPacket(AdventurePacketConvertor.createSoundStopPacket(stop));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Send a {@link Notification} to the audience.
     * @param notification the {@link Notification} to send
     */
    // Début d'une méthode/d'un bloc
    default void sendNotification(Notification notification) {
        // Appelle une méthode
        sendGroupedPacket(notification.buildAddPacket());
        // Appelle une méthode
        sendGroupedPacket(notification.buildRemovePacket());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Iterable<? extends Audience> audiences() {
        // Renvoie une valeur à l'appelant
        return this.getPlayers();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
