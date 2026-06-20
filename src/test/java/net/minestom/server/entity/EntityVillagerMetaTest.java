// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class EntityVillagerMetaTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void levelNetworkSerialization() {
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.builder(5).build();
        // Appelle une méthode
        VillagerMeta.Level.NETWORK_TYPE.write(buffer, VillagerMeta.Level.NOVICE);

        // Affecte une valeur
        int expected = VillagerMeta.Level.NOVICE.ordinal() + 1;  // Network representation is ordinal + 1
        // Appelle une méthode
        int readValue = buffer.read(NetworkBuffer.VAR_INT);
        // Appelle une méthode
        assertEquals(expected, readValue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void levelNetworkDeserialization() {
        // Affecte une valeur
        int networkValue = VillagerMeta.Level.NOVICE.ordinal() + 1;  // Simulate network value for NOVICE
        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.builder(5).build();
        // Appelle une méthode
        buffer.write(NetworkBuffer.VAR_INT, networkValue);

        // Appelle une méthode
        VillagerMeta.Level level = VillagerMeta.Level.NETWORK_TYPE.read(buffer);
        // Appelle une méthode
        assertEquals(VillagerMeta.Level.NOVICE, level);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
