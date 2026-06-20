// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.MobMeta;

// Déclaration de type (classe/interface/enum/record)
public class EnderDragonMeta extends MobMeta {
    // Début d'une méthode/d'un bloc
    public EnderDragonMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Phase getPhase() {
        // Renvoie une valeur à l'appelant
        return Phase.VALUES[metadata.get(MetadataDef.EnderDragon.DRAGON_PHASE)];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setPhase(Phase value) {
        // Appelle une méthode
        metadata.set(MetadataDef.EnderDragon.DRAGON_PHASE, value.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Phase {
        // Instruction de code
        CIRCLING,
        // Instruction de code
        STRAFING,
        // Instruction de code
        FLYING_TO_THE_PORTAL,
        // Instruction de code
        LANDING_ON_THE_PORTAL,
        // Instruction de code
        TAKING_OFF_FROM_THE_PORTAL,
        // Instruction de code
        BREATH_ATTACK,
        // Instruction de code
        LOOKING_FOR_BREATH_ATTACK_PLAYER,
        // Instruction de code
        ROAR,
        // Instruction de code
        CHARGING_PLAYER,
        // Instruction de code
        FLYING_TO_THE_PORTAL_TO_DIE,
        // Instruction de code
        HOVERING_WITHOUT_AI;

        // Appelle une méthode
        private final static Phase[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
