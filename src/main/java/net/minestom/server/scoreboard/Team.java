// Package declaration for this file
package net.minestom.server.scoreboard;

// Import of a required class
import net.kyori.adventure.identity.Identity;
// Import of a required class
import net.kyori.adventure.pointer.Pointers;
// Import of a required class
import net.kyori.adventure.pointer.PointersSupplier;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.play.TeamsPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.TeamsPacket.CollisionRule;
// Import of a required class
import net.minestom.server.network.packet.server.play.TeamsPacket.NameTagVisibility;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Collections;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This object represents a team on a scoreboard that has a common display theme and other properties.
 */
// Type declaration (class/interface/enum/record)
public class Team implements PacketGroupingAudience {
    // Assigns a value
    private static final byte ALLOW_FRIENDLY_FIRE_BIT = 0x01;
    // Assigns a value
    private static final byte SEE_INVISIBLE_PLAYERS_BIT = 0x02;

    // Assigns a value
    protected static final PointersSupplier<Team> TEAM_POINTERS_SUPPLIER = PointersSupplier.<Team>builder()
            // Code statement
            .resolving(Identity.NAME, Team::getTeamName)
            // Code statement
            .resolving(Identity.DISPLAY_NAME, Team::getTeamDisplayName)
            // Calls a method
            .build();

    /**
     * A collection of all registered entities who are on the team.
     */
    // Code statement
    private final Set<String> members;

    /**
     * The registry name of the team.
     */
    // Code statement
    private final String teamName;
    /**
     * The display name of the team.
     */
    // Code statement
    private Component teamDisplayName;
    /**
     * A BitMask.
     */
    // Code statement
    private byte friendlyFlags;
    /**
     * The visibility of the team.
     */
    // Code statement
    private NameTagVisibility nameTagVisibility;
    /**
     * The collision rule of the team.
     */
    // Code statement
    private CollisionRule collisionRule;

    /**
     * Used to color the name of players on the team <br>
     * The color of a team defines how the names of the team members are visualized.
     */
    // Code statement
    private NamedTextColor teamColor;

    /**
     * Shown before the names of the players who belong to this team.
     */
    // Code statement
    private Component prefix;
    /**
     * Shown after the names of the player who belong to this team.
     */
    // Code statement
    private Component suffix;

    // Calls a method
    private final Set<Player> playerMembers = ConcurrentHashMap.newKeySet();
    // Code statement
    private boolean isPlayerMembersUpToDate;

    /**
     * Default constructor to creates a team.
     *
     * @param teamName The registry name for the team
     */
    // Start of a method/block
    protected Team(String teamName) {
        // Access to the current/parent object
        this.teamName = teamName;

        // Access to the current/parent object
        this.teamDisplayName = Component.empty();
        // Access to the current/parent object
        this.friendlyFlags = 0x00;
        // Access to the current/parent object
        this.nameTagVisibility = NameTagVisibility.ALWAYS;
        // Access to the current/parent object
        this.collisionRule = CollisionRule.ALWAYS;

        // Access to the current/parent object
        this.teamColor = NamedTextColor.WHITE;
        // Access to the current/parent object
        this.prefix = Component.empty();
        // Access to the current/parent object
        this.suffix = Component.empty();

        // Access to the current/parent object
        this.members = new CopyOnWriteArraySet<>();
    // End of a block/expression
    }

    /**
     * Adds a member to the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param member The member to be added
     */
    // Start of a method/block
    public void addMember(String member) {
        // Calls a method
        addMembers(List.of(member));
    // End of a block/expression
    }

