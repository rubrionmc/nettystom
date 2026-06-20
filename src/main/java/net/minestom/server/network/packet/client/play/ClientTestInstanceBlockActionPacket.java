// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record ClientTestInstanceBlockActionPacket(
        // Instruction de code
        Point blockPosition,
        // Instruction de code
        Action action,
        // Instruction de code
        Data data
// Début d'une méthode/d'un bloc
) implements ClientPacket.Play {

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientTestInstanceBlockActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION, ClientTestInstanceBlockActionPacket::blockPosition,
            // Instruction de code
            Action.NETWORK_TYPE, ClientTestInstanceBlockActionPacket::action,
            // Instruction de code
            Data.NETWORK_TYPE, ClientTestInstanceBlockActionPacket::data,
            // Instruction de code
            ClientTestInstanceBlockActionPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public record Data(
            // Annotation pour l'élément suivant
            @Nullable String test,
            // Instruction de code
            Point size,
            // Instruction de code
            int rotation,
            // Instruction de code
            boolean ignoreEntities,
            // Instruction de code
            Status status,
            // Annotation pour l'élément suivant
            @Nullable Component errorMessage
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Data> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.STRING.optional(), Data::test,
                // Instruction de code
                NetworkBuffer.VECTOR3I, Data::size,
                // Instruction de code
                NetworkBuffer.VAR_INT, Data::rotation,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Data::ignoreEntities,
                // Instruction de code
                Status.NETWORK_TYPE, Data::status,
                // Instruction de code
                NetworkBuffer.COMPONENT.optional(), Data::errorMessage,
                // Instruction de code
                Data::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Action {
        // Instruction de code
        INIT,
        // Instruction de code
        QUERY,
        // Instruction de code
        SET,
        // Instruction de code
        RESET,
        // Instruction de code
        SAVE,
        // Instruction de code
        EXPORT,
        // Instruction de code
        RUN;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Action> NETWORK_TYPE = NetworkBuffer.Enum(Action.class);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Status {
        // Instruction de code
        CLEARED,
        // Instruction de code
        RUNNING,
        // Instruction de code
        FINISHED;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Status> NETWORK_TYPE = NetworkBuffer.Enum(Status.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
