// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.server.utils.block.BlockUtils.parseProperties;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class BlockPropertiesTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty() {
        // Calls a method
        assertEquals(Map.of(), parseProperties("[]"));
        // Calls a method
        assertEquals(Map.of(), parseProperties(""));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void noBrackets() {
        // Calls a method
        assertEquals(Map.of(), parseProperties("random test without brackets"));
        // Calls a method
        assertEquals(Map.of(), parseProperties("["));
        // Calls a method
        assertEquals(Map.of(), parseProperties("[end"));
        // Calls a method
        assertEquals(Map.of(), parseProperties("[random test without end bracket"));
        // Calls a method
        assertEquals(Map.of(), parseProperties("]"));
        // Calls a method
        assertEquals(Map.of(), parseProperties("start]"));
        // Calls a method
        assertEquals(Map.of(), parseProperties("random test without start bracket]"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void spaces() {
        // Calls a method
        assertEquals(Map.of(), parseProperties("[    ]"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void comma() {
        // Calls a method
        assertEquals(Map.of(), parseProperties("[  , , ,,,,  ]"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void single() {
        // Calls a method
        assertEquals(Map.of("facing", "east"), parseProperties("[facing=east]"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void doubleSpace() {
        // Calls a method
        assertEquals(Map.of("facing", "east", "key", "value"), parseProperties("[facing=east,key=value ]"));
        // Calls a method
        assertEquals(Map.of("facing", "east", "key", "value"), parseProperties("[ facing = east, key= value ]"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void allLengths() {
        // Verify all length variations
        // Loop: repeats a block
        for (int i = 0; i < 13; i++) {
            // Calls a method
            StringBuilder properties = new StringBuilder("[");
            // Loop: repeats a block
            for (int j = 0; j < i; j++) {
                // Calls a method
                properties.append("key").append(j).append("=value").append(j);
                // Branch: checks a condition
                if (j != i - 1) properties.append(",");
            // End of a block/expression
            }
            // Calls a method
            properties.append("]");

            // Calls a method
            var map = parseProperties(properties.toString());
            // Calls a method
            assertEquals(i, map.size());
            // Loop: repeats a block
            for (int j = 0; j < i; j++) {
                // Calls a method
                assertEquals("value" + j, map.get("key" + j));
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void corrupted() {
        // Assigns a value
        final int size = 12;
        // Calls a method
        StringBuilder properties = new StringBuilder("[");
        // Loop: repeats a block
        for (int j = 0; j < size; j++) {
            // Calls a method
            properties.append("key").append(j).append("=value").append(j);
            // Branch: checks a condition
            if (j != size - 1) properties.append(",");
        // End of a block/expression
        }
        // Calls a method
        properties.append(", , ,]");

        // Calls a method
        var map = parseProperties(properties.toString());
        // Calls a method
        assertEquals(size, map.size());
        // Loop: repeats a block
        for (int j = 0; j < size; j++) {
            // Calls a method
            assertEquals("value" + j, map.get("key" + j));
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
