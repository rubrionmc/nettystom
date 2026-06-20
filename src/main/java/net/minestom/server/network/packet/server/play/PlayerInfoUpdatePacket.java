// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.crypto.ChatSession;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record PlayerInfoUpdatePacket(
        // Code statement
        EnumSet<Action> actions,
        // Code statement
        List<Entry> entries
// Start of a method/block
) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final int MAX_ENTRIES = 1024;

    // Start of a method/block
    public PlayerInfoUpdatePacket(EnumSet<Action> actions, Entry entry) {
        // Calls a method
        this(actions, List.of(entry));
    // End of a block/expression
    }

    // Start of a method/block
    public PlayerInfoUpdatePacket(Action action, List<Entry> entries) {
        // Calls a method
        this(EnumSet.of(action), entries);
    // End of a block/expression
    }

    // Start of a method/block
    public PlayerInfoUpdatePacket(Action action, Entry entry) {
        // Calls a method
        this(EnumSet.of(action), List.of(entry));
    // End of a block/expression
    }

    // Start of a method/block
    public PlayerInfoUpdatePacket {
        // Calls a method
        actions = EnumSet.copyOf(actions);
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<PlayerInfoUpdatePacket> SERIALIZER = new Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer writer, PlayerInfoUpdatePacket value) {
            // Calls a method
            writer.write(EnumSet(Action.class), value.actions);
            // Calls a method
            writer.write(Entry.serializer(value.actions).list(MAX_ENTRIES), value.entries);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public PlayerInfoUpdatePacket read(NetworkBuffer reader) {
            // Calls a method
            var actions = reader.read(EnumSet(Action.class));
            // Calls a method
            var entries = reader.read(Entry.serializer(actions).list(MAX_ENTRIES));
            // Returns a value to the caller
            return new PlayerInfoUpdatePacket(actions, entries);
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Calls a method
        final List<Component> components = new ArrayList<>();
        // Loop: repeats a block
        for (final Entry entry : entries) {
            // Branch: checks a condition
            if (entry.displayName() == null) continue;
            // Calls a method
            components.add(entry.displayName());
        // End of a block/expression
        }
        // Returns a value to the caller
        return components;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Calls a method
        final List<Entry> newEntries = new ArrayList<>();
        // Loop: repeats a block
        for (final Entry entry : entries) {
            // Calls a method
            final Component displayName = entry.displayName();
            // Branch: checks a condition
            if (displayName != null) {
                // Code statement
                newEntries.add(new Entry(entry.uuid, entry.username,
                        // Code statement
                        entry.properties, entry.listed, entry.latency,
                        // Code statement
                        entry.gameMode, operator.apply(displayName),
                        // Code statement
                        entry.chatSession, entry.listOrder, entry.displayHat));
            // Alternative branch of the condition
            } else {
                // Calls a method
                newEntries.add(entry);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return new PlayerInfoUpdatePacket(actions, newEntries);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Entry(UUID uuid, String username, List<Property> properties,
                        // Code statement
                        boolean listed, int latency, GameMode gameMode,
                        // Annotation for the following element
                        @Nullable Component displayName, @Nullable ChatSession chatSession,
                        // Start of a method/block
                        int listOrder, boolean displayHat) {
        // Start of a method/block
        public Entry {
            // Calls a method
            properties = List.copyOf(properties);
        // End of a block/expression
        }

        // Start of a method/block
        public static NetworkBuffer.Type<Entry> serializer(EnumSet<Action> actions) {
            // Returns a value to the caller
            return new Type<>() {
                // Annotation for the following element
                @Override
                // Start of a method/block
                public void write(NetworkBuffer buffer, Entry value) {
                    // Calls a method
                    buffer.write(NetworkBuffer.UUID, value.uuid);
                    // Loop: repeats a block
                    for (Action action : actions) action.writer.write(buffer, value);
                // End of a block/expression
                }

                // Annotation for the following element
                @Override
                // Start of a method/block
                public Entry read(NetworkBuffer buffer) {
                    // Calls a method
                    UUID uuid = buffer.read(NetworkBuffer.UUID);
                    // Assigns a value
                    String username = "";
                    // Calls a method
                    List<Property> properties = List.of();
                    // Assigns a value
                    boolean listed = false;
                    // Assigns a value
                    int latency = 0;
                    // Assigns a value
                    GameMode gameMode = GameMode.SURVIVAL;
                    // Assigns a value
                    Component displayName = null;
                    // Assigns a value
                    ChatSession chatSession = null;
                    // Assigns a value
                    int listOrder = 0;
                    // Assigns a value
                    boolean displayHat = true;
                    // Loop: repeats a block
                    for (Action action : actions) {
                        // Multiple branching (switch/case)
                        switch (action) {
                            // Multiple branching (switch/case)
                            case ADD_PLAYER -> {
                                // Calls a method
                                username = buffer.read(STRING);
                                // Calls a method
                                properties = buffer.read(Property.SERIALIZER.list(GameProfile.MAX_PROPERTIES));
                            // End of a block/expression
                            }
                            // Multiple branching (switch/case)
                            case INITIALIZE_CHAT -> chatSession = ChatSession.SERIALIZER.optional().read(buffer);
                            // Multiple branching (switch/case)
                            case UPDATE_GAME_MODE -> gameMode = buffer.read(NetworkBuffer.Enum(GameMode.class));
                            // Multiple branching (switch/case)
                            case UPDATE_LISTED -> listed = buffer.read(BOOLEAN);
                            // Multiple branching (switch/case)
                            case UPDATE_LATENCY -> latency = buffer.read(VAR_INT);
                            // Multiple branching (switch/case)
                            case UPDATE_DISPLAY_NAME -> displayName = buffer.read(COMPONENT.optional());
                            // Multiple branching (switch/case)
                            case UPDATE_LIST_ORDER -> listOrder = buffer.read(VAR_INT);
                            // Multiple branching (switch/case)
                            case UPDATE_HAT -> displayHat = buffer.read(BOOLEAN);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                    // Returns a value to the caller
                    return new Entry(uuid, username, properties, listed, latency, gameMode, displayName, chatSession, listOrder, displayHat);
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Property(String name, String value, @Nullable String signature) {
        // Start of a method/block
        public Property(String name, String value) {
            // Calls a method
            this(name, value, null);
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<Property> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                STRING, Property::name,
                // Code statement
                STRING, Property::value,
                // Code statement
                STRING.optional(), Property::signature,
                // Code statement
                Property::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Action {
        // Start of a method/block
        ADD_PLAYER((writer, entry) -> {
            // Calls a method
            writer.write(STRING, entry.username);
            // Calls a method
            writer.write(Property.SERIALIZER.list(), entry.properties);
        // Code statement
        }),
        // Code statement
        INITIALIZE_CHAT((writer, entry) -> writer.write(ChatSession.SERIALIZER.optional(), entry.chatSession)),
        // Code statement
        UPDATE_GAME_MODE((writer, entry) -> writer.write(VAR_INT, entry.gameMode.ordinal())),
        // Code statement
        UPDATE_LISTED((writer, entry) -> writer.write(BOOLEAN, entry.listed)),
        // Code statement
        UPDATE_LATENCY((writer, entry) -> writer.write(VAR_INT, entry.latency)),
        // Code statement
        UPDATE_DISPLAY_NAME((writer, entry) -> writer.write(COMPONENT.optional(), entry.displayName)),
        // Code statement
        UPDATE_LIST_ORDER((writer, entry) -> writer.write(VAR_INT, entry.listOrder)),
        // Calls a method
        UPDATE_HAT((writer, entry) -> writer.write(BOOLEAN, entry.displayHat));

        // Code statement
        final Writer writer;

        // Start of a method/block
        Action(Writer writer) {
            // Access to the current/parent object
            this.writer = writer;
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        interface Writer {
            // Calls a method
            void write(NetworkBuffer writer, Entry entry);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
