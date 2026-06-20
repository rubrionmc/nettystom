// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.sound.SoundEvent;

// Type declaration (class/interface/enum/record)
public class TestCommand extends Command {

    // Start of a method/block
    public TestCommand() {
        // Access to the current/parent object
        super("testcmd");
        // Calls a method
        setDefaultExecutor(this::usage);

        // Calls a method
        var block = ArgumentType.BlockState("block");
        // Calls a method
        block.setCallback((sender, exception) -> exception.printStackTrace());

        // Start of a method/block
        setDefaultExecutor((sender, context) -> {
            // Calls a method
            sender.playSound(Sound.sound(Key.key("item.trumpet.doot"), Sound.Source.PLAYER, 1, 1));
            // Calls a method
            AdventurePacketConvertor.createSoundPacket(Sound.sound(Key.key(SoundEvent.BLOCK_ANVIL_HIT.name()), Sound.Source.HOSTILE, 1, 1), (Player) sender);
        // End of a block/expression
        });
        // Calls a method
        addSyntax((sender, context) -> System.out.println("executed"), block);

    // End of a block/expression
    }

    // Start of a method/block
    private void usage(CommandSender sender, CommandContext context) {
        // Calls a method
        sender.sendMessage(Component.text("Incorrect usage"));
    // End of a block/expression
    }

// End of a block/expression
}
