// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.SHORT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record SetSlotPacket(int windowId, int stateId, short slot,
                            // Début d'une méthode/d'un bloc
                            ItemStack itemStack) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SetSlotPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, SetSlotPacket::windowId,
            // Instruction de code
            VAR_INT, SetSlotPacket::stateId,
            // Instruction de code
            SHORT, SetSlotPacket::slot,
            // Instruction de code
            ItemStack.NETWORK_TYPE, SetSlotPacket::itemStack,
            // Instruction de code
            SetSlotPacket::new);

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return ItemStack.textComponents(itemStack);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return new SetSlotPacket(this.windowId, this.stateId, this.slot, ItemStack.copyWithOperator(this.itemStack, operator));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
