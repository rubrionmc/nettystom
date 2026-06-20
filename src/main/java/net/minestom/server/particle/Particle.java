// Package declaration for this file
package net.minestom.server.particle;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.color.AlphaColor;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.ItemStackTemplate;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.NotNull;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Objects;

// Static import of a member
import static net.minestom.server.instance.block.Block.STATE_STRUCT_CODEC;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VECTOR3D;

// Type declaration (class/interface/enum/record)
public sealed interface Particle extends StaticProtocolObject<Particle>, Particles permits Particle.Block, Particle.BlockCrumble, Particle.BlockMarker, Particle.DragonBreath, Particle.Dust, Particle.DustColorTransition, Particle.DustPillar, Particle.Effect, Particle.EntityEffect, Particle.FallingDust, Particle.Flash, Particle.InstantEffect, Particle.Item, Particle.SculkCharge, Particle.Shriek, Particle.Simple, Particle.TintedLeaves, Particle.Trail, Particle.Vibration {

    // Assigns a value
    NetworkBuffer.Type<Particle> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Particle value) {
            // Calls a method
            buffer.write(VAR_INT, value.id());
            // Calls a method
            value.writeData(buffer);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Particle read(NetworkBuffer buffer) {
            // Calls a method
            final int id = buffer.read(VAR_INT);
            // Calls a method
            final Particle particle = Objects.requireNonNull(fromId(id), () -> "unknown particle id: " + id);
            // Returns a value to the caller
            return particle.readData(buffer);
        // End of a block/expression
        }
    // End of a block/expression
    };
    // Assigns a value
    Codec<Particle> CODEC = new Codec<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<Particle> decode(Transcoder<D> coder, D value) {
            // Calls a method
            Result<Transcoder.MapLike<D>> mapResult = coder.getMap(value);
            // Branch: checks a condition
            if (!(mapResult instanceof Result.Ok(Transcoder.MapLike<D> map)))
                // Returns a value to the caller
                return mapResult.cast();

            // Assigns a value
            Result<Particle> particleResult = map.getValue("type")
                    // Calls a method
                    .map(coder::getString).mapResult(ParticleImpl::get);
            // Branch: checks a condition
            if (!(particleResult instanceof Result.Ok(Particle particle)))
                // Returns a value to the caller
                return particleResult.cast();

            //noinspection unchecked
            // Returns a value to the caller
            return (Result<Particle>) particle.codec().decodeFromMap(coder, map);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Particle value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");

            //noinspection unchecked
            // Returns a value to the caller
            return ((StructCodec<@NotNull Particle>) value.codec()).encode(coder, value);
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Start of a method/block
    static Collection<Particle> values() {
        // Returns a value to the caller
        return ParticleImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Particle fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Particle fromKey(Key key) {
        // Returns a value to the caller
        return ParticleImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Particle fromId(int id) {
        // Returns a value to the caller
        return ParticleImpl.REGISTRY.get(id);
    // End of a block/expression
    }

    // Calls a method
    Particle readData(NetworkBuffer reader);

    // Calls a method
    void writeData(NetworkBuffer writer);

    // Calls a method
    StructCodec<? extends Particle> codec();

    // Type declaration (class/interface/enum/record)
    record Simple(Key key, int id) implements Particle {
        // Assigns a value
        public static final StructCodec<Simple> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, Simple::key,
                // Code statement
                ParticleImpl::get);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Particle readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Block(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Assigns a value
        public static final StructCodec<Block> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, Block::key,
                // Code statement
                "block_state", STATE_STRUCT_CODEC, Block::block,
                // Calls a method
                (key, block) -> ParticleImpl.<Block>get(key).withBlock(block));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Block withBlock(net.minestom.server.instance.block.Block block) {
            // Returns a value to the caller
            return new Block(key(), id(), block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Block readData(NetworkBuffer reader) {
            // Calls a method
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Calls a method
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Calls a method
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Returns a value to the caller
            return this.withBlock(block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record BlockMarker(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Assigns a value
        public static final StructCodec<BlockMarker> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, BlockMarker::key,
                // Code statement
                "block_state", STATE_STRUCT_CODEC, BlockMarker::block,
                // Calls a method
                (key, block) -> ParticleImpl.<BlockMarker>get(key).withBlock(block));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public BlockMarker withBlock(net.minestom.server.instance.block.Block block) {
            // Returns a value to the caller
            return new BlockMarker(key(), id(), block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public BlockMarker readData(NetworkBuffer reader) {
            // Calls a method
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Calls a method
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Calls a method
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Returns a value to the caller
            return this.withBlock(block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Dust(Key key, int id, RGBLike color, float scale) implements Particle {
        // Assigns a value
        public static final StructCodec<Dust> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, Dust::key,
                // Code statement
                "color", Color.CODEC, Dust::color,
                // Code statement
                "scale", Codec.FLOAT, Dust::scale,
                // Calls a method
                (type, color, scale) -> ParticleImpl.<Dust>get(type).withProperties(color, scale));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Dust withProperties(RGBLike color, float scale) {
            // Returns a value to the caller
            return new Dust(key(), id(), color, scale);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Dust withColor(RGBLike color) {
            // Returns a value to the caller
            return this.withProperties(color, scale);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Dust withScale(float scale) {
            // Returns a value to the caller
            return this.withProperties(color, scale);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Dust readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return this.withProperties(reader.read(Color.NETWORK_TYPE), reader.read(NetworkBuffer.FLOAT));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(Color.NETWORK_TYPE, color);
            // Calls a method
            writer.write(NetworkBuffer.FLOAT, scale);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DustColorTransition(
            // Code statement
            Key key, int id,
            // Code statement
            RGBLike color,
            // Code statement
            RGBLike transitionColor,
            // Code statement
            float scale
    // Start of a method/block
    ) implements Particle {
        // Assigns a value
        public static final StructCodec<DustColorTransition> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, DustColorTransition::key,
                // Code statement
                "from_color", Color.CODEC, DustColorTransition::color,
                // Code statement
                "to_color", Color.CODEC, DustColorTransition::transitionColor,
                // Code statement
                "scale", Codec.FLOAT, DustColorTransition::scale,
                // Code statement
                (type, from, to, scale) ->
                        // Calls a method
                        ParticleImpl.<DustColorTransition>get(type).withProperties(from, to, scale));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public DustColorTransition withProperties(RGBLike color, RGBLike transitionColor, float scale) {
            // Returns a value to the caller
            return new DustColorTransition(key, id, color, transitionColor, scale);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public DustColorTransition withColor(RGBLike color) {
            // Returns a value to the caller
            return this.withProperties(color, transitionColor, scale);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public DustColorTransition withScale(float scale) {
            // Returns a value to the caller
            return this.withProperties(color, transitionColor, scale);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public DustColorTransition withTransitionColor(RGBLike transitionColor) {
            // Returns a value to the caller
            return this.withProperties(color, transitionColor, scale);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public DustColorTransition readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return this.withProperties(reader.read(Color.NETWORK_TYPE),
                    // Code statement
                    reader.read(Color.NETWORK_TYPE),
                    // Calls a method
                    reader.read(NetworkBuffer.FLOAT));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(Color.NETWORK_TYPE, color);
            // Calls a method
            writer.write(Color.NETWORK_TYPE, transitionColor);
            // Calls a method
            writer.write(NetworkBuffer.FLOAT, scale);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DustPillar(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Assigns a value
        public static final StructCodec<DustPillar> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, DustPillar::key,
                // Code statement
                "block_state", STATE_STRUCT_CODEC, DustPillar::block,
                // Calls a method
                (key, block) -> ParticleImpl.<DustPillar>get(key).withBlock(block));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public DustPillar withBlock(net.minestom.server.instance.block.Block block) {
            // Returns a value to the caller
            return new DustPillar(key(), id(), block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public DustPillar readData(NetworkBuffer reader) {
            // Calls a method
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Calls a method
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Calls a method
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Returns a value to the caller
            return this.withBlock(block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record FallingDust(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Assigns a value
        public static final StructCodec<FallingDust> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, FallingDust::key,
                // Code statement
                "block_state", STATE_STRUCT_CODEC, FallingDust::block,
                // Calls a method
                (key, block) -> ParticleImpl.<FallingDust>get(key).withBlock(block));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public FallingDust withBlock(net.minestom.server.instance.block.Block block) {
            // Returns a value to the caller
            return new FallingDust(key(), id(), block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public FallingDust readData(NetworkBuffer reader) {
            // Calls a method
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Calls a method
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Calls a method
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Returns a value to the caller
            return this.withBlock(block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Item(Key key, int id, ItemStack item) implements Particle {
        // Assigns a value
        public static final StructCodec<Item> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, Item::key,
                // Code statement
                "item", ItemStackTemplate.CODEC, Item::item,
                // Calls a method
                (type, item) -> ParticleImpl.<Item>get(type).withItem(item));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Item withItem(ItemStack item) {
            // Returns a value to the caller
            return new Item(key(), id(), item);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Item readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return this.withItem(reader.read(ItemStackTemplate.NETWORK_TYPE));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(ItemStackTemplate.NETWORK_TYPE, item);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record EntityEffect(Key key, int id, AlphaColor color) implements Particle {
        // Assigns a value
        public static final StructCodec<EntityEffect> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, EntityEffect::key,
                // Code statement
                "color", AlphaColor.CODEC, EntityEffect::color,
                // Calls a method
                (type, color) -> ParticleImpl.<EntityEffect>get(type).withColor(color));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public EntityEffect withColor(AlphaColor color) {
            // Returns a value to the caller
            return new EntityEffect(key(), id(), color);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public EntityEffect withColor(RGBLike color) {
            // Returns a value to the caller
            return new EntityEffect(key(), id(), new AlphaColor(1, color));
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public EntityEffect withColor(int alpha, RGBLike color) {
            // Returns a value to the caller
            return new EntityEffect(key(), id(), new AlphaColor(alpha, color));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public EntityEffect readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return withColor(reader.read(AlphaColor.NETWORK_TYPE));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(AlphaColor.NETWORK_TYPE, color);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SculkCharge(Key key, int id, float roll) implements Particle {
        // Assigns a value
        public static final StructCodec<SculkCharge> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, SculkCharge::key,
                // Code statement
                "roll", Codec.FLOAT, SculkCharge::roll,
                // Calls a method
                (type, roll) -> ParticleImpl.<SculkCharge>get(type).withRoll(roll));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public SculkCharge withRoll(float roll) {
            // Returns a value to the caller
            return new SculkCharge(key(), id(), roll);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public SculkCharge readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return this.withRoll(reader.read(NetworkBuffer.FLOAT));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(NetworkBuffer.FLOAT, roll);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Shriek(Key key, int id, int delay) implements Particle {
        // Assigns a value
        public static final StructCodec<Shriek> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, Shriek::key,
                // Code statement
                "delay", Codec.INT, Shriek::delay,
                // Calls a method
                (type, delay) -> ParticleImpl.<Shriek>get(type).withDelay(delay));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Shriek withDelay(int delay) {
            // Returns a value to the caller
            return new Shriek(key(), id(), delay);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Shriek readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return this.withDelay(reader.read(NetworkBuffer.VAR_INT));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(NetworkBuffer.VAR_INT, delay);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Vibration(
            // Code statement
            Key key, int id,
            // Code statement
            SourceType sourceType,
            // Annotation for the following element
            @Nullable Point sourceBlockPosition,
            // Code statement
            int sourceEntityId,
            // Code statement
            float sourceEntityEyeHeight,
            // Code statement
            int travelTicks
    // Start of a method/block
    ) implements Particle {

        // Annotation for the following element
        @Contract(pure = true)
        // Code statement
        public Vibration withProperties(SourceType sourceType, @Nullable Point sourceBlockPosition,
                                        // Start of a method/block
                                        int sourceEntityId, float sourceEntityEyeHeight, int travelTicks) {
            // Returns a value to the caller
            return new Vibration(key(), id(), sourceType, sourceBlockPosition, sourceEntityId, sourceEntityEyeHeight, travelTicks);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Vibration withSourceBlockPosition(@Nullable Point sourceBlockPosition, int travelTicks) {
            // Returns a value to the caller
            return new Vibration(key(), id(), SourceType.BLOCK, sourceBlockPosition, sourceEntityId, sourceEntityEyeHeight, travelTicks);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Vibration withSourceEntity(int sourceEntityId, float sourceEntityEyeHeight, int travelTicks) {
            // Returns a value to the caller
            return new Vibration(key(), id(), SourceType.ENTITY, sourceBlockPosition, sourceEntityId, sourceEntityEyeHeight, travelTicks);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Vibration readData(NetworkBuffer reader) {
            // Calls a method
            SourceType type = reader.read(NetworkBuffer.Enum(SourceType.class));
            // Branch: checks a condition
            if (type == SourceType.BLOCK) {
                // Returns a value to the caller
                return this.withSourceBlockPosition(reader.read(NetworkBuffer.BLOCK_POSITION), reader.read(NetworkBuffer.VAR_INT));
            // Alternative branch of the condition
            } else {
                // Returns a value to the caller
                return this.withSourceEntity(reader.read(NetworkBuffer.VAR_INT), reader.read(NetworkBuffer.FLOAT), reader.read(NetworkBuffer.VAR_INT));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(NetworkBuffer.Enum(SourceType.class), sourceType);
            // Branch: checks a condition
            if (sourceType == SourceType.BLOCK) {
                // Calls a method
                Objects.requireNonNull(sourceBlockPosition);
                // Calls a method
                writer.write(NetworkBuffer.BLOCK_POSITION, sourceBlockPosition);
                // Calls a method
                writer.write(NetworkBuffer.VAR_INT, travelTicks);
            // Alternative branch of the condition
            } else {
                // Calls a method
                writer.write(NetworkBuffer.VAR_INT, sourceEntityId);
                // Calls a method
                writer.write(NetworkBuffer.FLOAT, sourceEntityEyeHeight);
                // Calls a method
                writer.write(NetworkBuffer.VAR_INT, travelTicks);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Throws an exception
            throw new UnsupportedOperationException("Vibration particle cannot be serialized to NBT");
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        public enum SourceType {
            // Code statement
            BLOCK, ENTITY
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Trail(
            // Code statement
            Key key, int id,
            // Code statement
            Point target,
            // Code statement
            RGBLike color,
            // Code statement
            int duration
    // Start of a method/block
    ) implements Particle {
        // Assigns a value
        public static final StructCodec<Trail> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, Trail::key,
                // Code statement
                "target", Codec.VECTOR3D, Trail::target,
                // Code statement
                "color", Color.CODEC, Trail::color,
                // Code statement
                "duration", Codec.INT, Trail::duration,
                // Code statement
                (type, target, color, duration) ->
                        // Calls a method
                        ParticleImpl.<Trail>get(type).withProperties(target, color, duration));

        // Start of a method/block
        public Trail withProperties(Point target, RGBLike color, int duration) {
            // Returns a value to the caller
            return new Trail(key(), id(), target, color, duration);
        // End of a block/expression
        }

        // Start of a method/block
        public Trail withTarget(Point target) {
            // Returns a value to the caller
            return new Trail(key(), id(), target, color, duration);
        // End of a block/expression
        }

        // Start of a method/block
        public Trail withColor(RGBLike color) {
            // Returns a value to the caller
            return new Trail(key(), id(), target, color, duration);
        // End of a block/expression
        }

        // Start of a method/block
        public Trail withDuration(int duration) {
            // Returns a value to the caller
            return new Trail(key(), id(), target, color, duration);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Trail readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return this.withProperties(reader.read(VECTOR3D), reader.read(Color.NETWORK_TYPE), reader.read(VAR_INT));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(VECTOR3D, target);
            // Calls a method
            writer.write(Color.NETWORK_TYPE, color);
            // Calls a method
            writer.write(VAR_INT, duration);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record BlockCrumble(Key key, int id, net.minestom.server.instance.block.Block block) implements Particle {
        // Assigns a value
        public static final StructCodec<BlockCrumble> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, BlockCrumble::key,
                // Code statement
                "block_state", STATE_STRUCT_CODEC, BlockCrumble::block,
                // Calls a method
                (key, block) -> ParticleImpl.<BlockCrumble>get(key).withBlock(block));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public BlockCrumble withBlock(net.minestom.server.instance.block.Block block) {
            // Returns a value to the caller
            return new BlockCrumble(key(), id(), block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public BlockCrumble readData(NetworkBuffer reader) {
            // Calls a method
            short blockState = reader.read(NetworkBuffer.VAR_INT).shortValue();
            // Calls a method
            var block = net.minestom.server.instance.block.Block.fromStateId(blockState);
            // Calls a method
            Check.stateCondition(block == null, "Block state " + blockState + " is invalid");
            // Returns a value to the caller
            return this.withBlock(block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(NetworkBuffer.VAR_INT, block.stateId());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TintedLeaves(Key key, int id, AlphaColor color) implements Particle {
        // Assigns a value
        public static final StructCodec<TintedLeaves> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, TintedLeaves::key,
                // Code statement
                "color", AlphaColor.CODEC, TintedLeaves::color,
                // Calls a method
                (type, color) -> ParticleImpl.<TintedLeaves>get(type).withColor(color));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public TintedLeaves withColor(AlphaColor color) {
            // Returns a value to the caller
            return new TintedLeaves(key(), id(), color);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public TintedLeaves withColor(RGBLike color) {
            // Returns a value to the caller
            return new TintedLeaves(key(), id(), new AlphaColor(1, color));
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public TintedLeaves withColor(int alpha, RGBLike color) {
            // Returns a value to the caller
            return new TintedLeaves(key(), id(), new AlphaColor(alpha, color));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public TintedLeaves readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return withColor(reader.read(AlphaColor.NETWORK_TYPE));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(AlphaColor.NETWORK_TYPE, color);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DragonBreath(Key key, int id, float power) implements Particle {
        // Assigns a value
        public static final StructCodec<DragonBreath> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, DragonBreath::key,
                // Code statement
                "power", Codec.FLOAT, DragonBreath::power,
                // Calls a method
                (type, power) -> ParticleImpl.<DragonBreath>get(type).withPower(power));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public DragonBreath withPower(float power) {
            // Returns a value to the caller
            return new DragonBreath(key(), id(), power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public DragonBreath readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return withPower(reader.read(NetworkBuffer.FLOAT));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(NetworkBuffer.FLOAT, power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Effect(Key key, int id, RGBLike color, float power) implements Particle {
        // Assigns a value
        public static final StructCodec<Effect> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, Effect::key,
                // Code statement
                "color", Color.CODEC, Effect::color,
                // Code statement
                "power", Codec.FLOAT, Effect::power,
                // Calls a method
                (type, color, power) -> ParticleImpl.<Effect>get(type).withProperties(color, power));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Effect withColor(RGBLike color) {
            // Returns a value to the caller
            return new Effect(key(), id(), color, power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Effect withPower(float power) {
            // Returns a value to the caller
            return new Effect(key(), id(), color, power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Effect withProperties(RGBLike color, float power) {
            // Returns a value to the caller
            return new Effect(key(), id(), color, power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Effect readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return withProperties(reader.read(Color.NETWORK_TYPE), reader.read(NetworkBuffer.FLOAT));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(Color.NETWORK_TYPE, color);
            // Calls a method
            writer.write(NetworkBuffer.FLOAT, power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Flash(Key key, int id, AlphaColor color) implements Particle {
        // Assigns a value
        public static final StructCodec<Flash> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, Flash::key,
                // Code statement
                "color", Color.CODEC, Flash::color,
                // Calls a method
                (type, color) -> ParticleImpl.<Flash>get(type).withColor(color));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Flash withColor(AlphaColor color) {
            // Returns a value to the caller
            return new Flash(key(), id(), color);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Flash withColor(RGBLike color) {
            // Returns a value to the caller
            return new Flash(key(), id(), new AlphaColor(1, color));
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Flash withColor(int alpha, RGBLike color) {
            // Returns a value to the caller
            return new Flash(key(), id(), new AlphaColor(alpha, color));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Flash readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return withColor(reader.read(AlphaColor.NETWORK_TYPE));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(AlphaColor.NETWORK_TYPE, color);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record InstantEffect(Key key, int id, RGBLike color, float power) implements Particle {
        // Assigns a value
        public static final StructCodec<InstantEffect> CODEC = StructCodec.struct(
                // Code statement
                "type", Codec.KEY, InstantEffect::key,
                // Code statement
                "color", Color.CODEC, InstantEffect::color,
                // Code statement
                "power", Codec.FLOAT, InstantEffect::power,
                // Calls a method
                (key, color, power) -> ParticleImpl.<InstantEffect>get(key).withProperties(color, power));

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public InstantEffect withColor(RGBLike color) {
            // Returns a value to the caller
            return new InstantEffect(key(), id(), color, power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public InstantEffect withPower(float power) {
            // Returns a value to the caller
            return new InstantEffect(key(), id(), color, power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public InstantEffect withProperties(RGBLike color, float power) {
            // Returns a value to the caller
            return new InstantEffect(key(), id(), color, power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public InstantEffect readData(NetworkBuffer reader) {
            // Returns a value to the caller
            return withProperties(reader.read(Color.NETWORK_TYPE), reader.read(NetworkBuffer.FLOAT));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void writeData(NetworkBuffer writer) {
            // Calls a method
            writer.write(Color.NETWORK_TYPE, color);
            // Calls a method
            writer.write(NetworkBuffer.FLOAT, power);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends Particle> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
