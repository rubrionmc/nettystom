// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.server.registry.TestRegistries;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;
// Static import of a member
import static org.junit.jupiter.api.Assumptions.assumeFalse;

// Type declaration (class/interface/enum/record)
class SerializationContextTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testValueEffectSerializationVanilla() {
        // Calls a method
        var registry = ValueEffect.createDefaultRegistry();
        // Calls a method
        var coder = new RegistryTranscoder<>(Transcoder.NBT, new TestRegistries(r -> r.enchantmentValueEffects = registry), true, false);

        // Calls a method
        var result = assertOk(ValueEffect.CODEC.encode(coder, new ValueEffect.Add(new LevelBasedValue.Constant(1))));
        // Code statement
        assertEqualsSNBT("""
                {"type":"minecraft:add","value":1f}
                """, result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testValueEffectSerializationCustom() {
        // Calls a method
        var registry = ValueEffect.createDefaultRegistry();
        // Code statement
        registry.register("minestom:my_effect", MyEffect.CODEC); // NOT registered to MINECRAFT_CORE
        // Calls a method
        var coder = new RegistryTranscoder<>(Transcoder.NBT, new TestRegistries(r -> r.enchantmentValueEffects = registry), true, false);

        // Calls a method
        var result = assertOk(ValueEffect.CODEC.encode(coder, new MyEffect()));
        // Calls a method
        assertNull(result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testValueEffectSerializationCustomInList() {
        // Calls a method
        var registry = ValueEffect.createDefaultRegistry();
        // Code statement
        registry.register("minestom:my_effect", MyEffect.CODEC); // NOT registered to MINECRAFT_CORE
        // Calls a method
        var coder = new RegistryTranscoder<>(Transcoder.NBT, new TestRegistries(r -> r.enchantmentValueEffects = registry), true, false);

        // Assigns a value
        var result = assertOk(ValueEffect.CODEC.list().encode(coder, List.of(
                // Creates a new object
                new ValueEffect.Add(new LevelBasedValue.Constant(1)),
                // Creates a new object
                new MyEffect()
        // Code statement
        )));
        // Code statement
        assertEqualsSNBT("""
                [{"type":"minecraft:add","value":1f}]
                """, result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testValueEffectSerializationCompoundCustom() {
        // Calls a method
        assumeFalse(true, "TODO(1.21.5)");
        // Calls a method
        var levelBasedValueRegistry = LevelBasedValue.createDefaultRegistry();
        // Calls a method
        var valueEffectRegistry = ValueEffect.createDefaultRegistry();
        // Code statement
        levelBasedValueRegistry.register("minestom:my_level_based_value", MyLevelBasedValue.CODEC); // NOT registered to MINECRAFT_CORE
        // Assigns a value
        var coder = new RegistryTranscoder<>(Transcoder.NBT, new TestRegistries(r -> {
            // Assigns a value
            r.enchantmentLevelBasedValues = levelBasedValueRegistry;
            // Assigns a value
            r.enchantmentValueEffects = valueEffectRegistry;
        // Code statement
        }), true, false);

        // Calls a method
        var result = assertOk(ValueEffect.CODEC.encode(coder, new ValueEffect.Add(new MyLevelBasedValue())));
        // Code statement
        assertNull(result); // Should get nothing because MyLevelBasedValue is missing and that would create an invalid Add
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static class MyLevelBasedValue implements LevelBasedValue {
        // Calls a method
        public static final StructCodec<MyLevelBasedValue> CODEC = StructCodec.struct(MyLevelBasedValue::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float calc(int level) {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<MyLevelBasedValue> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static class MyEffect implements ValueEffect {
        // Calls a method
        public static final StructCodec<MyEffect> CODEC = StructCodec.struct(MyEffect::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float apply(float base, int level) {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<MyEffect> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
