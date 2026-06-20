// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.ChunkRange;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.generators.GroundNodeGenerator;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PathfinderIntegrationTest {

    /**
     * Validate that the path is valid
     * Currently only checks to make sure path is not null, and that nodes are not inside blocks
     *
     * @param nodes the nodes to validate
     * @return true if the path is valid
     */
    // Début d'une méthode/d'un bloc
    private boolean validateNodes(List<PNode> nodes, Instance instance) {
        // Embranchement : vérifie une condition
        if (nodes == null) fail("Path is null");
        // Embranchement : vérifie une condition
        if (nodes.isEmpty()) fail("Path is empty");

        // Début d'une méthode/d'un bloc
        nodes.forEach((node) -> {
            // Embranchement : vérifie une condition
            if (instance.getBlock(node.blockX(), node.blockY(), node.blockZ()).isSolid()) {
                // Appelle une méthode
                fail("Node is inside a block");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testTall(Env env) {
        // Appelle une méthode
        var i = env.createFlatInstance();

        // Appelle une méthode
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Appelle une méthode
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        zombie.setInstance(i, new Pos(0, 40, 0));
        // Appelle une méthode
        zombie.setBoundingBox(3f, 6.5f, 3f);

        // Appelle une méthode
        i.setBlock(1, 46, 7, Block.STONE);

        // Appelle une méthode
        Navigator nav = new Navigator(zombie);
        // Appelle une méthode
        nav.setPathTo(new Pos(0, 40, 10));
        // Boucle : répète un bloc
        while (nav.getState() == PPath.State.CALCULATING) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertNotNull(nav.getNodes());
        // Appelle une méthode
        validateNodes(nav.getNodes(), i);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStraightLine(Env env) {
        // Appelle une méthode
        var i = env.createFlatInstance();

        // Appelle une méthode
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Appelle une méthode
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        zombie.setInstance(i, new Pos(0, 40, 0));

        // Appelle une méthode
        Navigator nav = new Navigator(zombie);
        // Appelle une méthode
        nav.setPathTo(new Pos(0, 40, 10));
        // Boucle : répète un bloc
        while (nav.getState() == PPath.State.CALCULATING) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertNotNull(nav.getNodes());
        // Appelle une méthode
        validateNodes(nav.getNodes(), i);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testShort(Env env) {
        // Appelle une méthode
        var i = env.createFlatInstance();

        // Appelle une méthode
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Appelle une méthode
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        zombie.setInstance(i, new Pos(0, 40, 0));

        // Appelle une méthode
        Navigator nav = new Navigator(zombie);
        // Appelle une méthode
        nav.setPathTo(new Pos(2, 40, 2));

        // Boucle : répète un bloc
        while (nav.getState() == PPath.State.CALCULATING) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertNotNull(nav.getNodes());
        // Appelle une méthode
        validateNodes(nav.getNodes(), i);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBug(Env env) {
        // Appelle une méthode
        var i = env.createFlatInstance();

        // Appelle une méthode
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Appelle une méthode
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        zombie.setInstance(i, new Pos(43.972731367054266, 40.000000000040735, -39.89155139999369));

        // Appelle une méthode
        zombie.tick(0);
        // Appelle une méthode
        zombie.tick(0);

        // Appelle une méthode
        Navigator nav = new Navigator(zombie);
        // Appelle une méthode
        nav.setPathTo(new Pos(43.5, 40, -41.5));

        // Boucle : répète un bloc
        while (nav.getState() == PPath.State.CALCULATING) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertNotNull(nav.getNodes());
        // Appelle une méthode
        validateNodes(nav.getNodes(), i);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPFNodeEqual(Env env) {
        // Appelle une méthode
        PNode node1 = new PNode(new Pos(0.777, 0, 0), 2, 0, PNode.Type.WALK, null);
        // Appelle une méthode
        PNode node2 = new PNode(new Pos(0.777, 0, 0), 0, 3, PNode.Type.WALK, node1);

        // Appelle une méthode
        Set<PNode> nodes = new HashSet<>();
        // Appelle une méthode
        nodes.add(node1);
        // Appelle une méthode
        nodes.add(node2);

        // Appelle une méthode
        assertEquals(node1, node2);
        // Appelle une méthode
        assertEquals(1, nodes.size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStraightLineBlocked(Env env) {
        // Appelle une méthode
        var i = env.createFlatInstance();

        // Appelle une méthode
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Appelle une méthode
        i.setBlock(-6, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-5, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-4, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-3, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-2, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-1, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(0, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(1, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(2, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(3, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(4, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(5, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(6, 40, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(7, 40, 5, Block.STONE);

        // Appelle une méthode
        i.setBlock(-6, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-5, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-4, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-3, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-2, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(-1, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(0, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(1, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(2, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(3, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(4, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(5, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(6, 41, 5, Block.STONE);
        // Appelle une méthode
        i.setBlock(7, 41, 5, Block.STONE);

        // Appelle une méthode
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        zombie.setInstance(i, new Pos(0, 40, 0));
        // Appelle une méthode
        zombie.setBoundingBox(zombie.getBoundingBox().expand(4f, 4f, 4f));

        // Appelle une méthode
        Navigator nav = new Navigator(zombie);
        // Appelle une méthode
        nav.setPathTo(new Pos(0, 40, 10));
        // Boucle : répète un bloc
        while (nav.getState() == PPath.State.CALCULATING) {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertNotNull(nav.getNodes());
        // Appelle une méthode
        validateNodes(nav.getNodes(), i);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testGravitySnap(Env env) {
        // Appelle une méthode
        var i = env.createFlatInstance();

        // Appelle une méthode
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Appelle une méthode
        var zombie = new LivingEntity(EntityType.ZOMBIE);

        // Appelle une méthode
        var nodeGenerator = new GroundNodeGenerator();

        // Appelle une méthode
        var snapped = nodeGenerator.gravitySnap(i, -140.74433362614695, 40.58268292446131, 18.87966960447388, zombie.getBoundingBox(), 100);
        // Appelle une méthode
        assertTrue(snapped.isPresent());
        // Appelle une méthode
        assertEquals(40.0, snapped.getAsDouble());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
