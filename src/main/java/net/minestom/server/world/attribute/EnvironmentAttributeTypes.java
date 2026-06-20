// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.kyori.adventure.util.ARGBLike;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.kyori.adventure.util.TriState;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.color.AlphaColor;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.entity.EntityActivity;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import net.minestom.server.world.MoonPhase;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttribute.Modifier;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.server.world.attribute.EnvironmentAttributeTypeImpl.register;

// Type declaration (class/interface/enum/record)
sealed interface EnvironmentAttributeTypes permits EnvironmentAttribute.Type {
    // Calls a method
    EnvironmentAttribute.Type<Boolean> BOOLEAN = register("boolean", Codec.BOOLEAN, Modifier.BOOLEAN_OPERATORS);
    // Calls a method
    EnvironmentAttribute.Type<TriState> TRI_STATE = register("tri_state", Codec.TRI_STATE, Map.of());
    // Calls a method
    EnvironmentAttribute.Type<Float> FLOAT = register("float", Codec.FLOAT, Modifier.FLOAT_OPERATORS);
    // Calls a method
    EnvironmentAttribute.Type<Float> ANGLE_DEGREES = register("angle_degrees", Codec.FLOAT, Modifier.FLOAT_OPERATORS);
    // Calls a method
    EnvironmentAttribute.Type<RGBLike> RGB_COLOR = register("rgb_color", Color.STRING_CODEC, Modifier.RGB_OPERATORS);
    // Calls a method
    EnvironmentAttribute.Type<ARGBLike> ARGB_COLOR = register("argb_color", AlphaColor.ARGB_STRING_CODEC, Modifier.ARGB_OPERATORS);
    // Calls a method
    EnvironmentAttribute.Type<MoonPhase> MOON_PHASE = register("moon_phase", MoonPhase.CODEC, Map.of());
    // Calls a method
    EnvironmentAttribute.Type<EntityActivity> ACTIVITY = register("activity", EntityActivity.CODEC, Map.of());
    // Calls a method
    EnvironmentAttribute.Type<BedRule> BED_RULE = register("bed_rule", BedRule.CODEC, Map.of());
    // Calls a method
    EnvironmentAttribute.Type<Particle> PARTICLE = register("particle", Particle.CODEC, Map.of());
    // Calls a method
    EnvironmentAttribute.Type<List<AmbientParticle>> AMBIENT_PARTICLES = register("ambient_particles", AmbientParticle.CODEC.list(), Map.of());
    // Calls a method
    EnvironmentAttribute.Type<BackgroundMusic> BACKGROUND_MUSIC = register("background_music", BackgroundMusic.CODEC, Map.of());
    // Calls a method
    EnvironmentAttribute.Type<AmbientSounds> AMBIENT_SOUNDS = register("ambient_sounds", AmbientSounds.CODEC, Map.of());
// End of a block/expression
}
