// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record Tool(List<Rule> rules, float defaultMiningSpeed, int damagePerBlock,
                   // Start of a method/block
                   boolean canDestroyBlocksInCreative) {
    // Assigns a value
    public static final float DEFAULT_MINING_SPEED = 1.0f;
    // Assigns a value
    public static final int DEFAULT_DAMAGE_PER_BLOCK = 1;

    // Assigns a value
    public static final NetworkBuffer.Type<Tool> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            Rule.NETWORK_TYPE.list(Short.MAX_VALUE), Tool::rules,
            // Code statement
            NetworkBuffer.FLOAT, Tool::defaultMiningSpeed,
            // Code statement
            NetworkBuffer.VAR_INT, Tool::damagePerBlock,
            // Code statement
            NetworkBuffer.BOOLEAN, Tool::canDestroyBlocksInCreative,
            // Code statement
            Tool::new);
    // Assigns a value
    public static final Codec<Tool> CODEC = StructCodec.struct(
            // Code statement
            "rules", Rule.CODEC.list(), Tool::rules,
            // Code statement
            "default_mining_speed", Codec.FLOAT.optional(DEFAULT_MINING_SPEED), Tool::defaultMiningSpeed,
            // Code statement
            "damage_per_block", Codec.INT.optional(DEFAULT_DAMAGE_PER_BLOCK), Tool::damagePerBlock,
            // Code statement
            "can_destroy_blocks_in_creative", Codec.BOOLEAN.optional(true), Tool::canDestroyBlocksInCreative,
            // Code statement
            Tool::new);

    // Start of a method/block
    public Tool {
        // Calls a method
        rules = List.copyOf(rules);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Rule(RegistryTag<Block> blocks, @Nullable Float speed, @Nullable Boolean correctForDrops) {

        // Assigns a value
        public static final NetworkBuffer.Type<Rule> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                RegistryTag.networkType(Registries::blocks), Rule::blocks,
                // Code statement
                NetworkBuffer.FLOAT.optional(), Rule::speed,
                // Code statement
                NetworkBuffer.BOOLEAN.optional(), Rule::correctForDrops,
                // Code statement
                Rule::new
        // End of a block/expression
        );
        // Assigns a value
        public static final Codec<Rule> CODEC = StructCodec.struct(
                // Code statement
                "blocks", RegistryTag.codec(Registries::blocks), Rule::blocks,
                // Code statement
                "speed", Codec.FLOAT.optional(), Rule::speed,
                // Code statement
                "correct_for_drops", Codec.BOOLEAN.optional(), Rule::correctForDrops,
                // Code statement
                Rule::new);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isCorrectForDrops(Block block) {
        // Loop: repeats a block
        for (Rule rule : rules) {
            // Branch: checks a condition
            if (rule.correctForDrops != null && rule.blocks.contains(block)) {
                // First matching rule is picked, other rules are ignored
                // Returns a value to the caller
                return rule.correctForDrops;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Start of a method/block
    public float getSpeed(Block block) {
        // Loop: repeats a block
        for (Rule rule : rules) {
            // Branch: checks a condition
            if (rule.speed != null && rule.blocks.contains(block)) {
                // First matching rule is picked, other rules are ignored
                // Returns a value to the caller
                return rule.speed;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return defaultMiningSpeed;
    // End of a block/expression
    }
// End of a block/expression
}
