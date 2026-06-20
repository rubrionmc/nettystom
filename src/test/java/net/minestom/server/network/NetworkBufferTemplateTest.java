// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertThrows;

// Déclaration de type (classe/interface/enum/record)
public class NetworkBufferTemplateTest {

    // Début d'une méthode/d'un bloc
    private static <T> void assertRoundTrip(NetworkBuffer.Type<T> type, T expected) {
        // Appelle une méthode
        var array = NetworkBuffer.makeArray(type, expected);
        // Appelle une méthode
        var buffer = NetworkBuffer.wrap(array, 0, array.length);
        // Appelle une méthode
        assertEquals(expected, buffer.read(type));
        // Appelle une méthode
        assertEquals(0, buffer.readableBytes());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static NetworkBuffer.Type<Integer> trackingVarInt(String name, List<String> events) {
        // Renvoie une valeur à l'appelant
        return new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Integer value) {
                // Appelle une méthode
                events.add("write:" + name + "=" + value);
                // Appelle une méthode
                buffer.write(VAR_INT, value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Integer read(NetworkBuffer buffer) {
                // Appelle une méthode
                Integer value = buffer.read(VAR_INT);
                // Appelle une méthode
                events.add("read:" + name + "=" + value);
                // Renvoie une valeur à l'appelant
                return value;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleFieldTemplate() {
        // Déclaration de type (classe/interface/enum/record)
        record TemplateSingle(int value) {
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        NetworkBuffer.Type<TemplateSingle> singleType = NetworkBufferTemplate.template(
                // Instruction de code
                VAR_INT, TemplateSingle::value,
                // Instruction de code
                TemplateSingle::new
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertRoundTrip(singleType, new TemplateSingle(12));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void twoFieldTemplate() {
        // Déclaration de type (classe/interface/enum/record)
        record TemplatePair(int first, String second) {
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        NetworkBuffer.Type<TemplatePair> pairType = NetworkBufferTemplate.template(
                // Instruction de code
                VAR_INT, TemplatePair::first,
                // Instruction de code
                STRING, TemplatePair::second,
                // Instruction de code
                TemplatePair::new
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertRoundTrip(pairType, new TemplatePair(-7, "pair"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void threeFieldTemplate() {
        // Déclaration de type (classe/interface/enum/record)
        record TemplateTriple(int first, String second, long third) {
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        NetworkBuffer.Type<TemplateTriple> tripleType = NetworkBufferTemplate.template(
                // Instruction de code
                VAR_INT, TemplateTriple::first, STRING, TemplateTriple::second, LONG, TemplateTriple::third,
                // Instruction de code
                TemplateTriple::new);
        // Appelle une méthode
        assertRoundTrip(tripleType, new TemplateTriple(1, "test", 3L));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void mixedTypeTemplate() {
        // Déclaration de type (classe/interface/enum/record)
        record Mixed(boolean flag, byte b, short s, int var, long l, float f, double d, String text,
                     // Début d'une méthode/d'un bloc
                     String optionalText, List<Integer> ints) {
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        NetworkBuffer.Type<Mixed> mixedType = NetworkBufferTemplate.template(
                // Instruction de code
                BOOLEAN, Mixed::flag,
                // Instruction de code
                BYTE, Mixed::b,
                // Instruction de code
                SHORT, Mixed::s,
                // Instruction de code
                VAR_INT, Mixed::var,
                // Instruction de code
                LONG, Mixed::l,
                // Instruction de code
                FLOAT, Mixed::f,
                // Instruction de code
                DOUBLE, Mixed::d,
                // Instruction de code
                STRING, Mixed::text,
                // Instruction de code
                STRING.optional(), Mixed::optionalText,
                // Instruction de code
                VAR_INT.list(16), Mixed::ints,
                // Instruction de code
                Mixed::new
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        assertRoundTrip(mixedType, new Mixed(true, (byte) -12, (short) 1234, 2_097_151, Long.MIN_VALUE, 12.5f, -0.25d, "hello", "optional", List.of(1, -2, 3, 4)));
        // Appelle une méthode
        assertRoundTrip(mixedType, new Mixed(false, (byte) 42, (short) -1234, -1, Long.MAX_VALUE, -5.75f, 1024.5d, "world", null, List.of()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void maxFieldTemplate() {
        // Déclaration de type (classe/interface/enum/record)
        record TwentyFields(int f1, int f2, int f3, int f4, int f5, int f6, int f7, int f8, int f9, int f10, int f11,
                            // Début d'une méthode/d'un bloc
                            int f12, int f13, int f14, int f15, int f16, int f17, int f18, int f19, int f20) {
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        NetworkBuffer.Type<TwentyFields> twentyFieldsType = NetworkBufferTemplate.template(
                // Instruction de code
                VAR_INT, TwentyFields::f1, VAR_INT, TwentyFields::f2, VAR_INT, TwentyFields::f3, VAR_INT, TwentyFields::f4,
                // Instruction de code
                VAR_INT, TwentyFields::f5, VAR_INT, TwentyFields::f6, VAR_INT, TwentyFields::f7, VAR_INT, TwentyFields::f8,
                // Instruction de code
                VAR_INT, TwentyFields::f9, VAR_INT, TwentyFields::f10, VAR_INT, TwentyFields::f11, VAR_INT, TwentyFields::f12,
                // Instruction de code
                VAR_INT, TwentyFields::f13, VAR_INT, TwentyFields::f14, VAR_INT, TwentyFields::f15, VAR_INT, TwentyFields::f16,
                // Instruction de code
                VAR_INT, TwentyFields::f17, VAR_INT, TwentyFields::f18, VAR_INT, TwentyFields::f19, VAR_INT, TwentyFields::f20,
                // Instruction de code
                TwentyFields::new
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        assertRoundTrip(twentyFieldsType, new TwentyFields(1, -2, 3, -4, 5, -6, 7, -8, 9, -10, 11, -12, 13, -14, 15, -16, 17, -18, 19, -20));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void constantTemplateWritesNoBytesAndReadsConstantValue() {
        // Appelle une méthode
        NetworkBuffer.Type<String> constantType = NetworkBufferTemplate.template("constant");
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer();

        // Appelle une méthode
        buffer.write(constantType, "ignored");

        // Appelle une méthode
        assertEquals(0, buffer.writeIndex());
        // Appelle une méthode
        assertEquals("constant", buffer.read(constantType));
        // Appelle une méthode
        assertEquals(0, buffer.readIndex());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void supplierTemplateWritesNoBytesAndReadsSuppliedValue() {
        // Affecte une valeur
        int[] calls = {0};
        // Appelle une méthode
        NetworkBuffer.Type<String> supplierType = NetworkBufferTemplate.template(() -> "value-" + ++calls[0]);
        // Appelle une méthode
        var buffer = NetworkBuffer.resizableBuffer();

        // Appelle une méthode
        buffer.write(supplierType, "ignored");

        // Appelle une méthode
        assertEquals(0, buffer.writeIndex());
        // Appelle une méthode
        assertEquals("value-1", buffer.read(supplierType));
        // Appelle une méthode
        assertEquals("value-2", buffer.read(supplierType));
        // Appelle une méthode
        assertEquals(0, buffer.readIndex());
        // Appelle une méthode
        assertEquals(2, calls[0]);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void templatePreservesFieldOrder() {
        // Déclaration de type (classe/interface/enum/record)
        record Ordered(int first, int second, int third) {
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        var events = new ArrayList<String>();
        // Appelle une méthode
        NetworkBuffer.Type<Integer> first = trackingVarInt("first", events);
        // Appelle une méthode
        NetworkBuffer.Type<Integer> second = trackingVarInt("second", events);
        // Appelle une méthode
        NetworkBuffer.Type<Integer> third = trackingVarInt("third", events);
        // Affecte une valeur
        NetworkBuffer.Type<Ordered> orderedType = NetworkBufferTemplate.template(
                // Instruction de code
                first, Ordered::first,
                // Instruction de code
                second, Ordered::second,
                // Instruction de code
                third, Ordered::third,
                // Instruction de code
                Ordered::new
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        assertRoundTrip(orderedType, new Ordered(1, 2, 3));

        // Appelle une méthode
        assertEquals(List.of("write:first=1", "write:second=2", "write:third=3", "read:first=1", "read:second=2", "read:third=3"), events);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void templateNullArguments() {
        // Déclaration de type (classe/interface/enum/record)
        record Single(int value) {
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> NetworkBufferTemplate.template(null, Single::value, Single::new));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> NetworkBufferTemplate.template(VAR_INT, null, Single::new));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> NetworkBufferTemplate.template(VAR_INT, Single::value, null));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
