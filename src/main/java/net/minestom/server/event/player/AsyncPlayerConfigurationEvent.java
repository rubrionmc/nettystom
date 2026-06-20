// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
// Import of a required class
import it.unimi.dsi.fastutil.objects.ObjectSets;
// Import of a required class
import net.minestom.server.FeatureFlag;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.AsyncEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.ResetChatPacket;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.UpdateEnabledFeaturesPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
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
// Type declaration (class/interface/enum/record)
public class AsyncPlayerConfigurationEvent implements PlayerEvent, AsyncEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final boolean isFirstConfig;

    // Calls a method
    private final ObjectArraySet<FeatureFlag> featureFlags = new ObjectArraySet<>();
    // Code statement
    private boolean hardcore;
    // Code statement
    private boolean clearChat;
    // Code statement
    private boolean sendRegistryData;
    // Code statement
    private Instance spawningInstance;

    // Start of a method/block
    public AsyncPlayerConfigurationEvent(Player player, boolean isFirstConfig) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.isFirstConfig = isFirstConfig;

        // Access to the current/parent object
        this.featureFlags.add(FeatureFlag.VANILLA); // Vanilla feature-set, without this you get nothing at all. Kinda wacky!

        // Access to the current/parent object
        this.hardcore = false;
        // Access to the current/parent object
        this.clearChat = false;
        // Access to the current/parent object
        this.sendRegistryData = isFirstConfig;
        // Access to the current/parent object
        this.spawningInstance = null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return this.player;
    // End of a block/expression
    }

    /**
     * Returns true if this is the first time the player is in the configuration phase (they are joining), false otherwise.
     */
    // Start of a method/block
    public boolean isFirstConfig() {
        // Returns a value to the caller
        return isFirstConfig;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHardcore() {
        // Returns a value to the caller
        return this.hardcore;
    // End of a block/expression
    }

    // Start of a method/block
    public void setHardcore(boolean hardcore) {
        // Access to the current/parent object
        this.hardcore = hardcore;
    // End of a block/expression
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
    // Start of a method/block
    public void addFeatureFlag(FeatureFlag feature) {
        // Access to the current/parent object
        this.featureFlags.add(feature);
    // End of a block/expression
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
    // Start of a method/block
    public boolean removeFeatureFlag(FeatureFlag feature) {
        // Returns a value to the caller
        return this.featureFlags.remove(feature); // Should this have sanity checking to see if the feature was actually contained in the list?
    // End of a block/expression
    }

    /**
     * The list of currently added feature flags. This is an unmodifiable copy of what will be sent to the client.
     *
     * @return An unmodifiable set of feature flags
     *
     * @see UpdateEnabledFeaturesPacket
     * @see net.minestom.server.FeatureFlag
     */
    // Start of a method/block
    public Set<FeatureFlag> getFeatureFlags() {
        // Returns a value to the caller
        return ObjectSets.unmodifiable(this.featureFlags);
    // End of a block/expression
    }

    /**
     * If true, the player's chat will be cleared when exiting the configuration state, otherwise
     * it will be preserved. The default is not to clear the chat.
     *
     * @return true if the chat will be cleared, false otherwise
     *
     * @see ResetChatPacket
     */
    // Start of a method/block
    public boolean willClearChat() {
        // Returns a value to the caller
        return clearChat;
    // End of a block/expression
    }

    /**
     * Set whether the player's chat will be cleared when exiting the configuration state.
     *
     * @param clearChat true to clear the chat, false otherwise
     *
     * @see ResetChatPacket
     */
    // Start of a method/block
    public void setClearChat(boolean clearChat) {
        // Access to the current/parent object
        this.clearChat = clearChat;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean willSendRegistryData() {
        // Returns a value to the caller
        return sendRegistryData;
    // End of a block/expression
    }

    // Start of a method/block
    public void setSendRegistryData(boolean sendRegistryData) {
        // Access to the current/parent object
        this.sendRegistryData = sendRegistryData;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Instance getSpawningInstance() {
        // Returns a value to the caller
        return spawningInstance;
    // End of a block/expression
    }

    // Start of a method/block
    public void setSpawningInstance(@Nullable Instance spawningInstance) {
        // Access to the current/parent object
        this.spawningInstance = spawningInstance;
    // End of a block/expression
    }
// End of a block/expression
}
