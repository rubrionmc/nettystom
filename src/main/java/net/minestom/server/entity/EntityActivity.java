// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Keyed;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Locale;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Déclaration de type (classe/interface/enum/record)
public enum EntityActivity implements Keyed {
    // Instruction de code
    CORE,
    // Instruction de code
    IDLE,
    // Instruction de code
    WORK,
    // Instruction de code
    PLAY,
    // Instruction de code
    REST,
    // Instruction de code
    MEET,
    // Instruction de code
    PANIC,
    // Instruction de code
    RAID,
    // Instruction de code
    PRE_RAID,
    // Instruction de code
    HIDE,
    // Instruction de code
    FIGHT,
    // Instruction de code
    CELEBRATE,
    // Instruction de code
    ADMIRE_ITEM,
    // Instruction de code
    AVOID,
    // Instruction de code
    RIDE,
    // Instruction de code
    PLAY_DEAD,
    // Instruction de code
    LONG_JUMP,
    // Instruction de code
    RAM,
    // Instruction de code
    TONGUE,
    // Instruction de code
    SWIM,
    // Instruction de code
    LAY_SPAWN,
    // Instruction de code
    SNIFF,
    // Instruction de code
    INVESTIGATE,
    // Instruction de code
    ROAR,
    // Instruction de code
    EMERGE,
    // Instruction de code
    DIG;

    // Affecte une valeur
    private static final Map<Key, EntityActivity> BY_KEY = Arrays.stream(values())
            // Appelle une méthode
            .collect(Collectors.toMap(Keyed::key, Function.identity()));

    // Appelle une méthode
    public static final Codec<EntityActivity> CODEC = Codec.KEY.transform(BY_KEY::get, Keyed::key);

    // Appelle une méthode
    private final Key key = Key.key(name().toLowerCase(Locale.ROOT));

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key key() {
        // Renvoie une valeur à l'appelant
        return key;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
