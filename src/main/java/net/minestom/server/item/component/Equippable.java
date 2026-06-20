// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record Equippable(
        // Code statement
        EquipmentSlot slot,
        // Code statement
        SoundEvent equipSound,
        // Annotation for the following element
        @Nullable String assetId,
        // Annotation for the following element
        @Nullable String cameraOverlay,
        // Annotation for the following element
        @Nullable RegistryTag<EntityType> allowedEntities,
        // Code statement
        boolean dispensable,
        // Code statement
        boolean swappable,
        // Code statement
        boolean damageOnHurt,
        // Code statement
        boolean equipOnInteract,
        // Code statement
        boolean canBeSheared,
        // Code statement
        SoundEvent shearingSound
// Start of a method/block
) {
    // Assigns a value
    public static final SoundEvent DEFAULT_EQUIP_SOUND = SoundEvent.ITEM_ARMOR_EQUIP_GENERIC;
    // Assigns a value
    public static final SoundEvent DEFAULT_SHEARING_SOUND = SoundEvent.ITEM_SHEARS_SNIP;

    // Assigns a value
    public static final NetworkBuffer.Type<Equippable> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            EquipmentSlot.NETWORK_TYPE, Equippable::slot,
            // Code statement
            SoundEvent.NETWORK_TYPE, Equippable::equipSound,
            // Code statement
            NetworkBuffer.STRING.optional(), Equippable::assetId,
            // Code statement
            NetworkBuffer.STRING.optional(), Equippable::cameraOverlay,
            // Code statement
            RegistryTag.networkType(Registries::entityType).optional(), Equippable::allowedEntities,
            // Code statement
            NetworkBuffer.BOOLEAN, Equippable::dispensable,
            // Code statement
            NetworkBuffer.BOOLEAN, Equippable::swappable,
            // Code statement
            NetworkBuffer.BOOLEAN, Equippable::damageOnHurt,
            // Code statement
            NetworkBuffer.BOOLEAN, Equippable::equipOnInteract,
            // Code statement
            NetworkBuffer.BOOLEAN, Equippable::canBeSheared,
            // Code statement
            SoundEvent.NETWORK_TYPE, Equippable::shearingSound,
            // Code statement
            Equippable::new);
    // Assigns a value
    public static final Codec<Equippable> CODEC = StructCodec.struct(
            // Code statement
            "slot", EquipmentSlot.CODEC, Equippable::slot,
            // Code statement
            "equip_sound", SoundEvent.CODEC.optional(DEFAULT_EQUIP_SOUND), Equippable::equipSound,
            // Code statement
            "asset_id", Codec.STRING.optional(), Equippable::assetId,
            // Code statement
            "camera_overlay", Codec.STRING.optional(), Equippable::cameraOverlay,
            // Code statement
            "allowed_entities", RegistryTag.codec(Registries::entityType).optional(), Equippable::allowedEntities,
            // Code statement
            "dispensable", Codec.BOOLEAN.optional(true), Equippable::dispensable,
            // Code statement
            "swappable", Codec.BOOLEAN.optional(true), Equippable::swappable,
            // Code statement
            "damage_on_hurt", Codec.BOOLEAN.optional(true), Equippable::damageOnHurt,
            // Code statement
            "equip_on_interact", Codec.BOOLEAN.optional(false), Equippable::equipOnInteract,
            // Code statement
            "can_be_sheared", Codec.BOOLEAN.optional(false), Equippable::canBeSheared,
            // Code statement
            "shearing_sound", SoundEvent.CODEC.optional(DEFAULT_SHEARING_SOUND), Equippable::shearingSound,
            // Code statement
            Equippable::new);

    // Start of a method/block
    public Equippable withSlot(EquipmentSlot slot) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withEquipSound(SoundEvent equipSound) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withAssetId(@Nullable String assetId) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withCameraOverlay(@Nullable String cameraOverlay) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withAllowedEntities(@Nullable RegistryTag<EntityType> allowedEntities) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withDispensable(boolean dispensable) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withSwappable(boolean swappable) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withDamageOnHurt(boolean damageOnHurt) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withEquipOnInteract(boolean equipOnInteract) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withCanBeSheared(boolean canBeSheared) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }

    // Start of a method/block
    public Equippable withShearingSound(SoundEvent shearingSound) {
        // Returns a value to the caller
        return new Equippable(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable, damageOnHurt, equipOnInteract, canBeSheared, shearingSound);
    // End of a block/expression
    }
// End of a block/expression
}
