// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.audience.ForwardingAudience;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.TranslatableComponent;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.adventure.ComponentHolder;
// Import of a required class
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.Predicate;

// Type declaration (class/interface/enum/record)
public final class PacketSendingUtils {
    /**
     * Sends a packet to an audience. This method performs the following steps in the
     * following order:
     * <ol>
     *     <li>If {@code audience} is a {@link Player}, send the packet to them.</li>
     *     <li>Otherwise, if {@code audience} is a {@link PacketGroupingAudience}, call
     *     {@link #sendGroupedPacket(Collection, ServerPacket)} on the players that the
     *     grouping audience contains.</li>
     *     <li>Otherwise, if {@code audience} is a {@link ForwardingAudience.Single},
     *     call this method on the single audience inside the forwarding audience.</li>
     *     <li>Otherwise, if {@code audience} is a {@link ForwardingAudience}, call this
     *     method for each audience member of the forwarding audience.</li>
     *     <li>Otherwise, do nothing.</li>
     * </ol>
     *
     * @param audience the audience
     * @param packet   the packet
     */
    // Annotation for the following element
    @SuppressWarnings("OverrideOnly") // we need to access the audiences inside ForwardingAudience
    // Start of a method/block
    public static void sendPacket(Audience audience, ServerPacket packet) {
        // Multiple branching (switch/case)
        switch (audience) {
            // Multiple branching (switch/case)
            case Player player -> player.sendPacket(packet);
            // Multiple branching (switch/case)
            case PacketGroupingAudience groupingAudience -> sendGroupedPacket(groupingAudience.getPlayers(), packet);
            // Multiple branching (switch/case)
            case ForwardingAudience.Single singleAudience -> sendPacket(singleAudience.audience(), packet);
            // Multiple branching (switch/case)
            case ForwardingAudience forwardingAudience -> {
                // Loop: repeats a block
                for (Audience member : forwardingAudience.audiences()) {
                    // Calls a method
                    sendPacket(member, packet);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            default -> {
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Sends a {@link ServerPacket} to multiple players.
     * <p>
     * Can drastically improve performance since the packet will not have to be processed as much.
     *
     * @param players   the players to send the packet to
     * @param packet    the packet to send to the players
     * @param predicate predicate to ignore specific players
     */
    // Code statement
    public static <T extends Player> void sendGroupedPacket(Collection<T> players, ServerPacket packet,
                                         // Start of a method/block
                                         Predicate<? super T> predicate) {
        // Calls a method
        final SendablePacket sendablePacket = groupedPacket(packet);
        // Start of a method/block
        players.forEach(player -> {
            // Branch: checks a condition
            if (predicate.test(player)) player.sendPacket(sendablePacket);
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * Same as {@link #sendGroupedPacket(Collection, ServerPacket, Predicate)}
     * but without any predicate.
     *
     * @see #sendGroupedPacket(Collection, ServerPacket, Predicate)
     */
    // Start of a method/block
    public static void sendGroupedPacket(Collection<? extends Player> players, ServerPacket packet) {
        // Calls a method
        final SendablePacket sendablePacket = groupedPacket(packet);
        // Calls a method
        players.forEach(player -> player.sendPacket(sendablePacket));
    // End of a block/expression
    }

    // Start of a method/block
    public static void broadcastPlayPacket(ServerPacket packet) {
        // Calls a method
        sendGroupedPacket(MinecraftServer.getConnectionManager().getOnlinePlayers(), packet);
    // End of a block/expression
    }

    // Start of a method/block
    private static SendablePacket groupedPacket(ServerPacket packet) {
        // Returns a value to the caller
        return ServerFlag.GROUPED_PACKET && shouldUseCachePacket(packet) ? new CachedPacket(packet) : packet;
    // End of a block/expression
    }

    /**
     * Checks if the {@link ServerPacket} is suitable to be wrapped into a {@link CachedPacket}.
     * Note: {@link ServerPacket.ComponentHolding}s are not translated inside a {@link CachedPacket}.
     *
     * @see CachedPacket#body(ConnectionState)
     */
    // Start of a method/block
    private static boolean shouldUseCachePacket(final ServerPacket packet) {
        // Branch: checks a condition
        if (!ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION) return true;
        // Branch: checks a condition
        if (!(packet instanceof ServerPacket.ComponentHolding holder)) return true;
        // Returns a value to the caller
        return !containsTranslatableComponents(holder);
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean containsTranslatableComponents(final ComponentHolder<?> holder) {
        // Loop: repeats a block
        for (final Component component : holder.components()) {
            // Branch: checks a condition
            if (isTranslatable(component)) return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean isTranslatable(final Component component) {
        // Branch: checks a condition
        if (component instanceof TranslatableComponent) return true;
        // Calls a method
        final HoverEvent<?> hoverEvent = component.hoverEvent();
        // Branch: checks a condition
        if (hoverEvent != null && hoverEvent.value() instanceof Component hoverComponent && isTranslatable(hoverComponent))
            // Returns a value to the caller
            return true;
        // Calls a method
        final List<Component> children = component.children();
        // Branch: checks a condition
        if (children.isEmpty()) return false;
        // Loop: repeats a block
        for (final Component child : children) {
            // Branch: checks a condition
            if (isTranslatable(child)) return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }
// End of a block/expression
}
