// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
record MaterialImpl(RegistryData.MaterialEntry registry) implements Material {
    // Affecte une valeur
    static final Registry<Material> REGISTRY = RegistryData.createStaticRegistry(Key.key("item"),
            // Appelle une méthode
            (namespace, properties) -> new MaterialImpl(RegistryData.material(namespace, properties)));

    // Début d'une méthode/d'un bloc
    static @UnknownNullability Material get(String key) {
        // Renvoie une valeur à l'appelant
        return REGISTRY.get(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return name();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
