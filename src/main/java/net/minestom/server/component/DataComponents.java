// Package declaration for this file
package net.minestom.server.component;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.color.DyeColor;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.VillagerType;
// Import of a required class
import net.minestom.server.entity.damage.DamageType;
// Import of a required class
import net.minestom.server.entity.metadata.animal.*;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.*;
// Import of a required class
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import of a required class
import net.minestom.server.entity.metadata.water.AxolotlMeta;
// Import of a required class
import net.minestom.server.entity.metadata.water.fish.SalmonMeta;
// Import of a required class
import net.minestom.server.entity.metadata.water.fish.TropicalFishMeta;
// Import of a required class
import net.minestom.server.instance.block.BlockEntityType;
// Import of a required class
import net.minestom.server.instance.block.banner.BannerPattern;
// Import of a required class
import net.minestom.server.instance.block.jukebox.JukeboxSong;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.ItemStackTemplate;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.armor.TrimMaterial;
// Import of a required class
import net.minestom.server.item.component.*;
// Import of a required class
import net.minestom.server.item.instrument.Instrument;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.player.ResolvableProfile;
// Import of a required class
import net.minestom.server.registry.*;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.Unit;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.component.DataComponentImpl.register;

// Type declaration (class/interface/enum/record)
public class DataComponents {

