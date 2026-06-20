// Package declaration for this file
package net.minestom.codegen;

// Import of a required class
import java.nio.file.Path;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
public final class Generators {
    // Calls a method
    private static final UnaryOperator<String> IDENTITY = UnaryOperator.identity();
    // Calls a method
    private static final UnaryOperator<String> STRIP_CRAFTING = name -> name.replace("CRAFTING_", "");

    // Assigns a value
    private static final List<EnumSpec> ENUMS = List.of(
            // Creates a new object
            new EnumSpec("recipe_types.json", "net.minestom.server.recipe", "RecipeType", true, STRIP_CRAFTING, "RecipeTypeGenerator"),
            // Creates a new object
            new EnumSpec("recipe_display_types.json", "net.minestom.server.recipe.display", "RecipeDisplayType"),
            // Creates a new object
            new EnumSpec("slot_display_types.json", "net.minestom.server.recipe.display", "SlotDisplayType"),
            // Creates a new object
            new EnumSpec("recipe_book_categories.json", "net.minestom.server.recipe", "RecipeBookCategory"),
            // Creates a new object
            new EnumSpec("consume_effects.json", "net.minestom.server.item.component", "ConsumeEffectType", false, IDENTITY, "GenericEnumGenerator"),
            // Creates a new object
            new EnumSpec("command_arguments.json", "net.minestom.server.command", "ArgumentParserType"),
            // Creates a new object
            new EnumSpec("villager_types.json", "net.minestom.server.entity", "VillagerType")
    // End of a block/expression
    );

    // Assigns a value
    private static final List<StaticRegistrySpec> STATIC_REGISTRIES = List.of(
            // Creates a new object
            new StaticRegistrySpec("block.json", "net.minestom.server.instance.block", "Block", "BlockImpl", "Blocks"),
            // Creates a new object
            new StaticRegistrySpec("item.json", "net.minestom.server.item", "Material", "MaterialImpl", "Materials"),
            // Creates a new object
            new StaticRegistrySpec("entity_type.json", "net.minestom.server.entity", "EntityType", "EntityTypeImpl", "EntityTypes"),
            // Creates a new object
            new StaticRegistrySpec("potion_effect.json", "net.minestom.server.potion", "PotionEffect", "PotionEffectImpl", "PotionEffects"),
            // Creates a new object
            new StaticRegistrySpec("potion_type.json", "net.minestom.server.potion", "PotionType", "PotionTypeImpl", "PotionTypes"),
            // Creates a new object
            new StaticRegistrySpec("sound_event.json", "net.minestom.server.sound", "SoundEvent", "BuiltinSoundEvent", "SoundEvents"),
            // Creates a new object
            new StaticRegistrySpec("custom_statistics.json", "net.minestom.server.statistic", "StatisticType", "StatisticTypeImpl", "StatisticTypes"),
            // Creates a new object
            new StaticRegistrySpec("attribute.json", "net.minestom.server.entity.attribute", "Attribute", "AttributeImpl", "Attributes"),
            // Creates a new object
            new StaticRegistrySpec("feature_flag.json", "net.minestom.server", "FeatureFlag", "FeatureFlagImpl", "FeatureFlags"),
            // Creates a new object
            new StaticRegistrySpec("fluid.json", "net.minestom.server.instance.fluid", "Fluid", "FluidImpl", "Fluids"),
            // Creates a new object
            new StaticRegistrySpec("villager_profession.json", "net.minestom.server.entity", "VillagerProfession", "VillagerProfessionImpl", "VillagerProfessions"),
            // Creates a new object
            new StaticRegistrySpec("game_event.json", "net.minestom.server.game", "GameEvent", "GameEventImpl", "GameEvents"),
            // Creates a new object
            new StaticRegistrySpec("block_sound_type.json", "net.minestom.server.instance.block", "BlockSoundType", "BlockSoundImpl", "BlockSoundTypes"),
            // Creates a new object
            new StaticRegistrySpec("block_entity_types.json", "net.minestom.server.instance.block", "BlockEntityType", "BlockEntityTypeImpl", "BlockEntityTypes")
    // End of a block/expression
    );

