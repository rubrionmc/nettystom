// Déclaration du paquet de ce fichier
package net.minestom.server.utils.identity;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.UUID;

/**
 * Simple implementation of {@link NamedAndIdentified}.
 *
 * @see #of(String, UUID)
 * @see #of(Component, UUID)
 */
// Déclaration de type (classe/interface/enum/record)
class NamedAndIdentifiedImpl implements NamedAndIdentified {
    // Instruction de code
    private final Component name;
    // Instruction de code
    private final UUID uuid;

    /**
     * Creates a new named and identified implementation.
     *
     * @param name the name
     * @param uuid the uuid
     * @see NamedAndIdentified#of(String, UUID)
     */
    // Début d'une méthode/d'un bloc
    NamedAndIdentifiedImpl(String name, UUID uuid) {
        // Appelle une méthode
        this(Component.text(name), uuid);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new named and identified implementation.
     *
     * @param name the name
     * @param uuid the uuid
     * @see NamedAndIdentified#of(Component, UUID)
     */
    // Début d'une méthode/d'un bloc
    NamedAndIdentifiedImpl(Component name, UUID uuid) {
        // Accès à l'objet courant/parent
        this.name = Objects.requireNonNull(name, "name cannot be null");
        // Accès à l'objet courant/parent
        this.uuid = Objects.requireNonNull(uuid, "uuid cannot be null");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Component getName() {
        // Renvoie une valeur à l'appelant
        return this.name;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public UUID getUuid() {
        // Renvoie une valeur à l'appelant
        return this.uuid;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (this == o) return true;
        // Embranchement : vérifie une condition
        if (!(o instanceof NamedAndIdentified that)) return false;
        // Renvoie une valeur à l'appelant
        return this.uuid.equals(that.getUuid());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return Objects.hash(this.uuid);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("NamedAndIdentifiedImpl{name='%s', uuid=%s}", this.name, this.uuid);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
