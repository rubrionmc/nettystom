// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.lang.reflect.Constructor;
// Import of a required class
import java.lang.reflect.InvocationTargetException;
// Import of a required class
import java.lang.reflect.RecordComponent;
// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.function.Function;

// Static import of a member
import static java.util.Map.entry;

// Type declaration (class/interface/enum/record)
final class TagRecord {
    // Assigns a value
    static final Map<Class<?>, Function<String, Tag<?>>> SUPPORTED_TYPES = Map.ofEntries(
            // Code statement
            entry(Byte.class, Tag::Byte), entry(byte.class, Tag::Byte),
            // Code statement
            entry(Boolean.class, Tag::Boolean), entry(boolean.class, Tag::Boolean),
            // Code statement
            entry(Short.class, Tag::Short), entry(short.class, Tag::Short),
            // Code statement
            entry(Integer.class, Tag::Integer), entry(int.class, Tag::Integer),
            // Code statement
            entry(Long.class, Tag::Long), entry(long.class, Tag::Long),
            // Code statement
            entry(Float.class, Tag::Float), entry(float.class, Tag::Float),
            // Code statement
            entry(Double.class, Tag::Double), entry(double.class, Tag::Double),
            // Code statement
            entry(String.class, Tag::String),

            // Code statement
            entry(UUID.class, Tag::UUID),
            // Code statement
            entry(ItemStack.class, Tag::ItemStack),
            // Calls a method
            entry(Component.class, Tag::Component));

    // Assigns a value
    static final ClassValue<Serializer<? extends Record>> serializers = new ClassValue<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        protected Serializer<? extends Record> computeValue(Class<?> type) {
            // Calls a method
            assert type.isRecord();
            // Calls a method
            final RecordComponent[] components = type.getRecordComponents();
            // Assigns a value
            final Entry[] entries = Arrays.stream(components)
                    // Start of a method/block
                    .map(recordComponent -> {
                        // Calls a method
                        final String componentName = recordComponent.getName();
                        // Calls a method
                        final Class<?> componentType = recordComponent.getType();
                        // Code statement
                        final Tag<?> tag;
                        // Branch: checks a condition
                        if (componentType.isRecord()) {
                            // Calls a method
                            tag = Tag.Structure(componentName, serializers.get(componentType));
                        // Branch: checks a condition
                        } else if (BinaryTag.class.isAssignableFrom(componentType)) {
                            // Calls a method
                            tag = Tag.NBT(componentName);
                        // Alternative branch of the condition
                        } else {
                            // Calls a method
                            final var fun = SUPPORTED_TYPES.get(componentType);
                            // Branch: checks a condition
                            if (fun == null)
                                // Throws an exception
                                throw new IllegalArgumentException("Unsupported type: " + componentType);
                            // Calls a method
                            tag = fun.apply(componentName);
                        // End of a block/expression
                        }
                        // Returns a value to the caller
                        return new Entry(recordComponent, (Tag<Object>) tag);
                    // Calls a method
                    }).toArray(Entry[]::new);
            // Code statement
            Constructor<?> constructor;
            // Exception handling
            try {
                // Calls a method
                constructor = type.getDeclaredConstructor(Arrays.stream(components).map(RecordComponent::getType).toArray(Class[]::new));
            // Start of a method/block
            } catch (NoSuchMethodException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Serializer<>(Constructor.class.cast(constructor), entries);
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Start of a method/block
    static <T extends Record> Serializer<T> serializer(Class<T> type) {
        // Calls a method
        assert type.isRecord();
        //noinspection unchecked
        // Returns a value to the caller
        return (Serializer<T>) serializers.get(type);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class Serializer<T extends Record> implements TagSerializer<T> {
        // Code statement
        final Constructor<T> constructor;
        // Code statement
        final Entry[] entries;
        // Code statement
        final Serializers.Entry<T, CompoundBinaryTag> serializerEntry;

        // Start of a method/block
        Serializer(Constructor<T> constructor, Entry[] entries) {
            // Access to the current/parent object
            this.constructor = constructor;
            // Access to the current/parent object
            this.entries = entries;
            // Access to the current/parent object
            this.serializerEntry = Serializers.fromTagSerializer(this);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable T read(TagReadable reader) {
            // Assigns a value
            Object[] components = new Object[entries.length];
            // Loop: repeats a block
            for (int i = 0; i < components.length; i++) {
                // Assigns a value
                final Entry entry = entries[i];
                // Calls a method
                Object component = reader.getTag(entry.tag);
                // Branch: checks a condition
                if (component == null) return null;
                // Assigns a value
                components[i] = component;
            // End of a block/expression
            }
            // Exception handling
            try {
                // Returns a value to the caller
                return constructor.newInstance(components);
            // Start of a method/block
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(TagWritable writer, T value) {
            // Exception handling
            try {
                // Loop: repeats a block
                for (Entry entry : entries) {
                    // Calls a method
                    final Object component = entry.component.getAccessor().invoke(value);
                    // Calls a method
                    writer.setTag(entry.tag, component);
                // End of a block/expression
                }
            // Start of a method/block
            } catch (IllegalAccessException | InvocationTargetException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Entry(RecordComponent component, Tag<Object> tag) {
    // End of a block/expression
    }
// End of a block/expression
}