    // Assigns a value
    private static final List<DynamicRegistrySpec> DYNAMIC_REGISTRIES = List.of(
            // Creates a new object
            new DynamicRegistrySpec("chat_type.json", "net.minestom.server.message", "ChatType"),
            // Creates a new object
            new DynamicRegistrySpec("dimension_type.json", "net.minestom.server.world", "DimensionType"),
            // Creates a new object
            new DynamicRegistrySpec("damage_type.json", "net.minestom.server.entity.damage", "DamageType"),
            // Creates a new object
            new DynamicRegistrySpec("trim_material.json", "net.minestom.server.item.armor", "TrimMaterial"),
            // Creates a new object
            new DynamicRegistrySpec("trim_pattern.json", "net.minestom.server.item.armor", "TrimPattern"),
            // Creates a new object
            new DynamicRegistrySpec("banner_pattern.json", "net.minestom.server.instance.block.banner", "BannerPattern"),
            // Creates a new object
            new DynamicRegistrySpec("enchantment.json", "net.minestom.server.item.enchant", "Enchantment"),
            // Creates a new object
            new DynamicRegistrySpec("painting_variant.json", "net.minestom.server.entity.metadata.other", "PaintingVariant"),
            // Creates a new object
            new DynamicRegistrySpec("jukebox_song.json", "net.minestom.server.instance.block.jukebox", "JukeboxSong"),
            // Creates a new object
            new DynamicRegistrySpec("instrument.json", "net.minestom.server.item.instrument", "Instrument"),
            // Creates a new object
            new DynamicRegistrySpec("wolf_variant.json", "net.minestom.server.entity.metadata.animal.tameable", "WolfVariant"),
            // Creates a new object
            new DynamicRegistrySpec("wolf_sound_variant.json", "net.minestom.server.entity.metadata.animal.tameable", "WolfSoundVariant"),
            // Creates a new object
            new DynamicRegistrySpec("cat_variant.json", "net.minestom.server.entity.metadata.animal.tameable", "CatVariant"),
            // Creates a new object
            new DynamicRegistrySpec("cat_sound_variant.json", "net.minestom.server.entity.metadata.animal.tameable", "CatSoundVariant"),
            // Creates a new object
            new DynamicRegistrySpec("chicken_variant.json", "net.minestom.server.entity.metadata.animal", "ChickenVariant"),
            // Creates a new object
            new DynamicRegistrySpec("chicken_sound_variant.json", "net.minestom.server.entity.metadata.animal", "ChickenSoundVariant"),
            // Creates a new object
            new DynamicRegistrySpec("cow_variant.json", "net.minestom.server.entity.metadata.animal", "CowVariant"),
            // Creates a new object
            new DynamicRegistrySpec("cow_sound_variant.json", "net.minestom.server.entity.metadata.animal", "CowSoundVariant"),
            // Creates a new object
            new DynamicRegistrySpec("frog_variant.json", "net.minestom.server.entity.metadata.animal", "FrogVariant"),
            // Creates a new object
            new DynamicRegistrySpec("pig_variant.json", "net.minestom.server.entity.metadata.animal", "PigVariant"),
            // Creates a new object
            new DynamicRegistrySpec("pig_sound_variant.json", "net.minestom.server.entity.metadata.animal", "PigSoundVariant"),
            // Creates a new object
            new DynamicRegistrySpec("zombie_nautilus_variant.json", "net.minestom.server.entity.metadata.animal", "ZombieNautilusVariant"),
            // Creates a new object
            new DynamicRegistrySpec("worldgen/biome.json", "net.minestom.server.world.biome", "Biome"),
            // Creates a new object
            new DynamicRegistrySpec("timeline.json", "net.minestom.server.world.timeline", "Timeline"),
            // Creates a new object
            new DynamicRegistrySpec("world_clock.json", "net.minestom.server.world.clock", "WorldClock"),
            // Creates a new object
            new DynamicRegistrySpec("clock_time_marker.json", "net.minestom.server.world.clock", "ClockTimeMarker")
    // End of a block/expression
    );

