// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.WeightedList;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VECTOR3D;

// Type declaration (class/interface/enum/record)
public record ExplosionPacket(
        // Code statement
        Point center, float radius, int blockCount,
        // Annotation for the following element
        @Nullable Point playerKnockback,
        // Code statement
        Particle particle, SoundEvent sound,
        // Code statement
        WeightedList<BlockParticleInfo> blockParticles
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ExplosionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VECTOR3D, ExplosionPacket::center,
            // Code statement
            NetworkBuffer.FLOAT, ExplosionPacket::radius,
            // Code statement
            NetworkBuffer.INT, ExplosionPacket::blockCount,
            // Code statement
            VECTOR3D.optional(), ExplosionPacket::playerKnockback,
            // Code statement
            Particle.NETWORK_TYPE, ExplosionPacket::particle,
            // Code statement
            SoundEvent.NETWORK_TYPE, ExplosionPacket::sound,
            // Code statement
            WeightedList.networkType(BlockParticleInfo.SERIALIZER), ExplosionPacket::blockParticles,
            // Code statement
            ExplosionPacket::new);

    // Type declaration (class/interface/enum/record)
    public record BlockParticleInfo(Particle particle, float scaling, float speed) {
        // Assigns a value
        public static final NetworkBuffer.Type<BlockParticleInfo> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                Particle.NETWORK_TYPE, BlockParticleInfo::particle,
                // Code statement
                NetworkBuffer.FLOAT, BlockParticleInfo::scaling,
                // Code statement
                NetworkBuffer.FLOAT, BlockParticleInfo::speed,
                // Code statement
                BlockParticleInfo::new);
    // End of a block/expression
    }
// End of a block/expression
}
