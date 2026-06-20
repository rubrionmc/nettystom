// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

/**
 * Entity status ids used in {@link net.minestom.server.network.packet.server.play.EntityStatusPacket}.
 */
// Annotation pour l'élément suivant
@SuppressWarnings("ALL")
// Déclaration de type (classe/interface/enum/record)
public sealed class EntityStatuses {
    // Affecte une valeur
    public static final int SPAWNS_HONEY_BLOCK_PARTICLES = 53;

    // Début d'une méthode/d'un bloc
    public static final class Arrow extends EntityStatuses {
        // Affecte une valeur
        public static final int SPAWN_TIPPED_ARROW_PARTICLE = 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class LivingEntity extends EntityStatuses {
        // Affecte une valeur
        public static final int PLAY_DEATH_SOUND = 3;
        // Affecte une valeur
        public static final int PLAY_SHIELD_BLOCK_SOUND = 29;
        // Affecte une valeur
        public static final int PLAY_SHIELD_BREAK_SOUND = 30;
        // Affecte une valeur
        public static final int PLAY_TOTEM_UNDYING_ANIMATION_SOUND = 35;

        // Affecte une valeur
        public static final int SWAP_HAND_ITEMS = 55;
        // Affecte une valeur
        public static final int SPAWN_DEATH_SMOKE_PARTICLES = 60;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Player extends LivingEntity {
        // Affecte une valeur
        public static final int MARK_ITEM_FINISHED = 9;
        // Affecte une valeur
        public static final int ENABLE_DEBUG_SCREEN = 22;
        // Affecte une valeur
        public static final int DISABLE_DEBUG_SCREEN = 23;
        // Affecte une valeur
        public static final int PERMISSION_LEVEL_0 = 24;
        // Affecte une valeur
        public static final int PERMISSION_LEVEL_1 = 25;
        // Affecte une valeur
        public static final int PERMISSION_LEVEL_2 = 26;
        // Affecte une valeur
        public static final int PERMISSION_LEVEL_3 = 27;
        // Affecte une valeur
        public static final int PERMISSION_LEVEL_4 = 28;
        // Affecte une valeur
        public static final int SPAWN_CLOUD_PARTICLES = 43;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class Animal extends EntityStatuses {
        // Affecte une valeur
        public static final int SPAWN_LOVE_MODE_PARTICLES = 18;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Ocelot extends Animal {
        // Affecte une valeur
        public static final int SPAWN_SMOKE_PARTICLES = 40;
        // Affecte une valeur
        public static final int SPAWN_HEART_PARTICLES = 41;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Rabbit extends Animal {
        // Affecte une valeur
        public static final int JUMP_ANIMATION = 1;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Sheep extends Animal {
        // Affecte une valeur
        public static final int EAT_GRASS = 10;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Sniffer extends Animal {
        // Affecte une valeur
        public static final int PLAY_DIGGING_SOUND = 63;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
