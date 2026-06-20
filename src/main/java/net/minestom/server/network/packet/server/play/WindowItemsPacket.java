// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record WindowItemsPacket(int windowId, int stateId, List<ItemStack> items,
                                // Début d'une méthode/d'un bloc
                                ItemStack carriedItem) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final int MAX_ENTRIES = 128;

    // Affecte une valeur
    public static final NetworkBuffer.Type<WindowItemsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, WindowItemsPacket::windowId,
            // Instruction de code
            VAR_INT, WindowItemsPacket::stateId,
            // Instruction de code
            ItemStack.NETWORK_TYPE.list(MAX_ENTRIES), WindowItemsPacket::items,
            // Instruction de code
            ItemStack.NETWORK_TYPE, WindowItemsPacket::carriedItem,
            // Instruction de code
            WindowItemsPacket::new);

    // Début d'une méthode/d'un bloc
    public WindowItemsPacket {
        // Appelle une méthode
        items = List.copyOf(items);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Affecte une valeur
        final var list = new ArrayList<>(this.items);
        // Appelle une méthode
        list.add(this.carriedItem);

        // Affecte une valeur
        final var components = new ArrayList<Component>();

        // Début d'une méthode/d'un bloc
        list.forEach(itemStack -> {
            // Appelle une méthode
            components.addAll(itemStack.get(DataComponents.LORE, List.of()));

            // Appelle une méthode
            final var customName = itemStack.get(DataComponents.CUSTOM_NAME);
            // Embranchement : vérifie une condition
            if (customName != null) {
                // Appelle une méthode
                components.add(customName);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final var itemName = itemStack.get(DataComponents.ITEM_NAME);
            // Embranchement : vérifie une condition
            if (itemName != null) {
                // Appelle une méthode
                components.add(itemName);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Renvoie une valeur à l'appelant
        return components;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Affecte une valeur
        UnaryOperator<List<Component>> loreOperator = lines -> {
            // Affecte une valeur
            final var translatedComponents = new ArrayList<Component>();
            // Appelle une méthode
            lines.forEach(component -> translatedComponents.add(operator.apply(component)));
            // Renvoie une valeur à l'appelant
            return translatedComponents;
        // Fin d'un bloc/d'une expression
        };
        // Renvoie une valeur à l'appelant
        return new WindowItemsPacket(
                // Accès à l'objet courant/parent
                this.windowId,
                // Accès à l'objet courant/parent
                this.stateId,
                // Accès à l'objet courant/parent
                this.items.stream().map(stack -> stack
                                // Instruction de code
                                .with(DataComponents.ITEM_NAME, operator)
                                // Instruction de code
                                .with(DataComponents.CUSTOM_NAME, operator)
                                // Instruction de code
                                .with(DataComponents.LORE, loreOperator))
                        // Instruction de code
                        .toList(),
                // Accès à l'objet courant/parent
                this.carriedItem
                        // Instruction de code
                        .with(DataComponents.ITEM_NAME, operator)
                        // Instruction de code
                        .with(DataComponents.CUSTOM_NAME, operator)
                        // Instruction de code
                        .with(DataComponents.LORE, loreOperator)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
