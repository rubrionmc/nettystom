// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Predicate;
// Import of a required class
import java.util.stream.Collectors;

// Type declaration (class/interface/enum/record)
public enum EquipmentSlotGroup implements Predicate<EquipmentSlot> {
    // Code statement
    ANY("any", EquipmentSlot.values()),
    // Code statement
    MAIN_HAND("mainhand", EquipmentSlot.MAIN_HAND),
    // Code statement
    OFF_HAND("offhand", EquipmentSlot.OFF_HAND),
    // Code statement
    HAND("hand", EquipmentSlot.MAIN_HAND, EquipmentSlot.OFF_HAND),
    // Code statement
    FEET("feet", EquipmentSlot.BOOTS),
    // Code statement
    LEGS("legs", EquipmentSlot.LEGGINGS),
    // Code statement
    CHEST("chest", EquipmentSlot.CHESTPLATE),
    // Code statement
    HEAD("head", EquipmentSlot.HELMET),
    // Code statement
    ARMOR("armor", EquipmentSlot.CHESTPLATE, EquipmentSlot.LEGGINGS, EquipmentSlot.BOOTS, EquipmentSlot.HELMET),
    // Code statement
    BODY("body", EquipmentSlot.BODY),
    // Calls a method
    SADDLE("saddle", EquipmentSlot.SADDLE);

    // Assigns a value
    private static final Map<String, EquipmentSlotGroup> BY_NBT_NAME = Arrays.stream(values())
            // Calls a method
            .collect(Collectors.toMap(EquipmentSlotGroup::nbtName, Function.identity()));

    // Calls a method
    public static final NetworkBuffer.Type<EquipmentSlotGroup> NETWORK_TYPE = NetworkBuffer.Enum(EquipmentSlotGroup.class);
    // Assigns a value
    public static final Codec<EquipmentSlotGroup> CODEC = Codec.STRING
            // Calls a method
            .transform(BY_NBT_NAME::get, EquipmentSlotGroup::nbtName);

    // Code statement
    private final String nbtName;
    // Code statement
    private final List<EquipmentSlot> equipmentSlots;

    // Start of a method/block
    EquipmentSlotGroup(String nbtName, EquipmentSlot... equipmentSlots) {
        // Access to the current/parent object
        this.equipmentSlots = List.of(equipmentSlots);
        // Access to the current/parent object
        this.nbtName = nbtName;
    // End of a block/expression
    }

    /**
     * Returns the (potentially multiple) equipment slots associated with this attribute slot.
     */
    // Start of a method/block
    public List<EquipmentSlot> equipmentSlots() {
        // Returns a value to the caller
        return this.equipmentSlots;
    // End of a block/expression
    }

    // Start of a method/block
    public String nbtName() {
        // Returns a value to the caller
        return this.nbtName;
    // End of a block/expression
    }

    /**
     * Returns true if this attribute slot has an effect on the given {@link EquipmentSlot}, false otherwise.
     */
    // Start of a method/block
    public boolean contains(EquipmentSlot equipmentSlot) {
        // Returns a value to the caller
        return this.equipmentSlots.contains(equipmentSlot);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean test(EquipmentSlot equipmentSlot) {
        // Returns a value to the caller
        return this.contains(equipmentSlot);
    // End of a block/expression
    }
// End of a block/expression
}
