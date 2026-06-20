// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class EventNodeGraphTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void single() {
        // Calls a method
        EventNode<Event> node = EventNode.all("main");
        // Calls a method
        verifyGraph(node, new EventNodeImpl.Graph("main", "Event", 0, List.of()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleChild() {
        // Calls a method
        EventNode<Event> node = EventNode.all("main");
        // Calls a method
        node.addChild(EventNode.all("child"));
        // Code statement
        verifyGraph(node, new EventNodeImpl.Graph("main", "Event", 0,
                // Code statement
                List.of(new EventNodeImpl.Graph("child", "Event", 0, List.of())
                // Code statement
                )));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void childrenPriority() {
        // Start of a block
        {
            // Calls a method
            EventNode<Event> node = EventNode.all("main");
            // Calls a method
            node.addChild(EventNode.all("child1").setPriority(5));
            // Calls a method
            node.addChild(EventNode.all("child2").setPriority(10));
            // Code statement
            verifyGraph(node, new EventNodeImpl.Graph("main", "Event", 0,
                    // Code statement
                    List.of(new EventNodeImpl.Graph("child1", "Event", 5, List.of()),
                            // Creates a new object
                            new EventNodeImpl.Graph("child2", "Event", 10, List.of())
                    // Code statement
                    )));
        // End of a block/expression
        }
        // Start of a block
        {
            // Calls a method
            EventNode<Event> node = EventNode.all("main");
            // Calls a method
            node.addChild(EventNode.all("child2").setPriority(10));
            // Calls a method
            node.addChild(EventNode.all("child1").setPriority(5));
            // Code statement
            verifyGraph(node, new EventNodeImpl.Graph("main", "Event", 0,
                    // Code statement
                    List.of(new EventNodeImpl.Graph("child1", "Event", 5, List.of()),
                            // Creates a new object
                            new EventNodeImpl.Graph("child2", "Event", 10, List.of())
                    // Code statement
                    )));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    void verifyGraph(EventNode<?> n, EventNodeImpl.Graph graph) {
        // Calls a method
        EventNodeImpl<?> node = (EventNodeImpl<?>) n;
        // Calls a method
        var nodeGraph = node.createGraph();
        // Calls a method
        assertEquals(graph, nodeGraph, "Graphs are not equals");
        // Calls a method
        assertEquals(EventNodeImpl.createStringGraph(graph), EventNodeImpl.createStringGraph(nodeGraph), "String graphs are not equals");
        // Calls a method
        assertEquals(n.toString(), EventNodeImpl.createStringGraph(nodeGraph), "The node does not use createStringGraph");
    // End of a block/expression
    }
// End of a block/expression
}
