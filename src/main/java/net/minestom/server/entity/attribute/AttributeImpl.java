// Déclaration du paquet de ce fichier
package net.minestom.server.entity.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
record AttributeImpl(RegistryData.AttributeEntry registry) implements Attribute {
    // Affecte une valeur
    static final Registry<Attribute> REGISTRY = RegistryData.createStaticRegistry(Key.key("attribute"),
            // Appelle une méthode
            (namespace, properties) -> new AttributeImpl(RegistryData.attribute(namespace, properties)));

    // Début d'une méthode/d'un bloc
    static @UnknownNullability Attribute get(String namespace) {
        // Renvoie une valeur à l'appelant
        return REGISTRY.get(Key.key(namespace));
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
