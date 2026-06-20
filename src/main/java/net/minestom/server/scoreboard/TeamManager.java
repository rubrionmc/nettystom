// Déclaration du paquet de ce fichier
package net.minestom.server.scoreboard;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.UUIDUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * An object which manages all the {@link Team}'s
 */
// Déclaration de type (classe/interface/enum/record)
public final class TeamManager {

    /**
     * Represents all registered teams
     */
    // Instruction de code
    private final Set<Team> teams;

    /**
     * Default constructor
     */
    // Début d'une méthode/d'un bloc
    public TeamManager() {
        // Accès à l'objet courant/parent
        this.teams = new CopyOnWriteArraySet<>();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Registers a new {@link Team}
     *
     * @param team The team to be registered
     */
    // Début d'une méthode/d'un bloc
    void registerNewTeam(Team team) {
        // Accès à l'objet courant/parent
        this.teams.add(team);
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(team.createTeamsCreationPacket());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Deletes a {@link Team}
     *
     * @param registryName The registry name of team
     * @return {@code true} if the team was deleted, otherwise {@code false}
     */
    // Début d'une méthode/d'un bloc
    public boolean deleteTeam(String registryName) {
        // Appelle une méthode
        Team team = this.getTeam(registryName);
        // Embranchement : vérifie une condition
        if (team == null) return false;
        // Renvoie une valeur à l'appelant
        return this.deleteTeam(team);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Deletes a {@link Team}
     *
     * @param team The team to be deleted
     * @return {@code true} if the team was deleted, otherwise {@code false}
     */
    // Début d'une méthode/d'un bloc
    public boolean deleteTeam(Team team) {
        // Sends to all online players a team destroy packet
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(team.createTeamDestructionPacket());
        // Renvoie une valeur à l'appelant
        return this.teams.remove(team);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Initializes a new {@link TeamBuilder} for creating a team
     *
     * @param name The registry name of the team
     * @return the team builder
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder createBuilder(String name) {
        // Renvoie une valeur à l'appelant
        return new TeamBuilder(name, this);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link Team} with only the registry name
     *
     * @param name The registry name
     * @return the created {@link Team}
     */
    // Début d'une méthode/d'un bloc
    public Team createTeam(String name) {
        // Renvoie une valeur à l'appelant
        return this.createBuilder(name).build();
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public Team createTeam(String name, Component prefix, NamedTextColor teamColor, Component suffix) {
        // Renvoie une valeur à l'appelant
        return this.createBuilder(name).prefix(prefix).teamColor(teamColor).suffix(suffix).updateTeamPacket().build();
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public Team createTeam(String name, Component displayName, Component prefix, NamedTextColor teamColor, Component suffix) {
        // Renvoie une valeur à l'appelant
        return this.createBuilder(name).teamDisplayName(displayName).prefix(prefix).teamColor(teamColor).suffix(suffix).updateTeamPacket().build();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link Team} with the given name
     *
     * @param teamName The registry name of the team
     * @return a registered {@link Team} or {@code null}
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Team getTeam(String teamName) {
        // Boucle : répète un bloc
        for (Team team : this.teams) {
            // Embranchement : vérifie une condition
            if (team.getTeamName().equals(teamName)) return team;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the given name a registry name of a registered {@link Team}
     *
     * @param teamName The name of the team
     * @return {@code true} if the team is registered, otherwise {@code false}
     */
    // Début d'une méthode/d'un bloc
    public boolean exists(String teamName) {
        // Boucle : répète un bloc
        for (Team team : this.teams) {
            // Embranchement : vérifie une condition
            if (team.getTeamName().equals(teamName)) return true;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the given {@link Team} registered
     *
     * @param team The searched team
     * @return {@code true} if the team is registered, otherwise {@code false}
     */
    // Début d'une méthode/d'un bloc
    public boolean exists(Team team) {
        // Renvoie une valeur à l'appelant
        return this.exists(team.getTeamName());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link List} with all registered {@link Player} in the team
     * <br>
     * <b>Note:</b> The list exclude all entities. To get all entities of the team, you can use {@link #getEntities(Team)}
     *
     * @param team The team
     * @return a {@link List} with all registered {@link Player}
     */
    // Début d'une méthode/d'un bloc
    public List<String> getPlayers(Team team) {
        // Appelle une méthode
        List<String> players = new ArrayList<>();
        // Boucle : répète un bloc
        for (String member : team.getMembers()) {
            // Appelle une méthode
            boolean match = UUIDUtils.isUuid(member);

            // Embranchement : vérifie une condition
            if (!match) players.add(member);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return players;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link List} with all registered {@link LivingEntity} in the team
     * <br>
     * <b>Note:</b> The list exclude all players. To get all players of the team, you can use {@link #getPlayers(Team)}
     *
     * @param team The team
     * @return a {@link List} with all registered {@link LivingEntity}
     */
    // Début d'une méthode/d'un bloc
    public List<String> getEntities(Team team) {
        // Appelle une méthode
        List<String> entities = new ArrayList<>();
        // Boucle : répète un bloc
        for (String member : team.getMembers()) {
            // Appelle une méthode
            boolean match = UUIDUtils.isUuid(member);

            // Embranchement : vérifie une condition
            if (match) entities.add(member);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return entities;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link Set} with all registered {@link Team}'s
     *
     * @return a {@link Set} with all registered {@link Team}'s
     */
    // Début d'une méthode/d'un bloc
    public Set<Team> getTeams() {
        // Renvoie une valeur à l'appelant
        return this.teams;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
