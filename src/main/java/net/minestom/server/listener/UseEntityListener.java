// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityAttackEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerEntityInteractEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientAttackPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientInteractEntityPacket;

// Déclaration de type (classe/interface/enum/record)
public class UseEntityListener {

    // Début d'une méthode/d'un bloc
    public static void useEntityListener(ClientInteractEntityPacket packet, Player player) {
        // Appelle une méthode
        final Entity entity = player.getInstance().getEntityById(packet.targetId());
        // Embranchement : vérifie une condition
        if (entity == null || invalidUse(player, entity))
            // Renvoie une valeur à l'appelant
            return;
        // Appelle une méthode
        EventDispatcher.call(new PlayerEntityInteractEvent(player, entity, packet.hand(), packet.location()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void attackEntityListener(ClientAttackPacket packet, Player player) {
        // Appelle une méthode
        final Entity entity = player.getInstance().getEntityById(packet.targetId());
        // Embranchement : vérifie une condition
        if (entity == null || invalidUse(player, entity))
            // Renvoie une valeur à l'appelant
            return;
        // Embranchement : vérifie une condition
        if (entity instanceof LivingEntity livingEntity && livingEntity.isDead()) // Can't attack dead entities
            // Renvoie une valeur à l'appelant
            return;
        // Appelle une méthode
        EventDispatcher.call(new EntityAttackEvent(player, entity));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static boolean invalidUse(Player player, Entity entity) {
        // Embranchement : vérifie une condition
        if (!entity.isViewer(player))
            // Renvoie une valeur à l'appelant
            return true;

        // Embranchement : vérifie une condition
        if (ServerFlag.ENFORCE_INTERACTION_LIMIT) {
            // Appelle une méthode
            final double maxDistanceSquared = Math.pow(player.getAttributeValue(Attribute.ENTITY_INTERACTION_RANGE) + 1, 2);

            // Appelle une méthode
            final double distSquared = getDistSquared(player, entity);

            // Renvoie une valeur à l'appelant
            return distSquared >= maxDistanceSquared;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
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
        final double clampX = Math.clamp(px, minX, maxX);
        // Appelle une méthode
        final double clampY = Math.clamp(py, minY, maxY);
        // Appelle une méthode
        final double clampZ = Math.clamp(pz, minZ, maxZ);

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