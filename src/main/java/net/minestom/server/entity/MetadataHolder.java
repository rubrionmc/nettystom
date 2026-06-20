// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;
// Import of a required class
import net.minestom.server.entity.metadata.ambient.BatMeta;
// Import of a required class
import net.minestom.server.entity.metadata.animal.*;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.CatMeta;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.ParrotMeta;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.WolfMeta;
// Import of a required class
import net.minestom.server.entity.metadata.avatar.MannequinMeta;
// Import of a required class
import net.minestom.server.entity.metadata.avatar.PlayerMeta;
// Import of a required class
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
// Import of a required class
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
// Import of a required class
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
// Import of a required class
import net.minestom.server.entity.metadata.flying.GhastMeta;
// Import of a required class
import net.minestom.server.entity.metadata.flying.PhantomMeta;
// Import of a required class
import net.minestom.server.entity.metadata.golem.CopperGolemMeta;
// Import of a required class
import net.minestom.server.entity.metadata.golem.IronGolemMeta;
// Import of a required class
import net.minestom.server.entity.metadata.golem.ShulkerMeta;
// Import of a required class
import net.minestom.server.entity.metadata.golem.SnowGolemMeta;
// Import of a required class
import net.minestom.server.entity.metadata.item.*;
// Import of a required class
import net.minestom.server.entity.metadata.minecart.*;
// Import of a required class
import net.minestom.server.entity.metadata.monster.*;
// Import of a required class
import net.minestom.server.entity.metadata.monster.raider.*;
// Import of a required class
import net.minestom.server.entity.metadata.monster.skeleton.*;
// Import of a required class
import net.minestom.server.entity.metadata.monster.zombie.*;
// Import of a required class
import net.minestom.server.entity.metadata.other.*;
// Import of a required class
import net.minestom.server.entity.metadata.projectile.*;
// Import of a required class
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import of a required class
import net.minestom.server.entity.metadata.villager.WanderingTraderMeta;
// Import of a required class
import net.minestom.server.entity.metadata.water.AxolotlMeta;
// Import of a required class
import net.minestom.server.entity.metadata.water.DolphinMeta;
// Import of a required class
import net.minestom.server.entity.metadata.water.GlowSquidMeta;
// Import of a required class
import net.minestom.server.entity.metadata.water.SquidMeta;
// Import of a required class
import net.minestom.server.entity.metadata.water.fish.*;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.lang.invoke.MethodHandles;
// Import of a required class
import java.lang.invoke.VarHandle;
// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.BiFunction;
// Import of a required class
import java.util.function.Consumer;

// Static import of a member
import static java.util.Map.entry;

// Type declaration (class/interface/enum/record)
public final class MetadataHolder {
    // Code statement
    private static final VarHandle NOTIFIED_CHANGES;

