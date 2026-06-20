// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import of a required class
import org.opentest4j.AssertionFailedError;

// Import of a required class
import java.math.BigInteger;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.stream.Collectors;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.fail;

// Type declaration (class/interface/enum/record)
public class CommandTestUtils {

    // Start of a method/block
    public static void assertPacket(DeclareCommandsPacket packet, String expectedStructure) {
        // Calls a method
        final List<NodeStructure.TestNode> expectedList = NodeStructure.fromString("0\n0=$root$\n" + expectedStructure);
        // Calls a method
        final List<NodeStructure.TestNode> actualList = NodeStructure.fromString(NodeStructure.packetToString(packet));
        // Exception handling
        try {
            // Calls a method
            assertEquals(expectedList.size(), actualList.size(), "Different node counts");
            // Loop: repeats a block
            for (NodeStructure.TestNode expected : expectedList) {
                // Assigns a value
                boolean found = false;
                // Loop: repeats a block
                for (NodeStructure.TestNode actual : actualList) {
                    // Branch: checks a condition
                    if (expected.equals(actual)) {
                        // Assigns a value
                        found = true;
                        // Breaks out of the loop/block
                        break;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Branch: checks a condition
                if (!found) {
                    // Calls a method
                    fail("Packet doesn't contain " + expected.toString());
                // End of a block/expression
                }
            // End of a block/expression
            }
        // Start of a method/block
        } catch (AssertionFailedError error) {
            // Calls a method
            fail("Graphs didn't match. Actual graph from packet: " + CommandTestUtils.exportGarphvizDot(packet, false), error);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static class NodeStructure {
        // Assigns a value
        private static final Map<Character, Function<String, Collection<String>>> functions = Map.of(
                // Start of a method/block
                '!', s -> {
                    // Calls a method
                    final String[] strings = splitDeclaration(s);
                    // Calls a method
                    final ArrayList<String> result = new ArrayList<>();
                    // Loop: repeats a block
                    for (String s1 : strings[0].split(" ")) {
                        // Calls a method
                        result.add(s1 + "=" + (strings[1].replaceAll("!", s1)));
                    // End of a block/expression
                    }
                    // Returns a value to the caller
                    return result;
                // Code statement
                },
                // Start of a method/block
                '%', s -> {
                    // Calls a method
                    final String[] strings = splitDeclaration(s);
                    // Calls a method
                    final ArrayList<String> result = new ArrayList<>();
                    // Loop: repeats a block
                    for (String s1 : strings[0].split(" ")) {
                        // Calls a method
                        result.add(s1 + "=" + (strings[1].replaceAll("%", "'" + s1 + "'")));
                    // End of a block/expression
                    }
                    // Returns a value to the caller
                    return result;
                // Code statement
                },
                // Start of a method/block
                '§', s -> {
                    // Calls a method
                    final String[] strings = splitDeclaration(s);
                    // Calls a method
                    final ArrayList<String> result = new ArrayList<>();
                    // Loop: repeats a block
                    for (String s1 : strings[0].split(" ")) {
                        // Calls a method
                        result.add(s1 + "=" + (strings[1].replaceAll("§", "'" + (s1.toUpperCase(Locale.ROOT)) + "'")));
                    // End of a block/expression
                    }
                    // Returns a value to the caller
                    return result;
                // End of a block/expression
                }
        // End of a block/expression
        );
        // Calls a method
        private static final Set<Character> placeholders = functions.keySet();

        // Start of a method/block
        static String packetToString(DeclareCommandsPacket packet) {
            // Assigns a value
            final char lineSeparator = '\n';
            // Calls a method
            final StringBuilder builder = new StringBuilder();
            // Calls a method
            builder.append(packet.rootIndex());
            // Calls a method
            builder.append(lineSeparator);
            // Calls a method
            List<DeclareCommandsPacket.Node> nodes = packet.nodes();
            // Loop: repeats a block
            for (int i = 0; i < nodes.size(); i++) {
                // Calls a method
                DeclareCommandsPacket.Node node = nodes.get(i);
                // Calls a method
                builder.append(i);
                // Calls a method
                builder.append('=');
                // Meta
                // Branch: checks a condition
                if ((node.flags & 0x3) == 0) {
                    // Calls a method
                    builder.append("$root$");
                // Alternative branch of the condition
                } else {
                    // Branch: checks a condition
                    if ((node.flags & 0x3) == 1) {
                        // Calls a method
                        builder.append("'");
                        // Calls a method
                        builder.append(node.name);
                        // Calls a method
                        builder.append("'");
                    // Alternative branch of the condition
                    } else {
                        // Calls a method
                        builder.append(node.name);
                        // Calls a method
                        builder.append(' ');
                        // Calls a method
                        builder.append(node.parser);

                        // Branch: checks a condition
                        if (node.properties != null) {
                            // Calls a method
                            builder.append(' ');
                            // Calls a method
                            builder.append(new BigInteger(node.properties).toString(16));
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Branch: checks a condition
                if ((node.flags & 0x4) == 0x4) {
                    // Calls a method
                    builder.append(" executable");
                // End of a block/expression
                }
                // Branch: checks a condition
                if ((node.flags & 0x10) == 0x10) {
                    // Calls a method
                    builder.append(' ');
                    // Calls a method
                    builder.append(node.suggestionsType);
                // End of a block/expression
                }
                // Calls a method
                builder.append(lineSeparator);
                // Branch: checks a condition
                if (node.children.length > 0) {
                    // Calls a method
                    builder.append(i);
                    // Calls a method
                    builder.append("->");
                    // Calls a method
                    builder.append(Arrays.stream(node.children).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
                    // Calls a method
                    builder.append(lineSeparator);
                // End of a block/expression
                }
                // Branch: checks a condition
                if ((node.flags & 0x8) == 0x8) {
                    // Calls a method
                    builder.append(i);
                    // Calls a method
                    builder.append("+>");
                    // Calls a method
                    builder.append(node.redirectedNode);
                    // Calls a method
                    builder.append(lineSeparator);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return builder.toString();
        // End of a block/expression
        }

        // Start of a method/block
        private static String[] splitDeclaration(String input) {
            // Returns a value to the caller
            return input.split("=", 2);
        // End of a block/expression
        }

        // Start of a method/block
        private static List<String> preProcessString(String string) {
            // Calls a method
            final List<String> strings = Arrays.stream(string.split("\n")).toList();
            // Calls a method
            final ArrayList<String> result = new ArrayList<>();
            // Loop: repeats a block
            for (String s : strings) {
                // Branch: checks a condition
                if (s.indexOf('=') > -1) {
                    // Assigns a value
                    boolean match = false;
                    // Loop: repeats a block
                    for (Character placeholder : placeholders) {
                        // Branch: checks a condition
                        if (s.indexOf(placeholder) > -1) {
                            // Calls a method
                            result.addAll(functions.get(placeholder).apply(s));
                            // Assigns a value
                            match = true;
                            // Breaks out of the loop/block
                            break;
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                    // Branch: checks a condition
                    if (!match) {
                        // Calls a method
                        final int spaceIndex = s.indexOf(" ");
                        // Branch: checks a condition
                        if (spaceIndex > -1 && spaceIndex < s.indexOf('=')) {
                            // Calls a method
                            final String[] split = s.split("=", 2);
                            // Loop: repeats a block
                            for (String s1 : split[0].split(" ")) {
                                // Calls a method
                                result.add(s1 + "=" + split[1]);
                            // End of a block/expression
                            }
                        // Alternative branch of the condition
                        } else {
                            // Calls a method
                            result.add(s);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    final int spaceIndex = s.indexOf(" ");
                    // Branch: checks a condition
                    if (spaceIndex > -1 && spaceIndex < s.indexOf('-')) {
                        // Calls a method
                        final String[] split = s.split("-", 2);
                        // Loop: repeats a block
                        for (String s1 : split[0].split(" ")) {
                            // Calls a method
                            result.add(s1 + "-" + split[1]);
                        // End of a block/expression
                        }
                    // Branch: checks a condition
                    } else if (spaceIndex > -1 && spaceIndex < s.indexOf('+')) {
                        // Calls a method
                        final String[] split = s.split("\\+", 2);
                        // Loop: repeats a block
                        for (String s1 : split[0].split(" ")) {
                            // Calls a method
                            result.add(s1 + "+" + split[1]);
                        // End of a block/expression
                        }
                    // Alternative branch of the condition
                    } else {
                        // Calls a method
                        result.add(s);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }

        // Start of a method/block
        static List<TestNode> fromString(String input) {
            // Calls a method
            Map<String, String[]> references = new HashMap<>();
            // Calls a method
            Map<String, TestNode> nodes = new HashMap<>();
            // Calls a method
            final List<String> strings = preProcessString(input);
            // Calls a method
            final String rootId = strings.getFirst();

            // Loop: repeats a block
            for (String s : strings.stream().skip(0).toList()) {
                // Branch: checks a condition
                if (s.length() < 3) continue; //invalid line
                // Calls a method
                final int declareSeparator = s.indexOf('=');
                // Branch: checks a condition
                if (declareSeparator > -1) {
                    // Calls a method
                    final String id = s.substring(0, declareSeparator);
                    // Calls a method
                    final String meta = s.substring(declareSeparator + 1);
                    // Calls a method
                    nodes.put(id, new TestNode(new ArrayList<>(), meta, new AtomicReference<>()));
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    final int childSeparator = s.indexOf('-');
                    // Branch: checks a condition
                    if (childSeparator > -1) {
                        // Calls a method
                        references.put(s.substring(0, childSeparator), s.substring(childSeparator + 2).split(" "));
                    // Alternative branch of the condition
                    } else {
                        // Calls a method
                        final int redirectSeparator = s.indexOf('+');
                        // Calls a method
                        references.put(s.substring(0, redirectSeparator), new String[]{null, s.substring(redirectSeparator + 2)});
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            final ArrayList<TestNode> result = new ArrayList<>();
            // Calls a method
            List<Runnable> redirectSetters = new ArrayList<>();
            // Calls a method
            resolveNode(rootId, references, nodes, result, new HashMap<>(), redirectSetters, "");
            // Calls a method
            redirectSetters.forEach(Runnable::run);
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }

        // Code statement
        private static String resolveNode(String id, Map<String, String[]> references,
                                          // Code statement
                                          Map<String, TestNode> nodes, ArrayList<TestNode> result,
                                          // Code statement
                                          Map<String, String> nameToMetaPath,
                                          // Start of a method/block
                                          List<Runnable> redirectSetters, String metaPath) {
            // Calls a method
            final TestNode node = nodes.get(id);
            // Calls a method
            final String[] refs = references.get(id);
            // Assigns a value
            final String path = metaPath + "#" + node.meta;
            // Branch: checks a condition
            if (refs == null) {
                // Calls a method
                result.add(node);
                // Calls a method
                nameToMetaPath.put(id, path);
                // Returns a value to the caller
                return path;
            // Branch: checks a condition
            } else if (refs[0] == null) {
                // Calls a method
                redirectSetters.add(() -> node.redirect.set(nameToMetaPath.get(refs[1])));
            // Alternative branch of the condition
            } else {
                // Loop: repeats a block
                for (String ref : refs) {
                    // Calls a method
                    node.children.add(resolveNode(ref, references, nodes, result, nameToMetaPath, redirectSetters, path));
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            result.add(node);
            // Calls a method
            nameToMetaPath.put(id, path);
            // Returns a value to the caller
            return path;
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        record TestNode(List<String> children, String meta, AtomicReference<String> redirect) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean equals(Object obj) {
                // Branch: checks a condition
                if (obj instanceof TestNode(List<String> children1, String meta1, AtomicReference<String> redirect1)) {
                    // Returns a value to the caller
                    return this.meta.equals(meta1) && Objects.equals(this.redirect.get(), redirect1.get()) &&
                            // Access to the current/parent object
                            this.children.containsAll(children1) && this.children.size() == children1.size();
                // Alternative branch of the condition
                } else {
                    // Returns a value to the caller
                    return false;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    static String exportGarphvizDot(DeclareCommandsPacket packet, boolean prettyPrint) {
        // Calls a method
        final StringBuilder builder = new StringBuilder();
        // Assigns a value
        final char statementSeparator = ';';
        // Calls a method
        builder.append("digraph G {");
        // Calls a method
        builder.append("rankdir=LR");
        // Calls a method
        builder.append(statementSeparator);
        // Calls a method
        builder.append(packet.rootIndex());
        // Calls a method
        builder.append(" [label=\"root\",shape=rectangle]");
        // Calls a method
        builder.append(statementSeparator);
        // Calls a method
        List<DeclareCommandsPacket.Node> nodes = packet.nodes();
        // Loop: repeats a block
        for (int i = 0; i < nodes.size(); i++) {
            // Calls a method
            DeclareCommandsPacket.Node node = nodes.get(i);
            // Branch: checks a condition
            if ((node.flags & 0x3) != 0) {
                // Calls a method
                builder.append(i);
                // Calls a method
                builder.append(" [label=");
                // Calls a method
                builder.append('"');
                // Branch: checks a condition
                if ((node.flags & 0x3) == 1) {
                    // Calls a method
                    builder.append("'");
                    // Calls a method
                    builder.append(node.name);
                    // Calls a method
                    builder.append("'");
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    builder.append(node.name);
                // End of a block/expression
                }
                // Calls a method
                builder.append('"');
                // Branch: checks a condition
                if ((node.flags & 0x4) == 0x4) {
                    // Calls a method
                    builder.append(",bgcolor=gray,style=filled");
                // End of a block/expression
                }
                // Calls a method
                builder.append("]");
                // Calls a method
                builder.append(statementSeparator);
            // End of a block/expression
            }
            // Branch: checks a condition
            if (node.children.length == 0 && (node.flags & 0x8) == 0) continue;
            // Calls a method
            builder.append(i);
            // Calls a method
            builder.append(" -> { ");
            // Branch: checks a condition
            if ((node.flags & 0x8) == 0) {
                // Calls a method
                builder.append(Arrays.stream(node.children).mapToObj(Integer::toString).collect(Collectors.joining(" ")));
                // Calls a method
                builder.append(" }");
                // Calls a method
                builder.append(statementSeparator);
            // Alternative branch of the condition
            } else {
                // Calls a method
                builder.append(node.redirectedNode);
                // Calls a method
                builder.append(" } [style = dotted]");
                // Calls a method
                builder.append(statementSeparator);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        builder.append("}");
        // Branch: checks a condition
        if (prettyPrint)
            // Returns a value to the caller
            return builder.toString()
                    // Code statement
                    .replaceFirst("\\{r", "{\n  r")
                    // Code statement
                    .replace(";", "\n  ")
                    // Calls a method
                    .replaceFirst(" {2}}$", "}\n");
        // Alternative branch of the condition
        else
            // Returns a value to the caller
            return builder.toString();
    // End of a block/expression
    }

// End of a block/expression
}
