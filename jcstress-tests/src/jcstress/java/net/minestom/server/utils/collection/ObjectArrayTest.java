// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import org.openjdk.jcstress.annotations.*;
// Import of a required class
import org.openjdk.jcstress.infra.results.LL_Result;

// Static import of a member
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation for the following element
@JCStressTest
// Annotation for the following element
@Outcome(id = "1, 2", expect = ACCEPTABLE)
// Annotation for the following element
@State
// Type declaration (class/interface/enum/record)
public class ObjectArrayTest {
    // Calls a method
    private final ObjectArray<Integer> array = ObjectArray.concurrent();

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor1() {
        // Calls a method
        array.set(255, 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Actor
    // Start of a method/block
    public void actor2() {
        // Calls a method
        array.set(32_000, 2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Arbiter
    // Start of a method/block
    public void arbiter(LL_Result r) {
        // Calls a method
        r.r1 = array.get(255);
        // Calls a method
        r.r2 = array.get(32_000);
    // End of a block/expression
    }
// End of a block/expression
}
