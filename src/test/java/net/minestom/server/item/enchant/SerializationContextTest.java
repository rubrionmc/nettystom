// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.server.registry.TestRegistries;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;
// Import statique d'un membre
import static org.junit.jupiter.api.Assumptions.assumeFalse;

// Déclaration de type (classe/interface/enum/record)
class SerializationContextTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testValueEffectSerializationVanilla() {
        // Appelle une méthode
        var registry = ValueEffect.createDefaultRegistry();
        // Appelle une méthode
        var coder = new RegistryTranscoder<>(Transcoder.NBT, new TestRegistries(r -> r.enchantmentValueEffects = registry), true, false);

        // Appelle une méthode
        var result = assertOk(ValueEffect.CODEC.encode(coder, new ValueEffect.Add(new LevelBasedValue.Constant(1))));
        // Instruction de code
        assertEqualsSNBT("""
                {"type":"minecraft:add","value":1f}
                """, result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testValueEffectSerializationCustom() {
        // Appelle une méthode
        var registry = ValueEffect.createDefaultRegistry();
        // Instruction de code
        registry.register("minestom:my_effect", MyEffect.CODEC); // NOT registered to MINECRAFT_CORE
        // Appelle une méthode
        var coder = new RegistryTranscoder<>(Transcoder.NBT, new TestRegistries(r -> r.enchantmentValueEffects = registry), true, false);

        // Appelle une méthode
        var result = assertOk(ValueEffect.CODEC.encode(coder, new MyEffect()));
        // Appelle une méthode
        assertNull(result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testValueEffectSerializationCustomInList() {
        // Appelle une méthode
        var registry = ValueEffect.createDefaultRegistry();
        // Instruction de code
        registry.register("minestom:my_effect", MyEffect.CODEC); // NOT registered to MINECRAFT_CORE
        // Appelle une méthode
        var coder = new RegistryTranscoder<>(Transcoder.NBT, new TestRegistries(r -> r.enchantmentValueEffects = registry), true, false);

        // Affecte une valeur
        var result = assertOk(ValueEffect.CODEC.list().encode(coder, List.of(
                // Crée un nouvel objet
                new ValueEffect.Add(new LevelBasedValue.Constant(1)),
                // Crée un nouvel objet
                new MyEffect()
        // Instruction de code
        )));
        // Instruction de code
        assertEqualsSNBT("""
                [{"type":"minecraft:add","value":1f}]
                """, result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testValueEffectSerializationCompoundCustom() {
        // Appelle une méthode
        assumeFalse(true, "TODO(1.21.5)");
        // Appelle une méthode
        var levelBasedValueRegistry = LevelBasedValue.createDefaultRegistry();
        // Appelle une méthode
        var valueEffectRegistry = ValueEffect.createDefaultRegistry();
        // Instruction de code
        levelBasedValueRegistry.register("minestom:my_level_based_value", MyLevelBasedValue.CODEC); // NOT registered to MINECRAFT_CORE
        // Affecte une valeur
        var coder = new RegistryTranscoder<>(Transcoder.NBT, new TestRegistries(r -> {
            // Affecte une valeur
            r.enchantmentLevelBasedValues = levelBasedValueRegistry;
            // Affecte une valeur
            r.enchantmentValueEffects = valueEffectRegistry;
        // Instruction de code
        }), true, false);

        // Appelle une méthode
        var result = assertOk(ValueEffect.CODEC.encode(coder, new ValueEffect.Add(new MyLevelBasedValue())));
        // Instruction de code
        assertNull(result); // Should get nothing because MyLevelBasedValue is missing and that would create an invalid Add
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static class MyLevelBasedValue implements LevelBasedValue {
        // Appelle une méthode
        public static final StructCodec<MyLevelBasedValue> CODEC = StructCodec.struct(MyLevelBasedValue::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float calc(int level) {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<MyLevelBasedValue> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static class MyEffect implements ValueEffect {
        // Appelle une méthode
        public static final StructCodec<MyEffect> CODEC = StructCodec.struct(MyEffect::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float apply(float base, int level) {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<MyEffect> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
