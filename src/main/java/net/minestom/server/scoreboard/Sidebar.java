// Déclaration du paquet de ce fichier
package net.minestom.server.scoreboard;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.adventure.ComponentHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Collections;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public class Sidebar implements Scoreboard {

    // Appelle une méthode
    private static final AtomicInteger COUNTER = new AtomicInteger();

    /**
     * <b>WARNING:</b> You should NOT create any scoreboards/teams with the same prefixes as those
     */
    // Affecte une valeur
    private static final String SCOREBOARD_PREFIX = "sb-";
    // Affecte une valeur
    private static final String TEAM_PREFIX = "sbt-";

    /**
     * Limited by the notch client, do not change
     */
    // Affecte une valeur
    private static final int MAX_LINES_COUNT = 15;

    // Affecte une valeur
    private final Set<Player> viewers = new CopyOnWriteArraySet<>();

    // Affecte une valeur
    private final Set<ScoreboardLine> lines = new CopyOnWriteArraySet<>();
    // Appelle une méthode
    private final IntLinkedOpenHashSet availableColors = new IntLinkedOpenHashSet();

    // Instruction de code
    private final String objectiveName;

    // Instruction de code
    private Component title;

    /**
     * Creates a new sidebar
     *
     * @param title The title of the sidebar
     * @deprecated Use {@link #Sidebar(Component)}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Sidebar(String title) {
        // Appelle une méthode
        this(Component.text(title));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new sidebar
     *
     * @param title The title of the sidebar
     */
    // Début d'une méthode/d'un bloc
    public Sidebar(Component title) {
        // Accès à l'objet courant/parent
        this.title = title;

        // Accès à l'objet courant/parent
        this.objectiveName = SCOREBOARD_PREFIX + COUNTER.incrementAndGet();

        // Fill available colors for entities name showed in scoreboard
        // Boucle : répète un bloc
        for (int i = 0; i < 16; i++) {
            // Appelle une méthode
            availableColors.add(i);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link Sidebar} title
     *
     * @param title The new sidebar title
     * @deprecated Use {@link #setTitle(Component)}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setTitle(String title) {
        // Accès à l'objet courant/parent
        this.setTitle(Component.text(title));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link Sidebar} title
     *
     * @return The sidebar title
     */
    // Début d'une méthode/d'un bloc
    public Component getTitle() {
        // Renvoie une valeur à l'appelant
        return title;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link Sidebar} title
     *
     * @param title The new sidebar title
     */
    // Début d'une méthode/d'un bloc
    public void setTitle(Component title) {
        // Accès à l'objet courant/parent
        this.title = title;
        // Instruction de code
        sendPacketToViewers(new ScoreboardObjectivePacket(objectiveName, (byte) 2, title,
                // Instruction de code
                ScoreboardObjectivePacket.Type.INTEGER, null));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new {@link ScoreboardLine}.
     *
     * @param scoreboardLine the new scoreboard line
     * @throws IllegalStateException    if the sidebar cannot take more line
     * @throws IllegalArgumentException if the sidebar already contains the line {@code scoreboardLine}
     *                                  or has a line with the same id
     */
    // Début d'une méthode/d'un bloc
    public void createLine(ScoreboardLine scoreboardLine) {
        // Début d'une méthode/d'un bloc
        synchronized (lines) {
            // Appelle une méthode
            Check.stateCondition(lines.size() >= MAX_LINES_COUNT, "You cannot have more than " + MAX_LINES_COUNT + "  lines");
            // Appelle une méthode
            Check.argCondition(lines.contains(scoreboardLine), "You cannot add two times the same ScoreboardLine");

            // Check ID duplication
            // Boucle : répète un bloc
            for (ScoreboardLine line : lines) {
                // Instruction de code
                Check.argCondition(line.id.equals(scoreboardLine.id),
                        // Instruction de code
                        "You cannot add two ScoreboardLine with the same id");
            // Fin d'un bloc/d'une expression
            }

            // Setup line
            // Appelle une méthode
            scoreboardLine.retrieveName(availableColors);
            // Appelle une méthode
            scoreboardLine.createTeam();

            // Finally add the line in cache
            // Accès à l'objet courant/parent
            this.lines.add(scoreboardLine);

            // Send to current viewers
            // Appelle une méthode
            sendPacketsToViewers(scoreboardLine.sidebarTeam.getCreationPacket(), scoreboardLine.getScoreCreationPacket(objectiveName));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates a {@link ScoreboardLine} content through the given identifier.
     *
     * @param id      The identifier of the {@link ScoreboardLine}
     * @param content The new content for the {@link ScoreboardLine}
     */
    // Début d'une méthode/d'un bloc
    public void updateLineContent(String id, Component content) {
        // Appelle une méthode
        final ScoreboardLine scoreboardLine = getLine(id);
        // Embranchement : vérifie une condition
        if (scoreboardLine != null) {
            // Appelle une méthode
            scoreboardLine.refreshContent(content);
            // Appelle une méthode
            sendPacketToViewers(scoreboardLine.sidebarTeam.updatePrefix(content));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the score of a {@link ScoreboardLine} through the given identifier
     *
     * @param id    The identifier of the team
     * @param score The new score for the {@link ScoreboardLine}
     */
    // Début d'une méthode/d'un bloc
    public void updateLineScore(String id, int score) {
        // Appelle une méthode
        final ScoreboardLine scoreboardLine = getLine(id);
        // Embranchement : vérifie une condition
        if (scoreboardLine != null) {
            // Affecte une valeur
            scoreboardLine.line = score;
            // Appelle une méthode
            sendPacketToViewers(scoreboardLine.getLineScoreUpdatePacket(objectiveName, score));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates a {@link ScoreboardLine} number format through the given identifier.
     *
     * @param id           The identifier of the {@link ScoreboardLine}
     * @param numberFormat The new number format for the {@link ScoreboardLine}
     */
    // Début d'une méthode/d'un bloc
    public void updateLineNumberFormat(String id, NumberFormat numberFormat) {
        // Appelle une méthode
        final ScoreboardLine scoreboardLine = getLine(id);
        // Embranchement : vérifie une condition
        if (scoreboardLine != null) {
            // Affecte une valeur
            scoreboardLine.numberFormat = numberFormat;
            // Appelle une méthode
            sendPacketsToViewers(scoreboardLine.getNumberFormatPacket(objectiveName, numberFormat));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link ScoreboardLine} through the given identifier
     *
     * @param id The identifier of the line
     * @return a {@link ScoreboardLine} or {@code null}
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public ScoreboardLine getLine(String id) {
        // Boucle : répète un bloc
        for (ScoreboardLine line : lines) {
            // Embranchement : vérifie une condition
            if (line.id.equals(id))
                // Renvoie une valeur à l'appelant
                return line;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link Set} containing all the registered lines.
     *
     * @return an unmodifiable set containing the sidebar's lines
     */
    // Début d'une méthode/d'un bloc
    public Set<ScoreboardLine> getLines() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(lines);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes a {@link ScoreboardLine} through the given identifier
     *
     * @param id the identifier of the {@link ScoreboardLine}
     */
    // Début d'une méthode/d'un bloc
    public void removeLine(String id) {
        // Accès à l'objet courant/parent
        this.lines.removeIf(line -> {
            // Embranchement : vérifie une condition
            if (line.id.equals(id)) {

                // Remove the line for current viewers
                // Appelle une méthode
                sendPacketsToViewers(line.getScoreDestructionPacket(objectiveName), line.sidebarTeam.getDestructionPacket());

                // Appelle une méthode
                line.returnName(availableColors);
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        });
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
            ScoreboardObjectivePacket scoreboardObjectivePacket = this.getCreationObjectivePacket(this.title, ScoreboardObjectivePacket.Type.INTEGER);
            // Appelle une méthode
            player.sendPacket(scoreboardObjectivePacket);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        DisplayScoreboardPacket displayScoreboardPacket = this.getDisplayScoreboardPacket((byte) 1);
        // Instruction de code
        player.sendPacket(displayScoreboardPacket); // Show sidebar scoreboard (wait for scores packet)
        // Boucle : répète un bloc
        for (ScoreboardLine line : lines) {
            // Appelle une méthode
            player.sendPacket(line.sidebarTeam.getCreationPacket());
            // Appelle une méthode
            player.sendPacket(line.getScoreCreationPacket(objectiveName));
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
        if (!result) return false;
        // Appelle une méthode
        ScoreboardObjectivePacket scoreboardObjectivePacket = this.getDestructionObjectivePacket();
        // Appelle une méthode
        player.sendPacket(scoreboardObjectivePacket);
        // Boucle : répète un bloc
        for (ScoreboardLine line : lines) {
            // Instruction de code
            player.sendPacket(line.getScoreDestructionPacket(objectiveName)); // Is it necessary?
            // Appelle une méthode
            player.sendPacket(line.sidebarTeam.getDestructionPacket());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Set<Player> getViewers() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(viewers);
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

    /**
     * This class is used to create a line for the sidebar.
     */
    // Déclaration de type (classe/interface/enum/record)
    public static class ScoreboardLine {

        /**
         * The identifier is used to modify the line later
         */
        // Instruction de code
        private final String id;
        /**
         * The content for the line
         */
        // Instruction de code
        private final Component content;
        /**
         * The score of the line
         */
        // Instruction de code
        private int line;
        /**
         * The number format of the line
         */
        // Instruction de code
        private NumberFormat numberFormat;

        // Instruction de code
        private final String teamName;
        /**
         * The name of the score ({@code entityName}) which is essentially an identifier
         */
        // Instruction de code
        private int colorName;
        // Instruction de code
        private String entityName;
        /**
         * The sidebar team of the line
         */
        // Instruction de code
        private SidebarTeam sidebarTeam;

        // Début d'une méthode/d'un bloc
        public ScoreboardLine(String id, Component content, int line) {
            // Appelle une méthode
            this(id, content, line, null);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public ScoreboardLine(String id, Component content, int line, @Nullable NumberFormat numberFormat) {
            // Accès à l'objet courant/parent
            this.id = id;
            // Accès à l'objet courant/parent
            this.content = content;
            // Accès à l'objet courant/parent
            this.line = line;
            // Accès à l'objet courant/parent
            this.numberFormat = numberFormat;

            // Accès à l'objet courant/parent
            this.teamName = TEAM_PREFIX + COUNTER.incrementAndGet();
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the identifier of the line
         *
         * @return the line identifier
         */
        // Début d'une méthode/d'un bloc
        public String getId() {
            // Renvoie une valeur à l'appelant
            return id;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the content of the line
         *
         * @return The line content
         */
        // Début d'une méthode/d'un bloc
        public Component getContent() {
            // Renvoie une valeur à l'appelant
            return sidebarTeam == null ? content : sidebarTeam.getPrefix();
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the position of the line
         *
         * @return the line position
         */
        // Début d'une méthode/d'un bloc
        public int getLine() {
            // Renvoie une valeur à l'appelant
            return line;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void retrieveName(IntLinkedOpenHashSet colors) {
            // Début d'une méthode/d'un bloc
            synchronized (colors) {
                // Accès à l'objet courant/parent
                this.colorName = colors.removeFirstInt();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        /**
         * Creates a new {@link SidebarTeam}
         */
        // Début d'une méthode/d'un bloc
        private void createTeam() {
            // Accès à l'objet courant/parent
            this.entityName = '§' + Integer.toHexString(colorName);

            // Accès à l'objet courant/parent
            this.sidebarTeam = new SidebarTeam(teamName, content, Component.empty(), entityName);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void returnName(IntLinkedOpenHashSet colors) {
            // Début d'une méthode/d'un bloc
            synchronized (colors) {
                // Appelle une méthode
                colors.add(colorName);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets a score creation packet
         *
         * @param objectiveName The objective name to be updated
         * @return a {@link UpdateScorePacket}
         */
        // Début d'une méthode/d'un bloc
        private UpdateScorePacket getScoreCreationPacket(String objectiveName) {
            //TODO displayName acts as a suffix to the objective name, find way to handle elegantly
            // Renvoie une valeur à l'appelant
            return new UpdateScorePacket(entityName, objectiveName, line, Component.empty(), numberFormat);
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets a score destruction packet
         *
         * @param objectiveName The objective name to be destroyed
         * @return a {@link UpdateScorePacket}
         */
        // Début d'une méthode/d'un bloc
        private ResetScorePacket getScoreDestructionPacket(String objectiveName) {
            // Renvoie une valeur à l'appelant
            return new ResetScorePacket(entityName, objectiveName);
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets a line score update packet
         *
         * @param objectiveName The objective name to be updated
         * @param score         The new score
         * @return a {@link UpdateScorePacket}
         */
        // Début d'une méthode/d'un bloc
        private UpdateScorePacket getLineScoreUpdatePacket(String objectiveName, int score) {
            //TODO displayName acts as a suffix to the objective name, find way to handle elegantly
            // Renvoie une valeur à l'appelant
            return new UpdateScorePacket(entityName, objectiveName, score, Component.empty(), numberFormat);
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets a number format update packet
         *
         * @param objectiveName The objective name to be updated
         * @param numberFormat  The new number format
         * @return a {@link UpdateScorePacket}
         */
        // Début d'une méthode/d'un bloc
        private UpdateScorePacket getNumberFormatPacket(String objectiveName, NumberFormat numberFormat) {
            // Renvoie une valeur à l'appelant
            return new UpdateScorePacket(entityName, objectiveName, line, Component.empty(), numberFormat);
        // Fin d'un bloc/d'une expression
        }

        /**
         * Refresh the prefix of the {@link SidebarTeam}
         *
         * @param content The new content
         */
        // Début d'une méthode/d'un bloc
        private void refreshContent(Component content) {
            // Accès à l'objet courant/parent
            this.sidebarTeam.refreshPrefix(content);
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

    /**
     * This class is used to create a team for the {@link Sidebar}
     */
    // Déclaration de type (classe/interface/enum/record)
    private static class SidebarTeam {

        // Instruction de code
        private final String teamName;
        // Instruction de code
        private Component prefix, suffix;
        // Instruction de code
        private final String entityName;

        // Appelle une méthode
        private final Component teamDisplayName = Component.text("displaynametest");
        // Affecte une valeur
        private final byte friendlyFlags = 0x00;
        // Affecte une valeur
        private final TeamsPacket.NameTagVisibility nameTagVisibility = TeamsPacket.NameTagVisibility.NEVER;
        // Affecte une valeur
        private final TeamsPacket.CollisionRule collisionRule = TeamsPacket.CollisionRule.NEVER;
        // Affecte une valeur
        private final NamedTextColor teamColor = NamedTextColor.WHITE;


        /**
         * The constructor to creates a team
         *
         * @param teamName   The registry name of the team
         * @param prefix     The team prefix
         * @param suffix     The team suffix
         * @param entityName The team entity name
         */
        // Début d'une méthode/d'un bloc
        private SidebarTeam(String teamName, Component prefix, Component suffix, String entityName) {
            // Accès à l'objet courant/parent
            this.teamName = teamName;
            // Accès à l'objet courant/parent
            this.prefix = prefix;
            // Accès à l'objet courant/parent
            this.suffix = suffix;
            // Accès à l'objet courant/parent
            this.entityName = entityName;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets a team creation packet
         *
         * @return a {@link TeamsPacket} which creates a new team
         */
        // Début d'une méthode/d'un bloc
        private TeamsPacket getCreationPacket() {
            // Affecte une valeur
            final var action = new TeamsPacket.CreateTeamAction(teamDisplayName, friendlyFlags,
                    // Appelle une méthode
                    nameTagVisibility, collisionRule, teamColor, prefix, suffix, List.of(entityName));
            // Renvoie une valeur à l'appelant
            return new TeamsPacket(teamName, action);
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets a team destruction packet
         *
         * @return a {@link TeamsPacket} which destroyed a team
         */
        // Début d'une méthode/d'un bloc
        private TeamsPacket getDestructionPacket() {
            // Renvoie une valeur à l'appelant
            return new TeamsPacket(teamName, new TeamsPacket.RemoveTeamAction());
        // Fin d'un bloc/d'une expression
        }

        /**
         * Updates the prefix of the {@link SidebarTeam}
         *
         * @param prefix The new prefix
         * @return a {@link TeamsPacket} with the updated prefix
         */
        // Début d'une méthode/d'un bloc
        private TeamsPacket updatePrefix(Component prefix) {
            // Affecte une valeur
            final var action = new TeamsPacket.UpdateTeamAction(teamDisplayName, friendlyFlags,
                    // Instruction de code
                    nameTagVisibility, collisionRule, teamColor, prefix, suffix);
            // Renvoie une valeur à l'appelant
            return new TeamsPacket(teamName, action);
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the entity name of the team
         *
         * @return the entity name
         */
        // Début d'une méthode/d'un bloc
        private String getEntityName() {
            // Renvoie une valeur à l'appelant
            return entityName;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the prefix of the team
         *
         * @return the prefix
         */
        // Début d'une méthode/d'un bloc
        private Component getPrefix() {
            // Renvoie une valeur à l'appelant
            return prefix;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Refresh the prefix of the {@link SidebarTeam}
         *
         * @param prefix The refreshed prefix
         */
        // Début d'une méthode/d'un bloc
        private void refreshPrefix(Component prefix) {
            // Accès à l'objet courant/parent
            this.prefix = prefix;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }


    // Déclaration de type (classe/interface/enum/record)
    public record NumberFormat(FormatType formatType, @Nullable Component content) implements ComponentHolder<NumberFormat> {
        // Début d'une méthode/d'un bloc
        private NumberFormat() {
            // Appelle une méthode
            this(FormatType.BLANK, null);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<NumberFormat> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, NumberFormat value) {
                // Appelle une méthode
                buffer.write(NetworkBuffer.Enum(FormatType.class), value.formatType);
                // Embranchement : vérifie une condition
                if (value.formatType == FormatType.STYLED) {
                    // Instruction de code
                    assert value.content != null;
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.COMPONENT, value.content);
                // Embranchement : vérifie une condition
                } else if (value.formatType == FormatType.FIXED) {
                    // Instruction de code
                    assert value.content != null;
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.COMPONENT, value.content);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public NumberFormat read(NetworkBuffer buffer) {
                // Appelle une méthode
                final FormatType formatType = buffer.read(NetworkBuffer.Enum(FormatType.class));
                // Appelle une méthode
                final Component content = formatType != FormatType.BLANK ? buffer.read(NetworkBuffer.COMPONENT) : null;
                // Renvoie une valeur à l'appelant
                return new NumberFormat(formatType, content);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        /**
         * A number format which has no sidebar score displayed
         *
         * @return a blank number format
         */
        // Début d'une méthode/d'un bloc
        public static NumberFormat blank() {
            // Renvoie une valeur à l'appelant
            return new NumberFormat();
        // Fin d'un bloc/d'une expression
        }

        /**
         * A number format which lets the sidebar scores be styled
         *
         * @param style a styled component
         */
        // Début d'une méthode/d'un bloc
        public static NumberFormat styled(Component style) {
            // Renvoie une valeur à l'appelant
            return new NumberFormat(FormatType.STYLED, style);
        // Fin d'un bloc/d'une expression
        }

        /**
         * A number format which lets the sidebar scores be styled with explicit text
         *
         * @param content the fixed component
         */
        // Début d'une méthode/d'un bloc
        public static NumberFormat fixed(Component content) {
            // Renvoie une valeur à l'appelant
            return new NumberFormat(FormatType.FIXED, content);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Renvoie une valeur à l'appelant
            return content != null ? List.of(content) : List.of();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public NumberFormat copyWithOperator(UnaryOperator<Component> operator) {
            // Embranchement : vérifie une condition
            if (content == null) return this;

            // Renvoie une valeur à l'appelant
            return new NumberFormat(
                    // Boucle : répète un bloc
                    formatType,
                    // Instruction de code
                    operator.apply(content)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        private enum FormatType {
            // Instruction de code
            BLANK, STYLED, FIXED
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
