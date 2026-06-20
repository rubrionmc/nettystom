// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.resource.ResourcePackStatus;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.text.format.Style;
// Import of a required class
import net.kyori.adventure.text.format.TextDecoration;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.advancements.AdvancementAction;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.crypto.*;
// Import of a required class
import net.minestom.server.dialog.*;
// Import of a required class
import net.minestom.server.entity.*;
// Import of a required class
import net.minestom.server.entity.damage.DamageType;
// Import of a required class
import net.minestom.server.extras.mojangAuth.MojangCrypt;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockEntityType;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.gamerule.GameRule;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.message.ChatMessageType;
// Import of a required class
import net.minestom.server.network.debug.DebugSubscription;
// Import of a required class
import net.minestom.server.network.debug.info.DebugHiveInfo;
// Import of a required class
import net.minestom.server.network.debug.info.DebugPathInfo;
// Import of a required class
import net.minestom.server.network.debug.info.DebugPoiInfo;
// Import of a required class
import net.minestom.server.network.packet.PacketParser;
// Import of a required class
import net.minestom.server.network.packet.PacketRegistry;
// Import of a required class
import net.minestom.server.network.packet.PacketVanilla;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.client.common.*;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientAcceptCodeOfConductPacket;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientSelectKnownPacksPacket;
// Import of a required class
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientEncryptionResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginPluginResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginStartPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.*;
// Import of a required class
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.*;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.*;
// Import of a required class
import net.minestom.server.network.packet.server.login.*;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.ChunkData;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.LightData;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import of a required class
import net.minestom.server.network.packet.server.status.ResponsePacket;
// Import of a required class
import net.minestom.server.network.player.ClientSettings;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import net.minestom.server.potion.Potion;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import net.minestom.server.potion.PotionType;
// Import of a required class
import net.minestom.server.recipe.Ingredient;
// Import of a required class
import net.minestom.server.recipe.RecipeBookCategory;
// Import of a required class
import net.minestom.server.recipe.RecipeProperty;
// Import of a required class
import net.minestom.server.recipe.display.RecipeDisplay;
// Import of a required class
import net.minestom.server.recipe.display.SlotDisplay;
// Import of a required class
import net.minestom.server.scoreboard.Sidebar;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.statistic.StatisticCategory;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.Rotation;
// Import of a required class
import net.minestom.server.utils.WeightedList;
// Import of a required class
import net.minestom.server.world.Difficulty;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.BeforeAll;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.Arguments;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.time.Instant;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.stream.Stream;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

/**
 * Ensures that packet can be written and read correctly.
 */
// Annotation for the following element
@EnvTest // Some packets require registries.
// Type declaration (class/interface/enum/record)
public class PacketWriteReadTest {
    // Calls a method
    private static final Map<Class<? extends ServerPacket>, Set<ServerPacket>> SERVER_PACKETS = new HashMap<>();
    // Calls a method
    private static final Map<Class<? extends ClientPacket>, Set<ClientPacket>> CLIENT_PACKETS = new HashMap<>();

    // Assigns a value
    private static final String OG = "TheMode911";
    // Calls a method
    private static final Component COMPONENT = Component.text("Hey");
    // Calls a method
    private static final Vec VEC = new Vec(5, 5, 5);
    // Calls a method
    private static final Vec BLOCK_VEC = new Vec(5, 5, 5);

    // Annotation for the following element
    @SafeVarargs
    // Start of a method/block
    private static <T extends ServerPacket> void addServerPackets(T... packets) {
        // Calls a method
        assertNotEquals(0, packets.length);
        // Calls a method
        var packetClass = packets[0].getClass();
        // Calls a method
        var set = SERVER_PACKETS.computeIfAbsent(packetClass, c -> new HashSet<>(packets.length));
        // Loop: repeats a block
        for (var packet : packets)
            // Calls a method
            assertTrue(set.add(packet), "Found duplicate server packet in %s with `%s`".formatted(packet.getClass().getSimpleName(), packet));
    // End of a block/expression
    }

    // Annotation for the following element
    @SafeVarargs
    // Start of a method/block
    private static <T extends ClientPacket> void addClientPackets(T... packets) {
        // Calls a method
        assertNotEquals(0, packets.length);
        // Calls a method
        var packetClass = packets[0].getClass();
        // Calls a method
        var set = CLIENT_PACKETS.computeIfAbsent(packetClass, c -> new HashSet<>(packets.length));
        // Loop: repeats a block
        for (var packet : packets)
            // Calls a method
            assertTrue(set.add(packet), "Found duplicate client packet in %s with `%s`".formatted(packet.getClass().getSimpleName(), packet));
    // End of a block/expression
    }

