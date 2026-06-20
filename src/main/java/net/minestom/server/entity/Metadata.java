// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
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
import net.minestom.server.entity.metadata.golem.CopperGolemMeta;
// Import of a required class
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import of a required class
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import of a required class
import net.minestom.server.network.player.ResolvableProfile;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import net.minestom.server.registry.Holder;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.utils.Direction;
// Import of a required class
import net.minestom.server.utils.collection.ObjectArray;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Type declaration (class/interface/enum/record)
public final class Metadata {
    // Start of a method/block
    public static Entry<Byte> Byte(byte value) {
        // Returns a value to the caller
        return BYTE.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Integer> VarInt(int value) {
        // Returns a value to the caller
        return VAR_INT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Long> VarLong(long value) {
        // Returns a value to the caller
        return LONG.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Float> Float(float value) {
        // Returns a value to the caller
        return FLOAT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<String> String(String value) {
        // Returns a value to the caller
        return STRING.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Component> Component(Component value) {
        // Returns a value to the caller
        return CHAT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<@Nullable Component> OptComponent(@Nullable Component value) {
        // Returns a value to the caller
        return OPT_CHAT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<ItemStack> ItemStack(ItemStack value) {
        // Returns a value to the caller
        return ITEM_STACK.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Boolean> Boolean(boolean value) {
        // Returns a value to the caller
        return BOOLEAN.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Point> Rotation(Point value) {
        // Returns a value to the caller
        return ROTATION.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Point> BlockPosition(Point value) {
        // Returns a value to the caller
        return BLOCK_POSITION.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<@Nullable Point> OptBlockPosition(@Nullable Point value) {
        // Returns a value to the caller
        return OPT_BLOCK_POSITION.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Direction> Direction(Direction value) {
        // Returns a value to the caller
        return DIRECTION.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<@Nullable UUID> OptUUID(@Nullable UUID value) {
        // Returns a value to the caller
        return OPT_UUID.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Block> BlockState(Block value) {
        // Returns a value to the caller
        return BLOCK_STATE.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<@Nullable Block> OptBlockState(@Nullable Block value) {
        // Returns a value to the caller
        return OPT_BLOCK_STATE.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Particle> Particle(Particle particle) {
        // Returns a value to the caller
        return PARTICLE.entry(particle);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<List<Particle>> ParticleList(List<Particle> particles) {
        // Returns a value to the caller
        return PARTICLE_LIST.entry(particles);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<VillagerMeta.VillagerData> VillagerData(VillagerMeta.VillagerData data) {
        // Returns a value to the caller
        return VILLAGER_DATA.entry(data);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<@Nullable Integer> OptVarInt(@Nullable Integer value) {
        // Returns a value to the caller
        return OPT_VAR_INT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<EntityPose> Pose(EntityPose value) {
        // Returns a value to the caller
        return POSE.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<CatVariant>> CatVariant(RegistryKey<CatVariant> value) {
        // Returns a value to the caller
        return CAT_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<CatSoundVariant>> CatSoundVariant(RegistryKey<CatSoundVariant> value) {
        // Returns a value to the caller
        return CAT_SOUND_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<CowVariant>> CowVariant(RegistryKey<CowVariant> value) {
        // Returns a value to the caller
        return COW_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<CowSoundVariant>> CowSoundVariant(RegistryKey<CowSoundVariant> value) {
        // Returns a value to the caller
        return COW_SOUND_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<WolfVariant>> WolfVariant(RegistryKey<WolfVariant> value) {
        // Returns a value to the caller
        return WOLF_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<WolfSoundVariant>> WolfSoundVariant(RegistryKey<WolfSoundVariant> value) {
        // Returns a value to the caller
        return WOLF_SOUND_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<FrogVariant>> FrogVariant(RegistryKey<FrogVariant> value) {
        // Returns a value to the caller
        return FROG_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<PigVariant>> PigVariant(RegistryKey<PigVariant> value) {
        // Returns a value to the caller
        return PIG_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<PigSoundVariant>> PigSoundVariant(RegistryKey<PigSoundVariant> value) {
        // Returns a value to the caller
        return PIG_SOUND_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<ChickenVariant>> ChickenVariant(RegistryKey<ChickenVariant> value) {
        // Returns a value to the caller
        return CHICKEN_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<ChickenSoundVariant>> ChickenSoundVariant(RegistryKey<ChickenSoundVariant> value) {
        // Returns a value to the caller
        return CHICKEN_SOUND_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<RegistryKey<ZombieNautilusVariant>> ZombieNautilusVariant(RegistryKey<ZombieNautilusVariant> value) {
        // Returns a value to the caller
        return ZOMBIE_NAUTILUS_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<@Nullable WorldPos> OptGlobalPosition(@Nullable WorldPos value) {
        // Returns a value to the caller
        return OPT_GLOBAL_POSITION.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Holder<PaintingVariant>> PaintingVariant(Holder<PaintingVariant> value) {
        // Returns a value to the caller
        return PAINTING_VARIANT.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<SnifferMeta.State> SnifferState(SnifferMeta.State value) {
        // Returns a value to the caller
        return SNIFFER_STATE.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<ArmadilloMeta.State> ArmadilloState(ArmadilloMeta.State value) {
        // Returns a value to the caller
        return ARMADILLO_STATE.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<CopperGolemMeta.State> CopperGolemState(CopperGolemMeta.State value) {
        // Returns a value to the caller
        return COPPER_GOLEM_STATE.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<CopperGolemMeta.WeatherState> WeatherState(CopperGolemMeta.WeatherState value) {
        // Returns a value to the caller
        return WEATHER_STATE.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<Point> Vector3(Point value) {
        // Returns a value to the caller
        return VECTOR3.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<float[]> Quaternion(float[] value) {
        // Returns a value to the caller
        return QUATERNION.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<ResolvableProfile> ResolvableProfile(ResolvableProfile value) {
        // Returns a value to the caller
        return RESOLVABLE_PROFILE.entry(value);
    // End of a block/expression
    }

    // Start of a method/block
    public static Entry<MainHand> MainHand(MainHand value) {
        // Returns a value to the caller
        return MAIN_HAND.entry(value);
    // End of a block/expression
    }

    // Calls a method
    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);
    // Calls a method
    private static final ObjectArray<Type<?>> TYPES = ObjectArray.singleThread(64);

    // Calls a method
    private static final Type<Byte> BYTE = type(NetworkBuffer.BYTE, (byte) 0);
    // Calls a method
    private static final Type<Integer> VAR_INT = type(NetworkBuffer.VAR_INT, 0);
    // Calls a method
    private static final Type<Long> LONG = type(NetworkBuffer.VAR_LONG, 0L);
    // Calls a method
    private static final Type<Float> FLOAT = type(NetworkBuffer.FLOAT, 0f);
    // Calls a method
    private static final Type<String> STRING = type(NetworkBuffer.STRING, "");
    // Calls a method
    private static final Type<Component> CHAT = type(NetworkBuffer.COMPONENT, Component.empty());
    // Calls a method
    private static final Type<@Nullable Component> OPT_CHAT = type(NetworkBuffer.OPT_CHAT, null);
    // Calls a method
    private static final Type<ItemStack> ITEM_STACK = type(ItemStack.NETWORK_TYPE, ItemStack.AIR);
    // Calls a method
    private static final Type<Boolean> BOOLEAN = type(NetworkBuffer.BOOLEAN, false);
    // Calls a method
    private static final Type<Point> ROTATION = type(NetworkBuffer.VECTOR3, Vec.ZERO);
    // Calls a method
    private static final Type<Point> BLOCK_POSITION = type(NetworkBuffer.BLOCK_POSITION, Vec.ZERO);
    // Calls a method
    private static final Type<@Nullable Point> OPT_BLOCK_POSITION = type(NetworkBuffer.OPT_BLOCK_POSITION, null);
    // Calls a method
    private static final Type<Direction> DIRECTION = type(NetworkBuffer.DIRECTION, Direction.DOWN);
    // Calls a method
    private static final Type<@Nullable UUID> OPT_UUID = type(NetworkBuffer.UUID.optional(), null);
    // Calls a method
    private static final Type<Block> BLOCK_STATE = type(Block.STATE_NETWORK_TYPE, Block.AIR);
    // Assigns a value
    private static final Type<@Nullable Block> OPT_BLOCK_STATE = type(new NetworkBuffer.Type<>() { // OPT_VAR_INT
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, @Nullable Block value) {
            // Calls a method
            buffer.write(NetworkBuffer.VAR_INT, value == null ? 0 : value.id());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable Block read(NetworkBuffer buffer) {
            // Calls a method
            int value = buffer.read(NetworkBuffer.VAR_INT);
            // Returns a value to the caller
            return value == 0 ? null : Block.fromStateId(value);
        // End of a block/expression
        }
    // Code statement
    }, null);
    // Calls a method
    private static final Type<Particle> PARTICLE = type(Particle.NETWORK_TYPE, Particle.DUST);
    // Calls a method
    private static final Type<List<Particle>> PARTICLE_LIST = type(Particle.NETWORK_TYPE.list(Short.MAX_VALUE), List.of());
    // Calls a method
    private static final Type<VillagerMeta.VillagerData> VILLAGER_DATA = type(VillagerMeta.VillagerData.NETWORK_TYPE, VillagerMeta.VillagerData.DEFAULT);
    // Calls a method
    private static final Type<@Nullable Integer> OPT_VAR_INT = type(NetworkBuffer.OPTIONAL_VAR_INT, null);
    // Calls a method
    private static final Type<EntityPose> POSE = type(NetworkBuffer.POSE, EntityPose.STANDING);
    // Calls a method
    private static final Type<RegistryKey<CatVariant>> CAT_VARIANT = type(CatVariant.NETWORK_TYPE, CatVariant.TABBY);
    // Calls a method
    private static final Type<RegistryKey<CatSoundVariant>> CAT_SOUND_VARIANT = type(CatSoundVariant.NETWORK_TYPE, CatSoundVariant.CLASSIC);
    // Calls a method
    private static final Type<RegistryKey<CowVariant>> COW_VARIANT = type(CowVariant.NETWORK_TYPE, CowVariant.TEMPERATE);
    // Calls a method
    private static final Type<RegistryKey<CowSoundVariant>> COW_SOUND_VARIANT = type(CowSoundVariant.NETWORK_TYPE, CowSoundVariant.CLASSIC);
    // Calls a method
    private static final Type<RegistryKey<WolfVariant>> WOLF_VARIANT = type(WolfVariant.NETWORK_TYPE, WolfVariant.PALE);
    // Calls a method
    private static final Type<RegistryKey<WolfSoundVariant>> WOLF_SOUND_VARIANT = type(WolfSoundVariant.NETWORK_TYPE, WolfSoundVariant.CLASSIC);
    // Calls a method
    private static final Type<RegistryKey<FrogVariant>> FROG_VARIANT = type(FrogVariant.NETWORK_TYPE, FrogVariant.TEMPERATE);
    // Calls a method
    private static final Type<RegistryKey<PigVariant>> PIG_VARIANT = type(PigVariant.NETWORK_TYPE, PigVariant.TEMPERATE);
    // Calls a method
    private static final Type<RegistryKey<PigSoundVariant>> PIG_SOUND_VARIANT = type(PigSoundVariant.NETWORK_TYPE, PigSoundVariant.CLASSIC);
    // Calls a method
    private static final Type<RegistryKey<ChickenVariant>> CHICKEN_VARIANT = type(ChickenVariant.NETWORK_TYPE, ChickenVariant.TEMPERATE);
    // Calls a method
    private static final Type<RegistryKey<ChickenSoundVariant>> CHICKEN_SOUND_VARIANT = type(ChickenSoundVariant.NETWORK_TYPE, ChickenSoundVariant.CLASSIC);
    // Calls a method
    private static final Type<RegistryKey<ZombieNautilusVariant>> ZOMBIE_NAUTILUS_VARIANT = type(ZombieNautilusVariant.NETWORK_TYPE, ZombieNautilusVariant.TEMPERATE);
    // Calls a method
    private static final Type<@Nullable WorldPos> OPT_GLOBAL_POSITION = type(WorldPos.NETWORK_TYPE.optional(), null);
    // Calls a method
    private static final Type<Holder<PaintingVariant>> PAINTING_VARIANT = type(PaintingVariant.NETWORK_TYPE, PaintingVariant.KEBAB);
    // Calls a method
    private static final Type<SnifferMeta.State> SNIFFER_STATE = type(SnifferMeta.State.NETWORK_TYPE, SnifferMeta.State.IDLING);
    // Calls a method
    private static final Type<ArmadilloMeta.State> ARMADILLO_STATE = type(ArmadilloMeta.State.NETWORK_TYPE, ArmadilloMeta.State.IDLE);
    // Calls a method
    private static final Type<CopperGolemMeta.State> COPPER_GOLEM_STATE = type(CopperGolemMeta.State.NETWORK_TYPE, CopperGolemMeta.State.IDLE);
    // Calls a method
    private static final Type<CopperGolemMeta.WeatherState> WEATHER_STATE = type(CopperGolemMeta.WeatherState.NETWORK_TYPE, CopperGolemMeta.WeatherState.UNAFFECTED);
    // Calls a method
    private static final Type<Point> VECTOR3 = type(NetworkBuffer.VECTOR3, Vec.ZERO);
    // Calls a method
    private static final Type<float[]> QUATERNION = type(NetworkBuffer.QUATERNION, new float[]{0, 0, 0, 0});
    // Calls a method
    private static final Type<ResolvableProfile> RESOLVABLE_PROFILE = type(ResolvableProfile.NETWORK_TYPE, ResolvableProfile.EMPTY);
    // Calls a method
    private static final Type<MainHand> MAIN_HAND = type(MainHand.NETWORK_TYPE, MainHand.RIGHT);

    // Calls a method
    public static final byte TYPE_BYTE = id(BYTE);
    // Calls a method
    public static final byte TYPE_VARINT = id(VAR_INT);
    // Calls a method
    public static final byte TYPE_LONG = id(LONG);
    // Calls a method
    public static final byte TYPE_FLOAT = id(FLOAT);
    // Calls a method
    public static final byte TYPE_STRING = id(STRING);
    // Calls a method
    public static final byte TYPE_CHAT = id(CHAT);
    // Calls a method
    public static final byte TYPE_OPT_CHAT = id(OPT_CHAT);
    // Calls a method
    public static final byte TYPE_ITEM_STACK = id(ITEM_STACK);
    // Calls a method
    public static final byte TYPE_BOOLEAN = id(BOOLEAN);
    // Calls a method
    public static final byte TYPE_ROTATION = id(ROTATION);
    // Calls a method
    public static final byte TYPE_BLOCK_POSITION = id(BLOCK_POSITION);
    // Calls a method
    public static final byte TYPE_OPT_BLOCK_POSITION = id(OPT_BLOCK_POSITION);
    // Calls a method
    public static final byte TYPE_DIRECTION = id(DIRECTION);
    // Calls a method
    public static final byte TYPE_OPT_UUID = id(OPT_UUID);
    // Calls a method
    public static final byte TYPE_BLOCKSTATE = id(BLOCK_STATE);
    // Calls a method
    public static final byte TYPE_OPT_BLOCKSTATE = id(OPT_BLOCK_STATE);
    // Calls a method
    public static final byte TYPE_PARTICLE = id(PARTICLE);
    // Calls a method
    public static final byte TYPE_PARTICLE_LIST = id(PARTICLE_LIST);
    // Calls a method
    public static final byte TYPE_VILLAGERDATA = id(VILLAGER_DATA);
    // Calls a method
    public static final byte TYPE_OPT_VARINT = id(OPT_VAR_INT);
    // Calls a method
    public static final byte TYPE_POSE = id(POSE);
    // Calls a method
    public static final byte TYPE_CAT_VARIANT = id(CAT_VARIANT);
    // Calls a method
    public static final byte TYPE_CAT_SOUND_VARIANT = id(CAT_SOUND_VARIANT);
    // Calls a method
    public static final byte TYPE_COW_VARIANT = id(COW_VARIANT);
    // Calls a method
    public static final byte TYPE_COW_SOUND_VARIANT = id(COW_SOUND_VARIANT);
    // Calls a method
    public static final byte TYPE_WOLF_VARIANT = id(WOLF_VARIANT);
    // Calls a method
    public static final byte TYPE_WOLF_SOUND_VARIANT = id(WOLF_SOUND_VARIANT);
    // Calls a method
    public static final byte TYPE_FROG_VARIANT = id(FROG_VARIANT);
    // Calls a method
    public static final byte TYPE_PIG_VARIANT = id(PIG_VARIANT);
    // Calls a method
    public static final byte TYPE_PIG_SOUND_VARIANT = id(PIG_SOUND_VARIANT);
    // Calls a method
    public static final byte TYPE_CHICKEN_VARIANT = id(CHICKEN_VARIANT);
    // Calls a method
    public static final byte TYPE_CHICKEN_SOUND_VARIANT = id(CHICKEN_SOUND_VARIANT);
    // Calls a method
    public static final byte TYPE_ZOMBIE_NAUTILUS_VARIANT = id(ZOMBIE_NAUTILUS_VARIANT);
    // Calls a method
    public static final byte TYPE_OPT_GLOBAL_POSITION = id(OPT_GLOBAL_POSITION);
    // Calls a method
    public static final byte TYPE_PAINTING_VARIANT = id(PAINTING_VARIANT);
    // Calls a method
    public static final byte TYPE_SNIFFER_STATE = id(SNIFFER_STATE);
    // Calls a method
    public static final byte TYPE_ARMADILLO_STATE = id(ARMADILLO_STATE);
    // Calls a method
    public static final byte TYPE_COPPER_GOLEM_STATE = id(COPPER_GOLEM_STATE);
    // Calls a method
    public static final byte TYPE_WEATHER_STATE = id(WEATHER_STATE);
    // Calls a method
    public static final byte TYPE_VECTOR3 = id(VECTOR3);
    // Calls a method
    public static final byte TYPE_QUATERNION = id(QUATERNION);
    // Calls a method
    public static final byte TYPE_RESOLVABLE_PROFILE = id(RESOLVABLE_PROFILE);
    // Calls a method
    public static final byte TYPE_MAIN_HAND = id(MAIN_HAND);

    // Start of a method/block
    private static <T extends @UnknownNullability Object> Type<T> type(NetworkBuffer.Type<T> serializer, T defaultValue) {
        // Calls a method
        final int id = nextId();
        // Calls a method
        final Type<T> type = new Type<>(id, serializer, defaultValue);
        // Calls a method
        TYPES.set(id, type);
        // Returns a value to the caller
        return type;
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Type<?> typeById(int id) {
        // Returns a value to the caller
        return TYPES.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    static int typeCount() {
        // Returns a value to the caller
        return NEXT_ID.get();
    // End of a block/expression
    }

    // Start of a method/block
    private static int nextId() {
        // Returns a value to the caller
        return NEXT_ID.getAndIncrement();
    // End of a block/expression
    }

    // Start of a method/block
    private static byte id(Type<?> type) {
        // Returns a value to the caller
        return (byte) type.id();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Type<T extends @UnknownNullability Object>(
            // Code statement
            int id,
            // Code statement
            NetworkBuffer.Type<T> serializer,
            // Code statement
            T defaultValue
    // Start of a method/block
    ) {
        // Start of a method/block
        Entry<T> entry(T value) {
            // Returns a value to the caller
            return new MetadataImpl.EntryImpl<>(this, value);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public sealed interface Entry<T extends @UnknownNullability Object> permits MetadataImpl.EntryImpl {
        // Assigns a value
        NetworkBuffer.Type<Entry<?>> SERIALIZER = MetadataImpl.EntryImpl.SERIALIZER;

        // Calls a method
        int type();

        // Calls a method
        T value();
    // End of a block/expression
    }
// End of a block/expression
}
