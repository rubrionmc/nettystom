// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record Tool(List<Rule> rules, float defaultMiningSpeed, int damagePerBlock,
                   // Début d'une méthode/d'un bloc
                   boolean canDestroyBlocksInCreative) {
    // Affecte une valeur
    public static final float DEFAULT_MINING_SPEED = 1.0f;
    // Affecte une valeur
    public static final int DEFAULT_DAMAGE_PER_BLOCK = 1;

    // Affecte une valeur
    public static final NetworkBuffer.Type<Tool> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            Rule.NETWORK_TYPE.list(Short.MAX_VALUE), Tool::rules,
            // Instruction de code
            NetworkBuffer.FLOAT, Tool::defaultMiningSpeed,
            // Instruction de code
            NetworkBuffer.VAR_INT, Tool::damagePerBlock,
            // Instruction de code
            NetworkBuffer.BOOLEAN, Tool::canDestroyBlocksInCreative,
            // Instruction de code
            Tool::new);
    // Affecte une valeur
    public static final Codec<Tool> CODEC = StructCodec.struct(
            // Instruction de code
            "rules", Rule.CODEC.list(), Tool::rules,
            // Instruction de code
            "default_mining_speed", Codec.FLOAT.optional(DEFAULT_MINING_SPEED), Tool::defaultMiningSpeed,
            // Instruction de code
            "damage_per_block", Codec.INT.optional(DEFAULT_DAMAGE_PER_BLOCK), Tool::damagePerBlock,
            // Instruction de code
            "can_destroy_blocks_in_creative", Codec.BOOLEAN.optional(true), Tool::canDestroyBlocksInCreative,
            // Instruction de code
            Tool::new);

    // Début d'une méthode/d'un bloc
    public Tool {
        // Appelle une méthode
        rules = List.copyOf(rules);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Rule(RegistryTag<Block> blocks, @Nullable Float speed, @Nullable Boolean correctForDrops) {

        // Affecte une valeur
        public static final NetworkBuffer.Type<Rule> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                RegistryTag.networkType(Registries::blocks), Rule::blocks,
                // Instruction de code
                NetworkBuffer.FLOAT.optional(), Rule::speed,
                // Instruction de code
                NetworkBuffer.BOOLEAN.optional(), Rule::correctForDrops,
                // Instruction de code
                Rule::new
        // Fin d'un bloc/d'une expression
        );
        // Affecte une valeur
        public static final Codec<Rule> CODEC = StructCodec.struct(
                // Instruction de code
                "blocks", RegistryTag.codec(Registries::blocks), Rule::blocks,
                // Instruction de code
                "speed", Codec.FLOAT.optional(), Rule::speed,
                // Instruction de code
                "correct_for_drops", Codec.BOOLEAN.optional(), Rule::correctForDrops,
                // Instruction de code
                Rule::new);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isCorrectForDrops(Block block) {
        // Boucle : répète un bloc
        for (Rule rule : rules) {
            // Embranchement : vérifie une condition
            if (rule.correctForDrops != null && rule.blocks.contains(block)) {
                // First matching rule is picked, other rules are ignored
                // Renvoie une valeur à l'appelant
                return rule.correctForDrops;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getSpeed(Block block) {
        // Boucle : répète un bloc
        for (Rule rule : rules) {
            // Embranchement : vérifie une condition
            if (rule.speed != null && rule.blocks.contains(block)) {
                // First matching rule is picked, other rules are ignored
                // Renvoie une valeur à l'appelant
                return rule.speed;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return defaultMiningSpeed;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
