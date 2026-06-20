// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.lang.reflect.Constructor;
// Import d'une classe nécessaire
import java.lang.reflect.InvocationTargetException;
// Import d'une classe nécessaire
import java.lang.reflect.RecordComponent;
// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static java.util.Map.entry;

// Déclaration de type (classe/interface/enum/record)
final class TagRecord {
    // Affecte une valeur
    static final Map<Class<?>, Function<String, Tag<?>>> SUPPORTED_TYPES = Map.ofEntries(
            // Instruction de code
            entry(Byte.class, Tag::Byte), entry(byte.class, Tag::Byte),
            // Instruction de code
            entry(Boolean.class, Tag::Boolean), entry(boolean.class, Tag::Boolean),
            // Instruction de code
            entry(Short.class, Tag::Short), entry(short.class, Tag::Short),
            // Instruction de code
            entry(Integer.class, Tag::Integer), entry(int.class, Tag::Integer),
            // Instruction de code
            entry(Long.class, Tag::Long), entry(long.class, Tag::Long),
            // Instruction de code
            entry(Float.class, Tag::Float), entry(float.class, Tag::Float),
            // Instruction de code
            entry(Double.class, Tag::Double), entry(double.class, Tag::Double),
            // Instruction de code
            entry(String.class, Tag::String),

            // Instruction de code
            entry(UUID.class, Tag::UUID),
            // Instruction de code
            entry(ItemStack.class, Tag::ItemStack),
            // Appelle une méthode
            entry(Component.class, Tag::Component));

    // Affecte une valeur
    static final ClassValue<Serializer<? extends Record>> serializers = new ClassValue<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        protected Serializer<? extends Record> computeValue(Class<?> type) {
            // Appelle une méthode
            assert type.isRecord();
            // Appelle une méthode
            final RecordComponent[] components = type.getRecordComponents();
            // Affecte une valeur
            final Entry[] entries = Arrays.stream(components)
                    // Début d'une méthode/d'un bloc
                    .map(recordComponent -> {
                        // Appelle une méthode
                        final String componentName = recordComponent.getName();
                        // Appelle une méthode
                        final Class<?> componentType = recordComponent.getType();
                        // Instruction de code
                        final Tag<?> tag;
                        // Embranchement : vérifie une condition
                        if (componentType.isRecord()) {
                            // Appelle une méthode
                            tag = Tag.Structure(componentName, serializers.get(componentType));
                        // Embranchement : vérifie une condition
                        } else if (BinaryTag.class.isAssignableFrom(componentType)) {
                            // Appelle une méthode
                            tag = Tag.NBT(componentName);
                        // Branche alternative de la condition
                        } else {
                            // Appelle une méthode
                            final var fun = SUPPORTED_TYPES.get(componentType);
                            // Embranchement : vérifie une condition
                            if (fun == null)
                                // Lève une exception
                                throw new IllegalArgumentException("Unsupported type: " + componentType);
                            // Appelle une méthode
                            tag = fun.apply(componentName);
                        // Fin d'un bloc/d'une expression
                        }
                        // Renvoie une valeur à l'appelant
                        return new Entry(recordComponent, (Tag<Object>) tag);
                    // Appelle une méthode
                    }).toArray(Entry[]::new);
            // Instruction de code
            Constructor<?> constructor;
            // Gestion des exceptions
            try {
                // Appelle une méthode
                constructor = type.getDeclaredConstructor(Arrays.stream(components).map(RecordComponent::getType).toArray(Class[]::new));
            // Début d'une méthode/d'un bloc
            } catch (NoSuchMethodException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Serializer<>(Constructor.class.cast(constructor), entries);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    static <T extends Record> Serializer<T> serializer(Class<T> type) {
        // Appelle une méthode
        assert type.isRecord();
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return (Serializer<T>) serializers.get(type);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class Serializer<T extends Record> implements TagSerializer<T> {
        // Instruction de code
        final Constructor<T> constructor;
        // Instruction de code
        final Entry[] entries;
        // Instruction de code
        final Serializers.Entry<T, CompoundBinaryTag> serializerEntry;

        // Début d'une méthode/d'un bloc
        Serializer(Constructor<T> constructor, Entry[] entries) {
            // Accès à l'objet courant/parent
            this.constructor = constructor;
            // Accès à l'objet courant/parent
            this.entries = entries;
            // Accès à l'objet courant/parent
            this.serializerEntry = Serializers.fromTagSerializer(this);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable T read(TagReadable reader) {
            // Affecte une valeur
            Object[] components = new Object[entries.length];
            // Boucle : répète un bloc
            for (int i = 0; i < components.length; i++) {
                // Affecte une valeur
                final Entry entry = entries[i];
                // Appelle une méthode
                Object component = reader.getTag(entry.tag);
                // Embranchement : vérifie une condition
                if (component == null) return null;
                // Affecte une valeur
                components[i] = component;
            // Fin d'un bloc/d'une expression
            }
            // Gestion des exceptions
            try {
                // Renvoie une valeur à l'appelant
                return constructor.newInstance(components);
            // Début d'une méthode/d'un bloc
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(TagWritable writer, T value) {
            // Gestion des exceptions
            try {
                // Boucle : répète un bloc
                for (Entry entry : entries) {
                    // Appelle une méthode
                    final Object component = entry.component.getAccessor().invoke(value);
                    // Appelle une méthode
                    writer.setTag(entry.tag, component);
                // Fin d'un bloc/d'une expression
                }
            // Début d'une méthode/d'un bloc
            } catch (IllegalAccessException | InvocationTargetException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Entry(RecordComponent component, Tag<Object> tag) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
