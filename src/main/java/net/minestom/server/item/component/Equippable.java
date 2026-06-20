// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record Equippable(
        // Instruction de code
        EquipmentSlot slot,
        // Instruction de code
        SoundEvent equipSound,
        // Annotation pour l'élément suivant
        @Nullable String assetId,
        // Annotation pour l'élément suivant
        @Nullable String cameraOverlay,
        // Annotation pour l'élément suivant
        @Nullable RegistryTag<EntityType> allowedEntities,
        // Instruction de code
        boolean dispensable,
        // Instruction de code
        boolean swappable,
        // Instruction de code
        boolean damageOnHurt,
        // Instruction de code
        boolean equipOnInteract,
        // Instruction de code
        boolean canBeSheared,
        // Instruction de code
        SoundEvent shearingSound
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final SoundEvent DEFAULT_EQUIP_SOUND = SoundEvent.ITEM_ARMOR_EQUIP_GENERIC;
    // Affecte une valeur
    public static final SoundEvent DEFAULT_SHEARING_SOUND = SoundEvent.ITEM_SHEARS_SNIP;

    // Affecte une valeur
    public static final NetworkBuffer.Type<Equippable> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            EquipmentSlot.NETWORK_TYPE, Equippable::slot,
            // Instruction de code
            SoundEvent.NETWORK_TYPE, Equippable::equipSound,
            // Instruction de code
            NetworkBuffer.STRING.optional(), Equippable::assetId,
            // Instruction de code
            NetworkBuffer.STRING.optional(), Equippable::cameraOverlay,
            // Instruction de code
            RegistryTag.networkType(Registries::entityType).optional(), Equippable::allowedEntities,
            // Instruction de code
            NetworkBuffer.BOOLEAN, Equippable::dispensable,
            // Instruction de code
            NetworkBuffer.BOOLEAN, Equippable::swappable,
            // Instruction de code
            NetworkBuffer.BOOLEAN, Equippable::damageOnHurt,
            // Instruction de code
            NetworkBuffer.BOOLEAN, Equippable::equipOnInteract,
            // Instruction de code
            NetworkBuffer.BOOLEAN, Equippable::canBeSheared,
            // Instruction de code
            SoundEvent.NETWORK_TYPE, Equippable::shearingSound,
            // Instruction de code
            Equippable::new);
    // Affecte une valeur
    public static final Codec<Equippable> CODEC = StructCodec.struct(
            // Instruction de code
            "slot", EquipmentSlot.CODEC, Equippable::slot,
            // Instruction de code
            "equip_sound", SoundEvent.CODEC.optional(DEFAULT_EQUIP_SOUND), Equippable::equipSound,
            // Instruction de code
            "asset_id", Codec.STRING.optional(), Equippable::assetId,
            // Instruction de code
            "camera_overlay", Codec.STRING.optional(), Equippable::cameraOverlay,
            // Instruction de code
            "allowed_entities", RegistryTag.codec(Registries::entityType).optional(), Equippable::allowedEntities,
            // Instruction de code
            "dispensable", Codec.BOOLEAN.optional(true), Equippable::dispensable,
            // Instruction de code
            "swappable", Codec.BOOLEAN.optional(true), Equippable::swappable,
            // Instruction de code
            "damage_on_hurt", Codec.BOOLEAN.optional(true), Equippable::damageOnHurt,
            // Instruction de code
            "equip_on_interact", Codec.BOOLEAN.optional(false), Equippable::equipOnInteract,
            // Instruction de code
            "can_be_sheared", Codec.BOOLEAN.optional(false), Equippable::canBeSheared,
            // Instruction de code
            "shearing_sound", SoundEvent.CODEC.optional(DEFAULT_SHEARING_SOUND), Equippable::shearingSound,
            // Instruction de code
            Equippable::new);

    // Début d'une méthode/d'un bloc
    public Equippable withSlot(EquipmentSlot slot) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withEquipSound(SoundEvent equipSound) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withAssetId(@Nullable String assetId) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withCameraOverlay(@Nullable String cameraOverlay) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withAllowedEntities(@Nullable RegistryTag<EntityType> allowedEntities) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withDispensable(boolean dispensable) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withSwappable(boolean swappable) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withDamageOnHurt(boolean damageOnHurt) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withEquipOnInteract(boolean equipOnInteract) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withCanBeSheared(boolean canBeSheared) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Equippable withShearingSound(SoundEvent shearingSound) {
        // Renvoie une valeur à l'appelant
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
