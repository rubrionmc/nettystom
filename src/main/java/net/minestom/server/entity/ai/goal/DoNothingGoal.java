// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai.goal;

// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.GoalSelector;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;

// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Déclaration de type (classe/interface/enum/record)
public class DoNothingGoal extends GoalSelector {

    // Appelle une méthode
    private static final Random RANDOM = new Random();

    // Instruction de code
    private final long time;
    // Instruction de code
    private final float chance;
    // Instruction de code
    private long startTime;

    /**
     * Create a DoNothing goal
     *
     * @param entityCreature the entity
     * @param time           the time in milliseconds where nothing happen
     * @param chance         the chance to do nothing (0-1)
     */
    // Début d'une méthode/d'un bloc
    public DoNothingGoal(EntityCreature entityCreature, long time, float chance) {
        // Accès à l'objet courant/parent
        super(entityCreature);
        // Accès à l'objet courant/parent
        this.time = TimeUnit.MILLISECONDS.toNanos(time);
        // Accès à l'objet courant/parent
        this.chance = MathUtils.clamp(chance, 0, 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void end() {
        // Accès à l'objet courant/parent
        this.startTime = 0;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldEnd() {
        // Renvoie une valeur à l'appelant
        return System.nanoTime() - startTime >= time;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldStart() {
        // Renvoie une valeur à l'appelant
        return RANDOM.nextFloat() <= chance;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void start() {
        // Accès à l'objet courant/parent
        this.startTime = System.nanoTime();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
