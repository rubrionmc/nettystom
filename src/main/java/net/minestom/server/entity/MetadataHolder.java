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
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
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
    private final @Nullable Entity entity;
    // Affecte une valeur
    private final Int2ObjectMap<Metadata.Entry<?>> entries = new Int2ObjectOpenHashMap<>();

    // Annotation pour l'élément suivant
    @SuppressWarnings("FieldMayBeFinal")
    // Affecte une valeur
    private volatile boolean notifyAboutChanges = true;
    // Affecte une valeur
    private final Map<Integer, Metadata.Entry<?>> notNotifiedChanges = new HashMap<>();

    // Début d'une méthode/d'un bloc
    public MetadataHolder(@Nullable Entity entity) {
        // Accès à l'objet courant/parent
        this.entity = entity;
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
        // Affecte une valeur
        final Entity entity = this.entity;
        // Embranchement : vérifie une condition
        if (entity != null && entity.isActive()) {
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
                // Appelle une méthode
                entity.sendPacketToViewersAndSelf(new EntityMetaDataPacket(entity.getEntityId(), Map.of(id, result)));
            // Fin d'un bloc/d'une expression
            }
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
        // Affecte une valeur
        final Entity entity = this.entity;
        // Embranchement : vérifie une condition
        if (entity == null || !entity.isActive()) return;
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
        // Appelle une méthode
        entity.sendPacketToViewersAndSelf(new EntityMetaDataPacket(entity.getEntityId(), entries));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Map<Integer, Metadata.Entry<?>> getEntries() {
        // Renvoie une valeur à l'appelant
        return Map.copyOf(this.entries);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    static final Map<String, BiFunction<@Nullable Entity, MetadataHolder, EntityMeta>> ENTITY_META_SUPPLIER = createMetaMap();

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    public static EntityMeta createMeta(
            // Instruction de code
            EntityType entityType,
            // Annotation pour l'élément suivant
            @Nullable Entity entity,
            // Instruction de code
            MetadataHolder metadata
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return ENTITY_META_SUPPLIER.get(entityType.name()).apply(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Map<String, BiFunction<@Nullable Entity, MetadataHolder, EntityMeta>> createMetaMap() {
        // Affecte une valeur
        final Map<String, BiFunction<Entity, MetadataHolder, EntityMeta>> map = new HashMap<>();
        // Appelle une méthode
        map.put("minecraft:acacia_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:acacia_chest_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:allay", AllayMeta::new);
        // Appelle une méthode
        map.put("minecraft:area_effect_cloud", AreaEffectCloudMeta::new);
        // Appelle une méthode
        map.put("minecraft:armadillo", ArmadilloMeta::new);
        // Appelle une méthode
        map.put("minecraft:armor_stand", ArmorStandMeta::new);
        // Appelle une méthode
        map.put("minecraft:arrow", ArrowMeta::new);
        // Appelle une méthode
        map.put("minecraft:axolotl", AxolotlMeta::new);
        // Appelle une méthode
        map.put("minecraft:bamboo_raft", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:bamboo_chest_raft", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:bat", BatMeta::new);
        // Appelle une méthode
        map.put("minecraft:bee", BeeMeta::new);
        // Appelle une méthode
        map.put("minecraft:birch_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:birch_chest_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:blaze", BlazeMeta::new);
        // Appelle une méthode
        map.put("minecraft:block_display", BlockDisplayMeta::new);
        // Appelle une méthode
        map.put("minecraft:bogged", BoggedMeta::new);
        // Appelle une méthode
        map.put("minecraft:breeze", BreezeMeta::new);
        // Appelle une méthode
        map.put("minecraft:breeze_wind_charge", BreezeWindChargeMeta::new);
        // Appelle une méthode
        map.put("minecraft:camel", CamelMeta::new);
        // Appelle une méthode
        map.put("minecraft:camel_husk", CamelHuskMeta::new);
        // Appelle une méthode
        map.put("minecraft:cat", CatMeta::new);
        // Appelle une méthode
        map.put("minecraft:cave_spider", CaveSpiderMeta::new);
        // Appelle une méthode
        map.put("minecraft:cherry_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:cherry_chest_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:chicken", ChickenMeta::new);
        // Appelle une méthode
        map.put("minecraft:cod", CodMeta::new);
        // Appelle une méthode
        map.put("minecraft:copper_golem", CopperGolemMeta::new);
        // Appelle une méthode
        map.put("minecraft:cow", CowMeta::new);
        // Appelle une méthode
        map.put("minecraft:creaking", CreakingMeta::new);
        // Appelle une méthode
        map.put("minecraft:creeper", CreeperMeta::new);
        // Appelle une méthode
        map.put("minecraft:dark_oak_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:dark_oak_chest_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:dolphin", DolphinMeta::new);
        // Appelle une méthode
        map.put("minecraft:donkey", DonkeyMeta::new);
        // Appelle une méthode
        map.put("minecraft:dragon_fireball", DragonFireballMeta::new);
        // Appelle une méthode
        map.put("minecraft:drowned", DrownedMeta::new);
        // Appelle une méthode
        map.put("minecraft:elder_guardian", ElderGuardianMeta::new);
        // Appelle une méthode
        map.put("minecraft:end_crystal", EndCrystalMeta::new);
        // Appelle une méthode
        map.put("minecraft:ender_dragon", EnderDragonMeta::new);
        // Appelle une méthode
        map.put("minecraft:enderman", EndermanMeta::new);
        // Appelle une méthode
        map.put("minecraft:endermite", EndermiteMeta::new);
        // Appelle une méthode
        map.put("minecraft:evoker", EvokerMeta::new);
        // Appelle une méthode
        map.put("minecraft:evoker_fangs", EvokerFangsMeta::new);
        // Appelle une méthode
        map.put("minecraft:experience_orb", ExperienceOrbMeta::new);
        // Appelle une méthode
        map.put("minecraft:eye_of_ender", EyeOfEnderMeta::new);
        // Appelle une méthode
        map.put("minecraft:falling_block", FallingBlockMeta::new);
        // Appelle une méthode
        map.put("minecraft:fireball", FireballMeta::new);
        // Appelle une méthode
        map.put("minecraft:firework_rocket", FireworkRocketMeta::new);
        // Appelle une méthode
        map.put("minecraft:fox", FoxMeta::new);
        // Appelle une méthode
        map.put("minecraft:frog", FrogMeta::new);
        // Appelle une méthode
        map.put("minecraft:ghast", GhastMeta::new);
        // Appelle une méthode
        map.put("minecraft:giant", GiantMeta::new);
        // Appelle une méthode
        map.put("minecraft:glow_item_frame", GlowItemFrameMeta::new);
        // Appelle une méthode
        map.put("minecraft:glow_squid", GlowSquidMeta::new);
        // Appelle une méthode
        map.put("minecraft:goat", GoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:guardian", GuardianMeta::new);
        // Appelle une méthode
        map.put("minecraft:happy_ghast", HappyGhastMeta::new);
        // Appelle une méthode
        map.put("minecraft:hoglin", HoglinMeta::new);
        // Appelle une méthode
        map.put("minecraft:horse", HorseMeta::new);
        // Appelle une méthode
        map.put("minecraft:husk", HuskMeta::new);
        // Appelle une méthode
        map.put("minecraft:illusioner", IllusionerMeta::new);
        // Appelle une méthode
        map.put("minecraft:interaction", InteractionMeta::new);
        // Appelle une méthode
        map.put("minecraft:iron_golem", IronGolemMeta::new);
        // Appelle une méthode
        map.put("minecraft:item", ItemEntityMeta::new);
        // Appelle une méthode
        map.put("minecraft:item_display", ItemDisplayMeta::new);
        // Appelle une méthode
        map.put("minecraft:item_frame", ItemFrameMeta::new);
        // Appelle une méthode
        map.put("minecraft:jungle_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:jungle_chest_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:leash_knot", LeashKnotMeta::new);
        // Appelle une méthode
        map.put("minecraft:lightning_bolt", LightningBoltMeta::new);
        // Appelle une méthode
        map.put("minecraft:lingering_potion", LingeringPotionMeta::new);
        // Appelle une méthode
        map.put("minecraft:llama", LlamaMeta::new);
        // Appelle une méthode
        map.put("minecraft:llama_spit", LlamaSpitMeta::new);
        // Appelle une méthode
        map.put("minecraft:magma_cube", MagmaCubeMeta::new);
        // Appelle une méthode
        map.put("minecraft:mangrove_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:mangrove_chest_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:mannequin", MannequinMeta::new);
        // Appelle une méthode
        map.put("minecraft:marker", MarkerMeta::new);
        // Appelle une méthode
        map.put("minecraft:minecart", MinecartMeta::new);
        // Appelle une méthode
        map.put("minecraft:nautilus", NautilusMeta::new);
        // Appelle une méthode
        map.put("minecraft:chest_minecart", ChestMinecartMeta::new);
        // Appelle une méthode
        map.put("minecraft:command_block_minecart", CommandBlockMinecartMeta::new);
        // Appelle une méthode
        map.put("minecraft:furnace_minecart", FurnaceMinecartMeta::new);
        // Appelle une méthode
        map.put("minecraft:hopper_minecart", HopperMinecartMeta::new);
        // Appelle une méthode
        map.put("minecraft:spawner_minecart", SpawnerMinecartMeta::new);
        // Appelle une méthode
        map.put("minecraft:text_display", TextDisplayMeta::new);
        // Appelle une méthode
        map.put("minecraft:tnt_minecart", TntMinecartMeta::new);
        // Appelle une méthode
        map.put("minecraft:mooshroom", MooshroomMeta::new);
        // Appelle une méthode
        map.put("minecraft:mule", MuleMeta::new);
        // Appelle une méthode
        map.put("minecraft:oak_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:oak_chest_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:ocelot", OcelotMeta::new);
        // Appelle une méthode
        map.put("minecraft:ominous_item_spawner", OminousItemSpawnerMeta::new);
        // Appelle une méthode
        map.put("minecraft:painting", PaintingMeta::new);
        // Appelle une méthode
        map.put("minecraft:pale_oak_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:pale_oak_chest_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:panda", PandaMeta::new);
        // Appelle une méthode
        map.put("minecraft:parrot", ParrotMeta::new);
        // Appelle une méthode
        map.put("minecraft:parched", ParchedMeta::new);
        // Appelle une méthode
        map.put("minecraft:phantom", PhantomMeta::new);
        // Appelle une méthode
        map.put("minecraft:pig", PigMeta::new);
        // Appelle une méthode
        map.put("minecraft:piglin", PiglinMeta::new);
        // Appelle une méthode
        map.put("minecraft:piglin_brute", PiglinBruteMeta::new);
        // Appelle une méthode
        map.put("minecraft:pillager", PillagerMeta::new);
        // Appelle une méthode
        map.put("minecraft:polar_bear", PolarBearMeta::new);
        // Appelle une méthode
        map.put("minecraft:tnt", PrimedTntMeta::new);
        // Appelle une méthode
        map.put("minecraft:pufferfish", PufferfishMeta::new);
        // Appelle une méthode
        map.put("minecraft:rabbit", RabbitMeta::new);
        // Appelle une méthode
        map.put("minecraft:ravager", RavagerMeta::new);
        // Appelle une méthode
        map.put("minecraft:salmon", SalmonMeta::new);
        // Appelle une méthode
        map.put("minecraft:sheep", SheepMeta::new);
        // Appelle une méthode
        map.put("minecraft:shulker", ShulkerMeta::new);
        // Appelle une méthode
        map.put("minecraft:shulker_bullet", ShulkerBulletMeta::new);
        // Appelle une méthode
        map.put("minecraft:silverfish", SilverfishMeta::new);
        // Appelle une méthode
        map.put("minecraft:skeleton", SkeletonMeta::new);
        // Appelle une méthode
        map.put("minecraft:skeleton_horse", SkeletonHorseMeta::new);
        // Appelle une méthode
        map.put("minecraft:slime", SlimeMeta::new);
        // Appelle une méthode
        map.put("minecraft:small_fireball", SmallFireballMeta::new);
        // Appelle une méthode
        map.put("minecraft:sniffer", SnifferMeta::new);
        // Appelle une méthode
        map.put("minecraft:snow_golem", SnowGolemMeta::new);
        // Appelle une méthode
        map.put("minecraft:snowball", SnowballMeta::new);
        // Appelle une méthode
        map.put("minecraft:spectral_arrow", SpectralArrowMeta::new);
        // Appelle une méthode
        map.put("minecraft:spider", SpiderMeta::new);
        // Appelle une méthode
        map.put("minecraft:splash_potion", SplashPotionMeta::new);
        // Appelle une méthode
        map.put("minecraft:spruce_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:spruce_chest_boat", BoatMeta::new);
        // Appelle une méthode
        map.put("minecraft:squid", SquidMeta::new);
        // Appelle une méthode
        map.put("minecraft:stray", StrayMeta::new);
        // Appelle une méthode
        map.put("minecraft:strider", StriderMeta::new);
        // Appelle une méthode
        map.put("minecraft:tadpole", TadpoleMeta::new);
        // Appelle une méthode
        map.put("minecraft:egg", ThrownEggMeta::new);
        // Appelle une méthode
        map.put("minecraft:ender_pearl", ThrownEnderPearlMeta::new);
        // Appelle une méthode
        map.put("minecraft:experience_bottle", ThrownExperienceBottleMeta::new);
        // Appelle une méthode
        map.put("minecraft:potion", SplashPotionMeta::new);
        // Appelle une méthode
        map.put("minecraft:trident", ThrownTridentMeta::new);
        // Appelle une méthode
        map.put("minecraft:trader_llama", TraderLlamaMeta::new);
        // Appelle une méthode
        map.put("minecraft:tropical_fish", TropicalFishMeta::new);
        // Appelle une méthode
        map.put("minecraft:turtle", TurtleMeta::new);
        // Appelle une méthode
        map.put("minecraft:vex", VexMeta::new);
        // Appelle une méthode
        map.put("minecraft:villager", VillagerMeta::new);
        // Appelle une méthode
        map.put("minecraft:vindicator", VindicatorMeta::new);
        // Appelle une méthode
        map.put("minecraft:wandering_trader", WanderingTraderMeta::new);
        // Appelle une méthode
        map.put("minecraft:warden", WardenMeta::new);
        // Appelle une méthode
        map.put("minecraft:wind_charge", WindChargeMeta::new);
        // Appelle une méthode
        map.put("minecraft:witch", WitchMeta::new);
        // Appelle une méthode
        map.put("minecraft:wither", WitherMeta::new);
        // Appelle une méthode
        map.put("minecraft:wither_skeleton", WitherSkeletonMeta::new);
        // Appelle une méthode
        map.put("minecraft:wither_skull", WitherSkullMeta::new);
        // Appelle une méthode
        map.put("minecraft:wolf", WolfMeta::new);
        // Appelle une méthode
        map.put("minecraft:zoglin", ZoglinMeta::new);
        // Appelle une méthode
        map.put("minecraft:zombie", ZombieMeta::new);
        // Appelle une méthode
        map.put("minecraft:zombie_horse", ZombieHorseMeta::new);
        // Appelle une méthode
        map.put("minecraft:zombie_nautilus", ZombieNautilusMeta::new);
        // Appelle une méthode
        map.put("minecraft:zombie_villager", ZombieVillagerMeta::new);
        // Appelle une méthode
        map.put("minecraft:zombified_piglin", ZombifiedPiglinMeta::new);
        // Appelle une méthode
        map.put("minecraft:player", PlayerMeta::new);
        // Appelle une méthode
        map.put("minecraft:fishing_bobber", FishingHookMeta::new);
        // Renvoie une valeur à l'appelant
        return Map.copyOf(map);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
