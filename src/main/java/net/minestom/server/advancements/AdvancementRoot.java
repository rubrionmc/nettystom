// Déclaration du paquet de ce fichier
package net.minestom.server.advancements;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Represents an {@link Advancement} which is the root of an {@link AdvancementTab}.
 * Every tab requires one since advancements needs to be linked to a parent.
 * <p>
 * The difference between this and an {@link Advancement} is that the root is responsible for the tab background.
 */
// Déclaration de type (classe/interface/enum/record)
public class AdvancementRoot extends Advancement {
    // Instruction de code
    public AdvancementRoot(Component title, Component description,
                           // Instruction de code
                           ItemStack icon, FrameType frameType,
                           // Instruction de code
                           float x, float y,
                           // Annotation pour l'élément suivant
                           @Nullable String background) {
        // Accès à l'objet courant/parent
        super(title, description, icon, frameType, x, y);
        // Appelle une méthode
        setBackground(background);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public AdvancementRoot(Component title, Component description,
                           // Instruction de code
                           Material icon, FrameType frameType,
                           // Instruction de code
                           float x, float y,
                           // Annotation pour l'élément suivant
                           @Nullable String background) {
        // Accès à l'objet courant/parent
        super(title, description, icon, frameType, x, y);
        // Appelle une méthode
        setBackground(background);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
