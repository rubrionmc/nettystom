// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record EntityEquipmentPacket(int entityId,
                                    // Début d'une méthode/d'un bloc
                                    Map<EquipmentSlot, ItemStack> equipments) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Début d'une méthode/d'un bloc
    public EntityEquipmentPacket {
        // Appelle une méthode
        equipments = Map.copyOf(equipments);
        // Embranchement : vérifie une condition
        if (equipments.isEmpty())
            // Lève une exception
            throw new IllegalArgumentException("Equipments cannot be empty");
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityEquipmentPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, EntityEquipmentPacket value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.entityId);
            // Affecte une valeur
            int index = 0;
            // Boucle : répète un bloc
            for (var entry : value.equipments.entrySet()) {
                // Appelle une méthode
                final boolean last = index++ == value.equipments.size() - 1;
                // Appelle une méthode
                byte slotEnum = (byte) entry.getKey().legacyProtocolId();
                // Embranchement : vérifie une condition
                if (!last) slotEnum |= 0x80;
                // Appelle une méthode
                buffer.write(BYTE, slotEnum);
                // Appelle une méthode
                buffer.write(ItemStack.NETWORK_TYPE, entry.getValue());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public EntityEquipmentPacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new EntityEquipmentPacket(buffer.read(VAR_INT), readEquipments(buffer));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Appelle une méthode
        final var components = new ArrayList<Component>();
        // Boucle : répète un bloc
        for (var itemStack : this.equipments.values())
            // Appelle une méthode
            components.addAll(ItemStack.textComponents(itemStack));
        // Renvoie une valeur à l'appelant
        return List.copyOf(components);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Appelle une méthode
        final var newEquipment = new EnumMap<EquipmentSlot, ItemStack>(EquipmentSlot.class);
        // Boucle : répète un bloc
        for (var entry : this.equipments.entrySet())
            // Appelle une méthode
            newEquipment.put(entry.getKey(), ItemStack.copyWithOperator(entry.getValue(), operator));
        // Renvoie une valeur à l'appelant
        return new EntityEquipmentPacket(this.entityId, newEquipment);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Map<EquipmentSlot, ItemStack> readEquipments(NetworkBuffer reader) {
        // Appelle une méthode
        Map<EquipmentSlot, ItemStack> equipments = new EnumMap<>(EquipmentSlot.class);
        // Instruction de code
        byte slot;
        // Boucle : répète un bloc
        do {
            // Appelle une méthode
            slot = reader.read(BYTE);
            // Appelle une méthode
            equipments.put(EquipmentSlot.fromLegacyProtocolId(slot & 0x7F), reader.read(ItemStack.NETWORK_TYPE));
        // Appelle une méthode
        } while ((slot & 0x80) == 0x80);
        // Renvoie une valeur à l'appelant
        return equipments;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
