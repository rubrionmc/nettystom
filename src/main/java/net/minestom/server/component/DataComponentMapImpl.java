// Package declaration for this file
package net.minestom.server.component;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.codec.Transcoder.MapLike;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.NotNull;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.IntFunction;

/**
 * <p>A component list, always stored as a patch of added and removed components (even if none are removed).</p>
 *
 * <p>The inner map contains the value for added components, null for removed components, and no entry for unmodified components.</p>
 *
 * @param components The component patch.
 */
// Type declaration (class/interface/enum/record)
record DataComponentMapImpl(Int2ObjectMap<@Nullable Object> components) implements DataComponentMap {
    // Assigns a value
    private static final char REMOVAL_PREFIX = '!';

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isEmpty() {
        // Returns a value to the caller
        return components.isEmpty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean has(DataComponent<?> component) {
        // Returns a value to the caller
        return components.containsKey(component.id()) && components.get(component.id()) != null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @Nullable T get(DataComponent<T> component) {
        //noinspection unchecked
        // Returns a value to the caller
        return (T) components.get(component.id());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean has(DataComponentMap prototype, DataComponent<?> component) {
        // Branch: checks a condition
        if (components.containsKey(component.id())) {
            // Returns a value to the caller
            return components.get(component.id()) != null;
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return prototype.has(component);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @Nullable T get(DataComponentMap prototype, DataComponent<T> component) {
        // Branch: checks a condition
        if (components.containsKey(component.id())) {
            //noinspection unchecked
            // Returns a value to the caller
            return (T) components.get(component.id());
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return prototype.get(component);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> DataComponentMap set(DataComponent<T> component, T value) {
        // Calls a method
        Int2ObjectMap<Object> newComponents = new Int2ObjectArrayMap<>(components);
        // Calls a method
        newComponents.put(component.id(), component.freeze(value));
        // Returns a value to the caller
        return new DataComponentMapImpl(newComponents);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DataComponentMap remove(DataComponent<?> component) {
        // Calls a method
        Int2ObjectMap<@Nullable Object> newComponents = new Int2ObjectArrayMap<>(components);
        // Calls a method
        newComponents.put(component.id(), null);
        // Returns a value to the caller
        return new DataComponentMapImpl(newComponents);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<DataComponent.Value> entrySet() {
        // Branch: checks a condition
        if (components.isEmpty()) return List.of();
        // Calls a method
        final List<DataComponent.Value> entries = new ArrayList<>(components.size());
        // Loop: repeats a block
        for (var entry : components.int2ObjectEntrySet())
            // Calls a method
            entries.add(new DataComponent.Value(DataComponent.fromId(entry.getIntKey()), entry.getValue()));
        // Returns a value to the caller
        return entries;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Builder toBuilder() {
        // Returns a value to the caller
        return new BuilderImpl(new Int2ObjectArrayMap<>(components));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public PatchBuilder toPatchBuilder() {
        // Returns a value to the caller
        return new PatchBuilderImpl(new Int2ObjectArrayMap<>(components));
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record BuilderImpl(Int2ObjectMap<@Nullable Object> components) implements DataComponentMap.Builder {

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean has(DataComponent<?> component) {
            // Returns a value to the caller
            return components.get(component.id()) != null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> @Nullable T get(DataComponent<T> component) {
            //noinspection unchecked
            // Returns a value to the caller
            return (T) components.get(component.id());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> Builder set(DataComponent<T> component, T value) {
            // Calls a method
            components.put(component.id(), component.freeze(value));
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public DataComponentMap build() {
            // Returns a value to the caller
            return new DataComponentMapImpl(new Int2ObjectArrayMap<>(components));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record PatchBuilderImpl(Int2ObjectMap<@Nullable Object> components) implements DataComponentMap.PatchBuilder {

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean has(DataComponent<?> component) {
            // Returns a value to the caller
            return components.get(component.id()) != null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> @Nullable T get(DataComponent<T> component) {
            //noinspection unchecked
            // Returns a value to the caller
            return (T) components.get(component.id());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> PatchBuilder set(DataComponent<T> component, T value) {
            // Calls a method
            components.put(component.id(), component.freeze(value));
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public PatchBuilder remove(DataComponent<?> component) {
            // Calls a method
            components.put(component.id(), null);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public DataComponentMap build() {
            // Returns a value to the caller
            return new DataComponentMapImpl(new Int2ObjectArrayMap<>(components));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record NetworkTypeImpl(
            // Code statement
            IntFunction<@Nullable DataComponent<?>> idToType,
            // Code statement
            boolean isPatch, boolean isTrusted
    // Start of a method/block
    ) implements NetworkBuffer.Type<DataComponentMap> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, DataComponentMap value) {
            // Calls a method
            final DataComponentMapImpl patch = (DataComponentMapImpl) value;
            // Assigns a value
            int added = 0;
            // Loop: repeats a block
            for (Object o : patch.components.values()) {
                // Branch: checks a condition
                if (o != null) added++;
            // End of a block/expression
            }

            // Calls a method
            buffer.write(NetworkBuffer.VAR_INT, added);
            // Branch: checks a condition
            if (isPatch) {
                // Calls a method
                buffer.write(NetworkBuffer.VAR_INT, patch.components.size() - added);
            // End of a block/expression
            }
            // Loop: repeats a block
            for (var entry : patch.components.int2ObjectEntrySet()) {
                // Branch: checks a condition
                if (entry.getValue() == null) continue;

                // Calls a method
                buffer.write(NetworkBuffer.VAR_INT, entry.getIntKey());
                //noinspection unchecked
                // Calls a method
                DataComponent<Object> type = (DataComponent<@NotNull Object>) this.idToType.apply(entry.getIntKey());
                // Calls a method
                Check.notNull(type, "Unknown component id: {0}", entry.getIntKey());
                // Branch: checks a condition
                if (isTrusted) {
                    // Calls a method
                    type.write(buffer, entry.getValue());
                // Alternative branch of the condition
                } else {
                    // Need to length prefix it, so write to another buffer first then copy.
                    // Calls a method
                    final byte[] componentData = NetworkBuffer.makeArray(b -> type.write(b, entry.getValue()), buffer.registries());
                    // Calls a method
                    buffer.write(NetworkBuffer.BYTE_ARRAY, componentData);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Branch: checks a condition
            if (isPatch) {
                // Loop: repeats a block
                for (var entry : patch.components.int2ObjectEntrySet()) {
                    // Branch: checks a condition
                    if (entry.getValue() != null) continue;

                    // Calls a method
                    buffer.write(NetworkBuffer.VAR_INT, entry.getIntKey());
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public DataComponentMap read(NetworkBuffer buffer) {
            // Calls a method
            int added = buffer.read(NetworkBuffer.VAR_INT);
            // Calls a method
            int removed = isPatch ? buffer.read(NetworkBuffer.VAR_INT) : 0;
            // Calls a method
            Check.stateCondition(added + removed > 256, "Data component map too large: {0}", added + removed);
            // Calls a method
            Int2ObjectMap<@Nullable Object> patch = new Int2ObjectArrayMap<>(added + removed);
            // Loop: repeats a block
            for (int i = 0; i < added; i++) {
                // Calls a method
                int id = buffer.read(NetworkBuffer.VAR_INT);
                //noinspection unchecked
                // Calls a method
                DataComponent<Object> type = (DataComponent<@NotNull Object>) this.idToType.apply(id);
                // Calls a method
                Check.notNull(type, "Unknown component: {0}", id);
                // Branch: checks a condition
                if (isTrusted) {
                    // Calls a method
                    patch.put(type.id(), type.read(buffer));
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    final byte[] array = buffer.read(NetworkBuffer.BYTE_ARRAY);
                    // Calls a method
                    final NetworkBuffer tempBuffer = NetworkBuffer.wrap(array, 0, array.length, buffer.registries());
                    // Calls a method
                    patch.put(type.id(), type.read(tempBuffer));
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Loop: repeats a block
            for (int i = 0; i < removed; i++) {
                // Calls a method
                int id = buffer.read(NetworkBuffer.VAR_INT);
                // Calls a method
                patch.put(id, null);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new DataComponentMapImpl(patch);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record CodecImpl(
            // Code statement
            IntFunction<@Nullable DataComponent<?>> idToType,
            // Code statement
            Function<String, @Nullable DataComponent<?>> nameToType,
            // Code statement
            boolean isPatch
    // Start of a method/block
    ) implements Codec<DataComponentMap> {
        // Start of a method/block
        CodecImpl {
            // Calls a method
            Objects.requireNonNull(idToType, "idToType");
            // Calls a method
            Objects.requireNonNull(nameToType, "nameToType");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<DataComponentMap> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<MapLike<D>> mapResult = coder.getMap(value);
            // Branch: checks a condition
            if (!(mapResult instanceof Result.Ok(var map)))
                // Returns a value to the caller
                return mapResult.cast();
            // Branch: checks a condition
            if (map.isEmpty()) return new Result.Ok<>(EMPTY);

            // Calls a method
            final Int2ObjectMap<@Nullable Object> patch = new Int2ObjectArrayMap<>(map.size());
            // Loop: repeats a block
            for (String key : map.keys()) {
                // Assigns a value
                boolean remove = false;
                // Branch: checks a condition
                if (!key.isEmpty() && key.charAt(0) == REMOVAL_PREFIX) {
                    // Calls a method
                    key = key.substring(1);
                    // Assigns a value
                    remove = true;
                // End of a block/expression
                }
                // Calls a method
                final DataComponent<?> type = this.nameToType.apply(key);
                // Branch: checks a condition
                if (type == null) return new Result.Error<>("unknown data component: " + key);

                // Branch: checks a condition
                if (remove) {
                    // Branch: checks a condition
                    if (isPatch) patch.put(type.id(), null);
                    // Removing a component in an absolute (non-patch) builder is a noop because it is not yet present.
                // Alternative branch of the condition
                } else {
                    // Multiple branching (switch/case)
                    switch (map.getValue(key).map(v -> type.decode(coder, v))) {
                        // Multiple branching (switch/case)
                        case Result.Ok(Object componentData) -> patch.put(type.id(), componentData);
                        // Multiple branching (switch/case)
                        case Result.Error<?>(String message) -> {
                            // Returns a value to the caller
                            return new Result.Error<>(type.name() + ": " + message);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Returns a value to the caller
            return new Result.Ok<>(new DataComponentMapImpl(patch));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable DataComponentMap value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Calls a method
            final DataComponentMapImpl patch = (DataComponentMapImpl) value;

            // Calls a method
            final Transcoder.MapBuilder<D> map = coder.createMap();
            // Loop: repeats a block
            for (var entry : patch.components.int2ObjectEntrySet()) {
                //noinspection unchecked
                // Calls a method
                DataComponent<Object> type = (DataComponent<@NotNull Object>) this.idToType.apply(entry.getIntKey());
                // Branch: checks a condition
                if (type == null) return new Result.Error<>("unknown data component id: " + entry.getIntKey());
                // Branch: checks a condition
                if (entry.getValue() == null) {
                    // Branch: checks a condition
                    if (isPatch) map.put(REMOVAL_PREFIX + type.name(), coder.createMap().build());
                    // Removing a component in an absolute (non-patch) builder is a noop because it is not yet present.
                // Alternative branch of the condition
                } else {
                    // Multiple branching (switch/case)
                    switch (type.encode(coder, entry.getValue())) {
                        // Multiple branching (switch/case)
                        case Result.Ok(D componentValue) -> map.put(type.name(), componentValue);
                        // Multiple branching (switch/case)
                        case Result.Error<?>(String message) -> {
                            // Returns a value to the caller
                            return new Result.Error<>(type.name() + ": " + message);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Returns a value to the caller
            return new Result.Ok<>(map.build());
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
