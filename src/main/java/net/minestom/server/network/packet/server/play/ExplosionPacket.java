// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.WeightedList;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VECTOR3D;

// Déclaration de type (classe/interface/enum/record)
public record ExplosionPacket(
        // Instruction de code
        Point center, float radius, int blockCount,
        // Annotation pour l'élément suivant
        @Nullable Point playerKnockback,
        // Instruction de code
        Particle particle, SoundEvent sound,
        // Instruction de code
        WeightedList<BlockParticleInfo> blockParticles
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ExplosionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VECTOR3D, ExplosionPacket::center,
            // Instruction de code
            NetworkBuffer.FLOAT, ExplosionPacket::radius,
            // Instruction de code
            NetworkBuffer.INT, ExplosionPacket::blockCount,
            // Instruction de code
            VECTOR3D.optional(), ExplosionPacket::playerKnockback,
            // Instruction de code
            Particle.NETWORK_TYPE, ExplosionPacket::particle,
            // Instruction de code
            SoundEvent.NETWORK_TYPE, ExplosionPacket::sound,
            // Instruction de code
            WeightedList.networkType(BlockParticleInfo.SERIALIZER), ExplosionPacket::blockParticles,
            // Instruction de code
            ExplosionPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public record BlockParticleInfo(Particle particle, float scaling, float speed) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<BlockParticleInfo> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                Particle.NETWORK_TYPE, BlockParticleInfo::particle,
                // Instruction de code
                NetworkBuffer.FLOAT, BlockParticleInfo::scaling,
                // Instruction de code
                NetworkBuffer.FLOAT, BlockParticleInfo::speed,
                // Instruction de code
                BlockParticleInfo::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
