// Déclaration du paquet de ce fichier
package net.minestom.server.particle;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.color.AlphaColor;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
final class ParticleImpl {
    // Affecte une valeur
    static final Registry<Particle> REGISTRY = RegistryData.createStaticRegistry(Key.key("particle"),
            // Appelle une méthode
            (namespace, properties) -> defaultParticle(Key.key(namespace), properties.getInt("id")));

    // Début d'une méthode/d'un bloc
    static <P extends Particle> @UnknownNullability P get(String key) {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return (P) REGISTRY.get(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <P extends Particle> @UnknownNullability P get(Key key) {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return (P) REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Particle defaultParticle(Key key, int id) {
        // Renvoie une valeur à l'appelant
        return switch (key.asString()) {
            // Embranchement multiple (switch/case)
            case "minecraft:block" -> new Particle.Block(key, id, Block.STONE);
            // Embranchement multiple (switch/case)
            case "minecraft:block_marker" -> new Particle.BlockMarker(key, id, Block.STONE);
            // Embranchement multiple (switch/case)
            case "minecraft:falling_dust" -> new Particle.FallingDust(key, id, Block.STONE);
            // Embranchement multiple (switch/case)
            case "minecraft:dust_pillar" -> new Particle.DustPillar(key, id, Block.STONE);
            // Embranchement multiple (switch/case)
            case "minecraft:dust" -> new Particle.Dust(key, id, Color.WHITE, 1);
            // Embranchement multiple (switch/case)
            case "minecraft:dust_color_transition" ->
                    // Crée un nouvel objet
                    new Particle.DustColorTransition(key, id, Color.WHITE, Color.WHITE, 1);
            // Embranchement multiple (switch/case)
            case "minecraft:sculk_charge" -> new Particle.SculkCharge(key, id, 0);
            // Embranchement multiple (switch/case)
            case "minecraft:item" -> new Particle.Item(key, id, ItemStack.AIR);
            // Embranchement multiple (switch/case)
            case "minecraft:vibration" ->
                    // Crée un nouvel objet
                    new Particle.Vibration(key, id, Particle.Vibration.SourceType.BLOCK, Vec.ZERO, 0, 0, 0);
            // Embranchement multiple (switch/case)
            case "minecraft:shriek" -> new Particle.Shriek(key, id, 0);
            // Embranchement multiple (switch/case)
            case "minecraft:entity_effect" -> new Particle.EntityEffect(key, id, AlphaColor.WHITE);
            // Embranchement multiple (switch/case)
            case "minecraft:trail" -> new Particle.Trail(key, id, Vec.ZERO, Color.WHITE, 0);
            // Embranchement multiple (switch/case)
            case "minecraft:block_crumble" -> new Particle.BlockCrumble(key, id, Block.STONE);
            // Embranchement multiple (switch/case)
            case "minecraft:tinted_leaves" -> new Particle.TintedLeaves(key, id, AlphaColor.WHITE);
            // Embranchement multiple (switch/case)
            case "minecraft:dragon_breath" -> new Particle.DragonBreath(key, id, 1);
            // Embranchement multiple (switch/case)
            case "minecraft:effect" -> new Particle.Effect(key, id, Color.WHITE, 1);
            // Embranchement multiple (switch/case)
            case "minecraft:flash" -> new Particle.Flash(key, id, AlphaColor.WHITE);
            // Embranchement multiple (switch/case)
            case "minecraft:instant_effect" -> new Particle.InstantEffect(key, id, Color.WHITE, 1);
            // Embranchement multiple (switch/case)
            default -> new Particle.Simple(key, id);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private ParticleImpl() {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
