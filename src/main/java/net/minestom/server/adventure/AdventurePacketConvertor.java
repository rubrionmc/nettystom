// Package declaration for this file
package net.minestom.server.adventure;

// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.kyori.adventure.sound.SoundStop;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.title.Title;
// Import of a required class
import net.kyori.adventure.title.TitlePart;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.TickUtils;
// Import of a required class
import net.minestom.server.utils.collection.ObjectArray;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility methods to convert adventure enums to their packet values.
 */
// Type declaration (class/interface/enum/record)
public final class AdventurePacketConvertor {
    // Code statement
    private static final Map<NamedTextColor, Integer> NAMED_TEXT_COLOR_ID_MAP;
    // Code statement
    private static final List<NamedTextColor> ID_NAMED_TEXT_COLOR_MAP;

    // Start of a method/block
    static {
        // Calls a method
        Object2IntArrayMap<NamedTextColor> COLOR_ID_MAP = new Object2IntArrayMap<>(16);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.BLACK, 0);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.DARK_BLUE, 1);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.DARK_GREEN, 2);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.DARK_AQUA, 3);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.DARK_RED, 4);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.DARK_PURPLE, 5);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.GOLD, 6);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.GRAY, 7);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.DARK_GRAY, 8);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.BLUE, 9);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.GREEN, 10);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.AQUA, 11);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.RED, 12);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.LIGHT_PURPLE, 13);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.YELLOW, 14);
        // Calls a method
        COLOR_ID_MAP.put(NamedTextColor.WHITE, 15);

        // Calls a method
        ObjectArray<NamedTextColor> array = ObjectArray.singleThread(16);
        // Calls a method
        COLOR_ID_MAP.forEach((key, value) -> array.set(value, key));

        // Calls a method
        NAMED_TEXT_COLOR_ID_MAP = Map.copyOf(COLOR_ID_MAP);
        // Calls a method
        ID_NAMED_TEXT_COLOR_MAP = array.toList();
    // End of a block/expression
    }

    /**
     * Gets the int value of a boss bar overlay.
     *
     * @param overlay the overlay
     * @return the value
     */
    // Start of a method/block
    public static int getBossBarOverlayValue(BossBar.Overlay overlay) {
        // Returns a value to the caller
        return overlay.ordinal();
    // End of a block/expression
    }

    /**
     * Gets the byte value of a collection of boss bar flags.
     *
     * @param flags the flags
     * @return the value
     */
    // Start of a method/block
    public static byte getBossBarFlagValue(Collection<BossBar.Flag> flags) {
        // Assigns a value
        byte val = 0x0;
        // Loop: repeats a block
        for (BossBar.Flag flag : flags) {
            // Calls a method
            val |= (byte) (1 << flag.ordinal());
        // End of a block/expression
        }
        // Returns a value to the caller
        return val;
    // End of a block/expression
    }

    /**
     * Gets the int value of a boss bar color.
     *
     * @param color the color
     * @return the value
     */
    // Start of a method/block
    public static int getBossBarColorValue(BossBar.Color color) {
        // Returns a value to the caller
        return color.ordinal();
    // End of a block/expression
    }

    /**
     * Gets the int value of a sound source.
     *
     * @param source the source
     * @return the value
     */
    // Start of a method/block
    public static int getSoundSourceValue(Sound.Source source) {
        // Returns a value to the caller
        return source.ordinal();
    // End of a block/expression
    }

    /**
     * Gets the int value from a named text color.
     *
     * @param color the color
     * @return the int value
     */
    // Start of a method/block
    public static int getNamedTextColorValue(NamedTextColor color) {
        // Returns a value to the caller
        return NAMED_TEXT_COLOR_ID_MAP.get(color);
    // End of a block/expression
    }

    /**
     * Gets the named text color from the int value, see {@link #getNamedTextColorValue(NamedTextColor)}.
     *
     * @param id the color value
     * @return the int value
     */
    // Start of a method/block
    public static NamedTextColor getNamedTextColor(int id) {
        // Returns a value to the caller
        return ID_NAMED_TEXT_COLOR_MAP.get(id);
    // End of a block/expression
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
    // Start of a method/block
    public static ServerPacket createSoundPacket(Sound sound, double x, double y, double z) {
        // Calls a method
        SoundEvent minestomSound = SoundEvent.fromKey(sound.name());
        // Branch: checks a condition
        if (minestomSound == null) minestomSound = SoundEvent.of(sound.name(), null);

        // Calls a method
        final long seed = sound.seed().orElse(ThreadLocalRandom.current().nextLong());
        // Returns a value to the caller
        return new SoundEffectPacket(minestomSound, sound.source(), new Vec(x, y, z), sound.volume(), sound.pitch(), seed);
    // End of a block/expression
    }

    /**
     * Creates a sound effect packet from a sound and an emitter.<br>
     * Random variation by default unless a seed is provided in the {@link Sound}.
     *
     * @param sound   the sound
     * @param emitter the emitter, must be an {@link Entity}
     * @return the sound packet
     */
    // Start of a method/block
    public static ServerPacket createSoundPacket(Sound sound, Sound.Emitter emitter) {
        // Branch: checks a condition
        if (emitter == Sound.Emitter.self())
            // Throws an exception
            throw new IllegalArgumentException("you must replace instances of Emitter.self() before calling this method");
        // Branch: checks a condition
        if (!(emitter instanceof Entity entity))
            // Throws an exception
            throw new IllegalArgumentException("you can only call this method with entities");

        // Calls a method
        SoundEvent minestomSound = SoundEvent.fromKey(sound.name());
        // Branch: checks a condition
        if (minestomSound == null) minestomSound = SoundEvent.of(sound.name(), null);

        // Calls a method
        final long seed = sound.seed().orElse(ThreadLocalRandom.current().nextLong());
        // Returns a value to the caller
        return new EntitySoundEffectPacket(minestomSound, sound.source(), entity.getEntityId(), sound.volume(), sound.pitch(), seed);
    // End of a block/expression
    }

    /**
     * Creates an entity sound packet from an Adventure sound.
     *
     * @param sound  the sound
     * @param entity the entity the sound is coming from
     * @return the packet
     * @deprecated Use {@link #createSoundPacket(Sound, Sound.Emitter)}
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public static ServerPacket createEntitySoundPacket(Sound sound, Entity entity) {
        // Returns a value to the caller
        return createSoundPacket(sound, entity);
    // End of a block/expression
    }

    /**
     * Creates a sound stop packet from a sound stop.
     *
     * @param stop the sound stop
     * @return the sound stop packet
     */
    // Start of a method/block
    public static ServerPacket createSoundStopPacket(SoundStop stop) {
        // Assigns a value
        byte flags = 0x0;
        // Calls a method
        Sound.Source source = stop.source();
        // Assigns a value
        String sound = null;

        // Branch: checks a condition
        if (source != null) flags |= 0x1;

        // Calls a method
        final Key soundKey = stop.sound();
        // Branch: checks a condition
        if (soundKey != null) {
            // Code statement
            flags |= 0x2;
            // Calls a method
            sound = soundKey.asString();
        // End of a block/expression
        }

        // Returns a value to the caller
        return new StopSoundPacket(flags, source, sound);
    // End of a block/expression
    }

    /**
     * Creates one of the three title packets from a title part and a value.
     *
     * @param part  the part
     * @param value the value
     * @param <T>   the type of the part
     * @return the title packet
     */
    // Start of a method/block
    public static <T> ServerPacket createTitlePartPacket(TitlePart<T> part, T value) {
        // Branch: checks a condition
        if (part == TitlePart.TITLE) {
            // Returns a value to the caller
            return new SetTitleTextPacket((Component) value);
        // Branch: checks a condition
        } else if (part == TitlePart.SUBTITLE) {
            // Returns a value to the caller
            return new SetTitleSubTitlePacket((Component) value);
        // Branch: checks a condition
        } else if (part == TitlePart.TIMES) {
            // Calls a method
            Title.Times times = (Title.Times) value;
            // Returns a value to the caller
            return new SetTitleTimePacket(
                    // Code statement
                    TickUtils.fromDuration(times.fadeIn(), TickUtils.CLIENT_TICK_MS),
                    // Code statement
                    TickUtils.fromDuration(times.stay(), TickUtils.CLIENT_TICK_MS),
                    // Calls a method
                    TickUtils.fromDuration(times.fadeOut(), TickUtils.CLIENT_TICK_MS));
        // Alternative branch of the condition
        } else {
            // Throws an exception
            throw new IllegalArgumentException("Unknown TitlePart " + part);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
