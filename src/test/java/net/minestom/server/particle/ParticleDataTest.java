// Déclaration du paquet de ce fichier
package net.minestom.server.particle;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ParticlePacket;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertThrows;

// Déclaration de type (classe/interface/enum/record)
public class ParticleDataTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDustParticleDefault() {
        // Affecte une valeur
        Particle particle = Particle.DUST;
        // Appelle une méthode
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Appelle une méthode
        assertDoesNotThrow(() -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDustParticleInvalid() {
        // Appelle une méthode
        var particle = Particle.DUST.withProperties(null, 1);
        // Appelle une méthode
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testParticleValid() {
        // Affecte une valeur
        var particle = Particle.ENTITY_EFFECT;
        // Appelle une méthode
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Appelle une méthode
        assertDoesNotThrow(() -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testParticleData() {
        // Affecte une valeur
        var particle = Particle.ENTITY_EFFECT;
        // Appelle une méthode
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Appelle une méthode
        assertDoesNotThrow(() -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidBlock() {
        // Appelle une méthode
        var particle = Particle.BLOCK.withBlock(null);
        // Appelle une méthode
        ParticlePacket packet = new ParticlePacket(particle, false, true, 0, 0, 0, 0, 0, 0, 0, 0);
        // Appelle une méthode
        assertThrows(NullPointerException.class, () -> ParticlePacket.SERIALIZER.write(NetworkBuffer.resizableBuffer(), packet));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