    // Annotation for the following element
    @BeforeAll
    // Start of a method/block
    public static void setupServer() {
        // Code statement
        MinecraftServer.init(); // Need some tags in here, pretty gross.

        // Handshake
        // Status
        // Calls a method
        addServerPackets(new ResponsePacket(new JsonObject().toString()));
        // Calls a method
        addServerPackets(new PingResponsePacket(5));
        // Login
        // Code statement
        addServerPackets(
                // Creates a new object
                new LoginDisconnectPacket(COMPONENT.append(Component.text(" your Disconnected!", NamedTextColor.BLUE))),
                // Creates a new object
                new LoginDisconnectPacket(COMPONENT.appendNewline().appendSpace().append(Component.text("Disconnected!", NamedTextColor.RED)))
        // End of a block/expression
        );
        // Code statement
        addServerPackets(
                // Creates a new object
                new EncryptionRequestPacket("abvcr3ujt324joi32aaa", new byte[124], new byte[65], true), // max test
                // Creates a new object
                new EncryptionRequestPacket("server", new byte[64], new byte[235], false),
                // Creates a new object
                new EncryptionRequestPacket("", new byte[54], new byte[23], true) // default
        // End of a block/expression
        );
        // Code statement
        addServerPackets(
                // Creates a new object
                new LoginSuccessPacket(new GameProfile(UUID.randomUUID(), OG)),
                // Creates a new object
                new LoginSuccessPacket(new GameProfile(UUID.randomUUID(), "APlrWith_LongNam")),
                // Creates a new object
                new LoginSuccessPacket(new GameProfile(new UUID(0, 0), "8", List.of(
                        // Creates a new object
                        new GameProfile.Property("textures", "randomtexturethatprobablyshouldbevalidated"),
                        // Creates a new object
                        new GameProfile.Property("signature", "supersigned")
                // Code statement
                )))
        // End of a block/expression
        );
        // Calls a method
        addServerPackets(new SetCompressionPacket(256), new SetCompressionPacket(0), new SetCompressionPacket(1024));
        // Code statement
        addServerPackets(
                // Creates a new object
                new LoginPluginRequestPacket(5, "id", new byte[16]),
                // Creates a new object
                new LoginPluginRequestPacket(0, "", new byte[]{1, 2, 23, 123}),
                // Creates a new object
                new LoginPluginRequestPacket(123, "id", new byte[123]),
                // Creates a new object
                new LoginPluginRequestPacket(6, "somecoolChannel", new byte[]{125, 0x76, 0x32, 0x12, 0b1111}),
                // Creates a new object
                new LoginPluginRequestPacket(Integer.MAX_VALUE, "x", new byte[0])
        // End of a block/expression
        );
        // Code statement
        addServerPackets(
                // Creates a new object
                new CookieRequestPacket("cookieKey"),
                // Creates a new object
                new CookieRequestPacket(""),
                // Creates a new object
                new CookieRequestPacket("minestom:cookie"),
                // Creates a new object
                new CookieRequestPacket("iam/cookie")
        // End of a block/expression
        );
        // Configuration
        // Code statement
        addServerPackets(new CookieRequestPacket("cookie/master")); // See above
        // Code statement
        addServerPackets(
                // Creates a new object
                new PluginMessagePacket("channel", new byte[]{1, 2, 23, 123}),
                // Creates a new object
                new PluginMessagePacket("empty", new byte[124]),
                // Creates a new object
                new PluginMessagePacket("", new byte[]{1, 2, 23, 123}),
                // Creates a new object
                new PluginMessagePacket("", new byte[0])
        // End of a block/expression
        );
        // Code statement
        addServerPackets(
                // Creates a new object
                new DisconnectPacket(COMPONENT.append(Component.text(", Your gone!", NamedTextColor.RED))),
                // Creates a new object
                new DisconnectPacket(COMPONENT.appendNewline().appendNewline().appendSpace().append(Component.text("Why", Style.style(NamedTextColor.RED, TextDecoration.UNDERLINED)))),
                // Creates a new object
                new DisconnectPacket(Component.empty())
        // End of a block/expression
        );
        // Calls a method
        addServerPackets(new FinishConfigurationPacket());
        // Code statement
        addServerPackets(
                // Creates a new object
                new KeepAlivePacket(Long.MAX_VALUE),
                // Creates a new object
                new KeepAlivePacket(0),
                // Creates a new object
                new KeepAlivePacket(Long.MIN_VALUE),
                // Creates a new object
                new KeepAlivePacket(System.currentTimeMillis())
        // End of a block/expression
        );
        // Calls a method
        addServerPackets(new PingPacket(0), new PingPacket(Integer.MAX_VALUE));
        // Calls a method
        addServerPackets(new ResetChatPacket());
        // Code statement
        addServerPackets(new RegistryDataPacket("minecraft:damage_type", List.of( //TODO maybe use a proper one?
                // Creates a new object
                new RegistryDataPacket.Entry("some_value", CompoundBinaryTag.builder().putString("hey", "john").build()),
                // Creates a new object
                new RegistryDataPacket.Entry("some_value1", CompoundBinaryTag.builder().putInt("he5y", 1).build()),
                // Creates a new object
                new RegistryDataPacket.Entry("some_value2", CompoundBinaryTag.builder().putFloat("hey2", 0.23f).build()),
                // Creates a new object
                new RegistryDataPacket.Entry("some_value3", CompoundBinaryTag.builder().putString("h2ey", "john").build()),
                // Creates a new object
                new RegistryDataPacket.Entry("some_value4", CompoundBinaryTag.builder().putBoolean("", true).build()),
                // Creates a new object
                new RegistryDataPacket.Entry("some_value5", CompoundBinaryTag.builder().putBoolean("", false).build())
        // Code statement
        )));
        // Calls a method
        addServerPackets(new ResourcePackPushPacket(new UUID(Long.MAX_VALUE, 0), "test", "test", false, Component.text("hello").append(COMPONENT)));
        // Calls a method
        addServerPackets(new ResourcePackPopPacket(new UUID(Long.MAX_VALUE, 0)), new ResourcePackPopPacket(new UUID(0, Long.MAX_VALUE)));
        // Calls a method
        addServerPackets(new CookieStorePacket("somepacket", new byte[]{1, 2, 23, 123}), new CookieStorePacket("somepacket", new byte[5120]));
        // Calls a method
        addServerPackets(new TransferPacket("test", 20000), new TransferPacket("0", 25565));
        // Calls a method
        addServerPackets(new UpdateEnabledFeaturesPacket(List.of("unvalidated", "very")));
        // Calls a method
        addServerPackets(new TagsPacket(List.of(new TagsPacket.Registry("test", List.of(new TagsPacket.Tag("#cool", new int[]{1, 2, 23, 123}))))), new TagsPacket(List.of()));
        // Calls a method
        addServerPackets(new SelectKnownPacksPacket(List.of(new SelectKnownPacksPacket.Entry("test", "id", "randomversion"))));
        // Calls a method
        addServerPackets(new CustomReportDetailsPacket(Map.of("key", "value", "key1", "value1")));
        // Calls a method
        addServerPackets(new ServerLinksPacket(new ServerLinksPacket.Entry(ServerLinksPacket.KnownLinkType.BUG_REPORT, "https://minestom.net"), new ServerLinksPacket.Entry(ServerLinksPacket.KnownLinkType.ANNOUNCEMENTS, "https://minestom.net")));
        // Calls a method
        addServerPackets(new ClearDialogPacket());
        // Code statement
        addServerPackets(new ShowDialogPacket(
                // Creates a new object
                new Dialog.MultiAction(
                        // Creates a new object
                        new DialogMetadata(COMPONENT, COMPONENT, true, false, DialogAfterAction.WAIT_FOR_RESPONSE, List.of(), List.of(new DialogInput.Text("heyt", 12, COMPONENT, true, "", 10, null))),
                        // Code statement
                        List.of(),
                        // Code statement
                        null,
                        // Code statement
                        10
                // Code statement
                )));
        // Code statement
        addServerPackets(new ShowDialogPacket(
                // Creates a new object
                new Dialog.Confirmation(
                        // Creates a new object
                        new DialogMetadata(COMPONENT, COMPONENT.append(Component.text(OG)), true, false, DialogAfterAction.WAIT_FOR_RESPONSE, List.of(), List.of(new DialogInput.Text("heyt", 12, COMPONENT, true, "", 10, null))),
                        // Creates a new object
                        new DialogActionButton(COMPONENT.appendNewline(), COMPONENT, DialogActionButton.DEFAULT_WIDTH, new DialogAction.OpenUrl("https://minestom.net")),
                        // Creates a new object
                        new DialogActionButton(COMPONENT.appendNewline(), COMPONENT, 10, new DialogAction.CopyToClipboard("https://minestom.net"))
                // Code statement
                )));
        // Calls a method
        addServerPackets(new CodeOfConductPacket("You need to be a nice person, i think?"));
        // Play
        // Calls a method
        addServerPackets(new AcknowledgeBlockChangePacket(0));
        // Calls a method
        addServerPackets(new ActionBarPacket(COMPONENT));
        // Calls a method
        addServerPackets(new AttachEntityPacket(5, 10));
        // Calls a method
        addServerPackets(new BlockActionPacket(BLOCK_VEC, (byte) 5, (byte) 5, 5));
        // Calls a method
        addServerPackets(new BlockBreakAnimationPacket(5, BLOCK_VEC, (byte) 5));
        // Calls a method
        addServerPackets(new BlockChangePacket(BLOCK_VEC, 0));
        // Calls a method
        addServerPackets(new BlockEntityDataPacket(BLOCK_VEC, BlockEntityType.SIGN, CompoundBinaryTag.builder().putString("key", "value").build()));
        // Code statement
        addServerPackets(
                // Creates a new object
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.AddAction(COMPONENT, 5f, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS, (byte) 2)),
                // Creates a new object
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.RemoveAction()),
                // Creates a new object
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateHealthAction(5f)),
                // Creates a new object
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateTitleAction(COMPONENT)),
                // Creates a new object
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateStyleAction(BossBar.Color.BLUE, BossBar.Overlay.PROGRESS)),
                // Creates a new object
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateFlagsAction((byte) 5))
        // End of a block/expression
        );
        // Calls a method
        addServerPackets(new CameraPacket(5));
        // Calls a method
        addServerPackets(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, 2));
        // Calls a method
        addServerPackets(new SystemChatPacket(COMPONENT, false));
        // Calls a method
        addServerPackets(new ClearTitlesPacket(false));
        // Calls a method
        addServerPackets(new CloseWindowPacket((byte) 2));
        // Calls a method
        addServerPackets(new CollectItemPacket(5, 5, 5));
        // Assigns a value
        var recipeDisplay = new RecipeDisplay.CraftingShapeless(
                // Code statement
                List.of(new SlotDisplay.Item(Material.STONE)),
                // Creates a new object
                new SlotDisplay.Item(Material.STONE_BRICKS),
                // Creates a new object
                new SlotDisplay.Item(Material.CRAFTING_TABLE)
        // End of a block/expression
        );
        // Calls a method
        addServerPackets(new PlaceGhostRecipePacket(0, recipeDisplay));
        // Calls a method
        addServerPackets(new DeathCombatEventPacket(5, COMPONENT));
        // Code statement
        addServerPackets(new DeclareRecipesPacket(Map.of(
                // Code statement
                RecipeProperty.SMITHING_BASE, List.of(Material.STONE),
                // Code statement
                RecipeProperty.SMITHING_TEMPLATE, List.of(Material.STONE),
                // Code statement
                RecipeProperty.SMITHING_ADDITION, List.of(Material.STONE),
                // Code statement
                RecipeProperty.FURNACE_INPUT, List.of(Material.STONE),
                // Code statement
                RecipeProperty.BLAST_FURNACE_INPUT, List.of(Material.IRON_HOE, Material.DANDELION),
                // Code statement
                RecipeProperty.SMOKER_INPUT, List.of(Material.STONE),
                // Code statement
                RecipeProperty.CAMPFIRE_INPUT, List.of(Material.STONE)),
                // Code statement
                List.of(new DeclareRecipesPacket.StonecutterRecipe(new Ingredient(Material.DIAMOND),
                        // Creates a new object
                        new SlotDisplay.ItemStack(ItemStack.of(Material.GOLD_BLOCK))))
        // Code statement
        ));
        // Code statement
        addServerPackets(new RecipeBookAddPacket(List.of(new RecipeBookAddPacket.Entry(1, recipeDisplay, null,
                // Calls a method
                RecipeBookCategory.CRAFTING_MISC, List.of(new Ingredient(Material.STONE)), true, true)), false));
        // Calls a method
        addServerPackets(new RecipeBookRemovePacket(List.of(1)));

        // Calls a method
        addServerPackets(new DestroyEntitiesPacket(List.of(5, 5, 5)));
        // Calls a method
        addServerPackets(new DisconnectPacket(COMPONENT));
        // Calls a method
        addServerPackets(new DisplayScoreboardPacket((byte) 5, "scoreboard"));
        // Calls a method
        addServerPackets(new WorldEventPacket(5, BLOCK_VEC, 5, false));
        // Calls a method
        addServerPackets(new EndCombatEventPacket(5));
        // Calls a method
        addServerPackets(new EnterCombatEventPacket());
        // Calls a method
        addServerPackets(new EntityAnimationPacket(5, EntityAnimationPacket.Animation.TAKE_DAMAGE));
        // Calls a method
        addServerPackets(new EntityEquipmentPacket(6, Map.of(EquipmentSlot.MAIN_HAND, ItemStack.of(Material.DIAMOND_SWORD))));
        // Calls a method
        addServerPackets(new EntityHeadLookPacket(5, 90f));
        // Calls a method
        addServerPackets(new EntityMetaDataPacket(5, Map.of()));
        // Calls a method
        addServerPackets(new EntityMetaDataPacket(5, Map.of(1, Metadata.VarInt(5))));
        // Calls a method
        addServerPackets(new EntityPositionAndRotationPacket(5, (short) 0, (short) 0, (short) 0, 45f, 45f, false));
        // Calls a method
        addServerPackets(new EntityPositionPacket(5, (short) 0, (short) 0, (short) 0, true));
        // Calls a method
        addServerPackets(new EntityAttributesPacket(5, List.of()));
        // Calls a method
        addServerPackets(new EntityRotationPacket(5, 45f, 45f, false));

        // Calls a method
        final PlayerSkin skin = new PlayerSkin("hh", "hh");
        // Code statement
        addServerPackets( // TODO, these test are highly dependent on the default values, which arent great.
                // Creates a new object
                new PlayerInfoUpdatePacket(
                        // Code statement
                        EnumSet.of(
                                // Code statement
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE
                        // End of a block/expression
                        ),
                        // Creates a new object
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), OG, List.of(new PlayerInfoUpdatePacket.Property("textures", skin.textures(), skin.signature())), false, 0, GameMode.CREATIVE, null, null, 0, true)
                // End of a block/expression
                ),
                // Creates a new object
                new PlayerInfoUpdatePacket(
                        // Code statement
                        EnumSet.of(
                                // Code statement
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_HAT
                        // End of a block/expression
                        ),
                        // Creates a new object
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.CREATIVE, Component.text("Not").append(Component.text(OG)), null, 0, false)
                // End of a block/expression
                ),
                // Creates a new object
                new PlayerInfoUpdatePacket(
                        // Code statement
                        EnumSet.of(
                                // Code statement
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE
                        // End of a block/expression
                        ),
                        // Creates a new object
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.SPECTATOR, null, null, 0, true)
                // End of a block/expression
                ),
                // Creates a new object
                new PlayerInfoUpdatePacket(
                        // Code statement
                        EnumSet.of(
                                // Code statement
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_LATENCY,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_HAT
                        // End of a block/expression
                        ),
                        // Creates a new object
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 20, GameMode.CREATIVE, null, null, 0, false)
                // End of a block/expression
                ),
                // Creates a new object
                new PlayerInfoUpdatePacket(
                        // Code statement
                        EnumSet.of(
                                // Code statement
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_LISTED
                        // End of a block/expression
                        ),
                        // Creates a new object
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), true, 0, GameMode.SURVIVAL, null, null, 0, true)
                // End of a block/expression
                ),
                // Creates a new object
                new PlayerInfoUpdatePacket(
                        // Code statement
                        EnumSet.of(
                                // Code statement
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_LIST_ORDER,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_HAT
                        // End of a block/expression
                        ),
                        // Creates a new object
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.CREATIVE, null, null, 42, false)
                // End of a block/expression
                ),
                // Creates a new object
                new PlayerInfoUpdatePacket(
                        // Code statement
                        EnumSet.of(
                                // Code statement
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Code statement
                                PlayerInfoUpdatePacket.Action.UPDATE_HAT
                        // End of a block/expression
                        ),
                        // Creates a new object
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.SURVIVAL, null, null, 0, false)
                // End of a block/expression
                )
        // End of a block/expression
        );

        // Calls a method
        addServerPackets(new PlayerInfoRemovePacket(UUID.randomUUID()));
        // Calls a method
        addServerPackets(new EntitySoundEffectPacket(SoundEvent.ENTITY_PLAYER_HURT, Sound.Source.PLAYER, 5, 1.0f, 1.0f, 0L));
        // Calls a method
        addServerPackets(new EntityStatusPacket(5, (byte) 2));
        // Calls a method
        addServerPackets(new EntityTeleportPacket(5, new Pos(0, 64, 0, 0, 0), Vec.ZERO, RelativeFlags.NONE, false));
        // Calls a method
        addServerPackets(new EntityVelocityPacket(5, Vec.ONE));
        // Calls a method
        addServerPackets(new ExplosionPacket(VEC, 4.0f, 3, null, Particle.FLAME, SoundEvent.ENTITY_GENERIC_EXPLODE, WeightedList.of()));
        // Calls a method
        addServerPackets(new FacePlayerPacket(FacePlayerPacket.FacePosition.EYES, VEC, 0, null), new FacePlayerPacket(FacePlayerPacket.FacePosition.FEET, VEC, 10, FacePlayerPacket.FacePosition.EYES));
        // Calls a method
        addServerPackets(new HeldItemChangePacket((byte) 0));
        // Calls a method
        addServerPackets(new HitAnimationPacket(5, 90f));
        // Calls a method
        addServerPackets(new InitializeWorldBorderPacket(0.0, 0.0, 10.0, 5.0, 0L, 29999984, 5, 15));
        // Calls a method
        addServerPackets(new JoinGamePacket(5, false, List.of("minecraft:overworld"), 0, 10, 10, false, true, false, 0, "minecraft:overworld", 0L, GameMode.CREATIVE, GameMode.SURVIVAL, false, false, null, 0, 0, false));
        // Calls a method
        addServerPackets(new MapDataPacket(5, (byte) 1, true, true, List.of(), null));
        // Calls a method
        addServerPackets(new MultiBlockChangePacket(0, 0, 0, new long[0]));
        // Calls a method
        addServerPackets(new NbtQueryResponsePacket(5, CompoundBinaryTag.builder().putString("key", "value").build()));
        // Calls a method
        addServerPackets(new OpenBookPacket(PlayerHand.MAIN));
        // Calls a method
        addServerPackets(new OpenHorseWindowPacket((byte) 5, 5, 5));
        // Calls a method
        addServerPackets(new OpenWindowPacket(5, 5, COMPONENT));
        // Calls a method
        addServerPackets(new OpenSignEditorPacket(BLOCK_VEC, true), new OpenSignEditorPacket(Vec.ONE, false), new OpenSignEditorPacket(Vec.ZERO, true));
        // Calls a method
        addServerPackets(new ParticlePacket(Particle.FLAME, VEC, Vec.ZERO, 0.1f, 10));
        // Calls a method
        addServerPackets(new PlayerAbilitiesPacket((byte) 0x0F, 0.05f, 0.1f));
        // Calls a method
        addServerPackets(new PlayerListHeaderAndFooterPacket(COMPONENT, COMPONENT));
        // Calls a method
        addServerPackets(new PlayerPositionAndLookPacket(5, VEC, Vec.ZERO, 0f, 0f, 0));
        // Calls a method
        addServerPackets(new PlayerRotationPacket(45f, false, 90f, false));
        // Calls a method
        addServerPackets(new ProjectilePowerPacket(5, 1.0));
        // Calls a method
        addServerPackets(new RespawnPacket(0, "overworld", 0L, GameMode.CREATIVE, GameMode.SURVIVAL, false, false, null, 5, 63, (byte) RespawnPacket.COPY_METADATA));
        // Code statement
        addServerPackets(
                // Creates a new object
                new ScoreboardObjectivePacket("objective", (byte) 0, COMPONENT, ScoreboardObjectivePacket.Type.HEARTS, Sidebar.NumberFormat.blank()),
                // Creates a new object
                new ScoreboardObjectivePacket("objective", (byte) 0, COMPONENT, ScoreboardObjectivePacket.Type.HEARTS, null),
                // Creates a new object
                new ScoreboardObjectivePacket("objective", (byte) 1, null, null, null),
                // Creates a new object
                new ScoreboardObjectivePacket("objective", (byte) 2, COMPONENT, ScoreboardObjectivePacket.Type.HEARTS, Sidebar.NumberFormat.styled(Component.empty())),
                // Creates a new object
                new ScoreboardObjectivePacket("objective", (byte) 2, COMPONENT, ScoreboardObjectivePacket.Type.HEARTS, null)
        // End of a block/expression
        );
        // Calls a method
        addServerPackets(new SelectAdvancementTabPacket("minecraft:story/root"));
        // Calls a method
        addServerPackets(new ServerDataPacket(COMPONENT, null));
        // Calls a method
        addServerPackets(new ServerDifficultyPacket(Difficulty.NORMAL, true));
        // Calls a method
        addServerPackets(new SetCooldownPacket("minecraft:ender_pearl", 5));
        // Calls a method
        addServerPackets(new SetCursorItemPacket(ItemStack.of(Material.DIAMOND)));
        // Calls a method
        addServerPackets(new SetExperiencePacket(0.5f, 10, 5));
        // Calls a method
        addServerPackets(new SetPassengersPacket(5, List.of(6, 7)));
        // Calls a method
        addServerPackets(new SetPlayerInventorySlotPacket(36, ItemStack.of(Material.DIAMOND_SWORD)));
        // Calls a method
        addServerPackets(new SetSlotPacket((byte) 0, 0, (short) 36, ItemStack.of(Material.DIAMOND)));
        // Calls a method
        addServerPackets(new SetTickStatePacket(20.0f, false));
        // Calls a method
        addServerPackets(new SetTitleSubTitlePacket(COMPONENT));
        // Calls a method
        addServerPackets(new SetTitleTextPacket(COMPONENT));
        // Calls a method
        addServerPackets(new SetTitleTimePacket(10, 70, 20));
        // Calls a method
        addServerPackets(new SoundEffectPacket(SoundEvent.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, Sound.Source.VOICE, VEC, 1.0f, 1.0f, 0L), new SoundEffectPacket(SoundEvent.ENTITY_PLAYER_HURT, net.kyori.adventure.sound.Sound.Source.PLAYER, new Vec(0.25, 0.125, 0.125), 1.0f, 1.0f, 0L));
        // Calls a method
        addServerPackets(new SpawnEntityPacket(5, UUID.randomUUID(), EntityType.ZOMBIE, new Pos(0, 64, 0, 0, 0), 9.84375f, 0, Vec.ONE));
        // Calls a method
        addServerPackets(new SpawnPositionPacket(new WorldPos("overworld", BLOCK_VEC), 0f, 1f));
        // Calls a method
        addServerPackets(new StartConfigurationPacket());
        // Calls a method
        addServerPackets(new StatisticsPacket(List.of(new StatisticsPacket.Statistic(StatisticCategory.BROKEN, 5, 100))));
        // Code statement
        addServerPackets(
                // Creates a new object
                new StopSoundPacket((byte) 0, null, null),
                // Creates a new object
                new StopSoundPacket((byte) 1, Sound.Source.BLOCK, null),
                // Creates a new object
                new StopSoundPacket((byte) 2, null, "minecraft:block.amethyst_block.break"),
                // Creates a new object
                new StopSoundPacket((byte) 3, Sound.Source.BLOCK, "block.amethyst_block.break")
        // End of a block/expression
        );
        // Calls a method
        addServerPackets(new TabCompletePacket(5, 0, 0, List.of()));
        // Calls a method
        addServerPackets(new TeamsPacket("team", new TeamsPacket.CreateTeamAction(COMPONENT, (byte) 0, TeamsPacket.NameTagVisibility.ALWAYS, TeamsPacket.CollisionRule.ALWAYS, NamedTextColor.RED, COMPONENT, COMPONENT, List.of("player1"))));
        // Calls a method
        addServerPackets(new TickStepPacket(20));
        // Code statement
        addServerPackets(
                // Creates a new object
                new SetTimePacket(1000L, Map.of()),
                // Creates a new object
                new SetTimePacket(1000L, Map.of(WorldClock.OVERWORLD, new SetTimePacket.ClockState(1000L, 0.6f, 1f))),
                // Creates a new object
                new SetTimePacket(Long.MIN_VALUE, Map.of(WorldClock.OVERWORLD, new SetTimePacket.ClockState(1000L, 0.6f, 1f),
                        // Code statement
                        WorldClock.THE_END, new SetTimePacket.ClockState(Long.MAX_VALUE, 0f, 0f)))
        // End of a block/expression
        );
        // Calls a method
        addServerPackets(new TradeListPacket(5, List.of(), 5, 5, true, true));
        // Calls a method
        addServerPackets(new UnloadChunkPacket(0, 0));
        // Calls a method
        addServerPackets(new UpdateHealthPacket(20.0f, 20, 5.0f));
        // Calls a method
        addServerPackets(new UpdateScorePacket("player", "objective", 100, COMPONENT, null));
        // Calls a method
        addServerPackets(new UpdateSimulationDistancePacket(8));
        // Calls a method
        addServerPackets(new UpdateViewDistancePacket(10));
        // Calls a method
        addServerPackets(new UpdateViewPositionPacket(0, 0));
        // Calls a method
        addServerPackets(new VehicleMovePacket(new Pos(0, 64, 0, 0, 0)));
        // Calls a method
        addServerPackets(new WindowItemsPacket((byte) 0, 0, List.of(ItemStack.of(Material.DIAMOND)), ItemStack.of(Material.STONE)));
        // Calls a method
        addServerPackets(new WindowPropertyPacket((byte) 0, (short) 0, (short) 5));
        // Calls a method
        addServerPackets(new WorldBorderCenterPacket(0.0, 0.0));
        // Calls a method
        addServerPackets(new WorldBorderLerpSizePacket(10.0, 20.0, 5000L));
        // Calls a method
        addServerPackets(new WorldBorderSizePacket(10.0));
        // Calls a method
        addServerPackets(new WorldBorderWarningDelayPacket(5));
        // Calls a method
        addServerPackets(new WorldBorderWarningReachPacket(5));
        // Calls a method
        addServerPackets(new AdvancementsPacket(false, List.of(), List.of(), List.of(), true));
        // TODO, these chunk* skips important paths
        // Calls a method
        addServerPackets(new ChunkBatchStartPacket());
        // Calls a method
        addServerPackets(new ChunkBatchFinishedPacket(100));
        // Calls a method
        addServerPackets(new ChunkDataPacket(0, 0, new ChunkData(Map.of(), new byte[0], Map.of()), new LightData(new BitSet(), new BitSet(), new BitSet(), new BitSet(), List.of(), List.of())));
        // Calls a method
        addServerPackets(new ChunkBiomesPacket(List.of()), new ChunkBiomesPacket(List.of(new ChunkBiomesPacket.ChunkBiomeData(0, 0, new byte[0]))));
        // Calls a method
        addServerPackets(new CustomChatCompletionPacket(CustomChatCompletionPacket.Action.ADD, List.of("entry1", "entry2")));
        // Calls a method
        addServerPackets(new DamageEventPacket(5, MinecraftServer.getDamageTypeRegistry().getId(DamageType.ARROW), 2, 3, VEC), new DamageEventPacket(50, MinecraftServer.getDamageTypeRegistry().getId(DamageType.WITHER), 0, 0, null));
        // Calls a method
        addServerPackets(new DeclareCommandsPacket(List.of(), 0));
        // Calls a method
        addServerPackets(new BundlePacket());
        // Calls a method
        addServerPackets(new DebugBlockValuePacket(Vec.ONE, new DebugSubscription.Update<>(DebugSubscription.BEE_HIVES, new DebugHiveInfo(Block.BEEHIVE, 1, 0, true))));
        // Calls a method
        addServerPackets(new DebugChunkValuePacket(1, new DebugSubscription.Update<>(DebugSubscription.POIS, new DebugPoiInfo(BLOCK_VEC, DebugPoiInfo.Type.BUTCHER, 1))));
        // Calls a method
        addServerPackets(new DebugEntityValuePacket(0, new DebugSubscription.Update<>(DebugSubscription.ENTITY_PATHS, new DebugPathInfo(new DebugPathInfo.Path(true, 0, BLOCK_VEC, List.of(), new DebugPathInfo.Data(Set.of(), List.of(), List.of())), 1))));
        // Calls a method
        addServerPackets(new DebugEventPacket(new DebugSubscription.Event<>(DebugSubscription.NEIGHBOR_UPDATES, Vec.ZERO)));
        // Code statement
        addServerPackets(new DebugSamplePacket(new long[0], DebugSamplePacket.Type.TICK_TIME)); // Legacy debug wrapper, maybe it will change.
        // Calls a method
        addServerPackets(new DeleteChatPacket(new MessageSignature(new byte[256])));
        // Calls a method
        addServerPackets(new DisguisedChatPacket(Component.text("Hey"), 0, Component.text("Message"), null));
        // Calls a method
        addServerPackets(new EntityPositionSyncPacket(1, VEC, VEC, 1f, 1f, false));
        // Calls a method
        addServerPackets(new GameTestHighlightPosPacket(BLOCK_VEC, BLOCK_VEC));
        // Calls a method
        addServerPackets(new UpdateLightPacket(0, 0, new LightData(new BitSet(), new BitSet(), new BitSet(), new BitSet(), List.of(), List.of())));
        // Calls a method
        addServerPackets(new MoveMinecartPacket(1, List.of(new MoveMinecartPacket.LerpStep(VEC, Vec.ZERO, 1f, 1f, 1f))));
        // Calls a method
        addServerPackets(new PlayerChatMessagePacket(0, UUID.randomUUID(), 0, new MessageSignature(new byte[256]), new SignedMessageBody.Packed("hey", Instant.EPOCH, 0L, new LastSeenMessages.Packed(List.of())), null, new FilterMask(FilterMask.Type.FULLY_FILTERED, new BitSet()), 1, Component.text("hey"), null));
        // Calls a method
        addServerPackets(new RecipeBookSettingsPacket(false, false, true, false, false, false, false, false));
        // Calls a method
        addServerPackets(new RemoveEntityEffectPacket(0, PotionEffect.BAD_OMEN));
        // Calls a method
        addServerPackets(new ResetScorePacket("dummy_score", null), new ResetScorePacket("duoka", "testObjective"));
        // Calls a method
        addServerPackets(new TestInstanceBlockStatus(Component.text("Minestom is cool"), null), new TestInstanceBlockStatus(Component.text("Where is season 5 william?"), BLOCK_VEC));
        // Calls a method
        addServerPackets(new EntityEffectPacket(0, new Potion(PotionEffect.ABSORPTION, 1, 150)));
        // Calls a method
        addServerPackets(new TrackedWaypointPacket(TrackedWaypointPacket.Operation.UNTRACK, new TrackedWaypointPacket.Waypoint(Either.right("test"), TrackedWaypointPacket.Icon.DEFAULT, new TrackedWaypointPacket.Target.Empty())));
        // Calls a method
        addServerPackets(new GameRuleValuesPacket(Map.of()), new GameRuleValuesPacket(Map.of(Objects.requireNonNull(GameRule.staticRegistry().getKey(GameRule.ADVANCE_TIME)), "false", Objects.requireNonNull(GameRule.staticRegistry().getKey(GameRule.KEEP_INVENTORY)), "false")));
        // Calls a method
        addServerPackets(new LowDiskSpaceWarningPacket());
    // End of a block/expression
    }

    // Annotation for the following element
    @BeforeAll
    // Start of a method/block
    public static void setupClient() {
        // Code statement
        MinecraftServer.init(); // Need to validate packets with auth mode.

        // Handshake
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientHandshakePacket(755, "localhost", 25565, ClientHandshakePacket.Intent.LOGIN),
                // Creates a new object
                new ClientHandshakePacket(Integer.MAX_VALUE, "localhost", 25565, ClientHandshakePacket.Intent.LOGIN),
                // Creates a new object
                new ClientHandshakePacket(Integer.MIN_VALUE, "localhost", 25565, ClientHandshakePacket.Intent.LOGIN),
                // Creates a new object
                new ClientHandshakePacket(6321, "localhost", 25565, ClientHandshakePacket.Intent.STATUS),
                // Creates a new object
                new ClientHandshakePacket(12341, "transfer.example.com", 25565, ClientHandshakePacket.Intent.TRANSFER)
        // End of a block/expression
        );

        // Status
        // Code statement
        addClientPackets(
                // Creates a new object
                new StatusRequestPacket()
        // End of a block/expression
        );
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientPingRequestPacket(Long.MIN_VALUE),
                // Creates a new object
                new ClientPingRequestPacket(Long.MAX_VALUE),
                // Creates a new object
                new ClientPingRequestPacket(0)
        // End of a block/expression
        );

        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientPongPacket(Integer.MAX_VALUE), new ClientPongPacket(Integer.MIN_VALUE), new ClientPongPacket(6500125), new ClientPongPacket(0)
        // End of a block/expression
        );

        // Login
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientLoginStartPacket("APlrWith_LongNam", UUID.randomUUID()),
                // Creates a new object
                new ClientLoginStartPacket("", UUID.randomUUID()),
                // Creates a new object
                new ClientLoginStartPacket(OG, UUID.randomUUID()),
                // Creates a new object
                new ClientLoginStartPacket(OG, new UUID(0, 0))
        // End of a block/expression
        );
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientEncryptionResponsePacket(new byte[123], new byte[123]),
                // Creates a new object
                new ClientEncryptionResponsePacket(new byte[0], new byte[0]),
                // Creates a new object
                new ClientEncryptionResponsePacket(new byte[]{1, 21, 3, 0x04}, new byte[]{1, 2, 74, 4})
        // End of a block/expression
        );
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientLoginPluginResponsePacket(1, new byte[]{1, 2, 3, 4}),
                // Creates a new object
                new ClientLoginPluginResponsePacket(0, new byte[0]),
                // Creates a new object
                new ClientLoginPluginResponsePacket(Integer.MAX_VALUE, new byte[]{1, 2, 3, 4, 5, 6}),
                // Creates a new object
                new ClientLoginPluginResponsePacket(Integer.MIN_VALUE, new byte[123])
        // End of a block/expression
        );
        // Calls a method
        addClientPackets(new ClientLoginAcknowledgedPacket());
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientCookieResponsePacket("minestom:cookie", new byte[123]),
                // Creates a new object
                new ClientCookieResponsePacket("cookie", new byte[0]),
                // Creates a new object
                new ClientCookieResponsePacket("cookie/packet", new byte[]{1, 22, 36, 42, -51, 6}),
                // Creates a new object
                new ClientCookieResponsePacket("cookie/max", new byte[5120]) // max length
        // End of a block/expression
        );
        // Configuration
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientSettingsPacket(ClientSettings.DEFAULT),
                // Creates a new object
                new ClientSettingsPacket(new ClientSettings(
                        // Code statement
                        Locale.UK, (byte) 2, ChatMessageType.FULL, false,
                        // Code statement
                        (byte) 0x01, MainHand.LEFT,
                        // Code statement
                        false, false,
                        // Code statement
                        ClientSettings.ParticleSetting.MINIMAL
                // Code statement
                )),
                // Creates a new object
                new ClientSettingsPacket(new ClientSettings(
                        // Code statement
                        Locale.CANADA_FRENCH, (byte) 32,
                        // Code statement
                        ChatMessageType.SYSTEM, true,
                        // Code statement
                        (byte) 0x7F, MainHand.RIGHT,
                        // Code statement
                        true, false,
                        // Code statement
                        ClientSettings.ParticleSetting.DECREASED
                // Code statement
                )),
                // Creates a new object
                new ClientSettingsPacket(new ClientSettings(
                        // Code statement
                        Locale.GERMANY, (byte) 12,
                        // Code statement
                        ChatMessageType.FULL, true,
                        // Code statement
                        (byte) 0x3F, MainHand.LEFT,
                        // Code statement
                        true, false,
                        // Code statement
                        ClientSettings.ParticleSetting.ALL
                // Code statement
                )),
                // Creates a new object
                new ClientSettingsPacket(new ClientSettings(
                        // Code statement
                        Locale.JAPAN, (byte) 4,
                        // Code statement
                        ChatMessageType.NONE, true,
                        // Code statement
                        (byte) 0x7F, MainHand.RIGHT,
                        // Code statement
                        true, true,
                        // Code statement
                        ClientSettings.ParticleSetting.ALL
                // Code statement
                )),
                // Creates a new object
                new ClientSettingsPacket(new ClientSettings(
                        // Code statement
                        Locale.FRANCE, (byte) 8,
                        // Code statement
                        ChatMessageType.SYSTEM, false,
                        // Code statement
                        (byte) 0x2A, MainHand.LEFT,
                        // Code statement
                        false, true,
                        // Code statement
                        ClientSettings.ParticleSetting.MINIMAL
                // Code statement
                ))
        // End of a block/expression
        );
        // Code statement
        addClientPackets(new ClientCookieResponsePacket("cookie/master", new byte[]{127, -128})); // See above
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientPluginMessagePacket("channel", new byte[]{-128, -128, -128, 0, 127}),
                // Creates a new object
                new ClientPluginMessagePacket("empty", new byte[0]),
                // Creates a new object
                new ClientPluginMessagePacket("", new byte[]{1, 2, 3, 4}),
                // Creates a new object
                new ClientPluginMessagePacket("", new byte[123])
        // End of a block/expression
        );
        // Calls a method
        addClientPackets(new ClientConfigurationAckPacket());
        // Code statement
        addClientPackets(new ClientKeepAlivePacket(System.nanoTime())); // Incorrect but should still work.
        // Code statement
        addClientPackets(new ClientPingRequestPacket(-1)); // See above.
        // Loop: repeats a block
        for (ResourcePackStatus status : ResourcePackStatus.values()) { // Full enum test
            // Calls a method
            addClientPackets(new ClientResourcePackStatusPacket(UUID.randomUUID(), status));
        // End of a block/expression
        }
        // Code statement
        addClientPackets(new ClientSelectKnownPacksPacket(List.of(
                // Creates a new object
                new SelectKnownPacksPacket.Entry("namespaced:entry", "custom_id", "1.0.0"),
                // Creates a new object
                new SelectKnownPacksPacket.Entry("defaultnamespace", "other_id", "12598125")
        // Code statement
        )));
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientCustomClickActionPacket(Key.key("wowzers"), CompoundBinaryTag.builder().putInt("key", 0).build()),
                // Creates a new object
                new ClientCustomClickActionPacket(Key.key("asgdf"), CompoundBinaryTag.builder().putString("key", "value").build())
        // End of a block/expression
        );
        // Calls a method
        addClientPackets(new ClientAcceptCodeOfConductPacket());

        // Play
        // Calls a method
        addClientPackets(new ClientTeleportConfirmPacket(325626), new ClientTeleportConfirmPacket(Integer.MAX_VALUE), new ClientTeleportConfirmPacket(Integer.MIN_VALUE));
        // Calls a method
        addClientPackets(new ClientQueryBlockNbtPacket(1325, BLOCK_VEC), new ClientQueryBlockNbtPacket(-15, Vec.ONE));
        // Calls a method
        addClientPackets(new ClientSelectBundleItemPacket(32, 65), new ClientSelectBundleItemPacket(Integer.MAX_VALUE, Integer.MAX_VALUE));
        // Calls a method
        addClientPackets(new ClientChangeDifficultyPacket(Difficulty.EASY, false), new ClientChangeDifficultyPacket(Difficulty.HARD, true), new ClientChangeDifficultyPacket(Difficulty.PEACEFUL, true));
        // Calls a method
        addClientPackets(new ClientChangeGameModePacket(GameMode.ADVENTURE), new ClientChangeGameModePacket(GameMode.SURVIVAL), new ClientChangeGameModePacket(GameMode.CREATIVE), new ClientChangeGameModePacket(GameMode.SPECTATOR));
        // Calls a method
        addClientPackets(new ClientChatAckPacket(12549581), new ClientChatAckPacket(Integer.MIN_VALUE), new ClientChatAckPacket(Integer.MAX_VALUE));
        // Calls a method
        addClientPackets(new ClientCommandChatPacket("l".repeat(256)), new ClientCommandChatPacket("helloworld"));
        //TODO (signed) support signed chat/commands with proper data.
        // Calls a method
        addClientPackets(new ClientSignedCommandChatPacket("helloworld", Long.MAX_VALUE, 0L, new ArgumentSignatures(List.of(new ArgumentSignatures.Entry("hey", new MessageSignature(new byte[256])))), new LastSeenMessages.Update(100, new BitSet(20)), (byte) 0));
        // Calls a method
        addClientPackets(new ClientChatMessagePacket("My name is bob", Long.MAX_VALUE, 0L, new MessageSignature(new byte[256]), 100, new BitSet(), (byte) 100), new ClientChatMessagePacket("hello", 0L, 0L, null, 0, new BitSet(), (byte) 42));
        //TODO (signed) use a key for tests
        // Calls a method
        addClientPackets(new ClientChatSessionUpdatePacket(new ChatSession(UUID.randomUUID(), new PlayerPublicKey(Instant.EPOCH, Objects.requireNonNull(MojangCrypt.generateKeyPair()).getPublic(), new byte[4096]))));
        // Calls a method
        addClientPackets(new ClientChunkBatchReceivedPacket(0.5f));
        // Calls a method
        addClientPackets(new ClientStatusPacket(ClientStatusPacket.Action.PERFORM_RESPAWN), new ClientStatusPacket(ClientStatusPacket.Action.REQUEST_STATS));
        // Calls a method
        addClientPackets(new ClientTickEndPacket());
        // Calls a method
        addClientPackets(new ClientTabCompletePacket(15, "/hellloworld"), new ClientTabCompletePacket(Integer.MIN_VALUE, "/hello arg1 arg2 arg3"), new ClientTabCompletePacket(-1000, "//undo"));
        // Calls a method
        addClientPackets(new ClientFinishConfigurationPacket());
        // Calls a method
        addClientPackets(new ClientClickWindowButtonPacket(15, 14), new ClientClickWindowButtonPacket(Integer.MIN_VALUE, Integer.MAX_VALUE));
        // Calls a method
        addClientPackets(new ClientClickWindowPacket(125, 20, (short) -999, (byte) 1, ClientClickWindowPacket.ClickType.SWAP, Map.of(), ItemStack.Hash.AIR), new ClientClickWindowPacket(Integer.MAX_VALUE, Integer.MIN_VALUE, (short) 51, (byte) 1, ClientClickWindowPacket.ClickType.SWAP, Map.of((short) 5, ItemStack.Hash.AIR), ItemStack.Hash.AIR));
        // Calls a method
        addClientPackets(new ClientCloseWindowPacket(15), new ClientCloseWindowPacket(Integer.MIN_VALUE));
        // Calls a method
        addClientPackets(new ClientWindowSlotStatePacket(25, 25, true), new ClientWindowSlotStatePacket(Integer.MAX_VALUE, Integer.MAX_VALUE, true), new ClientWindowSlotStatePacket(Integer.MIN_VALUE, Integer.MAX_VALUE, false));
        //Cookie
        //Plugin message
        // Calls a method
        addClientPackets(new ClientDebugSubscriptionRequestPacket(Set.of(DebugSubscription.DEDICATED_SERVER_TICK_TIME, DebugSubscription.ENTITY_PATHS)));
        // Calls a method
        addClientPackets(new ClientEditBookPacket(14, List.of("page1", "page2"), "Wrath of nothing"), new ClientEditBookPacket(15, List.of(), null), new ClientEditBookPacket(12, List.of("hi".repeat(99).split("h")), "What is this book?"));
        // Calls a method
        addClientPackets(new ClientQueryEntityNbtPacket(1325, 25), new ClientQueryEntityNbtPacket(-15, Integer.MAX_VALUE));
        // Code statement
        addClientPackets(
                // Creates a new object
                new ClientInteractEntityPacket(10, PlayerHand.MAIN, VEC, true),
                // Creates a new object
                new ClientInteractEntityPacket(124, PlayerHand.OFF, VEC, true),
                // Creates a new object
                new ClientInteractEntityPacket(10, PlayerHand.OFF, Vec.ZERO, false),
                // Creates a new object
                new ClientInteractEntityPacket(Integer.MAX_VALUE, PlayerHand.MAIN, VEC, true),
                // Creates a new object
                new ClientInteractEntityPacket(Integer.MIN_VALUE, PlayerHand.MAIN, VEC, false)
        // End of a block/expression
        );
        // Calls a method
        addClientPackets(new ClientAttackPacket(10), new ClientAttackPacket(Integer.MAX_VALUE), new ClientAttackPacket(Integer.MIN_VALUE), new ClientAttackPacket(0));
        // Calls a method
        addClientPackets(new ClientGenerateStructurePacket(Vec.ZERO, Integer.MAX_VALUE, true));
        // Calls a method
        addClientPackets(new ClientLockDifficultyPacket(true), new ClientLockDifficultyPacket(false));
        // Calls a method
        addClientPackets(new ClientPlayerPositionPacket(Vec.ONE, (byte) ClientPlayerPositionPacket.FLAG_HORIZONTAL_COLLISION), new ClientPlayerPositionPacket(Vec.ZERO, (byte) ClientPlayerPositionPacket.FLAG_ON_GROUND));
        // Calls a method
        addClientPackets(new ClientPlayerPositionAndRotationPacket(Pos.ZERO, true, true), new ClientPlayerPositionAndRotationPacket(new Pos(10, 10, 10, 0f, 0f), false, true));
        // Calls a method
        addClientPackets(new ClientPlayerPositionStatusPacket(true, false), new ClientPlayerPositionStatusPacket(false, false), new ClientPlayerPositionStatusPacket(false, true), new ClientPlayerPositionStatusPacket(true, true));
        // Calls a method
        addClientPackets(new ClientVehicleMovePacket(new Pos(5, 5, 5, 45f, 45f), true));
        // Calls a method
        addClientPackets(new ClientVehicleMovePacket(new Pos(6, 5, 6, 82f, 12.5f), false));
        // Calls a method
        addClientPackets(new ClientSteerBoatPacket(true, false), new ClientSteerBoatPacket(false, false), new ClientSteerBoatPacket(true, true), new ClientSteerBoatPacket(false, true));
        // Calls a method
        addClientPackets(new ClientPickItemFromBlockPacket(Vec.ONE, true), new ClientPickItemFromBlockPacket(Vec.ZERO, false));
        // Calls a method
        addClientPackets(new ClientPickItemFromEntityPacket(124, true), new ClientPickItemFromEntityPacket(124, false), new ClientPickItemFromEntityPacket(Integer.MAX_VALUE, true), new ClientPickItemFromEntityPacket(Integer.MIN_VALUE, false));
        // Calls a method
        addClientPackets(new ClientPlaceRecipePacket((byte) 10, 10, true), new ClientPlaceRecipePacket((byte) 51, 14, false));
        // Calls a method
        addClientPackets(new ClientPlayerAbilitiesPacket((byte) 0x02));
        // Calls a method
        addClientPackets(new ClientPlayerActionPacket(ClientPlayerActionPacket.Status.STARTED_DIGGING, Vec.ZERO, BlockFace.BOTTOM, Integer.MAX_VALUE), new ClientPlayerActionPacket(ClientPlayerActionPacket.Status.DROP_ITEM_STACK, Vec.ONE, BlockFace.TOP, Integer.MIN_VALUE));
        // Calls a method
        addClientPackets(new ClientEntityActionPacket(10, ClientEntityActionPacket.Action.LEAVE_BED, 0), new ClientEntityActionPacket(15, ClientEntityActionPacket.Action.START_SPRINTING, 0), new ClientEntityActionPacket(321, ClientEntityActionPacket.Action.START_FLYING_ELYTRA, 0));
        // Calls a method
        addClientPackets(new ClientInputPacket(true, false, true, false, false, false, true), new ClientInputPacket(false, true, true, false, false, false, true));
        // Calls a method
        addClientPackets(new ClientPlayerLoadedPacket());
        // Calls a method
        addClientPackets(new ClientPlayerRotationPacket(45f, 90f, true, false), new ClientPlayerRotationPacket(180f, -45f, false, true));
        // Calls a method
        addClientPackets(new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, Vec.ONE, BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 0), new ClientPlayerBlockPlacementPacket(PlayerHand.OFF, Vec.ZERO, BlockFace.BOTTOM, 1f, 1f, 1f, true, true, Integer.MAX_VALUE));
        // Calls a method
        addClientPackets(new ClientUseItemPacket(PlayerHand.MAIN, 0, 45f, 90f), new ClientUseItemPacket(PlayerHand.OFF, Integer.MAX_VALUE, 180f, -45f));
        // Calls a method
        addClientPackets(new ClientSpectateEntityPacket(1251), new ClientSpectateEntityPacket(Integer.MAX_VALUE), new ClientSpectateEntityPacket(Integer.MIN_VALUE), new ClientSpectateEntityPacket(0));
        // Calls a method
        addClientPackets(new ClientTeleportToEntityPacket(UUID.randomUUID()), new ClientTeleportToEntityPacket(new UUID(0, 0)));
        // Calls a method
        addClientPackets(new ClientSetRecipeBookStatePacket(ClientSetRecipeBookStatePacket.BookType.CRAFTING, true, false), new ClientSetRecipeBookStatePacket(ClientSetRecipeBookStatePacket.BookType.FURNACE, false, true), new ClientSetRecipeBookStatePacket(ClientSetRecipeBookStatePacket.BookType.BLAST_FURNACE, true, true), new ClientSetRecipeBookStatePacket(ClientSetRecipeBookStatePacket.BookType.SMOKER, false, false));
        // Calls a method
        addClientPackets(new ClientNameItemPacket("Diamond Sword"), new ClientNameItemPacket(""), new ClientNameItemPacket("A".repeat(100)));
        // Calls a method
        addClientPackets(new ClientResourcePackStatusPacket(UUID.randomUUID(), ResourcePackStatus.ACCEPTED), new ClientResourcePackStatusPacket(UUID.randomUUID(), ResourcePackStatus.DECLINED));
        // Calls a method
        addClientPackets(new ClientAdvancementTabPacket(AdvancementAction.OPENED_TAB, "minecraft:story/root"), new ClientAdvancementTabPacket(AdvancementAction.CLOSED_SCREEN, null));
        // Calls a method
        addClientPackets(new ClientSelectTradePacket(0), new ClientSelectTradePacket(5), new ClientSelectTradePacket(Integer.MAX_VALUE));
        // Calls a method
        addClientPackets(new ClientSetBeaconEffectPacket(PotionType.STRENGTH, PotionType.REGENERATION), new ClientSetBeaconEffectPacket(null, null), new ClientSetBeaconEffectPacket(PotionType.fromKey("strength"), null));
        // Calls a method
        addClientPackets(new ClientHeldItemChangePacket((short) 0), new ClientHeldItemChangePacket((short) 8), new ClientHeldItemChangePacket((short) 4));
        // Calls a method
        addClientPackets(new ClientUpdateCommandBlockPacket(Vec.ONE, "/say hello", ClientUpdateCommandBlockPacket.Mode.REDSTONE, (byte) 0), new ClientUpdateCommandBlockPacket(Vec.ZERO, "/tp @p 0 100 0", ClientUpdateCommandBlockPacket.Mode.AUTO, (byte) 0x01));
        // Calls a method
        addClientPackets(new ClientUpdateCommandBlockMinecartPacket(100, "/say minecart", true), new ClientUpdateCommandBlockMinecartPacket(Integer.MAX_VALUE, "", false));
        // Calls a method
        addClientPackets(new ClientCreativeInventoryActionPacket((short) 36, ItemStack.of(Material.DIAMOND_SWORD)), new ClientCreativeInventoryActionPacket((short) -1, ItemStack.AIR));
        // Calls a method
        addClientPackets(new ClientUpdateJigsawBlockPacket(Vec.ONE, "minecraft:village/plains/houses", "minecraft:village/plains/terminators", "minecraft:village/plains/town_centers", "minecraft:air", "rollable", 5, 10));
        // Calls a method
        addClientPackets(new ClientUpdateStructureBlockPacket(Vec.ZERO, ClientUpdateStructureBlockPacket.Action.UPDATE_DATA, ClientUpdateStructureBlockPacket.Mode.SAVE, "mystructure", Vec.ZERO, new Vec(10, 10, 10), ClientUpdateStructureBlockPacket.Mirror.NONE, Rotation.NONE, "", 1.0f, 0L, (byte) 0), new ClientUpdateStructureBlockPacket(Vec.ONE, ClientUpdateStructureBlockPacket.Action.SAVE, ClientUpdateStructureBlockPacket.Mode.LOAD, "test", new Vec(5, 5, 5), new Vec(20, 20, 20), ClientUpdateStructureBlockPacket.Mirror.LEFT_RIGHT, Rotation.CLOCKWISE, "metadata", 0.5f, 12345L, ClientUpdateStructureBlockPacket.SHOW_BOUNDING_BOX));
        // Calls a method
        addClientPackets(new ClientUpdateSignPacket(Vec.ZERO, true, List.of("Line 1", "Line 2", "Line 3", "Line 4")), new ClientUpdateSignPacket(Vec.ONE, false, List.of("", "", "", "")));
        // Calls a method
        addClientPackets(new ClientAnimationPacket(PlayerHand.MAIN), new ClientAnimationPacket(PlayerHand.OFF));
        // Calls a method
        addClientPackets(new ClientRecipeBookSeenRecipePacket(0), new ClientRecipeBookSeenRecipePacket(100), new ClientRecipeBookSeenRecipePacket(Integer.MAX_VALUE));
        // Calls a method
        addClientPackets(new ClientSetTestBlockPacket(Vec.ZERO, ClientSetTestBlockPacket.TestBlockMode.START, "test started"), new ClientSetTestBlockPacket(Vec.ONE, ClientSetTestBlockPacket.TestBlockMode.FAIL, "test failed"), new ClientSetTestBlockPacket(Vec.ZERO, ClientSetTestBlockPacket.TestBlockMode.ACCEPT, ""));
        // Calls a method
        addClientPackets(new ClientTestInstanceBlockActionPacket(Vec.ZERO, ClientTestInstanceBlockActionPacket.Action.INIT, new ClientTestInstanceBlockActionPacket.Data("mytest", new Vec(10, 10, 10), 0, false, ClientTestInstanceBlockActionPacket.Status.CLEARED, null)), new ClientTestInstanceBlockActionPacket(Vec.ONE, ClientTestInstanceBlockActionPacket.Action.RUN, new ClientTestInstanceBlockActionPacket.Data(null, new Vec(5, 5, 5), 1, true, ClientTestInstanceBlockActionPacket.Status.RUNNING, Component.text("Error!"))));
        // Calls a method
        addClientPackets(new ClientSetGameRulesPacket(List.of()), new ClientSetGameRulesPacket(List.of(new ClientSetGameRulesPacket.Entry(Objects.requireNonNull(GameRule.staticRegistry().getKey(GameRule.MOB_DROPS)), "false"))));
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> void testPacket(NetworkBuffer.Type<T> networkType, T packet, Env env) {
        // Calls a method
        byte[] bytes = NetworkBuffer.makeArray(networkType, packet, env.process());
        // Assigns a value
        var buffer = NetworkBuffer.wrap(bytes, 0, bytes.length, env.process()); // Requires for serialization of some packets
        // Calls a method
        var createdPacket = buffer.read(networkType);
        // Calls a method
        assertEquals(packet, createdPacket);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> Stream<Arguments> packets(PacketParser<T> parser, Map<Class<? extends T>, ? extends Collection<T>> map) {
        // Returns a value to the caller
        return Stream.of(
                // Code statement
                parser.handshake(),
                // Code statement
                parser.status(),
                // Code statement
                parser.login(),
                // Code statement
                parser.configuration(),
                // Code statement
                parser.play()
        // Calls a method
        ).flatMap(it -> packets(it, map));
    // End of a block/expression
    }

    // Start of a method/block
    static <T> Stream<Arguments> packets(PacketRegistry<? extends T> registry, Map<Class<? extends T>, ? extends Collection<T>> map) {
        // Returns a value to the caller
        return registry.packets().stream().flatMap(info -> {
            // Calls a method
            var tests = map.get(info.packetClass());
            // Calls a method
            var name = info.packetClass().getSimpleName();
            // Calls a method
            assertNotNull(tests, "No packet tests for %s".formatted(name));
            // Calls a method
            assertNotEquals(0, tests.size(), "Empty packet tests for %s".formatted(name));

            // Calls a method
            var serializer = info.serializer();
            // Returns a value to the caller
            return tests.stream().map(packet ->
                    // Code statement
                    Arguments.of(serializer, packet)
            // End of a block/expression
            );
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    static Stream<Arguments> serverPacketArguments() {
        // Returns a value to the caller
        return packets(PacketVanilla.SERVER_PACKET_PARSER, SERVER_PACKETS);
    // End of a block/expression
    }

    // Start of a method/block
    static Stream<Arguments> clientPacketArguments() {
        // Returns a value to the caller
        return packets(PacketVanilla.CLIENT_PACKET_PARSER, CLIENT_PACKETS);
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest(name = "Server Packet Test: {1}")
    // Annotation for the following element
    @MethodSource("serverPacketArguments")
    // Start of a method/block
    void serverPacket(NetworkBuffer.Type<ServerPacket> serializer, ServerPacket packet, Env env) {
        // Calls a method
        testPacket(serializer, packet, env);
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest(name = "Client Packet Test: {1}")
    // Annotation for the following element
    @MethodSource("clientPacketArguments")
    // Start of a method/block
    void clientPacket(NetworkBuffer.Type<ClientPacket> serializer, ClientPacket packet, Env env) {
        // Calls a method
        testPacket(serializer, packet, env);
    // End of a block/expression
    }
// End of a block/expression
}
