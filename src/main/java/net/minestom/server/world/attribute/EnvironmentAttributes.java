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
import net.minestom.server.world.attribute.EnvironmentAttribute.Type;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.world.attribute.EnvironmentAttributeImpl.register;

// Déclaration de type (classe/interface/enum/record)
sealed interface EnvironmentAttributes permits EnvironmentAttribute {
    // Appelle une méthode
    EnvironmentAttribute<RGBLike> FOG_COLOR = register("visual/fog_color", Type.RGB_COLOR, Color.BLACK);
    // Appelle une méthode
    EnvironmentAttribute<Float> FOG_START_DISTANCE = register("visual/fog_start_distance", Type.FLOAT, 0f);
    // Appelle une méthode
    EnvironmentAttribute<Float> FOG_END_DISTANCE = register("visual/fog_end_distance", Type.FLOAT, 1024f);
    // Appelle une méthode
    EnvironmentAttribute<Float> SKY_FOG_END_DISTANCE = register("visual/sky_fog_end_distance", Type.FLOAT, 512f);
    // Appelle une méthode
    EnvironmentAttribute<Float> CLOUD_FOG_END_DISTANCE = register("visual/cloud_fog_end_distance", Type.FLOAT, 2048f);
    // Appelle une méthode
    EnvironmentAttribute<RGBLike> WATER_FOG_COLOR = register("visual/water_fog_color", Type.RGB_COLOR, new Color(0x050533));
    // Appelle une méthode
    EnvironmentAttribute<Float> WATER_FOG_START_DISTANCE = register("visual/water_fog_start_distance", Type.FLOAT, -8f);
    // Appelle une méthode
    EnvironmentAttribute<Float> WATER_FOG_END_DISTANCE = register("visual/water_fog_end_distance", Type.FLOAT, 96f);
    // Appelle une méthode
    EnvironmentAttribute<RGBLike> SKY_COLOR = register("visual/sky_color", Type.RGB_COLOR, Color.BLACK);
    // Appelle une méthode
    EnvironmentAttribute<ARGBLike> SUNRISE_SUNSET_COLOR = register("visual/sunrise_sunset_color", Type.ARGB_COLOR, AlphaColor.TRANSPARENT);
    // Appelle une méthode
    EnvironmentAttribute<ARGBLike> CLOUD_COLOR = register("visual/cloud_color", Type.ARGB_COLOR, AlphaColor.TRANSPARENT);
    // Appelle une méthode
    EnvironmentAttribute<Float> CLOUD_HEIGHT = register("visual/cloud_height", Type.FLOAT, 192.33f);
    // Appelle une méthode
    EnvironmentAttribute<Float> SUN_ANGLE = register("visual/sun_angle", Type.ANGLE_DEGREES, 0f);
    // Appelle une méthode
    EnvironmentAttribute<Float> MOON_ANGLE = register("visual/moon_angle", Type.ANGLE_DEGREES, 0f);
    // Appelle une méthode
    EnvironmentAttribute<Float> STAR_ANGLE = register("visual/star_angle", Type.ANGLE_DEGREES, 0f);
    // Appelle une méthode
    EnvironmentAttribute<MoonPhase> MOON_PHASE = register("visual/moon_phase", Type.MOON_PHASE, MoonPhase.FULL_MOON);
    // Appelle une méthode
    EnvironmentAttribute<Float> STAR_BRIGHTNESS = register("visual/star_brightness", Type.FLOAT, 0f);
    // Appelle une méthode
    EnvironmentAttribute<RGBLike> SKY_LIGHT_COLOR = register("visual/sky_light_color", Type.RGB_COLOR, Color.WHITE);
    // Appelle une méthode
    EnvironmentAttribute<Float> SKY_LIGHT_FACTOR = register("visual/sky_light_factor", Type.FLOAT, 1f);
    // Appelle une méthode
    EnvironmentAttribute<Particle> DEFAULT_DRIPSTONE_PARTICLE = register("visual/default_dripstone_particle", Type.PARTICLE, Particle.DRIPPING_DRIPSTONE_WATER);
    // Appelle une méthode
    EnvironmentAttribute<List<AmbientParticle>> AMBIENT_PARTICLES = register("visual/ambient_particles", Type.AMBIENT_PARTICLES, List.of());
    // Appelle une méthode
    EnvironmentAttribute<BackgroundMusic> BACKGROUND_MUSIC = register("audio/background_music", Type.BACKGROUND_MUSIC, BackgroundMusic.EMPTY);
    // Appelle une méthode
    EnvironmentAttribute<Float> MUSIC_VOLUME = register("audio/music_volume", Type.FLOAT, 1f);
    // Appelle une méthode
    EnvironmentAttribute<AmbientSounds> AMBIENT_SOUNDS = register("audio/ambient_sounds", Type.AMBIENT_SOUNDS, AmbientSounds.EMPTY);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> FIREFLY_BUSH_SOUNDS = register("audio/firefly_bush_sounds", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<Float> SKY_LIGHT_LEVEL = register("gameplay/sky_light_level", Type.FLOAT, 15f);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> CAN_START_RAID = register("gameplay/can_start_raid", Type.BOOLEAN, true);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> WATER_EVAPORATES = register("gameplay/water_evaporates", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<BedRule> BED_RULE = register("gameplay/bed_rule", Type.BED_RULE, BedRule.CAN_SLEEP_WHEN_DARK);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> RESPAWN_ANCHOR_WORKS = register("gameplay/respawn_anchor_works", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> NETHER_PORTAL_SPAWNS_PIGLINS = register("gameplay/nether_portal_spawns_piglin", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> FAST_LAVA = register("gameplay/fast_lava", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> INCREASED_FIRE_BURNOUT = register("gameplay/increased_fire_burnout", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<TriState> EYEBLOSSOM_OPEN = register("gameplay/eyeblossom_open", Type.TRI_STATE, TriState.NOT_SET);
    // Appelle une méthode
    EnvironmentAttribute<Float> TURTLE_EGG_HATCH_CHANCE = register("gameplay/turtle_egg_hatch_chance", Type.FLOAT, 0f);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> PIGLINS_ZOMBIFY = register("gameplay/piglins_zombify", Type.BOOLEAN, true);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> SNOW_GOLEM_MELTS = register("gameplay/snow_golem_melts", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> CREAKING_ACTIVE = register("gameplay/creaking_active", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<Float> SURFACE_SLIME_SPAWN_CHANCE = register("gameplay/surface_slime_spawn_chance", Type.FLOAT, 0f);
    // Appelle une méthode
    EnvironmentAttribute<Float> CAT_WAKING_UP_GIFT_CHANCE = register("gameplay/cat_waking_up_gift_chance", Type.FLOAT, 0f);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> BEES_STAY_IN_HIVE = register("gameplay/bees_stay_in_hive", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> MONSTERS_BURN = register("gameplay/monsters_burn", Type.BOOLEAN, false);
    // Appelle une méthode
    EnvironmentAttribute<Boolean> CAN_PILLAGER_PATROL_SPAWN = register("gameplay/can_pillager_patrol_spawn", Type.BOOLEAN, true);
    // Appelle une méthode
    EnvironmentAttribute<EntityActivity> VILLAGER_ACTIVITY = register("gameplay/villager_activity", Type.ACTIVITY, EntityActivity.IDLE);
    // Appelle une méthode
    EnvironmentAttribute<EntityActivity> BABY_VILLAGER_ACTIVITY = register("gameplay/baby_villager_activity", Type.ACTIVITY, EntityActivity.IDLE);

    // Affecte une valeur
    Codec<EnvironmentAttribute<?>> CODEC = EnvironmentAttributeImpl.CODEC;
// Fin d'un bloc/d'une expression
}
