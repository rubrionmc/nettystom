// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VECTOR3D;

/**
 * See <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Damage_Event">the Minecraft wiki</a> for more info.
 *
 * @param targetEntityId ID of the entity being damaged
 * @param damageTypeId   ID of damage type
 * @param sourceEntityId 0 if there is no source entity, otherwise it is entityId + 1
 * @param sourceDirectId 0 if there is no direct source. For direct attacks (e.g. melee), this is the same as sourceEntityId. For indirect attacks (e.g. projectiles), this is the projectile entity id + 1
 * @param sourcePos      null if there is no source position, otherwise the position of the source
 */
// Déclaration de type (classe/interface/enum/record)
public record DamageEventPacket(int targetEntityId, int damageTypeId, int sourceEntityId, int sourceDirectId,
                                // Annotation pour l'élément suivant
                                @Nullable Point sourcePos) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DamageEventPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, DamageEventPacket::targetEntityId,
            // Instruction de code
            VAR_INT, DamageEventPacket::damageTypeId,
            // Instruction de code
            VAR_INT, DamageEventPacket::sourceEntityId,
            // Instruction de code
            VAR_INT, DamageEventPacket::sourceDirectId,
            // Instruction de code
            VECTOR3D.optional(), DamageEventPacket::sourcePos,
            // Instruction de code
            DamageEventPacket::new);
// Fin d'un bloc/d'une expression
}
