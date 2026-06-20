// Package declaration for this file
package net.minestom.demo;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.event.ClickEvent;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.text.object.ObjectContents;
// Import of a required class
import net.minestom.demo.entity.PlayerEntity;
// Import of a required class
import net.minestom.server.FeatureFlag;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.advancements.FrameType;
// Import of a required class
import net.minestom.server.advancements.Notification;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.adventure.audience.Audiences;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.dialog.*;
// Import of a required class
import net.minestom.server.entity.*;
// Import of a required class
import net.minestom.server.entity.damage.Damage;
// Import of a required class
import net.minestom.server.entity.metadata.avatar.MannequinMeta;
// Import of a required class
import net.minestom.server.entity.metadata.golem.CopperGolemMeta;
// Import of a required class
import net.minestom.server.event.Event;
// Import of a required class
import net.minestom.server.event.EventNode;
// Import of a required class
import net.minestom.server.event.entity.EntityAttackEvent;
// Import of a required class
import net.minestom.server.event.inventory.CreativeInventoryActionEvent;
// Import of a required class
import net.minestom.server.event.item.*;
// Import of a required class
import net.minestom.server.event.player.*;
// Import of a required class
import net.minestom.server.event.server.ServerTickMonitorEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.InstanceContainer;
// Import of a required class
import net.minestom.server.instance.InstanceManager;
// Import of a required class
import net.minestom.server.instance.LightingChunk;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.instance.block.predicate.BlockPredicate;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.InventoryType;
// Import of a required class
import net.minestom.server.inventory.PlayerInventory;
// Import of a required class
import net.minestom.server.item.ItemAnimation;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.BlockPredicates;
// Import of a required class
import net.minestom.server.item.component.Consumable;
// Import of a required class
import net.minestom.server.monitoring.BenchmarkManager;
// Import of a required class
import net.minestom.server.monitoring.TickMonitor;
// Import of a required class
import net.minestom.server.network.packet.server.common.CustomReportDetailsPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.ServerLinksPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.TrackedWaypointPacket;
// Import of a required class
import net.minestom.server.network.player.ResolvableProfile;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.concurrent.ThreadLocalRandom;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Type declaration (class/interface/enum/record)
public class PlayerInit {

    // Code statement
    private final Inventory inventory;

