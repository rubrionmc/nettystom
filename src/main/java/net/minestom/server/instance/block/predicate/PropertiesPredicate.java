// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.predicate;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record PropertiesPredicate(Map<String, ValuePredicate> properties) implements Predicate<Block> {

    // Affecte une valeur
    public static final NetworkBuffer.Type<PropertiesPredicate> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.STRING.mapValue(ValuePredicate.NETWORK_TYPE), PropertiesPredicate::properties,
            // Instruction de code
            PropertiesPredicate::new
    // Fin d'un bloc/d'une expression
    );
    // Affecte une valeur
    public static final Codec<PropertiesPredicate> CODEC = Codec.STRING.mapValue(ValuePredicate.CODEC)
            // Appelle une méthode
            .transform(PropertiesPredicate::new, PropertiesPredicate::properties);

    // Début d'une méthode/d'un bloc
    public static PropertiesPredicate exact(String key, String value) {
        // Renvoie une valeur à l'appelant
        return new PropertiesPredicate(Map.of(key, new ValuePredicate.Exact(value)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PropertiesPredicate {
        // Appelle une méthode
        properties = Map.copyOf(properties);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean test(Block block) {
        // Boucle : répète un bloc
        for (Map.Entry<String, ValuePredicate> entry : properties.entrySet()) {
            // Appelle une méthode
            final String value = block.getProperty(entry.getKey());
            // Embranchement : vérifie une condition
            if (!entry.getValue().test(value))
                // Renvoie une valeur à l'appelant
                return false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public sealed interface ValuePredicate extends Predicate<@Nullable String> permits ValuePredicate.Exact, ValuePredicate.Range {
        // Affecte une valeur
        NetworkBuffer.Type<ValuePredicate> NETWORK_TYPE = NetworkBuffer.Either(Exact.NETWORK_TYPE, Range.NETWORK_TYPE)
                // Début d'une méthode/d'un bloc
                .transform(Either::identity, it -> switch (it) {
                            // Embranchement multiple (switch/case)
                            case Exact exact -> Either.left(exact);
                            // Embranchement multiple (switch/case)
                            case Range range -> Either.right(range);
                // Fin d'un bloc/d'une expression
                });
        // Affecte une valeur
        Codec<ValuePredicate> CODEC = new Codec<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<ValuePredicate> decode(Transcoder<D> coder, D value) {
                // Appelle une méthode
                final Result<Exact> exactResult = Exact.CODEC.decode(coder, value);
                // Embranchement : vérifie une condition
                if (exactResult instanceof Result.Ok(Exact exact))
                    // Renvoie une valeur à l'appelant
                    return new Result.Ok<>(exact);
                // Appelle une méthode
                final Result<Range> rangeResult = Range.CODEC.decode(coder, value);
                // Embranchement : vérifie une condition
                if (rangeResult instanceof Result.Ok(Range range))
                    // Renvoie une valeur à l'appelant
                    return new Result.Ok<>(range);
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Invalid value predicate");
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encode(Transcoder<D> coder, @Nullable ValuePredicate value) {
                // Embranchement : vérifie une condition
                if (value == null) return new Result.Error<>("null");
                // Renvoie une valeur à l'appelant
                return switch (value) {
                    // Embranchement multiple (switch/case)
                    case Exact exact -> Exact.CODEC.encode(coder, exact);
                    // Embranchement multiple (switch/case)
                    case Range range -> Range.CODEC.encode(coder, range);
                // Fin d'un bloc/d'une expression
                };
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Déclaration de type (classe/interface/enum/record)
        record Exact(@Nullable String value) implements ValuePredicate {

            // Appelle une méthode
            public static final NetworkBuffer.Type<Exact> NETWORK_TYPE = NetworkBuffer.STRING.transform(Exact::new, Exact::value);
            // Appelle une méthode
            public static final Codec<Exact> CODEC = Codec.STRING.transform(Exact::new, Exact::value);

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean test(@Nullable String prop) {
                // Renvoie une valeur à l'appelant
                return prop != null && prop.equals(value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        /**
         * <p>Vanilla has some fancy behavior to get integer properties as ints, but seems to just compare the value
         * anyway if its a string. Our behavior here is to attempt to parse the values as an integer and default
         * to a string.compareTo otherwise.</p>
         *
         * <p>Providing no min or max or a property which does exist results in a constant false.</p>
         *
         * @param min The min value to match, inclusive
         * @param max The max value to match, exclusive
         */
        // Déclaration de type (classe/interface/enum/record)
        record Range(@Nullable String min, @Nullable String max) implements ValuePredicate {
            // Affecte une valeur
            public static final NetworkBuffer.Type<Range> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Instruction de code
                    STRING.optional(), Range::min,
                    // Instruction de code
                    STRING.optional(), Range::max,
                    // Instruction de code
                    Range::new);
            // Affecte une valeur
            public static final Codec<Range> CODEC = StructCodec.struct(
                    // Instruction de code
                    "min", Codec.STRING.optional(), Range::min,
                    // Instruction de code
                    "max", Codec.STRING.optional(), Range::max,
                    // Instruction de code
                    Range::new);

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean test(@Nullable String prop) {
                // Embranchement : vérifie une condition
                if (prop == null || (min == null && max == null)) return false;
                // Gestion des exceptions
                try {
                    // Try to match as integers
                    // Appelle une méthode
                    int value = Integer.parseInt(prop);
                    // Renvoie une valeur à l'appelant
                    return (min == null || value >= Integer.parseInt(min))
                            // Appelle une méthode
                            && (max == null || value < Integer.parseInt(max));
                // Début d'une méthode/d'un bloc
                } catch (NumberFormatException e) {
                    // Not an integer, just compare the strings
                    // Renvoie une valeur à l'appelant
                    return (min == null || prop.compareTo(min) >= 0)
                            // Appelle une méthode
                            && (max == null || prop.compareTo(max) < 0);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
