// Package declaration for this file
package net.minestom.server.instance.block.predicate;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.Predicate;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record PropertiesPredicate(Map<String, ValuePredicate> properties) implements Predicate<Block> {

    // Assigns a value
    public static final NetworkBuffer.Type<PropertiesPredicate> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.STRING.mapValue(ValuePredicate.NETWORK_TYPE), PropertiesPredicate::properties,
            // Code statement
            PropertiesPredicate::new
    // End of a block/expression
    );
    // Assigns a value
    public static final Codec<PropertiesPredicate> CODEC = Codec.STRING.mapValue(ValuePredicate.CODEC)
            // Calls a method
            .transform(PropertiesPredicate::new, PropertiesPredicate::properties);

    // Start of a method/block
    public static PropertiesPredicate exact(String key, String value) {
        // Returns a value to the caller
        return new PropertiesPredicate(Map.of(key, new ValuePredicate.Exact(value)));
    // End of a block/expression
    }

    // Start of a method/block
    public PropertiesPredicate {
        // Calls a method
        properties = Map.copyOf(properties);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean test(Block block) {
        // Loop: repeats a block
        for (Map.Entry<String, ValuePredicate> entry : properties.entrySet()) {
            // Calls a method
            final String value = block.getProperty(entry.getKey());
            // Branch: checks a condition
            if (!entry.getValue().test(value))
                // Returns a value to the caller
                return false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public sealed interface ValuePredicate extends Predicate<@Nullable String> permits ValuePredicate.Exact, ValuePredicate.Range {
        // Assigns a value
        NetworkBuffer.Type<ValuePredicate> NETWORK_TYPE = NetworkBuffer.Either(Exact.NETWORK_TYPE, Range.NETWORK_TYPE)
                // Start of a method/block
                .transform(Either::identity, it -> switch (it) {
                            // Multiple branching (switch/case)
                            case Exact exact -> Either.left(exact);
                            // Multiple branching (switch/case)
                            case Range range -> Either.right(range);
                // End of a block/expression
                });
        // Assigns a value
        Codec<ValuePredicate> CODEC = new Codec<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<ValuePredicate> decode(Transcoder<D> coder, D value) {
                // Calls a method
                final Result<Exact> exactResult = Exact.CODEC.decode(coder, value);
                // Branch: checks a condition
                if (exactResult instanceof Result.Ok(Exact exact))
                    // Returns a value to the caller
                    return new Result.Ok<>(exact);
                // Calls a method
                final Result<Range> rangeResult = Range.CODEC.decode(coder, value);
                // Branch: checks a condition
                if (rangeResult instanceof Result.Ok(Range range))
                    // Returns a value to the caller
                    return new Result.Ok<>(range);
                // Returns a value to the caller
                return new Result.Error<>("Invalid value predicate");
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encode(Transcoder<D> coder, @Nullable ValuePredicate value) {
                // Branch: checks a condition
                if (value == null) return new Result.Error<>("null");
                // Returns a value to the caller
                return switch (value) {
                    // Multiple branching (switch/case)
                    case Exact exact -> Exact.CODEC.encode(coder, exact);
                    // Multiple branching (switch/case)
                    case Range range -> Range.CODEC.encode(coder, range);
                // End of a block/expression
                };
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Type declaration (class/interface/enum/record)
        record Exact(@Nullable String value) implements ValuePredicate {

            // Calls a method
            public static final NetworkBuffer.Type<Exact> NETWORK_TYPE = NetworkBuffer.STRING.transform(Exact::new, Exact::value);
            // Calls a method
            public static final Codec<Exact> CODEC = Codec.STRING.transform(Exact::new, Exact::value);

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean test(@Nullable String prop) {
                // Returns a value to the caller
                return prop != null && prop.equals(value);
            // End of a block/expression
            }
        // End of a block/expression
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
        // Type declaration (class/interface/enum/record)
        record Range(@Nullable String min, @Nullable String max) implements ValuePredicate {
            // Assigns a value
            public static final NetworkBuffer.Type<Range> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Code statement
                    STRING.optional(), Range::min,
                    // Code statement
                    STRING.optional(), Range::max,
                    // Code statement
                    Range::new);
            // Assigns a value
            public static final Codec<Range> CODEC = StructCodec.struct(
                    // Code statement
                    "min", Codec.STRING.optional(), Range::min,
                    // Code statement
                    "max", Codec.STRING.optional(), Range::max,
                    // Code statement
                    Range::new);

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean test(@Nullable String prop) {
                // Branch: checks a condition
                if (prop == null || (min == null && max == null)) return false;
                // Exception handling
                try {
                    // Try to match as integers
                    // Calls a method
                    int value = Integer.parseInt(prop);
                    // Returns a value to the caller
                    return (min == null || value >= Integer.parseInt(min))
                            // Calls a method
                            && (max == null || value < Integer.parseInt(max));
                // Start of a method/block
                } catch (NumberFormatException e) {
                    // Not an integer, just compare the strings
                    // Returns a value to the caller
                    return (min == null || prop.compareTo(min) >= 0)
                            // Calls a method
                            && (max == null || prop.compareTo(max) < 0);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
