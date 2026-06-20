// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute.Modifier;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record EnvironmentAttributeTypeImpl<T>(
        // Instruction de code
        Key key,
        // Instruction de code
        Codec<T> codec,
        // Instruction de code
        Codec<Modifier<T, ?>> modifierCodec
// Début d'une méthode/d'un bloc
) implements EnvironmentAttribute.Type<T> {

    // Instruction de code
    static <T> EnvironmentAttribute.Type<T> register(
            // Annotation pour l'élément suivant
            @KeyPattern String key,
            // Instruction de code
            Codec<T> codec,
            // Instruction de code
            Map<Modifier.Operator, Modifier<T, ?>> operators
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        final var withOverride = new HashMap<>(operators);
        // Appelle une méthode
        withOverride.put(Modifier.Operator.OVERRIDE, new Modifier.Override<>(codec));

        // Appelle une méthode
        final var inverse = new HashMap<Modifier<T, ?>, Modifier.Operator>(operators.size());
        // Boucle : répète un bloc
        for (var entry : operators.entrySet()) inverse.put(entry.getValue(), entry.getKey());

        // Affecte une valeur
        final Codec<Modifier<T, ?>> modifierCodec = Modifier.Operator.CODEC.transform(op ->
                // Appelle une méthode
                Objects.requireNonNull(withOverride.get(op), () -> "unsupported operator: " + op), inverse::get);
        // Renvoie une valeur à l'appelant
        return new EnvironmentAttributeTypeImpl<>(Key.key(key), codec, modifierCodec);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
