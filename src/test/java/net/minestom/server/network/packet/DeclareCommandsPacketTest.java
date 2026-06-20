// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.packet.server.play.DeclareCommandsPacket.getFlag;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class DeclareCommandsPacketTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testWriteGameProfileArg() {
        // Appelle une méthode
        var root = new DeclareCommandsPacket.Node();
        // Appelle une méthode
        root.flags = getFlag(DeclareCommandsPacket.NodeType.ARGUMENT, false, false, false);
        // Affecte une valeur
        root.parser = ArgumentParserType.GAME_PROFILE;
        // Appelle une méthode
        var packet = new DeclareCommandsPacket(List.of(root), 0);

        // Appelle une méthode
        var array = NetworkBuffer.makeArray(DeclareCommandsPacket.SERIALIZER, packet);
        // Appelle une méthode
        var readPacket = NetworkBuffer.wrap(array, 0, array.length).read(DeclareCommandsPacket.SERIALIZER);
        // Appelle une méthode
        assertEquals(ArgumentParserType.GAME_PROFILE, readPacket.nodes().getFirst().parser);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
