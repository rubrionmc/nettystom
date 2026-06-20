// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.minecart;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class CommandBlockMinecartMeta extends AbstractMinecartMeta {
    // Début d'une méthode/d'un bloc
    public CommandBlockMinecartMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getCommand() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.MinecartCommandBlock.COMMAND);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCommand(String value) {
        // Appelle une méthode
        metadata.set(MetadataDef.MinecartCommandBlock.COMMAND, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Component getLastOutput() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.MinecartCommandBlock.LAST_OUTPUT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLastOutput(Component value) {
        // Appelle une méthode
        metadata.set(MetadataDef.MinecartCommandBlock.LAST_OUTPUT, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
