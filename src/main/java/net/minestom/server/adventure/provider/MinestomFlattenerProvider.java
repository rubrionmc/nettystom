// Package declaration for this file
package net.minestom.server.adventure.provider;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.TranslatableComponent;
// Import of a required class
import net.kyori.adventure.text.flattener.ComponentFlattener;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;

// Type declaration (class/interface/enum/record)
final class MinestomFlattenerProvider {
    // Code statement
    static final ComponentFlattener INSTANCE;
    // Start of a method/block
    static {
        // Calls a method
        final ComponentFlattener.Builder builder = ComponentFlattener.basic().toBuilder();

        // handle server-side translations if needed
        // Start of a method/block
        builder.complexMapper(TranslatableComponent.class, (component, consumer) -> {
            // Branch: checks a condition
            if (ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION) {
                // Calls a method
                final Component translated = MinestomAdventure.COMPONENT_TRANSLATOR.apply(component, MinestomAdventure.getDefaultLocale());

                // In case the translated component is also a translatable component, we just leave the key to avoid infinite recursion
                // Branch: checks a condition
                if (translated instanceof TranslatableComponent translatable) {
                    // Calls a method
                    consumer.accept(Component.text(translatable.key()));
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    consumer.accept(translated);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Calls a method
        INSTANCE = builder.build();
    // End of a block/expression
    }
// End of a block/expression
}
