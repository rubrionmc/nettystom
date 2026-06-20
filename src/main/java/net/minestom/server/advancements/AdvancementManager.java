// Package declaration for this file
package net.minestom.server.advancements;

// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

/**
 * Used to manage all the registered {@link AdvancementTab}.
 * <p>
 * Use {@link #createTab(String, AdvancementRoot)} to create a tab with the appropriate {@link AdvancementRoot}.
 */
// Type declaration (class/interface/enum/record)
public class AdvancementManager {

    // root identifier = its advancement tab
    // Calls a method
    private final Map<String, AdvancementTab> advancementTabMap = new ConcurrentHashMap<>();

    /**
     * Creates a new {@link AdvancementTab} with a single {@link AdvancementRoot}.
     *
     * @param rootIdentifier the root identifier
     * @param root           the root advancement
     * @return the newly created {@link AdvancementTab}
     * @throws IllegalStateException if a tab with the identifier {@code rootIdentifier} already exists
     */
    // Start of a method/block
    public AdvancementTab createTab(String rootIdentifier, AdvancementRoot root) {
        // Code statement
        Check.stateCondition(advancementTabMap.containsKey(rootIdentifier),
                // Code statement
                "A tab with the identifier '" + rootIdentifier + "' already exists");
        // Calls a method
        final AdvancementTab advancementTab = new AdvancementTab(rootIdentifier, root);
        // Access to the current/parent object
        this.advancementTabMap.put(rootIdentifier, advancementTab);
        // Returns a value to the caller
        return advancementTab;
    // End of a block/expression
    }

    /**
     * Gets an advancement tab by its root identifier.
     *
     * @param rootIdentifier the root identifier of the tab
     * @return the {@link AdvancementTab} associated with the identifier, null if not any
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public AdvancementTab getTab(String rootIdentifier) {
        // Returns a value to the caller
        return advancementTabMap.get(rootIdentifier);
    // End of a block/expression
    }

    /**
     * Gets all the created {@link AdvancementTab}.
     *
     * @return the collection containing all created {@link AdvancementTab}
     */
    // Start of a method/block
    public Collection<AdvancementTab> getTabs() {
        // Returns a value to the caller
        return advancementTabMap.values();
    // End of a block/expression
    }

// End of a block/expression
}
