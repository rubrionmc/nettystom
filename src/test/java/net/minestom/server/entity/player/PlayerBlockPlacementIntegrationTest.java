// Déclaration du paquet de ce fichier
package net.minestom.server.entity.player;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.predicate.BlockPredicate;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.predicate.PropertiesPredicate;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.BlockPredicates;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerBlockPlacementPacket;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.Arguments;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;

// Import d'une classe nécessaire
import java.util.stream.Stream;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerBlockPlacementIntegrationTest {

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("placeBlockFromAdventureModeParams")
    // Début d'une méthode/d'un bloc
    public void placeBlockFromAdventureMode(Block baseBlock, BlockPredicates canPlaceOn, Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        instance.setBlock(2, 41, 0, baseBlock);

        // Appelle une méthode
        player.setGameMode(GameMode.ADVENTURE);
        // Appelle une méthode
        player.setItemInMainHand(ItemStack.builder(Material.WHITE_WOOL).set(DataComponents.CAN_PLACE_ON, canPlaceOn).build());

        // Affecte une valeur
        var packet = new ClientPlayerBlockPlacementPacket(
                // Instruction de code
                PlayerHand.MAIN, new Pos(2, 41, 0), BlockFace.WEST,
                // Instruction de code
                1f, 1f, 1f,
                // Instruction de code
                false, false, 0
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        player.addPacketToQueue(packet);
        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        var placedBlock = instance.getBlock(1, 41, 0);
        // Appelle une méthode
        assertEquals("minecraft:white_wool", placedBlock.name());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Stream<Arguments> placeBlockFromAdventureModeParams() {
        // Renvoie une valeur à l'appelant
        return Stream.of(
                // Instruction de code
                Arguments.of(Block.ACACIA_STAIRS.withProperty("facing", "south"), new BlockPredicates(new BlockPredicate(Block.ACACIA_STAIRS))),
                // Instruction de code
                Arguments.of(Block.ACACIA_STAIRS.withProperty("facing", "south"), new BlockPredicates(new BlockPredicate(RegistryTag.direct(Block.ACACIA_STAIRS), PropertiesPredicate.exact("facing", "south"), null))),
                // Instruction de code
                Arguments.of(Block.AMETHYST_BLOCK, new BlockPredicates(new BlockPredicate(Block.AMETHYST_BLOCK)))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
