// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;
// Static import of a member
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
// Type declaration (class/interface/enum/record)
public record DamageEventPacket(int targetEntityId, int damageTypeId, int sourceEntityId, int sourceDirectId,
                                // Annotation for the following element
                                @Nullable Point sourcePos) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<DamageEventPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, DamageEventPacket::targetEntityId,
            // Code statement
            VAR_INT, DamageEventPacket::damageTypeId,
            // Code statement
            VAR_INT, DamageEventPacket::sourceEntityId,
            // Code statement
            VAR_INT, DamageEventPacket::sourceDirectId,
            // Code statement
            VECTOR3D.optional(), DamageEventPacket::sourcePos,
            // Code statement
            DamageEventPacket::new);
// End of a block/expression
}
