// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.predicate;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.utils.Unit;

// Import d'une classe nécessaire
import java.util.List;

// TODO: Pending pr #2732
// Déclaration de type (classe/interface/enum/record)
public class DataComponentPredicates {
    // Appelle une méthode
    public static final DataComponentPredicates EMPTY = new DataComponentPredicates();

    // Affecte une valeur
    public static final NetworkBuffer.Type<DataComponentPredicates> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.UNIT.list(), DataComponentPredicates::exact,
            // Instruction de code
            NetworkBuffer.UNIT.list(), DataComponentPredicates::partial,
            // Instruction de code
            DataComponentPredicates::new);
    // Appelle une méthode
    public static final Codec<DataComponentPredicates> CODEC = StructCodec.struct(new DataComponentPredicates());

    // Début d'une méthode/d'un bloc
    private DataComponentPredicates() {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private DataComponentPredicates(List<Unit> exact, List<Unit> partial) {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private List<Unit> exact() {
        // Renvoie une valeur à l'appelant
        return List.of();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private List<Unit> partial() {
        // Renvoie une valeur à l'appelant
        return List.of();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
