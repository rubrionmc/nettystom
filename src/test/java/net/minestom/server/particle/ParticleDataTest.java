// Package declaration for this file
package net.minestom.server.particle;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.play.ParticlePacket;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertThrows;

// Type declaration (class/interface/enum/record)
public class ParticleDataTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDustParticleDefault() {
        // Assigns a value
        Particle particle = Particle.DUST;
        // Calls a method
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Calls a method
        assertDoesNotThrow(() -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDustParticleInvalid() {
        // Calls a method
        var particle = Particle.DUST.withProperties(null, 1);
        // Calls a method
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Calls a method
        assertThrows(NullPointerException.class, () -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testParticleValid() {
        // Assigns a value
        var particle = Particle.ENTITY_EFFECT;
        // Calls a method
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Calls a method
        assertDoesNotThrow(() -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testParticleData() {
        // Assigns a value
        var particle = Particle.ENTITY_EFFECT;
        // Calls a method
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Calls a method
        assertDoesNotThrow(() -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void invalidBlock() {
        // Calls a method
        var particle = Particle.BLOCK.withBlock(null);
        // Calls a method
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Calls a method
        assertThrows(NullPointerException.class, () -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // End of a block/expression
    }
// End of a block/expression
}
