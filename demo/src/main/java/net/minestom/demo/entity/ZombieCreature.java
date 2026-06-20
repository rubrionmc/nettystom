// Déclaration du paquet de ce fichier
package net.minestom.demo.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.EntityAIGroupBuilder;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.goal.RandomLookAroundGoal;

// Déclaration de type (classe/interface/enum/record)
public class ZombieCreature extends EntityCreature {

    // Début d'une méthode/d'un bloc
    public ZombieCreature() {
        // Accès à l'objet courant/parent
        super(EntityType.ZOMBIE);
        // Instruction de code
        addAIGroup(
                // Crée un nouvel objet
                new EntityAIGroupBuilder()
                        // Instruction de code
                        .addGoalSelector(new RandomLookAroundGoal(this, 20))
                        // Instruction de code
                        .build()
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
