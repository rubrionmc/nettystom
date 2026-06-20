// Déclaration du paquet de ce fichier
package net.minestom.server.advancements;

// Import d'une classe nécessaire
import net.minestom.server.Viewable;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.AdvancementsPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public class AdvancementTab implements Viewable {

    // Appelle une méthode
    private static final Map<UUID, Set<AdvancementTab>> PLAYER_TAB_MAP = new HashMap<>();

    // Appelle une méthode
    private final Set<Player> viewers = new HashSet<>();

    // Instruction de code
    private final AdvancementRoot root;

    // Advancement -> its parent
    // Appelle une méthode
    private final Map<Advancement, Advancement> advancementMap = new HashMap<>();

    // the packet used to clear the tab (used to remove it and to update an advancement)
    // will never change (since the root identifier is always the same)
    // Instruction de code
    protected final AdvancementsPacket removePacket;

    // Début d'une méthode/d'un bloc
    protected AdvancementTab(String rootIdentifier, AdvancementRoot root) {
        // Accès à l'objet courant/parent
        this.root = root;
        // Appelle une méthode
        cacheAdvancement(rootIdentifier, root, null);
        // Accès à l'objet courant/parent
        this.removePacket = new AdvancementsPacket(false, List.of(), List.of(rootIdentifier), List.of(), true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the tabs of a viewer.
     *
     * @param player the player to get the tabs from
     * @return all the advancement tabs that the player sees, can be null
     * if the player doesn't see anything
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public static Set<AdvancementTab> getTabs(Player player) {
        // Renvoie une valeur à l'appelant
        return PLAYER_TAB_MAP.getOrDefault(player.getUuid(), null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the root advancement of this tab.
     *
     * @return the root advancement
     */
    // Début d'une méthode/d'un bloc
    public AdvancementRoot getRoot() {
        // Renvoie une valeur à l'appelant
        return root;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates and add an advancement into this tab.
     *
     * @param identifier  the unique identifier
     * @param advancement the advancement to add
     * @param parent      the parent of this advancement, it cannot be null
     */
    // Début d'une méthode/d'un bloc
    public void createAdvancement(String identifier, Advancement advancement, Advancement parent) {
        // Instruction de code
        Check.stateCondition(!advancementMap.containsKey(parent),
                // Instruction de code
                "You tried to set a parent which doesn't exist or isn't registered");
        // Appelle une méthode
        cacheAdvancement(identifier, advancement, parent);
        // Embranchement : vérifie une condition
        if (!getViewers().isEmpty()) {
            // Appelle une méthode
            sendPacketToViewers(advancement.getUpdatePacket());
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

    /**
     * Builds the packet which build the whole advancement tab.
     *
     * @return the packet adding this advancement tab and all its advancements
     */
    // Début d'une méthode/d'un bloc
    protected AdvancementsPacket createPacket() {
        // Appelle une méthode
        List<AdvancementsPacket.AdvancementMapping> mappings = new ArrayList<>();
        // Appelle une méthode
        List<AdvancementsPacket.ProgressMapping> progressMappings = new ArrayList<>();
        // Boucle : répète un bloc
        for (Advancement advancement : advancementMap.keySet()) {
            // Appelle une méthode
            mappings.add(advancement.toMapping());
            // Appelle une méthode
            progressMappings.add(advancement.toProgressMapping());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new AdvancementsPacket(false, mappings, List.of(), progressMappings, true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Caches an advancement.
     *
     * @param identifier  the identifier of the advancement
     * @param advancement the advancement
     * @param parent      the parent of this advancement, only null for the root advancement
     */
    // Début d'une méthode/d'un bloc
    private void cacheAdvancement(String identifier, Advancement advancement, @Nullable Advancement parent) {
        // Instruction de code
        Check.stateCondition(advancement.getTab() != null,
                // Instruction de code
                "You tried to add an advancement already linked to a tab");
        // Appelle une méthode
        advancement.setTab(this);
        // Appelle une méthode
        advancement.setIdentifier(identifier);
        // Appelle une méthode
        advancement.setParent(parent);
        // Appelle une méthode
        advancement.updateCriteria();
        // Accès à l'objet courant/parent
        this.advancementMap.put(advancement, parent);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized boolean addViewer(Player player) {
        // Appelle une méthode
        final boolean result = viewers.add(player);
        // Embranchement : vérifie une condition
        if (!result) return false;
        // Send the tab to the player
        // Appelle une méthode
        player.sendPacket(createPacket());
        // Appelle une méthode
        addPlayer(player);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized boolean removeViewer(Player player) {
        // Embranchement : vérifie une condition
        if (!isViewer(player)) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Remove the tab
        // Embranchement : vérifie une condition
        if (!player.isRemoved()) player.sendPacket(removePacket);
        // Appelle une méthode
        removePlayer(player);
        // Renvoie une valeur à l'appelant
        return viewers.remove(player);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Set<? extends Player> getViewers() {
        // Renvoie une valeur à l'appelant
        return viewers;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds the tab to the player set.
     *
     * @param player the player
     */
    // Début d'une méthode/d'un bloc
    private void addPlayer(Player player) {
        // Appelle une méthode
        Set<AdvancementTab> tabs = PLAYER_TAB_MAP.computeIfAbsent(player.getUuid(), p -> new CopyOnWriteArraySet<>());
        // Appelle une méthode
        tabs.add(this);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes the tab from the player set.
     *
     * @param player the player
     */
    // Début d'une méthode/d'un bloc
    private void removePlayer(Player player) {
        // Appelle une méthode
        final UUID uuid = player.getUuid();
        // Embranchement : vérifie une condition
        if (!PLAYER_TAB_MAP.containsKey(uuid)) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Set<AdvancementTab> tabs = PLAYER_TAB_MAP.get(uuid);
        // Appelle une méthode
        tabs.remove(this);
        // Embranchement : vérifie une condition
        if (tabs.isEmpty()) {
            // Appelle une méthode
            PLAYER_TAB_MAP.remove(uuid);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
