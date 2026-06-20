// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.ValueSource;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ObjectArrayTest {

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void objectArray(boolean concurrent) {
        // Calls a method
        ObjectArray<String> array = concurrent ? ObjectArray.concurrent() : ObjectArray.singleThread();

        // Calls a method
        array.set(50, "Hey");
        // Calls a method
        assertEquals("Hey", array.get(50));
        // Calls a method
        assertNull(array.get(49));
        // Calls a method
        assertNull(array.get(51));

        // Calls a method
        array.set(0, "Hey2");
        // Calls a method
        assertEquals("Hey2", array.get(0));
        // Calls a method
        assertEquals("Hey", array.get(50));

        // Calls a method
        array.trim();
        // Calls a method
        assertEquals("Hey2", array.get(0));
        // Calls a method
        assertEquals("Hey", array.get(50));

        // Calls a method
        array.set(250, "Hey3");
        // Calls a method
        assertEquals("Hey3", array.get(250));
        // Calls a method
        assertEquals("Hey2", array.get(0));
        // Calls a method
        assertEquals("Hey", array.get(50));

        // Calls a method
        assertNull(array.get(49));
        // Calls a method
        assertNull(array.get(251));
        // Calls a method
        assertNull(array.get(Integer.MAX_VALUE));
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void arrayCopy(boolean concurrent) {
        // Calls a method
        ObjectArray<String> array = concurrent ? ObjectArray.concurrent() : ObjectArray.singleThread();

        // Calls a method
        array.set(1, "Hey");
        // Calls a method
        String[] copyCache = array.arrayCopy(String.class);
        // Calls a method
        assertArrayEquals(new String[]{null, "Hey"}, copyCache);

        // Calls a method
        array.set(2, "Hey2");
        // Calls a method
        assertArrayEquals(new String[]{null, "Hey", "Hey2"}, array.arrayCopy(String.class));
        // Calls a method
        assertArrayEquals(new String[]{null, "Hey"}, copyCache, "The copy cache should not be modified");
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @ValueSource(booleans = {false, true})
    // Start of a method/block
    public void trim(boolean concurrent) {
        // Calls a method
        ObjectArray<String> array = concurrent ? ObjectArray.concurrent() : ObjectArray.singleThread();
        // zero case
        // Calls a method
        array.trim();
        // Calls a method
        assertArrayEquals(new String[0], array.arrayCopy(String.class));

        // 3 elements with a space
        // Calls a method
        array.set(0, "Hey");
        // Calls a method
        array.set(1, "Hey2");
        // Calls a method
        array.set(3, "Hey4");
        // Calls a method
        array.trim();
        // Calls a method
        assertArrayEquals(new String[]{"Hey", "Hey2", null, "Hey4"}, array.arrayCopy(String.class));

        // 4 elements without a space
        // Calls a method
        array.set(2, "Hey3");
        // Calls a method
        array.trim();
        // Calls a method
        assertArrayEquals(new String[]{"Hey", "Hey2", "Hey3", "Hey4"}, array.arrayCopy(String.class));

        // set trailing 2 elements with a null
        // Calls a method
        array.remove(2);
        // Calls a method
        array.remove(3);
        // Calls a method
        array.trim();
        // Calls a method
        assertArrayEquals(new String[]{"Hey", "Hey2"}, array.arrayCopy(String.class));

        // remove first element
        // Calls a method
        array.remove(0);
        // Calls a method
        array.trim();
        // Calls a method
        assertArrayEquals(new String[]{null, "Hey2"}, array.arrayCopy(String.class));

        // remove last element, forcing the array to shrink after trim
        // Calls a method
        array.remove(1);
        // Calls a method
        array.trim();
        // Calls a method
        assertArrayEquals(new String[0], array.arrayCopy(String.class));
    // End of a block/expression
    }
// End of a block/expression
}
