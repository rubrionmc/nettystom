// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityAttackEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerEntityInteractEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientInteractEntityPacket;

// Déclaration de type (classe/interface/enum/record)
public class UseEntityListener {

    // Début d'une méthode/d'un bloc
    public static void useEntityListener(ClientInteractEntityPacket packet, Player player) {
        // Appelle une méthode
        final Entity entity = player.getInstance().getEntityById(packet.targetId());
        // Embranchement : vérifie une condition
        if (entity == null || !entity.isViewer(player))
            // Renvoie une valeur à l'appelant
            return;

        // Embranchement : vérifie une condition
        if (ServerFlag.ENFORCE_INTERACTION_LIMIT) {
            // Appelle une méthode
            final double maxDistanceSquared = Math.pow(player.getAttributeValue(Attribute.ENTITY_INTERACTION_RANGE) + 1, 2);

            // Appelle une méthode
            final double distSquared = getDistSquared(player, entity);

            // Embranchement : vérifie une condition
            if (distSquared > maxDistanceSquared) {
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        ClientInteractEntityPacket.Type type = packet.type();
        // Embranchement : vérifie une condition
        if (type instanceof ClientInteractEntityPacket.Attack) {
            // Embranchement : vérifie une condition
            if (entity instanceof LivingEntity && ((LivingEntity) entity).isDead()) // Can't attack dead entities
                // Renvoie une valeur à l'appelant
                return;
            // Appelle une méthode
            EventDispatcher.call(new EntityAttackEvent(player, entity));
        // Embranchement : vérifie une condition
        } else if (type instanceof ClientInteractEntityPacket.InteractAt interactAt) {
            // Appelle une méthode
            Point interactPosition = new Vec(interactAt.targetX(), interactAt.targetY(), interactAt.targetZ());
            // Appelle une méthode
            EventDispatcher.call(new PlayerEntityInteractEvent(player, entity, interactAt.hand(), interactPosition));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static double getDistSquared(Player player, Entity entity) {
        // Appelle une méthode
        final Pos playerPos = player.getPosition();
        // Appelle une méthode
        final double eyeHeight = player.getEyeHeight();
        // Appelle une méthode
        final double px = playerPos.x();
        // Appelle une méthode
        final double py = playerPos.y() + eyeHeight;
        // Appelle une méthode
        final double pz = playerPos.z();

        // Appelle une méthode
        final BoundingBox box = entity.getBoundingBox();
        // Appelle une méthode
        final double halfWidth = box.width() / 2;
        // Appelle une méthode
        final double height = box.height();
        // Appelle une méthode
        final Pos entityPos = entity.getPosition();

        // Appelle une méthode
        final double minX = entityPos.x() - halfWidth;
        // Appelle une méthode
        final double maxX = entityPos.x() + halfWidth;
        // Appelle une méthode
        final double minY = entityPos.y();
        // Appelle une méthode
        final double maxY = entityPos.y() + height;
        // Appelle une méthode
        final double minZ = entityPos.z() - halfWidth;
        // Appelle une méthode
        final double maxZ = entityPos.z() + halfWidth;

        // Appelle une méthode
        final double clampX = Math.max(minX, Math.min(px, maxX));
        // Appelle une méthode
        final double clampY = Math.max(minY, Math.min(py, maxY));
        // Appelle une méthode
        final double clampZ = Math.max(minZ, Math.min(pz, maxZ));

        // Affecte une valeur
        final double dx = px - clampX;
        // Affecte une valeur
        final double dy = py - clampY;
        // Affecte une valeur
        final double dz = pz - clampZ;
        // Renvoie une valeur à l'appelant
        return dx * dx + dy * dy + dz * dz;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}