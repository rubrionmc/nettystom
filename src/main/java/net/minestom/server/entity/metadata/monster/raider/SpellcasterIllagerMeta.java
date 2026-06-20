// Package declaration for this file
package net.minestom.server.entity.metadata.monster.raider;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class SpellcasterIllagerMeta extends AbstractIllagerMeta {
    // Start of a method/block
    protected SpellcasterIllagerMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public Spell getSpell() {
        // Returns a value to the caller
        return Spell.VALUES[metadata.get(MetadataDef.SpellcasterIllager.SPELL)];
    // End of a block/expression
    }

    // Start of a method/block
    public void setSpell(Spell spell) {
        // Calls a method
        metadata.set(MetadataDef.SpellcasterIllager.SPELL, (byte) spell.ordinal());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Spell {
        // Code statement
        NONE,
        // Code statement
        SUMMON_VEX,
        // Code statement
        ATTACK,
        // Code statement
        WOLOLO,
        // Code statement
        DISAPPEAR,
        // Code statement
        BLINDNESS;

        // Calls a method
        private final static Spell[] VALUES = values();
    // End of a block/expression
    }

// End of a block/expression
}
