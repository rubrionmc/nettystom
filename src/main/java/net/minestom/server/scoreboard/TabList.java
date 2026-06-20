// Déclaration du paquet de ce fichier
package net.minestom.server.scoreboard;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ScoreboardObjectivePacket;

// Import d'une classe nécessaire
import java.util.Collections;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Represents the {@link Player} tab list as a {@link Scoreboard}.
 */
// Déclaration de type (classe/interface/enum/record)
public class TabList implements Scoreboard {

    /**
     * <b>WARNING:</b> You shouldn't create scoreboards with the same prefix as those
     */
    // Affecte une valeur
    private static final String TAB_LIST_PREFIX = "tl-";

    // Affecte une valeur
    private final Set<Player> viewers = new CopyOnWriteArraySet<>();
    // Appelle une méthode
    private final Set<Player> unmodifiableViewers = Collections.unmodifiableSet(viewers);
    // Instruction de code
    private final String objectiveName;

    // Instruction de code
    private ScoreboardObjectivePacket.Type type;

    // Début d'une méthode/d'un bloc
    public TabList(String name, ScoreboardObjectivePacket.Type type) {
        // Accès à l'objet courant/parent
        this.objectiveName = TAB_LIST_PREFIX + name;

        // Accès à l'objet courant/parent
        this.type = type;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the scoreboard objective type
     *
     * @return the scoreboard objective type
     */
    // Début d'une méthode/d'un bloc
    public ScoreboardObjectivePacket.Type getType() {
        // Renvoie une valeur à l'appelant
        return type;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the scoreboard objective type
     *
     * @param type The new type for the objective
     */
    // Début d'une méthode/d'un bloc
    public void setType(ScoreboardObjectivePacket.Type type) {
        // Accès à l'objet courant/parent
        this.type = type;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean addViewer(Player player) {
        // Appelle une méthode
        final boolean result = this.viewers.add(player);
        // Embranchement : vérifie une condition
        if (result) {
            // Appelle une méthode
            player.sendPacket(this.getCreationObjectivePacket(Component.empty(), this.type));
            // Appelle une méthode
            player.sendPacket(this.getDisplayScoreboardPacket((byte) 0));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean removeViewer(Player player) {
        // Appelle une méthode
        final boolean result = this.viewers.remove(player);
        // Embranchement : vérifie une condition
        if (result) {
            // Appelle une méthode
            player.sendPacket(this.getDestructionObjectivePacket());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Set<Player> getViewers() {
        // Renvoie une valeur à l'appelant
        return unmodifiableViewers;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String getObjectiveName() {
        // Renvoie une valeur à l'appelant
        return this.objectiveName;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
