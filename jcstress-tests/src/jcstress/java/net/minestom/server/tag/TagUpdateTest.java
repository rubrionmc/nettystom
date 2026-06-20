// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.openjdk.jcstress.annotations.*;
// Import of a required class
import org.openjdk.jcstress.infra.results.L_Result;

// Static import of a member
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation for the following element
@JCStressTest
// Annotation for the following element
@Outcome(id = "2000", expect = ACCEPTABLE)
// Annotation for the following element
@State
// Type declaration (class/interface/enum/record)
public class TagUpdateTest {
    // Calls a method
    private static final Tag<Integer> TAG = Tag.Integer("key").defaultValue(0);

    // Calls a method
    private final TagHandler handler = TagHandler.newHandler();

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor1() {
        // Loop: repeats a block
        for (int i = 0; i < 1000; i++) {
            // Calls a method
            handler.updateAndGetTag(TAG, integer -> integer + 1);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor2() {
        // Loop: repeats a block
        for (int i = 0; i < 1000; i++) {
            // Calls a method
            handler.updateAndGetTag(TAG, integer -> integer + 1);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Arbiter
    // Start of a method/block
    public void arbiter(L_Result r) {
        // Calls a method
        r.r1 = handler.getTag(TAG);
    // End of a block/expression
    }
// End of a block/expression
}

