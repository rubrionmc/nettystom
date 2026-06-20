// Package declaration for this file
package net.minestom.server.scoreboard;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.Viewable;
// Import of a required class
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.play.DisplayScoreboardPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.ScoreboardObjectivePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.UpdateScorePacket;

// Import of a required class
import java.util.Collection;

/**
 * This interface represents all scoreboard of Minecraft.
 */
// Type declaration (class/interface/enum/record)
public interface Scoreboard extends Viewable, PacketGroupingAudience {

    /**
     * Creates a creation objective packet.
     *
     * @param value The value for the objective
     * @param type  The type for the objective
     * @return the creation objective packet
     * @deprecated Use {@link #getCreationObjectivePacket(Component, ScoreboardObjectivePacket.Type)}
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    default ScoreboardObjectivePacket getCreationObjectivePacket(String value, ScoreboardObjectivePacket.Type type) {
        // Returns a value to the caller
        return this.getCreationObjectivePacket(Component.text(value), type);
    // End of a block/expression
    }

    /**
     * Creates a creation objective packet.
     *
     * @param value The value for the objective
     * @param type  The type for the objective
     * @return the creation objective packet
     */
    // Start of a method/block
    default ScoreboardObjectivePacket getCreationObjectivePacket(Component value, ScoreboardObjectivePacket.Type type) {
        // Returns a value to the caller
        return new ScoreboardObjectivePacket(getObjectiveName(), (byte) 0, value, type, null);
    // End of a block/expression
    }

    /**
     * Creates the destruction objective packet.
     *
     * @return the destruction objective packet
     */
    // Start of a method/block
    default ScoreboardObjectivePacket getDestructionObjectivePacket() {
        // Returns a value to the caller
        return new ScoreboardObjectivePacket(getObjectiveName(), (byte) 1, null, null, null);
    // End of a block/expression
    }

    /**
     * Creates the {@link DisplayScoreboardPacket}.
     *
     * @param position The position of the scoreboard
     * @return the created display scoreboard packet
     */
    // Start of a method/block
    default DisplayScoreboardPacket getDisplayScoreboardPacket(byte position) {
        // Returns a value to the caller
        return new DisplayScoreboardPacket(position, getObjectiveName());
    // End of a block/expression
    }

    /**
     * Updates the score of a {@link Player}.
     *
     * @param player The player
     * @param score  The new score
     */
    // Start of a method/block
    default void updateScore(Player player, int score) {
        // Calls a method
        sendPacketsToViewers(new UpdateScorePacket(player.getUsername(), getObjectiveName(), score, null, null));
    // End of a block/expression
    }

    /**
     * Gets the objective name of the scoreboard.
     *
     * @return the objective name
     */
    // Calls a method
    String getObjectiveName();

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Collection<? extends Player> getPlayers() {
        // Returns a value to the caller
        return this.getViewers();
    // End of a block/expression
    }
// End of a block/expression
}
