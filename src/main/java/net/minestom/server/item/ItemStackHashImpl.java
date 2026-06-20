// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Set;

//TODO(1.21.5) hashes of components should be cached. Vanilla does it on a per player basis, could also do it globally perhaps.
// Déclaration de type (classe/interface/enum/record)
final class ItemStackHashImpl {

    // Début d'une méthode/d'un bloc
    public static ItemStack.Hash of(Transcoder<Integer> hashCoder, ItemStack itemStack) {
        // Embranchement : vérifie une condition
        if (itemStack.isAir()) return net.minestom.server.item.ItemStack.Hash.AIR;

        // Affecte une valeur
        final Map<DataComponent<?>, Integer> addedComponents = new HashMap<>();
        // Affecte une valeur
        final Set<DataComponent<?>> removedComponents = new HashSet<>();
        // Boucle : répète un bloc
        for (var entry : itemStack.componentPatch().entrySet()) {
            // Embranchement : vérifie une condition
            if (entry.value() != null) {
                // Appelle une méthode
                addedComponents.put(entry.component(), ((DataComponent<Object>) entry.component()).encode(hashCoder, entry.value()).orElseThrow());
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                removedComponents.add(entry.component());
            // Fin d'un bloc/d'une expression
            }

        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new ItemStackHashImpl.Item(
                // Instruction de code
                itemStack.material(),
                // Instruction de code
                itemStack.amount(),
                // Instruction de code
                addedComponents,
                // Instruction de code
                removedComponents
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<ItemStack.Hash> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, ItemStack.Hash value) {
            // Embranchement : vérifie une condition
            if (!(value instanceof Item item)) {
                // Appelle une méthode
                buffer.write(NetworkBuffer.BOOLEAN, false);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            buffer.write(NetworkBuffer.BOOLEAN, true);
            // Appelle une méthode
            buffer.write(Item.NETWORK_TYPE, item);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ItemStack.Hash read(NetworkBuffer buffer) {
            // Embranchement : vérifie une condition
            if (!buffer.read(NetworkBuffer.BOOLEAN))
                // Renvoie une valeur à l'appelant
                return ItemStack.Hash.AIR;
            // Renvoie une valeur à l'appelant
            return buffer.read(Item.NETWORK_TYPE);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Déclaration de type (classe/interface/enum/record)
    record Air() implements ItemStack.Hash {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Item(
            // Instruction de code
            Material material,
            // Instruction de code
            int amount,
            // Instruction de code
            Map<DataComponent<?>, Integer> addedComponents,
            // Instruction de code
            Set<DataComponent<?>> removedComponents
    // Début d'une méthode/d'un bloc
    ) implements ItemStack.Hash {
        // Affecte une valeur
        private static final int MAX_COMPONENTS = 256;
        // Affecte une valeur
        public static final NetworkBuffer.Type<Item> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                Material.NETWORK_TYPE, Item::material,
                // Instruction de code
                NetworkBuffer.VAR_INT, Item::amount,
                // Instruction de code
                DataComponent.NETWORK_TYPE.mapValue(NetworkBuffer.INT, MAX_COMPONENTS), Item::addedComponents,
                // Instruction de code
                DataComponent.NETWORK_TYPE.set(MAX_COMPONENTS), Item::removedComponents,
                // Instruction de code
                Item::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
