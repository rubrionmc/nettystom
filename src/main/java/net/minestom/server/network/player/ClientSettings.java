// Package declaration for this file
package net.minestom.server.network.player;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.entity.MainHand;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.message.ChatMessageType;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.utils.MathUtils;

// Import of a required class
import java.util.Locale;
// Import of a required class
import java.util.Objects;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientSettings(Locale locale, byte viewDistance,
                             // Code statement
                             ChatMessageType chatMessageType, boolean chatColors,
                             // Code statement
                             byte displayedSkinParts, MainHand mainHand,
                             // Code statement
                             boolean enableTextFiltering, boolean allowServerListings,
                             // Start of a method/block
                             ClientSettings.ParticleSetting particleSetting) {
    // Assigns a value
    public static final ClientSettings DEFAULT = new ClientSettings(
            // Code statement
            Locale.US, (byte) ServerFlag.CHUNK_VIEW_DISTANCE,
            // Code statement
            ChatMessageType.FULL, true,
            // Code statement
            (byte) 0x7F, MainHand.RIGHT,
            // Code statement
            true, true,
            // Code statement
            ParticleSetting.ALL
    // End of a block/expression
    );

    // Assigns a value
    private static final NetworkBuffer.Type<Locale> LOCALE_SERIALIZER = STRING.transform(
            // Start of a method/block
            s -> {
                // Calls a method
                final String locale = s.replace("_", "-");
                // Returns a value to the caller
                return Locale.forLanguageTag(locale);
            // Code statement
            },
            // Code statement
            Locale::toLanguageTag
    // End of a block/expression
    );

    // Assigns a value
    public static final NetworkBuffer.Type<ClientSettings> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            LOCALE_SERIALIZER, ClientSettings::locale,
            // Code statement
            BYTE, ClientSettings::viewDistance,
            // Code statement
            Enum(ChatMessageType.class), ClientSettings::chatMessageType,
            // Code statement
            BOOLEAN, ClientSettings::chatColors,
            // Code statement
            BYTE, ClientSettings::displayedSkinParts,
            // Code statement
            MainHand.NETWORK_TYPE, ClientSettings::mainHand,
            // Code statement
            BOOLEAN, ClientSettings::enableTextFiltering,
            // Code statement
            BOOLEAN, ClientSettings::allowServerListings,
            // Code statement
            ParticleSetting.NETWORK_TYPE, ClientSettings::particleSetting,
            // Code statement
            ClientSettings::new);

    // Start of a method/block
    public ClientSettings {
        // Calls a method
        Objects.requireNonNull(locale);
        // Clamp viewDistance to valid bounds
        // Calls a method
        viewDistance = (byte) MathUtils.clamp(viewDistance, 2, 32);
        // Calls a method
        Objects.requireNonNull(chatMessageType);
        // Calls a method
        Objects.requireNonNull(mainHand);
    // End of a block/expression
    }

    /**
     * Deprecated in favor of {@link Player#effectiveViewDistance()}
     * @return The effective view distance, which is the smaller of either the client's view distance settings and the server's max view distance
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public int effectiveViewDistance() {
        // Returns a value to the caller
        return Math.min(viewDistance(), ServerFlag.CHUNK_VIEW_DISTANCE);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum ParticleSetting {
        // Code statement
        ALL,
        // Code statement
        DECREASED,
        // Code statement
        MINIMAL;

        // Calls a method
        public static final NetworkBuffer.Type<ParticleSetting> NETWORK_TYPE = Enum(ParticleSetting.class);
    // End of a block/expression
    }
// End of a block/expression
}
