// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.projectile.ProjectileMeta;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityShootEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.projectile.ProjectileCollideWithBlockEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.projectile.ProjectileCollideWithEntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.projectile.ProjectileUncollideEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.thread.Acquirable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Optional;
// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.concurrent.ThreadLocalRandom;
// Import d'une classe nécessaire
import java.util.stream.Collectors;
// Import d'une classe nécessaire
import java.util.stream.Stream;

/**
 * Class that allows to instantiate entities with projectile-like physics handling.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityProjectile extends Entity {

    // Instruction de code
    private final Entity shooter;
    // Instruction de code
    private boolean wasStuck;

    // Début d'une méthode/d'un bloc
    public EntityProjectile(@Nullable Entity shooter, EntityType entityType) {
        // Accès à l'objet courant/parent
        super(entityType);
        // Accès à l'objet courant/parent
        this.shooter = shooter;
        // Appelle une méthode
        setup();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void setup() {
        // Accès à l'objet courant/parent
        super.hasPhysics = false;
        // Embranchement : vérifie une condition
        if (getEntityMeta() instanceof ProjectileMeta) {
            // Appelle une méthode
            ((ProjectileMeta) getEntityMeta()).setShooter(this.shooter);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getShooter() {
        // Renvoie une valeur à l'appelant
        return this.shooter;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void shoot(Point to, double power, double spread) {
        // Appelle une méthode
        final EntityShootEvent shootEvent = new EntityShootEvent(this.shooter, this, to, power, spread);
        // Appelle une méthode
        EventDispatcher.call(shootEvent);
        // Embranchement : vérifie une condition
        if (shootEvent.isCancelled()) {
            // Appelle une méthode
            remove();
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final Pos from = this.shooter.getPosition().add(0D, this.shooter.getEyeHeight(), 0D);
        // Appelle une méthode
        shoot(from, to, shootEvent.getPower(), shootEvent.getSpread());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void shoot(Point from, Point to, double power, double spread) {
        // Boucle : répète un bloc
        double dx = to.x() - from.x();
        // Boucle : répète un bloc
        double dy = to.y() - from.y();
        // Boucle : répète un bloc
        double dz = to.z() - from.z();
        // Embranchement : vérifie une condition
        if (!hasNoGravity()) {
            // Appelle une méthode
            final double xzLength = Math.sqrt(dx * dx + dz * dz);
            // Affecte une valeur
            dy += xzLength * 0.20000000298023224D;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
        // Affecte une valeur
        dx /= length;
        // Affecte une valeur
        dy /= length;
        // Affecte une valeur
        dz /= length;
        // Appelle une méthode
        Random random = ThreadLocalRandom.current();
        // Affecte une valeur
        spread *= 0.007499999832361937D;
        // Appelle une méthode
        dx += random.nextGaussian() * spread;
        // Appelle une méthode
        dy += random.nextGaussian() * spread;
        // Appelle une méthode
        dz += random.nextGaussian() * spread;

        // Affecte une valeur
        final double mul = 20 * power;
        // Accès à l'objet courant/parent
        this.velocity = new Vec(dx * mul, dy * mul, dz * mul);
        // Instruction de code
        setView(
                // Instruction de code
                (float) Math.toDegrees(Math.atan2(dx, dz)),
                // Instruction de code
                (float) Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Appelle une méthode
        final Pos posBefore = getPosition();
        // Accès à l'objet courant/parent
        super.tick(time);
        // Embranchement : vérifie une condition
        if (super.isRemoved()) return;

        // Appelle une méthode
        final Pos posNow = getPosition();
        // Appelle une méthode
        boolean isStuck = isStuck(posBefore, posNow);
        // Embranchement : vérifie une condition
        if (isRemoved()) return;
        // Embranchement : vérifie une condition
        if (isStuck) {
            // Embranchement : vérifie une condition
            if (super.onGround) {
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            super.onGround = true;
            // Accès à l'objet courant/parent
            this.velocity = Vec.ZERO;
            // Appelle une méthode
            sendPacketToViewersAndSelf(getVelocityPacket());
            // Appelle une méthode
            setNoGravity(true);
            // Affecte une valeur
            wasStuck = true;
        // Branche alternative de la condition
        } else {
            // Embranchement : vérifie une condition
            if (!wasStuck) return;
            // Affecte une valeur
            wasStuck = false;
            // Appelle une méthode
            setNoGravity(super.onGround);
            // Accès à l'objet courant/parent
            super.onGround = false;
            // Appelle une méthode
            EventDispatcher.call(new ProjectileUncollideEvent(this));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks whether an arrow is stuck in block / hit an entity.
     *
     * @param pos    position right before current tick.
     * @param posNow position after current tick.
     * @return if an arrow is stuck in block / hit an entity.
     */
    // Annotation pour l'élément suivant
    @SuppressWarnings("ConstantConditions")
    // Début d'une méthode/d'un bloc
    private boolean isStuck(Pos pos, Pos posNow) {
        // Appelle une méthode
        final Instance instance = getInstance();
        // Embranchement : vérifie une condition
        if (pos.samePoint(posNow)) {
            // Renvoie une valeur à l'appelant
            return instance.getBlock(pos).isSolid();
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        Chunk chunk = null;
        // Affecte une valeur
        Collection<LivingEntity> entities = null;
        // Appelle une méthode
        final BoundingBox bb = getBoundingBox();

        /*
          What we're about to do is to discretely jump from a previous position to the new one.
          For each point we will be checking blocks and entities we're in.
         */
        // Appelle une méthode
        final double part = bb.width() / 2;
        // Appelle une méthode
        final Vec dir = posNow.sub(pos).asVec();
        // Appelle une méthode
        final int parts = (int) Math.ceil(dir.length() / part);
        // Appelle une méthode
        final Pos direction = dir.normalize().mul(part).asPos();
        // Appelle une méthode
        final long aliveTicks = getAliveTicks();
        // Affecte une valeur
        Block block = null;
        // Affecte une valeur
        Point blockPos = null;
        // Boucle : répète un bloc
        for (int i = 0; i < parts; ++i) {
            // If we're at last part, we can't just add another direction-vector, because we can exceed the end point.
            // Appelle une méthode
            pos = (i == parts - 1) ? posNow : pos.add(direction);
            // Embranchement : vérifie une condition
            if (block == null || !pos.sameBlock(blockPos)) {
                // Appelle une méthode
                block = instance.getBlock(pos);
                // Affecte une valeur
                blockPos = pos;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (block.isSolid()) {
                // Appelle une méthode
                final ProjectileCollideWithBlockEvent event = new ProjectileCollideWithBlockEvent(this, pos, block);
                // Appelle une méthode
                EventDispatcher.call(event);
                // Embranchement : vérifie une condition
                if (isRemoved()) return true;
                // Embranchement : vérifie une condition
                if (!event.isCancelled()) {
                    // Appelle une méthode
                    teleport(pos);
                    // Renvoie une valeur à l'appelant
                    return true;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (currentChunk != chunk) {
                // Affecte une valeur
                chunk = currentChunk;
                // Affecte une valeur
                entities = instance.getChunkEntities(chunk)
                        // Instruction de code
                        .stream()
                        // Instruction de code
                        .filter(entity -> entity instanceof LivingEntity)
                        // Instruction de code
                        .map(entity -> (LivingEntity) entity)
                        // Appelle une méthode
                        .collect(Collectors.toSet());
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            final Point currentPos = pos;
            // Affecte une valeur
            Stream<LivingEntity> victimsStream = entities.stream()
                    // Appelle une méthode
                    .filter(entity -> bb.intersectEntity(currentPos, entity));
            /*
              We won't check collisions with a shooter for first ticks of arrow's life, because it spawns in him
              and will immediately deal damage.
             */
            // Embranchement : vérifie une condition
            if (aliveTicks < 3 && shooter != null) {
                // Appelle une méthode
                victimsStream = victimsStream.filter(entity -> entity != shooter);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final Optional<LivingEntity> victimOptional = victimsStream.findAny();
            // Embranchement : vérifie une condition
            if (victimOptional.isPresent()) {
                // Appelle une méthode
                final LivingEntity target = victimOptional.get();
                // Appelle une méthode
                final ProjectileCollideWithEntityEvent event = new ProjectileCollideWithEntityEvent(this, pos, target);
                // Appelle une méthode
                EventDispatcher.call(event);
                // Embranchement : vérifie une condition
                if (!event.isCancelled()) {
                    // Renvoie une valeur à l'appelant
                    return super.onGround;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Acquirable<? extends EntityProjectile> acquirable() {
        // Renvoie une valeur à l'appelant
        return (Acquirable<? extends EntityProjectile>) super.acquirable();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
