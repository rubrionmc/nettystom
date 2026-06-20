// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.BiConsumer;

// Déclaration de type (classe/interface/enum/record)
final class GraphConverter {
    // Début d'une méthode/d'un bloc
    private GraphConverter() {
        //no instance
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("_, _ -> new")
    // Début d'une méthode/d'un bloc
    public static DeclareCommandsPacket createPacket(Graph graph, @Nullable Player player) {
        // Affecte une valeur
        List<DeclareCommandsPacket.Node> nodes = new ArrayList<>();
        // Affecte une valeur
        List<BiConsumer<Graph, Integer>> redirects = new ArrayList<>();
        // Affecte une valeur
        Map<Argument<?>, Integer> argToPacketId = new HashMap<>();
        // Appelle une méthode
        final AtomicInteger idSource = new AtomicInteger(0);
        // Appelle une méthode
        final int rootId = append(graph.root(), nodes, redirects, idSource, null, player, argToPacketId)[0];
        // Boucle : répète un bloc
        for (var r : redirects) {
            // Appelle une méthode
            r.accept(graph, rootId);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new DeclareCommandsPacket(nodes, rootId);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static int[] append(Graph.Node graphNode, List<DeclareCommandsPacket.Node> to,
                                // Instruction de code
                                List<BiConsumer<Graph, Integer>> redirects, AtomicInteger id, @Nullable AtomicInteger redirect,
                                // Annotation pour l'élément suivant
                                @Nullable Player player, Map<Argument<?>, Integer> argToPacketId) {
        // Appelle une méthode
        final Graph.Execution execution = graphNode.execution();
        // Embranchement : vérifie une condition
        if (player != null && execution != null) {
            // Embranchement : vérifie une condition
            if (!execution.test(player)) return new int[0];
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final Argument<?> argument = graphNode.argument();
        // Appelle une méthode
        final List<Graph.Node> children = graphNode.next();

        // Appelle une méthode
        final DeclareCommandsPacket.Node node = new DeclareCommandsPacket.Node();
        // Appelle une méthode
        int[] packetNodeChildren = new int[children.size()];
        // Boucle : répète un bloc
        for (int i = 0, appendIndex = 0; i < children.size(); i++) {
            // Appelle une méthode
            final int[] append = append(children.get(i), to, redirects, id, redirect, player, argToPacketId);
            // Embranchement : vérifie une condition
            if (append.length > 0) {
                // Appelle une méthode
                argToPacketId.put(children.get(i).argument(), append[0]);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (append.length == 1) {
                // Affecte une valeur
                packetNodeChildren[appendIndex++] = append[0];
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                packetNodeChildren = Arrays.copyOf(packetNodeChildren, packetNodeChildren.length + append.length - 1);
                // Appelle une méthode
                System.arraycopy(append, 0, packetNodeChildren, appendIndex, append.length);
                // Affecte une valeur
                appendIndex += append.length;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        node.children = packetNodeChildren;

        // Appelle une méthode
        boolean isExecutable = graphNode.execution() != null && graphNode.execution().executor() != null;

        // Embranchement : vérifie une condition
        if (argument instanceof ArgumentLiteral literal) {
            // Embranchement : vérifie une condition
            if (literal.getId().isEmpty()) {
                // Affecte une valeur
                node.flags = 0; //root
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                node.flags = literal(isExecutable, false);
                // Appelle une méthode
                node.name = argument.getId();
                // Embranchement : vérifie une condition
                if (redirect != null) {
                    // Affecte une valeur
                    node.flags |= 0x8;
                    // Appelle une méthode
                    redirects.add((graph, root) -> node.redirectedNode = redirect.get());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            to.add(node);
            // Renvoie une valeur à l'appelant
            return new int[]{id.getAndIncrement()};
        // Branche alternative de la condition
        } else {
            // Embranchement : vérifie une condition
            if (argument instanceof ArgumentCommand argCmd) {
                // Appelle une méthode
                node.flags = literal(isExecutable, true);
                // Appelle une méthode
                node.name = argument.getId();
                // Appelle une méthode
                final String shortcut = argCmd.getShortcut();
                // Embranchement : vérifie une condition
                if (shortcut.isEmpty()) {
                    // Appelle une méthode
                    redirects.add((graph, root) -> node.redirectedNode = root);
                // Branche alternative de la condition
                } else {
                    // Début d'une méthode/d'un bloc
                    redirects.add((graph, root) -> {
                        // Appelle une méthode
                        var sender = player == null ? MinecraftServer.getCommandManager().getConsoleSender() : player;
                        // Appelle une méthode
                        final List<Argument<?>> args = CommandParser.parser().parse(sender, graph, shortcut).args();
                        // Appelle une méthode
                        final Argument<?> last = args.get(args.size() - 1);
                        // Embranchement : vérifie une condition
                        if (last.allowSpace()) {
                            // Appelle une méthode
                            node.redirectedNode = argToPacketId.get(args.get(args.size()-2));
                        // Branche alternative de la condition
                        } else {
                            // Appelle une méthode
                            node.redirectedNode = argToPacketId.get(last);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    });
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                to.add(node);

                // Renvoie une valeur à l'appelant
                return new int[]{id.getAndIncrement()};
            // Embranchement : vérifie une condition
            } else if (argument instanceof ArgumentEnum<?> || (argument instanceof ArgumentWord word && word.hasRestrictions())) {
                // Affecte une valeur
                List<String> entries = argument instanceof ArgumentEnum<?> ?
                        // Instruction de code
                        ((ArgumentEnum<?>) argument).entries() :
                        // Appelle une méthode
                        Arrays.stream(((ArgumentWord) argument).getRestrictions()).toList();
                // Appelle une méthode
                final int[] res = new int[entries.size()];
                // Boucle : répète un bloc
                for (int i = 0; i < res.length; i++) {
                    // Appelle une méthode
                    String entry = entries.get(i);
                    // Appelle une méthode
                    final DeclareCommandsPacket.Node subNode = new DeclareCommandsPacket.Node();
                    // Affecte une valeur
                    subNode.children = node.children;
                    // Appelle une méthode
                    subNode.flags = literal(isExecutable, false);
                    // Affecte une valeur
                    subNode.name = entry;
                    // Embranchement : vérifie une condition
                    if (redirect != null) {
                        // Affecte une valeur
                        subNode.flags |= 0x8;
                        // Appelle une méthode
                        redirects.add((graph, root) -> subNode.redirectedNode = redirect.get());
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    to.add(subNode);
                    // Appelle une méthode
                    res[i] = id.getAndIncrement();
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return res;
            // Embranchement : vérifie une condition
            } else if (argument instanceof ArgumentGroup special) {
                // Appelle une méthode
                List<Argument<?>> entries = special.group();
                // Affecte une valeur
                int[] res = null;
                // Affecte une valeur
                int[] last = new int[0];
                // Boucle : répète un bloc
                for (int i = 0; i < entries.size(); i++) {
                    // Appelle une méthode
                    Argument<?> entry = entries.get(i);
                    // Embranchement : vérifie une condition
                    if (i == entries.size() - 1) {
                        // Last will be the parent of next args
                        // Affecte une valeur
                        final int[] l = append(new GraphImpl.NodeImpl(entry, null, List.of()), to, redirects,
                                // Instruction de code
                                id, redirect, player, argToPacketId);
                        // Boucle : répète un bloc
                        for (int n : l) {
                            // Appelle une méthode
                            to.get(n).children = node.children;
                        // Fin d'un bloc/d'une expression
                        }
                        // Boucle : répète un bloc
                        for (int n : last) {
                            // Appelle une méthode
                            to.get(n).children = l;
                        // Fin d'un bloc/d'une expression
                        }
                        // Renvoie une valeur à l'appelant
                        return res == null ? l : res;
                    // Embranchement : vérifie une condition
                    } else if (i == 0) {
                        // First will be the children & parent of following
                        // Affecte une valeur
                        res = append(new GraphImpl.NodeImpl(entry, null, List.of()), to, redirects, id,
                                // Instruction de code
                                null, player, argToPacketId);
                        // Affecte une valeur
                        last = res;
                    // Branche alternative de la condition
                    } else {
                        // Affecte une valeur
                        final int[] l = append(new GraphImpl.NodeImpl(entry, null, List.of()), to, redirects,
                                // Instruction de code
                                id, null, player, argToPacketId);
                        // Boucle : répète un bloc
                        for (int n : last) {
                            // Appelle une méthode
                            to.get(n).children = l;
                        // Fin d'un bloc/d'une expression
                        }
                        // Affecte une valeur
                        last = l;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Lève une exception
                throw new RuntimeException("Arg group must have child args.");
            // Embranchement : vérifie une condition
            } else if (argument instanceof ArgumentLoop special) {
                // Appelle une méthode
                AtomicInteger r = new AtomicInteger();
                // Appelle une méthode
                int[] res = new int[special.arguments().size()];
                // Appelle une méthode
                List<?> arguments = special.arguments();
                // Boucle : répète un bloc
                for (int i = 0, appendIndex = 0; i < arguments.size(); i++) {
                    // Appelle une méthode
                    Object arg = arguments.get(i);
                    // Affecte une valeur
                    final int[] append = append(new GraphImpl.NodeImpl((Argument<?>) arg, null, List.of()), to,
                            // Instruction de code
                            redirects, id, r, player, argToPacketId);
                    // Embranchement : vérifie une condition
                    if (append.length == 1) {
                        // Affecte une valeur
                        res[appendIndex++] = append[0];
                    // Branche alternative de la condition
                    } else {
                        // Appelle une méthode
                        res = Arrays.copyOf(res, res.length + append.length - 1);
                        // Appelle une méthode
                        System.arraycopy(append, 0, res, appendIndex, append.length);
                        // Affecte une valeur
                        appendIndex += append.length;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                r.set(id.get());
                // Renvoie une valeur à l'appelant
                return res;
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                final boolean hasSuggestion = argument.hasSuggestion();
                // Appelle une méthode
                node.flags = arg(isExecutable, hasSuggestion);
                // Appelle une méthode
                node.name = argument.getId();
                // Appelle une méthode
                node.parser = argument.parser();
                // Appelle une méthode
                node.properties = argument.nodeProperties();
                // Embranchement : vérifie une condition
                if (redirect != null) {
                    // Affecte une valeur
                    node.flags |= 0x8;
                    // Appelle une méthode
                    redirects.add((graph, root) -> node.redirectedNode = redirect.get());
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if (hasSuggestion) {
                    // Appelle une méthode
                    node.suggestionsType = argument.suggestionType().getIdentifier();
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                to.add(node);
                // Renvoie une valeur à l'appelant
                return new int[]{id.getAndIncrement()};
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte literal(boolean executable, boolean hasRedirect) {
        // Renvoie une valeur à l'appelant
        return DeclareCommandsPacket.getFlag(DeclareCommandsPacket.NodeType.LITERAL, executable, hasRedirect, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte arg(boolean executable, boolean hasSuggestion) {
        // Renvoie une valeur à l'appelant
        return DeclareCommandsPacket.getFlag(DeclareCommandsPacket.NodeType.ARGUMENT, executable, false, hasSuggestion);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