    /**
     * Adds a members to the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toAdd The members to be added
     */
    // Start of a method/block
    public void addMembers(Collection<String> toAdd) {
        // Adds a new member to the team
        // Access to the current/parent object
        this.members.addAll(toAdd);

        // Initializes add player packet
        // Assigns a value
        final TeamsPacket addPlayerPacket = new TeamsPacket(teamName,
                // Creates a new object
                new TeamsPacket.AddEntitiesToTeamAction(toAdd));
        // Sends to all online players the add player packet
        // Calls a method
        PacketSendingUtils.broadcastPlayPacket(addPlayerPacket);

        // invalidate player members
        // Access to the current/parent object
        this.isPlayerMembersUpToDate = false;
    // End of a block/expression
    }

    /**
     * Removes a member from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param member The member to be removed
     */
    // Start of a method/block
    public void removeMember(String member) {
        // Calls a method
        removeMembers(List.of(member));
    // End of a block/expression
    }

    /**
     * Removes members from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toRemove The members to be removed
     */
    // Start of a method/block
    public void removeMembers(Collection<String> toRemove) {
        // Initializes remove player packet
        // Assigns a value
        final TeamsPacket removePlayerPacket = new TeamsPacket(teamName,
                // Creates a new object
                new TeamsPacket.RemoveEntitiesToTeamAction(toRemove));
        // Sends to all online player the remove player packet
        // Calls a method
        PacketSendingUtils.broadcastPlayPacket(removePlayerPacket);

        // Removes the member from the team
        // Access to the current/parent object
        this.members.removeAll(toRemove);

        // invalidate player members
        // Access to the current/parent object
        this.isPlayerMembersUpToDate = false;
    // End of a block/expression
    }

    /**
     * Changes the display name of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed <b>server side</b>.
     *
     * @param teamDisplayName The new display name
     */
    // Start of a method/block
    public void setTeamDisplayName(Component teamDisplayName) {
        // Access to the current/parent object
        this.teamDisplayName = teamDisplayName;
    // End of a block/expression
    }

    /**
     * Changes the display name of the team and sends an update packet.
     *
     * @param teamDisplayName The new display name
     */
    // Start of a method/block
    public void updateTeamDisplayName(Component teamDisplayName) {
        // Access to the current/parent object
        this.setTeamDisplayName(teamDisplayName);
        // Calls a method
        sendUpdatePacket();
    // End of a block/expression
    }

    /**
     * Changes the {@link NameTagVisibility} of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param visibility The new tag visibility
     * @see #updateNameTagVisibility(NameTagVisibility)
     */
    // Start of a method/block
    public void setNameTagVisibility(NameTagVisibility visibility) {
        // Access to the current/parent object
        this.nameTagVisibility = visibility;
    // End of a block/expression
    }

    /**
     * Changes the {@link NameTagVisibility} of the team and sends an update packet.
     *
     * @param nameTagVisibility The new tag visibility
     */
    // Start of a method/block
    public void updateNameTagVisibility(NameTagVisibility nameTagVisibility) {
        // Access to the current/parent object
        this.setNameTagVisibility(nameTagVisibility);
        // Calls a method
        sendUpdatePacket();
    // End of a block/expression
    }

    /**
     * Changes the {@link CollisionRule} of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param rule The new rule
     * @see #updateCollisionRule(CollisionRule)
     */
    // Start of a method/block
    public void setCollisionRule(CollisionRule rule) {
        // Access to the current/parent object
        this.collisionRule = rule;
    // End of a block/expression
    }

    /**
     * Changes the collision rule of the team and sends an update packet.
     *
     * @param collisionRule The new collision rule
     */
    // Start of a method/block
    public void updateCollisionRule(CollisionRule collisionRule) {
        // Access to the current/parent object
        this.setCollisionRule(collisionRule);
        // Calls a method
        sendUpdatePacket();
    // End of a block/expression
    }

    /**
     * Changes the color of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param color The new team color
     * @see #updateTeamColor(NamedTextColor)
     */
    // Start of a method/block
    public void setTeamColor(NamedTextColor color) {
        // Access to the current/parent object
        this.teamColor = color;
    // End of a block/expression
    }

