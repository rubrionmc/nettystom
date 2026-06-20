// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;

// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ParticlePacket(Particle particle, boolean overrideLimiter, boolean longDistance, double x, double y, double z,
                             // Instruction de code
                             float offsetX, float offsetY, float offsetZ, float maxSpeed,
                             // Début d'une méthode/d'un bloc
                             int particleCount) implements ServerPacket.Play {
    // Début d'une méthode/d'un bloc
    public ParticlePacket(Particle particle, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float maxSpeed, int particleCount) {
        // Appelle une méthode
        this(particle, false, false, x, y, z, offsetX, offsetY, offsetZ, maxSpeed, particleCount);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ParticlePacket(Particle particle, boolean overrideLimiter, boolean longDistance, Point position, Point offset, float maxSpeed, int particleCount) {
        // Appelle une méthode
        this(particle, overrideLimiter, longDistance, position.x(), position.y(), position.z(), (float) offset.x(), (float) offset.y(), (float) offset.z(), maxSpeed, particleCount);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ParticlePacket(Particle particle, Point position, Point offset, float maxSpeed, int particleCount) {
        // Appelle une méthode
        this(particle, false, false, position, offset, maxSpeed, particleCount);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<ParticlePacket> SERIALIZER = new Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, ParticlePacket value) {
            // Appelle une méthode
            buffer.write(BOOLEAN, value.overrideLimiter);
            // Appelle une méthode
            buffer.write(BOOLEAN, value.longDistance);
            // Appelle une méthode
            buffer.write(DOUBLE, value.x);
            // Appelle une méthode
            buffer.write(DOUBLE, value.y);
            // Appelle une méthode
            buffer.write(DOUBLE, value.z);
            // Appelle une méthode
            buffer.write(FLOAT, value.offsetX);
            // Appelle une méthode
            buffer.write(FLOAT, value.offsetY);
            // Appelle une méthode
            buffer.write(FLOAT, value.offsetZ);
            // Appelle une méthode
            buffer.write(FLOAT, value.maxSpeed);
            // Appelle une méthode
            buffer.write(INT, value.particleCount);
            // Appelle une méthode
            buffer.write(VAR_INT, value.particle.id());
            // Appelle une méthode
            value.particle.writeData(buffer);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ParticlePacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            Boolean overrideLimiter = buffer.read(BOOLEAN);
            // Appelle une méthode
            Boolean longDistance = buffer.read(BOOLEAN);
            // Boucle : répète un bloc
            Double x = buffer.read(DOUBLE);
            // Boucle : répète un bloc
            Double y = buffer.read(DOUBLE);
            // Boucle : répète un bloc
            Double z = buffer.read(DOUBLE);
            // Appelle une méthode
            Float offsetX = buffer.read(FLOAT);
            // Appelle une méthode
            Float offsetY = buffer.read(FLOAT);
            // Appelle une méthode
            Float offsetZ = buffer.read(FLOAT);
            // Appelle une méthode
            Float maxSpeed = buffer.read(FLOAT);
            // Appelle une méthode
            Integer particleCount = buffer.read(INT);

            // Appelle une méthode
            Particle particle = Particle.fromId(buffer.read(VAR_INT));
            // Appelle une méthode
            Objects.requireNonNull(particle);

            // Renvoie une valeur à l'appelant
            return new ParticlePacket(particle.readData(buffer), overrideLimiter, longDistance, x, y, z, offsetX, offsetY, offsetZ, maxSpeed, particleCount);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
// Fin d'un bloc/d'une expression
}
