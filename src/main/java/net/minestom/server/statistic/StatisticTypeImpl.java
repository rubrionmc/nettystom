// Déclaration du paquet de ce fichier
package net.minestom.server.statistic;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;

// Déclaration de type (classe/interface/enum/record)
record StatisticTypeImpl(Key key, int id) implements StatisticType {
    // Affecte une valeur
    static final Registry<StatisticType> REGISTRY = RegistryData.createStaticRegistry(Key.key("custom_statistics"),
            // Appelle une méthode
            (namespace, properties) -> new StatisticTypeImpl(Key.key(namespace), properties.getInt("id")));

    // Début d'une méthode/d'un bloc
    static StatisticType get(String key) {
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
