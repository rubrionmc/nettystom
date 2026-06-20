// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.minestom.server.dialog.Dialog;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public record ShowDialogPacket(
        // Instruction de code
        Holder<Dialog> dialog
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ShowDialogPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Dialog.NETWORK_TYPE, ShowDialogPacket::dialog,
            // Instruction de code
            ShowDialogPacket::new);

    // Affecte une valeur
    public static final NetworkBuffer.Type<ShowDialogPacket> INLINE_SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Dialog.REGISTRY_NETWORK_TYPE, (dialog) -> Objects.requireNonNull(dialog.dialog().asValue(), "Dialog holder must be direct during inline serialization"),
            // Instruction de code
            ShowDialogPacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
