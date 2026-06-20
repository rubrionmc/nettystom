// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.component.DataComponents;
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
import net.minestom.server.world.biome.Biome;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import net.minestom.server.world.timeline.Timeline;

// Type declaration (class/interface/enum/record)
final class VanillaRegistries implements Registries {
    // Code statement
    private final DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues;
    // Code statement
    private final DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects;
    // Code statement
    private final DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects;
    // Code statement
    private final DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects;

    // Code statement
    private final DynamicRegistry<ChatType> chatType;
    // Code statement
    private final DynamicRegistry<Dialog> dialog;
    // Code statement
    private final DynamicRegistry<DimensionType> dimensionType;
    // Code statement
    private final DynamicRegistry<Biome> biome;
    // Code statement
    private final DynamicRegistry<DamageType> damageType;
    // Code statement
    private final DynamicRegistry<TrimMaterial> trimMaterial;
    // Code statement
    private final DynamicRegistry<TrimPattern> trimPattern;
    // Code statement
    private final DynamicRegistry<BannerPattern> bannerPattern;
    // Code statement
    private final DynamicRegistry<Enchantment> enchantment;
    // Code statement
    private final DynamicRegistry<PaintingVariant> paintingVariant;
    // Code statement
    private final DynamicRegistry<JukeboxSong> jukeboxSong;
    // Code statement
    private final DynamicRegistry<Instrument> instrument;
    // Code statement
    private final DynamicRegistry<WolfVariant> wolfVariant;
    // Code statement
    private final DynamicRegistry<WolfSoundVariant> wolfSoundVariant;
    // Code statement
    private final DynamicRegistry<CatVariant> catVariant;
    // Code statement
    private final DynamicRegistry<CatSoundVariant> catSoundVariant;
    // Code statement
    private final DynamicRegistry<ChickenVariant> chickenVariant;
    // Code statement
    private final DynamicRegistry<ChickenSoundVariant> chickenSoundVariant;
    // Code statement
    private final DynamicRegistry<CowVariant> cowVariant;
    // Code statement
    private final DynamicRegistry<CowSoundVariant> cowSoundVariant;
    // Code statement
    private final DynamicRegistry<FrogVariant> frogVariant;
    // Code statement
    private final DynamicRegistry<PigVariant> pigVariant;
    // Code statement
    private final DynamicRegistry<PigSoundVariant> pigSoundVariant;
    // Code statement
    private final DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant;
    // Code statement
    private final DynamicRegistry<WorldClock> worldClock;
    // Code statement
    private final DynamicRegistry<Timeline> timeline;

