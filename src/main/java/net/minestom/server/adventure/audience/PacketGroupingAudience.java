// Package declaration for this file
package net.minestom.server.adventure.audience;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.audience.ForwardingAudience;
// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.kyori.adventure.sound.SoundStop;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.title.TitlePart;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.advancements.Notification;
// Import of a required class
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.message.ChatPosition;
// Import of a required class
import net.minestom.server.message.Messenger;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.ActionBarPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.ClearTitlesPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.PlayerListHeaderAndFooterPacket;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;

// Import of a required class
import java.util.Collection;

/**
 * An audience implementation that sends grouped packets if possible.
 */
// Type declaration (class/interface/enum/record)
public interface PacketGroupingAudience extends ForwardingAudience {
    /**
     * Creates a packet grouping audience that copies an iterable of players. The
     * underlying collection is not copied, so changes to the collection will be
     * reflected in the audience.
     *
     * @param players the players
     * @return the audience
     */
    // Start of a method/block
    static PacketGroupingAudience of(Collection<? extends Player> players) {
        // Returns a value to the caller
        return () -> players;
    // End of a block/expression
    }

    /**
     * Gets an iterable of the players this audience contains.
     *
     * @return the connections
     */
    // Calls a method
    Collection<? extends Player> getPlayers();

    /**
     * Broadcast a ServerPacket to all players of this audience
     *
     * @param packet the packet to broadcast
     */
    // Start of a method/block
    default void sendGroupedPacket(ServerPacket packet) {
        // Calls a method
        PacketSendingUtils.sendGroupedPacket(getPlayers(), packet);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void sendMessage(Component message) {
        // Calls a method
        Messenger.sendMessage(this.getPlayers(), message, ChatPosition.SYSTEM_MESSAGE);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void sendActionBar(Component message) {
        // Calls a method
        sendGroupedPacket(new ActionBarPacket(message));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void sendPlayerListHeaderAndFooter(Component header, Component footer) {
        // Calls a method
        sendGroupedPacket(new PlayerListHeaderAndFooterPacket(header, footer));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default <T> void sendTitlePart(TitlePart<T> part, T value) {
        // Calls a method
        sendGroupedPacket(AdventurePacketConvertor.createTitlePartPacket(part, value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void clearTitle() {
        // Calls a method
        sendGroupedPacket(new ClearTitlesPacket(false));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void resetTitle() {
        // Calls a method
        sendGroupedPacket(new ClearTitlesPacket(true));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void showBossBar(BossBar bar) {
        // Calls a method
        MinecraftServer.getBossBarManager().addBossBar(this.getPlayers(), bar);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void hideBossBar(BossBar bar) {
        // Calls a method
        MinecraftServer.getBossBarManager().removeBossBar(this.getPlayers(), bar);
    // End of a block/expression
    }

    /**
     * Plays a {@link Sound} at a given point
     * @param sound The sound to play
     * @param point The point in this instance at which to play the sound
     */
    // Start of a method/block
    default void playSound(Sound sound, Point point) {
        // Calls a method
        playSound(sound, point.x(), point.y(), point.z());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void playSound(Sound sound, double x, double y, double z) {
        // Calls a method
        sendGroupedPacket(AdventurePacketConvertor.createSoundPacket(sound, x, y, z));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void playSound(Sound sound, Sound.Emitter emitter) {
        // Branch: checks a condition
        if (emitter != Sound.Emitter.self()) {
            // Calls a method
            sendGroupedPacket(AdventurePacketConvertor.createSoundPacket(sound, emitter));
        // Alternative branch of the condition
        } else {
            // if we're playing on self, we need to delegate to each audience member
            // Loop: repeats a block
            for (Audience audience : this.audiences()) {
                // Calls a method
                audience.playSound(sound, emitter);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default void stopSound(SoundStop stop) {
        // Calls a method
        sendGroupedPacket(AdventurePacketConvertor.createSoundStopPacket(stop));
    // End of a block/expression
    }

    /**
     * Send a {@link Notification} to the audience.
     * @param notification the {@link Notification} to send
     */
    // Start of a method/block
    default void sendNotification(Notification notification) {
        // Calls a method
        sendGroupedPacket(notification.buildAddPacket());
        // Calls a method
        sendGroupedPacket(notification.buildRemovePacket());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Iterable<? extends Audience> audiences() {
        // Returns a value to the caller
        return this.getPlayers();
    // End of a block/expression
    }
// End of a block/expression
}
