// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponentMap;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record TradeListPacket(int windowId, List<Trade> trades,
                              // Code statement
                              int villagerLevel, int experience,
                              // Start of a method/block
                              boolean regularVillager, boolean canRestock) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_TRADES = Short.MAX_VALUE;

    // Assigns a value
    public static final NetworkBuffer.Type<TradeListPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, TradeListPacket::windowId,
            // Code statement
            Trade.SERIALIZER.list(MAX_TRADES), TradeListPacket::trades,
            // Code statement
            VAR_INT, TradeListPacket::villagerLevel,
            // Code statement
            VAR_INT, TradeListPacket::experience,
            // Code statement
            BOOLEAN, TradeListPacket::regularVillager,
            // Code statement
            BOOLEAN, TradeListPacket::canRestock,
            // Code statement
            TradeListPacket::new);

    // Start of a method/block
    public TradeListPacket {
        // Calls a method
        trades = List.copyOf(trades);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Trade(
            // Code statement
            ItemCost inputItem1,
            // Code statement
            ItemStack result,
            // Annotation for the following element
            @Nullable ItemCost inputItem2,
            // Code statement
            boolean tradeDisabled,
            // Code statement
            int tradeUsesNumber,
            // Code statement
            int maxTradeUsesNumber,
            // Code statement
            int exp,
            // Code statement
            int specialPrice,
            // Code statement
            float priceMultiplier,
            // Code statement
            int demand
    // Start of a method/block
    ) {

        // Assigns a value
        public static final NetworkBuffer.Type<Trade> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                ItemCost.NETWORK_TYPE, Trade::inputItem1,
                // Code statement
                ItemStack.NETWORK_TYPE, Trade::result,
                // Code statement
                ItemCost.NETWORK_TYPE.optional(), Trade::inputItem2,
                // Code statement
                BOOLEAN, Trade::tradeDisabled,
                // Code statement
                INT, Trade::tradeUsesNumber,
                // Code statement
                INT, Trade::maxTradeUsesNumber,
                // Code statement
                INT, Trade::exp,
                // Code statement
                INT, Trade::specialPrice,
                // Code statement
                FLOAT, Trade::priceMultiplier,
                // Code statement
                INT, Trade::demand,
                // Code statement
                Trade::new);

        // Code statement
        public Trade(
                // Code statement
                ItemStack inputItem1,
                // Code statement
                ItemStack result,
                // Annotation for the following element
                @Nullable ItemStack inputItem2,
                // Code statement
                boolean tradeDisabled,
                // Code statement
                int tradeUsesNumber,
                // Code statement
                int maxTradeUsesNumber,
                // Code statement
                int exp,
                // Code statement
                int specialPrice,
                // Code statement
                float priceMultiplier,
                // Code statement
                int demand
        // Start of a method/block
        ) {
            // Code statement
            this(
                    // Creates a new object
                    new ItemCost(inputItem1),
                    // Code statement
                    result,
                    // Code statement
                    inputItem2 == null ? null : new ItemCost(inputItem2),
                    // Code statement
                    tradeDisabled,
                    // Code statement
                    tradeUsesNumber,
                    // Code statement
                    maxTradeUsesNumber,
                    // Code statement
                    exp,
                    // Code statement
                    specialPrice,
                    // Code statement
                    priceMultiplier,
                    // Code statement
                    demand
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record ItemCost(Material material, int amount, DataComponentMap components) {
        // Assigns a value
        private static final NetworkBuffer.Type<ItemCost> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                Material.NETWORK_TYPE, ItemCost::material,
                // Code statement
                VAR_INT, ItemCost::amount,
                // Code statement
                DataComponent.MAP_NETWORK_TYPE, ItemCost::components,
                // Code statement
                ItemCost::new);

        // Start of a method/block
        public ItemCost(ItemStack itemStack) {
            // Calls a method
            this(itemStack.material(), itemStack.amount(), itemStack.componentPatch());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
