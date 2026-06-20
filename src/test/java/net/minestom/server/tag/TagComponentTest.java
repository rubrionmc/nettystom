// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Déclaration de type (classe/interface/enum/record)
public class TagComponentTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void get() {
        // Appelle une méthode
        var component = Component.text("Hey");
        // Appelle une méthode
        var tag = Tag.Component("component");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, component);
        // Appelle une méthode
        assertEquals(component, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Appelle une méthode
        var tag = Tag.Component("component");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidTag() {
        // Appelle une méthode
        var tag = Tag.Component("entry");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(Tag.Integer("entry"), 1);
        // Appelle une méthode
        assertNull(handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nbtFallback() {
        // Appelle une méthode
        var component = Component.text("Hey");
        // Appelle une méthode
        var tag = Tag.Component("component");
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        handler.setTag(tag, component);
        // Appelle une méthode
        handler = TagHandler.fromCompound(handler.asCompound());
        // Appelle une méthode
        assertEquals(component, handler.getTag(tag));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
