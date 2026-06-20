// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute.Modifier;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record EnvironmentAttributeMap(Map<EnvironmentAttribute<?>, Entry<?, ?>> entries) {
    // Appelle une méthode
    public static final EnvironmentAttributeMap EMPTY = new EnvironmentAttributeMap(Map.of());

    // Affecte une valeur
    public static final Codec<EnvironmentAttributeMap> CODEC = EnvironmentAttribute.CODEC
            // Instruction de code
            .mapValueTyped(Entry::codec0, true)
            // Appelle une méthode
            .transform(EnvironmentAttributeMap::new, EnvironmentAttributeMap::entries);

    // Début d'une méthode/d'un bloc
    public static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Builder builder(EnvironmentAttributeMap existing) {
        // Renvoie une valeur à l'appelant
        return new Builder(existing.entries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EnvironmentAttributeMap {
        // Appelle une méthode
        entries = Map.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Entry<T, Arg>(Arg argument, Modifier<T, Arg> modifier) {

        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Début d'une méthode/d'un bloc
        public static <T> Codec<Entry<T, ?>> codec(EnvironmentAttribute<T> attribute) {
            // A value is represented by either a single value which acts as an override,
            // or a struct with `modifier` and `argument` keys (full codec).

            // Affecte une valeur
            Codec<Entry<T, ?>> fullCodec = attribute.type().modifierCodec()
                    // Appelle une méthode
                    .unionType("modifier", Entry::fullCodec, Entry::modifier);

            // Appelle une méthode
            final var override = new Modifier.Override<>(attribute.valueCodec());
            // Renvoie une valeur à l'appelant
            return Codec.Either(attribute.valueCodec(), fullCodec).transform(
                    // Instruction de code
                    either -> either.unify(
                            // Instruction de code
                            value -> new Entry<>(value, override),
                            // Instruction de code
                            u -> u),
                    // Instruction de code
                    entry -> entry.modifier instanceof Modifier.Override
                            // Appelle une méthode
                            ? Either.left((T) entry.argument) : Either.right(entry));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static Codec<Entry<?, ?>> codec0(EnvironmentAttribute<?> attribute) {
            //noinspection unchecked,rawtypes
            // Renvoie une valeur à l'appelant
            return (Codec) codec(attribute);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static <T, Arg> StructCodec<Entry<T, Arg>> fullCodec(Modifier<T, Arg> modifier) {
            // Renvoie une valeur à l'appelant
            return StructCodec.struct(
                    // Instruction de code
                    "argument", modifier.argumentCodec(), Entry::argument,
                    // Instruction de code
                    (argument) -> new Entry<>(argument, modifier)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Builder {
        // Appelle une méthode
        private final Map<EnvironmentAttribute<?>, Entry<?, ?>> entries = new HashMap<>();

        // Début d'une méthode/d'un bloc
        public Builder() {

        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder(Map<EnvironmentAttribute<?>, Entry<?, ?>> existing) {
            // Appelle une méthode
            entries.putAll(existing);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public <T> Builder set(EnvironmentAttribute<T> attribute, T value) {
            // Appelle une méthode
            entries.put(attribute, new Entry<>(value, new Modifier.Override<>(attribute.valueCodec())));
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public <T, Arg> Builder modify(EnvironmentAttribute<T> attribute, Modifier<T, Arg> modifier, Arg argument) {
            // Appelle une méthode
            entries.put(attribute, new Entry<>(argument, modifier));
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public EnvironmentAttributeMap build() {
            // Renvoie une valeur à l'appelant
            return new EnvironmentAttributeMap(entries);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
