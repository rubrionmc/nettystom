// Package declaration for this file
package net.minestom.server.entity.player;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.*;
// Import of a required class
import net.minestom.server.entity.damage.DamageType;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.event.player.PlayerChunkUnloadEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerGameModeChangeEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerInputEvent;
// Import of a required class
import net.minestom.server.listener.PlayerInputListener;
// Import of a required class
import net.minestom.server.message.ChatMessageType;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientSettingsPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientInputPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.network.player.ClientSettings;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.testing.Collector;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.EnumSet;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Locale;
// Import of a required class
import java.util.function.Predicate;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerIntegrationTest {

    /**
     * Test to see whether player abilities are updated correctly and events
     * are handled properly when changing gamemode.
     */
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void gamemodeTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Abilities
        // Start of a block
        {
            // Calls a method
            player.setGameMode(GameMode.CREATIVE);
            // Calls a method
            assertAbilities(player, true, false, true, true);
            // Calls a method
            player.setGameMode(GameMode.SPECTATOR);
            // Calls a method
            assertAbilities(player, true, true, true, false);
            // Calls a method
            player.setGameMode(GameMode.CREATIVE);
            // Calls a method
            assertAbilities(player, true, true, true, true);
            // Calls a method
            player.setGameMode(GameMode.ADVENTURE);
            // Calls a method
            assertAbilities(player, false, false, false, false);
            // Calls a method
            player.setGameMode(GameMode.SURVIVAL);
            // Calls a method
            assertAbilities(player, false, false, false, false);
        // End of a block/expression
        }

        // Calls a method
        var listener = env.listen(PlayerGameModeChangeEvent.class);
        // Normal change
        // Start of a block
        {
            // Calls a method
            listener.followup();
            // Calls a method
            assertTrue(player.setGameMode(GameMode.ADVENTURE));
        // End of a block/expression
        }
        // Change target gamemode event
        // Start of a block
        {
            // Calls a method
            listener.followup(event -> event.setNewGameMode(GameMode.SPECTATOR));
            // Calls a method
            assertTrue(player.setGameMode(GameMode.CREATIVE));
            // Calls a method
            assertEquals(GameMode.SPECTATOR, player.getGameMode());
        // End of a block/expression
        }
        // Cancel event
        // Start of a block
        {
            // Calls a method
            listener.followup(event -> event.setCancelled(true));
            // Calls a method
            assertFalse(player.setGameMode(GameMode.CREATIVE));
            // Calls a method
            assertEquals(GameMode.SPECTATOR, player.getGameMode());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void handSwapTest(Env env) {
        // Assigns a value
        ClientSettingsPacket packet = new ClientSettingsPacket(new ClientSettings(
                // Code statement
                Locale.US, (byte) 16,
                // Code statement
                ChatMessageType.FULL, true,
                // Code statement
                (byte) 127, MainHand.LEFT,
                // Code statement
                true, true,
                // Code statement
                ClientSettings.ParticleSetting.ALL
        // Code statement
        ));

        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());
        // Calls a method
        env.tick();
        // Calls a method
        env.tick();

        // Calls a method
        player.addPacketToQueue(packet);
        // Calls a method
        var collector = connection.trackIncoming();
        // Calls a method
        env.tick();
        // Calls a method
        env.tick();
        // Calls a method
        assertEquals(MainHand.LEFT, player.getSettings().mainHand());

        // Assigns a value
        boolean found = false;
        // Loop: repeats a block
        for (ServerPacket serverPacket : collector.collect()) {
            // Branch: checks a condition
            if (!(serverPacket instanceof EntityMetaDataPacket metaDataPacket)) {
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }
            // Code statement
            assertEquals(MainHand.LEFT, metaDataPacket.entries().get(MetadataDef.Player.MAIN_HAND.index()).value(),
                    // Code statement
                    "EntityMetaDataPacket has the incorrect hand after client settings update.");
            // Assigns a value
            found = true;
        // End of a block/expression
        }
        // Calls a method
        assertTrue(found, "EntityMetaDataPacket not sent after client settings update.");

        // Calls a method
        assertEquals(ClientSettings.ParticleSetting.ALL, player.getSettings().particleSetting());
    // End of a block/expression
    }

    // Code statement
    private void assertAbilities(Player player, boolean isInvulnerable, boolean isFlying, boolean isAllowFlying,
                                 // Start of a method/block
                                 boolean isInstantBreak) {
        // Calls a method
        assertEquals(isInvulnerable, player.isInvulnerable());
        // Calls a method
        assertEquals(isFlying, player.isFlying());
        // Calls a method
        assertEquals(isAllowFlying, player.isAllowFlying());
        // Calls a method
        assertEquals(isInstantBreak, player.isInstantBreak());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void playerJoinPackets(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();

        // Assigns a value
        final var packets = List.of(
                // Code statement
                JoinGamePacket.class, ServerDifficultyPacket.class, SpawnPositionPacket.class,
                // Code statement
                DeclareCommandsPacket.class, EntityAttributesPacket.class, EntityStatusPacket.class,
                // Code statement
                UpdateHealthPacket.class, PlayerAbilitiesPacket.class
        // End of a block/expression
        );
        // Calls a method
        final List<Collector<?>> trackers = new ArrayList<>();
        // Loop: repeats a block
        for (var packet : packets) {
            // Calls a method
            trackers.add(connection.trackIncoming(packet));
        // End of a block/expression
        }

        // Calls a method
        var trackerAll = connection.trackIncoming(ServerPacket.class);

        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());
        // Calls a method
        assertEquals(new Pos(0, 40, 0), player.getPosition());

        // Loop: repeats a block
        for (var tracker : trackers) {
            // Calls a method
            assertEquals(1, tracker.collect().size());
        // End of a block/expression
        }
        // Calls a method
        assertTrue(trackerAll.collect().size() > packets.size());
    // End of a block/expression
    }

    /**
     * Test to see whether the packets from Player#refreshPlayer are sent
     * when changing dimensions
     */
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void refreshPlayerTest(Env env) {
        // Assigns a value
        final int TEST_PERMISSION_LEVEL = 2;
        // Calls a method
        final var testDimension = env.process().dimensionType().register(Key.key("minestom:test_dimension"), DimensionType.builder().build());

        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var instance2 = env.process().instance().createInstanceContainer(testDimension);

        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Calls a method
        var tracker1 = connection.trackIncoming(UpdateHealthPacket.class);
        // Calls a method
        var tracker2 = connection.trackIncoming(SetExperiencePacket.class);
        // Calls a method
        var trackerStatus = connection.trackIncoming(EntityStatusPacket.class);
        // Calls a method
        var tracker4 = connection.trackIncoming(PlayerAbilitiesPacket.class);

        // Calls a method
        player.setPermissionLevel(TEST_PERMISSION_LEVEL);

        // #join may cause the thread to hang as scheduled for the next tick when initially in a pool
        // Calls a method
        Assertions.assertTimeout(Duration.ofSeconds(2), () -> player.setInstance(instance2).join());
        // Calls a method
        assertEquals(instance2, player.getInstance());

        // Calls a method
        assertEquals(1, tracker1.collect().size());
        // Calls a method
        assertEquals(1, tracker2.collect().size());
        // Calls a method
        assertEquals(2, trackerStatus.collect().size());
        // Calls a method
        assertEquals(1, tracker4.collect().size());

        // Ensure that the player was sent the permission levels
        // Loop: repeats a block
        for (var statusPacket : trackerStatus.collect()) {
            // Calls a method
            assertEquals(player.getEntityId(), statusPacket.entityId());
            // Calls a method
            assertEquals(EntityStatuses.Player.PERMISSION_LEVEL_2, statusPacket.status());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void deathLocationTest(Env env) {
        // Assigns a value
        String dimensionNamespace = "minestom:test_dimension";
        // Calls a method
        final var testDimension = env.process().dimensionType().register(Key.key(dimensionNamespace), DimensionType.builder().build());

        // Calls a method
        var instance = env.process().instance().createInstanceContainer(testDimension);
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(5, 42, 2));

        // Calls a method
        assertNull(player.getDeathLocation());
        // Calls a method
        player.damage(DamageType.OUT_OF_WORLD, 30);

        // Calls a method
        assertNotNull(player.getDeathLocation());
        // Calls a method
        assertEquals(dimensionNamespace, player.getDeathLocation().dimension());
        // Calls a method
        assertEquals(5, player.getDeathLocation().blockPosition().x());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fullInfoSync(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Calls a method
        var _ = connection.connect(instance, new Pos(0, 42, 0));
        // Code statement
        tracker.assertSingle(
                // Code statement
                it -> assertEquals(EnumSet.allOf(PlayerInfoUpdatePacket.Action.class), it.actions(), "Not fully synced on join")
        // End of a block/expression
        );

        // Calls a method
        var connection2 = env.createConnection();
        // Calls a method
        var tracker2 = connection2.trackIncoming(PlayerInfoUpdatePacket.class);
        // Calls a method
        var _ = connection2.connect(instance, new Pos(5, 42, 0));
        // Calls a method
        tracker2.assertCount(2, packet -> packet.actions().equals(EnumSet.allOf(PlayerInfoUpdatePacket.Action.class)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void displayNameTest(Env env) {
        // Code statement
        Predicate<PlayerInfoUpdatePacket> predicate =
                // Calls a method
                packet -> packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME);
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        player.setDisplayName(Component.text("Display Name!"));

        // Calls a method
        var connection2 = env.createConnection();
        // Calls a method
        var tracker2 = connection2.trackIncoming(PlayerInfoUpdatePacket.class);
        // Calls a method
        connection2.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        tracker2.assertCount(2, predicate);

        // Calls a method
        var tracker3 = connection2.trackIncoming(PlayerInfoUpdatePacket.class);

        // Calls a method
        player.setDisplayName(Component.text("Other Name!"));

        // Calls a method
        tracker3.assertCount(1, predicate);
        // Calls a method
        tracker.assertCount(4, predicate);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void gameModeInfoTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Code statement
        tracker.assertCount(1, packet ->
                // Calls a method
                packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE));
        // Calls a method
        var tracker2 = connection.trackIncoming(PlayerInfoUpdatePacket.class);

        // Calls a method
        player.setGameMode(GameMode.CREATIVE);

        // Code statement
        tracker2.assertCount(1, packet ->
                // Calls a method
                packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void latencyTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Code statement
        tracker.assertCount(1, packet ->
                // Calls a method
                packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_LATENCY));

        // Calls a method
        var tracker2 = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Calls a method
        player.refreshLatency(100);

        // Code statement
        tracker2.assertCount(1, packet ->
                // Calls a method
                packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_LATENCY));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void listedTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));


        // Calls a method
        assertTrue(player.isListed());

        // Calls a method
        player.setListed(false);

        // Assigns a value
        var listedPackets = tracker.collect().stream().filter((packet) ->
                        // Code statement
                        packet.actions().stream().anyMatch((act) -> act == PlayerInfoUpdatePacket.Action.UPDATE_LISTED))
                // Calls a method
                .count();

        // Calls a method
        assertEquals(2, listedPackets);
        // Calls a method
        assertFalse(player.isListed());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void listOrderTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        assertEquals(0, player.getListOrder());

        // Calls a method
        player.setListOrder(1);

        // Assigns a value
        var orderPackets = tracker.collect().stream().filter((packet) ->
                        // Code statement
                        packet.actions().stream().anyMatch((act) -> act == PlayerInfoUpdatePacket.Action.UPDATE_LIST_ORDER))
                // Calls a method
                .count();

        // Calls a method
        assertEquals(2, orderPackets);
        // Calls a method
        assertEquals(1, player.getListOrder());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void setView(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        Pos startingPlayerPos = new Pos(0, 42, 0);
        // Calls a method
        var player = connection.connect(instance, startingPlayerPos);

        // Calls a method
        var tracker = connection.trackIncoming(PlayerPositionAndLookPacket.class);
        // Calls a method
        player.setView(30, 20);

        // Calls a method
        assertEquals(startingPlayerPos.withView(30, 20), player.getPosition());
        // Start of a method/block
        tracker.assertSingle(PlayerPositionAndLookPacket.class, packet -> {
            // Should be relative coord and velocity because we are only trying to change the view.
            // Calls a method
            assertEquals(RelativeFlags.COORD | RelativeFlags.DELTA_COORD, packet.flags());
            // Calls a method
            assertEquals(new Pos(0, 0, 0, 30, 20), packet.position());
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lookAt(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var tracker = connection.trackIncoming(FacePlayerPacket.class);
        // Calls a method
        Pos startingPlayerPos = new Pos(0, 42, 0);
        // Calls a method
        var player = connection.connect(instance, startingPlayerPos);

        // Calls a method
        Point pointLookAt = new Vec(3, 3, 3);
        // Calls a method
        player.lookAt(pointLookAt);
        // Calls a method
        tracker.assertSingle(FacePlayerPacket.class, packet -> assertEquals(pointLookAt, packet.target()));

        // Calls a method
        tracker = connection.trackIncoming(FacePlayerPacket.class);
        // Calls a method
        Entity entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(player.getInstance(), new Pos(9, 9, 9));
        // Calls a method
        player.lookAt(entity);
        // Calls a method
        tracker.assertSingle(FacePlayerPacket.class, packet -> assertEquals(entity.getEntityId(), packet.entityId()));

        // Calls a method
        assertEquals(startingPlayerPos, player.getPosition());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void removePlayer(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        Player player = connection.connect(instance, new Pos(0, 40, 0));

        // Start of a method/block
        instance.eventNode().addListener(PlayerChunkUnloadEvent.class, event -> {
            // Calls a method
            assertEquals(instance, event.getInstance());
            // Calls a method
            assertEquals(player, event.getPlayer());
        // End of a block/expression
        });

        // Calls a method
        player.remove(true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void inputsPressed(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        var events = env.trackEvent(PlayerInputEvent.class, EventFilter.PLAYER, player);
        // Code statement
        PlayerInputListener.listener(
                // Creates a new object
                new ClientInputPacket(true, true, true, true, true, true, true),
                // Code statement
                player
        // End of a block/expression
        );

        // Start of a method/block
        events.assertSingle(event -> {
            // Calls a method
            assertFalse(event.hasReleasedForwardKey());
            // Calls a method
            assertFalse(event.hasReleasedBackwardKey());
            // Calls a method
            assertFalse(event.hasReleasedLeftKey());
            // Calls a method
            assertFalse(event.hasReleasedRightKey());
            // Calls a method
            assertFalse(event.hasReleasedJumpKey());
            // Calls a method
            assertFalse(event.hasReleasedShiftKey());
            // Calls a method
            assertFalse(event.hasReleasedSprintKey());

            // Calls a method
            assertTrue(event.hasPressedForwardKey());
            // Calls a method
            assertTrue(event.hasPressedBackwardKey());
            // Calls a method
            assertTrue(event.hasPressedLeftKey());
            // Calls a method
            assertTrue(event.hasPressedRightKey());
            // Calls a method
            assertTrue(event.hasPressedJumpKey());
            // Calls a method
            assertTrue(event.hasPressedShiftKey());
            // Calls a method
            assertTrue(event.hasPressedSprintKey());

            // Calls a method
            assertTrue(event.isHoldingForwardKey());
            // Calls a method
            assertTrue(event.isHoldingBackwardKey());
            // Calls a method
            assertTrue(event.isHoldingLeftKey());
            // Calls a method
            assertTrue(event.isHoldingRightKey());
            // Calls a method
            assertTrue(event.isHoldingJumpKey());
            // Calls a method
            assertTrue(event.isHoldingShiftKey());
            // Calls a method
            assertTrue(event.isHoldingSprintKey());
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void inputsReleased(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Code statement
        PlayerInputListener.listener(
                // Creates a new object
                new ClientInputPacket(true, true, true, true, true, true, true),
                // Code statement
                player
        // End of a block/expression
        );

        // Calls a method
        var events = env.trackEvent(PlayerInputEvent.class, EventFilter.PLAYER, player);
        // Code statement
        PlayerInputListener.listener(
                // Creates a new object
                new ClientInputPacket(false, false, false, false, false, false, false),
                // Code statement
                player
        // End of a block/expression
        );

        // Start of a method/block
        events.assertSingle(event -> {
            // Calls a method
            assertFalse(event.hasPressedForwardKey());
            // Calls a method
            assertFalse(event.hasPressedBackwardKey());
            // Calls a method
            assertFalse(event.hasPressedLeftKey());
            // Calls a method
            assertFalse(event.hasPressedRightKey());
            // Calls a method
            assertFalse(event.hasPressedJumpKey());
            // Calls a method
            assertFalse(event.hasPressedShiftKey());
            // Calls a method
            assertFalse(event.hasPressedSprintKey());

            // Calls a method
            assertTrue(event.hasReleasedForwardKey());
            // Calls a method
            assertTrue(event.hasReleasedBackwardKey());
            // Calls a method
            assertTrue(event.hasReleasedLeftKey());
            // Calls a method
            assertTrue(event.hasReleasedRightKey());
            // Calls a method
            assertTrue(event.hasReleasedJumpKey());
            // Calls a method
            assertTrue(event.hasReleasedShiftKey());
            // Calls a method
            assertTrue(event.hasReleasedSprintKey());

            // Calls a method
            assertFalse(event.isHoldingForwardKey());
            // Calls a method
            assertFalse(event.isHoldingBackwardKey());
            // Calls a method
            assertFalse(event.isHoldingLeftKey());
            // Calls a method
            assertFalse(event.isHoldingRightKey());
            // Calls a method
            assertFalse(event.isHoldingJumpKey());
            // Calls a method
            assertFalse(event.isHoldingShiftKey());
            // Calls a method
            assertFalse(event.isHoldingSprintKey());
        // End of a block/expression
        });
    // End of a block/expression
    }

// End of a block/expression
}
