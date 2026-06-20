// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record ClientNameItemPacket(String itemName) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientNameItemPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, ClientNameItemPacket::itemName,
            // Instruction de code
            ClientNameItemPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientNameItemPacket {
        // Embranchement : vérifie une condition
        if (itemName.length() > Short.MAX_VALUE) {
            // Lève une exception
            throw new IllegalArgumentException("ItemStack name cannot be longer than Short.MAX_VALUE characters!");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
