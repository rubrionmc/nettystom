// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Déclaration de type (classe/interface/enum/record)
public record RecipeBookSettingsPacket(boolean craftingRecipeBookOpen,boolean craftingRecipeBookFilterActive,boolean smeltingRecipeBookOpen,boolean smeltingRecipeBookFilterActive,boolean blastFurnaceRecipeBookOpen,boolean blastFurnaceRecipeBookFilterActive,boolean smokerRecipeBookOpen,boolean smokerRecipeBookFilterActive) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<RecipeBookSettingsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BOOLEAN, RecipeBookSettingsPacket::craftingRecipeBookOpen,
            // Instruction de code
            BOOLEAN, RecipeBookSettingsPacket::craftingRecipeBookFilterActive,
            // Instruction de code
            BOOLEAN, RecipeBookSettingsPacket::smeltingRecipeBookOpen,
            // Instruction de code
            BOOLEAN, RecipeBookSettingsPacket::smeltingRecipeBookFilterActive,
            // Instruction de code
            BOOLEAN, RecipeBookSettingsPacket::blastFurnaceRecipeBookOpen,
            // Instruction de code
            BOOLEAN, RecipeBookSettingsPacket::blastFurnaceRecipeBookFilterActive,
            // Instruction de code
            BOOLEAN, RecipeBookSettingsPacket::smokerRecipeBookOpen,
            // Instruction de code
            BOOLEAN, RecipeBookSettingsPacket::smokerRecipeBookFilterActive,
            // Instruction de code
            RecipeBookSettingsPacket::new);

// Fin d'un bloc/d'une expression
}
