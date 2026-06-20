// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Déclaration de type (classe/interface/enum/record)
public record ClientSetRecipeBookStatePacket(BookType bookType,
                                             // Début d'une méthode/d'un bloc
                                             boolean bookOpen, boolean filterActive) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSetRecipeBookStatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.Enum(BookType.class), ClientSetRecipeBookStatePacket::bookType,
            // Instruction de code
            BOOLEAN, ClientSetRecipeBookStatePacket::bookOpen,
            // Instruction de code
            BOOLEAN, ClientSetRecipeBookStatePacket::filterActive,
            // Instruction de code
            ClientSetRecipeBookStatePacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum BookType {
        // Instruction de code
        CRAFTING, FURNACE, BLAST_FURNACE, SMOKER
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
