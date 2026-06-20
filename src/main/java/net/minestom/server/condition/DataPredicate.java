// Déclaration du paquet de ce fichier
package net.minestom.server.condition;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;

// Déclaration de type (classe/interface/enum/record)
public interface DataPredicate {
    // Déclaration de type (classe/interface/enum/record)
    record Noop(BinaryTag content) implements DataPredicate {

    // Fin d'un bloc/d'une expression
    }

    // TODO
    // Appelle une méthode
    Codec<DataPredicate> NBT_TYPE = Codec.NBT.transform(Noop::new, value -> ((Noop) value).content);
// Fin d'un bloc/d'une expression
}
