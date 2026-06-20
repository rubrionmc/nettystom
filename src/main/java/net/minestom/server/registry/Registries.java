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
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.server.world.timeline.Timeline;

/**
 * <p>Provides access to all the dynamic registries. {@link net.minestom.server.ServerProcess} is the most relevant
 * implementation of this interface.</p>
 *
 * @see net.minestom.server.MinecraftServer for static access to these
 */
// Déclaration de type (classe/interface/enum/record)
public interface Registries {

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
    DynamicRegistry<ChickenVariant> chickenVariant();

    // Appelle une méthode
    DynamicRegistry<CowVariant> cowVariant();

    // Appelle une méthode
    DynamicRegistry<FrogVariant> frogVariant();

    // Appelle une méthode
    DynamicRegistry<PigVariant> pigVariant();

    // Appelle une méthode
    DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant();

    // Appelle une méthode
    DynamicRegistry<Dialog> dialog();

    // Appelle une méthode
    DynamicRegistry<Timeline> timeline();

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

    // Déclaration de type (classe/interface/enum/record)
    class Delegating implements Registries {
        // Instruction de code
        private final Registries delegate;

        // Début d'une méthode/d'un bloc
        public Delegating(Registries delegate) {
            // Accès à l'objet courant/parent
            this.delegate = delegate;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Registry<Block> blocks() {
            // Renvoie une valeur à l'appelant
            return delegate.blocks();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Registry<Material> material() {
            // Renvoie une valeur à l'appelant
            return delegate.material();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Registry<PotionEffect> potionEffect() {
            // Renvoie une valeur à l'appelant
            return delegate.potionEffect();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Registry<EntityType> entityType() {
            // Renvoie une valeur à l'appelant
            return delegate.entityType();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Registry<Fluid> fluid() {
            // Renvoie une valeur à l'appelant
            return delegate.fluid();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Registry<GameEvent> gameEvent() {
            // Renvoie une valeur à l'appelant
            return delegate.gameEvent();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<ChatType> chatType() {
            // Renvoie une valeur à l'appelant
            return delegate.chatType();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<DimensionType> dimensionType() {
            // Renvoie une valeur à l'appelant
            return delegate.dimensionType();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<Biome> biome() {
            // Renvoie une valeur à l'appelant
            return delegate.biome();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<DamageType> damageType() {
            // Renvoie une valeur à l'appelant
            return delegate.damageType();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<TrimMaterial> trimMaterial() {
            // Renvoie une valeur à l'appelant
            return delegate.trimMaterial();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<TrimPattern> trimPattern() {
            // Renvoie une valeur à l'appelant
            return delegate.trimPattern();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<BannerPattern> bannerPattern() {
            // Renvoie une valeur à l'appelant
            return delegate.bannerPattern();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<Enchantment> enchantment() {
            // Renvoie une valeur à l'appelant
            return delegate.enchantment();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<PaintingVariant> paintingVariant() {
            // Renvoie une valeur à l'appelant
            return delegate.paintingVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<JukeboxSong> jukeboxSong() {
            // Renvoie une valeur à l'appelant
            return delegate.jukeboxSong();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<Instrument> instrument() {
            // Renvoie une valeur à l'appelant
            return delegate.instrument();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<WolfVariant> wolfVariant() {
            // Renvoie une valeur à l'appelant
            return delegate.wolfVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<WolfSoundVariant> wolfSoundVariant() {
            // Renvoie une valeur à l'appelant
            return delegate.wolfSoundVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<CatVariant> catVariant() {
            // Renvoie une valeur à l'appelant
            return delegate.catVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<ChickenVariant> chickenVariant() {
            // Renvoie une valeur à l'appelant
            return delegate.chickenVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<CowVariant> cowVariant() {
            // Renvoie une valeur à l'appelant
            return delegate.cowVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<FrogVariant> frogVariant() {
            // Renvoie une valeur à l'appelant
            return delegate.frogVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<PigVariant> pigVariant() {
            // Renvoie une valeur à l'appelant
            return delegate.pigVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant() {
            // Renvoie une valeur à l'appelant
            return delegate.zombieNautilusVariant();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<Dialog> dialog() {
            // Renvoie une valeur à l'appelant
            return delegate.dialog();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<Timeline> timeline() {
            // Renvoie une valeur à l'appelant
            return delegate.timeline();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
            // Renvoie une valeur à l'appelant
            return delegate.enchantmentLevelBasedValues();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
            // Renvoie une valeur à l'appelant
            return delegate.enchantmentValueEffects();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
            // Renvoie une valeur à l'appelant
            return delegate.enchantmentEntityEffects();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
            // Renvoie une valeur à l'appelant
            return delegate.enchantmentLocationEffects();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
