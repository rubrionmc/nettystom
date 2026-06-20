// Package declaration for this file
package net.minestom.server.event.item;

// Import of a required class
import net.minestom.server.entity.ExperienceOrb;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

// Type declaration (class/interface/enum/record)
public class PickupExperienceEvent implements CancellableEvent, PlayerInstanceEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final ExperienceOrb experienceOrb;
    // Code statement
    private short experienceCount;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PickupExperienceEvent(Player player, ExperienceOrb experienceOrb) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.experienceOrb = experienceOrb;
        // Access to the current/parent object
        this.experienceCount = experienceOrb.getExperienceCount();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    // Start of a method/block
    public ExperienceOrb getExperienceOrb() {
        // Returns a value to the caller
        return experienceOrb;
    // End of a block/expression
    }

    // Start of a method/block
    public short getExperienceCount() {
        // Returns a value to the caller
        return experienceCount;
    // End of a block/expression
    }

    // Start of a method/block
    public void setExperienceCount(short experienceCount) {
        // Access to the current/parent object
        this.experienceCount = experienceCount;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCancelled() {
        // Returns a value to the caller
        return cancelled;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setCancelled(boolean cancel) {
        // Access to the current/parent object
        this.cancelled = cancel;
    // End of a block/expression
    }
// End of a block/expression
}
