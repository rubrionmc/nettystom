// Package declaration for this file
package net.minestom.server.utils.block;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.BeforeEach;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.server.utils.block.BlockBreakCalculation.breakTicks;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class BlockBreakCalculationTest {
    // Code statement
    private Player player;
    // Code statement
    private Runnable assertInstabreak;
    // Code statement
    private Runnable assertNotQuiteInstabreak;

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testWool() {
        // Calls a method
        player.setItemInMainHand(ItemStack.AIR);
        // Assigns a value
        assertInstabreak = this::assertWoolInstabreak;
        // Assigns a value
        assertNotQuiteInstabreak = this::assertWoolNotQuiteInstabreak;
        // Calls a method
        assertBreak(24, -1, -1, -1, -1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testWoolWithShears() {
        // Calls a method
        player.setItemInMainHand(ItemStack.of(Material.SHEARS));
        // Assigns a value
        assertInstabreak = this::assertWoolInstabreak;
        // Assigns a value
        assertNotQuiteInstabreak = this::assertWoolNotQuiteInstabreak;
        // Calls a method
        assertBreak(4.8, 19, 115, 115, 595);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStone() {
        // Calls a method
        player.setItemInMainHand(ItemStack.AIR);
        // Assigns a value
        assertInstabreak = this::assertStoneInstabreak;
        // Assigns a value
        assertNotQuiteInstabreak = this::assertStoneNotQuiteInstabreak;
        // Calls a method
        assertBreak(150, -1, -1, -1, -1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStoneWithDiamondPickaxe() {
        // Calls a method
        player.setItemInMainHand(ItemStack.of(Material.DIAMOND_PICKAXE));
        // Assigns a value
        assertInstabreak = this::assertStoneInstabreak;
        // Assigns a value
        assertNotQuiteInstabreak = this::assertStoneNotQuiteInstabreak;
        // Calls a method
        assertBreak(5.625, 37, 217, 217, -1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBedrock() {
        // Calls a method
        assertEquals(BlockBreakCalculation.UNBREAKABLE, breakTicks(Block.BEDROCK, player));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testZeroHardnessBlock() {
        // Calls a method
        assertEquals(0, breakTicks(Block.SCAFFOLDING, player));
        // Calls a method
        player.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(0);
        // Calls a method
        assertEquals(BlockBreakCalculation.UNBREAKABLE, breakTicks(Block.SCAFFOLDING, player));
    // End of a block/expression
    }

    // Annotation for the following element
    @BeforeEach
    // Start of a method/block
    void setupPlayer(Env env) {
        // Calls a method
        final var instance = env.createFlatInstance();
        // Calls a method
        player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Calls a method
        player.refreshOnGround(true);
    // End of a block/expression
    }

    // Start of a method/block
    private void assertBreak(double instantBreakSpeed, double efficiency, double efficiencyNotOnGround, double efficiencyInWater, double efficiencyNotOnGroundInWater) {
        // Calls a method
        assertBreakSpeed(instantBreakSpeed);
        // Calls a method
        assertBreakEfficiency(efficiency);
        // Calls a method
        player.refreshOnGround(false);
        // Calls a method
        assertBreakSpeed(instantBreakSpeed * 5);
        // Calls a method
        assertBreakEfficiency(efficiencyNotOnGround);
        // Calls a method
        submerge();
        // Calls a method
        player.refreshOnGround(true);
        // Calls a method
        assertBreakSpeed(instantBreakSpeed * 5);
        // Calls a method
        assertBreakEfficiency(efficiencyInWater);
        // Calls a method
        player.refreshOnGround(false);
        // Calls a method
        assertBreakSpeed(instantBreakSpeed * 5 * 5);
        // Calls a method
        assertBreakEfficiency(efficiencyNotOnGroundInWater);
    // End of a block/expression
    }

    // Start of a method/block
    private void assertBreakEfficiency(double instantBreakEfficiency) {
        // Branch: checks a condition
        if (instantBreakEfficiency == -1) return;
        // Calls a method
        resetBreakSpeed();
        // Calls a method
        updateEfficiency(instantBreakEfficiency);
        // Calls a method
        assertInstabreak.run();
        // Calls a method
        updateEfficiency(instantBreakEfficiency - 0.001);
        // Calls a method
        assertNotQuiteInstabreak.run();
    // End of a block/expression
    }

    // Start of a method/block
    private void assertBreakSpeed(double instantBreakSpeed) {
        // Branch: checks a condition
        if (instantBreakSpeed > Attribute.BLOCK_BREAK_SPEED.maxValue()) return;
        // Calls a method
        resetBreakEfficiency();
        // Calls a method
        updateBreakSpeed(instantBreakSpeed);
        // Calls a method
        assertInstabreak.run();
        // Calls a method
        updateBreakSpeed(instantBreakSpeed - 0.001);
        // Calls a method
        assertNotQuiteInstabreak.run();
    // End of a block/expression
    }

    // Start of a method/block
    private void assertWoolInstabreak() {
        // Calls a method
        assertEquals(0, breakTicks(Block.WHITE_WOOL, player));
        // Calls a method
        assertEquals(0, breakTicks(Block.BLACK_WOOL, player));
    // End of a block/expression
    }

    // Start of a method/block
    private void assertWoolNotQuiteInstabreak() {
        // Calls a method
        assertTrue(breakTicks(Block.WHITE_WOOL, player) > 0);
        // Calls a method
        assertTrue(breakTicks(Block.BLACK_WOOL, player) > 0);
    // End of a block/expression
    }

    // Start of a method/block
    private void assertStoneInstabreak() {
        // Calls a method
        assertEquals(0, breakTicks(Block.STONE, player));
    // End of a block/expression
    }

    // Start of a method/block
    private void assertStoneNotQuiteInstabreak() {
        // Calls a method
        assertTrue(breakTicks(Block.STONE, player) > 0);
    // End of a block/expression
    }

    // Start of a method/block
    private void submerge() {
        // Calls a method
        player.getInstance().setBlock(player.getPosition().add(0, player.getEyeHeight(), 0), Block.WATER);
    // End of a block/expression
    }

    // Start of a method/block
    private void resetBreakSpeed() {
        // Calls a method
        player.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(Attribute.BLOCK_BREAK_SPEED.defaultValue());
    // End of a block/expression
    }

    // Start of a method/block
    private void resetBreakEfficiency() {
        // Calls a method
        player.getAttribute(Attribute.MINING_EFFICIENCY).setBaseValue(Attribute.MINING_EFFICIENCY.defaultValue());
    // End of a block/expression
    }

    // Start of a method/block
    private void updateBreakSpeed(double speed) {
        // Calls a method
        player.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(speed);
    // End of a block/expression
    }

    // Start of a method/block
    private void updateEfficiency(double efficiency) {
        // Calls a method
        player.getAttribute(Attribute.MINING_EFFICIENCY).setBaseValue(efficiency);
    // End of a block/expression
    }
// End of a block/expression
}
