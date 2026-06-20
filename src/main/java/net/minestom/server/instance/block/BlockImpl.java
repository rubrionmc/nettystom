// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ObjectArray;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.*;

// Déclaration de type (classe/interface/enum/record)
record BlockImpl(RegistryData.BlockEntry registry,
                 // Instruction de code
                 long propertiesArray,
                 // Annotation pour l'élément suivant
                 @Nullable CompoundBinaryTag nbt,
                 // Annotation pour l'élément suivant
                 @Nullable BlockHandler handler) implements Block {
    /**
     * Number of bits used to store the index of a property value.
     * <p>
     * Block states are all stored within a single number.
     */
    // Affecte une valeur
    private static final int BITS_PER_INDEX = 5;

    // Affecte une valeur
    private static final int MAX_STATES = Long.SIZE / BITS_PER_INDEX;
    // Affecte une valeur
    private static final int MAX_VALUES = 1 << BITS_PER_INDEX;

    // Block state -> block object
    // Instruction de code
    private static final List<Block> BLOCK_STATE_MAP;
    // Block id -> valid property keys (order is important for lookup)
    // Instruction de code
    private static final List<PropertyType[]> PROPERTIES_TYPE;
    // Block id -> Map<Properties, Block>
    // Instruction de code
    private static final List<Long2ObjectArrayMap<BlockImpl>> POSSIBLE_STATES;
    // Instruction de code
    static final Registry<Block> REGISTRY;

    // Début d'une méthode/d'un bloc
    static {
        //TODO compute default sizes from the registry data
        // Appelle une méthode
        ObjectArray<Block> blockStateMap = ObjectArray.singleThread();
        // Appelle une méthode
        ObjectArray<PropertyType[]> propertiesType = ObjectArray.singleThread();
        // Appelle une méthode
        ObjectArray<Long2ObjectArrayMap<BlockImpl>> possibleStates = ObjectArray.singleThread();
        // Affecte une valeur
        HashMap<Object, Object> internCache = new HashMap<>();

        // Affecte une valeur
        REGISTRY = RegistryData.createStaticRegistry(
                // Instruction de code
                Key.key("block"),
                // Début d'une méthode/d'un bloc
                (namespace, properties) -> {
                    // Appelle une méthode
                    final int blockId = properties.getInt("id");
                    // Appelle une méthode
                    final RegistryData.Properties stateObject = properties.section("states");

                    // Retrieve properties
                    // Instruction de code
                    PropertyType[] propertyTypes;
                    // Début d'un bloc
                    {
                        // Appelle une méthode
                        RegistryData.Properties stateProperties = properties.section("properties");
                        // Embranchement : vérifie une condition
                        if (stateProperties != null) {
                            // Appelle une méthode
                            final int stateCount = stateProperties.size();
                            // Embranchement : vérifie une condition
                            if (stateCount > MAX_STATES) {
                                // Lève une exception
                                throw new IllegalStateException("Too many properties for block " + namespace);
                            // Fin d'un bloc/d'une expression
                            }
                            // Affecte une valeur
                            propertyTypes = new PropertyType[stateCount];
                            // Affecte une valeur
                            int i = 0;
                            // Boucle : répète un bloc
                            for (var entry : stateProperties) {
                                // Appelle une méthode
                                final var k = entry.getKey();
                                // Appelle une méthode
                                final var v = (List<String>) entry.getValue();
                                // Appelle une méthode
                                assert v.size() < MAX_VALUES;
                                // Appelle une méthode
                                propertyTypes[i++] = new PropertyType(k, v);
                            // Fin d'un bloc/d'une expression
                            }
                        // Branche alternative de la condition
                        } else {
                            // Affecte une valeur
                            propertyTypes = new PropertyType[0];
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    propertiesType.set(blockId, propertyTypes);

                    // Appelle une méthode
                    final RegistryData.BlockEntry baseBlockEntry = RegistryData.block(namespace, properties, internCache, null, null);

                    // Retrieve block states
                    // Début d'un bloc
                    {
                        // Appelle une méthode
                        final int propertiesCount = stateObject.size();
                        // Affecte une valeur
                        long[] propertiesKeys = new long[propertiesCount];
                        // Affecte une valeur
                        BlockImpl[] blocksValues = new BlockImpl[propertiesCount];
                        // Affecte une valeur
                        int propertiesOffset = 0;
                        // Boucle : répète un bloc
                        for (var stateEntry : stateObject) {
                            // Appelle une méthode
                            final String query = stateEntry.getKey();
                            // Appelle une méthode
                            final var stateOverride = (Map<String, Object>) stateEntry.getValue();
                            // Appelle une méthode
                            final var propertyMap = BlockUtils.parseProperties(query);
                            // Appelle une méthode
                            assert propertyTypes.length == propertyMap.size();
                            // Affecte une valeur
                            long propertiesValue = 0;
                            // Boucle : répète un bloc
                            for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                                // Appelle une méthode
                                final byte keyIndex = findKeyIndexThrow(propertyTypes, entry.getKey(), null);
                                // Appelle une méthode
                                final byte valueIndex = findValueIndexThrow(propertyTypes[keyIndex], entry.getValue(), null);
                                // Appelle une méthode
                                propertiesValue = updateIndex(propertiesValue, keyIndex, valueIndex);
                            // Fin d'un bloc/d'une expression
                            }

                            // Appelle une méthode
                            final RegistryData.BlockEntry entryOverride = RegistryData.block(namespace, RegistryData.Properties.fromMap(stateOverride), internCache, baseBlockEntry, properties);
                            // Affecte une valeur
                            final BlockImpl block = new BlockImpl(entryOverride,
                                    // Instruction de code
                                    propertiesValue, null, null);
                            // Appelle une méthode
                            blockStateMap.set(block.stateId(), block);
                            // Affecte une valeur
                            propertiesKeys[propertiesOffset] = propertiesValue;
                            // Affecte une valeur
                            blocksValues[propertiesOffset++] = block;
                        // Fin d'un bloc/d'une expression
                        }
                        // Appelle une méthode
                        possibleStates.set(blockId, new Long2ObjectArrayMap<>(propertiesKeys, blocksValues, propertiesOffset));
                    // Fin d'un bloc/d'une expression
                    }
                    // Register default state
                    // Appelle une méthode
                    final int defaultState = properties.getInt("defaultStateId");
                    // Renvoie une valeur à l'appelant
                    return blockStateMap.get(defaultState);
                // Fin d'un bloc/d'une expression
                });
        // Appelle une méthode
        BLOCK_STATE_MAP = blockStateMap.toList();
        // Appelle une méthode
        PROPERTIES_TYPE = propertiesType.toList();
        // Appelle une méthode
        POSSIBLE_STATES = possibleStates.toList();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @UnknownNullability Block get(String key) {
        // Renvoie une valeur à l'appelant
        return REGISTRY.get(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static int statesCount() {
        // Renvoie une valeur à l'appelant
        return BLOCK_STATE_MAP.size();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Block getState(int stateId) {
        // Renvoie une valeur à l'appelant
        return BLOCK_STATE_MAP.get(stateId);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Block parseState(String input) {
        // Embranchement : vérifie une condition
        if (input.isEmpty()) return null;
        // Appelle une méthode
        final int nbtIndex = input.indexOf("[");
        // Embranchement : vérifie une condition
        if (nbtIndex == 0) return null;
        // Embranchement : vérifie une condition
        if (nbtIndex == -1) return Block.fromKey(input);
        // Embranchement : vérifie une condition
        if (!input.endsWith("]")) return null;
        // Block state
        // Appelle une méthode
        final String blockName = input.substring(0, nbtIndex);
        // Appelle une méthode
        Block block = Block.fromKey(blockName);
        // Embranchement : vérifie une condition
        if (block == null) return null;
        // Compute properties
        // Appelle une méthode
        final String query = input.substring(nbtIndex);
        // Appelle une méthode
        final Map<String, String> propertyMap = BlockUtils.parseProperties(query);
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return block.withProperties(propertyMap);
        // Début d'une méthode/d'un bloc
        } catch (IllegalArgumentException e) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Block withProperty(String property, String value) {
        // Appelle une méthode
        final PropertyType[] propertyTypes = PROPERTIES_TYPE.get(id());
        // Instruction de code
        assert propertyTypes != null;
        // Appelle une méthode
        final byte keyIndex = findKeyIndexThrow(propertyTypes, property, this);
        // Appelle une méthode
        final byte valueIndex = findValueIndexThrow(propertyTypes[keyIndex], value, this);
        // Appelle une méthode
        final long updatedProperties = updateIndex(propertiesArray, keyIndex, valueIndex);
        // Renvoie une valeur à l'appelant
        return compute(updatedProperties);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Block withProperties(Map<String, String> properties) {
        // Embranchement : vérifie une condition
        if (properties.isEmpty()) return this;
        // Appelle une méthode
        final PropertyType[] propertyTypes = PROPERTIES_TYPE.get(id());
        // Instruction de code
        assert propertyTypes != null;
        // Affecte une valeur
        long updatedProperties = this.propertiesArray;
        // Boucle : répète un bloc
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // Appelle une méthode
            final byte keyIndex = findKeyIndexThrow(propertyTypes, entry.getKey(), this);
            // Appelle une méthode
            final byte valueIndex = findValueIndexThrow(propertyTypes[keyIndex], entry.getValue(), this);
            // Appelle une méthode
            updatedProperties = updateIndex(updatedProperties, keyIndex, valueIndex);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return compute(updatedProperties);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> Block withTag(Tag<T> tag, @Nullable T value) {
        // Appelle une méthode
        var builder = CompoundBinaryTag.builder();
        // Embranchement : vérifie une condition
        if (nbt != null) builder.put(nbt);
        // Appelle une méthode
        tag.write(builder, value);
        // Appelle une méthode
        final CompoundBinaryTag temporaryNbt = builder.build();
        // Appelle une méthode
        final CompoundBinaryTag finalNbt = temporaryNbt.size() > 0 ? temporaryNbt : null;
        // Renvoie une valeur à l'appelant
        return new BlockImpl(registry, propertiesArray, finalNbt, handler);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Block withNbt(@Nullable CompoundBinaryTag compound) {
        // Renvoie une valeur à l'appelant
        return new BlockImpl(registry, propertiesArray, compound, handler);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Block withHandler(@Nullable BlockHandler handler) {
        // Renvoie une valeur à l'appelant
        return new BlockImpl(registry, propertiesArray, nbt, handler);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Unmodifiable Map<String, String> properties() {
        // Appelle une méthode
        final PropertyType[] propertyTypes = PROPERTIES_TYPE.get(id());
        // Instruction de code
        assert propertyTypes != null;
        // Affecte une valeur
        final int length = propertyTypes.length;
        // Embranchement : vérifie une condition
        if (length == 0) return Map.of();
        // Affecte une valeur
        String[] keys = new String[length];
        // Affecte une valeur
        String[] values = new String[length];
        // Boucle : répète un bloc
        for (int i = 0; i < length; i++) {
            // Affecte une valeur
            PropertyType property = propertyTypes[i];
            // Appelle une méthode
            keys[i] = property.key();
            // Appelle une méthode
            final long index = extractIndex(propertiesArray, i);
            // Appelle une méthode
            values[i] = property.values().get((int) index);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return Object2ObjectMaps.unmodifiable(new Object2ObjectArrayMap<>(keys, values, length));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String state() {
        // Appelle une méthode
        final Map<String, String> properties = properties();
        // Embranchement : vérifie une condition
        if (properties.isEmpty()) return name();
        // Appelle une méthode
        StringBuilder builder = new StringBuilder(name()).append('[');
        // Affecte une valeur
        boolean first = true;
        // Boucle : répète un bloc
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // Embranchement : vérifie une condition
            if (first) first = false;
            // Branche alternative de la condition
            else builder.append(',');
            // Appelle une méthode
            builder.append(entry.getKey()).append('=').append(entry.getValue());
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        builder.append(']');
        // Renvoie une valeur à l'appelant
        return builder.toString();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Block defaultState() {
        // Renvoie une valeur à l'appelant
        return Block.fromBlockId(id());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable String getProperty(String property) {
        // Appelle une méthode
        final PropertyType[] propertyTypes = PROPERTIES_TYPE.get(id());
        // Affecte une valeur
        final int length = propertyTypes.length;
        // Embranchement : vérifie une condition
        if (length == 0) return null;
        // Appelle une méthode
        final int key = findKeyIndex(propertyTypes, property);
        // Embranchement : vérifie une condition
        if (key == -1) return null; // Property not found
        // Appelle une méthode
        final long index = extractIndex(propertiesArray, key);
        // Renvoie une valeur à l'appelant
        return propertyTypes[key].values().get((int) index);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Block> possibleStates() {
        // Renvoie une valeur à l'appelant
        return Collection.class.cast(possibleProperties().values());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Renvoie une valeur à l'appelant
        return tag.read(Objects.requireNonNullElse(nbt, CompoundBinaryTag.empty()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private Long2ObjectArrayMap<BlockImpl> possibleProperties() {
        // Renvoie une valeur à l'appelant
        return POSSIBLE_STATES.get(id());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("%s{properties=%s, nbt=%s, handler=%s}", name(), properties(), nbt, handler);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (this == o) return true;
        // Embranchement : vérifie une condition
        if (!(o instanceof BlockImpl block)) return false;
        // Renvoie une valeur à l'appelant
        return stateId() == block.stateId() && Objects.equals(nbt, block.nbt) && Objects.equals(handler, block.handler);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return Objects.hash(stateId(), nbt, handler);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private Block compute(long updatedProperties) {
        // Embranchement : vérifie une condition
        if (updatedProperties == this.propertiesArray) return this;
        // Appelle une méthode
        final BlockImpl block = possibleProperties().get(updatedProperties);
        // Instruction de code
        assert block != null;
        // Reuse the same block instance if possible
        // Embranchement : vérifie une condition
        if (nbt == null && handler == null) return block;
        // Otherwise copy with the nbt and handler
        // Renvoie une valeur à l'appelant
        return new BlockImpl(block.registry(), block.propertiesArray, nbt, handler);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte findKeyIndex(PropertyType[] properties, String key) {
        // Boucle : répète un bloc
        for (byte i = 0; i < properties.length; i++) {
            // Embranchement : vérifie une condition
            if (properties[i].key().equals(key)) return i;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return -1;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte findValueIndex(PropertyType propertyType, String value) {
        // Appelle une méthode
        final List<String> values = propertyType.values();
        // Renvoie une valeur à l'appelant
        return (byte) values.indexOf(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte findKeyIndexThrow(PropertyType[] properties, String key, BlockImpl block) {
        // Appelle une méthode
        final byte index = findKeyIndex(properties, key);
        // Embranchement : vérifie une condition
        if (index == -1) {
            // Embranchement : vérifie une condition
            if (block != null) {
                // Lève une exception
                throw new IllegalArgumentException("Property " + key + " is not valid for block " + block);
            // Branche alternative de la condition
            } else {
                // Lève une exception
                throw new IllegalArgumentException("Unknown property key: " + key);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return index;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte findValueIndexThrow(PropertyType propertyType, String value, BlockImpl block) {
        // Appelle une méthode
        final byte index = findValueIndex(propertyType, value);
        // Embranchement : vérifie une condition
        if (index == -1) {
            // Embranchement : vérifie une condition
            if (block != null) {
                // Lève une exception
                throw new IllegalArgumentException("Property " + propertyType.key() + " value " + value + " is not valid for block " + block);
            // Branche alternative de la condition
            } else {
                // Lève une exception
                throw new IllegalArgumentException("Unknown property value: " + value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return index;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record PropertyType(String key, List<String> values) {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static long updateIndex(long value, int index, byte newValue) {
        // Affecte une valeur
        final int position = index * BITS_PER_INDEX;
        // Affecte une valeur
        final int mask = (1 << BITS_PER_INDEX) - 1;
        // Affecte une valeur
        value &= ~((long) mask << position); // Clear the bits at the specified position
        // Affecte une valeur
        value |= (long) (newValue & mask) << position; // Set the new bits
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static long extractIndex(long value, int index) {
        // Affecte une valeur
        final int position = index * BITS_PER_INDEX;
        // Affecte une valeur
        final int mask = (1 << BITS_PER_INDEX) - 1;
        // Renvoie une valeur à l'appelant
        return ((value >> position) & mask);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
