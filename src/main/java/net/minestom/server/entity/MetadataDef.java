// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.CatSoundVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.CatVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfSoundVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.golem.CopperGolemMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.water.fish.SalmonMeta;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ResolvableProfile;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static net.minestom.server.entity.MetadataDefImpl.*;

/**
 * List of all entity metadata.
 * <p>
 * Classes must be used (and not interfaces) to enforce loading order.
 * <p>
 * When using this class directly, ensure that you are using fields on the most inner class,
 * for example {@link Player#ENTITY_FLAGS} and not {@link MetadataDef#ENTITY_FLAGS}.
 * You need do this as some classes have different default values.
 */
// Annotation pour l'élément suivant
@SuppressWarnings({"unused", "SpellCheckingInspection"})
// Déclaration de type (classe/interface/enum/record)
public sealed class MetadataDef {
    // Appelle une méthode
    public static final Entry<Byte> ENTITY_FLAGS = index(0, Metadata::Byte, (byte) 0);
    // Appelle une méthode
    public static final Entry<Boolean> IS_ON_FIRE = bitMask(0, (byte) 0x01, false);
    // Appelle une méthode
    public static final Entry<Boolean> IS_CROUCHING = bitMask(0, (byte) 0x02, false);
    // Appelle une méthode
    public static final Entry<Boolean> IS_SPRINTING = bitMask(0, (byte) 0x08, false);
    // Appelle une méthode
    public static final Entry<Boolean> IS_SWIMMING = bitMask(0, (byte) 0x10, false);
    // Appelle une méthode
    public static final Entry<Boolean> IS_INVISIBLE = bitMask(0, (byte) 0x20, false);
    // Appelle une méthode
    public static final Entry<Boolean> HAS_GLOWING_EFFECT = bitMask(0, (byte) 0x40, false);
    // Appelle une méthode
    public static final Entry<Boolean> IS_FLYING_WITH_ELYTRA = bitMask(0, (byte) 0x80, false);
    // Appelle une méthode
    public static final Entry<Integer> AIR_TICKS = index(1, Metadata::VarInt, 300);
    // Appelle une méthode
    public static final Entry<@Nullable Component> CUSTOM_NAME = index(2, Metadata::OptComponent, null);
    // Appelle une méthode
    public static final Entry<Boolean> CUSTOM_NAME_VISIBLE = index(3, Metadata::Boolean, false);
    // Appelle une méthode
    public static final Entry<Boolean> IS_SILENT = index(4, Metadata::Boolean, false);
    // Appelle une méthode
    public static final Entry<Boolean> HAS_NO_GRAVITY = index(5, Metadata::Boolean, false);
    // Appelle une méthode
    public static final Entry<EntityPose> POSE = index(6, Metadata::Pose, EntityPose.STANDING);
    // Appelle une méthode
    public static final Entry<Integer> TICKS_FROZEN = index(7, Metadata::VarInt, 0);

