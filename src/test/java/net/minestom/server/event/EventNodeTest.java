// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.ItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.RecursiveEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.lang.ref.WeakReference;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.waitUntilCleared;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class EventNodeTest {

    // Déclaration de type (classe/interface/enum/record)
    static class EventTest implements Event {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static class CancellableTest implements CancellableEvent {
        // Affecte une valeur
        private boolean cancelled = false;

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean isCancelled() {
            // Renvoie une valeur à l'appelant
            return cancelled;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void setCancelled(boolean cancel) {
            // Accès à l'objet courant/parent
            this.cancelled = cancel;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static class Recursive1 implements RecursiveEvent {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static class Recursive2 extends Recursive1 {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ItemTestEvent(ItemStack item) implements ItemEvent {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ItemStack getItemStack() {
            // Renvoie une valeur à l'appelant
            return item;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record EntityTestEvent(Entity entity) implements EntityEvent {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Entity getEntity() {
            // Renvoie une valeur à l'appelant
            return entity;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCall() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Appelle une méthode
        var listener = EventListener.of(EventTest.class, eventTest -> result.set(true));
        // Appelle une méthode
        node.addListener(listener);
        // Appelle une méthode
        assertFalse(result.get(), "The event should not be called before the call");
        // Appelle une méthode
        node.call(new EventTest());
        // Appelle une méthode
        assertTrue(result.get(), "The event should be called after the call");

        // Test removal
        // Appelle une méthode
        result.set(false);
        // Appelle une méthode
        node.removeListener(listener);
        // Appelle une méthode
        node.call(new EventTest());
        // Appelle une méthode
        assertFalse(result.get(), "The event should not be called after the removal");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testHandle() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        var handle = node.getHandle(EventTest.class);
        // Appelle une méthode
        assertSame(handle, node.getHandle(EventTest.class));

        // Appelle une méthode
        var handle1 = node.getHandle(CancellableTest.class);
        // Appelle une méthode
        assertSame(handle1, node.getHandle(CancellableTest.class));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCancellable() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Affecte une valeur
        var listener = EventListener.builder(CancellableTest.class)
                // Début d'une méthode/d'un bloc
                .handler(event -> {
                    // Appelle une méthode
                    event.setCancelled(true);
                    // Appelle une méthode
                    result.set(true);
                    // Appelle une méthode
                    assertTrue(event.isCancelled(), "The event should be cancelled");
                // Appelle une méthode
                }).build();
        // Appelle une méthode
        node.addListener(listener);
        // Appelle une méthode
        node.call(new CancellableTest());
        // Appelle une méthode
        assertTrue(result.get(), "The event should be called after the call");

        // Test cancelling
        // Appelle une méthode
        node.addListener(CancellableTest.class, event -> fail("The event must have been cancelled"));
        // Appelle une méthode
        node.call(new CancellableTest());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void recursiveSub() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        AtomicBoolean result1 = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean result2 = new AtomicBoolean(false);
        // Appelle une méthode
        var listener1 = EventListener.of(Recursive1.class, event -> result1.set(true));
        // Appelle une méthode
        var listener2 = EventListener.of(Recursive2.class, event -> result2.set(true));
        // Appelle une méthode
        node.addListener(listener1);
        // Appelle une méthode
        node.addListener(listener2);
        // Appelle une méthode
        node.call(new Recursive2());
        // Appelle une méthode
        assertTrue(result2.get(), "Recursive2 should have been called directly");
        // Appelle une méthode
        assertTrue(result1.get(), "Recursive1 should be called due to the RecursiveEvent interface");

        // Remove the direct listener
        // Appelle une méthode
        result1.set(false);
        // Appelle une méthode
        result2.set(false);
        // Appelle une méthode
        node.removeListener(listener2);
        // Appelle une méthode
        node.call(new Recursive2());
        // Appelle une méthode
        assertFalse(result2.get(), "There is no listener for Recursive2");
        // Appelle une méthode
        assertTrue(result1.get(), "Recursive1 should be called due to the RecursiveEvent interface");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRecursiveChild() {
        // Appelle une méthode
        var called1 = new AtomicBoolean(false);
        // Appelle une méthode
        var called2 = new AtomicBoolean(false);
        // Appelle une méthode
        var child1 = EventNode.all("child1");
        // Appelle une méthode
        var child2 = EventNode.all("child2");
        // Appelle une méthode
        child1.addListener(Recursive1.class, event -> called1.set(true));
        // Appelle une méthode
        child2.addListener(Recursive1.class, event -> called2.set(true));

        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        node.addChild(child1);

        // Appelle une méthode
        node.call(new Recursive2());

        // Appelle une méthode
        assertTrue(called1.get());
        // Appelle une méthode
        assertFalse(called2.get());
        // Appelle une méthode
        called1.set(false);

        // Appelle une méthode
        node.removeChild(child1);
        // Appelle une méthode
        node.addChild(child2);

        // Appelle une méthode
        node.call(new Recursive2());

        // Appelle une méthode
        assertFalse(called1.get());
        // Appelle une méthode
        assertTrue(called2.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void recursiveSuper() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        AtomicBoolean result2 = new AtomicBoolean(false);
        // Appelle une méthode
        var listener2 = EventListener.of(Recursive2.class, event -> result2.set(true));
        // Appelle une méthode
        node.addListener(listener2);
        // Appelle une méthode
        node.call(new Recursive2());
        // Appelle une méthode
        assertTrue(result2.get(), "The event should be called after the call");

        // Appelle une méthode
        AtomicBoolean result1 = new AtomicBoolean(false);
        // Appelle une méthode
        var listener1 = EventListener.of(Recursive1.class, event -> result1.set(true));
        // Appelle une méthode
        node.addListener(listener1);
        // Appelle une méthode
        result2.set(false);
        // Appelle une méthode
        node.call(new Recursive2());
        // Appelle une méthode
        assertTrue(result2.get(), "Recursive2 should have been called directly");
        // Appelle une méthode
        assertTrue(result1.get(), "Recursive1 should be called due to the RecursiveEvent interface");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testChildren() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        AtomicInteger result = new AtomicInteger(0);
        // Affecte une valeur
        var child1 = EventNode.all("child1").setPriority(1)
                // Début d'une méthode/d'un bloc
                .addListener(EventTest.class, eventTest -> {
                    // Appelle une méthode
                    assertEquals(0, result.get(), "child1 should be called before child2");
                    // Appelle une méthode
                    result.set(1);
                // Fin d'un bloc/d'une expression
                });
        // Affecte une valeur
        var child2 = EventNode.all("child2").setPriority(2)
                // Début d'une méthode/d'un bloc
                .addListener(EventTest.class, eventTest -> {
                    // Appelle une méthode
                    assertEquals(1, result.get(), "child2 should be called after child1");
                    // Appelle une méthode
                    result.set(2);
                // Fin d'un bloc/d'une expression
                });
        // Appelle une méthode
        node.addChild(child1);
        // Appelle une méthode
        node.addChild(child2);
        // Appelle une méthode
        assertEquals(2, node.getChildren().size(), "The node should have 2 children");
        // Appelle une méthode
        node.call(new EventTest());
        // Appelle une méthode
        assertEquals(2, result.get(), "The event should be called after the call");

        // Test removal
        // Appelle une méthode
        result.set(0);
        // Appelle une méthode
        node.removeChild(child2);
        // Appelle une méthode
        assertEquals(1, node.getChildren().size(), "The node should have 1 child");
        // Appelle une méthode
        node.call(new EventTest());
        // Appelle une méthode
        assertEquals(1, result.get(), "child2 should has been removed");

        // Appelle une méthode
        result.set(0);
        // Appelle une méthode
        node.removeChild(child1);
        // Appelle une méthode
        node.call(new EventTest());
        // Appelle une méthode
        assertTrue(node.getChildren().isEmpty(), "The node should have no child left");
        // Appelle une méthode
        assertEquals(0, result.get(), "The event should not be called after the removal");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testFiltering() {
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean childResult = new AtomicBoolean(false);

        // Affecte une valeur
        var node = EventNode.type("item_node", EventFilter.ITEM,
                // Appelle une méthode
                (event, item) -> item.material() == Material.DIAMOND);
        // Affecte une valeur
        var child = EventNode.type("item_node2", EventFilter.ITEM)
                // Appelle une méthode
                .addListener(ItemTestEvent.class, event -> childResult.set(true));
        // Appelle une méthode
        node.addChild(child);

        // Appelle une méthode
        var listener = EventListener.of(ItemTestEvent.class, event -> fail("The event should not be called"));
        // Appelle une méthode
        node.addListener(listener);
        // Appelle une méthode
        node.call(new ItemTestEvent(ItemStack.of(Material.GOLD_BLOCK)));
        // Appelle une méthode
        assertFalse(childResult.get());

        // Appelle une méthode
        node.removeListener(listener);
        // Appelle une méthode
        listener = EventListener.of(ItemTestEvent.class, event -> result.set(true));
        // Appelle une méthode
        node.addListener(listener);
        // Appelle une méthode
        node.call(new ItemTestEvent(ItemStack.of(Material.DIAMOND)));
        // Appelle une méthode
        assertTrue(result.get(), "The event should be called");
        // Appelle une méthode
        assertTrue(childResult.get(), "The child event should be called");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBinding() {
        // Appelle une méthode
        var node = EventNode.all("main");

        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Affecte une valeur
        var binding = EventBinding.filtered(EventFilter.ITEM, itemStack -> itemStack.material() == Material.DIAMOND)
                // Instruction de code
                .map(ItemTestEvent.class, (itemStack, itemTestEvent) -> result.set(true))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        node.register(binding);
        // Appelle une méthode
        node.call(new ItemTestEvent(ItemStack.of(Material.GOLD_BLOCK)));
        // Appelle une méthode
        assertFalse(result.get());

        // Appelle une méthode
        result.set(false);
        // Appelle une méthode
        node.call(new ItemTestEvent(ItemStack.of(Material.DIAMOND)));
        // Appelle une méthode
        assertTrue(result.get());

        // Appelle une méthode
        result.set(false);
        // Appelle une méthode
        node.unregister(binding);
        // Appelle une méthode
        node.call(new ItemTestEvent(ItemStack.of(Material.DIAMOND)));
        // Appelle une méthode
        assertFalse(result.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nodeEmptyGC() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        var ref = new WeakReference<>(node);

        //noinspection UnusedAssignment
        // Affecte une valeur
        node = null;
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nodeGC() {
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        var ref = new WeakReference<>(node);
        // Début d'une méthode/d'un bloc
        node.addListener(EventTest.class, event -> {
        // Fin d'un bloc/d'une expression
        });

        //noinspection UnusedAssignment
        // Affecte une valeur
        node = null;
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
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

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nodeMapGC() {
        // Appelle une méthode
        var node = EventNode.all("main");

        // Affecte une valeur
        var handler = ItemStack.AIR;
        // Appelle une méthode
        var mapped = node.map(handler, EventFilter.ITEM);
        // Appelle une méthode
        var ref = new WeakReference<>(mapped);
        // Début d'une méthode/d'un bloc
        mapped.addListener(ItemTestEvent.class, event -> {
        // Fin d'un bloc/d'une expression
        });

        //noinspection UnusedAssignment
        // Affecte une valeur
        mapped = null;
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
