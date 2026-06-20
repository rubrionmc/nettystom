// Déclaration du paquet de ce fichier
package net.minestom.server.event.book;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.ItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class EditBookEvent implements PlayerInstanceEvent, ItemEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final ItemStack itemStack;
    // Instruction de code
    private final List<String> pages;
    // Instruction de code
    private final @Nullable String title;

    // Instruction de code
    public EditBookEvent(
            // Instruction de code
            Player player,
            // Instruction de code
            ItemStack itemStack,
            // Instruction de code
            List<String> pages,
            // Annotation pour l'élément suivant
            @Nullable String title
    // Début d'une méthode/d'un bloc
    ) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.itemStack = itemStack;
        // Accès à l'objet courant/parent
        this.pages = pages;
        // Accès à l'objet courant/parent
        this.title = title;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return itemStack;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<String> getPages() {
        // Renvoie une valeur à l'appelant
        return pages;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable String getTitle() {
        // Renvoie une valeur à l'appelant
        return title;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSigned() {
        // Renvoie une valeur à l'appelant
        return title != null;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
