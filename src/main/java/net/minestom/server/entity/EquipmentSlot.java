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
import java.util.stream.Collectors;

// Static import of a member
import static net.minestom.server.utils.inventory.PlayerInventoryUtils.*;

// Type declaration (class/interface/enum/record)
public enum EquipmentSlot {
    // Code statement
    MAIN_HAND(0, 0, "mainhand", false, -1),
    // Code statement
    OFF_HAND(5, 1, "offhand", false, -1),
    // Code statement
    BOOTS(1, 2, "feet", true, BOOTS_SLOT),
    // Code statement
    LEGGINGS(2, 3, "legs", true, LEGGINGS_SLOT),
    // Code statement
    CHESTPLATE(3, 4, "chest", true, CHESTPLATE_SLOT),
    // Code statement
    HELMET(4, 5, "head", true, HELMET_SLOT),
    // Code statement
    BODY(6, 6, "body", false, -1),
    // Calls a method
    SADDLE(7, 7, "saddle", false, -1);

    // Calls a method
    private static final List<EquipmentSlot> ARMORS = List.of(BOOTS, LEGGINGS, CHESTPLATE, HELMET);
    // Assigns a value
    private static final Map<String, EquipmentSlot> BY_NBT_NAME = Arrays.stream(values())
            // Calls a method
            .collect(Collectors.toMap(EquipmentSlot::nbtName, slot -> slot));
    // Assigns a value
    private static final Map<Integer, EquipmentSlot> BY_PROTOCOL_ID = Arrays.stream(values())
            // Calls a method
            .collect(Collectors.toMap(EquipmentSlot::protocolId, slot -> slot));
    // Assigns a value
    private static final Map<Integer, EquipmentSlot> BY_LEGACY_PROTOCOL_ID = Arrays.stream(values())
            // Calls a method
            .collect(Collectors.toMap(EquipmentSlot::legacyProtocolId, slot -> slot));

    // Assigns a value
    public static final NetworkBuffer.Type<EquipmentSlot> NETWORK_TYPE = NetworkBuffer.VAR_INT
            // Calls a method
            .transform(BY_PROTOCOL_ID::get, EquipmentSlot::protocolId);
    // Assigns a value
    public static final Codec<EquipmentSlot> CODEC = Codec.STRING
            // Calls a method
            .transform(BY_NBT_NAME::get, EquipmentSlot::nbtName);

    // Start of a method/block
    public static List<EquipmentSlot> armors() {
        // Returns a value to the caller
        return ARMORS;
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public static EquipmentSlot fromLegacyProtocolId(int legacyProtocolId) {
        // Calls a method
        final EquipmentSlot slot = BY_LEGACY_PROTOCOL_ID.get(legacyProtocolId);
        // Branch: checks a condition
        if (slot != null) return slot;

        // Throws an exception
        throw new IllegalStateException("Unexpected value: " + legacyProtocolId);
    // End of a block/expression
    }

    // Code statement
    private final int protocolId;
    // Code statement
    private final int legacyProtocolId;
    // Code statement
    private final String nbtName;
    // Code statement
    private final boolean armor;
    // Code statement
    private final int armorSlot;

    // Start of a method/block
    EquipmentSlot(int protocolId, int legacyProtocolId, String nbtName, boolean armor, int armorSlot) {
        // Access to the current/parent object
        this.protocolId = protocolId;
        // Access to the current/parent object
        this.legacyProtocolId = legacyProtocolId;
        // Access to the current/parent object
        this.nbtName = nbtName;
        // Access to the current/parent object
        this.armor = armor;
        // Access to the current/parent object
        this.armorSlot = armorSlot;
    // End of a block/expression
    }

    // Start of a method/block
    public int protocolId() {
        // Returns a value to the caller
        return protocolId;
    // End of a block/expression
    }

    /**
     * Legacy protocol ID exists because that format is used in EntityEquipmentPacket
     * It is being referred to as the legacy ID here because newer components are using
     * the equipment slot stream codec (the more modern mechanism for network serialization)
     * The legacy ID is expected to be removed eventually.
     *
     * @return the equipment slot
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public int legacyProtocolId() {
        // Returns a value to the caller
        return legacyProtocolId;
    // End of a block/expression
    }

    // Start of a method/block
    public String nbtName() {
        // Returns a value to the caller
        return nbtName;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHand() {
        // Returns a value to the caller
        return this == MAIN_HAND || this == OFF_HAND;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isArmor() {
        // Returns a value to the caller
        return armor;
    // End of a block/expression
    }

    // Start of a method/block
    public int armorSlot() {
        // Returns a value to the caller
        return armorSlot;
    // End of a block/expression
    }
// End of a block/expression
}
