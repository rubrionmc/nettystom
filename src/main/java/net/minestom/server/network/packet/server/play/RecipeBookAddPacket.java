// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.recipe.Ingredient;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeBookCategory;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.RecipeDisplay;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Déclaration de type (classe/interface/enum/record)
public record RecipeBookAddPacket(List<Entry> entries, boolean replace) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final byte FLAG_NOTIFICATION = 1;
    // Affecte une valeur
    public static final byte FLAG_HIGHLIGHT = 1 << 1;

    // Début d'une méthode/d'un bloc
    public RecipeBookAddPacket {
        // Appelle une méthode
        entries = List.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<RecipeBookAddPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Entry.SERIALIZER.list(), RecipeBookAddPacket::entries,
            // Instruction de code
            BOOLEAN, RecipeBookAddPacket::replace,
            // Instruction de code
            RecipeBookAddPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public record Entry(
            // Instruction de code
            int displayId, RecipeDisplay display,
            // Annotation pour l'élément suivant
            @Nullable Integer group, RecipeBookCategory category,
            // Annotation pour l'élément suivant
            @Nullable List<Ingredient> craftingRequirements,
            // Instruction de code
            byte flags
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Entry> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.VAR_INT, Entry::displayId,
                // Instruction de code
                RecipeDisplay.NETWORK_TYPE, Entry::display,
                // Instruction de code
                NetworkBuffer.OPTIONAL_VAR_INT, Entry::group,
                // Instruction de code
                RecipeBookCategory.NETWORK_TYPE, Entry::category,
                // Instruction de code
                Ingredient.NETWORK_TYPE.list().optional(), Entry::craftingRequirements,
                // Instruction de code
                NetworkBuffer.BYTE, Entry::flags,
                // Instruction de code
                Entry::new);

        // Début d'une méthode/d'un bloc
        public Entry {
            // Appelle une méthode
            craftingRequirements = craftingRequirements != null ? List.copyOf(craftingRequirements) : null;
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        public Entry(int displayId, RecipeDisplay display,
                     // Annotation pour l'élément suivant
                     @Nullable Integer group, RecipeBookCategory category,
                     // Annotation pour l'élément suivant
                     @Nullable List<Ingredient> craftingRequirements,
                     // Début d'une méthode/d'un bloc
                     boolean notification, boolean highlight) {
            // Instruction de code
            this(displayId, display, group, category, craftingRequirements,
                    // Appelle une méthode
                    (byte) ((notification ? FLAG_NOTIFICATION : 0) | (highlight ? FLAG_HIGHLIGHT : 0)));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean notification() {
            // Renvoie une valeur à l'appelant
            return (flags & FLAG_NOTIFICATION) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean highlight() {
            // Renvoie une valeur à l'appelant
            return (flags & FLAG_HIGHLIGHT) != 0;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Appelle une méthode
        final var components = new ArrayList<Component>();
        // Boucle : répète un bloc
        for (Entry entry : entries)
            // Appelle une méthode
            components.addAll(entry.display.components());
        // Renvoie une valeur à l'appelant
        return components;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Appelle une méthode
        final var entries = new ArrayList<Entry>();
        // Boucle : répète un bloc
        for (Entry entry : this.entries) {
            // Instruction de code
            entries.add(new Entry(entry.displayId, entry.display.copyWithOperator(operator),
                    // Instruction de code
                    entry.group, entry.category, entry.craftingRequirements, entry.flags));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new RecipeBookAddPacket(entries, replace);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
