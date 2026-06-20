// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceRegisterEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceUnregisterEvent;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class InstanceEventsIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void registerAndUnregisterInstance(Env env) {
        // Appelle une méthode
        var registerListener = env.listen(InstanceRegisterEvent.class);
        // Appelle une méthode
        var unregisterListener = env.listen(InstanceUnregisterEvent.class);

        // Appelle une méthode
        registerListener.followup();
        // Appelle une méthode
        Instance instance = env.process().instance().createInstanceContainer();

        // Appelle une méthode
        unregisterListener.followup();
        // Appelle une méthode
        env.destroyInstance(instance);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
