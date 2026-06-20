// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.ItemBlockState;
// Import of a required class
import net.minestom.server.listener.BlockPlacementListener;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerBlockPlacementPacket;
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
public class BlockPlaceIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testPlacementOutOfLimit(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        assertDoesNotThrow(() -> instance.setBlock(0, instance.getCachedDimensionType().maxY() + 1, 0, Block.STONE));
        // Calls a method
        assertDoesNotThrow(() -> instance.setBlock(0, instance.getCachedDimensionType().minY() - 1, 0, Block.STONE));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testPlacementOutOfBorder(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(1));
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Calls a method
        player.setItemInHand(PlayerHand.MAIN, ItemStack.of(Material.STONE, 5));

        // Should be air, then we place (this is outside the border)
        // Calls a method
        assertEquals(Block.AIR, instance.getBlock(3, 40, 0));
        // Calls a method
        var placePacket = new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, new Pos(3, 39, 0), BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 1);
        // Calls a method
        BlockPlacementListener.listener(placePacket, player);

        // Should still be air
        // Calls a method
        var placedBlock = instance.getBlock(3, 40, 0);
        // Calls a method
        assertEquals(Block.AIR, placedBlock);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testPlacementAtMinus64(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, -64, 0));
        // Calls a method
        player.setItemInHand(PlayerHand.MAIN, ItemStack.of(Material.STONE, 5));
        // Code statement
        env.tick(); // World border tick to update distance

        // Should be air, then we place
        // Calls a method
        assertEquals(Block.AIR, instance.getBlock(3, -64, 0));
        // Calls a method
        var placePacket = new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, new Pos(3, -64, 0), BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 1);
        // Calls a method
        BlockPlacementListener.listener(placePacket, player);

        // Should be stone.
        // Calls a method
        var placedBlock = instance.getBlock(3, -64, 0);
        // Calls a method
        assertEquals(Block.STONE, placedBlock);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testPlaceNoUpdateWithItemBlockStateComponent(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, -64, 0));
        // Code statement
        player.setItemInHand(PlayerHand.MAIN, ItemStack.of(Material.STONE_STAIRS, 5)
                // Calls a method
                .with(DataComponents.BLOCK_STATE, new ItemBlockState("facing", "west")));

        // Calls a method
        var placeCollector = env.trackEvent(PlayerBlockPlaceEvent.class, EventFilter.PLAYER, player);

        // Calls a method
        var placePacket = new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, new Pos(3, -64, 0), BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 1);
        // Calls a method
        BlockPlacementListener.listener(placePacket, player);

        // Should default to no updates because of the BLOCK_STATE component
        // Calls a method
        placeCollector.assertSingle(event -> assertFalse(event.shouldDoBlockUpdates()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testPlaceNoUpdateBlockStateComponentBeeHiveRegression(Env env) {
        // We originally compared to an empty block state but some blocks (like bee hive)
        // have a default value, so we only should trigger no updates if the block state is
        // different from the default.
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, -64, 0));
        // Calls a method
        player.setItemInHand(PlayerHand.MAIN, ItemStack.of(Material.BEEHIVE, 5));

        // Calls a method
        var placeCollector = env.trackEvent(PlayerBlockPlaceEvent.class, EventFilter.PLAYER, player);

        // Calls a method
        var placePacket = new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, new Pos(3, -64, 0), BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 1);
        // Calls a method
        BlockPlacementListener.listener(placePacket, player);

        // Should have updates because we only have the default BLOCK_STATE value
        // Calls a method
        placeCollector.assertSingle(event -> assertTrue(event.shouldDoBlockUpdates()));
    // End of a block/expression
    }

// End of a block/expression
}
