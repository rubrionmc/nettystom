// Déclaration du paquet de ce fichier
package net.minestom.server.utils.identity;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;

// Import d'une classe nécessaire
import java.util.UUID;

/**
 * An object with a {@link Component} name and a {@link UUID} identity.
 */
// Déclaration de type (classe/interface/enum/record)
public interface NamedAndIdentified {

    /**
     * Creates a {@link NamedAndIdentified} instance with an empty name and a random UUID.
     *
     * @return the named and identified instance
     */
    // Début d'une méthode/d'un bloc
    static NamedAndIdentified empty() {
        // Renvoie une valeur à l'appelant
        return of(Component.empty(), UUID.randomUUID());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with a given name and a random UUID.
     *
     * @param name the name
     * @return the named and identified instance
     */
    // Début d'une méthode/d'un bloc
    static NamedAndIdentified named(String name) {
        // Renvoie une valeur à l'appelant
        return of(name, UUID.randomUUID());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with a given name and a random UUID.
     *
     * @param name the name
     * @return the named and identified instance
     */
    // Début d'une méthode/d'un bloc
    static NamedAndIdentified named(Component name) {
        // Renvoie une valeur à l'appelant
        return of(name, UUID.randomUUID());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with an empty name and a given UUID.
     *
     * @param uuid the uuid
     * @return the named and identified instance
     */
    // Début d'une méthode/d'un bloc
    static NamedAndIdentified identified(UUID uuid) {
        // Renvoie une valeur à l'appelant
        return of(Component.empty(), uuid);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with a given name and UUID.
     *
     * @param name the name
     * @param uuid the uuid
     * @return the named and identified instance
     */
    // Début d'une méthode/d'un bloc
    static NamedAndIdentified of(String name, UUID uuid) {
        // Renvoie une valeur à l'appelant
        return new NamedAndIdentifiedImpl(name, uuid);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link NamedAndIdentified} instance with a given name and UUID.
     *
     * @param name the name
     * @param uuid the uuid
     * @return the named and identified instance
     */
    // Début d'une méthode/d'un bloc
    static NamedAndIdentified of(Component name, UUID uuid) {
        // Renvoie une valeur à l'appelant
        return new NamedAndIdentifiedImpl(name, uuid);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the name of this object.
     *
     * @return the name
     */
    // Appelle une méthode
    Component getName();

    /**
     * Gets the UUID of this object.
     *
     * @return the uuid
     */
    // Appelle une méthode
    UUID getUuid();
// Fin d'un bloc/d'une expression
}
