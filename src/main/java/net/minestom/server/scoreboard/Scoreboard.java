// Déclaration du paquet de ce fichier
package net.minestom.server.scoreboard;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.Viewable;
// Import d'une classe nécessaire
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DisplayScoreboardPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ScoreboardObjectivePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.UpdateScorePacket;

// Import d'une classe nécessaire
import java.util.Collection;

/**
 * This interface represents all scoreboard of Minecraft.
 */
// Déclaration de type (classe/interface/enum/record)
public interface Scoreboard extends Viewable, PacketGroupingAudience {

    /**
     * Creates a creation objective packet.
     *
     * @param value The value for the objective
     * @param type  The type for the objective
     * @return the creation objective packet
     * @deprecated Use {@link #getCreationObjectivePacket(Component, ScoreboardObjectivePacket.Type)}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    default ScoreboardObjectivePacket getCreationObjectivePacket(String value, ScoreboardObjectivePacket.Type type) {
        // Renvoie une valeur à l'appelant
        return this.getCreationObjectivePacket(Component.text(value), type);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a creation objective packet.
     *
     * @param value The value for the objective
     * @param type  The type for the objective
     * @return the creation objective packet
     */
    // Début d'une méthode/d'un bloc
    default ScoreboardObjectivePacket getCreationObjectivePacket(Component value, ScoreboardObjectivePacket.Type type) {
        // Renvoie une valeur à l'appelant
        return new ScoreboardObjectivePacket(getObjectiveName(), (byte) 0, value, type, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates the destruction objective packet.
     *
     * @return the destruction objective packet
     */
    // Début d'une méthode/d'un bloc
    default ScoreboardObjectivePacket getDestructionObjectivePacket() {
        // Renvoie une valeur à l'appelant
        return new ScoreboardObjectivePacket(getObjectiveName(), (byte) 1, null, null, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates the {@link DisplayScoreboardPacket}.
     *
     * @param position The position of the scoreboard
     * @return the created display scoreboard packet
     */
    // Début d'une méthode/d'un bloc
    default DisplayScoreboardPacket getDisplayScoreboardPacket(byte position) {
        // Renvoie une valeur à l'appelant
        return new DisplayScoreboardPacket(position, getObjectiveName());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the score of a {@link Player}.
     *
     * @param player The player
     * @param score  The new score
     */
    // Début d'une méthode/d'un bloc
    default void updateScore(Player player, int score) {
        // Appelle une méthode
        sendPacketsToViewers(new UpdateScorePacket(player.getUsername(), getObjectiveName(), score, null, null));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the objective name of the scoreboard.
     *
     * @return the objective name
     */
    // Appelle une méthode
    String getObjectiveName();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Collection<Player> getPlayers() {
        // Renvoie une valeur à l'appelant
        return this.getViewers();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
