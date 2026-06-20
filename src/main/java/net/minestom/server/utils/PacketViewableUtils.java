// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import it.unimi.dsi.fastutil.longs.LongArrayList;
// Import of a required class
import it.unimi.dsi.fastutil.longs.LongList;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.Viewable;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.PacketWriting;
// Import of a required class
import net.minestom.server.network.packet.server.BufferedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.network.player.PlayerSocketConnection;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.WeakHashMap;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class PacketViewableUtils {
    // Viewable packets
    // Calls a method
    private static volatile Map<Viewable, ViewableStorage> storageMap = new WeakHashMap<>();

    // Code statement
    public static void prepareViewablePacket(Viewable viewable, ServerPacket serverPacket,
                                             // Annotation for the following element
                                             @Nullable Entity entity) {
        // Branch: checks a condition
        if (entity != null && !entity.hasPredictableViewers()) {
            // Operation cannot be optimized
            // Calls a method
            entity.sendPacketToViewers(serverPacket);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (!ServerFlag.VIEWABLE_PACKET) {
            // Calls a method
            PacketSendingUtils.sendGroupedPacket(viewable.getViewers(), serverPacket, value -> !Objects.equals(value, entity));
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        final Player exception = entity instanceof Player ? (Player) entity : null;
        // Calls a method
        ViewableStorage storage = retrieveStorage(viewable);
        // Calls a method
        storage.append(serverPacket, exception);
    // End of a block/expression
    }

    // Start of a method/block
    private static ViewableStorage retrieveStorage(Viewable viewable) {
        // Assigns a value
        Map<Viewable, ViewableStorage> map = storageMap;
        // Calls a method
        ViewableStorage storage = map.get(viewable);
        // Branch: checks a condition
        if (storage == null) {
            // Start of a method/block
            synchronized (PacketViewableUtils.class) {
                // Assigns a value
                map = storageMap;
                // Calls a method
                storage = map.get(viewable);
                // Branch: checks a condition
                if (storage == null) {
                    // Calls a method
                    storage = new ViewableStorage();
                    // Calls a method
                    map = new WeakHashMap<>(map);
                    // Calls a method
                    map.put(viewable, storage);
                    // Assigns a value
                    storageMap = map;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return storage;
    // End of a block/expression
    }

    // Start of a method/block
    public static void flush() {
        // Branch: checks a condition
        if (!ServerFlag.VIEWABLE_PACKET) return;
        // Assigns a value
        Map<Viewable, ViewableStorage> map = storageMap;
        // Code statement
        map.entrySet().parallelStream().forEach(entry ->
                // Calls a method
                entry.getValue().process(entry.getKey()));
    // End of a block/expression
    }

    // Start of a method/block
    public static void prepareViewablePacket(Viewable viewable, ServerPacket serverPacket) {
        // Calls a method
        prepareViewablePacket(viewable, serverPacket, null);
    // End of a block/expression
    }

    // Start of a method/block
    private static final class ViewableStorage {
        // Assigns a value
        private static final ObjectPool<NetworkBuffer> POOL = ObjectPool.pool(
                // Code statement
                () -> NetworkBuffer.resizableBuffer(ServerFlag.POOLED_BUFFER_SIZE, MinecraftServer.process()),
                // Code statement
                NetworkBuffer::clear);
        // Player id -> list of offsets to ignore (32:32 bits)
        // Calls a method
        private final Int2ObjectMap<LongArrayList> entityIdMap = new Int2ObjectOpenHashMap<>();
        // Calls a method
        private final NetworkBuffer buffer = POOL.getAndRegister(this);

        // Start of a method/block
        private synchronized void append(ServerPacket serverPacket, @Nullable Player exception) {
            // Calls a method
            final long start = buffer.writeIndex();
            // Viewable storage is only used for play packets, so fine to assume this.
            // Calls a method
            PacketWriting.writeFramedPacket(buffer, ConnectionState.PLAY, serverPacket, MinecraftServer.getCompressionThreshold());
            // Calls a method
            final long end = buffer.writeIndex();
            // Branch: checks a condition
            if (exception != null) {
                // Assigns a value
                final long offsets = start << 32 | end & 0xFFFFFFFFL;
                // Calls a method
                LongList list = entityIdMap.computeIfAbsent(exception.getEntityId(), id -> new LongArrayList());
                // Calls a method
                list.add(offsets);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        private synchronized void process(Viewable viewable) {
            // Branch: checks a condition
            if (buffer.writeIndex() == 0) return;
            // Calls a method
            NetworkBuffer copy = buffer.copy(0, buffer.writeIndex());
            // Calls a method
            copy.readOnly();
            // Calls a method
            viewable.getViewers().forEach(player -> processPlayer(player, copy));
            // Access to the current/parent object
            this.buffer.clear();
            // Access to the current/parent object
            this.entityIdMap.clear();
        // End of a block/expression
        }

        // Start of a method/block
        private void processPlayer(Player player, NetworkBuffer buffer) {
            // Calls a method
            final long capacity = buffer.capacity();
            // Calls a method
            final PlayerConnection connection = player.getPlayerConnection();
            // Calls a method
            final LongArrayList pairs = entityIdMap.get(player.getEntityId());
            // Branch: checks a condition
            if (pairs == null) {
                // No range exception, write the whole buffer
                // Calls a method
                writeTo(connection, buffer, 0, capacity);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Player has range exception(s)
            // Ensure that we skip the specified parts of the buffer
            // Assigns a value
            int lastWrite = 0;
            // Calls a method
            final long[] elements = pairs.elements();
            // Loop: repeats a block
            for (int i = 0; i < pairs.size(); ++i) {
                // Assigns a value
                final long offsets = elements[i];
                // Calls a method
                final int start = (int) (offsets >> 32);
                // Branch: checks a condition
                if (start != lastWrite) writeTo(connection, buffer, lastWrite, start - lastWrite);
                // Assigns a value
                lastWrite = (int) offsets; // End = last 32 bits
            // End of a block/expression
            }
            // Branch: checks a condition
            if (capacity != lastWrite) writeTo(connection, buffer, lastWrite, capacity - lastWrite);
        // End of a block/expression
        }

        // Start of a method/block
        private static void writeTo(PlayerConnection connection, NetworkBuffer buffer, long offset, long length) {
            // Branch: checks a condition
            if (connection instanceof PlayerSocketConnection socketConnection) {
                // Calls a method
                socketConnection.sendPacket(new BufferedPacket(buffer, offset, length));
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // TODO for non-socket connection
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
