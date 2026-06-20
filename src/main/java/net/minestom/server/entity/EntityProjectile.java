// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.metadata.projectile.ProjectileMeta;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.entity.EntityShootEvent;
// Import of a required class
import net.minestom.server.event.entity.projectile.ProjectileCollideWithBlockEvent;
// Import of a required class
import net.minestom.server.event.entity.projectile.ProjectileCollideWithEntityEvent;
// Import of a required class
import net.minestom.server.event.entity.projectile.ProjectileUncollideEvent;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.thread.Acquirable;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Optional;
// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.concurrent.ThreadLocalRandom;
// Import of a required class
import java.util.stream.Collectors;
// Import of a required class
import java.util.stream.Stream;

/**
 * Class that allows to instantiate entities with projectile-like physics handling.
 */
// Type declaration (class/interface/enum/record)
public class EntityProjectile extends Entity {

    // Code statement
    private final Entity shooter;
    // Code statement
    private boolean wasStuck;

    // Start of a method/block
    public EntityProjectile(@Nullable Entity shooter, EntityType entityType) {
        // Access to the current/parent object
        super(entityType);
        // Access to the current/parent object
        this.shooter = shooter;
        // Calls a method
        setup();
    // End of a block/expression
    }

    // Start of a method/block
    private void setup() {
        // Access to the current/parent object
        super.hasPhysics = false;
        // Branch: checks a condition
        if (getEntityMeta() instanceof ProjectileMeta) {
            // Calls a method
            ((ProjectileMeta) getEntityMeta()).setShooter(this.shooter);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getShooter() {
        // Returns a value to the caller
        return this.shooter;
    // End of a block/expression
    }

    // Start of a method/block
    public void shoot(Point to, double power, double spread) {
        // Calls a method
        final EntityShootEvent shootEvent = new EntityShootEvent(this.shooter, this, to, power, spread);
        // Calls a method
        EventDispatcher.call(shootEvent);
        // Branch: checks a condition
        if (shootEvent.isCancelled()) {
            // Calls a method
            remove();
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        final Pos from = this.shooter.getPosition().add(0D, this.shooter.getEyeHeight(), 0D);
        // Calls a method
        shoot(from, to, shootEvent.getPower(), shootEvent.getSpread());
    // End of a block/expression
    }

    // Start of a method/block
    private void shoot(Point from, Point to, double power, double spread) {
        // Calls a method
        double dx = to.x() - from.x();
        // Calls a method
        double dy = to.y() - from.y();
        // Calls a method
        double dz = to.z() - from.z();
        // Branch: checks a condition
        if (!hasNoGravity()) {
            // Calls a method
            final double xzLength = Math.sqrt(dx * dx + dz * dz);
            // Code statement
            dy += xzLength * 0.20000000298023224D;
        // End of a block/expression
        }

        // Calls a method
        final double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
        // Code statement
        dx /= length;
        // Code statement
        dy /= length;
        // Code statement
        dz /= length;
        // Calls a method
        Random random = ThreadLocalRandom.current();
        // Code statement
        spread *= 0.007499999832361937D;
        // Calls a method
        dx += random.nextGaussian() * spread;
        // Calls a method
        dy += random.nextGaussian() * spread;
        // Calls a method
        dz += random.nextGaussian() * spread;

        // Assigns a value
        final double mul = 20 * power;
        // Access to the current/parent object
        this.velocity = new Vec(dx * mul, dy * mul, dz * mul);
        // Code statement
        setView(
                // Code statement
                (float) Math.toDegrees(Math.atan2(dx, dz)),
                // Code statement
                (float) Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Calls a method
        final Pos posBefore = getPosition();
        // Access to the current/parent object
        super.tick(time);
        // Branch: checks a condition
        if (super.isRemoved()) return;

        // Calls a method
        final Pos posNow = getPosition();
        // Calls a method
        boolean isStuck = isStuck(posBefore, posNow);
        // Branch: checks a condition
        if (isRemoved()) return;
        // Branch: checks a condition
        if (isStuck) {
            // Branch: checks a condition
            if (super.onGround) {
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Access to the current/parent object
            super.onGround = true;
            // Access to the current/parent object
            this.velocity = Vec.ZERO;
            // Calls a method
            sendPacketToViewersAndSelf(getVelocityPacket());
            // Calls a method
            setNoGravity(true);
            // Assigns a value
            wasStuck = true;
        // Alternative branch of the condition
        } else {
            // Branch: checks a condition
            if (!wasStuck) return;
            // Assigns a value
            wasStuck = false;
            // Calls a method
            setNoGravity(super.onGround);
            // Access to the current/parent object
            super.onGround = false;
            // Calls a method
            EventDispatcher.call(new ProjectileUncollideEvent(this));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Checks whether an arrow is stuck in block / hit an entity.
     *
     * @param pos    position right before current tick.
     * @param posNow position after current tick.
     * @return if an arrow is stuck in block / hit an entity.
     */
    // Annotation for the following element
    @SuppressWarnings("ConstantConditions")
    // Start of a method/block
    private boolean isStuck(Pos pos, Pos posNow) {
        // Calls a method
        final Instance instance = getInstance();
        // Branch: checks a condition
        if (pos.samePoint(posNow)) {
            // Returns a value to the caller
            return instance.getBlock(pos).isSolid();
        // End of a block/expression
        }

        // Assigns a value
        Chunk chunk = null;
        // Assigns a value
        Collection<LivingEntity> entities = null;
        // Calls a method
        final BoundingBox bb = getBoundingBox();

        /*
          What we're about to do is to discretely jump from a previous position to the new one.
          For each point we will be checking blocks and entities we're in.
         */
        // Calls a method
        final double part = bb.width() / 2;
        // Calls a method
        final Vec dir = posNow.sub(pos).asVec();
        // Calls a method
        final int parts = (int) Math.ceil(dir.length() / part);
        // Calls a method
        final Pos direction = dir.normalize().mul(part).asPos();
        // Calls a method
        final long aliveTicks = getAliveTicks();
        // Assigns a value
        Block block = null;
        // Assigns a value
        Point blockPos = null;
        // Loop: repeats a block
        for (int i = 0; i < parts; ++i) {
            // If we're at last part, we can't just add another direction-vector, because we can exceed the end point.
            // Calls a method
            pos = (i == parts - 1) ? posNow : pos.add(direction);
            // Branch: checks a condition
            if (block == null || !pos.sameBlock(blockPos)) {
                // Calls a method
                block = instance.getBlock(pos);
                // Assigns a value
                blockPos = pos;
            // End of a block/expression
            }
            // Branch: checks a condition
            if (block.isSolid()) {
                // Calls a method
                final ProjectileCollideWithBlockEvent event = new ProjectileCollideWithBlockEvent(this, pos, block);
                // Calls a method
                EventDispatcher.call(event);
                // Branch: checks a condition
                if (isRemoved()) return true;
                // Branch: checks a condition
                if (!event.isCancelled()) {
                    // Calls a method
                    teleport(pos);
                    // Returns a value to the caller
                    return true;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Branch: checks a condition
            if (currentChunk != chunk) {
                // Assigns a value
                chunk = currentChunk;
                // Assigns a value
                entities = instance.getChunkEntities(chunk)
                        // Code statement
                        .stream()
                        // Code statement
                        .filter(entity -> entity instanceof LivingEntity)
                        // Code statement
                        .map(entity -> (LivingEntity) entity)
                        // Calls a method
                        .collect(Collectors.toSet());
            // End of a block/expression
            }
            // Assigns a value
            final Point currentPos = pos;
            // Assigns a value
            Stream<LivingEntity> victimsStream = entities.stream()
                    // Calls a method
                    .filter(entity -> bb.intersectEntity(currentPos, entity));
            /*
              We won't check collisions with a shooter for first ticks of arrow's life, because it spawns in him
              and will immediately deal damage.
             */
            // Branch: checks a condition
            if (aliveTicks < 3 && shooter != null) {
                // Calls a method
                victimsStream = victimsStream.filter(entity -> entity != shooter);
            // End of a block/expression
            }
            // Calls a method
            final Optional<LivingEntity> victimOptional = victimsStream.findAny();
            // Branch: checks a condition
            if (victimOptional.isPresent()) {
                // Calls a method
                final LivingEntity target = victimOptional.get();
                // Calls a method
                final ProjectileCollideWithEntityEvent event = new ProjectileCollideWithEntityEvent(this, pos, target);
                // Calls a method
                EventDispatcher.call(event);
                // Branch: checks a condition
                if (!event.isCancelled()) {
                    // Returns a value to the caller
                    return super.onGround;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Acquirable<? extends EntityProjectile> acquirable() {
        // Returns a value to the caller
        return (Acquirable<? extends EntityProjectile>) super.acquirable();
    // End of a block/expression
    }
// End of a block/expression
}
