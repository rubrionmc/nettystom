// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Import statique d'un membre
import static net.minestom.server.utils.inventory.PlayerInventoryUtils.*;

// Déclaration de type (classe/interface/enum/record)
public enum EquipmentSlot {
    // Instruction de code
    MAIN_HAND(0, 0, "mainhand", false, -1),
    // Instruction de code
    OFF_HAND(5, 1, "offhand", false, -1),
    // Instruction de code
    BOOTS(1, 2, "feet", true, BOOTS_SLOT),
    // Instruction de code
    LEGGINGS(2, 3, "legs", true, LEGGINGS_SLOT),
    // Instruction de code
    CHESTPLATE(3, 4, "chest", true, CHESTPLATE_SLOT),
    // Instruction de code
    HELMET(4, 5, "head", true, HELMET_SLOT),
    // Instruction de code
    BODY(6, 6, "body", false, -1),
    // Appelle une méthode
    SADDLE(7, 7, "saddle", false, -1);

    // Appelle une méthode
    private static final List<EquipmentSlot> ARMORS = List.of(BOOTS, LEGGINGS, CHESTPLATE, HELMET);
    // Affecte une valeur
    private static final Map<String, EquipmentSlot> BY_NBT_NAME = Arrays.stream(values())
            // Appelle une méthode
            .collect(Collectors.toMap(EquipmentSlot::nbtName, slot -> slot));
    // Affecte une valeur
    private static final Map<Integer, EquipmentSlot> BY_PROTOCOL_ID = Arrays.stream(values())
            // Appelle une méthode
            .collect(Collectors.toMap(EquipmentSlot::protocolId, slot -> slot));
    // Affecte une valeur
    private static final Map<Integer, EquipmentSlot> BY_LEGACY_PROTOCOL_ID = Arrays.stream(values())
            // Appelle une méthode
            .collect(Collectors.toMap(EquipmentSlot::legacyProtocolId, slot -> slot));

    // Affecte une valeur
    public static final NetworkBuffer.Type<EquipmentSlot> NETWORK_TYPE = NetworkBuffer.VAR_INT
            // Appelle une méthode
            .transform(BY_PROTOCOL_ID::get, EquipmentSlot::protocolId);
    // Affecte une valeur
    public static final Codec<EquipmentSlot> CODEC = Codec.STRING
            // Appelle une méthode
            .transform(BY_NBT_NAME::get, EquipmentSlot::nbtName);

    // Début d'une méthode/d'un bloc
    public static List<EquipmentSlot> armors() {
        // Renvoie une valeur à l'appelant
        return ARMORS;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public static EquipmentSlot fromLegacyProtocolId(int legacyProtocolId) {
        // Appelle une méthode
        final EquipmentSlot slot = BY_LEGACY_PROTOCOL_ID.get(legacyProtocolId);
        // Embranchement : vérifie une condition
        if (slot != null) return slot;

        // Lève une exception
        throw new IllegalStateException("Unexpected value: " + legacyProtocolId);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private final int protocolId;
    // Instruction de code
    private final int legacyProtocolId;
    // Instruction de code
    private final String nbtName;
    // Instruction de code
    private final boolean armor;
    // Instruction de code
    private final int armorSlot;

    // Début d'une méthode/d'un bloc
    EquipmentSlot(int protocolId, int legacyProtocolId, String nbtName, boolean armor, int armorSlot) {
        // Accès à l'objet courant/parent
        this.protocolId = protocolId;
        // Accès à l'objet courant/parent
        this.legacyProtocolId = legacyProtocolId;
        // Accès à l'objet courant/parent
        this.nbtName = nbtName;
        // Accès à l'objet courant/parent
        this.armor = armor;
        // Accès à l'objet courant/parent
        this.armorSlot = armorSlot;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int protocolId() {
        // Renvoie une valeur à l'appelant
        return protocolId;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Legacy protocol ID exists because that format is used in EntityEquipmentPacket
     * It is being referred to as the legacy ID here because newer components are using
     * the equipment slot stream codec (the more modern mechanism for network serialization)
     * The legacy ID is expected to be removed eventually.
     *
     * @return the equipment slot
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public int legacyProtocolId() {
        // Renvoie une valeur à l'appelant
        return legacyProtocolId;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String nbtName() {
        // Renvoie une valeur à l'appelant
        return nbtName;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHand() {
        // Renvoie une valeur à l'appelant
        return this == MAIN_HAND || this == OFF_HAND;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isArmor() {
        // Renvoie une valeur à l'appelant
        return armor;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int armorSlot() {
        // Renvoie une valeur à l'appelant
        return armorSlot;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
