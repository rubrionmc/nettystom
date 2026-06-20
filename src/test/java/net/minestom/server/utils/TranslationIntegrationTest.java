// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.GlobalTranslator;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.Translator;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SetSlotPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SystemChatPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.UpdateScorePacket;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.Sidebar;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assumptions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.BeforeAll;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.text.MessageFormat;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Locale;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Annotation pour l'élément suivant
@EnvTest //TODO(server-properties) Remove assumptions
// Déclaration de type (classe/interface/enum/record)
public class TranslationIntegrationTest {

    // Annotation pour l'élément suivant
    @BeforeAll
    // Début d'une méthode/d'un bloc
    static void translator() {
        // Affecte une valeur
        final var translator = new Translator() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Key name() {
                // Renvoie une valeur à l'appelant
                return Key.key("test.reg");
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public MessageFormat translate(String key, Locale locale) {
                // Embranchement : vérifie une condition
                if (!"test.key".equals(key)) return null;
                // Renvoie une valeur à l'appelant
                return new MessageFormat("This is a test message", MinestomAdventure.getDefaultLocale());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        GlobalTranslator.translator().addSource(translator);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testTranslationEnabled(final Env env) {
        // Appelle une méthode
        Assumptions.assumeTrue(ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION);
        // Appelle une méthode
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        final var connection = env.createConnection();
        // Appelle une méthode
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        final var collector = connection.trackIncoming(SystemChatPacket.class);

        // Appelle une méthode
        final var message = Component.translatable("test.key");
        // Appelle une méthode
        final var packet = new SystemChatPacket(message, false);
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // the message should not be changed if translations are enabled.
        // the translation of the message itself will be proceeded in PlayerConnectionImpl class
        // Appelle une méthode
        collector.assertSingle(received -> assertNotEquals(message, received.message()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testTranslationDisabled(final Env env) {
        // Appelle une méthode
        Assumptions.assumeTrue(ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION);
        // Appelle une méthode
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        final var connection = env.createConnection();
        // Appelle une méthode
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        final var collector = connection.trackIncoming(SystemChatPacket.class);

        // Appelle une méthode
        final var message = Component.translatable("test.key");
        // Appelle une méthode
        final var packet = new SystemChatPacket(message, false);
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // Appelle une méthode
        collector.assertSingle(received -> assertEquals(message, received.message()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testItemStackTranslation(final Env env) {
        // Appelle une méthode
        Assumptions.assumeTrue(ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION);
        // Appelle une méthode
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        final var connection = env.createConnection();
        // Appelle une méthode
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        final var collector = connection.trackIncoming(SetSlotPacket.class);

        // Appelle une méthode
        final var message = Component.translatable("test.key");
        // Affecte une valeur
        final var itemStack = ItemStack.of(Material.STONE)
                // Instruction de code
                .with(DataComponents.ITEM_NAME, message)
                // Appelle une méthode
                .with(DataComponents.CUSTOM_NAME, message);
        // Appelle une méthode
        final var packet = new SetSlotPacket((byte) 0x01, 1, (short) 1, itemStack);
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // Début d'une méthode/d'un bloc
        collector.assertSingle(received -> {
            // Appelle une méthode
            assertNotEquals(message, received.itemStack().get(DataComponents.ITEM_NAME));
            // Appelle une méthode
            assertNotEquals(message, received.itemStack().get(DataComponents.CUSTOM_NAME));
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testUpdateScorePacketTranslations(final Env env) {
        // Appelle une méthode
        Assumptions.assumeTrue(ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION);
        // Appelle une méthode
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        final var connection = env.createConnection();
        // Appelle une méthode
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        final var collector = connection.trackIncoming(UpdateScorePacket.class);

        // Appelle une méthode
        final var message = Component.translatable("test.key");
        // Appelle une méthode
        final var numberFormat = Sidebar.NumberFormat.fixed(message);
        // Affecte une valeur
        final var packet = new UpdateScorePacket(
                // Instruction de code
                "",
                // Instruction de code
                "",
                // Instruction de code
                0,
                // Instruction de code
                message,
                // Instruction de code
                numberFormat
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // Début d'une méthode/d'un bloc
        collector.assertSingle(received -> {
            // Appelle une méthode
            assertNotEquals(message, received.displayName());
            // Appelle une méthode
            assertNotEquals(message, received.numberFormat().content());
        // Fin d'un bloc/d'une expression
        });

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
