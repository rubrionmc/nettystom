// Déclaration du paquet de ce fichier
package net.minestom.server.event.item;

// Import d'une classe nécessaire
import net.minestom.server.entity.ExperienceOrb;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

// Déclaration de type (classe/interface/enum/record)
public class PickupExperienceEvent implements CancellableEvent, PlayerInstanceEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final ExperienceOrb experienceOrb;
    // Instruction de code
    private short experienceCount;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public PickupExperienceEvent(Player player, ExperienceOrb experienceOrb) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.experienceOrb = experienceOrb;
        // Accès à l'objet courant/parent
        this.experienceCount = experienceOrb.getExperienceCount();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ExperienceOrb getExperienceOrb() {
        // Renvoie une valeur à l'appelant
        return experienceOrb;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public short getExperienceCount() {
        // Renvoie une valeur à l'appelant
        return experienceCount;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setExperienceCount(short experienceCount) {
        // Accès à l'objet courant/parent
        this.experienceCount = experienceCount;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return cancelled;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setCancelled(boolean cancel) {
        // Accès à l'objet courant/parent
        this.cancelled = cancel;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
