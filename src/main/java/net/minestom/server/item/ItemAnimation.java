// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Déclaration de type (classe/interface/enum/record)
public enum ItemAnimation {
    // Instruction de code
    NONE,
    // Instruction de code
    EAT,
    // Instruction de code
    DRINK,
    // Instruction de code
    BLOCK,
    // Instruction de code
    BOW,
    // Instruction de code
    TRIDENT,
    // Instruction de code
    CROSSBOW,
    // Instruction de code
    SPYGLASS,
    // Instruction de code
    TOOT_HORN,
    // Instruction de code
    BRUSH,
    // Instruction de code
    BUNDLE,
    // Instruction de code
    SPEAR;

    // Appelle une méthode
    public static final NetworkBuffer.Type<ItemAnimation> NETWORK_TYPE = NetworkBuffer.Enum(ItemAnimation.class);
    // Appelle une méthode
    public static final Codec<ItemAnimation> CODEC = Codec.Enum(ItemAnimation.class);
// Fin d'un bloc/d'une expression
}
