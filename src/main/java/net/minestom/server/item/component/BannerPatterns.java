// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.color.DyeColor;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.banner.BannerPattern;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record BannerPatterns(List<Layer> layers) {
    // Affecte une valeur
    public static final int MAX_LAYERS = 1024;

    // Appelle une méthode
    public static final NetworkBuffer.Type<BannerPatterns> NETWORK_TYPE = Layer.NETWORK_TYPE.list(MAX_LAYERS).transform(BannerPatterns::new, BannerPatterns::layers);
    // Appelle une méthode
    public static final Codec<BannerPatterns> CODEC = Layer.CODEC.list().transform(BannerPatterns::new, BannerPatterns::layers);

    // Déclaration de type (classe/interface/enum/record)
    public record Layer(Holder<BannerPattern> pattern, DyeColor color) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Layer> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                BannerPattern.HOLDER_NETWORK_TYPE, Layer::pattern,
                // Instruction de code
                DyeColor.NETWORK_TYPE, Layer::color,
                // Instruction de code
                Layer::new);
        // Affecte une valeur
        public static final Codec<Layer> CODEC = StructCodec.struct(
                // Instruction de code
                "pattern", BannerPattern.HOLDER_CODEC, Layer::pattern,
                // Instruction de code
                "color", DyeColor.CODEC, Layer::color,
                // Instruction de code
                Layer::new);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BannerPatterns {
        // Appelle une méthode
        layers = List.copyOf(layers);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BannerPatterns(Layer layer) {
        // Appelle une méthode
        this(List.of(layer));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BannerPatterns(Holder<BannerPattern> pattern, DyeColor color) {
        // Appelle une méthode
        this(new Layer(pattern, color));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BannerPatterns with(Layer layer) {
        // Appelle une méthode
        List<Layer> layers = new ArrayList<>(this.layers);
        // Appelle une méthode
        layers.add(layer);
        // Renvoie une valeur à l'appelant
        return new BannerPatterns(layers);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
