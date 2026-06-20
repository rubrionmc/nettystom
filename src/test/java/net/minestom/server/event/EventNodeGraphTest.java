// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class EventNodeGraphTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void single() {
        // Appelle une méthode
        EventNode<Event> node = EventNode.all("main");
        // Appelle une méthode
        verifyGraph(node, new EventNodeImpl.Graph("main", "Event", 0, List.of()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleChild() {
        // Appelle une méthode
        EventNode<Event> node = EventNode.all("main");
        // Appelle une méthode
        node.addChild(EventNode.all("child"));
        // Instruction de code
        verifyGraph(node, new EventNodeImpl.Graph("main", "Event", 0,
                // Instruction de code
                List.of(new EventNodeImpl.Graph("child", "Event", 0, List.of())
                // Instruction de code
                )));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void childrenPriority() {
        // Début d'un bloc
        {
            // Appelle une méthode
            EventNode<Event> node = EventNode.all("main");
            // Appelle une méthode
            node.addChild(EventNode.all("child1").setPriority(5));
            // Appelle une méthode
            node.addChild(EventNode.all("child2").setPriority(10));
            // Instruction de code
            verifyGraph(node, new EventNodeImpl.Graph("main", "Event", 0,
                    // Instruction de code
                    List.of(new EventNodeImpl.Graph("child1", "Event", 5, List.of()),
                            // Crée un nouvel objet
                            new EventNodeImpl.Graph("child2", "Event", 10, List.of())
                    // Instruction de code
                    )));
        // Fin d'un bloc/d'une expression
        }
        // Début d'un bloc
        {
            // Appelle une méthode
            EventNode<Event> node = EventNode.all("main");
            // Appelle une méthode
            node.addChild(EventNode.all("child2").setPriority(10));
            // Appelle une méthode
            node.addChild(EventNode.all("child1").setPriority(5));
            // Instruction de code
            verifyGraph(node, new EventNodeImpl.Graph("main", "Event", 0,
                    // Instruction de code
                    List.of(new EventNodeImpl.Graph("child1", "Event", 5, List.of()),
                            // Crée un nouvel objet
                            new EventNodeImpl.Graph("child2", "Event", 10, List.of())
                    // Instruction de code
                    )));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void verifyGraph(EventNode<?> n, EventNodeImpl.Graph graph) {
        // Affecte une valeur
        EventNodeImpl<?> node = (EventNodeImpl<?>) n;
        // Appelle une méthode
        var nodeGraph = node.createGraph();
        // Appelle une méthode
        assertEquals(graph, nodeGraph, "Graphs are not equals");
        // Appelle une méthode
        assertEquals(EventNodeImpl.createStringGraph(graph), EventNodeImpl.createStringGraph(nodeGraph), "String graphs are not equals");
        // Appelle une méthode
        assertEquals(n.toString(), EventNodeImpl.createStringGraph(nodeGraph), "The node does not use createStringGraph");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
