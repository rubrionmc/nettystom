// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.recipe.Ingredient;
// Import of a required class
import net.minestom.server.recipe.RecipeBookCategory;
// Import of a required class
import net.minestom.server.recipe.display.RecipeDisplay;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Type declaration (class/interface/enum/record)
public record RecipeBookAddPacket(List<Entry> entries, boolean replace) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final byte FLAG_NOTIFICATION = 1;
    // Assigns a value
    public static final byte FLAG_HIGHLIGHT = 1 << 1;

    // Start of a method/block
    public RecipeBookAddPacket {
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<RecipeBookAddPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Entry.SERIALIZER.list(), RecipeBookAddPacket::entries,
            // Code statement
            BOOLEAN, RecipeBookAddPacket::replace,
            // Code statement
            RecipeBookAddPacket::new);

    // Type declaration (class/interface/enum/record)
    public record Entry(
            // Code statement
            int displayId, RecipeDisplay display,
            // Annotation for the following element
            @Nullable Integer group, RecipeBookCategory category,
            // Annotation for the following element
            @Nullable List<Ingredient> craftingRequirements,
            // Code statement
            byte flags
    // Start of a method/block
    ) {
        // Assigns a value
        public static final NetworkBuffer.Type<Entry> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.VAR_INT, Entry::displayId,
                // Code statement
                RecipeDisplay.NETWORK_TYPE, Entry::display,
                // Code statement
                NetworkBuffer.OPTIONAL_VAR_INT, Entry::group,
                // Code statement
                RecipeBookCategory.NETWORK_TYPE, Entry::category,
                // Code statement
                Ingredient.NETWORK_TYPE.list().optional(), Entry::craftingRequirements,
                // Code statement
                NetworkBuffer.BYTE, Entry::flags,
                // Code statement
                Entry::new);

        // Start of a method/block
        public Entry {
            // Calls a method
            craftingRequirements = craftingRequirements != null ? List.copyOf(craftingRequirements) : null;
        // End of a block/expression
        }

        // Code statement
        public Entry(int displayId, RecipeDisplay display,
                     // Annotation for the following element
                     @Nullable Integer group, RecipeBookCategory category,
                     // Annotation for the following element
                     @Nullable List<Ingredient> craftingRequirements,
                     // Start of a method/block
                     boolean notification, boolean highlight) {
            // Code statement
            this(displayId, display, group, category, craftingRequirements,
                    // Calls a method
                    (byte) ((notification ? FLAG_NOTIFICATION : 0) | (highlight ? FLAG_HIGHLIGHT : 0)));
        // End of a block/expression
        }

        // Start of a method/block
        public boolean notification() {
            // Returns a value to the caller
            return (flags & FLAG_NOTIFICATION) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean highlight() {
            // Returns a value to the caller
            return (flags & FLAG_HIGHLIGHT) != 0;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Calls a method
        final var components = new ArrayList<Component>();
        // Loop: repeats a block
        for (Entry entry : entries)
            // Calls a method
            components.addAll(entry.display.components());
        // Returns a value to the caller
        return components;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Calls a method
        final var entries = new ArrayList<Entry>();
        // Loop: repeats a block
        for (Entry entry : this.entries) {
            // Code statement
            entries.add(new Entry(entry.displayId, entry.display.copyWithOperator(operator),
                    // Code statement
                    entry.group, entry.category, entry.craftingRequirements, entry.flags));
        // End of a block/expression
        }
        // Returns a value to the caller
        return new RecipeBookAddPacket(entries, replace);
    // End of a block/expression
    }
// End of a block/expression
}
