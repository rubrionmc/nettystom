// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.entity.metadata.other.AreaEffectCloudMeta;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class AreaEffectCloudTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void createWithDustParticle() {
        // Assigns a value
        int colour = 0x5505FF01;

        // Calls a method
        int b = (colour & 0x000000FF);
        // Calls a method
        int g = (colour & 0x0000FF00) >> 8;
        // Calls a method
        int r = (colour & 0x00FF0000) >> 16;

        // Assigns a value
        float size = 0.1f;

        // Calls a method
        Particle particle = Particle.DUST.withProperties(new Color(r, g, b), size);

        // Calls a method
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Calls a method
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Calls a method
        meta.setParticle(particle);

        // Calls a method
        var gotParticle = meta.getParticle();
        // Calls a method
        assertSame(particle, gotParticle);

        // Calls a method
        Particle.Dust gotData = (Particle.Dust) gotParticle;
        // Calls a method
        assertNotNull(gotData);
        // Calls a method
        assertEquals(r, gotData.color().red());
        // Calls a method
        assertEquals(g, gotData.color().green());
        // Calls a method
        assertEquals(b, gotData.color().blue());
        // Calls a method
        assertEquals(size, gotData.scale());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void createWithDustTransition() {
        // Assigns a value
        int colour = 0xFF05FF01;
        // Assigns a value
        int colourAfter = 0xFF05FF01;

        // Calls a method
        int b = (colour & 0x000000FF);
        // Calls a method
        int g = (colour & 0x0000FF00) >> 8;
        // Calls a method
        int r = (colour & 0x00FF0000) >> 16;

        // Calls a method
        int b2 = (colourAfter & 0x000000FF);
        // Calls a method
        int g2 = (colourAfter & 0x0000FF00) >> 8;
        // Calls a method
        int r2 = (colourAfter & 0x00FF0000) >> 16;

        // Assigns a value
        float size = 0.1f;

        // Calls a method
        Particle particle = Particle.DUST_COLOR_TRANSITION.withProperties(new Color(r, g, b), new Color(r2, g2, b2), size);

        // Calls a method
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Calls a method
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Calls a method
        meta.setParticle(particle);

        // Calls a method
        var gotParticle = meta.getParticle();
        // Calls a method
        assertSame(particle, gotParticle);

        // Calls a method
        Particle.DustColorTransition gotData = (Particle.DustColorTransition) gotParticle;
        // Calls a method
        assertNotNull(gotData);
        // Calls a method
        assertEquals(r, gotData.color().red());
        // Calls a method
        assertEquals(g, gotData.color().green());
        // Calls a method
        assertEquals(b, gotData.color().blue());
        // Calls a method
        assertEquals(size, gotData.scale());
        // Calls a method
        assertEquals(r2, gotData.transitionColor().red());
        // Calls a method
        assertEquals(g2, gotData.transitionColor().green());
        // Calls a method
        assertEquals(b2, gotData.transitionColor().blue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void createWithBlockParticle() {
        // Assigns a value
        Block block = Block.GRASS_BLOCK;
        // Calls a method
        Particle particle = Particle.BLOCK.withBlock(block);

        // Calls a method
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Calls a method
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Calls a method
        meta.setParticle(particle);

        // Calls a method
        var gotParticle = meta.getParticle();
        // Calls a method
        assertSame(particle, gotParticle);

        // Calls a method
        Particle.Block gotBlock = (Particle.Block) gotParticle;
        // Calls a method
        assertSame(block, gotBlock.block());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void createWithBlockMarkerParticle() {
        // Assigns a value
        Block block = Block.GRASS_BLOCK;
        // Calls a method
        Particle particle = Particle.BLOCK_MARKER.withBlock(block);

        // Calls a method
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Calls a method
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Calls a method
        meta.setParticle(particle);

        // Calls a method
        var gotParticle = meta.getParticle();
        // Calls a method
        assertSame(particle, gotParticle);

        // Calls a method
        Particle.BlockMarker gotBlock = (Particle.BlockMarker) gotParticle;
        // Calls a method
        assertSame(block, gotBlock.block());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void createWithItemParticle() {
        // Calls a method
        Particle particle = Particle.ITEM.withItem(ItemStack.of(Material.ACACIA_LOG));

        // Calls a method
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Calls a method
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Calls a method
        meta.setParticle(particle);

        // Calls a method
        var gotParticle = meta.getParticle();
        // Calls a method
        assertSame(particle, gotParticle);

        // Calls a method
        Particle.Item gotBlock = (Particle.Item) gotParticle;
        // Calls a method
        assertSame(Material.ACACIA_LOG, gotBlock.item().material());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void createWithSculkChargeParticle() {
        // Calls a method
        Particle particle = Particle.SCULK_CHARGE.withRoll(3);

        // Calls a method
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Calls a method
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Calls a method
        meta.setParticle(particle);

        // Calls a method
        var gotParticle = meta.getParticle();
        // Calls a method
        assertSame(particle, gotParticle);

        // Calls a method
        Particle.SculkCharge gotBlock = (Particle.SculkCharge) gotParticle;
        // Calls a method
        assertEquals(3, gotBlock.roll());
    // End of a block/expression
    }
// End of a block/expression
}
