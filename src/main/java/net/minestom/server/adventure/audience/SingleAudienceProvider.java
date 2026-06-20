// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.audience;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Import d'une classe nécessaire
import java.util.function.Predicate;

/**
 * A provider of audiences. For complex returns, this instance is backed by
 * {@link IterableAudienceProvider}.
 */
// Déclaration de type (classe/interface/enum/record)
class SingleAudienceProvider implements AudienceProvider<Audience> {

    // Appelle une méthode
    protected final IterableAudienceProvider collection = new IterableAudienceProvider();
    // Appelle une méthode
    protected final Audience players = PacketGroupingAudience.of(MinecraftServer.getConnectionManager().getOnlinePlayers());
    // Appelle une méthode
    protected final Audience server = Audience.audience(this.players, MinecraftServer.getCommandManager().getConsoleSender());

    // Début d'une méthode/d'un bloc
    protected SingleAudienceProvider() {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link IterableAudienceProvider} instance.
     *
     * @return the instance
     */
    // Début d'une méthode/d'un bloc
    public IterableAudienceProvider iterable() {
        // Renvoie une valeur à l'appelant
        return this.collection;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience all() {
        // Renvoie une valeur à l'appelant
        return Audience.audience(this.server, this.customs());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience players() {
        // Renvoie une valeur à l'appelant
        return this.players;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience players(Predicate<Player> filter) {
        // Renvoie une valeur à l'appelant
        return PacketGroupingAudience.of(MinecraftServer.getConnectionManager().getOnlinePlayers().stream().filter(filter).toList());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience console() {
        // Renvoie une valeur à l'appelant
        return MinecraftServer.getCommandManager().getConsoleSender();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience server() {
        // Renvoie une valeur à l'appelant
        return this.server;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience customs() {
        // Renvoie une valeur à l'appelant
        return Audience.audience(this.iterable().customs());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience custom(Key key) {
        // Renvoie une valeur à l'appelant
        return Audience.audience(this.iterable().custom(key));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience custom(Key key, Predicate<Audience> filter) {
        // Renvoie une valeur à l'appelant
        return Audience.audience(this.iterable().custom(key, filter));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience customs(Predicate<Audience> filter) {
        // Renvoie une valeur à l'appelant
        return Audience.audience(this.iterable().customs(filter));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Audience all(Predicate<Audience> filter) {
        // Renvoie une valeur à l'appelant
        return Audience.audience(this.iterable().all(filter));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public AudienceRegistry registry() {
        // Renvoie une valeur à l'appelant
        return this.iterable().registry();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
