// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;

// Déclaration de type (classe/interface/enum/record)
public final class EventDispatcher {

    // Début d'une méthode/d'un bloc
    public static void call(Event event) {
        // Appelle une méthode
        MinecraftServer.getGlobalEventHandler().call(event);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static <E extends Event> ListenerHandle<E> getHandle(Class<E> handleType) {
        // Renvoie une valeur à l'appelant
        return MinecraftServer.getGlobalEventHandler().getHandle(handleType);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void callCancellable(CancellableEvent event, Runnable successCallback) {
        // Appelle une méthode
        MinecraftServer.getGlobalEventHandler().callCancellable(event, successCallback);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
