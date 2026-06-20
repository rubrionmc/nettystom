// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.Consumer;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class MetadataHolderTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void changesListenerCalledOnSet() {
        // Calls a method
        List<Map<Integer, Metadata.Entry<?>>> received = new ArrayList<>();
        // Calls a method
        MetadataHolder holder = new MetadataHolder(received::add);

        // Calls a method
        holder.set(MetadataDef.CUSTOM_NAME_VISIBLE, true);

        // Calls a method
        assertEquals(1, received.size());
        // Calls a method
        Map<Integer, Metadata.Entry<?>> changes = received.getFirst();
        // Calls a method
        assertEquals(1, changes.size());
        // Calls a method
        assertEquals(true, changes.get(MetadataDef.CUSTOM_NAME_VISIBLE.index()).value());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void changesListenerBatchedWhenNotifyDisabled() {
        // Calls a method
        List<Map<Integer, Metadata.Entry<?>>> received = new ArrayList<>();
        // Calls a method
        MetadataHolder holder = new MetadataHolder(received::add);

        // Calls a method
        holder.setNotifyAboutChanges(false);
        // Calls a method
        holder.set(MetadataDef.CUSTOM_NAME_VISIBLE, true);
        // Calls a method
        holder.set(MetadataDef.AIR_TICKS, 42);
        // Calls a method
        assertTrue(received.isEmpty(), "Listener should not be called while notification is disabled");

        // Calls a method
        holder.setNotifyAboutChanges(true);

        // Calls a method
        assertEquals(1, received.size());
        // Calls a method
        Map<Integer, Metadata.Entry<?>> changes = received.getFirst();
        // Calls a method
        assertEquals(2, changes.size());
        // Calls a method
        assertEquals(true, changes.get(MetadataDef.CUSTOM_NAME_VISIBLE.index()).value());
        // Calls a method
        assertEquals(42, changes.get(MetadataDef.AIR_TICKS.index()).value());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void changesListenerNotCalledWhenNothingBatched() {
        // Calls a method
        List<Map<Integer, Metadata.Entry<?>>> received = new ArrayList<>();
        // Calls a method
        MetadataHolder holder = new MetadataHolder(received::add);

        // Calls a method
        holder.setNotifyAboutChanges(false);
        // Calls a method
        holder.setNotifyAboutChanges(true);

        // Calls a method
        assertTrue(received.isEmpty());
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings({"ConstantConditions", "removal"})
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testNullCtor() {
        // Calls a method
        assertDoesNotThrow(() -> new MetadataHolder((Entity) null));
        // Calls a method
        assertThrows(NullPointerException.class, () -> new MetadataHolder((Consumer<Map<Integer, Metadata.Entry<?>>>) null));
    // End of a block/expression
    }
// End of a block/expression
}
