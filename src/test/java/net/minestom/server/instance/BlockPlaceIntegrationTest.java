// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.ItemBlockState;
// Import d'une classe nécessaire
import net.minestom.server.listener.BlockPlacementListener;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerBlockPlacementPacket;
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
public class BlockPlaceIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testPlacementOutOfLimit(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        assertDoesNotThrow(() -> instance.setBlock(0, instance.getCachedDimensionType().maxY() + 1, 0, Block.STONE));
        // Appelle une méthode
        assertDoesNotThrow(() -> instance.setBlock(0, instance.getCachedDimensionType().minY() - 1, 0, Block.STONE));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testPlacementOutOfBorder(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(1));
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        player.setItemInHand(PlayerHand.MAIN, ItemStack.of(Material.STONE, 5));

        // Should be air, then we place (this is outside the border)
        // Appelle une méthode
        assertEquals(Block.AIR, instance.getBlock(3, 40, 0));
        // Appelle une méthode
        var placePacket = new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, new Pos(3, 39, 0), BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 1);
        // Appelle une méthode
        BlockPlacementListener.listener(placePacket, player);

        // Should still be air
        // Appelle une méthode
        var placedBlock = instance.getBlock(3, 40, 0);
        // Appelle une méthode
        assertEquals(Block.AIR, placedBlock);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testPlacementAtMinus64(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, -64, 0));
        // Appelle une méthode
        player.setItemInHand(PlayerHand.MAIN, ItemStack.of(Material.STONE, 5));
        // Instruction de code
        env.tick(); // World border tick to update distance

        // Should be air, then we place
        // Appelle une méthode
        assertEquals(Block.AIR, instance.getBlock(3, -64, 0));
        // Appelle une méthode
        var placePacket = new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, new Pos(3, -64, 0), BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 1);
        // Appelle une méthode
        BlockPlacementListener.listener(placePacket, player);

        // Should be stone.
        // Appelle une méthode
        var placedBlock = instance.getBlock(3, -64, 0);
        // Appelle une méthode
        assertEquals(Block.STONE, placedBlock);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testPlaceNoUpdateWithItemBlockStateComponent(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, -64, 0));
        // Instruction de code
        player.setItemInHand(PlayerHand.MAIN, ItemStack.of(Material.STONE_STAIRS, 5)
                // Appelle une méthode
                .with(DataComponents.BLOCK_STATE, new ItemBlockState("facing", "west")));

        // Appelle une méthode
        var placeCollector = env.trackEvent(PlayerBlockPlaceEvent.class, EventFilter.PLAYER, player);

        // Appelle une méthode
        var placePacket = new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, new Pos(3, -64, 0), BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 1);
        // Appelle une méthode
        BlockPlacementListener.listener(placePacket, player);

        // Should default to no updates because of the BLOCK_STATE component
        // Appelle une méthode
        placeCollector.assertSingle(event -> assertFalse(event.shouldDoBlockUpdates()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testPlaceNoUpdateBlockStateComponentBeeHiveRegression(Env env) {
        // We originally compared to an empty block state but some blocks (like bee hive)
        // have a default value, so we only should trigger no updates if the block state is
        // different from the default.
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, -64, 0));
        // Appelle une méthode
        player.setItemInHand(PlayerHand.MAIN, ItemStack.of(Material.BEEHIVE, 5));

        // Appelle une méthode
        var placeCollector = env.trackEvent(PlayerBlockPlaceEvent.class, EventFilter.PLAYER, player);

        // Appelle une méthode
        var placePacket = new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, new Pos(3, -64, 0), BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 1);
        // Appelle une méthode
        BlockPlacementListener.listener(placePacket, player);

        // Should have updates because we only have the default BLOCK_STATE value
        // Appelle une méthode
        placeCollector.assertSingle(event -> assertTrue(event.shouldDoBlockUpdates()));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
