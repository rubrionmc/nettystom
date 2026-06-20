// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
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
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;
// Import d'une classe nécessaire
import net.minestom.server.world.timeline.Timeline;

// Déclaration de type (classe/interface/enum/record)
final class VanillaRegistries implements Registries {
    // Instruction de code
    private final DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues;
    // Instruction de code
    private final DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects;
    // Instruction de code
    private final DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects;
    // Instruction de code
    private final DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects;

    // Instruction de code
    private final DynamicRegistry<ChatType> chatType;
    // Instruction de code
    private final DynamicRegistry<Dialog> dialog;
    // Instruction de code
    private final DynamicRegistry<DimensionType> dimensionType;
    // Instruction de code
    private final DynamicRegistry<Biome> biome;
    // Instruction de code
    private final DynamicRegistry<DamageType> damageType;
    // Instruction de code
    private final DynamicRegistry<TrimMaterial> trimMaterial;
    // Instruction de code
    private final DynamicRegistry<TrimPattern> trimPattern;
    // Instruction de code
    private final DynamicRegistry<BannerPattern> bannerPattern;
    // Instruction de code
    private final DynamicRegistry<Enchantment> enchantment;
    // Instruction de code
    private final DynamicRegistry<PaintingVariant> paintingVariant;
    // Instruction de code
    private final DynamicRegistry<JukeboxSong> jukeboxSong;
    // Instruction de code
    private final DynamicRegistry<Instrument> instrument;
    // Instruction de code
    private final DynamicRegistry<WolfVariant> wolfVariant;
    // Instruction de code
    private final DynamicRegistry<WolfSoundVariant> wolfSoundVariant;
    // Instruction de code
    private final DynamicRegistry<CatVariant> catVariant;
    // Instruction de code
    private final DynamicRegistry<CatSoundVariant> catSoundVariant;
    // Instruction de code
    private final DynamicRegistry<ChickenVariant> chickenVariant;
    // Instruction de code
    private final DynamicRegistry<ChickenSoundVariant> chickenSoundVariant;
    // Instruction de code
    private final DynamicRegistry<CowVariant> cowVariant;
    // Instruction de code
    private final DynamicRegistry<CowSoundVariant> cowSoundVariant;
    // Instruction de code
    private final DynamicRegistry<FrogVariant> frogVariant;
    // Instruction de code
    private final DynamicRegistry<PigVariant> pigVariant;
    // Instruction de code
    private final DynamicRegistry<PigSoundVariant> pigSoundVariant;
    // Instruction de code
    private final DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant;
    // Instruction de code
    private final DynamicRegistry<WorldClock> worldClock;
    // Instruction de code
    private final DynamicRegistry<Timeline> timeline;

