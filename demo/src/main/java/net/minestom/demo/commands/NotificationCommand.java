// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.advancements.FrameType;
// Import of a required class
import net.minestom.server.advancements.Notification;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.item.Material;

// Type declaration (class/interface/enum/record)
public class NotificationCommand extends Command {
    // Start of a method/block
    public NotificationCommand() {
        // Access to the current/parent object
        super("notification");

        // Start of a method/block
        setDefaultExecutor((sender, context) -> {
            // Calls a method
            var player = (Player) sender;
            // Code statement
            player.sendNotification(new Notification(
                    // Code statement
                    Component.text("Hello World!"),
                    // Code statement
                    FrameType.GOAL,
                    // Code statement
                    Material.DIAMOND_AXE));
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
