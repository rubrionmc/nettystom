// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.BeforeEach;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class ConnectionManagerIntegrationTest {


    // Instruction de code
    private GameProfile[] profiles;

    // Annotation pour l'élément suivant
    @BeforeEach
    // Début d'une méthode/d'un bloc
    public void setup(Env env) {
        // Affecte une valeur
        profiles = new GameProfile[]{
                // Crée un nouvel objet
                new GameProfile(UUID.randomUUID(), "Minestom"),
                // Crée un nouvel objet
                new GameProfile(UUID.randomUUID(), "Notch")};
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPartialFind(Env env) {
        // Appelle une méthode
        Instance instance = env.createEmptyInstance();
        // Appelle une méthode
        Player minestomPlayer = env.createConnection(profiles[0]).connect(instance, Pos.ZERO);
        // Appelle une méthode
        ConnectionManager connectionManager = env.process().connection();

        // Appelle une méthode
        assertEquals(minestomPlayer, connectionManager.findOnlinePlayer("Mine"));
        // Appelle une méthode
        assertNull(connectionManager.findOnlinePlayer("No"));

        // Appelle une méthode
        Player notchPlayer = env.createConnection(profiles[1]).connect(instance, Pos.ZERO);

        // Appelle une méthode
        assertEquals(minestomPlayer, connectionManager.findOnlinePlayer("Mine"));
        // Appelle une méthode
        assertEquals(notchPlayer, connectionManager.findOnlinePlayer("No"));
        // Appelle une méthode
        assertNull(connectionManager.findOnlinePlayer("leo"));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
