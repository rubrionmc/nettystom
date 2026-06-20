// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.particle.Particle;

// Import of a required class
import java.util.Objects;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ParticlePacket(Particle particle, boolean overrideLimiter, boolean longDistance, double x, double y, double z,
                             // Code statement
                             float offsetX, float offsetY, float offsetZ, float maxSpeed,
                             // Start of a method/block
                             int particleCount) implements ServerPacket.Play {
    // Start of a method/block
    public ParticlePacket(Particle particle, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float maxSpeed, int particleCount) {
        // Calls a method
        this(particle, false, false, x, y, z, offsetX, offsetY, offsetZ, maxSpeed, particleCount);
    // End of a block/expression
    }

    // Start of a method/block
    public ParticlePacket(Particle particle, boolean overrideLimiter, boolean longDistance, Point position, Point offset, float maxSpeed, int particleCount) {
        // Calls a method
        this(particle, overrideLimiter, longDistance, position.x(), position.y(), position.z(), (float) offset.x(), (float) offset.y(), (float) offset.z(), maxSpeed, particleCount);
    // End of a block/expression
    }

    // Start of a method/block
    public ParticlePacket(Particle particle, Point position, Point offset, float maxSpeed, int particleCount) {
        // Calls a method
        this(particle, false, false, position, offset, maxSpeed, particleCount);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<ParticlePacket> SERIALIZER = new Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, ParticlePacket value) {
            // Calls a method
            buffer.write(BOOLEAN, value.overrideLimiter);
            // Calls a method
            buffer.write(BOOLEAN, value.longDistance);
            // Calls a method
            buffer.write(DOUBLE, value.x);
            // Calls a method
            buffer.write(DOUBLE, value.y);
            // Calls a method
            buffer.write(DOUBLE, value.z);
            // Calls a method
            buffer.write(FLOAT, value.offsetX);
            // Calls a method
            buffer.write(FLOAT, value.offsetY);
            // Calls a method
            buffer.write(FLOAT, value.offsetZ);
            // Calls a method
            buffer.write(FLOAT, value.maxSpeed);
            // Calls a method
            buffer.write(INT, value.particleCount);
            // Calls a method
            buffer.write(VAR_INT, value.particle.id());
            // Calls a method
            value.particle.writeData(buffer);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ParticlePacket read(NetworkBuffer buffer) {
            // Calls a method
            Boolean overrideLimiter = buffer.read(BOOLEAN);
            // Calls a method
            Boolean longDistance = buffer.read(BOOLEAN);
            // Calls a method
            Double x = buffer.read(DOUBLE);
            // Calls a method
            Double y = buffer.read(DOUBLE);
            // Calls a method
            Double z = buffer.read(DOUBLE);
            // Calls a method
            Float offsetX = buffer.read(FLOAT);
            // Calls a method
            Float offsetY = buffer.read(FLOAT);
            // Calls a method
            Float offsetZ = buffer.read(FLOAT);
            // Calls a method
            Float maxSpeed = buffer.read(FLOAT);
            // Calls a method
            Integer particleCount = buffer.read(INT);

            // Calls a method
            Particle particle = Particle.fromId(buffer.read(VAR_INT));
            // Calls a method
            Objects.requireNonNull(particle);

            // Returns a value to the caller
            return new ParticlePacket(particle.readData(buffer), overrideLimiter, longDistance, x, y, z, offsetX, offsetY, offsetZ, maxSpeed, particleCount);
        // End of a block/expression
        }
    // End of a block/expression
    };
// End of a block/expression
}
