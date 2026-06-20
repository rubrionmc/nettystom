// Déclaration du paquet de ce fichier
package net.minestom.demo.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class ChickenCreature extends EntityCreature {

    // Début d'une méthode/d'un bloc
    public ChickenCreature() {
        // Accès à l'objet courant/parent
        super(EntityType.CHICKEN);

        // Instruction de code
        addAIGroup(
                // Instruction de code
                List.of(
//                        new DoNothingGoal(this, 500, 0.1f),
//                        new MeleeAttackGoal(this, 500, 2, TimeUnit.MILLISECOND),
                        // Crée un nouvel objet
                        new RandomStrollGoal(this, 2)
                // Fin d'un bloc/d'une expression
                ),
                // Instruction de code
                List.of(
//                        new LastEntityDamagerTarget(this, 15),
//                        new ClosestEntityTarget(this, 15, LivingEntity.class)
                // Fin d'un bloc/d'une expression
                )
        // Fin d'un bloc/d'une expression
        );

        // Another way to register previously added EntityAIGroup, using specialized builder:
//        addAIGroup(
//                new EntityAIGroupBuilder()
//                        .addGoalSelector(new DoNothingGoal(this, 500, .1F))
//                        .addGoalSelector(new MeleeAttackGoal(this, 500, 2, TimeUnit.MILLISECOND))
//                        .addGoalSelector(new RandomStrollGoal(this, 2))
//                        .addTargetSelector(new LastEntityDamagerTarget(this, 15))
//                        .addTargetSelector(new ClosestEntityTarget(this, 15, LivingEntity.class))
//                        .build()
//        );

        // Appelle une méthode
        getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void spawn() {

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
