// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.metadata.other.BoatMeta;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientSteerBoatPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientVehicleMovePacket;

// Type declaration (class/interface/enum/record)
public class PlayerVehicleListener {

    // Start of a method/block
    public static void vehicleMoveListener(ClientVehicleMovePacket packet, Player player) {
        // Calls a method
        final Entity vehicle = player.getVehicle();
        // Branch: checks a condition
        if (vehicle == null)
            // Returns a value to the caller
            return;

        // Calls a method
        vehicle.refreshPosition(packet.position());

        // This packet causes weird screen distortion
        /*VehicleMovePacket vehicleMovePacket = new VehicleMovePacket();
        vehicleMovePacket.x = packet.x;
        vehicleMovePacket.y = packet.y;
        vehicleMovePacket.z = packet.z;
        vehicleMovePacket.yaw = packet.yaw;
        vehicleMovePacket.pitch = packet.pitch;
        player.getPlayerConnection().sendPacket(vehicleMovePacket);*/

    // End of a block/expression
    }

    // Start of a method/block
    public static void boatSteerListener(ClientSteerBoatPacket packet, Player player) {
        // Calls a method
        final Entity vehicle = player.getVehicle();
        /* The packet may have been received after already exiting the vehicle. */
        // Branch: checks a condition
        if (vehicle == null) return;
        // Branch: checks a condition
        if (!(vehicle.getEntityMeta() instanceof BoatMeta boat)) return;
        // Only send metadata packet if there are changes
        // Branch: checks a condition
        if (boat.isLeftPaddleTurning() != packet.leftPaddleTurning()) {
            // Calls a method
            boat.setLeftPaddleTurning(packet.leftPaddleTurning());
        // End of a block/expression
        }
        // Branch: checks a condition
        if (boat.isRightPaddleTurning() != packet.rightPaddleTurning()) {
            // Calls a method
            boat.setRightPaddleTurning(packet.rightPaddleTurning());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}