    // Start of a method/block
    static void main(String[] args) {
        // Branch: checks a condition
        if (args.length != 1) {
            // Calls a method
            System.err.println("Usage: <target folder>");
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        generateAll(Path.of(args[0]));
        // Calls a method
        System.out.println("Finished generating code");
    // End of a block/expression
    }

    // Start of a method/block
    public static void generateAll(Path outputFolder) {
        // Calls a method
        Codegen codegen = new Codegen(outputFolder);

        // Creates a new object
        new DyeColorGenerator(codegen).generate();
        // Creates a new object
        new ParticleGenerator(codegen).generate();
        // Creates a new object
        new ConstantsGenerator(codegen).generate();
        // Creates a new object
        new GameRuleGenerator(codegen).generate();

        // Calls a method
        GenericEnumGenerator enumGenerator = new GenericEnumGenerator(codegen);
        // Calls a method
        ENUMS.forEach(enumGenerator::generate);

        // Creates a new object
        new WorldEventGenerator(codegen, "net.minestom.server.worldevent", "WorldEvent", "world_events.json").generate();

        // Calls a method
        RegistryGenerator registryGenerator = new RegistryGenerator(codegen);
        // Calls a method
        STATIC_REGISTRIES.forEach(registryGenerator::generate);
        // Calls a method
        DYNAMIC_REGISTRIES.forEach(registryGenerator::generate);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record EnumSpec(String resource, String packageName, String className, boolean isPublic,
                    // Start of a method/block
                    UnaryOperator<String> constantNameTransform, String generatorName) {
        // Start of a method/block
        EnumSpec(String resource, String packageName, String className) {
            // Calls a method
            this(resource, packageName, className, true, IDENTITY, "GenericEnumGenerator");
        // End of a block/expression
        }

        // Start of a method/block
        EnumSpec {
            // Calls a method
            Objects.requireNonNull(resource, "resource cannot be null");
            // Calls a method
            Objects.requireNonNull(packageName, "packageName cannot be null");
            // Calls a method
            Objects.requireNonNull(className, "className cannot be null");
            // Calls a method
            Objects.requireNonNull(constantNameTransform, "constantNameTransform cannot be null");
            // Calls a method
            Objects.requireNonNull(generatorName, "generatorName cannot be null");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record StaticRegistrySpec(String resource, String packageName, String typeName, String loaderName,
                              // Start of a method/block
                              String generatedName) {
        // Start of a method/block
        StaticRegistrySpec {
            // Calls a method
            Objects.requireNonNull(resource, "resource cannot be null");
            // Calls a method
            Objects.requireNonNull(packageName, "packageName cannot be null");
            // Calls a method
            Objects.requireNonNull(typeName, "typeName cannot be null");
            // Calls a method
            Objects.requireNonNull(loaderName, "loaderName cannot be null");
            // Calls a method
            Objects.requireNonNull(generatedName, "generatedName cannot be null");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DynamicRegistrySpec(String resource, String packageName, String typeName, String generatedName) {
        // Start of a method/block
        DynamicRegistrySpec(String resource, String packageName, String typeName) {
            // Calls a method
            this(resource, packageName, typeName, typeName + "s");
        // End of a block/expression
        }

        // Start of a method/block
        DynamicRegistrySpec {
            // Calls a method
            Objects.requireNonNull(resource, "resource cannot be null");
            // Calls a method
            Objects.requireNonNull(packageName, "packageName cannot be null");
            // Calls a method
            Objects.requireNonNull(typeName, "typeName cannot be null");
            // Calls a method
            Objects.requireNonNull(generatedName, "generatedName cannot be null");
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
