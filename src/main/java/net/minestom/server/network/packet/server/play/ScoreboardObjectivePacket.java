// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.Sidebar;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ScoreboardObjectivePacket(String objectiveName, byte mode,
                                        // Annotation pour l'élément suivant
                                        @Nullable Component objectiveValue,
                                        // Annotation pour l'élément suivant
                                        @Nullable Type type,
                                        // Annotation pour l'élément suivant
                                        @Nullable Sidebar.NumberFormat numberFormat) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ScoreboardObjectivePacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, ScoreboardObjectivePacket value) {
            // Appelle une méthode
            buffer.write(STRING, value.objectiveName);
            // Appelle une méthode
            buffer.write(BYTE, value.mode);
            // Embranchement : vérifie une condition
            if (value.mode == 0 || value.mode == 2) {
                // Instruction de code
                assert value.objectiveValue != null;
                // Appelle une méthode
                buffer.write(COMPONENT, value.objectiveValue);
                // Instruction de code
                assert value.type != null;
                // Appelle une méthode
                buffer.write(VAR_INT, value.type.ordinal());
                // Appelle une méthode
                buffer.write(Sidebar.NumberFormat.SERIALIZER.optional(), value.numberFormat);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ScoreboardObjectivePacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            String objectiveName = buffer.read(STRING);
            // Appelle une méthode
            byte mode = buffer.read(BYTE);
            // Affecte une valeur
            Component objectiveValue = null;
            // Affecte une valeur
            Type type = null;
            // Affecte une valeur
            Sidebar.NumberFormat numberFormat = null;
            // Embranchement : vérifie une condition
            if (mode == 0 || mode == 2) {
                // Appelle une méthode
                objectiveValue = buffer.read(COMPONENT);
                // Appelle une méthode
                type = Type.values()[buffer.read(VAR_INT)];
                // Appelle une méthode
                numberFormat = buffer.read(Sidebar.NumberFormat.SERIALIZER.optional());
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new ScoreboardObjectivePacket(objectiveName, mode, objectiveValue, type, numberFormat);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return mode == 0 || mode == 2 ? List.of(objectiveValue) :
                // Appelle une méthode
                List.of();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return mode == 0 || mode == 2 ? new ScoreboardObjectivePacket(objectiveName, mode,
                // Appelle une méthode
                operator.apply(objectiveValue), type, numberFormat) : this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * This enumeration represents all available types for the scoreboard objective
     */
    // Déclaration de type (classe/interface/enum/record)
    public enum Type {
        // Instruction de code
        INTEGER,
        // Instruction de code
        HEARTS
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
