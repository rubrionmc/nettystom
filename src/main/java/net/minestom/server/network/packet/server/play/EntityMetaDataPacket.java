// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Metadata;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record EntityMetaDataPacket(int entityId,
                                   // Début d'une méthode/d'un bloc
                                   Map<Integer, Metadata.Entry<?>> entries) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Début d'une méthode/d'un bloc
    public EntityMetaDataPacket {
        // Appelle une méthode
        entries = Map.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityMetaDataPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, EntityMetaDataPacket value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.entityId);
            // Boucle : répète un bloc
            for (Map.Entry<Integer, Metadata.Entry<?>> entry : value.entries.entrySet()) {
                // Appelle une méthode
                buffer.write(BYTE, entry.getKey().byteValue());
                // Appelle une méthode
                buffer.write(Metadata.Entry.SERIALIZER, entry.getValue());
            // Fin d'un bloc/d'une expression
            }
            // Instruction de code
            buffer.write(BYTE, (byte) 0xFF); // End
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public EntityMetaDataPacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new EntityMetaDataPacket(buffer.read(VAR_INT), readEntries(buffer));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    private static Map<Integer, Metadata.Entry<?>> readEntries(NetworkBuffer reader) {
        // Affecte une valeur
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>();
        // Boucle : répète un bloc
        while (true) {
            // Appelle une méthode
            final byte index = reader.read(BYTE);
            // Embranchement : vérifie une condition
            if (index == (byte) 0xFF) { // reached the end
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            Metadata.Entry<?> entry = Metadata.Entry.SERIALIZER.read(reader);
            // Appelle une méthode
            entries.put((int) index, entry);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return entries;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return this.entries.values()
                // Instruction de code
                .stream()
                // Instruction de code
                .map(Metadata.Entry::value)
                // Instruction de code
                .filter(entry -> entry instanceof Component)
                // Instruction de code
                .map(entry -> (Component) entry)
                // Appelle une méthode
                .toList();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Affecte une valeur
        final var entries = new HashMap<Integer, Metadata.Entry<?>>();

        // Accès à l'objet courant/parent
        this.entries.forEach((key, value) -> {
            // Appelle une méthode
            final var t = value.type();
            // Appelle une méthode
            final var v = value.value();

            // Embranchement : vérifie une condition
            if (v instanceof Component c) {
                // Appelle une méthode
                var translated = operator.apply(c);
                // Appelle une méthode
                entries.put(key, t == Metadata.TYPE_OPT_CHAT ? Metadata.OptComponent(translated) : Metadata.Component(translated));
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                entries.put(key, value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Renvoie une valeur à l'appelant
        return new EntityMetaDataPacket(this.entityId, entries);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
