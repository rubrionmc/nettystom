// Déclaration du paquet de ce fichier
package net.minestom.server.scoreboard;

// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identity;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.Pointers;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.PointersSupplier;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TeamsPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TeamsPacket.CollisionRule;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TeamsPacket.NameTagVisibility;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Collections;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This object represents a team on a scoreboard that has a common display theme and other properties.
 */
// Déclaration de type (classe/interface/enum/record)
public class Team implements PacketGroupingAudience {
    // Affecte une valeur
    private static final byte ALLOW_FRIENDLY_FIRE_BIT = 0x01;
    // Affecte une valeur
    private static final byte SEE_INVISIBLE_PLAYERS_BIT = 0x02;

    // Affecte une valeur
    protected static final PointersSupplier<Team> TEAM_POINTERS_SUPPLIER = PointersSupplier.<Team>builder()
            // Instruction de code
            .resolving(Identity.NAME, Team::getTeamName)
            // Instruction de code
            .resolving(Identity.DISPLAY_NAME, Team::getTeamDisplayName)
            // Appelle une méthode
            .build();

    /**
     * A collection of all registered entities who are on the team.
     */
    // Instruction de code
    private final Set<String> members;

    /**
     * The registry name of the team.
     */
    // Instruction de code
    private final String teamName;
    /**
     * The display name of the team.
     */
    // Instruction de code
    private Component teamDisplayName;
    /**
     * A BitMask.
     */
    // Instruction de code
    private byte friendlyFlags;
    /**
     * The visibility of the team.
     */
    // Instruction de code
    private NameTagVisibility nameTagVisibility;
    /**
     * The collision rule of the team.
     */
    // Instruction de code
    private CollisionRule collisionRule;

    /**
     * Used to color the name of players on the team <br>
     * The color of a team defines how the names of the team members are visualized.
     */
    // Instruction de code
    private NamedTextColor teamColor;

    /**
     * Shown before the names of the players who belong to this team.
     */
    // Instruction de code
    private Component prefix;
    /**
     * Shown after the names of the player who belong to this team.
     */
    // Instruction de code
    private Component suffix;

    // Appelle une méthode
    private final Set<Player> playerMembers = ConcurrentHashMap.newKeySet();
    // Instruction de code
    private boolean isPlayerMembersUpToDate;