    // Start of a method/block
    VanillaRegistries() {
        // The order of initialization here is relevant, we must load the enchantment util registries before the vanilla data is loaded.
        // Assigns a value
        var ignoredForInit = DataComponents.ITEM_NAME;

        // Access to the current/parent object
        this.enchantmentLevelBasedValues = LevelBasedValue.createDefaultRegistry();
        // Access to the current/parent object
        this.enchantmentValueEffects = ValueEffect.createDefaultRegistry();
        // Access to the current/parent object
        this.enchantmentEntityEffects = EntityEffect.createDefaultRegistry();
        // Access to the current/parent object
        this.enchantmentLocationEffects = LocationEffect.createDefaultRegistry();

        // Access to the current/parent object
        this.chatType = ChatType.createDefaultRegistry();
        // Access to the current/parent object
        this.dialog = Dialog.createDefaultRegistry(this);
        // Access to the current/parent object
        this.biome = Biome.createDefaultRegistry();
        // Access to the current/parent object
        this.damageType = DamageType.createDefaultRegistry();
        // Access to the current/parent object
        this.trimMaterial = TrimMaterial.createDefaultRegistry();
        // Access to the current/parent object
        this.trimPattern = TrimPattern.createDefaultRegistry();
        // Access to the current/parent object
        this.bannerPattern = BannerPattern.createDefaultRegistry();
        // Access to the current/parent object
        this.enchantment = Enchantment.createDefaultRegistry(this);
        // Access to the current/parent object
        this.paintingVariant = PaintingVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.jukeboxSong = JukeboxSong.createDefaultRegistry();
        // Access to the current/parent object
        this.instrument = Instrument.createDefaultRegistry();
        // Access to the current/parent object
        this.wolfVariant = WolfVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.wolfSoundVariant = WolfSoundVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.catVariant = CatVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.catSoundVariant = CatSoundVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.chickenVariant = ChickenVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.chickenSoundVariant = ChickenSoundVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.cowVariant = CowVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.cowSoundVariant = CowSoundVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.frogVariant = FrogVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.pigVariant = PigVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.pigSoundVariant = PigSoundVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.zombieNautilusVariant = ZombieNautilusVariant.createDefaultRegistry();
        // Access to the current/parent object
        this.worldClock = WorldClock.createDefaultRegistry();
        // Access to the current/parent object
        this.timeline = Timeline.createDefaultRegistry(this);
        // Access to the current/parent object
        this.dimensionType = DimensionType.createDefaultRegistry(this); // depends on timelines

        // Quite a hack because materials are a static registry, and can be loaded before but are cyclic on components.
        // So we break the loop and bind them here
        // Loop: repeats a block
        for (var entry: material().values()) {
            // Calls a method
            entry.registry().bindComponents(this);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<ChatType> chatType() {
        // Returns a value to the caller
        return chatType;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<DimensionType> dimensionType() {
        // Returns a value to the caller
        return dimensionType;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Biome> biome() {
        // Returns a value to the caller
        return biome;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<DamageType> damageType() {
        // Returns a value to the caller
        return damageType;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<TrimMaterial> trimMaterial() {
        // Returns a value to the caller
        return trimMaterial;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<TrimPattern> trimPattern() {
        // Returns a value to the caller
        return trimPattern;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<BannerPattern> bannerPattern() {
        // Returns a value to the caller
        return bannerPattern;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Enchantment> enchantment() {
        // Returns a value to the caller
        return enchantment;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<PaintingVariant> paintingVariant() {
        // Returns a value to the caller
        return paintingVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<JukeboxSong> jukeboxSong() {
        // Returns a value to the caller
        return jukeboxSong;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Instrument> instrument() {
        // Returns a value to the caller
        return instrument;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<WolfVariant> wolfVariant() {
        // Returns a value to the caller
        return wolfVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<WolfSoundVariant> wolfSoundVariant() {
        // Returns a value to the caller
        return wolfSoundVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<CatVariant> catVariant() {
        // Returns a value to the caller
        return catVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<CatSoundVariant> catSoundVariant() {
        // Returns a value to the caller
        return catSoundVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<ChickenVariant> chickenVariant() {
        // Returns a value to the caller
        return chickenVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<ChickenSoundVariant> chickenSoundVariant() {
        // Returns a value to the caller
        return chickenSoundVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<CowVariant> cowVariant() {
        // Returns a value to the caller
        return cowVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<CowSoundVariant> cowSoundVariant() {
        // Returns a value to the caller
        return cowSoundVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<FrogVariant> frogVariant() {
        // Returns a value to the caller
        return frogVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<PigVariant> pigVariant() {
        // Returns a value to the caller
        return pigVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<PigSoundVariant> pigSoundVariant() {
        // Returns a value to the caller
        return pigSoundVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant() {
        // Returns a value to the caller
        return zombieNautilusVariant;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Dialog> dialog() {
        // Returns a value to the caller
        return dialog;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<WorldClock> worldClock() {
        // Returns a value to the caller
        return worldClock;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<Timeline> timeline() {
        // Returns a value to the caller
        return timeline;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
        // Returns a value to the caller
        return enchantmentLevelBasedValues;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
        // Returns a value to the caller
        return enchantmentValueEffects;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
        // Returns a value to the caller
        return enchantmentEntityEffects;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
        // Returns a value to the caller
        return enchantmentLocationEffects;
    // End of a block/expression
    }
// End of a block/expression
}
