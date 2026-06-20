// Package declaration for this file
package net.minestom.server.adventure.provider;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.text.event.ClickCallback;
// Import of a required class
import net.kyori.adventure.text.event.ClickEvent;
// Import of a required class
import net.minestom.server.MinecraftServer;

// Annotation for the following element
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Type declaration (class/interface/enum/record)
public final class MinestomClickCallbackProvider implements ClickCallback.Provider {
    // Annotation for the following element
    @Override
    // Start of a method/block
    public ClickEvent<ClickEvent.Payload.Custom> create(ClickCallback<Audience> callback, ClickCallback.Options options) {
        // Returns a value to the caller
        return MinecraftServer.getClickCallbackManager().createClickEvent(callback, options);
    // End of a block/expression
    }
// End of a block/expression
}
