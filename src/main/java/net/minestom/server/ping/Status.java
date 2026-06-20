// Package declaration for this file
package net.minestom.server.ping;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.utils.identity.NamedAndIdentified;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.nio.charset.StandardCharsets;
// Import of a required class
import java.util.*;

// Type declaration (class/interface/enum/record)
public record Status(
        // Code statement
        Component description,
        // Code statement
        byte @Nullable [] favicon,
        // Code statement
        VersionInfo versionInfo,
        // Annotation for the following element
        @Nullable PlayerInfo playerInfo,
        // Code statement
        boolean enforcesSecureChat
// Start of a method/block
) {
    // Assigns a value
    private static final String FAVICON_PREFIX = "data:image/png;base64,";

    // Assigns a value
    public static final Codec<byte @Nullable []> FAVICON_CODEC = Codec.STRING.transform(
            // Start of a method/block
            string -> {
                // Calls a method
                Check.argCondition(!string.startsWith(FAVICON_PREFIX), "Favicon format must be a PNG image encoded in base 64!");
                // Returns a value to the caller
                return Base64.getDecoder().decode(string.substring(FAVICON_PREFIX.length()).getBytes(StandardCharsets.UTF_8));
            // Calls a method
            }, data -> FAVICON_PREFIX + new String(Base64.getEncoder().encode(data), StandardCharsets.UTF_8));

    // Assigns a value
    public static final Codec<Status> CODEC = StructCodec.struct(
            // Code statement
            "description", Codec.COMPONENT.optional(Component.empty()), Status::description,
            // Code statement
            "favicon", FAVICON_CODEC.optional(), Status::favicon,
            // Code statement
            "version", VersionInfo.CODEC, Status::versionInfo,
            // Code statement
            "players", PlayerInfo.CODEC.optional(), Status::playerInfo,
            // Code statement
            "enforcesSecureChat", Codec.BOOLEAN.optional(false), Status::enforcesSecureChat,
            // Code statement
            Status::new);

    // Start of a method/block
    public Status {
        // Branch: checks a condition
        if (favicon != null) {
            // Calls a method
            favicon = favicon.clone();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    // Start of a method/block
    public static Builder builder(Status status) {
        // Returns a value to the caller
        return new Builder(status);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record VersionInfo(String name, int protocolVersion) {
        // Calls a method
        public static final VersionInfo DEFAULT = new VersionInfo(MinecraftServer.VERSION_NAME, MinecraftServer.PROTOCOL_VERSION);
        // Assigns a value
        public static final Codec<VersionInfo> CODEC = StructCodec.struct(
                // Code statement
                "name", Codec.STRING, VersionInfo::name,
                // Code statement
                "protocol", Codec.INT, VersionInfo::protocolVersion,
                // Code statement
                VersionInfo::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record PlayerInfo(int onlinePlayers, int maxPlayers, List<NamedAndIdentified> sample) {
        // Assigns a value
        private static final Codec<Component> LEGACY_CODEC = Codec.STRING.transform(
                // Code statement
                string -> LegacyComponentSerializer.legacySection().deserialize(string),
                // Calls a method
                component -> LegacyComponentSerializer.legacySection().serialize(component));

        // Assigns a value
        private static final Codec<NamedAndIdentified> SAMPLE_CODEC = StructCodec.struct(
                // Code statement
                "name", LEGACY_CODEC, NamedAndIdentified::getName,
                // Code statement
                "id", Codec.UUID_STRING, NamedAndIdentified::getUuid,
                // Code statement
                NamedAndIdentified::of);

        // Assigns a value
        public static final Codec<PlayerInfo> CODEC = StructCodec.struct(
                // Code statement
                "online", Codec.INT, PlayerInfo::onlinePlayers,
                // Code statement
                "max", Codec.INT, PlayerInfo::maxPlayers,
                // Code statement
                "sample", SAMPLE_CODEC.list(), PlayerInfo::sample,
                // Code statement
                PlayerInfo::new);

        // Start of a method/block
        public PlayerInfo {
            // Calls a method
            sample = List.copyOf(sample);
        // End of a block/expression
        }

        // Start of a method/block
        public PlayerInfo(int onlinePlayers, int maxPlayers) {
            // Calls a method
            this(onlinePlayers, maxPlayers, List.of());
        // End of a block/expression
        }

        // Start of a method/block
        public static PlayerInfo onlineCount() {
            // Calls a method
            final Collection<Player> players = MinecraftServer.getConnectionManager().getOnlinePlayers();
            // Returns a value to the caller
            return new PlayerInfo(players.size(), players.size() + 1, List.of());
        // End of a block/expression
        }

        /**
         * @param maxSamples The maximum number of player entries to include in the sample
         * @return A {@link PlayerInfo} containing the online count, and a sample of online players.
         */
        // Start of a method/block
        public static PlayerInfo online(int maxSamples) {
            // Calls a method
            final Collection<Player> players = MinecraftServer.getConnectionManager().getOnlinePlayers();
            // Calls a method
            final List<NamedAndIdentified> samples = new ArrayList<>(Math.min(maxSamples, players.size()));
            // Loop: repeats a block
            for (final Player player : players) {
                // Branch: checks a condition
                if (!player.getSettings().allowServerListings())
                    // Continues to the next loop iteration
                    continue;
                // Calls a method
                samples.add(player);
                // Branch: checks a condition
                if (samples.size() >= maxSamples)
                    // Breaks out of the loop/block
                    break;
            // End of a block/expression
            }
            // Returns a value to the caller
            return new PlayerInfo(players.size(), players.size() + 1, samples);
        // End of a block/expression
        }

        // Start of a method/block
        public static Builder builder() {
            // Returns a value to the caller
            return new Builder();
        // End of a block/expression
        }

        // Start of a method/block
        public static Builder builder(PlayerInfo playerInfo) {
            // Returns a value to the caller
            return new Builder(playerInfo);
        // End of a block/expression
        }

        // Start of a method/block
        public static final class Builder {
            // Code statement
            private int onlinePlayers;
            // Code statement
            private int maxPlayers;
            // Code statement
            private List<NamedAndIdentified> sample;

            // Start of a method/block
            private Builder() {
                // Access to the current/parent object
                this.sample = new ArrayList<>();
            // End of a block/expression
            }

            // Start of a method/block
            private Builder(PlayerInfo playerInfo) {
                // Access to the current/parent object
                this.onlinePlayers = playerInfo.onlinePlayers;
                // Access to the current/parent object
                this.maxPlayers = playerInfo.maxPlayers;
                // Access to the current/parent object
                this.sample = new ArrayList<>(playerInfo.sample);
            // End of a block/expression
            }

            // Annotation for the following element
            @Contract(value = "_ -> this")
            // Start of a method/block
            public Builder onlinePlayers(int onlinePlayers) {
                // Access to the current/parent object
                this.onlinePlayers = onlinePlayers;
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Contract(value = "_ -> this")
            // Start of a method/block
            public Builder maxPlayers(int maxPlayers) {
                // Access to the current/parent object
                this.maxPlayers = maxPlayers;
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Contract(value = "_ -> this")
            // Start of a method/block
            public Builder sample(List<NamedAndIdentified> sample) {
                // Access to the current/parent object
                this.sample = sample;
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Contract(value = "_ -> this")
            // Start of a method/block
            public Builder sample(NamedAndIdentified profile) {
                // Access to the current/parent object
                this.sample.add(profile);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Contract(value = "_ -> this")
            // Start of a method/block
            public Builder sample(GameProfile profile) {
                // Returns a value to the caller
                return this.sample(NamedAndIdentified.of(profile.name(), profile.uuid()));
            // End of a block/expression
            }

            // Annotation for the following element
            @Contract(value = "_ -> this")
            // Start of a method/block
            public Builder sample(Component component) {
                // Returns a value to the caller
                return this.sample(NamedAndIdentified.named(component));
            // End of a block/expression
            }

            // Annotation for the following element
            @Contract(value = "_ -> this")
            // Start of a method/block
            public Builder sample(String string) {
                // Returns a value to the caller
                return this.sample(NamedAndIdentified.named(string));
            // End of a block/expression
            }

            // Start of a method/block
            public PlayerInfo build() {
                // Returns a value to the caller
                return new PlayerInfo(this.onlinePlayers, this.maxPlayers, this.sample);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static final class Builder {
        // Calls a method
        public static final Component DEFAULT_DESCRIPTION = Component.text("Minestom Server");

        // Code statement
        private Component description;
        // Code statement
        private byte @Nullable [] favicon;
        // Code statement
        private VersionInfo versionInfo;
        // Code statement
        private @Nullable PlayerInfo playerInfo;
        // Code statement
        private boolean enforcesSecureChat;

        // Start of a method/block
        private Builder() {
            // Access to the current/parent object
            this.description = DEFAULT_DESCRIPTION;
            // Access to the current/parent object
            this.versionInfo = VersionInfo.DEFAULT;
            // Access to the current/parent object
            this.playerInfo = PlayerInfo.onlineCount();
        // End of a block/expression
        }

        // Start of a method/block
        private Builder(Status status) {
            // Access to the current/parent object
            this.description = status.description;
            // Access to the current/parent object
            this.favicon = status.favicon;
            // Access to the current/parent object
            this.versionInfo = status.versionInfo;
            // Access to the current/parent object
            this.playerInfo = status.playerInfo;
            // Access to the current/parent object
            this.enforcesSecureChat = status.enforcesSecureChat;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder description(Component description) {
            // Access to the current/parent object
            this.description = description;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder favicon(byte @Nullable [] favicon) {
            // Access to the current/parent object
            this.favicon = favicon;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder versionInfo(VersionInfo versionInfo) {
            // Access to the current/parent object
            this.versionInfo = versionInfo;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder playerInfo(@Nullable PlayerInfo playerInfo) {
            // Access to the current/parent object
            this.playerInfo = playerInfo;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_, _ -> this")
        // Start of a method/block
        public Builder playerInfo(int onlinePlayers, int maxPlayers) {
            // Access to the current/parent object
            this.playerInfo = new PlayerInfo(onlinePlayers, maxPlayers);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder enforcesSecureChat(boolean enforcesSecureChat) {
            // Access to the current/parent object
            this.enforcesSecureChat = enforcesSecureChat;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Status build() {
            // Returns a value to the caller
            return new Status(
                    // Access to the current/parent object
                    this.description,
                    // Access to the current/parent object
                    this.favicon,
                    // Access to the current/parent object
                    this.versionInfo,
                    // Access to the current/parent object
                    this.playerInfo,
                    // Access to the current/parent object
                    this.enforcesSecureChat);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof Status(
                // Code statement
                Component description1, byte[] favicon1, VersionInfo info, PlayerInfo playerInfo1, boolean secureChat
        // Code statement
        ))) return false;
        // Returns a value to the caller
        return enforcesSecureChat() == secureChat && description().equals(description1) && Objects.equals(playerInfo(), playerInfo1) && versionInfo().equals(info) && Arrays.equals(favicon(), favicon1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = description().hashCode();
        // Calls a method
        result = 31 * result + Arrays.hashCode(favicon());
        // Calls a method
        result = 31 * result + versionInfo().hashCode();
        // Calls a method
        result = 31 * result + Objects.hashCode(playerInfo());
        // Calls a method
        result = 31 * result + Boolean.hashCode(enforcesSecureChat());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