    // Début d'une méthode/d'un bloc
    public static final class Interaction extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Float> WIDTH = index(0, Metadata::Float, 1f);
        // Appelle une méthode
        public static final Entry<Float> HEIGHT = index(1, Metadata::Float, 1f);
        // Appelle une méthode
        public static final Entry<Boolean> RESPONSIVE = index(2, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class Display extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Integer> INTERPOLATION_DELAY = index(0, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> TRANSFORMATION_INTERPOLATION_DURATION = index(1, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> POSITION_ROTATION_INTERPOLATION_DURATION = index(2, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Point> TRANSLATION = index(3, Metadata::Vector3, Vec.ZERO);
        // Appelle une méthode
        public static final Entry<Point> SCALE = index(4, Metadata::Vector3, Vec.ONE);
        // Appelle une méthode
        public static final Entry<float[]> ROTATION_LEFT = index(5, Metadata::Quaternion, new float[]{0, 0, 0, 1});
        // Appelle une méthode
        public static final Entry<float[]> ROTATION_RIGHT = index(6, Metadata::Quaternion, new float[]{0, 0, 0, 1});
        // Appelle une méthode
        public static final Entry<Byte> BILLBOARD_CONSTRAINTS = index(7, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Integer> BRIGHTNESS_OVERRIDE = index(8, Metadata::VarInt, -1);
        // Appelle une méthode
        public static final Entry<Float> VIEW_RANGE = index(9, Metadata::Float, 1f);
        // Appelle une méthode
        public static final Entry<Float> SHADOW_RADIUS = index(10, Metadata::Float, 0f);
        // Appelle une méthode
        public static final Entry<Float> SHADOW_STRENGTH = index(11, Metadata::Float, 1f);
        // Appelle une méthode
        public static final Entry<Float> WIDTH = index(12, Metadata::Float, 0f);
        // Appelle une méthode
        public static final Entry<Float> HEIGHT = index(13, Metadata::Float, 0f);
        // Appelle une méthode
        public static final Entry<Integer> GLOW_COLOR_OVERRIDE = index(14, Metadata::VarInt, -1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class BlockDisplay extends Display {
        // Appelle une méthode
        public static final Entry<Block> DISPLAYED_BLOCK_STATE = index(0, Metadata::BlockState, Block.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class ItemDisplay extends Display {
        // Appelle une méthode
        public static final Entry<ItemStack> DISPLAYED_ITEM = index(0, Metadata::ItemStack, ItemStack.AIR);
        // Appelle une méthode
        public static final Entry<Byte> DISPLAY_TYPE = index(1, Metadata::Byte, (byte) 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class TextDisplay extends Display {
        // Appelle une méthode
        public static final Entry<Component> TEXT = index(0, Metadata::Component, Component.empty());
        // Appelle une méthode
        public static final Entry<Integer> LINE_WIDTH = index(1, Metadata::VarInt, 200);
        // Appelle une méthode
        public static final Entry<Integer> BACKGROUND_COLOR = index(2, Metadata::VarInt, 0x40000000);
        // Appelle une méthode
        public static final Entry<Byte> TEXT_OPACITY = index(3, Metadata::Byte, (byte) -1);
        // Appelle une méthode
        public static final Entry<Byte> TEXT_DISPLAY_FLAGS = index(4, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> HAS_SHADOW = bitMask(4, (byte) 0x01, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SEE_THROUGH = bitMask(4, (byte) 0x02, false);
        // Appelle une méthode
        public static final Entry<Boolean> USE_DEFAULT_BACKGROUND_COLOR = bitMask(4, (byte) 0x04, false);
        // Appelle une méthode
        public static final Entry<Byte> ALIGNMENT = byteMask(4, (byte) 0x18, 3, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> ALIGN_LEFT = bitMask(4, (byte) 0x08, false);
        // Appelle une méthode
        public static final Entry<Boolean> ALIGN_RIGHT = bitMask(4, (byte) 0x10, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class ExperienceOrb extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Integer> VALUE = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class ThrownItemProjectile extends MetadataDef {
        // Appelle une méthode
        public static final Entry<ItemStack> ITEM = index(0, Metadata::ItemStack, ItemStack.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class EyeOfEnder extends MetadataDef {
        // Appelle une méthode
        public static final Entry<ItemStack> ITEM = index(0, Metadata::ItemStack, ItemStack.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class FallingBlock extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Point> SPAWN_POSITION = index(0, Metadata::BlockPosition, Vec.ZERO);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class AreaEffectCloud extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Float> RADIUS = index(0, Metadata::Float, 0.5f);
        // Appelle une méthode
        public static final Entry<Boolean> WAITING = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Particle> PARTICLE = index(2, Metadata::Particle, Particle.EFFECT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class FishingHook extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Integer> HOOKED = index(0, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_CATCHABLE = index(1, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class AbstractArrow extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Byte> ARROW_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_CRITICAL = bitMask(0, (byte) 0x01, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_NO_CLIP = bitMask(0, (byte) 0x02, false);
        // Appelle une méthode
        public static final Entry<Byte> PIERCING_LEVEL = index(1, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IN_GROUND = index(2, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Arrow extends AbstractArrow {
        // Appelle une méthode
        public static final Entry<Integer> COLOR = index(0, Metadata::VarInt, -1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class ThrownTrident extends AbstractArrow {
        // Appelle une méthode
        public static final Entry<Byte> LOYALTY_LEVEL = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> HAS_ENCHANTMENT_GLINT = index(1, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class AbstractVehicle extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Integer> SHAKING_POWER = index(0, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> SHAKING_DIRECTION = index(1, Metadata::VarInt, 1);
        // Appelle une méthode
        public static final Entry<Float> SHAKING_MULTIPLIER = index(2, Metadata::Float, 0f);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Boat extends AbstractVehicle {
        // Appelle une méthode
        public static final Entry<Boolean> IS_LEFT_PADDLE_TURNING = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_RIGHT_PADDLE_TURNING = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Integer> SPLASH_TIMER = index(2, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class AbstractMinecart extends AbstractVehicle {
        // Appelle une méthode
        public static final Entry<@Nullable Block> CUSTOM_BLOCK_STATE = index(0, Metadata::OptBlockState, null);
        // Appelle une méthode
        public static final Entry<Integer> CUSTOM_BLOCK_Y_POSITION = index(1, Metadata::VarInt, 6);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class MinecartFurnace extends AbstractMinecart {
        // Appelle une méthode
        public static final Entry<Boolean> HAS_FUEL = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class MinecartCommandBlock extends AbstractMinecart {
        // Appelle une méthode
        public static final Entry<String> COMMAND = index(0, Metadata::String, "");
        // Appelle une méthode
        public static final Entry<Component> LAST_OUTPUT = index(1, Metadata::Component, Component.empty());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class EndCrystal extends MetadataDef {
        // Appelle une méthode
        public static final Entry<@Nullable Point> BEAM_TARGET = index(0, Metadata::OptBlockPosition, null);
        // Appelle une méthode
        public static final Entry<Boolean> SHOW_BOTTOM = index(1, Metadata::Boolean, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class SmartFireball extends MetadataDef {
        // Appelle une méthode
        public static final Entry<ItemStack> ITEM = index(0, Metadata::ItemStack, ItemStack.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Fireball extends MetadataDef {
        // Appelle une méthode
        public static final Entry<ItemStack> ITEM = index(0, Metadata::ItemStack, ItemStack.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class WitherSkull extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Boolean> IS_INVULNERABLE = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class FireworkRocketEntity extends MetadataDef {
        // Appelle une méthode
        public static final Entry<ItemStack> ITEM = index(0, Metadata::ItemStack, ItemStack.AIR);
        // Appelle une méthode
        public static final Entry<@Nullable Integer> SHOOTER_ENTITY_ID = index(1, Metadata::OptVarInt, null);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SHOT_AT_ANGLE = index(2, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class Hanging extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Direction> DIRECTION = index(0, Metadata::Direction, Direction.SOUTH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class ItemFrame extends Hanging {
        // Appelle une méthode
        public static final Entry<ItemStack> ITEM = index(0, Metadata::ItemStack, ItemStack.AIR);
        // Appelle une méthode
        public static final Entry<Integer> ROTATION = index(1, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Painting extends Hanging {
        // Affecte une valeur
        public static final Entry<Holder<PaintingVariant>> VARIANT = index(0, Metadata::PaintingVariant,
                                                                           // Instruction de code
                                                                           PaintingVariant.KEBAB);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class ItemEntity extends MetadataDef {
        // Appelle une méthode
        public static final Entry<ItemStack> ITEM = index(0, Metadata::ItemStack, ItemStack.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class LivingEntity extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Byte> LIVING_ENTITY_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_HAND_ACTIVE = bitMask(0, (byte) 0x01, false);
        // Appelle une méthode
        public static final Entry<Boolean> ACTIVE_HAND = bitMask(0, (byte) 0x02, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_RIPTIDE_SPIN_ATTACK = bitMask(0, (byte) 0x04, false);
        // Appelle une méthode
        public static final Entry<Float> HEALTH = index(1, Metadata::Float, 1f);
        // Appelle une méthode
        public static final Entry<List<Particle>> POTION_EFFECT_PARTICLES = index(2, Metadata::ParticleList, List.of());
        // Appelle une méthode
        public static final Entry<Boolean> IS_POTION_EFFECT_AMBIANT = index(3, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Integer> NUMBER_OF_ARROWS = index(4, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> NUMBER_OF_BEE_STINGERS = index(5, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<@Nullable Point> LOCATION_OF_BED = index(6, Metadata::OptBlockPosition, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class Avatar extends LivingEntity {
        // Appelle une méthode
        public static final Entry<MainHand> MAIN_HAND = index(0, Metadata::MainHand, MainHand.RIGHT);
        // Appelle une méthode
        public static final Entry<Byte> DISPLAYED_MODEL_PARTS_FLAGS = index(1, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_CAPE_ENABLED = bitMask(1, (byte) 0x01, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_JACKET_ENABLED = bitMask(1, (byte) 0x02, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_LEFT_SLEEVE_ENABLED = bitMask(1, (byte) 0x04, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_RIGHT_SLEEVE_ENABLED = bitMask(1, (byte) 0x08, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_LEFT_PANTS_LEG_ENABLED = bitMask(1, (byte) 0x10, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_RIGHT_PANTS_LEG_ENABLED = bitMask(1, (byte) 0x20, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_HAT_ENABLED = bitMask(1, (byte) 0x40, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Player extends Avatar {
        // Appelle une méthode
        public static final Entry<Float> ADDITIONAL_HEARTS = index(0, Metadata::Float, 0f);
        // Appelle une méthode
        public static final Entry<Integer> SCORE = index(1, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<@Nullable Integer> LEFT_SHOULDER_ENTITY_DATA = index(2, Metadata::OptVarInt, null);
        // Appelle une méthode
        public static final Entry<@Nullable Integer> RIGHT_SHOULDER_ENTITY_DATA = index(3, Metadata::OptVarInt, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Mannequin extends Avatar {
        // Maniquens have their model flags with all flags enabled compared to Avatar.
        // Appelle une méthode
        public static final Entry<Byte> DISPLAYED_MODEL_PARTS_FLAGS = index(-1, Metadata::Byte, (byte) 0x7F);
        // Appelle une méthode
        public static final Entry<Boolean> IS_CAPE_ENABLED = bitMask(-1, (byte) 0x01, true);
        // Appelle une méthode
        public static final Entry<Boolean> IS_JACKET_ENABLED = bitMask(-1, (byte) 0x02, true);
        // Appelle une méthode
        public static final Entry<Boolean> IS_LEFT_SLEEVE_ENABLED = bitMask(-1, (byte) 0x04, true);
        // Appelle une méthode
        public static final Entry<Boolean> IS_RIGHT_SLEEVE_ENABLED = bitMask(-1, (byte) 0x08, true);
        // Appelle une méthode
        public static final Entry<Boolean> IS_LEFT_PANTS_LEG_ENABLED = bitMask(-1, (byte) 0x10, true);
        // Appelle une méthode
        public static final Entry<Boolean> IS_RIGHT_PANTS_LEG_ENABLED = bitMask(-1, (byte) 0x20, true);
        // Appelle une méthode
        public static final Entry<Boolean> IS_HAT_ENABLED = bitMask(-1, (byte) 0x40, true);
        // Appelle une méthode
        public static final Entry<ResolvableProfile> PROFILE = index(0, Metadata::ResolvableProfile, ResolvableProfile.EMPTY);
        // Appelle une méthode
        public static final Entry<Boolean> IMMOVABLE = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<@Nullable Component> DESCRIPTION = index(2, Metadata::OptComponent, Component.translatable("entity.minecraft.mannequin.label"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class ArmorStand extends LivingEntity {
        // Appelle une méthode
        public static final Entry<Byte> ARMOR_STAND_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SMALL = bitMask(0, (byte) 0x01, false);
        // Appelle une méthode
        public static final Entry<Boolean> HAS_ARMS = bitMask(0, (byte) 0x04, false);
        // Appelle une méthode
        public static final Entry<Boolean> HAS_NO_BASE_PLATE = bitMask(0, (byte) 0x08, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_MARKER = bitMask(0, (byte) 0x10, false);
        // Appelle une méthode
        public static final Entry<Point> HEAD_ROTATION = index(1, Metadata::Rotation, Vec.ZERO);
        // Appelle une méthode
        public static final Entry<Point> BODY_ROTATION = index(2, Metadata::Rotation, Vec.ZERO);
        // Appelle une méthode
        public static final Entry<Point> LEFT_ARM_ROTATION = index(3, Metadata::Rotation, new Vec(-10, 0, -10));
        // Appelle une méthode
        public static final Entry<Point> RIGHT_ARM_ROTATION = index(4, Metadata::Rotation, new Vec(-15, 0, 10));
        // Appelle une méthode
        public static final Entry<Point> LEFT_LEG_ROTATION = index(5, Metadata::Rotation, new Vec(-1, 0, -1));
        // Appelle une méthode
        public static final Entry<Point> RIGHT_LEG_ROTATION = index(6, Metadata::Rotation, new Vec(1, 0, 1));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class Mob extends LivingEntity {
        // Appelle une méthode
        public static final Entry<Byte> MOB_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> NO_AI = bitMask(0, (byte) 0x01, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_LEFT_HANDED = bitMask(0, (byte) 0x02, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_AGGRESSIVE = bitMask(0, (byte) 0x04, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Allay extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_DANCING = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> CAN_DUPLICATE = index(1, Metadata::Boolean, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Armadillo extends AgeableMob {
        // Affecte une valeur
        public static final Entry<ArmadilloMeta.State> STATE = index(0, Metadata::ArmadilloState,
                                                                     // Instruction de code
                                                                     ArmadilloMeta.State.IDLE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Bat extends Mob {
        // Appelle une méthode
        public static final Entry<Byte> BAT_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_HANGING = bitMask(0, (byte) 0x01, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Dolphin extends Mob {
        // Appelle une méthode
        public static final Entry<Point> TREASURE_POSITION = index(0, Metadata::BlockPosition, Vec.ZERO);
        // Appelle une méthode
        public static final Entry<Boolean> HAS_FISH = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Integer> MOISTURE_LEVEL = index(2, Metadata::VarInt, 2400);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class AbstractFish extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> FROM_BUCKET = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class PufferFish extends AbstractFish {
        // Appelle une méthode
        public static final Entry<Integer> PUFF_STATE = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Salmon extends AbstractFish {
        // Appelle une méthode
        public static final Entry<Integer> SIZE = index(0, Metadata::VarInt, SalmonMeta.Size.SMALL.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class TropicalFish extends AbstractFish {
        // Appelle une méthode
        public static final Entry<Integer> VARIANT = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Tadpole extends AbstractFish {
        // Appelle une méthode
        public static final Entry<Boolean> AGE_LOCKED = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class AgeableMob extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_BABY = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> AGE_LOCKED = index(1, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Sniffer extends AgeableMob {
        // Appelle une méthode
        public static final Entry<SnifferMeta.State> STATE = index(0, Metadata::SnifferState, SnifferMeta.State.IDLING);
        // Appelle une méthode
        public static final Entry<Integer> DROP_SEED_AT_TICK = index(1, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class AbstractHorse extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Byte> ABSTRACT_HORSE_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_TAME = bitMask(0, (byte) 0x02, false);
        // 0x04 is unused, historically was for saddle
        // Appelle une méthode
        public static final Entry<Boolean> HAS_BRED = bitMask(0, (byte) 0x08, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_EATING = bitMask(0, (byte) 0x10, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_REARING = bitMask(0, (byte) 0x20, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_MOUTH_OPEN = bitMask(0, (byte) 0x40, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Horse extends AbstractHorse {
        // Appelle une méthode
        public static final Entry<Integer> VARIANT = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Camel extends AbstractHorse {
        // Appelle une méthode
        public static final Entry<Boolean> DASHING = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Long> LAST_POSE_CHANGE_TICK = index(1, Metadata::VarLong, 0L);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class ChestedHorse extends AbstractHorse {
        // Appelle une méthode
        public static final Entry<Boolean> HAS_CHEST = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Llama extends ChestedHorse {
        // Appelle une méthode
        public static final Entry<Integer> STRENGTH = index(0, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> VARIANT = index(1, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Axolotl extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Integer> VARIANT = index(0, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_PLAYING_DEAD = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_FROM_BUCKET = index(2, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Bee extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Byte> BEE_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_ROLLING = bitMask(0, (byte) 0x02, false);
        // Appelle une méthode
        public static final Entry<Boolean> HAS_STUNG = bitMask(0, (byte) 0x04, false);
        // Appelle une méthode
        public static final Entry<Boolean> HAS_NECTAR = bitMask(0, (byte) 0x08, false);
        // Appelle une méthode
        public static final Entry<Long> ANGER_END_TIME = index(1, Metadata::VarLong, -1L);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class GlowSquid extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Integer> DARK_TICKS_REMAINING = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Fox extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Integer> VARIANT = index(0, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Byte> FOX_FLAGS = index(1, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SITTING = bitMask(1, (byte) 0x01, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_CROUCHING = bitMask(1, (byte) 0x04, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_INTERESTED = bitMask(1, (byte) 0x08, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_POUNCING = bitMask(1, (byte) 0x10, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SLEEPING = bitMask(1, (byte) 0x20, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_FACEPLANTED = bitMask(1, (byte) 0x40, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_DEFENDING = bitMask(1, (byte) 0x80, false);
        // Appelle une méthode
        public static final Entry<@Nullable UUID> FIRST_UUID = index(2, Metadata::OptUUID, null);
        // Appelle une méthode
        public static final Entry<@Nullable UUID> SECOND_UUID = index(3, Metadata::OptUUID, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Frog extends AgeableMob {
        // Affecte une valeur
        public static final Entry<RegistryKey<FrogVariant>> VARIANT = index(0, Metadata::FrogVariant,
                                                                            // Instruction de code
                                                                            FrogVariant.TEMPERATE);
        // Appelle une méthode
        public static final Entry<@Nullable Integer> TONGUE_TARGET = index(1, Metadata::OptVarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Ocelot extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_TRUSTING = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Panda extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Integer> BREED_TIMER = index(0, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> SNEEZE_TIMER = index(1, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> EAT_TIMER = index(2, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Byte> MAIN_GENE = index(3, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Byte> HIDDEN_GENE = index(4, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Byte> PANDA_FLAGS = index(5, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SNEEZING = bitMask(5, (byte) 0x02, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_ROLLING = bitMask(5, (byte) 0x04, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SITTING = bitMask(5, (byte) 0x08, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_ON_BACK = bitMask(5, (byte) 0x10, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Chicken extends AgeableMob {
        // Affecte une valeur
        public static final Entry<RegistryKey<ChickenVariant>> VARIANT = index(0, Metadata::ChickenVariant,
                                                                               // Instruction de code
                                                                               ChickenVariant.TEMPERATE);
        // Appelle une méthode
        public static final Entry<RegistryKey<ChickenSoundVariant>> SOUND_VARIANT = index(1, Metadata::ChickenSoundVariant, ChickenSoundVariant.CLASSIC);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Cow extends AgeableMob {
        // Affecte une valeur
        public static final Entry<RegistryKey<CowVariant>> VARIANT = index(0, Metadata::CowVariant,
                                                                           // Instruction de code
                                                                           CowVariant.TEMPERATE);
        // Appelle une méthode
        public static final Entry<RegistryKey<CowSoundVariant>> SOUND_VARIANT = index(1, Metadata::CowSoundVariant, CowSoundVariant.CLASSIC);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Pig extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Integer> BOOST_TIME = index(0, Metadata::VarInt, 0);
        // Affecte une valeur
        public static final Entry<RegistryKey<PigVariant>> VARIANT = index(1, Metadata::PigVariant,
                                                                           // Instruction de code
                                                                           PigVariant.TEMPERATE);
        // Appelle une méthode
        public static final Entry<RegistryKey<PigSoundVariant>> SOUND_VARIANT = index(2, Metadata::PigSoundVariant, PigSoundVariant.CLASSIC);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Rabbit extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Integer> TYPE = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Turtle extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Boolean> HAS_EGG = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_LAYING_EGG = index(1, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class PolarBear extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_STANDING_UP = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Mooshroom extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Integer> VARIANT = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Hoglin extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Boolean> IMMUNE_ZOMBIFICATION = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Sheep extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Byte> SHEEP_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Byte> COLOR_ID = byteMask(0, (byte) 0x0F, 0, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SHEARED = bitMask(0, (byte) 0x10, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Strider extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Integer> FUNGUS_BOOST = index(0, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SHAKING = index(1, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Goat extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_SCREAMING_GOAT = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> HAS_LEFT_HORN = index(1, Metadata::Boolean, true);
        // Appelle une méthode
        public static final Entry<Boolean> HAS_RIGHT_HORN = index(2, Metadata::Boolean, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class TameableAnimal extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Byte> TAMEABLE_ANIMAL_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SITTING = bitMask(0, (byte) 0x01, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_TAMED = bitMask(0, (byte) 0x04, false);
        // Appelle une méthode
        public static final Entry<@Nullable UUID> OWNER = index(1, Metadata::OptUUID, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Cat extends TameableAnimal {
        // Appelle une méthode
        public static final Entry<RegistryKey<CatVariant>> VARIANT = index(0, Metadata::CatVariant, CatVariant.BLACK);
        // Appelle une méthode
        public static final Entry<Boolean> IS_LYING = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_RELAXED = index(2, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Integer> COLLAR_COLOR = index(3, Metadata::VarInt, 14);
        // Appelle une méthode
        public static final Entry<RegistryKey<CatSoundVariant>> SOUND_VARIANT = index(4, Metadata::CatSoundVariant, CatSoundVariant.CLASSIC);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Wolf extends TameableAnimal {
        // Appelle une méthode
        public static final Entry<Boolean> IS_BEGGING = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Integer> COLLAR_COLOR = index(1, Metadata::VarInt, 14);
        // Appelle une méthode
        public static final Entry<Long> ANGER_TIME = index(2, Metadata::VarLong, -1L);
        // Appelle une méthode
        public static final Entry<RegistryKey<WolfVariant>> VARIANT = index(3, Metadata::WolfVariant, WolfVariant.PALE);
        // Affecte une valeur
        public static final Entry<RegistryKey<WolfSoundVariant>> SOUND_VARIANT = index(4, Metadata::WolfSoundVariant,
                                                                                       // Instruction de code
                                                                                       WolfSoundVariant.CLASSIC);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Parrot extends TameableAnimal {
        // Appelle une méthode
        public static final Entry<Integer> VARIANT = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class AbstractNautilus extends TameableAnimal {
        // Appelle une méthode
        public static final Entry<Boolean> DASH = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class ZombieNautilus extends AbstractNautilus {
        // Appelle une méthode
        public static final Entry<RegistryKey<ZombieNautilusVariant>> VARIANT = index(0, Metadata::ZombieNautilusVariant, ZombieNautilusVariant.TEMPERATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class AbstractVillager extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Integer> HEAD_SHAKE_TIMER = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Villager extends AbstractVillager {
        // Affecte une valeur
        public static final Entry<VillagerMeta.VillagerData> VARIANT = index(0, Metadata::VillagerData,
                                                                             // Instruction de code
                                                                             VillagerMeta.VillagerData.DEFAULT);
        // Appelle une méthode
        public static final Entry<Boolean> IS_FINALIZED = index(1, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class HappyGhast extends AgeableMob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_LEASH_HOLDER = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> STAYS_STILL = index(1, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class IronGolem extends Mob {
        // Appelle une méthode
        public static final Entry<Byte> IRON_GOLEM_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_PLAYER_CREATED = bitMask(0, (byte) 0x01, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class SnowGolem extends Mob {
        // Appelle une méthode
        public static final Entry<Byte> SNOW_GOLEM_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> PUMPKIN_HAT = bitMask(0, (byte) 0x10, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Shulker extends Mob {
        // Appelle une méthode
        public static final Entry<Direction> ATTACH_FACE = index(0, Metadata::Direction, Direction.DOWN);
        // Appelle une méthode
        public static final Entry<Byte> SHIELD_HEIGHT = index(1, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Byte> COLOR = index(2, Metadata::Byte, (byte) 16);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class CopperGolem extends Mob {
        // Affecte une valeur
        public static final Entry<CopperGolemMeta.WeatherState> WEATHER_STATE = index(0, Metadata::WeatherState,
                                                                                      // Instruction de code
                                                                                      CopperGolemMeta.WeatherState.UNAFFECTED);
        // Affecte une valeur
        public static final Entry<CopperGolemMeta.State> STATE = index(1, Metadata::CopperGolemState,
                                                                       // Instruction de code
                                                                       CopperGolemMeta.State.IDLE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class BasePiglin extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> IMMUNE_ZOMBIFICATION = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Piglin extends BasePiglin {
        // Appelle une méthode
        public static final Entry<Boolean> IS_BABY = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_CHARGING_CROSSBOW = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_DANCING = index(2, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Blaze extends Mob {
        // Appelle une méthode
        public static final Entry<Byte> BLAZE_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_ON_FIRE = bitMask(0, (byte) 0x01, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Bogged extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_SHEARED = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Creaking extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> CAN_MOVE = index(0, Metadata::Boolean, true);
        // Appelle une méthode
        public static final Entry<Boolean> IS_ACTIVE = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_TEARING_DOWN = index(2, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<@Nullable Point> HOME_POS = index(3, Metadata::OptBlockPosition, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Creeper extends Mob {
        // Appelle une méthode
        public static final Entry<Integer> STATE = index(0, Metadata::VarInt, -1);
        // Appelle une méthode
        public static final Entry<Boolean> IS_CHARGED = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_IGNITED = index(2, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Guardian extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_RETRACTING_SPIKES = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Integer> TARGET_EID = index(1, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class Raider extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_CELEBRATING = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Pillager extends Raider {
        // Appelle une méthode
        public static final Entry<Boolean> IS_CHARGING = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class SpellcasterIllager extends Raider {
        // Appelle une méthode
        public static final Entry<Byte> SPELL = index(0, Metadata::Byte, (byte) 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Witch extends Raider {
        // Appelle une méthode
        public static final Entry<Boolean> IS_DRINKING_POTION = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Spider extends Mob {
        // Appelle une méthode
        public static final Entry<Byte> SPIDER_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_CLIMBING = bitMask(0, (byte) 0x01, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Vex extends Mob {
        // Appelle une méthode
        public static final Entry<Byte> VEX_FLAGS = index(0, Metadata::Byte, (byte) 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_ATTACKING = bitMask(0, (byte) 0x01, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Warden extends Mob {
        // Appelle une méthode
        public static final Entry<Integer> ANGER_LEVEL = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Wither extends Mob {
        // Appelle une méthode
        public static final Entry<Integer> CENTER_HEAD_TARGET = index(0, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> LEFT_HEAD_TARGET = index(1, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> RIGHT_HEAD_TARGET = index(2, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Integer> INVULNERABLE_TIME = index(3, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Zoglin extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_BABY = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static sealed class Zombie extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_BABY = index(0, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Integer> SPECIAL_TYPE = index(1, Metadata::VarInt, 0);
        // Appelle une méthode
        public static final Entry<Boolean> IS_BECOMING_DROWNED = index(2, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class ZombieVillager extends Zombie {
        // Appelle une méthode
        public static final Entry<Boolean> IS_CONVERTING = index(0, Metadata::Boolean, false);
        // Affecte une valeur
        public static final Entry<VillagerMeta.VillagerData> VILLAGER_DATA = index(1, Metadata::VillagerData,
                                                                                   // Instruction de code
                                                                                   VillagerMeta.VillagerData.DEFAULT);
        // Appelle une méthode
        public static final Entry<Boolean> IS_FINALIZED = index(2, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Enderman extends Mob {
        // Appelle une méthode
        public static final Entry<@Nullable Block> CARRIED_BLOCK = index(0, Metadata::OptBlockState, null);
        // Appelle une méthode
        public static final Entry<Boolean> IS_SCREAMING = index(1, Metadata::Boolean, false);
        // Appelle une méthode
        public static final Entry<Boolean> IS_STARING = index(2, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class EnderDragon extends Mob {
        // Appelle une méthode
        public static final Entry<Integer> DRAGON_PHASE = index(0, Metadata::VarInt, 10);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Ghast extends Mob {
        // Appelle une méthode
        public static final Entry<Boolean> IS_ATTACKING = index(0, Metadata::Boolean, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Phantom extends Mob {
        // Appelle une méthode
        public static final Entry<Integer> SIZE = index(0, Metadata::VarInt, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class Slime extends Mob {
        // Appelle une méthode
        public static final Entry<Integer> SIZE = index(0, Metadata::VarInt, 1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class PrimedTnt extends MetadataDef {
        // Appelle une méthode
        public static final Entry<Integer> FUSE_TIME = index(0, Metadata::VarInt, 80);
        // Appelle une méthode
        public static final Entry<Block> BLOCK_STATE = index(1, Metadata::BlockState, Block.TNT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class OminousItemSpawner extends MetadataDef {
        // Appelle une méthode
        public static final Entry<ItemStack> ITEM = index(0, Metadata::ItemStack, ItemStack.AIR);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Get the number of metadata entries for a specific class.
     * <p>
     * Useful if you want to pre-allocate the metadata array.
     */
    // Début d'une méthode/d'un bloc
    public static <T extends MetadataDef> int count(Class<T> clazz) {
        // Renvoie une valeur à l'appelant
        return MetadataDefImpl.count(clazz);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public sealed interface Entry<T extends @UnknownNullability Object> {
        // Appelle une méthode
        int index();

        // Appelle une méthode
        T defaultValue();

        // Déclaration de type (classe/interface/enum/record)
        record Index<T extends @UnknownNullability Object>(int index, Function<T, Metadata.Entry<T>> function, T defaultValue) implements Entry<T> {
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record BitMask(int index, byte bitMask, Boolean defaultValue) implements Entry<Boolean> {
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record ByteMask(int index, byte byteMask, int offset, Byte defaultValue) implements Entry<Byte> {
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
