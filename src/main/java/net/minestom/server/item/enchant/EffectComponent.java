// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponentMap;
// Import of a required class
import net.minestom.server.item.crossbow.CrossbowChargingSounds;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.Unit;
// Import of a required class
import net.minestom.server.utils.collection.ObjectArray;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
public class EffectComponent {
    // Calls a method
    static final Map<String, DataComponent<?>> NAMESPACES = new HashMap<>(32);
    // Calls a method
    static final ObjectArray<DataComponent<?>> IDS = ObjectArray.singleThread(32);

    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> DAMAGE_PROTECTION = register("damage_protection", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<DamageImmunityEffect>>> DAMAGE_IMMUNITY = register("damage_immunity", ConditionalEffect.codec(DamageImmunityEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> DAMAGE = register("damage", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> SMASH_DAMAGE_PER_FALLEN_BLOCK = register("smash_damage_per_fallen_block", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> KNOCKBACK = register("knockback", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> ARMOR_EFFECTIVENESS = register("armor_effectiveness", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<TargetedConditionalEffect<EntityEffect>>> POST_ATTACK = register("post_attack", TargetedConditionalEffect.codec(EntityEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<EntityEffect>>> POST_PIERCING_ATTACK = register("post_piercing_attack", ConditionalEffect.codec(EntityEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<EntityEffect>>> HIT_BLOCK = register("hit_block", ConditionalEffect.codec(EntityEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> ITEM_DAMAGE = register("item_damage", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<AttributeEffect>> ATTRIBUTES = register("attributes", AttributeEffect.CODEC.list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<TargetedConditionalEffect<ValueEffect>>> EQUIPMENT_DROPS = register("equipment_drops", TargetedConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<LocationEffect>>> LOCATION_CHANGED = register("location_changed", ConditionalEffect.codec(LocationEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<EntityEffect>>> TICK = register("tick", ConditionalEffect.codec(EntityEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> AMMO_USE = register("ammo_use", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> PROJECTILE_PIERCING = register("projectile_piercing", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<EntityEffect>>> PROJECTILE_SPAWNED = register("projectile_spawned", ConditionalEffect.codec(EntityEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> PROJECTILE_SPREAD = register("projectile_spread", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> PROJECTILE_COUNT = register("projectile_count", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> TRIDENT_RETURN_ACCELERATION = register("trident_return_acceleration", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> FISHING_TIME_REDUCTION = register("fishing_time_reduction", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> FISHING_LUCK_BONUS = register("fishing_luck_bonus", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> BLOCK_EXPERIENCE = register("block_experience", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> MOB_EXPERIENCE = register("mob_experience", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ConditionalEffect<ValueEffect>>> REPAIR_WITH_XP = register("repair_with_xp", ConditionalEffect.codec(ValueEffect.CODEC).list(), List::copyOf);
    // Calls a method
    public static final DataComponent<ValueEffect> CROSSBOW_CHARGE_TIME = register("crossbow_charge_time", ValueEffect.CODEC, null);
    // Calls a method
    public static final DataComponent<List<CrossbowChargingSounds>> CROSSBOW_CHARGING_SOUNDS = register("crossbow_charging_sounds", CrossbowChargingSounds.NBT_TYPE.list(), List::copyOf);
    // Calls a method
    public static final DataComponent<List<SoundEvent>> TRIDENT_SOUND = register("trident_sound", SoundEvent.CODEC.list(), null);
    // Calls a method
    public static final DataComponent<Unit> PREVENT_EQUIPMENT_DROP = register("prevent_equipment_drop", Codec.UNIT, null);
    // Calls a method
    public static final DataComponent<Unit> PREVENT_ARMOR_CHANGE = register("prevent_armor_change", Codec.UNIT, null);
    // Calls a method
    public static final DataComponent<ValueEffect> TRIDENT_SPIN_ATTACK_STRENGTH = register("trident_spin_attack_strength", ValueEffect.CODEC, null);

    // Calls a method
    public static final Codec<DataComponentMap> CODEC = DataComponentMap.codec(EffectComponent::fromId, EffectComponent::fromNamespaceId);

    // Start of a method/block
    public static @Nullable DataComponent<?> fromNamespaceId(String namespaceId) {
        // Returns a value to the caller
        return NAMESPACES.get(namespaceId);
    // End of a block/expression
    }

    // Start of a method/block
    public static @Nullable DataComponent<?> fromKey(Key namespaceId) {
        // Returns a value to the caller
        return fromNamespaceId(namespaceId.asString());
    // End of a block/expression
    }

    // Start of a method/block
    public static @Nullable DataComponent<?> fromId(int id) {
        // Returns a value to the caller
        return IDS.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    public static Collection<DataComponent<?>> values() {
        // Returns a value to the caller
        return NAMESPACES.values();
    // End of a block/expression
    }

    // Start of a method/block
    static <T> DataComponent<T> register(String name, @Nullable Codec<T> nbt, @Nullable UnaryOperator<T> freeze) {
        // Calls a method
        DataComponent<T> impl = DataComponent.createHeadless(NAMESPACES.size(), Key.key(name), null, nbt, freeze);
        // Calls a method
        NAMESPACES.put(impl.name(), impl);
        // Calls a method
        IDS.set(impl.id(), impl);
        // Returns a value to the caller
        return impl;
    // End of a block/expression
    }
// End of a block/expression
}
