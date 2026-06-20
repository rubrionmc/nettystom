// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Metadata;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
class FrogMetaTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSetVariant() {
        // Appelle une méthode
        Entity frog = new Entity(EntityType.FROG);
        // Appelle une méthode
        frog.set(DataComponents.FROG_VARIANT, FrogVariant.COLD);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSerializeVariant() {
        // Variant is serialized as meta as of 1.21.6
        // Appelle une méthode
        Entity frog = new Entity(EntityType.FROG);
        // Appelle une méthode
        frog.set(DataComponents.FROG_VARIANT, FrogVariant.COLD);
        // Affecte une valeur
        boolean found = false;
        // Boucle : répète un bloc
        for (Map.Entry<Integer, Metadata.Entry<?>> entry : frog.getMetadataPacket().entries().entrySet()) {
            // Embranchement : vérifie une condition
            if (entry.getValue().type() == Metadata.TYPE_FROG_VARIANT) {
                // Appelle une méthode
                Assertions.assertEquals(FrogVariant.COLD, entry.getValue().value());
                // Affecte une valeur
                found = true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        Assertions.assertTrue(found, "Frog variant was not serialized");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}