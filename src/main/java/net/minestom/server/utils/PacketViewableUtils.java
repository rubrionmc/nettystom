// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.LongArrayList;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.LongList;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.Viewable;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketWriting;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.BufferedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerSocketConnection;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.WeakHashMap;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class PacketViewableUtils {
    // Viewable packets
    // Appelle une méthode
    private static volatile Map<Viewable, ViewableStorage> storageMap = new WeakHashMap<>();

    // Instruction de code
    public static void prepareViewablePacket(Viewable viewable, ServerPacket serverPacket,
                                             // Annotation pour l'élément suivant
                                             @Nullable Entity entity) {
        // Embranchement : vérifie une condition
        if (entity != null && !entity.hasPredictableViewers()) {
            // Operation cannot be optimized
            // Appelle une méthode
            entity.sendPacketToViewers(serverPacket);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (!ServerFlag.VIEWABLE_PACKET) {
            // Appelle une méthode
            PacketSendingUtils.sendGroupedPacket(viewable.getViewers(), serverPacket, value -> !Objects.equals(value, entity));
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final Player exception = entity instanceof Player ? (Player) entity : null;
        // Appelle une méthode
        ViewableStorage storage = retrieveStorage(viewable);
        // Appelle une méthode
        storage.append(serverPacket, exception);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static ViewableStorage retrieveStorage(Viewable viewable) {
        // Affecte une valeur
        Map<Viewable, ViewableStorage> map = storageMap;
        // Appelle une méthode
        ViewableStorage storage = map.get(viewable);
        // Embranchement : vérifie une condition
        if (storage == null) {
            // Début d'une méthode/d'un bloc
            synchronized (PacketViewableUtils.class) {
                // Affecte une valeur
                map = storageMap;
                // Appelle une méthode
                storage = map.get(viewable);
                // Embranchement : vérifie une condition
                if (storage == null) {
                    // Appelle une méthode
                    storage = new ViewableStorage();
                    // Appelle une méthode
                    map = new WeakHashMap<>(map);
                    // Appelle une méthode
                    map.put(viewable, storage);
                    // Affecte une valeur
                    storageMap = map;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return storage;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void flush() {
        // Embranchement : vérifie une condition
        if (!ServerFlag.VIEWABLE_PACKET) return;
        // Affecte une valeur
        Map<Viewable, ViewableStorage> map = storageMap;
        // Instruction de code
        map.entrySet().parallelStream().forEach(entry ->
                // Appelle une méthode
                entry.getValue().process(entry.getKey()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void prepareViewablePacket(Viewable viewable, ServerPacket serverPacket) {
        // Appelle une méthode
        prepareViewablePacket(viewable, serverPacket, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static final class ViewableStorage {
        // Affecte une valeur
        private static final ObjectPool<NetworkBuffer> POOL = ObjectPool.pool(
                // Instruction de code
                () -> NetworkBuffer.resizableBuffer(ServerFlag.POOLED_BUFFER_SIZE, MinecraftServer.process()),
                // Instruction de code
                NetworkBuffer::clear);
        // Player id -> list of offsets to ignore (32:32 bits)
        // Appelle une méthode
        private final Int2ObjectMap<LongArrayList> entityIdMap = new Int2ObjectOpenHashMap<>();
        // Appelle une méthode
        private final NetworkBuffer buffer = POOL.getAndRegister(this);

        // Début d'une méthode/d'un bloc
        private synchronized void append(ServerPacket serverPacket, @Nullable Player exception) {
            // Appelle une méthode
            final long start = buffer.writeIndex();
            // Viewable storage is only used for play packets, so fine to assume this.
            // Appelle une méthode
            PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, serverPacket, MinecraftServer.getCompressionThreshold());
            // Appelle une méthode
            final long end = buffer.writeIndex();
            // Embranchement : vérifie une condition
            if (exception != null) {
                // Affecte une valeur
                final long offsets = start << 32 | end & 0xFFFFFFFFL;
                // Appelle une méthode
                LongList list = entityIdMap.computeIfAbsent(exception.getEntityId(), id -> new LongArrayList());
                // Appelle une méthode
                list.add(offsets);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private synchronized void process(Viewable viewable) {
            // Embranchement : vérifie une condition
            if (buffer.writeIndex() == 0) return;
            // Appelle une méthode
            NetworkBuffer copy = buffer.copy(0, buffer.writeIndex());
            // Appelle une méthode
            copy.readOnly();
            // Appelle une méthode
            viewable.getViewers().forEach(player -> processPlayer(player, copy));
            // Accès à l'objet courant/parent
            this.buffer.clear();
            // Accès à l'objet courant/parent
            this.entityIdMap.clear();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void processPlayer(Player player, NetworkBuffer buffer) {
            // Appelle une méthode
            final long capacity = buffer.capacity();
            // Appelle une méthode
            final PlayerConnection connection = player.getPlayerConnection();
            // Appelle une méthode
            final LongArrayList pairs = entityIdMap.get(player.getEntityId());
            // Embranchement : vérifie une condition
            if (pairs == null) {
                // No range exception, write the whole buffer
                // Appelle une méthode
                writeTo(connection, buffer, 0, capacity);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Player has range exception(s)
            // Ensure that we skip the specified parts of the buffer
            // Affecte une valeur
            int lastWrite = 0;
            // Appelle une méthode
            final long[] elements = pairs.elements();
            // Boucle : répète un bloc
            for (int i = 0; i < pairs.size(); ++i) {
                // Affecte une valeur
                final long offsets = elements[i];
                // Appelle une méthode
                final int start = (int) (offsets >> 32);
                // Embranchement : vérifie une condition
                if (start != lastWrite) writeTo(connection, buffer, lastWrite, start - lastWrite);
                // Affecte une valeur
                lastWrite = (int) offsets; // End = last 32 bits
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (capacity != lastWrite) writeTo(connection, buffer, lastWrite, capacity - lastWrite);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static void writeTo(PlayerConnection connection, NetworkBuffer buffer, long offset, long length) {
            // Embranchement : vérifie une condition
            if (connection instanceof PlayerSocketConnection socketConnection) {
                // Appelle une méthode
                socketConnection.sendPacket(new BufferedPacket(buffer, offset, length));
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // TODO for non-socket connection
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
