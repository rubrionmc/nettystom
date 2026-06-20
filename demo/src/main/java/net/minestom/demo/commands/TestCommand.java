// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;

// Déclaration de type (classe/interface/enum/record)
public class TestCommand extends Command {

    // Début d'une méthode/d'un bloc
    public TestCommand() {
        // Accès à l'objet courant/parent
        super("testcmd");
        // Appelle une méthode
        setDefaultExecutor(this::usage);

        // Appelle une méthode
        var block = ArgumentType.BlockState("block");
        // Appelle une méthode
        block.setCallback((sender, exception) -> exception.printStackTrace());

        // Début d'une méthode/d'un bloc
        setDefaultExecutor((sender, context) -> {
            // Appelle une méthode
            sender.playSound(Sound.sound(Key.key("item.trumpet.doot"), Sound.Source.PLAYER, 1, 1));
            // Appelle une méthode
            AdventurePacketConvertor.createSoundPacket(Sound.sound(Key.key(SoundEvent.BLOCK_ANVIL_HIT.name()), Sound.Source.HOSTILE, 1, 1), (Player) sender);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        addSyntax((sender, context) -> System.out.println("executed"), block);

    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void usage(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        sender.sendMessage(Component.text("Incorrect usage"));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
