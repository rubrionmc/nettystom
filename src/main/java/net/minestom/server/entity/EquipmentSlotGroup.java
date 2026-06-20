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
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Predicate;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Déclaration de type (classe/interface/enum/record)
public enum EquipmentSlotGroup implements Predicate<EquipmentSlot> {
    // Instruction de code
    ANY("any", EquipmentSlot.values()),
    // Instruction de code
    MAIN_HAND("mainhand", EquipmentSlot.MAIN_HAND),
    // Instruction de code
    OFF_HAND("offhand", EquipmentSlot.OFF_HAND),
    // Instruction de code
    HAND("hand", EquipmentSlot.MAIN_HAND, EquipmentSlot.OFF_HAND),
    // Instruction de code
    FEET("feet", EquipmentSlot.BOOTS),
    // Instruction de code
    LEGS("legs", EquipmentSlot.LEGGINGS),
    // Instruction de code
    CHEST("chest", EquipmentSlot.CHESTPLATE),
    // Instruction de code
    HEAD("head", EquipmentSlot.HELMET),
    // Instruction de code
    ARMOR("armor", EquipmentSlot.CHESTPLATE, EquipmentSlot.LEGGINGS, EquipmentSlot.BOOTS, EquipmentSlot.HELMET),
    // Instruction de code
    BODY("body", EquipmentSlot.BODY),
    // Appelle une méthode
    SADDLE("saddle", EquipmentSlot.SADDLE);

    // Affecte une valeur
    private static final Map<String, EquipmentSlotGroup> BY_NBT_NAME = Arrays.stream(values())
            // Appelle une méthode
            .collect(Collectors.toMap(EquipmentSlotGroup::nbtName, Function.identity()));

    // Appelle une méthode
    public static final NetworkBuffer.Type<EquipmentSlotGroup> NETWORK_TYPE = NetworkBuffer.Enum(EquipmentSlotGroup.class);
    // Affecte une valeur
    public static final Codec<EquipmentSlotGroup> CODEC = Codec.STRING
            // Appelle une méthode
            .transform(BY_NBT_NAME::get, EquipmentSlotGroup::nbtName);

    // Instruction de code
    private final String nbtName;
    // Instruction de code
    private final List<EquipmentSlot> equipmentSlots;

    // Début d'une méthode/d'un bloc
    EquipmentSlotGroup(String nbtName, EquipmentSlot... equipmentSlots) {
        // Accès à l'objet courant/parent
        this.equipmentSlots = List.of(equipmentSlots);
        // Accès à l'objet courant/parent
        this.nbtName = nbtName;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the (potentially multiple) equipment slots associated with this attribute slot.
     */
    // Début d'une méthode/d'un bloc
    public List<EquipmentSlot> equipmentSlots() {
        // Renvoie une valeur à l'appelant
        return this.equipmentSlots;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String nbtName() {
        // Renvoie une valeur à l'appelant
        return this.nbtName;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns true if this attribute slot has an effect on the given {@link EquipmentSlot}, false otherwise.
     */
    // Début d'une méthode/d'un bloc
    public boolean contains(EquipmentSlot equipmentSlot) {
        // Renvoie une valeur à l'appelant
        return this.equipmentSlots.contains(equipmentSlot);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean test(EquipmentSlot equipmentSlot) {
        // Renvoie une valeur à l'appelant
        return this.contains(equipmentSlot);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
