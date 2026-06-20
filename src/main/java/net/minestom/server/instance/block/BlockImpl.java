// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.utils.block.BlockUtils;
// Import of a required class
import net.minestom.server.utils.collection.ObjectArray;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.*;

// Type declaration (class/interface/enum/record)
record BlockImpl(RegistryData.BlockEntry registry,
                 // Code statement
                 long propertiesArray,
                 // Annotation for the following element
                 @Nullable CompoundBinaryTag nbt,
                 // Annotation for the following element
                 @Nullable BlockHandler handler) implements Block {
    /**
     * Number of bits used to store the index of a property value.
     * <p>
     * Block states are all stored within a single number.
     */
    // Assigns a value
    private static final int BITS_PER_INDEX = 5;

    // Assigns a value
    private static final int MAX_STATES = Long.SIZE / BITS_PER_INDEX;
    // Assigns a value
    private static final int MAX_VALUES = 1 << BITS_PER_INDEX;

    // Block state -> block object
    // Code statement
    private static final List<Block> BLOCK_STATE_MAP;
    // Block id -> valid property keys (order is important for lookup)
    // Code statement
    private static final List<PropertyType[]> PROPERTIES_TYPE;
    // Block id -> Map<Properties, Block>
    // Code statement
    private static final List<Long2ObjectArrayMap<BlockImpl>> POSSIBLE_STATES;
    // Code statement
    static final Registry<Block> REGISTRY;

    // Start of a method/block
    static {
        //TODO compute default sizes from the registry data
        // Calls a method
        ObjectArray<Block> blockStateMap = ObjectArray.singleThread();
        // Calls a method
        ObjectArray<PropertyType[]> propertiesType = ObjectArray.singleThread();
        // Calls a method
        ObjectArray<Long2ObjectArrayMap<BlockImpl>> possibleStates = ObjectArray.singleThread();
        // Calls a method
        HashMap<Object, Object> internCache = new HashMap<>();

        // Assigns a value
        REGISTRY = RegistryData.createStaticRegistry(
                // Code statement
                Key.key("block"),
                // Start of a method/block
                (namespace, properties) -> {
                    // Calls a method
                    final int blockId = properties.getInt("id");
                    // Calls a method
                    final RegistryData.Properties stateObject = properties.section("states");

                    // Retrieve properties
                    // Code statement
                    PropertyType[] propertyTypes;
                    // Start of a block
                    {
                        // Calls a method
                        RegistryData.Properties stateProperties = properties.section("properties");
                        // Branch: checks a condition
                        if (stateProperties != null) {
                            // Calls a method
                            final int stateCount = stateProperties.size();
                            // Branch: checks a condition
                            if (stateCount > MAX_STATES) {
                                // Throws an exception
                                throw new IllegalStateException("Too many properties for block " + namespace);
                            // End of a block/expression
                            }
                            // Assigns a value
                            propertyTypes = new PropertyType[stateCount];
                            // Assigns a value
                            int i = 0;
                            // Loop: repeats a block
                            for (var entry : stateProperties) {
                                // Calls a method
                                final var k = entry.getKey();
                                // Calls a method
                                final var v = (List<String>) entry.getValue();
                                // Calls a method
                                assert v.size() < MAX_VALUES;
                                // Calls a method
                                propertyTypes[i++] = new PropertyType(k, v);
                            // End of a block/expression
                            }
                        // Alternative branch of the condition
                        } else {
                            // Assigns a value
                            propertyTypes = new PropertyType[0];
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                    // Calls a method
                    propertiesType.set(blockId, propertyTypes);

                    // Calls a method
                    final RegistryData.BlockEntry baseBlockEntry = RegistryData.block(namespace, properties, internCache, null, null);

                    // Retrieve block states
                    // Start of a block
                    {
                        // Calls a method
                        final int propertiesCount = stateObject.size();
                        // Assigns a value
                        long[] propertiesKeys = new long[propertiesCount];
                        // Assigns a value
                        BlockImpl[] blocksValues = new BlockImpl[propertiesCount];
                        // Assigns a value
                        int propertiesOffset = 0;
                        // Loop: repeats a block
                        for (var stateEntry : stateObject) {
                            // Calls a method
                            final String query = stateEntry.getKey();
                            // Calls a method
                            final var stateOverride = (Map<String, Object>) stateEntry.getValue();
                            // Calls a method
                            final var propertyMap = BlockUtils.parseProperties(query);
                            // Calls a method
                            assert propertyTypes.length == propertyMap.size();
                            // Assigns a value
                            long propertiesValue = 0;
                            // Loop: repeats a block
                            for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                                // Calls a method
                                final byte keyIndex = findKeyIndexThrow(propertyTypes, entry.getKey(), null);
                                // Calls a method
                                final byte valueIndex = findValueIndexThrow(propertyTypes[keyIndex], entry.getValue(), null);
                                // Calls a method
                                propertiesValue = updateIndex(propertiesValue, keyIndex, valueIndex);
                            // End of a block/expression
                            }

                            // Calls a method
                            final RegistryData.BlockEntry entryOverride = RegistryData.block(namespace, RegistryData.Properties.fromMap(stateOverride), internCache, baseBlockEntry, properties);
                            // Assigns a value
                            final BlockImpl block = new BlockImpl(entryOverride,
                                    // Code statement
                                    propertiesValue, null, null);
                            // Calls a method
                            blockStateMap.set(block.stateId(), block);
                            // Assigns a value
                            propertiesKeys[propertiesOffset] = propertiesValue;
                            // Assigns a value
                            blocksValues[propertiesOffset++] = block;
                        // End of a block/expression
                        }
                        // Calls a method
                        possibleStates.set(blockId, new Long2ObjectArrayMap<>(propertiesKeys, blocksValues, propertiesOffset));
                    // End of a block/expression
                    }
                    // Register default state
                    // Calls a method
                    final int defaultState = properties.getInt("defaultStateId");
                    // Returns a value to the caller
                    return blockStateMap.get(defaultState);
                // End of a block/expression
                });
        // Calls a method
        BLOCK_STATE_MAP = blockStateMap.toList();
        // Calls a method
        PROPERTIES_TYPE = propertiesType.toList();
        // Calls a method
        POSSIBLE_STATES = possibleStates.toList();
    // End of a block/expression
    }

    // Start of a method/block
    static @UnknownNullability Block get(String key) {
        // Returns a value to the caller
        return REGISTRY.get(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static int statesCount() {
        // Returns a value to the caller
        return BLOCK_STATE_MAP.size();
    // End of a block/expression
    }

    // Start of a method/block
    static Block getState(int stateId) {
        // Returns a value to the caller
        return BLOCK_STATE_MAP.get(stateId);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Block parseState(String input) {
        // Branch: checks a condition
        if (input.isEmpty()) return null;
        // Calls a method
        final int nbtIndex = input.indexOf("[");
        // Branch: checks a condition
        if (nbtIndex == 0) return null;
        // Branch: checks a condition
        if (nbtIndex == -1) return Block.fromKey(input);
        // Branch: checks a condition
        if (!input.endsWith("]")) return null;
        // Block state
        // Calls a method
        final String blockName = input.substring(0, nbtIndex);
        // Calls a method
        Block block = Block.fromKey(blockName);
        // Branch: checks a condition
        if (block == null) return null;
        // Compute properties
        // Calls a method
        final String query = input.substring(nbtIndex);
        // Calls a method
        final Map<String, String> propertyMap = BlockUtils.parseProperties(query);
        // Exception handling
        try {
            // Returns a value to the caller
            return block.withProperties(propertyMap);
        // Start of a method/block
        } catch (IllegalArgumentException e) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block withProperty(String property, String value) {
        // Calls a method
        final PropertyType[] propertyTypes = PROPERTIES_TYPE.get(id());
        // Code statement
        assert propertyTypes != null;
        // Calls a method
        final byte keyIndex = findKeyIndexThrow(propertyTypes, property, this);
        // Calls a method
        final byte valueIndex = findValueIndexThrow(propertyTypes[keyIndex], value, this);
        // Calls a method
        final long updatedProperties = updateIndex(propertiesArray, keyIndex, valueIndex);
        // Returns a value to the caller
        return compute(updatedProperties);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block withProperties(Map<String, String> properties) {
        // Branch: checks a condition
        if (properties.isEmpty()) return this;
        // Calls a method
        final PropertyType[] propertyTypes = PROPERTIES_TYPE.get(id());
        // Code statement
        assert propertyTypes != null;
        // Assigns a value
        long updatedProperties = this.propertiesArray;
        // Loop: repeats a block
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // Calls a method
            final byte keyIndex = findKeyIndexThrow(propertyTypes, entry.getKey(), this);
            // Calls a method
            final byte valueIndex = findValueIndexThrow(propertyTypes[keyIndex], entry.getValue(), this);
            // Calls a method
            updatedProperties = updateIndex(updatedProperties, keyIndex, valueIndex);
        // End of a block/expression
        }
        // Returns a value to the caller
        return compute(updatedProperties);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> Block withTag(Tag<T> tag, @Nullable T value) {
        // Calls a method
        var builder = CompoundBinaryTag.builder();
        // Branch: checks a condition
        if (nbt != null) builder.put(nbt);
        // Calls a method
        tag.write(builder, value);
        // Calls a method
        final CompoundBinaryTag temporaryNbt = builder.build();
        // Calls a method
        final CompoundBinaryTag finalNbt = !temporaryNbt.isEmpty() ? temporaryNbt : null;
        // Returns a value to the caller
        return new BlockImpl(registry, propertiesArray, finalNbt, handler);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block withNbt(@Nullable CompoundBinaryTag compound) {
        // Returns a value to the caller
        return new BlockImpl(registry, propertiesArray, compound, handler);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block withHandler(@Nullable BlockHandler handler) {
        // Returns a value to the caller
        return new BlockImpl(registry, propertiesArray, nbt, handler);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Unmodifiable Map<String, String> properties() {
        // Calls a method
        final PropertyType[] propertyTypes = PROPERTIES_TYPE.get(id());
        // Code statement
        assert propertyTypes != null;
        // Assigns a value
        final int length = propertyTypes.length;
        // Branch: checks a condition
        if (length == 0) return Map.of();
        // Assigns a value
        String[] keys = new String[length];
        // Assigns a value
        String[] values = new String[length];
        // Loop: repeats a block
        for (int i = 0; i < length; i++) {
            // Assigns a value
            PropertyType property = propertyTypes[i];
            // Calls a method
            keys[i] = property.key();
            // Calls a method
            final long index = extractIndex(propertiesArray, i);
            // Calls a method
            values[i] = property.values().get((int) index);
        // End of a block/expression
        }
        // Returns a value to the caller
        return Object2ObjectMaps.unmodifiable(new Object2ObjectArrayMap<>(keys, values, length));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String state() {
        // Calls a method
        final Map<String, String> properties = properties();
        // Branch: checks a condition
        if (properties.isEmpty()) return name();
        // Calls a method
        StringBuilder builder = new StringBuilder(name()).append('[');
        // Assigns a value
        boolean first = true;
        // Loop: repeats a block
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // Branch: checks a condition
            if (first) first = false;
            // Alternative branch of the condition
            else builder.append(',');
            // Calls a method
            builder.append(entry.getKey()).append('=').append(entry.getValue());
        // End of a block/expression
        }
        // Calls a method
        builder.append(']');
        // Returns a value to the caller
        return builder.toString();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block defaultState() {
        // Returns a value to the caller
        return Block.fromBlockId(id());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable String getProperty(String property) {
        // Calls a method
        final PropertyType[] propertyTypes = PROPERTIES_TYPE.get(id());
        // Assigns a value
        final int length = propertyTypes.length;
        // Branch: checks a condition
        if (length == 0) return null;
        // Calls a method
        final int key = findKeyIndex(propertyTypes, property);
        // Branch: checks a condition
        if (key == -1) return null; // Property not found
        // Calls a method
        final long index = extractIndex(propertiesArray, key);
        // Returns a value to the caller
        return propertyTypes[key].values().get((int) index);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Block> possibleStates() {
        // Returns a value to the caller
        return Collection.class.cast(possibleProperties().values());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Returns a value to the caller
        return tag.read(Objects.requireNonNullElse(nbt, CompoundBinaryTag.empty()));
    // End of a block/expression
    }

    // Start of a method/block
    private Long2ObjectArrayMap<BlockImpl> possibleProperties() {
        // Returns a value to the caller
        return POSSIBLE_STATES.get(id());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("%s{properties=%s, nbt=%s, handler=%s}", name(), properties(), nbt, handler);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (this == o) return true;
        // Branch: checks a condition
        if (!(o instanceof BlockImpl block)) return false;
        // Returns a value to the caller
        return stateId() == block.stateId() && Objects.equals(nbt, block.nbt) && Objects.equals(handler, block.handler);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return Objects.hash(stateId(), nbt, handler);
    // End of a block/expression
    }

    // Start of a method/block
    private Block compute(long updatedProperties) {
        // Branch: checks a condition
        if (updatedProperties == this.propertiesArray) return this;
        // Calls a method
        final BlockImpl block = possibleProperties().get(updatedProperties);
        // Code statement
        assert block != null;
        // Reuse the same block instance if possible
        // Branch: checks a condition
        if (nbt == null && handler == null) return block;
        // Otherwise copy with the nbt and handler
        // Returns a value to the caller
        return new BlockImpl(block.registry(), block.propertiesArray, nbt, handler);
    // End of a block/expression
    }

    // Start of a method/block
    private static byte findKeyIndex(PropertyType[] properties, String key) {
        // Loop: repeats a block
        for (byte i = 0; i < properties.length; i++) {
            // Branch: checks a condition
            if (properties[i].key().equals(key)) return i;
        // End of a block/expression
        }
        // Returns a value to the caller
        return -1;
    // End of a block/expression
    }

    // Start of a method/block
    private static byte findValueIndex(PropertyType propertyType, String value) {
        // Calls a method
        final List<String> values = propertyType.values();
        // Returns a value to the caller
        return (byte) values.indexOf(value);
    // End of a block/expression
    }

    // Start of a method/block
    private static byte findKeyIndexThrow(PropertyType[] properties, String key, BlockImpl block) {
        // Calls a method
        final byte index = findKeyIndex(properties, key);
        // Branch: checks a condition
        if (index == -1) {
            // Branch: checks a condition
            if (block != null) {
                // Throws an exception
                throw new IllegalArgumentException("Property " + key + " is not valid for block " + block);
            // Alternative branch of the condition
            } else {
                // Throws an exception
                throw new IllegalArgumentException("Unknown property key: " + key);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return index;
    // End of a block/expression
    }

    // Start of a method/block
    private static byte findValueIndexThrow(PropertyType propertyType, String value, BlockImpl block) {
        // Calls a method
        final byte index = findValueIndex(propertyType, value);
        // Branch: checks a condition
        if (index == -1) {
            // Branch: checks a condition
            if (block != null) {
                // Throws an exception
                throw new IllegalArgumentException("Property " + propertyType.key() + " value " + value + " is not valid for block " + block);
            // Alternative branch of the condition
            } else {
                // Throws an exception
                throw new IllegalArgumentException("Unknown property value: " + value);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return index;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record PropertyType(String key, List<String> values) {
    // End of a block/expression
    }

    // Start of a method/block
    static long updateIndex(long value, int index, byte newValue) {
        // Assigns a value
        final int position = index * BITS_PER_INDEX;
        // Calls a method
        final int mask = (1 << BITS_PER_INDEX) - 1;
        // Code statement
        value &= ~((long) mask << position); // Clear the bits at the specified position
        // Code statement
        value |= (long) (newValue & mask) << position; // Set the new bits
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Start of a method/block
    static long extractIndex(long value, int index) {
        // Assigns a value
        final int position = index * BITS_PER_INDEX;
        // Calls a method
        final int mask = (1 << BITS_PER_INDEX) - 1;
        // Returns a value to the caller
        return ((value >> position) & mask);
    // End of a block/expression
    }
// End of a block/expression
}