    // Calls a method
    public static final DataComponent<CustomData> CUSTOM_DATA = register("custom_data", CustomData.NETWORK_TYPE, CustomData.CODEC);
    // Calls a method
    public static final DataComponent<Integer> MAX_STACK_SIZE = register("max_stack_size", NetworkBuffer.VAR_INT, Codec.INT);
    // Calls a method
    public static final DataComponent<Integer> MAX_DAMAGE = register("max_damage", NetworkBuffer.VAR_INT, Codec.INT);
    // Calls a method
    public static final DataComponent<Integer> DAMAGE = register("damage", NetworkBuffer.VAR_INT, Codec.INT);
    // Calls a method
    public static final DataComponent<Unit> UNBREAKABLE = register("unbreakable", NetworkBuffer.UNIT, Codec.UNIT);
    // Calls a method
    public static final DataComponent<UseEffects> USE_EFFECTS = register("use_effects", UseEffects.NETWORK_TYPE, UseEffects.CODEC);
    // Calls a method
    public static final DataComponent<Component> CUSTOM_NAME = register("custom_name", NetworkBuffer.COMPONENT, Codec.COMPONENT);
    // Calls a method
    public static final DataComponent<Float> MINIMUM_ATTACK_CHARGE = register("minimum_attack_charge", NetworkBuffer.FLOAT, Codec.FLOAT);
    // Calls a method
    public static final DataComponent<RegistryKey<DamageType>> DAMAGE_TYPE = register("damage_type", DamageType.NETWORK_TYPE, DamageType.CODEC);
    // Calls a method
    public static final DataComponent<Component> ITEM_NAME = register("item_name", NetworkBuffer.COMPONENT, Codec.COMPONENT);
    // Calls a method
    public static final DataComponent<String> ITEM_MODEL = register("item_model", NetworkBuffer.STRING, Codec.STRING);
    // Calls a method
    public static final DataComponent<List<Component>> LORE = register("lore", NetworkBuffer.COMPONENT.list(256), Codec.COMPONENT.list(256), List::copyOf);
    // Calls a method
    public static final DataComponent<ItemRarity> RARITY = register("rarity", ItemRarity.NETWORK_TYPE, ItemRarity.CODEC);
    // Calls a method
    public static final DataComponent<EnchantmentList> ENCHANTMENTS = register("enchantments", EnchantmentList.NETWORK_TYPE, EnchantmentList.CODEC);
    // Calls a method
    public static final DataComponent<BlockPredicates> CAN_PLACE_ON = register("can_place_on", BlockPredicates.NETWORK_TYPE, BlockPredicates.CODEC);
    // Calls a method
    public static final DataComponent<BlockPredicates> CAN_BREAK = register("can_break", BlockPredicates.NETWORK_TYPE, BlockPredicates.CODEC);
    // Calls a method
    public static final DataComponent<AttributeList> ATTRIBUTE_MODIFIERS = register("attribute_modifiers", AttributeList.NETWORK_TYPE, AttributeList.CODEC);
    // Calls a method
    public static final DataComponent<CustomModelData> CUSTOM_MODEL_DATA = register("custom_model_data", CustomModelData.NETWORK_TYPE, CustomModelData.CODEC);
    // Calls a method
    public static final DataComponent<TooltipDisplay> TOOLTIP_DISPLAY = register("tooltip_display", TooltipDisplay.NETWORK_TYPE, TooltipDisplay.CODEC);
    // Calls a method
    public static final DataComponent<Integer> REPAIR_COST = register("repair_cost", NetworkBuffer.VAR_INT, Codec.INT);
    // Calls a method
    public static final DataComponent<Unit> CREATIVE_SLOT_LOCK = register("creative_slot_lock", NetworkBuffer.UNIT, null);
    // Calls a method
    public static final DataComponent<Boolean> ENCHANTMENT_GLINT_OVERRIDE = register("enchantment_glint_override", NetworkBuffer.BOOLEAN, Codec.BOOLEAN);
    // Calls a method
    public static final DataComponent<Unit> INTANGIBLE_PROJECTILE = register("intangible_projectile", null, Codec.UNIT);
    // Calls a method
    public static final DataComponent<Food> FOOD = register("food", Food.NETWORK_TYPE, Food.CODEC);
    // Calls a method
    public static final DataComponent<Consumable> CONSUMABLE = register("consumable", Consumable.NETWORK_TYPE, Consumable.CODEC);
    // Calls a method
    public static final DataComponent<ItemStack> USE_REMAINDER = register("use_remainder", ItemStackTemplate.NETWORK_TYPE, ItemStackTemplate.CODEC);
    // Calls a method
    public static final DataComponent<UseCooldown> USE_COOLDOWN = register("use_cooldown", UseCooldown.NETWORK_TYPE, UseCooldown.CODEC);
    // Calls a method
    public static final DataComponent<DamageResistant> DAMAGE_RESISTANT = register("damage_resistant", DamageResistant.NETWORK_TYPE, DamageResistant.CODEC);
    // Calls a method
    public static final DataComponent<Tool> TOOL = register("tool", Tool.NETWORK_TYPE, Tool.CODEC);
    // Calls a method
    public static final DataComponent<Weapon> WEAPON = register("weapon", Weapon.NETWORK_TYPE, Weapon.CODEC);
    // Calls a method
    public static final DataComponent<AttackRange> ATTACK_RANGE = register("attack_range", AttackRange.NETWORK_TYPE, AttackRange.CODEC);
    // Calls a method
    public static final DataComponent<Integer> ENCHANTABLE = register("enchantable", NetworkBuffer.VAR_INT, wrapObject("value", Codec.INT));
    // Calls a method
    public static final DataComponent<Equippable> EQUIPPABLE = register("equippable", Equippable.NETWORK_TYPE, Equippable.CODEC);
    // Calls a method
    public static final DataComponent<RegistryTag<Material>> REPAIRABLE = register("repairable", RegistryTag.networkType(Registries::material), wrapObject("items", RegistryTag.codec(Registries::material)));
    // Calls a method
    public static final DataComponent<Unit> GLIDER = register("glider", NetworkBuffer.UNIT, Codec.UNIT);
    // Calls a method
    public static final DataComponent<String> TOOLTIP_STYLE = register("tooltip_style", NetworkBuffer.STRING, Codec.STRING);
    // Calls a method
    public static final DataComponent<DeathProtection> DEATH_PROTECTION = register("death_protection", DeathProtection.NETWORK_TYPE, DeathProtection.CODEC);
    // Calls a method
    public static final DataComponent<BlocksAttacks> BLOCKS_ATTACKS = register("blocks_attacks", BlocksAttacks.NETWORK_TYPE, BlocksAttacks.NBT_TYPE);
    // Calls a method
    public static final DataComponent<PiercingWeapon> PIERCING_WEAPON = register("piercing_weapon", PiercingWeapon.NETWORK_TYPE, PiercingWeapon.CODEC);
    // Calls a method
    public static final DataComponent<KineticWeapon> KINETIC_WEAPON = register("kinetic_weapon", KineticWeapon.NETWORK_TYPE, KineticWeapon.CODEC);
    // Calls a method
    public static final DataComponent<SwingAnimation> SWING_ANIMATION = register("swing_animation", SwingAnimation.NETWORK_TYPE, SwingAnimation.CODEC);
    // Calls a method
    public static final DataComponent<Integer> ADDITIONAL_TRADE_COST = register("additional_trade_cost", NetworkBuffer.VAR_INT, null);
    // Calls a method
    public static final DataComponent<EnchantmentList> STORED_ENCHANTMENTS = register("stored_enchantments", EnchantmentList.NETWORK_TYPE, EnchantmentList.CODEC);
    // Calls a method
    public static final DataComponent<DyeColor> DYE = register("dye", DyeColor.NETWORK_TYPE, DyeColor.CODEC);
    // Calls a method
    public static final DataComponent<RGBLike> DYED_COLOR = register("dyed_color", Color.NETWORK_TYPE, Color.CODEC);
    // Calls a method
    public static final DataComponent<RGBLike> MAP_COLOR = register("map_color", Color.NETWORK_TYPE, Color.CODEC);
    // Calls a method
    public static final DataComponent<Integer> MAP_ID = register("map_id", NetworkBuffer.VAR_INT, Codec.INT);
    // Calls a method
    public static final DataComponent<MapDecorations> MAP_DECORATIONS = register("map_decorations", null, MapDecorations.CODEC);
    // Calls a method
    public static final DataComponent<MapPostProcessing> MAP_POST_PROCESSING = register("map_post_processing", MapPostProcessing.NETWORK_TYPE, null);
    // Calls a method
    public static final DataComponent<List<ItemStack>> CHARGED_PROJECTILES = register("charged_projectiles", ItemStackTemplate.NETWORK_TYPE.list(Short.MAX_VALUE), ItemStackTemplate.CODEC.list(Short.MAX_VALUE), List::copyOf);
    // Calls a method
    public static final DataComponent<List<ItemStack>> BUNDLE_CONTENTS = register("bundle_contents", ItemStackTemplate.NETWORK_TYPE.list(Short.MAX_VALUE), ItemStackTemplate.CODEC.list(Short.MAX_VALUE), List::copyOf);
    // Calls a method
    public static final DataComponent<PotionContents> POTION_CONTENTS = register("potion_contents", PotionContents.NETWORK_TYPE, PotionContents.CODEC);
    // Calls a method
    public static final DataComponent<Float> POTION_DURATION_SCALE = register("potion_duration_scale", NetworkBuffer.FLOAT, Codec.FLOAT);
    // Calls a method
    public static final DataComponent<SuspiciousStewEffects> SUSPICIOUS_STEW_EFFECTS = register("suspicious_stew_effects", SuspiciousStewEffects.NETWORK_TYPE, SuspiciousStewEffects.CODEC);
    // Calls a method
    public static final DataComponent<WritableBookContent> WRITABLE_BOOK_CONTENT = register("writable_book_content", WritableBookContent.NETWORK_TYPE, WritableBookContent.CODEC);
    // Calls a method
    public static final DataComponent<WrittenBookContent> WRITTEN_BOOK_CONTENT = register("written_book_content", WrittenBookContent.NETWORK_TYPE, WrittenBookContent.CODEC);
    // Calls a method
    public static final DataComponent<ArmorTrim> TRIM = register("trim", ArmorTrim.NETWORK_TYPE, ArmorTrim.CODEC);
    // Calls a method
    public static final DataComponent<DebugStickState> DEBUG_STICK_STATE = register("debug_stick_state", DebugStickState.NETWORK_TYPE, DebugStickState.CODEC);
    // Calls a method
    public static final DataComponent<TypedCustomData<EntityType>> ENTITY_DATA = register("entity_data", TypedCustomData.networkType(EntityType.NETWORK_TYPE), TypedCustomData.codec(EntityType.CODEC));
    // Calls a method
    public static final DataComponent<CustomData> BUCKET_ENTITY_DATA = register("bucket_entity_data", CustomData.NETWORK_TYPE, CustomData.CODEC);
    // Calls a method
    public static final DataComponent<TypedCustomData<BlockEntityType>> BLOCK_ENTITY_DATA = register("block_entity_data", TypedCustomData.networkType(BlockEntityType.NETWORK_TYPE), TypedCustomData.codec(BlockEntityType.CODEC));
    // Calls a method
    public static final DataComponent<Holder<Instrument>> INSTRUMENT = register("instrument", Holder.networkType(Registries::instrument, Instrument.REGISTRY_NETWORK_TYPE), Holder.codec(Registries::instrument, Instrument.REGISTRY_CODEC));
    // Calls a method
    public static final DataComponent<Holder<TrimMaterial>> PROVIDES_TRIM_MATERIAL = register("provides_trim_material", Holder.networkType(Registries::trimMaterial, TrimMaterial.REGISTRY_NETWORK_TYPE), Holder.codec(Registries::trimMaterial, TrimMaterial.REGISTRY_CODEC));
    // Calls a method
    public static final DataComponent<Integer> OMINOUS_BOTTLE_AMPLIFIER = register("ominous_bottle_amplifier", NetworkBuffer.VAR_INT, Codec.INT);
    // Calls a method
    public static final DataComponent<RegistryKey<JukeboxSong>> JUKEBOX_PLAYABLE = register("jukebox_playable", JukeboxSong.JUKEBOX_PLAYABLE_NETWORK_TYPE, JukeboxSong.CODEC);
    // Calls a method
    public static final DataComponent<TagKey<BannerPattern>> PROVIDES_BANNER_PATTERNS = register("provides_banner_patterns", TagKey.networkType(Registries::bannerPattern), TagKey.hashCodec(Registries::bannerPattern));
    // Calls a method
    public static final DataComponent<List<String>> RECIPES = register("recipes", NetworkBuffer.STRING.list(Short.MAX_VALUE), Codec.STRING.list(Short.MAX_VALUE), List::copyOf);
    // Calls a method
    public static final DataComponent<LodestoneTracker> LODESTONE_TRACKER = register("lodestone_tracker", LodestoneTracker.NETWORK_TYPE, LodestoneTracker.CODEC);
    // Calls a method
    public static final DataComponent<FireworkExplosion> FIREWORK_EXPLOSION = register("firework_explosion", FireworkExplosion.NETWORK_TYPE, FireworkExplosion.CODEC);
    // Calls a method
    public static final DataComponent<FireworkList> FIREWORKS = register("fireworks", FireworkList.NETWORK_TYPE, FireworkList.NBT_TYPE);
    // Calls a method
    public static final DataComponent<ResolvableProfile> PROFILE = register("profile", ResolvableProfile.NETWORK_TYPE, ResolvableProfile.CODEC);
    // Calls a method
    public static final DataComponent<String> NOTE_BLOCK_SOUND = register("note_block_sound", NetworkBuffer.STRING, Codec.STRING);
    // Calls a method
    public static final DataComponent<BannerPatterns> BANNER_PATTERNS = register("banner_patterns", BannerPatterns.NETWORK_TYPE, BannerPatterns.CODEC);
    // Calls a method
    public static final DataComponent<DyeColor> BASE_COLOR = register("base_color", DyeColor.NETWORK_TYPE, DyeColor.CODEC);
    // Calls a method
    public static final DataComponent<PotDecorations> POT_DECORATIONS = register("pot_decorations", PotDecorations.NETWORK_TYPE, PotDecorations.NBT_TYPE);
    // Calls a method
    public static final DataComponent<List<ItemStack>> CONTAINER = register("container", ItemStackTemplate.NETWORK_TYPE.list(256), ItemStackTemplate.CODEC.list(256), List::copyOf);
    // Calls a method
    public static final DataComponent<ItemBlockState> BLOCK_STATE = register("block_state", ItemBlockState.NETWORK_TYPE, ItemBlockState.CODEC);
    // Calls a method
    public static final DataComponent<List<Bee>> BEES = register("bees", Bee.NETWORK_TYPE.list(Short.MAX_VALUE), Bee.CODEC.list(), List::copyOf);
    // Lock is an item predicate which we do not support, but can be user-represented as a compound tag (an empty tag would match everything).
    // Calls a method
    public static final DataComponent<CustomData> LOCK = register("lock", null, CustomData.CODEC);
    // Calls a method
    public static final DataComponent<SeededContainerLoot> CONTAINER_LOOT = register("container_loot", null, SeededContainerLoot.CODEC);
    // Calls a method
    public static final DataComponent<SoundEvent> BREAK_SOUND = register("break_sound", SoundEvent.NETWORK_TYPE, SoundEvent.CODEC);
    // Calls a method
    public static final DataComponent<VillagerType> VILLAGER_VARIANT = register("villager/variant", VillagerType.NETWORK_TYPE, VillagerType.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<WolfVariant>> WOLF_VARIANT = register("wolf/variant", WolfVariant.NETWORK_TYPE, WolfVariant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<WolfSoundVariant>> WOLF_SOUND_VARIANT = register("wolf/sound_variant", WolfSoundVariant.NETWORK_TYPE, WolfSoundVariant.CODEC);
    // Calls a method
    public static final DataComponent<DyeColor> WOLF_COLLAR = register("wolf/collar", DyeColor.NETWORK_TYPE, DyeColor.CODEC);
    // Calls a method
    public static final DataComponent<FoxMeta.Variant> FOX_VARIANT = register("fox/variant", FoxMeta.Variant.NETWORK_TYPE, FoxMeta.Variant.CODEC);
    // Calls a method
    public static final DataComponent<SalmonMeta.Size> SALMON_SIZE = register("salmon/size", SalmonMeta.Size.NETWORK_TYPE, SalmonMeta.Size.CODEC);
    // Calls a method
    public static final DataComponent<ParrotMeta.Color> PARROT_VARIANT = register("parrot/variant", ParrotMeta.Color.NETWORK_TYPE, ParrotMeta.Color.CODEC);
    // Calls a method
    public static final DataComponent<TropicalFishMeta.Pattern> TROPICAL_FISH_PATTERN = register("tropical_fish/pattern", TropicalFishMeta.Pattern.NETWORK_TYPE, TropicalFishMeta.Pattern.CODEC);
    // Calls a method
    public static final DataComponent<DyeColor> TROPICAL_FISH_BASE_COLOR = register("tropical_fish/base_color", DyeColor.NETWORK_TYPE, DyeColor.CODEC);
    // Calls a method
    public static final DataComponent<DyeColor> TROPICAL_FISH_PATTERN_COLOR = register("tropical_fish/pattern_color", DyeColor.NETWORK_TYPE, DyeColor.CODEC);
    // Calls a method
    public static final DataComponent<MooshroomMeta.Variant> MOOSHROOM_VARIANT = register("mooshroom/variant", MooshroomMeta.Variant.NETWORK_TYPE, MooshroomMeta.Variant.CODEC);
    // Calls a method
    public static final DataComponent<RabbitMeta.Variant> RABBIT_VARIANT = register("rabbit/variant", RabbitMeta.Variant.NETWORK_TYPE, RabbitMeta.Variant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<PigVariant>> PIG_VARIANT = register("pig/variant", PigVariant.NETWORK_TYPE, PigVariant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<PigSoundVariant>> PIG_SOUND_VARIANT = register("pig/sound_variant", PigSoundVariant.NETWORK_TYPE, PigSoundVariant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<CowVariant>> COW_VARIANT = register("cow/variant", CowVariant.NETWORK_TYPE, CowVariant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<CowSoundVariant>> COW_SOUND_VARIANT = register("cow/sound_variant", CowSoundVariant.NETWORK_TYPE, CowSoundVariant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<ChickenVariant>> CHICKEN_VARIANT = register("chicken/variant", ChickenVariant.NETWORK_TYPE, ChickenVariant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<ChickenSoundVariant>> CHICKEN_SOUND_VARIANT = register("chicken/sound_variant", ChickenSoundVariant.NETWORK_TYPE, ChickenSoundVariant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<ZombieNautilusVariant>> ZOMBIE_NAUTILUS_VARIANT = register("zombie_nautilus/variant", ZombieNautilusVariant.NETWORK_TYPE, ZombieNautilusVariant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<FrogVariant>> FROG_VARIANT = register("frog/variant", FrogVariant.NETWORK_TYPE, FrogVariant.CODEC);
    // Calls a method
    public static final DataComponent<HorseMeta.Color> HORSE_VARIANT = register("horse/variant", HorseMeta.Color.NETWORK_TYPE, HorseMeta.Color.NBT_TYPE);
    // Calls a method
    public static final DataComponent<Holder<PaintingVariant>> PAINTING_VARIANT = register("painting/variant", PaintingVariant.NETWORK_TYPE, PaintingVariant.CODEC);
    // Calls a method
    public static final DataComponent<LlamaMeta.Variant> LLAMA_VARIANT = register("llama/variant", LlamaMeta.Variant.NETWORK_TYPE, LlamaMeta.Variant.CODEC);
    // Calls a method
    public static final DataComponent<AxolotlMeta.Variant> AXOLOTL_VARIANT = register("axolotl/variant", AxolotlMeta.Variant.NETWORK_TYPE, AxolotlMeta.Variant.CODEC);
    // Calls a method
    public static final DataComponent<RegistryKey<CatVariant>> CAT_VARIANT = register("cat/variant", CatVariant.NETWORK_TYPE, CatVariant.NBT_TYPE);
    // Calls a method
    public static final DataComponent<RegistryKey<CatSoundVariant>> CAT_SOUND_VARIANT = register("cat/sound_variant", CatSoundVariant.NETWORK_TYPE, CatSoundVariant.CODEC);
    // Calls a method
    public static final DataComponent<DyeColor> CAT_COLLAR = register("cat/collar", DyeColor.NETWORK_TYPE, DyeColor.CODEC);
    // Calls a method
    public static final DataComponent<DyeColor> SHEEP_COLOR = register("sheep/color", DyeColor.NETWORK_TYPE, DyeColor.CODEC);
    // Calls a method
    public static final DataComponent<DyeColor> SHULKER_COLOR = register("shulker/color", DyeColor.NETWORK_TYPE, DyeColor.CODEC);

    // There are some components that are serialized to codec as an object containing a single field, for now we just inline them here.
    // Start of a method/block
    private static <T> Codec<T> wrapObject(String fieldName, Codec<T> serializer) {
        // Returns a value to the caller
        return StructCodec.struct(fieldName, serializer, t -> t, t -> t);
    // End of a block/expression
    }
// End of a block/expression
}
