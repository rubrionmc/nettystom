// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;

// Déclaration de type (classe/interface/enum/record)
public final class DamageImmunityEffect implements Enchantment.Effect {
    // Appelle une méthode
    public static final DamageImmunityEffect INSTANCE = new DamageImmunityEffect();

    // Appelle une méthode
    public static final Codec<DamageImmunityEffect> CODEC = StructCodec.struct(INSTANCE);

    // Début d'une méthode/d'un bloc
    private DamageImmunityEffect() {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
