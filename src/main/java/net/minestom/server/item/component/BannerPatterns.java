// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.color.DyeColor;
// Import of a required class
import net.minestom.server.instance.block.banner.BannerPattern;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.Holder;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record BannerPatterns(List<Layer> layers) {
    // Assigns a value
    public static final int MAX_LAYERS = 1024;

    // Calls a method
    public static final NetworkBuffer.Type<BannerPatterns> NETWORK_TYPE = Layer.NETWORK_TYPE.list(MAX_LAYERS).transform(BannerPatterns::new, BannerPatterns::layers);
    // Calls a method
    public static final Codec<BannerPatterns> CODEC = Layer.CODEC.list().transform(BannerPatterns::new, BannerPatterns::layers);

    // Type declaration (class/interface/enum/record)
    public record Layer(Holder<BannerPattern> pattern, DyeColor color) {
        // Assigns a value
        public static final NetworkBuffer.Type<Layer> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                BannerPattern.HOLDER_NETWORK_TYPE, Layer::pattern,
                // Code statement
                DyeColor.NETWORK_TYPE, Layer::color,
                // Code statement
                Layer::new);
        // Assigns a value
        public static final Codec<Layer> CODEC = StructCodec.struct(
                // Code statement
                "pattern", BannerPattern.HOLDER_CODEC, Layer::pattern,
                // Code statement
                "color", DyeColor.CODEC, Layer::color,
                // Code statement
                Layer::new);
    // End of a block/expression
    }

    // Start of a method/block
    public BannerPatterns {
        // Calls a method
        layers = List.copyOf(layers);
    // End of a block/expression
    }

    // Start of a method/block
    public BannerPatterns(Layer layer) {
        // Calls a method
        this(List.of(layer));
    // End of a block/expression
    }

    // Start of a method/block
    public BannerPatterns(Holder<BannerPattern> pattern, DyeColor color) {
        // Calls a method
        this(new Layer(pattern, color));
    // End of a block/expression
    }

    // Start of a method/block
    public BannerPatterns with(Layer layer) {
        // Calls a method
        List<Layer> layers = new ArrayList<>(this.layers);
        // Calls a method
        layers.add(layer);
        // Returns a value to the caller
        return new BannerPatterns(layers);
    // End of a block/expression
    }
// End of a block/expression
}
