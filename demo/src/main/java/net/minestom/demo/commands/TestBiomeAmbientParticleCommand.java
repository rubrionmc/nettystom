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
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventListener;
// Import of a required class
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.world.attribute.AmbientParticle;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import of a required class
import net.minestom.server.world.biome.Biome;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Type declaration (class/interface/enum/record)
public class TestBiomeAmbientParticleCommand extends Command {

    // Start of a method/block
    public TestBiomeAmbientParticleCommand() {
        // Access to the current/parent object
        super("testbiomeambientparticle");
        // Calls a method
        setDefaultExecutor(this::usage);
    // End of a block/expression
    }

    // Start of a method/block
    private void usage(CommandSender sender, CommandContext context) {
        // Branch: checks a condition
        if (!(sender instanceof Player player)) {
            // Calls a method
            sender.sendMessage(Component.text("This command is only available for players"));
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        Instance instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        // Assigns a value
        Particle particle = Particle.BLOCK_MARKER.withBlock(
                // Code statement
                Block.COPPER_BULB
                        // Code statement
                        .withProperty("lit", "true")
                        // Code statement
                        .withProperty("powered", "false")
        // End of a block/expression
        );
        // Assigns a value
        Biome biome = Biome.builder()
                // Code statement
                .setAttribute(EnvironmentAttribute.AMBIENT_PARTICLES, List.of(new AmbientParticle(particle, 0.005f)))
                // Calls a method
                .build();
        // Calls a method
        RegistryKey<Biome> key = MinecraftServer.getBiomeRegistry().register("testbiome", biome);
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            unit.modifier().fillBiome(key);
            // Calls a method
            unit.fork(unit.absoluteStart().withY(63), unit.absoluteEnd().withY(63)).modifier().fill(Block.STONE);
        // End of a block/expression
        });
        // register the biome on the client side
        // Calls a method
        player.startConfigurationPhase();
        // Calls a method
        AtomicReference<EventListener<AsyncPlayerConfigurationEvent>> handlerRef = new AtomicReference<>();
        // Assigns a value
        EventListener<AsyncPlayerConfigurationEvent> handler = EventListener.builder(AsyncPlayerConfigurationEvent.class).handler(event -> {
            // Calls a method
            event.setSendRegistryData(true);
            // Calls a method
            player.eventNode().removeListener(handlerRef.get());
            // Calls a method
            player.scheduler().scheduleNextTick(() -> player.setInstance(instance));
        // Calls a method
        }).build();
        // Calls a method
        handlerRef.set(handler);
        // Calls a method
        player.eventNode().addListener(handler);
    // End of a block/expression
    }
// End of a block/expression
}
