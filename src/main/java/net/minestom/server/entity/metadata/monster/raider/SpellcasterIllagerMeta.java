// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster.raider;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class SpellcasterIllagerMeta extends AbstractIllagerMeta {
    // Début d'une méthode/d'un bloc
    protected SpellcasterIllagerMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Spell getSpell() {
        // Renvoie une valeur à l'appelant
        return Spell.VALUES[metadata.get(MetadataDef.SpellcasterIllager.SPELL)];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSpell(Spell spell) {
        // Appelle une méthode
        metadata.set(MetadataDef.SpellcasterIllager.SPELL, (byte) spell.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Spell {
        // Instruction de code
        NONE,
        // Instruction de code
        SUMMON_VEX,
        // Instruction de code
        ATTACK,
        // Instruction de code
        WOLOLO,
        // Instruction de code
        DISAPPEAR,
        // Instruction de code
        BLINDNESS;

        // Appelle une méthode
        private final static Spell[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
