// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.provider;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.ClickCallback;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.ClickEvent;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;

// Annotation pour l'élément suivant
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Déclaration de type (classe/interface/enum/record)
public final class MinestomClickCallbackProvider implements ClickCallback.Provider {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ClickEvent<ClickEvent.Payload.Custom> create(ClickCallback<Audience> callback, ClickCallback.Options options) {
        // Renvoie une valeur à l'appelant
        return MinecraftServer.getClickCallbackManager().createClickEvent(callback, options);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
