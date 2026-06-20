// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer.Type;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * A utility class to create {@link NetworkBuffer.Type} templates
 * useful for serializing and deserializing objects and ensure the same type written is the same type read.
 * <pre>{@code
 * record MyClass(int id, String name) {
 *         // Using the template utility:
 *         public static final NetworkBuffer.Type<MyClass> SERIALIZER = NetworkBufferTemplate.template(
 *                 NetworkBuffer.INT, MyClass::id,
 *                 NetworkBuffer.STRING, MyClass::name,
 *                 MyClass::new
 *         );
 *         // Compared to writing a custom serializer:
 *         public static final NetworkBuffer.Type<MyClass> SERIALIZER = new NetworkBufferTypeImpl<>() {
 *             @Override
 *             public void write(NetworkBuffer buffer, MyClass value) {
 *                 buffer.write(NetworkBuffer.INT, value.id());
 *                 buffer.write(NetworkBuffer.STRING, value.name());
 *             }
 *
 *             @Override
 *             public MyClass read(NetworkBuffer buffer) {
 *                 return new MyClass(
 *                         buffer.read(NetworkBuffer.INT),
 *                         buffer.read(NetworkBuffer.STRING)
 *                 );
 *             }
 *         };
 * }
 * }</pre>
 */
// Déclaration de type (classe/interface/enum/record)
public final class NetworkBufferTemplate {
    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F1<P1 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F2<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F3<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F4<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F5<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F6<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F7<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F8<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F9<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F10<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F11<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F12<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F13<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F14<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F15<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F16<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F17<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16, P17 p17);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F18<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16, P17 p17, P18 p18);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F19<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16, P17 p17, P18 p18, P19 p19);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F20<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object, P20 extends @UnknownNullability Object, R extends @UnknownNullability Object> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16, P17 p17, P18 p18, P19 p19, P20 p20);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template that always returns {@link R}
     *
     * @param value the value to return
     * @param <R>   the type of the value
     * @return the new template
     */
    // Début d'une méthode/d'un bloc
    public static <R extends @UnknownNullability Object> Type<R> template(R value) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return value;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template that uses a supplier to get a value {@link R}
     *
     * @param supplier the supplier to get the value
     * @param <R>      the type of the value
     * @return the new template
     */
    // Début d'une méthode/d'un bloc
    public static <R extends @UnknownNullability Object> Type<R> template(Supplier<? extends R> supplier) {
        // Appelle une méthode
        Objects.requireNonNull(supplier, "supplier");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return supplier.get();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with one parameter
     *
     * @param p1   the first parameter {@link Type}
     * @param g1   the first parameter getter
     * @param ctor the constructor for {@link R}
     * @param <P1> the type of the first parameter
     * @param <R>  the type of the value
     * @return the new template
     */
    // Début d'une méthode/d'un bloc
    public static <P1 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(Type<P1> p1, Function<? super R, ? extends P1> g1, F1<? super P1, ? extends R> ctor) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(p1.read(buffer));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with two parameters
     *
     * @param p1   the first parameter {@link Type}
     * @param g1   the first parameter getter
     * @param p2   the second parameter {@link Type}
     * @param g2   the second parameter getter
     * @param ctor the constructor for {@link R}
     * @param <P1> the type of the first parameter
     * @param <P2> the type of the second parameter
     * @param <R>  the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            F2<? super P1, ? super P2, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(p1.read(buffer), p2.read(buffer));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with three parameters
     *
     * @param p1   the first parameter {@link Type}
     * @param g1   the first parameter getter
     * @param p2   the second parameter {@link Type}
     * @param g2   the second parameter getter
     * @param p3   the third parameter {@link Type}
     * @param g3   the third parameter getter
     * @param ctor the constructor for {@link R}
     * @param <P1> the type of the first parameter
     * @param <P2> the type of the second parameter
     * @param <P3> the type of the third parameter
     * @param <R>  the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, F3<? super P1, ? super P2, ? super P3, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(p1.read(buffer), p2.read(buffer), p3.read(buffer));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with four parameters
     *
     * @param p1   the first parameter {@link Type}
     * @param g1   the first parameter getter
     * @param p2   the second parameter {@link Type}
     * @param g2   the second parameter getter
     * @param p3   the third parameter {@link Type}
     * @param g3   the third parameter getter
     * @param p4   the fourth parameter {@link Type}
     * @param g4   the fourth parameter getter
     * @param ctor the constructor for {@link R}
     * @param <P1> the type of the first parameter
     * @param <P2> the type of the second parameter
     * @param <P3> the type of the third parameter
     * @param <P4> the type of the fourth parameter
     * @param <R>  the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            F4<? super P1, ? super P2, ? super P3, ? super P4, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with five parameters
     *
     * @param p1   the first parameter {@link Type}
     * @param g1   the first parameter getter
     * @param p2   the second parameter {@link Type}
     * @param g2   the second parameter getter
     * @param p3   the third parameter {@link Type}
     * @param g3   the third parameter getter
     * @param p4   the fourth parameter {@link Type}
     * @param g4   the fourth parameter getter
     * @param p5   the fifth parameter {@link Type}
     * @param g5   the fifth parameter getter
     * @param ctor the constructor for {@link R}
     * @param <P1> the type of the first parameter
     * @param <P2> the type of the second parameter
     * @param <P3> the type of the third parameter
     * @param <P4> the type of the fourth parameter
     * @param <P5> the type of the fifth parameter
     * @param <R>  the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, F5<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with six parameters
     *
     * @param p1   the first parameter {@link Type}
     * @param g1   the first parameter getter
     * @param p2   the second parameter {@link Type}
     * @param g2   the second parameter getter
     * @param p3   the third parameter {@link Type}
     * @param g3   the third parameter getter
     * @param p4   the fourth parameter {@link Type}
     * @param g4   the fourth parameter getter
     * @param p5   the fifth parameter {@link Type}
     * @param g5   the fifth parameter getter
     * @param p6   the sixth parameter {@link Type}
     * @param g6   the sixth parameter getter
     * @param ctor the constructor for {@link R}
     * @param <P1> the type of the first parameter
     * @param <P2> the type of the second parameter
     * @param <P3> the type of the third parameter
     * @param <P4> the type of the fourth parameter
     * @param <P5> the type of the fifth parameter
     * @param <P6> the type of the sixth parameter
     * @param <R>  the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            F6<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with seven parameters
     *
     * @param p1   the first parameter {@link Type}
     * @param g1   the first parameter getter
     * @param p2   the second parameter {@link Type}
     * @param g2   the second parameter getter
     * @param p3   the third parameter {@link Type}
     * @param g3   the third parameter getter
     * @param p4   the fourth parameter {@link Type}
     * @param g4   the fourth parameter getter
     * @param p5   the fifth parameter {@link Type}
     * @param g5   the fifth parameter getter
     * @param p6   the sixth parameter {@link Type}
     * @param g6   the sixth parameter getter
     * @param p7   the seventh parameter {@link Type}
     * @param g7   the seventh parameter getter
     * @param ctor the constructor for {@link R}
     * @param <P1> the type of the first parameter
     * @param <P2> the type of the second parameter
     * @param <P3> the type of the third parameter
     * @param <P4> the type of the fourth parameter
     * @param <P5> the type of the fifth parameter
     * @param <P6> the type of the sixth parameter
     * @param <P7> the type of the seventh parameter
     * @param <R>  the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, F7<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with eight parameters
     *
     * @param p1   the first parameter {@link Type}
     * @param g1   the first parameter getter
     * @param p2   the second parameter {@link Type}
     * @param g2   the second parameter getter
     * @param p3   the third parameter {@link Type}
     * @param g3   the third parameter getter
     * @param p4   the fourth parameter {@link Type}
     * @param g4   the fourth parameter getter
     * @param p5   the fifth parameter {@link Type}
     * @param g5   the fifth parameter getter
     * @param p6   the sixth parameter {@link Type}
     * @param g6   the sixth parameter getter
     * @param p7   the seventh parameter {@link Type}
     * @param g7   the seventh parameter getter
     * @param p8   the eighth parameter {@link Type}
     * @param g8   the eighth parameter getter
     * @param ctor the constructor for {@link R}
     * @param <P1> the type of the first parameter
     * @param <P2> the type of the second parameter
     * @param <P3> the type of the third parameter
     * @param <P4> the type of the fourth parameter
     * @param <P5> the type of the fifth parameter
     * @param <P6> the type of the sixth parameter
     * @param <P7> the type of the seventh parameter
     * @param <P8> the type of the eighth parameter
     * @param <R>  the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            F8<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with nine parameters
     *
     * @param p1   the first parameter {@link Type}
     * @param g1   the first parameter getter
     * @param p2   the second parameter {@link Type}
     * @param g2   the second parameter getter
     * @param p3   the third parameter {@link Type}
     * @param g3   the third parameter getter
     * @param p4   the fourth parameter {@link Type}
     * @param g4   the fourth parameter getter
     * @param p5   the fifth parameter {@link Type}
     * @param g5   the fifth parameter getter
     * @param p6   the sixth parameter {@link Type}
     * @param g6   the sixth parameter getter
     * @param p7   the seventh parameter {@link Type}
     * @param g7   the seventh parameter getter
     * @param p8   the eighth parameter {@link Type}
     * @param g8   the eighth parameter getter
     * @param p9   the ninth parameter {@link Type}
     * @param g9   the ninth parameter getter
     * @param ctor the constructor for {@link R}
     * @param <P1> the type of the first parameter
     * @param <P2> the type of the second parameter
     * @param <P3> the type of the third parameter
     * @param <P4> the type of the fourth parameter
     * @param <P5> the type of the fifth parameter
     * @param <P6> the type of the sixth parameter
     * @param <P7> the type of the seventh parameter
     * @param <P8> the type of the eighth parameter
     * @param <P9> the type of the ninth parameter
     * @param <R>  the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, F9<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with ten parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            F10<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with eleven parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, F11<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with twelve parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param p12   the twelfth parameter {@link Type}
     * @param g12   the twelfth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <P12> the type of the twelfth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, Type<P12> p12, Function<? super R, ? extends P12> g12, F12<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, p12, g12, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(p12, "p12");
        // Appelle une méthode
        Objects.requireNonNull(g12, "g12");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
                // Appelle une méthode
                p12.write(buffer, g12.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer), p12.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with thirteen parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param p12   the twelfth parameter {@link Type}
     * @param g12   the twelfth parameter getter
     * @param p13   the thirteenth parameter {@link Type}
     * @param g13   the thirteenth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <P12> the type of the twelfth parameter
     * @param <P13> the type of the thirteenth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, Type<P12> p12, Function<? super R, ? extends P12> g12,
            // Instruction de code
            Type<P13> p13, Function<? super R, ? extends P13> g13,
            // Instruction de code
            F13<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, p12, g12, p13, g13, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(p12, "p12");
        // Appelle une méthode
        Objects.requireNonNull(g12, "g12");
        // Appelle une méthode
        Objects.requireNonNull(p13, "p13");
        // Appelle une méthode
        Objects.requireNonNull(g13, "g13");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
                // Appelle une méthode
                p12.write(buffer, g12.apply(value));
                // Appelle une méthode
                p13.write(buffer, g13.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer), p12.read(buffer),
                        // Instruction de code
                        p13.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with fourteen parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param p12   the twelfth parameter {@link Type}
     * @param g12   the twelfth parameter getter
     * @param p13   the thirteenth parameter {@link Type}
     * @param g13   the thirteenth parameter getter
     * @param p14   the fourteenth parameter {@link Type}
     * @param g14   the fourteenth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <P12> the type of the twelfth parameter
     * @param <P13> the type of the thirteenth parameter
     * @param <P14> the type of the fourteenth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, Type<P12> p12, Function<? super R, ? extends P12> g12,
            // Instruction de code
            Type<P13> p13, Function<? super R, ? extends P13> g13, Type<P14> p14, Function<? super R, ? extends P14> g14,
            // Instruction de code
            F14<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, p12, g12, p13, g13, p14, g14, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(p12, "p12");
        // Appelle une méthode
        Objects.requireNonNull(g12, "g12");
        // Appelle une méthode
        Objects.requireNonNull(p13, "p13");
        // Appelle une méthode
        Objects.requireNonNull(g13, "g13");
        // Appelle une méthode
        Objects.requireNonNull(p14, "p14");
        // Appelle une méthode
        Objects.requireNonNull(g14, "g14");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
                // Appelle une méthode
                p12.write(buffer, g12.apply(value));
                // Appelle une méthode
                p13.write(buffer, g13.apply(value));
                // Appelle une méthode
                p14.write(buffer, g14.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer), p12.read(buffer),
                        // Instruction de code
                        p13.read(buffer), p14.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with fifteen parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param p12   the twelfth parameter {@link Type}
     * @param g12   the twelfth parameter getter
     * @param p13   the thirteenth parameter {@link Type}
     * @param g13   the thirteenth parameter getter
     * @param p14   the fourteenth parameter {@link Type}
     * @param g14   the fourteenth parameter getter
     * @param p15   the fifteenth parameter {@link Type}
     * @param g15   the fifteenth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <P12> the type of the twelfth parameter
     * @param <P13> the type of the thirteenth parameter
     * @param <P14> the type of the fourteenth parameter
     * @param <P15> the type of the fifteenth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, Type<P12> p12, Function<? super R, ? extends P12> g12,
            // Instruction de code
            Type<P13> p13, Function<? super R, ? extends P13> g13, Type<P14> p14, Function<? super R, ? extends P14> g14,
            // Instruction de code
            Type<P15> p15, Function<? super R, ? extends P15> g15,
            // Instruction de code
            F15<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, p12, g12, p13, g13, p14, g14, p15, g15, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(p12, "p12");
        // Appelle une méthode
        Objects.requireNonNull(g12, "g12");
        // Appelle une méthode
        Objects.requireNonNull(p13, "p13");
        // Appelle une méthode
        Objects.requireNonNull(g13, "g13");
        // Appelle une méthode
        Objects.requireNonNull(p14, "p14");
        // Appelle une méthode
        Objects.requireNonNull(g14, "g14");
        // Appelle une méthode
        Objects.requireNonNull(p15, "p15");
        // Appelle une méthode
        Objects.requireNonNull(g15, "g15");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
                // Appelle une méthode
                p12.write(buffer, g12.apply(value));
                // Appelle une méthode
                p13.write(buffer, g13.apply(value));
                // Appelle une méthode
                p14.write(buffer, g14.apply(value));
                // Appelle une méthode
                p15.write(buffer, g15.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer), p12.read(buffer),
                        // Instruction de code
                        p13.read(buffer), p14.read(buffer),
                        // Instruction de code
                        p15.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with sixteen parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param p12   the twelfth parameter {@link Type}
     * @param g12   the twelfth parameter getter
     * @param p13   the thirteenth parameter {@link Type}
     * @param g13   the thirteenth parameter getter
     * @param p14   the fourteenth parameter {@link Type}
     * @param g14   the fourteenth parameter getter
     * @param p15   the fifteenth parameter {@link Type}
     * @param g15   the fifteenth parameter getter
     * @param p16   the sixteenth parameter {@link Type}
     * @param g16   the sixteenth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <P12> the type of the twelfth parameter
     * @param <P13> the type of the thirteenth parameter
     * @param <P14> the type of the fourteenth parameter
     * @param <P15> the type of the fifteenth parameter
     * @param <P16> the type of the sixteenth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, Type<P12> p12, Function<? super R, ? extends P12> g12,
            // Instruction de code
            Type<P13> p13, Function<? super R, ? extends P13> g13, Type<P14> p14, Function<? super R, ? extends P14> g14,
            // Instruction de code
            Type<P15> p15, Function<? super R, ? extends P15> g15, Type<P16> p16, Function<? super R, ? extends P16> g16,
            // Instruction de code
            F16<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, p12, g12, p13, g13, p14, g14, p15, g15, p16, g16, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(p12, "p12");
        // Appelle une méthode
        Objects.requireNonNull(g12, "g12");
        // Appelle une méthode
        Objects.requireNonNull(p13, "p13");
        // Appelle une méthode
        Objects.requireNonNull(g13, "g13");
        // Appelle une méthode
        Objects.requireNonNull(p14, "p14");
        // Appelle une méthode
        Objects.requireNonNull(g14, "g14");
        // Appelle une méthode
        Objects.requireNonNull(p15, "p15");
        // Appelle une méthode
        Objects.requireNonNull(g15, "g15");
        // Appelle une méthode
        Objects.requireNonNull(p16, "p16");
        // Appelle une méthode
        Objects.requireNonNull(g16, "g16");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
                // Appelle une méthode
                p12.write(buffer, g12.apply(value));
                // Appelle une méthode
                p13.write(buffer, g13.apply(value));
                // Appelle une méthode
                p14.write(buffer, g14.apply(value));
                // Appelle une méthode
                p15.write(buffer, g15.apply(value));
                // Appelle une méthode
                p16.write(buffer, g16.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer), p12.read(buffer),
                        // Instruction de code
                        p13.read(buffer), p14.read(buffer),
                        // Instruction de code
                        p15.read(buffer), p16.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with seventeen parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param p12   the twelfth parameter {@link Type}
     * @param g12   the twelfth parameter getter
     * @param p13   the thirteenth parameter {@link Type}
     * @param g13   the thirteenth parameter getter
     * @param p14   the fourteenth parameter {@link Type}
     * @param g14   the fourteenth parameter getter
     * @param p15   the fifteenth parameter {@link Type}
     * @param g15   the fifteenth parameter getter
     * @param p16   the sixteenth parameter {@link Type}
     * @param g16   the sixteenth parameter getter
     * @param p17   the seventeenth parameter {@link Type}
     * @param g17   the seventeenth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <P12> the type of the twelfth parameter
     * @param <P13> the type of the thirteenth parameter
     * @param <P14> the type of the fourteenth parameter
     * @param <P15> the type of the fifteenth parameter
     * @param <P16> the type of the sixteenth parameter
     * @param <P17> the type of the seventeenth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, Type<P12> p12, Function<? super R, ? extends P12> g12,
            // Instruction de code
            Type<P13> p13, Function<? super R, ? extends P13> g13, Type<P14> p14, Function<? super R, ? extends P14> g14,
            // Instruction de code
            Type<P15> p15, Function<? super R, ? extends P15> g15, Type<P16> p16, Function<? super R, ? extends P16> g16,
            // Instruction de code
            Type<P17> p17, Function<? super R, ? extends P17> g17,
            // Instruction de code
            F17<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? super P17, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, p12, g12, p13, g13, p14, g14, p15, g15, p16, g16, p17, g17, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(p12, "p12");
        // Appelle une méthode
        Objects.requireNonNull(g12, "g12");
        // Appelle une méthode
        Objects.requireNonNull(p13, "p13");
        // Appelle une méthode
        Objects.requireNonNull(g13, "g13");
        // Appelle une méthode
        Objects.requireNonNull(p14, "p14");
        // Appelle une méthode
        Objects.requireNonNull(g14, "g14");
        // Appelle une méthode
        Objects.requireNonNull(p15, "p15");
        // Appelle une méthode
        Objects.requireNonNull(g15, "g15");
        // Appelle une méthode
        Objects.requireNonNull(p16, "p16");
        // Appelle une méthode
        Objects.requireNonNull(g16, "g16");
        // Appelle une méthode
        Objects.requireNonNull(p17, "p17");
        // Appelle une méthode
        Objects.requireNonNull(g17, "g17");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
                // Appelle une méthode
                p12.write(buffer, g12.apply(value));
                // Appelle une méthode
                p13.write(buffer, g13.apply(value));
                // Appelle une méthode
                p14.write(buffer, g14.apply(value));
                // Appelle une méthode
                p15.write(buffer, g15.apply(value));
                // Appelle une méthode
                p16.write(buffer, g16.apply(value));
                // Appelle une méthode
                p17.write(buffer, g17.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer), p12.read(buffer),
                        // Instruction de code
                        p13.read(buffer), p14.read(buffer),
                        // Instruction de code
                        p15.read(buffer), p16.read(buffer),
                        // Instruction de code
                        p17.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with eighteen parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param p12   the twelfth parameter {@link Type}
     * @param g12   the twelfth parameter getter
     * @param p13   the thirteenth parameter {@link Type}
     * @param g13   the thirteenth parameter getter
     * @param p14   the fourteenth parameter {@link Type}
     * @param g14   the fourteenth parameter getter
     * @param p15   the fifteenth parameter {@link Type}
     * @param g15   the fifteenth parameter getter
     * @param p16   the sixteenth parameter {@link Type}
     * @param g16   the sixteenth parameter getter
     * @param p17   the seventeenth parameter {@link Type}
     * @param g17   the seventeenth parameter getter
     * @param p18   the eighteenth parameter {@link Type}
     * @param g18   the eighteenth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <P12> the type of the twelfth parameter
     * @param <P13> the type of the thirteenth parameter
     * @param <P14> the type of the fourteenth parameter
     * @param <P15> the type of the fifteenth parameter
     * @param <P16> the type of the sixteenth parameter
     * @param <P17> the type of the seventeenth parameter
     * @param <P18> the type of the eighteenth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, Type<P12> p12, Function<? super R, ? extends P12> g12,
            // Instruction de code
            Type<P13> p13, Function<? super R, ? extends P13> g13, Type<P14> p14, Function<? super R, ? extends P14> g14,
            // Instruction de code
            Type<P15> p15, Function<? super R, ? extends P15> g15, Type<P16> p16, Function<? super R, ? extends P16> g16,
            // Instruction de code
            Type<P17> p17, Function<? super R, ? extends P17> g17, Type<P18> p18, Function<? super R, ? extends P18> g18,
            // Instruction de code
            F18<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? super P17, ? super P18, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, p12, g12, p13, g13, p14, g14, p15, g15, p16, g16, p17, g17, p18, g18, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(p12, "p12");
        // Appelle une méthode
        Objects.requireNonNull(g12, "g12");
        // Appelle une méthode
        Objects.requireNonNull(p13, "p13");
        // Appelle une méthode
        Objects.requireNonNull(g13, "g13");
        // Appelle une méthode
        Objects.requireNonNull(p14, "p14");
        // Appelle une méthode
        Objects.requireNonNull(g14, "g14");
        // Appelle une méthode
        Objects.requireNonNull(p15, "p15");
        // Appelle une méthode
        Objects.requireNonNull(g15, "g15");
        // Appelle une méthode
        Objects.requireNonNull(p16, "p16");
        // Appelle une méthode
        Objects.requireNonNull(g16, "g16");
        // Appelle une méthode
        Objects.requireNonNull(p17, "p17");
        // Appelle une méthode
        Objects.requireNonNull(g17, "g17");
        // Appelle une méthode
        Objects.requireNonNull(p18, "p18");
        // Appelle une méthode
        Objects.requireNonNull(g18, "g18");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
                // Appelle une méthode
                p12.write(buffer, g12.apply(value));
                // Appelle une méthode
                p13.write(buffer, g13.apply(value));
                // Appelle une méthode
                p14.write(buffer, g14.apply(value));
                // Appelle une méthode
                p15.write(buffer, g15.apply(value));
                // Appelle une méthode
                p16.write(buffer, g16.apply(value));
                // Appelle une méthode
                p17.write(buffer, g17.apply(value));
                // Appelle une méthode
                p18.write(buffer, g18.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer), p12.read(buffer),
                        // Instruction de code
                        p13.read(buffer), p14.read(buffer),
                        // Instruction de code
                        p15.read(buffer), p16.read(buffer),
                        // Instruction de code
                        p17.read(buffer), p18.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with nineteen parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param p12   the twelfth parameter {@link Type}
     * @param g12   the twelfth parameter getter
     * @param p13   the thirteenth parameter {@link Type}
     * @param g13   the thirteenth parameter getter
     * @param p14   the fourteenth parameter {@link Type}
     * @param g14   the fourteenth parameter getter
     * @param p15   the fifteenth parameter {@link Type}
     * @param g15   the fifteenth parameter getter
     * @param p16   the sixteenth parameter {@link Type}
     * @param g16   the sixteenth parameter getter
     * @param p17   the seventeenth parameter {@link Type}
     * @param g17   the seventeenth parameter getter
     * @param p18   the eighteenth parameter {@link Type}
     * @param g18   the eighteenth parameter getter
     * @param p19   the nineteenth parameter {@link Type}
     * @param g19   the nineteenth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <P12> the type of the twelfth parameter
     * @param <P13> the type of the thirteenth parameter
     * @param <P14> the type of the fourteenth parameter
     * @param <P15> the type of the fifteenth parameter
     * @param <P16> the type of the sixteenth parameter
     * @param <P17> the type of the seventeenth parameter
     * @param <P18> the type of the eighteenth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, Type<P12> p12, Function<? super R, ? extends P12> g12,
            // Instruction de code
            Type<P13> p13, Function<? super R, ? extends P13> g13, Type<P14> p14, Function<? super R, ? extends P14> g14,
            // Instruction de code
            Type<P15> p15, Function<? super R, ? extends P15> g15, Type<P16> p16, Function<? super R, ? extends P16> g16,
            // Instruction de code
            Type<P17> p17, Function<? super R, ? extends P17> g17, Type<P18> p18, Function<? super R, ? extends P18> g18,
            // Instruction de code
            Type<P19> p19, Function<? super R, ? extends P19> g19, F19<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? super P17, ? super P18, ? super P19, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, p12, g12, p13, g13, p14, g14, p15, g15, p16, g16, p17, g17, p18, g18, p19, g19, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(p12, "p12");
        // Appelle une méthode
        Objects.requireNonNull(g12, "g12");
        // Appelle une méthode
        Objects.requireNonNull(p13, "p13");
        // Appelle une méthode
        Objects.requireNonNull(g13, "g13");
        // Appelle une méthode
        Objects.requireNonNull(p14, "p14");
        // Appelle une méthode
        Objects.requireNonNull(g14, "g14");
        // Appelle une méthode
        Objects.requireNonNull(p15, "p15");
        // Appelle une méthode
        Objects.requireNonNull(g15, "g15");
        // Appelle une méthode
        Objects.requireNonNull(p16, "p16");
        // Appelle une méthode
        Objects.requireNonNull(g16, "g16");
        // Appelle une méthode
        Objects.requireNonNull(p17, "p17");
        // Appelle une méthode
        Objects.requireNonNull(g17, "g17");
        // Appelle une méthode
        Objects.requireNonNull(p18, "p18");
        // Appelle une méthode
        Objects.requireNonNull(g18, "g18");
        // Appelle une méthode
        Objects.requireNonNull(p19, "p19");
        // Appelle une méthode
        Objects.requireNonNull(g19, "g19");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
                // Appelle une méthode
                p12.write(buffer, g12.apply(value));
                // Appelle une méthode
                p13.write(buffer, g13.apply(value));
                // Appelle une méthode
                p14.write(buffer, g14.apply(value));
                // Appelle une méthode
                p15.write(buffer, g15.apply(value));
                // Appelle une méthode
                p16.write(buffer, g16.apply(value));
                // Appelle une méthode
                p17.write(buffer, g17.apply(value));
                // Appelle une méthode
                p18.write(buffer, g18.apply(value));
                // Appelle une méthode
                p19.write(buffer, g19.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer), p12.read(buffer),
                        // Instruction de code
                        p13.read(buffer), p14.read(buffer),
                        // Instruction de code
                        p15.read(buffer), p16.read(buffer),
                        // Instruction de code
                        p17.read(buffer), p18.read(buffer),
                        // Instruction de code
                        p19.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a template with twenty parameters
     *
     * @param p1    the first parameter {@link Type}
     * @param g1    the first parameter getter
     * @param p2    the second parameter {@link Type}
     * @param g2    the second parameter getter
     * @param p3    the third parameter {@link Type}
     * @param g3    the third parameter getter
     * @param p4    the fourth parameter {@link Type}
     * @param g4    the fourth parameter getter
     * @param p5    the fifth parameter {@link Type}
     * @param g5    the fifth parameter getter
     * @param p6    the sixth parameter {@link Type}
     * @param g6    the sixth parameter getter
     * @param p7    the seventh parameter {@link Type}
     * @param g7    the seventh parameter getter
     * @param p8    the eighth parameter {@link Type}
     * @param g8    the eighth parameter getter
     * @param p9    the ninth parameter {@link Type}
     * @param g9    the ninth parameter getter
     * @param p10   the tenth parameter {@link Type}
     * @param g10   the tenth parameter getter
     * @param p11   the eleventh parameter {@link Type}
     * @param g11   the eleventh parameter getter
     * @param p12   the twelfth parameter {@link Type}
     * @param g12   the twelfth parameter getter
     * @param p13   the thirteenth parameter {@link Type}
     * @param g13   the thirteenth parameter getter
     * @param p14   the fourteenth parameter {@link Type}
     * @param g14   the fourteenth parameter getter
     * @param p15   the fifteenth parameter {@link Type}
     * @param g15   the fifteenth parameter getter
     * @param p16   the sixteenth parameter {@link Type}
     * @param g16   the sixteenth parameter getter
     * @param p17   the seventeenth parameter {@link Type}
     * @param g17   the seventeenth parameter getter
     * @param p18   the eighteenth parameter {@link Type}
     * @param g18   the eighteenth parameter getter
     * @param p19   the nineteenth parameter {@link Type}
     * @param g19   the nineteenth parameter getter
     * @param p20   the twentieth parameter {@link Type}
     * @param g20   the twentieth parameter getter
     * @param ctor  the constructor for {@link R}
     * @param <P1>  the type of the first parameter
     * @param <P2>  the type of the second parameter
     * @param <P3>  the type of the third parameter
     * @param <P4>  the type of the fourth parameter
     * @param <P5>  the type of the fifth parameter
     * @param <P6>  the type of the sixth parameter
     * @param <P7>  the type of the seventh parameter
     * @param <P8>  the type of the eighth parameter
     * @param <P9>  the type of the ninth parameter
     * @param <P10> the type of the tenth parameter
     * @param <P11> the type of the eleventh parameter
     * @param <P12> the type of the twelfth parameter
     * @param <P13> the type of the thirteenth parameter
     * @param <P14> the type of the fourteenth parameter
     * @param <P15> the type of the fifteenth parameter
     * @param <P16> the type of the sixteenth parameter
     * @param <P17> the type of the seventeenth parameter
     * @param <P18> the type of the eighteenth parameter
     * @param <P19> the type of the nineteenth parameter
     * @param <P20> the type of the twentieth parameter
     * @param <R>   the type of the value
     * @return the new template
     */
    // Instruction de code
    public static <P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object, P20 extends @UnknownNullability Object, R extends @UnknownNullability Object> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<? super R, ? extends P1> g1, Type<P2> p2, Function<? super R, ? extends P2> g2,
            // Instruction de code
            Type<P3> p3, Function<? super R, ? extends P3> g3, Type<P4> p4, Function<? super R, ? extends P4> g4,
            // Instruction de code
            Type<P5> p5, Function<? super R, ? extends P5> g5, Type<P6> p6, Function<? super R, ? extends P6> g6,
            // Instruction de code
            Type<P7> p7, Function<? super R, ? extends P7> g7, Type<P8> p8, Function<? super R, ? extends P8> g8,
            // Instruction de code
            Type<P9> p9, Function<? super R, ? extends P9> g9, Type<P10> p10, Function<? super R, ? extends P10> g10,
            // Instruction de code
            Type<P11> p11, Function<? super R, ? extends P11> g11, Type<P12> p12, Function<? super R, ? extends P12> g12,
            // Instruction de code
            Type<P13> p13, Function<? super R, ? extends P13> g13, Type<P14> p14, Function<? super R, ? extends P14> g14,
            // Instruction de code
            Type<P15> p15, Function<? super R, ? extends P15> g15, Type<P16> p16, Function<? super R, ? extends P16> g16,
            // Instruction de code
            Type<P17> p17, Function<? super R, ? extends P17> g17, Type<P18> p18, Function<? super R, ? extends P18> g18,
            // Instruction de code
            Type<P19> p19, Function<? super R, ? extends P19> g19, Type<P20> p20, Function<? super R, ? extends P20> g20,
            // Instruction de code
            F20<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10, ? super P11, ? super P12, ? super P13, ? super P14, ? super P15, ? super P16, ? super P17, ? super P18, ? super P19, ? super P20, ? extends R> ctor
    // Début d'une méthode/d'un bloc
    ) {
        // Embranchement : vérifie une condition
        if (ServerFlag.TEMPLATE_COMPILER) return NetworkBufferTemplateImpl.template(p1, g1, p2, g2, p3, g3, p4, g4, p5, g5, p6, g6, p7, g7, p8, g8, p9, g9, p10, g10, p11, g11, p12, g12, p13, g13, p14, g14, p15, g15, p16, g16, p17, g17, p18, g18, p19, g19, p20, g20, ctor);
        // Appelle une méthode
        Objects.requireNonNull(p1, "p1");
        // Appelle une méthode
        Objects.requireNonNull(g1, "g1");
        // Appelle une méthode
        Objects.requireNonNull(p2, "p2");
        // Appelle une méthode
        Objects.requireNonNull(g2, "g2");
        // Appelle une méthode
        Objects.requireNonNull(p3, "p3");
        // Appelle une méthode
        Objects.requireNonNull(g3, "g3");
        // Appelle une méthode
        Objects.requireNonNull(p4, "p4");
        // Appelle une méthode
        Objects.requireNonNull(g4, "g4");
        // Appelle une méthode
        Objects.requireNonNull(p5, "p5");
        // Appelle une méthode
        Objects.requireNonNull(g5, "g5");
        // Appelle une méthode
        Objects.requireNonNull(p6, "p6");
        // Appelle une méthode
        Objects.requireNonNull(g6, "g6");
        // Appelle une méthode
        Objects.requireNonNull(p7, "p7");
        // Appelle une méthode
        Objects.requireNonNull(g7, "g7");
        // Appelle une méthode
        Objects.requireNonNull(p8, "p8");
        // Appelle une méthode
        Objects.requireNonNull(g8, "g8");
        // Appelle une méthode
        Objects.requireNonNull(p9, "p9");
        // Appelle une méthode
        Objects.requireNonNull(g9, "g9");
        // Appelle une méthode
        Objects.requireNonNull(p10, "p10");
        // Appelle une méthode
        Objects.requireNonNull(g10, "g10");
        // Appelle une méthode
        Objects.requireNonNull(p11, "p11");
        // Appelle une méthode
        Objects.requireNonNull(g11, "g11");
        // Appelle une méthode
        Objects.requireNonNull(p12, "p12");
        // Appelle une méthode
        Objects.requireNonNull(g12, "g12");
        // Appelle une méthode
        Objects.requireNonNull(p13, "p13");
        // Appelle une méthode
        Objects.requireNonNull(g13, "g13");
        // Appelle une méthode
        Objects.requireNonNull(p14, "p14");
        // Appelle une méthode
        Objects.requireNonNull(g14, "g14");
        // Appelle une méthode
        Objects.requireNonNull(p15, "p15");
        // Appelle une méthode
        Objects.requireNonNull(g15, "g15");
        // Appelle une méthode
        Objects.requireNonNull(p16, "p16");
        // Appelle une méthode
        Objects.requireNonNull(g16, "g16");
        // Appelle une méthode
        Objects.requireNonNull(p17, "p17");
        // Appelle une méthode
        Objects.requireNonNull(g17, "g17");
        // Appelle une méthode
        Objects.requireNonNull(p18, "p18");
        // Appelle une méthode
        Objects.requireNonNull(g18, "g18");
        // Appelle une méthode
        Objects.requireNonNull(p19, "p19");
        // Appelle une méthode
        Objects.requireNonNull(g19, "g19");
        // Appelle une méthode
        Objects.requireNonNull(p20, "p20");
        // Appelle une méthode
        Objects.requireNonNull(g20, "g20");
        // Appelle une méthode
        Objects.requireNonNull(ctor, "ctor");
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, R value) {
                // Appelle une méthode
                p1.write(buffer, g1.apply(value));
                // Appelle une méthode
                p2.write(buffer, g2.apply(value));
                // Appelle une méthode
                p3.write(buffer, g3.apply(value));
                // Appelle une méthode
                p4.write(buffer, g4.apply(value));
                // Appelle une méthode
                p5.write(buffer, g5.apply(value));
                // Appelle une méthode
                p6.write(buffer, g6.apply(value));
                // Appelle une méthode
                p7.write(buffer, g7.apply(value));
                // Appelle une méthode
                p8.write(buffer, g8.apply(value));
                // Appelle une méthode
                p9.write(buffer, g9.apply(value));
                // Appelle une méthode
                p10.write(buffer, g10.apply(value));
                // Appelle une méthode
                p11.write(buffer, g11.apply(value));
                // Appelle une méthode
                p12.write(buffer, g12.apply(value));
                // Appelle une méthode
                p13.write(buffer, g13.apply(value));
                // Appelle une méthode
                p14.write(buffer, g14.apply(value));
                // Appelle une méthode
                p15.write(buffer, g15.apply(value));
                // Appelle une méthode
                p16.write(buffer, g16.apply(value));
                // Appelle une méthode
                p17.write(buffer, g17.apply(value));
                // Appelle une méthode
                p18.write(buffer, g18.apply(value));
                // Appelle une méthode
                p19.write(buffer, g19.apply(value));
                // Appelle une méthode
                p20.write(buffer, g20.apply(value));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return ctor.apply(
                        // Instruction de code
                        p1.read(buffer), p2.read(buffer),
                        // Instruction de code
                        p3.read(buffer), p4.read(buffer),
                        // Instruction de code
                        p5.read(buffer), p6.read(buffer),
                        // Instruction de code
                        p7.read(buffer), p8.read(buffer),
                        // Instruction de code
                        p9.read(buffer), p10.read(buffer),
                        // Instruction de code
                        p11.read(buffer), p12.read(buffer),
                        // Instruction de code
                        p13.read(buffer), p14.read(buffer),
                        // Instruction de code
                        p15.read(buffer), p16.read(buffer),
                        // Instruction de code
                        p17.read(buffer), p18.read(buffer),
                        // Instruction de code
                        p19.read(buffer), p20.read(buffer)
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}