// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerEvent;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsIgnoreOrder;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class EventNodeQueryTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void find() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        assertEquals(List.of(), node.findChildren("test"));

        // Appelle une méthode
        var child1 = EventNode.all("test");
        // Appelle une méthode
        var child2 = EventNode.all("test");
        // Appelle une méthode
        var child3 = EventNode.all("test3");

        // Appelle une méthode
        node.addChild(child1);
        // Appelle une méthode
        node.addChild(child2);
        // Appelle une méthode
        node.addChild(child3);

        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child1, child2), node.findChildren("test"));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child3), node.findChildren("test3"));

        // Appelle une méthode
        node.removeChild(child1);
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test"));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child3), node.findChildren("test3"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void findType() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        assertEquals(List.of(), node.findChildren("test", Event.class));

        // Appelle une méthode
        var child1 = EventNode.type("test", EventFilter.PLAYER);
        // Appelle une méthode
        var child2 = EventNode.type("test", EventFilter.ENTITY);
        // Appelle une méthode
        var child3 = EventNode.type("test3", EventFilter.ENTITY);

        // Appelle une méthode
        node.addChild(child1);
        // Appelle une méthode
        node.addChild(child2);
        // Appelle une méthode
        node.addChild(child3);

        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child1, child2), node.findChildren("test", Event.class));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child1, child2), node.findChildren("test", EntityEvent.class));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child1), node.findChildren("test", PlayerEvent.class));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child3), node.findChildren("test3", EntityEvent.class));

        // Appelle une méthode
        node.removeChild(child1);
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test", Event.class));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test", EntityEvent.class));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(), node.findChildren("test", PlayerEvent.class));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child3), node.findChildren("test3", EntityEvent.class));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void replace() {
        // Appelle une méthode
        var node = EventNode.all("main");

        // Appelle une méthode
        var child1 = EventNode.all("test");
        // Appelle une méthode
        var child2 = EventNode.all("test");
        // Appelle une méthode
        var child3 = EventNode.all("test3");

        // Appelle une méthode
        node.addChild(child1);
        // Appelle une méthode
        node.addChild(child2);
        // Appelle une méthode
        node.addChild(child3);

        // Appelle une méthode
        var tmp1 = EventNode.all("tmp1");
        // Appelle une méthode
        var tmp2 = EventNode.all("tmp2");

        // Appelle une méthode
        node.replaceChildren("test", tmp1);
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test"));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(tmp1), node.findChildren("tmp1"));

        // Appelle une méthode
        node.replaceChildren("test3", tmp2);
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test"));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(tmp1), node.findChildren("tmp1"));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(), node.findChildren("test3"));
        // Appelle une méthode
        assertEqualsIgnoreOrder(List.of(tmp2), node.findChildren("tmp2"));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
