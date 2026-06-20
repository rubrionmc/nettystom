// Déclaration du paquet de ce fichier
package net.minestom.server.ping;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.utils.identity.NamedAndIdentified;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;
// Import d'une classe nécessaire
import java.util.*;

// Déclaration de type (classe/interface/enum/record)
public record Status(
        // Instruction de code
        Component description,
        // Instruction de code
        byte @Nullable [] favicon,
        // Instruction de code
        VersionInfo versionInfo,
        // Annotation pour l'élément suivant
        @Nullable PlayerInfo playerInfo,
        // Instruction de code
        boolean enforcesSecureChat
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    private static final String FAVICON_PREFIX = "data:image/png;base64,";

    // Affecte une valeur
    public static final Codec<byte @Nullable []> FAVICON_CODEC = Codec.STRING.transform(
            // Début d'une méthode/d'un bloc
            string -> {
                // Appelle une méthode
                Check.argCondition(!string.startsWith(FAVICON_PREFIX), "Favicon format must be a PNG image encoded in base 64!");
                // Renvoie une valeur à l'appelant
                return Base64.getDecoder().decode(string.substring(FAVICON_PREFIX.length()).getBytes(StandardCharsets.UTF_8));
            // Appelle une méthode
            }, data -> FAVICON_PREFIX + new String(Base64.getEncoder().encode(data), StandardCharsets.UTF_8));

    // Affecte une valeur
    public static final Codec<Status> CODEC = StructCodec.struct(
            // Instruction de code
            "description", Codec.COMPONENT.optional(Component.empty()), Status::description,
            // Instruction de code
            "favicon", FAVICON_CODEC.optional(), Status::favicon,
            // Instruction de code
            "version", VersionInfo.CODEC, Status::versionInfo,
            // Instruction de code
            "players", PlayerInfo.CODEC.optional(), Status::playerInfo,
            // Instruction de code
            "enforcesSecureChat", Codec.BOOLEAN.optional(false), Status::enforcesSecureChat,
            // Instruction de code
            Status::new);

    // Début d'une méthode/d'un bloc
    public Status {
        // Embranchement : vérifie une condition
        if (favicon != null) {
            // Appelle une méthode
            favicon = favicon.clone();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Builder builder(Status status) {
        // Renvoie une valeur à l'appelant
        return new Builder(status);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record VersionInfo(String name, int protocolVersion) {
        // Appelle une méthode
        public static final VersionInfo DEFAULT = new VersionInfo(MinecraftServer.VERSION_NAME, MinecraftServer.PROTOCOL_VERSION);
        // Affecte une valeur
        public static final Codec<VersionInfo> CODEC = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING, VersionInfo::name,
                // Instruction de code
                "protocol", Codec.INT, VersionInfo::protocolVersion,
                // Instruction de code
                VersionInfo::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record PlayerInfo(int onlinePlayers, int maxPlayers, List<NamedAndIdentified> sample) {
        // Affecte une valeur
        private static final Codec<Component> LEGACY_CODEC = Codec.STRING.transform(
                // Instruction de code
                string -> LegacyComponentSerializer.legacySection().deserialize(string),
                // Appelle une méthode
                component -> LegacyComponentSerializer.legacySection().serialize(component));

        // Affecte une valeur
        private static final Codec<NamedAndIdentified> SAMPLE_CODEC = StructCodec.struct(
                // Instruction de code
                "name", LEGACY_CODEC, NamedAndIdentified::getName,
                // Instruction de code
                "id", Codec.UUID_STRING, NamedAndIdentified::getUuid,
                // Instruction de code
                NamedAndIdentified::of);

        // Affecte une valeur
        public static final Codec<PlayerInfo> CODEC = StructCodec.struct(
                // Instruction de code
                "online", Codec.INT, PlayerInfo::onlinePlayers,
                // Instruction de code
                "max", Codec.INT, PlayerInfo::maxPlayers,
                // Instruction de code
                "sample", SAMPLE_CODEC.list(), PlayerInfo::sample,
                // Instruction de code
                PlayerInfo::new);

        // Début d'une méthode/d'un bloc
        public PlayerInfo {
            // Appelle une méthode
            sample = List.copyOf(sample);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public PlayerInfo(int onlinePlayers, int maxPlayers) {
            // Appelle une méthode
            this(onlinePlayers, maxPlayers, List.of());
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public static PlayerInfo onlineCount() {
            // Appelle une méthode
            final Collection<Player> players = MinecraftServer.getConnectionManager().getOnlinePlayers();
            // Renvoie une valeur à l'appelant
            return new PlayerInfo(players.size(), players.size() + 1, List.of());
        // Fin d'un bloc/d'une expression
        }

        /**
         * @param maxSamples The maximum number of player entries to include in the sample
         * @return A {@link PlayerInfo} containing the online count, and a sample of online players.
         */
        // Début d'une méthode/d'un bloc
        public static PlayerInfo online(int maxSamples) {
            // Appelle une méthode
            final Collection<Player> players = MinecraftServer.getConnectionManager().getOnlinePlayers();
            // Appelle une méthode
            final List<NamedAndIdentified> samples = new ArrayList<>(Math.min(maxSamples, players.size()));
            // Boucle : répète un bloc
            for (final Player player : players) {
                // Embranchement : vérifie une condition
                if (!player.getSettings().allowServerListings())
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Appelle une méthode
                samples.add(player);
                // Embranchement : vérifie une condition
                if (samples.size() >= maxSamples)
                    // Interrompt la boucle/le bloc
                    break;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new PlayerInfo(players.size(), players.size() + 1, samples);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public static Builder builder() {
            // Renvoie une valeur à l'appelant
            return new Builder();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public static Builder builder(PlayerInfo playerInfo) {
            // Renvoie une valeur à l'appelant
            return new Builder(playerInfo);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public static final class Builder {
            // Instruction de code
            private int onlinePlayers;
            // Instruction de code
            private int maxPlayers;
            // Instruction de code
            private List<NamedAndIdentified> sample;

            // Début d'une méthode/d'un bloc
            private Builder() {
                // Accès à l'objet courant/parent
                this.sample = new ArrayList<>();
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            private Builder(PlayerInfo playerInfo) {
                // Accès à l'objet courant/parent
                this.onlinePlayers = playerInfo.onlinePlayers;
                // Accès à l'objet courant/parent
                this.maxPlayers = playerInfo.maxPlayers;
                // Accès à l'objet courant/parent
                this.sample = new ArrayList<>(playerInfo.sample);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Contract(value = "_ -> this")
            // Début d'une méthode/d'un bloc
            public Builder onlinePlayers(int onlinePlayers) {
                // Accès à l'objet courant/parent
                this.onlinePlayers = onlinePlayers;
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Contract(value = "_ -> this")
            // Début d'une méthode/d'un bloc
            public Builder maxPlayers(int maxPlayers) {
                // Accès à l'objet courant/parent
                this.maxPlayers = maxPlayers;
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Contract(value = "_ -> this")
            // Début d'une méthode/d'un bloc
            public Builder sample(List<NamedAndIdentified> sample) {
                // Accès à l'objet courant/parent
                this.sample = sample;
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Contract(value = "_ -> this")
            // Début d'une méthode/d'un bloc
            public Builder sample(NamedAndIdentified profile) {
                // Accès à l'objet courant/parent
                this.sample.add(profile);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Contract(value = "_ -> this")
            // Début d'une méthode/d'un bloc
            public Builder sample(GameProfile profile) {
                // Renvoie une valeur à l'appelant
                return this.sample(NamedAndIdentified.of(profile.name(), profile.uuid()));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Contract(value = "_ -> this")
            // Début d'une méthode/d'un bloc
            public Builder sample(Component component) {
                // Renvoie une valeur à l'appelant
                return this.sample(NamedAndIdentified.named(component));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Contract(value = "_ -> this")
            // Début d'une méthode/d'un bloc
            public Builder sample(String string) {
                // Renvoie une valeur à l'appelant
                return this.sample(NamedAndIdentified.named(string));
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public PlayerInfo build() {
                // Renvoie une valeur à l'appelant
                return new PlayerInfo(this.onlinePlayers, this.maxPlayers, this.sample);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Builder {
        // Appelle une méthode
        public static final Component DEFAULT_DESCRIPTION = Component.text("Minestom Server");

        // Instruction de code
        private Component description;
        // Instruction de code
        private byte @Nullable [] favicon;
        // Instruction de code
        private VersionInfo versionInfo;
        // Instruction de code
        private @Nullable PlayerInfo playerInfo;
        // Instruction de code
        private boolean enforcesSecureChat;

        // Début d'une méthode/d'un bloc
        private Builder() {
            // Accès à l'objet courant/parent
            this.description = DEFAULT_DESCRIPTION;
            // Accès à l'objet courant/parent
            this.versionInfo = VersionInfo.DEFAULT;
            // Accès à l'objet courant/parent
            this.playerInfo = PlayerInfo.onlineCount();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private Builder(Status status) {
            // Accès à l'objet courant/parent
            this.description = status.description;
            // Accès à l'objet courant/parent
            this.favicon = status.favicon;
            // Accès à l'objet courant/parent
            this.versionInfo = status.versionInfo;
            // Accès à l'objet courant/parent
            this.playerInfo = status.playerInfo;
            // Accès à l'objet courant/parent
            this.enforcesSecureChat = status.enforcesSecureChat;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder description(Component description) {
            // Accès à l'objet courant/parent
            this.description = description;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder favicon(byte @Nullable [] favicon) {
            // Accès à l'objet courant/parent
            this.favicon = favicon;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder versionInfo(VersionInfo versionInfo) {
            // Accès à l'objet courant/parent
            this.versionInfo = versionInfo;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder playerInfo(@Nullable PlayerInfo playerInfo) {
            // Accès à l'objet courant/parent
            this.playerInfo = playerInfo;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_, _ -> this")
        // Début d'une méthode/d'un bloc
        public Builder playerInfo(int onlinePlayers, int maxPlayers) {
            // Accès à l'objet courant/parent
            this.playerInfo = new PlayerInfo(onlinePlayers, maxPlayers);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder enforcesSecureChat(boolean enforcesSecureChat) {
            // Accès à l'objet courant/parent
            this.enforcesSecureChat = enforcesSecureChat;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Status build() {
            // Renvoie une valeur à l'appelant
            return new Status(
                    // Accès à l'objet courant/parent
                    this.description,
                    // Accès à l'objet courant/parent
                    this.favicon,
                    // Accès à l'objet courant/parent
                    this.versionInfo,
                    // Accès à l'objet courant/parent
                    this.playerInfo,
                    // Accès à l'objet courant/parent
                    this.enforcesSecureChat);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (!(o instanceof Status(
                // Instruction de code
                Component description1, byte[] favicon1, VersionInfo info, PlayerInfo playerInfo1, boolean secureChat
        // Instruction de code
        ))) return false;
        // Renvoie une valeur à l'appelant
        return enforcesSecureChat() == secureChat && description().equals(description1) && Objects.equals(playerInfo(), playerInfo1) && versionInfo().equals(info) && Arrays.equals(favicon(), favicon1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = description().hashCode();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(favicon());
        // Appelle une méthode
        result = 31 * result + versionInfo().hashCode();
        // Appelle une méthode
        result = 31 * result + Objects.hashCode(playerInfo());
        // Appelle une méthode
        result = 31 * result + Boolean.hashCode(enforcesSecureChat());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
