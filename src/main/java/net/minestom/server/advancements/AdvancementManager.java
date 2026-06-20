// Déclaration du paquet de ce fichier
package net.minestom.server.advancements;

// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;

/**
 * Used to manage all the registered {@link AdvancementTab}.
 * <p>
 * Use {@link #createTab(String, AdvancementRoot)} to create a tab with the appropriate {@link AdvancementRoot}.
 */
// Déclaration de type (classe/interface/enum/record)
public class AdvancementManager {

    // root identifier = its advancement tab
    // Affecte une valeur
    private final Map<String, AdvancementTab> advancementTabMap = new ConcurrentHashMap<>();

    /**
     * Creates a new {@link AdvancementTab} with a single {@link AdvancementRoot}.
     *
     * @param rootIdentifier the root identifier
     * @param root           the root advancement
     * @return the newly created {@link AdvancementTab}
     * @throws IllegalStateException if a tab with the identifier {@code rootIdentifier} already exists
     */
    // Début d'une méthode/d'un bloc
    public AdvancementTab createTab(String rootIdentifier, AdvancementRoot root) {
        // Instruction de code
        Check.stateCondition(advancementTabMap.containsKey(rootIdentifier),
                // Instruction de code
                "A tab with the identifier '" + rootIdentifier + "' already exists");
        // Appelle une méthode
        final AdvancementTab advancementTab = new AdvancementTab(rootIdentifier, root);
        // Accès à l'objet courant/parent
        this.advancementTabMap.put(rootIdentifier, advancementTab);
        // Renvoie une valeur à l'appelant
        return advancementTab;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets an advancement tab by its root identifier.
     *
     * @param rootIdentifier the root identifier of the tab
     * @return the {@link AdvancementTab} associated with the identifier, null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public AdvancementTab getTab(String rootIdentifier) {
        // Renvoie une valeur à l'appelant
        return advancementTabMap.get(rootIdentifier);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the created {@link AdvancementTab}.
     *
     * @return the collection containing all created {@link AdvancementTab}
     */
    // Début d'une méthode/d'un bloc
    public Collection<AdvancementTab> getTabs() {
        // Renvoie une valeur à l'appelant
        return advancementTabMap.values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
