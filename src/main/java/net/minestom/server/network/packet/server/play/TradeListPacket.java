// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponentMap;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record TradeListPacket(int windowId, List<Trade> trades,
                              // Instruction de code
                              int villagerLevel, int experience,
                              // Début d'une méthode/d'un bloc
                              boolean regularVillager, boolean canRestock) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_TRADES = Short.MAX_VALUE;

    // Affecte une valeur
    public static final NetworkBuffer.Type<TradeListPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, TradeListPacket::windowId,
            // Instruction de code
            Trade.SERIALIZER.list(MAX_TRADES), TradeListPacket::trades,
            // Instruction de code
            VAR_INT, TradeListPacket::villagerLevel,
            // Instruction de code
            VAR_INT, TradeListPacket::experience,
            // Instruction de code
            BOOLEAN, TradeListPacket::regularVillager,
            // Instruction de code
            BOOLEAN, TradeListPacket::canRestock,
            // Instruction de code
            TradeListPacket::new);

    // Début d'une méthode/d'un bloc
    public TradeListPacket {
        // Appelle une méthode
        trades = List.copyOf(trades);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Trade(
            // Instruction de code
            ItemCost inputItem1,
            // Instruction de code
            ItemStack result,
            // Annotation pour l'élément suivant
            @Nullable ItemCost inputItem2,
            // Instruction de code
            boolean tradeDisabled,
            // Instruction de code
            int tradeUsesNumber,
            // Instruction de code
            int maxTradeUsesNumber,
            // Instruction de code
            int exp,
            // Instruction de code
            int specialPrice,
            // Instruction de code
            float priceMultiplier,
            // Instruction de code
            int demand
    // Début d'une méthode/d'un bloc
    ) {

        // Affecte une valeur
        public static final NetworkBuffer.Type<Trade> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                ItemCost.NETWORK_TYPE, Trade::inputItem1,
                // Instruction de code
                ItemStack.NETWORK_TYPE, Trade::result,
                // Instruction de code
                ItemCost.NETWORK_TYPE.optional(), Trade::inputItem2,
                // Instruction de code
                BOOLEAN, Trade::tradeDisabled,
                // Instruction de code
                INT, Trade::tradeUsesNumber,
                // Instruction de code
                INT, Trade::maxTradeUsesNumber,
                // Instruction de code
                INT, Trade::exp,
                // Instruction de code
                INT, Trade::specialPrice,
                // Instruction de code
                FLOAT, Trade::priceMultiplier,
                // Instruction de code
                INT, Trade::demand,
                // Instruction de code
                Trade::new);

        // Instruction de code
        public Trade(
                // Instruction de code
                ItemStack inputItem1,
                // Instruction de code
                ItemStack result,
                // Annotation pour l'élément suivant
                @Nullable ItemStack inputItem2,
                // Instruction de code
                boolean tradeDisabled,
                // Instruction de code
                int tradeUsesNumber,
                // Instruction de code
                int maxTradeUsesNumber,
                // Instruction de code
                int exp,
                // Instruction de code
                int specialPrice,
                // Instruction de code
                float priceMultiplier,
                // Instruction de code
                int demand
        // Début d'une méthode/d'un bloc
        ) {
            // Instruction de code
            this(
                    // Crée un nouvel objet
                    new ItemCost(inputItem1),
                    // Instruction de code
                    result,
                    // Instruction de code
                    inputItem2 == null ? null : new ItemCost(inputItem2),
                    // Instruction de code
                    tradeDisabled,
                    // Instruction de code
                    tradeUsesNumber,
                    // Instruction de code
                    maxTradeUsesNumber,
                    // Instruction de code
                    exp,
                    // Instruction de code
                    specialPrice,
                    // Instruction de code
                    priceMultiplier,
                    // Instruction de code
                    demand
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record ItemCost(Material material, int amount, DataComponentMap components) {
        // Affecte une valeur
        private static final NetworkBuffer.Type<ItemCost> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                Material.NETWORK_TYPE, ItemCost::material,
                // Instruction de code
                VAR_INT, ItemCost::amount,
                // Instruction de code
                DataComponent.MAP_NETWORK_TYPE, ItemCost::components,
                // Instruction de code
                ItemCost::new);

        // Début d'une méthode/d'un bloc
        public ItemCost(ItemStack itemStack) {
            // Appelle une méthode
            this(itemStack.material(), itemStack.amount(), itemStack.componentPatch());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
