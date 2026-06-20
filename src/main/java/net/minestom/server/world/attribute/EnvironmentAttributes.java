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
import net.minestom.server.world.attribute.EnvironmentAttribute.Type;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.world.attribute.EnvironmentAttributeImpl.register;

// Type declaration (class/interface/enum/record)
sealed interface EnvironmentAttributes permits EnvironmentAttribute {
    // Calls a method
    EnvironmentAttribute<RGBLike> FOG_COLOR = register("visual/fog_color", Type.RGB_COLOR, Color.BLACK);
    // Calls a method
    EnvironmentAttribute<Float> FOG_START_DISTANCE = register("visual/fog_start_distance", Type.FLOAT, 0f);
    // Calls a method
    EnvironmentAttribute<Float> FOG_END_DISTANCE = register("visual/fog_end_distance", Type.FLOAT, 1024f);
    // Calls a method
    EnvironmentAttribute<Float> SKY_FOG_END_DISTANCE = register("visual/sky_fog_end_distance", Type.FLOAT, 512f);
    // Calls a method
    EnvironmentAttribute<Float> CLOUD_FOG_END_DISTANCE = register("visual/cloud_fog_end_distance", Type.FLOAT, 2048f);
    // Calls a method
    EnvironmentAttribute<RGBLike> WATER_FOG_COLOR = register("visual/water_fog_color", Type.RGB_COLOR, new Color(0x050533));
    // Calls a method
    EnvironmentAttribute<Float> WATER_FOG_START_DISTANCE = register("visual/water_fog_start_distance", Type.FLOAT, -8f);
    // Calls a method
    EnvironmentAttribute<Float> WATER_FOG_END_DISTANCE = register("visual/water_fog_end_distance", Type.FLOAT, 96f);
    // Calls a method
    EnvironmentAttribute<RGBLike> SKY_COLOR = register("visual/sky_color", Type.RGB_COLOR, Color.BLACK);
    // Calls a method
    EnvironmentAttribute<ARGBLike> SUNRISE_SUNSET_COLOR = register("visual/sunrise_sunset_color", Type.ARGB_COLOR, AlphaColor.TRANSPARENT);
    // Calls a method
    EnvironmentAttribute<ARGBLike> CLOUD_COLOR = register("visual/cloud_color", Type.ARGB_COLOR, AlphaColor.TRANSPARENT);
    // Calls a method
    EnvironmentAttribute<Float> CLOUD_HEIGHT = register("visual/cloud_height", Type.FLOAT, 192.33f);
    // Calls a method
    EnvironmentAttribute<Float> SUN_ANGLE = register("visual/sun_angle", Type.ANGLE_DEGREES, 0f);
    // Calls a method
    EnvironmentAttribute<Float> MOON_ANGLE = register("visual/moon_angle", Type.ANGLE_DEGREES, 0f);
    // Calls a method
    EnvironmentAttribute<Float> STAR_ANGLE = register("visual/star_angle", Type.ANGLE_DEGREES, 0f);
    // Calls a method
    EnvironmentAttribute<MoonPhase> MOON_PHASE = register("visual/moon_phase", Type.MOON_PHASE, MoonPhase.FULL_MOON);
    // Calls a method
    EnvironmentAttribute<Float> STAR_BRIGHTNESS = register("visual/star_brightness", Type.FLOAT, 0f);
    // Calls a method
    EnvironmentAttribute<RGBLike> BLOCK_LIGHT_TINT = register("visual/block_light_tint", Type.RGB_COLOR, new Color(0xFFD88C));
    // Calls a method
    EnvironmentAttribute<RGBLike> SKY_LIGHT_COLOR = register("visual/sky_light_color", Type.RGB_COLOR, Color.WHITE);
    // Calls a method
    EnvironmentAttribute<Float> SKY_LIGHT_FACTOR = register("visual/sky_light_factor", Type.FLOAT, 1f);
    // Calls a method
    EnvironmentAttribute<RGBLike> NIGHT_VISION_COLOR = register("visual/night_vision_color", Type.RGB_COLOR, new Color(0x999999));
    // Calls a method
    EnvironmentAttribute<RGBLike> AMBIENT_LIGHT_COLOR = register("visual/ambient_light_color", Type.RGB_COLOR, new Color(0x000000));
    // Calls a method
    EnvironmentAttribute<Particle> DEFAULT_DRIPSTONE_PARTICLE = register("visual/default_dripstone_particle", Type.PARTICLE, Particle.DRIPPING_DRIPSTONE_WATER);
    // Calls a method
    EnvironmentAttribute<List<AmbientParticle>> AMBIENT_PARTICLES = register("visual/ambient_particles", Type.AMBIENT_PARTICLES, List.of());
    // Calls a method
    EnvironmentAttribute<BackgroundMusic> BACKGROUND_MUSIC = register("audio/background_music", Type.BACKGROUND_MUSIC, BackgroundMusic.EMPTY);
    // Calls a method
    EnvironmentAttribute<Float> MUSIC_VOLUME = register("audio/music_volume", Type.FLOAT, 1f);
    // Calls a method
    EnvironmentAttribute<AmbientSounds> AMBIENT_SOUNDS = register("audio/ambient_sounds", Type.AMBIENT_SOUNDS, AmbientSounds.EMPTY);
    // Calls a method
    EnvironmentAttribute<Boolean> FIREFLY_BUSH_SOUNDS = register("audio/firefly_bush_sounds", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<Float> SKY_LIGHT_LEVEL = register("gameplay/sky_light_level", Type.FLOAT, 15f);
    // Calls a method
    EnvironmentAttribute<Boolean> CAN_START_RAID = register("gameplay/can_start_raid", Type.BOOLEAN, true);
    // Calls a method
    EnvironmentAttribute<Boolean> WATER_EVAPORATES = register("gameplay/water_evaporates", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<BedRule> BED_RULE = register("gameplay/bed_rule", Type.BED_RULE, BedRule.CAN_SLEEP_WHEN_DARK);
    // Calls a method
    EnvironmentAttribute<Boolean> RESPAWN_ANCHOR_WORKS = register("gameplay/respawn_anchor_works", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<Boolean> NETHER_PORTAL_SPAWNS_PIGLINS = register("gameplay/nether_portal_spawns_piglin", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<Boolean> FAST_LAVA = register("gameplay/fast_lava", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<Boolean> INCREASED_FIRE_BURNOUT = register("gameplay/increased_fire_burnout", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<TriState> EYEBLOSSOM_OPEN = register("gameplay/eyeblossom_open", Type.TRI_STATE, TriState.NOT_SET);
    // Calls a method
    EnvironmentAttribute<Float> TURTLE_EGG_HATCH_CHANCE = register("gameplay/turtle_egg_hatch_chance", Type.FLOAT, 0.02f);
    // Calls a method
    EnvironmentAttribute<Boolean> PIGLINS_ZOMBIFY = register("gameplay/piglins_zombify", Type.BOOLEAN, true);
    // Calls a method
    EnvironmentAttribute<Boolean> SNOW_GOLEM_MELTS = register("gameplay/snow_golem_melts", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<Boolean> CREAKING_ACTIVE = register("gameplay/creaking_active", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<Float> SURFACE_SLIME_SPAWN_CHANCE = register("gameplay/surface_slime_spawn_chance", Type.FLOAT, 0f);
    // Calls a method
    EnvironmentAttribute<Float> CAT_WAKING_UP_GIFT_CHANCE = register("gameplay/cat_waking_up_gift_chance", Type.FLOAT, 0f);
    // Calls a method
    EnvironmentAttribute<Boolean> BEES_STAY_IN_HIVE = register("gameplay/bees_stay_in_hive", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<Boolean> MONSTERS_BURN = register("gameplay/monsters_burn", Type.BOOLEAN, false);
    // Calls a method
    EnvironmentAttribute<Boolean> CAN_PILLAGER_PATROL_SPAWN = register("gameplay/can_pillager_patrol_spawn", Type.BOOLEAN, true);
    // Calls a method
    EnvironmentAttribute<EntityActivity> VILLAGER_ACTIVITY = register("gameplay/villager_activity", Type.ACTIVITY, EntityActivity.IDLE);
    // Calls a method
    EnvironmentAttribute<EntityActivity> BABY_VILLAGER_ACTIVITY = register("gameplay/baby_villager_activity", Type.ACTIVITY, EntityActivity.IDLE);

    // Assigns a value
    Codec<EnvironmentAttribute<?>> CODEC = EnvironmentAttributeImpl.CODEC;
// End of a block/expression
}
