// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import org.junit.jupiter.api.BeforeAll;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.COMPONENT;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.NBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
class ComponentNetworkBufferTypeReadTest {

    // Annotation for the following element
    @BeforeAll
    // Start of a method/block
    static void init() {
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void networkBufferReadsShowEntityWithoutName() {
        // Calls a method
        UUID uuid = UUID.randomUUID();

        // Assigns a value
        CompoundBinaryTag hoverEvent = CompoundBinaryTag.builder()
                // Code statement
                .putString("action", "show_entity")
                // Code statement
                .putString("id", "minecraft:player")
                // Code statement
                .putString("uuid", uuid.toString())
                // Calls a method
                .build();

        // Assigns a value
        CompoundBinaryTag component = CompoundBinaryTag.builder()
                // Code statement
                .putString("type", "text")
                // Code statement
                .putString("text", "hover")
                // Code statement
                .put("hover_event", hoverEvent)
                // Calls a method
                .build();

        // Assigns a value
        Component expected = Component.text("hover")
                // Code statement
                .hoverEvent(HoverEvent.showEntity(
                        // Code statement
                        Key.key("minecraft:player"),
                        // Code statement
                        uuid,
                        // Code statement
                        null
                // Code statement
                ));

        // Calls a method
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer(256, MinecraftServer.process());
        // Calls a method
        buffer.write(NBT, component);
        // Calls a method
        buffer.readIndex(0);

        // Calls a method
        assertEquals(expected, buffer.read(COMPONENT));
    // End of a block/expression
    }
// End of a block/expression
}