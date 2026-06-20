// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.provider;

// Import d'une classe nécessaire
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
// Import d'une classe nécessaire
import net.kyori.adventure.text.logger.slf4j.ComponentLoggerProvider;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Annotation pour l'élément suivant
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Déclaration de type (classe/interface/enum/record)
public class MinestomComponentLoggerProvider implements ComponentLoggerProvider {
    // Appelle une méthode
    private static final ANSIComponentSerializer SERIALIZER = ANSIComponentSerializer.ansi();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ComponentLogger logger(LoggerHelper helper, String name) {
        // Renvoie une valeur à l'appelant
        return helper.delegating(LoggerFactory.getLogger(name), SERIALIZER::serialize);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
