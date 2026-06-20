// Package declaration for this file
package net.minestom.testing;

// Import of a required class
import org.junit.jupiter.api.extension.ExtendWith;

// Import of a required class
import java.lang.annotation.ElementType;
// Import of a required class
import java.lang.annotation.Retention;
// Import of a required class
import java.lang.annotation.RetentionPolicy;
// Import of a required class
import java.lang.annotation.Target;

// Annotation for the following element
@ExtendWith(EnvTestExt.class)
// Annotation for the following element
@Retention(RetentionPolicy.RUNTIME)
// Annotation for the following element
@Target(ElementType.TYPE)
// Type declaration (class/interface/enum/record)
public @interface EnvTest {
// End of a block/expression
}
