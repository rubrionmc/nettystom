// Package declaration for this file
package net.minestom.server.advancements;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.packet.server.play.AdvancementsPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

/**
 * Represents an advancement located in an {@link AdvancementTab}.
 * <p>
 * All fields are dynamic, changing one will update the advancement in the specific {@link AdvancementTab}.
 */
// Type declaration (class/interface/enum/record)
public class Advancement {

    // Code statement
    protected AdvancementTab tab;

    // Code statement
    private boolean achieved;

    // Code statement
    private Component title;
    // Code statement
    private Component description;

    // Code statement
    private ItemStack icon;

    // Code statement
    private FrameType frameType;

    // Code statement
    private String background; // Only on root
    // Code statement
    private boolean toast;
    // Code statement
    private boolean hidden;

    // Code statement
    private float x, y;

    // Code statement
    private String identifier;
    // Code statement
    private Advancement parent;

    // Packet
    // Code statement
    private AdvancementsPacket.Criteria criteria;
    // Code statement
    private final boolean sendTelemetryData;

    // Code statement
    public Advancement(Component title, Component description,
                       // Code statement
                       Material icon, FrameType frameType,
                       // Start of a method/block
                       float x, float y) {
        // Calls a method
        this(title, description, ItemStack.of(icon), frameType, x, y, false);
    // End of a block/expression
    }

    // Code statement
    public Advancement(Component title, Component description,
                       // Code statement
                       ItemStack icon, FrameType frameType,
                       // Start of a method/block
                       float x, float y) {
        // Calls a method
        this(title, description, icon, frameType, x, y, false);
    // End of a block/expression
    }

    // Code statement
    public Advancement(Component title, Component description,
                       // Code statement
                       ItemStack icon, FrameType frameType,
                       // Start of a method/block
                       float x, float y, boolean sendTelemetryData) {
        // Access to the current/parent object
        this.title = title;
        // Access to the current/parent object
        this.description = description;
        // Access to the current/parent object
        this.icon = icon;
        // Access to the current/parent object
        this.frameType = frameType;
        // Access to the current/parent object
        this.x = x;
        // Access to the current/parent object
        this.y = y;
        // Access to the current/parent object
        this.sendTelemetryData = sendTelemetryData;
    // End of a block/expression
    }

    /**
     * Gets if the advancement is achieved.
     *
     * @return true if the advancement is achieved
     */
    // Start of a method/block
    public boolean isAchieved() {
        // Returns a value to the caller
        return achieved;
    // End of a block/expression
    }

