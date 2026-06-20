// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.identity.Identity;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.event.ClickEvent;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.ArgumentComponent;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.ArgumentUUID;

// Type declaration (class/interface/enum/record)
public class EchoCommand extends Command {
    // Start of a method/block
    public EchoCommand() {
        // Access to the current/parent object
        super("echo");

        // Access to the current/parent object
        this.setDefaultExecutor((sender, context) -> sender.sendMessage(
                // Code statement
                Component.text("Usage: /echo <json> [uuid]")
                        // Code statement
                        .hoverEvent(Component.text("Click to get this command.")
                        // Calls a method
                        .clickEvent(ClickEvent.suggestCommand("/echo ")))));

        // Calls a method
        ArgumentComponent json = ArgumentType.Component("json");
        // Calls a method
        ArgumentUUID uuid = ArgumentType.UUID("uuid");

        // Access to the current/parent object
        this.addSyntax((sender, context) -> sender.sendMessage(context.get(json)), json);

        // Access to the current/parent object
        this.addSyntax((sender, context) -> sender.sendMessage(context.get(json)), uuid, json);
    // End of a block/expression
    }
// End of a block/expression
}
