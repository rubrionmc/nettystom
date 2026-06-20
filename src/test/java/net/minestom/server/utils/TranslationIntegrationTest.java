// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.GlobalTranslator;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.TranslationRegistry;
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
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class TranslationIntegrationTest {

    // Annotation pour l'élément suivant
    @BeforeAll
    // Début d'une méthode/d'un bloc
    static void translator() {
        // Appelle une méthode
        final var translator = TranslationRegistry.create(Key.key("test.reg"));
        // Have to use US as default language because the default ClientSettings are in US :)
        // Appelle une méthode
        translator.register("test.key", Locale.US, new MessageFormat("This is a test message", MinestomAdventure.getDefaultLocale()));

        // Appelle une méthode
        GlobalTranslator.translator().addSource(translator);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testTranslationEnabled(final Env env) {
        // Appelle une méthode
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        final var connection = env.createConnection();
        // Appelle une méthode
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        final var collector = connection.trackIncoming(SystemChatPacket.class);

        // Affecte une valeur
        MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION = true;
        // Appelle une méthode
        final var message = Component.translatable("test.key");
        // Appelle une méthode
        final var packet = new SystemChatPacket(message, false);
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // the message should not be changed if translations are enabled.
        // the translation of the message itself will be proceeded in PlayerConnectionImpl class
        // Début d'une méthode/d'un bloc
        collector.assertSingle(received -> {
            // Appelle une méthode
            assertNotEquals(message, received.message());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testTranslationDisabled(final Env env) {
        // Appelle une méthode
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        final var connection = env.createConnection();
        // Appelle une méthode
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        final var collector = connection.trackIncoming(SystemChatPacket.class);

        // Affecte une valeur
        MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION = false;
        // Appelle une méthode
        final var message = Component.translatable("test.key");
        // Appelle une méthode
        final var packet = new SystemChatPacket(message, false);
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(List.of(player), packet);

        // Début d'une méthode/d'un bloc
        collector.assertSingle(received -> {
            // Appelle une méthode
            assertEquals(message, received.message());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testItemStackTranslation(final Env env) {
        // Appelle une méthode
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        final var connection = env.createConnection();
        // Appelle une méthode
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        final var collector = connection.trackIncoming(SetSlotPacket.class);

        // Affecte une valeur
        MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION = true;
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
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        final var connection = env.createConnection();
        // Appelle une méthode
        final var player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        final var collector = connection.trackIncoming(UpdateScorePacket.class);

        // Affecte une valeur
        MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION = true;
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
