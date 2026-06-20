// Déclaration du paquet de ce fichier
package net.minestom.demo;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.ClickEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.text.object.ObjectContents;
// Import d'une classe nécessaire
import net.minestom.demo.entity.PlayerEntity;
// Import d'une classe nécessaire
import net.minestom.server.FeatureFlag;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.advancements.FrameType;
// Import d'une classe nécessaire
import net.minestom.server.advancements.Notification;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.adventure.audience.Audiences;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.dialog.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.Damage;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.avatar.MannequinMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.golem.CopperGolemMeta;
// Import d'une classe nécessaire
import net.minestom.server.event.Event;
// Import d'une classe nécessaire
import net.minestom.server.event.EventNode;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityAttackEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.CreativeInventoryActionEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.item.*;
// Import d'une classe nécessaire
import net.minestom.server.event.player.*;
// Import d'une classe nécessaire
import net.minestom.server.event.server.ServerTickMonitorEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.InstanceContainer;
// Import d'une classe nécessaire
import net.minestom.server.instance.InstanceManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.LightingChunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.predicate.BlockPredicate;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryType;
// Import d'une classe nécessaire
import net.minestom.server.inventory.PlayerInventory;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemAnimation;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.BlockPredicates;
// Import d'une classe nécessaire
import net.minestom.server.item.component.Consumable;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.BenchmarkManager;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.TickMonitor;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.CustomReportDetailsPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.ServerLinksPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TrackedWaypointPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ResolvableProfile;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.concurrent.ThreadLocalRandom;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Déclaration de type (classe/interface/enum/record)
public class PlayerInit {

    // Instruction de code
    private final Inventory inventory;

