// Package declaration for this file
package net.minestom.server.entity.metadata.minecart;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class CommandBlockMinecartMeta extends AbstractMinecartMeta {
    // Start of a method/block
    public CommandBlockMinecartMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public String getCommand() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.MinecartCommandBlock.COMMAND);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCommand(String value) {
        // Calls a method
        metadata.set(MetadataDef.MinecartCommandBlock.COMMAND, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Component getLastOutput() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.MinecartCommandBlock.LAST_OUTPUT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLastOutput(Component value) {
        // Calls a method
        metadata.set(MetadataDef.MinecartCommandBlock.LAST_OUTPUT, value);
    // End of a block/expression
    }
// End of a block/expression
}
