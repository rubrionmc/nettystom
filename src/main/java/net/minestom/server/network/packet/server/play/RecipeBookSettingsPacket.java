// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Type declaration (class/interface/enum/record)
public record RecipeBookSettingsPacket(boolean craftingRecipeBookOpen,boolean craftingRecipeBookFilterActive,boolean smeltingRecipeBookOpen,boolean smeltingRecipeBookFilterActive,boolean blastFurnaceRecipeBookOpen,boolean blastFurnaceRecipeBookFilterActive,boolean smokerRecipeBookOpen,boolean smokerRecipeBookFilterActive) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<RecipeBookSettingsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BOOLEAN, RecipeBookSettingsPacket::craftingRecipeBookOpen,
            // Code statement
            BOOLEAN, RecipeBookSettingsPacket::craftingRecipeBookFilterActive,
            // Code statement
            BOOLEAN, RecipeBookSettingsPacket::smeltingRecipeBookOpen,
            // Code statement
            BOOLEAN, RecipeBookSettingsPacket::smeltingRecipeBookFilterActive,
            // Code statement
            BOOLEAN, RecipeBookSettingsPacket::blastFurnaceRecipeBookOpen,
            // Code statement
            BOOLEAN, RecipeBookSettingsPacket::blastFurnaceRecipeBookFilterActive,
            // Code statement
            BOOLEAN, RecipeBookSettingsPacket::smokerRecipeBookOpen,
            // Code statement
            BOOLEAN, RecipeBookSettingsPacket::smokerRecipeBookFilterActive,
            // Code statement
            RecipeBookSettingsPacket::new);

// End of a block/expression
}
