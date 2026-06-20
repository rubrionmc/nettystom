// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import net.minestom.server.event.trait.EntityEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerEvent;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsIgnoreOrder;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class EventNodeQueryTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void find() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        assertEquals(List.of(), node.findChildren("test"));

        // Calls a method
        var child1 = EventNode.all("test");
        // Calls a method
        var child2 = EventNode.all("test");
        // Calls a method
        var child3 = EventNode.all("test3");

        // Calls a method
        node.addChild(child1);
        // Calls a method
        node.addChild(child2);
        // Calls a method
        node.addChild(child3);

        // Calls a method
        assertEqualsIgnoreOrder(List.of(child1, child2), node.findChildren("test"));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child3), node.findChildren("test3"));

        // Calls a method
        node.removeChild(child1);
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test"));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child3), node.findChildren("test3"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void findType() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        assertEquals(List.of(), node.findChildren("test", Event.class));

        // Calls a method
        var child1 = EventNode.type("test", EventFilter.PLAYER);
        // Calls a method
        var child2 = EventNode.type("test", EventFilter.ENTITY);
        // Calls a method
        var child3 = EventNode.type("test3", EventFilter.ENTITY);

        // Calls a method
        node.addChild(child1);
        // Calls a method
        node.addChild(child2);
        // Calls a method
        node.addChild(child3);

        // Calls a method
        assertEqualsIgnoreOrder(List.of(child1, child2), node.findChildren("test", Event.class));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child1, child2), node.findChildren("test", EntityEvent.class));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child1), node.findChildren("test", PlayerEvent.class));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child3), node.findChildren("test3", EntityEvent.class));

        // Calls a method
        node.removeChild(child1);
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test", Event.class));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test", EntityEvent.class));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(), node.findChildren("test", PlayerEvent.class));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child3), node.findChildren("test3", EntityEvent.class));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void replace() {
        // Calls a method
        var node = EventNode.all("main");

        // Calls a method
        var child1 = EventNode.all("test");
        // Calls a method
        var child2 = EventNode.all("test");
        // Calls a method
        var child3 = EventNode.all("test3");

        // Calls a method
        node.addChild(child1);
        // Calls a method
        node.addChild(child2);
        // Calls a method
        node.addChild(child3);

        // Calls a method
        var tmp1 = EventNode.all("tmp1");
        // Calls a method
        var tmp2 = EventNode.all("tmp2");

        // Calls a method
        node.replaceChildren("test", tmp1);
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test"));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(tmp1), node.findChildren("tmp1"));

        // Calls a method
        node.replaceChildren("test3", tmp2);
        // Calls a method
        assertEqualsIgnoreOrder(List.of(child2), node.findChildren("test"));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(tmp1), node.findChildren("tmp1"));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(), node.findChildren("test3"));
        // Calls a method
        assertEqualsIgnoreOrder(List.of(tmp2), node.findChildren("tmp2"));
    // End of a block/expression
    }
// End of a block/expression
}
