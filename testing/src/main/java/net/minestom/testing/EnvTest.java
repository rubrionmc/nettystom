// Déclaration du paquet de ce fichier
package net.minestom.testing;

// Import d'une classe nécessaire
import org.junit.jupiter.api.extension.ExtendWith;

// Import d'une classe nécessaire
import java.lang.annotation.ElementType;
// Import d'une classe nécessaire
import java.lang.annotation.Retention;
// Import d'une classe nécessaire
import java.lang.annotation.RetentionPolicy;
// Import d'une classe nécessaire
import java.lang.annotation.Target;

// Annotation pour l'élément suivant
@ExtendWith(EnvTestExt.class)
// Annotation pour l'élément suivant
@Retention(RetentionPolicy.RUNTIME)
// Annotation pour l'élément suivant
@Target(ElementType.TYPE)
// Déclaration de type (classe/interface/enum/record)
public @interface EnvTest {
// Fin d'un bloc/d'une expression
}
