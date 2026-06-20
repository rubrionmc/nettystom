// Package declaration for this file
package net.minestom.server.command.builder.arguments;

// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.*;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentParticle;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentDouble;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentFloat;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentLong;
// Import of a required class
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition;
// Import of a required class
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec2;
// Import of a required class
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec3;
// Import of a required class
import net.minestom.server.command.builder.parser.ArgumentParser;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

/**
 * Convenient class listing all the basics {@link Argument}.
 * <p>
 * Please see the specific class documentation for further info.
 */
// Type declaration (class/interface/enum/record)
public class ArgumentType {

    /**
     * @see ArgumentLiteral
     */
    // Start of a method/block
    public static ArgumentLiteral Literal(String id) {
        // Returns a value to the caller
        return new ArgumentLiteral(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentGroup
     */
    // Start of a method/block
    public static ArgumentGroup Group(String id, Argument<?>... arguments) {
        // Returns a value to the caller
        return new ArgumentGroup(id, arguments);
    // End of a block/expression
    }

    /**
     * @see ArgumentLoop
     */
    // Annotation for the following element
    @SafeVarargs
    // Start of a method/block
    public static <T> ArgumentLoop<T> Loop(String id, Argument<T>... arguments) {
        // Returns a value to the caller
        return new ArgumentLoop<>(id, arguments);
    // End of a block/expression
    }

    /**
     * @see ArgumentBoolean
     */
    // Start of a method/block
    public static ArgumentBoolean Boolean(String id) {
        // Returns a value to the caller
        return new ArgumentBoolean(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentInteger
     */
    // Start of a method/block
    public static ArgumentInteger Integer(String id) {
        // Returns a value to the caller
        return new ArgumentInteger(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentDouble
     */
    // Start of a method/block
    public static ArgumentDouble Double(String id) {
        // Returns a value to the caller
        return new ArgumentDouble(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentFloat
     */
    // Start of a method/block
    public static ArgumentFloat Float(String id) {
        // Returns a value to the caller
        return new ArgumentFloat(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentString
     */
    // Start of a method/block
    public static ArgumentString String(String id) {
        // Returns a value to the caller
        return new ArgumentString(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentWord
     */
    // Start of a method/block
    public static ArgumentWord Word(String id) {
        // Returns a value to the caller
        return new ArgumentWord(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentStringArray
     */
    // Start of a method/block
    public static ArgumentStringArray StringArray(String id) {
        // Returns a value to the caller
        return new ArgumentStringArray(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentCommand
     */
    // Start of a method/block
    public static ArgumentCommand Command(String id) {
        // Returns a value to the caller
        return new ArgumentCommand(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentEnum
     */
    // Annotation for the following element
    @SuppressWarnings("rawtypes")
    // Start of a method/block
    public static <E extends Enum> ArgumentEnum<E> Enum(String id, Class<E> enumClass) {
        // Returns a value to the caller
        return new ArgumentEnum<>(id, enumClass);
    // End of a block/expression
    }

    // Minecraft specific arguments

    /**
     * @see ArgumentColor
     */
    // Start of a method/block
    public static ArgumentColor Color(String id) {
        // Returns a value to the caller
        return new ArgumentColor(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentTime
     */
    // Start of a method/block
    public static ArgumentTime Time(String id) {
        // Returns a value to the caller
        return new ArgumentTime(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentParticle
     */
    // Start of a method/block
    public static ArgumentParticle Particle(String id) {
        // Returns a value to the caller
        return new ArgumentParticle(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentResource
     */
    // Start of a method/block
    public static ArgumentResource Resource(String id, String identifier) {
        // Returns a value to the caller
        return new ArgumentResource(id, identifier);
    // End of a block/expression
    }

    /**
     * @see ArgumentResourceLocation
     */
    // Start of a method/block
    public static ArgumentResourceLocation ResourceLocation(String id) {
        // Returns a value to the caller
        return new ArgumentResourceLocation(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentResourceOrTag
     */
    // Start of a method/block
    public static ArgumentResourceOrTag ResourceOrTag(String id, String identifier) {
        // Returns a value to the caller
        return new ArgumentResourceOrTag(id, identifier);
    // End of a block/expression
    }

    /**
     * @see ArgumentEntityType
     */
    // Start of a method/block
    public static ArgumentEntityType EntityType(String id) {
        // Returns a value to the caller
        return new ArgumentEntityType(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentBlockState
     */
    // Start of a method/block
    public static ArgumentBlockState BlockState(String id) {
        // Returns a value to the caller
        return new ArgumentBlockState(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentIntRange
     */
    // Start of a method/block
    public static ArgumentIntRange IntRange(String id) {
        // Returns a value to the caller
        return new ArgumentIntRange(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentFloatRange
     */
    // Start of a method/block
    public static ArgumentFloatRange FloatRange(String id) {
        // Returns a value to the caller
        return new ArgumentFloatRange(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentEntity
     */
    // Start of a method/block
    public static ArgumentEntity Entity(String id) {
        // Returns a value to the caller
        return new ArgumentEntity(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentItemStack
     */
    // Start of a method/block
    public static ArgumentItemStack ItemStack(String id) {
        // Returns a value to the caller
        return new ArgumentItemStack(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentComponent
     */
    // Start of a method/block
    public static ArgumentComponent Component(String id) {
        // Returns a value to the caller
        return new ArgumentComponent(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentUUID
     */
    // Start of a method/block
    public static ArgumentUUID UUID(String id) {
        // Returns a value to the caller
        return new ArgumentUUID(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentNbtTag
     */
    // Start of a method/block
    public static ArgumentNbtTag NBT(String id) {
        // Returns a value to the caller
        return new ArgumentNbtTag(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentNbtCompoundTag
     */
    // Start of a method/block
    public static ArgumentNbtCompoundTag NbtCompound(String id) {
        // Returns a value to the caller
        return new ArgumentNbtCompoundTag(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentRelativeBlockPosition
     */
    // Start of a method/block
    public static ArgumentRelativeBlockPosition RelativeBlockPosition(String id) {
        // Returns a value to the caller
        return new ArgumentRelativeBlockPosition(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentRelativeVec3
     */
    // Start of a method/block
    public static ArgumentRelativeVec3 RelativeVec3(String id) {
        // Returns a value to the caller
        return new ArgumentRelativeVec3(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentRelativeVec2
     */
    // Start of a method/block
    public static ArgumentRelativeVec2 RelativeVec2(String id) {
        // Returns a value to the caller
        return new ArgumentRelativeVec2(id);
    // End of a block/expression
    }

    /**
     * Generates arguments from a string format.
     * <p>
     * Example: "Entity&lt;targets&gt; Integer&lt;number&gt;"
     * <p>
     * Note: this feature is in beta and is very likely to change depending on feedback.
     */
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public static Argument<?>[] generate(String format) {
        // Returns a value to the caller
        return ArgumentParser.generate(format);
    // End of a block/expression
    }

    /**
     * @see ArgumentLong
     */
    // Start of a method/block
    public static ArgumentLong Long(String id) {
        // Returns a value to the caller
        return new ArgumentLong(id);
    // End of a block/expression
    }

    /**
     * @see ArgumentEntity
     * @deprecated use {@link #Entity(String)}
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public static ArgumentEntity Entities(String id) {
        // Returns a value to the caller
        return new ArgumentEntity(id);
    // End of a block/expression
    }
// End of a block/expression
}
