// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.ambient.BatMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.CatMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.ParrotMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.avatar.MannequinMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.avatar.PlayerMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.flying.GhastMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.flying.PhantomMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.golem.CopperGolemMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.golem.IronGolemMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.golem.ShulkerMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.golem.SnowGolemMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.item.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.minecart.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.monster.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.monster.raider.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.monster.skeleton.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.monster.zombie.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.projectile.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.villager.WanderingTraderMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.water.AxolotlMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.water.DolphinMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.water.GlowSquidMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.water.SquidMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.water.fish.*;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.lang.invoke.MethodHandles;
// Import d'une classe nécessaire
import java.lang.invoke.VarHandle;
// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.BiFunction;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Import statique d'un membre
import static java.util.Map.entry;

// Déclaration de type (classe/interface/enum/record)
public final class MetadataHolder {
    // Instruction de code
    private static final VarHandle NOTIFIED_CHANGES;

    // Début d'une méthode/d'un bloc
    static {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            NOTIFIED_CHANGES = MethodHandles.lookup().findVarHandle(MetadataHolder.class, "notifyAboutChanges", boolean.class);
        // Début d'une méthode/d'un bloc
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Lève une exception
            throw new IllegalStateException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private final Consumer<Map<Integer, Metadata.Entry<?>>> changesListener;
    // Appelle une méthode
    private final Int2ObjectMap<Metadata.Entry<?>> entries = new Int2ObjectOpenHashMap<>();

    // Annotation pour l'élément suivant
    @SuppressWarnings("FieldMayBeFinal")
    // Affecte une valeur
    private volatile boolean notifyAboutChanges = true;
    // Appelle une méthode
    private final Map<Integer, Metadata.Entry<?>> notNotifiedChanges = new HashMap<>();

    /**
     * @deprecated Use {@link #MetadataHolder(Consumer)} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    public MetadataHolder(@Nullable Entity entity) {
        // Début d'une méthode/d'un bloc
        this(entity == null ? _ -> {
        // Instruction de code
        } : entity::notifyMetadataChanges);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public MetadataHolder(Consumer<Map<Integer, Metadata.Entry<?>>> changesListener) {
        // Accès à l'objet courant/parent
        this.changesListener = Objects.requireNonNull(changesListener, "changesListener");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    public <T extends @UnknownNullability Object> T get(MetadataDef.Entry<T> entry) {
        // Appelle une méthode
        final int id = entry.index();

        // Appelle une méthode
        final Metadata.Entry<?> value = this.entries.get(id);
        // Embranchement : vérifie une condition
        if (value == null) return entry.defaultValue();
        // Renvoie une valeur à l'appelant
        return switch (entry) {
            // Embranchement multiple (switch/case)
            case MetadataDef.Entry.Index<T> _ -> (T) value.value();
            // Embranchement multiple (switch/case)
            case MetadataDef.Entry.BitMask bitMask -> {
                // Appelle une méthode
                final byte maskValue = (byte) value.value();
                // Appelle une méthode
                yield (T) ((Boolean) getMaskBit(maskValue, bitMask.bitMask()));
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case MetadataDef.Entry.ByteMask byteMask -> {
                // Appelle une méthode
                final byte maskValue = (byte) value.value();
                // Appelle une méthode
                yield (T) ((Byte) getMaskByte(maskValue, byteMask.byteMask(), byteMask.offset()));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <T extends @UnknownNullability Object> void set(MetadataDef.Entry<T> entry, T value) {
        // Appelle une méthode
        final int id = entry.index();

        // Appelle une méthode
        T current = get(entry);

        // If a metadata value is unchanged we should not send it. In particular we need to be careful with
        //  sending bitmasks which will overwrite client-predicted values. See PR 3089 for more info.
        // However, interpolation delay is expected to be sent regularly with the same value to begin
        //  interpolation so we always send it for now.
        // Embranchement : vérifie une condition
        if (Objects.equals(current, value) && entry != MetadataDef.Display.INTERPOLATION_DELAY) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        Metadata.Entry<?> result = switch (entry) {
            // Embranchement multiple (switch/case)
            case MetadataDef.Entry.Index<T> v -> v.function().apply(value);
            // Embranchement multiple (switch/case)
            case MetadataDef.Entry.BitMask bitMask -> {
                // Appelle une méthode
                Metadata.Entry<?> currentEntry = this.entries.get(id);
                // Appelle une méthode
                byte maskValue = currentEntry != null ? (byte) currentEntry.value() : 0;
                // Appelle une méthode
                maskValue = setMaskBit(maskValue, bitMask.bitMask(), (Boolean) value);
                // Appelle une méthode
                yield Metadata.Byte(maskValue);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case MetadataDef.Entry.ByteMask byteMask -> {
                // Appelle une méthode
                Metadata.Entry<?> currentEntry = this.entries.get(id);
                // Appelle une méthode
                byte maskValue = currentEntry != null ? (byte) currentEntry.value() : 0;
                // Appelle une méthode
                maskValue = setMaskByte(maskValue, byteMask.byteMask(), byteMask.offset(), (Byte) value);
                // Appelle une méthode
                yield Metadata.Byte(maskValue);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Accès à l'objet courant/parent
        this.entries.put(id, result);

        // Embranchement : vérifie une condition
        if (!this.notifyAboutChanges) {
            // Début d'une méthode/d'un bloc
            synchronized (this.notNotifiedChanges) {
                // Accès à l'objet courant/parent
                this.notNotifiedChanges.put(id, result);
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.changesListener.accept(Map.of(id, result));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private boolean getMaskBit(byte maskValue, byte bit) {
        // Renvoie une valeur à l'appelant
        return (maskValue & bit) == bit;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private byte setMaskBit(byte mask, byte bit, boolean value) {
        // Renvoie une valeur à l'appelant
        return value ? (byte) (mask | bit) : (byte) (mask & ~bit);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private byte getMaskByte(byte data, byte byteMask, int offset) {
        // Renvoie une valeur à l'appelant
        return (byte) ((data & byteMask) >> offset);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private byte setMaskByte(byte data, byte byteMask, int offset, byte newValue) {
        // Renvoie une valeur à l'appelant
        return (byte) ((data & ~byteMask) | ((newValue << offset) & byteMask));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setNotifyAboutChanges(boolean notifyAboutChanges) {
        // Embranchement : vérifie une condition
        if (!NOTIFIED_CHANGES.compareAndSet(this, !notifyAboutChanges, notifyAboutChanges))
            // Renvoie une valeur à l'appelant
            return;
        // Embranchement : vérifie une condition
        if (!notifyAboutChanges) {
            // Ask future metadata changes to be cached
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Instruction de code
        Map<Integer, Metadata.Entry<?>> entries;
        // Début d'une méthode/d'un bloc
        synchronized (this.notNotifiedChanges) {
            // Affecte une valeur
            Map<Integer, Metadata.Entry<?>> awaitingChanges = this.notNotifiedChanges;
            // Embranchement : vérifie une condition
            if (awaitingChanges.isEmpty()) return;
            // Appelle une méthode
            entries = Map.copyOf(awaitingChanges);
            // Appelle une méthode
            awaitingChanges.clear();
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.changesListener.accept(entries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Map<Integer, Metadata.Entry<?>> getEntries() {
        // Renvoie une valeur à l'appelant
        return Map.copyOf(this.entries);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    static final Map<String, BiFunction<@Nullable Entity, MetadataHolder, ? extends EntityMeta>> ENTITY_META_SUPPLIER = Map.ofEntries(
            // Instruction de code
            entry("minecraft:acacia_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:acacia_chest_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:allay", AllayMeta::new),
            // Instruction de code
            entry("minecraft:area_effect_cloud", AreaEffectCloudMeta::new),
            // Instruction de code
            entry("minecraft:armadillo", ArmadilloMeta::new),
            // Instruction de code
            entry("minecraft:armor_stand", ArmorStandMeta::new),
            // Instruction de code
            entry("minecraft:arrow", ArrowMeta::new),
            // Instruction de code
            entry("minecraft:axolotl", AxolotlMeta::new),
            // Instruction de code
            entry("minecraft:bamboo_raft", BoatMeta::new),
            // Instruction de code
            entry("minecraft:bamboo_chest_raft", BoatMeta::new),
            // Instruction de code
            entry("minecraft:bat", BatMeta::new),
            // Instruction de code
            entry("minecraft:bee", BeeMeta::new),
            // Instruction de code
            entry("minecraft:birch_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:birch_chest_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:blaze", BlazeMeta::new),
            // Instruction de code
            entry("minecraft:block_display", BlockDisplayMeta::new),
            // Instruction de code
            entry("minecraft:bogged", BoggedMeta::new),
            // Instruction de code
            entry("minecraft:breeze", BreezeMeta::new),
            // Instruction de code
            entry("minecraft:breeze_wind_charge", BreezeWindChargeMeta::new),
            // Instruction de code
            entry("minecraft:camel", CamelMeta::new),
            // Instruction de code
            entry("minecraft:camel_husk", CamelHuskMeta::new),
            // Instruction de code
            entry("minecraft:cat", CatMeta::new),
            // Instruction de code
            entry("minecraft:cave_spider", CaveSpiderMeta::new),
            // Instruction de code
            entry("minecraft:cherry_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:cherry_chest_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:chicken", ChickenMeta::new),
            // Instruction de code
            entry("minecraft:cod", CodMeta::new),
            // Instruction de code
            entry("minecraft:copper_golem", CopperGolemMeta::new),
            // Instruction de code
            entry("minecraft:cow", CowMeta::new),
            // Instruction de code
            entry("minecraft:creaking", CreakingMeta::new),
            // Instruction de code
            entry("minecraft:creeper", CreeperMeta::new),
            // Instruction de code
            entry("minecraft:dark_oak_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:dark_oak_chest_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:dolphin", DolphinMeta::new),
            // Instruction de code
            entry("minecraft:donkey", DonkeyMeta::new),
            // Instruction de code
            entry("minecraft:dragon_fireball", DragonFireballMeta::new),
            // Instruction de code
            entry("minecraft:drowned", DrownedMeta::new),
            // Instruction de code
            entry("minecraft:elder_guardian", ElderGuardianMeta::new),
            // Instruction de code
            entry("minecraft:end_crystal", EndCrystalMeta::new),
            // Instruction de code
            entry("minecraft:ender_dragon", EnderDragonMeta::new),
            // Instruction de code
            entry("minecraft:enderman", EndermanMeta::new),
            // Instruction de code
            entry("minecraft:endermite", EndermiteMeta::new),
            // Instruction de code
            entry("minecraft:evoker", EvokerMeta::new),
            // Instruction de code
            entry("minecraft:evoker_fangs", EvokerFangsMeta::new),
            // Instruction de code
            entry("minecraft:experience_orb", ExperienceOrbMeta::new),
            // Instruction de code
            entry("minecraft:eye_of_ender", EyeOfEnderMeta::new),
            // Instruction de code
            entry("minecraft:falling_block", FallingBlockMeta::new),
            // Instruction de code
            entry("minecraft:fireball", FireballMeta::new),
            // Instruction de code
            entry("minecraft:firework_rocket", FireworkRocketMeta::new),
            // Instruction de code
            entry("minecraft:fox", FoxMeta::new),
            // Instruction de code
            entry("minecraft:frog", FrogMeta::new),
            // Instruction de code
            entry("minecraft:ghast", GhastMeta::new),
            // Instruction de code
            entry("minecraft:giant", GiantMeta::new),
            // Instruction de code
            entry("minecraft:glow_item_frame", GlowItemFrameMeta::new),
            // Instruction de code
            entry("minecraft:glow_squid", GlowSquidMeta::new),
            // Instruction de code
            entry("minecraft:goat", GoatMeta::new),
            // Instruction de code
            entry("minecraft:guardian", GuardianMeta::new),
            // Instruction de code
            entry("minecraft:happy_ghast", HappyGhastMeta::new),
            // Instruction de code
            entry("minecraft:hoglin", HoglinMeta::new),
            // Instruction de code
            entry("minecraft:horse", HorseMeta::new),
            // Instruction de code
            entry("minecraft:husk", HuskMeta::new),
            // Instruction de code
            entry("minecraft:illusioner", IllusionerMeta::new),
            // Instruction de code
            entry("minecraft:interaction", InteractionMeta::new),
            // Instruction de code
            entry("minecraft:iron_golem", IronGolemMeta::new),
            // Instruction de code
            entry("minecraft:item", ItemEntityMeta::new),
            // Instruction de code
            entry("minecraft:item_display", ItemDisplayMeta::new),
            // Instruction de code
            entry("minecraft:item_frame", ItemFrameMeta::new),
            // Instruction de code
            entry("minecraft:jungle_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:jungle_chest_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:leash_knot", LeashKnotMeta::new),
            // Instruction de code
            entry("minecraft:lightning_bolt", LightningBoltMeta::new),
            // Instruction de code
            entry("minecraft:lingering_potion", LingeringPotionMeta::new),
            // Instruction de code
            entry("minecraft:llama", LlamaMeta::new),
            // Instruction de code
            entry("minecraft:llama_spit", LlamaSpitMeta::new),
            // Instruction de code
            entry("minecraft:magma_cube", MagmaCubeMeta::new),
            // Instruction de code
            entry("minecraft:mangrove_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:mangrove_chest_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:mannequin", MannequinMeta::new),
            // Instruction de code
            entry("minecraft:marker", MarkerMeta::new),
            // Instruction de code
            entry("minecraft:minecart", MinecartMeta::new),
            // Instruction de code
            entry("minecraft:nautilus", NautilusMeta::new),
            // Instruction de code
            entry("minecraft:chest_minecart", ChestMinecartMeta::new),
            // Instruction de code
            entry("minecraft:command_block_minecart", CommandBlockMinecartMeta::new),
            // Instruction de code
            entry("minecraft:furnace_minecart", FurnaceMinecartMeta::new),
            // Instruction de code
            entry("minecraft:hopper_minecart", HopperMinecartMeta::new),
            // Instruction de code
            entry("minecraft:spawner_minecart", SpawnerMinecartMeta::new),
            // Instruction de code
            entry("minecraft:text_display", TextDisplayMeta::new),
            // Instruction de code
            entry("minecraft:tnt_minecart", TntMinecartMeta::new),
            // Instruction de code
            entry("minecraft:mooshroom", MooshroomMeta::new),
            // Instruction de code
            entry("minecraft:mule", MuleMeta::new),
            // Instruction de code
            entry("minecraft:oak_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:oak_chest_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:ocelot", OcelotMeta::new),
            // Instruction de code
            entry("minecraft:ominous_item_spawner", OminousItemSpawnerMeta::new),
            // Instruction de code
            entry("minecraft:painting", PaintingMeta::new),
            // Instruction de code
            entry("minecraft:pale_oak_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:pale_oak_chest_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:panda", PandaMeta::new),
            // Instruction de code
            entry("minecraft:parrot", ParrotMeta::new),
            // Instruction de code
            entry("minecraft:parched", ParchedMeta::new),
            // Instruction de code
            entry("minecraft:phantom", PhantomMeta::new),
            // Instruction de code
            entry("minecraft:pig", PigMeta::new),
            // Instruction de code
            entry("minecraft:piglin", PiglinMeta::new),
            // Instruction de code
            entry("minecraft:piglin_brute", PiglinBruteMeta::new),
            // Instruction de code
            entry("minecraft:pillager", PillagerMeta::new),
            // Instruction de code
            entry("minecraft:polar_bear", PolarBearMeta::new),
            // Instruction de code
            entry("minecraft:tnt", PrimedTntMeta::new),
            // Instruction de code
            entry("minecraft:pufferfish", PufferfishMeta::new),
            // Instruction de code
            entry("minecraft:rabbit", RabbitMeta::new),
            // Instruction de code
            entry("minecraft:ravager", RavagerMeta::new),
            // Instruction de code
            entry("minecraft:salmon", SalmonMeta::new),
            // Instruction de code
            entry("minecraft:sheep", SheepMeta::new),
            // Instruction de code
            entry("minecraft:shulker", ShulkerMeta::new),
            // Instruction de code
            entry("minecraft:shulker_bullet", ShulkerBulletMeta::new),
            // Instruction de code
            entry("minecraft:silverfish", SilverfishMeta::new),
            // Instruction de code
            entry("minecraft:skeleton", SkeletonMeta::new),
            // Instruction de code
            entry("minecraft:skeleton_horse", SkeletonHorseMeta::new),
            // Instruction de code
            entry("minecraft:slime", SlimeMeta::new),
            // Instruction de code
            entry("minecraft:small_fireball", SmallFireballMeta::new),
            // Instruction de code
            entry("minecraft:sniffer", SnifferMeta::new),
            // Instruction de code
            entry("minecraft:snow_golem", SnowGolemMeta::new),
            // Instruction de code
            entry("minecraft:snowball", SnowballMeta::new),
            // Instruction de code
            entry("minecraft:spectral_arrow", SpectralArrowMeta::new),
            // Instruction de code
            entry("minecraft:spider", SpiderMeta::new),
            // Instruction de code
            entry("minecraft:splash_potion", SplashPotionMeta::new),
            // Instruction de code
            entry("minecraft:spruce_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:spruce_chest_boat", BoatMeta::new),
            // Instruction de code
            entry("minecraft:squid", SquidMeta::new),
            // Instruction de code
            entry("minecraft:stray", StrayMeta::new),
            // Instruction de code
            entry("minecraft:strider", StriderMeta::new),
            // Instruction de code
            entry("minecraft:tadpole", TadpoleMeta::new),
            // Instruction de code
            entry("minecraft:egg", ThrownEggMeta::new),
            // Instruction de code
            entry("minecraft:ender_pearl", ThrownEnderPearlMeta::new),
            // Instruction de code
            entry("minecraft:experience_bottle", ThrownExperienceBottleMeta::new),
            // Instruction de code
            entry("minecraft:potion", SplashPotionMeta::new),
            // Instruction de code
            entry("minecraft:trident", ThrownTridentMeta::new),
            // Instruction de code
            entry("minecraft:trader_llama", TraderLlamaMeta::new),
            // Instruction de code
            entry("minecraft:tropical_fish", TropicalFishMeta::new),
            // Instruction de code
            entry("minecraft:turtle", TurtleMeta::new),
            // Instruction de code
            entry("minecraft:vex", VexMeta::new),
            // Instruction de code
            entry("minecraft:villager", VillagerMeta::new),
            // Instruction de code
            entry("minecraft:vindicator", VindicatorMeta::new),
            // Instruction de code
            entry("minecraft:wandering_trader", WanderingTraderMeta::new),
            // Instruction de code
            entry("minecraft:warden", WardenMeta::new),
            // Instruction de code
            entry("minecraft:wind_charge", WindChargeMeta::new),
            // Instruction de code
            entry("minecraft:witch", WitchMeta::new),
            // Instruction de code
            entry("minecraft:wither", WitherMeta::new),
            // Instruction de code
            entry("minecraft:wither_skeleton", WitherSkeletonMeta::new),
            // Instruction de code
            entry("minecraft:wither_skull", WitherSkullMeta::new),
            // Instruction de code
            entry("minecraft:wolf", WolfMeta::new),
            // Instruction de code
            entry("minecraft:zoglin", ZoglinMeta::new),
            // Instruction de code
            entry("minecraft:zombie", ZombieMeta::new),
            // Instruction de code
            entry("minecraft:zombie_horse", ZombieHorseMeta::new),
            // Instruction de code
            entry("minecraft:zombie_nautilus", ZombieNautilusMeta::new),
            // Instruction de code
            entry("minecraft:zombie_villager", ZombieVillagerMeta::new),
            // Instruction de code
            entry("minecraft:zombified_piglin", ZombifiedPiglinMeta::new),
            // Instruction de code
            entry("minecraft:player", PlayerMeta::new),
            // Instruction de code
            entry("minecraft:fishing_bobber", FishingHookMeta::new)
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static EntityMeta createMeta(EntityType entityType, @Nullable Entity entity, MetadataHolder metadata) {
        // Renvoie une valeur à l'appelant
        return ENTITY_META_SUPPLIER.get(entityType.name()).apply(entity, metadata);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
