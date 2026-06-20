// Package declaration for this file
package net.minestom.server.utils.identity;

// Import of a required class
import net.kyori.adventure.text.Component;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.UUID;

/**
 * Simple implementation of {@link NamedAndIdentified}.
 *
 * @see #of(String, UUID)
 * @see #of(Component, UUID)
 */
// Type declaration (class/interface/enum/record)
class NamedAndIdentifiedImpl implements NamedAndIdentified {
    // Code statement
    private final Component name;
    // Code statement
    private final UUID uuid;

    /**
     * Creates a new named and identified implementation.
     *
     * @param name the name
     * @param uuid the uuid
     * @see NamedAndIdentified#of(String, UUID)
     */
    // Start of a method/block
    NamedAndIdentifiedImpl(String name, UUID uuid) {
        // Calls a method
        this(Component.text(name), uuid);
    // End of a block/expression
    }

    /**
     * Creates a new named and identified implementation.
     *
     * @param name the name
     * @param uuid the uuid
     * @see NamedAndIdentified#of(Component, UUID)
     */
    // Start of a method/block
    NamedAndIdentifiedImpl(Component name, UUID uuid) {
        // Access to the current/parent object
        this.name = Objects.requireNonNull(name, "name cannot be null");
        // Access to the current/parent object
        this.uuid = Objects.requireNonNull(uuid, "uuid cannot be null");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Component getName() {
        // Returns a value to the caller
        return this.name;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public UUID getUuid() {
        // Returns a value to the caller
        return this.uuid;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (this == o) return true;
        // Branch: checks a condition
        if (!(o instanceof NamedAndIdentified that)) return false;
        // Returns a value to the caller
        return this.uuid.equals(that.getUuid());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return Objects.hash(this.uuid);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("NamedAndIdentifiedImpl{name='%s', uuid=%s}", this.name, this.uuid);
    // End of a block/expression
    }
// End of a block/expression
}
