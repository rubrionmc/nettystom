// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record EntityEquipmentPacket(int entityId,
                                    // Start of a method/block
                                    Map<EquipmentSlot, ItemStack> equipments) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Start of a method/block
    public EntityEquipmentPacket {
        // Calls a method
        equipments = Map.copyOf(equipments);
        // Branch: checks a condition
        if (equipments.isEmpty())
            // Throws an exception
            throw new IllegalArgumentException("Equipments cannot be empty");
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<EntityEquipmentPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, EntityEquipmentPacket value) {
            // Calls a method
            buffer.write(VAR_INT, value.entityId);
            // Assigns a value
            int index = 0;
            // Loop: repeats a block
            for (var entry : value.equipments.entrySet()) {
                // Calls a method
                final boolean last = index++ == value.equipments.size() - 1;
                // Calls a method
                byte slotEnum = (byte) entry.getKey().legacyProtocolId();
                // Branch: checks a condition
                if (!last) slotEnum |= 0x80;
                // Calls a method
                buffer.write(BYTE, slotEnum);
                // Calls a method
                buffer.write(ItemStack.NETWORK_TYPE, entry.getValue());
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public EntityEquipmentPacket read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new EntityEquipmentPacket(buffer.read(VAR_INT), readEquipments(buffer));
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Calls a method
        final var components = new ArrayList<Component>();
        // Loop: repeats a block
        for (var itemStack : this.equipments.values())
            // Calls a method
            components.addAll(ItemStack.textComponents(itemStack));
        // Returns a value to the caller
        return List.copyOf(components);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Calls a method
        final var newEquipment = new EnumMap<EquipmentSlot, ItemStack>(EquipmentSlot.class);
        // Loop: repeats a block
        for (var entry : this.equipments.entrySet())
            // Calls a method
            newEquipment.put(entry.getKey(), ItemStack.copyWithOperator(entry.getValue(), operator));
        // Returns a value to the caller
        return new EntityEquipmentPacket(this.entityId, newEquipment);
    // End of a block/expression
    }

    // Start of a method/block
    private static Map<EquipmentSlot, ItemStack> readEquipments(NetworkBuffer reader) {
        // Calls a method
        Map<EquipmentSlot, ItemStack> equipments = new EnumMap<>(EquipmentSlot.class);
        // Code statement
        byte slot;
        // Loop: repeats a block
        do {
            // Calls a method
            slot = reader.read(BYTE);
            // Calls a method
            equipments.put(EquipmentSlot.fromLegacyProtocolId(slot & 0x7F), reader.read(ItemStack.NETWORK_TYPE));
        // Calls a method
        } while ((slot & 0x80) == 0x80);
        // Returns a value to the caller
        return equipments;
    // End of a block/expression
    }
// End of a block/expression
}
