// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityEvent;
// Import of a required class
import net.minestom.server.event.trait.ItemEvent;
// Import of a required class
import net.minestom.server.event.trait.RecursiveEvent;
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
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Static import of a member
import static net.minestom.testing.TestUtils.waitUntilCleared;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class EventNodeTest {

    // Type declaration (class/interface/enum/record)
    static class EventTest implements Event {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static class CancellableTest implements CancellableEvent {
        // Assigns a value
        private boolean cancelled = false;

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean isCancelled() {
            // Returns a value to the caller
            return cancelled;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void setCancelled(boolean cancel) {
            // Access to the current/parent object
            this.cancelled = cancel;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static class Recursive1 implements RecursiveEvent {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static class Recursive2 extends Recursive1 {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ItemTestEvent(ItemStack item) implements ItemEvent {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public ItemStack getItemStack() {
            // Returns a value to the caller
            return item;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record EntityTestEvent(Entity entity) implements EntityEvent {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public Entity getEntity() {
            // Returns a value to the caller
            return entity;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCall() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Calls a method
        var listener = EventListener.of(EventTest.class, eventTest -> result.set(true));
        // Calls a method
        node.addListener(listener);
        // Calls a method
        assertFalse(result.get(), "The event should not be called before the call");
        // Calls a method
        node.call(new EventTest());
        // Calls a method
        assertTrue(result.get(), "The event should be called after the call");

        // Test removal
        // Calls a method
        result.set(false);
        // Calls a method
        node.removeListener(listener);
        // Calls a method
        node.call(new EventTest());
        // Calls a method
        assertFalse(result.get(), "The event should not be called after the removal");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testHandle() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        var handle = node.getHandle(EventTest.class);
        // Calls a method
        assertSame(handle, node.getHandle(EventTest.class));

        // Calls a method
        var handle1 = node.getHandle(CancellableTest.class);
        // Calls a method
        assertSame(handle1, node.getHandle(CancellableTest.class));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCancellable() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Assigns a value
        var listener = EventListener.builder(CancellableTest.class)
                // Start of a method/block
                .handler(event -> {
                    // Calls a method
                    event.setCancelled(true);
                    // Calls a method
                    result.set(true);
                    // Calls a method
                    assertTrue(event.isCancelled(), "The event should be cancelled");
                // Calls a method
                }).build();
        // Calls a method
        node.addListener(listener);
        // Calls a method
        node.call(new CancellableTest());
        // Calls a method
        assertTrue(result.get(), "The event should be called after the call");

        // Test cancelling
        // Calls a method
        node.addListener(CancellableTest.class, event -> fail("The event must have been cancelled"));
        // Calls a method
        node.call(new CancellableTest());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void recursiveSub() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        AtomicBoolean result1 = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean result2 = new AtomicBoolean(false);
        // Calls a method
        var listener1 = EventListener.of(Recursive1.class, event -> result1.set(true));
        // Calls a method
        var listener2 = EventListener.of(Recursive2.class, event -> result2.set(true));
        // Calls a method
        node.addListener(listener1);
        // Calls a method
        node.addListener(listener2);
        // Calls a method
        node.call(new Recursive2());
        // Calls a method
        assertTrue(result2.get(), "Recursive2 should have been called directly");
        // Calls a method
        assertTrue(result1.get(), "Recursive1 should be called due to the RecursiveEvent interface");

        // Remove the direct listener
        // Calls a method
        result1.set(false);
        // Calls a method
        result2.set(false);
        // Calls a method
        node.removeListener(listener2);
        // Calls a method
        node.call(new Recursive2());
        // Calls a method
        assertFalse(result2.get(), "There is no listener for Recursive2");
        // Calls a method
        assertTrue(result1.get(), "Recursive1 should be called due to the RecursiveEvent interface");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRecursiveChild() {
        // Calls a method
        var called1 = new AtomicBoolean(false);
        // Calls a method
        var called2 = new AtomicBoolean(false);
        // Calls a method
        var child1 = EventNode.all("child1");
        // Calls a method
        var child2 = EventNode.all("child2");
        // Calls a method
        child1.addListener(Recursive1.class, event -> called1.set(true));
        // Calls a method
        child2.addListener(Recursive1.class, event -> called2.set(true));

        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        node.addChild(child1);

        // Calls a method
        node.call(new Recursive2());

        // Calls a method
        assertTrue(called1.get());
        // Calls a method
        assertFalse(called2.get());
        // Calls a method
        called1.set(false);

        // Calls a method
        node.removeChild(child1);
        // Calls a method
        node.addChild(child2);

        // Calls a method
        node.call(new Recursive2());

        // Calls a method
        assertFalse(called1.get());
        // Calls a method
        assertTrue(called2.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void recursiveSuper() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        AtomicBoolean result2 = new AtomicBoolean(false);
        // Calls a method
        var listener2 = EventListener.of(Recursive2.class, event -> result2.set(true));
        // Calls a method
        node.addListener(listener2);
        // Calls a method
        node.call(new Recursive2());
        // Calls a method
        assertTrue(result2.get(), "The event should be called after the call");

        // Calls a method
        AtomicBoolean result1 = new AtomicBoolean(false);
        // Calls a method
        var listener1 = EventListener.of(Recursive1.class, event -> result1.set(true));
        // Calls a method
        node.addListener(listener1);
        // Calls a method
        result2.set(false);
        // Calls a method
        node.call(new Recursive2());
        // Calls a method
        assertTrue(result2.get(), "Recursive2 should have been called directly");
        // Calls a method
        assertTrue(result1.get(), "Recursive1 should be called due to the RecursiveEvent interface");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testChildren() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        AtomicInteger result = new AtomicInteger(0);
        // Assigns a value
        var child1 = EventNode.all("child1").setPriority(1)
                // Start of a method/block
                .addListener(EventTest.class, eventTest -> {
                    // Calls a method
                    assertEquals(0, result.get(), "child1 should be called before child2");
                    // Calls a method
                    result.set(1);
                // End of a block/expression
                });
        // Assigns a value
        var child2 = EventNode.all("child2").setPriority(2)
                // Start of a method/block
                .addListener(EventTest.class, eventTest -> {
                    // Calls a method
                    assertEquals(1, result.get(), "child2 should be called after child1");
                    // Calls a method
                    result.set(2);
                // End of a block/expression
                });
        // Calls a method
        node.addChild(child1);
        // Calls a method
        node.addChild(child2);
        // Calls a method
        assertEquals(2, node.getChildren().size(), "The node should have 2 children");
        // Calls a method
        node.call(new EventTest());
        // Calls a method
        assertEquals(2, result.get(), "The event should be called after the call");

        // Test removal
        // Calls a method
        result.set(0);
        // Calls a method
        node.removeChild(child2);
        // Calls a method
        assertEquals(1, node.getChildren().size(), "The node should have 1 child");
        // Calls a method
        node.call(new EventTest());
        // Calls a method
        assertEquals(1, result.get(), "child2 should has been removed");

        // Calls a method
        result.set(0);
        // Calls a method
        node.removeChild(child1);
        // Calls a method
        node.call(new EventTest());
        // Calls a method
        assertTrue(node.getChildren().isEmpty(), "The node should have no child left");
        // Calls a method
        assertEquals(0, result.get(), "The event should not be called after the removal");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testFiltering() {
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean childResult = new AtomicBoolean(false);

        // Assigns a value
        var node = EventNode.type("item_node", EventFilter.ITEM,
                // Calls a method
                (event, item) -> item.material() == Material.DIAMOND);
        // Assigns a value
        var child = EventNode.type("item_node2", EventFilter.ITEM)
                // Calls a method
                .addListener(ItemTestEvent.class, event -> childResult.set(true));
        // Calls a method
        node.addChild(child);

        // Calls a method
        var listener = EventListener.of(ItemTestEvent.class, event -> fail("The event should not be called"));
        // Calls a method
        node.addListener(listener);
        // Calls a method
        node.call(new ItemTestEvent(ItemStack.of(Material.GOLD_BLOCK)));
        // Calls a method
        assertFalse(childResult.get());

        // Calls a method
        node.removeListener(listener);
        // Calls a method
        listener = EventListener.of(ItemTestEvent.class, event -> result.set(true));
        // Calls a method
        node.addListener(listener);
        // Calls a method
        node.call(new ItemTestEvent(ItemStack.of(Material.DIAMOND)));
        // Calls a method
        assertTrue(result.get(), "The event should be called");
        // Calls a method
        assertTrue(childResult.get(), "The child event should be called");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBinding() {
        // Calls a method
        var node = EventNode.all("main");

        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Assigns a value
        var binding = EventBinding.filtered(EventFilter.ITEM, itemStack -> itemStack.material() == Material.DIAMOND)
                // Code statement
                .map(ItemTestEvent.class, (itemStack, itemTestEvent) -> result.set(true))
                // Calls a method
                .build();
        // Calls a method
        node.register(binding);
        // Calls a method
        node.call(new ItemTestEvent(ItemStack.of(Material.GOLD_BLOCK)));
        // Calls a method
        assertFalse(result.get());

        // Calls a method
        result.set(false);
        // Calls a method
        node.call(new ItemTestEvent(ItemStack.of(Material.DIAMOND)));
        // Calls a method
        assertTrue(result.get());

        // Calls a method
        result.set(false);
        // Calls a method
        node.unregister(binding);
        // Calls a method
        node.call(new ItemTestEvent(ItemStack.of(Material.DIAMOND)));
        // Calls a method
        assertFalse(result.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nodeEmptyGC() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        var ref = new WeakReference<>(node);

        //noinspection UnusedAssignment
        // Assigns a value
        node = null;
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nodeGC() {
        // Calls a method
        var node = EventNode.all("main");
        // Calls a method
        var ref = new WeakReference<>(node);
        // Start of a method/block
        node.addListener(EventTest.class, event -> {
        // End of a block/expression
        });

        //noinspection UnusedAssignment
        // Assigns a value
        node = null;
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }

//    @Test
//    public void nodeChildGC() {
//        var node = EventNode.all("main");
//
//        var child = EventNode.all("child");
//        var ref = new WeakReference<>(child);
//        child.addListener(EventTest.class, event -> {
//        });
//        node.addChild(child);
//
//        //noinspection UnusedAssignment
//        child = null;
//        waitUntilCleared(ref);
//    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void nodeMapGC() {
        // Calls a method
        var node = EventNode.all("main");

        // Assigns a value
        var handler = ItemStack.AIR;
        // Calls a method
        var mapped = node.map(handler, EventFilter.ITEM);
        // Calls a method
        var ref = new WeakReference<>(mapped);
        // Start of a method/block
        mapped.addListener(ItemTestEvent.class, event -> {
        // End of a block/expression
        });

        //noinspection UnusedAssignment
        // Assigns a value
        mapped = null;
        // Calls a method
        waitUntilCleared(ref);
    // End of a block/expression
    }
// End of a block/expression
}
