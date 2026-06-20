// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
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
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record BossBarPacket(UUID uuid,
                            // Début d'une méthode/d'un bloc
                            Action action) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final NetworkBuffer.Type<BossBarPacket> SERIALIZER = new Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, BossBarPacket value) {
            // Appelle une méthode
            buffer.write(NetworkBuffer.UUID, value.uuid);
            // Appelle une méthode
            buffer.write(VAR_INT, value.action.id());
            // Annotation pour l'élément suivant
            @SuppressWarnings("unchecked") final Type<Action> serializer = (Type<Action>) actionSerializer(value.action.id());
            // Appelle une méthode
            buffer.write(serializer, value.action);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public BossBarPacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            final UUID uuid = buffer.read(NetworkBuffer.UUID);
            // Appelle une méthode
            final int id = buffer.read(VAR_INT);
            // Appelle une méthode
            final Type<? extends Action> serializer = actionSerializer(id);
            // Renvoie une valeur à l'appelant
            return new BossBarPacket(uuid, serializer.read(buffer));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return this.action instanceof ComponentHolder<?> holder
                // Instruction de code
                ? holder.components()
                // Appelle une méthode
                : List.of();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Type<? extends Action> actionSerializer(int id) {
        // Renvoie une valeur à l'appelant
        return switch (id) {
            // Embranchement multiple (switch/case)
            case 0 -> AddAction.SERIALIZER;
            // Embranchement multiple (switch/case)
            case 1 -> RemoveAction.SERIALIZER;
            // Embranchement multiple (switch/case)
            case 2 -> UpdateHealthAction.SERIALIZER;
            // Embranchement multiple (switch/case)
            case 3 -> UpdateTitleAction.SERIALIZER;
            // Embranchement multiple (switch/case)
            case 4 -> UpdateStyleAction.SERIALIZER;
            // Embranchement multiple (switch/case)
            case 5 -> UpdateFlagsAction.SERIALIZER;
            // Appelle une méthode
            default -> throw new RuntimeException("Unknown action id");
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return this.action instanceof ComponentHolder<?> holder
                // Instruction de code
                ? new BossBarPacket(this.uuid, (Action) holder.copyWithOperator(operator))
                // Instruction de code
                : this;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public sealed interface Action permits
            // Instruction de code
            AddAction, RemoveAction, UpdateHealthAction,
            // Début d'une méthode/d'un bloc
            UpdateTitleAction, UpdateStyleAction, UpdateFlagsAction {
        // Appelle une méthode
        int id();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record AddAction(Component title, float health, BossBar.Color color,
                            // Instruction de code
                            BossBar.Overlay overlay,
                            // Début d'une méthode/d'un bloc
                            byte flags) implements Action, ComponentHolder<AddAction> {
        // Début d'une méthode/d'un bloc
        public AddAction(BossBar bar) {
            // Instruction de code
            this(bar.name(), bar.progress(), bar.color(), bar.overlay(),
                    // Appelle une méthode
                    AdventurePacketConvertor.getBossBarFlagValue(bar.flags()));
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<AddAction> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                COMPONENT, AddAction::title,
                // Instruction de code
                FLOAT, AddAction::health,
                // Instruction de code
                Enum(BossBar.Color.class), AddAction::color,
                // Instruction de code
                Enum(BossBar.Overlay.class), AddAction::overlay,
                // Instruction de code
                BYTE, AddAction::flags,
                // Instruction de code
                AddAction::new
        // Fin d'un bloc/d'une expression
        );

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
            return List.of(this.title);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public AddAction copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new AddAction(operator.apply(this.title), this.health, this.color, this.overlay, this.flags);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record RemoveAction() implements Action {
        // Appelle une méthode
        public static final NetworkBuffer.Type<RemoveAction> SERIALIZER = NetworkBufferTemplate.template(new RemoveAction());

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
    public record UpdateHealthAction(float health) implements Action {
        // Début d'une méthode/d'un bloc
        public UpdateHealthAction(BossBar bar) {
            // Appelle une méthode
            this(bar.progress());
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<UpdateHealthAction> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                FLOAT, UpdateHealthAction::health,
                // Instruction de code
                UpdateHealthAction::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return 2;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record UpdateTitleAction(Component title) implements Action, ComponentHolder<UpdateTitleAction> {
        // Début d'une méthode/d'un bloc
        public UpdateTitleAction(BossBar bar) {
            // Appelle une méthode
            this(bar.name());
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<UpdateTitleAction> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                COMPONENT, UpdateTitleAction::title,
                // Instruction de code
                UpdateTitleAction::new
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

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Renvoie une valeur à l'appelant
            return List.of(this.title);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public UpdateTitleAction copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new UpdateTitleAction(operator.apply(this.title));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record UpdateStyleAction(BossBar.Color color,
                                    // Début d'une méthode/d'un bloc
                                    BossBar.Overlay overlay) implements Action {
        // Début d'une méthode/d'un bloc
        public UpdateStyleAction(BossBar bar) {
            // Appelle une méthode
            this(bar.color(), bar.overlay());
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<UpdateStyleAction> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                Enum(BossBar.Color.class), UpdateStyleAction::color,
                // Instruction de code
                Enum(BossBar.Overlay.class), UpdateStyleAction::overlay,
                // Instruction de code
                UpdateStyleAction::new
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

    // Déclaration de type (classe/interface/enum/record)
    public record UpdateFlagsAction(byte flags) implements Action {
        // Début d'une méthode/d'un bloc
        public UpdateFlagsAction(BossBar bar) {
            // Appelle une méthode
            this(AdventurePacketConvertor.getBossBarFlagValue(bar.flags()));
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<UpdateFlagsAction> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                BYTE, UpdateFlagsAction::flags,
                // Instruction de code
                UpdateFlagsAction::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return 5;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
