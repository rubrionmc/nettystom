// Package declaration for this file
package net.minestom.server.entity.pathfinding;

// Import of a required class
import net.minestom.server.coordinate.ChunkRange;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.pathfinding.generators.GroundNodeGenerator;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PathfinderIntegrationTest {

    /**
     * Validate that the path is valid
     * Currently only checks to make sure path is not null, and that nodes are not inside blocks
     *
     * @param nodes the nodes to validate
     * @return true if the path is valid
     */
    // Start of a method/block
    private boolean validateNodes(List<PNode> nodes, Instance instance) {
        // Branch: checks a condition
        if (nodes == null) fail("Path is null");
        // Branch: checks a condition
        if (nodes.isEmpty()) fail("Path is empty");

        // Start of a method/block
        nodes.forEach((node) -> {
            // Branch: checks a condition
            if (instance.getBlock(node.blockX(), node.blockY(), node.blockZ()).isSolid()) {
                // Calls a method
                fail("Node is inside a block");
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testTall(Env env) {
        // Calls a method
        var i = env.createFlatInstance();

        // Calls a method
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Calls a method
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        zombie.setInstance(i, new Pos(0, 40, 0));
        // Calls a method
        zombie.setBoundingBox(3f, 6.5f, 3f);

        // Calls a method
        i.setBlock(1, 46, 7, Block.STONE);

        // Calls a method
        Navigator nav = new Navigator(zombie);
        // Calls a method
        nav.setPathTo(new Pos(0, 40, 10));
        // Loop: repeats a block
        while (nav.getState() == PPath.State.CALCULATING) {
        // End of a block/expression
        }

        // Calls a method
        assertNotNull(nav.getNodes());
        // Calls a method
        validateNodes(nav.getNodes(), i);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStraightLine(Env env) {
        // Calls a method
        var i = env.createFlatInstance();

        // Calls a method
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Calls a method
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        zombie.setInstance(i, new Pos(0, 40, 0));

        // Calls a method
        Navigator nav = new Navigator(zombie);
        // Calls a method
        nav.setPathTo(new Pos(0, 40, 10));
        // Loop: repeats a block
        while (nav.getState() == PPath.State.CALCULATING) {
        // End of a block/expression
        }

        // Calls a method
        assertNotNull(nav.getNodes());
        // Calls a method
        validateNodes(nav.getNodes(), i);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testShort(Env env) {
        // Calls a method
        var i = env.createFlatInstance();

        // Calls a method
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Calls a method
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        zombie.setInstance(i, new Pos(0, 40, 0));

        // Calls a method
        Navigator nav = new Navigator(zombie);
        // Calls a method
        nav.setPathTo(new Pos(2, 40, 2));

        // Loop: repeats a block
        while (nav.getState() == PPath.State.CALCULATING) {
        // End of a block/expression
        }

        // Calls a method
        assertNotNull(nav.getNodes());
        // Calls a method
        validateNodes(nav.getNodes(), i);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBug(Env env) {
        // Calls a method
        var i = env.createFlatInstance();

        // Calls a method
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Calls a method
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        zombie.setInstance(i, new Pos(43.972731367054266, 40.000000000040735, -39.89155139999369));

        // Calls a method
        zombie.tick(0);
        // Calls a method
        zombie.tick(0);

        // Calls a method
        Navigator nav = new Navigator(zombie);
        // Calls a method
        nav.setPathTo(new Pos(43.5, 40, -41.5));

        // Loop: repeats a block
        while (nav.getState() == PPath.State.CALCULATING) {
        // End of a block/expression
        }

        // Calls a method
        assertNotNull(nav.getNodes());
        // Calls a method
        validateNodes(nav.getNodes(), i);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPFNodeEqual(Env env) {
        // Calls a method
        PNode node1 = new PNode(new Pos(0.777, 0, 0), 2, 0, PNode.Type.WALK, null);
        // Calls a method
        PNode node2 = new PNode(new Pos(0.777, 0, 0), 0, 3, PNode.Type.WALK, node1);

        // Calls a method
        Set<PNode> nodes = new HashSet<>();
        // Calls a method
        nodes.add(node1);
        // Calls a method
        nodes.add(node2);

        // Calls a method
        assertEquals(node1, node2);
        // Calls a method
        assertEquals(1, nodes.size());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStraightLineBlocked(Env env) {
        // Calls a method
        var i = env.createFlatInstance();

        // Calls a method
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Calls a method
        i.setBlock(-6, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(-5, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(-4, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(-3, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(-2, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(-1, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(0, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(1, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(2, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(3, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(4, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(5, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(6, 40, 5, Block.STONE);
        // Calls a method
        i.setBlock(7, 40, 5, Block.STONE);

        // Calls a method
        i.setBlock(-6, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(-5, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(-4, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(-3, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(-2, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(-1, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(0, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(1, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(2, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(3, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(4, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(5, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(6, 41, 5, Block.STONE);
        // Calls a method
        i.setBlock(7, 41, 5, Block.STONE);

        // Calls a method
        var zombie = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        zombie.setInstance(i, new Pos(0, 40, 0));
        // Calls a method
        zombie.setBoundingBox(zombie.getBoundingBox().expand(4f, 4f, 4f));

        // Calls a method
        Navigator nav = new Navigator(zombie);
        // Calls a method
        nav.setPathTo(new Pos(0, 40, 10));
        // Loop: repeats a block
        while (nav.getState() == PPath.State.CALCULATING) {
        // End of a block/expression
        }

        // Calls a method
        assertNotNull(nav.getNodes());
        // Calls a method
        validateNodes(nav.getNodes(), i);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testGravitySnap(Env env) {
        // Calls a method
        var i = env.createFlatInstance();

        // Calls a method
        ChunkRange.chunksInRange(0, 0, 10, (x, z) -> i.loadChunk(x, z).join());

        // Calls a method
        var zombie = new LivingEntity(EntityType.ZOMBIE);

        // Calls a method
        var nodeGenerator = new GroundNodeGenerator();

        // Calls a method
        var snapped = nodeGenerator.gravitySnap(i, -140.74433362614695, 40.58268292446131, 18.87966960447388, zombie.getBoundingBox(), 100);
        // Calls a method
        assertTrue(snapped.isPresent());
        // Calls a method
        assertEquals(40.0, snapped.getAsDouble());
    // End of a block/expression
    }
// End of a block/expression
}
