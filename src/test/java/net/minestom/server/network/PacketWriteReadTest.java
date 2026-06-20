// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackStatus;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.Style;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.TextDecoration;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.advancements.AdvancementAction;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.crypto.*;
// Import d'une classe nécessaire
import net.minestom.server.dialog.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.DamageType;
// Import d'une classe nécessaire
import net.minestom.server.extras.mojangAuth.MojangCrypt;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockEntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.gamerule.GameRule;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatMessageType;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.DebugSubscription;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.info.DebugHiveInfo;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.info.DebugPathInfo;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.info.DebugPoiInfo;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketParser;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketRegistry;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketVanilla;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientAcceptCodeOfConductPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientSelectKnownPacksPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientEncryptionResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginPluginResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginStartPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.ChunkData;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.LightData;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.status.ResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ClientSettings;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import net.minestom.server.potion.Potion;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionType;
// Import d'une classe nécessaire
import net.minestom.server.recipe.Ingredient;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeBookCategory;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeProperty;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.RecipeDisplay;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.SlotDisplay;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.Sidebar;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.statistic.StatisticCategory;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.Rotation;
// Import d'une classe nécessaire
import net.minestom.server.utils.WeightedList;
// Import d'une classe nécessaire
import net.minestom.server.world.Difficulty;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.BeforeAll;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.Arguments;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;

// Import d'une classe nécessaire
import java.time.Instant;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

/**
 * Ensures that packet can be written and read correctly.
 */
// Annotation pour l'élément suivant
@EnvTest // Some packets require registries.
// Déclaration de type (classe/interface/enum/record)
public class PacketWriteReadTest {
    // Appelle une méthode
    private static final Map<Class<? extends ServerPacket>, Set<ServerPacket>> SERVER_PACKETS = new HashMap<>();
    // Appelle une méthode
    private static final Map<Class<? extends ClientPacket>, Set<ClientPacket>> CLIENT_PACKETS = new HashMap<>();

    // Affecte une valeur
    private static final String OG = "TheMode911";
    // Appelle une méthode
    private static final Component COMPONENT = Component.text("Hey");
    // Appelle une méthode
    private static final Vec VEC = new Vec(5, 5, 5);
    // Appelle une méthode
    private static final Vec BLOCK_VEC = new Vec(5, 5, 5);

