// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class PandaMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public PandaMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getBreedTimer() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Panda.BREED_TIMER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBreedTimer(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Panda.BREED_TIMER, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getSneezeTimer() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Panda.SNEEZE_TIMER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSneezeTimer(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Panda.SNEEZE_TIMER, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getEatTimer() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Panda.EAT_TIMER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setEatTimer(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Panda.EAT_TIMER, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Gene getMainGene() {
        // Renvoie une valeur à l'appelant
        return Gene.VALUES[metadata.get(MetadataDef.Panda.MAIN_GENE)];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setMainGene(Gene value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Panda.MAIN_GENE, (byte) value.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Gene getHiddenGene() {
        // Renvoie une valeur à l'appelant
        return Gene.VALUES[metadata.get(MetadataDef.Panda.HIDDEN_GENE)];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHiddenGene(Gene value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Panda.HIDDEN_GENE, (byte) value.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSneezing() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Panda.IS_SNEEZING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSneezing(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Panda.IS_SNEEZING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isRolling() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Panda.IS_ROLLING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRolling(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Panda.IS_ROLLING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSitting() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Panda.IS_SITTING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSitting(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Panda.IS_SITTING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isOnBack() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Panda.IS_ON_BACK);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setOnBack(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Panda.IS_ON_BACK, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Gene {
        // Instruction de code
        NORMAL,
        // Instruction de code
        LAZY,
        // Instruction de code
        WORRIED,
        // Instruction de code
        PLAYFUL,
        // Instruction de code
        BROWN,
        // Instruction de code
        WEAK,
        // Instruction de code
        AGGRESSIVE;

        // Appelle une méthode
        private final static Gene[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
