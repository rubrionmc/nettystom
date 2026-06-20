// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.dialog.Dialog;
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
import net.minestom.server.instance.block.banner.BannerPattern;
// Import of a required class
import net.minestom.server.instance.block.jukebox.JukeboxSong;
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
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import net.minestom.server.world.timeline.Timeline;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Consumer;

// Type declaration (class/interface/enum/record)
public class TestRegistries implements Registries {
    // Assigns a value
    public @Nullable DynamicRegistry<ChatType> chatType = null;
    // Assigns a value
    public @Nullable DynamicRegistry<DimensionType> dimensionType = null;
    // Assigns a value
    public @Nullable DynamicRegistry<Biome> biome = null;
    // Assigns a value
    public @Nullable DynamicRegistry<DamageType> damageType = null;
    // Assigns a value
    public @Nullable DynamicRegistry<TrimMaterial> trimMaterial = null;
    // Assigns a value
    public @Nullable DynamicRegistry<TrimPattern> trimPattern = null;
    // Assigns a value
    public @Nullable DynamicRegistry<BannerPattern> bannerPattern = null;
    // Assigns a value
    public @Nullable DynamicRegistry<WolfVariant> wolfVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<Enchantment> enchantment = null;
    // Assigns a value
    public @Nullable DynamicRegistry<PaintingVariant> paintingVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<JukeboxSong> jukeboxSong = null;
    // Assigns a value
    public @Nullable DynamicRegistry<Instrument> instrument = null;
    // Assigns a value
    public @Nullable DynamicRegistry<WolfSoundVariant> wolfSoundVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<CatVariant> catVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<CatSoundVariant> catSoundVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<ChickenVariant> chickenVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<ChickenSoundVariant> chickenSoundVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<CowVariant> cowVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<CowSoundVariant> cowSoundVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<FrogVariant> frogVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<PigVariant> pigVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<PigSoundVariant> pigSoundVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant = null;
    // Assigns a value
    public @Nullable DynamicRegistry<Dialog> dialog = null;
    // Assigns a value
    public @Nullable DynamicRegistry<Timeline> timeline = null;
    // Assigns a value
    public @Nullable DynamicRegistry<WorldClock> worldClock = null;
    // Assigns a value
    public @Nullable DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues = null;
    // Assigns a value
    public @Nullable DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects = null;
    // Assigns a value
    public @Nullable DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects = null;
    // Assigns a value
    public @Nullable DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects = null;

    // Start of a method/block
    public TestRegistries(Consumer<TestRegistries> init) {
        // Calls a method
        init.accept(this);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<ChatType> chatType() {
        // Returns a value to the caller
        return Objects.requireNonNull(chatType);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<DimensionType> dimensionType() {
        // Returns a value to the caller
        return Objects.requireNonNull(dimensionType);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Biome> biome() {
        // Returns a value to the caller
        return Objects.requireNonNull(biome);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<DamageType> damageType() {
        // Returns a value to the caller
        return Objects.requireNonNull(damageType);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<TrimMaterial> trimMaterial() {
        // Returns a value to the caller
        return Objects.requireNonNull(trimMaterial);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<TrimPattern> trimPattern() {
        // Returns a value to the caller
        return Objects.requireNonNull(trimPattern);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<BannerPattern> bannerPattern() {
        // Returns a value to the caller
        return Objects.requireNonNull(bannerPattern);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<WolfVariant> wolfVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(wolfVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<WolfSoundVariant> wolfSoundVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(wolfSoundVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<CatVariant> catVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(catVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<CatSoundVariant> catSoundVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(catSoundVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<ChickenVariant> chickenVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(chickenVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<ChickenSoundVariant> chickenSoundVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(chickenSoundVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<CowVariant> cowVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(cowVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<CowSoundVariant> cowSoundVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(cowSoundVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<FrogVariant> frogVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(frogVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<PigVariant> pigVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(pigVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<PigSoundVariant> pigSoundVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(pigSoundVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(zombieNautilusVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Enchantment> enchantment() {
        // Returns a value to the caller
        return Objects.requireNonNull(enchantment);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<PaintingVariant> paintingVariant() {
        // Returns a value to the caller
        return Objects.requireNonNull(paintingVariant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<JukeboxSong> jukeboxSong() {
        // Returns a value to the caller
        return Objects.requireNonNull(jukeboxSong);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Instrument> instrument() {
        // Returns a value to the caller
        return Objects.requireNonNull(instrument);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Dialog> dialog() {
        // Returns a value to the caller
        return Objects.requireNonNull(dialog);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Timeline> timeline() {
        // Returns a value to the caller
        return Objects.requireNonNull(timeline);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<WorldClock> worldClock() {
        // Returns a value to the caller
        return Objects.requireNonNull(worldClock);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
        // Returns a value to the caller
        return Objects.requireNonNull(enchantmentLevelBasedValues);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
        // Returns a value to the caller
        return Objects.requireNonNull(enchantmentValueEffects);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
        // Returns a value to the caller
        return Objects.requireNonNull(enchantmentEntityEffects);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
        // Returns a value to the caller
        return Objects.requireNonNull(enchantmentLocationEffects);
    // End of a block/expression
    }
// End of a block/expression
}
