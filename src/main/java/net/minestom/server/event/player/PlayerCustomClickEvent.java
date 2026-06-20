// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Triggered when we receive a custom click packet from the client during the <b>play</b> state.
 *
 * @see PlayerConfigCustomClickEvent
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerCustomClickEvent implements PlayerInstanceEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Key key;
    // Instruction de code
    private final BinaryTag payload;

    // Début d'une méthode/d'un bloc
    public PlayerCustomClickEvent(Player player, Key key, @Nullable BinaryTag payload) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.key = key;
        // Accès à l'objet courant/parent
        this.payload = payload;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return this.player;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Key getKey() {
        // Renvoie une valeur à l'appelant
        return this.key;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable BinaryTag getPayload() {
        // Renvoie une valeur à l'appelant
        return this.payload;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
