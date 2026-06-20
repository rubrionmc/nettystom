// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertThrows;

// Type declaration (class/interface/enum/record)
public class NetworkBufferTemplateTest {

    // Start of a method/block
    private static <T> void assertRoundTrip(NetworkBuffer.Type<T> type, T expected) {
        // Calls a method
        var array = NetworkBuffer.makeArray(type, expected);
        // Calls a method
        var buffer = NetworkBuffer.wrap(array, 0, array.length);
        // Calls a method
        assertEquals(expected, buffer.read(type));
        // Calls a method
        assertEquals(0, buffer.readableBytes());
    // End of a block/expression
    }

    // Start of a method/block
    private static NetworkBuffer.Type<Integer> trackingVarInt(String name, List<String> events) {
        // Returns a value to the caller
        return new NetworkBuffer.Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, Integer value) {
                // Calls a method
                events.add("write:" + name + "=" + value);
                // Calls a method
                buffer.write(VAR_INT, value);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Integer read(NetworkBuffer buffer) {
                // Calls a method
                Integer value = buffer.read(VAR_INT);
                // Calls a method
                events.add("read:" + name + "=" + value);
                // Returns a value to the caller
                return value;
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleFieldTemplate() {
        // Type declaration (class/interface/enum/record)
        record TemplateSingle(int value) {
        // End of a block/expression
        }
        // Assigns a value
        NetworkBuffer.Type<TemplateSingle> singleType = NetworkBufferTemplate.template(
                // Code statement
                VAR_INT, TemplateSingle::value,
                // Code statement
                TemplateSingle::new
        // End of a block/expression
        );
        // Calls a method
        assertRoundTrip(singleType, new TemplateSingle(12));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void twoFieldTemplate() {
        // Type declaration (class/interface/enum/record)
        record TemplatePair(int first, String second) {
        // End of a block/expression
        }
        // Assigns a value
        NetworkBuffer.Type<TemplatePair> pairType = NetworkBufferTemplate.template(
                // Code statement
                VAR_INT, TemplatePair::first,
                // Code statement
                STRING, TemplatePair::second,
                // Code statement
                TemplatePair::new
        // End of a block/expression
        );
        // Calls a method
        assertRoundTrip(pairType, new TemplatePair(-7, "pair"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void threeFieldTemplate() {
        // Type declaration (class/interface/enum/record)
        record TemplateTriple(int first, String second, long third) {
        // End of a block/expression
        }
        // Assigns a value
        NetworkBuffer.Type<TemplateTriple> tripleType = NetworkBufferTemplate.template(
                // Code statement
                VAR_INT, TemplateTriple::first, STRING, TemplateTriple::second, LONG, TemplateTriple::third,
                // Code statement
                TemplateTriple::new);
        // Calls a method
        assertRoundTrip(tripleType, new TemplateTriple(1, "test", 3L));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void mixedTypeTemplate() {
        // Type declaration (class/interface/enum/record)
        record Mixed(boolean flag, byte b, short s, int var, long l, float f, double d, String text,
                     // Start of a method/block
                     String optionalText, List<Integer> ints) {
        // End of a block/expression
        }
        // Assigns a value
        NetworkBuffer.Type<Mixed> mixedType = NetworkBufferTemplate.template(
                // Code statement
                BOOLEAN, Mixed::flag,
                // Code statement
                BYTE, Mixed::b,
                // Code statement
                SHORT, Mixed::s,
                // Code statement
                VAR_INT, Mixed::var,
                // Code statement
                LONG, Mixed::l,
                // Code statement
                FLOAT, Mixed::f,
                // Code statement
                DOUBLE, Mixed::d,
                // Code statement
                STRING, Mixed::text,
                // Code statement
                STRING.optional(), Mixed::optionalText,
                // Code statement
                VAR_INT.list(16), Mixed::ints,
                // Code statement
                Mixed::new
        // End of a block/expression
        );

        // Calls a method
        assertRoundTrip(mixedType, new Mixed(true, (byte) -12, (short) 1234, 2_097_151, Long.MIN_VALUE, 12.5f, -0.25d, "hello", "optional", List.of(1, -2, 3, 4)));
        // Calls a method
        assertRoundTrip(mixedType, new Mixed(false, (byte) 42, (short) -1234, -1, Long.MAX_VALUE, -5.75f, 1024.5d, "world", null, List.of()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void maxFieldTemplate() {
        // Type declaration (class/interface/enum/record)
        record TwentyFields(int f1, int f2, int f3, int f4, int f5, int f6, int f7, int f8, int f9, int f10, int f11,
                            // Start of a method/block
                            int f12, int f13, int f14, int f15, int f16, int f17, int f18, int f19, int f20) {
        // End of a block/expression
        }
        // Assigns a value
        NetworkBuffer.Type<TwentyFields> twentyFieldsType = NetworkBufferTemplate.template(
                // Code statement
                VAR_INT, TwentyFields::f1, VAR_INT, TwentyFields::f2, VAR_INT, TwentyFields::f3, VAR_INT, TwentyFields::f4,
                // Code statement
                VAR_INT, TwentyFields::f5, VAR_INT, TwentyFields::f6, VAR_INT, TwentyFields::f7, VAR_INT, TwentyFields::f8,
                // Code statement
                VAR_INT, TwentyFields::f9, VAR_INT, TwentyFields::f10, VAR_INT, TwentyFields::f11, VAR_INT, TwentyFields::f12,
                // Code statement
                VAR_INT, TwentyFields::f13, VAR_INT, TwentyFields::f14, VAR_INT, TwentyFields::f15, VAR_INT, TwentyFields::f16,
                // Code statement
                VAR_INT, TwentyFields::f17, VAR_INT, TwentyFields::f18, VAR_INT, TwentyFields::f19, VAR_INT, TwentyFields::f20,
                // Code statement
                TwentyFields::new
        // End of a block/expression
        );

        // Calls a method
        assertRoundTrip(twentyFieldsType, new TwentyFields(1, -2, 3, -4, 5, -6, 7, -8, 9, -10, 11, -12, 13, -14, 15, -16, 17, -18, 19, -20));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void constantTemplateWritesNoBytesAndReadsConstantValue() {
        // Calls a method
        NetworkBuffer.Type<String> constantType = NetworkBufferTemplate.template("constant");
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer();

        // Calls a method
        buffer.write(constantType, "ignored");

        // Calls a method
        assertEquals(0, buffer.writeIndex());
        // Calls a method
        assertEquals("constant", buffer.read(constantType));
        // Calls a method
        assertEquals(0, buffer.readIndex());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void supplierTemplateWritesNoBytesAndReadsSuppliedValue() {
        // Assigns a value
        int[] calls = {0};
        // Calls a method
        NetworkBuffer.Type<String> supplierType = NetworkBufferTemplate.template(() -> "value-" + ++calls[0]);
        // Calls a method
        var buffer = NetworkBuffer.resizableBuffer();

        // Calls a method
        buffer.write(supplierType, "ignored");

        // Calls a method
        assertEquals(0, buffer.writeIndex());
        // Calls a method
        assertEquals("value-1", buffer.read(supplierType));
        // Calls a method
        assertEquals("value-2", buffer.read(supplierType));
        // Calls a method
        assertEquals(0, buffer.readIndex());
        // Calls a method
        assertEquals(2, calls[0]);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void templatePreservesFieldOrder() {
        // Type declaration (class/interface/enum/record)
        record Ordered(int first, int second, int third) {
        // End of a block/expression
        }
        // Calls a method
        var events = new ArrayList<String>();
        // Calls a method
        NetworkBuffer.Type<Integer> first = trackingVarInt("first", events);
        // Calls a method
        NetworkBuffer.Type<Integer> second = trackingVarInt("second", events);
        // Calls a method
        NetworkBuffer.Type<Integer> third = trackingVarInt("third", events);
        // Assigns a value
        NetworkBuffer.Type<Ordered> orderedType = NetworkBufferTemplate.template(
                // Code statement
                first, Ordered::first,
                // Code statement
                second, Ordered::second,
                // Code statement
                third, Ordered::third,
                // Code statement
                Ordered::new
        // End of a block/expression
        );

        // Calls a method
        assertRoundTrip(orderedType, new Ordered(1, 2, 3));

        // Calls a method
        assertEquals(List.of("write:first=1", "write:second=2", "write:third=3", "read:first=1", "read:second=2", "read:third=3"), events);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void templateNullArguments() {
        // Type declaration (class/interface/enum/record)
        record Single(int value) {
        // End of a block/expression
        }
        // Calls a method
        assertThrows(NullPointerException.class, () -> NetworkBufferTemplate.template(null, Single::value, Single::new));
        // Calls a method
        assertThrows(NullPointerException.class, () -> NetworkBufferTemplate.template(VAR_INT, null, Single::new));
        // Calls a method
        assertThrows(NullPointerException.class, () -> NetworkBufferTemplate.template(VAR_INT, Single::value, null));
    // End of a block/expression
    }
// End of a block/expression
}
