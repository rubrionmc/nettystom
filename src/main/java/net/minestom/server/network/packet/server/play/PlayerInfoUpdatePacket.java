// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.crypto.ChatSession;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record PlayerInfoUpdatePacket(
        // Instruction de code
        EnumSet<Action> actions,
        // Instruction de code
        List<Entry> entries
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final int MAX_ENTRIES = 1024;

    // Début d'une méthode/d'un bloc
    public PlayerInfoUpdatePacket(Action action, Entry entry) {
        // Appelle une méthode
        this(EnumSet.of(action), List.of(entry));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PlayerInfoUpdatePacket {
        // Appelle une méthode
        actions = EnumSet.copyOf(actions);
        // Appelle une méthode
        entries = List.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<PlayerInfoUpdatePacket> SERIALIZER = new Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer writer, PlayerInfoUpdatePacket value) {
            // Appelle une méthode
            writer.write(EnumSet(Action.class), value.actions);
            // Appelle une méthode
            writer.write(Entry.serializer(value.actions).list(MAX_ENTRIES), value.entries);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public PlayerInfoUpdatePacket read(NetworkBuffer reader) {
            // Appelle une méthode
            var actions = reader.read(EnumSet(Action.class));
            // Appelle une méthode
            var entries = reader.read(Entry.serializer(actions).list(MAX_ENTRIES));
            // Renvoie une valeur à l'appelant
            return new PlayerInfoUpdatePacket(actions, entries);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Affecte une valeur
        final List<Component> components = new ArrayList<>();
        // Boucle : répète un bloc
        for (final Entry entry : entries) {
            // Embranchement : vérifie une condition
            if (entry.displayName() == null) continue;
            // Appelle une méthode
            components.add(entry.displayName());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return components;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Affecte une valeur
        final List<Entry> newEntries = new ArrayList<>();
        // Boucle : répète un bloc
        for (final Entry entry : entries) {
            // Appelle une méthode
            final Component displayName = entry.displayName();
            // Embranchement : vérifie une condition
            if (displayName != null) {
                // Instruction de code
                newEntries.add(new Entry(entry.uuid, entry.username,
                        // Instruction de code
                        entry.properties, entry.listed, entry.latency,
                        // Instruction de code
                        entry.gameMode, operator.apply(displayName),
                        // Instruction de code
                        entry.chatSession, entry.listOrder, entry.displayHat));
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                newEntries.add(entry);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new PlayerInfoUpdatePacket(actions, newEntries);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Entry(UUID uuid, String username, List<Property> properties,
                        // Instruction de code
                        boolean listed, int latency, GameMode gameMode,
                        // Annotation pour l'élément suivant
                        @Nullable Component displayName, @Nullable ChatSession chatSession,
                        // Début d'une méthode/d'un bloc
                        int listOrder, boolean displayHat) {
        // Début d'une méthode/d'un bloc
        public Entry {
            // Appelle une méthode
            properties = List.copyOf(properties);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public static NetworkBuffer.Type<Entry> serializer(EnumSet<Action> actions) {
            // Renvoie une valeur à l'appelant
            return new Type<>() {
                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public void write(NetworkBuffer buffer, Entry value) {
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.UUID, value.uuid);
                    // Boucle : répète un bloc
                    for (Action action : actions) action.writer.write(buffer, value);
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public Entry read(NetworkBuffer buffer) {
                    // Appelle une méthode
                    UUID uuid = buffer.read(NetworkBuffer.UUID);
                    // Affecte une valeur
                    String username = "";
                    // Appelle une méthode
                    List<Property> properties = List.of();
                    // Affecte une valeur
                    boolean listed = false;
                    // Affecte une valeur
                    int latency = 0;
                    // Affecte une valeur
                    GameMode gameMode = GameMode.SURVIVAL;
                    // Affecte une valeur
                    Component displayName = null;
                    // Affecte une valeur
                    ChatSession chatSession = null;
                    // Affecte une valeur
                    int listOrder = 0;
                    // Affecte une valeur
                    boolean displayHat = true;
                    // Boucle : répète un bloc
                    for (Action action : actions) {
                        // Embranchement multiple (switch/case)
                        switch (action) {
                            // Embranchement multiple (switch/case)
                            case ADD_PLAYER -> {
                                // Appelle une méthode
                                username = buffer.read(STRING);
                                // Appelle une méthode
                                properties = buffer.read(Property.SERIALIZER.list(GameProfile.MAX_PROPERTIES));
                            // Fin d'un bloc/d'une expression
                            }
                            // Embranchement multiple (switch/case)
                            case INITIALIZE_CHAT -> chatSession = ChatSession.SERIALIZER.optional().read(buffer);
                            // Embranchement multiple (switch/case)
                            case UPDATE_GAME_MODE -> gameMode = buffer.read(NetworkBuffer.Enum(GameMode.class));
                            // Embranchement multiple (switch/case)
                            case UPDATE_LISTED -> listed = buffer.read(BOOLEAN);
                            // Embranchement multiple (switch/case)
                            case UPDATE_LATENCY -> latency = buffer.read(VAR_INT);
                            // Embranchement multiple (switch/case)
                            case UPDATE_DISPLAY_NAME -> displayName = buffer.read(COMPONENT.optional());
                            // Embranchement multiple (switch/case)
                            case UPDATE_LIST_ORDER -> listOrder = buffer.read(VAR_INT);
                            // Embranchement multiple (switch/case)
                            case UPDATE_HAT -> displayHat = buffer.read(BOOLEAN);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                    // Renvoie une valeur à l'appelant
                    return new Entry(uuid, username, properties, listed, latency, gameMode, displayName, chatSession, listOrder, displayHat);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Property(String name, String value, @Nullable String signature) {
        // Début d'une méthode/d'un bloc
        public Property(String name, String value) {
            // Appelle une méthode
            this(name, value, null);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<Property> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                STRING, Property::name,
                // Instruction de code
                STRING, Property::value,
                // Instruction de code
                STRING.optional(), Property::signature,
                // Instruction de code
                Property::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Action {
        // Début d'une méthode/d'un bloc
        ADD_PLAYER((writer, entry) -> {
            // Appelle une méthode
            writer.write(STRING, entry.username);
            // Appelle une méthode
            writer.write(Property.SERIALIZER.list(), entry.properties);
        // Instruction de code
        }),
        // Instruction de code
        INITIALIZE_CHAT((writer, entry) -> writer.write(ChatSession.SERIALIZER.optional(), entry.chatSession)),
        // Instruction de code
        UPDATE_GAME_MODE((writer, entry) -> writer.write(VAR_INT, entry.gameMode.ordinal())),
        // Instruction de code
        UPDATE_LISTED((writer, entry) -> writer.write(BOOLEAN, entry.listed)),
        // Instruction de code
        UPDATE_LATENCY((writer, entry) -> writer.write(VAR_INT, entry.latency)),
        // Instruction de code
        UPDATE_DISPLAY_NAME((writer, entry) -> writer.write(COMPONENT.optional(), entry.displayName)),
        // Instruction de code
        UPDATE_LIST_ORDER((writer, entry) -> writer.write(VAR_INT, entry.listOrder)),
        // Appelle une méthode
        UPDATE_HAT((writer, entry) -> writer.write(BOOLEAN, entry.displayHat));

        // Instruction de code
        final Writer writer;

        // Début d'une méthode/d'un bloc
        Action(Writer writer) {
            // Accès à l'objet courant/parent
            this.writer = writer;
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        interface Writer {
            // Appelle une méthode
            void write(NetworkBuffer writer, Entry entry);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
