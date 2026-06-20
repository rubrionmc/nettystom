// Package declaration for this file
package net.minestom.demo;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.text.format.Style;
// Import of a required class
import net.kyori.adventure.text.format.TextColor;
// Import of a required class
import net.kyori.adventure.text.format.TextDecoration;
// Import of a required class
import net.minestom.demo.block.SignHandler;
// Import of a required class
import net.minestom.demo.block.TestBlockHandler;
// Import of a required class
import net.minestom.demo.block.placement.BedPlacementRule;
// Import of a required class
import net.minestom.demo.block.placement.DripstonePlacementRule;
// Import of a required class
import net.minestom.demo.commands.*;
// Import of a required class
import net.minestom.demo.recipe.ShapelessRecipe;
// Import of a required class
import net.minestom.server.Auth;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.CommandManager;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.event.server.ServerListPingEvent;
// Import of a required class
import net.minestom.server.extras.lan.OpenToLAN;
// Import of a required class
import net.minestom.server.extras.lan.OpenToLANConfig;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockEntityType;
// Import of a required class
import net.minestom.server.instance.block.BlockManager;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.ping.Status;
// Import of a required class
import net.minestom.server.recipe.RecipeBookCategory;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.registry.TagKey;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.io.InputStream;
// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public class Main {

    // Start of a method/block
    static void main(String[] args) {
        // Calls a method
        System.setProperty("minestom.new-socket-write-lock", "true");
        // Calls a method
        System.setProperty("minestom.registry.unsafe-ops", "true");
        // Calls a method
        MinecraftServer.setCompressionThreshold(0);

        // Calls a method
        MinecraftServer minecraftServer = MinecraftServer.init(new Auth.Offline());

        // Calls a method
        BlockManager blockManager = MinecraftServer.getBlockManager();
        // Calls a method
        blockManager.registerBlockPlacementRule(new DripstonePlacementRule());
        // Calls a method
        var beds = Block.values().stream().filter(block -> BlockEntityType.BED.equals(block.registry().blockEntityType())).toList();
        // Calls a method
        beds.forEach(block -> blockManager.registerBlockPlacementRule(new BedPlacementRule(block)));
        // Calls a method
        blockManager.registerHandler(TestBlockHandler.INSTANCE.getKey(), () -> TestBlockHandler.INSTANCE);

        // Calls a method
        CommandManager commandManager = MinecraftServer.getCommandManager();
        // Calls a method
        commandManager.register(new TestCommand());
        // Calls a method
        commandManager.register(new EntitySelectorCommand());
        // Calls a method
        commandManager.register(new HealthCommand());
        // Calls a method
        commandManager.register(new LegacyCommand());
        // Calls a method
        commandManager.register(new DimensionCommand());
        // Calls a method
        commandManager.register(new ShutdownCommand());
        // Calls a method
        commandManager.register(new TeleportCommand());
        // Calls a method
        commandManager.register(new PlayersCommand());
        // Calls a method
        commandManager.register(new FindCommand());
        // Calls a method
        commandManager.register(new TitleCommand());
        // Calls a method
        commandManager.register(new BookCommand());
        // Calls a method
        commandManager.register(new ShootCommand());
        // Calls a method
        commandManager.register(new HorseCommand());
        // Calls a method
        commandManager.register(new EchoCommand());
        // Calls a method
        commandManager.register(new SummonCommand());
        // Calls a method
        commandManager.register(new RemoveCommand());
        // Calls a method
        commandManager.register(new GiveCommand());
        // Calls a method
        commandManager.register(new SetBlockCommand());
        // Calls a method
        commandManager.register(new AutoViewCommand());
        // Calls a method
        commandManager.register(new SaveCommand());
        // Calls a method
        commandManager.register(new GamemodeCommand());
        // Calls a method
        commandManager.register(new ExecuteCommand());
        // Calls a method
        commandManager.register(new RedirectTestCommand());
        // Calls a method
        commandManager.register(new DebugGridCommand());
        // Calls a method
        commandManager.register(new DisplayCommand());
        // Calls a method
        commandManager.register(new NotificationCommand());
        // Calls a method
        commandManager.register(new TestCommand2());
        // Calls a method
        commandManager.register(new ConfigCommand());
        // Calls a method
        commandManager.register(new SidebarCommand());
        // Calls a method
        commandManager.register(new SetEntityType());
        // Calls a method
        commandManager.register(new RelightCommand());
        // Calls a method
        commandManager.register(new KillCommand());
        // Calls a method
        commandManager.register(new WeatherCommand());
        // Calls a method
        commandManager.register(new PotionCommand());
        // Calls a method
        commandManager.register(new CookieCommand());
        // Calls a method
        commandManager.register(new WorldBorderCommand());
        // Calls a method
        commandManager.register(new TransferCommand());
        // Calls a method
        commandManager.register(new TestInstabreakCommand());
        // Calls a method
        commandManager.register(new AttributeCommand());
        // Calls a method
        commandManager.register(new PrimedTNTCommand());
        // Calls a method
        commandManager.register(new SleepCommand());
        // Calls a method
        commandManager.register(new MinecartCommand());
        // Calls a method
        commandManager.register(new BelowNameCommand());
        // Calls a method
        commandManager.register(new TestBiomeAmbientParticleCommand());

        // Calls a method
        commandManager.setUnknownCommandCallback((sender, command) -> sender.sendMessage(Component.text("Unknown command", NamedTextColor.RED)));

        // Calls a method
        MinecraftServer.getBenchmarkManager().enable(Duration.of(10, TimeUnit.SECOND));

        // Calls a method
        MinecraftServer.getSchedulerManager().buildShutdownTask(() -> System.out.println("Good night"));

        // Calls a method
        RegistryTag<Block> tag = Block.staticRegistry().getTag(TagKey.ofHash("#minecraft:all_signs"));
        // Calls a method
        SignHandler signHandler = new SignHandler();
        // Loop: repeats a block
        for (RegistryKey<Block> key : Objects.requireNonNull(tag)) {
            // Calls a method
            blockManager.registerHandler(key.key(), () -> signHandler);
        // End of a block/expression
        }

        // Code statement
        byte[] favicon;

        // Exception handling
        try (InputStream stream = Main.class.getResourceAsStream("/minestom.png")) {
            // Calls a method
            favicon = Objects.requireNonNull(stream).readAllBytes();
        // Start of a method/block
        } catch (IOException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }

        // Start of a method/block
        MinecraftServer.getGlobalEventHandler().addListener(ServerListPingEvent.class, event -> {
            // Calls a method
            int onlinePlayers = MinecraftServer.getConnectionManager().getOnlinePlayers().size();
            // Assigns a value
            Status.PlayerInfo.Builder builder = Status.PlayerInfo.builder(Status.PlayerInfo.online(20))
                    // Code statement
                    .sample("The first line is separated from the others")
                    // Calls a method
                    .sample("Could be a name, or a message");

            // on modern versions, you can obtain the player connection directly from the event
            // Branch: checks a condition
            if (event.getConnection() != null) {
                // Calls a method
                String ip = event.getConnection().getServerAddress();
                // Assigns a value
                builder = builder
                        // Code statement
                        .sample("IP test: " + event.getConnection().getRemoteAddress())
                        // Code statement
                        .sample("Connection Info:")
                        // Code statement
                        .sample(Component.text('-', NamedTextColor.DARK_GRAY)
                                // Code statement
                                .append(Component.text(" IP: ", NamedTextColor.GRAY))
                                // Code statement
                                .append(Component.text(ip != null ? ip : "???", NamedTextColor.YELLOW)))
                        // Code statement
                        .sample(Component.text('-', NamedTextColor.DARK_GRAY)
                                // Code statement
                                .append(Component.text(" PORT: ", NamedTextColor.GRAY))
                                // Code statement
                                .append(Component.text(event.getConnection().getServerPort())))
                        // Code statement
                        .sample(Component.text('-', NamedTextColor.DARK_GRAY)
                                // Code statement
                                .append(Component.text(" VERSION: ", NamedTextColor.GRAY))
                                // Calls a method
                                .append(Component.text(event.getConnection().getProtocolVersion())));
            // End of a block/expression
            }

            // Assigns a value
            builder = builder
                    // Code statement
                    .sample(Component.text("Time", NamedTextColor.YELLOW)
                            // Code statement
                            .append(Component.text(": ", NamedTextColor.GRAY))
                            // Code statement
                            .append(Component.text(System.currentTimeMillis(), Style.style(TextDecoration.ITALIC))))
                    // components will be converted the legacy section sign format so they are displayed in the client
                    // Calls a method
                    .sample(Component.text("You can use ").append(Component.text("styling too!", NamedTextColor.RED, TextDecoration.BOLD)));

            // Code statement
            event.setStatus(Status.builder()
                    // the data will be automatically converted to the correct format on response, so you can do RGB and it'll be downsampled!
                    // on legacy versions, colors will be converted to the section format so it'll work there too
                    // Code statement
                    .description(Component.text("This is a Minestom Server", TextColor.color(0x66b3ff)))
                    // Code statement
                    .favicon(favicon)
                    // Code statement
                    .playerInfo(builder.build())
                    // Calls a method
                    .build());
        // End of a block/expression
        });

        // Code statement
        MinecraftServer.getRecipeManager().addRecipe(new ShapelessRecipe(
                // Code statement
                RecipeBookCategory.CRAFTING_MISC,
                // Code statement
                List.of(Material.DIRT),
                // Code statement
                ItemStack.builder(Material.GOLD_BLOCK)
                        // Code statement
                        .set(DataComponents.CUSTOM_NAME, Component.text("abc"))
                        // Code statement
                        .build()
        // Code statement
        ));

        // Creates a new object
        new PlayerInit().init();

//        VelocityProxy.enable("abcdef");
        //BungeeCordProxy.enable();

//        MojangAuth.init();

        // useful for testing - we don't need to worry about event calls so just set this to a long time
        // Calls a method
        OpenToLAN.open(new OpenToLANConfig().eventCallDelay(Duration.of(1, TimeUnit.DAY)));

        // Calls a method
        minecraftServer.start("0.0.0.0", 25565);
//        minecraftServer.start(java.net.UnixDomainSocketAddress.of("minestom-demo.sock"));
        //Runtime.getRuntime().addShutdownHook(new Thread(MinecraftServer::stopCleanly));
    // End of a block/expression
    }
// End of a block/expression
}
