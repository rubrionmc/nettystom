// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerSkin;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Called at the player connection to initialize his skin.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerSkinInitEvent implements PlayerEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private PlayerSkin skin;

    // Début d'une méthode/d'un bloc
    public PlayerSkinInitEvent(Player player, @Nullable PlayerSkin currentSkin) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.skin = currentSkin;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the spawning skin of the player.
     *
     * @return the player skin, or null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public PlayerSkin getSkin() {
        // Renvoie une valeur à l'appelant
        return skin;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the spawning skin of the player.
     *
     * @param skin the new player skin
     */
    // Début d'une méthode/d'un bloc
    public void setSkin(@Nullable PlayerSkin skin) {
        // Accès à l'objet courant/parent
        this.skin = skin;
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
// Fin d'un bloc/d'une expression
}
