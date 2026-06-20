// Déclaration du paquet de ce fichier
package net.minestom.demo;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.Style;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.TextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.TextDecoration;
// Import d'une classe nécessaire
import net.minestom.demo.block.SignHandler;
// Import d'une classe nécessaire
import net.minestom.demo.block.TestBlockHandler;
// Import d'une classe nécessaire
import net.minestom.demo.block.placement.BedPlacementRule;
// Import d'une classe nécessaire
import net.minestom.demo.block.placement.DripstonePlacementRule;
// Import d'une classe nécessaire
import net.minestom.demo.commands.*;
// Import d'une classe nécessaire
import net.minestom.demo.recipe.ShapelessRecipe;
// Import d'une classe nécessaire
import net.minestom.server.Auth;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandManager;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.event.server.ServerListPingEvent;
// Import d'une classe nécessaire
import net.minestom.server.extras.lan.OpenToLAN;
// Import d'une classe nécessaire
import net.minestom.server.extras.lan.OpenToLANConfig;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockEntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockManager;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.ping.Status;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeBookCategory;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.registry.TagKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.io.InputStream;
// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public class Main {

    // Début d'une méthode/d'un bloc
    public static void main(String[] args) {
        // Appelle une méthode
        System.setProperty("minestom.new-socket-write-lock", "true");
        // Appelle une méthode
        MinecraftServer.setCompressionThreshold(0);

        // Appelle une méthode
        MinecraftServer minecraftServer = MinecraftServer.init(new Auth.Offline());

        // Appelle une méthode
        BlockManager blockManager = MinecraftServer.getBlockManager();
        // Appelle une méthode
        blockManager.registerBlockPlacementRule(new DripstonePlacementRule());
        // Appelle une méthode
        var beds = Block.values().stream().filter(block -> BlockEntityType.BED.equals(block.registry().blockEntityType())).toList();
        // Appelle une méthode
        beds.forEach(block -> blockManager.registerBlockPlacementRule(new BedPlacementRule(block)));
        // Appelle une méthode
        blockManager.registerHandler(TestBlockHandler.INSTANCE.getKey(), () -> TestBlockHandler.INSTANCE);

        // Appelle une méthode
        CommandManager commandManager = MinecraftServer.getCommandManager();
        // Appelle une méthode
        commandManager.register(new TestCommand());
        // Appelle une méthode
        commandManager.register(new EntitySelectorCommand());
        // Appelle une méthode
        commandManager.register(new HealthCommand());
        // Appelle une méthode
        commandManager.register(new LegacyCommand());
        // Appelle une méthode
        commandManager.register(new DimensionCommand());
        // Appelle une méthode
        commandManager.register(new ShutdownCommand());
        // Appelle une méthode
        commandManager.register(new TeleportCommand());
        // Appelle une méthode
        commandManager.register(new PlayersCommand());
        // Appelle une méthode
        commandManager.register(new FindCommand());
        // Appelle une méthode
        commandManager.register(new TitleCommand());
        // Appelle une méthode
        commandManager.register(new BookCommand());
        // Appelle une méthode
        commandManager.register(new ShootCommand());
        // Appelle une méthode
        commandManager.register(new HorseCommand());
        // Appelle une méthode
        commandManager.register(new EchoCommand());
        // Appelle une méthode
        commandManager.register(new SummonCommand());
        // Appelle une méthode
        commandManager.register(new RemoveCommand());
        // Appelle une méthode
        commandManager.register(new GiveCommand());
        // Appelle une méthode
        commandManager.register(new SetBlockCommand());
        // Appelle une méthode
        commandManager.register(new AutoViewCommand());
        // Appelle une méthode
        commandManager.register(new SaveCommand());
        // Appelle une méthode
        commandManager.register(new GamemodeCommand());
        // Appelle une méthode
        commandManager.register(new ExecuteCommand());
        // Appelle une méthode
        commandManager.register(new RedirectTestCommand());
        // Appelle une méthode
        commandManager.register(new DebugGridCommand());
        // Appelle une méthode
        commandManager.register(new DisplayCommand());
        // Appelle une méthode
        commandManager.register(new NotificationCommand());
        // Appelle une méthode
        commandManager.register(new TestCommand2());
        // Appelle une méthode
        commandManager.register(new ConfigCommand());
        // Appelle une méthode
        commandManager.register(new SidebarCommand());
        // Appelle une méthode
        commandManager.register(new SetEntityType());
        // Appelle une méthode
        commandManager.register(new RelightCommand());
        // Appelle une méthode
        commandManager.register(new KillCommand());
        // Appelle une méthode
        commandManager.register(new WeatherCommand());
        // Appelle une méthode
        commandManager.register(new PotionCommand());
        // Appelle une méthode
        commandManager.register(new CookieCommand());
        // Appelle une méthode
        commandManager.register(new WorldBorderCommand());
        // Appelle une méthode
        commandManager.register(new TransferCommand());
        // Appelle une méthode
        commandManager.register(new TestInstabreakCommand());
        // Appelle une méthode
        commandManager.register(new AttributeCommand());
        // Appelle une méthode
        commandManager.register(new PrimedTNTCommand());
        // Appelle une méthode
        commandManager.register(new SleepCommand());
        // Appelle une méthode
        commandManager.register(new MinecartCommand());
        // Appelle une méthode
        commandManager.register(new BelowNameCommand());

        // Appelle une méthode
        commandManager.setUnknownCommandCallback((sender, command) -> sender.sendMessage(Component.text("Unknown command", NamedTextColor.RED)));

        // Appelle une méthode
        MinecraftServer.getBenchmarkManager().enable(Duration.of(10, TimeUnit.SECOND));

        // Appelle une méthode
        MinecraftServer.getSchedulerManager().buildShutdownTask(() -> System.out.println("Good night"));

        // Appelle une méthode
        RegistryTag<Block> tag = Block.staticRegistry().getTag(TagKey.ofHash("#minecraft:all_signs"));
        // Appelle une méthode
        SignHandler signHandler = new SignHandler();
        // Boucle : répète un bloc
        for (RegistryKey<Block> key : Objects.requireNonNull(tag)) {
            // Appelle une méthode
            blockManager.registerHandler(key.key(), () -> signHandler);
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        byte[] favicon;

        // Gestion des exceptions
        try (InputStream stream = Main.class.getResourceAsStream("/minestom.png")) {
            // Appelle une méthode
            favicon = Objects.requireNonNull(stream).readAllBytes();
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        MinecraftServer.getGlobalEventHandler().addListener(ServerListPingEvent.class, event -> {
            // Appelle une méthode
            int onlinePlayers = MinecraftServer.getConnectionManager().getOnlinePlayers().size();
            // Affecte une valeur
            Status.PlayerInfo.Builder builder = Status.PlayerInfo.builder(Status.PlayerInfo.online(20))
                    // Instruction de code
                    .sample("The first line is separated from the others")
                    // Appelle une méthode
                    .sample("Could be a name, or a message");

            // on modern versions, you can obtain the player connection directly from the event
            // Embranchement : vérifie une condition
            if (event.getConnection() != null) {
                // Appelle une méthode
                String ip = event.getConnection().getServerAddress();
                // Affecte une valeur
                builder = builder
                        // Instruction de code
                        .sample("IP test: " + event.getConnection().getRemoteAddress().toString())
                        // Instruction de code
                        .sample("Connection Info:")
                        // Instruction de code
                        .sample(Component.text('-', NamedTextColor.DARK_GRAY)
                                // Instruction de code
                                .append(Component.text(" IP: ", NamedTextColor.GRAY))
                                // Instruction de code
                                .append(Component.text(ip != null ? ip : "???", NamedTextColor.YELLOW)))
                        // Instruction de code
                        .sample(Component.text('-', NamedTextColor.DARK_GRAY)
                                // Instruction de code
                                .append(Component.text(" PORT: ", NamedTextColor.GRAY))
                                // Instruction de code
                                .append(Component.text(event.getConnection().getServerPort())))
                        // Instruction de code
                        .sample(Component.text('-', NamedTextColor.DARK_GRAY)
                                // Instruction de code
                                .append(Component.text(" VERSION: ", NamedTextColor.GRAY))
                                // Appelle une méthode
                                .append(Component.text(event.getConnection().getProtocolVersion())));
            // Fin d'un bloc/d'une expression
            }

            // Affecte une valeur
            builder = builder
                    // Instruction de code
                    .sample(Component.text("Time", NamedTextColor.YELLOW)
                            // Instruction de code
                            .append(Component.text(": ", NamedTextColor.GRAY))
                            // Instruction de code
                            .append(Component.text(System.currentTimeMillis(), Style.style(TextDecoration.ITALIC))))
                    // components will be converted the legacy section sign format so they are displayed in the client
                    // Appelle une méthode
                    .sample(Component.text("You can use ").append(Component.text("styling too!", NamedTextColor.RED, TextDecoration.BOLD)));

            // Instruction de code
            event.setStatus(Status.builder()
                    // the data will be automatically converted to the correct format on response, so you can do RGB and it'll be downsampled!
                    // on legacy versions, colors will be converted to the section format so it'll work there too
                    // Instruction de code
                    .description(Component.text("This is a Minestom Server", TextColor.color(0x66b3ff)))
                    // Instruction de code
                    .favicon(favicon)
                    // Instruction de code
                    .playerInfo(builder.build())
                    // Appelle une méthode
                    .build());
        // Fin d'un bloc/d'une expression
        });

        // Instruction de code
        MinecraftServer.getRecipeManager().addRecipe(new ShapelessRecipe(
                // Instruction de code
                RecipeBookCategory.CRAFTING_MISC,
                // Instruction de code
                List.of(Material.DIRT),
                // Instruction de code
                ItemStack.builder(Material.GOLD_BLOCK)
                        // Instruction de code
                        .set(DataComponents.CUSTOM_NAME, Component.text("abc"))
                        // Instruction de code
                        .build()
        // Instruction de code
        ));

        // Crée un nouvel objet
        new PlayerInit().init();

//        VelocityProxy.enable("abcdef");
        //BungeeCordProxy.enable();

//        MojangAuth.init();

        // useful for testing - we don't need to worry about event calls so just set this to a long time
        // Appelle une méthode
        OpenToLAN.open(new OpenToLANConfig().eventCallDelay(Duration.of(1, TimeUnit.DAY)));

        // Appelle une méthode
        minecraftServer.start("0.0.0.0", 25565);
//        minecraftServer.start(java.net.UnixDomainSocketAddress.of("minestom-demo.sock"));
        //Runtime.getRuntime().addShutdownHook(new Thread(MinecraftServer::stopCleanly));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
