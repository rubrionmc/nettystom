// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.metadata.other.PrimedTntMeta;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Type declaration (class/interface/enum/record)
public class PrimedTNTCommand extends Command {
    // Start of a method/block
    public PrimedTNTCommand() {
        // Access to the current/parent object
        super("primedtnt");

        // Start of a method/block
        setDefaultExecutor((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;

            // Calls a method
            Entity entity = new Entity(EntityType.TNT);
            // Start of a method/block
            entity.editEntityMeta(PrimedTntMeta.class, meta -> {
                // Calls a method
                meta.setFuseTime(60);
                // Calls a method
                meta.setBlockState(Block.STONE);
            // End of a block/expression
            });

            // Calls a method
            entity.setInstance(player.getInstance(), player.getPosition());
        // End of a block/expression
        });

    // End of a block/expression
    }
// End of a block/expression
}
