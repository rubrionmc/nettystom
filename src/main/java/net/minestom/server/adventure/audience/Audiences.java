// Package declaration for this file
package net.minestom.server.adventure.audience;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.Keyed;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Player;

// Import of a required class
import java.util.function.Predicate;

/**
 * Utility class to access Adventure audiences.
 */
// Type declaration (class/interface/enum/record)
public class Audiences {
    // Calls a method
    private static final SingleAudienceProvider audience = new SingleAudienceProvider();

    /**
     * Gets the {@link AudienceProvider} that provides forwarding audiences.
     *
     * @return the instance
     */
    // Start of a method/block
    public static AudienceProvider<Audience> single() {
        // Returns a value to the caller
        return audience;
    // End of a block/expression
    }

    /**
     * Gets the {@link AudienceProvider} that provides iterables of audience members.
     *
     * @return the instance
     */
    // Start of a method/block
    public static AudienceProvider<Iterable<? extends Audience>> iterable() {
        // Returns a value to the caller
        return audience.collection;
    // End of a block/expression
    }

    /**
     * Gets all audience members. This returns {@link #players()} combined with
     * {@link #customs()} and {@link #console()}. This can be a costly operation, so it
     * is often preferable to use {@link #server()} instead.
     *
     * @return all audience members
     */
    // Start of a method/block
    public static Audience all() {
        // Returns a value to the caller
        return Audience.audience(audience.server, audience.customs());
    // End of a block/expression
    }

    /**
     * Gets all audience members that are of type {@link Player}.
     *
     * @return all players
     */
    // Start of a method/block
    public static Audience players() {
        // Returns a value to the caller
        return audience.players;
    // End of a block/expression
    }

    /**
     * Gets all audience members that are of type {@link Player} and match the predicate.
     *
     * @param filter the predicate
     * @return all players matching the predicate
     */
    // Start of a method/block
    public static Audience players(Predicate<? super Player> filter) {
        // Returns a value to the caller
        return PacketGroupingAudience.of(MinecraftServer.getConnectionManager().getOnlinePlayers().stream().filter(filter).toList());
    // End of a block/expression
    }

    /**
     * Gets the console as an audience.
     *
     * @return the console
     */
    // Start of a method/block
    public static Audience console() {
        // Returns a value to the caller
        return MinecraftServer.getCommandManager().getConsoleSender();
    // End of a block/expression
    }

    /**
     * Gets the combination of {@link #players()} and {@link #console()}.
     *
     * @return the audience of all players and the console
     */
    // Start of a method/block
    public static Audience server() {
        // Returns a value to the caller
        return audience.server;
    // End of a block/expression
    }

    /**
     * Gets all custom audience members.
     *
     * @return all custom audience members
     */
    // Start of a method/block
    public static Audience customs() {
        // Returns a value to the caller
        return Audience.audience(audience.iterable().customs());
    // End of a block/expression
    }

    /**
     * Gets all custom audience members stored using the given keyed object.
     *
     * @param keyed the keyed object
     * @return all custom audience members stored using the key of the object
     */
    // Start of a method/block
    public static Audience custom(Keyed keyed) {
        // Returns a value to the caller
        return custom(keyed.key());
    // End of a block/expression
    }

    /**
     * Gets all custom audience members stored using the given key.
     *
     * @param key the key
     * @return all custom audience members stored using the key
     */
    // Start of a method/block
    public static Audience custom(Key key) {
        // Returns a value to the caller
        return Audience.audience(audience.iterable().custom(key));
    // End of a block/expression
    }

    /**
     * Gets all custom audience members stored using the given keyed object that match
     * the given predicate.
     *
     * @param keyed  the keyed object
     * @param filter the predicate
     * @return all custom audience members stored using the key
     */
    // Start of a method/block
    public static Audience custom(Keyed keyed, Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return custom(keyed.key(), filter);
    // End of a block/expression
    }

    /**
     * Gets all custom audience members stored using the given key that match the
     * given predicate.
     *
     * @param key    the key
     * @param filter the predicate
     * @return all custom audience members stored using the key
     */
    // Start of a method/block
    public static Audience custom(Key key, Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return Audience.audience(audience.iterable().custom(key, filter));
    // End of a block/expression
    }

    /**
     * Gets all custom audience members matching the given predicate.
     *
     * @param filter the predicate
     * @return all matching custom audience members
     */
    // Start of a method/block
    public static Audience customs(Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return Audience.audience(audience.iterable().customs(filter));
    // End of a block/expression
    }

    /**
     * Gets all audience members that match the given predicate.
     *
     * @param filter the predicate
     * @return all matching audience members
     */
    // Start of a method/block
    public static Audience all(Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return Audience.audience(audience.iterable().all(filter));
    // End of a block/expression
    }

    /**
     * Gets the audience registry used to register custom audiences.
     *
     * @return the registry
     */
    // Start of a method/block
    public static AudienceRegistry registry() {
        // Returns a value to the caller
        return audience.iterable().registry();
    // End of a block/expression
    }
// End of a block/expression
}
