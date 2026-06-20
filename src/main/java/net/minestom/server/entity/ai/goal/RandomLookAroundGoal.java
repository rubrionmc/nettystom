// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai.goal;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.GoalSelector;

// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Déclaration de type (classe/interface/enum/record)
public class RandomLookAroundGoal extends GoalSelector {
    // Appelle une méthode
    private static final Random RANDOM = new Random();
    // Instruction de code
    private final int chancePerTick;
    // Instruction de code
    private final Supplier<Integer> minimalLookTimeSupplier;
    // Instruction de code
    private final Function<EntityCreature, Vec> randomDirectionFunction;
    // Instruction de code
    private Vec lookDirection;
    // Affecte une valeur
    private int lookTime = 0;

    // Début d'une méthode/d'un bloc
    public RandomLookAroundGoal(EntityCreature entityCreature, int chancePerTick) {
        // Instruction de code
        this(entityCreature, chancePerTick,
                // These two functions act similarly enough to how MC randomly looks around.

                // Look in one direction for at most 40 ticks and at minimum 20 ticks.
                // Instruction de code
                () -> 20 + RANDOM.nextInt(20),
                // Look at a random block
                // Début d'une méthode/d'un bloc
                (creature) -> {
                    // Appelle une méthode
                    final double n = Math.PI * 2 * RANDOM.nextDouble();
                    // Renvoie une valeur à l'appelant
                    return new Vec(
                            // Instruction de code
                            (float) Math.cos(n),
                            // Instruction de code
                            0,
                            // Instruction de code
                            (float) Math.sin(n)
                    // Fin d'un bloc/d'une expression
                    );
                // Fin d'un bloc/d'une expression
                });
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param entityCreature          Creature that should randomly look around.
     * @param chancePerTick           The chance (per tick) that the entity looks around. Setting this to N would mean there is a 1 in N chance.
     * @param minimalLookTimeSupplier A supplier that returns the minimal amount of time an entity looks in a direction.
     * @param randomDirectionFunction A function that returns a random vector that the entity will look in/at.
     */
    // Instruction de code
    public RandomLookAroundGoal(
            // Instruction de code
            EntityCreature entityCreature,
            // Instruction de code
            int chancePerTick,
            // Instruction de code
            Supplier<Integer> minimalLookTimeSupplier,
            // Début d'une méthode/d'un bloc
            Function<EntityCreature, Vec> randomDirectionFunction) {
        // Accès à l'objet courant/parent
        super(entityCreature);
        // Accès à l'objet courant/parent
        this.chancePerTick = chancePerTick;
        // Accès à l'objet courant/parent
        this.minimalLookTimeSupplier = minimalLookTimeSupplier;
        // Accès à l'objet courant/parent
        this.randomDirectionFunction = randomDirectionFunction;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldStart() {
        // Embranchement : vérifie une condition
        if (RANDOM.nextInt(chancePerTick) != 0) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return entityCreature.getNavigator().getPathPosition() == null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void start() {
        // Appelle une méthode
        lookTime = minimalLookTimeSupplier.get();
        // Appelle une méthode
        lookDirection = randomDirectionFunction.apply(entityCreature);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Instruction de code
        --lookTime;
        // Appelle une méthode
        entityCreature.refreshPosition(entityCreature.getPosition().withDirection(lookDirection));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldEnd() {
        // Renvoie une valeur à l'appelant
        return this.lookTime < 0;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void end() {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
