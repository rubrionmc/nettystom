// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.display.AbstractDisplayMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;

// Déclaration de type (classe/interface/enum/record)
public class DisplayCommand extends Command {

    // Début d'une méthode/d'un bloc
    public DisplayCommand() {
        // Accès à l'objet courant/parent
        super("display");

        // Appelle une méthode
        var follow = ArgumentType.Literal("follow");

        // Appelle une méthode
        addSyntax(this::spawnItem, ArgumentType.Literal("item"));
        // Appelle une méthode
        addSyntax(this::spawnBlock, ArgumentType.Literal("block"));
        // Appelle une méthode
        addSyntax(this::spawnText, ArgumentType.Literal("text"));

        // Appelle une méthode
        addSyntax(this::spawnItem, ArgumentType.Literal("item"), follow);
        // Appelle une méthode
        addSyntax(this::spawnBlock, ArgumentType.Literal("block"), follow);
        // Appelle une méthode
        addSyntax(this::spawnText, ArgumentType.Literal("text"), follow);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void spawnItem(CommandSender sender, CommandContext context) {
        // Embranchement : vérifie une condition
        if (!(sender instanceof Player player))
            // Renvoie une valeur à l'appelant
            return;

        // Appelle une méthode
        var entity = new Entity(EntityType.ITEM_DISPLAY);
        // Appelle une méthode
        var meta = (ItemDisplayMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setTransformationInterpolationDuration(20);
        // Appelle une méthode
        meta.setItemStack(ItemStack.of(Material.STICK));
        // Appelle une méthode
        entity.setInstance(player.getInstance(), player.getPosition());

        // Embranchement : vérifie une condition
        if (context.has("follow")) {
            // Appelle une méthode
            startSmoothFollow(entity, player);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void spawnBlock(CommandSender sender, CommandContext context) {
        // Embranchement : vérifie une condition
        if (!(sender instanceof Player player))
            // Renvoie une valeur à l'appelant
            return;

        // Appelle une méthode
        var entity = new Entity(EntityType.BLOCK_DISPLAY);
        // Appelle une méthode
        var meta = (BlockDisplayMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setTransformationInterpolationDuration(20);
        // Appelle une méthode
        meta.setBlockState(Block.ORANGE_CANDLE_CAKE);
        // Appelle une méthode
        entity.setInstance(player.getInstance(), player.getPosition()).join();

        // Embranchement : vérifie une condition
        if (context.has("follow")) {
            // Appelle une méthode
            startSmoothFollow(entity, player);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void spawnText(CommandSender sender, CommandContext context) {
        // Embranchement : vérifie une condition
        if (!(sender instanceof Player player))
            // Renvoie une valeur à l'appelant
            return;

        // Appelle une méthode
        var entity = new Entity(EntityType.TEXT_DISPLAY);
        // Appelle une méthode
        var meta = (TextDisplayMeta) entity.getEntityMeta();
        // Appelle une méthode
        meta.setTransformationInterpolationDuration(20);
        // Appelle une méthode
        meta.setBillboardRenderConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
        // Appelle une méthode
        meta.setText(Component.text("Hello, world!"));
        // Appelle une méthode
        entity.setInstance(player.getInstance(), player.getPosition());

        // Embranchement : vérifie une condition
        if (context.has("follow")) {
            // Appelle une méthode
            startSmoothFollow(entity, player);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void startSmoothFollow(Entity entity, Player player) {
//        entity.setCustomName(Component.text("MY CUSTOM NAME"));
//        entity.setCustomNameVisible(true);
        // Début d'une méthode/d'un bloc
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            // Appelle une méthode
            var meta = (AbstractDisplayMeta) entity.getEntityMeta();
            // Appelle une méthode
            meta.setNotifyAboutChanges(false);
            // Appelle une méthode
            meta.setTransformationInterpolationStartDelta(1);
            // Appelle une méthode
            meta.setTransformationInterpolationDuration(20);
//            meta.setPosRotInterpolationDuration(20);
//            entity.teleport(player.getPosition());
//            meta.setScale(new Vec(5, 5, 5));
            // Appelle une méthode
            meta.setTranslation(player.getPosition().sub(entity.getPosition()));
            // Appelle une méthode
            meta.setNotifyAboutChanges(true);
        // Appelle une méthode
        }).delay(20, TimeUnit.SERVER_TICK).repeat(20, TimeUnit.SERVER_TICK).schedule();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
