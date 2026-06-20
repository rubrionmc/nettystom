// Package declaration for this file
package net.minestom.server.scoreboard;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.play.ScoreboardObjectivePacket;

// Import of a required class
import java.util.Collections;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Represents a scoreboard which rendered a tag below the name.
 */
// Type declaration (class/interface/enum/record)
public class BelowNameTag implements Scoreboard {

    /**
     * <b>WARNING:</b> You shouldn't create scoreboards with the same prefix as those
     */
    // Assigns a value
    public static final String BELOW_NAME_TAG_PREFIX = "bnt-";

    // Calls a method
    private final Set<Player> viewers = new CopyOnWriteArraySet<>();
    // Calls a method
    private final Set<Player> unmodifiableViewers = Collections.unmodifiableSet(viewers);
    // Code statement
    private final String objectiveName;

    // Code statement
    private final ScoreboardObjectivePacket scoreboardObjectivePacket;

    /**
     * Creates a new below name scoreboard.
     *
     * @param name  The objective name of the scoreboard
     * @param value The value of the scoreboard
     * @deprecated Use {@link #BelowNameTag(String, Component)}
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public BelowNameTag(String name, String value) {
        // Calls a method
        this(name, Component.text(value));
    // End of a block/expression
    }

    /**
     * Creates a new below name scoreboard.
     *
     * @param name  The objective name of the scoreboard
     * @param value The value of the scoreboard
     */
    // Start of a method/block
    public BelowNameTag(String name, Component value) {
        // Access to the current/parent object
        this.objectiveName = BELOW_NAME_TAG_PREFIX + name;
        // Access to the current/parent object
        this.scoreboardObjectivePacket = this.getCreationObjectivePacket(value, ScoreboardObjectivePacket.Type.INTEGER);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String getObjectiveName() {
        // Returns a value to the caller
        return this.objectiveName;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean addViewer(Player player) {
        // Calls a method
        final boolean result = this.viewers.add(player);
        // Branch: checks a condition
        if (result) {
            // Calls a method
            player.sendPacket(this.scoreboardObjectivePacket);
            // Calls a method
            player.sendPacket(this.getDisplayScoreboardPacket((byte) 2));
            // Calls a method
            player.setBelowNameTag(this);
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean removeViewer(Player player) {
        // Calls a method
        final boolean result = this.viewers.remove(player);
        // Branch: checks a condition
        if (result) {
            // Calls a method
            player.sendPacket(this.getDestructionObjectivePacket());
            // Calls a method
            player.setBelowNameTag(null);
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Set<? extends Player> getViewers() {
        // Returns a value to the caller
        return unmodifiableViewers;
    // End of a block/expression
    }
// End of a block/expression
}