    /**
     * Changes the color of the team and sends an update packet.
     *
     * @param color The new team color
     */
    // Start of a method/block
    public void updateTeamColor(NamedTextColor color) {
        // Access to the current/parent object
        this.setTeamColor(color);
        // Calls a method
        sendUpdatePacket();
    // End of a block/expression
    }

    /**
     * Changes the prefix of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param prefix The new prefix
     */
    // Start of a method/block
    public void setPrefix(Component prefix) {
        // Access to the current/parent object
        this.prefix = prefix;
    // End of a block/expression
    }

    /**
     * Changes the prefix of the team and sends an update packet.
     *
     * @param prefix The new prefix
     */
    // Start of a method/block
    public void updatePrefix(Component prefix) {
        // Access to the current/parent object
        this.setPrefix(prefix);
        // Calls a method
        sendUpdatePacket();
    // End of a block/expression
    }

    /**
     * Changes the suffix of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param suffix The new suffix
     */
    // Start of a method/block
    public void setSuffix(Component suffix) {
        // Access to the current/parent object
        this.suffix = suffix;
    // End of a block/expression
    }

    /**
     * Changes the suffix of the team and sends an update packet.
     *
     * @param suffix The new suffix
     */
    // Start of a method/block
    public void updateSuffix(Component suffix) {
        // Access to the current/parent object
        this.setSuffix(suffix);
        // Calls a method
        sendUpdatePacket();
    // End of a block/expression
    }

    /**
     * Changes the friendly flags of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param flag The new friendly flag
     */
    // Start of a method/block
    public void setFriendlyFlags(byte flag) {
        // Access to the current/parent object
        this.friendlyFlags = flag;
    // End of a block/expression
    }

    /**
     * Changes the friendly flags of the team and sends an update packet.
     *
     * @param flag The new friendly flag
     */
    // Start of a method/block
    public void updateFriendlyFlags(byte flag) {
        // Access to the current/parent object
        this.setFriendlyFlags(flag);
        // Access to the current/parent object
        this.sendUpdatePacket();
    // End of a block/expression
    }

    // Start of a method/block
    private boolean getFriendlyFlagBit(byte index) {
        // Returns a value to the caller
        return (this.friendlyFlags & index) == index;
    // End of a block/expression
    }

