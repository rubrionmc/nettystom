// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;

// Déclaration de type (classe/interface/enum/record)
public record LoginSuccessPacket(GameProfile gameProfile) implements ServerPacket.Login {
    // Affecte une valeur
    public static final NetworkBuffer.Type<LoginSuccessPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            GameProfile.SERIALIZER, LoginSuccessPacket::gameProfile,
            // Instruction de code
            LoginSuccessPacket::new);
// Fin d'un bloc/d'une expression
}
