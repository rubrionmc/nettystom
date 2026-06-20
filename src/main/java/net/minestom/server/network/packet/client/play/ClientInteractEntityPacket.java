// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientInteractEntityPacket(int targetId, Type type, boolean sneaking) implements ClientPacket {

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientInteractEntityPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, ClientInteractEntityPacket value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.targetId);
            // Appelle une méthode
            buffer.write(VAR_INT, value.type.id());
            // Annotation pour l'élément suivant
            @SuppressWarnings("unchecked") NetworkBuffer.Type<Type> serializer = (NetworkBuffer.Type<Type>) typeSerializer(value.type.id());
            // Appelle une méthode
            buffer.write(serializer, value.type);
            // Appelle une méthode
            buffer.write(BOOLEAN, value.sneaking);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ClientInteractEntityPacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int targetId = buffer.read(VAR_INT);
            // Appelle une méthode
            final Type type = typeSerializer(buffer.read(VAR_INT)).read(buffer);
            // Appelle une méthode
            final boolean sneaking = buffer.read(BOOLEAN);
            // Renvoie une valeur à l'appelant
            return new ClientInteractEntityPacket(targetId, type, sneaking);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    private static NetworkBuffer.Type<? extends Type> typeSerializer(int id) {
        // Renvoie une valeur à l'appelant
        return switch (id) {
            // Embranchement multiple (switch/case)
            case 0 -> Interact.SERIALIZER;
            // Embranchement multiple (switch/case)
            case 1 -> Attack.SERIALIZER;
            // Embranchement multiple (switch/case)
            case 2 -> InteractAt.SERIALIZER;
            // Appelle une méthode
            default -> throw new RuntimeException("Unknown action id");
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public sealed interface Type permits Interact, Attack, InteractAt {
        // Appelle une méthode
        int id();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Interact(PlayerHand hand) implements Type {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Interact> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.Enum(PlayerHand.class), Interact::hand,
                // Instruction de code
                Interact::new
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
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Attack() implements Type {
        // Appelle une méthode
        public static final NetworkBuffer.Type<Attack> SERIALIZER = NetworkBufferTemplate.template(new Attack());

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
    public record InteractAt(float targetX, float targetY, float targetZ,
                             // Début d'une méthode/d'un bloc
                             PlayerHand hand) implements Type {
        // Affecte une valeur
        public static final NetworkBuffer.Type<InteractAt> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                FLOAT, InteractAt::targetX,
                // Instruction de code
                FLOAT, InteractAt::targetY,
                // Instruction de code
                FLOAT, InteractAt::targetZ,
                // Instruction de code
                NetworkBuffer.Enum(PlayerHand.class), InteractAt::hand,
                // Instruction de code
                InteractAt::new
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
// Fin d'un bloc/d'une expression
}
