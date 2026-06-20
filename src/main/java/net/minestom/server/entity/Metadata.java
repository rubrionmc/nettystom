// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
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
import net.minestom.server.entity.metadata.golem.CopperGolemMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ResolvableProfile;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ObjectArray;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Déclaration de type (classe/interface/enum/record)
public final class Metadata {
    // Début d'une méthode/d'un bloc
    public static Entry<Byte> Byte(byte value) {
        // Renvoie une valeur à l'appelant
        return BYTE.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Integer> VarInt(int value) {
        // Renvoie une valeur à l'appelant
        return VAR_INT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Long> VarLong(long value) {
        // Renvoie une valeur à l'appelant
        return LONG.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Float> Float(float value) {
        // Renvoie une valeur à l'appelant
        return FLOAT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<String> String(String value) {
        // Renvoie une valeur à l'appelant
        return STRING.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Component> Component(Component value) {
        // Renvoie une valeur à l'appelant
        return CHAT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable Component> OptComponent(@Nullable Component value) {
        // Renvoie une valeur à l'appelant
        return OPT_CHAT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<ItemStack> ItemStack(ItemStack value) {
        // Renvoie une valeur à l'appelant
        return ITEM_STACK.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Boolean> Boolean(boolean value) {
        // Renvoie une valeur à l'appelant
        return BOOLEAN.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Point> Rotation(Point value) {
        // Renvoie une valeur à l'appelant
        return ROTATION.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Point> BlockPosition(Point value) {
        // Renvoie une valeur à l'appelant
        return BLOCK_POSITION.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable Point> OptBlockPosition(@Nullable Point value) {
        // Renvoie une valeur à l'appelant
        return OPT_BLOCK_POSITION.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Direction> Direction(Direction value) {
        // Renvoie une valeur à l'appelant
        return DIRECTION.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable UUID> OptUUID(@Nullable UUID value) {
        // Renvoie une valeur à l'appelant
        return OPT_UUID.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Block> BlockState(Block value) {
        // Renvoie une valeur à l'appelant
        return BLOCK_STATE.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable Block> OptBlockState(@Nullable Block value) {
        // Renvoie une valeur à l'appelant
        return OPT_BLOCK_STATE.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Particle> Particle(Particle particle) {
        // Renvoie une valeur à l'appelant
        return PARTICLE.entry(particle);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<List<Particle>> ParticleList(List<Particle> particles) {
        // Renvoie une valeur à l'appelant
        return PARTICLE_LIST.entry(particles);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<VillagerMeta.VillagerData> VillagerData(VillagerMeta.VillagerData data) {
        // Renvoie une valeur à l'appelant
        return VILLAGER_DATA.entry(data);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable Integer> OptVarInt(@Nullable Integer value) {
        // Renvoie une valeur à l'appelant
        return OPT_VAR_INT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<EntityPose> Pose(EntityPose value) {
        // Renvoie une valeur à l'appelant
        return POSE.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<CatVariant>> CatVariant(RegistryKey<CatVariant> value) {
        // Renvoie une valeur à l'appelant
        return CAT_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<CatSoundVariant>> CatSoundVariant(RegistryKey<CatSoundVariant> value) {
        // Renvoie une valeur à l'appelant
        return CAT_SOUND_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<CowVariant>> CowVariant(RegistryKey<CowVariant> value) {
        // Renvoie une valeur à l'appelant
        return COW_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<CowSoundVariant>> CowSoundVariant(RegistryKey<CowSoundVariant> value) {
        // Renvoie une valeur à l'appelant
        return COW_SOUND_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<WolfVariant>> WolfVariant(RegistryKey<WolfVariant> value) {
        // Renvoie une valeur à l'appelant
        return WOLF_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<WolfSoundVariant>> WolfSoundVariant(RegistryKey<WolfSoundVariant> value) {
        // Renvoie une valeur à l'appelant
        return WOLF_SOUND_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<FrogVariant>> FrogVariant(RegistryKey<FrogVariant> value) {
        // Renvoie une valeur à l'appelant
        return FROG_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<PigVariant>> PigVariant(RegistryKey<PigVariant> value) {
        // Renvoie une valeur à l'appelant
        return PIG_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<PigSoundVariant>> PigSoundVariant(RegistryKey<PigSoundVariant> value) {
        // Renvoie une valeur à l'appelant
        return PIG_SOUND_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<ChickenVariant>> ChickenVariant(RegistryKey<ChickenVariant> value) {
        // Renvoie une valeur à l'appelant
        return CHICKEN_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<ChickenSoundVariant>> ChickenSoundVariant(RegistryKey<ChickenSoundVariant> value) {
        // Renvoie une valeur à l'appelant
        return CHICKEN_SOUND_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<ZombieNautilusVariant>> ZombieNautilusVariant(RegistryKey<ZombieNautilusVariant> value) {
        // Renvoie une valeur à l'appelant
        return ZOMBIE_NAUTILUS_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable WorldPos> OptGlobalPosition(@Nullable WorldPos value) {
        // Renvoie une valeur à l'appelant
        return OPT_GLOBAL_POSITION.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Holder<PaintingVariant>> PaintingVariant(Holder<PaintingVariant> value) {
        // Renvoie une valeur à l'appelant
        return PAINTING_VARIANT.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<SnifferMeta.State> SnifferState(SnifferMeta.State value) {
        // Renvoie une valeur à l'appelant
        return SNIFFER_STATE.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<ArmadilloMeta.State> ArmadilloState(ArmadilloMeta.State value) {
        // Renvoie une valeur à l'appelant
        return ARMADILLO_STATE.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<CopperGolemMeta.State> CopperGolemState(CopperGolemMeta.State value) {
        // Renvoie une valeur à l'appelant
        return COPPER_GOLEM_STATE.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<CopperGolemMeta.WeatherState> WeatherState(CopperGolemMeta.WeatherState value) {
        // Renvoie une valeur à l'appelant
        return WEATHER_STATE.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Point> Vector3(Point value) {
        // Renvoie une valeur à l'appelant
        return VECTOR3.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<float[]> Quaternion(float[] value) {
        // Renvoie une valeur à l'appelant
        return QUATERNION.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<ResolvableProfile> ResolvableProfile(ResolvableProfile value) {
        // Renvoie une valeur à l'appelant
        return RESOLVABLE_PROFILE.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<MainHand> MainHand(MainHand value) {
        // Renvoie une valeur à l'appelant
        return MAIN_HAND.entry(value);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);
    // Appelle une méthode
    private static final ObjectArray<Type<?>> TYPES = ObjectArray.singleThread(64);

    // Appelle une méthode
    private static final Type<Byte> BYTE = type(NetworkBuffer.BYTE, (byte) 0);
    // Appelle une méthode
    private static final Type<Integer> VAR_INT = type(NetworkBuffer.VAR_INT, 0);
    // Appelle une méthode
    private static final Type<Long> LONG = type(NetworkBuffer.VAR_LONG, 0L);
    // Appelle une méthode
    private static final Type<Float> FLOAT = type(NetworkBuffer.FLOAT, 0f);
    // Appelle une méthode
    private static final Type<String> STRING = type(NetworkBuffer.STRING, "");
    // Appelle une méthode
    private static final Type<Component> CHAT = type(NetworkBuffer.COMPONENT, Component.empty());
    // Appelle une méthode
    private static final Type<@Nullable Component> OPT_CHAT = type(NetworkBuffer.OPT_CHAT, null);
    // Appelle une méthode
    private static final Type<ItemStack> ITEM_STACK = type(ItemStack.NETWORK_TYPE, ItemStack.AIR);
    // Appelle une méthode
    private static final Type<Boolean> BOOLEAN = type(NetworkBuffer.BOOLEAN, false);
    // Appelle une méthode
    private static final Type<Point> ROTATION = type(NetworkBuffer.VECTOR3, Vec.ZERO);
    // Appelle une méthode
    private static final Type<Point> BLOCK_POSITION = type(NetworkBuffer.BLOCK_POSITION, Vec.ZERO);
    // Appelle une méthode
    private static final Type<@Nullable Point> OPT_BLOCK_POSITION = type(NetworkBuffer.OPT_BLOCK_POSITION, null);
    // Appelle une méthode
    private static final Type<Direction> DIRECTION = type(NetworkBuffer.DIRECTION, Direction.DOWN);
    // Appelle une méthode
    private static final Type<@Nullable UUID> OPT_UUID = type(NetworkBuffer.UUID.optional(), null);
    // Appelle une méthode
    private static final Type<Block> BLOCK_STATE = type(Block.STATE_NETWORK_TYPE, Block.AIR);
    // Affecte une valeur
    private static final Type<@Nullable Block> OPT_BLOCK_STATE = type(new NetworkBuffer.Type<>() { // OPT_VAR_INT
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, @Nullable Block value) {
            // Appelle une méthode
            buffer.write(NetworkBuffer.VAR_INT, value == null ? 0 : value.id());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable Block read(NetworkBuffer buffer) {
            // Appelle une méthode
            int value = buffer.read(NetworkBuffer.VAR_INT);
            // Renvoie une valeur à l'appelant
            return value == 0 ? null : Block.fromStateId(value);
        // Fin d'un bloc/d'une expression
        }
    // Instruction de code
    }, null);
    // Appelle une méthode
    private static final Type<Particle> PARTICLE = type(Particle.NETWORK_TYPE, Particle.DUST);
    // Appelle une méthode
    private static final Type<List<Particle>> PARTICLE_LIST = type(Particle.NETWORK_TYPE.list(Short.MAX_VALUE), List.of());
    // Appelle une méthode
    private static final Type<VillagerMeta.VillagerData> VILLAGER_DATA = type(VillagerMeta.VillagerData.NETWORK_TYPE, VillagerMeta.VillagerData.DEFAULT);
    // Appelle une méthode
    private static final Type<@Nullable Integer> OPT_VAR_INT = type(NetworkBuffer.OPTIONAL_VAR_INT, null);
    // Appelle une méthode
    private static final Type<EntityPose> POSE = type(NetworkBuffer.POSE, EntityPose.STANDING);
    // Appelle une méthode
    private static final Type<RegistryKey<CatVariant>> CAT_VARIANT = type(CatVariant.NETWORK_TYPE, CatVariant.TABBY);
    // Appelle une méthode
    private static final Type<RegistryKey<CatSoundVariant>> CAT_SOUND_VARIANT = type(CatSoundVariant.NETWORK_TYPE, CatSoundVariant.CLASSIC);
    // Appelle une méthode
    private static final Type<RegistryKey<CowVariant>> COW_VARIANT = type(CowVariant.NETWORK_TYPE, CowVariant.TEMPERATE);
    // Appelle une méthode
    private static final Type<RegistryKey<CowSoundVariant>> COW_SOUND_VARIANT = type(CowSoundVariant.NETWORK_TYPE, CowSoundVariant.CLASSIC);
    // Appelle une méthode
    private static final Type<RegistryKey<WolfVariant>> WOLF_VARIANT = type(WolfVariant.NETWORK_TYPE, WolfVariant.PALE);
    // Appelle une méthode
    private static final Type<RegistryKey<WolfSoundVariant>> WOLF_SOUND_VARIANT = type(WolfSoundVariant.NETWORK_TYPE, WolfSoundVariant.CLASSIC);
    // Appelle une méthode
    private static final Type<RegistryKey<FrogVariant>> FROG_VARIANT = type(FrogVariant.NETWORK_TYPE, FrogVariant.TEMPERATE);
    // Appelle une méthode
    private static final Type<RegistryKey<PigVariant>> PIG_VARIANT = type(PigVariant.NETWORK_TYPE, PigVariant.TEMPERATE);
    // Appelle une méthode
    private static final Type<RegistryKey<PigSoundVariant>> PIG_SOUND_VARIANT = type(PigSoundVariant.NETWORK_TYPE, PigSoundVariant.CLASSIC);
    // Appelle une méthode
    private static final Type<RegistryKey<ChickenVariant>> CHICKEN_VARIANT = type(ChickenVariant.NETWORK_TYPE, ChickenVariant.TEMPERATE);
    // Appelle une méthode
    private static final Type<RegistryKey<ChickenSoundVariant>> CHICKEN_SOUND_VARIANT = type(ChickenSoundVariant.NETWORK_TYPE, ChickenSoundVariant.CLASSIC);
    // Appelle une méthode
    private static final Type<RegistryKey<ZombieNautilusVariant>> ZOMBIE_NAUTILUS_VARIANT = type(ZombieNautilusVariant.NETWORK_TYPE, ZombieNautilusVariant.TEMPERATE);
    // Appelle une méthode
    private static final Type<@Nullable WorldPos> OPT_GLOBAL_POSITION = type(WorldPos.NETWORK_TYPE.optional(), null);
    // Appelle une méthode
    private static final Type<Holder<PaintingVariant>> PAINTING_VARIANT = type(PaintingVariant.NETWORK_TYPE, PaintingVariant.KEBAB);
    // Appelle une méthode
    private static final Type<SnifferMeta.State> SNIFFER_STATE = type(SnifferMeta.State.NETWORK_TYPE, SnifferMeta.State.IDLING);
    // Appelle une méthode
    private static final Type<ArmadilloMeta.State> ARMADILLO_STATE = type(ArmadilloMeta.State.NETWORK_TYPE, ArmadilloMeta.State.IDLE);
    // Appelle une méthode
    private static final Type<CopperGolemMeta.State> COPPER_GOLEM_STATE = type(CopperGolemMeta.State.NETWORK_TYPE, CopperGolemMeta.State.IDLE);
    // Appelle une méthode
    private static final Type<CopperGolemMeta.WeatherState> WEATHER_STATE = type(CopperGolemMeta.WeatherState.NETWORK_TYPE, CopperGolemMeta.WeatherState.UNAFFECTED);
    // Appelle une méthode
    private static final Type<Point> VECTOR3 = type(NetworkBuffer.VECTOR3, Vec.ZERO);
    // Appelle une méthode
    private static final Type<float[]> QUATERNION = type(NetworkBuffer.QUATERNION, new float[]{0, 0, 0, 0});
    // Appelle une méthode
    private static final Type<ResolvableProfile> RESOLVABLE_PROFILE = type(ResolvableProfile.NETWORK_TYPE, ResolvableProfile.EMPTY);
    // Appelle une méthode
    private static final Type<MainHand> MAIN_HAND = type(MainHand.NETWORK_TYPE, MainHand.RIGHT);

    // Appelle une méthode
    public static final byte TYPE_BYTE = id(BYTE);
    // Appelle une méthode
    public static final byte TYPE_VARINT = id(VAR_INT);
    // Appelle une méthode
    public static final byte TYPE_LONG = id(LONG);
    // Appelle une méthode
    public static final byte TYPE_FLOAT = id(FLOAT);
    // Appelle une méthode
    public static final byte TYPE_STRING = id(STRING);
    // Appelle une méthode
    public static final byte TYPE_CHAT = id(CHAT);
    // Appelle une méthode
    public static final byte TYPE_OPT_CHAT = id(OPT_CHAT);
    // Appelle une méthode
    public static final byte TYPE_ITEM_STACK = id(ITEM_STACK);
    // Appelle une méthode
    public static final byte TYPE_BOOLEAN = id(BOOLEAN);
    // Appelle une méthode
    public static final byte TYPE_ROTATION = id(ROTATION);
    // Appelle une méthode
    public static final byte TYPE_BLOCK_POSITION = id(BLOCK_POSITION);
    // Appelle une méthode
    public static final byte TYPE_OPT_BLOCK_POSITION = id(OPT_BLOCK_POSITION);
    // Appelle une méthode
    public static final byte TYPE_DIRECTION = id(DIRECTION);
    // Appelle une méthode
    public static final byte TYPE_OPT_UUID = id(OPT_UUID);
    // Appelle une méthode
    public static final byte TYPE_BLOCKSTATE = id(BLOCK_STATE);
    // Appelle une méthode
    public static final byte TYPE_OPT_BLOCKSTATE = id(OPT_BLOCK_STATE);
    // Appelle une méthode
    public static final byte TYPE_PARTICLE = id(PARTICLE);
    // Appelle une méthode
    public static final byte TYPE_PARTICLE_LIST = id(PARTICLE_LIST);
    // Appelle une méthode
    public static final byte TYPE_VILLAGERDATA = id(VILLAGER_DATA);
    // Appelle une méthode
    public static final byte TYPE_OPT_VARINT = id(OPT_VAR_INT);
    // Appelle une méthode
    public static final byte TYPE_POSE = id(POSE);
    // Appelle une méthode
    public static final byte TYPE_CAT_VARIANT = id(CAT_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_CAT_SOUND_VARIANT = id(CAT_SOUND_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_COW_VARIANT = id(COW_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_COW_SOUND_VARIANT = id(COW_SOUND_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_WOLF_VARIANT = id(WOLF_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_WOLF_SOUND_VARIANT = id(WOLF_SOUND_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_FROG_VARIANT = id(FROG_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_PIG_VARIANT = id(PIG_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_PIG_SOUND_VARIANT = id(PIG_SOUND_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_CHICKEN_VARIANT = id(CHICKEN_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_CHICKEN_SOUND_VARIANT = id(CHICKEN_SOUND_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_ZOMBIE_NAUTILUS_VARIANT = id(ZOMBIE_NAUTILUS_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_OPT_GLOBAL_POSITION = id(OPT_GLOBAL_POSITION);
    // Appelle une méthode
    public static final byte TYPE_PAINTING_VARIANT = id(PAINTING_VARIANT);
    // Appelle une méthode
    public static final byte TYPE_SNIFFER_STATE = id(SNIFFER_STATE);
    // Appelle une méthode
    public static final byte TYPE_ARMADILLO_STATE = id(ARMADILLO_STATE);
    // Appelle une méthode
    public static final byte TYPE_COPPER_GOLEM_STATE = id(COPPER_GOLEM_STATE);
    // Appelle une méthode
    public static final byte TYPE_WEATHER_STATE = id(WEATHER_STATE);
    // Appelle une méthode
    public static final byte TYPE_VECTOR3 = id(VECTOR3);
    // Appelle une méthode
    public static final byte TYPE_QUATERNION = id(QUATERNION);
    // Appelle une méthode
    public static final byte TYPE_RESOLVABLE_PROFILE = id(RESOLVABLE_PROFILE);
    // Appelle une méthode
    public static final byte TYPE_MAIN_HAND = id(MAIN_HAND);

    // Début d'une méthode/d'un bloc
    private static <T extends @UnknownNullability Object> Type<T> type(NetworkBuffer.Type<T> serializer, T defaultValue) {
        // Appelle une méthode
        final int id = nextId();
        // Appelle une méthode
        final Type<T> type = new Type<>(id, serializer, defaultValue);
        // Appelle une méthode
        TYPES.set(id, type);
        // Renvoie une valeur à l'appelant
        return type;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Type<?> typeById(int id) {
        // Renvoie une valeur à l'appelant
        return TYPES.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static int typeCount() {
        // Renvoie une valeur à l'appelant
        return NEXT_ID.get();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int nextId() {
        // Renvoie une valeur à l'appelant
        return NEXT_ID.getAndIncrement();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte id(Type<?> type) {
        // Renvoie une valeur à l'appelant
        return (byte) type.id();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Type<T extends @UnknownNullability Object>(
            // Instruction de code
            int id,
            // Instruction de code
            NetworkBuffer.Type<T> serializer,
            // Instruction de code
            T defaultValue
    // Début d'une méthode/d'un bloc
    ) {
        // Début d'une méthode/d'un bloc
        Entry<T> entry(T value) {
            // Renvoie une valeur à l'appelant
            return new MetadataImpl.EntryImpl<>(this, value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public sealed interface Entry<T extends @UnknownNullability Object> permits MetadataImpl.EntryImpl {
        // Affecte une valeur
        NetworkBuffer.Type<Entry<?>> SERIALIZER = MetadataImpl.EntryImpl.SERIALIZER;

        // Appelle une méthode
        int type();

        // Appelle une méthode
        T value();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
