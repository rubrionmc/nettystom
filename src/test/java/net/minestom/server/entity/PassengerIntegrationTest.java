// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SetPassengersPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SpawnEntityPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PassengerIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void passenger(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var passenger = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        passenger.setInstance(instance, new Pos(0, 40, 0)).join();

        // Appelle une méthode
        assertEquals(0, vehicle.getPassengers().size());
        // Appelle une méthode
        assertNull(passenger.getVehicle());

        // Appelle une méthode
        vehicle.addPassenger(passenger);
        // Appelle une méthode
        assertEquals(1, vehicle.getPassengers().size());
        // Appelle une méthode
        assertEquals(vehicle, passenger.getVehicle());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void passengerTeleport(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var passenger = new Entity(EntityType.ZOMBIE);

        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        passenger.setInstance(instance, new Pos(0, 40, 5000)).join();

        // Appelle une méthode
        assertEquals(0, vehicle.getPassengers().size());
        // Appelle une méthode
        assertNull(passenger.getVehicle());

        // Appelle une méthode
        vehicle.addPassenger(passenger);
        // Appelle une méthode
        assertEquals(1, vehicle.getPassengers().size());
        // Appelle une méthode
        assertEquals(vehicle, passenger.getVehicle());

        // Appelle une méthode
        assertTrue(passenger.getDistance(vehicle) < 2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void passengerPacketOrder(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var vehicle = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Add 3 passengers to vehicle to test Entity#updateNewViewer recursion
        // Appelle une méthode
        var passenger1 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var passenger2 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var passenger3 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        vehicle.addPassenger(passenger1);
        // Appelle une méthode
        passenger1.addPassenger(passenger2);
        // Appelle une méthode
        passenger2.addPassenger(passenger3);

        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var spawnTracker = connection.trackIncoming(SpawnEntityPacket.class);
        // Appelle une méthode
        var passengerTracker = connection.trackIncoming(SetPassengersPacket.class);

        // Appelle une méthode
        connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        int startingId = passenger3.getEntityId();
        // Appelle une méthode
        passengerTracker.assertCount(3);
        // Appelle une méthode
        var passengerPackets = passengerTracker.collect();
        // Boucle : répète un bloc
        for (int i = 0; i < passengerPackets.size(); i++) {
            // Passenger packet order will be sent backwards down the chain of passenger vehicles
            // Appelle une méthode
            assertEquals(startingId - i, passengerPackets.get(i).passengersId().getFirst());
        // Fin d'un bloc/d'une expression
        }

        // Ensure spawn packets are never sent more than once per entity
        // Appelle une méthode
        startingId = vehicle.getEntityId();
        // Appelle une méthode
        spawnTracker.assertCount(4);
        // Appelle une méthode
        var spawnPackets = spawnTracker.collect();
        // Boucle : répète un bloc
        for (int i = 0; i < spawnPackets.size(); i++) {
            // If the passenger spawn packets are sent in order we know that
            // Entity#updateNewViewer ran as it should
            // Appelle une méthode
            assertEquals(startingId + i, spawnPackets.get(i).entityId());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
