// Déclaration du paquet de ce fichier
package net.minestom.server.instance.fluid;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
record FluidImpl(RegistryData.FluidEntry registry) implements Fluid {
    // Affecte une valeur
    static final Registry<Fluid> REGISTRY = RegistryData.createStaticRegistry(Key.key("fluid"),
            // Appelle une méthode
            (namespace, properties) -> new FluidImpl(RegistryData.fluid(namespace, properties)));


    // Début d'une méthode/d'un bloc
    static @UnknownNullability Fluid get(String key) {
        // Renvoie une valeur à l'appelant
        return REGISTRY.get(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key key() {
        // Renvoie une valeur à l'appelant
        return registry.key();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int id() {
        // Renvoie une valeur à l'appelant
        return registry.id();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
