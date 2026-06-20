// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai.goal;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.GoalSelector;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Déclaration de type (classe/interface/enum/record)
public class RandomStrollGoal extends GoalSelector {

    // Appelle une méthode
    private static final long DELAY = TimeUnit.MILLISECONDS.toNanos(2500);

    // Instruction de code
    private final int radius;
    // Instruction de code
    private final List<Vec> closePositions;
    // Appelle une méthode
    private final Random random = new Random();

    // Instruction de code
    private long lastStroll;

    // Début d'une méthode/d'un bloc
    public RandomStrollGoal(EntityCreature entityCreature, int radius) {
        // Accès à l'objet courant/parent
        super(entityCreature);
        // Accès à l'objet courant/parent
        this.radius = radius;
        // Accès à l'objet courant/parent
        this.closePositions = getNearbyBlocks(radius);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldStart() {
        // Renvoie une valeur à l'appelant
        return System.nanoTime() - lastStroll >= DELAY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void start() {
        // Appelle une méthode
        int remainingAttempt = closePositions.size();
        // Boucle : répète un bloc
        while (remainingAttempt-- > 0) {
            // Appelle une méthode
            final int index = random.nextInt(closePositions.size());
            // Appelle une méthode
            final Vec position = closePositions.get(index);

            // Appelle une méthode
            final var target = entityCreature.getPosition().add(position);
            // Appelle une méthode
            final boolean result = entityCreature.getNavigator().setPathTo(target);
            // Embranchement : vérifie une condition
            if (result) {
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shouldEnd() {
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void end() {
        // Accès à l'objet courant/parent
        this.lastStroll = System.nanoTime();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getRadius() {
        // Renvoie une valeur à l'appelant
        return radius;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static List<Vec> getNearbyBlocks(int radius) {
        // Appelle une méthode
        List<Vec> blocks = new ArrayList<>();
        // Boucle : répète un bloc
        for (int x = -radius; x <= radius; x++) {
            // Boucle : répète un bloc
            for (int y = -radius; y <= radius; y++) {
                // Boucle : répète un bloc
                for (int z = -radius; z <= radius; z++) {
                    // Appelle une méthode
                    blocks.add(new Vec(x, y, z));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return blocks;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
