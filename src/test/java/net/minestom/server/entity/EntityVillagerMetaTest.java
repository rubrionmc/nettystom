// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class EntityVillagerMetaTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    void levelNetworkSerialization() {
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.builder(5).build();
        // Calls a method
        VillagerMeta.Level.NETWORK_TYPE.write(buffer, VillagerMeta.Level.NOVICE);

        // Assigns a value
        int expected = VillagerMeta.Level.NOVICE.ordinal() + 1;  // Network representation is ordinal + 1
        // Calls a method
        int readValue = buffer.read(NetworkBuffer.VAR_INT);
        // Calls a method
        assertEquals(expected, readValue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void levelNetworkDeserialization() {
        // Assigns a value
        int networkValue = VillagerMeta.Level.NOVICE.ordinal() + 1;  // Simulate network value for NOVICE
        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.builder(5).build();
        // Calls a method
        buffer.write(NetworkBuffer.VAR_INT, networkValue);

        // Calls a method
        VillagerMeta.Level level = VillagerMeta.Level.NETWORK_TYPE.read(buffer);
        // Calls a method
        assertEquals(VillagerMeta.Level.NOVICE, level);
    // End of a block/expression
    }
// End of a block/expression
}
