// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;

// Déclaration de type (classe/interface/enum/record)
public record AmbientParticle(Particle particle, float probability) {
    // Affecte une valeur
    public static final Codec<AmbientParticle> CODEC = StructCodec.struct(
            // Instruction de code
            "particle", Particle.CODEC, AmbientParticle::particle,
            // Instruction de code
            "probability", Codec.FLOAT, AmbientParticle::probability,
            // Instruction de code
            AmbientParticle::new);
// Fin d'un bloc/d'une expression
}
