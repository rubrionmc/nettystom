// Package declaration for this file
package net.minestom.server.particle;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.color.AlphaColor;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
final class ParticleImpl {
    // Assigns a value
    static final Registry<Particle> REGISTRY = RegistryData.createStaticRegistry(Key.key("particle"),
            // Calls a method
            (namespace, properties) -> defaultParticle(Key.key(namespace), properties.getInt("id")));

    // Start of a method/block
    static <P extends Particle> @UnknownNullability P get(String key) {
        //noinspection unchecked
        // Returns a value to the caller
        return (P) REGISTRY.get(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static <P extends Particle> @UnknownNullability P get(Key key) {
        //noinspection unchecked
        // Returns a value to the caller
        return (P) REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    private static Particle defaultParticle(Key key, int id) {
        // Returns a value to the caller
        return switch (key.asString()) {
            // Multiple branching (switch/case)
            case "minecraft:block" -> new Particle.Block(key, id, Block.STONE);
            // Multiple branching (switch/case)
            case "minecraft:block_marker" -> new Particle.BlockMarker(key, id, Block.STONE);
            // Multiple branching (switch/case)
            case "minecraft:falling_dust" -> new Particle.FallingDust(key, id, Block.STONE);
            // Multiple branching (switch/case)
            case "minecraft:dust_pillar" -> new Particle.DustPillar(key, id, Block.STONE);
            // Multiple branching (switch/case)
            case "minecraft:dust" -> new Particle.Dust(key, id, Color.WHITE, 1);
            // Multiple branching (switch/case)
            case "minecraft:dust_color_transition" ->
                    // Creates a new object
                    new Particle.DustColorTransition(key, id, Color.WHITE, Color.WHITE, 1);
            // Multiple branching (switch/case)
            case "minecraft:sculk_charge" -> new Particle.SculkCharge(key, id, 0);
            // Multiple branching (switch/case)
            case "minecraft:item" -> new Particle.Item(key, id, ItemStack.AIR);
            // Multiple branching (switch/case)
            case "minecraft:vibration" ->
                    // Creates a new object
                    new Particle.Vibration(key, id, Particle.Vibration.SourceType.BLOCK, Vec.ZERO, 0, 0, 0);
            // Multiple branching (switch/case)
            case "minecraft:shriek" -> new Particle.Shriek(key, id, 0);
            // Multiple branching (switch/case)
            case "minecraft:entity_effect" -> new Particle.EntityEffect(key, id, AlphaColor.WHITE);
            // Multiple branching (switch/case)
            case "minecraft:trail" -> new Particle.Trail(key, id, Vec.ZERO, Color.WHITE, 0);
            // Multiple branching (switch/case)
            case "minecraft:block_crumble" -> new Particle.BlockCrumble(key, id, Block.STONE);
            // Multiple branching (switch/case)
            case "minecraft:tinted_leaves" -> new Particle.TintedLeaves(key, id, AlphaColor.WHITE);
            // Multiple branching (switch/case)
            case "minecraft:dragon_breath" -> new Particle.DragonBreath(key, id, 1);
            // Multiple branching (switch/case)
            case "minecraft:effect" -> new Particle.Effect(key, id, Color.WHITE, 1);
            // Multiple branching (switch/case)
            case "minecraft:flash" -> new Particle.Flash(key, id, AlphaColor.WHITE);
            // Multiple branching (switch/case)
            case "minecraft:instant_effect" -> new Particle.InstantEffect(key, id, Color.WHITE, 1);
            // Multiple branching (switch/case)
            default -> new Particle.Simple(key, id);
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private ParticleImpl() {
    // End of a block/expression
    }
// End of a block/expression
}