    // Start of a method/block
    private void setFriendlyFlagBit(byte index, boolean value) {
        // Branch: checks a condition
        if (value) {
            // Access to the current/parent object
            this.friendlyFlags |= index;
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.friendlyFlags &= ~index;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void setAllowFriendlyFire(boolean value) {
        // Access to the current/parent object
        this.setFriendlyFlagBit(ALLOW_FRIENDLY_FIRE_BIT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public void updateAllowFriendlyFire(boolean value) {
        // Access to the current/parent object
        this.setAllowFriendlyFire(value);
        // Access to the current/parent object
        this.sendUpdatePacket();
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isAllowFriendlyFire() {
        // Returns a value to the caller
        return this.getFriendlyFlagBit(ALLOW_FRIENDLY_FIRE_BIT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSeeInvisiblePlayers(boolean value) {
        // Access to the current/parent object
        this.setFriendlyFlagBit(SEE_INVISIBLE_PLAYERS_BIT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public void updateSeeInvisiblePlayers(boolean value) {
        // Access to the current/parent object
        this.setSeeInvisiblePlayers(value);
        // Access to the current/parent object
        this.sendUpdatePacket();
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSeeInvisiblePlayers() {
        // Returns a value to the caller
        return this.getFriendlyFlagBit(SEE_INVISIBLE_PLAYERS_BIT);
    // End of a block/expression
    }

    /**
     * Gets the registry name of the team.
     *
     * @return the registry name
     */
    // Start of a method/block
    public String getTeamName() {
        // Returns a value to the caller
        return teamName;
    // End of a block/expression
    }

    /**
     * Creates the creation packet to add a team.
     *
     * @return the packet to add the team
     */
    // Start of a method/block
    public TeamsPacket createTeamsCreationPacket() {
        // Assigns a value
        final var info = new TeamsPacket.CreateTeamAction(teamDisplayName, friendlyFlags,
                // Calls a method
                nameTagVisibility, collisionRule, teamColor, prefix, suffix, List.copyOf(members));
        // Returns a value to the caller
        return new TeamsPacket(teamName, info);
    // End of a block/expression
    }

    /**
     * Creates an destruction packet to remove the team.
     *
     * @return the packet to remove the team
     */
    // Start of a method/block
    public TeamsPacket createTeamDestructionPacket() {
        // Returns a value to the caller
        return new TeamsPacket(teamName, new TeamsPacket.RemoveTeamAction());
    // End of a block/expression
    }

    /**
     * Obtains an unmodifiable {@link Set} of registered players who are on the team.
     *
     * @return an unmodifiable {@link Set} of registered players
     */
    // Start of a method/block
    public Set<String> getMembers() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(members);
    // End of a block/expression
    }

    /**
     * Gets the display name of the team.
     *
     * @return the display name
     */
    // Start of a method/block
    public Component getTeamDisplayName() {
        // Returns a value to the caller
        return teamDisplayName;
    // End of a block/expression
    }

    /**
     * Gets the friendly flags of the team.
     *
     * @return the friendly flags
     */
    // Start of a method/block
    public byte getFriendlyFlags() {
        // Returns a value to the caller
        return friendlyFlags;
    // End of a block/expression
    }

    /**
     * Gets the tag visibility of the team.
     *
     * @return the tag visibility
     */
    // Start of a method/block
    public NameTagVisibility getNameTagVisibility() {
        // Returns a value to the caller
        return nameTagVisibility;
    // End of a block/expression
    }

    /**
     * Gets the collision rule of the team.
     *
     * @return the collision rule
     */
    // Start of a method/block
    public CollisionRule getCollisionRule() {
        // Returns a value to the caller
        return collisionRule;
    // End of a block/expression
    }

    /**
     * Gets the color of the team.
     *
     * @return the team color
     */
    // Start of a method/block
    public NamedTextColor getTeamColor() {
        // Returns a value to the caller
        return teamColor;
    // End of a block/expression
    }

    /**
     * Gets the prefix of the team.
     *
     * @return the team prefix
     */
    // Start of a method/block
    public Component getPrefix() {
        // Returns a value to the caller
        return prefix;
    // End of a block/expression
    }

    /**
     * Gets the suffix of the team.
     *
     * @return the suffix team
     */
    // Start of a method/block
    public Component getSuffix() {
        // Returns a value to the caller
        return suffix;
    // End of a block/expression
    }

    /**
     * Sends an {@link TeamsPacket.UpdateTeamAction} action packet.
     */
    // Start of a method/block
    public void sendUpdatePacket() {
        // Assigns a value
        final var info = new TeamsPacket.UpdateTeamAction(teamDisplayName, friendlyFlags,
                // Code statement
                nameTagVisibility, collisionRule, teamColor, prefix, suffix);
        // Calls a method
        PacketSendingUtils.broadcastPlayPacket(new TeamsPacket(teamName, info));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<? extends Player> getPlayers() {
        // Branch: checks a condition
        if (!this.isPlayerMembersUpToDate) {
            // Access to the current/parent object
            this.playerMembers.clear();

            // Loop: repeats a block
            for (String member : this.members) {
                // Calls a method
                Player player = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(member);

                // Branch: checks a condition
                if (player != null) {
                    // Access to the current/parent object
                    this.playerMembers.add(player);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Access to the current/parent object
            this.isPlayerMembersUpToDate = true;
        // End of a block/expression
        }

        // Returns a value to the caller
        return this.playerMembers;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pointers pointers() {
        // Returns a value to the caller
        return TEAM_POINTERS_SUPPLIER.view(this);
    // End of a block/expression
    }
// End of a block/expression
}
