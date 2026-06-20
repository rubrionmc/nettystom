// Package declaration for this file
package net.minestom.server.adventure.audience;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.ConsoleSender;
// Import of a required class
import net.minestom.server.entity.Player;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.CopyOnWriteArrayList;
// Import of a required class
import java.util.function.Predicate;
// Import of a required class
import java.util.stream.StreamSupport;

/**
 * A provider of iterable audiences.
 */
// Type declaration (class/interface/enum/record)
class IterableAudienceProvider implements AudienceProvider<Iterable<? extends Audience>> {
    // Calls a method
    private final List<ConsoleSender> console = List.of(MinecraftServer.getCommandManager().getConsoleSender());
    // Calls a method
    private final AudienceRegistry registry = new AudienceRegistry(new ConcurrentHashMap<>(), CopyOnWriteArrayList::new);

    // Start of a method/block
    protected IterableAudienceProvider() {
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> all() {
        // Calls a method
        List<Audience> all = new ArrayList<>();
        // Access to the current/parent object
        this.players().forEach(all::add);
        // Access to the current/parent object
        this.console().forEach(all::add);
        // Access to the current/parent object
        this.customs().forEach(all::add);
        // Returns a value to the caller
        return all;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> players() {
        // Returns a value to the caller
        return MinecraftServer.getConnectionManager().getOnlinePlayers();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> players(Predicate<? super Player> filter) {
        // Returns a value to the caller
        return MinecraftServer.getConnectionManager().getOnlinePlayers().stream().filter(filter).toList();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> console() {
        // Returns a value to the caller
        return this.console;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> server() {
        // Calls a method
        List<Audience> all = new ArrayList<>();
        // Access to the current/parent object
        this.players().forEach(all::add);
        // Access to the current/parent object
        this.console().forEach(all::add);
        // Returns a value to the caller
        return all;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> customs() {
        // Returns a value to the caller
        return this.registry.all();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> custom(Key key) {
        // Returns a value to the caller
        return this.registry.of(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> custom(Key key, Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return StreamSupport.stream(this.registry.of(key).spliterator(), false).filter(filter).toList();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> customs(Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return this.registry.of(filter);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterable<? extends Audience> all(Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return StreamSupport.stream(this.all().spliterator(), false).filter(filter).toList();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public AudienceRegistry registry() {
        // Returns a value to the caller
        return this.registry;
    // End of a block/expression
    }
// End of a block/expression
}
