// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai;

// Déclaration de type (classe/interface/enum/record)
public class EntityAIGroupBuilder {

    // Appelle une méthode
    private final EntityAIGroup group = new EntityAIGroup();

    /**
     * Adds {@link GoalSelector} to the list of goal selectors of the building {@link EntityAIGroup}.
     * Addition order is also a priority: priority the higher the earlier selector was added.
     *
     * @param goalSelector goal selector to be added.
     * @return this builder.
     */
    // Début d'une méthode/d'un bloc
    public EntityAIGroupBuilder addGoalSelector(GoalSelector goalSelector) {
        // Accès à l'objet courant/parent
        this.group.getGoalSelectors().add(goalSelector);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds {@link TargetSelector} to the list of target selectors of the building {@link EntityAIGroup}.
     * Addition order is also a priority: priority the higher the earlier selector was added.
     *
     * @param targetSelector target selector to be added.
     * @return this builder.
     */
    // Début d'une méthode/d'un bloc
    public EntityAIGroupBuilder addTargetSelector(TargetSelector targetSelector) {
        // Accès à l'objet courant/parent
        this.group.getTargetSelectors().add(targetSelector);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates new {@link EntityAIGroup}.
     *
     * @return new {@link EntityAIGroup}.
     */
    // Début d'une méthode/d'un bloc
    public EntityAIGroup build() {
        // Renvoie une valeur à l'appelant
        return this.group;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
