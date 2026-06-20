// Package declaration for this file
package net.minestom.server.scoreboard;

// Import of a required class
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.adventure.ComponentHolder;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Collections;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.UnaryOperator;

/**
 * Represents a sidebar which can contain up to 16 {@link ScoreboardLine}.
 * <p>
 * In order to use it you need to create a new instance using the constructor {@link #Sidebar(String)} and create new lines
 * with {@link #createLine(ScoreboardLine)}. You can then add a {@link Player} to the viewing list using {@link #addViewer(Player)}
 * and remove him later with {@link #removeViewer(Player)}.
 * <p>
 * Lines can be modified using their respective identifier using
 * {@link #updateLineContent(String, Component)} and {@link #updateLineScore(String, int)}.
 */
// Type declaration (class/interface/enum/record)
public class Sidebar implements Scoreboard {

    // Calls a method
    private static final AtomicInteger COUNTER = new AtomicInteger();

    /**
     * <b>WARNING:</b> You should NOT create any scoreboards/teams with the same prefixes as those
     */
    // Assigns a value
    private static final String SCOREBOARD_PREFIX = "sb-";
    // Assigns a value
    private static final String TEAM_PREFIX = "sbt-";

    /**
     * Limited by the notch client, do not change
     */
    // Assigns a value
    private static final int MAX_LINES_COUNT = 15;

    // Calls a method
    private final Set<Player> viewers = new CopyOnWriteArraySet<>();

    // Calls a method
    private final Set<ScoreboardLine> lines = new CopyOnWriteArraySet<>();
    // Calls a method
    private final IntLinkedOpenHashSet availableColors = new IntLinkedOpenHashSet();

    // Code statement
    private final String objectiveName;

    // Code statement
    private Component title;

