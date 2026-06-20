// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Déclaration de type (classe/interface/enum/record)
public record ProvidesTrimMaterial(Key key) {
    // This can be either a key or a holder of trim material. we need to support holders better.

    // Affecte une valeur
    public static final NetworkBuffer.Type<ProvidesTrimMaterial> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, ProvidesTrimMaterial value) {
            // Appelle une méthode
            buffer.write(NetworkBuffer.BOOLEAN, false);
            // Appelle une méthode
            buffer.write(NetworkBuffer.STRING, value.key.asString());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ProvidesTrimMaterial read(NetworkBuffer buffer) {
            // Embranchement : vérifie une condition
            if (buffer.read(NetworkBuffer.BOOLEAN))
                // Lève une exception
                throw new IllegalArgumentException("Cannot read direct trim material");
            // Renvoie une valeur à l'appelant
            return new ProvidesTrimMaterial(Key.key(buffer.read(NetworkBuffer.STRING)));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Appelle une méthode
    public static final Codec<ProvidesTrimMaterial> CODEC = Codec.KEY.transform(ProvidesTrimMaterial::new, ProvidesTrimMaterial::key);
// Fin d'un bloc/d'une expression
}
