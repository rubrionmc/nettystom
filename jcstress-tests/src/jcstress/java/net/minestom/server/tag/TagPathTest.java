// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import org.openjdk.jcstress.annotations.*;
// Import of a required class
import org.openjdk.jcstress.infra.results.L_Result;

// Import of a required class
import java.util.Map;

// Static import of a member
import static net.kyori.adventure.nbt.IntBinaryTag.intBinaryTag;
// Static import of a member
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation for the following element
@JCStressTest
// Annotation for the following element
@Outcome(id = "tag", expect = ACCEPTABLE)
// Annotation for the following element
@Outcome(id = "tag_path", expect = ACCEPTABLE)
// Annotation for the following element
@State
// Type declaration (class/interface/enum/record)
public class TagPathTest {
    // Calls a method
    private static final Tag<Integer> TAG = Tag.Integer("path");
    // Calls a method
    private static final Tag<Integer> TAG_PATH = Tag.Integer("key").path("path");

    // Calls a method
    private final TagHandler handler = TagHandler.newHandler();

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor1() {
        // Calls a method
        handler.setTag(TAG, 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor2() {
        // Calls a method
        handler.setTag(TAG_PATH, 5);
    // End of a block/expression
    }

    // Annotation for the following element
    @Arbiter
    // Start of a method/block
    public void arbiter(L_Result r) {
        // Calls a method
        var compound = handler.asCompound();
        // Branch: checks a condition
        if (compound.equals(CompoundBinaryTag.from(Map.of("path", intBinaryTag(1))))) {
            // Assigns a value
            r.r1 = "tag";
        // Branch: checks a condition
        } else if (compound.equals(CompoundBinaryTag.from(Map.of("path", CompoundBinaryTag.from(Map.of("key", intBinaryTag(5))))))) {
            // Assigns a value
            r.r1 = "tag_path";
        // Alternative branch of the condition
        } else {
            // Assigns a value
            r.r1 = compound;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
