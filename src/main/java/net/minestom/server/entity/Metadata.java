// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.*;
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
        return new MetadataImpl.EntryImpl<>(TYPE_BYTE, value, NetworkBuffer.BYTE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Integer> VarInt(int value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_VARINT, value, NetworkBuffer.VAR_INT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Long> VarLong(long value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_LONG, value, NetworkBuffer.VAR_LONG);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Float> Float(float value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_FLOAT, value, NetworkBuffer.FLOAT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<String> String(String value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_STRING, value, NetworkBuffer.STRING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Component> Component(Component value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_CHAT, value, NetworkBuffer.COMPONENT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable Component> OptComponent(@Nullable Component value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_OPT_CHAT, value, NetworkBuffer.OPT_CHAT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<ItemStack> ItemStack(ItemStack value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_ITEM_STACK, value, ItemStack.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Boolean> Boolean(boolean value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_BOOLEAN, value, NetworkBuffer.BOOLEAN);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Point> Rotation(Point value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_ROTATION, value, NetworkBuffer.VECTOR3);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Point> BlockPosition(Point value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_BLOCK_POSITION, value, NetworkBuffer.BLOCK_POSITION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable Point> OptBlockPosition(@Nullable Point value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_OPT_BLOCK_POSITION, value, NetworkBuffer.OPT_BLOCK_POSITION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Direction> Direction(Direction value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_DIRECTION, value, NetworkBuffer.DIRECTION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable UUID> OptUUID(@Nullable UUID value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_OPT_UUID, value, NetworkBuffer.UUID.optional());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Block> BlockState(Block value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_BLOCKSTATE, value, Block.STATE_NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable Block> OptBlockState(@Nullable Block value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_OPT_BLOCKSTATE, value, new NetworkBuffer.Type<>() { //OPT_VAR_INT
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
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Particle> Particle(Particle particle) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_PARTICLE, particle, Particle.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<List<Particle>> ParticleList(List<Particle> particles) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_PARTICLE_LIST, particles, Particle.NETWORK_TYPE.list(Short.MAX_VALUE));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<VillagerMeta.VillagerData> VillagerData(VillagerMeta.VillagerData data) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_VILLAGERDATA, data, VillagerMeta.VillagerData.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<@Nullable Integer> OptVarInt(@Nullable Integer value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_OPT_VARINT, value, NetworkBuffer.OPTIONAL_VAR_INT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<EntityPose> Pose(EntityPose value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_POSE, value, NetworkBuffer.POSE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<CatVariant>> CatVariant(RegistryKey<CatVariant> value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_CAT_VARIANT, value, CatVariant.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<CowVariant>> CowVariant(RegistryKey<CowVariant> value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_COW_VARIANT, value, CowVariant.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<WolfVariant>> WolfVariant(RegistryKey<WolfVariant> value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_WOLF_VARIANT, value, WolfVariant.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<WolfSoundVariant>> WolfSoundVariant(RegistryKey<WolfSoundVariant> value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_WOLF_SOUND_VARIANT, value, WolfSoundVariant.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<FrogVariant>> FrogVariant(RegistryKey<FrogVariant> value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_FROG_VARIANT, value, FrogVariant.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<PigVariant>> PigVariant(RegistryKey<PigVariant> value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_PIG_VARIANT, value, PigVariant.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<ChickenVariant>> ChickenVariant(RegistryKey<ChickenVariant> value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_CHICKEN_VARIANT, value, ChickenVariant.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<RegistryKey<ZombieNautilusVariant>> ZombieNautilusVariant(RegistryKey<ZombieNautilusVariant> value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_ZOMBIE_NAUTILUS_VARIANT, value, ZombieNautilusVariant.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Holder<PaintingVariant>> PaintingVariant(Holder<PaintingVariant> value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_PAINTING_VARIANT, value, PaintingVariant.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<SnifferMeta.State> SnifferState(SnifferMeta.State value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_SNIFFER_STATE, value, SnifferMeta.State.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<ArmadilloMeta.State> ArmadilloState(ArmadilloMeta.State value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_ARMADILLO_STATE, value, ArmadilloMeta.State.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<CopperGolemMeta.State> CopperGolemState(CopperGolemMeta.State value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_COPPER_GOLEM_STATE, value, CopperGolemMeta.State.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<CopperGolemMeta.WeatherState> WeatherState(CopperGolemMeta.WeatherState value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_WEATHER_STATE, value, CopperGolemMeta.WeatherState.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<Point> Vector3(Point value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_VECTOR3, value, NetworkBuffer.VECTOR3);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<float[]> Quaternion(float[] value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_QUATERNION, value, NetworkBuffer.QUATERNION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<ResolvableProfile> ResolvableProfile(ResolvableProfile value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_RESOLVABLE_PROFILE, value, ResolvableProfile.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Entry<MainHand> MainHand(MainHand value) {
        // Renvoie une valeur à l'appelant
        return new MetadataImpl.EntryImpl<>(TYPE_MAIN_HAND, value, MainHand.NETWORK_TYPE);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    // Appelle une méthode
    public static final byte TYPE_BYTE = nextId();
    // Appelle une méthode
    public static final byte TYPE_VARINT = nextId();
    // Appelle une méthode
    public static final byte TYPE_LONG = nextId();
    // Appelle une méthode
    public static final byte TYPE_FLOAT = nextId();
    // Appelle une méthode
    public static final byte TYPE_STRING = nextId();
    // Appelle une méthode
    public static final byte TYPE_CHAT = nextId();
    // Appelle une méthode
    public static final byte TYPE_OPT_CHAT = nextId();
    // Appelle une méthode
    public static final byte TYPE_ITEM_STACK = nextId();
    // Appelle une méthode
    public static final byte TYPE_BOOLEAN = nextId();
    // Appelle une méthode
    public static final byte TYPE_ROTATION = nextId();
    // Appelle une méthode
    public static final byte TYPE_BLOCK_POSITION = nextId();
    // Appelle une méthode
    public static final byte TYPE_OPT_BLOCK_POSITION = nextId();
    // Appelle une méthode
    public static final byte TYPE_DIRECTION = nextId();
    // Appelle une méthode
    public static final byte TYPE_OPT_UUID = nextId();
    // Appelle une méthode
    public static final byte TYPE_BLOCKSTATE = nextId();
    // Appelle une méthode
    public static final byte TYPE_OPT_BLOCKSTATE = nextId();
    // Appelle une méthode
    public static final byte TYPE_PARTICLE = nextId();
    // Appelle une méthode
    public static final byte TYPE_PARTICLE_LIST = nextId();
    // Appelle une méthode
    public static final byte TYPE_VILLAGERDATA = nextId();
    // Appelle une méthode
    public static final byte TYPE_OPT_VARINT = nextId();
    // Appelle une méthode
    public static final byte TYPE_POSE = nextId();
    // Appelle une méthode
    public static final byte TYPE_CAT_VARIANT = nextId();
    // Appelle une méthode
    public static final byte TYPE_COW_VARIANT = nextId();
    // Appelle une méthode
    public static final byte TYPE_WOLF_VARIANT = nextId();
    // Appelle une méthode
    public static final byte TYPE_WOLF_SOUND_VARIANT = nextId();
    // Appelle une méthode
    public static final byte TYPE_FROG_VARIANT = nextId();
    // Appelle une méthode
    public static final byte TYPE_PIG_VARIANT = nextId();
    // Appelle une méthode
    public static final byte TYPE_CHICKEN_VARIANT = nextId();
    // Appelle une méthode
    public static final byte TYPE_ZOMBIE_NAUTILUS_VARIANT = nextId();
    // Affecte une valeur
    public static final byte TYPE_OPT_GLOBAL_POSITION = nextId(); // Unused by protocol it seems
    // Appelle une méthode
    public static final byte TYPE_PAINTING_VARIANT = nextId();
    // Appelle une méthode
    public static final byte TYPE_SNIFFER_STATE = nextId();
    // Appelle une méthode
    public static final byte TYPE_ARMADILLO_STATE = nextId();
    // Appelle une méthode
    public static final byte TYPE_COPPER_GOLEM_STATE = nextId();
    // Appelle une méthode
    public static final byte TYPE_WEATHER_STATE = nextId();
    // Appelle une méthode
    public static final byte TYPE_VECTOR3 = nextId();
    // Appelle une méthode
    public static final byte TYPE_QUATERNION = nextId();
    // Appelle une méthode
    public static final byte TYPE_RESOLVABLE_PROFILE = nextId();
    // Appelle une méthode
    public static final byte TYPE_MAIN_HAND = nextId();

    // Impl Note: Adding an entry here requires that a default value entry is added in MetadataImpl.EMPTY_VALUES

    // Début d'une méthode/d'un bloc
    private static byte nextId() {
        // Renvoie une valeur à l'appelant
        return (byte) NEXT_ID.getAndIncrement();
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
