// Package declaration for this file
package net.minestom.server.dialog;

// Import of a required class
import net.kyori.adventure.dialog.DialogLike;
// Import of a required class
import net.minestom.server.registry.RegistryKey;

// Type declaration (class/interface/enum/record)
record RegistryKeyDialog(RegistryKey<Dialog> key) implements DialogLike {
// End of a block/expression
}
