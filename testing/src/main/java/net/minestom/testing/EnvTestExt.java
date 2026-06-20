// Déclaration du paquet de ce fichier
package net.minestom.testing;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.junit.jupiter.api.extension.*;

// Déclaration de type (classe/interface/enum/record)
final class EnvTestExt implements
        // Instruction de code
        BeforeEachCallback,
        // Instruction de code
        AfterEachCallback,
        // Début d'une méthode/d'un bloc
        ParameterResolver {

    // Affecte une valeur
    private static final String ENV_KEY = "minestom.env";

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void beforeEach(ExtensionContext context) {
        // Appelle une méthode
        System.setProperty("minestom.viewable-packet", "false");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public Object resolveParameter(ParameterContext parameterContext,
                                   // Début d'une méthode/d'un bloc
                                   ExtensionContext extensionContext) {
        // Renvoie une valeur à l'appelant
        return extensionContext.getStore(ExtensionContext.Namespace.create(getClass()))
                // Instruction de code
                .getOrComputeIfAbsent(ENV_KEY,
                        // Instruction de code
                        key -> new EnvImpl(MinecraftServer.updateProcess()),
                        // Instruction de code
                        EnvImpl.class);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void afterEach(ExtensionContext context) {
        // Appelle une méthode
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(getClass()));
        // Appelle une méthode
        EnvImpl env = store.remove(ENV_KEY, EnvImpl.class);
        // Embranchement : vérifie une condition
        if (env != null) env.cleanup();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public boolean supportsParameter(ParameterContext parameterContext,
                                     // Début d'une méthode/d'un bloc
                                     ExtensionContext extensionContext) {
        // Renvoie une valeur à l'appelant
        return parameterContext.getParameter().getType() == Env.class;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