    // Assigns a value
    private final EventNode<Event> DEMO_NODE = EventNode.all("demo")
            // Start of a method/block
            .addListener(EntityAttackEvent.class, event -> {
                // Calls a method
                final Entity source = event.getEntity();
                // Calls a method
                final Entity entity = event.getTarget();

                // Calls a method
                entity.takeKnockback(0.4f, Math.sin(source.getPosition().yaw() * 0.017453292), -Math.cos(source.getPosition().yaw() * 0.017453292));

                // Branch: checks a condition
                if (entity instanceof Player target) {
                    // Calls a method
                    target.damage(Damage.fromEntity(source, 5));
                // End of a block/expression
                }

                // Branch: checks a condition
                if (source instanceof Player) {
                    // Calls a method
                    ((Player) source).sendMessage("You attacked something!");
                // End of a block/expression
                }
            // Code statement
            })
            // Code statement
            .addListener(PlayerDeathEvent.class, event -> event.setChatMessage(Component.text("custom death message")))
            // Start of a method/block
            .addListener(PickupItemEvent.class, event -> {
                // Calls a method
                final Entity entity = event.getLivingEntity();
                // Branch: checks a condition
                if (entity instanceof Player) {
                    // Cancel event if player does not have enough inventory space
                    // Calls a method
                    final ItemStack itemStack = event.getItemEntity().getItemStack();
                    // Calls a method
                    event.setCancelled(!((Player) entity).getInventory().addItemStack(itemStack));
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(ItemDropEvent.class, event -> {
                // Calls a method
                final Player player = event.getPlayer();
                // Calls a method
                ItemStack droppedItem = event.getItemStack();

                // Calls a method
                Pos playerPos = player.getPosition();
                // Calls a method
                ItemEntity itemEntity = new ItemEntity(droppedItem);
                // Calls a method
                itemEntity.setPickupDelay(Duration.of(500, TimeUnit.MILLISECOND));
                // Calls a method
                itemEntity.setInstance(player.getInstance(), playerPos.withY(y -> y + 1.5));
                // Calls a method
                Vec velocity = playerPos.direction().mul(6);
                // Calls a method
                itemEntity.setVelocity(velocity);
            // Code statement
            })
            // Code statement
            .addListener(PlayerDisconnectEvent.class, event -> System.out.println("DISCONNECTION " + event.getPlayer().getUsername()))
            // Start of a method/block
            .addListener(AsyncPlayerConfigurationEvent.class, event -> {
                // Calls a method
                final Player player = event.getPlayer();

                // Show off adding and removing feature flags
                // Code statement
                event.removeFeatureFlag(FeatureFlag.TRADE_REBALANCE); // not enabled by default, just removed for demonstration

                // Calls a method
                var instances = MinecraftServer.getInstanceManager().getInstances();
                // Calls a method
                Instance instance = instances.stream().skip(new Random().nextInt(instances.size())).findFirst().orElse(null);
                // Calls a method
                event.setSpawningInstance(instance);
                // Calls a method
                int x = Math.abs(ThreadLocalRandom.current().nextInt()) % 500 - 250;
                // Calls a method
                int z = Math.abs(ThreadLocalRandom.current().nextInt()) % 500 - 250;
                // Calls a method
                player.setRespawnPoint(new Pos(0, 40f, 0));
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerSpawnEvent.class, event -> {
                // Calls a method
                final Player player = event.getPlayer();
                // Calls a method
                player.setGameMode(GameMode.CREATIVE);
                // Calls a method
                player.setPermissionLevel(4);

                // Code statement
                player.sendMessage(Component.text("click me for less health ")
                        // Code statement
                        .clickEvent(ClickEvent.runCommand("health set 2"))
                        // Code statement
                        .append(Component.object(ObjectContents.sprite(Key.key("block/stone"))))
                        // Calls a method
                        .append(Component.object(ObjectContents.playerHead("Minestom"))));
                // Assigns a value
                ItemStack itemStack = ItemStack.builder(Material.STONE)
                        // Code statement
                        .amount(64)
                        // Code statement
                        .set(DataComponents.CAN_PLACE_ON, new BlockPredicates(new BlockPredicate(Block.STONE)))
                        // Code statement
                        .set(DataComponents.CAN_BREAK, new BlockPredicates(new BlockPredicate(Block.DIAMOND_ORE)))
                        // Calls a method
                        .build();
                // Calls a method
                player.getInventory().addItemStack(itemStack);

                // Code statement
                player.sendPacket(new CustomReportDetailsPacket(Map.of(
                        // Code statement
                        "hello", "world"
                // Code statement
                )));

                // Code statement
                player.sendPacket(new ServerLinksPacket(
                        // Creates a new object
                        new ServerLinksPacket.Entry(ServerLinksPacket.KnownLinkType.NEWS, "https://minestom.net"),
                        // Creates a new object
                        new ServerLinksPacket.Entry(ServerLinksPacket.KnownLinkType.BUG_REPORT, "https://minestom.net"),
                        // Creates a new object
                        new ServerLinksPacket.Entry(Component.text("Hello world!"), "https://minestom.net")
                // Code statement
                ));

                // TODO(1.21.2): Handle bundle slot selection
                // Assigns a value
                ItemStack bundle = ItemStack.builder(Material.BUNDLE)
                        // Code statement
                        .set(DataComponents.BUNDLE_CONTENTS, List.of(
                                // Code statement
                                ItemStack.of(Material.DIAMOND, 5),
                                // Code statement
                                ItemStack.of(Material.RABBIT_FOOT, 5)
                        // Code statement
                        ))
                        // Calls a method
                        .build();
                // Calls a method
                player.getInventory().addItemStack(bundle);

                // Calls a method
                PlayerInventory inventory = event.getPlayer().getInventory();
                // Calls a method
                inventory.addItemStack(getFoodItem(20));
                // Calls a method
                inventory.addItemStack(ItemStack.of(Material.PURPLE_BED));

                // Branch: checks a condition
                if (event.isFirstSpawn()) {
                    // Code statement
                    event.getPlayer().sendNotification(new Notification(
                            // Code statement
                            Component.text("Welcome!"),
                            // Code statement
                            FrameType.TASK,
                            // Code statement
                            Material.IRON_SWORD
                    // Code statement
                    ));

                    // Calls a method
                    player.playSound(Sound.sound(SoundEvent.ENTITY_EXPERIENCE_ORB_PICKUP, Sound.Source.PLAYER, 0.5f, 1f));

                    // Calls a method
                    var happyGhast = new LivingEntity(EntityType.HAPPY_GHAST);
                    // Calls a method
                    happyGhast.setNoGravity(true);
                    // Calls a method
                    happyGhast.setBodyEquipment(ItemStack.of(Material.GREEN_HARNESS));
                    // Calls a method
                    happyGhast.setInstance(player.getInstance(), new Pos(10, 43, 5, 45, 0));

                    // Calls a method
                    var copperGolem = new LivingEntity(EntityType.COPPER_GOLEM);
                    // Calls a method
                    copperGolem.setNoGravity(true);
                    // Calls a method
                    copperGolem.setItemInMainHand(ItemStack.of(Material.STICK));
                    // Calls a method
                    ((CopperGolemMeta) copperGolem.getEntityMeta()).setState(CopperGolemMeta.State.GETTING_ITEM);
                    // Calls a method
                    copperGolem.setInstance(player.getInstance(), new Pos(-10, 40, 5, -133, 0));

                    // Calls a method
                    player.getInstance().setBlock(new Vec(-12, 40, 5), Block.WEATHERED_COPPER_GOLEM_STATUE.withProperty("copper_golem_pose", "star"));

                    // Code statement
                    player.sendPacket(new TrackedWaypointPacket(TrackedWaypointPacket.Operation.TRACK, new TrackedWaypointPacket.Waypoint(
                            // Code statement
                            Either.left(happyGhast.getUuid()),
                            // Code statement
                            TrackedWaypointPacket.Icon.DEFAULT,
                            // Creates a new object
                            new TrackedWaypointPacket.Target.Vec3i(happyGhast.getPosition())
                    // Code statement
                    )));

                    // Calls a method
                    var playerEntity = new PlayerEntity();
                    // Calls a method
                    playerEntity.setInstance(player.getInstance(), new Pos(-2.5, 40, 6.7, -163, 0));
                    // Code statement
                    player.sendPacket(new TrackedWaypointPacket(TrackedWaypointPacket.Operation.TRACK, new TrackedWaypointPacket.Waypoint(
                            // Code statement
                            Either.left(playerEntity.getUuid()),
                            // Code statement
                            TrackedWaypointPacket.Icon.DEFAULT,
                            // Creates a new object
                            new TrackedWaypointPacket.Target.Vec3i(playerEntity.getPosition())
                    // Code statement
                    )));

                    // Calls a method
                    var mannequinEntity = new LivingEntity(EntityType.MANNEQUIN);
                    // Calls a method
                    mannequinEntity.setNoGravity(true);
                    // Calls a method
                    var mannequinMeta = (MannequinMeta) mannequinEntity.getEntityMeta();
                    // Calls a method
                    mannequinEntity.set(DataComponents.CUSTOM_NAME, Component.text("Minestom"));
                    // Calls a method
                    mannequinMeta.setCustomNameVisible(true);
                    // Calls a method
                    mannequinMeta.setProfile(new ResolvableProfile(new ResolvableProfile.Partial("Minestom", null, List.of())));
                    // Calls a method
                    mannequinMeta.setImmovable(true);
                    // Calls a method
                    mannequinMeta.setDescription(Component.text("npc"));
                    // Calls a method
                    mannequinEntity.setInstance(player.getInstance(), new Pos(-4, 40, 6, -131, 0));
                    // Code statement
                    mannequinEntity.setItemInMainHand(ItemStack.of(Material.PLAYER_HEAD).with(DataComponents.PROFILE,
                            // Creates a new object
                            new ResolvableProfile(new ResolvableProfile.Partial("Minestom", null, List.of()))));
                    // Code statement
                    player.sendPacket(new TrackedWaypointPacket(TrackedWaypointPacket.Operation.TRACK, new TrackedWaypointPacket.Waypoint(
                            // Code statement
                            Either.left(mannequinEntity.getUuid()),
                            // Code statement
                            TrackedWaypointPacket.Icon.DEFAULT,
                            // Creates a new object
                            new TrackedWaypointPacket.Target.Vec3i(mannequinEntity.getPosition())
                    // Code statement
                    )));
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerGameModeRequestEvent.class, event -> {
                // Calls a method
                final Player player = event.getPlayer();
                // Branch: checks a condition
                if (player.getPermissionLevel() >= 2) {
                    // Calls a method
                    player.setGameMode(event.getRequestedGameMode());
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerChatEvent.class, event -> {
                // Assigns a value
                var dialog = new Dialog.MultiAction(
                        // Creates a new object
                        new DialogMetadata(
                                // Code statement
                                Component.text("Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?Are you sure you want to confirm?").hoverEvent(HoverEvent.showText(Component.text("Hover text here"))),
                                // Code statement
                                null, true, false,
                                // Code statement
                                DialogAfterAction.CLOSE,
                                // Code statement
                                List.of(
                                        // Creates a new object
                                        new DialogBody.PlainMessage(Component.text("plain message here").hoverEvent(HoverEvent.showText(Component.text("Hover text here"))), DialogBody.PlainMessage.DEFAULT_WIDTH),
                                        // Creates a new object
                                        new DialogBody.Item(ItemStack.of(Material.DIAMOND, 5),
                                                // Creates a new object
                                                new DialogBody.PlainMessage(Component.text("item message"), DialogBody.PlainMessage.DEFAULT_WIDTH),
                                                // Code statement
                                                false, true, 16, 16)
                                // End of a block/expression
                                ),
                                // Code statement
                                List.of(
                                        // Creates a new object
                                        new DialogInput.Text("text", DialogInput.DEFAULT_WIDTH * 2, Component.text("Enter some text")
                                                // Code statement
                                                .hoverEvent(HoverEvent.showText(Component.text("Hover text here"))), true, "", Integer.MAX_VALUE, new DialogInput.Text.Multiline(15, null)),
                                        // Creates a new object
                                        new DialogInput.Boolean("bool", Component.text("Checkbox"), false, "true", "false"),
                                        // Creates a new object
                                        new DialogInput.SingleOption("single_option", DialogInput.DEFAULT_WIDTH, List.of(
                                                // Creates a new object
                                                new DialogInput.SingleOption.Option("option1", Component.text("Option 1"), true),
                                                // Creates a new object
                                                new DialogInput.SingleOption.Option("option2", Component.text("Option 2"), false),
                                                // Creates a new object
                                                new DialogInput.SingleOption.Option("option3", Component.text("Option 3"), false)
                                        // Code statement
                                        ), Component.text("Single option"), true),
                                        // Creates a new object
                                        new DialogInput.NumberRange("number_range", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Code statement
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Creates a new object
                                        new DialogInput.NumberRange("number_r2ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Code statement
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Creates a new object
                                        new DialogInput.NumberRange("number_r3ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Code statement
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Creates a new object
                                        new DialogInput.NumberRange("number_r4ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Code statement
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Creates a new object
                                        new DialogInput.NumberRange("number_r5ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Code statement
                                                "options.generic_value", 0, 500, 250f, 1f),
                                        // Creates a new object
                                        new DialogInput.NumberRange("number_r6ange", DialogInput.DEFAULT_WIDTH, Component.text("Number range"),
                                                // Code statement
                                                "options.generic_value", 0, 500, 250f, 1f)
                                // End of a block/expression
                                )
                        // End of a block/expression
                        ),
                        // Code statement
                        List.of(
                                // Creates a new object
                                new DialogActionButton(Component.text("Done"), null, DialogActionButton.DEFAULT_WIDTH, new DialogAction.DynamicCustom(Key.key("done_action"), null)),
                                // Creates a new object
                                new DialogActionButton(Component.text("Done"), null, DialogActionButton.DEFAULT_WIDTH, null)
                        // End of a block/expression
                        ),
                        // Code statement
                        null, 2
                // End of a block/expression
                );

                // Calls a method
                event.getPlayer().sendMessage(Component.text("Click for dialog!").clickEvent(ClickEvent.showDialog(dialog)));
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerCustomClickEvent.class, event -> {
                // Assigns a value
                String payload = "null";
                // Branch: checks a condition
                if (event.getPayload() != null) {
                    // Exception handling
                    try {
                        // Calls a method
                        payload = MinestomAdventure.tagStringIO().asString(event.getPayload());
                    // Start of a method/block
                    } catch (IOException e) {
                        // Throws an exception
                        throw new RuntimeException(e);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Calls a method
                System.out.println(event.getKey() + " -> " + payload);
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerPacketOutEvent.class, event -> {
                //System.out.println("out " + event.getPacket().getClass().getSimpleName());
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerPacketEvent.class, event -> {

                //System.out.println("in " + event.getPacket().getClass().getSimpleName());
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerBlockBreakEvent.class, event -> {
                // Calls a method
                var instance = event.getInstance();
                // Calls a method
                var block = event.getBlock();
                // Calls a method
                var pos = event.getBlockPosition();
                // Branch: checks a condition
                if (block.getProperty("part") == null || block.getProperty("facing") == null) return;
                // Calls a method
                var isHead = "head".equals(block.getProperty("part"));
                // Calls a method
                var facing = BlockFace.valueOf(block.getProperty("facing").toUpperCase());
                // Calls a method
                var other = (isHead ? pos.add(facing.getOppositeFace().toDirection().vec().asPos()) : pos.add(facing.toDirection().vec().asPos()));
                // Calls a method
                var otherBlock = instance.getBlock(other);
                // Branch: checks a condition
                if (otherBlock.id() == block.id()) {
                    // Calls a method
                    instance.setBlock(other, Block.AIR);
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerBlockInteractEvent.class, event -> {
                // Calls a method
                var player = event.getPlayer();
                // Calls a method
                var instance = event.getInstance();
                // Calls a method
                var block = event.getBlock();
                // Branch: checks a condition
                if (event.getBlock().key().asMinimalString().endsWith("_bed")) {
                    // Calls a method
                    var pos = event.getBlockPosition();
                    // Branch: checks a condition
                    if (block.getProperty("part") == null || block.getProperty("facing") == null) return;
                    // Calls a method
                    var isHead = "head".equals(block.getProperty("part"));
                    // Calls a method
                    var facing = BlockFace.valueOf(block.getProperty("facing").toUpperCase());
                    // Calls a method
                    var other = (isHead ? pos.add(facing.getOppositeFace().toDirection().vec().asPos()) : pos.add(facing.toDirection().vec().asPos()));
                    // Calls a method
                    var otherBlock = instance.getBlock(other);
                    // Branch: checks a condition
                    if (otherBlock.id() == block.id()) {
                        // Calls a method
                        player.setVelocity(Vec.ZERO);
                        // Calls a method
                        player.swingMainHand();
                        // Calls a method
                        player.enterBed((isHead ? pos : other));
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerLeaveBedEvent.class, event -> {
                // Calls a method
                var player = event.getPlayer();
                // Calls a method
                boolean snooze = ThreadLocalRandom.current().nextFloat() < 0.7f;
                // Branch: checks a condition
                if (snooze) {
                    // Calls a method
                    event.setCancelled(true);
                    // Calls a method
                    player.playSound(Sound.sound(SoundEvent.ENTITY_ALLAY_ITEM_THROWN, Sound.Source.PLAYER, 1f, 0.6f));
                    // Calls a method
                    player.sendActionBar(Component.text("I'm too tired to stand up!"));
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    player.sendActionBar(Component.empty());
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerUseItemOnBlockEvent.class, event -> {
                // Branch: checks a condition
                if (event.getHand() != PlayerHand.MAIN) return;

                // Calls a method
                var itemStack = event.getItemStack();
                // Calls a method
                var block = event.getInstance().getBlock(event.getPosition());

                // Branch: checks a condition
                if ("false".equals(block.getProperty("waterlogged")) && itemStack.material().equals(Material.WATER_BUCKET)) {
                    // Calls a method
                    block = block.withProperty("waterlogged", "true");
                // Branch: checks a condition
                } else if ("true".equals(block.getProperty("waterlogged")) && itemStack.material().equals(Material.BUCKET)) {
                    // Calls a method
                    block = block.withProperty("waterlogged", "false");
                // Alternative branch of the condition
                } else return;

                // Calls a method
                event.getInstance().setBlock(event.getPosition(), block);

            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerBeginItemUseEvent.class, event -> {
                // Calls a method
                final Player player = event.getPlayer();
                // Calls a method
                final ItemStack itemStack = event.getItemStack();
                // Calls a method
                final boolean hasProjectile = !itemStack.get(DataComponents.CHARGED_PROJECTILES, List.of()).isEmpty();
                // Branch: checks a condition
                if (itemStack.material() == Material.CROSSBOW && hasProjectile) {
                    // "shoot" the arrow
                    // Calls a method
                    player.setItemInHand(event.getHand(), itemStack.without(DataComponents.CHARGED_PROJECTILES));
                    // Calls a method
                    event.getPlayer().sendMessage("pew pew!");
                    // Code statement
                    event.setItemUseDuration(0); // Do not start using the item
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerFinishItemUseEvent.class, event -> {
                // Branch: checks a condition
                if (event.getItemStack().material() == Material.APPLE) {
                    // Calls a method
                    event.getPlayer().sendMessage("yummy yummy apple");
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerCancelItemUseEvent.class, event -> {
                // Calls a method
                final Player player = event.getPlayer();
                // Calls a method
                final ItemStack itemStack = event.getItemStack();
                // Branch: checks a condition
                if (itemStack.material() == Material.CROSSBOW && event.getUseDuration() > 25) {
                    // Calls a method
                    player.setItemInHand(event.getHand(), itemStack.with(DataComponents.CHARGED_PROJECTILES, List.of(ItemStack.of(Material.ARROW))));
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerBlockInteractEvent.class, event -> {
                // Calls a method
                var block = event.getBlock();
                // Calls a method
                var rawOpenProp = block.getProperty("open");
                // Branch: checks a condition
                if (rawOpenProp != null) {
                    // Calls a method
                    block = block.withProperty("open", String.valueOf(!Boolean.parseBoolean(rawOpenProp)));
                    // Calls a method
                    event.getInstance().setBlock(event.getBlockPosition(), block);
                // End of a block/expression
                }

                // Branch: checks a condition
                if (block.id() == Block.CRAFTING_TABLE.id()) {
                    // Calls a method
                    event.getPlayer().openInventory(new Inventory(InventoryType.CRAFTING, "Crafting"));
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(CreativeInventoryActionEvent.class, event -> {
                // Branch: checks a condition
                if (event.getClickedItem().material() == Material.APPLE) {
                    // Calls a method
                    event.setClickedItem(ItemStack.of(Material.GOLDEN_APPLE, event.getClickedItem().amount()));
                // Branch: checks a condition
                } else if (event.getClickedItem().material() == Material.ENCHANTED_GOLDEN_APPLE) {
                    // Calls a method
                    event.setCancelled(true);
                // End of a block/expression
                }
            // Code statement
            })
            // Start of a method/block
            .addListener(PlayerBlockPlaceEvent.class, event -> {
                // Calls a method
                Block block = event.getBlock();
                // Calls a method
                BlockHandler handler = block.handler();
                // Branch: checks a condition
                if (handler != null) return;
                // Calls a method
                event.setBlock(event.getBlock().withHandler(MinecraftServer.getBlockManager().getHandler(block.key().asString())));
            // Code statement
            })
            // Code statement
            .addListener(PlayerEditSignEvent.class, event -> event.getLines()
                    // Code statement
                    .stream()
                    // Code statement
                    .map(Component::text)
                    // Code statement
                    .forEach(comp -> event.getPlayer().sendMessage(comp)))
            // Code statement
            .addListener(PlayerInputEvent.class, event -> event.getPlayer().sendActionBar(Component.empty()
                    // Code statement
                    .append(Component.keybind("key.left").color(event.isHoldingLeftKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Code statement
                    .append(Component.text(" "))
                    // Code statement
                    .append(Component.keybind("key.forward").color(event.isHoldingForwardKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Code statement
                    .append(Component.text(" "))
                    // Code statement
                    .append(Component.keybind("key.back").color(event.isHoldingBackwardKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Code statement
                    .append(Component.text(" "))
                    // Code statement
                    .append(Component.keybind("key.right").color(event.isHoldingRightKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Code statement
                    .append(Component.text(" | "))
                    // Code statement
                    .append(Component.keybind("key.jump").color(event.isHoldingJumpKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Code statement
                    .append(Component.text(" "))
                    // Code statement
                    .append(Component.keybind("key.sneak").color(event.isHoldingShiftKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
                    // Code statement
                    .append(Component.text(" "))
                    // Code statement
                    .append(Component.keybind("key.sprint").color(event.isHoldingSprintKey() ? NamedTextColor.GREEN : NamedTextColor.RED))
            // Code statement
            ));

    // Start of a block
    {
        // Calls a method
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        // Calls a method
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        // Start of a method/block
        instanceContainer.setGenerator(unit -> {
            // Calls a method
            unit.modifier().fillHeight(0, 40, Block.STONE);

            // Branch: checks a condition
            if (unit.absoluteStart().blockY() < 40 && unit.absoluteEnd().blockY() > 40) {
                // Calls a method
                unit.modifier().setBlock(unit.absoluteStart().blockX(), 40, unit.absoluteStart().blockZ(), Block.TORCH);
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Calls a method
        instanceContainer.setChunkSupplier(LightingChunk::new);

        // Calls a method
        var defaultClock = instanceContainer.defaultClock();
        // Calls a method
        defaultClock.rate(4f);

        // Calls a method
        inventory = new Inventory(InventoryType.CHEST_1_ROW, Component.text("Test inventory"));
        // Calls a method
        inventory.setItemStack(3, ItemStack.of(Material.DIAMOND, 34));
    // End of a block/expression
    }

    // Calls a method
    private final AtomicReference<TickMonitor> LAST_TICK = new AtomicReference<>();

    // Start of a method/block
    public void init() {
        // Calls a method
        var eventHandler = MinecraftServer.getGlobalEventHandler();
        // Calls a method
        eventHandler.addChild(DEMO_NODE);

        // Calls a method
        eventHandler.addListener(ServerTickMonitorEvent.class, event -> LAST_TICK.set(event.getTickMonitor()));

        // Calls a method
        BenchmarkManager benchmarkManager = MinecraftServer.getBenchmarkManager();
        // Start of a method/block
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            // Branch: checks a condition
            if (LAST_TICK.get() == null || MinecraftServer.getConnectionManager().getOnlinePlayerCount() == 0)
                // Returns a value to the caller
                return;

            // Calls a method
            long ramUsage = benchmarkManager.getUsedMemory();
            // Code statement
            ramUsage /= 1e6; // bytes to MB

            // Calls a method
            TickMonitor tickMonitor = LAST_TICK.get();
            // Assigns a value
            final Component header = Component.text("RAM USAGE: " + ramUsage + " MB")
                    // Code statement
                    .append(Component.newline())
                    // Code statement
                    .append(Component.text("TICK TIME: " + MathUtils.round(tickMonitor.getTickTime(), 2) + "ms"))
                    // Code statement
                    .append(Component.newline())
                    // Calls a method
                    .append(Component.text("ACQ TIME: " + MathUtils.round(tickMonitor.getAcquisitionTime(), 2) + "ms"));
            // Calls a method
            final Component footer = benchmarkManager.getCpuMonitoringMessage();
            // Calls a method
            Audiences.players().sendPlayerListHeaderAndFooter(header, footer);
        // Calls a method
        }).repeat(10, TimeUnit.SERVER_TICK).schedule();
    // End of a block/expression
    }

    // Start of a method/block
    public static ItemStack getFoodItem(int consumeTicks) {
        // Returns a value to the caller
        return ItemStack.builder(Material.IRON_NUGGET)
                // Code statement
                .amount(64)
                // Code statement
                .set(DataComponents.CONSUMABLE, new Consumable(
                        // Code statement
                        (float) consumeTicks / 20,
                        // Code statement
                        ItemAnimation.EAT,
                        // Code statement
                        SoundEvent.BLOCK_CHAIN_STEP,
                        // Code statement
                        true,
                        // Creates a new object
                        new ArrayList<>()))
                // Calls a method
                .build();
    // End of a block/expression
    }
// End of a block/expression
}
