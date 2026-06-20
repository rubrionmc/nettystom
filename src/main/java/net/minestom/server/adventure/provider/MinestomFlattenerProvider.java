// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.provider;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.TranslatableComponent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.flattener.ComponentFlattener;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;

// Déclaration de type (classe/interface/enum/record)
final class MinestomFlattenerProvider {
    // Instruction de code
    static final ComponentFlattener INSTANCE;
    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        final ComponentFlattener.Builder builder = ComponentFlattener.basic().toBuilder();

        // handle server-side translations if needed
        // Début d'une méthode/d'un bloc
        builder.complexMapper(TranslatableComponent.class, (component, consumer) -> {
            // Embranchement : vérifie une condition
            if (ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION) {
                // Appelle une méthode
                final Component translated = MinestomAdventure.COMPONENT_TRANSLATOR.apply(component, MinestomAdventure.getDefaultLocale());

                // In case the translated component is also a translatable component, we just leave the key to avoid infinite recursion
                // Embranchement : vérifie une condition
                if (translated instanceof TranslatableComponent translatable) {
                    // Appelle une méthode
                    consumer.accept(Component.text(translatable.key()));
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    consumer.accept(translated);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        INSTANCE = builder.build();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