    /**
     * Default constructor to creates a team.
     *
     * @param teamName The registry name for the team
     */
    // Début d'une méthode/d'un bloc
    protected Team(String teamName) {
        // Accès à l'objet courant/parent
        this.teamName = teamName;

        // Accès à l'objet courant/parent
        this.teamDisplayName = Component.empty();
        // Accès à l'objet courant/parent
        this.friendlyFlags = 0x00;
        // Accès à l'objet courant/parent
        this.nameTagVisibility = NameTagVisibility.ALWAYS;
        // Accès à l'objet courant/parent
        this.collisionRule = CollisionRule.ALWAYS;

        // Accès à l'objet courant/parent
        this.teamColor = NamedTextColor.WHITE;
        // Accès à l'objet courant/parent
        this.prefix = Component.empty();
        // Accès à l'objet courant/parent
        this.suffix = Component.empty();

        // Accès à l'objet courant/parent
        this.members = new CopyOnWriteArraySet<>();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds a member to the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param member The member to be added
     */
    // Début d'une méthode/d'un bloc
    public void addMember(String member) {
        // Appelle une méthode
        addMembers(List.of(member));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds a members to the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toAdd The members to be added
     */
    // Début d'une méthode/d'un bloc
    public void addMembers(Collection<String> toAdd) {
        // Adds a new member to the team
        // Accès à l'objet courant/parent
        this.members.addAll(toAdd);

        // Initializes add player packet
        // Affecte une valeur
        final TeamsPacket addPlayerPacket = new TeamsPacket(teamName,
                // Crée un nouvel objet
                new TeamsPacket.AddEntitiesToTeamAction(toAdd));
        // Sends to all online players the add player packet
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(addPlayerPacket);

        // invalidate player members
        // Accès à l'objet courant/parent
        this.isPlayerMembersUpToDate = false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes a member from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param member The member to be removed
     */
    // Début d'une méthode/d'un bloc
    public void removeMember(String member) {
        // Appelle une méthode
        removeMembers(List.of(member));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes members from the {@link Team}.
     * <br>
     * This member collection can contain {@link Player} or {@link LivingEntity}.
     * For players use their username, for entities use their UUID
     *
     * @param toRemove The members to be removed
     */
    // Début d'une méthode/d'un bloc
    public void removeMembers(Collection<String> toRemove) {
        // Initializes remove player packet
        // Affecte une valeur
        final TeamsPacket removePlayerPacket = new TeamsPacket(teamName,
                // Crée un nouvel objet
                new TeamsPacket.RemoveEntitiesToTeamAction(toRemove));
        // Sends to all online player the remove player packet
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(removePlayerPacket);

        // Removes the member from the team
        // Accès à l'objet courant/parent
        this.members.removeAll(toRemove);

        // invalidate player members
        // Accès à l'objet courant/parent
        this.isPlayerMembersUpToDate = false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the display name of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed <b>server side</b>.
     *
     * @param teamDisplayName The new display name
     */
    // Début d'une méthode/d'un bloc
    public void setTeamDisplayName(Component teamDisplayName) {
        // Accès à l'objet courant/parent
        this.teamDisplayName = teamDisplayName;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the display name of the team and sends an update packet.
     *
     * @param teamDisplayName The new display name
     */
    // Début d'une méthode/d'un bloc
    public void updateTeamDisplayName(Component teamDisplayName) {
        // Accès à l'objet courant/parent
        this.setTeamDisplayName(teamDisplayName);
        // Appelle une méthode
        sendUpdatePacket();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link NameTagVisibility} of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param visibility The new tag visibility
     * @see #updateNameTagVisibility(NameTagVisibility)
     */
    // Début d'une méthode/d'un bloc
    public void setNameTagVisibility(NameTagVisibility visibility) {
        // Accès à l'objet courant/parent
        this.nameTagVisibility = visibility;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link NameTagVisibility} of the team and sends an update packet.
     *
     * @param nameTagVisibility The new tag visibility
     */
    // Début d'une méthode/d'un bloc
    public void updateNameTagVisibility(NameTagVisibility nameTagVisibility) {
        // Accès à l'objet courant/parent
        this.setNameTagVisibility(nameTagVisibility);
        // Appelle une méthode
        sendUpdatePacket();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link CollisionRule} of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param rule The new rule
     * @see #updateCollisionRule(CollisionRule)
     */
    // Début d'une méthode/d'un bloc
    public void setCollisionRule(CollisionRule rule) {
        // Accès à l'objet courant/parent
        this.collisionRule = rule;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the collision rule of the team and sends an update packet.
     *
     * @param collisionRule The new collision rule
     */
    // Début d'une méthode/d'un bloc
    public void updateCollisionRule(CollisionRule collisionRule) {
        // Accès à l'objet courant/parent
        this.setCollisionRule(collisionRule);
        // Appelle une méthode
        sendUpdatePacket();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the color of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param color The new team color
     * @see #updateTeamColor(NamedTextColor)
     */
    // Début d'une méthode/d'un bloc
    public void setTeamColor(NamedTextColor color) {
        // Accès à l'objet courant/parent
        this.teamColor = color;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the color of the team and sends an update packet.
     *
     * @param color The new team color
     */
    // Début d'une méthode/d'un bloc
    public void updateTeamColor(NamedTextColor color) {
        // Accès à l'objet courant/parent
        this.setTeamColor(color);
        // Appelle une méthode
        sendUpdatePacket();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the prefix of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param prefix The new prefix
     */
    // Début d'une méthode/d'un bloc
    public void setPrefix(Component prefix) {
        // Accès à l'objet courant/parent
        this.prefix = prefix;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the prefix of the team and sends an update packet.
     *
     * @param prefix The new prefix
     */
    // Début d'une méthode/d'un bloc
    public void updatePrefix(Component prefix) {
        // Accès à l'objet courant/parent
        this.setPrefix(prefix);
        // Appelle une méthode
        sendUpdatePacket();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the suffix of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param suffix The new suffix
     */
    // Début d'une méthode/d'un bloc
    public void setSuffix(Component suffix) {
        // Accès à l'objet courant/parent
        this.suffix = suffix;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the suffix of the team and sends an update packet.
     *
     * @param suffix The new suffix
     */
    // Début d'une méthode/d'un bloc
    public void updateSuffix(Component suffix) {
        // Accès à l'objet courant/parent
        this.setSuffix(suffix);
        // Appelle une méthode
        sendUpdatePacket();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the friendly flags of the team.
     * <br><br>
     * <b>Warning:</b> This is only changed on the <b>server side</b>.
     *
     * @param flag The new friendly flag
     */
    // Début d'une méthode/d'un bloc
    public void setFriendlyFlags(byte flag) {
        // Accès à l'objet courant/parent
        this.friendlyFlags = flag;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the friendly flags of the team and sends an update packet.
     *
     * @param flag The new friendly flag
     */
    // Début d'une méthode/d'un bloc
    public void updateFriendlyFlags(byte flag) {
        // Accès à l'objet courant/parent
        this.setFriendlyFlags(flag);
        // Accès à l'objet courant/parent
        this.sendUpdatePacket();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private boolean getFriendlyFlagBit(byte index) {
        // Renvoie une valeur à l'appelant
        return (this.friendlyFlags & index) == index;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void setFriendlyFlagBit(byte index, boolean value) {
        // Embranchement : vérifie une condition
        if (value) {
            // Accès à l'objet courant/parent
            this.friendlyFlags |= index;
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.friendlyFlags &= ~index;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAllowFriendlyFire(boolean value) {
        // Accès à l'objet courant/parent
        this.setFriendlyFlagBit(ALLOW_FRIENDLY_FIRE_BIT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void updateAllowFriendlyFire(boolean value) {
        // Accès à l'objet courant/parent
        this.setAllowFriendlyFire(value);
        // Accès à l'objet courant/parent
        this.sendUpdatePacket();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isAllowFriendlyFire() {
        // Renvoie une valeur à l'appelant
        return this.getFriendlyFlagBit(ALLOW_FRIENDLY_FIRE_BIT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSeeInvisiblePlayers(boolean value) {
        // Accès à l'objet courant/parent
        this.setFriendlyFlagBit(SEE_INVISIBLE_PLAYERS_BIT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void updateSeeInvisiblePlayers(boolean value) {
        // Accès à l'objet courant/parent
        this.setSeeInvisiblePlayers(value);
        // Accès à l'objet courant/parent
        this.sendUpdatePacket();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSeeInvisiblePlayers() {
        // Renvoie une valeur à l'appelant
        return this.getFriendlyFlagBit(SEE_INVISIBLE_PLAYERS_BIT);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the registry name of the team.
     *
     * @return the registry name
     */
    // Début d'une méthode/d'un bloc
    public String getTeamName() {
        // Renvoie une valeur à l'appelant
        return teamName;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates the creation packet to add a team.
     *
     * @return the packet to add the team
     */
    // Début d'une méthode/d'un bloc
    public TeamsPacket createTeamsCreationPacket() {
        // Affecte une valeur
        final var info = new TeamsPacket.CreateTeamAction(teamDisplayName, friendlyFlags,
                // Appelle une méthode
                nameTagVisibility, collisionRule, teamColor, prefix, suffix, List.copyOf(members));
        // Renvoie une valeur à l'appelant
        return new TeamsPacket(teamName, info);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an destruction packet to remove the team.
     *
     * @return the packet to remove the team
     */
    // Début d'une méthode/d'un bloc
    public TeamsPacket createTeamDestructionPacket() {
        // Renvoie une valeur à l'appelant
        return new TeamsPacket(teamName, new TeamsPacket.RemoveTeamAction());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Obtains an unmodifiable {@link Set} of registered players who are on the team.
     *
     * @return an unmodifiable {@link Set} of registered players
     */
    // Début d'une méthode/d'un bloc
    public Set<String> getMembers() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(members);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the display name of the team.
     *
     * @return the display name
     */
    // Début d'une méthode/d'un bloc
    public Component getTeamDisplayName() {
        // Renvoie une valeur à l'appelant
        return teamDisplayName;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the friendly flags of the team.
     *
     * @return the friendly flags
     */
    // Début d'une méthode/d'un bloc
    public byte getFriendlyFlags() {
        // Renvoie une valeur à l'appelant
        return friendlyFlags;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the tag visibility of the team.
     *
     * @return the tag visibility
     */
    // Début d'une méthode/d'un bloc
    public NameTagVisibility getNameTagVisibility() {
        // Renvoie une valeur à l'appelant
        return nameTagVisibility;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the collision rule of the team.
     *
     * @return the collision rule
     */
    // Début d'une méthode/d'un bloc
    public CollisionRule getCollisionRule() {
        // Renvoie une valeur à l'appelant
        return collisionRule;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the color of the team.
     *
     * @return the team color
     */
    // Début d'une méthode/d'un bloc
    public NamedTextColor getTeamColor() {
        // Renvoie une valeur à l'appelant
        return teamColor;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the prefix of the team.
     *
     * @return the team prefix
     */
    // Début d'une méthode/d'un bloc
    public Component getPrefix() {
        // Renvoie une valeur à l'appelant
        return prefix;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the suffix of the team.
     *
     * @return the suffix team
     */
    // Début d'une méthode/d'un bloc
    public Component getSuffix() {
        // Renvoie une valeur à l'appelant
        return suffix;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends an {@link TeamsPacket.UpdateTeamAction} action packet.
     */
    // Début d'une méthode/d'un bloc
    public void sendUpdatePacket() {
        // Affecte une valeur
        final var info = new TeamsPacket.UpdateTeamAction(teamDisplayName, friendlyFlags,
                // Instruction de code
                nameTagVisibility, collisionRule, teamColor, prefix, suffix);
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(new TeamsPacket(teamName, info));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<? extends Player> getPlayers() {
        // Embranchement : vérifie une condition
        if (!this.isPlayerMembersUpToDate) {
            // Accès à l'objet courant/parent
            this.playerMembers.clear();

            // Boucle : répète un bloc
            for (String member : this.members) {
                // Appelle une méthode
                Player player = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(member);

                // Embranchement : vérifie une condition
                if (player != null) {
                    // Accès à l'objet courant/parent
                    this.playerMembers.add(player);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Accès à l'objet courant/parent
            this.isPlayerMembersUpToDate = true;
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return this.playerMembers;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pointers pointers() {
        // Renvoie une valeur à l'appelant
        return TEAM_POINTERS_SUPPLIER.view(this);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
