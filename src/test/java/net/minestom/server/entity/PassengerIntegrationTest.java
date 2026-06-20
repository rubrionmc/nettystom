// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.packet.server.play.SetPassengersPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.SpawnEntityPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PassengerIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void passenger(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var passenger = new Entity(EntityType.ZOMBIE);

        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        passenger.setInstance(instance, new Pos(0, 40, 0)).join();

        // Calls a method
        assertEquals(0, vehicle.getPassengers().size());
        // Calls a method
        assertNull(passenger.getVehicle());

        // Calls a method
        vehicle.addPassenger(passenger);
        // Calls a method
        assertEquals(1, vehicle.getPassengers().size());
        // Calls a method
        assertEquals(vehicle, passenger.getVehicle());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void passengerTeleport(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var passenger = new Entity(EntityType.ZOMBIE);

        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        passenger.setInstance(instance, new Pos(0, 40, 5000)).join();

        // Calls a method
        assertEquals(0, vehicle.getPassengers().size());
        // Calls a method
        assertNull(passenger.getVehicle());

        // Calls a method
        vehicle.addPassenger(passenger);
        // Calls a method
        assertEquals(1, vehicle.getPassengers().size());
        // Calls a method
        assertEquals(vehicle, passenger.getVehicle());

        // Calls a method
        assertTrue(passenger.getDistance(vehicle) < 2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void passengerPacketOrder(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Add 3 passengers to vehicle to test Entity#updateNewViewer recursion
        // Calls a method
        var passenger1 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var passenger2 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var passenger3 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        vehicle.addPassenger(passenger1);
        // Calls a method
        passenger1.addPassenger(passenger2);
        // Calls a method
        passenger2.addPassenger(passenger3);

        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var spawnTracker = connection.trackIncoming(SpawnEntityPacket.class);
        // Calls a method
        var passengerTracker = connection.trackIncoming(SetPassengersPacket.class);

        // Calls a method
        connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        int startingId = passenger3.getEntityId();
        // Calls a method
        passengerTracker.assertCount(3);
        // Calls a method
        var passengerPackets = passengerTracker.collect();
        // Loop: repeats a block
        for (int i = 0; i < passengerPackets.size(); i++) {
            // Passenger packet order will be sent backwards down the chain of passenger vehicles
            // Calls a method
            assertEquals(startingId - i, passengerPackets.get(i).passengersId().getFirst());
        // End of a block/expression
        }

        // Ensure spawn packets are never sent more than once per entity
        // Calls a method
        startingId = vehicle.getEntityId();
        // Calls a method
        spawnTracker.assertCount(4);
        // Calls a method
        var spawnPackets = spawnTracker.collect();
        // Loop: repeats a block
        for (int i = 0; i < spawnPackets.size(); i++) {
            // If the passenger spawn packets are sent in order we know that
            // Entity#updateNewViewer ran as it should
            // Calls a method
            assertEquals(startingId + i, spawnPackets.get(i).entityId());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
