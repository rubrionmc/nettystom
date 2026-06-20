// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.advancements.FrameType;
// Import d'une classe nécessaire
import net.minestom.server.advancements.Notification;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;

// Déclaration de type (classe/interface/enum/record)
public class NotificationCommand extends Command {
    // Début d'une méthode/d'un bloc
    public NotificationCommand() {
        // Accès à l'objet courant/parent
        super("notification");

        // Début d'une méthode/d'un bloc
        setDefaultExecutor((sender, context) -> {
            // Affecte une valeur
            var player = (Player) sender;
            // Instruction de code
            player.sendNotification(new Notification(
                    // Instruction de code
                    Component.text("Hello World!"),
                    // Instruction de code
                    FrameType.GOAL,
                    // Instruction de code
                    Material.DIAMOND_AXE));
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
