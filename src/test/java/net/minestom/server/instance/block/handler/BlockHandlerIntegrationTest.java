// Package declaration for this file
package net.minestom.server.instance.block.handler;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerBlockPlacementPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
class BlockHandlerIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testOnPlace(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var blockPosition = new Vec(-64, 40, 64);

        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void onPlace(Placement placement) {
                // Calls a method
                assertEquals(blockPosition, placement.getBlockPosition());
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testOnDestroy(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var blockPosition = new Vec(64, 40, -64);

        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void onDestroy(Destroy destroy) {
                // Calls a method
                assertEquals(blockPosition, destroy.getBlockPosition());
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
        // Calls a method
        instance.setBlock(blockPosition, Block.AIR);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testOnInteract(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var blockPosition = new Vec(-64, 40, 64);

        // Calls a method
        AtomicBoolean interacted = new AtomicBoolean(false);
        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean onInteract(Interaction interaction) {
                // Calls a method
                interacted.set(true);
                // Calls a method
                assertEquals(blockPosition, interaction.getBlockPosition());
                // Returns a value to the caller
                return false;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
        // Calls a method
        var player = env.createPlayer(instance, blockPosition.asPos());
        // Calls a method
        player.addPacketToQueue(new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, blockPosition, BlockFace.TOP, 0, 0, 0, false, false, 1));
        // Code statement
        player.interpretPacketQueue(); // Use packets

        // Calls a method
        assertTrue(interacted.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testTick(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var blockPosition = new BlockVec(64, 40, -64);

        // Calls a method
        AtomicBoolean ticked = new AtomicBoolean(false);
        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void tick(Tick tick) {
                // Calls a method
                ticked.set(true);
                // Calls a method
                assertEquals(tick.getBlockPosition(), blockPosition.asVec());
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean isTickable() {
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
        // Tick the chunk
        // Calls a method
        var chunk = instance.getChunk(4, -4);
        // Calls a method
        assertNotNull(chunk);
        // Calls a method
        chunk.tick(0);

        // Calls a method
        assertTrue(ticked.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testTickRemoved(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var blockPosition = new BlockVec(64, 40, -64);

        // Calls a method
        AtomicBoolean ticked = new AtomicBoolean(false);
        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void tick(Tick tick) {
                // Calls a method
                ticked.set(true);
                // Calls a method
                assertEquals(tick.getBlockPosition(), blockPosition.asVec());
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean isTickable() {
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
        // Tick the chunk
        // Calls a method
        var chunk = instance.getChunk(4, -4);
        // Calls a method
        assertNotNull(chunk);
        // Calls a method
        chunk.tick(0);

        // Calls a method
        assertTrue(ticked.get());
        // Now assume there is no chunk left.
        // Calls a method
        ticked.set(false);
        // Calls a method
        instance.setBlock(blockPosition, Block.AIR);
        // Calls a method
        chunk.tick(0);
        // Calls a method
        assertFalse(ticked.get(), "Chunk ticked block when it no longer exists!");
    // End of a block/expression
    }
// End of a block/expression
}
