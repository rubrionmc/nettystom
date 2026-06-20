// Package declaration for this file
package net.minestom.server.dialog;

// Import of a required class
import net.minestom.server.codec.Codec;

// Type declaration (class/interface/enum/record)
public enum DialogAfterAction {
    // Code statement
    CLOSE,
    // Code statement
    NONE,
    // Code statement
    WAIT_FOR_RESPONSE;

    // Calls a method
    public static final Codec<DialogAfterAction> CODEC = Codec.Enum(DialogAfterAction.class);
// End of a block/expression
}
