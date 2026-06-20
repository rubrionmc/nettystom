// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.dialog.Dialog;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.damage.DamageType;
// Import of a required class
import net.minestom.server.entity.metadata.animal.*;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.CatSoundVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.CatVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.WolfSoundVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.WolfVariant;
// Import of a required class
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import of a required class
import net.minestom.server.game.GameEvent;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.banner.BannerPattern;
// Import of a required class
import net.minestom.server.instance.block.jukebox.JukeboxSong;
// Import of a required class
import net.minestom.server.instance.fluid.Fluid;
// Import of a required class
import net.minestom.server.instance.gamerule.GameRule;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.armor.TrimMaterial;
// Import of a required class
import net.minestom.server.item.armor.TrimPattern;
// Import of a required class
import net.minestom.server.item.enchant.*;
// Import of a required class
import net.minestom.server.item.instrument.Instrument;
// Import of a required class
import net.minestom.server.message.ChatType;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.TagsPacket;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import net.minestom.server.world.timeline.Timeline;

// Import of a required class
import java.util.List;

/**
 * <p>Provides access to all the dynamic registries. {@link net.minestom.server.ServerProcess} is the most relevant
 * implementation of this interface.</p>
 *
 * @see net.minestom.server.MinecraftServer for static access to these
 */
// Type declaration (class/interface/enum/record)
public interface Registries {
    // Start of a method/block
    static Registries vanilla() {
        // Returns a value to the caller
        return new VanillaRegistries();
    // End of a block/expression
    }

    // Start of a method/block
    static List<SendablePacket> registryDataPackets(Registries registries, boolean excludeVanilla) {
        // Returns a value to the caller
        return RegistriesImpl.registryDataPackets(registries, excludeVanilla);
    // End of a block/expression
    }

    // Start of a method/block
    static TagsPacket tagsPacket(Registries registries) {
        // Returns a value to the caller
        return RegistriesImpl.tagsPacket(registries);
    // End of a block/expression
    }

    // Static registries

    // The name block conflicts with blockmanager :(
    // Start of a method/block
    default Registry<Block> blocks() {
        // Returns a value to the caller
        return Block.staticRegistry();
    // End of a block/expression
    }

    // Start of a method/block
    default Registry<Material> material() {
        // Returns a value to the caller
        return Material.staticRegistry();
    // End of a block/expression
    }

    // Start of a method/block
    default Registry<PotionEffect> potionEffect() {
        // Returns a value to the caller
        return PotionEffect.staticRegistry();
    // End of a block/expression
    }

    // Start of a method/block
    default Registry<EntityType> entityType() {
        // Returns a value to the caller
        return EntityType.staticRegistry();
    // End of a block/expression
    }

    // Start of a method/block
    default Registry<Fluid> fluid() {
        // Returns a value to the caller
        return Fluid.staticRegistry();
    // End of a block/expression
    }

    // Start of a method/block
    default Registry<GameEvent> gameEvent() {
        // Returns a value to the caller
        return GameEvent.staticRegistry();
    // End of a block/expression
    }

    // Start of a method/block
    default Registry<GameRule<?>> gameRule() {
        // Returns a value to the caller
        return GameRule.staticRegistry();
    // End of a block/expression
    }

    // Dynamic registries

    // Calls a method
    DynamicRegistry<ChatType> chatType();

    // Calls a method
    DynamicRegistry<DimensionType> dimensionType();

    // Calls a method
    DynamicRegistry<Biome> biome();

    // Calls a method
    DynamicRegistry<DamageType> damageType();

    // Calls a method
    DynamicRegistry<TrimMaterial> trimMaterial();

    // Calls a method
    DynamicRegistry<TrimPattern> trimPattern();

    // Calls a method
    DynamicRegistry<BannerPattern> bannerPattern();

    // Calls a method
    DynamicRegistry<Enchantment> enchantment();

    // Calls a method
    DynamicRegistry<PaintingVariant> paintingVariant();

    // Calls a method
    DynamicRegistry<JukeboxSong> jukeboxSong();

    // Calls a method
    DynamicRegistry<Instrument> instrument();

    // Calls a method
    DynamicRegistry<WolfVariant> wolfVariant();

    // Calls a method
    DynamicRegistry<WolfSoundVariant> wolfSoundVariant();

    // Calls a method
    DynamicRegistry<CatVariant> catVariant();

    // Calls a method
    DynamicRegistry<CatSoundVariant> catSoundVariant();

    // Calls a method
    DynamicRegistry<ChickenVariant> chickenVariant();

    // Calls a method
    DynamicRegistry<ChickenSoundVariant> chickenSoundVariant();

