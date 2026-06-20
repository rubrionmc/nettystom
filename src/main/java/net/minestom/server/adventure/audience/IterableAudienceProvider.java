// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.audience;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.ConsoleSender;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArrayList;
// Import d'une classe nécessaire
import java.util.function.Predicate;
// Import d'une classe nécessaire
import java.util.stream.StreamSupport;

/**
 * A provider of iterable audiences.
 */
// Déclaration de type (classe/interface/enum/record)
class IterableAudienceProvider implements AudienceProvider<Iterable<? extends Audience>> {
    // Appelle une méthode
    private final List<ConsoleSender> console = List.of(MinecraftServer.getCommandManager().getConsoleSender());
    // Appelle une méthode
    private final AudienceRegistry registry = new AudienceRegistry(new ConcurrentHashMap<>(), CopyOnWriteArrayList::new);

    // Début d'une méthode/d'un bloc
    protected IterableAudienceProvider() {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> all() {
        // Appelle une méthode
        List<Audience> all = new ArrayList<>();
        // Accès à l'objet courant/parent
        this.players().forEach(all::add);
        // Accès à l'objet courant/parent
        this.console().forEach(all::add);
        // Accès à l'objet courant/parent
        this.customs().forEach(all::add);
        // Renvoie une valeur à l'appelant
        return all;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> players() {
        // Renvoie une valeur à l'appelant
        return MinecraftServer.getConnectionManager().getOnlinePlayers();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> players(Predicate<? super Player> filter) {
        // Renvoie une valeur à l'appelant
        return MinecraftServer.getConnectionManager().getOnlinePlayers().stream().filter(filter).toList();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> console() {
        // Renvoie une valeur à l'appelant
        return this.console;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> server() {
        // Appelle une méthode
        List<Audience> all = new ArrayList<>();
        // Accès à l'objet courant/parent
        this.players().forEach(all::add);
        // Accès à l'objet courant/parent
        this.console().forEach(all::add);
        // Renvoie une valeur à l'appelant
        return all;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> customs() {
        // Renvoie une valeur à l'appelant
        return this.registry.all();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> custom(Key key) {
        // Renvoie une valeur à l'appelant
        return this.registry.of(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> custom(Key key, Predicate<? super Audience> filter) {
        // Renvoie une valeur à l'appelant
        return StreamSupport.stream(this.registry.of(key).spliterator(), false).filter(filter).toList();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> customs(Predicate<? super Audience> filter) {
        // Renvoie une valeur à l'appelant
        return this.registry.of(filter);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterable<? extends Audience> all(Predicate<? super Audience> filter) {
        // Renvoie une valeur à l'appelant
        return StreamSupport.stream(this.all().spliterator(), false).filter(filter).toList();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public AudienceRegistry registry() {
        // Renvoie une valeur à l'appelant
        return this.registry;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
