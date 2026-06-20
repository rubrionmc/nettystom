// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.entity.EntityAttackEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerEntityInteractEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientAttackPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientInteractEntityPacket;

// Type declaration (class/interface/enum/record)
public class UseEntityListener {

    // Start of a method/block
    public static void useEntityListener(ClientInteractEntityPacket packet, Player player) {
        // Calls a method
        final Entity entity = player.getInstance().getEntityById(packet.targetId());
        // Branch: checks a condition
        if (entity == null || invalidUse(player, entity))
            // Returns a value to the caller
            return;
        // Calls a method
        EventDispatcher.call(new PlayerEntityInteractEvent(player, entity, packet.hand(), packet.location()));
    // End of a block/expression
    }

    // Start of a method/block
    public static void attackEntityListener(ClientAttackPacket packet, Player player) {
        // Calls a method
        final Entity entity = player.getInstance().getEntityById(packet.targetId());
        // Branch: checks a condition
        if (entity == null || invalidUse(player, entity))
            // Returns a value to the caller
            return;
        // Branch: checks a condition
        if (entity instanceof LivingEntity livingEntity && livingEntity.isDead()) // Can't attack dead entities
            // Returns a value to the caller
            return;
        // Calls a method
        EventDispatcher.call(new EntityAttackEvent(player, entity));
    // End of a block/expression
    }

    // Start of a method/block
    static boolean invalidUse(Player player, Entity entity) {
        // Branch: checks a condition
        if (!entity.isViewer(player))
            // Returns a value to the caller
            return true;

        // Branch: checks a condition
        if (ServerFlag.ENFORCE_INTERACTION_LIMIT) {
            // Calls a method
            final double maxDistanceSquared = Math.pow(player.getAttributeValue(Attribute.ENTITY_INTERACTION_RANGE) + 1, 2);

            // Calls a method
            final double distSquared = getDistSquared(player, entity);

            // Returns a value to the caller
            return distSquared >= maxDistanceSquared;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Start of a method/block
    private static double getDistSquared(Player player, Entity entity) {
        // Calls a method
        final Pos playerPos = player.getPosition();
        // Calls a method
        final double eyeHeight = player.getEyeHeight();
        // Calls a method
        final double px = playerPos.x();
        // Calls a method
        final double py = playerPos.y() + eyeHeight;
        // Calls a method
        final double pz = playerPos.z();

        // Calls a method
        final BoundingBox box = entity.getBoundingBox();
        // Calls a method
        final double halfWidth = box.width() / 2;
        // Calls a method
        final double height = box.height();
        // Calls a method
        final Pos entityPos = entity.getPosition();

        // Calls a method
        final double minX = entityPos.x() - halfWidth;
        // Calls a method
        final double maxX = entityPos.x() + halfWidth;
        // Calls a method
        final double minY = entityPos.y();
        // Calls a method
        final double maxY = entityPos.y() + height;
        // Calls a method
        final double minZ = entityPos.z() - halfWidth;
        // Calls a method
        final double maxZ = entityPos.z() + halfWidth;

        // Calls a method
        final double clampX = Math.clamp(px, minX, maxX);
        // Calls a method
        final double clampY = Math.clamp(py, minY, maxY);
        // Calls a method
        final double clampZ = Math.clamp(pz, minZ, maxZ);

        // Assigns a value
        final double dx = px - clampX;
        // Assigns a value
        final double dy = py - clampY;
        // Assigns a value
        final double dz = pz - clampZ;
        // Returns a value to the caller
        return dx * dx + dy * dy + dz * dz;
    // End of a block/expression
    }
// End of a block/expression
}