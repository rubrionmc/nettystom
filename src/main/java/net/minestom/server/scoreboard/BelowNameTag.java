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
 * Represents a scoreboard which rendered a tag below the name.
 */
// Déclaration de type (classe/interface/enum/record)
public class BelowNameTag implements Scoreboard {

    /**
     * <b>WARNING:</b> You shouldn't create scoreboards with the same prefix as those
     */
    // Affecte une valeur
    public static final String BELOW_NAME_TAG_PREFIX = "bnt-";

    // Affecte une valeur
    private final Set<Player> viewers = new CopyOnWriteArraySet<>();
    // Appelle une méthode
    private final Set<Player> unmodifiableViewers = Collections.unmodifiableSet(viewers);
    // Instruction de code
    private final String objectiveName;

    // Instruction de code
    private final ScoreboardObjectivePacket scoreboardObjectivePacket;

    /**
     * Creates a new below name scoreboard.
     *
     * @param name  The objective name of the scoreboard
     * @param value The value of the scoreboard
     * @deprecated Use {@link #BelowNameTag(String, Component)}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public BelowNameTag(String name, String value) {
        // Appelle une méthode
        this(name, Component.text(value));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new below name scoreboard.
     *
     * @param name  The objective name of the scoreboard
     * @param value The value of the scoreboard
     */
    // Début d'une méthode/d'un bloc
    public BelowNameTag(String name, Component value) {
        // Accès à l'objet courant/parent
        this.objectiveName = BELOW_NAME_TAG_PREFIX + name;
        // Accès à l'objet courant/parent
        this.scoreboardObjectivePacket = this.getCreationObjectivePacket(value, ScoreboardObjectivePacket.Type.INTEGER);
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean addViewer(Player player) {
        // Appelle une méthode
        final boolean result = this.viewers.add(player);
        // Embranchement : vérifie une condition
        if (result) {
            // Appelle une méthode
            player.sendPacket(this.scoreboardObjectivePacket);
            // Appelle une méthode
            player.sendPacket(this.getDisplayScoreboardPacket((byte) 2));
            // Appelle une méthode
            player.setBelowNameTag(this);
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
            // Appelle une méthode
            player.setBelowNameTag(null);
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
// Fin d'un bloc/d'une expression
}
