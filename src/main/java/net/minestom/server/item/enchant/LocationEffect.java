// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.List;

// Début d'une méthode/d'un bloc
public non-sealed interface LocationEffect extends Enchantment.Effect {

    // Affecte une valeur
    StructCodec<LocationEffect> CODEC = Codec.RegistryTaggedUnion(
            // Instruction de code
            Registries::enchantmentLocationEffects, LocationEffect::codec);

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<StructCodec<? extends LocationEffect>> createDefaultRegistry() {
        // Appelle une méthode
        final DynamicRegistry<StructCodec<? extends LocationEffect>> registry = DynamicRegistry.create(Key.key("minestom:enchantment_value_effect"));
        // Appelle une méthode
        registry.register("all_of", AllOf.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("apply_mob_effect", EntityEffect.ApplyPotionEffect.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("attribute", AttributeEffect.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("change_item_damage", EntityEffect.ChangeItemDamage.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("damage_entity", EntityEffect.DamageEntity.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("explode", EntityEffect.Explode.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("ignite", EntityEffect.Ignite.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("apply_impulse", EntityEffect.ApplyImpulse.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("apply_exhaustion", EntityEffect.ApplyExhaustion.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("play_sound", EntityEffect.PlaySound.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("replace_block", EntityEffect.ReplaceBlock.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("replace_disk", EntityEffect.ReplaceDisc.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("run_function", EntityEffect.RunFunction.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("set_block_properties", EntityEffect.SetBlockProperties.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("spawn_particles", EntityEffect.SpawnParticles.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("summon_entity", EntityEffect.SummonEntity.CODEC, DataPack.MINECRAFT_CORE);
        // Renvoie une valeur à l'appelant
        return registry;
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    StructCodec<? extends LocationEffect> codec();

    // Déclaration de type (classe/interface/enum/record)
    record AllOf(List<LocationEffect> effect) implements LocationEffect {
        // Affecte une valeur
        public static final StructCodec<AllOf> CODEC = StructCodec.struct(
                // Instruction de code
                "effects", LocationEffect.CODEC.list(), AllOf::effect,
                // Instruction de code
                AllOf::new
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        public AllOf {
            // Appelle une méthode
            effect = List.copyOf(effect);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<AllOf> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
