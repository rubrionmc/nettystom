// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.Keyed;
// Import of a required class
import net.minestom.server.codec.Codec;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.Locale;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.stream.Collectors;

// Type declaration (class/interface/enum/record)
public enum EntityActivity implements Keyed {
    // Code statement
    CORE,
    // Code statement
    IDLE,
    // Code statement
    WORK,
    // Code statement
    PLAY,
    // Code statement
    REST,
    // Code statement
    MEET,
    // Code statement
    PANIC,
    // Code statement
    RAID,
    // Code statement
    PRE_RAID,
    // Code statement
    HIDE,
    // Code statement
    FIGHT,
    // Code statement
    CELEBRATE,
    // Code statement
    ADMIRE_ITEM,
    // Code statement
    AVOID,
    // Code statement
    RIDE,
    // Code statement
    PLAY_DEAD,
    // Code statement
    LONG_JUMP,
    // Code statement
    RAM,
    // Code statement
    TONGUE,
    // Code statement
    SWIM,
    // Code statement
    LAY_SPAWN,
    // Code statement
    SNIFF,
    // Code statement
    INVESTIGATE,
    // Code statement
    ROAR,
    // Code statement
    EMERGE,
    // Code statement
    DIG;

    // Assigns a value
    private static final Map<Key, EntityActivity> BY_KEY = Arrays.stream(values())
            // Calls a method
            .collect(Collectors.toMap(Keyed::key, Function.identity()));

    // Calls a method
    public static final Codec<EntityActivity> CODEC = Codec.KEY.transform(BY_KEY::get, Keyed::key);

    // Calls a method
    private final Key key = Key.key(name().toLowerCase(Locale.ROOT));

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key key() {
        // Returns a value to the caller
        return key;
    // End of a block/expression
    }
// End of a block/expression
}
