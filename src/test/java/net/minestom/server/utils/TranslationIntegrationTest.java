// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.translation.GlobalTranslator;
// Import of a required class
import net.kyori.adventure.translation.Translator;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.packet.server.play.SetSlotPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.SystemChatPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.UpdateScorePacket;
// Import of a required class
import net.minestom.server.scoreboard.Sidebar;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assumptions;
// Import of a required class
import org.junit.jupiter.api.BeforeAll;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.text.MessageFormat;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Locale;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Annotation for the following element
@EnvTest //TODO(server-properties) Remove assumptions
// Type declaration (class/interface/enum/record)
public class TranslationIntegrationTest {

    // Annotation for the following element
    @BeforeAll
    // Start of a method/block
    static void translator() {
        // Assigns a value
        final var translator = new Translator() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key name() {
                // Returns a value to the caller
                return Key.key("test.reg");
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public MessageFormat translate(String key, Locale locale) {
                // Branch: checks a condition
                if (!"test.key".equals(key)) return null;
                // Returns a value to the caller
                return new MessageFormat("This is a test message", MinestomAdventure.getDefaultLocale());
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        GlobalTranslator.translator().addSource(translator);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testTranslationEnabled(final Env env) {
        // Calls a method
        Assumptions.assumeTrue(ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION);
        // Calls a method
        final var instance = env.createFlatInstance();
        // Calls a method
        final var connection = env.createConnection();
        // Calls a method
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        final var collector = connection.trackIncoming(SystemChatPacket.class);

        // Calls a method
        final var message = Component.translatable("test.key");
        // Calls a method
        final var packet = new SystemChatPacket(message, false);
        // Calls a method
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // the message should not be changed if translations are enabled.
        // the translation of the message itself will be proceeded in PlayerConnectionImpl class
        // Calls a method
        collector.assertSingle(received -> assertNotEquals(message, received.message()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testTranslationDisabled(final Env env) {
        // Calls a method
        Assumptions.assumeTrue(ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION);
        // Calls a method
        final var instance = env.createFlatInstance();
        // Calls a method
        final var connection = env.createConnection();
        // Calls a method
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        final var collector = connection.trackIncoming(SystemChatPacket.class);

        // Calls a method
        final var message = Component.translatable("test.key");
        // Calls a method
        final var packet = new SystemChatPacket(message, false);
        // Calls a method
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // Calls a method
        collector.assertSingle(received -> assertEquals(message, received.message()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testItemStackTranslation(final Env env) {
        // Calls a method
        Assumptions.assumeTrue(ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION);
        // Calls a method
        final var instance = env.createFlatInstance();
        // Calls a method
        final var connection = env.createConnection();
        // Calls a method
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        final var collector = connection.trackIncoming(SetSlotPacket.class);

        // Calls a method
        final var message = Component.translatable("test.key");
        // Assigns a value
        final var itemStack = ItemStack.of(Material.STONE)
                // Code statement
                .with(DataComponents.ITEM_NAME, message)
                // Calls a method
                .with(DataComponents.CUSTOM_NAME, message);
        // Calls a method
        final var packet = new SetSlotPacket((byte) 0x01, 1, (short) 1, itemStack);
        // Calls a method
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // Start of a method/block
        collector.assertSingle(received -> {
            // Calls a method
            assertNotEquals(message, received.itemStack().get(DataComponents.ITEM_NAME));
            // Calls a method
            assertNotEquals(message, received.itemStack().get(DataComponents.CUSTOM_NAME));
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testUpdateScorePacketTranslations(final Env env) {
        // Calls a method
        Assumptions.assumeTrue(ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION);
        // Calls a method
        final var instance = env.createFlatInstance();
        // Calls a method
        final var connection = env.createConnection();
        // Calls a method
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Calls a method
        final var collector = connection.trackIncoming(UpdateScorePacket.class);

        // Calls a method
        final var message = Component.translatable("test.key");
        // Calls a method
        final var numberFormat = Sidebar.NumberFormat.fixed(message);
        // Assigns a value
        final var packet = new UpdateScorePacket(
                // Code statement
                "",
                // Code statement
                "",
                // Code statement
                0,
                // Code statement
                message,
                // Code statement
                numberFormat
        // End of a block/expression
        );
        // Calls a method
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // Start of a method/block
        collector.assertSingle(received -> {
            // Calls a method
            assertNotEquals(message, received.displayName());
            // Calls a method
            assertNotEquals(message, received.numberFormat().content());
        // End of a block/expression
        });

    // End of a block/expression
    }
// End of a block/expression
}
