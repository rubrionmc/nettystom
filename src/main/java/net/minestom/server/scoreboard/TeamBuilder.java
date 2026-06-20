// Déclaration du paquet de ce fichier
package net.minestom.server.scoreboard;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TeamsPacket.CollisionRule;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TeamsPacket.NameTagVisibility;

/**
 * A builder which represents a fluent Object to built teams.
 */
// Déclaration de type (classe/interface/enum/record)
public class TeamBuilder {

    /**
     * The management for the teams
     */
    // Instruction de code
    private final TeamManager teamManager;
    /**
     * The team to create
     */
    // Instruction de code
    private final Team team;
    /**
     * True, if it should send an update packet
     */
    // Instruction de code
    private boolean updateTeam;

    /**
     * Creates an team builder.
     *
     * @param name        The name of the new team
     * @param teamManager The manager for the team
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder(String name, TeamManager teamManager) {
        // Appelle une méthode
        this(teamManager.exists(name) ? teamManager.getTeam(name) : new Team(name), teamManager);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an team builder.
     *
     * @param team        The new team
     * @param teamManager The manager for the team
     */
    // Début d'une méthode/d'un bloc
    private TeamBuilder(Team team, TeamManager teamManager) {
        // Accès à l'objet courant/parent
        this.team = team;
        // Accès à l'objet courant/parent
        this.teamManager = teamManager;
        // Accès à l'objet courant/parent
        this.updateTeam = false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the prefix of the {@link Team}.
     *
     * @param prefix The new prefix
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updatePrefix(Component prefix) {
        // Accès à l'objet courant/parent
        this.team.updatePrefix(prefix);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the color of the {@link Team}.
     *
     * @param color The new color
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updateTeamColor(NamedTextColor color) {
        // Accès à l'objet courant/parent
        this.team.updateTeamColor(color);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the suffix of the {@link Team}.
     *
     * @param suffix The new suffix
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updateSuffix(Component suffix) {
        // Accès à l'objet courant/parent
        this.team.updateSuffix(suffix);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the display name of the {@link Team}.
     *
     * @param displayName The new display name
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updateTeamDisplayName(Component displayName) {
        // Accès à l'objet courant/parent
        this.team.updateTeamDisplayName(displayName);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the {@link CollisionRule} of the {@link Team}.
     *
     * @param rule The new rule
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updateCollisionRule(CollisionRule rule) {
        // Accès à l'objet courant/parent
        this.team.updateCollisionRule(rule);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the {@link NameTagVisibility} of the {@link Team}.
     *
     * @param visibility The new tag visibility
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updateNameTagVisibility(NameTagVisibility visibility) {
        // Accès à l'objet courant/parent
        this.team.updateNameTagVisibility(visibility);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the friendly flags of the {@link Team}.
     *
     * @param flag The new friendly flag
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updateFriendlyFlags(byte flag) {
        // Accès à l'objet courant/parent
        this.team.updateFriendlyFlags(flag);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the friendly flags for allow friendly fire.
     *
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updateAllowFriendlyFire() {
        // Accès à l'objet courant/parent
        this.team.updateAllowFriendlyFire(true);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the friendly flags to sees invisible players of own team.
     *
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updateSeeInvisiblePlayers() {
        // Accès à l'objet courant/parent
        this.team.updateSeeInvisiblePlayers(true);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the prefix of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param prefix The new prefix
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder prefix(Component prefix) {
        // Accès à l'objet courant/parent
        this.team.setPrefix(prefix);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the suffix of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param suffix The new suffix
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder suffix(Component suffix) {
        // Accès à l'objet courant/parent
        this.team.setSuffix(suffix);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the color of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param color The new team color
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder teamColor(NamedTextColor color) {
        // Accès à l'objet courant/parent
        this.team.setTeamColor(color);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the display name of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param displayName The new display name
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder teamDisplayName(Component displayName) {
        // Accès à l'objet courant/parent
        this.team.setTeamDisplayName(displayName);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link CollisionRule} of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param rule The new rule
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder collisionRule(CollisionRule rule) {
        // Accès à l'objet courant/parent
        this.team.setCollisionRule(rule);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link NameTagVisibility} of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param visibility The new tag visibility
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder nameTagVisibility(NameTagVisibility visibility) {
        // Accès à l'objet courant/parent
        this.team.setNameTagVisibility(visibility);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the friendly flags of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param flag The new flag
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder friendlyFlags(byte flag) {
        // Accès à l'objet courant/parent
        this.team.setFriendlyFlags(flag);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the friendly flags for allow friendly fire without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder allowFriendlyFire() {
        // Accès à l'objet courant/parent
        this.team.setAllowFriendlyFire(true);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the friendly flags to sees invisible players of own team without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder seeInvisiblePlayers() {
        // Accès à l'objet courant/parent
        this.team.setSeeInvisiblePlayers(true);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Allows to send an update packet when the team is built.
     *
     * @return this builder, for chaining
     */
    // Début d'une méthode/d'un bloc
    public TeamBuilder updateTeamPacket() {
        // Accès à l'objet courant/parent
        this.updateTeam = true;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Built a team.
     *
     * @return the built team
     */
    // Début d'une méthode/d'un bloc
    public Team build() {
        // Embranchement : vérifie une condition
        if (!this.teamManager.exists(this.team)) this.teamManager.registerNewTeam(this.team);
        // Embranchement : vérifie une condition
        if (this.updateTeam) {
            // Accès à l'objet courant/parent
            this.team.sendUpdatePacket();
            // Accès à l'objet courant/parent
            this.updateTeam = false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return this.team;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
