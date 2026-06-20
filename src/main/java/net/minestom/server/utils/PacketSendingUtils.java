// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.audience.ForwardingAudience;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.TranslatableComponent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.ComponentHolder;
// Import d'une classe nécessaire
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Déclaration de type (classe/interface/enum/record)
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
    // Annotation pour l'élément suivant
    @SuppressWarnings("OverrideOnly") // we need to access the audiences inside ForwardingAudience
    // Début d'une méthode/d'un bloc
    public static void sendPacket(Audience audience, ServerPacket packet) {
        // Embranchement multiple (switch/case)
        switch (audience) {
            // Embranchement multiple (switch/case)
            case Player player -> player.sendPacket(packet);
            // Embranchement multiple (switch/case)
            case PacketGroupingAudience groupingAudience -> sendGroupedPacket(groupingAudience.getPlayers(), packet);
            // Embranchement multiple (switch/case)
            case ForwardingAudience.Single singleAudience -> sendPacket(singleAudience.audience(), packet);
            // Embranchement multiple (switch/case)
            case ForwardingAudience forwardingAudience -> {
                // Boucle : répète un bloc
                for (Audience member : forwardingAudience.audiences()) {
                    // Appelle une méthode
                    sendPacket(member, packet);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            default -> {
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    public static <T extends Player> void sendGroupedPacket(Collection<T> players, ServerPacket packet,
                                         // Début d'une méthode/d'un bloc
                                         Predicate<? super T> predicate) {
        // Appelle une méthode
        final SendablePacket sendablePacket = groupedPacket(packet);
        // Début d'une méthode/d'un bloc
        players.forEach(player -> {
            // Embranchement : vérifie une condition
            if (predicate.test(player)) player.sendPacket(sendablePacket);
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * Same as {@link #sendGroupedPacket(Collection, ServerPacket, Predicate)}
     * but without any predicate.
     *
     * @see #sendGroupedPacket(Collection, ServerPacket, Predicate)
     */
    // Début d'une méthode/d'un bloc
    public static void sendGroupedPacket(Collection<? extends Player> players, ServerPacket packet) {
        // Appelle une méthode
        final SendablePacket sendablePacket = groupedPacket(packet);
        // Appelle une méthode
        players.forEach(player -> player.sendPacket(sendablePacket));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void broadcastPlayPacket(ServerPacket packet) {
        // Appelle une méthode
        sendGroupedPacket(MinecraftServer.getConnectionManager().getOnlinePlayers(), packet);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static SendablePacket groupedPacket(ServerPacket packet) {
        // Renvoie une valeur à l'appelant
        return ServerFlag.GROUPED_PACKET && shouldUseCachePacket(packet) ? new CachedPacket(packet) : packet;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the {@link ServerPacket} is suitable to be wrapped into a {@link CachedPacket}.
     * Note: {@link ServerPacket.ComponentHolding}s are not translated inside a {@link CachedPacket}.
     *
     * @see CachedPacket#body(ConnectionState)
     */
    // Début d'une méthode/d'un bloc
    private static boolean shouldUseCachePacket(final ServerPacket packet) {
        // Embranchement : vérifie une condition
        if (!ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION) return true;
        // Embranchement : vérifie une condition
        if (!(packet instanceof ServerPacket.ComponentHolding holder)) return true;
        // Renvoie une valeur à l'appelant
        return !containsTranslatableComponents(holder);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean containsTranslatableComponents(final ComponentHolder<?> holder) {
        // Boucle : répète un bloc
        for (final Component component : holder.components()) {
            // Embranchement : vérifie une condition
            if (isTranslatable(component)) return true;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean isTranslatable(final Component component) {
        // Embranchement : vérifie une condition
        if (component instanceof TranslatableComponent) return true;
        // Appelle une méthode
        final HoverEvent<?> hoverEvent = component.hoverEvent();
        // Embranchement : vérifie une condition
        if (hoverEvent != null && hoverEvent.value() instanceof Component hoverComponent && isTranslatable(hoverComponent))
            // Renvoie une valeur à l'appelant
            return true;
        // Appelle une méthode
        final List<Component> children = component.children();
        // Embranchement : vérifie une condition
        if (children.isEmpty()) return false;
        // Boucle : répète un bloc
        for (final Component child : children) {
            // Embranchement : vérifie une condition
            if (isTranslatable(child)) return true;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
