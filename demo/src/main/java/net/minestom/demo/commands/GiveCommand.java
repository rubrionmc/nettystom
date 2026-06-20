// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.inventory.PlayerInventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.TransactionOption;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.utils.entity.EntityFinder;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Integer;
// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.*;

// Déclaration de type (classe/interface/enum/record)
public class GiveCommand extends Command {
    // Début d'une méthode/d'un bloc
    public GiveCommand() {
        // Accès à l'objet courant/parent
        super("give");

        // Instruction de code
        setDefaultExecutor((sender, context) ->
                // Appelle une méthode
                sender.sendMessage(Component.text("Usage: /give <target> <item> [<count>]")));

        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Appelle une méthode
            final EntityFinder entityFinder = context.get("target");
            // Appelle une méthode
            int count = context.get("count");
            // Appelle une méthode
            count = Math.min(count, PlayerInventory.INVENTORY_SIZE * 64);
            // Appelle une méthode
            ItemStack itemStack = context.get("item");

            // Instruction de code
            List<ItemStack> itemStacks;
            // Embranchement : vérifie une condition
            if (count <= 64) {
                // Appelle une méthode
                itemStack = itemStack.withAmount(count);
                // Appelle une méthode
                itemStacks = List.of(itemStack);
            // Branche alternative de la condition
            } else {
                // Affecte une valeur
                itemStacks = new ArrayList<>();
                // Boucle : répète un bloc
                while (count > 64) {
                    // Appelle une méthode
                    itemStacks.add(itemStack.withAmount(64));
                    // Affecte une valeur
                    count -= 64;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                itemStacks.add(itemStack.withAmount(count));
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final List<Entity> targets = entityFinder.find(sender);
            // Boucle : répète un bloc
            for (Entity target : targets) {
                // Embranchement : vérifie une condition
                if (target instanceof Player) {
                    // Affecte une valeur
                    Player player = (Player) target;
                    // Appelle une méthode
                    player.getInventory().addItemStacks(itemStacks, TransactionOption.ALL);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            sender.sendMessage(Component.text("Items have been given successfully!"));

        // Appelle une méthode
        }, Entity("target").onlyPlayers(true), ItemStack("item"), Integer("count").setDefaultValue(() -> 1));

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
