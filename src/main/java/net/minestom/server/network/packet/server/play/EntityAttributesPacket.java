// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeModifier;
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

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.DOUBLE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record EntityAttributesPacket(int entityId, List<Property> properties) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_ENTRIES = 1024;

    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityAttributesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, EntityAttributesPacket::entityId,
            // Instruction de code
            Property.SERIALIZER.list(MAX_ENTRIES), EntityAttributesPacket::properties,
            // Instruction de code
            EntityAttributesPacket::new);

    // Début d'une méthode/d'un bloc
    public EntityAttributesPacket {
        // Appelle une méthode
        properties = List.copyOf(properties);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Property(Attribute attribute, double value, List<AttributeModifier> modifiers) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Property> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                Attribute.NETWORK_TYPE, Property::attribute,
                // Boucle : répète un bloc
                DOUBLE, Property::value,
                // Instruction de code
                AttributeModifier.NETWORK_TYPE.list(), Property::modifiers,
                // Instruction de code
                Property::new);

        // Début d'une méthode/d'un bloc
        public Property {
            // Appelle une méthode
            modifiers = List.copyOf(modifiers);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Property(Attribute attribute, double value, Collection<AttributeModifier> modifiers) {
            // Appelle une méthode
            this(attribute, value, List.copyOf(modifiers));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
