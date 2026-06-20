// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Metadata;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerSkin;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockEntityType;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientVehicleMovePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.DisconnectPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.PingResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.LoginDisconnectPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.LoginSuccessPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.SetCompressionPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.status.ResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
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
import org.junit.jupiter.api.BeforeAll;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ensures that packet can be written and read correctly.
 */
// Déclaration de type (classe/interface/enum/record)
public class PacketWriteReadTest {
    // Affecte une valeur
    private static final List<ServerPacket> SERVER_PACKETS = new ArrayList<>();
    // Affecte une valeur
    private static final List<ClientPacket> CLIENT_PACKETS = new ArrayList<>();

    // Appelle une méthode
    private static final Component COMPONENT = Component.text("Hey");
    // Appelle une méthode
    private static final Vec VEC = new Vec(5, 5, 5);

    // Annotation pour l'élément suivant
    @BeforeAll
    // Début d'une méthode/d'un bloc
    public static void setupServer() {
        // Instruction de code
        MinecraftServer.init(); // Need some tags in here, pretty gross.

        // Handshake
        // Appelle une méthode
        SERVER_PACKETS.add(new ResponsePacket(new JsonObject().toString()));
        // Status
        // Appelle une méthode
        SERVER_PACKETS.add(new PingResponsePacket(5));
        // Login
        //SERVER_PACKETS.add(new EncryptionRequestPacket("server", generateByteArray(16), generateByteArray(16)));
        // Appelle une méthode
        SERVER_PACKETS.add(new LoginDisconnectPacket(COMPONENT));
        //SERVER_PACKETS.add(new LoginPluginRequestPacket(5, "id", generateByteArray(16)));
        // Appelle une méthode
        SERVER_PACKETS.add(new LoginSuccessPacket(new GameProfile(UUID.randomUUID(), "TheMode911")));
        // Appelle une méthode
        SERVER_PACKETS.add(new SetCompressionPacket(256));
        // Play
        // Appelle une méthode
        SERVER_PACKETS.add(new AcknowledgeBlockChangePacket(0));
        // Appelle une méthode
        SERVER_PACKETS.add(new ActionBarPacket(COMPONENT));
        // Appelle une méthode
        SERVER_PACKETS.add(new AttachEntityPacket(5, 10));
        // Appelle une méthode
        SERVER_PACKETS.add(new BlockActionPacket(VEC, (byte) 5, (byte) 5, 5));
        // Appelle une méthode
        SERVER_PACKETS.add(new BlockBreakAnimationPacket(5, VEC, (byte) 5));
        // Appelle une méthode
        SERVER_PACKETS.add(new BlockChangePacket(VEC, 0));
        // Appelle une méthode
        SERVER_PACKETS.add(new BlockEntityDataPacket(VEC, BlockEntityType.SIGN, CompoundBinaryTag.builder().putString("key", "value").build()));
        // Appelle une méthode
        SERVER_PACKETS.add(new BossBarPacket(UUID.randomUUID(), new BossBarPacket.AddAction(COMPONENT, 5f, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS, (byte) 2)));
        // Appelle une méthode
        SERVER_PACKETS.add(new BossBarPacket(UUID.randomUUID(), new BossBarPacket.RemoveAction()));
        // Appelle une méthode
        SERVER_PACKETS.add(new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateHealthAction(5f)));
        // Appelle une méthode
        SERVER_PACKETS.add(new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateTitleAction(COMPONENT)));
        // Appelle une méthode
        SERVER_PACKETS.add(new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateStyleAction(BossBar.Color.BLUE, BossBar.Overlay.PROGRESS)));
        // Appelle une méthode
        SERVER_PACKETS.add(new BossBarPacket(UUID.randomUUID(), new BossBarPacket.UpdateFlagsAction((byte) 5)));
        // Appelle une méthode
        SERVER_PACKETS.add(new CameraPacket(5));
        // Appelle une méthode
        SERVER_PACKETS.add(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, 2));
        // Appelle une méthode
        SERVER_PACKETS.add(new SystemChatPacket(COMPONENT, false));
        // Appelle une méthode
        SERVER_PACKETS.add(new ClearTitlesPacket(false));
        // Appelle une méthode
        SERVER_PACKETS.add(new CloseWindowPacket((byte) 2));
        // Appelle une méthode
        SERVER_PACKETS.add(new CollectItemPacket(5, 5, 5));
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
        SERVER_PACKETS.add(new PlaceGhostRecipePacket(0, recipeDisplay));
        // Appelle une méthode
        SERVER_PACKETS.add(new DeathCombatEventPacket(5, COMPONENT));
        // Instruction de code
        SERVER_PACKETS.add(new DeclareRecipesPacket(Map.of(
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
        SERVER_PACKETS.add(new RecipeBookAddPacket(List.of(new RecipeBookAddPacket.Entry(1, recipeDisplay, null,
                // Appelle une méthode
                RecipeBookCategory.CRAFTING_MISC, List.of(new Ingredient(Material.STONE)), true, true)), false));
        // Appelle une méthode
        SERVER_PACKETS.add(new RecipeBookRemovePacket(List.of(1)));

        // Appelle une méthode
        SERVER_PACKETS.add(new DestroyEntitiesPacket(List.of(5, 5, 5)));
        // Appelle une méthode
        SERVER_PACKETS.add(new DisconnectPacket(COMPONENT));
        // Appelle une méthode
        SERVER_PACKETS.add(new DisplayScoreboardPacket((byte) 5, "scoreboard"));
        // Appelle une méthode
        SERVER_PACKETS.add(new WorldEventPacket(5, VEC, 5, false));
        // Appelle une méthode
        SERVER_PACKETS.add(new EndCombatEventPacket(5));
        // Appelle une méthode
        SERVER_PACKETS.add(new EnterCombatEventPacket());
        // Appelle une méthode
        SERVER_PACKETS.add(new EntityAnimationPacket(5, EntityAnimationPacket.Animation.TAKE_DAMAGE));
        // Appelle une méthode
        SERVER_PACKETS.add(new EntityEquipmentPacket(6, Map.of(EquipmentSlot.MAIN_HAND, ItemStack.of(Material.DIAMOND_SWORD))));
        // Appelle une méthode
        SERVER_PACKETS.add(new EntityHeadLookPacket(5, 90f));
        // Appelle une méthode
        SERVER_PACKETS.add(new EntityMetaDataPacket(5, Map.of()));
        // Appelle une méthode
        SERVER_PACKETS.add(new EntityMetaDataPacket(5, Map.of(1, Metadata.VarInt(5))));
        // Appelle une méthode
        SERVER_PACKETS.add(new EntityPositionAndRotationPacket(5, (short) 0, (short) 0, (short) 0, 45f, 45f, false));
        // Appelle une méthode
        SERVER_PACKETS.add(new EntityPositionPacket(5, (short) 0, (short) 0, (short) 0, true));
        // Appelle une méthode
        SERVER_PACKETS.add(new EntityAttributesPacket(5, List.of()));
        // Appelle une méthode
        SERVER_PACKETS.add(new EntityRotationPacket(5, 45f, 45f, false));

        // Appelle une méthode
        final PlayerSkin skin = new PlayerSkin("hh", "hh");
        // Appelle une méthode
        List<PlayerInfoUpdatePacket.Property> prop = List.of(new PlayerInfoUpdatePacket.Property("textures", skin.textures(), skin.signature()));

        // Instruction de code
        SERVER_PACKETS.add(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.ADD_PLAYER,
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "TheMode911", prop, false, 0, GameMode.SURVIVAL, null, null, 0, true)));
        // Instruction de code
        SERVER_PACKETS.add(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME,
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.SURVIVAL, Component.text("NotTheMode911"), null, 0, true)));
        // Instruction de code
        SERVER_PACKETS.add(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE,
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.CREATIVE, null, null, 0, true)));
        // Instruction de code
        SERVER_PACKETS.add(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_LATENCY,
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 20, GameMode.SURVIVAL, null, null, 0, true)));
        // Instruction de code
        SERVER_PACKETS.add(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_LISTED,
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), true, 0, GameMode.SURVIVAL, null, null, 0, true)));
        // Instruction de code
        SERVER_PACKETS.add(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_LIST_ORDER,
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.SURVIVAL, null, null, 42, true)));
        // Instruction de code
        SERVER_PACKETS.add(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_HAT,
                // Crée un nouvel objet
                new PlayerInfoUpdatePacket.Entry(UUID.randomUUID(), "", List.of(), false, 0, GameMode.SURVIVAL, null, null, 0, false)));
        // Appelle une méthode
        SERVER_PACKETS.add(new PlayerInfoRemovePacket(UUID.randomUUID()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @BeforeAll
    // Début d'une méthode/d'un bloc
    public static void setupClient() {
        // Appelle une méthode
        CLIENT_PACKETS.add(new ClientHandshakePacket(755, "localhost", 25565, ClientHandshakePacket.Intent.LOGIN));
        // Appelle une méthode
        CLIENT_PACKETS.add(new ClientVehicleMovePacket(new Pos(5, 5, 5, 45f, 45f), true));
        // Appelle une méthode
        CLIENT_PACKETS.add(new ClientVehicleMovePacket(new Pos(6, 5, 6, 82f, 12.5f), false));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void serverTest() throws NoSuchFieldException, IllegalAccessException {
        // Boucle : répète un bloc
        for (var packet : SERVER_PACKETS) {
            // Appelle une méthode
            var packetClass = packet.getClass();
            // Appelle une méthode
            NetworkBuffer.Type<ServerPacket> serializer = (NetworkBuffer.Type<ServerPacket>) packetClass.getField("SERIALIZER").get(packetClass);
            // Appelle une méthode
            testPacket(serializer, packet);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void clientTest() throws NoSuchFieldException, IllegalAccessException {
        // Boucle : répète un bloc
        for (var packet : CLIENT_PACKETS) {
            // Appelle une méthode
            var packetClass = packet.getClass();
            // Appelle une méthode
            NetworkBuffer.Type<ClientPacket> serializer = (NetworkBuffer.Type<ClientPacket>) packetClass.getField("SERIALIZER").get(packetClass);
            // Appelle une méthode
            testPacket(serializer, packet);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> void testPacket(NetworkBuffer.Type<T> networkType, T packet) {
        // Appelle une méthode
        byte[] bytes = NetworkBuffer.makeArray(networkType, packet);
        // Appelle une méthode
        NetworkBuffer reader = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        reader.write(NetworkBuffer.RAW_BYTES, bytes);
        // Appelle une méthode
        var createdPacket = networkType.read(reader);
        // Appelle une méthode
        assertEquals(packet, createdPacket);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
