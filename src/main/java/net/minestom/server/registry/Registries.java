// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.dialog.Dialog;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.DamageType;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.CatSoundVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.CatVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfSoundVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import d'une classe nécessaire
import net.minestom.server.game.GameEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.banner.BannerPattern;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.jukebox.JukeboxSong;
// Import d'une classe nécessaire
import net.minestom.server.instance.fluid.Fluid;
// Import d'une classe nécessaire
import net.minestom.server.instance.gamerule.GameRule;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.armor.TrimMaterial;
// Import d'une classe nécessaire
import net.minestom.server.item.armor.TrimPattern;
// Import d'une classe nécessaire
import net.minestom.server.item.enchant.*;
// Import d'une classe nécessaire
import net.minestom.server.item.instrument.Instrument;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatType;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.TagsPacket;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;
// Import d'une classe nécessaire
import net.minestom.server.world.timeline.Timeline;

// Import d'une classe nécessaire
import java.util.List;

/**
 * <p>Provides access to all the dynamic registries. {@link net.minestom.server.ServerProcess} is the most relevant
 * implementation of this interface.</p>
 *
 * @see net.minestom.server.MinecraftServer for static access to these
 */
// Déclaration de type (classe/interface/enum/record)
public interface Registries {
    // Début d'une méthode/d'un bloc
    static Registries vanilla() {
        // Renvoie une valeur à l'appelant
        return new VanillaRegistries();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static List<SendablePacket> registryDataPackets(Registries registries, boolean excludeVanilla) {
        // Renvoie une valeur à l'appelant
        return RegistriesImpl.registryDataPackets(registries, excludeVanilla);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TagsPacket tagsPacket(Registries registries) {
        // Renvoie une valeur à l'appelant
        return RegistriesImpl.tagsPacket(registries);
    // Fin d'un bloc/d'une expression
    }

    // Static registries

    // The name block conflicts with blockmanager :(
    // Début d'une méthode/d'un bloc
    default Registry<Block> blocks() {
        // Renvoie une valeur à l'appelant
        return Block.staticRegistry();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Registry<Material> material() {
        // Renvoie une valeur à l'appelant
        return Material.staticRegistry();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Registry<PotionEffect> potionEffect() {
        // Renvoie une valeur à l'appelant
        return PotionEffect.staticRegistry();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Registry<EntityType> entityType() {
        // Renvoie une valeur à l'appelant
        return EntityType.staticRegistry();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Registry<Fluid> fluid() {
        // Renvoie une valeur à l'appelant
        return Fluid.staticRegistry();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Registry<GameEvent> gameEvent() {
        // Renvoie une valeur à l'appelant
        return GameEvent.staticRegistry();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Registry<GameRule<?>> gameRule() {
        // Renvoie une valeur à l'appelant
        return GameRule.staticRegistry();
    // Fin d'un bloc/d'une expression
    }

    // Dynamic registries

    // Appelle une méthode
    DynamicRegistry<ChatType> chatType();

    // Appelle une méthode
    DynamicRegistry<DimensionType> dimensionType();

    // Appelle une méthode
    DynamicRegistry<Biome> biome();

    // Appelle une méthode
    DynamicRegistry<DamageType> damageType();

    // Appelle une méthode
    DynamicRegistry<TrimMaterial> trimMaterial();

    // Appelle une méthode
    DynamicRegistry<TrimPattern> trimPattern();

    // Appelle une méthode
    DynamicRegistry<BannerPattern> bannerPattern();

    // Appelle une méthode
    DynamicRegistry<Enchantment> enchantment();

    // Appelle une méthode
    DynamicRegistry<PaintingVariant> paintingVariant();

    // Appelle une méthode
    DynamicRegistry<JukeboxSong> jukeboxSong();

    // Appelle une méthode
    DynamicRegistry<Instrument> instrument();

    // Appelle une méthode
    DynamicRegistry<WolfVariant> wolfVariant();

    // Appelle une méthode
    DynamicRegistry<WolfSoundVariant> wolfSoundVariant();

    // Appelle une méthode
    DynamicRegistry<CatVariant> catVariant();

    // Appelle une méthode
    DynamicRegistry<CatSoundVariant> catSoundVariant();

    // Appelle une méthode
    DynamicRegistry<ChickenVariant> chickenVariant();

    // Appelle une méthode
    DynamicRegistry<ChickenSoundVariant> chickenSoundVariant();

    // Appelle une méthode
    DynamicRegistry<CowVariant> cowVariant();

    // Appelle une méthode
    DynamicRegistry<CowSoundVariant> cowSoundVariant();

    // Appelle une méthode
    DynamicRegistry<FrogVariant> frogVariant();

    // Appelle une méthode
    DynamicRegistry<PigVariant> pigVariant();

    // Appelle une méthode
    DynamicRegistry<PigSoundVariant> pigSoundVariant();

    // Appelle une méthode
    DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant();

    // Appelle une méthode
    DynamicRegistry<Dialog> dialog();

    // Appelle une méthode
    DynamicRegistry<Timeline> timeline();

    // Appelle une méthode
    DynamicRegistry<WorldClock> worldClock();

    // The following are _not_ sent to the client.

    // Appelle une méthode
    DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues();

    // Appelle une méthode
    DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects();

    // Appelle une méthode
    DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects();

    // Appelle une méthode
    DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects();

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface Selector<T> {
        // Appelle une méthode
        Registry<T> select(Registries registries);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface Delegating extends Registries {
        // Appelle une méthode
        Registries registries();

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default Registry<Block> blocks() {
            // Renvoie une valeur à l'appelant
            return registries().blocks();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default Registry<Material> material() {
            // Renvoie une valeur à l'appelant
            return registries().material();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default Registry<PotionEffect> potionEffect() {
            // Renvoie une valeur à l'appelant
            return registries().potionEffect();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default Registry<EntityType> entityType() {
            // Renvoie une valeur à l'appelant
            return registries().entityType();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default Registry<Fluid> fluid() {
            // Renvoie une valeur à l'appelant
            return registries().fluid();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default Registry<GameEvent> gameEvent() {
            // Renvoie une valeur à l'appelant
            return registries().gameEvent();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<ChatType> chatType() {
            // Renvoie une valeur à l'appelant
            return registries().chatType();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<DimensionType> dimensionType() {
            // Renvoie une valeur à l'appelant
            return registries().dimensionType();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<Biome> biome() {
            // Renvoie une valeur à l'appelant
            return registries().biome();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<DamageType> damageType() {
            // Renvoie une valeur à l'appelant
            return registries().damageType();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<TrimMaterial> trimMaterial() {
            // Renvoie une valeur à l'appelant
            return registries().trimMaterial();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<TrimPattern> trimPattern() {
            // Renvoie une valeur à l'appelant
            return registries().trimPattern();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<BannerPattern> bannerPattern() {
            // Renvoie une valeur à l'appelant
            return registries().bannerPattern();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<Enchantment> enchantment() {
            // Renvoie une valeur à l'appelant
            return registries().enchantment();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<PaintingVariant> paintingVariant() {
            // Renvoie une valeur à l'appelant
            return registries().paintingVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<JukeboxSong> jukeboxSong() {
            // Renvoie une valeur à l'appelant
            return registries().jukeboxSong();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<Instrument> instrument() {
            // Renvoie une valeur à l'appelant
            return registries().instrument();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<WolfVariant> wolfVariant() {
            // Renvoie une valeur à l'appelant
            return registries().wolfVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<WolfSoundVariant> wolfSoundVariant() {
            // Renvoie une valeur à l'appelant
            return registries().wolfSoundVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<CatVariant> catVariant() {
            // Renvoie une valeur à l'appelant
            return registries().catVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<CatSoundVariant> catSoundVariant() {
            // Renvoie une valeur à l'appelant
            return registries().catSoundVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<ChickenVariant> chickenVariant() {
            // Renvoie une valeur à l'appelant
            return registries().chickenVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<ChickenSoundVariant> chickenSoundVariant() {
            // Renvoie une valeur à l'appelant
            return registries().chickenSoundVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<CowVariant> cowVariant() {
            // Renvoie une valeur à l'appelant
            return registries().cowVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<CowSoundVariant> cowSoundVariant() {
            // Renvoie une valeur à l'appelant
            return registries().cowSoundVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<FrogVariant> frogVariant() {
            // Renvoie une valeur à l'appelant
            return registries().frogVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<PigVariant> pigVariant() {
            // Renvoie une valeur à l'appelant
            return registries().pigVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<PigSoundVariant> pigSoundVariant() {
            // Renvoie une valeur à l'appelant
            return registries().pigSoundVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant() {
            // Renvoie une valeur à l'appelant
            return registries().zombieNautilusVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<Dialog> dialog() {
            // Renvoie une valeur à l'appelant
            return registries().dialog();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<Timeline> timeline() {
            // Renvoie une valeur à l'appelant
            return registries().timeline();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<WorldClock> worldClock() {
            // Renvoie une valeur à l'appelant
            return registries().worldClock();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
            // Renvoie une valeur à l'appelant
            return registries().enchantmentLevelBasedValues();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
            // Renvoie une valeur à l'appelant
            return registries().enchantmentValueEffects();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
            // Renvoie une valeur à l'appelant
            return registries().enchantmentEntityEffects();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
            // Renvoie une valeur à l'appelant
            return registries().enchantmentLocationEffects();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
