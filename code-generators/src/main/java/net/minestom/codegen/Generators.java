// Déclaration du paquet de ce fichier
package net.minestom.codegen;

// Import d'une classe nécessaire
import java.io.InputStream;
// Import d'une classe nécessaire
import java.nio.file.Path;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public final class Generators {

    // Début d'une méthode/d'un bloc
    public static void main(String[] args) {
        // Embranchement : vérifie une condition
        if (args.length != 1) {
            // Appelle une méthode
            System.err.println("Usage: <target folder>");
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        Path outputFolder = Path.of(args[0]);

        // Special generators
        // Crée un nouvel objet
        new DyeColorGenerator(resource("dye_colors.json"), outputFolder).generate();
        // Crée un nouvel objet
        new ParticleGenerator(resource("particle.json"), outputFolder).generate();
        // Crée un nouvel objet
        new ConstantsGenerator(resource("constants.json"), outputFolder).generate();
        // Crée un nouvel objet
        new RecipeTypeGenerator(resource("recipe_types.json"), outputFolder).generate();
        // Crée un nouvel objet
        new GenericEnumGenerator("net.minestom.server.recipe.display", "RecipeDisplayType",
                // Appelle une méthode
                resource("recipe_display_types.json"), outputFolder).generate();
        // Crée un nouvel objet
        new GenericEnumGenerator("net.minestom.server.recipe.display", "SlotDisplayType",
                // Appelle une méthode
                resource("slot_display_types.json"), outputFolder).generate();
        // Crée un nouvel objet
        new GenericEnumGenerator("net.minestom.server.recipe", "RecipeBookCategory",
                // Appelle une méthode
                resource("recipe_book_categories.json"), outputFolder).generate();
        // Crée un nouvel objet
        new GenericEnumGenerator("net.minestom.server.item.component", "ConsumeEffectType",
                // Appelle une méthode
                resource("consume_effects.json"), outputFolder).packagePrivate().generate();
        // Crée un nouvel objet
        new GenericEnumGenerator("net.minestom.server.command", "ArgumentParserType",
                // Appelle une méthode
                resource("command_arguments.json"), outputFolder).generate();
        // Crée un nouvel objet
        new GenericEnumGenerator("net.minestom.server.entity", "VillagerType",
                // Appelle une méthode
                resource("villager_types.json"), outputFolder).generate();
        // Crée un nouvel objet
        new WorldEventGenerator("net.minestom.server.worldevent", "WorldEvent",
                // Appelle une méthode
                resource("world_events.json"), outputFolder).generate();

        // Appelle une méthode
        var generator = new RegistryGenerator(outputFolder);

        // Static registries
        // Appelle une méthode
        generator.generate(resource("block.json"), "net.minestom.server.instance.block", "Block", "BlockImpl", "Blocks");
        // Appelle une méthode
        generator.generate(resource("item.json"), "net.minestom.server.item", "Material", "MaterialImpl", "Materials");
        // Appelle une méthode
        generator.generate(resource("entity_type.json"), "net.minestom.server.entity", "EntityType", "EntityTypeImpl", "EntityTypes");
        // Appelle une méthode
        generator.generate(resource("potion_effect.json"), "net.minestom.server.potion", "PotionEffect", "PotionEffectImpl", "PotionEffects");
        // Appelle une méthode
        generator.generate(resource("potion_type.json"), "net.minestom.server.potion", "PotionType", "PotionTypeImpl", "PotionTypes");
        // Appelle une méthode
        generator.generate(resource("sound_event.json"), "net.minestom.server.sound", "SoundEvent", "BuiltinSoundEvent", "SoundEvents");
        // Appelle une méthode
        generator.generate(resource("custom_statistics.json"), "net.minestom.server.statistic", "StatisticType", "StatisticTypeImpl", "StatisticTypes");
        // Appelle une méthode
        generator.generate(resource("attribute.json"), "net.minestom.server.entity.attribute", "Attribute", "AttributeImpl", "Attributes");
        // Appelle une méthode
        generator.generate(resource("feature_flag.json"), "net.minestom.server", "FeatureFlag", "FeatureFlagImpl", "FeatureFlags");
        // Appelle une méthode
        generator.generate(resource("fluid.json"), "net.minestom.server.instance.fluid", "Fluid", "FluidImpl", "Fluids");
        // Appelle une méthode
        generator.generate(resource("villager_profession.json"), "net.minestom.server.entity", "VillagerProfession", "VillagerProfessionImpl", "VillagerProfessions");
        // Appelle une méthode
        generator.generate(resource("game_event.json"), "net.minestom.server.game", "GameEvent", "GameEventImpl", "GameEvents");
        // Appelle une méthode
        generator.generate(resource("block_sound_type.json"), "net.minestom.server.instance.block", "BlockSoundType", "BlockSoundImpl", "BlockSoundTypes");
        // Appelle une méthode
        generator.generate(resource("block_entity_types.json"), "net.minestom.server.instance.block", "BlockEntityType", "BlockEntityTypeImpl", "BlockEntityTypes");

        // Dynamic registries
        // Appelle une méthode
        generator.generateKeys(resource("chat_type.json"), "net.minestom.server.message", "ChatType");
        // Appelle une méthode
        generator.generateKeys(resource("dimension_type.json"), "net.minestom.server.world", "DimensionType");
        // Appelle une méthode
        generator.generateKeys(resource("damage_type.json"), "net.minestom.server.entity.damage", "DamageType");
        // Appelle une méthode
        generator.generateKeys(resource("trim_material.json"), "net.minestom.server.item.armor", "TrimMaterial");
        // Appelle une méthode
        generator.generateKeys(resource("trim_pattern.json"), "net.minestom.server.item.armor", "TrimPattern");
        // Appelle une méthode
        generator.generateKeys(resource("banner_pattern.json"), "net.minestom.server.instance.block.banner", "BannerPattern");
        // Appelle une méthode
        generator.generateKeys(resource("enchantment.json"), "net.minestom.server.item.enchant", "Enchantment");
        // Appelle une méthode
        generator.generateKeys(resource("painting_variant.json"), "net.minestom.server.entity.metadata.other", "PaintingVariant");
        // Appelle une méthode
        generator.generateKeys(resource("jukebox_song.json"), "net.minestom.server.instance.block.jukebox", "JukeboxSong");
        // Appelle une méthode
        generator.generateKeys(resource("instrument.json"), "net.minestom.server.item.instrument", "Instrument");
        // Appelle une méthode
        generator.generateKeys(resource("wolf_variant.json"), "net.minestom.server.entity.metadata.animal.tameable", "WolfVariant");
        // Appelle une méthode
        generator.generateKeys(resource("wolf_sound_variant.json"), "net.minestom.server.entity.metadata.animal.tameable", "WolfSoundVariant");
        // Appelle une méthode
        generator.generateKeys(resource("cat_variant.json"), "net.minestom.server.entity.metadata.animal.tameable", "CatVariant");
        // Appelle une méthode
        generator.generateKeys(resource("chicken_variant.json"), "net.minestom.server.entity.metadata.animal", "ChickenVariant");
        // Appelle une méthode
        generator.generateKeys(resource("cow_variant.json"), "net.minestom.server.entity.metadata.animal", "CowVariant");
        // Appelle une méthode
        generator.generateKeys(resource("frog_variant.json"), "net.minestom.server.entity.metadata.animal", "FrogVariant");
        // Appelle une méthode
        generator.generateKeys(resource("pig_variant.json"), "net.minestom.server.entity.metadata.animal", "PigVariant");
        // Appelle une méthode
        generator.generateKeys(resource("zombie_nautilus_variant.json"), "net.minestom.server.entity.metadata.animal", "ZombieNautilusVariant");
        // Appelle une méthode
        generator.generateKeys(resource("worldgen/biome.json"), "net.minestom.server.world.biome", "Biome");
        // Appelle une méthode
        generator.generateKeys(resource("timeline.json"), "net.minestom.server.world.timeline", "Timeline");

        // Appelle une méthode
        System.out.println("Finished generating code");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static InputStream resource(String name) {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(Generators.class.getResourceAsStream("/" + name), "Cannot find resource: %s".formatted(name));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
