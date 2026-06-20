// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer.Type;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Déclaration de type (classe/interface/enum/record)
public final class NetworkBufferTemplate {
    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F1<P1 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F2<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F3<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F4<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F5<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F6<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F7<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F8<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F9<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F10<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F11<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F12<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F13<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F14<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F15<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F16<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F17<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16, P17 p17);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F18<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16, P17 p17, P18 p18);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F19<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16, P17 p17, P18 p18, P19 p19);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface F20<P1 extends @UnknownNullability Object, P2 extends @UnknownNullability Object, P3 extends @UnknownNullability Object, P4 extends @UnknownNullability Object, P5 extends @UnknownNullability Object, P6 extends @UnknownNullability Object, P7 extends @UnknownNullability Object, P8 extends @UnknownNullability Object, P9 extends @UnknownNullability Object, P10 extends @UnknownNullability Object, P11 extends @UnknownNullability Object, P12 extends @UnknownNullability Object, P13 extends @UnknownNullability Object, P14 extends @UnknownNullability Object, P15 extends @UnknownNullability Object, P16 extends @UnknownNullability Object, P17 extends @UnknownNullability Object, P18 extends @UnknownNullability Object, P19 extends @UnknownNullability Object, P20 extends @UnknownNullability Object, R> {
        // Appelle une méthode
        R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, P13 p13, P14 p14, P15 p15, P16 p16, P17 p17, P18 p18, P19 p19, P20 p20);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static <R> Type<R> template(R value) {
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

    // Début d'une méthode/d'un bloc
    public static <R> Type<R> template(Supplier<R> supplier) {
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

    // Début d'une méthode/d'un bloc
    public static <P1, R> Type<R> template(Type<P1> p1, Function<R, P1> g1, F1<P1, R> reader) {
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
                return reader.apply(p1.read(buffer));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <P1, P2, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            F2<P1, P2, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(p1.read(buffer), p2.read(buffer));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <P1, P2, P3, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, F3<P1, P2, P3, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(p1.read(buffer), p2.read(buffer), p3.read(buffer));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static <P1, P2, P3, P4, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            F4<P1, P2, P3, P4, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, F5<P1, P2, P3, P4, P5, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            F6<P1, P2, P3, P4, P5, P6, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, F7<P1, P2, P3, P4, P5, P6, P7, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            F8<P1, P2, P3, P4, P5, P6, P7, P8, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, F9<P1, P2, P3, P4, P5, P6, P7, P8, P9, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            F10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, F11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, Type<P12> p12, Function<R, P12> g12, F12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, Type<P12> p12, Function<R, P12> g12,
            // Instruction de code
            Type<P13> p13, Function<R, P13> g13,
            // Instruction de code
            F13<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, Type<P12> p12, Function<R, P12> g12,
            // Instruction de code
            Type<P13> p13, Function<R, P13> g13, Type<P14> p14, Function<R, P14> g14,
            // Instruction de code
            F14<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, Type<P12> p12, Function<R, P12> g12,
            // Instruction de code
            Type<P13> p13, Function<R, P13> g13, Type<P14> p14, Function<R, P14> g14,
            // Instruction de code
            Type<P15> p15, Function<R, P15> g15,
            // Instruction de code
            F15<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, Type<P12> p12, Function<R, P12> g12,
            // Instruction de code
            Type<P13> p13, Function<R, P13> g13, Type<P14> p14, Function<R, P14> g14,
            // Instruction de code
            Type<P15> p15, Function<R, P15> g15, Type<P16> p16, Function<R, P16> g16,
            // Instruction de code
            F16<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, Type<P12> p12, Function<R, P12> g12,
            // Instruction de code
            Type<P13> p13, Function<R, P13> g13, Type<P14> p14, Function<R, P14> g14,
            // Instruction de code
            Type<P15> p15, Function<R, P15> g15, Type<P16> p16, Function<R, P16> g16,
            // Instruction de code
            Type<P17> p17, Function<R, P17> g17,
            // Instruction de code
            F17<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, Type<P12> p12, Function<R, P12> g12,
            // Instruction de code
            Type<P13> p13, Function<R, P13> g13, Type<P14> p14, Function<R, P14> g14,
            // Instruction de code
            Type<P15> p15, Function<R, P15> g15, Type<P16> p16, Function<R, P16> g16,
            // Instruction de code
            Type<P17> p17, Function<R, P17> g17, Type<P18> p18, Function<R, P18> g18,
            // Instruction de code
            F18<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, Type<P12> p12, Function<R, P12> g12,
            // Instruction de code
            Type<P13> p13, Function<R, P13> g13, Type<P14> p14, Function<R, P14> g14,
            // Instruction de code
            Type<P15> p15, Function<R, P15> g15, Type<P16> p16, Function<R, P16> g16,
            // Instruction de code
            Type<P17> p17, Function<R, P17> g17, Type<P18> p18, Function<R, P18> g18,
            // Instruction de code
            Type<P19> p19, Function<R, P19> g19, F19<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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

    // Instruction de code
    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, R> Type<R> template(
            // Instruction de code
            Type<P1> p1, Function<R, P1> g1, Type<P2> p2, Function<R, P2> g2,
            // Instruction de code
            Type<P3> p3, Function<R, P3> g3, Type<P4> p4, Function<R, P4> g4,
            // Instruction de code
            Type<P5> p5, Function<R, P5> g5, Type<P6> p6, Function<R, P6> g6,
            // Instruction de code
            Type<P7> p7, Function<R, P7> g7, Type<P8> p8, Function<R, P8> g8,
            // Instruction de code
            Type<P9> p9, Function<R, P9> g9, Type<P10> p10, Function<R, P10> g10,
            // Instruction de code
            Type<P11> p11, Function<R, P11> g11, Type<P12> p12, Function<R, P12> g12,
            // Instruction de code
            Type<P13> p13, Function<R, P13> g13, Type<P14> p14, Function<R, P14> g14,
            // Instruction de code
            Type<P15> p15, Function<R, P15> g15, Type<P16> p16, Function<R, P16> g16,
            // Instruction de code
            Type<P17> p17, Function<R, P17> g17, Type<P18> p18, Function<R, P18> g18,
            // Instruction de code
            Type<P19> p19, Function<R, P19> g19, Type<P20> p20, Function<R, P20> g20,
            // Instruction de code
            F20<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, R> reader
    // Début d'une méthode/d'un bloc
    ) {
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
                return reader.apply(
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
