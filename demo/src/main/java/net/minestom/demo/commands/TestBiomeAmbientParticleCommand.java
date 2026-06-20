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
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventListener;
// Import d'une classe nécessaire
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.AmbientParticle;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Déclaration de type (classe/interface/enum/record)
public class TestBiomeAmbientParticleCommand extends Command {

    // Début d'une méthode/d'un bloc
    public TestBiomeAmbientParticleCommand() {
        // Accès à l'objet courant/parent
        super("testbiomeambientparticle");
        // Appelle une méthode
        setDefaultExecutor(this::usage);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void usage(CommandSender sender, CommandContext context) {
        // Embranchement : vérifie une condition
        if (!(sender instanceof Player player)) {
            // Appelle une méthode
            sender.sendMessage(Component.text("This command is only available for players"));
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Instance instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        // Affecte une valeur
        Particle particle = Particle.BLOCK_MARKER.withBlock(
                // Instruction de code
                Block.COPPER_BULB
                        // Instruction de code
                        .withProperty("lit", "true")
                        // Instruction de code
                        .withProperty("powered", "false")
        // Fin d'un bloc/d'une expression
        );
        // Affecte une valeur
        Biome biome = Biome.builder()
                // Instruction de code
                .setAttribute(EnvironmentAttribute.AMBIENT_PARTICLES, List.of(new AmbientParticle(particle, 0.005f)))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        RegistryKey<Biome> key = MinecraftServer.getBiomeRegistry().register("testbiome", biome);
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            unit.modifier().fillBiome(key);
            // Appelle une méthode
            unit.fork(unit.absoluteStart().withY(63), unit.absoluteEnd().withY(63)).modifier().fill(Block.STONE);
        // Fin d'un bloc/d'une expression
        });
        // register the biome on the client side
        // Appelle une méthode
        player.startConfigurationPhase();
        // Appelle une méthode
        AtomicReference<EventListener<AsyncPlayerConfigurationEvent>> handlerRef = new AtomicReference<>();
        // Affecte une valeur
        EventListener<AsyncPlayerConfigurationEvent> handler = EventListener.builder(AsyncPlayerConfigurationEvent.class).handler(event -> {
            // Appelle une méthode
            event.setSendRegistryData(true);
            // Appelle une méthode
            player.eventNode().removeListener(handlerRef.get());
            // Appelle une méthode
            player.scheduler().scheduleNextTick(() -> player.setInstance(instance));
        // Appelle une méthode
        }).build();
        // Appelle une méthode
        handlerRef.set(handler);
        // Appelle une méthode
        player.eventNode().addListener(handler);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
