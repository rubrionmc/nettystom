// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai;

// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

/**
 * Represents a group of entity's AI.
 * It may contains {@link GoalSelector goal selectors} and {@link TargetSelector target selectors}.
 * All AI groups of a single entity are independent of each other.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityAIGroup {

    // Instruction de code
    private GoalSelector currentGoalSelector;
    // Appelle une méthode
    private final List<GoalSelector> goalSelectors = new GoalSelectorsArrayList();
    // Appelle une méthode
    private final List<TargetSelector> targetSelectors = new ArrayList<>();

    /**
     * Gets the goal selectors of this group.
     *
     * @return a modifiable list containing this group goal selectors
     */
    // Début d'une méthode/d'un bloc
    public List<GoalSelector> getGoalSelectors() {
        // Renvoie une valeur à l'appelant
        return this.goalSelectors;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the target selectors of this group.
     *
     * @return a modifiable list containing this group target selectors
     */
    // Début d'une méthode/d'un bloc
    public List<TargetSelector> getTargetSelectors() {
        // Renvoie une valeur à l'appelant
        return this.targetSelectors;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the current goal selector of this group.
     *
     * @return the current goal selector of this group, null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public GoalSelector getCurrentGoalSelector() {
        // Renvoie une valeur à l'appelant
        return this.currentGoalSelector;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the current goal selector of this group.
     * <p>
     * Mostly unsafe since the current goal selector should normally
     * be chosen during the group tick method.
     *
     * @param goalSelector the new goal selector of this group, null to disable it
     */
    // Début d'une méthode/d'un bloc
    public void setCurrentGoalSelector(@Nullable GoalSelector goalSelector) {
        // Instruction de code
        Check.argCondition(
                // Instruction de code
                goalSelector != null && goalSelector.getAIGroup() != this,
                // Instruction de code
                "Tried to set goal selector attached to another AI group!"
        // Fin d'un bloc/d'une expression
        );
        // Accès à l'objet courant/parent
        this.currentGoalSelector = goalSelector;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Appelle une méthode
        GoalSelector currentGoalSelector = getCurrentGoalSelector();

        // Embranchement : vérifie une condition
        if (currentGoalSelector != null && currentGoalSelector.shouldEnd()) {
            // Appelle une méthode
            currentGoalSelector.end();
            // Affecte une valeur
            currentGoalSelector = null;
            // Appelle une méthode
            setCurrentGoalSelector(null);
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (GoalSelector selector : getGoalSelectors()) {
            // Embranchement : vérifie une condition
            if (selector == currentGoalSelector) {
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (selector.shouldStart()) {
                // Embranchement : vérifie une condition
                if (currentGoalSelector != null) {
                    // Appelle une méthode
                    currentGoalSelector.end();
                // Fin d'un bloc/d'une expression
                }
                // Affecte une valeur
                currentGoalSelector = selector;
                // Appelle une méthode
                setCurrentGoalSelector(currentGoalSelector);
                // Appelle une méthode
                currentGoalSelector.start();
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (currentGoalSelector != null) {
            // Appelle une méthode
            currentGoalSelector.tick(time);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * The purpose of this list is to guarantee that every {@link GoalSelector} added to that group
     * has a reference to it for some internal interactions. We don't provide developers with
     * methods like `addGoalSelector` or `removeGoalSelector`: instead we provide them with direct
     * access to list of goal selectors, so that they could use operations such as `clear`, `set`, `removeIf`, etc.
     */
    // Déclaration de type (classe/interface/enum/record)
    private class GoalSelectorsArrayList extends ArrayList<GoalSelector> {

        // Début d'une méthode/d'un bloc
        private GoalSelectorsArrayList() {
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public GoalSelector set(int index, GoalSelector element) {
            // Appelle une méthode
            element.setAIGroup(EntityAIGroup.this);
            // Renvoie une valeur à l'appelant
            return super.set(index, element);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean add(GoalSelector element) {
            // Appelle une méthode
            element.setAIGroup(EntityAIGroup.this);
            // Renvoie une valeur à l'appelant
            return super.add(element);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void add(int index, GoalSelector element) {
            // Appelle une méthode
            element.setAIGroup(EntityAIGroup.this);
            // Accès à l'objet courant/parent
            super.add(index, element);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean addAll(Collection<? extends GoalSelector> c) {
            // Appelle une méthode
            c.forEach(goalSelector -> goalSelector.setAIGroup(EntityAIGroup.this));
            // Renvoie une valeur à l'appelant
            return super.addAll(c);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean addAll(int index, Collection<? extends GoalSelector> c) {
            // Appelle une méthode
            c.forEach(goalSelector -> goalSelector.setAIGroup(EntityAIGroup.this));
            // Renvoie une valeur à l'appelant
            return super.addAll(index, c);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void replaceAll(UnaryOperator<GoalSelector> operator) {
            // Accès à l'objet courant/parent
            super.replaceAll(goalSelector -> {
                // Appelle une méthode
                goalSelector = operator.apply(goalSelector);
                // Appelle une méthode
                goalSelector.setAIGroup(EntityAIGroup.this);
                // Renvoie une valeur à l'appelant
                return goalSelector;
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
