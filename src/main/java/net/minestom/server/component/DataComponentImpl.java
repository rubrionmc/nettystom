// Déclaration du paquet de ce fichier
package net.minestom.server.component;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ObjectArray;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Déclaration de type (classe/interface/enum/record)
record DataComponentImpl<T>(
        // Instruction de code
        int id,
        // Instruction de code
        Key key,
        // Annotation pour l'élément suivant
        @Nullable NetworkBuffer.Type<T> networkType,
        // Annotation pour l'élément suivant
        @Nullable Codec<T> codec,
        // Annotation pour l'élément suivant
        @Nullable UnaryOperator<T> freeze
// Début d'une méthode/d'un bloc
) implements DataComponent<T> {
    // Appelle une méthode
    static final Map<Key, DataComponent<?>> NAMESPACES = new HashMap<>(32);
    // Appelle une méthode
    static final ObjectArray<DataComponent<?>> IDS = ObjectArray.singleThread(32);

    // Début d'une méthode/d'un bloc
    static <T> DataComponent<T> register(@KeyPattern String name, @Nullable NetworkBuffer.Type<T> network, @Nullable Codec<T> nbt) {
        // Renvoie une valeur à l'appelant
        return register(name, network, nbt, null);
    // Fin d'un bloc/d'une expression
    }

    // Used when Collections are involved, where T could still be mutable.
    // Début d'une méthode/d'un bloc
    static <T> DataComponent<T> register(@KeyPattern String name, @Nullable NetworkBuffer.Type<T> network, @Nullable Codec<T> nbt, @Nullable UnaryOperator<T> freeze) {
        // Appelle une méthode
        DataComponent<T> impl = DataComponent.createHeadless(NAMESPACES.size(), Key.key(name), network, nbt, freeze);
        // Appelle une méthode
        NAMESPACES.put(impl.key(), impl);
        // Appelle une méthode
        IDS.set(impl.id(), impl);
        // Renvoie une valeur à l'appelant
        return impl;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isSynced() {
        // Renvoie une valeur à l'appelant
        return networkType != null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isSerialized() {
        // Renvoie une valeur à l'appelant
        return codec != null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <D> Result<T> decode(Transcoder<D> coder, D value) {
        // Appelle une méthode
        Check.notNull(codec, "{0} cannot be deserialized from Codec", this);
        // Renvoie une valeur à l'appelant
        return this.codec.decode(coder, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
        // Appelle une méthode
        Check.notNull(codec, "{0} cannot be deserialized from Codec", this);
        // Renvoie une valeur à l'appelant
        return this.codec.encode(coder, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public T read(NetworkBuffer reader) {
        // Appelle une méthode
        Check.notNull(networkType, "{0} cannot be deserialized from network", this);
        // Renvoie une valeur à l'appelant
        return networkType.read(reader);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void write(NetworkBuffer writer, T value) {
        // Appelle une méthode
        Check.notNull(networkType, "{0} cannot be serialized to network", this);
        // Appelle une méthode
        networkType.write(writer, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public T freeze(T value) {
        // Embranchement : vérifie une condition
        if (freeze == null) return value;
        // Renvoie une valeur à l'appelant
        return freeze.apply(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return name();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
