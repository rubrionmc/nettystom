// Déclaration du paquet de ce fichier
package net.minestom.server.network.player;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.entity.MainHand;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatMessageType;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;

// Import d'une classe nécessaire
import java.util.Locale;
// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientSettings(Locale locale, byte viewDistance,
                             // Instruction de code
                             ChatMessageType chatMessageType, boolean chatColors,
                             // Instruction de code
                             byte displayedSkinParts, MainHand mainHand,
                             // Instruction de code
                             boolean enableTextFiltering, boolean allowServerListings,
                             // Début d'une méthode/d'un bloc
                             ClientSettings.ParticleSetting particleSetting) {
    // Affecte une valeur
    public static final ClientSettings DEFAULT = new ClientSettings(
            // Instruction de code
            Locale.US, (byte) ServerFlag.CHUNK_VIEW_DISTANCE,
            // Instruction de code
            ChatMessageType.FULL, true,
            // Instruction de code
            (byte) 0x7F, MainHand.RIGHT,
            // Instruction de code
            true, true,
            // Instruction de code
            ParticleSetting.ALL
    // Fin d'un bloc/d'une expression
    );

    // Affecte une valeur
    private static final NetworkBuffer.Type<Locale> LOCALE_SERIALIZER = STRING.transform(
            // Début d'une méthode/d'un bloc
            s -> {
                // Appelle une méthode
                final String locale = s.replace("_", "-");
                // Renvoie une valeur à l'appelant
                return Locale.forLanguageTag(locale);
            // Instruction de code
            },
            // Instruction de code
            Locale::toLanguageTag
    // Fin d'un bloc/d'une expression
    );

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSettings> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            LOCALE_SERIALIZER, ClientSettings::locale,
            // Instruction de code
            BYTE, ClientSettings::viewDistance,
            // Instruction de code
            Enum(ChatMessageType.class), ClientSettings::chatMessageType,
            // Instruction de code
            BOOLEAN, ClientSettings::chatColors,
            // Instruction de code
            BYTE, ClientSettings::displayedSkinParts,
            // Instruction de code
            MainHand.NETWORK_TYPE, ClientSettings::mainHand,
            // Instruction de code
            BOOLEAN, ClientSettings::enableTextFiltering,
            // Instruction de code
            BOOLEAN, ClientSettings::allowServerListings,
            // Instruction de code
            ParticleSetting.NETWORK_TYPE, ClientSettings::particleSetting,
            // Instruction de code
            ClientSettings::new);

    // Début d'une méthode/d'un bloc
    public ClientSettings {
        // Appelle une méthode
        Objects.requireNonNull(locale);
        // Clamp viewDistance to valid bounds
        // Appelle une méthode
        viewDistance = (byte) MathUtils.clamp(viewDistance, 2, 32);
        // Appelle une méthode
        Objects.requireNonNull(chatMessageType);
        // Appelle une méthode
        Objects.requireNonNull(mainHand);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Deprecated in favor of {@link Player#effectiveViewDistance()}
     * @return The effective view distance, which is the smaller of either the client's view distance settings and the server's max view distance
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public int effectiveViewDistance() {
        // Renvoie une valeur à l'appelant
        return Math.min(viewDistance(), ServerFlag.CHUNK_VIEW_DISTANCE);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum ParticleSetting {
        // Instruction de code
        ALL,
        // Instruction de code
        DECREASED,
        // Instruction de code
        MINIMAL;

        // Appelle une méthode
        public static final NetworkBuffer.Type<ParticleSetting> NETWORK_TYPE = Enum(ParticleSetting.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