    // Début d'une méthode/d'un bloc
    VanillaRegistries() {
        // The order of initialization here is relevant, we must load the enchantment util registries before the vanilla data is loaded.
        // Affecte une valeur
        var ignoredForInit = DataComponents.ITEM_NAME;

        // Accès à l'objet courant/parent
        this.enchantmentLevelBasedValues = LevelBasedValue.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.enchantmentValueEffects = ValueEffect.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.enchantmentEntityEffects = EntityEffect.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.enchantmentLocationEffects = LocationEffect.createDefaultRegistry();

        // Accès à l'objet courant/parent
        this.chatType = ChatType.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.dialog = Dialog.createDefaultRegistry(this);
        // Accès à l'objet courant/parent
        this.biome = Biome.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.damageType = DamageType.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.trimMaterial = TrimMaterial.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.trimPattern = TrimPattern.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.bannerPattern = BannerPattern.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.enchantment = Enchantment.createDefaultRegistry(this);
        // Accès à l'objet courant/parent
        this.paintingVariant = PaintingVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.jukeboxSong = JukeboxSong.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.instrument = Instrument.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.wolfVariant = WolfVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.wolfSoundVariant = WolfSoundVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.catVariant = CatVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.catSoundVariant = CatSoundVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.chickenVariant = ChickenVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.chickenSoundVariant = ChickenSoundVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.cowVariant = CowVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.cowSoundVariant = CowSoundVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.frogVariant = FrogVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.pigVariant = PigVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.pigSoundVariant = PigSoundVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.zombieNautilusVariant = ZombieNautilusVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.worldClock = WorldClock.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.timeline = Timeline.createDefaultRegistry(this);
        // Accès à l'objet courant/parent
        this.dimensionType = DimensionType.createDefaultRegistry(this); // depends on timelines

        // Quite a hack because materials are a static registry, and can be loaded before but are cyclic on components.
        // So we break the loop and bind them here
        // Boucle : répète un bloc
        for (var entry: material().values()) {
            // Appelle une méthode
            entry.registry().bindComponents(this);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ChatType> chatType() {
        // Renvoie une valeur à l'appelant
        return chatType;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<DimensionType> dimensionType() {
        // Renvoie une valeur à l'appelant
        return dimensionType;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Biome> biome() {
        // Renvoie une valeur à l'appelant
        return biome;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<DamageType> damageType() {
        // Renvoie une valeur à l'appelant
        return damageType;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<TrimMaterial> trimMaterial() {
        // Renvoie une valeur à l'appelant
        return trimMaterial;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<TrimPattern> trimPattern() {
        // Renvoie une valeur à l'appelant
        return trimPattern;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<BannerPattern> bannerPattern() {
        // Renvoie une valeur à l'appelant
        return bannerPattern;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Enchantment> enchantment() {
        // Renvoie une valeur à l'appelant
        return enchantment;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<PaintingVariant> paintingVariant() {
        // Renvoie une valeur à l'appelant
        return paintingVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<JukeboxSong> jukeboxSong() {
        // Renvoie une valeur à l'appelant
        return jukeboxSong;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Instrument> instrument() {
        // Renvoie une valeur à l'appelant
        return instrument;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<WolfVariant> wolfVariant() {
        // Renvoie une valeur à l'appelant
        return wolfVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<WolfSoundVariant> wolfSoundVariant() {
        // Renvoie une valeur à l'appelant
        return wolfSoundVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CatVariant> catVariant() {
        // Renvoie une valeur à l'appelant
        return catVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CatSoundVariant> catSoundVariant() {
        // Renvoie une valeur à l'appelant
        return catSoundVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ChickenVariant> chickenVariant() {
        // Renvoie une valeur à l'appelant
        return chickenVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ChickenSoundVariant> chickenSoundVariant() {
        // Renvoie une valeur à l'appelant
        return chickenSoundVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CowVariant> cowVariant() {
        // Renvoie une valeur à l'appelant
        return cowVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CowSoundVariant> cowSoundVariant() {
        // Renvoie une valeur à l'appelant
        return cowSoundVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<FrogVariant> frogVariant() {
        // Renvoie une valeur à l'appelant
        return frogVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<PigVariant> pigVariant() {
        // Renvoie une valeur à l'appelant
        return pigVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<PigSoundVariant> pigSoundVariant() {
        // Renvoie une valeur à l'appelant
        return pigSoundVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant() {
        // Renvoie une valeur à l'appelant
        return zombieNautilusVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Dialog> dialog() {
        // Renvoie une valeur à l'appelant
        return dialog;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<WorldClock> worldClock() {
        // Renvoie une valeur à l'appelant
        return worldClock;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Timeline> timeline() {
        // Renvoie une valeur à l'appelant
        return timeline;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
        // Renvoie une valeur à l'appelant
        return enchantmentLevelBasedValues;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
        // Renvoie une valeur à l'appelant
        return enchantmentValueEffects;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
        // Renvoie une valeur à l'appelant
        return enchantmentEntityEffects;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
        // Renvoie une valeur à l'appelant
        return enchantmentLocationEffects;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
