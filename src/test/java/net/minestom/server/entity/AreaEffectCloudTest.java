// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.AreaEffectCloudMeta;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class AreaEffectCloudTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void createWithDustParticle() {
        // Affecte une valeur
        int colour = 0x5505FF01;

        // Affecte une valeur
        int b = (colour & 0x000000FF);
        // Affecte une valeur
        int g = (colour & 0x0000FF00) >> 8;
        // Affecte une valeur
        int r = (colour & 0x00FF0000) >> 16;

        // Affecte une valeur
        float size = 0.1f;

        // Appelle une méthode
        Particle particle = Particle.DUST.withProperties(new Color(r, g, b), size);

        // Appelle une méthode
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Appelle une méthode
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setParticle(particle);

        // Appelle une méthode
        var gotParticle = meta.getParticle();
        // Appelle une méthode
        assertSame(particle, gotParticle);

        // Affecte une valeur
        Particle.Dust gotData = (Particle.Dust) gotParticle;
        // Appelle une méthode
        assertNotNull(gotData);
        // Appelle une méthode
        assertEquals(r, gotData.color().red());
        // Appelle une méthode
        assertEquals(g, gotData.color().green());
        // Appelle une méthode
        assertEquals(b, gotData.color().blue());
        // Appelle une méthode
        assertEquals(size, gotData.scale());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void createWithDustTransition() {
        // Affecte une valeur
        int colour = 0xFF05FF01;
        // Affecte une valeur
        int colourAfter = 0xFF05FF01;

        // Affecte une valeur
        int b = (colour & 0x000000FF);
        // Affecte une valeur
        int g = (colour & 0x0000FF00) >> 8;
        // Affecte une valeur
        int r = (colour & 0x00FF0000) >> 16;

        // Affecte une valeur
        int b2 = (colourAfter & 0x000000FF);
        // Affecte une valeur
        int g2 = (colourAfter & 0x0000FF00) >> 8;
        // Affecte une valeur
        int r2 = (colourAfter & 0x00FF0000) >> 16;

        // Affecte une valeur
        float size = 0.1f;

        // Appelle une méthode
        Particle particle = Particle.DUST_COLOR_TRANSITION.withProperties(new Color(r, g, b), new Color(r2, g2, b2), size);

        // Appelle une méthode
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Appelle une méthode
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setParticle(particle);

        // Appelle une méthode
        var gotParticle = meta.getParticle();
        // Appelle une méthode
        assertSame(particle, gotParticle);

        // Affecte une valeur
        Particle.DustColorTransition gotData = (Particle.DustColorTransition) gotParticle;
        // Appelle une méthode
        assertNotNull(gotData);
        // Appelle une méthode
        assertEquals(r, gotData.color().red());
        // Appelle une méthode
        assertEquals(g, gotData.color().green());
        // Appelle une méthode
        assertEquals(b, gotData.color().blue());
        // Appelle une méthode
        assertEquals(size, gotData.scale());
        // Appelle une méthode
        assertEquals(r2, gotData.transitionColor().red());
        // Appelle une méthode
        assertEquals(g2, gotData.transitionColor().green());
        // Appelle une méthode
        assertEquals(b2, gotData.transitionColor().blue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void createWithBlockParticle() {
        // Affecte une valeur
        Block block = Block.GRASS_BLOCK;
        // Appelle une méthode
        Particle particle = Particle.BLOCK.withBlock(block);

        // Appelle une méthode
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Appelle une méthode
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setParticle(particle);

        // Appelle une méthode
        var gotParticle = meta.getParticle();
        // Appelle une méthode
        assertSame(particle, gotParticle);

        // Affecte une valeur
        Particle.Block gotBlock = (Particle.Block) gotParticle;
        // Appelle une méthode
        assertSame(block, gotBlock.block());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void createWithBlockMarkerParticle() {
        // Affecte une valeur
        Block block = Block.GRASS_BLOCK;
        // Appelle une méthode
        Particle particle = Particle.BLOCK_MARKER.withBlock(block);

        // Appelle une méthode
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Appelle une méthode
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setParticle(particle);

        // Appelle une méthode
        var gotParticle = meta.getParticle();
        // Appelle une méthode
        assertSame(particle, gotParticle);

        // Affecte une valeur
        Particle.BlockMarker gotBlock = (Particle.BlockMarker) gotParticle;
        // Appelle une méthode
        assertSame(block, gotBlock.block());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void createWithItemParticle() {
        // Appelle une méthode
        Particle particle = Particle.ITEM.withItem(ItemStack.of(Material.ACACIA_LOG));

        // Appelle une méthode
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Appelle une méthode
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setParticle(particle);

        // Appelle une méthode
        var gotParticle = meta.getParticle();
        // Appelle une méthode
        assertSame(particle, gotParticle);

        // Affecte une valeur
        Particle.Item gotBlock = (Particle.Item) gotParticle;
        // Appelle une méthode
        assertSame(Material.ACACIA_LOG, gotBlock.item().material());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void createWithSculkChargeParticle() {
        // Appelle une méthode
        Particle particle = Particle.SCULK_CHARGE.withRoll(3);

        // Appelle une méthode
        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        // Appelle une méthode
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setParticle(particle);

        // Appelle une méthode
        var gotParticle = meta.getParticle();
        // Appelle une méthode
        assertSame(particle, gotParticle);

        // Affecte une valeur
        Particle.SculkCharge gotBlock = (Particle.SculkCharge) gotParticle;
        // Appelle une méthode
        assertEquals(3, gotBlock.roll());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
