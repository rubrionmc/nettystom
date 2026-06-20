// Déclaration du paquet de ce fichier
package net.minestom.server.sound;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
public record BuiltinSoundEvent(Key key, int id) implements StaticProtocolObject<BuiltinSoundEvent>, SoundEvent {
    // Affecte une valeur
    static final Registry<BuiltinSoundEvent> REGISTRY = RegistryData.createStaticRegistry(Key.key("sound_event"),
            // Appelle une méthode
            (namespace, properties) -> new BuiltinSoundEvent(Key.key(namespace), properties.getInt("id")));

    // Début d'une méthode/d'un bloc
    static @UnknownNullability SoundEvent get(String key) {
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String name() {
        // Renvoie une valeur à l'appelant
        return StaticProtocolObject.super.name();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
