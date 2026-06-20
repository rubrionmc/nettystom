// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.kyori.adventure.text.Component;
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
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record BossBarPacket(UUID uuid,
                            // Start of a method/block
                            Action action) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<BossBarPacket> SERIALIZER = new Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, BossBarPacket value) {
            // Calls a method
            buffer.write(NetworkBuffer.UUID, value.uuid);
            // Calls a method
            buffer.write(VAR_INT, value.action.id());
            // Annotation for the following element
            @SuppressWarnings("unchecked") final Type<Action> serializer = (Type<Action>) actionSerializer(value.action.id());
            // Calls a method
            buffer.write(serializer, value.action);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public BossBarPacket read(NetworkBuffer buffer) {
            // Calls a method
            final UUID uuid = buffer.read(NetworkBuffer.UUID);
            // Calls a method
            final int id = buffer.read(VAR_INT);
            // Calls a method
            final Type<? extends Action> serializer = actionSerializer(id);
            // Returns a value to the caller
            return new BossBarPacket(uuid, serializer.read(buffer));
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<? extends Component> components() {
        // Returns a value to the caller
        return this.action instanceof ComponentHolder<?> holder
                // Code statement
                ? holder.components()
                // Calls a method
                : List.of();
    // End of a block/expression
    }

    // Start of a method/block
    private static Type<? extends Action> actionSerializer(int id) {
        // Returns a value to the caller
        return switch (id) {
            // Multiple branching (switch/case)
            case 0 -> AddAction.SERIALIZER;
            // Multiple branching (switch/case)
            case 1 -> RemoveAction.SERIALIZER;
            // Multiple branching (switch/case)
            case 2 -> UpdateHealthAction.SERIALIZER;
            // Multiple branching (switch/case)
            case 3 -> UpdateTitleAction.SERIALIZER;
            // Multiple branching (switch/case)
            case 4 -> UpdateStyleAction.SERIALIZER;
            // Multiple branching (switch/case)
            case 5 -> UpdateFlagsAction.SERIALIZER;
            // Multiple branching (switch/case)
            default -> throw new RuntimeException("Unknown action id");
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return this.action instanceof ComponentHolder<?> holder
                // Code statement
                ? new BossBarPacket(this.uuid, (Action) holder.copyWithOperator(operator))
                // Code statement
                : this;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public sealed interface Action permits
            // Code statement
            AddAction, RemoveAction, UpdateHealthAction,
            // Start of a method/block
            UpdateTitleAction, UpdateStyleAction, UpdateFlagsAction {
        // Calls a method
        int id();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record AddAction(Component title, float health, BossBar.Color color,
                            // Code statement
                            BossBar.Overlay overlay,
                            // Start of a method/block
                            byte flags) implements Action, ComponentHolder<AddAction> {
        // Start of a method/block
        public AddAction(BossBar bar) {
            // Code statement
            this(bar.name(), bar.progress(), bar.color(), bar.overlay(),
                    // Calls a method
                    AdventurePacketConvertor.getBossBarFlagValue(bar.flags()));
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<AddAction> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                COMPONENT, AddAction::title,
                // Code statement
                FLOAT, AddAction::health,
                // Code statement
                Enum(BossBar.Color.class), AddAction::color,
                // Code statement
                Enum(BossBar.Overlay.class), AddAction::overlay,
                // Code statement
                BYTE, AddAction::flags,
                // Code statement
                AddAction::new
        // End of a block/expression
        );

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
            return List.of(this.title);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public AddAction copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new AddAction(operator.apply(this.title), this.health, this.color, this.overlay, this.flags);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record RemoveAction() implements Action {
        // Calls a method
        public static final NetworkBuffer.Type<RemoveAction> SERIALIZER = NetworkBufferTemplate.template(new RemoveAction());

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
    public record UpdateHealthAction(float health) implements Action {
        // Start of a method/block
        public UpdateHealthAction(BossBar bar) {
            // Calls a method
            this(bar.progress());
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<UpdateHealthAction> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                FLOAT, UpdateHealthAction::health,
                // Code statement
                UpdateHealthAction::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return 2;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record UpdateTitleAction(Component title) implements Action, ComponentHolder<UpdateTitleAction> {
        // Start of a method/block
        public UpdateTitleAction(BossBar bar) {
            // Calls a method
            this(bar.name());
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<UpdateTitleAction> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                COMPONENT, UpdateTitleAction::title,
                // Code statement
                UpdateTitleAction::new
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

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Returns a value to the caller
            return List.of(this.title);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public UpdateTitleAction copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new UpdateTitleAction(operator.apply(this.title));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record UpdateStyleAction(BossBar.Color color,
                                    // Start of a method/block
                                    BossBar.Overlay overlay) implements Action {
        // Start of a method/block
        public UpdateStyleAction(BossBar bar) {
            // Calls a method
            this(bar.color(), bar.overlay());
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<UpdateStyleAction> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                Enum(BossBar.Color.class), UpdateStyleAction::color,
                // Code statement
                Enum(BossBar.Overlay.class), UpdateStyleAction::overlay,
                // Code statement
                UpdateStyleAction::new
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

    // Type declaration (class/interface/enum/record)
    public record UpdateFlagsAction(byte flags) implements Action {
        // Start of a method/block
        public UpdateFlagsAction(BossBar bar) {
            // Calls a method
            this(AdventurePacketConvertor.getBossBarFlagValue(bar.flags()));
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<UpdateFlagsAction> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                BYTE, UpdateFlagsAction::flags,
                // Code statement
                UpdateFlagsAction::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return 5;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
