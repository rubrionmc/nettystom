// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.common;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientCustomClickActionPacket(Key key, BinaryTag payload) implements ClientPacket.Configuration, ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientCustomClickActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.KEY, ClientCustomClickActionPacket::key,
            // Instruction de code
            NetworkBuffer.NBT.lengthPrefixed(65536), ClientCustomClickActionPacket::payload,
            // Instruction de code
            ClientCustomClickActionPacket::new);
// Fin d'un bloc/d'une expression
}
