// Package declaration for this file
package net.minestom.server.event.book;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.ItemEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class EditBookEvent implements PlayerInstanceEvent, ItemEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final ItemStack itemStack;
    // Code statement
    private final List<String> pages;
    // Code statement
    private final @Nullable String title;

    // Code statement
    public EditBookEvent(
            // Code statement
            Player player,
            // Code statement
            ItemStack itemStack,
            // Code statement
            List<String> pages,
            // Annotation for the following element
            @Nullable String title
    // Start of a method/block
    ) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.itemStack = itemStack;
        // Access to the current/parent object
        this.pages = pages;
        // Access to the current/parent object
        this.title = title;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack getItemStack() {
        // Returns a value to the caller
        return itemStack;
    // End of a block/expression
    }

    // Start of a method/block
    public List<String> getPages() {
        // Returns a value to the caller
        return pages;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable String getTitle() {
        // Returns a value to the caller
        return title;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSigned() {
        // Returns a value to the caller
        return title != null;
    // End of a block/expression
    }
// End of a block/expression
}
