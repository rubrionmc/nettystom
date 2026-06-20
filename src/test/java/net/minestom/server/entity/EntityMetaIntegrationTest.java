// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.metadata.avatar.MannequinMeta;
// Import of a required class
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
// Import of a required class
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.Consumer;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityMetaIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void notifyAboutChanges(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var connection2 = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 1));
        // Calls a method
        var otherPlayer = connection2.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        assertTrue(player.getViewers().contains(otherPlayer));

        // Calls a method
        var incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        // Calls a method
        player.getEntityMeta().setNotifyAboutChanges(false);
        // Calls a method
        player.setInvisible(true);
        // Calls a method
        player.setNoGravity(true);
        // Calls a method
        player.setSneaking(true);
        // No packets should be received here: notifyAboutChanges is off
        // Calls a method
        incomingPackets.assertEmpty();
        // Calls a method
        incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        // Calls a method
        player.getEntityMeta().setNotifyAboutChanges(true);

        // Calls a method
        var packets = incomingPackets.collect();
        // Calls a method
        assertEquals(1, packets.size());
        // Start of a method/block
        validMetaDataPackets(packets, player.getEntityId(), entry -> {
            // Calls a method
            final Object content = entry.value();
            // Branch: checks a condition
            if (entry.type() == Metadata.TYPE_BYTE) {
                // Calls a method
                assertEquals((byte) 34, content);
            // Branch: checks a condition
            } else if (entry.type() == Metadata.TYPE_BOOLEAN) {
                // Calls a method
                assertTrue((boolean) content);
            // Branch: checks a condition
            } else if (entry.type() == Metadata.TYPE_POSE) {
                // Calls a method
                assertEquals(EntityPose.SNEAKING, content);
            // Alternative branch of the condition
            } else {
                // Calls a method
                Assertions.fail("Invalid MetaData entry");
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Now test the "normal" behavior: Updates should be sent instantly
        // Calls a method
        incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);
        // Calls a method
        player.setInvisible(false);
        // Calls a method
        player.setNoGravity(false);
        // Calls a method
        player.setSneaking(false);
        // Calls a method
        packets = incomingPackets.collect();
        // Start of a method/block
        validMetaDataPackets(packets, player.getEntityId(), entry -> {
            // Calls a method
            final Object content = entry.value();
            // Branch: checks a condition
            if (entry.type() == Metadata.TYPE_BYTE) {
                // Calls a method
                assertTrue(content.equals((byte) 2) || content.equals((byte) 0));
            // Branch: checks a condition
            } else if (entry.type() == Metadata.TYPE_BOOLEAN) {
                // Calls a method
                assertFalse((boolean) content);
            // Branch: checks a condition
            } else if (entry.type() == Metadata.TYPE_POSE) {
                // Calls a method
                assertEquals(EntityPose.STANDING, content);
            // Alternative branch of the condition
            } else {
                // Calls a method
                Assertions.fail("Invalid MetaData entry");
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Calls a method
        assertEquals(4, packets.size());
    // End of a block/expression
    }

    // Start of a method/block
    private void validMetaDataPackets(List<EntityMetaDataPacket> packets, int entityId, Consumer<Metadata.Entry<?>> contentChecker) {
        // Loop: repeats a block
        for (var packet : packets) {
            // Calls a method
            assertEquals(packet.entityId(), entityId);
            // Loop: repeats a block
            for (var entry : packet.entries().values()) {
                // Calls a method
                contentChecker.accept(entry);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void customName(Env env) {
        //Base things.
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        Pos startPos = new Pos(0, 42, 1);

        //Viewer.
        // Calls a method
        var player = connection.connect(instance, startPos);

        //Tracks incoming packets.
        // Calls a method
        var incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        //Creates entity and name.
        // Calls a method
        Entity entity = new Entity(EntityType.BEE);
        // Calls a method
        entity.setAutoViewable(false);
        // Calls a method
        entity.getEntityMeta().setNotifyAboutChanges(false);
        // Calls a method
        entity.setCustomName(Component.text("Custom Name"));
        // Calls a method
        entity.setCustomNameVisible(true);
        // Calls a method
        entity.setInstance(instance, startPos);
        // Calls a method
        entity.getEntityMeta().setNotifyAboutChanges(true);
        // Calls a method
        entity.addViewer(player);

        //Listen packets to check if entity name is "Custom Name".
        //This is first test, and it is not related to "custom name" bug. Therefore, it should work.
        // Calls a method
        var packets = incomingPackets.collect();
        // Start of a method/block
        validMetaDataPackets(packets, entity.getEntityId(), entry -> {
            // Branch: checks a condition
            if (entry.type() != Metadata.TYPE_OPT_CHAT) return;
            // Calls a method
            assertEquals(Component.text("Custom Name"), entry.value());
        // End of a block/expression
        });

        //Removes viewer.
        // Calls a method
        entity.removeViewer(player);

        //Tracks incoming packets again. (resets previous)
        // Calls a method
        incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        //Sets entity name again.
        // Calls a method
        entity.setCustomName(Component.text("Custom Name 2"));

        //After setting entity's name, we add viewer again to see if the entity name is "Custom Name 2"
        // Calls a method
        entity.addViewer(player);

        //Checks if entity name is "Custom Name 2" in the metadata entry.
        // Calls a method
        assertEquals(Component.text("Custom Name 2"), entity.getCustomName());

        //Listen packets to check if entity name is "Custom Name 2".
        // Calls a method
        packets = incomingPackets.collect();
        // Start of a method/block
        validMetaDataPackets(packets, entity.getEntityId(), entry -> {
            // Branch: checks a condition
            if (entry.type() != Metadata.TYPE_OPT_CHAT) return;
            // Calls a method
            assertEquals(Component.text("Custom Name 2"), entry.value());
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void displayInterpolationDurationAlwaysSend(Env env) {
        // ensure that display entity interpolation start delta is always sent even if we send the same value repeatedly.

        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var startPos = new Pos(0, 42, 1);

        // Calls a method
        connection.connect(instance, startPos);
        // Calls a method
        var incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        // Calls a method
        var entity = new Entity(EntityType.ITEM_DISPLAY);
        // Calls a method
        entity.setInstance(instance, startPos);
        // Calls a method
        var meta = (ItemDisplayMeta) entity.getEntityMeta();

        // Calls a method
        meta.setTransformationInterpolationStartDelta(1);
        // Code statement
        meta.setTransformationInterpolationStartDelta(2); // same tick

        // Calls a method
        env.tick();
        // Calls a method
        env.tick();

        // Calls a method
        meta.setTransformationInterpolationStartDelta(3);

        // Calls a method
        var packets = incomingPackets.collect();
        // Code statement
        assertEquals(4, packets.size()); // the 3 we sent, and 1 more for the spawn
        // Calls a method
        assertEquals(1, packets.get(1).entries().get(MetadataDef.Display.INTERPOLATION_DELAY.index()).value());
        // Calls a method
        assertEquals(2, packets.get(2).entries().get(MetadataDef.Display.INTERPOLATION_DELAY.index()).value());
        // Calls a method
        assertEquals(3, packets.get(3).entries().get(MetadataDef.Display.INTERPOLATION_DELAY.index()).value());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testMannequin(Env env) {
        // Ensure that mannequins have all skin layers by default, and that they can be changed by setting the metadata.
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var startPos = new Pos(0, 42, 1);

        // Calls a method
        connection.connect(instance, startPos);
        // Calls a method
        var incomingPackets = connection.trackIncoming(EntityMetaDataPacket.class);

        // Calls a method
        var entity = new Entity(EntityType.MANNEQUIN);
        // Calls a method
        var meta = (MannequinMeta) entity.getEntityMeta();
        // Calls a method
        Assertions.assertTrue(meta.isCapeEnabled());
        // Code statement
        Assertions.assertEquals(0x7F, meta.getDisplayedSkinParts()); // all enabled
        // Code statement
        meta.setDisplayedSkinParts((byte) 0); // disable all
        // Calls a method
        entity.setInstance(instance, startPos).join();

        // Start of a method/block
        incomingPackets.assertSingle(packet -> {
            // Calls a method
            assertEquals(packet.entityId(), entity.getEntityId());
            // Calls a method
            var entry = packet.entries().get(MetadataDef.Mannequin.DISPLAYED_MODEL_PARTS_FLAGS.index());
            // Calls a method
            assertEquals(Metadata.TYPE_BYTE, entry.type());
            // Calls a method
            assertEquals((byte) 0, entry.value());
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
