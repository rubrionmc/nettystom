// Déclaration du paquet de ce fichier
package net.minestom.server.utils.block;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.BeforeEach;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.server.utils.block.BlockBreakCalculation.breakTicks;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class BlockBreakCalculationTest {
    // Instruction de code
    private Player player;
    // Instruction de code
    private Runnable assertInstabreak;
    // Instruction de code
    private Runnable assertNotQuiteInstabreak;

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testWool() {
        // Appelle une méthode
        player.setItemInMainHand(ItemStack.AIR);
        // Affecte une valeur
        assertInstabreak = this::assertWoolInstabreak;
        // Affecte une valeur
        assertNotQuiteInstabreak = this::assertWoolNotQuiteInstabreak;
        // Appelle une méthode
        assertBreak(24, -1, -1, -1, -1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testWoolWithShears() {
        // Appelle une méthode
        player.setItemInMainHand(ItemStack.of(Material.SHEARS));
        // Affecte une valeur
        assertInstabreak = this::assertWoolInstabreak;
        // Affecte une valeur
        assertNotQuiteInstabreak = this::assertWoolNotQuiteInstabreak;
        // Appelle une méthode
        assertBreak(4.8, 19, 115, 115, 595);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStone() {
        // Appelle une méthode
        player.setItemInMainHand(ItemStack.AIR);
        // Affecte une valeur
        assertInstabreak = this::assertStoneInstabreak;
        // Affecte une valeur
        assertNotQuiteInstabreak = this::assertStoneNotQuiteInstabreak;
        // Appelle une méthode
        assertBreak(150, -1, -1, -1, -1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStoneWithDiamondPickaxe() {
        // Appelle une méthode
        player.setItemInMainHand(ItemStack.of(Material.DIAMOND_PICKAXE));
        // Affecte une valeur
        assertInstabreak = this::assertStoneInstabreak;
        // Affecte une valeur
        assertNotQuiteInstabreak = this::assertStoneNotQuiteInstabreak;
        // Appelle une méthode
        assertBreak(5.625, 37, 217, 217, -1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBedrock() {
        // Appelle une méthode
        assertEquals(BlockBreakCalculation.UNBREAKABLE, breakTicks(Block.BEDROCK, player));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testZeroHardnessBlock() {
        // Appelle une méthode
        assertEquals(0, breakTicks(Block.SCAFFOLDING, player));
        // Appelle une méthode
        player.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(0);
        // Appelle une méthode
        assertEquals(BlockBreakCalculation.UNBREAKABLE, breakTicks(Block.SCAFFOLDING, player));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @BeforeEach
    // Début d'une méthode/d'un bloc
    void setupPlayer(Env env) {
        // Appelle une méthode
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        player.refreshOnGround(true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void assertBreak(double instantBreakSpeed, double efficiency, double efficiencyNotOnGround, double efficiencyInWater, double efficiencyNotOnGroundInWater) {
        // Appelle une méthode
        assertBreakSpeed(instantBreakSpeed);
        // Appelle une méthode
        assertBreakEfficiency(efficiency);
        // Appelle une méthode
        player.refreshOnGround(false);
        // Appelle une méthode
        assertBreakSpeed(instantBreakSpeed * 5);
        // Appelle une méthode
        assertBreakEfficiency(efficiencyNotOnGround);
        // Appelle une méthode
        submerge();
        // Appelle une méthode
        player.refreshOnGround(true);
        // Appelle une méthode
        assertBreakSpeed(instantBreakSpeed * 5);
        // Appelle une méthode
        assertBreakEfficiency(efficiencyInWater);
        // Appelle une méthode
        player.refreshOnGround(false);
        // Appelle une méthode
        assertBreakSpeed(instantBreakSpeed * 5 * 5);
        // Appelle une méthode
        assertBreakEfficiency(efficiencyNotOnGroundInWater);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void assertBreakEfficiency(double instantBreakEfficiency) {
        // Embranchement : vérifie une condition
        if (instantBreakEfficiency == -1) return;
        // Appelle une méthode
        resetBreakSpeed();
        // Appelle une méthode
        updateEfficiency(instantBreakEfficiency);
        // Appelle une méthode
        assertInstabreak.run();
        // Appelle une méthode
        updateEfficiency(instantBreakEfficiency - 0.001);
        // Appelle une méthode
        assertNotQuiteInstabreak.run();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void assertBreakSpeed(double instantBreakSpeed) {
        // Embranchement : vérifie une condition
        if (instantBreakSpeed > Attribute.BLOCK_BREAK_SPEED.maxValue()) return;
        // Appelle une méthode
        resetBreakEfficiency();
        // Appelle une méthode
        updateBreakSpeed(instantBreakSpeed);
        // Appelle une méthode
        assertInstabreak.run();
        // Appelle une méthode
        updateBreakSpeed(instantBreakSpeed - 0.001);
        // Appelle une méthode
        assertNotQuiteInstabreak.run();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void assertWoolInstabreak() {
        // Appelle une méthode
        assertEquals(0, breakTicks(Block.WHITE_WOOL, player));
        // Appelle une méthode
        assertEquals(0, breakTicks(Block.BLACK_WOOL, player));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void assertWoolNotQuiteInstabreak() {
        // Appelle une méthode
        assertTrue(breakTicks(Block.WHITE_WOOL, player) > 0);
        // Appelle une méthode
        assertTrue(breakTicks(Block.BLACK_WOOL, player) > 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void assertStoneInstabreak() {
        // Appelle une méthode
        assertEquals(0, breakTicks(Block.STONE, player));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void assertStoneNotQuiteInstabreak() {
        // Appelle une méthode
        assertTrue(breakTicks(Block.STONE, player) > 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void submerge() {
        // Appelle une méthode
        player.getInstance().setBlock(player.getPosition().add(0, player.getEyeHeight(), 0), Block.WATER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void resetBreakSpeed() {
        // Appelle une méthode
        player.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(Attribute.BLOCK_BREAK_SPEED.defaultValue());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void resetBreakEfficiency() {
        // Appelle une méthode
        player.getAttribute(Attribute.MINING_EFFICIENCY).setBaseValue(Attribute.MINING_EFFICIENCY.defaultValue());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void updateBreakSpeed(double speed) {
        // Appelle une méthode
        player.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(speed);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void updateEfficiency(double efficiency) {
        // Appelle une méthode
        player.getAttribute(Attribute.MINING_EFFICIENCY).setBaseValue(efficiency);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
