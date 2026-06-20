// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.PrimedTntMeta;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Déclaration de type (classe/interface/enum/record)
public class PrimedTNTCommand extends Command {
    // Début d'une méthode/d'un bloc
    public PrimedTNTCommand() {
        // Accès à l'objet courant/parent
        super("primedtnt");

        // Début d'une méthode/d'un bloc
        setDefaultExecutor((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;

            // Appelle une méthode
            Entity entity = new Entity(EntityType.TNT);
            // Début d'une méthode/d'un bloc
            entity.editEntityMeta(PrimedTntMeta.class, meta -> {
                // Appelle une méthode
                meta.setFuseTime(60);
                // Appelle une méthode
                meta.setBlockState(Block.STONE);
            // Fin d'un bloc/d'une expression
            });

            // Appelle une méthode
            entity.setInstance(player.getInstance(), player.getPosition());
        // Fin d'un bloc/d'une expression
        });

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
