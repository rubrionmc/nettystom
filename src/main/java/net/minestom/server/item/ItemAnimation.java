// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Type declaration (class/interface/enum/record)
public enum ItemAnimation {
    // Code statement
    NONE,
    // Code statement
    EAT,
    // Code statement
    DRINK,
    // Code statement
    BLOCK,
    // Code statement
    BOW,
    // Code statement
    TRIDENT,
    // Code statement
    CROSSBOW,
    // Code statement
    SPYGLASS,
    // Code statement
    TOOT_HORN,
    // Code statement
    BRUSH,
    // Code statement
    BUNDLE,
    // Code statement
    SPEAR;

    // Calls a method
    public static final NetworkBuffer.Type<ItemAnimation> NETWORK_TYPE = NetworkBuffer.Enum(ItemAnimation.class);
    // Calls a method
    public static final Codec<ItemAnimation> CODEC = Codec.Enum(ItemAnimation.class);
// End of a block/expression
}
