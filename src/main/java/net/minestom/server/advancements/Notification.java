// Déclaration du paquet de ce fichier
package net.minestom.server.advancements;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.AdvancementsPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Represents a toast which can be sent to the player using {@link net.minestom.server.entity.Player#sendNotification(Notification)}.
 */
// Déclaration de type (classe/interface/enum/record)
public record Notification(Component title, FrameType frameType, ItemStack icon) {
    // Affecte une valeur
    private static final String IDENTIFIER = "minestom:notification";

    // Début d'une méthode/d'un bloc
    public Notification(Component title, FrameType frameType, Material icon) {
        // Appelle une méthode
        this(title, frameType, ItemStack.of(icon));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public AdvancementsPacket buildAddPacket() {
        // For an advancement to be shown, it must have all of its criteria achieved (progress 100%)
        // Create a criteria that we can set to 100% achieved.
        // Affecte une valeur
        final var displayData = new AdvancementsPacket.DisplayData(
                // Instruction de code
                title, Component.text("Articdive was here. #Minestom"),
                // Instruction de code
                icon, frameType,
                // Instruction de code
                0x6, null, 0f, 0f);

        // Affecte une valeur
        final var criteria = new AdvancementsPacket.Criteria("minestom:some_criteria",
                // Crée un nouvel objet
                new AdvancementsPacket.CriterionProgress(System.currentTimeMillis()));

        // Affecte une valeur
        final var advancement = new AdvancementsPacket.Advancement(null, displayData,
                // Instruction de code
                List.of(new AdvancementsPacket.Requirement(List.of(criteria.criterionIdentifier()))),
                // Instruction de code
                false);

        // Appelle une méthode
        final var mapping = new AdvancementsPacket.AdvancementMapping(IDENTIFIER, advancement);
        // Affecte une valeur
        final var progressMapping = new AdvancementsPacket.ProgressMapping(IDENTIFIER,
                // Crée un nouvel objet
                new AdvancementsPacket.AdvancementProgress(List.of(criteria)));

        // Renvoie une valeur à l'appelant
        return new AdvancementsPacket(
                // Instruction de code
                false,
                // Instruction de code
                List.of(mapping),
                // Instruction de code
                List.of(),
                // Instruction de code
                List.of(progressMapping),
                // Instruction de code
                true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public AdvancementsPacket buildRemovePacket() {
        // Renvoie une valeur à l'appelant
        return new AdvancementsPacket(
                // Instruction de code
                false,
                // Instruction de code
                List.of(),
                // Instruction de code
                List.of(IDENTIFIER),
                // Instruction de code
                List.of(),
                // Instruction de code
                true);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
