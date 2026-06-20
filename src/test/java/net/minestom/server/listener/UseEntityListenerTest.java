// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.event.player.PlayerEntityInteractEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientInteractEntityPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.BeforeEach;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class UseEntityListenerTest {

    // Code statement
    private Player player;
    // Code statement
    private Entity targetEntity;
    // Code statement
    private boolean eventWasCalled;

    // Annotation for the following element
    @BeforeEach
    // Start of a method/block
    public void setup(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();

        // Calls a method
        player = env.createPlayer(instance, new Pos(0, 0, 0));
        // Calls a method
        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(5.0);

        // Calls a method
        targetEntity = new Entity(EntityType.SLIME);
        // Calls a method
        targetEntity.setInstance(instance, new Pos(2, 0, 2)).join();

        // Assigns a value
        eventWasCalled = false;

        // Start of a method/block
        player.eventNode().addListener(PlayerEntityInteractEvent.class, event -> {
            // Branch: checks a condition
            if (event.getPlayer().equals(player) && event.getTarget().equals(targetEntity)) {
                // Assigns a value
                eventWasCalled = true;
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testInteractionWithinRange() {
        // Assigns a value
        ClientInteractEntityPacket packet = new ClientInteractEntityPacket(
                // Code statement
                targetEntity.getEntityId(),
                // Code statement
                PlayerHand.MAIN,
                // Code statement
                Vec.ZERO,
                // Code statement
                false
        // End of a block/expression
        );

        // Calls a method
        UseEntityListener.useEntityListener(packet, player);
        // Calls a method
        assertTrue(eventWasCalled, "Expected PlayerEntityInteractEvent to be called for nearby target");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testInteractionOutOfRange() {
        // Calls a method
        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(1.0);

        // Calls a method
        targetEntity.teleport(new Pos(10, 0, 10)).join();
        // Assigns a value
        ClientInteractEntityPacket packet = new ClientInteractEntityPacket(
                // Code statement
                targetEntity.getEntityId(),
                // Code statement
                PlayerHand.MAIN,
                // Code statement
                Vec.ZERO,
                // Code statement
                false
        // End of a block/expression
        );

        // Assigns a value
        eventWasCalled = false;
        // Calls a method
        UseEntityListener.useEntityListener(packet, player);
        // Calls a method
        assertFalse(eventWasCalled, "Expected PlayerEntityInteractEvent NOT to be called for out-of-range target");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testInteractionConsideringHitboxAndEyePosition() {
        // Calls a method
        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(1.5);

        // Calls a method
        targetEntity.teleport(new Pos(1.6, 0, 0)).join();

        // Assigns a value
        ClientInteractEntityPacket packet = new ClientInteractEntityPacket(
                // Code statement
                targetEntity.getEntityId(),
                // Code statement
                PlayerHand.MAIN,
                // Code statement
                Vec.ZERO,
                // Code statement
                false
        // End of a block/expression
        );

        // Assigns a value
        eventWasCalled = false;
        // Calls a method
        UseEntityListener.useEntityListener(packet, player);
        // Calls a method
        assertTrue(eventWasCalled, "Expected PlayerEntityInteractEvent to be called considering hitbox size and eye position");
    // End of a block/expression
    }


    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testInteractionConsideringEyeHeight() {
        // Calls a method
        player.teleport(new Pos(0, 1.6, 0)).join();
        // Calls a method
        targetEntity.teleport(new Pos(0, 1.6, 2)).join();

        // Assigns a value
        ClientInteractEntityPacket packet = new ClientInteractEntityPacket(
                // Code statement
                targetEntity.getEntityId(),
                // Code statement
                PlayerHand.MAIN,
                // Code statement
                Vec.ZERO,
                // Code statement
                false
        // End of a block/expression
        );

        // Assigns a value
        eventWasCalled = false;
        // Calls a method
        UseEntityListener.useEntityListener(packet, player);
        // Calls a method
        assertTrue(eventWasCalled, "Expected PlayerEntityInteractEvent to be called considering eye height");
    // End of a block/expression
    }
// End of a block/expression
}