    /**
     * Creates a new sidebar
     *
     * @param title The title of the sidebar
     * @deprecated Use {@link #Sidebar(Component)}
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Sidebar(String title) {
        // Calls a method
        this(Component.text(title));
    // End of a block/expression
    }

    /**
     * Creates a new sidebar
     *
     * @param title The title of the sidebar
     */
    // Start of a method/block
    public Sidebar(Component title) {
        // Access to the current/parent object
        this.title = title;

        // Access to the current/parent object
        this.objectiveName = SCOREBOARD_PREFIX + COUNTER.incrementAndGet();

        // Fill available colors for entities name showed in scoreboard
        // Loop: repeats a block
        for (int i = 0; i < 16; i++) {
            // Calls a method
            availableColors.add(i);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Changes the {@link Sidebar} title
     *
     * @param title The new sidebar title
     * @deprecated Use {@link #setTitle(Component)}
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setTitle(String title) {
        // Access to the current/parent object
        this.setTitle(Component.text(title));
    // End of a block/expression
    }

    /**
     * Gets the {@link Sidebar} title
     *
     * @return The sidebar title
     */
    // Start of a method/block
    public Component getTitle() {
        // Returns a value to the caller
        return title;
    // End of a block/expression
    }

    /**
     * Changes the {@link Sidebar} title
     *
     * @param title The new sidebar title
     */
    // Start of a method/block
    public void setTitle(Component title) {
        // Access to the current/parent object
        this.title = title;
        // Code statement
        sendPacketToViewers(new ScoreboardObjectivePacket(objectiveName, (byte) 2, title,
                // Code statement
                ScoreboardObjectivePacket.Type.INTEGER, null));
    // End of a block/expression
    }

    /**
     * Creates a new {@link ScoreboardLine}.
     *
     * @param scoreboardLine the new scoreboard line
     * @throws IllegalStateException    if the sidebar cannot take more line
     * @throws IllegalArgumentException if the sidebar already contains the line {@code scoreboardLine}
     *                                  or has a line with the same id
     */
    // Start of a method/block
    public void createLine(ScoreboardLine scoreboardLine) {
        // Start of a method/block
        synchronized (lines) {
            // Calls a method
            Check.stateCondition(lines.size() >= MAX_LINES_COUNT, "You cannot have more than " + MAX_LINES_COUNT + "  lines");
            // Calls a method
            Check.argCondition(lines.contains(scoreboardLine), "You cannot add two times the same ScoreboardLine");

            // Check ID duplication
            // Loop: repeats a block
            for (ScoreboardLine line : lines) {
                // Code statement
                Check.argCondition(line.id.equals(scoreboardLine.id),
                        // Code statement
                        "You cannot add two ScoreboardLine with the same id");
            // End of a block/expression
            }

            // Setup line
            // Calls a method
            scoreboardLine.retrieveName(availableColors);
            // Calls a method
            scoreboardLine.createTeam();

            // Finally add the line in cache
            // Access to the current/parent object
            this.lines.add(scoreboardLine);

            // Send to current viewers
            // Calls a method
            sendPacketsToViewers(scoreboardLine.sidebarTeam.getCreationPacket(), scoreboardLine.getScoreCreationPacket(objectiveName));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Updates a {@link ScoreboardLine} content through the given identifier.
     *
     * @param id      The identifier of the {@link ScoreboardLine}
     * @param content The new content for the {@link ScoreboardLine}
     */
    // Start of a method/block
    public void updateLineContent(String id, Component content) {
        // Calls a method
        final ScoreboardLine scoreboardLine = getLine(id);
        // Branch: checks a condition
        if (scoreboardLine != null) {
            // Calls a method
            scoreboardLine.refreshContent(content);
            // Calls a method
            sendPacketToViewers(scoreboardLine.sidebarTeam.updatePrefix(content));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Updates the score of a {@link ScoreboardLine} through the given identifier
     *
     * @param id    The identifier of the team
     * @param score The new score for the {@link ScoreboardLine}
     */
    // Start of a method/block
    public void updateLineScore(String id, int score) {
        // Calls a method
        final ScoreboardLine scoreboardLine = getLine(id);
        // Branch: checks a condition
        if (scoreboardLine != null) {
            // Assigns a value
            scoreboardLine.line = score;
            // Calls a method
            sendPacketToViewers(scoreboardLine.getLineScoreUpdatePacket(objectiveName, score));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Updates a {@link ScoreboardLine} number format through the given identifier.
     *
     * @param id           The identifier of the {@link ScoreboardLine}
     * @param numberFormat The new number format for the {@link ScoreboardLine}
     */
    // Start of a method/block
    public void updateLineNumberFormat(String id, NumberFormat numberFormat) {
        // Calls a method
        final ScoreboardLine scoreboardLine = getLine(id);
        // Branch: checks a condition
        if (scoreboardLine != null) {
            // Assigns a value
            scoreboardLine.numberFormat = numberFormat;
            // Calls a method
            sendPacketsToViewers(scoreboardLine.getNumberFormatPacket(objectiveName, numberFormat));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets a {@link ScoreboardLine} through the given identifier
     *
     * @param id The identifier of the line
     * @return a {@link ScoreboardLine} or {@code null}
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public ScoreboardLine getLine(String id) {
        // Loop: repeats a block
        for (ScoreboardLine line : lines) {
            // Branch: checks a condition
            if (line.id.equals(id))
                // Returns a value to the caller
                return line;
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    /**
     * Gets a {@link Set} containing all the registered lines.
     *
     * @return an unmodifiable set containing the sidebar's lines
     */
    // Start of a method/block
    public Set<ScoreboardLine> getLines() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(lines);
    // End of a block/expression
    }

    /**
     * Removes a {@link ScoreboardLine} through the given identifier
     *
     * @param id the identifier of the {@link ScoreboardLine}
     */
    // Start of a method/block
    public void removeLine(String id) {
        // Access to the current/parent object
        this.lines.removeIf(line -> {
            // Branch: checks a condition
            if (line.id.equals(id)) {

                // Remove the line for current viewers
                // Calls a method
                sendPacketsToViewers(line.getScoreDestructionPacket(objectiveName), line.sidebarTeam.getDestructionPacket());

                // Calls a method
                line.returnName(availableColors);
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
            // Returns a value to the caller
            return false;
        // End of a block/expression
        });
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
            ScoreboardObjectivePacket scoreboardObjectivePacket = this.getCreationObjectivePacket(this.title, ScoreboardObjectivePacket.Type.INTEGER);
            // Calls a method
            player.sendPacket(scoreboardObjectivePacket);
        // End of a block/expression
        }
        // Calls a method
        DisplayScoreboardPacket displayScoreboardPacket = this.getDisplayScoreboardPacket((byte) 1);
        // Code statement
        player.sendPacket(displayScoreboardPacket); // Show sidebar scoreboard (wait for scores packet)
        // Loop: repeats a block
        for (ScoreboardLine line : lines) {
            // Calls a method
            player.sendPacket(line.sidebarTeam.getCreationPacket());
            // Calls a method
            player.sendPacket(line.getScoreCreationPacket(objectiveName));
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
        if (!result) return false;
        // Calls a method
        ScoreboardObjectivePacket scoreboardObjectivePacket = this.getDestructionObjectivePacket();
        // Calls a method
        player.sendPacket(scoreboardObjectivePacket);
        // Loop: repeats a block
        for (ScoreboardLine line : lines) {
            // Code statement
            player.sendPacket(line.getScoreDestructionPacket(objectiveName)); // Is it necessary?
            // Calls a method
            player.sendPacket(line.sidebarTeam.getDestructionPacket());
        // End of a block/expression
        }
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Set<? extends Player> getViewers() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(viewers);
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

    /**
     * This class is used to create a line for the sidebar.
     */
    // Type declaration (class/interface/enum/record)
    public static class ScoreboardLine {

        /**
         * The identifier is used to modify the line later
         */
        // Code statement
        private final String id;
        /**
         * The content for the line
         */
        // Code statement
        private final Component content;
        /**
         * The score of the line
         */
        // Code statement
        private int line;
        /**
         * The number format of the line
         */
        // Code statement
        private NumberFormat numberFormat;

        // Code statement
        private final String teamName;
        /**
         * The name of the score ({@code entityName}) which is essentially an identifier
         */
        // Code statement
        private int colorName;
        // Code statement
        private String entityName;
        /**
         * The sidebar team of the line
         */
        // Code statement
        private SidebarTeam sidebarTeam;

        // Start of a method/block
        public ScoreboardLine(String id, Component content, int line) {
            // Calls a method
            this(id, content, line, null);
        // End of a block/expression
        }

        // Start of a method/block
        public ScoreboardLine(String id, Component content, int line, @Nullable NumberFormat numberFormat) {
            // Access to the current/parent object
            this.id = id;
            // Access to the current/parent object
            this.content = content;
            // Access to the current/parent object
            this.line = line;
            // Access to the current/parent object
            this.numberFormat = numberFormat;

            // Access to the current/parent object
            this.teamName = TEAM_PREFIX + COUNTER.incrementAndGet();
        // End of a block/expression
        }

        /**
         * Gets the identifier of the line
         *
         * @return the line identifier
         */
        // Start of a method/block
        public String getId() {
            // Returns a value to the caller
            return id;
        // End of a block/expression
        }

        /**
         * Gets the content of the line
         *
         * @return The line content
         */
        // Start of a method/block
        public Component getContent() {
            // Returns a value to the caller
            return sidebarTeam == null ? content : sidebarTeam.getPrefix();
        // End of a block/expression
        }

        /**
         * Gets the position of the line
         *
         * @return the line position
         */
        // Start of a method/block
        public int getLine() {
            // Returns a value to the caller
            return line;
        // End of a block/expression
        }

        // Start of a method/block
        private void retrieveName(IntLinkedOpenHashSet colors) {
            // Start of a method/block
            synchronized (colors) {
                // Access to the current/parent object
                this.colorName = colors.removeFirstInt();
            // End of a block/expression
            }
        // End of a block/expression
        }

        /**
         * Creates a new {@link SidebarTeam}
         */
        // Start of a method/block
        private void createTeam() {
            // Access to the current/parent object
            this.entityName = '§' + Integer.toHexString(colorName);

            // Access to the current/parent object
            this.sidebarTeam = new SidebarTeam(teamName, content, Component.empty(), entityName);
        // End of a block/expression
        }

        // Start of a method/block
        private void returnName(IntLinkedOpenHashSet colors) {
            // Start of a method/block
            synchronized (colors) {
                // Calls a method
                colors.add(colorName);
            // End of a block/expression
            }
        // End of a block/expression
        }

        /**
         * Gets a score creation packet
         *
         * @param objectiveName The objective name to be updated
         * @return a {@link UpdateScorePacket}
         */
        // Start of a method/block
        private UpdateScorePacket getScoreCreationPacket(String objectiveName) {
            //TODO displayName acts as a suffix to the objective name, find way to handle elegantly
            // Returns a value to the caller
            return new UpdateScorePacket(entityName, objectiveName, line, Component.empty(), numberFormat);
        // End of a block/expression
        }

        /**
         * Gets a score destruction packet
         *
         * @param objectiveName The objective name to be destroyed
         * @return a {@link UpdateScorePacket}
         */
        // Start of a method/block
        private ResetScorePacket getScoreDestructionPacket(String objectiveName) {
            // Returns a value to the caller
            return new ResetScorePacket(entityName, objectiveName);
        // End of a block/expression
        }

        /**
         * Gets a line score update packet
         *
         * @param objectiveName The objective name to be updated
         * @param score         The new score
         * @return a {@link UpdateScorePacket}
         */
        // Start of a method/block
        private UpdateScorePacket getLineScoreUpdatePacket(String objectiveName, int score) {
            //TODO displayName acts as a suffix to the objective name, find way to handle elegantly
            // Returns a value to the caller
            return new UpdateScorePacket(entityName, objectiveName, score, Component.empty(), numberFormat);
        // End of a block/expression
        }

        /**
         * Gets a number format update packet
         *
         * @param objectiveName The objective name to be updated
         * @param numberFormat  The new number format
         * @return a {@link UpdateScorePacket}
         */
        // Start of a method/block
        private UpdateScorePacket getNumberFormatPacket(String objectiveName, NumberFormat numberFormat) {
            // Returns a value to the caller
            return new UpdateScorePacket(entityName, objectiveName, line, Component.empty(), numberFormat);
        // End of a block/expression
        }

        /**
         * Refresh the prefix of the {@link SidebarTeam}
         *
         * @param content The new content
         */
        // Start of a method/block
        private void refreshContent(Component content) {
            // Access to the current/parent object
            this.sidebarTeam.refreshPrefix(content);
        // End of a block/expression
        }

    // End of a block/expression
    }

    /**
     * This class is used to create a team for the {@link Sidebar}
     */
    // Type declaration (class/interface/enum/record)
    private static class SidebarTeam {

        // Code statement
        private final String teamName;
        // Code statement
        private Component prefix, suffix;
        // Code statement
        private final String entityName;

        // Calls a method
        private final Component teamDisplayName = Component.text("displaynametest");
        // Assigns a value
        private final byte friendlyFlags = 0x00;
        // Assigns a value
        private final TeamsPacket.NameTagVisibility nameTagVisibility = TeamsPacket.NameTagVisibility.NEVER;
        // Assigns a value
        private final TeamsPacket.CollisionRule collisionRule = TeamsPacket.CollisionRule.NEVER;
        // Assigns a value
        private final NamedTextColor teamColor = NamedTextColor.WHITE;


        /**
         * The constructor to creates a team
         *
         * @param teamName   The registry name of the team
         * @param prefix     The team prefix
         * @param suffix     The team suffix
         * @param entityName The team entity name
         */
        // Start of a method/block
        private SidebarTeam(String teamName, Component prefix, Component suffix, String entityName) {
            // Access to the current/parent object
            this.teamName = teamName;
            // Access to the current/parent object
            this.prefix = prefix;
            // Access to the current/parent object
            this.suffix = suffix;
            // Access to the current/parent object
            this.entityName = entityName;
        // End of a block/expression
        }

        /**
         * Gets a team creation packet
         *
         * @return a {@link TeamsPacket} which creates a new team
         */
        // Start of a method/block
        private TeamsPacket getCreationPacket() {
            // Assigns a value
            final var action = new TeamsPacket.CreateTeamAction(teamDisplayName, friendlyFlags,
                    // Calls a method
                    nameTagVisibility, collisionRule, teamColor, prefix, suffix, List.of(entityName));
            // Returns a value to the caller
            return new TeamsPacket(teamName, action);
        // End of a block/expression
        }

        /**
         * Gets a team destruction packet
         *
         * @return a {@link TeamsPacket} which destroyed a team
         */
        // Start of a method/block
        private TeamsPacket getDestructionPacket() {
            // Returns a value to the caller
            return new TeamsPacket(teamName, new TeamsPacket.RemoveTeamAction());
        // End of a block/expression
        }

        /**
         * Updates the prefix of the {@link SidebarTeam}
         *
         * @param prefix The new prefix
         * @return a {@link TeamsPacket} with the updated prefix
         */
        // Start of a method/block
        private TeamsPacket updatePrefix(Component prefix) {
            // Assigns a value
            final var action = new TeamsPacket.UpdateTeamAction(teamDisplayName, friendlyFlags,
                    // Code statement
                    nameTagVisibility, collisionRule, teamColor, prefix, suffix);
            // Returns a value to the caller
            return new TeamsPacket(teamName, action);
        // End of a block/expression
        }

        /**
         * Gets the entity name of the team
         *
         * @return the entity name
         */
        // Start of a method/block
        private String getEntityName() {
            // Returns a value to the caller
            return entityName;
        // End of a block/expression
        }

        /**
         * Gets the prefix of the team
         *
         * @return the prefix
         */
        // Start of a method/block
        private Component getPrefix() {
            // Returns a value to the caller
            return prefix;
        // End of a block/expression
        }

        /**
         * Refresh the prefix of the {@link SidebarTeam}
         *
         * @param prefix The refreshed prefix
         */
        // Start of a method/block
        private void refreshPrefix(Component prefix) {
            // Access to the current/parent object
            this.prefix = prefix;
        // End of a block/expression
        }
    // End of a block/expression
    }


    // Type declaration (class/interface/enum/record)
    public record NumberFormat(FormatType formatType, @Nullable Component content) implements ComponentHolder<NumberFormat> {
        // Start of a method/block
        private NumberFormat() {
            // Calls a method
            this(FormatType.BLANK, null);
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<NumberFormat> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, NumberFormat value) {
                // Calls a method
                buffer.write(NetworkBuffer.Enum(FormatType.class), value.formatType);
                // Branch: checks a condition
                if (value.formatType == FormatType.STYLED) {
                    // Code statement
                    assert value.content != null;
                    // Calls a method
                    buffer.write(NetworkBuffer.COMPONENT, value.content);
                // Branch: checks a condition
                } else if (value.formatType == FormatType.FIXED) {
                    // Code statement
                    assert value.content != null;
                    // Calls a method
                    buffer.write(NetworkBuffer.COMPONENT, value.content);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public NumberFormat read(NetworkBuffer buffer) {
                // Calls a method
                final FormatType formatType = buffer.read(NetworkBuffer.Enum(FormatType.class));
                // Calls a method
                final Component content = formatType != FormatType.BLANK ? buffer.read(NetworkBuffer.COMPONENT) : null;
                // Returns a value to the caller
                return new NumberFormat(formatType, content);
            // End of a block/expression
            }
        // End of a block/expression
        };

        /**
         * A number format which has no sidebar score displayed
         *
         * @return a blank number format
         */
        // Start of a method/block
        public static NumberFormat blank() {
            // Returns a value to the caller
            return new NumberFormat();
        // End of a block/expression
        }

        /**
         * A number format which lets the sidebar scores be styled
         *
         * @param style a styled component
         */
        // Start of a method/block
        public static NumberFormat styled(Component style) {
            // Returns a value to the caller
            return new NumberFormat(FormatType.STYLED, style);
        // End of a block/expression
        }

        /**
         * A number format which lets the sidebar scores be styled with explicit text
         *
         * @param content the fixed component
         */
        // Start of a method/block
        public static NumberFormat fixed(Component content) {
            // Returns a value to the caller
            return new NumberFormat(FormatType.FIXED, content);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Returns a value to the caller
            return content != null ? List.of(content) : List.of();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public NumberFormat copyWithOperator(UnaryOperator<Component> operator) {
            // Branch: checks a condition
            if (content == null) return this;

            // Returns a value to the caller
            return new NumberFormat(
                    // Code statement
                    formatType,
                    // Code statement
                    operator.apply(content)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        private enum FormatType {
            // Code statement
            BLANK, STYLED, FIXED
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
