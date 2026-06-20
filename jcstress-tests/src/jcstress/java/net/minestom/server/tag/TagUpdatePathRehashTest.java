// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import org.openjdk.jcstress.annotations.*;
// Import of a required class
import org.openjdk.jcstress.infra.results.L_Result;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Static import of a member
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation for the following element
@JCStressTest
// Annotation for the following element
@Outcome(id = "2000", expect = ACCEPTABLE)
// Annotation for the following element
@State
// Type declaration (class/interface/enum/record)
public class TagUpdatePathRehashTest {
    // Calls a method
    private static final Tag<Integer> TAG = Tag.Integer("key").path("path").defaultValue(0);

    // Assigns a value
    private static final int MAX_SIZE = 500;
    // Code statement
    private static final List<Tag<Integer>> TAGS;

    // Start of a method/block
    static {
        // Calls a method
        List<Tag<Integer>> tags = new ArrayList<>();
        // Loop: repeats a block
        for (int i = 0; i < MAX_SIZE; i++) {
            // Calls a method
            tags.add(Tag.Integer("key" + i).path("path"));
        // End of a block/expression
        }
        // Calls a method
        TAGS = List.copyOf(tags);
    // End of a block/expression
    }

    // Calls a method
    private final TagHandler handler = TagHandler.newHandler();

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor1() {
        // Loop: repeats a block
        for (int i = 0; i < 1000; i++) {
            // Calls a method
            handler.updateTag(TAG, integer -> integer + 1);
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
            handler.updateTag(TAG, integer -> integer + 1);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor3() {
        // May be able to disturb actor1/2
        // Loop: repeats a block
        for (int i = 0; i < MAX_SIZE; i++) {
            // Calls a method
            handler.setTag(TAGS.get(i), i);
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

