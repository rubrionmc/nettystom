// Package declaration for this file
package net.minestom.server.component;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.collection.ObjectArray;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
record DataComponentImpl<T>(
        // Code statement
        int id,
        // Code statement
        Key key,
        // Annotation for the following element
        @Nullable NetworkBuffer.Type<T> networkType,
        // Annotation for the following element
        @Nullable Codec<T> codec,
        // Annotation for the following element
        @Nullable UnaryOperator<T> freeze
// Start of a method/block
) implements DataComponent<T> {
    // Calls a method
    static final Map<Key, DataComponent<?>> NAMESPACES = new HashMap<>(32);
    // Calls a method
    static final ObjectArray<DataComponent<?>> IDS = ObjectArray.singleThread(32);

    // Start of a method/block
    static <T> DataComponent<T> register(@KeyPattern String name, @Nullable NetworkBuffer.Type<T> network, @Nullable Codec<T> nbt) {
        // Returns a value to the caller
        return register(name, network, nbt, null);
    // End of a block/expression
    }

    // Used when Collections are involved, where T could still be mutable.
    // Start of a method/block
    static <T> DataComponent<T> register(@KeyPattern String name, @Nullable NetworkBuffer.Type<T> network, @Nullable Codec<T> nbt, @Nullable UnaryOperator<T> freeze) {
        // Calls a method
        DataComponent<T> impl = DataComponent.createHeadless(NAMESPACES.size(), Key.key(name), network, nbt, freeze);
        // Calls a method
        NAMESPACES.put(impl.key(), impl);
        // Calls a method
        IDS.set(impl.id(), impl);
        // Returns a value to the caller
        return impl;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isSynced() {
        // Returns a value to the caller
        return networkType != null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isSerialized() {
        // Returns a value to the caller
        return codec != null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <D> Result<T> decode(Transcoder<D> coder, D value) {
        // Calls a method
        Check.notNull(codec, "{0} cannot be deserialized from Codec", this);
        // Returns a value to the caller
        return this.codec.decode(coder, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
        // Calls a method
        Check.notNull(codec, "{0} cannot be deserialized from Codec", this);
        // Returns a value to the caller
        return this.codec.encode(coder, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public T read(NetworkBuffer reader) {
        // Calls a method
        Check.notNull(networkType, "{0} cannot be deserialized from network", this);
        // Returns a value to the caller
        return networkType.read(reader);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void write(NetworkBuffer writer, T value) {
        // Calls a method
        Check.notNull(networkType, "{0} cannot be serialized to network", this);
        // Calls a method
        networkType.write(writer, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public T freeze(T value) {
        // Branch: checks a condition
        if (freeze == null) return value;
        // Returns a value to the caller
        return freeze.apply(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return name();
    // End of a block/expression
    }
// End of a block/expression
}
