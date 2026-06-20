// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class MetadataHolderTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void changesListenerCalledOnSet() {
        // Appelle une méthode
        List<Map<Integer, Metadata.Entry<?>>> received = new ArrayList<>();
        // Appelle une méthode
        MetadataHolder holder = new MetadataHolder(received::add);

        // Appelle une méthode
        holder.set(MetadataDef.CUSTOM_NAME_VISIBLE, true);

        // Appelle une méthode
        assertEquals(1, received.size());
        // Appelle une méthode
        Map<Integer, Metadata.Entry<?>> changes = received.getFirst();
        // Appelle une méthode
        assertEquals(1, changes.size());
        // Appelle une méthode
        assertEquals(true, changes.get(MetadataDef.CUSTOM_NAME_VISIBLE.index()).value());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void changesListenerBatchedWhenNotifyDisabled() {
        // Appelle une méthode
        List<Map<Integer, Metadata.Entry<?>>> received = new ArrayList<>();
        // Appelle une méthode
        MetadataHolder holder = new MetadataHolder(received::add);

        // Appelle une méthode
        holder.setNotifyAboutChanges(false);
        // Appelle une méthode
        holder.set(MetadataDef.CUSTOM_NAME_VISIBLE, true);
        // Appelle une méthode
        holder.set(MetadataDef.AIR_TICKS, 42);
        // Appelle une méthode
        assertTrue(received.isEmpty(), "Listener should not be called while notification is disabled");

        // Appelle une méthode
        holder.setNotifyAboutChanges(true);

        // Appelle une méthode
        assertEquals(1, received.size());
        // Appelle une méthode
        Map<Integer, Metadata.Entry<?>> changes = received.getFirst();
        // Appelle une méthode
        assertEquals(2, changes.size());
        // Appelle une méthode
        assertEquals(true, changes.get(MetadataDef.CUSTOM_NAME_VISIBLE.index()).value());
        // Appelle une méthode
        assertEquals(42, changes.get(MetadataDef.AIR_TICKS.index()).value());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void changesListenerNotCalledWhenNothingBatched() {
        // Appelle une méthode
        List<Map<Integer, Metadata.Entry<?>>> received = new ArrayList<>();
        // Appelle une méthode
        MetadataHolder holder = new MetadataHolder(received::add);

        // Appelle une méthode
        holder.setNotifyAboutChanges(false);
        // Appelle une méthode
        holder.setNotifyAboutChanges(true);

        // Appelle une méthode
        assertTrue(received.isEmpty());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings({"ConstantConditions", "removal"})
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testNullCtor() {
        // Appelle une méthode
        assertDoesNotThrow(() -> new MetadataHolder((Entity) null));
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> new MetadataHolder((Consumer<Map<Integer, Metadata.Entry<?>>>) null));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
