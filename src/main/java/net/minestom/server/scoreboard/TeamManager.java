// Package declaration for this file
package net.minestom.server.scoreboard;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;
// Import of a required class
import net.minestom.server.utils.UUIDUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * An object which manages all the {@link Team}'s
 */
// Type declaration (class/interface/enum/record)
public final class TeamManager {

    /**
     * Represents all registered teams
     */
    // Code statement
    private final Set<Team> teams;

    /**
     * Default constructor
     */
    // Start of a method/block
    public TeamManager() {
        // Access to the current/parent object
        this.teams = new CopyOnWriteArraySet<>();
    // End of a block/expression
    }

    /**
     * Registers a new {@link Team}
     *
     * @param team The team to be registered
     */
    // Start of a method/block
    void registerNewTeam(Team team) {
        // Access to the current/parent object
        this.teams.add(team);
        // Calls a method
        PacketSendingUtils.broadcastPlayPacket(team.createTeamsCreationPacket());
    // End of a block/expression
    }

    /**
     * Deletes a {@link Team}
     *
     * @param registryName The registry name of team
     * @return {@code true} if the team was deleted, otherwise {@code false}
     */
    // Start of a method/block
    public boolean deleteTeam(String registryName) {
        // Calls a method
        Team team = this.getTeam(registryName);
        // Branch: checks a condition
        if (team == null) return false;
        // Returns a value to the caller
        return this.deleteTeam(team);
    // End of a block/expression
    }

    /**
     * Deletes a {@link Team}
     *
     * @param team The team to be deleted
     * @return {@code true} if the team was deleted, otherwise {@code false}
     */
    // Start of a method/block
    public boolean deleteTeam(Team team) {
        // Sends to all online players a team destroy packet
        // Calls a method
        PacketSendingUtils.broadcastPlayPacket(team.createTeamDestructionPacket());
        // Returns a value to the caller
        return this.teams.remove(team);
    // End of a block/expression
    }

    /**
     * Initializes a new {@link TeamBuilder} for creating a team
     *
     * @param name The registry name of the team
     * @return the team builder
     */
    // Start of a method/block
    public TeamBuilder createBuilder(String name) {
        // Returns a value to the caller
        return new TeamBuilder(name, this);
    // End of a block/expression
    }

    /**
     * Creates a {@link Team} with only the registry name
     *
     * @param name The registry name
     * @return the created {@link Team}
     */
    // Start of a method/block
    public Team createTeam(String name) {
        // Returns a value to the caller
        return this.createBuilder(name).build();
    // End of a block/expression
    }

    /**
     * Creates a {@link Team} with the registry name, prefix, suffix and the team format
     *
     * @param name      The registry name
     * @param prefix    The team prefix
     * @param teamColor The team format
     * @param suffix    The team suffix
     * @return the created {@link Team} with a prefix, teamColor and suffix
     */
    // Start of a method/block
    public Team createTeam(String name, Component prefix, NamedTextColor teamColor, Component suffix) {
        // Returns a value to the caller
        return this.createBuilder(name).prefix(prefix).teamColor(teamColor).suffix(suffix).updateTeamPacket().build();
    // End of a block/expression
    }

    /**
     * Creates a {@link Team} with the registry name, display name, prefix, suffix and the team colro
     *
     * @param name        The registry name
     * @param displayName The display name
     * @param prefix      The team prefix
     * @param teamColor   The team color
     * @param suffix      The team suffix
     * @return the created {@link Team} with a prefix, teamColor, suffix and the display name
     */
    // Start of a method/block
    public Team createTeam(String name, Component displayName, Component prefix, NamedTextColor teamColor, Component suffix) {
        // Returns a value to the caller
        return this.createBuilder(name).teamDisplayName(displayName).prefix(prefix).teamColor(teamColor).suffix(suffix).updateTeamPacket().build();
    // End of a block/expression
    }

    /**
     * Gets a {@link Team} with the given name
     *
     * @param teamName The registry name of the team
     * @return a registered {@link Team} or {@code null}
     */
    // Start of a method/block
    public @Nullable Team getTeam(String teamName) {
        // Loop: repeats a block
        for (Team team : this.teams) {
            // Branch: checks a condition
            if (team.getTeamName().equals(teamName)) return team;
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    /**
     * Checks if the given name a registry name of a registered {@link Team}
     *
     * @param teamName The name of the team
     * @return {@code true} if the team is registered, otherwise {@code false}
     */
    // Start of a method/block
    public boolean exists(String teamName) {
        // Loop: repeats a block
        for (Team team : this.teams) {
            // Branch: checks a condition
            if (team.getTeamName().equals(teamName)) return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    /**
     * Checks if the given {@link Team} registered
     *
     * @param team The searched team
     * @return {@code true} if the team is registered, otherwise {@code false}
     */
    // Start of a method/block
    public boolean exists(Team team) {
        // Returns a value to the caller
        return this.exists(team.getTeamName());
    // End of a block/expression
    }

    /**
     * Gets a {@link List} with all registered {@link Player} in the team
     * <br>
     * <b>Note:</b> The list exclude all entities. To get all entities of the team, you can use {@link #getEntities(Team)}
     *
     * @param team The team
     * @return a {@link List} with all registered {@link Player}
     */
    // Start of a method/block
    public List<String> getPlayers(Team team) {
        // Calls a method
        List<String> players = new ArrayList<>();
        // Loop: repeats a block
        for (String member : team.getMembers()) {
            // Calls a method
            boolean match = UUIDUtils.isUuid(member);

            // Branch: checks a condition
            if (!match) players.add(member);
        // End of a block/expression
        }
        // Returns a value to the caller
        return players;
    // End of a block/expression
    }

    /**
     * Gets a {@link List} with all registered {@link LivingEntity} in the team
     * <br>
     * <b>Note:</b> The list exclude all players. To get all players of the team, you can use {@link #getPlayers(Team)}
     *
     * @param team The team
     * @return a {@link List} with all registered {@link LivingEntity}
     */
    // Start of a method/block
    public List<String> getEntities(Team team) {
        // Calls a method
        List<String> entities = new ArrayList<>();
        // Loop: repeats a block
        for (String member : team.getMembers()) {
            // Calls a method
            boolean match = UUIDUtils.isUuid(member);

            // Branch: checks a condition
            if (match) entities.add(member);
        // End of a block/expression
        }
        // Returns a value to the caller
        return entities;
    // End of a block/expression
    }

    /**
     * Gets a {@link Set} with all registered {@link Team}'s
     *
     * @return a {@link Set} with all registered {@link Team}'s
     */
    // Start of a method/block
    public Set<Team> getTeams() {
        // Returns a value to the caller
        return this.teams;
    // End of a block/expression
    }
// End of a block/expression
}