    // Annotation pour l'élément suivant
    @SafeVarargs
    // Début d'une méthode/d'un bloc
    private static <T extends ServerPacket> void addServerPackets(T... packets) {
        // Appelle une méthode
        assertNotEquals(0, packets.length);
        // Appelle une méthode
        var packetClass = packets[0].getClass();
        // Appelle une méthode
        var set = SERVER_PACKETS.computeIfAbsent(packetClass, c -> new HashSet<>(packets.length));
        // Boucle : répète un bloc
        for (var packet : packets)
            // Appelle une méthode
            assertTrue(set.add(packet), "Found duplicate server packet in %s with `%s`".formatted(packet.getClass().getSimpleName(), packet));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SafeVarargs
    // Début d'une méthode/d'un bloc
    private static <T extends ClientPacket> void addClientPackets(T... packets) {
        // Appelle une méthode
        assertNotEquals(0, packets.length);
        // Appelle une méthode
        var packetClass = packets[0].getClass();
        // Appelle une méthode
        var set = CLIENT_PACKETS.computeIfAbsent(packetClass, c -> new HashSet<>(packets.length));
        // Boucle : répète un bloc
        for (var packet : packets)
            // Appelle une méthode
            assertTrue(set.add(packet), "Found duplicate client packet in %s with `%s`".formatted(packet.getClass().getSimpleName(), packet));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @BeforeAll
    // Début d'une méthode/d'un bloc
    public static void setupServer() {
        // Instruction de code
        MinecraftServer.init(); // Need some tags in here, pretty gross.

        // Handshake
        // Status
        // Appelle une méthode
        addServerPackets(new ResponsePacket(new JsonObject().toString()));
        // Appelle une méthode
        addServerPackets(new PingResponsePacket(5));
        // Login
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new LoginDisconnectPacket(COMPONENT.append(Component.text(" your Disconnected!", NamedTextColor.BLUE))),
                // Crée un nouvel objet
                new LoginDisconnectPacket(COMPONENT.appendNewline().appendSpace().append(Component.text("Disconnected!", NamedTextColor.RED)))
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new EncryptionRequestPacket("abvcr3ujt324joi32aaa", new byte[124], new byte[65], true), // max test
                // Crée un nouvel objet
                new EncryptionRequestPacket("server", new byte[64], new byte[235], false),
                // Crée un nouvel objet
                new EncryptionRequestPacket("", new byte[54], new byte[23], true) // default
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new LoginSuccessPacket(new GameProfile(UUID.randomUUID(), OG)),
                // Crée un nouvel objet
                new LoginSuccessPacket(new GameProfile(UUID.randomUUID(), "APlrWith_LongNam")),
                // Crée un nouvel objet
                new LoginSuccessPacket(new GameProfile(new UUID(0, 0), "8", List.of(
                        // Crée un nouvel objet
                        new GameProfile.Property("textures", "randomtexturethatprobablyshouldbevalidated"),
                        // Crée un nouvel objet
                        new GameProfile.Property("signature", "supersigned")
                // Instruction de code
                )))
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addServerPackets(new SetCompressionPacket(256), new SetCompressionPacket(0), new SetCompressionPacket(1024));
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new LoginPluginRequestPacket(5, "id", new byte[16]),
                // Crée un nouvel objet
                new LoginPluginRequestPacket(0, "", new byte[]{1, 2, 23, 123}),
                // Crée un nouvel objet
                new LoginPluginRequestPacket(123, "id", new byte[123]),
                // Crée un nouvel objet
                new LoginPluginRequestPacket(6, "somecoolChannel", new byte[]{125, 0x76, 0x32, 0x12, 0b1111}),
                // Crée un nouvel objet
                new LoginPluginRequestPacket(Integer.MAX_VALUE, "x", new byte[0])
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new CookieRequestPacket("cookieKey"),
                // Crée un nouvel objet
                new CookieRequestPacket(""),
                // Crée un nouvel objet
                new CookieRequestPacket("minestom:cookie"),
                // Crée un nouvel objet
                new CookieRequestPacket("iam/cookie")
        // Fin d'un bloc/d'une expression
        );
        // Configuration
        // Instruction de code
        addServerPackets(new CookieRequestPacket("cookie/master")); // See above
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new PluginMessagePacket("channel", new byte[]{1, 2, 23, 123}),
                // Crée un nouvel objet
                new PluginMessagePacket("empty", new byte[124]),
                // Crée un nouvel objet
                new PluginMessagePacket("", new byte[]{1, 2, 23, 123}),
                // Crée un nouvel objet
                new PluginMessagePacket("", new byte[0])
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new DisconnectPacket(COMPONENT.append(Component.text(", Your gone!", NamedTextColor.RED))),
                // Crée un nouvel objet
                new DisconnectPacket(COMPONENT.appendNewline().appendNewline().appendSpace().append(Component.text("Why", Style.style(NamedTextColor.RED, TextDecoration.UNDERLINED)))),
                // Crée un nouvel objet
                new DisconnectPacket(Component.empty())
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addServerPackets(new FinishConfigurationPacket());
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new KeepAlivePacket(Long.MAX_VALUE),
                // Crée un nouvel objet
                new KeepAlivePacket(0),
                // Crée un nouvel objet
                new KeepAlivePacket(Long.MIN_VALUE),
                // Crée un nouvel objet
                new KeepAlivePacket(System.currentTimeMillis())
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addServerPackets(new PingPacket(0), new PingPacket(Integer.MAX_VALUE));
        // Appelle une méthode
        addServerPackets(new ResetChatPacket());
        // Instruction de code
        addServerPackets(new RegistryDataPacket("minecraft:damage_type", List.of( //TODO maybe use a proper one?
                // Crée un nouvel objet
                new RegistryDataPacket.Entry("some_value", CompoundBinaryTag.builder().putString("hey", "john").build()),
                // Crée un nouvel objet
                new RegistryDataPacket.Entry("some_value1", CompoundBinaryTag.builder().putInt("he5y", 1).build()),
                // Crée un nouvel objet
                new RegistryDataPacket.Entry("some_value2", CompoundBinaryTag.builder().putFloat("hey2", 0.23f).build()),
                // Crée un nouvel objet
                new RegistryDataPacket.Entry("some_value3", CompoundBinaryTag.builder().putString("h2ey", "john").build()),
                // Crée un nouvel objet
                new RegistryDataPacket.Entry("some_value4", CompoundBinaryTag.builder().putBoolean("", true).build()),
                // Crée un nouvel objet
                new RegistryDataPacket.Entry("some_value5", CompoundBinaryTag.builder().putBoolean("", false).build())
        // Instruction de code
        )));
        // Appelle une méthode
        addServerPackets(new ResourcePackPushPacket(new UUID(Long.MAX_VALUE, 0), "test", "test", false, Component.text("hello").append(COMPONENT)));
        // Appelle une méthode
        addServerPackets(new ResourcePackPopPacket(new UUID(Long.MAX_VALUE, 0)), new ResourcePackPopPacket(new UUID(0, Long.MAX_VALUE)));
        // Appelle une méthode
        addServerPackets(new CookieStorePacket("somepacket", new byte[]{1, 2, 23, 123}), new CookieStorePacket("somepacket", new byte[5120]));
        // Appelle une méthode
        addServerPackets(new TransferPacket("test", 20000), new TransferPacket("0", 25565));
        // Appelle une méthode
        addServerPackets(new UpdateEnabledFeaturesPacket(List.of("unvalidated", "very")));
        // Appelle une méthode
        addServerPackets(new TagsPacket(List.of(new TagsPacket.Registry("test", List.of(new TagsPacket.Tag("#cool", new int[]{1, 2, 23, 123}))))), new TagsPacket(List.of()));
        // Appelle une méthode
        addServerPackets(new SelectKnownPacksPacket(List.of(new SelectKnownPacksPacket.Entry("test", "id", "randomversion"))));
        // Appelle une méthode
        addServerPackets(new CustomReportDetailsPacket(Map.of("key", "value", "key1", "value1")));
        // Appelle une méthode
        addServerPackets(new ServerLinksPacket(new ServerLinksPacket.Entry(ServerLinksPacket.KnownLinkType.BUG_REPORT, "https://minestom.net"), new ServerLinksPacket.Entry(ServerLinksPacket.KnownLinkType.ANNOUNCEMENTS, "https://minestom.net")));
        // Appelle une méthode
        addServerPackets(new ClearDialogPacket());
        // Instruction de code
        addServerPackets(new ShowDialogPacket(
                // Crée un nouvel objet
                new Dialog.MultiAction(
                        // Crée un nouvel objet
                        new DialogMetadata(COMPONENT, COMPONENT, true, false, DialogAfterAction.WAIT_FOR_RESPONSE, List.of(), List.of(new DialogInput.Text("heyt", 12, COMPONENT, true, "", 10, null))),
                        // Instruction de code
                        List.of(),
                        // Instruction de code
                        null,
                        // Instruction de code
                        10
                // Instruction de code
                )));
        // Instruction de code
        addServerPackets(new ShowDialogPacket(
                // Crée un nouvel objet
                new Dialog.Confirmation(
                        // Crée un nouvel objet
                        new DialogMetadata(COMPONENT, COMPONENT.append(Component.text(OG)), true, false, DialogAfterAction.WAIT_FOR_RESPONSE, List.of(), List.of(new DialogInput.Text("heyt", 12, COMPONENT, true, "", 10, null))),
                        // Crée un nouvel objet
                        new DialogActionButton(COMPONENT.appendNewline(), COMPONENT, DialogActionButton.DEFAULT_WIDTH, new DialogAction.OpenUrl("https://minestom.net")),
                        // Crée un nouvel objet
                        new DialogActionButton(COMPONENT.appendNewline(), COMPONENT, 10, new DialogAction.CopyToClipboard("https://minestom.net"))
                // Instruction de code
                )));
        // Appelle une méthode
        addServerPackets(new CodeOfConductPacket("You need to be a nice person, i think?"));
        // Play
        // Appelle une méthode
        addServerPackets(new AcknowledgeBlockChangePacket(0));
        // Appelle une méthode
        addServerPackets(new ActionBarPacket(COMPONENT));
        // Appelle une méthode
        addServerPackets(new AttachEntityPacket(5, 10));
        // Appelle une méthode
        addServerPackets(new BlockActionPacket(BLOCK_VEC, (byte) 5, (byte) 5, 5));
        // Appelle une méthode
        addServerPackets(new BlockBreakAnimationPacket(5, BLOCK_VEC, (byte) 5));
        // Appelle une méthode
        addServerPackets(new BlockChangePacket(BLOCK_VEC, 0));
        // Appelle une méthode
        addServerPackets(new BlockEntityDataPacket(BLOCK_VEC, BlockEntityType.SIGN, CompoundBinaryTag.builder().putString("key", "value").build()));
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.AddAction(COMPONENT, 5f, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS, (byte) 2)),
                // Crée un nouvel objet
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.RemoveAction()),
                // Crée un nouvel objet
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateHealthAction(5f)),
                // Crée un nouvel objet
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateTitleAction(COMPONENT)),
                // Crée un nouvel objet
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateStyleAction(BossBar.Color.BLUE, BossBar.Overlay.PROGRESS)),
                // Crée un nouvel objet
                new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateFlagsAction((byte) 5))
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addServerPackets(new CameraPacket(5));
        // Appelle une méthode
        addServerPackets(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, 2));
        // Appelle une méthode
        addServerPackets(new SystemChatPacket(COMPONENT, false));
        // Appelle une méthode
        addServerPackets(new ClearTitlesPacket(false));
        // Appelle une méthode
        addServerPackets(new CloseWindowPacket((byte) 2));
        // Appelle une méthode
        addServerPackets(new CollectItemPacket(5, 5, 5));
        // Affecte une valeur
        var recipeDisplay = new RecipeDisplay.CraftingShapeless(
                // Instruction de code
                List.of(new SlotDisplay.Item(Material.STONE)),
                // Crée un nouvel objet
                new SlotDisplay.Item(Material.STONE_BRICKS),
                // Crée un nouvel objet
                new SlotDisplay.Item(Material.CRAFTING_TABLE)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addServerPackets(new PlaceGhostRecipePacket(0, recipeDisplay));
        // Appelle une méthode
        addServerPackets(new DeathCombatEventPacket(5, COMPONENT));
        // Instruction de code
        addServerPackets(new DeclareRecipesPacket(Map.of(
                // Instruction de code
                RecipeProperty.SMITHING_BASE, List.of(Material.STONE),
                // Instruction de code
                RecipeProperty.SMITHING_TEMPLATE, List.of(Material.STONE),
                // Instruction de code
                RecipeProperty.SMITHING_ADDITION, List.of(Material.STONE),
                // Instruction de code
                RecipeProperty.FURNACE_INPUT, List.of(Material.STONE),
                // Instruction de code
                RecipeProperty.BLAST_FURNACE_INPUT, List.of(Material.IRON_HOE, Material.DANDELION),
                // Instruction de code
                RecipeProperty.SMOKER_INPUT, List.of(Material.STONE),
                // Instruction de code
                RecipeProperty.CAMPFIRE_INPUT, List.of(Material.STONE)),
                // Instruction de code
                List.of(new DeclareRecipesPacket.StonecutterRecipe(new Ingredient(Material.DIAMOND),
                        // Crée un nouvel objet
                        new SlotDisplay.ItemStack(ItemStack.of(Material.GOLD_BLOCK))))
        // Instruction de code
        ));
        // Instruction de code
        addServerPackets(new RecipeBookAddPacket(List.of(new RecipeBookAddPacket.Entry(1, recipeDisplay, null,
                // Appelle une méthode
                RecipeBookCategory.CRAFTING_MISC, List.of(new Ingredient(Material.STONE)), true, true)), false));
        // Appelle une méthode
        addServerPackets(new RecipeBookRemovePacket(List.of(1)));

        // Appelle une méthode
        addServerPackets(new DestroyEntitiesPacket(List.of(5, 5, 5)));
        // Appelle une méthode
        addServerPackets(new DisconnectPacket(COMPONENT));
        // Appelle une méthode
        addServerPackets(new DisplayScoreboardPacket((byte) 5, "scoreboard"));
        // Appelle une méthode
        addServerPackets(new WorldEventPacket(5, BLOCK_VEC, 5, false));
        // Appelle une méthode
        addServerPackets(new EndCombatEventPacket(5));
        // Appelle une méthode
        addServerPackets(new EnterCombatEventPacket());
        // Appelle une méthode
        addServerPackets(new EntityAnimationPacket(5, EntityAnimationPacket.Animation.TAKE_DAMAGE));
        // Appelle une méthode
        addServerPackets(new EntityEquipmentPacket(6, Map.of(EquipmentSlot.MAIN_HAND, ItemStack.of(Material.DIAMOND_SWORD))));
        // Appelle une méthode
        addServerPackets(new EntityHeadLookPacket(5, 90f));
        // Appelle une méthode
        addServerPackets(new EntityMetaDataPacket(5, Map.of()));
        // Appelle une méthode
        addServerPackets(new EntityMetaDataPacket(5, Map.of(1, Metadata.VarInt(5))));
        // Appelle une méthode
        addServerPackets(new EntityPositionAndRotationPacket(5, (short) 0, (short) 0, (short) 0, 45f, 45f, false));
        // Appelle une méthode
        addServerPackets(new EntityPositionPacket(5, (short) 0, (short) 0, (short) 0, true));
        // Appelle une méthode
        addServerPackets(new EntityAttributesPacket(5, List.of()));
        // Appelle une méthode
        addServerPackets(new EntityRotationPacket(5, 45f, 45f, false));

        // Appelle une méthode
        final PlayerSkin skin = new PlayerSkin("hh", "hh");
        // Instruction de code
        addServerPackets( // TODO, these test are highly dependent on the default values, which arent great.
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket(
                        // Instruction de code
                        EnumSet.of(
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE
                        // Fin d'un bloc/d'une expression
                        ),
                        // Crée un nouvel objet
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), OG, List.of(new PlayerInfoUpdatePacket.Property("textures", skin.textures(), skin.signature())), false, 0, GameMode.CREATIVE, null, null, 0, true)
                // Fin d'un bloc/d'une expression
                ),
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket(
                        // Instruction de code
                        EnumSet.of(
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_HAT
                        // Fin d'un bloc/d'une expression
                        ),
                        // Crée un nouvel objet
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.CREATIVE, Component.text("Not").append(Component.text(OG)), null, 0, false)
                // Fin d'un bloc/d'une expression
                ),
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket(
                        // Instruction de code
                        EnumSet.of(
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE
                        // Fin d'un bloc/d'une expression
                        ),
                        // Crée un nouvel objet
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.SPECTATOR, null, null, 0, true)
                // Fin d'un bloc/d'une expression
                ),
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket(
                        // Instruction de code
                        EnumSet.of(
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_LATENCY,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_HAT
                        // Fin d'un bloc/d'une expression
                        ),
                        // Crée un nouvel objet
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 20, GameMode.CREATIVE, null, null, 0, false)
                // Fin d'un bloc/d'une expression
                ),
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket(
                        // Instruction de code
                        EnumSet.of(
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_LISTED
                        // Fin d'un bloc/d'une expression
                        ),
                        // Crée un nouvel objet
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), true, 0, GameMode.SURVIVAL, null, null, 0, true)
                // Fin d'un bloc/d'une expression
                ),
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket(
                        // Instruction de code
                        EnumSet.of(
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_LIST_ORDER,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_HAT
                        // Fin d'un bloc/d'une expression
                        ),
                        // Crée un nouvel objet
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.CREATIVE, null, null, 42, false)
                // Fin d'un bloc/d'une expression
                ),
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket(
                        // Instruction de code
                        EnumSet.of(
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                                // Instruction de code
                                PlayerInfoUpdatePacket.Action.UPDATE_HAT
                        // Fin d'un bloc/d'une expression
                        ),
                        // Crée un nouvel objet
                        new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.SURVIVAL, null, null, 0, false)
                // Fin d'un bloc/d'une expression
                )
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        addServerPackets(new PlayerInfoRemovePacket(UUID.randomUUID()));
        // Appelle une méthode
        addServerPackets(new EntitySoundEffectPacket(SoundEvent.ENTITY_PLAYER_HURT, Sound.Source.PLAYER, 5, 1.0f, 1.0f, 0L));
        // Appelle une méthode
        addServerPackets(new EntityStatusPacket(5, (byte) 2));
        // Appelle une méthode
        addServerPackets(new EntityTeleportPacket(5, new Pos(0, 64, 0, 0, 0), Vec.ZERO, RelativeFlags.NONE, false));
        // Appelle une méthode
        addServerPackets(new EntityVelocityPacket(5, Vec.ONE));
        // Appelle une méthode
        addServerPackets(new ExplosionPacket(VEC, 4.0f, 3, null, Particle.FLAME, SoundEvent.ENTITY_GENERIC_EXPLODE, WeightedList.of()));
        // Appelle une méthode
        addServerPackets(new FacePlayerPacket(FacePlayerPacket.FacePosition.EYES, VEC, 0, null), new FacePlayerPacket(FacePlayerPacket.FacePosition.FEET, VEC, 10, FacePlayerPacket.FacePosition.EYES));
        // Appelle une méthode
        addServerPackets(new HeldItemChangePacket((byte) 0));
        // Appelle une méthode
        addServerPackets(new HitAnimationPacket(5, 90f));
        // Appelle une méthode
        addServerPackets(new InitializeWorldBorderPacket(0.0, 0.0, 10.0, 5.0, 0L, 29999984, 5, 15));
        // Appelle une méthode
        addServerPackets(new JoinGamePacket(5, false, List.of("minecraft:overworld"), 0, 10, 10, false, true, false, 0, "minecraft:overworld", 0L, GameMode.CREATIVE, GameMode.SURVIVAL, false, false, null, 0, 0, false));
        // Appelle une méthode
        addServerPackets(new MapDataPacket(5, (byte) 1, true, true, List.of(), null));
        // Appelle une méthode
        addServerPackets(new MultiBlockChangePacket(0, 0, 0, new long[0]));
        // Appelle une méthode
        addServerPackets(new NbtQueryResponsePacket(5, CompoundBinaryTag.builder().putString("key", "value").build()));
        // Appelle une méthode
        addServerPackets(new OpenBookPacket(PlayerHand.MAIN));
        // Appelle une méthode
        addServerPackets(new OpenHorseWindowPacket((byte) 5, 5, 5));
        // Appelle une méthode
        addServerPackets(new OpenWindowPacket(5, 5, COMPONENT));
        // Appelle une méthode
        addServerPackets(new OpenSignEditorPacket(BLOCK_VEC, true), new OpenSignEditorPacket(Vec.ONE, false), new OpenSignEditorPacket(Vec.ZERO, true));
        // Appelle une méthode
        addServerPackets(new ParticlePacket(Particle.FLAME, VEC, Vec.ZERO, 0.1f, 10));
        // Appelle une méthode
        addServerPackets(new PlayerAbilitiesPacket((byte) 0x0F, 0.05f, 0.1f));
        // Appelle une méthode
        addServerPackets(new PlayerListHeaderAndFooterPacket(COMPONENT, COMPONENT));
        // Appelle une méthode
        addServerPackets(new PlayerPositionAndLookPacket(5, VEC, Vec.ZERO, 0f, 0f, 0));
        // Appelle une méthode
        addServerPackets(new PlayerRotationPacket(45f, false, 90f, false));
        // Appelle une méthode
        addServerPackets(new ProjectilePowerPacket(5, 1.0));
        // Appelle une méthode
        addServerPackets(new RespawnPacket(0, "overworld", 0L, GameMode.CREATIVE, GameMode.SURVIVAL, false, false, null, 5, 63, (byte) RespawnPacket.COPY_METADATA));
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new ScoreboardObjectivePacket("objective", (byte) 0, COMPONENT, ScoreboardObjectivePacket.Type.HEARTS, Sidebar.NumberFormat.blank()),
                // Crée un nouvel objet
                new ScoreboardObjectivePacket("objective", (byte) 0, COMPONENT, ScoreboardObjectivePacket.Type.HEARTS, null),
                // Crée un nouvel objet
                new ScoreboardObjectivePacket("objective", (byte) 1, null, null, null),
                // Crée un nouvel objet
                new ScoreboardObjectivePacket("objective", (byte) 2, COMPONENT, ScoreboardObjectivePacket.Type.HEARTS, Sidebar.NumberFormat.styled(Component.empty())),
                // Crée un nouvel objet
                new ScoreboardObjectivePacket("objective", (byte) 2, COMPONENT, ScoreboardObjectivePacket.Type.HEARTS, null)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addServerPackets(new SelectAdvancementTabPacket("minecraft:story/root"));
        // Appelle une méthode
        addServerPackets(new ServerDataPacket(COMPONENT, null));
        // Appelle une méthode
        addServerPackets(new ServerDifficultyPacket(Difficulty.NORMAL, true));
        // Appelle une méthode
        addServerPackets(new SetCooldownPacket("minecraft:ender_pearl", 5));
        // Appelle une méthode
        addServerPackets(new SetCursorItemPacket(ItemStack.of(Material.DIAMOND)));
        // Appelle une méthode
        addServerPackets(new SetExperiencePacket(0.5f, 10, 5));
        // Appelle une méthode
        addServerPackets(new SetPassengersPacket(5, List.of(6, 7)));
        // Appelle une méthode
        addServerPackets(new SetPlayerInventorySlotPacket(36, ItemStack.of(Material.DIAMOND_SWORD)));
        // Appelle une méthode
        addServerPackets(new SetSlotPacket((byte) 0, 0, (short) 36, ItemStack.of(Material.DIAMOND)));
        // Appelle une méthode
        addServerPackets(new SetTickStatePacket(20.0f, false));
        // Appelle une méthode
        addServerPackets(new SetTitleSubTitlePacket(COMPONENT));
        // Appelle une méthode
        addServerPackets(new SetTitleTextPacket(COMPONENT));
        // Appelle une méthode
        addServerPackets(new SetTitleTimePacket(10, 70, 20));
        // Appelle une méthode
        addServerPackets(new SoundEffectPacket(SoundEvent.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, Sound.Source.VOICE, VEC, 1.0f, 1.0f, 0L), new SoundEffectPacket(SoundEvent.ENTITY_PLAYER_HURT, net.kyori.adventure.sound.Sound.Source.PLAYER, new Vec(0.25, 0.125, 0.125), 1.0f, 1.0f, 0L));
        // Appelle une méthode
        addServerPackets(new SpawnEntityPacket(5, UUID.randomUUID(), EntityType.ZOMBIE, new Pos(0, 64, 0, 0, 0), 9.84375f, 0, Vec.ONE));
        // Appelle une méthode
        addServerPackets(new SpawnPositionPacket(new WorldPos("overworld", BLOCK_VEC), 0f, 1f));
        // Appelle une méthode
        addServerPackets(new StartConfigurationPacket());
        // Appelle une méthode
        addServerPackets(new StatisticsPacket(List.of(new StatisticsPacket.Statistic(StatisticCategory.BROKEN, 5, 100))));
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new StopSoundPacket((byte) 0, null, null),
                // Crée un nouvel objet
                new StopSoundPacket((byte) 1, Sound.Source.BLOCK, null),
                // Crée un nouvel objet
                new StopSoundPacket((byte) 2, null, "minecraft:block.amethyst_block.break"),
                // Crée un nouvel objet
                new StopSoundPacket((byte) 3, Sound.Source.BLOCK, "block.amethyst_block.break")
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addServerPackets(new TabCompletePacket(5, 0, 0, List.of()));
        // Appelle une méthode
        addServerPackets(new TeamsPacket("team", new TeamsPacket.CreateTeamAction(COMPONENT, (byte) 0, TeamsPacket.NameTagVisibility.ALWAYS, TeamsPacket.CollisionRule.ALWAYS, NamedTextColor.RED, COMPONENT, COMPONENT, List.of("player1"))));
        // Appelle une méthode
        addServerPackets(new TickStepPacket(20));
        // Instruction de code
        addServerPackets(
                // Crée un nouvel objet
                new SetTimePacket(1000L, Map.of()),
                // Crée un nouvel objet
                new SetTimePacket(1000L, Map.of(WorldClock.OVERWORLD, new SetTimePacket.ClockState(1000L, 0.6f, 1f))),
                // Crée un nouvel objet
                new SetTimePacket(Long.MIN_VALUE, Map.of(WorldClock.OVERWORLD, new SetTimePacket.ClockState(1000L, 0.6f, 1f),
                        // Instruction de code
                        WorldClock.THE_END, new SetTimePacket.ClockState(Long.MAX_VALUE, 0f, 0f)))
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addServerPackets(new TradeListPacket(5, List.of(), 5, 5, true, true));
        // Appelle une méthode
        addServerPackets(new UnloadChunkPacket(0, 0));
        // Appelle une méthode
        addServerPackets(new UpdateHealthPacket(20.0f, 20, 5.0f));
        // Appelle une méthode
        addServerPackets(new UpdateScorePacket("player", "objective", 100, COMPONENT, null));
        // Appelle une méthode
        addServerPackets(new UpdateSimulationDistancePacket(8));
        // Appelle une méthode
        addServerPackets(new UpdateViewDistancePacket(10));
        // Appelle une méthode
        addServerPackets(new UpdateViewPositionPacket(0, 0));
        // Appelle une méthode
        addServerPackets(new VehicleMovePacket(new Pos(0, 64, 0, 0, 0)));
        // Appelle une méthode
        addServerPackets(new WindowItemsPacket((byte) 0, 0, List.of(ItemStack.of(Material.DIAMOND)), ItemStack.of(Material.STONE)));
        // Appelle une méthode
        addServerPackets(new WindowPropertyPacket((byte) 0, (short) 0, (short) 5));
        // Appelle une méthode
        addServerPackets(new WorldBorderCenterPacket(0.0, 0.0));
        // Appelle une méthode
        addServerPackets(new WorldBorderLerpSizePacket(10.0, 20.0, 5000L));
        // Appelle une méthode
        addServerPackets(new WorldBorderSizePacket(10.0));
        // Appelle une méthode
        addServerPackets(new WorldBorderWarningDelayPacket(5));
        // Appelle une méthode
        addServerPackets(new WorldBorderWarningReachPacket(5));
        // Appelle une méthode
        addServerPackets(new AdvancementsPacket(false, List.of(), List.of(), List.of(), true));
        // TODO, these chunk* skips important paths
        // Appelle une méthode
        addServerPackets(new ChunkBatchStartPacket());
        // Appelle une méthode
        addServerPackets(new ChunkBatchFinishedPacket(100));
        // Appelle une méthode
        addServerPackets(new ChunkDataPacket(0, 0, new ChunkData(Map.of(), new byte[0], Map.of()), new LightData(new BitSet(), new BitSet(), new BitSet(), new BitSet(), List.of(), List.of())));
        // Appelle une méthode
        addServerPackets(new ChunkBiomesPacket(List.of()), new ChunkBiomesPacket(List.of(new ChunkBiomesPacket.ChunkBiomeData(0, 0, new byte[0]))));
        // Appelle une méthode
        addServerPackets(new CustomChatCompletionPacket(CustomChatCompletionPacket.Action.ADD, List.of("entry1", "entry2")));
        // Appelle une méthode
        addServerPackets(new DamageEventPacket(5, MinecraftServer.getDamageTypeRegistry().getId(DamageType.ARROW), 2, 3, VEC), new DamageEventPacket(50, MinecraftServer.getDamageTypeRegistry().getId(DamageType.WITHER), 0, 0, null));
        // Appelle une méthode
        addServerPackets(new DeclareCommandsPacket(List.of(), 0));
        // Appelle une méthode
        addServerPackets(new BundlePacket());
        // Appelle une méthode
        addServerPackets(new DebugBlockValuePacket(Vec.ONE, new DebugSubscription.Update<>(DebugSubscription.BEE_HIVES, new DebugHiveInfo(Block.BEEHIVE, 1, 0, true))));
        // Appelle une méthode
        addServerPackets(new DebugChunkValuePacket(1, new DebugSubscription.Update<>(DebugSubscription.POIS, new DebugPoiInfo(BLOCK_VEC, DebugPoiInfo.Type.BUTCHER, 1))));
        // Appelle une méthode
        addServerPackets(new DebugEntityValuePacket(0, new DebugSubscription.Update<>(DebugSubscription.ENTITY_PATHS, new DebugPathInfo(new DebugPathInfo.Path(true, 0, BLOCK_VEC, List.of(), new DebugPathInfo.Data(Set.of(), List.of(), List.of())), 1))));
        // Appelle une méthode
        addServerPackets(new DebugEventPacket(new DebugSubscription.Event<>(DebugSubscription.NEIGHBOR_UPDATES, Vec.ZERO)));
        // Instruction de code
        addServerPackets(new DebugSamplePacket(new long[0], DebugSamplePacket.Type.TICK_TIME)); // Legacy debug wrapper, maybe it will change.
        // Appelle une méthode
        addServerPackets(new DeleteChatPacket(new MessageSignature(new byte[256])));
        // Appelle une méthode
        addServerPackets(new DisguisedChatPacket(Component.text("Hey"), 0, Component.text("Message"), null));
        // Appelle une méthode
        addServerPackets(new EntityPositionSyncPacket(1, VEC, VEC, 1f, 1f, false));
        // Appelle une méthode
        addServerPackets(new GameTestHighlightPosPacket(BLOCK_VEC, BLOCK_VEC));
        // Appelle une méthode
        addServerPackets(new UpdateLightPacket(0, 0, new LightData(new BitSet(), new BitSet(), new BitSet(), new BitSet(), List.of(), List.of())));
        // Appelle une méthode
        addServerPackets(new MoveMinecartPacket(1, List.of(new MoveMinecartPacket.LerpStep(VEC, Vec.ZERO, 1f, 1f, 1f))));
        // Appelle une méthode
        addServerPackets(new PlayerChatMessagePacket(0, UUID.randomUUID(), 0, new MessageSignature(new byte[256]), new SignedMessageBody.Packed("hey", Instant.EPOCH, 0L, new LastSeenMessages.Packed(List.of())), null, new FilterMask(FilterMask.Type.FULLY_FILTERED, new BitSet()), 1, Component.text("hey"), null));
        // Appelle une méthode
        addServerPackets(new RecipeBookSettingsPacket(false, false, true, false, false, false, false, false));
        // Appelle une méthode
        addServerPackets(new RemoveEntityEffectPacket(0, PotionEffect.BAD_OMEN));
        // Appelle une méthode
        addServerPackets(new ResetScorePacket("dummy_score", null), new ResetScorePacket("duoka", "testObjective"));
        // Appelle une méthode
        addServerPackets(new TestInstanceBlockStatus(Component.text("Minestom is cool"), null), new TestInstanceBlockStatus(Component.text("Where is season 5 william?"), BLOCK_VEC));
        // Appelle une méthode
        addServerPackets(new EntityEffectPacket(0, new Potion(PotionEffect.ABSORPTION, 1, 150)));
        // Appelle une méthode
        addServerPackets(new TrackedWaypointPacket(TrackedWaypointPacket.Operation.UNTRACK, new TrackedWaypointPacket.Waypoint(Either.right("test"), TrackedWaypointPacket.Icon.DEFAULT, new TrackedWaypointPacket.Target.Empty())));
        // Appelle une méthode
        addServerPackets(new GameRuleValuesPacket(Map.of()), new GameRuleValuesPacket(Map.of(Objects.requireNonNull(GameRule.staticRegistry().getKey(GameRule.ADVANCE_TIME)), "false", Objects.requireNonNull(GameRule.staticRegistry().getKey(GameRule.KEEP_INVENTORY)), "false")));
        // Appelle une méthode
        addServerPackets(new LowDiskSpaceWarningPacket());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @BeforeAll
    // Début d'une méthode/d'un bloc
    public static void setupClient() {
        // Instruction de code
        MinecraftServer.init(); // Need to validate packets with auth mode.

        // Handshake
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientHandshakePacket(755, "localhost", 25565, ClientHandshakePacket.Intent.LOGIN),
                // Crée un nouvel objet
                new ClientHandshakePacket(Integer.MAX_VALUE, "localhost", 25565, ClientHandshakePacket.Intent.LOGIN),
                // Crée un nouvel objet
                new ClientHandshakePacket(Integer.MIN_VALUE, "localhost", 25565, ClientHandshakePacket.Intent.LOGIN),
                // Crée un nouvel objet
                new ClientHandshakePacket(6321, "localhost", 25565, ClientHandshakePacket.Intent.STATUS),
                // Crée un nouvel objet
                new ClientHandshakePacket(12341, "transfer.example.com", 25565, ClientHandshakePacket.Intent.TRANSFER)
        // Fin d'un bloc/d'une expression
        );

        // Status
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new StatusRequestPacket()
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientPingRequestPacket(Long.MIN_VALUE),
                // Crée un nouvel objet
                new ClientPingRequestPacket(Long.MAX_VALUE),
                // Crée un nouvel objet
                new ClientPingRequestPacket(0)
        // Fin d'un bloc/d'une expression
        );

        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientPongPacket(Integer.MAX_VALUE), new ClientPongPacket(Integer.MIN_VALUE), new ClientPongPacket(6500125), new ClientPongPacket(0)
        // Fin d'un bloc/d'une expression
        );

        // Login
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientLoginStartPacket("APlrWith_LongNam", UUID.randomUUID()),
                // Crée un nouvel objet
                new ClientLoginStartPacket("", UUID.randomUUID()),
                // Crée un nouvel objet
                new ClientLoginStartPacket(OG, UUID.randomUUID()),
                // Crée un nouvel objet
                new ClientLoginStartPacket(OG, new UUID(0, 0))
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientEncryptionResponsePacket(new byte[123], new byte[123]),
                // Crée un nouvel objet
                new ClientEncryptionResponsePacket(new byte[0], new byte[0]),
                // Crée un nouvel objet
                new ClientEncryptionResponsePacket(new byte[]{1, 21, 3, 0x04}, new byte[]{1, 2, 74, 4})
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientLoginPluginResponsePacket(1, new byte[]{1, 2, 3, 4}),
                // Crée un nouvel objet
                new ClientLoginPluginResponsePacket(0, new byte[0]),
                // Crée un nouvel objet
                new ClientLoginPluginResponsePacket(Integer.MAX_VALUE, new byte[]{1, 2, 3, 4, 5, 6}),
                // Crée un nouvel objet
                new ClientLoginPluginResponsePacket(Integer.MIN_VALUE, new byte[123])
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addClientPackets(new ClientLoginAcknowledgedPacket());
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientCookieResponsePacket("minestom:cookie", new byte[123]),
                // Crée un nouvel objet
                new ClientCookieResponsePacket("cookie", new byte[0]),
                // Crée un nouvel objet
                new ClientCookieResponsePacket("cookie/packet", new byte[]{1, 22, 36, 42, -51, 6}),
                // Crée un nouvel objet
                new ClientCookieResponsePacket("cookie/max", new byte[5120]) // max length
        // Fin d'un bloc/d'une expression
        );
        // Configuration
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientSettingsPacket(ClientSettings.DEFAULT),
                // Crée un nouvel objet
                new ClientSettingsPacket(new ClientSettings(
                        // Instruction de code
                        Locale.UK, (byte) 2, ChatMessageType.FULL, false,
                        // Instruction de code
                        (byte) 0x01, MainHand.LEFT,
                        // Instruction de code
                        false, false,
                        // Instruction de code
                        ClientSettings.ParticleSetting.MINIMAL
                // Instruction de code
                )),
                // Crée un nouvel objet
                new ClientSettingsPacket(new ClientSettings(
                        // Instruction de code
                        Locale.CANADA_FRENCH, (byte) 32,
                        // Instruction de code
                        ChatMessageType.SYSTEM, true,
                        // Instruction de code
                        (byte) 0x7F, MainHand.RIGHT,
                        // Instruction de code
                        true, false,
                        // Instruction de code
                        ClientSettings.ParticleSetting.DECREASED
                // Instruction de code
                )),
                // Crée un nouvel objet
                new ClientSettingsPacket(new ClientSettings(
                        // Instruction de code
                        Locale.GERMANY, (byte) 12,
                        // Instruction de code
                        ChatMessageType.FULL, true,
                        // Instruction de code
                        (byte) 0x3F, MainHand.LEFT,
                        // Instruction de code
                        true, false,
                        // Instruction de code
                        ClientSettings.ParticleSetting.ALL
                // Instruction de code
                )),
                // Crée un nouvel objet
                new ClientSettingsPacket(new ClientSettings(
                        // Instruction de code
                        Locale.JAPAN, (byte) 4,
                        // Instruction de code
                        ChatMessageType.NONE, true,
                        // Instruction de code
                        (byte) 0x7F, MainHand.RIGHT,
                        // Instruction de code
                        true, true,
                        // Instruction de code
                        ClientSettings.ParticleSetting.ALL
                // Instruction de code
                )),
                // Crée un nouvel objet
                new ClientSettingsPacket(new ClientSettings(
                        // Instruction de code
                        Locale.FRANCE, (byte) 8,
                        // Instruction de code
                        ChatMessageType.SYSTEM, false,
                        // Instruction de code
                        (byte) 0x2A, MainHand.LEFT,
                        // Instruction de code
                        false, true,
                        // Instruction de code
                        ClientSettings.ParticleSetting.MINIMAL
                // Instruction de code
                ))
        // Fin d'un bloc/d'une expression
        );
        // Instruction de code
        addClientPackets(new ClientCookieResponsePacket("cookie/master", new byte[]{127, -128})); // See above
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientPluginMessagePacket("channel", new byte[]{-128, -128, -128, 0, 127}),
                // Crée un nouvel objet
                new ClientPluginMessagePacket("empty", new byte[0]),
                // Crée un nouvel objet
                new ClientPluginMessagePacket("", new byte[]{1, 2, 3, 4}),
                // Crée un nouvel objet
                new ClientPluginMessagePacket("", new byte[123])
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addClientPackets(new ClientConfigurationAckPacket());
        // Instruction de code
        addClientPackets(new ClientKeepAlivePacket(System.nanoTime())); // Incorrect but should still work.
        // Instruction de code
        addClientPackets(new ClientPingRequestPacket(-1)); // See above.
        // Boucle : répète un bloc
        for (ResourcePackStatus status : ResourcePackStatus.values()) { // Full enum test
            // Appelle une méthode
            addClientPackets(new ClientResourcePackStatusPacket(UUID.randomUUID(), status));
        // Fin d'un bloc/d'une expression
        }
        // Instruction de code
        addClientPackets(new ClientSelectKnownPacksPacket(List.of(
                // Crée un nouvel objet
                new SelectKnownPacksPacket.Entry("namespaced:entry", "custom_id", "1.0.0"),
                // Crée un nouvel objet
                new SelectKnownPacksPacket.Entry("defaultnamespace", "other_id", "12598125")
        // Instruction de code
        )));
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientCustomClickActionPacket(Key.key("wowzers"), CompoundBinaryTag.builder().putInt("key", 0).build()),
                // Crée un nouvel objet
                new ClientCustomClickActionPacket(Key.key("asgdf"), CompoundBinaryTag.builder().putString("key", "value").build())
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addClientPackets(new ClientAcceptCodeOfConductPacket());

        // Play
        // Appelle une méthode
        addClientPackets(new ClientTeleportConfirmPacket(325626), new ClientTeleportConfirmPacket(Integer.MAX_VALUE), new ClientTeleportConfirmPacket(Integer.MIN_VALUE));
        // Appelle une méthode
        addClientPackets(new ClientQueryBlockNbtPacket(1325, BLOCK_VEC), new ClientQueryBlockNbtPacket(-15, Vec.ONE));
        // Appelle une méthode
        addClientPackets(new ClientSelectBundleItemPacket(32, 65), new ClientSelectBundleItemPacket(Integer.MAX_VALUE, Integer.MAX_VALUE));
        // Appelle une méthode
        addClientPackets(new ClientChangeDifficultyPacket(Difficulty.EASY, false), new ClientChangeDifficultyPacket(Difficulty.HARD, true), new ClientChangeDifficultyPacket(Difficulty.PEACEFUL, true));
        // Appelle une méthode
        addClientPackets(new ClientChangeGameModePacket(GameMode.ADVENTURE), new ClientChangeGameModePacket(GameMode.SURVIVAL), new ClientChangeGameModePacket(GameMode.CREATIVE), new ClientChangeGameModePacket(GameMode.SPECTATOR));
        // Appelle une méthode
        addClientPackets(new ClientChatAckPacket(12549581), new ClientChatAckPacket(Integer.MIN_VALUE), new ClientChatAckPacket(Integer.MAX_VALUE));
        // Appelle une méthode
        addClientPackets(new ClientCommandChatPacket("l".repeat(256)), new ClientCommandChatPacket("helloworld"));
        //TODO (signed) support signed chat/commands with proper data.
        // Appelle une méthode
        addClientPackets(new ClientSignedCommandChatPacket("helloworld", Long.MAX_VALUE, 0L, new ArgumentSignatures(List.of(new ArgumentSignatures.Entry("hey", new MessageSignature(new byte[256])))), new LastSeenMessages.Update(100, new BitSet(20)), (byte) 0));
        // Appelle une méthode
        addClientPackets(new ClientChatMessagePacket("My name is bob", Long.MAX_VALUE, 0L, new MessageSignature(new byte[256]), 100, new BitSet(), (byte) 100), new ClientChatMessagePacket("hello", 0L, 0L, null, 0, new BitSet(), (byte) 42));
        //TODO (signed) use a key for tests
        // Appelle une méthode
        addClientPackets(new ClientChatSessionUpdatePacket(new ChatSession(UUID.randomUUID(), new PlayerPublicKey(Instant.EPOCH, Objects.requireNonNull(MojangCrypt.generateKeyPair()).getPublic(), new byte[4096]))));
        // Appelle une méthode
        addClientPackets(new ClientChunkBatchReceivedPacket(0.5f));
        // Appelle une méthode
        addClientPackets(new ClientStatusPacket(ClientStatusPacket.Action.PERFORM_RESPAWN), new ClientStatusPacket(ClientStatusPacket.Action.REQUEST_STATS));
        // Appelle une méthode
        addClientPackets(new ClientTickEndPacket());
        // Appelle une méthode
        addClientPackets(new ClientTabCompletePacket(15, "/hellloworld"), new ClientTabCompletePacket(Integer.MIN_VALUE, "/hello arg1 arg2 arg3"), new ClientTabCompletePacket(-1000, "//undo"));
        // Appelle une méthode
        addClientPackets(new ClientFinishConfigurationPacket());
        // Appelle une méthode
        addClientPackets(new ClientClickWindowButtonPacket(15, 14), new ClientClickWindowButtonPacket(Integer.MIN_VALUE, Integer.MAX_VALUE));
        // Appelle une méthode
        addClientPackets(new ClientClickWindowPacket(125, 20, (short) -999, (byte) 1, ClientClickWindowPacket.ClickType.SWAP, Map.of(), ItemStack.Hash.AIR), new ClientClickWindowPacket(Integer.MAX_VALUE, Integer.MIN_VALUE, (short) 51, (byte) 1, ClientClickWindowPacket.ClickType.SWAP, Map.of((short) 5, ItemStack.Hash.AIR), ItemStack.Hash.AIR));
        // Appelle une méthode
        addClientPackets(new ClientCloseWindowPacket(15), new ClientCloseWindowPacket(Integer.MIN_VALUE));
        // Appelle une méthode
        addClientPackets(new ClientWindowSlotStatePacket(25, 25, true), new ClientWindowSlotStatePacket(Integer.MAX_VALUE, Integer.MAX_VALUE, true), new ClientWindowSlotStatePacket(Integer.MIN_VALUE, Integer.MAX_VALUE, false));
        //Cookie
        //Plugin message
        // Appelle une méthode
        addClientPackets(new ClientDebugSubscriptionRequestPacket(Set.of(DebugSubscription.DEDICATED_SERVER_TICK_TIME, DebugSubscription.ENTITY_PATHS)));
        // Appelle une méthode
        addClientPackets(new ClientEditBookPacket(14, List.of("page1", "page2"), "Wrath of nothing"), new ClientEditBookPacket(15, List.of(), null), new ClientEditBookPacket(12, List.of("hi".repeat(99).split("h")), "What is this book?"));
        // Appelle une méthode
        addClientPackets(new ClientQueryEntityNbtPacket(1325, 25), new ClientQueryEntityNbtPacket(-15, Integer.MAX_VALUE));
        // Instruction de code
        addClientPackets(
                // Crée un nouvel objet
                new ClientInteractEntityPacket(10, PlayerHand.MAIN, VEC, true),
                // Crée un nouvel objet
                new ClientInteractEntityPacket(124, PlayerHand.OFF, VEC, true),
                // Crée un nouvel objet
                new ClientInteractEntityPacket(10, PlayerHand.OFF, Vec.ZERO, false),
                // Crée un nouvel objet
                new ClientInteractEntityPacket(Integer.MAX_VALUE, PlayerHand.MAIN, VEC, true),
                // Crée un nouvel objet
                new ClientInteractEntityPacket(Integer.MIN_VALUE, PlayerHand.MAIN, VEC, false)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        addClientPackets(new ClientAttackPacket(10), new ClientAttackPacket(Integer.MAX_VALUE), new ClientAttackPacket(Integer.MIN_VALUE), new ClientAttackPacket(0));
        // Appelle une méthode
        addClientPackets(new ClientGenerateStructurePacket(Vec.ZERO, Integer.MAX_VALUE, true));
        // Appelle une méthode
        addClientPackets(new ClientLockDifficultyPacket(true), new ClientLockDifficultyPacket(false));
        // Appelle une méthode
        addClientPackets(new ClientPlayerPositionPacket(Vec.ONE, (byte) ClientPlayerPositionPacket.FLAG_HORIZONTAL_COLLISION), new ClientPlayerPositionPacket(Vec.ZERO, (byte) ClientPlayerPositionPacket.FLAG_ON_GROUND));
        // Appelle une méthode
        addClientPackets(new ClientPlayerPositionAndRotationPacket(Pos.ZERO, true, true), new ClientPlayerPositionAndRotationPacket(new Pos(10, 10, 10, 0f, 0f), false, true));
        // Appelle une méthode
        addClientPackets(new ClientPlayerPositionStatusPacket(true, false), new ClientPlayerPositionStatusPacket(false, false), new ClientPlayerPositionStatusPacket(false, true), new ClientPlayerPositionStatusPacket(true, true));
        // Appelle une méthode
        addClientPackets(new ClientVehicleMovePacket(new Pos(5, 5, 5, 45f, 45f), true));
        // Appelle une méthode
        addClientPackets(new ClientVehicleMovePacket(new Pos(6, 5, 6, 82f, 12.5f), false));
        // Appelle une méthode
        addClientPackets(new ClientSteerBoatPacket(true, false), new ClientSteerBoatPacket(false, false), new ClientSteerBoatPacket(true, true), new ClientSteerBoatPacket(false, true));
        // Appelle une méthode
        addClientPackets(new ClientPickItemFromBlockPacket(Vec.ONE, true), new ClientPickItemFromBlockPacket(Vec.ZERO, false));
        // Appelle une méthode
        addClientPackets(new ClientPickItemFromEntityPacket(124, true), new ClientPickItemFromEntityPacket(124, false), new ClientPickItemFromEntityPacket(Integer.MAX_VALUE, true), new ClientPickItemFromEntityPacket(Integer.MIN_VALUE, false));
        // Appelle une méthode
        addClientPackets(new ClientPlaceRecipePacket((byte) 10, 10, true), new ClientPlaceRecipePacket((byte) 51, 14, false));
        // Appelle une méthode
        addClientPackets(new ClientPlayerAbilitiesPacket((byte) 0x02));
        // Appelle une méthode
        addClientPackets(new ClientPlayerActionPacket(ClientPlayerActionPacket.Status.STARTED_DIGGING, Vec.ZERO, BlockFace.BOTTOM, Integer.MAX_VALUE), new ClientPlayerActionPacket(ClientPlayerActionPacket.Status.DROP_ITEM_STACK, Vec.ONE, BlockFace.TOP, Integer.MIN_VALUE));
        // Appelle une méthode
        addClientPackets(new ClientEntityActionPacket(10, ClientEntityActionPacket.Action.LEAVE_BED, 0), new ClientEntityActionPacket(15, ClientEntityActionPacket.Action.START_SPRINTING, 0), new ClientEntityActionPacket(321, ClientEntityActionPacket.Action.START_FLYING_ELYTRA, 0));
        // Appelle une méthode
        addClientPackets(new ClientInputPacket(true, false, true, false, false, false, true), new ClientInputPacket(false, true, true, false, false, false, true));
        // Appelle une méthode
        addClientPackets(new ClientPlayerLoadedPacket());
        // Appelle une méthode
        addClientPackets(new ClientPlayerRotationPacket(45f, 90f, true, false), new ClientPlayerRotationPacket(180f, -45f, false, true));
        // Appelle une méthode
        addClientPackets(new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, Vec.ONE, BlockFace.TOP, 0.5f, 0.5f, 0.5f, false, false, 0), new ClientPlayerBlockPlacementPacket(PlayerHand.OFF, Vec.ZERO, BlockFace.BOTTOM, 1f, 1f, 1f, true, true, Integer.MAX_VALUE));
        // Appelle une méthode
        addClientPackets(new ClientUseItemPacket(PlayerHand.MAIN, 0, 45f, 90f), new ClientUseItemPacket(PlayerHand.OFF, Integer.MAX_VALUE, 180f, -45f));
        // Appelle une méthode
        addClientPackets(new ClientSpectateEntityPacket(1251), new ClientSpectateEntityPacket(Integer.MAX_VALUE), new ClientSpectateEntityPacket(Integer.MIN_VALUE), new ClientSpectateEntityPacket(0));
        // Appelle une méthode
        addClientPackets(new ClientTeleportToEntityPacket(UUID.randomUUID()), new ClientTeleportToEntityPacket(new UUID(0, 0)));
        // Appelle une méthode
        addClientPackets(new ClientSetRecipeBookStatePacket(ClientSetRecipeBookStatePacket.BookType.CRAFTING, true, false), new ClientSetRecipeBookStatePacket(ClientSetRecipeBookStatePacket.BookType.FURNACE, false, true), new ClientSetRecipeBookStatePacket(ClientSetRecipeBookStatePacket.BookType.BLAST_FURNACE, true, true), new ClientSetRecipeBookStatePacket(ClientSetRecipeBookStatePacket.BookType.SMOKER, false, false));
        // Appelle une méthode
        addClientPackets(new ClientNameItemPacket("Diamond Sword"), new ClientNameItemPacket(""), new ClientNameItemPacket("A".repeat(100)));
        // Appelle une méthode
        addClientPackets(new ClientResourcePackStatusPacket(UUID.randomUUID(), ResourcePackStatus.ACCEPTED), new ClientResourcePackStatusPacket(UUID.randomUUID(), ResourcePackStatus.DECLINED));
        // Appelle une méthode
        addClientPackets(new ClientAdvancementTabPacket(AdvancementAction.OPENED_TAB, "minecraft:story/root"), new ClientAdvancementTabPacket(AdvancementAction.CLOSED_SCREEN, null));
        // Appelle une méthode
        addClientPackets(new ClientSelectTradePacket(0), new ClientSelectTradePacket(5), new ClientSelectTradePacket(Integer.MAX_VALUE));
        // Appelle une méthode
        addClientPackets(new ClientSetBeaconEffectPacket(PotionType.STRENGTH, PotionType.REGENERATION), new ClientSetBeaconEffectPacket(null, null), new ClientSetBeaconEffectPacket(PotionType.fromKey("strength"), null));
        // Appelle une méthode
        addClientPackets(new ClientHeldItemChangePacket((short) 0), new ClientHeldItemChangePacket((short) 8), new ClientHeldItemChangePacket((short) 4));
        // Appelle une méthode
        addClientPackets(new ClientUpdateCommandBlockPacket(Vec.ONE, "/say hello", ClientUpdateCommandBlockPacket.Mode.REDSTONE, (byte) 0), new ClientUpdateCommandBlockPacket(Vec.ZERO, "/tp @p 0 100 0", ClientUpdateCommandBlockPacket.Mode.AUTO, (byte) 0x01));
        // Appelle une méthode
        addClientPackets(new ClientUpdateCommandBlockMinecartPacket(100, "/say minecart", true), new ClientUpdateCommandBlockMinecartPacket(Integer.MAX_VALUE, "", false));
        // Appelle une méthode
        addClientPackets(new ClientCreativeInventoryActionPacket((short) 36, ItemStack.of(Material.DIAMOND_SWORD)), new ClientCreativeInventoryActionPacket((short) -1, ItemStack.AIR));
        // Appelle une méthode
        addClientPackets(new ClientUpdateJigsawBlockPacket(Vec.ONE, "minecraft:village/plains/houses", "minecraft:village/plains/terminators", "minecraft:village/plains/town_centers", "minecraft:air", "rollable", 5, 10));
        // Appelle une méthode
        addClientPackets(new ClientUpdateStructureBlockPacket(Vec.ZERO, ClientUpdateStructureBlockPacket.Action.UPDATE_DATA, ClientUpdateStructureBlockPacket.Mode.SAVE, "mystructure", Vec.ZERO, new Vec(10, 10, 10), ClientUpdateStructureBlockPacket.Mirror.NONE, Rotation.NONE, "", 1.0f, 0L, (byte) 0), new ClientUpdateStructureBlockPacket(Vec.ONE, ClientUpdateStructureBlockPacket.Action.SAVE, ClientUpdateStructureBlockPacket.Mode.LOAD, "test", new Vec(5, 5, 5), new Vec(20, 20, 20), ClientUpdateStructureBlockPacket.Mirror.LEFT_RIGHT, Rotation.CLOCKWISE, "metadata", 0.5f, 12345L, ClientUpdateStructureBlockPacket.SHOW_BOUNDING_BOX));
        // Appelle une méthode
        addClientPackets(new ClientUpdateSignPacket(Vec.ZERO, true, List.of("Line 1", "Line 2", "Line 3", "Line 4")), new ClientUpdateSignPacket(Vec.ONE, false, List.of("", "", "", "")));
        // Appelle une méthode
        addClientPackets(new ClientAnimationPacket(PlayerHand.MAIN), new ClientAnimationPacket(PlayerHand.OFF));
        // Appelle une méthode
        addClientPackets(new ClientRecipeBookSeenRecipePacket(0), new ClientRecipeBookSeenRecipePacket(100), new ClientRecipeBookSeenRecipePacket(Integer.MAX_VALUE));
        // Appelle une méthode
        addClientPackets(new ClientSetTestBlockPacket(Vec.ZERO, ClientSetTestBlockPacket.TestBlockMode.START, "test started"), new ClientSetTestBlockPacket(Vec.ONE, ClientSetTestBlockPacket.TestBlockMode.FAIL, "test failed"), new ClientSetTestBlockPacket(Vec.ZERO, ClientSetTestBlockPacket.TestBlockMode.ACCEPT, ""));
        // Appelle une méthode
        addClientPackets(new ClientTestInstanceBlockActionPacket(Vec.ZERO, ClientTestInstanceBlockActionPacket.Action.INIT, new ClientTestInstanceBlockActionPacket.Data("mytest", new Vec(10, 10, 10), 0, false, ClientTestInstanceBlockActionPacket.Status.CLEARED, null)), new ClientTestInstanceBlockActionPacket(Vec.ONE, ClientTestInstanceBlockActionPacket.Action.RUN, new ClientTestInstanceBlockActionPacket.Data(null, new Vec(5, 5, 5), 1, true, ClientTestInstanceBlockActionPacket.Status.RUNNING, Component.text("Error!"))));
        // Appelle une méthode
        addClientPackets(new ClientSetGameRulesPacket(List.of()), new ClientSetGameRulesPacket(List.of(new ClientSetGameRulesPacket.Entry(Objects.requireNonNull(GameRule.staticRegistry().getKey(GameRule.MOB_DROPS)), "false"))));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> void testPacket(NetworkBuffer.Type<T> networkType, T packet, Env env) {
        // Appelle une méthode
        byte[] bytes = NetworkBuffer.makeArray(networkType, packet, env.process());
        // Affecte une valeur
        var buffer = NetworkBuffer.wrap(bytes, 0, bytes.length, env.process()); // Requires for serialization of some packets
        // Appelle une méthode
        var createdPacket = buffer.read(networkType);
        // Appelle une méthode
        assertEquals(packet, createdPacket);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> Stream<Arguments> packets(PacketParser<T> parser, Map<Class<? extends T>, ? extends Collection<T>> map) {
        // Renvoie une valeur à l'appelant
        return Stream.of(
                // Instruction de code
                parser.handshake(),
                // Instruction de code
                parser.status(),
                // Instruction de code
                parser.login(),
                // Instruction de code
                parser.configuration(),
                // Instruction de code
                parser.play()
        // Appelle une méthode
        ).flatMap(it -> packets(it, map));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> Stream<Arguments> packets(PacketRegistry<? extends T> registry, Map<Class<? extends T>, ? extends Collection<T>> map) {
        // Renvoie une valeur à l'appelant
        return registry.packets().stream().flatMap(info -> {
            // Appelle une méthode
            var tests = map.get(info.packetClass());
            // Appelle une méthode
            var name = info.packetClass().getSimpleName();
            // Appelle une méthode
            assertNotNull(tests, "No packet tests for %s".formatted(name));
            // Appelle une méthode
            assertNotEquals(0, tests.size(), "Empty packet tests for %s".formatted(name));

            // Appelle une méthode
            var serializer = info.serializer();
            // Renvoie une valeur à l'appelant
            return tests.stream().map(packet ->
                    // Instruction de code
                    Arguments.of(serializer, packet)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Stream<Arguments> serverPacketArguments() {
        // Renvoie une valeur à l'appelant
        return packets(PacketVanilla.SERVER_PACKET_PARSER, SERVER_PACKETS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Stream<Arguments> clientPacketArguments() {
        // Renvoie une valeur à l'appelant
        return packets(PacketVanilla.CLIENT_PACKET_PARSER, CLIENT_PACKETS);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest(name = "Server Packet Test: {1}")
    // Annotation pour l'élément suivant
    @MethodSource("serverPacketArguments")
    // Début d'une méthode/d'un bloc
    void serverPacket(NetworkBuffer.Type<ServerPacket> serializer, ServerPacket packet, Env env) {
        // Appelle une méthode
        testPacket(serializer, packet, env);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest(name = "Client Packet Test: {1}")
    // Annotation pour l'élément suivant
    @MethodSource("clientPacketArguments")
    // Début d'une méthode/d'un bloc
    void clientPacket(NetworkBuffer.Type<ClientPacket> serializer, ClientPacket packet, Env env) {
        // Appelle une méthode
        testPacket(serializer, packet, env);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
