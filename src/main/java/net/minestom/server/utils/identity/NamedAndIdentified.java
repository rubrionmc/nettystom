// Package declaration for this file
package net.minestom.server.utils.identity;

// Import of a required class
import net.kyori.adventure.text.Component;

// Import of a required class
import java.util.UUID;

/**
 * An object with a {@link Component} name and a {@link UUID} identity.
 */
// Type declaration (class/interface/enum/record)
public interface NamedAndIdentified {

    /**
     * Creates a {@link NamedAndIdentified} instance with an empty name and a random UUID.
     *
     * @return the named and identified instance
     */
    // Start of a method/block
    static NamedAndIdentified empty() {
        // Returns a value to the caller
        return of(Component.empty(), UUID.randomUUID());
    // End of a block/expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with a given name and a random UUID.
     *
     * @param name the name
     * @return the named and identified instance
     */
    // Start of a method/block
    static NamedAndIdentified named(String name) {
        // Returns a value to the caller
        return of(name, UUID.randomUUID());
    // End of a block/expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with a given name and a random UUID.
     *
     * @param name the name
     * @return the named and identified instance
     */
    // Start of a method/block
    static NamedAndIdentified named(Component name) {
        // Returns a value to the caller
        return of(name, UUID.randomUUID());
    // End of a block/expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with an empty name and a given UUID.
     *
     * @param uuid the uuid
     * @return the named and identified instance
     */
    // Start of a method/block
    static NamedAndIdentified identified(UUID uuid) {
        // Returns a value to the caller
        return of(Component.empty(), uuid);
    // End of a block/expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with a given name and UUID.
     *
     * @param name the name
     * @param uuid the uuid
     * @return the named and identified instance
     */
    // Start of a method/block
    static NamedAndIdentified of(String name, UUID uuid) {
        // Returns a value to the caller
        return new NamedAndIdentifiedImpl(name, uuid);
    // End of a block/expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with a given name and UUID.
     *
     * @param name the name
     * @param uuid the uuid
     * @return the named and identified instance
     */
    // Start of a method/block
    static NamedAndIdentified of(Component name, UUID uuid) {
        // Returns a value to the caller
        return new NamedAndIdentifiedImpl(name, uuid);
    // End of a block/expression
    }

    /**
     * Gets the name of this object.
     *
     * @return the name
     */
    // Calls a method
    Component getName();

    /**
     * Gets the UUID of this object.
     *
     * @return the uuid
     */
    // Calls a method
    UUID getUuid();
// End of a block/expression
}
