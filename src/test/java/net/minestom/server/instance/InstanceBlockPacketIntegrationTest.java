// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.BlockChangePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.BlockEntityDataPacket;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertPoint;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class InstanceBlockPacketIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void replaceAir(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var blockPoint = new Vec(5, 41, 0);

        // Appelle une méthode
        assertEquals(Block.AIR, instance.getBlock(blockPoint));

        // Appelle une méthode
        var tracker = connection.trackIncoming();
        // Appelle une méthode
        instance.setBlock(blockPoint, Block.STONE);
        // Début d'une méthode/d'un bloc
        tracker.assertSingle(BlockChangePacket.class, packet -> {
            // Appelle une méthode
            assertPoint(blockPoint, packet.blockPosition());
            // Appelle une méthode
            assertEquals(Block.STONE.stateId(), packet.blockStateId());
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        assertEquals(Block.STONE, instance.getBlock(blockPoint));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void placeBlockEntity(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var blockPoint = new Vec(5, 41, 0);

        // Affecte une valeur
        BlockHandler signHandler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Collection<Tag<?>> getBlockEntityTags() {
                // Renvoie une valeur à l'appelant
                return List.of(Tag.Byte("is_waxed"));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Key getKey() {
                // Renvoie une valeur à l'appelant
                return Key.key("sign");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        assertEquals(Block.AIR, instance.getBlock(blockPoint));

        // Instruction de code
        final Block block;
        // Instruction de code
        final CompoundBinaryTag data;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            data = MinestomAdventure.tagStringIO().asCompound("{\"is_waxed\":1B}");
            // Appelle une méthode
            block = Block.OAK_SIGN.withHandler(signHandler).withNbt(data);
        // Début d'une méthode/d'un bloc
        } catch (Exception ex) {
            // Lève une exception
            throw new RuntimeException(ex);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var blockChangeTracker = connection.trackIncoming(BlockChangePacket.class);
        // Appelle une méthode
        var blockEntityTracker = connection.trackIncoming(BlockEntityDataPacket.class);
        // Appelle une méthode
        instance.setBlock(blockPoint, block);
        // Début d'une méthode/d'un bloc
        blockChangeTracker.assertSingle(packet -> {
            // Appelle une méthode
            assertPoint(blockPoint, packet.blockPosition());
            // Appelle une méthode
            assertEquals(block.stateId(), packet.blockStateId());
        // Fin d'un bloc/d'une expression
        });
        // Début d'une méthode/d'un bloc
        blockEntityTracker.assertSingle(packet -> {
            // Appelle une méthode
            assertPoint(blockPoint, packet.blockPosition());
            // Appelle une méthode
            assertEquals(block.registry().blockEntityType(), packet.type());
            // Appelle une méthode
            assertEquals(data, packet.data());
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        assertEquals(block, instance.getBlock(blockPoint));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
