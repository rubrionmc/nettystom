// Package declaration for this file
package net.minestom.server.advancements;

// Import of a required class
import net.minestom.server.Viewable;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.play.AdvancementsPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Represents a tab which can be shared between multiple players. Created using {@link AdvancementManager#createTab(String, AdvancementRoot)}.
 * <p>
 * Each tab requires a root advancement and all succeeding advancements need to have a parent in the tab.
 * You can create a new advancement using {@link #createAdvancement(String, Advancement, Advancement)}.
 * <p>
 * Be sure to use {@link #addViewer(Player)} and {@link #removeViewer(Player)} to control which players can see the tab.
 * (all viewers will see the same tab, with the same amount of validated advancements etc... so shared).
 */
// Type declaration (class/interface/enum/record)
public class AdvancementTab implements Viewable {

    // Calls a method
    private static final Map<UUID, Set<AdvancementTab>> PLAYER_TAB_MAP = new HashMap<>();

    // Calls a method
    private final Set<Player> viewers = new HashSet<>();

    // Code statement
    private final AdvancementRoot root;

    // Advancement -> its parent
    // Calls a method
    private final Map<Advancement, Advancement> advancementMap = new HashMap<>();

    // the packet used to clear the tab (used to remove it and to update an advancement)
    // will never change (since the root identifier is always the same)
    // Code statement
    protected final AdvancementsPacket removePacket;

    // Start of a method/block
    protected AdvancementTab(String rootIdentifier, AdvancementRoot root) {
        // Access to the current/parent object
        this.root = root;
        // Calls a method
        cacheAdvancement(rootIdentifier, root, null);
        // Access to the current/parent object
        this.removePacket = new AdvancementsPacket(false, List.of(), List.of(rootIdentifier), List.of(), true);
    // End of a block/expression
    }

    /**
     * Gets all the tabs of a viewer.
     *
     * @param player the player to get the tabs from
     * @return all the advancement tabs that the player sees, can be null
     * if the player doesn't see anything
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public static Set<AdvancementTab> getTabs(Player player) {
        // Returns a value to the caller
        return PLAYER_TAB_MAP.getOrDefault(player.getUuid(), null);
    // End of a block/expression
    }

    /**
     * Gets the root advancement of this tab.
     *
     * @return the root advancement
     */
    // Start of a method/block
    public AdvancementRoot getRoot() {
        // Returns a value to the caller
        return root;
    // End of a block/expression
    }

    /**
     * Creates and add an advancement into this tab.
     *
     * @param identifier  the unique identifier
     * @param advancement the advancement to add
     * @param parent      the parent of this advancement, it cannot be null
     */
    // Start of a method/block
    public void createAdvancement(String identifier, Advancement advancement, Advancement parent) {
        // Code statement
        Check.stateCondition(!advancementMap.containsKey(parent),
                // Code statement
                "You tried to set a parent which doesn't exist or isn't registered");
        // Calls a method
        cacheAdvancement(identifier, advancement, parent);
        // Branch: checks a condition
        if (!getViewers().isEmpty()) {
            // Calls a method
            sendPacketToViewers(advancement.getUpdatePacket());
        // End of a block/expression
        }

    // End of a block/expression
    }

    /**
     * Builds the packet which build the whole advancement tab.
     *
     * @return the packet adding this advancement tab and all its advancements
     */
    // Start of a method/block
    protected AdvancementsPacket createPacket() {
        // Calls a method
        List<AdvancementsPacket.AdvancementMapping> mappings = new ArrayList<>();
        // Calls a method
        List<AdvancementsPacket.ProgressMapping> progressMappings = new ArrayList<>();
        // Loop: repeats a block
        for (Advancement advancement : advancementMap.keySet()) {
            // Calls a method
            mappings.add(advancement.toMapping());
            // Calls a method
            progressMappings.add(advancement.toProgressMapping());
        // End of a block/expression
        }
        // Returns a value to the caller
        return new AdvancementsPacket(false, mappings, List.of(), progressMappings, true);
    // End of a block/expression
    }

    /**
     * Caches an advancement.
     *
     * @param identifier  the identifier of the advancement
     * @param advancement the advancement
     * @param parent      the parent of this advancement, only null for the root advancement
     */
    // Start of a method/block
    private void cacheAdvancement(String identifier, Advancement advancement, @Nullable Advancement parent) {
        // Code statement
        Check.stateCondition(advancement.getTab() != null,
                // Code statement
                "You tried to add an advancement already linked to a tab");
        // Calls a method
        advancement.setTab(this);
        // Calls a method
        advancement.setIdentifier(identifier);
        // Calls a method
        advancement.setParent(parent);
        // Calls a method
        advancement.updateCriteria();
        // Access to the current/parent object
        this.advancementMap.put(advancement, parent);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized boolean addViewer(Player player) {
        // Calls a method
        final boolean result = viewers.add(player);
        // Branch: checks a condition
        if (!result) return false;
        // Send the tab to the player
        // Calls a method
        player.sendPacket(createPacket());
        // Calls a method
        addPlayer(player);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized boolean removeViewer(Player player) {
        // Branch: checks a condition
        if (!isViewer(player)) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Remove the tab
        // Branch: checks a condition
        if (!player.isRemoved()) player.sendPacket(removePacket);
        // Calls a method
        removePlayer(player);
        // Returns a value to the caller
        return viewers.remove(player);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Set<? extends Player> getViewers() {
        // Returns a value to the caller
        return viewers;
    // End of a block/expression
    }

    /**
     * Adds the tab to the player set.
     *
     * @param player the player
     */
    // Start of a method/block
    private void addPlayer(Player player) {
        // Calls a method
        Set<AdvancementTab> tabs = PLAYER_TAB_MAP.computeIfAbsent(player.getUuid(), p -> new CopyOnWriteArraySet<>());
        // Calls a method
        tabs.add(this);
    // End of a block/expression
    }

    /**
     * Removes the tab from the player set.
     *
     * @param player the player
     */
    // Start of a method/block
    private void removePlayer(Player player) {
        // Calls a method
        final UUID uuid = player.getUuid();
        // Branch: checks a condition
        if (!PLAYER_TAB_MAP.containsKey(uuid)) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        Set<AdvancementTab> tabs = PLAYER_TAB_MAP.get(uuid);
        // Calls a method
        tabs.remove(this);
        // Branch: checks a condition
        if (tabs.isEmpty()) {
            // Calls a method
            PLAYER_TAB_MAP.remove(uuid);
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
