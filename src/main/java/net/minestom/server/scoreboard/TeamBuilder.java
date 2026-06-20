// Package declaration for this file
package net.minestom.server.scoreboard;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.network.packet.server.play.TeamsPacket.CollisionRule;
// Import of a required class
import net.minestom.server.network.packet.server.play.TeamsPacket.NameTagVisibility;

/**
 * A builder which represents a fluent Object to built teams.
 */
// Type declaration (class/interface/enum/record)
public class TeamBuilder {

    /**
     * The management for the teams
     */
    // Code statement
    private final TeamManager teamManager;
    /**
     * The team to create
     */
    // Code statement
    private final Team team;
    /**
     * True, if it should send an update packet
     */
    // Code statement
    private boolean updateTeam;

    /**
     * Creates an team builder.
     *
     * @param name        The name of the new team
     * @param teamManager The manager for the team
     */
    // Start of a method/block
    public TeamBuilder(String name, TeamManager teamManager) {
        // Calls a method
        this(teamManager.exists(name) ? teamManager.getTeam(name) : new Team(name), teamManager);
    // End of a block/expression
    }

    /**
     * Creates an team builder.
     *
     * @param team        The new team
     * @param teamManager The manager for the team
     */
    // Start of a method/block
    private TeamBuilder(Team team, TeamManager teamManager) {
        // Access to the current/parent object
        this.team = team;
        // Access to the current/parent object
        this.teamManager = teamManager;
        // Access to the current/parent object
        this.updateTeam = false;
    // End of a block/expression
    }

    /**
     * Updates the prefix of the {@link Team}.
     *
     * @param prefix The new prefix
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updatePrefix(Component prefix) {
        // Access to the current/parent object
        this.team.updatePrefix(prefix);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Updates the color of the {@link Team}.
     *
     * @param color The new color
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updateTeamColor(NamedTextColor color) {
        // Access to the current/parent object
        this.team.updateTeamColor(color);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Updates the suffix of the {@link Team}.
     *
     * @param suffix The new suffix
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updateSuffix(Component suffix) {
        // Access to the current/parent object
        this.team.updateSuffix(suffix);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Updates the display name of the {@link Team}.
     *
     * @param displayName The new display name
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updateTeamDisplayName(Component displayName) {
        // Access to the current/parent object
        this.team.updateTeamDisplayName(displayName);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Updates the {@link CollisionRule} of the {@link Team}.
     *
     * @param rule The new rule
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updateCollisionRule(CollisionRule rule) {
        // Access to the current/parent object
        this.team.updateCollisionRule(rule);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Updates the {@link NameTagVisibility} of the {@link Team}.
     *
     * @param visibility The new tag visibility
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updateNameTagVisibility(NameTagVisibility visibility) {
        // Access to the current/parent object
        this.team.updateNameTagVisibility(visibility);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Updates the friendly flags of the {@link Team}.
     *
     * @param flag The new friendly flag
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updateFriendlyFlags(byte flag) {
        // Access to the current/parent object
        this.team.updateFriendlyFlags(flag);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Updates the friendly flags for allow friendly fire.
     *
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updateAllowFriendlyFire() {
        // Access to the current/parent object
        this.team.updateAllowFriendlyFire(true);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Updates the friendly flags to sees invisible players of own team.
     *
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updateSeeInvisiblePlayers() {
        // Access to the current/parent object
        this.team.updateSeeInvisiblePlayers(true);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Changes the prefix of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param prefix The new prefix
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder prefix(Component prefix) {
        // Access to the current/parent object
        this.team.setPrefix(prefix);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Changes the suffix of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param suffix The new suffix
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder suffix(Component suffix) {
        // Access to the current/parent object
        this.team.setSuffix(suffix);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Changes the color of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param color The new team color
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder teamColor(NamedTextColor color) {
        // Access to the current/parent object
        this.team.setTeamColor(color);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Changes the display name of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param displayName The new display name
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder teamDisplayName(Component displayName) {
        // Access to the current/parent object
        this.team.setTeamDisplayName(displayName);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Changes the {@link CollisionRule} of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param rule The new rule
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder collisionRule(CollisionRule rule) {
        // Access to the current/parent object
        this.team.setCollisionRule(rule);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Changes the {@link NameTagVisibility} of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param visibility The new tag visibility
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder nameTagVisibility(NameTagVisibility visibility) {
        // Access to the current/parent object
        this.team.setNameTagVisibility(visibility);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Changes the friendly flags of the {@link Team} without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @param flag The new flag
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder friendlyFlags(byte flag) {
        // Access to the current/parent object
        this.team.setFriendlyFlags(flag);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Changes the friendly flags for allow friendly fire without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder allowFriendlyFire() {
        // Access to the current/parent object
        this.team.setAllowFriendlyFire(true);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Changes the friendly flags to sees invisible players of own team without an update packet.
     * <br><br>
     * <b>Warning: </b> If you do not call {@link #updateTeamPacket()}, this is only changed of the <b>server side</b>.
     *
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder seeInvisiblePlayers() {
        // Access to the current/parent object
        this.team.setSeeInvisiblePlayers(true);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Allows to send an update packet when the team is built.
     *
     * @return this builder, for chaining
     */
    // Start of a method/block
    public TeamBuilder updateTeamPacket() {
        // Access to the current/parent object
        this.updateTeam = true;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Built a team.
     *
     * @return the built team
     */
    // Start of a method/block
    public Team build() {
        // Branch: checks a condition
        if (!this.teamManager.exists(this.team)) this.teamManager.registerNewTeam(this.team);
        // Branch: checks a condition
        if (this.updateTeam) {
            // Access to the current/parent object
            this.team.sendUpdatePacket();
            // Access to the current/parent object
            this.updateTeam = false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return this.team;
    // End of a block/expression
    }

// End of a block/expression
}
