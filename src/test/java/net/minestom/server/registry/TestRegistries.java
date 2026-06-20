// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.dialog.Dialog;
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
import net.minestom.server.instance.block.banner.BannerPattern;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.jukebox.JukeboxSong;
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
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.server.world.timeline.Timeline;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Déclaration de type (classe/interface/enum/record)
public class TestRegistries implements Registries {
    // Affecte une valeur
    public @Nullable DynamicRegistry<ChatType> chatType = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<DimensionType> dimensionType = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<Biome> biome = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<DamageType> damageType = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<TrimMaterial> trimMaterial = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<TrimPattern> trimPattern = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<BannerPattern> bannerPattern = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<WolfVariant> wolfVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<Enchantment> enchantment = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<PaintingVariant> paintingVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<JukeboxSong> jukeboxSong = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<Instrument> instrument = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<WolfSoundVariant> wolfSoundVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<CatVariant> catVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<CatSoundVariant> catSoundVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<ChickenVariant> chickenVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<ChickenSoundVariant> chickenSoundVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<CowVariant> cowVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<CowSoundVariant> cowSoundVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<FrogVariant> frogVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<PigVariant> pigVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<PigSoundVariant> pigSoundVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<Dialog> dialog = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<Timeline> timeline = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<WorldClock> worldClock = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects = null;
    // Affecte une valeur
    public @Nullable DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects = null;

    // Début d'une méthode/d'un bloc
    public TestRegistries(Consumer<TestRegistries> init) {
        // Appelle une méthode
        init.accept(this);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ChatType> chatType() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(chatType);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<DimensionType> dimensionType() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(dimensionType);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Biome> biome() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(biome);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<DamageType> damageType() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(damageType);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<TrimMaterial> trimMaterial() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(trimMaterial);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<TrimPattern> trimPattern() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(trimPattern);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<BannerPattern> bannerPattern() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(bannerPattern);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<WolfVariant> wolfVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(wolfVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<WolfSoundVariant> wolfSoundVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(wolfSoundVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CatVariant> catVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(catVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CatSoundVariant> catSoundVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(catSoundVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ChickenVariant> chickenVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(chickenVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ChickenSoundVariant> chickenSoundVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(chickenSoundVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CowVariant> cowVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(cowVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CowSoundVariant> cowSoundVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(cowSoundVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<FrogVariant> frogVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(frogVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<PigVariant> pigVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(pigVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<PigSoundVariant> pigSoundVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(pigSoundVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(zombieNautilusVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Enchantment> enchantment() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(enchantment);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<PaintingVariant> paintingVariant() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(paintingVariant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<JukeboxSong> jukeboxSong() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(jukeboxSong);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Instrument> instrument() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(instrument);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Dialog> dialog() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(dialog);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Timeline> timeline() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(timeline);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<WorldClock> worldClock() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(worldClock);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(enchantmentLevelBasedValues);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(enchantmentValueEffects);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(enchantmentEntityEffects);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(enchantmentLocationEffects);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