    // Start of a method/block
    static {
        // Exception handling
        try {
            // Calls a method
            NOTIFIED_CHANGES = MethodHandles.lookup().findVarHandle(MetadataHolder.class, "notifyAboutChanges", boolean.class);
        // Start of a method/block
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Throws an exception
            throw new IllegalStateException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    private final Consumer<Map<Integer, Metadata.Entry<?>>> changesListener;
    // Calls a method
    private final Int2ObjectMap<Metadata.Entry<?>> entries = new Int2ObjectOpenHashMap<>();

    // Annotation for the following element
    @SuppressWarnings("FieldMayBeFinal")
    // Assigns a value
    private volatile boolean notifyAboutChanges = true;
    // Calls a method
    private final Map<Integer, Metadata.Entry<?>> notNotifiedChanges = new HashMap<>();

    /**
     * @deprecated Use {@link #MetadataHolder(Consumer)} instead.
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public MetadataHolder(@Nullable Entity entity) {
        // Start of a method/block
        this(entity == null ? _ -> {
        // Code statement
        } : entity::notifyMetadataChanges);
    // End of a block/expression
    }

    // Start of a method/block
    public MetadataHolder(Consumer<Map<Integer, Metadata.Entry<?>>> changesListener) {
        // Access to the current/parent object
        this.changesListener = Objects.requireNonNull(changesListener, "changesListener");
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    public <T extends @UnknownNullability Object> T get(MetadataDef.Entry<T> entry) {
        // Calls a method
        final int id = entry.index();

        // Calls a method
        final Metadata.Entry<?> value = this.entries.get(id);
        // Branch: checks a condition
        if (value == null) return entry.defaultValue();
        // Returns a value to the caller
        return switch (entry) {
            // Multiple branching (switch/case)
            case MetadataDef.Entry.Index<T> _ -> (T) value.value();
            // Multiple branching (switch/case)
            case MetadataDef.Entry.BitMask bitMask -> {
                // Calls a method
                final byte maskValue = (byte) value.value();
                // Calls a method
                yield (T) ((Boolean) getMaskBit(maskValue, bitMask.bitMask()));
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case MetadataDef.Entry.ByteMask byteMask -> {
                // Calls a method
                final byte maskValue = (byte) value.value();
                // Calls a method
                yield (T) ((Byte) getMaskByte(maskValue, byteMask.byteMask(), byteMask.offset()));
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    public <T extends @UnknownNullability Object> void set(MetadataDef.Entry<T> entry, T value) {
        // Calls a method
        final int id = entry.index();

        // Calls a method
        T current = get(entry);

        // If a metadata value is unchanged we should not send it. In particular we need to be careful with
        //  sending bitmasks which will overwrite client-predicted values. See PR 3089 for more info.
        // However, interpolation delay is expected to be sent regularly with the same value to begin
        //  interpolation so we always send it for now.
        // Branch: checks a condition
        if (Objects.equals(current, value) && entry != MetadataDef.Display.INTERPOLATION_DELAY) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Assigns a value
        Metadata.Entry<?> result = switch (entry) {
            // Multiple branching (switch/case)
            case MetadataDef.Entry.Index<T> v -> v.function().apply(value);
            // Multiple branching (switch/case)
            case MetadataDef.Entry.BitMask bitMask -> {
                // Calls a method
                Metadata.Entry<?> currentEntry = this.entries.get(id);
                // Calls a method
                byte maskValue = currentEntry != null ? (byte) currentEntry.value() : 0;
                // Calls a method
                maskValue = setMaskBit(maskValue, bitMask.bitMask(), (Boolean) value);
                // Calls a method
                yield Metadata.Byte(maskValue);
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case MetadataDef.Entry.ByteMask byteMask -> {
                // Calls a method
                Metadata.Entry<?> currentEntry = this.entries.get(id);
                // Calls a method
                byte maskValue = currentEntry != null ? (byte) currentEntry.value() : 0;
                // Calls a method
                maskValue = setMaskByte(maskValue, byteMask.byteMask(), byteMask.offset(), (Byte) value);
                // Calls a method
                yield Metadata.Byte(maskValue);
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Access to the current/parent object
        this.entries.put(id, result);

        // Branch: checks a condition
        if (!this.notifyAboutChanges) {
            // Start of a method/block
            synchronized (this.notNotifiedChanges) {
                // Access to the current/parent object
                this.notNotifiedChanges.put(id, result);
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.changesListener.accept(Map.of(id, result));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private boolean getMaskBit(byte maskValue, byte bit) {
        // Returns a value to the caller
        return (maskValue & bit) == bit;
    // End of a block/expression
    }

    // Start of a method/block
    private byte setMaskBit(byte mask, byte bit, boolean value) {
        // Returns a value to the caller
        return value ? (byte) (mask | bit) : (byte) (mask & ~bit);
    // End of a block/expression
    }

    // Start of a method/block
    private byte getMaskByte(byte data, byte byteMask, int offset) {
        // Returns a value to the caller
        return (byte) ((data & byteMask) >> offset);
    // End of a block/expression
    }

    // Start of a method/block
    private byte setMaskByte(byte data, byte byteMask, int offset, byte newValue) {
        // Returns a value to the caller
        return (byte) ((data & ~byteMask) | ((newValue << offset) & byteMask));
    // End of a block/expression
    }

    // Start of a method/block
    public void setNotifyAboutChanges(boolean notifyAboutChanges) {
        // Branch: checks a condition
        if (!NOTIFIED_CHANGES.compareAndSet(this, !notifyAboutChanges, notifyAboutChanges))
            // Returns a value to the caller
            return;
        // Branch: checks a condition
        if (!notifyAboutChanges) {
            // Ask future metadata changes to be cached
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Code statement
        Map<Integer, Metadata.Entry<?>> entries;
        // Start of a method/block
        synchronized (this.notNotifiedChanges) {
            // Assigns a value
            Map<Integer, Metadata.Entry<?>> awaitingChanges = this.notNotifiedChanges;
            // Branch: checks a condition
            if (awaitingChanges.isEmpty()) return;
            // Calls a method
            entries = Map.copyOf(awaitingChanges);
            // Calls a method
            awaitingChanges.clear();
        // End of a block/expression
        }
        // Access to the current/parent object
        this.changesListener.accept(entries);
    // End of a block/expression
    }

    // Start of a method/block
    public Map<Integer, Metadata.Entry<?>> getEntries() {
        // Returns a value to the caller
        return Map.copyOf(this.entries);
    // End of a block/expression
    }

    // Assigns a value
    static final Map<String, BiFunction<@Nullable Entity, MetadataHolder, ? extends EntityMeta>> ENTITY_META_SUPPLIER = Map.ofEntries(
            // Code statement
            entry("minecraft:acacia_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:acacia_chest_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:allay", AllayMeta::new),
            // Code statement
            entry("minecraft:area_effect_cloud", AreaEffectCloudMeta::new),
            // Code statement
            entry("minecraft:armadillo", ArmadilloMeta::new),
            // Code statement
            entry("minecraft:armor_stand", ArmorStandMeta::new),
            // Code statement
            entry("minecraft:arrow", ArrowMeta::new),
            // Code statement
            entry("minecraft:axolotl", AxolotlMeta::new),
            // Code statement
            entry("minecraft:bamboo_raft", BoatMeta::new),
            // Code statement
            entry("minecraft:bamboo_chest_raft", BoatMeta::new),
            // Code statement
            entry("minecraft:bat", BatMeta::new),
            // Code statement
            entry("minecraft:bee", BeeMeta::new),
            // Code statement
            entry("minecraft:birch_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:birch_chest_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:blaze", BlazeMeta::new),
            // Code statement
            entry("minecraft:block_display", BlockDisplayMeta::new),
            // Code statement
            entry("minecraft:bogged", BoggedMeta::new),
            // Code statement
            entry("minecraft:breeze", BreezeMeta::new),
            // Code statement
            entry("minecraft:breeze_wind_charge", BreezeWindChargeMeta::new),
            // Code statement
            entry("minecraft:camel", CamelMeta::new),
            // Code statement
            entry("minecraft:camel_husk", CamelHuskMeta::new),
            // Code statement
            entry("minecraft:cat", CatMeta::new),
            // Code statement
            entry("minecraft:cave_spider", CaveSpiderMeta::new),
            // Code statement
            entry("minecraft:cherry_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:cherry_chest_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:chicken", ChickenMeta::new),
            // Code statement
            entry("minecraft:cod", CodMeta::new),
            // Code statement
            entry("minecraft:copper_golem", CopperGolemMeta::new),
            // Code statement
            entry("minecraft:cow", CowMeta::new),
            // Code statement
            entry("minecraft:creaking", CreakingMeta::new),
            // Code statement
            entry("minecraft:creeper", CreeperMeta::new),
            // Code statement
            entry("minecraft:dark_oak_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:dark_oak_chest_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:dolphin", DolphinMeta::new),
            // Code statement
            entry("minecraft:donkey", DonkeyMeta::new),
            // Code statement
            entry("minecraft:dragon_fireball", DragonFireballMeta::new),
            // Code statement
            entry("minecraft:drowned", DrownedMeta::new),
            // Code statement
            entry("minecraft:elder_guardian", ElderGuardianMeta::new),
            // Code statement
            entry("minecraft:end_crystal", EndCrystalMeta::new),
            // Code statement
            entry("minecraft:ender_dragon", EnderDragonMeta::new),
            // Code statement
            entry("minecraft:enderman", EndermanMeta::new),
            // Code statement
            entry("minecraft:endermite", EndermiteMeta::new),
            // Code statement
            entry("minecraft:evoker", EvokerMeta::new),
            // Code statement
            entry("minecraft:evoker_fangs", EvokerFangsMeta::new),
            // Code statement
            entry("minecraft:experience_orb", ExperienceOrbMeta::new),
            // Code statement
            entry("minecraft:eye_of_ender", EyeOfEnderMeta::new),
            // Code statement
            entry("minecraft:falling_block", FallingBlockMeta::new),
            // Code statement
            entry("minecraft:fireball", FireballMeta::new),
            // Code statement
            entry("minecraft:firework_rocket", FireworkRocketMeta::new),
            // Code statement
            entry("minecraft:fox", FoxMeta::new),
            // Code statement
            entry("minecraft:frog", FrogMeta::new),
            // Code statement
            entry("minecraft:ghast", GhastMeta::new),
            // Code statement
            entry("minecraft:giant", GiantMeta::new),
            // Code statement
            entry("minecraft:glow_item_frame", GlowItemFrameMeta::new),
            // Code statement
            entry("minecraft:glow_squid", GlowSquidMeta::new),
            // Code statement
            entry("minecraft:goat", GoatMeta::new),
            // Code statement
            entry("minecraft:guardian", GuardianMeta::new),
            // Code statement
            entry("minecraft:happy_ghast", HappyGhastMeta::new),
            // Code statement
            entry("minecraft:hoglin", HoglinMeta::new),
            // Code statement
            entry("minecraft:horse", HorseMeta::new),
            // Code statement
            entry("minecraft:husk", HuskMeta::new),
            // Code statement
            entry("minecraft:illusioner", IllusionerMeta::new),
            // Code statement
            entry("minecraft:interaction", InteractionMeta::new),
            // Code statement
            entry("minecraft:iron_golem", IronGolemMeta::new),
            // Code statement
            entry("minecraft:item", ItemEntityMeta::new),
            // Code statement
            entry("minecraft:item_display", ItemDisplayMeta::new),
            // Code statement
            entry("minecraft:item_frame", ItemFrameMeta::new),
            // Code statement
            entry("minecraft:jungle_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:jungle_chest_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:leash_knot", LeashKnotMeta::new),
            // Code statement
            entry("minecraft:lightning_bolt", LightningBoltMeta::new),
            // Code statement
            entry("minecraft:lingering_potion", LingeringPotionMeta::new),
            // Code statement
            entry("minecraft:llama", LlamaMeta::new),
            // Code statement
            entry("minecraft:llama_spit", LlamaSpitMeta::new),
            // Code statement
            entry("minecraft:magma_cube", MagmaCubeMeta::new),
            // Code statement
            entry("minecraft:mangrove_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:mangrove_chest_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:mannequin", MannequinMeta::new),
            // Code statement
            entry("minecraft:marker", MarkerMeta::new),
            // Code statement
            entry("minecraft:minecart", MinecartMeta::new),
            // Code statement
            entry("minecraft:nautilus", NautilusMeta::new),
            // Code statement
            entry("minecraft:chest_minecart", ChestMinecartMeta::new),
            // Code statement
            entry("minecraft:command_block_minecart", CommandBlockMinecartMeta::new),
            // Code statement
            entry("minecraft:furnace_minecart", FurnaceMinecartMeta::new),
            // Code statement
            entry("minecraft:hopper_minecart", HopperMinecartMeta::new),
            // Code statement
            entry("minecraft:spawner_minecart", SpawnerMinecartMeta::new),
            // Code statement
            entry("minecraft:text_display", TextDisplayMeta::new),
            // Code statement
            entry("minecraft:tnt_minecart", TntMinecartMeta::new),
            // Code statement
            entry("minecraft:mooshroom", MooshroomMeta::new),
            // Code statement
            entry("minecraft:mule", MuleMeta::new),
            // Code statement
            entry("minecraft:oak_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:oak_chest_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:ocelot", OcelotMeta::new),
            // Code statement
            entry("minecraft:ominous_item_spawner", OminousItemSpawnerMeta::new),
            // Code statement
            entry("minecraft:painting", PaintingMeta::new),
            // Code statement
            entry("minecraft:pale_oak_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:pale_oak_chest_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:panda", PandaMeta::new),
            // Code statement
            entry("minecraft:parrot", ParrotMeta::new),
            // Code statement
            entry("minecraft:parched", ParchedMeta::new),
            // Code statement
            entry("minecraft:phantom", PhantomMeta::new),
            // Code statement
            entry("minecraft:pig", PigMeta::new),
            // Code statement
            entry("minecraft:piglin", PiglinMeta::new),
            // Code statement
            entry("minecraft:piglin_brute", PiglinBruteMeta::new),
            // Code statement
            entry("minecraft:pillager", PillagerMeta::new),
            // Code statement
            entry("minecraft:polar_bear", PolarBearMeta::new),
            // Code statement
            entry("minecraft:tnt", PrimedTntMeta::new),
            // Code statement
            entry("minecraft:pufferfish", PufferfishMeta::new),
            // Code statement
            entry("minecraft:rabbit", RabbitMeta::new),
            // Code statement
            entry("minecraft:ravager", RavagerMeta::new),
            // Code statement
            entry("minecraft:salmon", SalmonMeta::new),
            // Code statement
            entry("minecraft:sheep", SheepMeta::new),
            // Code statement
            entry("minecraft:shulker", ShulkerMeta::new),
            // Code statement
            entry("minecraft:shulker_bullet", ShulkerBulletMeta::new),
            // Code statement
            entry("minecraft:silverfish", SilverfishMeta::new),
            // Code statement
            entry("minecraft:skeleton", SkeletonMeta::new),
            // Code statement
            entry("minecraft:skeleton_horse", SkeletonHorseMeta::new),
            // Code statement
            entry("minecraft:slime", SlimeMeta::new),
            // Code statement
            entry("minecraft:small_fireball", SmallFireballMeta::new),
            // Code statement
            entry("minecraft:sniffer", SnifferMeta::new),
            // Code statement
            entry("minecraft:snow_golem", SnowGolemMeta::new),
            // Code statement
            entry("minecraft:snowball", SnowballMeta::new),
            // Code statement
            entry("minecraft:spectral_arrow", SpectralArrowMeta::new),
            // Code statement
            entry("minecraft:spider", SpiderMeta::new),
            // Code statement
            entry("minecraft:splash_potion", SplashPotionMeta::new),
            // Code statement
            entry("minecraft:spruce_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:spruce_chest_boat", BoatMeta::new),
            // Code statement
            entry("minecraft:squid", SquidMeta::new),
            // Code statement
            entry("minecraft:stray", StrayMeta::new),
            // Code statement
            entry("minecraft:strider", StriderMeta::new),
            // Code statement
            entry("minecraft:tadpole", TadpoleMeta::new),
            // Code statement
            entry("minecraft:egg", ThrownEggMeta::new),
            // Code statement
            entry("minecraft:ender_pearl", ThrownEnderPearlMeta::new),
            // Code statement
            entry("minecraft:experience_bottle", ThrownExperienceBottleMeta::new),
            // Code statement
            entry("minecraft:potion", SplashPotionMeta::new),
            // Code statement
            entry("minecraft:trident", ThrownTridentMeta::new),
            // Code statement
            entry("minecraft:trader_llama", TraderLlamaMeta::new),
            // Code statement
            entry("minecraft:tropical_fish", TropicalFishMeta::new),
            // Code statement
            entry("minecraft:turtle", TurtleMeta::new),
            // Code statement
            entry("minecraft:vex", VexMeta::new),
            // Code statement
            entry("minecraft:villager", VillagerMeta::new),
            // Code statement
            entry("minecraft:vindicator", VindicatorMeta::new),
            // Code statement
            entry("minecraft:wandering_trader", WanderingTraderMeta::new),
            // Code statement
            entry("minecraft:warden", WardenMeta::new),
            // Code statement
            entry("minecraft:wind_charge", WindChargeMeta::new),
            // Code statement
            entry("minecraft:witch", WitchMeta::new),
            // Code statement
            entry("minecraft:wither", WitherMeta::new),
            // Code statement
            entry("minecraft:wither_skeleton", WitherSkeletonMeta::new),
            // Code statement
            entry("minecraft:wither_skull", WitherSkullMeta::new),
            // Code statement
            entry("minecraft:wolf", WolfMeta::new),
            // Code statement
            entry("minecraft:zoglin", ZoglinMeta::new),
            // Code statement
            entry("minecraft:zombie", ZombieMeta::new),
            // Code statement
            entry("minecraft:zombie_horse", ZombieHorseMeta::new),
            // Code statement
            entry("minecraft:zombie_nautilus", ZombieNautilusMeta::new),
            // Code statement
            entry("minecraft:zombie_villager", ZombieVillagerMeta::new),
            // Code statement
            entry("minecraft:zombified_piglin", ZombifiedPiglinMeta::new),
            // Code statement
            entry("minecraft:player", PlayerMeta::new),
            // Code statement
            entry("minecraft:fishing_bobber", FishingHookMeta::new)
    // End of a block/expression
    );

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static EntityMeta createMeta(EntityType entityType, @Nullable Entity entity, MetadataHolder metadata) {
        // Returns a value to the caller
        return ENTITY_META_SUPPLIER.get(entityType.name()).apply(entity, metadata);
    // End of a block/expression
    }
// End of a block/expression
}
