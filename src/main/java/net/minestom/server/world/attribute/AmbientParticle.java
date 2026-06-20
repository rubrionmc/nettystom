// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.particle.Particle;

// Type declaration (class/interface/enum/record)
public record AmbientParticle(Particle particle, float probability) {
    // Assigns a value
    public static final Codec<AmbientParticle> CODEC = StructCodec.struct(
            // Code statement
            "particle", Particle.CODEC, AmbientParticle::particle,
            // Code statement
            "probability", Codec.FLOAT, AmbientParticle::probability,
            // Code statement
            AmbientParticle::new);
// End of a block/expression
}
