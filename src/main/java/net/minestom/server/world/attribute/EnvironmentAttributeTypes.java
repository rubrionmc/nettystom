// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.util.ARGBLike;
// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.kyori.adventure.util.TriState;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.color.AlphaColor;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityActivity;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import net.minestom.server.world.MoonPhase;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute.Modifier;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.minestom.server.world.attribute.EnvironmentAttributeTypeImpl.register;

// Déclaration de type (classe/interface/enum/record)
sealed interface EnvironmentAttributeTypes permits EnvironmentAttribute.Type {
    // Appelle une méthode
    EnvironmentAttribute.Type<Boolean> BOOLEAN = register("boolean", Codec.BOOLEAN, Modifier.BOOLEAN_OPERATORS);
    // Appelle une méthode
    EnvironmentAttribute.Type<TriState> TRI_STATE = register("tri_state", Codec.TRI_STATE, Map.of());
    // Appelle une méthode
    EnvironmentAttribute.Type<Float> FLOAT = register("float", Codec.FLOAT, Modifier.FLOAT_OPERATORS);
    // Appelle une méthode
    EnvironmentAttribute.Type<Float> ANGLE_DEGREES = register("angle_degrees", Codec.FLOAT, Modifier.FLOAT_OPERATORS);
    // Appelle une méthode
    EnvironmentAttribute.Type<RGBLike> RGB_COLOR = register("rgb_color", Color.STRING_CODEC, Modifier.RGB_OPERATORS);
    // Appelle une méthode
    EnvironmentAttribute.Type<ARGBLike> ARGB_COLOR = register("argb_color", AlphaColor.ARGB_STRING_CODEC, Modifier.ARGB_OPERATORS);
    // Appelle une méthode
    EnvironmentAttribute.Type<MoonPhase> MOON_PHASE = register("moon_phase", MoonPhase.CODEC, Map.of());
    // Appelle une méthode
    EnvironmentAttribute.Type<EntityActivity> ACTIVITY = register("activity", EntityActivity.CODEC, Map.of());
    // Appelle une méthode
    EnvironmentAttribute.Type<BedRule> BED_RULE = register("bed_rule", BedRule.CODEC, Map.of());
    // Appelle une méthode
    EnvironmentAttribute.Type<Particle> PARTICLE = register("particle", Particle.CODEC, Map.of());
    // Appelle une méthode
    EnvironmentAttribute.Type<List<AmbientParticle>> AMBIENT_PARTICLES = register("ambient_particles", AmbientParticle.CODEC.list(), Map.of());
    // Appelle une méthode
    EnvironmentAttribute.Type<BackgroundMusic> BACKGROUND_MUSIC = register("background_music", BackgroundMusic.CODEC, Map.of());
    // Appelle une méthode
    EnvironmentAttribute.Type<AmbientSounds> AMBIENT_SOUNDS = register("ambient_sounds", AmbientSounds.CODEC, Map.of());
// Fin d'un bloc/d'une expression
}
