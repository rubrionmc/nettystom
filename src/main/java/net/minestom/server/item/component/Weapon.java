// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record Weapon(int itemDamagePerAttack, float disableBlockingForSeconds) {
    // Appelle une méthode
    public static final Weapon DEFAULT = new Weapon(1, 0.0f);

    // Affecte une valeur
    public static final NetworkBuffer.Type<Weapon> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, Weapon::itemDamagePerAttack,
            // Instruction de code
            NetworkBuffer.FLOAT, Weapon::disableBlockingForSeconds,
            // Instruction de code
            Weapon::new);
    // Affecte une valeur
    public static final Codec<Weapon> CODEC = StructCodec.struct(
            // Instruction de code
            "item_damage_per_attack", Codec.INT.optional(1), Weapon::itemDamagePerAttack,
            // Instruction de code
            "disable_blocking_for_seconds", Codec.FLOAT.optional(0f), Weapon::disableBlockingForSeconds,
            // Instruction de code
            Weapon::new);

    // Début d'une méthode/d'un bloc
    public Weapon(int itemDamagePerAttack) {
        // Appelle une méthode
        this(itemDamagePerAttack, 0.0f);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Weapon withItemDamagePerAttack(int itemDamagePerAttack) {
        // Renvoie une valeur à l'appelant
        return new Weapon(itemDamagePerAttack, this.disableBlockingForSeconds);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Weapon withDisableBlockingForSeconds(float disableBlockingForSeconds) {
        // Renvoie une valeur à l'appelant
        return new Weapon(this.itemDamagePerAttack, disableBlockingForSeconds);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
