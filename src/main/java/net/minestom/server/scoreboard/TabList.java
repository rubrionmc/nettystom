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
 * Represents the {@link Player} tab list as a {@link Scoreboard}.
 */
// Type declaration (class/interface/enum/record)
public class TabList implements Scoreboard {

    /**
     * <b>WARNING:</b> You shouldn't create scoreboards with the same prefix as those
     */
    // Assigns a value
    private static final String TAB_LIST_PREFIX = "tl-";

    // Calls a method
    private final Set<Player> viewers = new CopyOnWriteArraySet<>();
    // Calls a method
    private final Set<Player> unmodifiableViewers = Collections.unmodifiableSet(viewers);
    // Code statement
    private final String objectiveName;

    // Code statement
    private ScoreboardObjectivePacket.Type type;

    // Start of a method/block
    public TabList(String name, ScoreboardObjectivePacket.Type type) {
        // Access to the current/parent object
        this.objectiveName = TAB_LIST_PREFIX + name;

        // Access to the current/parent object
        this.type = type;
    // End of a block/expression
    }

    /**
     * Gets the scoreboard objective type
     *
     * @return the scoreboard objective type
     */
    // Start of a method/block
    public ScoreboardObjectivePacket.Type getType() {
        // Returns a value to the caller
        return type;
    // End of a block/expression
    }

    /**
     * Changes the scoreboard objective type
     *
     * @param type The new type for the objective
     */
    // Start of a method/block
    public void setType(ScoreboardObjectivePacket.Type type) {
        // Access to the current/parent object
        this.type = type;
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
            player.sendPacket(this.getCreationObjectivePacket(Component.empty(), this.type));
            // Calls a method
            player.sendPacket(this.getDisplayScoreboardPacket((byte) 0));
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

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String getObjectiveName() {
        // Returns a value to the caller
        return this.objectiveName;
    // End of a block/expression
    }
// End of a block/expression
}
