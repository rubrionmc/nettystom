// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.avatar.MannequinMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityMetaIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void notifyAboutChanges(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var connection2 = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 1));
        // Appelle une méthode
        var otherPlayer = connection2.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        assertTrue(player.getViewers().contains(otherPlayer));

        // Appelle une méthode
        var incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        // Appelle une méthode
        player.getEntityMeta().setNotifyAboutChanges(false);
        // Appelle une méthode
        player.setInvisible(true);
        // Appelle une méthode
        player.setNoGravity(true);
        // Appelle une méthode
        player.setSneaking(true);
        // No packets should be received here: notifyAboutChanges is off
        // Appelle une méthode
        incomingPackets.assertEmpty();
        // Appelle une méthode
        incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        // Appelle une méthode
        player.getEntityMeta().setNotifyAboutChanges(true);

        // Appelle une méthode
        var packets = incomingPackets.collect();
        // Appelle une méthode
        assertEquals(1, packets.size());
        // Début d'une méthode/d'un bloc
        validMetaDataPackets(packets, player.getEntityId(), entry -> {
            // Appelle une méthode
            final Object content = entry.value();
            // Embranchement : vérifie une condition
            if (entry.type() == Metadata.TYPE_BYTE) {
                // Appelle une méthode
                assertEquals((byte) 34, content);
            // Embranchement : vérifie une condition
            } else if (entry.type() == Metadata.TYPE_BOOLEAN) {
                // Appelle une méthode
                assertTrue((boolean) content);
            // Embranchement : vérifie une condition
            } else if (entry.type() == Metadata.TYPE_POSE) {
                // Appelle une méthode
                assertEquals(EntityPose.SNEAKING, content);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                Assertions.fail("Invalid MetaData entry");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Now test the "normal" behavior: Updates should be sent instantly
        // Appelle une méthode
        incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);
        // Appelle une méthode
        player.setInvisible(false);
        // Appelle une méthode
        player.setNoGravity(false);
        // Appelle une méthode
        player.setSneaking(false);
        // Appelle une méthode
        packets = incomingPackets.collect();
        // Début d'une méthode/d'un bloc
        validMetaDataPackets(packets, player.getEntityId(), entry -> {
            // Appelle une méthode
            final Object content = entry.value();
            // Embranchement : vérifie une condition
            if (entry.type() == Metadata.TYPE_BYTE) {
                // Appelle une méthode
                assertTrue(content.equals((byte) 2) || content.equals((byte) 0));
            // Embranchement : vérifie une condition
            } else if (entry.type() == Metadata.TYPE_BOOLEAN) {
                // Appelle une méthode
                assertFalse((boolean) content);
            // Embranchement : vérifie une condition
            } else if (entry.type() == Metadata.TYPE_POSE) {
                // Appelle une méthode
                assertEquals(EntityPose.STANDING, content);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                Assertions.fail("Invalid MetaData entry");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertEquals(4, packets.size());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void validMetaDataPackets(List<EntityMetaDataPacket> packets, int entityId, Consumer<Metadata.Entry<?>> contentChecker) {
        // Boucle : répète un bloc
        for (var packet : packets) {
            // Appelle une méthode
            assertEquals(packet.entityId(), entityId);
            // Boucle : répète un bloc
            for (var entry : packet.entries().values()) {
                // Appelle une méthode
                contentChecker.accept(entry);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void customName(Env env) {
        //Base things.
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        Pos startPos = new Pos(0, 42, 1);

        //Viewer.
        // Appelle une méthode
        var player = connection.connect(instance, startPos);

        //Tracks incoming packets.
        // Appelle une méthode
        var incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        //Creates entity and name.
        // Appelle une méthode
        Entity entity = new Entity(EntityType.BEE);
        // Appelle une méthode
        entity.setAutoViewable(false);
        // Appelle une méthode
        entity.getEntityMeta().setNotifyAboutChanges(false);
        // Appelle une méthode
        entity.setCustomName(Component.text("Custom Name"));
        // Appelle une méthode
        entity.setCustomNameVisible(true);
        // Appelle une méthode
        entity.setInstance(instance, startPos);
        // Appelle une méthode
        entity.getEntityMeta().setNotifyAboutChanges(true);
        // Appelle une méthode
        entity.addViewer(player);

        //Listen packets to check if entity name is "Custom Name".
        //This is first test, and it is not related to "custom name" bug. Therefore, it should work.
        // Appelle une méthode
        var packets = incomingPackets.collect();
        // Début d'une méthode/d'un bloc
        validMetaDataPackets(packets, entity.getEntityId(), entry -> {
            // Embranchement : vérifie une condition
            if (entry.type() != Metadata.TYPE_OPT_CHAT) return;
            // Appelle une méthode
            assertEquals(Component.text("Custom Name"), entry.value());
        // Fin d'un bloc/d'une expression
        });

        //Removes viewer.
        // Appelle une méthode
        entity.removeViewer(player);

        //Tracks incoming packets again. (resets previous)
        // Appelle une méthode
        incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        //Sets entity name again.
        // Appelle une méthode
        entity.setCustomName(Component.text("Custom Name 2"));

        //After setting entity's name, we add viewer again to see if the entity name is "Custom Name 2"
        // Appelle une méthode
        entity.addViewer(player);

        //Checks if entity name is "Custom Name 2" in the metadata entry.
        // Appelle une méthode
        assertEquals(Component.text("Custom Name 2"), entity.getCustomName());

        //Listen packets to check if entity name is "Custom Name 2".
        // Appelle une méthode
        packets = incomingPackets.collect();
        // Début d'une méthode/d'un bloc
        validMetaDataPackets(packets, entity.getEntityId(), entry -> {
            // Embranchement : vérifie une condition
            if (entry.type() != Metadata.TYPE_OPT_CHAT) return;
            // Appelle une méthode
            assertEquals(Component.text("Custom Name 2"), entry.value());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void displayInterpolationDurationAlwaysSend(Env env) {
        // ensure that display entity interpolation start delta is always sent even if we send the same value repeatedly.

        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var startPos = new Pos(0, 42, 1);

        // Appelle une méthode
        connection.connect(instance, startPos);
        // Appelle une méthode
        var incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        // Appelle une méthode
        var entity = new Entity(EntityType.ITEM_DISPLAY);
        // Appelle une méthode
        entity.setInstance(instance, startPos);
        // Appelle une méthode
        var meta = (ItemDisplayMeta) entity.getEntityMeta();

        // Appelle une méthode
        meta.setTransformationInterpolationStartDelta(1);
        // Instruction de code
        meta.setTransformationInterpolationStartDelta(2); // same tick

        // Appelle une méthode
        env.tick();
        // Appelle une méthode
        env.tick();

        // Appelle une méthode
        meta.setTransformationInterpolationStartDelta(3);

        // Appelle une méthode
        var packets = incomingPackets.collect();
        // Instruction de code
        assertEquals(4, packets.size()); // the 3 we sent, and 1 more for the spawn
        // Appelle une méthode
        assertEquals(1, packets.get(1).entries().get(MetadataDef.Display.INTERPOLATION_DELAY.index()).value());
        // Appelle une méthode
        assertEquals(2, packets.get(2).entries().get(MetadataDef.Display.INTERPOLATION_DELAY.index()).value());
        // Appelle une méthode
        assertEquals(3, packets.get(3).entries().get(MetadataDef.Display.INTERPOLATION_DELAY.index()).value());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testMannequin(Env env) {
        // Ensure that mannequins have all skin layers by default, and that they can be changed by setting the metadata.
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var startPos = new Pos(0, 42, 1);

        // Appelle une méthode
        connection.connect(instance, startPos);
        // Appelle une méthode
        var incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        // Appelle une méthode
        var entity = new Entity(EntityType.MANNEQUIN);
        // Appelle une méthode
        var meta = (MannequinMeta) entity.getEntityMeta();
        // Appelle une méthode
        Assertions.assertTrue(meta.isCapeEnabled());
        // Instruction de code
        Assertions.assertEquals(0x7F, meta.getDisplayedSkinParts()); // all enabled
        // Instruction de code
        meta.setDisplayedSkinParts((byte) 0); // disable all
        // Appelle une méthode
        entity.setInstance(instance, startPos).join();

        // Début d'une méthode/d'un bloc
        incomingPackets.assertSingle(packet -> {
            // Appelle une méthode
            assertEquals(packet.entityId(), entity.getEntityId());
            // Appelle une méthode
            var entry = packet.entries().get(MetadataDef.Mannequin.DISPLAYED_MODEL_PARTS_FLAGS.index());
            // Appelle une méthode
            assertEquals(Metadata.TYPE_BYTE, entry.type());
            // Appelle une méthode
            assertEquals((byte) 0, entry.value());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
