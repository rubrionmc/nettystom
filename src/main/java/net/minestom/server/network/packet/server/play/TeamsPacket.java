// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import of a required class
import net.minestom.server.adventure.ComponentHolder;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

/**
 * The packet creates or updates teams
 */
// Type declaration (class/interface/enum/record)
public record TeamsPacket(String teamName, Action action) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final int MAX_MEMBERS = 16384;

    // Assigns a value
    private static final NetworkBuffer.Type<Action> ACTION_NETWORK_TYPE = Tagged(
            // Code statement
            NetworkBuffer.BYTE, action -> (byte) action.id(),
            // Code statement
            Map.of(
                    // Code statement
                    (byte) 0, CreateTeamAction.SERIALIZER,
                    // Code statement
                    (byte) 1, RemoveTeamAction.SERIALIZER,
                    // Code statement
                    (byte) 2, UpdateTeamAction.SERIALIZER,
                    // Code statement
                    (byte) 3, AddEntitiesToTeamAction.SERIALIZER,
                    // Code statement
                    (byte) 4, RemoveEntitiesToTeamAction.SERIALIZER
            // End of a block/expression
            )
    // End of a block/expression
    );

    // Assigns a value
    public static final NetworkBuffer.Type<TeamsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, TeamsPacket::teamName,
            // Code statement
            ACTION_NETWORK_TYPE, TeamsPacket::action,
            // Code statement
            TeamsPacket::new
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<? extends Component> components() {
        // Returns a value to the caller
        return this.action instanceof ComponentHolder<?> holder ? holder.components() : List.of();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new TeamsPacket(
                // Access to the current/parent object
                this.teamName,
                // Access to the current/parent object
                this.action instanceof ComponentHolder<?> holder
                        // Code statement
                        ? (Action) holder.copyWithOperator(operator)
                        // Code statement
                        : this.action
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public sealed interface Action permits CreateTeamAction, RemoveTeamAction, UpdateTeamAction, AddEntitiesToTeamAction, RemoveEntitiesToTeamAction {
        // Calls a method
        int id();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record CreateTeamAction(Component displayName, byte friendlyFlags,
                                   // Code statement
                                   NameTagVisibility nameTagVisibility, CollisionRule collisionRule,
                                   // Code statement
                                   NamedTextColor teamColor, Component teamPrefix, Component teamSuffix,
                                   // Start of a method/block
                                   List<String> entities) implements Action, ComponentHolder<CreateTeamAction> {
        // Start of a method/block
        public CreateTeamAction {
            // Calls a method
            entities = List.copyOf(entities);
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<CreateTeamAction> SERIALIZER = new Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, CreateTeamAction value) {
                // Calls a method
                buffer.write(COMPONENT, value.displayName);
                // Calls a method
                buffer.write(BYTE, value.friendlyFlags);
                // Calls a method
                buffer.write(NameTagVisibility.NETWORK_TYPE, value.nameTagVisibility);
                // Calls a method
                buffer.write(CollisionRule.NETWORK_TYPE, value.collisionRule);
                // Calls a method
                buffer.write(VAR_INT, AdventurePacketConvertor.getNamedTextColorValue(value.teamColor));
                // Calls a method
                buffer.write(COMPONENT, value.teamPrefix);
                // Calls a method
                buffer.write(COMPONENT, value.teamSuffix);
                // Calls a method
                buffer.write(STRING.list(), value.entities);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public CreateTeamAction read(NetworkBuffer buffer) {
                // Returns a value to the caller
                return new CreateTeamAction(buffer.read(COMPONENT), buffer.read(BYTE),
                        // Code statement
                        buffer.read(NameTagVisibility.NETWORK_TYPE), buffer.read(CollisionRule.NETWORK_TYPE),
                        // Code statement
                        AdventurePacketConvertor.getNamedTextColor(buffer.read(VAR_INT)), buffer.read(COMPONENT), buffer.read(COMPONENT),
                        // Calls a method
                        buffer.read(STRING.list(MAX_MEMBERS)));
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Returns a value to the caller
            return List.of(this.displayName, this.teamPrefix, this.teamSuffix);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public CreateTeamAction copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new CreateTeamAction(
                    // Code statement
                    operator.apply(this.displayName),
                    // Access to the current/parent object
                    this.friendlyFlags,
                    // Access to the current/parent object
                    this.nameTagVisibility,
                    // Access to the current/parent object
                    this.collisionRule,
                    // Access to the current/parent object
                    this.teamColor,
                    // Code statement
                    operator.apply(this.teamPrefix),
                    // Code statement
                    operator.apply(this.teamSuffix),
                    // Code statement
                    entities
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record RemoveTeamAction() implements Action {
        // Calls a method
        public static final NetworkBuffer.Type<RemoveTeamAction> SERIALIZER = NetworkBufferTemplate.template(new RemoveTeamAction());

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return 1;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record UpdateTeamAction(Component displayName, byte friendlyFlags,
                                   // Code statement
                                   NameTagVisibility nameTagVisibility, CollisionRule collisionRule,
                                   // Code statement
                                   NamedTextColor teamColor,
                                   // Code statement
                                   Component teamPrefix,
                                   // Start of a method/block
                                   Component teamSuffix) implements Action, ComponentHolder<UpdateTeamAction> {

        // Assigns a value
        public static final NetworkBuffer.Type<UpdateTeamAction> SERIALIZER = new Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, UpdateTeamAction value) {
                // Calls a method
                buffer.write(COMPONENT, value.displayName);
                // Calls a method
                buffer.write(BYTE, value.friendlyFlags);
                // Calls a method
                buffer.write(NameTagVisibility.NETWORK_TYPE, value.nameTagVisibility);
                // Calls a method
                buffer.write(CollisionRule.NETWORK_TYPE, value.collisionRule);
                // Calls a method
                buffer.write(VAR_INT, AdventurePacketConvertor.getNamedTextColorValue(value.teamColor));
                // Calls a method
                buffer.write(COMPONENT, value.teamPrefix);
                // Calls a method
                buffer.write(COMPONENT, value.teamSuffix);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public UpdateTeamAction read(NetworkBuffer buffer) {
                // Returns a value to the caller
                return new UpdateTeamAction(buffer.read(COMPONENT), buffer.read(BYTE),
                        // Code statement
                        buffer.read(NameTagVisibility.NETWORK_TYPE), buffer.read(CollisionRule.NETWORK_TYPE),
                        // Code statement
                        AdventurePacketConvertor.getNamedTextColor(buffer.read(VAR_INT)),
                        // Calls a method
                        buffer.read(COMPONENT), buffer.read(COMPONENT));
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return 2;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Returns a value to the caller
            return List.of(this.displayName, this.teamPrefix, this.teamSuffix);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public UpdateTeamAction copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new UpdateTeamAction(
                    // Code statement
                    operator.apply(this.displayName),
                    // Access to the current/parent object
                    this.friendlyFlags,
                    // Access to the current/parent object
                    this.nameTagVisibility,
                    // Access to the current/parent object
                    this.collisionRule,
                    // Access to the current/parent object
                    this.teamColor,
                    // Code statement
                    operator.apply(this.teamPrefix),
                    // Code statement
                    operator.apply(this.teamSuffix)
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record AddEntitiesToTeamAction(List<String> entities) implements Action {
        // Start of a method/block
        public AddEntitiesToTeamAction {
            // Calls a method
            entities = List.copyOf(entities);
        // End of a block/expression
        }

        // Start of a method/block
        public AddEntitiesToTeamAction(Collection<String> entities) {
            // Calls a method
            this(List.copyOf(entities));
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<AddEntitiesToTeamAction> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                STRING.list(MAX_MEMBERS), AddEntitiesToTeamAction::entities,
                // Code statement
                AddEntitiesToTeamAction::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return 3;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record RemoveEntitiesToTeamAction(List<String> entities) implements Action {
        // Start of a method/block
        public RemoveEntitiesToTeamAction {
            // Calls a method
            entities = List.copyOf(entities);
        // End of a block/expression
        }

        // Start of a method/block
        public RemoveEntitiesToTeamAction(Collection<String> entities) {
            // Calls a method
            this(List.copyOf(entities));
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<RemoveEntitiesToTeamAction> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                STRING.list(MAX_MEMBERS), RemoveEntitiesToTeamAction::entities,
                // Code statement
                RemoveEntitiesToTeamAction::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return 4;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * An enumeration which representing all visibility states for the name tags
     */
    // Type declaration (class/interface/enum/record)
    public enum NameTagVisibility {
        /**
         * The name tag is visible
         */
        // Code statement
        ALWAYS("always"),
        /**
         * The name tag is invisible
         */
        // Code statement
        NEVER("never"),
        /**
         * Hides the name tag for other teams
         */
        // Code statement
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
        /**
         * Hides the name tag for the own team
         */
        // Calls a method
        HIDE_FOR_OWN_TEAM("hideForOwnTeam");

        // Calls a method
        public static final NetworkBuffer.Type<NameTagVisibility> NETWORK_TYPE = NetworkBuffer.Enum(NameTagVisibility.class);

        /**
         * The identifier for the client
         */
        // Code statement
        private final String identifier;

        /**
         * Default constructor
         *
         * @param identifier The client identifier
         */
        // Start of a method/block
        NameTagVisibility(String identifier) {
            // Access to the current/parent object
            this.identifier = identifier;
        // End of a block/expression
        }

        // Start of a method/block
        public static NameTagVisibility fromIdentifier(String identifier) {
            // Loop: repeats a block
            for (NameTagVisibility v : values()) {
                // Branch: checks a condition
                if (v.getIdentifier().equals(identifier))
                    // Returns a value to the caller
                    return v;
            // End of a block/expression
            }
            // Calls a method
            Check.fail("Identifier for NameTagVisibility is invalid: {0}", identifier);
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        /**
         * Gets the client identifier
         *
         * @return the identifier
         */
        // Start of a method/block
        public String getIdentifier() {
            // Returns a value to the caller
            return identifier;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * An enumeration which representing all rules for the collision
     */
    // Type declaration (class/interface/enum/record)
    public enum CollisionRule {
        /**
         * Can push all objects and can be pushed by all objects
         */
        // Code statement
        ALWAYS("always"),
        /**
         * Cannot push an object, but neither can they be pushed
         */
        // Code statement
        NEVER("never"),
        /**
         * Can push objects of other teams, but teammates cannot
         */
        // Code statement
        PUSH_OTHER_TEAMS("pushOtherTeams"),
        /**
         * Can only push objects of the same team
         */
        // Calls a method
        PUSH_OWN_TEAM("pushOwnTeam");

        // Calls a method
        public static final NetworkBuffer.Type<CollisionRule> NETWORK_TYPE = NetworkBuffer.Enum(CollisionRule.class);

        /**
         * The identifier for the client
         */
        // Code statement
        private final String identifier;

        /**
         * Default constructor
         *
         * @param identifier The identifier for the client
         */
        // Start of a method/block
        CollisionRule(String identifier) {
            // Access to the current/parent object
            this.identifier = identifier;
        // End of a block/expression
        }

        // Start of a method/block
        public static CollisionRule fromIdentifier(String identifier) {
            // Loop: repeats a block
            for (CollisionRule v : values()) {
                // Branch: checks a condition
                if (v.getIdentifier().equals(identifier))
                    // Returns a value to the caller
                    return v;
            // End of a block/expression
            }
            // Calls a method
            Check.fail("Identifier for CollisionRule is invalid: {0}", identifier);
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        /**
         * Gets the identifier of the rule
         *
         * @return the identifier
         */
        // Start of a method/block
        public String getIdentifier() {
            // Returns a value to the caller
            return identifier;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
