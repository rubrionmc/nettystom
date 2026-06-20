// Déclaration du paquet de ce fichier
package net.minestom.server.instance.fluid;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Fluid extends StaticProtocolObject<Fluid>, Fluids permits FluidImpl {

    // Annotation pour l'élément suivant
    @Override
    // Appelle une méthode
    RegistryData.FluidEntry registry();

    // Début d'une méthode/d'un bloc
    static Collection<Fluid> values() {
        // Renvoie une valeur à l'appelant
        return FluidImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Fluid fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Fluid fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return FluidImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Fluid fromId(int id) {
        // Renvoie une valeur à l'appelant
        return FluidImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Registry<Fluid> staticRegistry() {
        // Renvoie une valeur à l'appelant
        return FluidImpl.REGISTRY;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
