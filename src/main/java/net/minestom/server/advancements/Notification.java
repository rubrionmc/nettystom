// Package declaration for this file
package net.minestom.server.advancements;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.packet.server.play.AdvancementsPacket;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.List;

/**
 * Represents a toast which can be sent to the player using {@link net.minestom.server.entity.Player#sendNotification(Notification)}.
 */
// Type declaration (class/interface/enum/record)
public record Notification(Component title, FrameType frameType, ItemStack icon) {
    // Assigns a value
    private static final String IDENTIFIER = "minestom:notification";

    // Start of a method/block
    public Notification(Component title, FrameType frameType, Material icon) {
        // Calls a method
        this(title, frameType, ItemStack.of(icon));
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public AdvancementsPacket buildAddPacket() {
        // For an advancement to be shown, it must have all of its criteria achieved (progress 100%)
        // Create a criteria that we can set to 100% achieved.
        // Assigns a value
        final var displayData = new AdvancementsPacket.DisplayData(
                // Code statement
                title, Component.text("Articdive was here. #Minestom"),
                // Code statement
                icon, frameType,
                // Code statement
                0x6, null, 0f, 0f);

        // Assigns a value
        final var criteria = new AdvancementsPacket.Criteria("minestom:some_criteria",
                // Creates a new object
                new AdvancementsPacket.CriterionProgress(System.currentTimeMillis()));

        // Assigns a value
        final var advancement = new AdvancementsPacket.Advancement(null, displayData,
                // Code statement
                List.of(new AdvancementsPacket.Requirement(List.of(criteria.criterionIdentifier()))),
                // Code statement
                false);

        // Calls a method
        final var mapping = new AdvancementsPacket.AdvancementMapping(IDENTIFIER, advancement);
        // Assigns a value
        final var progressMapping = new AdvancementsPacket.ProgressMapping(IDENTIFIER,
                // Creates a new object
                new AdvancementsPacket.AdvancementProgress(List.of(criteria)));

        // Returns a value to the caller
        return new AdvancementsPacket(
                // Code statement
                false,
                // Code statement
                List.of(mapping),
                // Code statement
                List.of(),
                // Code statement
                List.of(progressMapping),
                // Code statement
                true);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public AdvancementsPacket buildRemovePacket() {
        // Returns a value to the caller
        return new AdvancementsPacket(
                // Code statement
                false,
                // Code statement
                List.of(),
                // Code statement
                List.of(IDENTIFIER),
                // Code statement
                List.of(),
                // Code statement
                true);
    // End of a block/expression
    }
// End of a block/expression
}
