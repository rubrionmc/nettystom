// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantLock;

// Déclaration de type (classe/interface/enum/record)
final class AcquiredImpl<T> implements Acquired<T> {
    // Instruction de code
    private final T value;
    // Instruction de code
    private final Thread owner;
    // Instruction de code
    private final ReentrantLock lock;
    // Instruction de code
    private boolean unlocked;

    // Début d'une méthode/d'un bloc
    AcquiredImpl(T value, ReentrantLock lock) {
        // Accès à l'objet courant/parent
        this.value = value;
        // Accès à l'objet courant/parent
        this.owner = Thread.currentThread();
        // Accès à l'objet courant/parent
        this.lock = lock;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public T get() {
        // Appelle une méthode
        safeCheck();
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void unlock() {
        // Appelle une méthode
        safeCheck();
        // Accès à l'objet courant/parent
        this.unlocked = true;
        // Appelle une méthode
        AcquirableImpl.leave(lock);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void safeCheck() {
        // Appelle une méthode
        Check.stateCondition(Thread.currentThread() != owner, "Acquired object is owned by the thread {0}", owner);
        // Appelle une méthode
        Check.stateCondition(unlocked, "The acquired element has already been unlocked!");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
