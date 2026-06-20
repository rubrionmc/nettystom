// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
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

// Import statique d'un membre
import static net.minestom.testing.TestUtils.waitUntilCleared;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class EventNodeMapTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void uniqueMapping() {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        var itemNode1 = node.map(item, EventFilter.ITEM);
        // Appelle une méthode
        var itemNode2 = node.map(item, EventFilter.ITEM);
        // Appelle une méthode
        assertNotNull(itemNode1);
        // Appelle une méthode
        assertSame(itemNode1, itemNode2);

        // Node should still keep track of the mapping until GCed
        // This is to ensure that we do not end up with multiple nodes theoretically mapping the same object
        // Appelle une méthode
        node.unmap(item);
        // Appelle une méthode
        assertSame(itemNode1, node.map(item, EventFilter.ITEM));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lazyRegistration() {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var node = (EventNodeImpl<Event>) EventNode.all("main");
        // Appelle une méthode
        var itemNode = node.map(item, EventFilter.ITEM);
        // Appelle une méthode
        assertFalse(node.registeredMappedNode.containsKey(item));
        // Début d'une méthode/d'un bloc
        itemNode.addListener(EventNodeTest.ItemTestEvent.class, event -> {
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertTrue(node.registeredMappedNode.containsKey(item));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void secondMap() {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var node = (EventNodeImpl<Event>) EventNode.all("main");
        // Appelle une méthode
        var itemNode = node.map(item, EventFilter.ITEM);
        // Appelle une méthode
        assertSame(itemNode, itemNode.map(item, EventFilter.ITEM));
        // Appelle une méthode
        assertThrows(Exception.class, () -> itemNode.map(ItemStack.AIR, EventFilter.ITEM));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void map() {
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var node = EventNode.all("main");

        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Appelle une méthode
        var itemNode = node.map(item, EventFilter.ITEM);

        // Appelle une méthode
        assertFalse(node.hasListener(EventNodeTest.ItemTestEvent.class));
        // Appelle une méthode
        itemNode.addListener(EventNodeTest.ItemTestEvent.class, event -> result.set(true));
        // Appelle une méthode
        assertTrue(node.hasListener(EventNodeTest.ItemTestEvent.class));

        // Appelle une méthode
        node.call(new EventNodeTest.ItemTestEvent(item));
        // Appelle une méthode
        assertTrue(result.get());

        // Appelle une méthode
        result.set(false);
        // Appelle une méthode
        node.call(new EventNodeTest.ItemTestEvent(ItemStack.of(Material.GOLD_INGOT)));
        // Appelle une méthode
        assertFalse(result.get());

        // Appelle une méthode
        result.set(false);
        // Appelle une méthode
        node.unmap(item);
        // Appelle une méthode
        node.call(new EventNodeTest.ItemTestEvent(item));
        // Appelle une méthode
        assertFalse(result.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityLocal() {
        // Appelle une méthode
        var process = MinecraftServer.updateProcess();
        // Appelle une méthode
        var node = process.eventHandler();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Appelle une méthode
        var listener = EventListener.of(EventNodeTest.EntityTestEvent.class, event -> result.set(true));

        // Appelle une méthode
        var handle = node.getHandle(EventNodeTest.EntityTestEvent.class);
        // Appelle une méthode
        assertFalse(handle.hasListener());
        // Appelle une méthode
        entity.eventNode().addListener(listener);
        // Appelle une méthode
        assertTrue(handle.hasListener());

        // Appelle une méthode
        assertFalse(result.get());

        // Appelle une méthode
        handle.call(new EventNodeTest.EntityTestEvent(entity));
        // Appelle une méthode
        assertTrue(result.get());

        // Appelle une méthode
        result.set(false);
        // Appelle une méthode
        entity.eventNode().removeListener(listener);

        // Appelle une méthode
        handle.call(new EventNodeTest.EntityTestEvent(entity));
        // Appelle une méthode
        assertFalse(result.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void ownerGC() {
        // Ensure that the mapped object gets GCed
        // Appelle une méthode
        var item = ItemStack.of(Material.DIAMOND);
        // Appelle une méthode
        var node = EventNode.all("main");
        // Appelle une méthode
        var itemNode = node.map(item, EventFilter.ITEM);
        // Début d'une méthode/d'un bloc
        itemNode.addListener(EventNodeTest.ItemTestEvent.class, event -> {
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        node.call(new EventNodeTest.ItemTestEvent(item));

        // Appelle une méthode
        var ref = new WeakReference<>(item);
        //noinspection UnusedAssignment
        // Affecte une valeur
        item = null;
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
