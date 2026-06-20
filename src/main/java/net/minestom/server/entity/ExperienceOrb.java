// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.thread.Acquirable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.Comparator;

// Déclaration de type (classe/interface/enum/record)
public class ExperienceOrb extends Entity {

    // Instruction de code
    private short experienceCount;
    // Instruction de code
    private Player target;
    // Instruction de code
    private long lastTargetUpdateTick;

    // Début d'une méthode/d'un bloc
    public ExperienceOrb(short experienceCount) {
        // Accès à l'objet courant/parent
        super(EntityType.EXPERIENCE_ORB);
        // Appelle une méthode
        setBoundingBox(0.5f, 0.5f, 0.5f);
        //todo vanilla sets random velocity here?
        // Accès à l'objet courant/parent
        this.experienceCount = experienceCount;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void update(long time) {

        // TODO slide toward nearest player

        //todo water movement
        // Embranchement : vérifie une condition
        if (hasNoGravity()) {
            // Appelle une méthode
            setVelocity(getVelocity().add(0, -0.3f, 0));
        // Fin d'un bloc/d'une expression
        }

        //todo lava

        // Boucle : répète un bloc
        double d = 8.0;
        // Embranchement : vérifie une condition
        if (lastTargetUpdateTick < time - 20 + getEntityId() % 100) {
            // Embranchement : vérifie une condition
            if (target == null || target.getPosition().distanceSquared(getPosition()) > 64) {
                // Accès à l'objet courant/parent
                this.target = getClosestPlayer(this, 8);
            // Fin d'un bloc/d'une expression
            }

            // Affecte une valeur
            lastTargetUpdateTick = time;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (target != null && target.getGameMode() == GameMode.SPECTATOR) {
            // Affecte une valeur
            target = null;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (target != null) {
            // Appelle une méthode
            final var pos = getPosition();
            // Appelle une méthode
            final var targetPos = target.getPosition();
            // Appelle une méthode
            final Vec toTarget = new Vec(targetPos.x() - pos.x(), targetPos.y() + (target.getEyeHeight() / 2) - pos.y(), targetPos.z() - pos.z());
            // Boucle : répète un bloc
            double e = toTarget.length(); //could really be lengthSquared
            // Embranchement : vérifie une condition
            if (e < 8) {
                // Boucle : répète un bloc
                double f = 1 - (e / 8);
                // Appelle une méthode
                setVelocity(getVelocity().add(toTarget.normalize().mul(f * f * 0.1)));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Move should be called here
        // Affecte une valeur
        float g = 0.98f;
        // Embranchement : vérifie une condition
        if (this.onGround) {
//            g = 2f;
            // Affecte une valeur
            g = 0.6f * 0.98f;
        // Fin d'un bloc/d'une expression
        }
        // apply slipperiness

        // Appelle une méthode
        setVelocity(getVelocity().mul(new Vec(g, 0.98f, g)));
        // Embranchement : vérifie une condition
        if (isOnGround()) {
            // Appelle une méthode
            setVelocity(getVelocity().mul(new Vec(1, -0.9f, 1)));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void spawn() {

    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the experience count.
     *
     * @return the experience count
     */
    // Début d'une méthode/d'un bloc
    public short getExperienceCount() {
        // Renvoie une valeur à l'appelant
        return experienceCount;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the experience count.
     *
     * @param experienceCount the new experience count
     */
    // Début d'une méthode/d'un bloc
    public void setExperienceCount(short experienceCount) {
        // Remove the entity in order to respawn it with the correct experience count
        // Appelle une méthode
        getViewers().forEach(this::removeViewer);

        // Accès à l'objet courant/parent
        this.experienceCount = experienceCount;

        // Appelle une méthode
        getViewers().forEach(this::addViewer);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private Player getClosestPlayer(Entity entity, float maxDistance) {
        // Affecte une valeur
        Player closest = entity.getInstance()
                // Instruction de code
                .getPlayers()
                // Instruction de code
                .stream()
                // Instruction de code
                .min(Comparator.comparingDouble(a -> a.getDistanceSquared(entity)))
                // Appelle une méthode
                .orElse(null);
        // Embranchement : vérifie une condition
        if (closest == null) return null;
        // Embranchement : vérifie une condition
        if (closest.getDistanceSquared(entity) > maxDistance * maxDistance) return null;
        // Renvoie une valeur à l'appelant
        return closest;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Acquirable<? extends ExperienceOrb> acquirable() {
        // Renvoie une valeur à l'appelant
        return (Acquirable<? extends ExperienceOrb>) super.acquirable();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
