// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Déclaration de type (classe/interface/enum/record)
public class EntityMetaTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void ensureRegistration() throws IllegalAccessException {
        // Affecte une valeur
        List<String> list = new ArrayList<>();
        // Boucle : répète un bloc
        for (var field : EntityTypes.class.getDeclaredFields()) {
            // Appelle une méthode
            final EntityType entityType = (EntityType) field.get(this);
            // Appelle une méthode
            final String name = entityType.name();
            // Embranchement : vérifie une condition
            if (MetadataHolder.ENTITY_META_SUPPLIER.get(name) == null) {
                // Appelle une méthode
                list.add(name);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertTrue(list.isEmpty(), "Missing meta for: " + list);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
