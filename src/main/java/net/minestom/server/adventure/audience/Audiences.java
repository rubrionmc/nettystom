// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.audience;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Keyed;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Import d'une classe nécessaire
import java.util.function.Predicate;

/**
 * Utility class to access Adventure audiences.
 */
// Déclaration de type (classe/interface/enum/record)
public class Audiences {
    // Appelle une méthode
    private static final SingleAudienceProvider audience = new SingleAudienceProvider();

    /**
     * Gets the {@link AudienceProvider} that provides forwarding audiences.
     *
     * @return the instance
     */
    // Début d'une méthode/d'un bloc
    public static AudienceProvider<Audience> single() {
        // Renvoie une valeur à l'appelant
        return audience;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link AudienceProvider} that provides iterables of audience members.
     *
     * @return the instance
     */
    // Début d'une méthode/d'un bloc
    public static AudienceProvider<Iterable<? extends Audience>> iterable() {
        // Renvoie une valeur à l'appelant
        return audience.collection;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all audience members. This returns {@link #players()} combined with
     * {@link #customs()} and {@link #console()}. This can be a costly operation, so it
     * is often preferable to use {@link #server()} instead.
     *
     * @return all audience members
     */
    // Début d'une méthode/d'un bloc
    public static Audience all() {
        // Renvoie une valeur à l'appelant
        return Audience.audience(audience.server, audience.customs());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all audience members that are of type {@link Player}.
     *
     * @return all players
     */
    // Début d'une méthode/d'un bloc
    public static Audience players() {
        // Renvoie une valeur à l'appelant
        return audience.players;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all audience members that are of type {@link Player} and match the predicate.
     *
     * @param filter the predicate
     * @return all players matching the predicate
     */
    // Début d'une méthode/d'un bloc
    public static Audience players(Predicate<Player> filter) {
        // Renvoie une valeur à l'appelant
        return PacketGroupingAudience.of(MinecraftServer.getConnectionManager().getOnlinePlayers().stream().filter(filter).toList());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the console as an audience.
     *
     * @return the console
     */
    // Début d'une méthode/d'un bloc
    public static Audience console() {
        // Renvoie une valeur à l'appelant
        return MinecraftServer.getCommandManager().getConsoleSender();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the combination of {@link #players()} and {@link #console()}.
     *
     * @return the audience of all players and the console
     */
    // Début d'une méthode/d'un bloc
    public static Audience server() {
        // Renvoie une valeur à l'appelant
        return audience.server;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all custom audience members.
     *
     * @return all custom audience members
     */
    // Début d'une méthode/d'un bloc
    public static Audience customs() {
        // Renvoie une valeur à l'appelant
        return Audience.audience(audience.iterable().customs());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all custom audience members stored using the given keyed object.
     *
     * @param keyed the keyed object
     * @return all custom audience members stored using the key of the object
     */
    // Début d'une méthode/d'un bloc
    public static Audience custom(Keyed keyed) {
        // Renvoie une valeur à l'appelant
        return custom(keyed.key());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all custom audience members stored using the given key.
     *
     * @param key the key
     * @return all custom audience members stored using the key
     */
    // Début d'une méthode/d'un bloc
    public static Audience custom(Key key) {
        // Renvoie une valeur à l'appelant
        return Audience.audience(audience.iterable().custom(key));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all custom audience members stored using the given keyed object that match
     * the given predicate.
     *
     * @param keyed  the keyed object
     * @param filter the predicate
     * @return all custom audience members stored using the key
     */
    // Début d'une méthode/d'un bloc
    public static Audience custom(Keyed keyed, Predicate<Audience> filter) {
        // Renvoie une valeur à l'appelant
        return custom(keyed.key(), filter);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all custom audience members stored using the given key that match the
     * given predicate.
     *
     * @param key    the key
     * @param filter the predicate
     * @return all custom audience members stored using the key
     */
    // Début d'une méthode/d'un bloc
    public static Audience custom(Key key, Predicate<Audience> filter) {
        // Renvoie une valeur à l'appelant
        return Audience.audience(audience.iterable().custom(key, filter));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all custom audience members matching the given predicate.
     *
     * @param filter the predicate
     * @return all matching custom audience members
     */
    // Début d'une méthode/d'un bloc
    public static Audience customs(Predicate<Audience> filter) {
        // Renvoie une valeur à l'appelant
        return Audience.audience(audience.iterable().customs(filter));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all audience members that match the given predicate.
     *
     * @param filter the predicate
     * @return all matching audience members
     */
    // Début d'une méthode/d'un bloc
    public static Audience all(Predicate<Audience> filter) {
        // Renvoie une valeur à l'appelant
        return Audience.audience(audience.iterable().all(filter));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the audience registry used to register custom audiences.
     *
     * @return the registry
     */
    // Début d'une méthode/d'un bloc
    public static AudienceRegistry registry() {
        // Renvoie une valeur à l'appelant
        return audience.iterable().registry();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
