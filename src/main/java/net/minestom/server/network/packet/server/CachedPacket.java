// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketWriting;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.lang.ref.SoftReference;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * Represents a packet that is only computed when required (either due to memory demand or invalidated data)
 * <p>
 * The cache is stored in a {@link SoftReference} and is invalidated when {@link #invalidate()} is called.
 * <p>
 * Packet supplier must be thread-safe.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class CachedPacket implements SendablePacket {
    // Instruction de code
    private final Supplier<ServerPacket> packetSupplier;
    // Instruction de code
    private volatile @Nullable SoftReference<FramedPacket> packet;

    // Début d'une méthode/d'un bloc
    public CachedPacket(Supplier<ServerPacket> packetSupplier) {
        // Accès à l'objet courant/parent
        this.packetSupplier = packetSupplier;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CachedPacket(ServerPacket packet) {
        // Appelle une méthode
        this(() -> packet);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void invalidate() {
        // Accès à l'objet courant/parent
        this.packet = null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ServerPacket packet(ConnectionState state) {
        // Appelle une méthode
        FramedPacket cache = updatedCache(state);
        // Renvoie une valeur à l'appelant
        return cache != null ? cache.packet() : packetSupplier.get();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable NetworkBuffer body(ConnectionState state) {
        // Appelle une méthode
        FramedPacket cache = updatedCache(state);
        // Renvoie une valeur à l'appelant
        return cache != null ? cache.body() : null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private @Nullable FramedPacket updatedCache(ConnectionState state) {
        // Embranchement : vérifie une condition
        if (!ServerFlag.CACHED_PACKET)
            // Renvoie une valeur à l'appelant
            return null;
        // Affecte une valeur
        SoftReference<FramedPacket> ref = packet;
        // Instruction de code
        FramedPacket cache;
        // Embranchement : vérifie une condition
        if (ref == null || (cache = ref.get()) == null) {
            // Appelle une méthode
            final ServerPacket packet = packetSupplier.get();
            // Affecte une valeur
            final NetworkBuffer buffer = PacketWriting.allocateTrimmedPacket(state, packet,
                    // Appelle une méthode
                    MinecraftServer.getCompressionThreshold());
            // Appelle une méthode
            cache = new FramedPacket(packet, buffer);
            // Accès à l'objet courant/parent
            this.packet = new SoftReference<>(cache);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return cache;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isValid() {
        // Affecte une valeur
        final SoftReference<FramedPacket> ref = packet;
        // Renvoie une valeur à l'appelant
        return ref != null && ref.get() != null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Affecte une valeur
        final SoftReference<FramedPacket> ref = packet;
        // Appelle une méthode
        final FramedPacket cache = ref != null ? ref.get() : null;
        // Renvoie une valeur à l'appelant
        return String.format("CachedPacket{cache=%s}", cache);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
