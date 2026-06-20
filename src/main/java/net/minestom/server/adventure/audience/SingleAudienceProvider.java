// Package declaration for this file
package net.minestom.server.adventure.audience;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Player;

// Import of a required class
import java.util.function.Predicate;

/**
 * A provider of audiences. For complex returns, this instance is backed by
 * {@link IterableAudienceProvider}.
 */
// Type declaration (class/interface/enum/record)
class SingleAudienceProvider implements AudienceProvider<Audience> {

    // Calls a method
    protected final IterableAudienceProvider collection = new IterableAudienceProvider();
    // Calls a method
    protected final Audience players = PacketGroupingAudience.of(MinecraftServer.getConnectionManager().getOnlinePlayers());
    // Calls a method
    protected final Audience server = Audience.audience(this.players, MinecraftServer.getCommandManager().getConsoleSender());

    // Start of a method/block
    protected SingleAudienceProvider() {
    // End of a block/expression
    }

    /**
     * Gets the {@link IterableAudienceProvider} instance.
     *
     * @return the instance
     */
    // Start of a method/block
    public IterableAudienceProvider iterable() {
        // Returns a value to the caller
        return this.collection;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience all() {
        // Returns a value to the caller
        return Audience.audience(this.server, this.customs());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience players() {
        // Returns a value to the caller
        return this.players;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience players(Predicate<? super Player> filter) {
        // Returns a value to the caller
        return PacketGroupingAudience.of(MinecraftServer.getConnectionManager().getOnlinePlayers().stream().filter(filter).toList());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience console() {
        // Returns a value to the caller
        return MinecraftServer.getCommandManager().getConsoleSender();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience server() {
        // Returns a value to the caller
        return this.server;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience customs() {
        // Returns a value to the caller
        return Audience.audience(this.iterable().customs());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience custom(Key key) {
        // Returns a value to the caller
        return Audience.audience(this.iterable().custom(key));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience custom(Key key, Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return Audience.audience(this.iterable().custom(key, filter));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience customs(Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return Audience.audience(this.iterable().customs(filter));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Audience all(Predicate<? super Audience> filter) {
        // Returns a value to the caller
        return Audience.audience(this.iterable().all(filter));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public AudienceRegistry registry() {
        // Returns a value to the caller
        return this.iterable().registry();
    // End of a block/expression
    }
// End of a block/expression
}
