// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.BlockEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class PlayerEditSignEvent implements PlayerInstanceEvent, BlockEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Block block;
    // Instruction de code
    private final BlockVec blockPosition;
    // Instruction de code
    private final List<String> lines;
    // Instruction de code
    private final boolean isFrontText;

    // Début d'une méthode/d'un bloc
    public PlayerEditSignEvent(Player player, Block block, BlockVec blockPosition, List<String> lines, boolean isFrontText) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.block = block;
        // Accès à l'objet courant/parent
        this.blockPosition = blockPosition;
        // Accès à l'objet courant/parent
        this.lines = lines;
        // Accès à l'objet courant/parent
        this.isFrontText = isFrontText;
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
    public Block getBlock() {
        // Renvoie une valeur à l'appelant
        return block;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BlockVec getBlockPosition() {
        // Renvoie une valeur à l'appelant
        return blockPosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns a list of strings representing the lines typed by the player onto the sign.
     * The length is always exactly 4.
     */
    // Début d'une méthode/d'un bloc
    public List<String> getLines() {
        // Renvoie une valeur à l'appelant
        return lines;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isFrontText() {
        // Renvoie une valeur à l'appelant
        return isFrontText;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
