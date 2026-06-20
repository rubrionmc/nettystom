// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.kyori.adventure.resource.ResourcePackInfo;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.COMPONENT;

// Type declaration (class/interface/enum/record)
public record ResourcePackPushPacket(
        // Code statement
        UUID id,
        // Code statement
        String url,
        // Code statement
        String hash,
        // Code statement
        boolean forced,
        // Annotation for the following element
        @Nullable Component prompt
// Start of a method/block
) implements ServerPacket.Configuration, ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<ResourcePackPushPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.UUID, ResourcePackPushPacket::id,
            // Code statement
            NetworkBuffer.STRING, ResourcePackPushPacket::url,
            // Code statement
            NetworkBuffer.STRING, ResourcePackPushPacket::hash,
            // Code statement
            NetworkBuffer.BOOLEAN, ResourcePackPushPacket::forced,
            // Code statement
            COMPONENT.optional(), ResourcePackPushPacket::prompt,
            // Code statement
            ResourcePackPushPacket::new);

    // Start of a method/block
    public ResourcePackPushPacket(ResourcePackInfo resourcePackInfo, boolean required, @Nullable Component prompt) {
        // Calls a method
        this(resourcePackInfo.id(), resourcePackInfo.uri().toString(), resourcePackInfo.hash(), required, prompt);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Returns a value to the caller
        return this.prompt == null ? List.of() : List.of(this.prompt);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return new ResourcePackPushPacket(this.id, this.url, this.hash, this.forced, this.prompt == null ? null : operator.apply(this.prompt));
    // End of a block/expression
    }
// End of a block/expression
}
