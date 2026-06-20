// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeModifier;
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

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.DOUBLE;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record EntityAttributesPacket(int entityId, List<Property> properties) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_ENTRIES = 1024;

    // Assigns a value
    public static final NetworkBuffer.Type<EntityAttributesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, EntityAttributesPacket::entityId,
            // Code statement
            Property.SERIALIZER.list(MAX_ENTRIES), EntityAttributesPacket::properties,
            // Code statement
            EntityAttributesPacket::new);

    // Start of a method/block
    public EntityAttributesPacket {
        // Calls a method
        properties = List.copyOf(properties);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Property(Attribute attribute, double value, List<AttributeModifier> modifiers) {
        // Assigns a value
        public static final NetworkBuffer.Type<Property> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                Attribute.NETWORK_TYPE, Property::attribute,
                // Code statement
                DOUBLE, Property::value,
                // Code statement
                AttributeModifier.NETWORK_TYPE.list(), Property::modifiers,
                // Code statement
                Property::new);

        // Start of a method/block
        public Property {
            // Calls a method
            modifiers = List.copyOf(modifiers);
        // End of a block/expression
        }

        // Start of a method/block
        public Property(Attribute attribute, double value, Collection<AttributeModifier> modifiers) {
            // Calls a method
            this(attribute, value, List.copyOf(modifiers));
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
