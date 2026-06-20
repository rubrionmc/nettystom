// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identity;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.ClickEvent;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.ArgumentComponent;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.ArgumentUUID;

// Déclaration de type (classe/interface/enum/record)
public class EchoCommand extends Command {
    // Début d'une méthode/d'un bloc
    public EchoCommand() {
        // Accès à l'objet courant/parent
        super("echo");

        // Accès à l'objet courant/parent
        this.setDefaultExecutor((sender, context) -> sender.sendMessage(
                // Instruction de code
                Component.text("Usage: /echo <json> [uuid]")
                        // Instruction de code
                        .hoverEvent(Component.text("Click to get this command.")
                        // Appelle une méthode
                        .clickEvent(ClickEvent.suggestCommand("/echo ")))));

        // Appelle une méthode
        ArgumentComponent json = ArgumentType.Component("json");
        // Appelle une méthode
        ArgumentUUID uuid = ArgumentType.UUID("uuid");

        // Accès à l'objet courant/parent
        this.addSyntax((sender, context) -> sender.sendMessage(context.get(json)), json);

        // Accès à l'objet courant/parent
        this.addSyntax((sender, context) -> sender.sendMessage(context.get(json)), uuid, json);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
