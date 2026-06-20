// Déclaration du paquet de ce fichier
package net.minestom.server.dialog;

// Import d'une classe nécessaire
import net.kyori.adventure.dialog.DialogLike;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;

// Déclaration de type (classe/interface/enum/record)
record RegistryKeyDialog(RegistryKey<Dialog> key) implements DialogLike {
// Fin d'un bloc/d'une expression
}
