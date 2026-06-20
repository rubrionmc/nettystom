// Package declaration for this file
package net.minestom.server.entity.player;

// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.predicate.BlockPredicate;
// Import of a required class
import net.minestom.server.instance.block.predicate.PropertiesPredicate;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.BlockPredicates;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerBlockPlacementPacket;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.Arguments;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.util.stream.Stream;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerBlockPlacementIntegrationTest {

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("placeBlockFromAdventureModeParams")
    // Start of a method/block
    public void placeBlockFromAdventureMode(Block baseBlock, BlockPredicates canPlaceOn, Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        instance.setBlock(2, 41, 0, baseBlock);

        // Calls a method
        player.setGameMode(GameMode.ADVENTURE);
        // Calls a method
        player.setItemInMainHand(ItemStack.builder(Material.WHITE_WOOL).set(DataComponents.CAN_PLACE_ON, canPlaceOn).build());

        // Assigns a value
        var packet = new ClientPlayerBlockPlacementPacket(
                // Code statement
                PlayerHand.MAIN, new Pos(2, 41, 0), BlockFace.WEST,
                // Code statement
                1f, 1f, 1f,
                // Code statement
                false, false, 0
        // End of a block/expression
        );
        // Calls a method
        player.addPacketToQueue(packet);
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        var placedBlock = instance.getBlock(1, 41, 0);
        // Calls a method
        assertEquals("minecraft:white_wool", placedBlock.name());
    // End of a block/expression
    }

    // Start of a method/block
    private static Stream<Arguments> placeBlockFromAdventureModeParams() {
        // Returns a value to the caller
        return Stream.of(
                // Code statement
                Arguments.of(Block.ACACIA_STAIRS.withProperty("facing", "south"), new BlockPredicates(new BlockPredicate(Block.ACACIA_STAIRS))),
                // Code statement
                Arguments.of(Block.ACACIA_STAIRS.withProperty("facing", "south"), new BlockPredicates(new BlockPredicate(RegistryTag.direct(Block.ACACIA_STAIRS), PropertiesPredicate.exact("facing", "south"), null))),
                // Code statement
                Arguments.of(Block.AMETHYST_BLOCK, new BlockPredicates(new BlockPredicate(Block.AMETHYST_BLOCK)))
        // End of a block/expression
        );
    // End of a block/expression
    }

// End of a block/expression
}
