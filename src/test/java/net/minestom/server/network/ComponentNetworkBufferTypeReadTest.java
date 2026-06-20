// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.junit.jupiter.api.BeforeAll;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.COMPONENT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.NBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
class ComponentNetworkBufferTypeReadTest {

    // Annotation pour l'élément suivant
    @BeforeAll
    // Début d'une méthode/d'un bloc
    static void init() {
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void networkBufferReadsShowEntityWithoutName() {
        // Appelle une méthode
        UUID uuid = UUID.randomUUID();

        // Affecte une valeur
        CompoundBinaryTag hoverEvent = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("action", "show_entity")
                // Instruction de code
                .putString("id", "minecraft:player")
                // Instruction de code
                .putString("uuid", uuid.toString())
                // Appelle une méthode
                .build();

        // Affecte une valeur
        CompoundBinaryTag component = CompoundBinaryTag.builder()
                // Instruction de code
                .putString("type", "text")
                // Instruction de code
                .putString("text", "hover")
                // Instruction de code
                .put("hover_event", hoverEvent)
                // Appelle une méthode
                .build();

        // Affecte une valeur
        Component expected = Component.text("hover")
                // Instruction de code
                .hoverEvent(HoverEvent.showEntity(
                        // Instruction de code
                        Key.key("minecraft:player"),
                        // Instruction de code
                        uuid,
                        // Instruction de code
                        null
                // Instruction de code
                ));

        // Appelle une méthode
        NetworkBuffer buffer = NetworkBuffer.resizableBuffer(256, MinecraftServer.process());
        // Appelle une méthode
        buffer.write(NBT, component);
        // Appelle une méthode
        buffer.readIndex(0);

        // Appelle une méthode
        assertEquals(expected, buffer.read(COMPONENT));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}