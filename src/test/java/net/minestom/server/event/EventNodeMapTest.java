// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.lang.ref.WeakReference;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static net.minestom.testing.TestUtils.waitUntilCleared;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class EventNodeMapTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void uniqueMapping() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        var itemNode1 = node.map(item, EventFilter.ITEM);
        // Calls a method
        var itemNode2 = node.map(item, EventFilter.ITEM);
        // Calls a method
        assertNotNull(itemNode1);
        // Calls a method
        assertSame(itemNode1, itemNode2);

        // Node should still keep track of the mapping until GCed
        // This is to ensure that we do not end up with multiple nodes theoretically mapping the same object
        // Calls a method
        node.unmap(item);
        // Calls a method
        assertSame(itemNode1, node.map(item, EventFilter.ITEM));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lazyRegistration() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var node = (EventNodeImpl<Event>) EventNode.all("main");
        // Calls a method
        var itemNode = node.map(item, EventFilter.ITEM);
        // Calls a method
        assertFalse(node.registeredMappedNode.containsKey(item));
        // Start of a method/block
        itemNode.addListener(EventNodeTest.ItemTestEvent.class, event -> {
        // End of a block/expression
        });
        // Calls a method
        assertTrue(node.registeredMappedNode.containsKey(item));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void secondMap() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var node = (EventNodeImpl<Event>) EventNode.all("main");
        // Calls a method
        var itemNode = node.map(item, EventFilter.ITEM);
        // Calls a method
        assertSame(itemNode, itemNode.map(item, EventFilter.ITEM));
        // Calls a method
        assertThrows(Exception.class, () -> itemNode.map(ItemStack.AIR, EventFilter.ITEM));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void map() {
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var node = EventNode.all("main");

        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Calls a method
        var itemNode = node.map(item, EventFilter.ITEM);

        // Calls a method
        assertFalse(node.hasListener(EventNodeTest.ItemTestEvent.class));
        // Calls a method
        itemNode.addListener(EventNodeTest.ItemTestEvent.class, event -> result.set(true));
        // Calls a method
        assertTrue(node.hasListener(EventNodeTest.ItemTestEvent.class));

        // Calls a method
        node.call(new EventNodeTest.ItemTestEvent(item));
        // Calls a method
        assertTrue(result.get());

        // Calls a method
        result.set(false);
        // Calls a method
        node.call(new EventNodeTest.ItemTestEvent(ItemStack.of(Material.GOLD_INGOT)));
        // Calls a method
        assertFalse(result.get());

        // Calls a method
        result.set(false);
        // Calls a method
        node.unmap(item);
        // Calls a method
        node.call(new EventNodeTest.ItemTestEvent(item));
        // Calls a method
        assertFalse(result.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityLocal() {
        // Calls a method
        var process = MinecraftServer.updateProcess();
        // Calls a method
        var node = process.eventHandler();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);

        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Calls a method
        var listener = EventListener.of(EventNodeTest.EntityTestEvent.class, event -> result.set(true));

        // Calls a method
        var handle = node.getHandle(EventNodeTest.EntityTestEvent.class);
        // Calls a method
        assertFalse(handle.hasListener());
        // Calls a method
        entity.eventNode().addListener(listener);
        // Calls a method
        assertTrue(handle.hasListener());

        // Calls a method
        assertFalse(result.get());

        // Calls a method
        handle.call(new EventNodeTest.EntityTestEvent(entity));
        // Calls a method
        assertTrue(result.get());

        // Calls a method
        result.set(false);
        // Calls a method
        entity.eventNode().removeListener(listener);

        // Calls a method
        handle.call(new EventNodeTest.EntityTestEvent(entity));
        // Calls a method
        assertFalse(result.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void ownerGC() {
        // Ensure that the mapped object gets GCed
        // Calls a method
        var item = ItemStack.of(Material.DIAMOND);
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        var itemNode = node.map(item, EventFilter.ITEM);
        // Start of a method/block
        itemNode.addListener(EventNodeTest.ItemTestEvent.class, event -> {
        // End of a block/expression
        });
        // Calls a method
        node.call(new EventNodeTest.ItemTestEvent(item));

        // Calls a method
        var ref = new WeakReference<>(item);
        //noinspection UnusedAssignment
        // Assigns a value
        item = null;
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }
// End of a block/expression
}
