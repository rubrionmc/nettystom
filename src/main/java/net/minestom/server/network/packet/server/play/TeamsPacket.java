// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import d'une classe nécessaire
import net.minestom.server.adventure.ComponentHolder;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

/**
 * The packet creates or updates teams
 */
// Déclaration de type (classe/interface/enum/record)
public record TeamsPacket(String teamName, Action action) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final int MAX_MEMBERS = 16384;

    // Affecte une valeur
    private static final NetworkBuffer.Type<Action> ACTION_NETWORK_TYPE = Tagged(
            // Instruction de code
            NetworkBuffer.BYTE, action -> (byte) action.id(),
            // Instruction de code
            Map.of(
                    // Instruction de code
                    (byte) 0, CreateTeamAction.SERIALIZER,
                    // Instruction de code
                    (byte) 1, RemoveTeamAction.SERIALIZER,
                    // Instruction de code
                    (byte) 2, UpdateTeamAction.SERIALIZER,
                    // Instruction de code
                    (byte) 3, AddEntitiesToTeamAction.SERIALIZER,
                    // Instruction de code
                    (byte) 4, RemoveEntitiesToTeamAction.SERIALIZER
            // Fin d'un bloc/d'une expression
            )
    // Fin d'un bloc/d'une expression
    );

    // Affecte une valeur
    public static final NetworkBuffer.Type<TeamsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, TeamsPacket::teamName,
            // Instruction de code
            ACTION_NETWORK_TYPE, TeamsPacket::action,
            // Instruction de code
            TeamsPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<? extends Component> components() {
        // Renvoie une valeur à l'appelant
        return this.action instanceof ComponentHolder<?> holder ? holder.components() : List.of();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return new TeamsPacket(
                // Accès à l'objet courant/parent
                this.teamName,
                // Accès à l'objet courant/parent
                this.action instanceof ComponentHolder<?> holder
                        // Instruction de code
                        ? (Action) holder.copyWithOperator(operator)
                        // Instruction de code
                        : this.action
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public sealed interface Action permits CreateTeamAction, RemoveTeamAction, UpdateTeamAction, AddEntitiesToTeamAction, RemoveEntitiesToTeamAction {
        // Appelle une méthode
        int id();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record CreateTeamAction(Component displayName, byte friendlyFlags,
                                   // Instruction de code
                                   NameTagVisibility nameTagVisibility, CollisionRule collisionRule,
                                   // Instruction de code
                                   NamedTextColor teamColor, Component teamPrefix, Component teamSuffix,
                                   // Début d'une méthode/d'un bloc
                                   List<String> entities) implements Action, ComponentHolder<CreateTeamAction> {
        // Début d'une méthode/d'un bloc
        public CreateTeamAction {
            // Appelle une méthode
            entities = List.copyOf(entities);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<CreateTeamAction> SERIALIZER = new Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, CreateTeamAction value) {
                // Appelle une méthode
                buffer.write(COMPONENT, value.displayName);
                // Appelle une méthode
                buffer.write(BYTE, value.friendlyFlags);
                // Appelle une méthode
                buffer.write(NameTagVisibility.NETWORK_TYPE, value.nameTagVisibility);
                // Appelle une méthode
                buffer.write(CollisionRule.NETWORK_TYPE, value.collisionRule);
                // Appelle une méthode
                buffer.write(VAR_INT, AdventurePacketConvertor.getNamedTextColorValue(value.teamColor));
                // Appelle une méthode
                buffer.write(COMPONENT, value.teamPrefix);
                // Appelle une méthode
                buffer.write(COMPONENT, value.teamSuffix);
                // Appelle une méthode
                buffer.write(STRING.list(), value.entities);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public CreateTeamAction read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return new CreateTeamAction(buffer.read(COMPONENT), buffer.read(BYTE),
                        // Instruction de code
                        buffer.read(NameTagVisibility.NETWORK_TYPE), buffer.read(CollisionRule.NETWORK_TYPE),
                        // Instruction de code
                        AdventurePacketConvertor.getNamedTextColor(buffer.read(VAR_INT)), buffer.read(COMPONENT), buffer.read(COMPONENT),
                        // Appelle une méthode
                        buffer.read(STRING.list(MAX_MEMBERS)));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Renvoie une valeur à l'appelant
            return List.of(this.displayName, this.teamPrefix, this.teamSuffix);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public CreateTeamAction copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new CreateTeamAction(
                    // Instruction de code
                    operator.apply(this.displayName),
                    // Accès à l'objet courant/parent
                    this.friendlyFlags,
                    // Accès à l'objet courant/parent
                    this.nameTagVisibility,
                    // Accès à l'objet courant/parent
                    this.collisionRule,
                    // Accès à l'objet courant/parent
                    this.teamColor,
                    // Instruction de code
                    operator.apply(this.teamPrefix),
                    // Instruction de code
                    operator.apply(this.teamSuffix),
                    // Instruction de code
                    entities
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record RemoveTeamAction() implements Action {
        // Appelle une méthode
        public static final NetworkBuffer.Type<RemoveTeamAction> SERIALIZER = NetworkBufferTemplate.template(new RemoveTeamAction());

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return 1;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record UpdateTeamAction(Component displayName, byte friendlyFlags,
                                   // Instruction de code
                                   NameTagVisibility nameTagVisibility, CollisionRule collisionRule,
                                   // Instruction de code
                                   NamedTextColor teamColor,
                                   // Instruction de code
                                   Component teamPrefix,
                                   // Début d'une méthode/d'un bloc
                                   Component teamSuffix) implements Action, ComponentHolder<UpdateTeamAction> {

        // Affecte une valeur
        public static final NetworkBuffer.Type<UpdateTeamAction> SERIALIZER = new Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, UpdateTeamAction value) {
                // Appelle une méthode
                buffer.write(COMPONENT, value.displayName);
                // Appelle une méthode
                buffer.write(BYTE, value.friendlyFlags);
                // Appelle une méthode
                buffer.write(NameTagVisibility.NETWORK_TYPE, value.nameTagVisibility);
                // Appelle une méthode
                buffer.write(CollisionRule.NETWORK_TYPE, value.collisionRule);
                // Appelle une méthode
                buffer.write(VAR_INT, AdventurePacketConvertor.getNamedTextColorValue(value.teamColor));
                // Appelle une méthode
                buffer.write(COMPONENT, value.teamPrefix);
                // Appelle une méthode
                buffer.write(COMPONENT, value.teamSuffix);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public UpdateTeamAction read(NetworkBuffer buffer) {
                // Renvoie une valeur à l'appelant
                return new UpdateTeamAction(buffer.read(COMPONENT), buffer.read(BYTE),
                        // Instruction de code
                        buffer.read(NameTagVisibility.NETWORK_TYPE), buffer.read(CollisionRule.NETWORK_TYPE),
                        // Instruction de code
                        AdventurePacketConvertor.getNamedTextColor(buffer.read(VAR_INT)),
                        // Appelle une méthode
                        buffer.read(COMPONENT), buffer.read(COMPONENT));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return 2;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Renvoie une valeur à l'appelant
            return List.of(this.displayName, this.teamPrefix, this.teamSuffix);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public UpdateTeamAction copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new UpdateTeamAction(
                    // Instruction de code
                    operator.apply(this.displayName),
                    // Accès à l'objet courant/parent
                    this.friendlyFlags,
                    // Accès à l'objet courant/parent
                    this.nameTagVisibility,
                    // Accès à l'objet courant/parent
                    this.collisionRule,
                    // Accès à l'objet courant/parent
                    this.teamColor,
                    // Instruction de code
                    operator.apply(this.teamPrefix),
                    // Instruction de code
                    operator.apply(this.teamSuffix)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record AddEntitiesToTeamAction(List<String> entities) implements Action {
        // Début d'une méthode/d'un bloc
        public AddEntitiesToTeamAction {
            // Appelle une méthode
            entities = List.copyOf(entities);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public AddEntitiesToTeamAction(Collection<String> entities) {
            // Appelle une méthode
            this(List.copyOf(entities));
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<AddEntitiesToTeamAction> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                STRING.list(MAX_MEMBERS), AddEntitiesToTeamAction::entities,
                // Instruction de code
                AddEntitiesToTeamAction::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return 3;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record RemoveEntitiesToTeamAction(List<String> entities) implements Action {
        // Début d'une méthode/d'un bloc
        public RemoveEntitiesToTeamAction {
            // Appelle une méthode
            entities = List.copyOf(entities);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public RemoveEntitiesToTeamAction(Collection<String> entities) {
            // Appelle une méthode
            this(List.copyOf(entities));
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<RemoveEntitiesToTeamAction> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                STRING.list(MAX_MEMBERS), RemoveEntitiesToTeamAction::entities,
                // Instruction de code
                RemoveEntitiesToTeamAction::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return 4;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * An enumeration which representing all visibility states for the name tags
     */
    // Déclaration de type (classe/interface/enum/record)
    public enum NameTagVisibility {
        /**
         * The name tag is visible
         */
        // Instruction de code
        ALWAYS("always"),
        /**
         * The name tag is invisible
         */
        // Instruction de code
        NEVER("never"),
        /**
         * Hides the name tag for other teams
         */
        // Instruction de code
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
        /**
         * Hides the name tag for the own team
         */
        // Appelle une méthode
        HIDE_FOR_OWN_TEAM("hideForOwnTeam");

        // Appelle une méthode
        public static final NetworkBuffer.Type<NameTagVisibility> NETWORK_TYPE = NetworkBuffer.Enum(NameTagVisibility.class);

        /**
         * The identifier for the client
         */
        // Instruction de code
        private final String identifier;

        /**
         * Default constructor
         *
         * @param identifier The client identifier
         */
        // Début d'une méthode/d'un bloc
        NameTagVisibility(String identifier) {
            // Accès à l'objet courant/parent
            this.identifier = identifier;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public static NameTagVisibility fromIdentifier(String identifier) {
            // Boucle : répète un bloc
            for (NameTagVisibility v : values()) {
                // Embranchement : vérifie une condition
                if (v.getIdentifier().equals(identifier))
                    // Renvoie une valeur à l'appelant
                    return v;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            Check.fail("Identifier for NameTagVisibility is invalid: {0}", identifier);
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the client identifier
         *
         * @return the identifier
         */
        // Début d'une méthode/d'un bloc
        public String getIdentifier() {
            // Renvoie une valeur à l'appelant
            return identifier;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * An enumeration which representing all rules for the collision
     */
    // Déclaration de type (classe/interface/enum/record)
    public enum CollisionRule {
        /**
         * Can push all objects and can be pushed by all objects
         */
        // Instruction de code
        ALWAYS("always"),
        /**
         * Cannot push an object, but neither can they be pushed
         */
        // Instruction de code
        NEVER("never"),
        /**
         * Can push objects of other teams, but teammates cannot
         */
        // Instruction de code
        PUSH_OTHER_TEAMS("pushOtherTeams"),
        /**
         * Can only push objects of the same team
         */
        // Appelle une méthode
        PUSH_OWN_TEAM("pushOwnTeam");

        // Appelle une méthode
        public static final NetworkBuffer.Type<CollisionRule> NETWORK_TYPE = NetworkBuffer.Enum(CollisionRule.class);

        /**
         * The identifier for the client
         */
        // Instruction de code
        private final String identifier;

        /**
         * Default constructor
         *
         * @param identifier The identifier for the client
         */
        // Début d'une méthode/d'un bloc
        CollisionRule(String identifier) {
            // Accès à l'objet courant/parent
            this.identifier = identifier;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public static CollisionRule fromIdentifier(String identifier) {
            // Boucle : répète un bloc
            for (CollisionRule v : values()) {
                // Embranchement : vérifie une condition
                if (v.getIdentifier().equals(identifier))
                    // Renvoie une valeur à l'appelant
                    return v;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            Check.fail("Identifier for CollisionRule is invalid: {0}", identifier);
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the identifier of the rule
         *
         * @return the identifier
         */
        // Début d'une méthode/d'un bloc
        public String getIdentifier() {
            // Renvoie une valeur à l'appelant
            return identifier;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
