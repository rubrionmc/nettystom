// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.ObjectSets;
// Import d'une classe nécessaire
import net.minestom.server.FeatureFlag;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.AsyncEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.ResetChatPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.UpdateEnabledFeaturesPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Set;

/**
 * Called when a player enters the configuration state (either on first connection, or if they are
 * sent back to configuration later). The player is moved to the play state as soon as all event
 * handles finish processing this event.
 *
 * <p>The spawning instance <b>must</b> be set for the player to join.</p>
 *
 * <p>The event is called off the tick threads, so it is safe to block here</p>
 *
 * <p>It is valid to kick a player using {@link Player#kick(net.kyori.adventure.text.Component)} during this event.</p>
 */
// Déclaration de type (classe/interface/enum/record)
public class AsyncPlayerConfigurationEvent implements PlayerEvent, AsyncEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final boolean isFirstConfig;

    // Affecte une valeur
    private final ObjectArraySet<FeatureFlag> featureFlags = new ObjectArraySet<>();
    // Instruction de code
    private boolean hardcore;
    // Instruction de code
    private boolean clearChat;
    // Instruction de code
    private boolean sendRegistryData;
    // Instruction de code
    private Instance spawningInstance;

    // Début d'une méthode/d'un bloc
    public AsyncPlayerConfigurationEvent(Player player, boolean isFirstConfig) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.isFirstConfig = isFirstConfig;

        // Accès à l'objet courant/parent
        this.featureFlags.add(FeatureFlag.VANILLA); // Vanilla feature-set, without this you get nothing at all. Kinda wacky!

        // Accès à l'objet courant/parent
        this.hardcore = false;
        // Accès à l'objet courant/parent
        this.clearChat = false;
        // Accès à l'objet courant/parent
        this.sendRegistryData = isFirstConfig;
        // Accès à l'objet courant/parent
        this.spawningInstance = null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return this.player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns true if this is the first time the player is in the configuration phase (they are joining), false otherwise.
     */
    // Début d'une méthode/d'un bloc
    public boolean isFirstConfig() {
        // Renvoie une valeur à l'appelant
        return isFirstConfig;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHardcore() {
        // Renvoie une valeur à l'appelant
        return this.hardcore;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHardcore(boolean hardcore) {
        // Accès à l'objet courant/parent
        this.hardcore = hardcore;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Add a feature flag, see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Feature_Flags">Minecraft Wiki's Feature Flags</a> for a list of applicable features
     * Note: the flag "minecraft:vanilla" is already included by default.
     *
     * @param feature A minecraft feature flag
     *
     * @see UpdateEnabledFeaturesPacket
     * @see net.minestom.server.FeatureFlag
     */
    // Début d'une méthode/d'un bloc
    public void addFeatureFlag(FeatureFlag feature) {
        // Accès à l'objet courant/parent
        this.featureFlags.add(feature);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Remove a feature flag, see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Feature_Flags">Minecraft Wiki's Feature Flags</a> for a list of applicable features
     * Note: removing the flag "minecraft:vanilla" may result in weird behavior
     *
     * @param feature A minecraft feature flag
     * @return if the feature specified existed prior to being removed
     *
     * @see UpdateEnabledFeaturesPacket
     * @see net.minestom.server.FeatureFlag
     */
    // Début d'une méthode/d'un bloc
    public boolean removeFeatureFlag(FeatureFlag feature) {
        // Renvoie une valeur à l'appelant
        return this.featureFlags.remove(feature); // Should this have sanity checking to see if the feature was actually contained in the list?
    // Fin d'un bloc/d'une expression
    }

    /**
     * The list of currently added feature flags. This is an unmodifiable copy of what will be sent to the client.
     *
     * @return An unmodifiable set of feature flags
     *
     * @see UpdateEnabledFeaturesPacket
     * @see net.minestom.server.FeatureFlag
     */
    // Début d'une méthode/d'un bloc
    public Set<FeatureFlag> getFeatureFlags() {
        // Renvoie une valeur à l'appelant
        return ObjectSets.unmodifiable(this.featureFlags);
    // Fin d'un bloc/d'une expression
    }

    /**
     * If true, the player's chat will be cleared when exiting the configuration state, otherwise
     * it will be preserved. The default is not to clear the chat.
     *
     * @return true if the chat will be cleared, false otherwise
     *
     * @see ResetChatPacket
     */
    // Début d'une méthode/d'un bloc
    public boolean willClearChat() {
        // Renvoie une valeur à l'appelant
        return clearChat;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Set whether the player's chat will be cleared when exiting the configuration state.
     *
     * @param clearChat true to clear the chat, false otherwise
     *
     * @see ResetChatPacket
     */
    // Début d'une méthode/d'un bloc
    public void setClearChat(boolean clearChat) {
        // Accès à l'objet courant/parent
        this.clearChat = clearChat;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean willSendRegistryData() {
        // Renvoie une valeur à l'appelant
        return sendRegistryData;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSendRegistryData(boolean sendRegistryData) {
        // Accès à l'objet courant/parent
        this.sendRegistryData = sendRegistryData;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Instance getSpawningInstance() {
        // Renvoie une valeur à l'appelant
        return spawningInstance;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSpawningInstance(@Nullable Instance spawningInstance) {
        // Accès à l'objet courant/parent
        this.spawningInstance = spawningInstance;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