    // Affecte une valeur
    private final EventNode<Event> DEMO_NODE = EventNode.all("demo")
            // Début d'une méthode/d'un bloc
            .addListener(EntityAttackEvent.class, event -> {
                // Appelle une méthode
                final Entity source = event.getEntity();
                // Appelle une méthode
                final Entity entity = event.getTarget();

                // Appelle une méthode
                entity.takeKnockback(0.4f, Math.sin(source.getPosition().yaw() * 0.017453292), -Math.cos(source.getPosition().yaw() * 0.017453292));

                // Embranchement : vérifie une condition
                if (entity instanceof Player target) {
                    // Appelle une méthode
                    target.damage(Damage.fromEntity(source, 5));
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (source instanceof Player) {
                    // Appelle une méthode
                    ((Player) source).sendMessage("You attacked something!");
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Instruction de code
            .addListener(PlayerDeathEvent.class, event -> event.setChatMessage(Component.text("custom death message")))
            // Début d'une méthode/d'un bloc
            .addListener(PickupItemEvent.class, event -> {
                // Appelle une méthode
                final Entity entity = event.getLivingEntity();
                // Embranchement : vérifie une condition
                if (entity instanceof Player) {
                    // Cancel event if player does not have enough inventory space
                    // Appelle une méthode
                    final ItemStack itemStack = event.getItemEntity().getItemStack();
                    // Appelle une méthode
                    event.setCancelled(!((Player) entity).getInventory().addItemStack(itemStack));
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(ItemDropEvent.class, event -> {
                // Appelle une méthode
                final Player player = event.getPlayer();
                // Appelle une méthode
                ItemStack droppedItem = event.getItemStack();

                // Appelle une méthode
                Pos playerPos = player.getPosition();
                // Appelle une méthode
                ItemEntity itemEntity = new ItemEntity(droppedItem);
                // Appelle une méthode
                itemEntity.setPickupDelay(Duration.of(500, TimeUnit.MILLISECOND));
                // Appelle une méthode
                itemEntity.setInstance(player.getInstance(), playerPos.withY(y -> y + 1.5));
                // Appelle une méthode
                Vec velocity = playerPos.direction().mul(6);
                // Appelle une méthode
                itemEntity.setVelocity(velocity);
            // Instruction de code
            })
            // Instruction de code
            .addListener(PlayerDisconnectEvent.class, event -> System.out.println("DISCONNECTION " + event.getPlayer().getUsername()))
            // Début d'une méthode/d'un bloc
            .addListener(AsyncPlayerConfigurationEvent.class, event -> {
                // Appelle une méthode
                final Player player = event.getPlayer();

                // Show off adding and removing feature flags
                // Instruction de code
                event.removeFeatureFlag(FeatureFlag.TRADE_REBALANCE); // not enabled by default, just removed for demonstration

                // Appelle une méthode
                var instances = MinecraftServer.getInstanceManager().getInstances();
                // Appelle une méthode
                Instance instance = instances.stream().skip(new Random().nextInt(instances.size())).findFirst().orElse(null);
                // Appelle une méthode
                event.setSpawningInstance(instance);
                // Appelle une méthode
                int x = Math.abs(ThreadLocalRandom.current().nextInt()) % 500 - 250;
                // Appelle une méthode
                int z = Math.abs(ThreadLocalRandom.current().nextInt()) % 500 - 250;
                // Appelle une méthode
                player.setRespawnPoint(new Pos(0, 40f, 0));
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerSpawnEvent.class, event -> {
                // Appelle une méthode
                final Player player = event.getPlayer();
                // Appelle une méthode
                player.setGameMode(GameMode.CREATIVE);
                // Appelle une méthode
                player.setPermissionLevel(4);

                // Instruction de code
                player.sendMessage(Component.text("click me for less health ")
                        // Instruction de code
                        .clickEvent(ClickEvent.runCommand("health set 2"))
                        // Instruction de code
                        .append(Component.object(ObjectContents.sprite(Key.key("block/stone"))))
                        // Appelle une méthode
                        .append(Component.object(ObjectContents.playerHead("Minestom"))));
                // Affecte une valeur
                ItemStack itemStack = ItemStack.builder(Material.STONE)
                        // Instruction de code
                        .amount(64)
                        // Instruction de code
                        .set(DataComponents.CAN_PLACE_ON, new BlockPredicates(new BlockPredicate(Block.STONE)))
                        // Instruction de code
                        .set(DataComponents.CAN_BREAK, new BlockPredicates(new BlockPredicate(Block.DIAMOND_ORE)))
                        // Appelle une méthode
                        .build();
                // Appelle une méthode
                player.getInventory().addItemStack(itemStack);

                // Instruction de code
                player.sendPacket(new CustomReportDetailsPacket(Map.of(
                        // Instruction de code
                        "hello", "world"
                // Instruction de code
                )));

                // Instruction de code
                player.sendPacket(new ServerLinksPacket(
                        // Crée un nouvel objet
                        new ServerLinksPacket.Entry(ServerLinksPacket.KnownLinkType.NEWS, "https://minestom.net"),
                        // Crée un nouvel objet
                        new ServerLinksPacket.Entry(ServerLinksPacket.KnownLinkType.BUG_REPORT, "https://minestom.net"),
                        // Crée un nouvel objet
                        new ServerLinksPacket.Entry(Component.text("Hello world!"), "https://minestom.net")
                // Instruction de code
                ));

                // TODO(1.21.2): Handle bundle slot selection
                // Affecte une valeur
                ItemStack bundle = ItemStack.builder(Material.BUNDLE)
                        // Instruction de code
                        .set(DataComponents.BUNDLE_CONTENTS, List.of(
                                // Instruction de code
                                ItemStack.of(Material.DIAMOND, 5),
                                // Instruction de code
                                ItemStack.of(Material.RABBIT_FOOT, 5)
                        // Instruction de code
                        ))
                        // Appelle une méthode
                        .build();
                // Appelle une méthode
                player.getInventory().addItemStack(bundle);

                // Appelle une méthode
                PlayerInventory inventory = event.getPlayer().getInventory();
                // Appelle une méthode
                inventory.addItemStack(getFoodItem(20));
                // Appelle une méthode
                inventory.addItemStack(ItemStack.of(Material.PURPLE_BED));

                // Embranchement : vérifie une condition
                if (event.isFirstSpawn()) {
                    // Instruction de code
                    event.getPlayer().sendNotification(new Notification(
                            // Instruction de code
                            Component.text("Welcome!"),
                            // Instruction de code
                            FrameType.TASK,
                            // Instruction de code
                            Material.IRON_SWORD
                    // Instruction de code
                    ));

                    // Appelle une méthode
                    player.playSound(Sound.sound(SoundEvent.ENTITY_EXPERIENCE_ORB_PICKUP, Sound.Source.PLAYER, 0.5f, 1f));

                    // Appelle une méthode
                    var happyGhast = new LivingEntity(EntityType.HAPPY_GHAST);
                    // Appelle une méthode
                    happyGhast.setNoGravity(true);
                    // Appelle une méthode
                    happyGhast.setBodyEquipment(ItemStack.of(Material.GREEN_HARNESS));
                    // Appelle une méthode
                    happyGhast.setInstance(player.getInstance(), new Pos(10, 43, 5, 45, 0));

                    // Appelle une méthode
                    var copperGolem = new LivingEntity(EntityType.COPPER_GOLEM);
                    // Appelle une méthode
                    copperGolem.setNoGravity(true);
                    // Appelle une méthode
                    copperGolem.setItemInMainHand(ItemStack.of(Material.STICK));
                    // Appelle une méthode
                    ((CopperGolemMeta) copperGolem.getEntityMeta()).setState(CopperGolemMeta.State.GETTING_ITEM);
                    // Appelle une méthode
                    copperGolem.setInstance(player.getInstance(), new Pos(-10, 40, 5, -133, 0));

                    // Appelle une méthode
                    player.getInstance().setBlock(new Vec(-12, 40, 5), Block.WEATHERED_COPPER_GOLEM_STATUE.withProperty("copper_golem_pose", "star"));

                    // Instruction de code
                    player.sendPacket(new TrackedWaypointPacket(TrackedWaypointPacket.Operation.TRACK, new TrackedWaypointPacket.Waypoint(
                            // Instruction de code
                            Either.left(happyGhast.getUuid()),
                            // Instruction de code
                            TrackedWaypointPacket.Icon.DEFAULT,
                            // Crée un nouvel objet
                            new TrackedWaypointPacket.Target.Vec3i(happyGhast.getPosition())
                    // Instruction de code
                    )));

                    // Appelle une méthode
                    var playerEntity = new PlayerEntity();
                    // Appelle une méthode
                    playerEntity.setInstance(player.getInstance(), new Pos(-2.5, 40, 6.7, -163, 0));
                    // Instruction de code
                    player.sendPacket(new TrackedWaypointPacket(TrackedWaypointPacket.Operation.TRACK, new TrackedWaypointPacket.Waypoint(
                            // Instruction de code
                            Either.left(playerEntity.getUuid()),
                            // Instruction de code
                            TrackedWaypointPacket.Icon.DEFAULT,
                            // Crée un nouvel objet
                            new TrackedWaypointPacket.Target.Vec3i(playerEntity.getPosition())
                    // Instruction de code
                    )));

                    // Appelle une méthode
                    var mannequinEntity = new LivingEntity(EntityType.MANNEQUIN);
                    // Appelle une méthode
                    mannequinEntity.setNoGravity(true);
                    // Appelle une méthode
                    var mannequinMeta = (MannequinMeta) mannequinEntity.getEntityMeta();
                    // Appelle une méthode
                    mannequinEntity.set(DataComponents.CUSTOM_NAME, Component.text("Minestom"));
                    // Appelle une méthode
                    mannequinMeta.setCustomNameVisible(true);
                    // Appelle une méthode
                    mannequinMeta.setProfile(new ResolvableProfile(new ResolvableProfile.Partial("Minestom", null, List.of())));
                    // Appelle une méthode
                    mannequinMeta.setImmovable(true);
                    // Appelle une méthode
                    mannequinMeta.setDescription(Component.text("npc"));
                    // Appelle une méthode
                    mannequinEntity.setInstance(player.getInstance(), new Pos(-4, 40, 6, -131, 0));
                    // Instruction de code
                    mannequinEntity.setItemInMainHand(ItemStack.of(Material.PLAYER_HEAD).with(DataComponents.PROFILE,
                            // Crée un nouvel objet
                            new ResolvableProfile(new ResolvableProfile.Partial("Minestom", null, List.of()))));
                    // Instruction de code
                    player.sendPacket(new TrackedWaypointPacket(TrackedWaypointPacket.Operation.TRACK, new TrackedWaypointPacket.Waypoint(
                            // Instruction de code
                            Either.left(mannequinEntity.getUuid()),
                            // Instruction de code
                            TrackedWaypointPacket.Icon.DEFAULT,
                            // Crée un nouvel objet
                            new TrackedWaypointPacket.Target.Vec3i(mannequinEntity.getPosition())
                    // Instruction de code
                    )));
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerGameModeRequestEvent.class, event -> {
                // Appelle une méthode
                final Player player = event.getPlayer();
                // Embranchement : vérifie une condition
                if (player.getPermissionLevel() >= 2) {
                    // Appelle une méthode
                    player.setGameMode(event.getRequestedGameMode());
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerChatEvent.class, event -> {
                // Affecte une valeur
                var dialog = new Dialog.MultiAction(
                        // Crée un nouvel objet
                        new DialogMetadata(
                                // Instruction de code
                                Component.text("Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?").hoverEvent(HoverEvent.showText(Component.text("Hover text here"))),
                                // Instruction de code
                                null, true, false,
                                // Instruction de code
                                DialogAfterAction.CLOSE,
                                // Instruction de code
                                List.of(
                                        // Crée un nouvel objet
                                        new DialogBody.PlainMessage(Component.text("plain message here").hoverEvent(HoverEvent.showText(Component.text("Hover text here"))), DialogBody.PlainMessage.DEFAULT_WIDTH),
                                        // Crée un nouvel objet
                                        new DialogBody.Item(ItemStack.of(Material.DIAMOND, 5),
                                                // Crée un nouvel objet
                                                new DialogBody.PlainMessage(Component.text("item message"), DialogBody.PlainMessage.DEFAULT_WIDTH),
                                                // Instruction de code
                                                false, true, 16, 16)
                                // Fin d'un bloc/d'une expression
                                ),
                                // Instruction de code
                                List.of(
                                        // Crée un nouvel objet
                                        new DialogInput.Text("text", DialogInput.DEFAULT_WIDTH * 2, Component.text("Enter some text")
                                                // Instruction de code
                                                .hoverEvent(HoverEvent.showText(Component.text("Hover text here"))), true, "", Integer.MAX_VALUE, new DialogInput.Text.Multiline(15, null)),
                                        // Crée un nouvel objet
                                        new DialogInput.Boolean("bool", Component.text("Checkbox"), false, "true", "false"),
                                        // Crée un nouvel objet
                                        new DialogInput.SingleOption("single_option", DialogInput.DEFAULT_WIDTH, List.of(
                                                // Crée un nouvel objet
                                                new DialogInput.SingleOption.Option("option1", Component.text("Option 1"), true),
                                                // Crée un nouvel objet
                                                new DialogInput.SingleOption.Option("option2", Component.text("Option 2"), false),
                                                // Crée un nouvel objet
                                                new DialogInput.SingleOption.Option("option3", Component.text("Option 3"), false)
                                        // Instruction de code
                                        ), Component.text("Single option"), true),
                                        // Crée un nouvel objet
                                        new DialogInput.NumberRange("number_range", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Instruction de code
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Crée un nouvel objet
                                        new DialogInput.NumberRange("number_r2ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Instruction de code
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Crée un nouvel objet
                                        new DialogInput.NumberRange("number_r3ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Instruction de code
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Crée un nouvel objet
                                        new DialogInput.NumberRange("number_r4ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Instruction de code
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Crée un nouvel objet
                                        new DialogInput.NumberRange("number_r5ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Instruction de code
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Crée un nouvel objet
                                        new DialogInput.NumberRange("number_r6ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Instruction de code
                                                "options.generic_value", 0, 500, 250f, 1f)
                                // Fin d'un bloc/d'une expression
                                )
                        // Fin d'un bloc/d'une expression
                        ),
                        // Instruction de code
                        List.of(
                                // Crée un nouvel objet
                                new DialogActionButton(Component.text("Done"), null, DialogActionButton.DEFAULT_WIDTH, new DialogAction.DynamicCustom(Key.key("done_action"), null)),
                                // Crée un nouvel objet
                                new DialogActionButton(Component.text("Done"), null, DialogActionButton.DEFAULT_WIDTH, null)
                        // Fin d'un bloc/d'une expression
                        ),
                        // Instruction de code
                        null, 2
                // Fin d'un bloc/d'une expression
                );

                // Appelle une méthode
                event.getPlayer().sendMessage(Component.text("Click for dialog!").clickEvent(ClickEvent.showDialog(dialog)));
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerCustomClickEvent.class, event -> {
                // Affecte une valeur
                String payload = "null";
                // Embranchement : vérifie une condition
                if (event.getPayload() != null) {
                    // Gestion des exceptions
                    try {
                        // Appelle une méthode
                        payload = MinestomAdventure.tagStringIO().asString(event.getPayload());
                    // Début d'une méthode/d'un bloc
                    } catch (IOException e) {
                        // Lève une exception
                        throw new RuntimeException(e);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                System.out.println(event.getKey() + " -> " + payload);
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerPacketOutEvent.class, event -> {
                //System.out.println("out " + event.getPacket().getClass().getSimpleName());
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerPacketEvent.class, event -> {

                //System.out.println("in " + event.getPacket().getClass().getSimpleName());
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerBlockBreakEvent.class, event -> {
                // Appelle une méthode
                var instance = event.getInstance();
                // Appelle une méthode
                var block = event.getBlock();
                // Appelle une méthode
                var pos = event.getBlockPosition();
                // Embranchement : vérifie une condition
                if (block.getProperty("part") == null || block.getProperty("facing") == null) return;
                // Appelle une méthode
                var isHead = "head".equals(block.getProperty("part"));
                // Appelle une méthode
                var facing = BlockFace.valueOf(block.getProperty("facing").toUpperCase());
                // Appelle une méthode
                var other = (isHead ? pos.add(facing.getOppositeFace().toDirection().vec().asPos()) : pos.add(facing.toDirection().vec().asPos()));
                // Appelle une méthode
                var otherBlock = instance.getBlock(other);
                // Embranchement : vérifie une condition
                if (otherBlock.id() == block.id()) {
                    // Appelle une méthode
                    instance.setBlock(other, Block.AIR);
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerBlockInteractEvent.class, event -> {
                // Appelle une méthode
                var player = event.getPlayer();
                // Appelle une méthode
                var instance = event.getInstance();
                // Appelle une méthode
                var block = event.getBlock();
                // Embranchement : vérifie une condition
                if (event.getBlock().key().asMinimalString().endsWith("_bed")) {
                    // Appelle une méthode
                    var pos = event.getBlockPosition();
                    // Embranchement : vérifie une condition
                    if (block.getProperty("part") == null || block.getProperty("facing") == null) return;
                    // Appelle une méthode
                    var isHead = "head".equals(block.getProperty("part"));
                    // Appelle une méthode
                    var facing = BlockFace.valueOf(block.getProperty("facing").toUpperCase());
                    // Appelle une méthode
                    var other = (isHead ? pos.add(facing.getOppositeFace().toDirection().vec().asPos()) : pos.add(facing.toDirection().vec().asPos()));
                    // Appelle une méthode
                    var otherBlock = instance.getBlock(other);
                    // Embranchement : vérifie une condition
                    if (otherBlock.id() == block.id()) {
                        // Appelle une méthode
                        player.setVelocity(Vec.ZERO);
                        // Appelle une méthode
                        player.swingMainHand();
                        // Appelle une méthode
                        player.enterBed((isHead ? pos : other));
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerLeaveBedEvent.class, event -> {
                // Appelle une méthode
                var player = event.getPlayer();
                // Appelle une méthode
                boolean snooze = ThreadLocalRandom.current().nextFloat() < 0.7f;
                // Embranchement : vérifie une condition
                if (snooze) {
                    // Appelle une méthode
                    event.setCancelled(true);
                    // Appelle une méthode
                    player.playSound(Sound.sound(SoundEvent.ENTITY_ALLAY_ITEM_THROWN, Sound.Source.PLAYER, 1f, 0.6f));
                    // Appelle une méthode
                    player.sendActionBar(Component.text("I'm too tired to stand up!"));
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    player.sendActionBar(Component.empty());
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerUseItemOnBlockEvent.class, event -> {
                // Embranchement : vérifie une condition
                if (event.getHand() != PlayerHand.MAIN) return;

                // Appelle une méthode
                var itemStack = event.getItemStack();
                // Appelle une méthode
                var block = event.getInstance().getBlock(event.getPosition());

                // Embranchement : vérifie une condition
                if ("false".equals(block.getProperty("waterlogged")) && itemStack.material().equals(Material.WATER_BUCKET)) {
                    // Appelle une méthode
                    block = block.withProperty("waterlogged", "true");
                // Embranchement : vérifie une condition
                } else if ("true".equals(block.getProperty("waterlogged")) && itemStack.material().equals(Material.BUCKET)) {
                    // Appelle une méthode
                    block = block.withProperty("waterlogged", "false");
                // Branche alternative de la condition
                } else return;

                // Appelle une méthode
                event.getInstance().setBlock(event.getPosition(), block);

            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerBeginItemUseEvent.class, event -> {
                // Appelle une méthode
                final Player player = event.getPlayer();
                // Appelle une méthode
                final ItemStack itemStack = event.getItemStack();
                // Appelle une méthode
                final boolean hasProjectile = !itemStack.get(DataComponents.CHARGED_PROJECTILES, List.of()).isEmpty();
                // Embranchement : vérifie une condition
                if (itemStack.material() == Material.CROSSBOW && hasProjectile) {
                    // "shoot" the arrow
                    // Appelle une méthode
                    player.setItemInHand(event.getHand(), itemStack.without(DataComponents.CHARGED_PROJECTILES));
                    // Appelle une méthode
                    event.getPlayer().sendMessage("pew pew!");
                    // Instruction de code
                    event.setItemUseDuration(0); // Do not start using the item
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerFinishItemUseEvent.class, event -> {
                // Embranchement : vérifie une condition
                if (event.getItemStack().material() == Material.APPLE) {
                    // Appelle une méthode
                    event.getPlayer().sendMessage("yummy yummy apple");
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerCancelItemUseEvent.class, event -> {
                // Appelle une méthode
                final Player player = event.getPlayer();
                // Appelle une méthode
                final ItemStack itemStack = event.getItemStack();
                // Embranchement : vérifie une condition
                if (itemStack.material() == Material.CROSSBOW && event.getUseDuration() > 25) {
                    // Appelle une méthode
                    player.setItemInHand(event.getHand(), itemStack.with(DataComponents.CHARGED_PROJECTILES, List.of(ItemStack.of(Material.ARROW))));
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerBlockInteractEvent.class, event -> {
                // Appelle une méthode
                var block = event.getBlock();
                // Appelle une méthode
                var rawOpenProp = block.getProperty("open");
                // Embranchement : vérifie une condition
                if (rawOpenProp != null) {
                    // Appelle une méthode
                    block = block.withProperty("open", String.valueOf(!Boolean.parseBoolean(rawOpenProp)));
                    // Appelle une méthode
                    event.getInstance().setBlock(event.getBlockPosition(), block);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (block.id() == Block.CRAFTING_TABLE.id()) {
                    // Appelle une méthode
                    event.getPlayer().openInventory(new Inventory(InventoryType.CRAFTING, "Crafting"));
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(CreativeInventoryActionEvent.class, event -> {
                // Embranchement : vérifie une condition
                if (event.getClickedItem().material() == Material.APPLE) {
                    // Appelle une méthode
                    event.setClickedItem(ItemStack.of(Material.GOLDEN_APPLE, event.getClickedItem().amount()));
                // Embranchement : vérifie une condition
                } else if (event.getClickedItem().material() == Material.ENCHANTED_GOLDEN_APPLE) {
                    // Appelle une méthode
                    event.setCancelled(true);
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            })
            // Début d'une méthode/d'un bloc
            .addListener(PlayerBlockPlaceEvent.class, event -> {
                // Appelle une méthode
                Block block = event.getBlock();
                // Appelle une méthode
                BlockHandler handler = block.handler();
                // Embranchement : vérifie une condition
                if (handler != null) return;
                // Appelle une méthode
                event.setBlock(event.getBlock().withHandler(MinecraftServer.getBlockManager().getHandler(block.key().asString())));
            // Instruction de code
            })
            // Instruction de code
            .addListener(PlayerEditSignEvent.class, event -> event.getLines()
                    // Instruction de code
                    .stream()
                    // Instruction de code
                    .map(Component::text)
                    // Instruction de code
                    .forEach(comp -> event.getPlayer().sendMessage(comp)))
            // Instruction de code
            .addListener(PlayerInputEvent.class, event -> event.getPlayer().sendActionBar(Component.empty()
                    // Instruction de code
                    .append(Component.keybind("key.left").color(event.isHoldingLeftKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Instruction de code
                    .append(Component.text(" "))
                    // Instruction de code
                    .append(Component.keybind("key.forward").color(event.isHoldingForwardKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Instruction de code
                    .append(Component.text(" "))
                    // Instruction de code
                    .append(Component.keybind("key.back").color(event.isHoldingBackwardKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Instruction de code
                    .append(Component.text(" "))
                    // Instruction de code
                    .append(Component.keybind("key.right").color(event.isHoldingRightKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Instruction de code
                    .append(Component.text(" | "))
                    // Instruction de code
                    .append(Component.keybind("key.jump").color(event.isHoldingJumpKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Instruction de code
                    .append(Component.text(" "))
                    // Instruction de code
                    .append(Component.keybind("key.sneak").color(event.isHoldingShiftKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Instruction de code
                    .append(Component.text(" "))
                    // Instruction de code
                    .append(Component.keybind("key.sprint").color(event.isHoldingSprintKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
            // Instruction de code
            ));

    // Début d'un bloc
    {
        // Appelle une méthode
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        // Appelle une méthode
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instanceContainer.setGenerator(unit -> {
            // Appelle une méthode
            unit.modifier().fillHeight(0, 40, Block.STONE);

            // Embranchement : vérifie une condition
            if (unit.absoluteStart().blockY() < 40 && unit.absoluteEnd().blockY() > 40) {
                // Appelle une méthode
                unit.modifier().setBlock(unit.absoluteStart().blockX(), 40, unit.absoluteStart().blockZ(), Block.TORCH);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instanceContainer.setChunkSupplier(LightingChunk::new);

        // Appelle une méthode
        var defaultClock = instanceContainer.defaultClock();
        // Appelle une méthode
        defaultClock.rate(4f);

        // Appelle une méthode
        inventory = new Inventory(InventoryType.CHEST_1_ROW, Component.text("Test inventory"));
        // Appelle une méthode
        inventory.setItemStack(3, ItemStack.of(Material.DIAMOND, 34));
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private final AtomicReference<TickMonitor> LAST_TICK = new AtomicReference<>();

    // Début d'une méthode/d'un bloc
    public void init() {
        // Appelle une méthode
        var eventHandler = MinecraftServer.getGlobalEventHandler();
        // Appelle une méthode
        eventHandler.addChild(DEMO_NODE);

        // Appelle une méthode
        eventHandler.addListener(ServerTickMonitorEvent.class, event -> LAST_TICK.set(event.getTickMonitor()));

        // Appelle une méthode
        BenchmarkManager benchmarkManager = MinecraftServer.getBenchmarkManager();
        // Début d'une méthode/d'un bloc
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            // Embranchement : vérifie une condition
            if (LAST_TICK.get() == null || MinecraftServer.getConnectionManager().getOnlinePlayerCount() == 0)
                // Renvoie une valeur à l'appelant
                return;

            // Appelle une méthode
            long ramUsage = benchmarkManager.getUsedMemory();
            // Instruction de code
            ramUsage /= 1e6; // bytes to MB

            // Appelle une méthode
            TickMonitor tickMonitor = LAST_TICK.get();
            // Affecte une valeur
            final Component header = Component.text("RAM USAGE: " + ramUsage + " MB")
                    // Instruction de code
                    .append(Component.newline())
                    // Instruction de code
                    .append(Component.text("TICK TIME: " + MathUtils.round(tickMonitor.getTickTime(), 2) + "ms"))
                    // Instruction de code
                    .append(Component.newline())
                    // Appelle une méthode
                    .append(Component.text("ACQ TIME: " + MathUtils.round(tickMonitor.getAcquisitionTime(), 2) + "ms"));
            // Appelle une méthode
            final Component footer = benchmarkManager.getCpuMonitoringMessage();
            // Appelle une méthode
            Audiences.players().sendPlayerListHeaderAndFooter(header, footer);
        // Appelle une méthode
        }).repeat(10, TimeUnit.SERVER_TICK).schedule();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static ItemStack getFoodItem(int consumeTicks) {
        // Renvoie une valeur à l'appelant
        return ItemStack.builder(Material.IRON_NUGGET)
                // Instruction de code
                .amount(64)
                // Instruction de code
                .set(DataComponents.CONSUMABLE, new Consumable(
                        // Instruction de code
                        (float) consumeTicks / 20,
                        // Instruction de code
                        ItemAnimation.EAT,
                        // Instruction de code
                        SoundEvent.BLOCK_CHAIN_STEP,
                        // Instruction de code
                        true,
                        // Crée un nouvel objet
                        new ArrayList<>()))
                // Appelle une méthode
                .build();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
