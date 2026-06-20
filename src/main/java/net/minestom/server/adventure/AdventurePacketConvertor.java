// Déclaration du paquet de ce fichier
package net.minestom.server.adventure;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.SoundStop;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.title.Title;
// Import d'une classe nécessaire
import net.kyori.adventure.title.TitlePart;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.TickUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ObjectArray;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility methods to convert adventure enums to their packet values.
 */
// Déclaration de type (classe/interface/enum/record)
public final class AdventurePacketConvertor {
    // Instruction de code
    private static final Map<NamedTextColor, Integer> NAMED_TEXT_COLOR_ID_MAP;
    // Instruction de code
    private static final List<NamedTextColor> ID_NAMED_TEXT_COLOR_MAP;

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        Object2IntArrayMap<NamedTextColor> COLOR_ID_MAP = new Object2IntArrayMap<>(16);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.BLACK, 0);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.DARK_BLUE, 1);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.DARK_GREEN, 2);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.DARK_AQUA, 3);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.DARK_RED, 4);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.DARK_PURPLE, 5);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.GOLD, 6);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.GRAY, 7);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.DARK_GRAY, 8);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.BLUE, 9);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.GREEN, 10);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.AQUA, 11);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.RED, 12);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.LIGHT_PURPLE, 13);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.YELLOW, 14);
        // Appelle une méthode
        COLOR_ID_MAP.put(NamedTextColor.WHITE, 15);

        // Appelle une méthode
        ObjectArray<NamedTextColor> array = ObjectArray.singleThread(16);
        // Appelle une méthode
        COLOR_ID_MAP.forEach((key, value) -> array.set(value, key));

        // Appelle une méthode
        NAMED_TEXT_COLOR_ID_MAP = Map.copyOf(COLOR_ID_MAP);
        // Appelle une méthode
        ID_NAMED_TEXT_COLOR_MAP = array.toList();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the int value of a boss bar overlay.
     *
     * @param overlay the overlay
     * @return the value
     */
    // Début d'une méthode/d'un bloc
    public static int getBossBarOverlayValue(BossBar.Overlay overlay) {
        // Renvoie une valeur à l'appelant
        return overlay.ordinal();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the byte value of a collection of boss bar flags.
     *
     * @param flags the flags
     * @return the value
     */
    // Début d'une méthode/d'un bloc
    public static byte getBossBarFlagValue(Collection<BossBar.Flag> flags) {
        // Affecte une valeur
        byte val = 0x0;
        // Boucle : répète un bloc
        for (BossBar.Flag flag : flags) {
            // Appelle une méthode
            val |= (byte) (1 << flag.ordinal());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return val;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the int value of a boss bar color.
     *
     * @param color the color
     * @return the value
     */
    // Début d'une méthode/d'un bloc
    public static int getBossBarColorValue(BossBar.Color color) {
        // Renvoie une valeur à l'appelant
        return color.ordinal();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the int value of a sound source.
     *
     * @param source the source
     * @return the value
     */
    // Début d'une méthode/d'un bloc
    public static int getSoundSourceValue(Sound.Source source) {
        // Renvoie une valeur à l'appelant
        return source.ordinal();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the int value from a named text color.
     *
     * @param color the color
     * @return the int value
     */
    // Début d'une méthode/d'un bloc
    public static int getNamedTextColorValue(NamedTextColor color) {
        // Renvoie une valeur à l'appelant
        return NAMED_TEXT_COLOR_ID_MAP.get(color);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the named text color from the int value, see {@link #getNamedTextColorValue(NamedTextColor)}.
     *
     * @param id the color value
     * @return the int value
     */
    // Début d'une méthode/d'un bloc
    public static NamedTextColor getNamedTextColor(int id) {
        // Renvoie une valeur à l'appelant
        return ID_NAMED_TEXT_COLOR_MAP.get(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a sound packet from a sound and a location.<br>
     * Random variation by default unless a seed is provided in the {@link Sound}.
     *
     * @param sound the sound
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param z     the z coordinate
     * @return the sound packet
     */
    // Début d'une méthode/d'un bloc
    public static ServerPacket createSoundPacket(Sound sound, double x, double y, double z) {
        // Appelle une méthode
        SoundEvent minestomSound = SoundEvent.fromKey(sound.name());
        // Embranchement : vérifie une condition
        if (minestomSound == null) minestomSound = SoundEvent.of(sound.name(), null);

        // Appelle une méthode
        final long seed = sound.seed().orElse(ThreadLocalRandom.current().nextLong());
        // Renvoie une valeur à l'appelant
        return new SoundEffectPacket(minestomSound, sound.source(), new Vec(x, y, z), sound.volume(), sound.pitch(), seed);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a sound effect packet from a sound and an emitter.<br>
     * Random variation by default unless a seed is provided in the {@link Sound}.
     *
     * @param sound   the sound
     * @param emitter the emitter, must be an {@link Entity}
     * @return the sound packet
     */
    // Début d'une méthode/d'un bloc
    public static ServerPacket createSoundPacket(Sound sound, Sound.Emitter emitter) {
        // Embranchement : vérifie une condition
        if (emitter == Sound.Emitter.self())
            // Lève une exception
            throw new IllegalArgumentException("you must replace instances of Emitter.self() before calling this method");
        // Embranchement : vérifie une condition
        if (!(emitter instanceof Entity entity))
            // Lève une exception
            throw new IllegalArgumentException("you can only call this method with entities");

        // Appelle une méthode
        SoundEvent minestomSound = SoundEvent.fromKey(sound.name());
        // Embranchement : vérifie une condition
        if (minestomSound == null) minestomSound = SoundEvent.of(sound.name(), null);

        // Appelle une méthode
        final long seed = sound.seed().orElse(ThreadLocalRandom.current().nextLong());
        // Renvoie une valeur à l'appelant
        return new EntitySoundEffectPacket(minestomSound, sound.source(), entity.getEntityId(), sound.volume(), sound.pitch(), seed);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an entity sound packet from an Adventure sound.
     *
     * @param sound  the sound
     * @param entity the entity the sound is coming from
     * @return the packet
     * @deprecated Use {@link #createSoundPacket(Sound, Sound.Emitter)}
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    public static ServerPacket createEntitySoundPacket(Sound sound, Entity entity) {
        // Renvoie une valeur à l'appelant
        return createSoundPacket(sound, entity);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a sound stop packet from a sound stop.
     *
     * @param stop the sound stop
     * @return the sound stop packet
     */
    // Début d'une méthode/d'un bloc
    public static ServerPacket createSoundStopPacket(SoundStop stop) {
        // Affecte une valeur
        byte flags = 0x0;
        // Appelle une méthode
        Sound.Source source = stop.source();
        // Affecte une valeur
        String sound = null;

        // Embranchement : vérifie une condition
        if (source != null) flags |= 0x1;

        // Appelle une méthode
        final Key soundKey = stop.sound();
        // Embranchement : vérifie une condition
        if (soundKey != null) {
            // Instruction de code
            flags |= 0x2;
            // Appelle une méthode
            sound = soundKey.asString();
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return new StopSoundPacket(flags, source, sound);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates one of the three title packets from a title part and a value.
     *
     * @param part  the part
     * @param value the value
     * @param <T>   the type of the part
     * @return the title packet
     */
    // Début d'une méthode/d'un bloc
    public static <T> ServerPacket createTitlePartPacket(TitlePart<T> part, T value) {
        // Embranchement : vérifie une condition
        if (part == TitlePart.TITLE) {
            // Renvoie une valeur à l'appelant
            return new SetTitleTextPacket((Component) value);
        // Embranchement : vérifie une condition
        } else if (part == TitlePart.SUBTITLE) {
            // Renvoie une valeur à l'appelant
            return new SetTitleSubTitlePacket((Component) value);
        // Embranchement : vérifie une condition
        } else if (part == TitlePart.TIMES) {
            // Appelle une méthode
            Title.Times times = (Title.Times) value;
            // Renvoie une valeur à l'appelant
            return new SetTitleTimePacket(
                    // Instruction de code
                    TickUtils.fromDuration(times.fadeIn(), TickUtils.CLIENT_TICK_MS),
                    // Instruction de code
                    TickUtils.fromDuration(times.stay(), TickUtils.CLIENT_TICK_MS),
                    // Appelle une méthode
                    TickUtils.fromDuration(times.fadeOut(), TickUtils.CLIENT_TICK_MS));
        // Branche alternative de la condition
        } else {
            // Lève une exception
            throw new IllegalArgumentException("Unknown TitlePart " + part);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
