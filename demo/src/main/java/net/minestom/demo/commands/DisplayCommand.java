// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.metadata.display.AbstractDisplayMeta;
// Import of a required class
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
// Import of a required class
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
// Import of a required class
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;

// Type declaration (class/interface/enum/record)
public class DisplayCommand extends Command {

    // Start of a method/block
    public DisplayCommand() {
        // Access to the current/parent object
        super("display");

        // Calls a method
        var follow = ArgumentType.Literal("follow");

        // Calls a method
        addSyntax(this::spawnItem, ArgumentType.Literal("item"));
        // Calls a method
        addSyntax(this::spawnBlock, ArgumentType.Literal("block"));
        // Calls a method
        addSyntax(this::spawnText, ArgumentType.Literal("text"));

        // Calls a method
        addSyntax(this::spawnItem, ArgumentType.Literal("item"), follow);
        // Calls a method
        addSyntax(this::spawnBlock, ArgumentType.Literal("block"), follow);
        // Calls a method
        addSyntax(this::spawnText, ArgumentType.Literal("text"), follow);
    // End of a block/expression
    }

    // Start of a method/block
    public void spawnItem(CommandSender sender, CommandContext context) {
        // Branch: checks a condition
        if (!(sender instanceof Player player))
            // Returns a value to the caller
            return;

        // Calls a method
        var entity = new Entity(EntityType.ITEM_DISPLAY);
        // Calls a method
        var meta = (ItemDisplayMeta) entity.getEntityMeta();
        // Calls a method
        meta.setTransformationInterpolationDuration(20);
        // Calls a method
        meta.setItemStack(ItemStack.of(Material.STICK));
        // Calls a method
        entity.setInstance(player.getInstance(), player.getPosition());

        // Branch: checks a condition
        if (context.has("follow")) {
            // Calls a method
            startSmoothFollow(entity, player);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void spawnBlock(CommandSender sender, CommandContext context) {
        // Branch: checks a condition
        if (!(sender instanceof Player player))
            // Returns a value to the caller
            return;

        // Calls a method
        var entity = new Entity(EntityType.BLOCK_DISPLAY);
        // Calls a method
        var meta = (BlockDisplayMeta) entity.getEntityMeta();
        // Calls a method
        meta.setTransformationInterpolationDuration(20);
        // Calls a method
        meta.setBlockState(Block.ORANGE_CANDLE_CAKE);
        // Calls a method
        entity.setInstance(player.getInstance(), player.getPosition()).join();

        // Branch: checks a condition
        if (context.has("follow")) {
            // Calls a method
            startSmoothFollow(entity, player);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void spawnText(CommandSender sender, CommandContext context) {
        // Branch: checks a condition
        if (!(sender instanceof Player player))
            // Returns a value to the caller
            return;

        // Calls a method
        var entity = new Entity(EntityType.TEXT_DISPLAY);
        // Calls a method
        var meta = (TextDisplayMeta) entity.getEntityMeta();
        // Calls a method
        meta.setTransformationInterpolationDuration(20);
        // Calls a method
        meta.setBillboardRenderConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
        // Calls a method
        meta.setText(Component.text("Hello, world!"));
        // Calls a method
        entity.setInstance(player.getInstance(), player.getPosition());

        // Branch: checks a condition
        if (context.has("follow")) {
            // Calls a method
            startSmoothFollow(entity, player);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void startSmoothFollow(Entity entity, Player player) {
//        entity.setCustomName(Component.text("MY CUSTOM NAME"));
//        entity.setCustomNameVisible(true);
        // Start of a method/block
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            // Calls a method
            var meta = (AbstractDisplayMeta) entity.getEntityMeta();
            // Calls a method
            meta.setNotifyAboutChanges(false);
            // Calls a method
            meta.setTransformationInterpolationStartDelta(1);
            // Calls a method
            meta.setTransformationInterpolationDuration(20);
//            meta.setPosRotInterpolationDuration(20);
//            entity.teleport(player.getPosition());
//            meta.setScale(new Vec(5, 5, 5));
            // Calls a method
            meta.setTranslation(player.getPosition().sub(entity.getPosition()));
            // Calls a method
            meta.setNotifyAboutChanges(true);
        // Calls a method
        }).delay(20, TimeUnit.SERVER_TICK).repeat(20, TimeUnit.SERVER_TICK).schedule();
    // End of a block/expression
    }
// End of a block/expression
}
