// Déclaration du paquet de ce fichier
package net.minestom.demo.block;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.OpenSignEditorPacket;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class SignHandler implements BlockHandler {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key getKey() {
        // Renvoie une valeur à l'appelant
        return Key.key("minestom:sign");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean onInteract(Interaction interaction) {
        // Instruction de code
        interaction.getPlayer().sendPacket(
                // Crée un nouvel objet
                new OpenSignEditorPacket(
                        // Instruction de code
                        interaction.getBlockPosition(),
                        // Instruction de code
                        true
                // Fin d'un bloc/d'une expression
                )
        // Fin d'un bloc/d'une expression
        );

        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Tag<?>> getBlockEntityTags() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Tag.NBT("front_text"),
                // Instruction de code
                Tag.NBT("back_text"),
                // Instruction de code
                Tag.Boolean("is_waxed")
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
