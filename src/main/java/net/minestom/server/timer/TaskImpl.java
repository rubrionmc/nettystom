// Déclaration du paquet de ce fichier
package net.minestom.server.timer;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.HashCommon;

// Import d'une classe nécessaire
import java.lang.invoke.MethodHandles;
// Import d'une classe nécessaire
import java.lang.invoke.VarHandle;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Déclaration de type (classe/interface/enum/record)
final class TaskImpl implements Task {
    // Instruction de code
    private static final VarHandle PARKED;

    // Début d'une méthode/d'un bloc
    static {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            PARKED = MethodHandles.lookup().findVarHandle(TaskImpl.class, "parked", boolean.class);
        // Début d'une méthode/d'un bloc
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Lève une exception
            throw new IllegalStateException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private final int id;
    // Instruction de code
    private final Supplier<TaskSchedule> task;
    // Instruction de code
    private final ExecutionType executionType;
    // Instruction de code
    private final SchedulerImpl owner;

    // Instruction de code
    volatile boolean alive;
    // Instruction de code
    volatile boolean parked;

    // Instruction de code
    TaskImpl(int id,
             // Instruction de code
             Supplier<TaskSchedule> task,
             // Instruction de code
             ExecutionType executionType,
             // Début d'une méthode/d'un bloc
             SchedulerImpl owner) {
        // Accès à l'objet courant/parent
        this.id = id;
        // Accès à l'objet courant/parent
        this.task = task;
        // Accès à l'objet courant/parent
        this.executionType = executionType;
        // Accès à l'objet courant/parent
        this.owner = owner;
        // Accès à l'objet courant/parent
        this.alive = true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void unpark() {
        // Accès à l'objet courant/parent
        this.owner.unparkTask(this);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    boolean tryUnpark() {
        // Renvoie une valeur à l'appelant
        return PARKED.compareAndSet(this, true, false);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isParked() {
        // Renvoie une valeur à l'appelant
        return parked;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void cancel() {
        // Accès à l'objet courant/parent
        this.alive = false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isAlive() {
        // Renvoie une valeur à l'appelant
        return alive;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int id() {
        // Renvoie une valeur à l'appelant
        return id;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Supplier<TaskSchedule> task() {
        // Renvoie une valeur à l'appelant
        return task;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ExecutionType executionType() {
        // Renvoie une valeur à l'appelant
        return executionType;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public SchedulerImpl owner() {
        // Renvoie une valeur à l'appelant
        return owner;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object obj) {
        // Embranchement : vérifie une condition
        if (obj == this) return true;
        // Embranchement : vérifie une condition
        if (obj == null || obj.getClass() != this.getClass()) return false;
        // Affecte une valeur
        var that = (TaskImpl) obj;
        // Renvoie une valeur à l'appelant
        return this.id == that.id;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return HashCommon.murmurHash3(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return "TaskImpl[" +
                // Affecte une valeur
                "id=" + id + ", " +
                // Affecte une valeur
                "task=" + task + ", " +
                // Affecte une valeur
                "executionType=" + executionType + ", " +
                // Affecte une valeur
                "owner=" + owner + ']';
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