    // Calls a method
    DynamicRegistry<CowVariant> cowVariant();

    // Calls a method
    DynamicRegistry<CowSoundVariant> cowSoundVariant();

    // Calls a method
    DynamicRegistry<FrogVariant> frogVariant();

    // Calls a method
    DynamicRegistry<PigVariant> pigVariant();

    // Calls a method
    DynamicRegistry<PigSoundVariant> pigSoundVariant();

    // Calls a method
    DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant();

    // Calls a method
    DynamicRegistry<Dialog> dialog();

    // Calls a method
    DynamicRegistry<Timeline> timeline();

    // Calls a method
    DynamicRegistry<WorldClock> worldClock();

    // The following are _not_ sent to the client.

    // Calls a method
    DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues();

    // Calls a method
    DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects();

    // Calls a method
    DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects();

    // Calls a method
    DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects();

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface Selector<T> {
        // Calls a method
        Registry<T> select(Registries registries);
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface Delegating extends Registries {
        // Calls a method
        Registries registries();

        // Annotation for the following element
        @Override
        // Start of a method/block
        default Registry<Block> blocks() {
            // Returns a value to the caller
            return registries().blocks();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default Registry<Material> material() {
            // Returns a value to the caller
            return registries().material();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default Registry<PotionEffect> potionEffect() {
            // Returns a value to the caller
            return registries().potionEffect();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default Registry<EntityType> entityType() {
            // Returns a value to the caller
            return registries().entityType();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default Registry<Fluid> fluid() {
            // Returns a value to the caller
            return registries().fluid();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default Registry<GameEvent> gameEvent() {
            // Returns a value to the caller
            return registries().gameEvent();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<ChatType> chatType() {
            // Returns a value to the caller
            return registries().chatType();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<DimensionType> dimensionType() {
            // Returns a value to the caller
            return registries().dimensionType();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<Biome> biome() {
            // Returns a value to the caller
            return registries().biome();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<DamageType> damageType() {
            // Returns a value to the caller
            return registries().damageType();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<TrimMaterial> trimMaterial() {
            // Returns a value to the caller
            return registries().trimMaterial();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<TrimPattern> trimPattern() {
            // Returns a value to the caller
            return registries().trimPattern();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<BannerPattern> bannerPattern() {
            // Returns a value to the caller
            return registries().bannerPattern();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<Enchantment> enchantment() {
            // Returns a value to the caller
            return registries().enchantment();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<PaintingVariant> paintingVariant() {
            // Returns a value to the caller
            return registries().paintingVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<JukeboxSong> jukeboxSong() {
            // Returns a value to the caller
            return registries().jukeboxSong();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<Instrument> instrument() {
            // Returns a value to the caller
            return registries().instrument();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<WolfVariant> wolfVariant() {
            // Returns a value to the caller
            return registries().wolfVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<WolfSoundVariant> wolfSoundVariant() {
            // Returns a value to the caller
            return registries().wolfSoundVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<CatVariant> catVariant() {
            // Returns a value to the caller
            return registries().catVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<CatSoundVariant> catSoundVariant() {
            // Returns a value to the caller
            return registries().catSoundVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<ChickenVariant> chickenVariant() {
            // Returns a value to the caller
            return registries().chickenVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<ChickenSoundVariant> chickenSoundVariant() {
            // Returns a value to the caller
            return registries().chickenSoundVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<CowVariant> cowVariant() {
            // Returns a value to the caller
            return registries().cowVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<CowSoundVariant> cowSoundVariant() {
            // Returns a value to the caller
            return registries().cowSoundVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<FrogVariant> frogVariant() {
            // Returns a value to the caller
            return registries().frogVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<PigVariant> pigVariant() {
            // Returns a value to the caller
            return registries().pigVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<PigSoundVariant> pigSoundVariant() {
            // Returns a value to the caller
            return registries().pigSoundVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant() {
            // Returns a value to the caller
            return registries().zombieNautilusVariant();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<Dialog> dialog() {
            // Returns a value to the caller
            return registries().dialog();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<Timeline> timeline() {
            // Returns a value to the caller
            return registries().timeline();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<WorldClock> worldClock() {
            // Returns a value to the caller
            return registries().worldClock();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
            // Returns a value to the caller
            return registries().enchantmentLevelBasedValues();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
            // Returns a value to the caller
            return registries().enchantmentValueEffects();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
            // Returns a value to the caller
            return registries().enchantmentEntityEffects();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        default DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
            // Returns a value to the caller
            return registries().enchantmentLocationEffects();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
