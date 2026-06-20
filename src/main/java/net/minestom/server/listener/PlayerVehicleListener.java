// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.BoatMeta;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientSteerBoatPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientInputPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientVehicleMovePacket;

// Déclaration de type (classe/interface/enum/record)
public class PlayerVehicleListener {

    // Début d'une méthode/d'un bloc
    public static void vehicleMoveListener(ClientVehicleMovePacket packet, Player player) {
        // Appelle une méthode
        final Entity vehicle = player.getVehicle();
        // Embranchement : vérifie une condition
        if (vehicle == null)
            // Renvoie une valeur à l'appelant
            return;

        // Appelle une méthode
        vehicle.refreshPosition(packet.position());

        // This packet causes weird screen distortion
        /*VehicleMovePacket vehicleMovePacket = new VehicleMovePacket();
        vehicleMovePacket.x = packet.x;
        vehicleMovePacket.y = packet.y;
        vehicleMovePacket.z = packet.z;
        vehicleMovePacket.yaw = packet.yaw;
        vehicleMovePacket.pitch = packet.pitch;
        player.getPlayerConnection().sendPacket(vehicleMovePacket);*/

    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void boatSteerListener(ClientSteerBoatPacket packet, Player player) {
        // Appelle une méthode
        final Entity vehicle = player.getVehicle();
        /* The packet may have been received after already exiting the vehicle. */
        // Embranchement : vérifie une condition
        if (vehicle == null) return;
        // Embranchement : vérifie une condition
        if (!(vehicle.getEntityMeta() instanceof BoatMeta boat)) return;
        // Only send metadata packet if there are changes
        // Embranchement : vérifie une condition
        if (boat.isLeftPaddleTurning() != packet.leftPaddleTurning()) {
            // Appelle une méthode
            boat.setLeftPaddleTurning(packet.leftPaddleTurning());
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (boat.isRightPaddleTurning() != packet.rightPaddleTurning()) {
            // Appelle une méthode
            boat.setRightPaddleTurning(packet.rightPaddleTurning());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}