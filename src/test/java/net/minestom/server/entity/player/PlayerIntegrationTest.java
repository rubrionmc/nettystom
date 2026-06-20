// Déclaration du paquet de ce fichier
package net.minestom.server.entity.player;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.DamageType;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerChunkUnloadEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerGameModeChangeEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerInputEvent;
// Import d'une classe nécessaire
import net.minestom.server.listener.PlayerInputListener;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatMessageType;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientSettingsPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientInputPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ClientSettings;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.testing.Collector;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.EnumSet;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Locale;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerIntegrationTest {

    /**
     * Test to see whether player abilities are updated correctly and events
     * are handled properly when changing gamemode.
     */
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void gamemodeTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Abilities
        // Début d'un bloc
        {
            // Appelle une méthode
            player.setGameMode(GameMode.CREATIVE);
            // Appelle une méthode
            assertAbilities(player, true, false, true, true);
            // Appelle une méthode
            player.setGameMode(GameMode.SPECTATOR);
            // Appelle une méthode
            assertAbilities(player, true, true, true, false);
            // Appelle une méthode
            player.setGameMode(GameMode.CREATIVE);
            // Appelle une méthode
            assertAbilities(player, true, true, true, true);
            // Appelle une méthode
            player.setGameMode(GameMode.ADVENTURE);
            // Appelle une méthode
            assertAbilities(player, false, false, false, false);
            // Appelle une méthode
            player.setGameMode(GameMode.SURVIVAL);
            // Appelle une méthode
            assertAbilities(player, false, false, false, false);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var listener = env.listen(PlayerGameModeChangeEvent.class);
        // Normal change
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup();
            // Appelle une méthode
            assertTrue(player.setGameMode(GameMode.ADVENTURE));
        // Fin d'un bloc/d'une expression
        }
        // Change target gamemode event
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup(event -> event.setNewGameMode(GameMode.SPECTATOR));
            // Appelle une méthode
            assertTrue(player.setGameMode(GameMode.CREATIVE));
            // Appelle une méthode
            assertEquals(GameMode.SPECTATOR, player.getGameMode());
        // Fin d'un bloc/d'une expression
        }
        // Cancel event
        // Début d'un bloc
        {
            // Appelle une méthode
            listener.followup(event -> event.setCancelled(true));
            // Appelle une méthode
            assertFalse(player.setGameMode(GameMode.CREATIVE));
            // Appelle une méthode
            assertEquals(GameMode.SPECTATOR, player.getGameMode());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void handSwapTest(Env env) {
        // Affecte une valeur
        ClientSettingsPacket packet = new ClientSettingsPacket(new ClientSettings(
                // Instruction de code
                Locale.US, (byte) 16,
                // Instruction de code
                ChatMessageType.FULL, true,
                // Instruction de code
                (byte) 127, MainHand.LEFT,
                // Instruction de code
                true, true,
                // Instruction de code
                ClientSettings.ParticleSetting.ALL
        // Instruction de code
        ));

        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());
        // Appelle une méthode
        env.tick();
        // Appelle une méthode
        env.tick();

        // Appelle une méthode
        player.addPacketToQueue(packet);
        // Appelle une méthode
        var collector = connection.trackIncoming();
        // Appelle une méthode
        env.tick();
        // Appelle une méthode
        env.tick();
        // Appelle une méthode
        assertEquals(MainHand.LEFT, player.getSettings().mainHand());

        // Affecte une valeur
        boolean found = false;
        // Boucle : répète un bloc
        for (ServerPacket serverPacket : collector.collect()) {
            // Embranchement : vérifie une condition
            if (!(serverPacket instanceof EntityMetaDataPacket metaDataPacket)) {
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }
            // Instruction de code
            assertEquals(MainHand.LEFT, metaDataPacket.entries().get(MetadataDef.Player.MAIN_HAND.index()).value(),
                    // Instruction de code
                    "EntityMetaDataPacket has the incorrect hand after client settings update.");
            // Affecte une valeur
            found = true;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertTrue(found, "EntityMetaDataPacket not sent after client settings update.");

        // Appelle une méthode
        assertEquals(ClientSettings.ParticleSetting.ALL, player.getSettings().particleSetting());
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private void assertAbilities(Player player, boolean isInvulnerable, boolean isFlying, boolean isAllowFlying,
                                 // Début d'une méthode/d'un bloc
                                 boolean isInstantBreak) {
        // Appelle une méthode
        assertEquals(isInvulnerable, player.isInvulnerable());
        // Appelle une méthode
        assertEquals(isFlying, player.isFlying());
        // Appelle une méthode
        assertEquals(isAllowFlying, player.isAllowFlying());
        // Appelle une méthode
        assertEquals(isInstantBreak, player.isInstantBreak());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void playerJoinPackets(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();

        // Affecte une valeur
        final var packets = List.of(
                // Instruction de code
                JoinGamePacket.class, ServerDifficultyPacket.class, SpawnPositionPacket.class,
                // Instruction de code
                DeclareCommandsPacket.class, EntityAttributesPacket.class, EntityStatusPacket.class,
                // Instruction de code
                UpdateHealthPacket.class, PlayerAbilitiesPacket.class
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        final List<Collector<?>> trackers = new ArrayList<>();
        // Boucle : répète un bloc
        for (var packet : packets) {
            // Appelle une méthode
            trackers.add(connection.trackIncoming(packet));
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var trackerAll = connection.trackIncoming(ServerPacket.class);

        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());
        // Appelle une méthode
        assertEquals(new Pos(0, 40, 0), player.getPosition());

        // Boucle : répète un bloc
        for (var tracker : trackers) {
            // Appelle une méthode
            assertEquals(1, tracker.collect().size());
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertTrue(trackerAll.collect().size() > packets.size());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Test to see whether the packets from Player#refreshPlayer are sent
     * when changing dimensions
     */
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void refreshPlayerTest(Env env) {
        // Affecte une valeur
        final int TEST_PERMISSION_LEVEL = 2;
        // Appelle une méthode
        final var testDimension = env.process().dimensionType().register(Key.key("minestom:test_dimension"), DimensionType.builder().build());

        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var instance2 = env.process().instance().createInstanceContainer(testDimension);

        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Appelle une méthode
        var tracker1 = connection.trackIncoming(UpdateHealthPacket.class);
        // Appelle une méthode
        var tracker2 = connection.trackIncoming(SetExperiencePacket.class);
        // Appelle une méthode
        var trackerStatus = connection.trackIncoming(EntityStatusPacket.class);
        // Appelle une méthode
        var tracker4 = connection.trackIncoming(PlayerAbilitiesPacket.class);

        // Appelle une méthode
        player.setPermissionLevel(TEST_PERMISSION_LEVEL);

        // #join may cause the thread to hang as scheduled for the next tick when initially in a pool
        // Appelle une méthode
        Assertions.assertTimeout(Duration.ofSeconds(2), () -> player.setInstance(instance2).join());
        // Appelle une méthode
        assertEquals(instance2, player.getInstance());

        // Appelle une méthode
        assertEquals(1, tracker1.collect().size());
        // Appelle une méthode
        assertEquals(1, tracker2.collect().size());
        // Appelle une méthode
        assertEquals(2, trackerStatus.collect().size());
        // Appelle une méthode
        assertEquals(1, tracker4.collect().size());

        // Ensure that the player was sent the permission levels
        // Boucle : répète un bloc
        for (var statusPacket : trackerStatus.collect()) {
            // Appelle une méthode
            assertEquals(player.getEntityId(), statusPacket.entityId());
            // Appelle une méthode
            assertEquals(EntityStatuses.Player.PERMISSION_LEVEL_2, statusPacket.status());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void deathLocationTest(Env env) {
        // Affecte une valeur
        String dimensionNamespace = "minestom:test_dimension";
        // Appelle une méthode
        final var testDimension = env.process().dimensionType().register(Key.key(dimensionNamespace), DimensionType.builder().build());

        // Appelle une méthode
        var instance = env.process().instance().createInstanceContainer(testDimension);
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(5, 42, 2));

        // Appelle une méthode
        assertNull(player.getDeathLocation());
        // Appelle une méthode
        player.damage(DamageType.OUT_OF_WORLD, 30);

        // Appelle une méthode
        assertNotNull(player.getDeathLocation());
        // Appelle une méthode
        assertEquals(dimensionNamespace, player.getDeathLocation().dimension());
        // Appelle une méthode
        assertEquals(5, player.getDeathLocation().blockPosition().x());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fullInfoSync(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Appelle une méthode
        var _ = connection.connect(instance, new Pos(0, 42, 0));
        // Instruction de code
        tracker.assertSingle(
                // Instruction de code
                it -> assertEquals(EnumSet.allOf(PlayerInfoUpdatePacket.Action.class), it.actions(), "Not fully synced on join")
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        var connection2 = env.createConnection();
        // Appelle une méthode
        var tracker2 = connection2.trackIncoming(PlayerInfoUpdatePacket.class);
        // Appelle une méthode
        var _ = connection2.connect(instance, new Pos(5, 42, 0));
        // Appelle une méthode
        tracker2.assertCount(2, packet -> packet.actions().equals(EnumSet.allOf(PlayerInfoUpdatePacket.Action.class)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void displayNameTest(Env env) {
        // Instruction de code
        Predicate<PlayerInfoUpdatePacket> predicate =
                // Appelle une méthode
                packet -> packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME);
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        player.setDisplayName(Component.text("Display Name!"));

        // Appelle une méthode
        var connection2 = env.createConnection();
        // Appelle une méthode
        var tracker2 = connection2.trackIncoming(PlayerInfoUpdatePacket.class);
        // Appelle une méthode
        connection2.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        tracker2.assertCount(2, predicate);

        // Appelle une méthode
        var tracker3 = connection2.trackIncoming(PlayerInfoUpdatePacket.class);

        // Appelle une méthode
        player.setDisplayName(Component.text("Other Name!"));

        // Appelle une méthode
        tracker3.assertCount(1, predicate);
        // Appelle une méthode
        tracker.assertCount(4, predicate);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void gameModeInfoTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Instruction de code
        tracker.assertCount(1, packet ->
                // Appelle une méthode
                packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE));
        // Appelle une méthode
        var tracker2 = connection.trackIncoming(PlayerInfoUpdatePacket.class);

        // Appelle une méthode
        player.setGameMode(GameMode.CREATIVE);

        // Instruction de code
        tracker2.assertCount(1, packet ->
                // Appelle une méthode
                packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void latencyTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Instruction de code
        tracker.assertCount(1, packet ->
                // Appelle une méthode
                packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_LATENCY));

        // Appelle une méthode
        var tracker2 = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Appelle une méthode
        player.refreshLatency(100);

        // Instruction de code
        tracker2.assertCount(1, packet ->
                // Appelle une méthode
                packet.actions().contains(PlayerInfoUpdatePacket.Action.UPDATE_LATENCY));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void listedTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));


        // Appelle une méthode
        assertTrue(player.isListed());

        // Appelle une méthode
        player.setListed(false);

        // Affecte une valeur
        var listedPackets = tracker.collect().stream().filter((packet) ->
                        // Instruction de code
                        packet.actions().stream().anyMatch((act) -> act == PlayerInfoUpdatePacket.Action.UPDATE_LISTED))
                // Appelle une méthode
                .count();

        // Appelle une méthode
        assertEquals(2, listedPackets);
        // Appelle une méthode
        assertFalse(player.isListed());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void listOrderTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var tracker = connection.trackIncoming(PlayerInfoUpdatePacket.class);
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        assertEquals(0, player.getListOrder());

        // Appelle une méthode
        player.setListOrder(1);

        // Affecte une valeur
        var orderPackets = tracker.collect().stream().filter((packet) ->
                        // Instruction de code
                        packet.actions().stream().anyMatch((act) -> act == PlayerInfoUpdatePacket.Action.UPDATE_LIST_ORDER))
                // Appelle une méthode
                .count();

        // Appelle une méthode
        assertEquals(2, orderPackets);
        // Appelle une méthode
        assertEquals(1, player.getListOrder());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void setView(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        Pos startingPlayerPos = new Pos(0, 42, 0);
        // Appelle une méthode
        var player = connection.connect(instance, startingPlayerPos);

        // Appelle une méthode
        var tracker = connection.trackIncoming(PlayerPositionAndLookPacket.class);
        // Appelle une méthode
        player.setView(30, 20);

        // Appelle une méthode
        assertEquals(startingPlayerPos.withView(30, 20), player.getPosition());
        // Début d'une méthode/d'un bloc
        tracker.assertSingle(PlayerPositionAndLookPacket.class, packet -> {
            // Should be relative coord and velocity because we are only trying to change the view.
            // Appelle une méthode
            assertEquals(RelativeFlags.COORD | RelativeFlags.DELTA_COORD, packet.flags());
            // Appelle une méthode
            assertEquals(new Pos(0, 0, 0, 30, 20), packet.position());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lookAt(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var tracker = connection.trackIncoming(FacePlayerPacket.class);
        // Appelle une méthode
        Pos startingPlayerPos = new Pos(0, 42, 0);
        // Appelle une méthode
        var player = connection.connect(instance, startingPlayerPos);

        // Appelle une méthode
        Point pointLookAt = new Vec(3, 3, 3);
        // Appelle une méthode
        player.lookAt(pointLookAt);
        // Appelle une méthode
        tracker.assertSingle(FacePlayerPacket.class, packet -> assertEquals(pointLookAt, packet.target()));

        // Appelle une méthode
        tracker = connection.trackIncoming(FacePlayerPacket.class);
        // Appelle une méthode
        Entity entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(player.getInstance(), new Pos(9, 9, 9));
        // Appelle une méthode
        player.lookAt(entity);
        // Appelle une méthode
        tracker.assertSingle(FacePlayerPacket.class, packet -> assertEquals(entity.getEntityId(), packet.entityId()));

        // Appelle une méthode
        assertEquals(startingPlayerPos, player.getPosition());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void removePlayer(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        Player player = connection.connect(instance, new Pos(0, 40, 0));

        // Début d'une méthode/d'un bloc
        instance.eventNode().addListener(PlayerChunkUnloadEvent.class, event -> {
            // Appelle une méthode
            assertEquals(instance, event.getInstance());
            // Appelle une méthode
            assertEquals(player, event.getPlayer());
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        player.remove(true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void inputsPressed(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var events = env.trackEvent(PlayerInputEvent.class, EventFilter.PLAYER, player);
        // Instruction de code
        PlayerInputListener.listener(
                // Crée un nouvel objet
                new ClientInputPacket(true, true, true, true, true, true, true),
                // Instruction de code
                player
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        events.assertSingle(event -> {
            // Appelle une méthode
            assertFalse(event.hasReleasedForwardKey());
            // Appelle une méthode
            assertFalse(event.hasReleasedBackwardKey());
            // Appelle une méthode
            assertFalse(event.hasReleasedLeftKey());
            // Appelle une méthode
            assertFalse(event.hasReleasedRightKey());
            // Appelle une méthode
            assertFalse(event.hasReleasedJumpKey());
            // Appelle une méthode
            assertFalse(event.hasReleasedShiftKey());
            // Appelle une méthode
            assertFalse(event.hasReleasedSprintKey());

            // Appelle une méthode
            assertTrue(event.hasPressedForwardKey());
            // Appelle une méthode
            assertTrue(event.hasPressedBackwardKey());
            // Appelle une méthode
            assertTrue(event.hasPressedLeftKey());
            // Appelle une méthode
            assertTrue(event.hasPressedRightKey());
            // Appelle une méthode
            assertTrue(event.hasPressedJumpKey());
            // Appelle une méthode
            assertTrue(event.hasPressedShiftKey());
            // Appelle une méthode
            assertTrue(event.hasPressedSprintKey());

            // Appelle une méthode
            assertTrue(event.isHoldingForwardKey());
            // Appelle une méthode
            assertTrue(event.isHoldingBackwardKey());
            // Appelle une méthode
            assertTrue(event.isHoldingLeftKey());
            // Appelle une méthode
            assertTrue(event.isHoldingRightKey());
            // Appelle une méthode
            assertTrue(event.isHoldingJumpKey());
            // Appelle une méthode
            assertTrue(event.isHoldingShiftKey());
            // Appelle une méthode
            assertTrue(event.isHoldingSprintKey());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void inputsReleased(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Instruction de code
        PlayerInputListener.listener(
                // Crée un nouvel objet
                new ClientInputPacket(true, true, true, true, true, true, true),
                // Instruction de code
                player
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        var events = env.trackEvent(PlayerInputEvent.class, EventFilter.PLAYER, player);
        // Instruction de code
        PlayerInputListener.listener(
                // Crée un nouvel objet
                new ClientInputPacket(false, false, false, false, false, false, false),
                // Instruction de code
                player
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        events.assertSingle(event -> {
            // Appelle une méthode
            assertFalse(event.hasPressedForwardKey());
            // Appelle une méthode
            assertFalse(event.hasPressedBackwardKey());
            // Appelle une méthode
            assertFalse(event.hasPressedLeftKey());
            // Appelle une méthode
            assertFalse(event.hasPressedRightKey());
            // Appelle une méthode
            assertFalse(event.hasPressedJumpKey());
            // Appelle une méthode
            assertFalse(event.hasPressedShiftKey());
            // Appelle une méthode
            assertFalse(event.hasPressedSprintKey());

            // Appelle une méthode
            assertTrue(event.hasReleasedForwardKey());
            // Appelle une méthode
            assertTrue(event.hasReleasedBackwardKey());
            // Appelle une méthode
            assertTrue(event.hasReleasedLeftKey());
            // Appelle une méthode
            assertTrue(event.hasReleasedRightKey());
            // Appelle une méthode
            assertTrue(event.hasReleasedJumpKey());
            // Appelle une méthode
            assertTrue(event.hasReleasedShiftKey());
            // Appelle une méthode
            assertTrue(event.hasReleasedSprintKey());

            // Appelle une méthode
            assertFalse(event.isHoldingForwardKey());
            // Appelle une méthode
            assertFalse(event.isHoldingBackwardKey());
            // Appelle une méthode
            assertFalse(event.isHoldingLeftKey());
            // Appelle une méthode
            assertFalse(event.isHoldingRightKey());
            // Appelle une méthode
            assertFalse(event.isHoldingJumpKey());
            // Appelle une méthode
            assertFalse(event.isHoldingShiftKey());
            // Appelle une méthode
            assertFalse(event.isHoldingSprintKey());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
