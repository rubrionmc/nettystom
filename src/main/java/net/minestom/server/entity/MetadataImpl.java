// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.ArmadilloMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.FrogVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.SnifferMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.CatVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.golem.CopperGolemMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.ChickenVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.CowVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.PigVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.ZombieNautilusVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfSoundVariant;
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
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ObjectArray;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.entity.Metadata.*;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
final class MetadataImpl {
    // Appelle une méthode
    static final ObjectArray<Metadata.Entry<?>> EMPTY_VALUES = ObjectArray.singleThread(20);

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_BYTE, Byte((byte) 0));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_VARINT, VarInt(0));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_LONG, VarLong(0L));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_FLOAT, Float(0f));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_STRING, String(""));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_CHAT, Component(Component.empty()));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_OPT_CHAT, OptComponent(null));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_ITEM_STACK, ItemStack(ItemStack.AIR));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_BOOLEAN, Boolean(false));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_ROTATION, Rotation(Vec.ZERO));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_BLOCK_POSITION, BlockPosition(Vec.ZERO));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_OPT_BLOCK_POSITION, OptBlockPosition(null));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_DIRECTION, Direction(Direction.DOWN));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_OPT_UUID, OptUUID(null));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_BLOCKSTATE, BlockState(Block.AIR));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_OPT_BLOCKSTATE, OptBlockState(null));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_PARTICLE, Particle(Particle.DUST));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_PARTICLE_LIST, ParticleList(List.of()));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_VILLAGERDATA, VillagerData(VillagerMeta.VillagerData.DEFAULT));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_OPT_VARINT, OptVarInt(null));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_POSE, Pose(EntityPose.STANDING));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_CAT_VARIANT, CatVariant(CatVariant.TABBY));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_COW_VARIANT, CowVariant(CowVariant.TEMPERATE));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_WOLF_VARIANT, WolfVariant(WolfVariant.PALE));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_WOLF_SOUND_VARIANT, WolfSoundVariant(WolfSoundVariant.CLASSIC));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_FROG_VARIANT, FrogVariant(FrogVariant.TEMPERATE));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_PIG_VARIANT, PigVariant(PigVariant.TEMPERATE));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_CHICKEN_VARIANT, ChickenVariant(ChickenVariant.TEMPERATE));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_ZOMBIE_NAUTILUS_VARIANT, ZombieNautilusVariant(ZombieNautilusVariant.TEMPERATE));
        // OptGlobalPos
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_PAINTING_VARIANT, PaintingVariant(PaintingVariant.KEBAB));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_SNIFFER_STATE, SnifferState(SnifferMeta.State.IDLING));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_ARMADILLO_STATE, ArmadilloState(ArmadilloMeta.State.IDLE));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_COPPER_GOLEM_STATE, CopperGolemState(CopperGolemMeta.State.IDLE));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_WEATHER_STATE, WeatherState(CopperGolemMeta.WeatherState.UNAFFECTED));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_VECTOR3, Vector3(Vec.ZERO));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_QUATERNION, Quaternion(new float[]{0, 0, 0, 0}));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_RESOLVABLE_PROFILE, ResolvableProfile(ResolvableProfile.EMPTY));
        // Appelle une méthode
        EMPTY_VALUES.set(TYPE_MAIN_HAND, MainHand(MainHand.RIGHT));
        // Appelle une méthode
        EMPTY_VALUES.trim();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings({"rawtypes", "unchecked"})
    // Déclaration de type (classe/interface/enum/record)
    record EntryImpl<T extends @UnknownNullability Object>(int type, T value,
                        // Début d'une méthode/d'un bloc
                        NetworkBuffer.Type<T> serializer) implements Metadata.Entry<T> {
        // Affecte une valeur
        static final NetworkBuffer.Type<Entry<?>> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Entry value) {
                // Affecte une valeur
                final EntryImpl impl = (EntryImpl) value;
                // Appelle une méthode
                buffer.write(VAR_INT, impl.type);
                // Appelle une méthode
                buffer.write(impl.serializer, impl.value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Entry read(NetworkBuffer buffer) {
                // Appelle une méthode
                final int type = buffer.read(VAR_INT);
                // Appelle une méthode
                final EntryImpl<?> value = (EntryImpl<?>) EMPTY_VALUES.get(type);
                // Embranchement : vérifie une condition
                if (value == null) throw new UnsupportedOperationException("Unknown value type: " + type);
                // Renvoie une valeur à l'appelant
                return new EntryImpl(type, value.serializer.read(buffer), value.serializer);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
