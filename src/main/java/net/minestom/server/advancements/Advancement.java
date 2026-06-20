// Déclaration du paquet de ce fichier
package net.minestom.server.advancements;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.AdvancementsPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Represents an advancement located in an {@link AdvancementTab}.
 * <p>
 * All fields are dynamic, changing one will update the advancement in the specific {@link AdvancementTab}.
 */
// Déclaration de type (classe/interface/enum/record)
public class Advancement {

    // Instruction de code
    protected AdvancementTab tab;

    // Instruction de code
    private boolean achieved;

    // Instruction de code
    private Component title;
    // Instruction de code
    private Component description;

    // Instruction de code
    private ItemStack icon;

    // Instruction de code
    private FrameType frameType;

    // Instruction de code
    private String background; // Only on root
    // Instruction de code
    private boolean toast;
    // Instruction de code
    private boolean hidden;

    // Instruction de code
    private float x, y;

    // Instruction de code
    private String identifier;
    // Instruction de code
    private Advancement parent;

    // Packet
    // Instruction de code
    private AdvancementsPacket.Criteria criteria;
    // Instruction de code
    private final boolean sendTelemetryData;

    // Instruction de code
    public Advancement(Component title, Component description,
                       // Instruction de code
                       Material icon, FrameType frameType,
                       // Début d'une méthode/d'un bloc
                       float x, float y) {
        // Appelle une méthode
        this(title, description, ItemStack.of(icon), frameType, x, y, false);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public Advancement(Component title, Component description,
                       // Instruction de code
                       ItemStack icon, FrameType frameType,
                       // Début d'une méthode/d'un bloc
                       float x, float y) {
        // Appelle une méthode
        this(title, description, icon, frameType, x, y, false);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public Advancement(Component title, Component description,
                       // Instruction de code
                       ItemStack icon, FrameType frameType,
                       // Début d'une méthode/d'un bloc
                       float x, float y, boolean sendTelemetryData) {
        // Accès à l'objet courant/parent
        this.title = title;
        // Accès à l'objet courant/parent
        this.description = description;
        // Accès à l'objet courant/parent
        this.icon = icon;
        // Accès à l'objet courant/parent
        this.frameType = frameType;
        // Accès à l'objet courant/parent
        this.x = x;
        // Accès à l'objet courant/parent
        this.y = y;
        // Accès à l'objet courant/parent
        this.sendTelemetryData = sendTelemetryData;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the advancement is achieved.
     *
     * @return true if the advancement is achieved
     */
    // Début d'une méthode/d'un bloc
    public boolean isAchieved() {
        // Renvoie une valeur à l'appelant
        return achieved;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Makes the advancement achieved.
     *
     * @param achieved true to make it achieved
     * @return this advancement
     */
    // Début d'une méthode/d'un bloc
    public Advancement setAchieved(boolean achieved) {
        // Accès à l'objet courant/parent
        this.achieved = achieved;
        // Appelle une méthode
        update();
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the advancement tab linked to this advancement.
     *
     * @return the {@link AdvancementTab} linked to this advancement, null if not linked to anything yet
     */
    // Début d'une méthode/d'un bloc
    public @Nullable AdvancementTab getTab() {
        // Renvoie une valeur à l'appelant
        return tab;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void setTab(@Nullable AdvancementTab tab) {
        // Accès à l'objet courant/parent
        this.tab = tab;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the title of the advancement.
     *
     * @return the title
     */
    // Début d'une méthode/d'un bloc
    public Component getTitle() {
        // Renvoie une valeur à l'appelant
        return title;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the advancement title.
     *
     * @param title the new title
     */
    // Début d'une méthode/d'un bloc
    public void setTitle(Component title) {
        // Accès à l'objet courant/parent
        this.title = title;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the description of the advancement.
     *
     * @return the description title
     */
    // Début d'une méthode/d'un bloc
    public Component getDescription() {
        // Renvoie une valeur à l'appelant
        return description;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the description title.
     *
     * @param description the new description
     */
    // Début d'une méthode/d'un bloc
    public void setDescription(Component description) {
        // Accès à l'objet courant/parent
        this.description = description;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the advancement icon.
     *
     * @return the advancement icon
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getIcon() {
        // Renvoie une valeur à l'appelant
        return icon;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the advancement icon.
     *
     * @param icon the new advancement icon
     */
    // Début d'une méthode/d'un bloc
    public void setIcon(ItemStack icon) {
        // Accès à l'objet courant/parent
        this.icon = icon;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if this advancement has a toast.
     *
     * @return true if the advancement has a toast
     */
    // Début d'une méthode/d'un bloc
    public boolean hasToast() {
        // Renvoie une valeur à l'appelant
        return toast;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Makes this argument a toast.
     *
     * @param toast true to make this advancement a toast
     * @return this advancement
     */
    // Début d'une méthode/d'un bloc
    public Advancement showToast(boolean toast) {
        // Accès à l'objet courant/parent
        this.toast = toast;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHidden() {
        // Renvoie une valeur à l'appelant
        return hidden;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Advancement setHidden(boolean hidden) {
        // Accès à l'objet courant/parent
        this.hidden = hidden;
        // Appelle une méthode
        update();
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the advancement frame type.
     *
     * @return this advancement frame type
     */
    // Début d'une méthode/d'un bloc
    public FrameType getFrameType() {
        // Renvoie une valeur à l'appelant
        return frameType;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the advancement frame type.
     *
     * @param frameType the new frame type
     */
    // Début d'une méthode/d'un bloc
    public void setFrameType(FrameType frameType) {
        // Accès à l'objet courant/parent
        this.frameType = frameType;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the X position of this advancement.
     *
     * @return this advancement X
     */
    // Début d'une méthode/d'un bloc
    public float getX() {
        // Renvoie une valeur à l'appelant
        return x;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes this advancement X coordinate.
     *
     * @param x the new X coordinate
     */
    // Début d'une méthode/d'un bloc
    public void setX(float x) {
        // Accès à l'objet courant/parent
        this.x = x;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the Y position of this advancement.
     *
     * @return this advancement Y
     */
    // Début d'une méthode/d'un bloc
    public float getY() {
        // Renvoie une valeur à l'appelant
        return y;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes this advancement Y coordinate.
     *
     * @param y the new Y coordinate
     */
    // Début d'une méthode/d'un bloc
    public void setY(float y) {
        // Accès à l'objet courant/parent
        this.y = y;
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the background.
     * <p>
     * Only available for {@link AdvancementRoot}.
     *
     * @param background the new background
     */
    // Début d'une méthode/d'un bloc
    protected void setBackground(String background) {
        // Accès à l'objet courant/parent
        this.background = background;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the identifier of this advancement, used to register the advancement, use it as a parent and to retrieve it later
     * in the {@link AdvancementTab}.
     *
     * @return the advancement identifier
     */
    // Début d'une méthode/d'un bloc
    protected String getIdentifier() {
        // Renvoie une valeur à l'appelant
        return identifier;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the advancement identifier.
     * <p>
     * WARNING: unsafe, only used by {@link AdvancementTab} to initialize the advancement.
     *
     * @param identifier the new advancement identifier
     */
    // Début d'une méthode/d'un bloc
    protected void setIdentifier(String identifier) {
        // Accès à l'objet courant/parent
        this.identifier = identifier;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the advancement parent.
     *
     * @return the advancement parent, null for {@link AdvancementRoot}
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    protected Advancement getParent() {
        // Renvoie une valeur à l'appelant
        return parent;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void setParent(@Nullable Advancement parent) {
        // Accès à l'objet courant/parent
        this.parent = parent;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected AdvancementsPacket.ProgressMapping toProgressMapping() {
        // Appelle une méthode
        final var advancementProgress = new AdvancementsPacket.AdvancementProgress(List.of(criteria));
        // Renvoie une valeur à l'appelant
        return new AdvancementsPacket.ProgressMapping(identifier, advancementProgress);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected AdvancementsPacket.DisplayData toDisplayData() {
        // Renvoie une valeur à l'appelant
        return new AdvancementsPacket.DisplayData(title, description, icon,
                // Appelle une méthode
                frameType, getFlags(), background, x, y);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts this advancement to an {@link AdvancementsPacket.AdvancementMapping}.
     *
     * @return the mapping of this advancement
     */
    // Début d'une méthode/d'un bloc
    protected AdvancementsPacket.AdvancementMapping toMapping() {
        // Appelle une méthode
        final Advancement parent = getParent();
        // Appelle une méthode
        final String parentIdentifier = parent != null ? parent.getIdentifier() : null;
        // Affecte une valeur
        AdvancementsPacket.Advancement adv = new AdvancementsPacket.Advancement(parentIdentifier, toDisplayData(),
                // Instruction de code
                List.of(new AdvancementsPacket.Requirement(List.of(criteria.criterionIdentifier()))),
                // Instruction de code
                sendTelemetryData);
        // Renvoie une valeur à l'appelant
        return new AdvancementsPacket.AdvancementMapping(getIdentifier(), adv);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the packet used to add this advancement to the already existing tab.
     *
     * @return the packet to add this advancement
     */
    // Début d'une méthode/d'un bloc
    protected AdvancementsPacket getUpdatePacket() {
        // Renvoie une valeur à l'appelant
        return new AdvancementsPacket(false, List.of(toMapping()),
                // Appelle une méthode
                List.of(), List.of(toProgressMapping()), true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends update to all tab viewers if one of the advancement value changes.
     */
    // Début d'une méthode/d'un bloc
    protected void update() {
        // Appelle une méthode
        updateCriteria();
        // Embranchement : vérifie une condition
        if (tab != null) {
            // Appelle une méthode
            tab.sendPacketsToViewers(tab.removePacket, tab.createPacket());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void updateCriteria() {
        // Appelle une méthode
        final Long achievedDate = achieved ? System.currentTimeMillis() : null;
        // Appelle une méthode
        final var progress = new AdvancementsPacket.CriterionProgress(achievedDate);
        // Accès à l'objet courant/parent
        this.criteria = new AdvancementsPacket.Criteria(identifier, progress);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private int getFlags() {
        // Affecte une valeur
        byte result = 0;
        // Embranchement : vérifie une condition
        if (background != null) result |= 0x1;
        // Embranchement : vérifie une condition
        if (hasToast()) result |= 0x2;
        // Embranchement : vérifie une condition
        if (isHidden()) result |= 0x4;
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