    /**
     * Makes the advancement achieved.
     *
     * @param achieved true to make it achieved
     * @return this advancement
     */
    // Start of a method/block
    public Advancement setAchieved(boolean achieved) {
        // Access to the current/parent object
        this.achieved = achieved;
        // Calls a method
        update();
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Gets the advancement tab linked to this advancement.
     *
     * @return the {@link AdvancementTab} linked to this advancement, null if not linked to anything yet
     */
    // Start of a method/block
    public @Nullable AdvancementTab getTab() {
        // Returns a value to the caller
        return tab;
    // End of a block/expression
    }

    // Start of a method/block
    protected void setTab(@Nullable AdvancementTab tab) {
        // Access to the current/parent object
        this.tab = tab;
    // End of a block/expression
    }

    /**
     * Gets the title of the advancement.
     *
     * @return the title
     */
    // Start of a method/block
    public Component getTitle() {
        // Returns a value to the caller
        return title;
    // End of a block/expression
    }

    /**
     * Changes the advancement title.
     *
     * @param title the new title
     */
    // Start of a method/block
    public void setTitle(Component title) {
        // Access to the current/parent object
        this.title = title;
        // Calls a method
        update();
    // End of a block/expression
    }

    /**
     * Gets the description of the advancement.
     *
     * @return the description title
     */
    // Start of a method/block
    public Component getDescription() {
        // Returns a value to the caller
        return description;
    // End of a block/expression
    }

    /**
     * Changes the description title.
     *
     * @param description the new description
     */
    // Start of a method/block
    public void setDescription(Component description) {
        // Access to the current/parent object
        this.description = description;
        // Calls a method
        update();
    // End of a block/expression
    }

    /**
     * Gets the advancement icon.
     *
     * @return the advancement icon
     */
    // Start of a method/block
    public ItemStack getIcon() {
        // Returns a value to the caller
        return icon;
    // End of a block/expression
    }

    /**
     * Changes the advancement icon.
     *
     * @param icon the new advancement icon
     */
    // Start of a method/block
    public void setIcon(ItemStack icon) {
        // Access to the current/parent object
        this.icon = icon;
        // Calls a method
        update();
    // End of a block/expression
    }

    /**
     * Gets if this advancement has a toast.
     *
     * @return true if the advancement has a toast
     */
    // Start of a method/block
    public boolean hasToast() {
        // Returns a value to the caller
        return toast;
    // End of a block/expression
    }

    /**
     * Makes this argument a toast.
     *
     * @param toast true to make this advancement a toast
     * @return this advancement
     */
    // Start of a method/block
    public Advancement showToast(boolean toast) {
        // Access to the current/parent object
        this.toast = toast;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHidden() {
        // Returns a value to the caller
        return hidden;
    // End of a block/expression
    }

    // Start of a method/block
    public Advancement setHidden(boolean hidden) {
        // Access to the current/parent object
        this.hidden = hidden;
        // Calls a method
        update();
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Gets the advancement frame type.
     *
     * @return this advancement frame type
     */
    // Start of a method/block
    public FrameType getFrameType() {
        // Returns a value to the caller
        return frameType;
    // End of a block/expression
    }

    /**
     * Changes the advancement frame type.
     *
     * @param frameType the new frame type
     */
    // Start of a method/block
    public void setFrameType(FrameType frameType) {
        // Access to the current/parent object
        this.frameType = frameType;
        // Calls a method
        update();
    // End of a block/expression
    }

    /**
     * Gets the X position of this advancement.
     *
     * @return this advancement X
     */
    // Start of a method/block
    public float getX() {
        // Returns a value to the caller
        return x;
    // End of a block/expression
    }

    /**
     * Changes this advancement X coordinate.
     *
     * @param x the new X coordinate
     */
    // Start of a method/block
    public void setX(float x) {
        // Access to the current/parent object
        this.x = x;
        // Calls a method
        update();
    // End of a block/expression
    }

    /**
     * Gets the Y position of this advancement.
     *
     * @return this advancement Y
     */
    // Start of a method/block
    public float getY() {
        // Returns a value to the caller
        return y;
    // End of a block/expression
    }

    /**
     * Changes this advancement Y coordinate.
     *
     * @param y the new Y coordinate
     */
    // Start of a method/block
    public void setY(float y) {
        // Access to the current/parent object
        this.y = y;
        // Calls a method
        update();
    // End of a block/expression
    }

    /**
     * Sets the background.
     * <p>
     * Only available for {@link AdvancementRoot}.
     *
     * @param background the new background
     */
    // Start of a method/block
    protected void setBackground(String background) {
        // Access to the current/parent object
        this.background = background;
    // End of a block/expression
    }

    /**
     * Gets the identifier of this advancement, used to register the advancement, use it as a parent and to retrieve it later
     * in the {@link AdvancementTab}.
     *
     * @return the advancement identifier
     */
    // Start of a method/block
    protected String getIdentifier() {
        // Returns a value to the caller
        return identifier;
    // End of a block/expression
    }

    /**
     * Changes the advancement identifier.
     * <p>
     * WARNING: unsafe, only used by {@link AdvancementTab} to initialize the advancement.
     *
     * @param identifier the new advancement identifier
     */
    // Start of a method/block
    protected void setIdentifier(String identifier) {
        // Access to the current/parent object
        this.identifier = identifier;
    // End of a block/expression
    }

    /**
     * Gets the advancement parent.
     *
     * @return the advancement parent, null for {@link AdvancementRoot}
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    protected Advancement getParent() {
        // Returns a value to the caller
        return parent;
    // End of a block/expression
    }

    // Start of a method/block
    protected void setParent(@Nullable Advancement parent) {
        // Access to the current/parent object
        this.parent = parent;
    // End of a block/expression
    }

    // Start of a method/block
    protected AdvancementsPacket.ProgressMapping toProgressMapping() {
        // Calls a method
        final var advancementProgress = new AdvancementsPacket.AdvancementProgress(List.of(criteria));
        // Returns a value to the caller
        return new AdvancementsPacket.ProgressMapping(identifier, advancementProgress);
    // End of a block/expression
    }

    // Start of a method/block
    protected AdvancementsPacket.DisplayData toDisplayData() {
        // Returns a value to the caller
        return new AdvancementsPacket.DisplayData(title, description, icon,
                // Calls a method
                frameType, getFlags(), background, x, y);
    // End of a block/expression
    }

    /**
     * Converts this advancement to an {@link AdvancementsPacket.AdvancementMapping}.
     *
     * @return the mapping of this advancement
     */
    // Start of a method/block
    protected AdvancementsPacket.AdvancementMapping toMapping() {
        // Calls a method
        final Advancement parent = getParent();
        // Calls a method
        final String parentIdentifier = parent != null ? parent.getIdentifier() : null;
        // Assigns a value
        AdvancementsPacket.Advancement adv = new AdvancementsPacket.Advancement(parentIdentifier, toDisplayData(),
                // Code statement
                List.of(new AdvancementsPacket.Requirement(List.of(criteria.criterionIdentifier()))),
                // Code statement
                sendTelemetryData);
        // Returns a value to the caller
        return new AdvancementsPacket.AdvancementMapping(getIdentifier(), adv);
    // End of a block/expression
    }

    /**
     * Gets the packet used to add this advancement to the already existing tab.
     *
     * @return the packet to add this advancement
     */
    // Start of a method/block
    protected AdvancementsPacket getUpdatePacket() {
        // Returns a value to the caller
        return new AdvancementsPacket(false, List.of(toMapping()),
                // Calls a method
                List.of(), List.of(toProgressMapping()), true);
    // End of a block/expression
    }

    /**
     * Sends update to all tab viewers if one of the advancement value changes.
     */
    // Start of a method/block
    protected void update() {
        // Calls a method
        updateCriteria();
        // Branch: checks a condition
        if (tab != null) {
            // Calls a method
            tab.sendPacketsToViewers(tab.removePacket, tab.createPacket());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    protected void updateCriteria() {
        // Calls a method
        final Long achievedDate = achieved ? System.currentTimeMillis() : null;
        // Calls a method
        final var progress = new AdvancementsPacket.CriterionProgress(achievedDate);
        // Access to the current/parent object
        this.criteria = new AdvancementsPacket.Criteria(identifier, progress);
    // End of a block/expression
    }

    // Start of a method/block
    private int getFlags() {
        // Assigns a value
        byte result = 0;
        // Branch: checks a condition
        if (background != null) result |= 0x1;
        // Branch: checks a condition
        if (hasToast()) result |= 0x2;
        // Branch: checks a condition
        if (isHidden()) result |= 0x4;
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
