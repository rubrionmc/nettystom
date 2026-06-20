// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.*;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentParticle;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentDouble;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentFloat;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentLong;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec2;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec3;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.parser.ArgumentParser;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

/**
 * Convenient class listing all the basics {@link Argument}.
 * <p>
 * Please see the specific class documentation for further info.
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentType {

    /**
     * @see ArgumentLiteral
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentLiteral Literal(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentLiteral(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentGroup
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentGroup Group(String id, Argument<?>... arguments) {
        // Renvoie une valeur à l'appelant
        return new ArgumentGroup(id, arguments);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentLoop
     */
    // Annotation pour l'élément suivant
    @SafeVarargs
    // Début d'une méthode/d'un bloc
    public static <T> ArgumentLoop<T> Loop(String id, Argument<T>... arguments) {
        // Renvoie une valeur à l'appelant
        return new ArgumentLoop<>(id, arguments);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentBoolean
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentBoolean Boolean(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentBoolean(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentInteger
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentInteger Integer(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentInteger(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentDouble
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentDouble Double(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentDouble(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentFloat
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentFloat Float(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentFloat(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentString
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentString String(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentString(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentWord
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentWord Word(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentWord(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentStringArray
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentStringArray StringArray(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentStringArray(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentCommand
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentCommand Command(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentCommand(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentEnum
     */
    // Annotation pour l'élément suivant
    @SuppressWarnings("rawtypes")
    // Début d'une méthode/d'un bloc
    public static <E extends Enum> ArgumentEnum<E> Enum(String id, Class<E> enumClass) {
        // Renvoie une valeur à l'appelant
        return new ArgumentEnum<>(id, enumClass);
    // Fin d'un bloc/d'une expression
    }

    // Minecraft specific arguments

    /**
     * @see ArgumentColor
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentColor Color(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentColor(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentTime
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentTime Time(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentTime(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentParticle
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentParticle Particle(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentParticle(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentResource
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentResource Resource(String id, String identifier) {
        // Renvoie une valeur à l'appelant
        return new ArgumentResource(id, identifier);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentResourceLocation
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentResourceLocation ResourceLocation(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentResourceLocation(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentResourceOrTag
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentResourceOrTag ResourceOrTag(String id, String identifier) {
        // Renvoie une valeur à l'appelant
        return new ArgumentResourceOrTag(id, identifier);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentEntityType
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentEntityType EntityType(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentEntityType(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentBlockState
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentBlockState BlockState(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentBlockState(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentIntRange
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentIntRange IntRange(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentIntRange(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentFloatRange
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentFloatRange FloatRange(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentFloatRange(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentEntity
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentEntity Entity(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentEntity(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentItemStack
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentItemStack ItemStack(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentItemStack(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentComponent
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentComponent Component(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentComponent(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentUUID
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentUUID UUID(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentUUID(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentNbtTag
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentNbtTag NBT(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentNbtTag(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentNbtCompoundTag
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentNbtCompoundTag NbtCompound(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentNbtCompoundTag(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentRelativeBlockPosition
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentRelativeBlockPosition RelativeBlockPosition(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentRelativeBlockPosition(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentRelativeVec3
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentRelativeVec3 RelativeVec3(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentRelativeVec3(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentRelativeVec2
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentRelativeVec2 RelativeVec2(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentRelativeVec2(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Generates arguments from a string format.
     * <p>
     * Example: "Entity&lt;targets&gt; Integer&lt;number&gt;"
     * <p>
     * Note: this feature is in beta and is very likely to change depending on feedback.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public static Argument<?>[] generate(String format) {
        // Renvoie une valeur à l'appelant
        return ArgumentParser.generate(format);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentLong
     */
    // Début d'une méthode/d'un bloc
    public static ArgumentLong Long(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentLong(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see ArgumentEntity
     * @deprecated use {@link #Entity(String)}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public static ArgumentEntity Entities(String id) {
        // Renvoie une valeur à l'appelant
        return new ArgumentEntity(id);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
