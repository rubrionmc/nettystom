// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import d'une classe nécessaire
import org.opentest4j.AssertionFailedError;

// Import d'une classe nécessaire
import java.math.BigInteger;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.fail;

// Déclaration de type (classe/interface/enum/record)
public class CommandTestUtils {

    // Début d'une méthode/d'un bloc
    public static void assertPacket(DeclareCommandsPacket packet, String expectedStructure) {
        // Appelle une méthode
        final List<NodeStructure.TestNode> expectedList = NodeStructure.fromString("0\n0=$root$\n" + expectedStructure);
        // Appelle une méthode
        final List<NodeStructure.TestNode> actualList = NodeStructure.fromString(NodeStructure.packetToString(packet));
        // Gestion des exceptions
        try {
            // Appelle une méthode
            assertEquals(expectedList.size(), actualList.size(), "Different node counts");
            // Boucle : répète un bloc
            for (NodeStructure.TestNode expected : expectedList) {
                // Affecte une valeur
                boolean found = false;
                // Boucle : répète un bloc
                for (NodeStructure.TestNode actual : actualList) {
                    // Embranchement : vérifie une condition
                    if (expected.equals(actual)) {
                        // Affecte une valeur
                        found = true;
                        // Interrompt la boucle/le bloc
                        break;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if (!found) {
                    // Appelle une méthode
                    fail("Packet doesn't contain " + expected.toString());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Début d'une méthode/d'un bloc
        } catch (AssertionFailedError error) {
            // Appelle une méthode
            fail("Graphs didn't match. Actual graph from packet: " + CommandTestUtils.exportGarphvizDot(packet, false), error);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static class NodeStructure {
        // Affecte une valeur
        private static final Map<Character, Function<String, Collection<String>>> functions = Map.of(
                // Début d'une méthode/d'un bloc
                '!', s -> {
                    // Appelle une méthode
                    final String[] strings = splitDeclaration(s);
                    // Appelle une méthode
                    final ArrayList<String> result = new ArrayList<>();
                    // Boucle : répète un bloc
                    for (String s1 : strings[0].split(" ")) {
                        // Appelle une méthode
                        result.add(s1 + "=" + (strings[1].replaceAll("!", s1)));
                    // Fin d'un bloc/d'une expression
                    }
                    // Renvoie une valeur à l'appelant
                    return result;
                // Instruction de code
                },
                // Début d'une méthode/d'un bloc
                '%', s -> {
                    // Appelle une méthode
                    final String[] strings = splitDeclaration(s);
                    // Appelle une méthode
                    final ArrayList<String> result = new ArrayList<>();
                    // Boucle : répète un bloc
                    for (String s1 : strings[0].split(" ")) {
                        // Appelle une méthode
                        result.add(s1 + "=" + (strings[1].replaceAll("%", "'" + s1 + "'")));
                    // Fin d'un bloc/d'une expression
                    }
                    // Renvoie une valeur à l'appelant
                    return result;
                // Instruction de code
                },
                // Début d'une méthode/d'un bloc
                '§', s -> {
                    // Appelle une méthode
                    final String[] strings = splitDeclaration(s);
                    // Appelle une méthode
                    final ArrayList<String> result = new ArrayList<>();
                    // Boucle : répète un bloc
                    for (String s1 : strings[0].split(" ")) {
                        // Appelle une méthode
                        result.add(s1 + "=" + (strings[1].replaceAll("§", "'" + (s1.toUpperCase(Locale.ROOT)) + "'")));
                    // Fin d'un bloc/d'une expression
                    }
                    // Renvoie une valeur à l'appelant
                    return result;
                // Fin d'un bloc/d'une expression
                }
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        private static final Set<Character> placeholders = functions.keySet();

        // Début d'une méthode/d'un bloc
        static String packetToString(DeclareCommandsPacket packet) {
            // Affecte une valeur
            final char lineSeparator = '\n';
            // Appelle une méthode
            final StringBuilder builder = new StringBuilder();
            // Appelle une méthode
            builder.append(packet.rootIndex());
            // Appelle une méthode
            builder.append(lineSeparator);
            // Appelle une méthode
            List<DeclareCommandsPacket.Node> nodes = packet.nodes();
            // Boucle : répète un bloc
            for (int i = 0; i < nodes.size(); i++) {
                // Appelle une méthode
                DeclareCommandsPacket.Node node = nodes.get(i);
                // Appelle une méthode
                builder.append(i);
                // Appelle une méthode
                builder.append('=');
                // Meta
                // Embranchement : vérifie une condition
                if ((node.flags & 0x3) == 0) {
                    // Appelle une méthode
                    builder.append("$root$");
                // Branche alternative de la condition
                } else {
                    // Embranchement : vérifie une condition
                    if ((node.flags & 0x3) == 1) {
                        // Appelle une méthode
                        builder.append("'");
                        // Appelle une méthode
                        builder.append(node.name);
                        // Appelle une méthode
                        builder.append("'");
                    // Branche alternative de la condition
                    } else {
                        // Appelle une méthode
                        builder.append(node.name);
                        // Appelle une méthode
                        builder.append(' ');
                        // Appelle une méthode
                        builder.append(node.parser);

                        // Embranchement : vérifie une condition
                        if (node.properties != null) {
                            // Appelle une méthode
                            builder.append(' ');
                            // Appelle une méthode
                            builder.append(new BigInteger(node.properties).toString(16));
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if ((node.flags & 0x4) == 0x4) {
                    // Appelle une méthode
                    builder.append(" executable");
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if ((node.flags & 0x10) == 0x10) {
                    // Appelle une méthode
                    builder.append(' ');
                    // Appelle une méthode
                    builder.append(node.suggestionsType);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                builder.append(lineSeparator);
                // Embranchement : vérifie une condition
                if (node.children.length > 0) {
                    // Appelle une méthode
                    builder.append(i);
                    // Appelle une méthode
                    builder.append("->");
                    // Appelle une méthode
                    builder.append(Arrays.stream(node.children).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
                    // Appelle une méthode
                    builder.append(lineSeparator);
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if ((node.flags & 0x8) == 0x8) {
                    // Appelle une méthode
                    builder.append(i);
                    // Appelle une méthode
                    builder.append("+>");
                    // Appelle une méthode
                    builder.append(node.redirectedNode);
                    // Appelle une méthode
                    builder.append(lineSeparator);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return builder.toString();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static String[] splitDeclaration(String input) {
            // Renvoie une valeur à l'appelant
            return input.split("=", 2);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static List<String> preProcessString(String string) {
            // Appelle une méthode
            final List<String> strings = Arrays.stream(string.split("\n")).toList();
            // Appelle une méthode
            final ArrayList<String> result = new ArrayList<>();
            // Boucle : répète un bloc
            for (String s : strings) {
                // Embranchement : vérifie une condition
                if (s.indexOf('=') > -1) {
                    // Affecte une valeur
                    boolean match = false;
                    // Boucle : répète un bloc
                    for (Character placeholder : placeholders) {
                        // Embranchement : vérifie une condition
                        if (s.indexOf(placeholder) > -1) {
                            // Appelle une méthode
                            result.addAll(functions.get(placeholder).apply(s));
                            // Affecte une valeur
                            match = true;
                            // Interrompt la boucle/le bloc
                            break;
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                    // Embranchement : vérifie une condition
                    if (!match) {
                        // Appelle une méthode
                        final int spaceIndex = s.indexOf(" ");
                        // Embranchement : vérifie une condition
                        if (spaceIndex > -1 && spaceIndex < s.indexOf('=')) {
                            // Appelle une méthode
                            final String[] split = s.split("=", 2);
                            // Boucle : répète un bloc
                            for (String s1 : split[0].split(" ")) {
                                // Appelle une méthode
                                result.add(s1 + "=" + split[1]);
                            // Fin d'un bloc/d'une expression
                            }
                        // Branche alternative de la condition
                        } else {
                            // Appelle une méthode
                            result.add(s);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    final int spaceIndex = s.indexOf(" ");
                    // Embranchement : vérifie une condition
                    if (spaceIndex > -1 && spaceIndex < s.indexOf('-')) {
                        // Appelle une méthode
                        final String[] split = s.split("-", 2);
                        // Boucle : répète un bloc
                        for (String s1 : split[0].split(" ")) {
                            // Appelle une méthode
                            result.add(s1 + "-" + split[1]);
                        // Fin d'un bloc/d'une expression
                        }
                    // Embranchement : vérifie une condition
                    } else if (spaceIndex > -1 && spaceIndex < s.indexOf('+')) {
                        // Appelle une méthode
                        final String[] split = s.split("\\+", 2);
                        // Boucle : répète un bloc
                        for (String s1 : split[0].split(" ")) {
                            // Appelle une méthode
                            result.add(s1 + "+" + split[1]);
                        // Fin d'un bloc/d'une expression
                        }
                    // Branche alternative de la condition
                    } else {
                        // Appelle une méthode
                        result.add(s);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        static List<TestNode> fromString(String input) {
            // Appelle une méthode
            Map<String, String[]> references = new HashMap<>();
            // Appelle une méthode
            Map<String, TestNode> nodes = new HashMap<>();
            // Appelle une méthode
            final List<String> strings = preProcessString(input);
            // Appelle une méthode
            final String rootId = strings.getFirst();

            // Boucle : répète un bloc
            for (String s : strings.stream().skip(0).toList()) {
                // Embranchement : vérifie une condition
                if (s.length() < 3) continue; //invalid line
                // Appelle une méthode
                final int declareSeparator = s.indexOf('=');
                // Embranchement : vérifie une condition
                if (declareSeparator > -1) {
                    // Appelle une méthode
                    final String id = s.substring(0, declareSeparator);
                    // Appelle une méthode
                    final String meta = s.substring(declareSeparator + 1);
                    // Appelle une méthode
                    nodes.put(id, new TestNode(new ArrayList<>(), meta, new AtomicReference<>()));
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    final int childSeparator = s.indexOf('-');
                    // Embranchement : vérifie une condition
                    if (childSeparator > -1) {
                        // Appelle une méthode
                        references.put(s.substring(0, childSeparator), s.substring(childSeparator + 2).split(" "));
                    // Branche alternative de la condition
                    } else {
                        // Appelle une méthode
                        final int redirectSeparator = s.indexOf('+');
                        // Appelle une méthode
                        references.put(s.substring(0, redirectSeparator), new String[]{null, s.substring(redirectSeparator + 2)});
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final ArrayList<TestNode> result = new ArrayList<>();
            // Appelle une méthode
            List<Runnable> redirectSetters = new ArrayList<>();
            // Appelle une méthode
            resolveNode(rootId, references, nodes, result, new HashMap<>(), redirectSetters, "");
            // Appelle une méthode
            redirectSetters.forEach(Runnable::run);
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        private static String resolveNode(String id, Map<String, String[]> references,
                                          // Instruction de code
                                          Map<String, TestNode> nodes, ArrayList<TestNode> result,
                                          // Instruction de code
                                          Map<String, String> nameToMetaPath,
                                          // Début d'une méthode/d'un bloc
                                          List<Runnable> redirectSetters, String metaPath) {
            // Appelle une méthode
            final TestNode node = nodes.get(id);
            // Appelle une méthode
            final String[] refs = references.get(id);
            // Affecte une valeur
            final String path = metaPath + "#" + node.meta;
            // Embranchement : vérifie une condition
            if (refs == null) {
                // Appelle une méthode
                result.add(node);
                // Appelle une méthode
                nameToMetaPath.put(id, path);
                // Renvoie une valeur à l'appelant
                return path;
            // Embranchement : vérifie une condition
            } else if (refs[0] == null) {
                // Appelle une méthode
                redirectSetters.add(() -> node.redirect.set(nameToMetaPath.get(refs[1])));
            // Branche alternative de la condition
            } else {
                // Boucle : répète un bloc
                for (String ref : refs) {
                    // Appelle une méthode
                    node.children.add(resolveNode(ref, references, nodes, result, nameToMetaPath, redirectSetters, path));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            result.add(node);
            // Appelle une méthode
            nameToMetaPath.put(id, path);
            // Renvoie une valeur à l'appelant
            return path;
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record TestNode(List<String> children, String meta, AtomicReference<String> redirect) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean equals(Object obj) {
                // Embranchement : vérifie une condition
                if (obj instanceof TestNode(List<String> children1, String meta1, AtomicReference<String> redirect1)) {
                    // Renvoie une valeur à l'appelant
                    return this.meta.equals(meta1) && Objects.equals(this.redirect.get(), redirect1.get()) &&
                            // Accès à l'objet courant/parent
                            this.children.containsAll(children1) && this.children.size() == children1.size();
                // Branche alternative de la condition
                } else {
                    // Renvoie une valeur à l'appelant
                    return false;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static String exportGarphvizDot(DeclareCommandsPacket packet, boolean prettyPrint) {
        // Appelle une méthode
        final StringBuilder builder = new StringBuilder();
        // Affecte une valeur
        final char statementSeparator = ';';
        // Appelle une méthode
        builder.append("digraph G {");
        // Appelle une méthode
        builder.append("rankdir=LR");
        // Appelle une méthode
        builder.append(statementSeparator);
        // Appelle une méthode
        builder.append(packet.rootIndex());
        // Appelle une méthode
        builder.append(" [label=\"root\",shape=rectangle]");
        // Appelle une méthode
        builder.append(statementSeparator);
        // Appelle une méthode
        List<DeclareCommandsPacket.Node> nodes = packet.nodes();
        // Boucle : répète un bloc
        for (int i = 0; i < nodes.size(); i++) {
            // Appelle une méthode
            DeclareCommandsPacket.Node node = nodes.get(i);
            // Embranchement : vérifie une condition
            if ((node.flags & 0x3) != 0) {
                // Appelle une méthode
                builder.append(i);
                // Appelle une méthode
                builder.append(" [label=");
                // Appelle une méthode
                builder.append('"');
                // Embranchement : vérifie une condition
                if ((node.flags & 0x3) == 1) {
                    // Appelle une méthode
                    builder.append("'");
                    // Appelle une méthode
                    builder.append(node.name);
                    // Appelle une méthode
                    builder.append("'");
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    builder.append(node.name);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                builder.append('"');
                // Embranchement : vérifie une condition
                if ((node.flags & 0x4) == 0x4) {
                    // Appelle une méthode
                    builder.append(",bgcolor=gray,style=filled");
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                builder.append("]");
                // Appelle une méthode
                builder.append(statementSeparator);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (node.children.length == 0 && (node.flags & 0x8) == 0) continue;
            // Appelle une méthode
            builder.append(i);
            // Appelle une méthode
            builder.append(" -> { ");
            // Embranchement : vérifie une condition
            if ((node.flags & 0x8) == 0) {
                // Appelle une méthode
                builder.append(Arrays.stream(node.children).mapToObj(Integer::toString).collect(Collectors.joining(" ")));
                // Appelle une méthode
                builder.append(" }");
                // Appelle une méthode
                builder.append(statementSeparator);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                builder.append(node.redirectedNode);
                // Appelle une méthode
                builder.append(" } [style = dotted]");
                // Appelle une méthode
                builder.append(statementSeparator);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        builder.append("}");
        // Embranchement : vérifie une condition
        if (prettyPrint)
            // Renvoie une valeur à l'appelant
            return builder.toString()
                    // Instruction de code
                    .replaceFirst("\\{r", "{\n  r")
                    // Instruction de code
                    .replace(";", "\n  ")
                    // Appelle une méthode
                    .replaceFirst(" {2}}$", "}\n");
        // Branche alternative de la condition
        else
            // Renvoie une valeur à l'appelant
            return builder.toString();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
