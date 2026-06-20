// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.network.packet.server.play.BlockChangePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.BlockEntityDataPacket;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.testing.TestUtils.assertPoint;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class InstanceBlockPacketIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void replaceAir(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var blockPoint = new Vec(5, 41, 0);

        // Calls a method
        assertEquals(Block.AIR, instance.getBlock(blockPoint));

        // Calls a method
        var tracker = connection.trackIncoming();
        // Calls a method
        instance.setBlock(blockPoint, Block.STONE);
        // Start of a method/block
        tracker.assertSingle(BlockChangePacket.class, packet -> {
            // Calls a method
            assertPoint(blockPoint, packet.blockPosition());
            // Calls a method
            assertEquals(Block.STONE.stateId(), packet.blockStateId());
        // End of a block/expression
        });

        // Calls a method
        assertEquals(Block.STONE, instance.getBlock(blockPoint));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void placeBlockEntity(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var blockPoint = new Vec(5, 41, 0);

        // Assigns a value
        BlockHandler signHandler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Collection<Tag<?>> getBlockEntityTags() {
                // Returns a value to the caller
                return List.of(Tag.Byte("is_waxed"));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("sign");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        assertEquals(Block.AIR, instance.getBlock(blockPoint));

        // Code statement
        final Block block;
        // Code statement
        final CompoundBinaryTag data;
        // Exception handling
        try {
            // Calls a method
            data = MinestomAdventure.tagStringIO().asCompound("{\"is_waxed\":1B}");
            // Calls a method
            block = Block.OAK_SIGN.withHandler(signHandler).withNbt(data);
        // Start of a method/block
        } catch (Exception ex) {
            // Throws an exception
            throw new RuntimeException(ex);
        // End of a block/expression
        }

        // Calls a method
        var blockChangeTracker = connection.trackIncoming(BlockChangePacket.class);
        // Calls a method
        var blockEntityTracker = connection.trackIncoming(BlockEntityDataPacket.class);
        // Calls a method
        instance.setBlock(blockPoint, block);
        // Start of a method/block
        blockChangeTracker.assertSingle(packet -> {
            // Calls a method
            assertPoint(blockPoint, packet.blockPosition());
            // Calls a method
            assertEquals(block.stateId(), packet.blockStateId());
        // End of a block/expression
        });
        // Start of a method/block
        blockEntityTracker.assertSingle(packet -> {
            // Calls a method
            assertPoint(blockPoint, packet.blockPosition());
            // Calls a method
            assertEquals(block.registry().blockEntityType(), packet.type());
            // Calls a method
            assertEquals(data, packet.data());
        // End of a block/expression
        });

        // Calls a method
        assertEquals(block, instance.getBlock(blockPoint));
    // End of a block/expression
    }
// End of a block/expression
}
