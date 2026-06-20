// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.dialog.Dialog;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.registry.Holder;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public record ShowDialogPacket(
        // Code statement
        Holder<Dialog> dialog
// Start of a method/block
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ShowDialogPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Dialog.NETWORK_TYPE, ShowDialogPacket::dialog,
            // Code statement
            ShowDialogPacket::new);

    // Assigns a value
    public static final NetworkBuffer.Type<ShowDialogPacket> INLINE_SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Dialog.REGISTRY_NETWORK_TYPE, (dialog) -> Objects.requireNonNull(dialog.dialog().asValue(), "Dialog holder must be direct during inline serialization"),
            // Code statement
            ShowDialogPacket::new
    // End of a block/expression
    );
// End of a block/expression
}
