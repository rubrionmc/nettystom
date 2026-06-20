// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.builder.arguments.*;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.BiConsumer;

// Type declaration (class/interface/enum/record)
final class GraphConverter {
    // Start of a method/block
    private GraphConverter() {
        //no instance
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("_, _ -> new")
    // Start of a method/block
    public static DeclareCommandsPacket createPacket(Graph graph, @Nullable Player player) {
        // Calls a method
        List<DeclareCommandsPacket.Node> nodes = new ArrayList<>();
        // Calls a method
        List<BiConsumer<Graph, Integer>> redirects = new ArrayList<>();
        // Calls a method
        Map<Argument<?>, Integer> argToPacketId = new HashMap<>();
        // Calls a method
        final AtomicInteger idSource = new AtomicInteger(0);
        // Calls a method
        final int rootId = append(graph.root(), nodes, redirects, idSource, null, player, argToPacketId)[0];
        // Loop: repeats a block
        for (var r : redirects) {
            // Calls a method
            r.accept(graph, rootId);
        // End of a block/expression
        }
        // Returns a value to the caller
        return new DeclareCommandsPacket(nodes, rootId);
    // End of a block/expression
    }

    // Code statement
    private static int[] append(Graph.Node graphNode, List<DeclareCommandsPacket.Node> to,
                                // Code statement
                                List<BiConsumer<Graph, Integer>> redirects, AtomicInteger id, @Nullable AtomicInteger redirect,
                                // Annotation for the following element
                                @Nullable Player player, Map<Argument<?>, Integer> argToPacketId) {
        // Calls a method
        final Graph.Execution execution = graphNode.execution();
        // Branch: checks a condition
        if (player != null && execution != null) {
            // Branch: checks a condition
            if (!execution.test(player)) return new int[0];
        // End of a block/expression
        }

        // Calls a method
        final Argument<?> argument = graphNode.argument();
        // Calls a method
        final List<Graph.Node> children = graphNode.next();

        // Calls a method
        final DeclareCommandsPacket.Node node = new DeclareCommandsPacket.Node();
        // Calls a method
        int[] packetNodeChildren = new int[children.size()];
        // Loop: repeats a block
        for (int i = 0, appendIndex = 0; i < children.size(); i++) {
            // Calls a method
            final int[] append = append(children.get(i), to, redirects, id, redirect, player, argToPacketId);
            // Branch: checks a condition
            if (append.length > 0) {
                // Calls a method
                argToPacketId.put(children.get(i).argument(), append[0]);
            // End of a block/expression
            }
            // Branch: checks a condition
            if (append.length == 1) {
                // Assigns a value
                packetNodeChildren[appendIndex++] = append[0];
            // Alternative branch of the condition
            } else {
                // Calls a method
                packetNodeChildren = Arrays.copyOf(packetNodeChildren, packetNodeChildren.length + append.length - 1);
                // Calls a method
                System.arraycopy(append, 0, packetNodeChildren, appendIndex, append.length);
                // Code statement
                appendIndex += append.length;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Assigns a value
        node.children = packetNodeChildren;

        // Calls a method
        boolean isExecutable = graphNode.execution() != null && graphNode.execution().executor() != null;

        // Branch: checks a condition
        if (argument instanceof ArgumentLiteral literal) {
            // Branch: checks a condition
            if (literal.getId().isEmpty()) {
                // Assigns a value
                node.flags = 0; //root
            // Alternative branch of the condition
            } else {
                // Calls a method
                node.flags = literal(isExecutable, false);
                // Calls a method
                node.name = argument.getId();
                // Branch: checks a condition
                if (redirect != null) {
                    // Code statement
                    node.flags |= 0x8;
                    // Calls a method
                    redirects.add((graph, root) -> node.redirectedNode = redirect.get());
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            to.add(node);
            // Returns a value to the caller
            return new int[]{id.getAndIncrement()};
        // Alternative branch of the condition
        } else {
            // Branch: checks a condition
            if (argument instanceof ArgumentCommand argCmd) {
                // Calls a method
                node.flags = literal(isExecutable, true);
                // Calls a method
                node.name = argument.getId();
                // Calls a method
                final String shortcut = argCmd.getShortcut();
                // Branch: checks a condition
                if (shortcut.isEmpty()) {
                    // Calls a method
                    redirects.add((graph, root) -> node.redirectedNode = root);
                // Alternative branch of the condition
                } else {
                    // Start of a method/block
                    redirects.add((graph, root) -> {
                        // Calls a method
                        var sender = player == null ? MinecraftServer.getCommandManager().getConsoleSender() : player;
                        // Calls a method
                        final List<Argument<?>> args = CommandParser.parser().parse(sender, graph, shortcut).args();
                        // Calls a method
                        final Argument<?> last = args.getLast();
                        // Branch: checks a condition
                        if (last.allowSpace()) {
                            // Calls a method
                            node.redirectedNode = argToPacketId.get(args.get(args.size() - 2));
                        // Alternative branch of the condition
                        } else {
                            // Calls a method
                            node.redirectedNode = argToPacketId.get(last);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    });
                // End of a block/expression
                }
                // Calls a method
                to.add(node);

                // Returns a value to the caller
                return new int[]{id.getAndIncrement()};
            // Branch: checks a condition
            } else if (argument instanceof ArgumentEnum<?> || (argument instanceof ArgumentWord word && word.hasRestrictions())) {
                // Assigns a value
                List<String> entries = argument instanceof ArgumentEnum<?> ?
                        // Code statement
                        ((ArgumentEnum<?>) argument).entries() :
                        // Calls a method
                        Arrays.stream(((ArgumentWord) argument).getRestrictions()).toList();
                // Calls a method
                final int[] res = new int[entries.size()];
                // Loop: repeats a block
                for (int i = 0; i < res.length; i++) {
                    // Calls a method
                    String entry = entries.get(i);
                    // Calls a method
                    final DeclareCommandsPacket.Node subNode = new DeclareCommandsPacket.Node();
                    // Assigns a value
                    subNode.children = node.children;
                    // Calls a method
                    subNode.flags = literal(isExecutable, false);
                    // Assigns a value
                    subNode.name = entry;
                    // Branch: checks a condition
                    if (redirect != null) {
                        // Code statement
                        subNode.flags |= 0x8;
                        // Calls a method
                        redirects.add((graph, root) -> subNode.redirectedNode = redirect.get());
                    // End of a block/expression
                    }
                    // Calls a method
                    to.add(subNode);
                    // Calls a method
                    res[i] = id.getAndIncrement();
                // End of a block/expression
                }
                // Returns a value to the caller
                return res;
            // Branch: checks a condition
            } else if (argument instanceof ArgumentGroup special) {
                // Calls a method
                List<Argument<?>> entries = special.group();
                // Assigns a value
                int[] res = null;
                // Assigns a value
                int[] last = new int[0];
                // Loop: repeats a block
                for (int i = 0; i < entries.size(); i++) {
                    // Calls a method
                    Argument<?> entry = entries.get(i);
                    // Branch: checks a condition
                    if (i == entries.size() - 1) {
                        // Last will be the parent of next args
                        // Assigns a value
                        final int[] l = append(new GraphImpl.NodeImpl(entry, null, List.of()), to, redirects,
                                // Code statement
                                id, redirect, player, argToPacketId);
                        // Loop: repeats a block
                        for (int n : l) {
                            // Calls a method
                            to.get(n).children = node.children;
                        // End of a block/expression
                        }
                        // Loop: repeats a block
                        for (int n : last) {
                            // Calls a method
                            to.get(n).children = l;
                        // End of a block/expression
                        }
                        // Returns a value to the caller
                        return res == null ? l : res;
                    // Branch: checks a condition
                    } else if (i == 0) {
                        // First will be the children & parent of following
                        // Assigns a value
                        res = append(new GraphImpl.NodeImpl(entry, null, List.of()), to, redirects, id,
                                // Code statement
                                null, player, argToPacketId);
                        // Assigns a value
                        last = res;
                    // Alternative branch of the condition
                    } else {
                        // Assigns a value
                        final int[] l = append(new GraphImpl.NodeImpl(entry, null, List.of()), to, redirects,
                                // Code statement
                                id, null, player, argToPacketId);
                        // Loop: repeats a block
                        for (int n : last) {
                            // Calls a method
                            to.get(n).children = l;
                        // End of a block/expression
                        }
                        // Assigns a value
                        last = l;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Throws an exception
                throw new RuntimeException("Arg group must have child args.");
            // Branch: checks a condition
            } else if (argument instanceof ArgumentLoop special) {
                // Calls a method
                AtomicInteger r = new AtomicInteger();
                // Calls a method
                int[] res = new int[special.arguments().size()];
                // Calls a method
                List<?> arguments = special.arguments();
                // Loop: repeats a block
                for (int i = 0, appendIndex = 0; i < arguments.size(); i++) {
                    // Calls a method
                    Object arg = arguments.get(i);
                    // Assigns a value
                    final int[] append = append(new GraphImpl.NodeImpl((Argument<?>) arg, null, List.of()), to,
                            // Code statement
                            redirects, id, r, player, argToPacketId);
                    // Branch: checks a condition
                    if (append.length == 1) {
                        // Assigns a value
                        res[appendIndex++] = append[0];
                    // Alternative branch of the condition
                    } else {
                        // Calls a method
                        res = Arrays.copyOf(res, res.length + append.length - 1);
                        // Calls a method
                        System.arraycopy(append, 0, res, appendIndex, append.length);
                        // Code statement
                        appendIndex += append.length;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Calls a method
                r.set(id.get());
                // Returns a value to the caller
                return res;
            // Alternative branch of the condition
            } else {
                // Calls a method
                final boolean hasSuggestion = argument.hasSuggestion();
                // Calls a method
                node.flags = arg(isExecutable, hasSuggestion);
                // Calls a method
                node.name = argument.getId();
                // Calls a method
                node.parser = argument.parser();
                // Calls a method
                node.properties = argument.nodeProperties();
                // Branch: checks a condition
                if (redirect != null) {
                    // Code statement
                    node.flags |= 0x8;
                    // Calls a method
                    redirects.add((graph, root) -> node.redirectedNode = redirect.get());
                // End of a block/expression
                }
                // Branch: checks a condition
                if (hasSuggestion) {
                    // Calls a method
                    node.suggestionsType = argument.suggestionType().getIdentifier();
                // End of a block/expression
                }
                // Calls a method
                to.add(node);
                // Returns a value to the caller
                return new int[]{id.getAndIncrement()};
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static byte literal(boolean executable, boolean hasRedirect) {
        // Returns a value to the caller
        return DeclareCommandsPacket.getFlag(DeclareCommandsPacket.NodeType.LITERAL, executable, hasRedirect, false);
    // End of a block/expression
    }

    // Start of a method/block
    private static byte arg(boolean executable, boolean hasSuggestion) {
        // Returns a value to the caller
        return DeclareCommandsPacket.getFlag(DeclareCommandsPacket.NodeType.ARGUMENT, executable, false, hasSuggestion);
    // End of a block/expression
    }
// End of a block/expression
}
