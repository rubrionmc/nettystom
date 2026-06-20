// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerEntityInteractEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientInteractEntityPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.BeforeEach;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class UseEntityListenerTest {

    // Instruction de code
    private Player player;
    // Instruction de code
    private Entity targetEntity;
    // Instruction de code
    private boolean eventWasCalled;

    // Annotation pour l'élément suivant
    @BeforeEach
    // Début d'une méthode/d'un bloc
    public void setup(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();

        // Appelle une méthode
        player = env.createPlayer(instance, new Pos(0, 0, 0));
        // Appelle une méthode
        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(5.0);

        // Appelle une méthode
        targetEntity = new Entity(EntityType.SLIME);
        // Appelle une méthode
        targetEntity.setInstance(instance, new Pos(2, 0, 2)).join();

        // Affecte une valeur
        eventWasCalled = false;

        // Début d'une méthode/d'un bloc
        player.eventNode().addListener(PlayerEntityInteractEvent.class, event -> {
            // Embranchement : vérifie une condition
            if (event.getPlayer().equals(player) && event.getTarget().equals(targetEntity)) {
                // Affecte une valeur
                eventWasCalled = true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testInteractionWithinRange() {
        // Affecte une valeur
        ClientInteractEntityPacket packet = new ClientInteractEntityPacket(
                // Instruction de code
                targetEntity.getEntityId(),
                // Crée un nouvel objet
                new ClientInteractEntityPacket.InteractAt(0, 0, 0, PlayerHand.MAIN),
                // Instruction de code
                false
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        UseEntityListener.useEntityListener(packet, player);
        // Appelle une méthode
        assertTrue(eventWasCalled, "Expected PlayerEntityInteractEvent to be called for nearby target");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testInteractionOutOfRange() {
        // Appelle une méthode
        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(1.0);

        // Appelle une méthode
        targetEntity.teleport(new Pos(10, 0, 10)).join();
        // Affecte une valeur
        ClientInteractEntityPacket packet = new ClientInteractEntityPacket(
                // Instruction de code
                targetEntity.getEntityId(),
                // Crée un nouvel objet
                new ClientInteractEntityPacket.InteractAt(0, 0, 0, PlayerHand.MAIN),
                // Instruction de code
                false
        // Fin d'un bloc/d'une expression
        );

        // Affecte une valeur
        eventWasCalled = false;
        // Appelle une méthode
        UseEntityListener.useEntityListener(packet, player);
        // Appelle une méthode
        assertFalse(eventWasCalled, "Expected PlayerEntityInteractEvent NOT to be called for out-of-range target");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testInteractionConsideringHitboxAndEyePosition() {
        // Appelle une méthode
        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(1.5);

        // Appelle une méthode
        targetEntity.teleport(new Pos(1.6, 0, 0)).join();

        // Affecte une valeur
        ClientInteractEntityPacket packet = new ClientInteractEntityPacket(
                // Instruction de code
                targetEntity.getEntityId(),
                // Crée un nouvel objet
                new ClientInteractEntityPacket.InteractAt(0, 0, 0, PlayerHand.MAIN),
                // Instruction de code
                false
        // Fin d'un bloc/d'une expression
        );

        // Affecte une valeur
        eventWasCalled = false;
        // Appelle une méthode
        UseEntityListener.useEntityListener(packet, player);
        // Appelle une méthode
        assertTrue(eventWasCalled, "Expected PlayerEntityInteractEvent to be called considering hitbox size and eye position");
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testInteractionConsideringEyeHeight() {
        // Appelle une méthode
        player.teleport(new Pos(0, 1.6, 0)).join();
        // Appelle une méthode
        targetEntity.teleport(new Pos(0, 1.6, 2)).join();

        // Affecte une valeur
        ClientInteractEntityPacket packet = new ClientInteractEntityPacket(
                // Instruction de code
                targetEntity.getEntityId(),
                // Crée un nouvel objet
                new ClientInteractEntityPacket.InteractAt(0, 0, 0, PlayerHand.MAIN),
                // Instruction de code
                false
        // Fin d'un bloc/d'une expression
        );

        // Affecte une valeur
        eventWasCalled = false;
        // Appelle une méthode
        UseEntityListener.useEntityListener(packet, player);
        // Appelle une méthode
        assertTrue(eventWasCalled, "Expected PlayerEntityInteractEvent to be called considering eye height");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
