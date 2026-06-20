// Déclaration du paquet de ce fichier
package net.minestom.server.particle;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.color.AlphaColor;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStackTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.NotNull;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.instance.block.Block.STATE_STRUCT_CODEC;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VECTOR3D;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Particle extends StaticProtocolObject<Particle>, Particles permits Particle.Block, Particle.BlockCrumble, Particle.BlockMarker, Particle.DragonBreath, Particle.Dust, Particle.DustColorTransition, Particle.DustPillar, Particle.Effect, Particle.EntityEffect, Particle.FallingDust, Particle.Flash, Particle.InstantEffect, Particle.Item, Particle.SculkCharge, Particle.Shriek, Particle.Simple, Particle.TintedLeaves, Particle.Trail, Particle.Vibration {

    // Affecte une valeur
    NetworkBuffer.Type<Particle> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Particle value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.id());
            // Appelle une méthode
            value.writeData(buffer);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Particle read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int id = buffer.read(VAR_INT);
            // Appelle une méthode
            final Particle particle = Objects.requireNonNull(fromId(id), () -> "unknown particle id: " + id);
            // Renvoie une valeur à l'appelant
            return particle.readData(buffer);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    Codec<Particle> CODEC = new Codec<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<Particle> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            Result<Transcoder.MapLike<D>> mapResult = coder.getMap(value);
            // Embranchement : vérifie une condition
            if (!(mapResult instanceof Result.Ok(Transcoder.MapLike<D> map)))
                // Renvoie une valeur à l'appelant
                return mapResult.cast();

            // Affecte une valeur
            Result<Particle> particleResult = map.getValue("type")
                    // Appelle une méthode
                    .map(coder::getString).mapResult(ParticleImpl::get);
            // Embranchement : vérifie une condition
            if (!(particleResult instanceof Result.Ok(Particle particle)))
                // Renvoie une valeur à l'appelant
                return particleResult.cast();

            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return (Result<Particle>) particle.codec().decodeFromMap(coder, map);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Particle value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");

            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return ((StructCodec<@NotNull Particle>) value.codec()).encode(coder, value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    static Collection<Particle> values() {
        // Renvoie une valeur à l'appelant
        return ParticleImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Particle fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Particle fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return ParticleImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Particle fromId(int id) {
        // Renvoie une valeur à l'appelant
        return ParticleImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Particle readData(NetworkBuffer reader);

    // Appelle une méthode
    void writeData(NetworkBuffer writer);

    // Appelle une méthode
    StructCodec<? extends Particle> codec();

    // Déclaration de type (classe/interface/enum/record)
    record Simple(Key key, int id) implements Particle {
        // Affecte une valeur
        public static final StructCodec<Simple> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, Simple::key,
                // Instruction de code
                ParticleImpl::get);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Particle readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Block(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Affecte une valeur
        public static final StructCodec<Block> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, Block::key,
                // Instruction de code
                "block_state", STATE_STRUCT_CODEC, Block::block,
                // Appelle une méthode
                (key, block) -> ParticleImpl.<Block>get(key).withBlock(block));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Block withBlock(net.minestom.server.instance.block.Block block) {
            // Renvoie une valeur à l'appelant
            return new Block(key(), id(), block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Block readData(NetworkBuffer reader) {
            // Appelle une méthode
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Appelle une méthode
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Appelle une méthode
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Renvoie une valeur à l'appelant
            return this.withBlock(block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record BlockMarker(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Affecte une valeur
        public static final StructCodec<BlockMarker> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, BlockMarker::key,
                // Instruction de code
                "block_state", STATE_STRUCT_CODEC, BlockMarker::block,
                // Appelle une méthode
                (key, block) -> ParticleImpl.<BlockMarker>get(key).withBlock(block));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public BlockMarker withBlock(net.minestom.server.instance.block.Block block) {
            // Renvoie une valeur à l'appelant
            return new BlockMarker(key(), id(), block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public BlockMarker readData(NetworkBuffer reader) {
            // Appelle une méthode
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Appelle une méthode
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Appelle une méthode
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Renvoie une valeur à l'appelant
            return this.withBlock(block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Dust(Key key, int id, RGBLike color, float scale) implements Particle {
        // Affecte une valeur
        public static final StructCodec<Dust> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, Dust::key,
                // Instruction de code
                "color", Color.CODEC, Dust::color,
                // Instruction de code
                "scale", Codec.FLOAT, Dust::scale,
                // Appelle une méthode
                (type, color, scale) -> ParticleImpl.<Dust>get(type).withProperties(color, scale));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Dust withProperties(RGBLike color, float scale) {
            // Renvoie une valeur à l'appelant
            return new Dust(key(), id(), color, scale);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Dust withColor(RGBLike color) {
            // Renvoie une valeur à l'appelant
            return this.withProperties(color, scale);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Dust withScale(float scale) {
            // Renvoie une valeur à l'appelant
            return this.withProperties(color, scale);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Dust readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return this.withProperties(reader.read(Color.NETWORK_TYPE), reader.read(NetworkBuffer.FLOAT));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(Color.NETWORK_TYPE, color);
            // Appelle une méthode
            writer.write(NetworkBuffer.FLOAT, scale);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record DustColorTransition(
            // Instruction de code
            Key key, int id,
            // Instruction de code
            RGBLike color,
            // Instruction de code
            RGBLike transitionColor,
            // Instruction de code
            float scale
    // Début d'une méthode/d'un bloc
    ) implements Particle {
        // Affecte une valeur
        public static final StructCodec<DustColorTransition> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, DustColorTransition::key,
                // Instruction de code
                "from_color", Color.CODEC, DustColorTransition::color,
                // Instruction de code
                "to_color", Color.CODEC, DustColorTransition::transitionColor,
                // Instruction de code
                "scale", Codec.FLOAT, DustColorTransition::scale,
                // Instruction de code
                (type, from, to, scale) ->
                        // Appelle une méthode
                        ParticleImpl.<DustColorTransition>get(type).withProperties(from, to, scale));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public DustColorTransition withProperties(RGBLike color, RGBLike transitionColor, float scale) {
            // Renvoie une valeur à l'appelant
            return new DustColorTransition(key, id, color, transitionColor, scale);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public DustColorTransition withColor(RGBLike color) {
            // Renvoie une valeur à l'appelant
            return this.withProperties(color, transitionColor, scale);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public DustColorTransition withScale(float scale) {
            // Renvoie une valeur à l'appelant
            return this.withProperties(color, transitionColor, scale);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public DustColorTransition withTransitionColor(RGBLike transitionColor) {
            // Renvoie une valeur à l'appelant
            return this.withProperties(color, transitionColor, scale);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DustColorTransition readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return this.withProperties(reader.read(Color.NETWORK_TYPE),
                    // Instruction de code
                    reader.read(Color.NETWORK_TYPE),
                    // Appelle une méthode
                    reader.read(NetworkBuffer.FLOAT));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(Color.NETWORK_TYPE, color);
            // Appelle une méthode
            writer.write(Color.NETWORK_TYPE, transitionColor);
            // Appelle une méthode
            writer.write(NetworkBuffer.FLOAT, scale);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record DustPillar(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Affecte une valeur
        public static final StructCodec<DustPillar> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, DustPillar::key,
                // Instruction de code
                "block_state", STATE_STRUCT_CODEC, DustPillar::block,
                // Appelle une méthode
                (key, block) -> ParticleImpl.<DustPillar>get(key).withBlock(block));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public DustPillar withBlock(net.minestom.server.instance.block.Block block) {
            // Renvoie une valeur à l'appelant
            return new DustPillar(key(), id(), block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DustPillar readData(NetworkBuffer reader) {
            // Appelle une méthode
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Appelle une méthode
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Appelle une méthode
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Renvoie une valeur à l'appelant
            return this.withBlock(block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record FallingDust(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Affecte une valeur
        public static final StructCodec<FallingDust> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, FallingDust::key,
                // Instruction de code
                "block_state", STATE_STRUCT_CODEC, FallingDust::block,
                // Appelle une méthode
                (key, block) -> ParticleImpl.<FallingDust>get(key).withBlock(block));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public FallingDust withBlock(net.minestom.server.instance.block.Block block) {
            // Renvoie une valeur à l'appelant
            return new FallingDust(key(), id(), block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public FallingDust readData(NetworkBuffer reader) {
            // Appelle une méthode
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Appelle une méthode
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Appelle une méthode
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Renvoie une valeur à l'appelant
            return this.withBlock(block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Item(Key key, int id, ItemStack item) implements Particle {
        // Affecte une valeur
        public static final StructCodec<Item> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, Item::key,
                // Instruction de code
                "item", ItemStackTemplate.CODEC, Item::item,
                // Appelle une méthode
                (type, item) -> ParticleImpl.<Item>get(type).withItem(item));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Item withItem(ItemStack item) {
            // Renvoie une valeur à l'appelant
            return new Item(key(), id(), item);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Item readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return this.withItem(reader.read(ItemStackTemplate.NETWORK_TYPE));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(ItemStackTemplate.NETWORK_TYPE, item);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record EntityEffect(Key key, int id, AlphaColor color) implements Particle {
        // Affecte une valeur
        public static final StructCodec<EntityEffect> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, EntityEffect::key,
                // Instruction de code
                "color", AlphaColor.CODEC, EntityEffect::color,
                // Appelle une méthode
                (type, color) -> ParticleImpl.<EntityEffect>get(type).withColor(color));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public EntityEffect withColor(AlphaColor color) {
            // Renvoie une valeur à l'appelant
            return new EntityEffect(key(), id(), color);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public EntityEffect withColor(RGBLike color) {
            // Renvoie une valeur à l'appelant
            return new EntityEffect(key(), id(), new AlphaColor(1, color));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public EntityEffect withColor(int alpha, RGBLike color) {
            // Renvoie une valeur à l'appelant
            return new EntityEffect(key(), id(), new AlphaColor(alpha, color));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public EntityEffect readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return withColor(reader.read(AlphaColor.NETWORK_TYPE));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(AlphaColor.NETWORK_TYPE, color);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SculkCharge(Key key, int id, float roll) implements Particle {
        // Affecte une valeur
        public static final StructCodec<SculkCharge> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, SculkCharge::key,
                // Instruction de code
                "roll", Codec.FLOAT, SculkCharge::roll,
                // Appelle une méthode
                (type, roll) -> ParticleImpl.<SculkCharge>get(type).withRoll(roll));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public SculkCharge withRoll(float roll) {
            // Renvoie une valeur à l'appelant
            return new SculkCharge(key(), id(), roll);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public SculkCharge readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return this.withRoll(reader.read(NetworkBuffer.FLOAT));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(NetworkBuffer.FLOAT, roll);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Shriek(Key key, int id, int delay) implements Particle {
        // Affecte une valeur
        public static final StructCodec<Shriek> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, Shriek::key,
                // Instruction de code
                "delay", Codec.INT, Shriek::delay,
                // Appelle une méthode
                (type, delay) -> ParticleImpl.<Shriek>get(type).withDelay(delay));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Shriek withDelay(int delay) {
            // Renvoie une valeur à l'appelant
            return new Shriek(key(), id(), delay);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Shriek readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return this.withDelay(reader.read(NetworkBuffer.VAR_INT));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(NetworkBuffer.VAR_INT, delay);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Vibration(
            // Instruction de code
            Key key, int id,
            // Instruction de code
            SourceType sourceType,
            // Annotation pour l'élément suivant
            @Nullable Point sourceBlockPosition,
            // Instruction de code
            int sourceEntityId,
            // Instruction de code
            float sourceEntityEyeHeight,
            // Instruction de code
            int travelTicks
    // Début d'une méthode/d'un bloc
    ) implements Particle {

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Instruction de code
        public Vibration withProperties(SourceType sourceType, @Nullable Point sourceBlockPosition,
                                        // Début d'une méthode/d'un bloc
                                        int sourceEntityId, float sourceEntityEyeHeight, int travelTicks) {
            // Renvoie une valeur à l'appelant
            return new Vibration(key(), id(), sourceType, sourceBlockPosition, sourceEntityId, sourceEntityEyeHeight, travelTicks);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Vibration withSourceBlockPosition(@Nullable Point sourceBlockPosition, int travelTicks) {
            // Renvoie une valeur à l'appelant
            return new Vibration(key(), id(), SourceType.BLOCK, sourceBlockPosition, sourceEntityId, sourceEntityEyeHeight, travelTicks);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Vibration withSourceEntity(int sourceEntityId, float sourceEntityEyeHeight, int travelTicks) {
            // Renvoie une valeur à l'appelant
            return new Vibration(key(), id(), SourceType.ENTITY, sourceBlockPosition, sourceEntityId, sourceEntityEyeHeight, travelTicks);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Vibration readData(NetworkBuffer reader) {
            // Appelle une méthode
            SourceType type = reader.read(NetworkBuffer.Enum(SourceType.class));
            // Embranchement : vérifie une condition
            if (type == SourceType.BLOCK) {
                // Renvoie une valeur à l'appelant
                return this.withSourceBlockPosition(reader.read(NetworkBuffer.BLOCK_POSITION), reader.read(NetworkBuffer.VAR_INT));
            // Branche alternative de la condition
            } else {
                // Renvoie une valeur à l'appelant
                return this.withSourceEntity(reader.read(NetworkBuffer.VAR_INT), reader.read(NetworkBuffer.FLOAT), reader.read(NetworkBuffer.VAR_INT));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(NetworkBuffer.Enum(SourceType.class), sourceType);
            // Embranchement : vérifie une condition
            if (sourceType == SourceType.BLOCK) {
                // Appelle une méthode
                Objects.requireNonNull(sourceBlockPosition);
                // Appelle une méthode
                writer.write(NetworkBuffer.BLOCK_POSITION, sourceBlockPosition);
                // Appelle une méthode
                writer.write(NetworkBuffer.VAR_INT, travelTicks);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                writer.write(NetworkBuffer.VAR_INT, sourceEntityId);
                // Appelle une méthode
                writer.write(NetworkBuffer.FLOAT, sourceEntityEyeHeight);
                // Appelle une méthode
                writer.write(NetworkBuffer.VAR_INT, travelTicks);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Lève une exception
            throw new UnsupportedOperationException("Vibration particle cannot be serialized to NBT");
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        public enum SourceType {
            // Instruction de code
            BLOCK, ENTITY
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Trail(
            // Instruction de code
            Key key, int id,
            // Instruction de code
            Point target,
            // Instruction de code
            RGBLike color,
            // Instruction de code
            int duration
    // Début d'une méthode/d'un bloc
    ) implements Particle {
        // Affecte une valeur
        public static final StructCodec<Trail> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, Trail::key,
                // Instruction de code
                "target", Codec.VECTOR3D, Trail::target,
                // Instruction de code
                "color", Color.CODEC, Trail::color,
                // Instruction de code
                "duration", Codec.INT, Trail::duration,
                // Instruction de code
                (type, target, color, duration) ->
                        // Appelle une méthode
                        ParticleImpl.<Trail>get(type).withProperties(target, color, duration));

        // Début d'une méthode/d'un bloc
        public Trail withProperties(Point target, RGBLike color, int duration) {
            // Renvoie une valeur à l'appelant
            return new Trail(key(), id(), target, color, duration);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Trail withTarget(Point target) {
            // Renvoie une valeur à l'appelant
            return new Trail(key(), id(), target, color, duration);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Trail withColor(RGBLike color) {
            // Renvoie une valeur à l'appelant
            return new Trail(key(), id(), target, color, duration);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Trail withDuration(int duration) {
            // Renvoie une valeur à l'appelant
            return new Trail(key(), id(), target, color, duration);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Trail readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return this.withProperties(reader.read(VECTOR3D), reader.read(Color.NETWORK_TYPE), reader.read(VAR_INT));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(VECTOR3D, target);
            // Appelle une méthode
            writer.write(Color.NETWORK_TYPE, color);
            // Appelle une méthode
            writer.write(VAR_INT, duration);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record BlockCrumble(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Affecte une valeur
        public static final StructCodec<BlockCrumble> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, BlockCrumble::key,
                // Instruction de code
                "block_state", STATE_STRUCT_CODEC, BlockCrumble::block,
                // Appelle une méthode
                (key, block) -> ParticleImpl.<BlockCrumble>get(key).withBlock(block));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public BlockCrumble withBlock(net.minestom.server.instance.block.Block block) {
            // Renvoie une valeur à l'appelant
            return new BlockCrumble(key(), id(), block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public BlockCrumble readData(NetworkBuffer reader) {
            // Appelle une méthode
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Appelle une méthode
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Appelle une méthode
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Renvoie une valeur à l'appelant
            return this.withBlock(block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TintedLeaves(Key key, int id, AlphaColor color) implements Particle {
        // Affecte une valeur
        public static final StructCodec<TintedLeaves> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, TintedLeaves::key,
                // Instruction de code
                "color", AlphaColor.CODEC, TintedLeaves::color,
                // Appelle une méthode
                (type, color) -> ParticleImpl.<TintedLeaves>get(type).withColor(color));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public TintedLeaves withColor(AlphaColor color) {
            // Renvoie une valeur à l'appelant
            return new TintedLeaves(key(), id(), color);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public TintedLeaves withColor(RGBLike color) {
            // Renvoie une valeur à l'appelant
            return new TintedLeaves(key(), id(), new AlphaColor(1, color));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public TintedLeaves withColor(int alpha, RGBLike color) {
            // Renvoie une valeur à l'appelant
            return new TintedLeaves(key(), id(), new AlphaColor(alpha, color));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public TintedLeaves readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return withColor(reader.read(AlphaColor.NETWORK_TYPE));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(AlphaColor.NETWORK_TYPE, color);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record DragonBreath(Key key, int id, float power) implements Particle {
        // Affecte une valeur
        public static final StructCodec<DragonBreath> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, DragonBreath::key,
                // Instruction de code
                "power", Codec.FLOAT, DragonBreath::power,
                // Appelle une méthode
                (type, power) -> ParticleImpl.<DragonBreath>get(type).withPower(power));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public DragonBreath withPower(float power) {
            // Renvoie une valeur à l'appelant
            return new DragonBreath(key(), id(), power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DragonBreath readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return withPower(reader.read(NetworkBuffer.FLOAT));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(NetworkBuffer.FLOAT, power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Effect(Key key, int id, RGBLike color, float power) implements Particle {
        // Affecte une valeur
        public static final StructCodec<Effect> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, Effect::key,
                // Instruction de code
                "color", Color.CODEC, Effect::color,
                // Instruction de code
                "power", Codec.FLOAT, Effect::power,
                // Appelle une méthode
                (type, color, power) -> ParticleImpl.<Effect>get(type).withProperties(color, power));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Effect withColor(RGBLike color) {
            // Renvoie une valeur à l'appelant
            return new Effect(key(), id(), color, power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Effect withPower(float power) {
            // Renvoie une valeur à l'appelant
            return new Effect(key(), id(), color, power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Effect withProperties(RGBLike color, float power) {
            // Renvoie une valeur à l'appelant
            return new Effect(key(), id(), color, power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Effect readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return withProperties(reader.read(Color.NETWORK_TYPE), reader.read(NetworkBuffer.FLOAT));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(Color.NETWORK_TYPE, color);
            // Appelle une méthode
            writer.write(NetworkBuffer.FLOAT, power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Flash(Key key, int id, AlphaColor color) implements Particle {
        // Affecte une valeur
        public static final StructCodec<Flash> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, Flash::key,
                // Instruction de code
                "color", Color.CODEC, Flash::color,
                // Appelle une méthode
                (type, color) -> ParticleImpl.<Flash>get(type).withColor(color));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Flash withColor(AlphaColor color) {
            // Renvoie une valeur à l'appelant
            return new Flash(key(), id(), color);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Flash withColor(RGBLike color) {
            // Renvoie une valeur à l'appelant
            return new Flash(key(), id(), new AlphaColor(1, color));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Flash withColor(int alpha, RGBLike color) {
            // Renvoie une valeur à l'appelant
            return new Flash(key(), id(), new AlphaColor(alpha, color));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Flash readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return withColor(reader.read(AlphaColor.NETWORK_TYPE));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(AlphaColor.NETWORK_TYPE, color);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record InstantEffect(Key key, int id, RGBLike color, float power) implements Particle {
        // Affecte une valeur
        public static final StructCodec<InstantEffect> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Codec.KEY, InstantEffect::key,
                // Instruction de code
                "color", Color.CODEC, InstantEffect::color,
                // Instruction de code
                "power", Codec.FLOAT, InstantEffect::power,
                // Appelle une méthode
                (key, color, power) -> ParticleImpl.<InstantEffect>get(key).withProperties(color, power));

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public InstantEffect withColor(RGBLike color) {
            // Renvoie une valeur à l'appelant
            return new InstantEffect(key(), id(), color, power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public InstantEffect withPower(float power) {
            // Renvoie une valeur à l'appelant
            return new InstantEffect(key(), id(), color, power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public InstantEffect withProperties(RGBLike color, float power) {
            // Renvoie une valeur à l'appelant
            return new InstantEffect(key(), id(), color, power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public InstantEffect readData(NetworkBuffer reader) {
            // Renvoie une valeur à l'appelant
            return withProperties(reader.read(Color.NETWORK_TYPE), reader.read(NetworkBuffer.FLOAT));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void writeData(NetworkBuffer writer) {
            // Appelle une méthode
            writer.write(Color.NETWORK_TYPE, color);
            // Appelle une méthode
            writer.write(NetworkBuffer.FLOAT, power);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends Particle> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
