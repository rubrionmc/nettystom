// Package declaration for this file
package net.minestom.server.entity;

/**
 * Entity status ids used in {@link net.minestom.server.network.packet.server.play.EntityStatusPacket}.
 */
// Annotation for the following element
@SuppressWarnings("ALL")
// Type declaration (class/interface/enum/record)
public sealed class EntityStatuses {
    // Assigns a value
    public static final int SPAWNS_HONEY_BLOCK_PARTICLES = 53;

    // Start of a method/block
    public static final class Arrow extends EntityStatuses {
        // Assigns a value
        public static final int SPAWN_TIPPED_ARROW_PARTICLE = 0;
    // End of a block/expression
    }

    // Start of a method/block
    public static sealed class LivingEntity extends EntityStatuses {
        // Assigns a value
        public static final int PLAY_DEATH_SOUND = 3;
        // Assigns a value
        public static final int PLAY_SHIELD_BLOCK_SOUND = 29;
        // Assigns a value
        public static final int PLAY_SHIELD_BREAK_SOUND = 30;
        // Assigns a value
        public static final int PLAY_TOTEM_UNDYING_ANIMATION_SOUND = 35;

        // Assigns a value
        public static final int SWAP_HAND_ITEMS = 55;
        // Assigns a value
        public static final int SPAWN_DEATH_SMOKE_PARTICLES = 60;
    // End of a block/expression
    }

    // Start of a method/block
    public static final class Player extends LivingEntity {
        // Assigns a value
        public static final int MARK_ITEM_FINISHED = 9;
        // Assigns a value
        public static final int ENABLE_DEBUG_SCREEN = 22;
        // Assigns a value
        public static final int DISABLE_DEBUG_SCREEN = 23;
        // Assigns a value
        public static final int PERMISSION_LEVEL_0 = 24;
        // Assigns a value
        public static final int PERMISSION_LEVEL_1 = 25;
        // Assigns a value
        public static final int PERMISSION_LEVEL_2 = 26;
        // Assigns a value
        public static final int PERMISSION_LEVEL_3 = 27;
        // Assigns a value
        public static final int PERMISSION_LEVEL_4 = 28;
        // Assigns a value
        public static final int SPAWN_CLOUD_PARTICLES = 43;
    // End of a block/expression
    }

    // Start of a method/block
    public static sealed class Animal extends EntityStatuses {
        // Assigns a value
        public static final int SPAWN_LOVE_MODE_PARTICLES = 18;
    // End of a block/expression
    }

    // Start of a method/block
    public static final class Ocelot extends Animal {
        // Assigns a value
        public static final int SPAWN_SMOKE_PARTICLES = 40;
        // Assigns a value
        public static final int SPAWN_HEART_PARTICLES = 41;
    // End of a block/expression
    }

    // Start of a method/block
    public static final class Rabbit extends Animal {
        // Assigns a value
        public static final int JUMP_ANIMATION = 1;
    // End of a block/expression
    }

    // Start of a method/block
    public static final class Sheep extends Animal {
        // Assigns a value
        public static final int EAT_GRASS = 10;
    // End of a block/expression
    }

    // Start of a method/block
    public static final class Sniffer extends Animal {
        // Assigns a value
        public static final int PLAY_DIGGING_SOUND = 63;
    // End of a block/expression
    }
// End of a block/expression
}
